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
      VALIDATE_RULE.add( new String[] { "1", "1", "2", "1", "employeeId", "Ա��ID", "Employee ID" } );
      //VALIDATE_RULE.add( new String[] { "2", "1", "2", "2", "employeeName", "Ա������", "Employee Name" } );
      VALIDATE_RULE.add( new String[] { "3", "1", "1", "1", "newParentBranchId", "BU/Function", "BU/Function" } );
      VALIDATE_RULE.add( new String[] { "4", "1", "1", "1", "newBranchId", "����", "Department" } );
      VALIDATE_RULE.add( new String[] { "5", "1", "1", "1", "newPositionGradeId", "ְ��", "Job Grade" } );
      VALIDATE_RULE.add( new String[] { "6", "1", "1", "1", "newParentPositionId", "ֱ�߻㱨��", "Direct Report Manager" } );
      VALIDATE_RULE.add( new String[] { "7", "1", "2", "1", "newPositionNameZH", "ְλ���ƣ����ģ�", "Working Title (Chinese)" } );
      VALIDATE_RULE.add( new String[] { "8", "1", "2", "1", "newPositionNameEN", "ְλ���ƣ�Ӣ�ģ�", "Working Title (English)" } );
      VALIDATE_RULE.add( new String[] { "9", "1", "1", "2", "isChildChange", "��������", "Shift of subordinates reporting line" } );
      VALIDATE_RULE.add( new String[] { "10", "3", "2", "2", "effectiveDate", "��Ч����", "Effective Date" } );
      VALIDATE_RULE.add( new String[] { "11", "1", "1", "1", "remark3", "�춯ԭ��", "Movement Category" } );
      VALIDATE_RULE.add( new String[] { "12", "1", "2", "2", "remark2", "ҵ��㱨��", "Direct Report Manager (Biz Leader)" } );
      VALIDATE_RULE.add( new String[] { "13", "1", "1", "2", "remark1", "Job Role", "Job Role" } );
   }

   /***
    * Ա�������춯 - ����
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

      // У�����<���±꣬����>
      final Map< Integer, EmployeePositionChangeTempVO > objectsMap = new HashMap< Integer, EmployeePositionChangeTempVO >();
      // У�����<���±꣬��������>
      final Map< Integer, String[] > validateMap = new HashMap< Integer, String[] >();
      // ������Ϣ
      final List< String > errorMsgList = new ArrayList< String >();
      // ����������ʽ
      final Pattern dateValidate = Pattern.compile( "^(?:(?!0000)[0-9]{4}[-/\\.](?:(?:0[1-9]|1[0-2])[-/\\.](?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])[-/\\.](?:29|30)|(?:0[13578]|1[02])[-/\\.]31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)[-/\\.]02[-/\\.]29)(\\s+([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?)?$" );
      // ��ֵ���ʽ
      final Pattern numberValidate = Pattern.compile( "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,4})?$" );
      // �������춯ԭ��
      final List< MappingVO > changeResaons = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
      // ������BU/Function | ����
      final List< MappingVO > depts = constants.getBranchs( request.getLocale().getLanguage(), corpId );
      // ������ְ��
      final List< MappingVO > positionGrades = constants.getPositionGrades( request.getLocale().getLanguage(), corpId );
      // ������ֱ�߻㱨��
      final List< MappingVO > lineManagers = constants.getLineManagerNames( request.getLocale().getLanguage() );
      // ��������������
      final List< MappingVO > flags = KANUtil.getMappings( request.getLocale(), "flag" );
      // Job Role
      final List< MappingVO > jobRoles = KANUtil.getColumnOptionValues( request.getLocale(), constants.getColumnVOByColumnId( "13365" ), accountId, corpId );

      // ��ʼ��EmployeeDao
      final EmployeeDao employeeDao = ( EmployeeDao ) ServiceLocator.getService( "employeeDao" );
      // ��ʼ��StaffDao
      final StaffDao staffDao = ( StaffDao ) ServiceLocator.getService( "staffDao" );
      // ��ʼ��StaffDao
      final PositionStaffRelationDao positionStaffRelationDao = ( PositionStaffRelationDao ) ServiceLocator.getService( "positionStaffRelationDao" );

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
                  final EmployeePositionChangeTempVO tempVO = new EmployeePositionChangeTempVO();
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

                        //* ��������:1>�ַ�;2>��ֵ;3>����
                        if ( validateRuleArray[ 1 ].equals( "2" ) )
                        {
                           if ( !numberValidate.matcher( tempCellData.getValue() ).matches() )
                           {
                              errorMsgList.add( "��" + ( curRow + 1 ) + "�У���" + ( i + 1 ) + "��[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]��ֵ������ֵ���ͣ�" );
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
                              errorMsgList.add( "��" + ( curRow + 1 ) + "�У���" + ( i + 1 ) + "��[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]��ֵ����ϵͳ��Чֵ��" );
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
                  // ����������
                  if ( KANUtil.filterEmpty( tempVO.getNewBranchId() ) != null )
                  {
                     List< MappingVO > branchs = constants.getBranchsByParentBranchId( tempVO.getNewParentBranchId() );
                     if ( branchs != null && branchs.size() > 0 )
                     {
                        String newBranchId = getMappingId( branchs, tempVO.getNewBranchId().replaceAll( "����", "" ).replaceAll( "&nbsp;��", "" ).replaceAll( "����", "" ).replaceAll( "����", "" ).replaceAll( "����", "" ).replaceAll( "����", "" ) );
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
                     errorMsgList.add( "��" + ( curRow + 1 ) + "�У�BU/Function ��û�н�[ " + tempVO.getNewBranchId() + " ]�Ĳ��ţ�" );
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
            UploadFileAction.setStatusMsg( request, "1", "������*[ " + ( lang_zh ? validateArray[ 5 ] : validateArray[ 6 ] ) + " ]δ�ҵ�������������ģ�壻", "2" );
            return;
         }
      }

      // ��������Ƿ���Ч
      for ( Integer intKey : objectsMap.keySet() )
      {
         final EmployeePositionChangeTempVO tempVO = objectsMap.get( intKey );

         final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( tempVO.getEmployeeId() );

         if ( employeeVO == null )
         {
            errorMsgList.add( "��" + intKey + "�У�[ ID: " + tempVO.getEmployeeId() + " ]��Ա�������ڣ�" );
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
               errorMsgList.add( "��" + intKey + "�У�[ ID: " + tempVO.getEmployeeId() + " ]��Ա��û����Ч��ְλ��" );
            }
            else
            {
               tempVO.setStaffId( staffVO.getStaffId() );
               PositionVO mainPositionVO = constants.getMainPositionVOByStaffId( staffVO.getStaffId() );
               if ( mainPositionVO == null )
               {
                  errorMsgList.add( "��" + intKey + "�У�[ ID: " + tempVO.getEmployeeId() + " ]��Ա��û����Ч��ְλ��" );
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
   private static int insertDB( final Map< Integer, EmployeePositionChangeTempVO > objectsMap, final String accountId, final String corpId, final String fileName,
         final String onwer, final String userId ) throws KANException, InterruptedException
   {
      // ����һ������
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
    * Excel���������text�ҵ�id
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
    * ����춯����
    * @param employeePositionChangeTempVO
    * @return -1:û����Чְλ; 0:�����춯; 1:��ͨ�춯
    */
   //   private static int checkPositionChangeType( final EmployeePositionChangeTempVO employeePositionChangeTempVO )
   //   {
   //      final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( employeePositionChangeTempVO.getAccountId() ).getStaffDTOsByPositionId( employeePositionChangeTempVO.getOldPositionId() );
   //
   //      // ���ְλֻ��һ���ˣ�ʹ�����춯
   //      if ( staffDTOs != null && staffDTOs.size() == 1 )
   //      {
   //         employeePositionChangeTempVO.setNewPositionId( employeePositionChangeTempVO.getOldPositionId() );
   //         employeePositionChangeTempVO.setSubmitFlag( 3 );//��ʾ�����춯
   //         setPositionChangeOtherInfo( employeePositionChangeTempVO );
   //         return 0;
   //      }
   //
   //      // ���ְλ���ڶ���ˣ�����һ��ְλ
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
   //                  employeePositionChangeTempVO.setSubmitFlag( 1 );//�ǿ����춯����Ҫ��Aְλ�춯Bְλ
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
   //         //ְλ
   //         employeePositionChangeTempVO.setOldPositionNameZH( oldPosiitonVO.getTitleZH() );
   //         employeePositionChangeTempVO.setOldPositionNameEN( oldPosiitonVO.getTitleEN() );
   //
   //         //ְ��
   //         final PositionGradeVO oldParentPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( oldPosiitonVO.getPositionGradeId() );
   //         if ( oldParentPositionGradeVO != null )
   //         {
   //            employeePositionChangeTempVO.setOldPositionGradeId( oldParentPositionGradeVO.getPositionGradeId() );
   //            employeePositionChangeTempVO.setOldPositionGradeNameZH( oldParentPositionGradeVO.getGradeNameZH() );
   //            employeePositionChangeTempVO.setOldPositionGradeNameEN( oldParentPositionGradeVO.getGradeNameEN() );
   //         }
   //
   //         //�ϼ��쵼
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
   //         //����
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
   //         //ְ��
   //         final PositionGradeVO newParentPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( employeePositionChangeTempVO.getNewPositionGradeId() );
   //         if ( newParentPositionGradeVO != null )
   //         {
   //            employeePositionChangeTempVO.setNewPositionGradeNameZH( newParentPositionGradeVO.getGradeNameZH() );
   //            employeePositionChangeTempVO.setNewPositionGradeNameEN( newParentPositionGradeVO.getGradeNameEN() );
   //         }
   //
   //         //�ϼ��쵼
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
   //         //����
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
