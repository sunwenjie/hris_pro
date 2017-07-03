package com.kan.base.util;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ServiceLocator;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.poi.ReadXlsxHander;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.UploadFileAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentTempVO;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentBatchService;
import com.kan.hro.web.actions.biz.employee.EmployeeSalaryAdjustmentTempAction;

public class ExcelImport4EmployeeSalaryAdjustment
{
   /***
    * 列下标:读取excel的赋值
    * 数据类型:1>字符;2>数值;3>日期
    * 是否为下拉框:1/2
    * 是否必填:1/2
    * new String[]{"列下标","数据类型","是否为下拉框","是否必填","成员变量名","模板列表名中文","模板列名英文"};
    */
   // 验证规则
   public static final List< String[] > VALIDATE_RULE = new ArrayList< String[] >();

   static
   {
      // 初始化验证规则
      VALIDATE_RULE.add( new String[] { "1", "1", "2", "2", "employeeId", "员工ID", "Employee ID" } );
      //VALIDATE_RULE.add( new String[] { "2", "1", "2", "2", "employeeName", "员工姓名", "Employee Name" } );
      VALIDATE_RULE.add( new String[] { "3", "1", "2", "1", "contractId", "劳动合同ID", "Employment ID" } );
      VALIDATE_RULE.add( new String[] { "4", "1", "1", "1", "itemId", "薪资科目", "Salary Item" } );
      VALIDATE_RULE.add( new String[] { "5", "2", "2", "1", "newBase", "金额", "Base" } );
      VALIDATE_RULE.add( new String[] { "6", "3", "2", "1", "newStartDate", "开始时间", "Start Date" } );
      VALIDATE_RULE.add( new String[] { "7", "3", "2", "2", "newEndDate", "结束时间", "End Date" } );
      VALIDATE_RULE.add( new String[] { "8", "1", "1", "1", "remark3", "异动原因", "Movement Category" } );
      VALIDATE_RULE.add( new String[] { "9", "3", "2", "1", "effectiveDate", "生效日期", "Effective Date" } );
   }

   /***
    * 员工批量调薪 - 导入
    * @param localFileString
    * @param request
    * @throws Exception
    */
   public static void importDB( final String localFileString, final HttpServletRequest request ) throws Exception
   {
      final String accountId = BaseAction.getAccountId( request, null );
      final String corpId = BaseAction.getCorpId( request, null );
      final boolean lang_zh = request.getLocale().getLanguage().equalsIgnoreCase( "zh" );
      final String fileName = localFileString.substring( localFileString.lastIndexOf( "/" ) + 1, localFileString.length() );

      // 校验对象<行下标，对象>
      final Map< Integer, EmployeeSalaryAdjustmentTempVO > objectsMap = new HashMap< Integer, EmployeeSalaryAdjustmentTempVO >();
      // 校验规则<列下标，规则数组>
      final Map< Integer, String[] > validateMap = new HashMap< Integer, String[] >();
      // 错误消息
      final List< String > errorMsgList = new ArrayList< String >();
      // 日期正则表达式
      final Pattern dateValidate = Pattern.compile( "^(?:(?!0000)[0-9]{4}[-/\\.](?:(?:0[1-9]|1[0-2])[-/\\.](?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])[-/\\.](?:29|30)|(?:0[13578]|1[02])[-/\\.]31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)[-/\\.]02[-/\\.]29)(\\s+([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?)?$" );
      // 数值表达式
      final Pattern numberValidate = Pattern.compile( "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,8})?$" );
      // 下拉框薪资科目
      final List< MappingVO > salaryItems = KANConstants.getKANAccountConstants( accountId ).getSalaryItems( request.getLocale().getLanguage(), corpId );
      // 下拉框异动原因
      final List< MappingVO > changeResaons = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
      // 初始化EmployeeContractDao
      final EmployeeContractDao employeeContractDao = ( EmployeeContractDao ) ServiceLocator.getService( "employeeContractDao" );
      // EmployeeContractSalaryDao
      final EmployeeContractSalaryDao employeeContractSalaryDao = ( EmployeeContractSalaryDao ) ServiceLocator.getService( "employeeContractSalaryDao" );

      new ReadXlsxHander()
      {
         @Override
         public void optRows( int sheetIndex, int curRow, List< CellData > cellDatas ) throws SQLException
         {
            try
            {
               // 只读取第一个sheet
               if ( sheetIndex != 0 )
                  return;

               // 读取Title，初始化校验
               if ( curRow == 0 )
               {
                  for ( int i = 0; i < cellDatas.size(); i++ )
                  {
                     String[] validateRuleArray = getValidateRuleStringArrayByTitleName( cellDatas.get( i ).getValue() );
                     if ( validateRuleArray != null )
                     {
                        validateMap.put( i, validateRuleArray );
                     }
                  }
               }
               else
               {
                  final EmployeeSalaryAdjustmentTempVO tempVO = new EmployeeSalaryAdjustmentTempVO();
                  tempVO.setAccountId( accountId );
                  tempVO.setCorpId( corpId );

                  for ( int i = 0; i < cellDatas.size(); i++ )
                  {
                     final CellData tempCellData = cellDatas.get( i );
                     final String[] validateRuleArray = validateMap.get( i );
                     if ( tempCellData != null && validateRuleArray != null )
                     {
                        // 验证是否必填
                        if ( validateRuleArray[ 3 ].equals( "1" ) && !StringUtils.isNotEmpty( tempCellData.getValue() ) )
                        {
                           errorMsgList.add( "第" + ( curRow + 1 ) + "行，第" + ( i + 1 ) + "列[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]的值不能为空；" );
                        }

                        if ( KANUtil.filterEmpty( tempCellData.getValue() ) == null )
                        {
                           continue;
                        }

                        boolean isNumber = false;
                        //* 数据类型:1>字符;2>数值;3>日期
                        if ( validateRuleArray[ 1 ].equals( "2" ) )
                        {
                          if ( !numberValidate.matcher( tempCellData.getFormateValue() ).matches() )
                          {
                              errorMsgList.add( "第" + ( curRow + 1 ) + "行，第" + ( i + 1 ) + "列[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]的值不是数值类型；" );
                          }else{
                            isNumber = true;
                          }
                          
                        }
                        else if ( validateRuleArray[ 1 ].equals( "3" ) )
                        {
                           if ( !dateValidate.matcher( tempCellData.getFormateValue() ).matches() )
                           {
                              errorMsgList.add( "第" + ( curRow + 1 ) + "行，第" + ( i + 1 ) + "列[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]的值不是日期类型；" );
                           }
                           else
                           {
                              tempCellData.setValue( tempCellData.getFormateValue() );
                           }
                        }

                        // 如果是下拉框
                        if ( validateRuleArray[ 2 ].equals( "1" ) )
                        {
                           String optionId = null;
                           switch ( validateRuleArray[ 4 ] )
                           {
                              case "itemId":
                                 optionId = getMappingId( salaryItems, tempCellData.getValue() );
                                 break;
                              case "remark3":
                                 optionId = getMappingId( changeResaons, tempCellData.getValue() );
                                 break;
                              default:
                                 optionId = null;
                                 break;
                           }

                           if ( KANUtil.filterEmpty( optionId ) == null )
                           {
                              errorMsgList.add( "第" + ( curRow + 1 ) + "行，第" + ( i + 1 ) + "列[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]的值不是系统有效值；" );
                           }
                           else
                           {
                              KANUtil.setValue( tempVO, validateRuleArray[ 4 ], optionId );
                           }
                        }
                        else
                        {
                           KANUtil.setValue( tempVO, validateRuleArray[ 4 ], isNumber?tempCellData.getFormateValue():tempCellData.getValue() );
                        }
                     }
                  }

                  objectsMap.put( curRow + 2, tempVO );
               }
            }
            catch ( Exception e )
            {
               e.printStackTrace();
            }
         }
      }.process( localFileString );

      // 标记验证是否通过
      boolean validateFlag = true;
      // 导入成功条数
      int successRows = 0;
      // 开始验证……
      UploadFileAction.setStatusMsg( request, "3", "数据验证中..." );

      // 检查数据是否可导入
      if ( objectsMap.size() == 0 )
      {
         UploadFileAction.setStatusMsg( request, "1", "没有数据可以导入！", "2" );
         return;
      }

      // 检查必填列是否缺失
      for ( String[] validateArray : VALIDATE_RULE )
      {
         if ( validateArray[ 3 ].equals( "1" ) && getStringArray( validateMap, validateArray[ 4 ] ) == null )
         {
            UploadFileAction.setStatusMsg( request, "1", "必填列[ " + ( lang_zh ? validateArray[ 5 ] : validateArray[ 6 ] ) + " ]未找到！请下载最新模板；", "2" );
            return;
         }
      }

      // 检查数据是否有效
      for ( Integer intKey : objectsMap.keySet() )
      {
         final EmployeeSalaryAdjustmentTempVO tempVO = objectsMap.get( intKey );

         final EmployeeContractVO tempEmployeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( tempVO.getContractId() );
         if ( tempEmployeeContractVO == null )
         {
            errorMsgList.add( "第" + intKey + "行，系统找不到[ ID: " + tempVO.getContractId() + " ]的劳动合同；" );
         }
         else
         {
            tempVO.setOrderId( tempEmployeeContractVO.getOrderId() );
            tempVO.setEmployeeId( tempEmployeeContractVO.getEmployeeId() );
            tempVO.setStatus( "1" );
            tempVO.setCreateBy( BaseAction.getUserId( request, null ) );
            tempVO.setModifyBy( BaseAction.getUserId( request, null ) );

            ItemVO itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemId( tempVO.getItemId() );
            if ( itemVO != null )
            {
               tempVO.setItemNameZH( itemVO.getNameZH() );
               tempVO.setItemNameEN( itemVO.getNameEN() );
            }

            final Map< String, Object > parameters = new HashMap< String, Object >();
            parameters.put( "contractId", tempVO.getContractId() );
            parameters.put( "itemId", tempVO.getItemId() );
            List< Object > lists = employeeContractSalaryDao.getEmployeeContractSalaryVOsByContractIdAndItemId( parameters );

            EmployeeContractSalaryVO tempContractSalaryVO = null;
            long tempLong = Long.MIN_VALUE;
            if ( lists != null && lists.size() > 0 )
            {
               for ( Object obj : lists )
               {
                  final EmployeeContractSalaryVO objVO = ( EmployeeContractSalaryVO ) obj;
                  if ( KANUtil.filterEmpty( objVO.getStartDate() ) != null && KANUtil.createDate( objVO.getStartDate() ).getTime() > tempLong )
                  {
                     tempContractSalaryVO = objVO;
                     tempLong = KANUtil.createDate( objVO.getStartDate() ).getTime();
                  }
               }
            }

            if ( tempContractSalaryVO == null )
            {
               tempVO.setNeedNewSalarySolution( true );
            }
            else
            {
               tempVO.setEmployeeSalaryId( tempContractSalaryVO.getEmployeeSalaryId() );
               tempVO.setOldBase( tempContractSalaryVO.getBase() );
               tempVO.setOldStartDate( tempContractSalaryVO.getStartDate() );
               tempVO.setOldEndDate( KANUtil.formatDate( KANUtil.getDate( tempVO.getNewStartDate(), 0, 0, -1 ) ) );
            }
         }
      }

      if ( errorMsgList.size() > 0 )
      {
         validateFlag = false;
         final StringBuilder sb = new StringBuilder( "\n" );
         for ( String errorMsg : errorMsgList )
         {
            sb.append( errorMsg + "\n" );
         }
         UploadFileAction.setStatusMsg( request, "1", sb.toString(), "2" );
      }

      // 通过所有验证，insertDB
      if ( validateFlag )
      {
         successRows = insertDB( objectsMap, accountId, corpId, fileName, BaseAction.getPositionId( request, null ), BaseAction.getUserId( request, null ) );
      }

      UploadFileAction.setStatusMsg( request, "4", "共" + ( objectsMap.size() ) + "条，" + successRows + "条导入成功！" );
   }

   /***
    * 通过验证后，insert到数据库临时表
    * @param objects
    * @param accountId
    * @param corpId
    * @param fileName
    * @param onwer
    * @param userId
    * @return
    * @throws KANException
    * @throws InterruptedException
    */
   private static int insertDB( final Map< Integer, EmployeeSalaryAdjustmentTempVO > objectsMap, final String accountId, final String corpId, final String fileName,
         final String onwer, final String userId ) throws KANException, InterruptedException
   {
      // 创建一个批次
      final CommonBatchVO commonBatchVO = new CommonBatchVO();
      commonBatchVO.setAccountId( accountId );
      commonBatchVO.setCorpId( corpId );
      commonBatchVO.setAccessAction( EmployeeSalaryAdjustmentTempAction.ACCESS_ACTION );
      commonBatchVO.setImportExcelName( fileName );
      commonBatchVO.setOwner( onwer );
      commonBatchVO.setStatus( "1" );
      commonBatchVO.setDeleted( "1" );
      commonBatchVO.setCreateBy( userId );
      commonBatchVO.setModifyBy( userId );

      List< EmployeeSalaryAdjustmentTempVO > listTempVO = new ArrayList< EmployeeSalaryAdjustmentTempVO >();
      listTempVO.addAll( objectsMap.values() );

      final EmployeeSalaryAdjustmentBatchService employeeSalaryAdjustmentBatchService = ( EmployeeSalaryAdjustmentBatchService ) ServiceLocator.getService( "employeeSalaryAdjustmentBatchService" );
      return employeeSalaryAdjustmentBatchService.insertEmployeeSalaryAdjustmentBatch( commonBatchVO, listTempVO );
   }

   /***
    * 根据titleName找到常量对应的校验规则
    * @param titleName
    * @return 验证规则[]
    */
   private static String[] getValidateRuleStringArrayByTitleName( final String titleName )
   {
      for ( String[] validateRule : VALIDATE_RULE )
      {
         if ( StringUtils.isNotEmpty( titleName ) && ( validateRule[ 5 ].equals( titleName ) || validateRule[ 6 ].equals( titleName ) ) )
         {
            return validateRule.clone();
         }
      }

      return null;
   }

   /***
    * 根据property找到validateMap对应的校验规则
    * @param validateMap
    * @param property
    * @return 验证规则[]
    */
   private static String[] getStringArray( Map< Integer, String[] > validateMap, final String property )
   {
      if ( validateMap != null && validateMap.size() > 0 )
      {
         for ( int k : validateMap.keySet() )
         {
            if ( validateMap.get( k )[ 4 ].equals( property ) )
            {
               return validateMap.get( k );
            }
         }
      }
      return null;
   }

   /***
    * Excel下拉框根据text找到id
    * @param mappingVOs
    * @param mappingValue
    * @return id
    */
   private static String getMappingId( final List< MappingVO > mappingVOs, final String mappingValue )
   {
      for ( MappingVO mappingVO : mappingVOs )
      {
         if ( mappingVO.getMappingValue() != null && mappingVO.getMappingValue().equals( mappingValue ) )
         {
            return mappingVO.getMappingId();
         }
      }

      return null;
   }

   private static EmployeeContractSalaryVO getEmployeeContractSalaryVOByItemId( final List< Object > employeeContractSalaryVOs, final String itemId )
   {
      final List< EmployeeContractSalaryVO > tempList = new ArrayList< EmployeeContractSalaryVO >();
      if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
      {
         for ( Object obj : employeeContractSalaryVOs )
         {
            final EmployeeContractSalaryVO tempVO = ( EmployeeContractSalaryVO ) obj;
            if ( tempVO.getItemId().equals( itemId ) )
            {
               if ( ( KANUtil.filterEmpty( tempVO.getEndDate() ) == null )
                     || ( KANUtil.filterEmpty( tempVO.getEndDate() ) != null && KANUtil.getDays( KANUtil.getDateAfterMonth( KANUtil.createDate( tempVO.getEndDate() ), 3 ) ) >= KANUtil.getDays( new Date() ) ) )
               {
                  tempList.add( tempVO );
               }
            }
         }
      }

      if ( tempList.size() == 0 )
         return null;
      else
      return tempList.get(0);
  }
}
