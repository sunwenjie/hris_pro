package com.kan.base.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ServiceLocator;
import com.kan.base.dao.inf.security.PositionStaffRelationDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.poi.ReadXlsxHander;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.UploadFileAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeTempVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeBatchService;
import com.kan.hro.web.actions.biz.employee.EmployeePositionChangeBatchAction;

public class ExcelImport4PositionChange
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
      VALIDATE_RULE.add( new String[] { "1", "1", "2", "1", "employeeId", "员工ID", "Employee ID" } );
      //VALIDATE_RULE.add( new String[] { "2", "1", "2", "2", "employeeName", "员工姓名", "Employee Name" } );
      VALIDATE_RULE.add( new String[] { "3", "1", "1", "1", "newParentBranchId", "BU/Function", "BU/Function" } );
      VALIDATE_RULE.add( new String[] { "4", "1", "1", "1", "newBranchId", "部门", "Department" } );
      VALIDATE_RULE.add( new String[] { "5", "1", "1", "1", "newPositionGradeId", "职级", "Job Grade" } );
      VALIDATE_RULE.add( new String[] { "6", "1", "1", "1", "newParentPositionId", "直线汇报线", "Direct Report Manager" } );
      VALIDATE_RULE.add( new String[] { "7", "1", "2", "1", "newPositionNameZH", "职位名称（中文）", "Working Title (Chinese)" } );
      VALIDATE_RULE.add( new String[] { "8", "1", "2", "1", "newPositionNameEN", "职位名称（英文）", "Working Title (English)" } );
      VALIDATE_RULE.add( new String[] { "9", "1", "1", "2", "isChildChange", "下属联动", "Shift of subordinates reporting line" } );
      VALIDATE_RULE.add( new String[] { "10", "3", "2", "2", "effectiveDate", "生效日期", "Effective Date" } );
      VALIDATE_RULE.add( new String[] { "11", "1", "1", "1", "remark3", "异动原因", "Movement Category" } );
      VALIDATE_RULE.add( new String[] { "12", "1", "2", "2", "remark2", "业务汇报线", "Direct Report Manager (Biz Leader)" } );
      VALIDATE_RULE.add( new String[] { "13", "1", "1", "2", "remark1", "Job Role", "Job Role" } );
   }

   /***
    * 员工批量异动 - 导入
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
      final KANAccountConstants constants = KANConstants.getKANAccountConstants( accountId );

      // 校验对象<行下标，对象>
      final Map< Integer, EmployeePositionChangeTempVO > objectsMap = new HashMap< Integer, EmployeePositionChangeTempVO >();
      // 校验规则<列下标，规则数组>
      final Map< Integer, String[] > validateMap = new HashMap< Integer, String[] >();
      // 错误消息
      final List< String > errorMsgList = new ArrayList< String >();
      // 日期正则表达式
      final Pattern dateValidate = Pattern.compile( "^(?:(?!0000)[0-9]{4}[-/\\.](?:(?:0[1-9]|1[0-2])[-/\\.](?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])[-/\\.](?:29|30)|(?:0[13578]|1[02])[-/\\.]31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)[-/\\.]02[-/\\.]29)(\\s+([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?)?$" );
      // 数值表达式
      final Pattern numberValidate = Pattern.compile( "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,4})?$" );
      // 下拉框异动原因
      final List< MappingVO > changeResaons = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
      // 下拉框BU/Function | 部门
      final List< MappingVO > depts = constants.getBranchs( request.getLocale().getLanguage(), corpId );
      // 下拉框职级
      final List< MappingVO > positionGrades = constants.getPositionGrades( request.getLocale().getLanguage(), corpId );
      // 下拉框直线汇报线
      final List< MappingVO > lineManagers = constants.getLineManagerNames( request.getLocale().getLanguage() );
      // 下拉框下属联动
      final List< MappingVO > flags = KANUtil.getMappings( request.getLocale(), "flag" );
      // Job Role
      final List< MappingVO > jobRoles = KANUtil.getColumnOptionValues( request.getLocale(), constants.getColumnVOByColumnId( "13365" ), accountId, corpId );

      // 初始化EmployeeDao
      final EmployeeDao employeeDao = ( EmployeeDao ) ServiceLocator.getService( "employeeDao" );
      // 初始化StaffDao
      final StaffDao staffDao = ( StaffDao ) ServiceLocator.getService( "staffDao" );
      // 初始化StaffDao
      final PositionStaffRelationDao positionStaffRelationDao = ( PositionStaffRelationDao ) ServiceLocator.getService( "positionStaffRelationDao" );

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
                  final EmployeePositionChangeTempVO tempVO = new EmployeePositionChangeTempVO();
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

                        //* 数据类型:1>字符;2>数值;3>日期
                        if ( validateRuleArray[ 1 ].equals( "2" ) )
                        {
                           if ( !numberValidate.matcher( tempCellData.getValue() ).matches() )
                           {
                              errorMsgList.add( "第" + ( curRow + 1 ) + "行，第" + ( i + 1 ) + "列[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]的值不是数值类型；" );
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
                              case "newParentBranchId":
                                 optionId = getMappingId( depts, tempCellData.getValue() );
                                 break;
                              case "newBranchId":
                                 optionId = tempCellData.getValue();
                                 break;
                              case "newPositionGradeId":
                                 optionId = getMappingId( positionGrades, tempCellData.getValue() );
                                 break;
                              case "newParentPositionId":
                                 optionId = getMappingId( lineManagers, tempCellData.getValue() );
                                 break;
                              case "remark3":
                                 optionId = getMappingId( changeResaons, tempCellData.getValue() );
                                 break;
                              case "remark1":
                                 optionId = getMappingId( jobRoles, tempCellData.getValue() );
                                 break;
                              case "isChildChange":
                                 optionId = getMappingId( flags, tempCellData.getValue() );
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
                           KANUtil.setValue( tempVO, validateRuleArray[ 4 ], tempCellData.getValue() );
                        }
                     }
                  }

                  boolean newBranchIdError = false;
                  // 单独处理部门
                  if ( KANUtil.filterEmpty( tempVO.getNewBranchId() ) != null )
                  {
                     List< MappingVO > branchs = constants.getBranchsByParentBranchId( tempVO.getNewParentBranchId() );
                     if ( branchs != null && branchs.size() > 0 )
                     {
                        String newBranchId = getMappingId( branchs, tempVO.getNewBranchId().replaceAll( "├─", "" ).replaceAll( "&nbsp;│", "" ).replaceAll( "├─", "" ).replaceAll( "──", "" ).replaceAll( "└─", "" ).replaceAll( "├─", "" ) );
                        if ( KANUtil.filterEmpty( newBranchId ) != null )
                        {
                           tempVO.setNewBranchId( newBranchId );
                        }
                        else
                        {
                           newBranchIdError = true;
                        }
                     }
                     else
                     {
                        newBranchIdError = true;
                     }
                  }
                  else
                  {
                     newBranchIdError = true;
                  }

                  if ( newBranchIdError )
                  {
                     errorMsgList.add( "第" + ( curRow + 1 ) + "行，BU/Function 下没有叫[ " + tempVO.getNewBranchId() + " ]的部门；" );
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
            UploadFileAction.setStatusMsg( request, "1", "必填列*[ " + ( lang_zh ? validateArray[ 5 ] : validateArray[ 6 ] ) + " ]未找到！请下载最新模板；", "2" );
            return;
         }
      }

      // 检查数据是否有效
      for ( Integer intKey : objectsMap.keySet() )
      {
         final EmployeePositionChangeTempVO tempVO = objectsMap.get( intKey );

         final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( tempVO.getEmployeeId() );

         if ( employeeVO == null )
         {
            errorMsgList.add( "第" + intKey + "行，[ ID: " + tempVO.getEmployeeId() + " ]的员工不存在；" );
         }
         else
         {
            tempVO.setEmployeeNo( employeeVO.getEmployeeNo() );
            tempVO.setEmployeeCertificateNumber( employeeVO.getCertificateNumber() );
            tempVO.setEmployeeNameZH( employeeVO.getNameZH() );
            tempVO.setEmployeeNameEN( employeeVO.getNameEN() );
            tempVO.setStatus( "1" );
            tempVO.setCreateBy( BaseAction.getUserId( request, null ) );
            tempVO.setModifyBy( BaseAction.getUserId( request, null ) );

            StaffVO staffVO = staffDao.getStaffVOByEmployeeId( tempVO.getEmployeeId() );

            if ( staffVO == null )
            {
               errorMsgList.add( "第" + intKey + "行，[ ID: " + tempVO.getEmployeeId() + " ]的员工没有有效的职位；" );
            }
            else
            {
               tempVO.setStaffId( staffVO.getStaffId() );
               PositionVO mainPositionVO = constants.getMainPositionVOByStaffId( staffVO.getStaffId() );
               if ( mainPositionVO == null )
               {
                  errorMsgList.add( "第" + intKey + "行，[ ID: " + tempVO.getEmployeeId() + " ]的员工没有有效的职位；" );
               }
               else
               {
                  tempVO.setLocationId( mainPositionVO.getLocationId() );
                  tempVO.setOldPositionId( mainPositionVO.getPositionId() );
                  tempVO.setOldBranchId( mainPositionVO.getBranchId() );
                  PositionStaffRelationVO tempPositionStaffRelationVO = new PositionStaffRelationVO();
                  tempPositionStaffRelationVO.setPositionId( mainPositionVO.getPositionId() );
                  tempPositionStaffRelationVO.setStaffId( staffVO.getStaffId() );
                  PositionStaffRelationVO positionStaffRelationVO = positionStaffRelationDao.getPositionStaffRelationVOByStaffAndPositionId( tempPositionStaffRelationVO );
                  if ( tempPositionStaffRelationVO != null )
                  {
                     tempVO.setOldStaffPositionRelationId( positionStaffRelationVO.getRelationId() );
                  }
               }
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
   private static int insertDB( final Map< Integer, EmployeePositionChangeTempVO > objectsMap, final String accountId, final String corpId, final String fileName,
         final String onwer, final String userId ) throws KANException, InterruptedException
   {
      // 创建一个批次
      final CommonBatchVO commonBatchVO = new CommonBatchVO();
      commonBatchVO.setAccountId( accountId );
      commonBatchVO.setCorpId( corpId );
      commonBatchVO.setAccessAction( EmployeePositionChangeBatchAction.ACCESS_ACTION );
      commonBatchVO.setImportExcelName( fileName );
      commonBatchVO.setOwner( onwer );
      commonBatchVO.setStatus( "1" );
      commonBatchVO.setDeleted( "1" );
      commonBatchVO.setCreateBy( userId );
      commonBatchVO.setModifyBy( userId );

      List< EmployeePositionChangeTempVO > listTempVO = new ArrayList< EmployeePositionChangeTempVO >();
      listTempVO.addAll( objectsMap.values() );

      final EmployeePositionChangeBatchService employeePositionChangeBatchService = ( EmployeePositionChangeBatchService ) ServiceLocator.getService( "employeePositionChangeBatchService" );
      return employeePositionChangeBatchService.insertEmployeePositionChangeBatch( commonBatchVO, listTempVO );
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
    * Excel下拉框根据text找到id
    * @param mappingVOs
    * @param mappingValue
    * @return id
    */
   private static String getMappingId( final List< MappingVO > mappingVOs, final String mappingValue )
   {
      for ( MappingVO mappingVO : mappingVOs )
      {
         if ( ( mappingVO.getMappingValue() != null && mappingVO.getMappingValue().equals( mappingValue ) )
               || ( mappingVO.getMappingTemp() != null && mappingVO.getMappingTemp().equals( mappingValue ) ) )
         {
            return mappingVO.getMappingId();
         }
      }

      return null;
   }
   /***
    * 检查异动类型
    * @param employeePositionChangeTempVO
    * @return -1:没有有效职位; 0:快速异动; 1:普通异动
    */
   //   private static int checkPositionChangeType( final EmployeePositionChangeTempVO employeePositionChangeTempVO )
   //   {
   //      final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( employeePositionChangeTempVO.getAccountId() ).getStaffDTOsByPositionId( employeePositionChangeTempVO.getOldPositionId() );
   //
   //      // 如果职位只有一个人，使用新异动
   //      if ( staffDTOs != null && staffDTOs.size() == 1 )
   //      {
   //         employeePositionChangeTempVO.setNewPositionId( employeePositionChangeTempVO.getOldPositionId() );
   //         employeePositionChangeTempVO.setSubmitFlag( 3 );//表示快速异动
   //         setPositionChangeOtherInfo( employeePositionChangeTempVO );
   //         return 0;
   //      }
   //
   //      // 如果职位存在多个人，新增一个职位
   //      //final PositionVO newPositionVO
   //
   //      final PositionDTO positionDTO = KANConstants.getKANAccountConstants( employeePositionChangeTempVO.getAccountId() ).getPositionDTOByPositionId( employeePositionChangeTempVO.getNewParentPositionId() );
   //      if ( positionDTO != null && positionDTO.getPositionDTOs().size() > 0 )
   //      {
   //         for ( PositionDTO subPositionDTO : positionDTO.getPositionDTOs() )
   //         {
   //            if ( subPositionDTO.getPositionVO() == null )
   //               continue;
   //
   //            if ( subPositionDTO.getPositionVO().getBranchId().equals( employeePositionChangeTempVO.getNewBranchId() )
   //                  && subPositionDTO.getPositionVO().getPositionGradeId().equals( employeePositionChangeTempVO.getNewPositionGradeId() ) )
   //            {
   //               final BranchVO branchVO = KANConstants.getKANAccountConstants( employeePositionChangeTempVO.getAccountId() ).getBranchVOByBranchId( employeePositionChangeTempVO.getNewBranchId() );
   //               if ( branchVO != null && branchVO.getParentBranchId().equals( employeePositionChangeTempVO.getNewParentBranchId() ) )
   //               {
   //                  employeePositionChangeTempVO.setNewPositionId( subPositionDTO.getPositionVO().getPositionId() );
   //                  employeePositionChangeTempVO.setSubmitFlag( 1 );//非快速异动，需要从A职位异动B职位
   //                  setPositionChangeOtherInfo( employeePositionChangeTempVO );
   //                  return 1;
   //               }
   //            }
   //         }
   //      }
   //
   //      return -1;
   //   }

   // 
   //   private static void setPositionChangeOtherInfo( final EmployeePositionChangeTempVO employeePositionChangeTempVO )
   //   {
   //      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( employeePositionChangeTempVO.getAccountId() );
   //
   //      /*** old ***/
   //      final PositionVO oldPosiitonVO = accountConstants.getPositionVOByPositionId( employeePositionChangeTempVO.getOldPositionId() );
   //      if ( oldPosiitonVO != null )
   //      {
   //         //职位
   //         employeePositionChangeTempVO.setOldPositionNameZH( oldPosiitonVO.getTitleZH() );
   //         employeePositionChangeTempVO.setOldPositionNameEN( oldPosiitonVO.getTitleEN() );
   //
   //         //职级
   //         final PositionGradeVO oldParentPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( oldPosiitonVO.getPositionGradeId() );
   //         if ( oldParentPositionGradeVO != null )
   //         {
   //            employeePositionChangeTempVO.setOldPositionGradeId( oldParentPositionGradeVO.getPositionGradeId() );
   //            employeePositionChangeTempVO.setOldPositionGradeNameZH( oldParentPositionGradeVO.getGradeNameZH() );
   //            employeePositionChangeTempVO.setOldPositionGradeNameEN( oldParentPositionGradeVO.getGradeNameEN() );
   //         }
   //
   //         //上级领导
   //         final PositionVO oldParentPositionVO = accountConstants.getPositionVOByPositionId( oldPosiitonVO.getParentPositionId() );
   //         if ( oldParentPositionVO != null )
   //         {
   //            employeePositionChangeTempVO.setOldParentPositionId( oldParentPositionVO.getPositionId() );
   //            employeePositionChangeTempVO.setOldParentPositionNameZH( oldParentPositionVO.getTitleZH() );
   //            employeePositionChangeTempVO.setOldParentPositionNameEN( oldParentPositionVO.getTitleEN() );
   //
   //            employeePositionChangeTempVO.setOldParentPositionOwnersZH( accountConstants.getStaffNamesByPositionId( "zh", oldParentPositionVO.getPositionId() ) );
   //            employeePositionChangeTempVO.setOldParentPositionOwnersEN( accountConstants.getStaffNamesByPositionId( "en", oldParentPositionVO.getPositionId() ) );
   //         }
   //
   //         //部门
   //         final BranchVO oldBranchVO = accountConstants.getBranchVOByBranchId( oldPosiitonVO.getBranchId() );
   //         if ( oldBranchVO != null )
   //         {
   //            employeePositionChangeTempVO.setOldBranchId( oldBranchVO.getBranchId() );
   //            employeePositionChangeTempVO.setOldBranchNameZH( oldBranchVO.getNameZH() );
   //            employeePositionChangeTempVO.setOldBranchNameEN( oldBranchVO.getNameEN() );
   //
   //            //BU
   //            final BranchVO oldParentBranchVO = accountConstants.getBranchVOByBranchId( oldBranchVO.getParentBranchId() );
   //            if ( oldParentBranchVO != null )
   //            {
   //               employeePositionChangeTempVO.setOldParentBranchId( oldParentBranchVO.getBranchId() );
   //               employeePositionChangeTempVO.setOldParentBranchNameZH( oldParentBranchVO.getNameZH() );
   //               employeePositionChangeTempVO.setOldParentBranchNameEN( oldParentBranchVO.getNameEN() );
   //            }
   //         }
   //      }
   //
   //      /*** new ***/
   //      final PositionVO newPosiitonVO = accountConstants.getPositionVOByPositionId( employeePositionChangeTempVO.getNewPositionId() );
   //      if ( newPosiitonVO != null )
   //      {
   //         //职级
   //         final PositionGradeVO newParentPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( employeePositionChangeTempVO.getNewPositionGradeId() );
   //         if ( newParentPositionGradeVO != null )
   //         {
   //            employeePositionChangeTempVO.setNewPositionGradeNameZH( newParentPositionGradeVO.getGradeNameZH() );
   //            employeePositionChangeTempVO.setNewPositionGradeNameEN( newParentPositionGradeVO.getGradeNameEN() );
   //         }
   //
   //         //上级领导
   //         final PositionVO newParentPositionVO = accountConstants.getPositionVOByPositionId( employeePositionChangeTempVO.getNewParentPositionId() );
   //         if ( newParentPositionVO != null )
   //         {
   //            employeePositionChangeTempVO.setNewParentPositionId( newParentPositionVO.getPositionId() );
   //            employeePositionChangeTempVO.setNewParentPositionNameZH( newParentPositionVO.getTitleZH() );
   //            employeePositionChangeTempVO.setNewParentPositionNameEN( newParentPositionVO.getTitleEN() );
   //
   //            employeePositionChangeTempVO.setNewParentPositionOwnersZH( accountConstants.getStaffNamesByPositionId( "zh", newParentPositionVO.getPositionId() ) );
   //            employeePositionChangeTempVO.setNewParentPositionOwnersEN( accountConstants.getStaffNamesByPositionId( "en", newParentPositionVO.getPositionId() ) );
   //         }
   //
   //         //部门
   //         final BranchVO newBranchVO = accountConstants.getBranchVOByBranchId( employeePositionChangeTempVO.getNewBranchId() );
   //         if ( newBranchVO != null )
   //         {
   //            employeePositionChangeTempVO.setNewBranchNameZH( newBranchVO.getNameZH() );
   //            employeePositionChangeTempVO.setNewBranchNameEN( newBranchVO.getNameEN() );
   //
   //            //BU
   //            final BranchVO newParentBranchVO = accountConstants.getBranchVOByBranchId( employeePositionChangeTempVO.getNewParentBranchId() );
   //            if ( newParentBranchVO != null )
   //            {
   //               employeePositionChangeTempVO.setNewParentBranchNameZH( newParentBranchVO.getNameZH() );
   //               employeePositionChangeTempVO.setNewParentBranchNameEN( newParentBranchVO.getNameEN() );
   //            }
   //         }
   //      }
   //
   //   }
}
