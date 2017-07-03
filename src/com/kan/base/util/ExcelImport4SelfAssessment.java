package com.kan.base.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ServiceLocator;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.poi.ReadXlsxHander;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.UploadFileAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.performance.SelfAssessmentDao;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.performance.SelfAssessmentVO;
import com.kan.hro.service.inf.biz.performance.SelfAssessmentService;

public class ExcelImport4SelfAssessment
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
      VALIDATE_RULE.add( new String[] { "2", "1", "2", "2", "", "����", "Name" } );
      VALIDATE_RULE.add( new String[] { "3", "1", "2", "1", "accomplishments", "�����ɹ�", "Accomplishments" } );
      VALIDATE_RULE.add( new String[] { "4", "1", "2", "1", "areasOfStrengths", "���Ƴ���", "Areas of Strengths" } );
      VALIDATE_RULE.add( new String[] { "5", "1", "2", "1", "areasOfImprovement", "����������", "Areas of Improvement" } );
      VALIDATE_RULE.add( new String[] { "6", "1", "2", "2", "successors", "�Ӱ���", "Successor" } );
      VALIDATE_RULE.add( new String[] { "7", "1", "2", "2", "otherComments", "�����������ѡ�", "Comments (optional)" } );
      VALIDATE_RULE.add( new String[] { "8", "1", "2", "1", "nextYearGoals", "Ŀ���趨", "Goals" } );
      VALIDATE_RULE.add( new String[] { "9", "1", "2", "1", "nextYearPlans", "�ƻ��趨", "Plans" } );

      //      VALIDATE_RULE.add( new String[] { "10", "1", "2", "1", "accomplishments_bm", "�����ɹ�", "Accomplishments" } );
      //      VALIDATE_RULE.add( new String[] { "11", "1", "2", "1", "areasOfStrengths_bm", "���Ƴ���", "Areas of Strengths" } );
      //      VALIDATE_RULE.add( new String[] { "12", "1", "2", "1", "areasOfImprovement_bm", "����������", "Areas of Improvement" } );
      //      VALIDATE_RULE.add( new String[] { "13", "1", "1", "1", "isPromotion_bm", "�Ƿ����", "Promotion or not" } );
      //      VALIDATE_RULE.add( new String[] { "14", "1", "2", "2", "promotionReason_bm", "��������", "Justification for promotion" } );
      //      VALIDATE_RULE.add( new String[] { "15", "1", "2", "2", "successors_bm", "�Ӱ���", "Successor" } );
      //      VALIDATE_RULE.add( new String[] { "16", "1", "2", "1", "nextYearGoalsAndPlans_bm", "Ŀ���趨", "Goals" } );
      //
      //      VALIDATE_RULE.add( new String[] { "17", "1", "2", "1", "accomplishments_pm", "�����ɹ�", "Accomplishments" } );
      //      VALIDATE_RULE.add( new String[] { "18", "1", "2", "1", "areasOfStrengths_pm", "���Ƴ���", "Areas of Strengths" } );
      //      VALIDATE_RULE.add( new String[] { "19", "1", "2", "1", "areasOfImprovement_pm", "����������", "Areas of Improvement" } );
      //      VALIDATE_RULE.add( new String[] { "20", "1", "1", "1", "isPromotion_pm", "�Ƿ����", "Promotion or not" } );
      //      VALIDATE_RULE.add( new String[] { "21", "1", "2", "2", "promotionReason_pm", "��������", "Justification for promotion" } );
      //      VALIDATE_RULE.add( new String[] { "22", "1", "2", "2", "successors_pm", "�Ӱ���", "Successor" } );
      //      VALIDATE_RULE.add( new String[] { "23", "1", "2", "1", "rating_pm", "���ܽ���", "Manager��s rating" } );
      //      VALIDATE_RULE.add( new String[] { "24", "1", "2", "1", "nextYearGoalsAndPlans_pm", "Ŀ���趨", "Goals" } );
      //
      //      VALIDATE_RULE.add( new String[] { "25", "1", "2", "1", "rating_bu", "BU Head��s Rating", "BU Head��s Rating" } );
   }

   /***
    * ���� - Ա������
    * @param localFileString
    * @param request
    * @throws Exception
    */
   public static void importDB( final String localFileString, final HttpServletRequest request ) throws Exception
   {
      final String accountId = BaseAction.getAccountId( request, null );
      final String corpId = BaseAction.getCorpId( request, null );
      final boolean lang_zh = request.getLocale().getLanguage().equalsIgnoreCase( "zh" );
      final KANAccountConstants constants = KANConstants.getKANAccountConstants( accountId );
      final Integer year = Integer.parseInt( KANUtil.formatDate( new Date(), "yyyy" ) );

      // У�����<���±꣬����>
      final Map< Integer, SelfAssessmentVO > objectsMap = new HashMap< Integer, SelfAssessmentVO >();
      // У�����<���±꣬��������>
      final Map< Integer, String[] > validateMap = new HashMap< Integer, String[] >();
      // ������Ϣ
      final List< String > errorMsgList = new ArrayList< String >();

      for ( int i = 0; i < VALIDATE_RULE.size(); i++ )
      {
         validateMap.put( i, VALIDATE_RULE.get( i ) );
      }

      // �������춯ԭ��
      final List< MappingVO > isPromotions = KANUtil.getMappings( request.getLocale(), "emp.self.assessment.isPromotion_pms" );

      // ��ʼ��EmployeeDao
      final EmployeeDao employeeDao = ( EmployeeDao ) ServiceLocator.getService( "employeeDao" );
      final EmployeeContractDao employeeContractDao = ( EmployeeContractDao ) ServiceLocator.getService( "employeeContractDao" );
      final StaffDao staffDao = ( StaffDao ) ServiceLocator.getService( "staffDao" );
      final SelfAssessmentDao selfAssessmentDao = ( SelfAssessmentDao ) ServiceLocator.getService( "selfAssessmentDao" );

      new ReadXlsxHander()
      {
         @Override
         public void optRows( int sheetIndex, int curRow, List< CellData > cellDatas ) throws SQLException
         {
            try
            {
               // ֻ��ȡ��һ��sheet
               if ( sheetIndex != 0 || curRow < 1 )
                  return;

               final SelfAssessmentVO tempVO = new SelfAssessmentVO();
               tempVO.setAccountId( accountId );
               tempVO.setCorpId( corpId );
               tempVO.setCreateBy( BaseAction.getUserId( request, null ) );
               tempVO.setModifyBy( BaseAction.getUserId( request, null ) );
               tempVO.setStatus( "2" );
               tempVO.setDeleted( "1" );
               tempVO.setStatus_bm( "1" );
               tempVO.setYear( year );

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

                     // �����������
                     if ( validateRuleArray[ 2 ].equals( "1" ) )
                     {
                        String optionId = null;
                        switch ( validateRuleArray[ 4 ] )
                        {
                           case "isPromotion_bm":
                              optionId = getMappingId( isPromotions, tempCellData.getValue() );
                              break;
                           case "isPromotion_pm":
                              optionId = getMappingId( isPromotions, tempCellData.getValue() );
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
                        if ( StringUtils.isNotBlank( validateRuleArray[ 4 ] ) )
                        {
                           KANUtil.setValue( tempVO, validateRuleArray[ 4 ], tempCellData.getValue() );
                        }
                     }
                  }
               }

               objectsMap.put( curRow + 1, tempVO );
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
         final SelfAssessmentVO tempVO = objectsMap.get( intKey );

         final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( tempVO.getEmployeeId() );

         if ( employeeVO == null )
         {
            errorMsgList.add( "��" + intKey + "�У�ID:[ " + tempVO.getEmployeeId() + " ]��Ա�������ڣ�" );
         }
         else
         {

            final Map< String, Object > paramters = new HashMap< String, Object >();
            paramters.put( "year", year );
            paramters.put( "employeeId", employeeVO.getEmployeeId() );

            List< Object > list = selfAssessmentDao.getSelfAssessmentVOsByMapParameter( paramters );
            if ( list != null && list.size() > 0 )
            {
               errorMsgList.add( "��" + intKey + "�У�ID:[ " + tempVO.getEmployeeId() + " ]��Ա���Ѿ�������[ " + year + " ]���Ա��������" );
               continue;
            }

            final StaffVO thisStaffVO = staffDao.getStaffVOByEmployeeId( tempVO.getEmployeeId() );

            tempVO.setEmployeeNameZH( employeeVO.getNameZH() );
            tempVO.setEmployeeNameEN( employeeVO.getNameEN() );
            tempVO.setHrOwnerPositionId( employeeVO.getOwner() );

            String bizLeaderName = "";
            List< Object > employeeContractVOs = employeeContractDao.getEmployeeContractVOsByEmployeeId( tempVO.getEmployeeId() );
            if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
            {
               for ( Object o : employeeContractVOs )
               {
                  if ( ( ( EmployeeContractVO ) o ).getEmployStatus().equals( "1" ) )
                  {
                     EmployeeContractVO contractVO = employeeContractDao.getEmployeeContractVOByContractId( ( ( EmployeeContractVO ) o ).getContractId() );
                     if ( contractVO != null && KANUtil.filterEmpty( contractVO.getRemark1() ) != null )
                     {
                        final JSONObject jsonObject = JSONObject.fromObject( contractVO.getRemark1() );
                        if ( jsonObject.get( "yewuhuibaoxianjingli" ) != null )
                        {
                           bizLeaderName = jsonObject.get( "yewuhuibaoxianjingli" ).toString();
                           break;
                        }
                     }
                  }
               }
            }

            if ( KANUtil.filterEmpty( bizLeaderName ) != null )
            {
               EmployeeVO searchEmployee = new EmployeeVO();
               searchEmployee.setAccountId( accountId );
               searchEmployee.setCorpId( corpId );
               searchEmployee.setRemark1( "%\"jiancheng\":\"" + bizLeaderName + "%" );

               List< Object > employeeVOs = employeeDao.getEmployeeVOsByCondition( searchEmployee );
               if ( employeeVOs != null && employeeVOs.size() == 1 )
               {
                  StaffVO bizLeaderStaffVO = staffDao.getStaffVOByEmployeeId( ( ( EmployeeVO ) employeeVOs.get( 0 ) ).getEmployeeId() );
                  if ( bizLeaderStaffVO != null )
                  {
                     PositionVO bizLeaderPositionVO = constants.getMainPositionVOByStaffId( bizLeaderStaffVO.getStaffId() );
                     if ( bizLeaderPositionVO != null )
                     {
                        tempVO.setBizLeaderPositionId( bizLeaderPositionVO.getPositionId() );
                     }
                  }
               }
            }

            PositionVO mainPositionVO = constants.getMainPositionVOByStaffId( thisStaffVO == null ? "" : thisStaffVO.getStaffId() );

            if ( mainPositionVO != null )
            {
               tempVO.setSelfPositionId( mainPositionVO.getPositionId() );
               BranchVO branchVO = constants.getBranchVOByBranchId( mainPositionVO.getBranchId() );

               if ( branchVO != null )
               {
                  tempVO.setParentBranchId( branchVO.getParentBranchId() );

                  if ( "0".equals( branchVO.getParentBranchId() ) )
                  {
                     tempVO.setParentBranchId( branchVO.getBranchId() );
                  }
               }

               String directLeaderNameZH = "";
               String directLeaderNameEN = "";
               final PositionDTO parentPositionDTO = constants.getPositionDTOByPositionId( mainPositionVO.getParentPositionId() );

               if ( parentPositionDTO != null && parentPositionDTO.getPositionStaffRelationVOs() != null && parentPositionDTO.getPositionStaffRelationVOs().size() > 0 )
               {
                  for ( PositionStaffRelationVO relation : parentPositionDTO.getPositionStaffRelationVOs() )
                  {
                     if ( "1".equals( relation.getStaffType() ) )
                     {
                        directLeaderNameZH = constants.getStaffNameByStaffIdAndLanguage( relation.getStaffId(), "zh" );
                        directLeaderNameEN = constants.getStaffNameByStaffIdAndLanguage( relation.getStaffId(), "en" );
                     }
                  }
               }

               tempVO.setParentPositionId( mainPositionVO.getParentPositionId() );
               tempVO.setDirectLeaderNameZH( directLeaderNameZH );
               tempVO.setDirectLeaderNameEN( directLeaderNameEN );

               PositionVO buLeaderPositionVO = constants.getBUHeaderPositionVOByPositionId( mainPositionVO.getPositionId() );
               if ( buLeaderPositionVO != null )
               {
                  if ( buLeaderPositionVO.getPositionId().equals( mainPositionVO.getPositionId() ) )
                  {
                     tempVO.setBuLeaderPositionId( mainPositionVO.getParentPositionId() );
                  }
                  else
                  {
                     tempVO.setBuLeaderPositionId( buLeaderPositionVO.getPositionId() );
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
         List< SelfAssessmentVO > listTempVO = new ArrayList< SelfAssessmentVO >();
         listTempVO.addAll( objectsMap.values() );
         SelfAssessmentService selfAssessmentService = ( SelfAssessmentService ) ServiceLocator.getService( "selfAssessmentService" );
         successRows = selfAssessmentService.insertBatchSelfAssessment( listTempVO );
      }

      UploadFileAction.setStatusMsg( request, "4", "��" + ( objectsMap.size() ) + "����" + successRows + "������ɹ���" );
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
         if ( ( mappingVO.getMappingValue() != null && mappingVO.getMappingValue().equals( mappingValue ) )
               || ( mappingVO.getMappingTemp() != null && mappingVO.getMappingTemp().equals( mappingValue ) ) )
         {
            return mappingVO.getMappingId();
         }
      }

      return null;
   }

}
