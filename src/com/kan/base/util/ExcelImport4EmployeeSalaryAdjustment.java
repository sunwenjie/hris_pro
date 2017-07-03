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
    * ���±�:��ȡexcel�ĸ�ֵ
    * ��������:1>�ַ�;2>��ֵ;3>����
    * �Ƿ�Ϊ������:1/2
    * �Ƿ����:1/2
    * new String[]{"���±�","��������","�Ƿ�Ϊ������","�Ƿ����","��Ա������","ģ���б�������","ģ������Ӣ��"};
    */
   // ��֤����
   public static final List< String[] > VALIDATE_RULE = new ArrayList< String[] >();

   static
   {
      // ��ʼ����֤����
      VALIDATE_RULE.add( new String[] { "1", "1", "2", "2", "employeeId", "Ա��ID", "Employee ID" } );
      //VALIDATE_RULE.add( new String[] { "2", "1", "2", "2", "employeeName", "Ա������", "Employee Name" } );
      VALIDATE_RULE.add( new String[] { "3", "1", "2", "1", "contractId", "�Ͷ���ͬID", "Employment ID" } );
      VALIDATE_RULE.add( new String[] { "4", "1", "1", "1", "itemId", "н�ʿ�Ŀ", "Salary Item" } );
      VALIDATE_RULE.add( new String[] { "5", "2", "2", "1", "newBase", "���", "Base" } );
      VALIDATE_RULE.add( new String[] { "6", "3", "2", "1", "newStartDate", "��ʼʱ��", "Start Date" } );
      VALIDATE_RULE.add( new String[] { "7", "3", "2", "2", "newEndDate", "����ʱ��", "End Date" } );
      VALIDATE_RULE.add( new String[] { "8", "1", "1", "1", "remark3", "�춯ԭ��", "Movement Category" } );
      VALIDATE_RULE.add( new String[] { "9", "3", "2", "1", "effectiveDate", "��Ч����", "Effective Date" } );
   }

   /***
    * Ա��������н - ����
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

      // У�����<���±꣬����>
      final Map< Integer, EmployeeSalaryAdjustmentTempVO > objectsMap = new HashMap< Integer, EmployeeSalaryAdjustmentTempVO >();
      // У�����<���±꣬��������>
      final Map< Integer, String[] > validateMap = new HashMap< Integer, String[] >();
      // ������Ϣ
      final List< String > errorMsgList = new ArrayList< String >();
      // ����������ʽ
      final Pattern dateValidate = Pattern.compile( "^(?:(?!0000)[0-9]{4}[-/\\.](?:(?:0[1-9]|1[0-2])[-/\\.](?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])[-/\\.](?:29|30)|(?:0[13578]|1[02])[-/\\.]31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)[-/\\.]02[-/\\.]29)(\\s+([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?)?$" );
      // ��ֵ���ʽ
      final Pattern numberValidate = Pattern.compile( "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,8})?$" );
      // ������н�ʿ�Ŀ
      final List< MappingVO > salaryItems = KANConstants.getKANAccountConstants( accountId ).getSalaryItems( request.getLocale().getLanguage(), corpId );
      // �������춯ԭ��
      final List< MappingVO > changeResaons = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
      // ��ʼ��EmployeeContractDao
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
               // ֻ��ȡ��һ��sheet
               if ( sheetIndex != 0 )
                  return;

               // ��ȡTitle����ʼ��У��
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
                        // ��֤�Ƿ����
                        if ( validateRuleArray[ 3 ].equals( "1" ) && !StringUtils.isNotEmpty( tempCellData.getValue() ) )
                        {
                           errorMsgList.add( "��" + ( curRow + 1 ) + "�У���" + ( i + 1 ) + "��[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]��ֵ����Ϊ�գ�" );
                        }

                        if ( KANUtil.filterEmpty( tempCellData.getValue() ) == null )
                        {
                           continue;
                        }

                        boolean isNumber = false;
                        //* ��������:1>�ַ�;2>��ֵ;3>����
                        if ( validateRuleArray[ 1 ].equals( "2" ) )
                        {
                          if ( !numberValidate.matcher( tempCellData.getFormateValue() ).matches() )
                          {
                              errorMsgList.add( "��" + ( curRow + 1 ) + "�У���" + ( i + 1 ) + "��[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]��ֵ������ֵ���ͣ�" );
                          }else{
                            isNumber = true;
                          }
                          
                        }
                        else if ( validateRuleArray[ 1 ].equals( "3" ) )
                        {
                           if ( !dateValidate.matcher( tempCellData.getFormateValue() ).matches() )
                           {
                              errorMsgList.add( "��" + ( curRow + 1 ) + "�У���" + ( i + 1 ) + "��[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]��ֵ�����������ͣ�" );
                           }
                           else
                           {
                              tempCellData.setValue( tempCellData.getFormateValue() );
                           }
                        }

                        // �����������
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
                              errorMsgList.add( "��" + ( curRow + 1 ) + "�У���" + ( i + 1 ) + "��[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]��ֵ����ϵͳ��Чֵ��" );
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

      // �����֤�Ƿ�ͨ��
      boolean validateFlag = true;
      // ����ɹ�����
      int successRows = 0;
      // ��ʼ��֤����
      UploadFileAction.setStatusMsg( request, "3", "������֤��..." );

      // ��������Ƿ�ɵ���
      if ( objectsMap.size() == 0 )
      {
         UploadFileAction.setStatusMsg( request, "1", "û�����ݿ��Ե��룡", "2" );
         return;
      }

      // ���������Ƿ�ȱʧ
      for ( String[] validateArray : VALIDATE_RULE )
      {
         if ( validateArray[ 3 ].equals( "1" ) && getStringArray( validateMap, validateArray[ 4 ] ) == null )
         {
            UploadFileAction.setStatusMsg( request, "1", "������[ " + ( lang_zh ? validateArray[ 5 ] : validateArray[ 6 ] ) + " ]δ�ҵ�������������ģ�壻", "2" );
            return;
         }
      }

      // ��������Ƿ���Ч
      for ( Integer intKey : objectsMap.keySet() )
      {
         final EmployeeSalaryAdjustmentTempVO tempVO = objectsMap.get( intKey );

         final EmployeeContractVO tempEmployeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( tempVO.getContractId() );
         if ( tempEmployeeContractVO == null )
         {
            errorMsgList.add( "��" + intKey + "�У�ϵͳ�Ҳ���[ ID: " + tempVO.getContractId() + " ]���Ͷ���ͬ��" );
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

      // ͨ��������֤��insertDB
      if ( validateFlag )
      {
         successRows = insertDB( objectsMap, accountId, corpId, fileName, BaseAction.getPositionId( request, null ), BaseAction.getUserId( request, null ) );
      }

      UploadFileAction.setStatusMsg( request, "4", "��" + ( objectsMap.size() ) + "����" + successRows + "������ɹ���" );
   }

   /***
    * ͨ����֤��insert�����ݿ���ʱ��
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
      // ����һ������
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
    * ����titleName�ҵ�������Ӧ��У�����
    * @param titleName
    * @return ��֤����[]
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
    * ����property�ҵ�validateMap��Ӧ��У�����
    * @param validateMap
    * @param property
    * @return ��֤����[]
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
    * Excel���������text�ҵ�id
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
