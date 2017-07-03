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
      VALIDATE_RULE.add( new String[] { "2", "1", "2", "2", "", "姓名", "Name" } );
      VALIDATE_RULE.add( new String[] { "3", "1", "2", "1", "accomplishments", "工作成果", "Accomplishments" } );
      VALIDATE_RULE.add( new String[] { "4", "1", "2", "1", "areasOfStrengths", "优势长处", "Areas of Strengths" } );
      VALIDATE_RULE.add( new String[] { "5", "1", "2", "1", "areasOfImprovement", "待改善领域", "Areas of Improvement" } );
      VALIDATE_RULE.add( new String[] { "6", "1", "2", "2", "successors", "接班人", "Successor" } );
      VALIDATE_RULE.add( new String[] { "7", "1", "2", "2", "otherComments", "其它的意见（选填）", "Comments (optional)" } );
      VALIDATE_RULE.add( new String[] { "8", "1", "2", "1", "nextYearGoals", "目标设定", "Goals" } );
      VALIDATE_RULE.add( new String[] { "9", "1", "2", "1", "nextYearPlans", "计划设定", "Plans" } );

      //      VALIDATE_RULE.add( new String[] { "10", "1", "2", "1", "accomplishments_bm", "工作成果", "Accomplishments" } );
      //      VALIDATE_RULE.add( new String[] { "11", "1", "2", "1", "areasOfStrengths_bm", "优势长处", "Areas of Strengths" } );
      //      VALIDATE_RULE.add( new String[] { "12", "1", "2", "1", "areasOfImprovement_bm", "待改善领域", "Areas of Improvement" } );
      //      VALIDATE_RULE.add( new String[] { "13", "1", "1", "1", "isPromotion_bm", "是否晋升", "Promotion or not" } );
      //      VALIDATE_RULE.add( new String[] { "14", "1", "2", "2", "promotionReason_bm", "晋升理由", "Justification for promotion" } );
      //      VALIDATE_RULE.add( new String[] { "15", "1", "2", "2", "successors_bm", "接班人", "Successor" } );
      //      VALIDATE_RULE.add( new String[] { "16", "1", "2", "1", "nextYearGoalsAndPlans_bm", "目标设定", "Goals" } );
      //
      //      VALIDATE_RULE.add( new String[] { "17", "1", "2", "1", "accomplishments_pm", "工作成果", "Accomplishments" } );
      //      VALIDATE_RULE.add( new String[] { "18", "1", "2", "1", "areasOfStrengths_pm", "优势长处", "Areas of Strengths" } );
      //      VALIDATE_RULE.add( new String[] { "19", "1", "2", "1", "areasOfImprovement_pm", "待改善领域", "Areas of Improvement" } );
      //      VALIDATE_RULE.add( new String[] { "20", "1", "1", "1", "isPromotion_pm", "是否晋升", "Promotion or not" } );
      //      VALIDATE_RULE.add( new String[] { "21", "1", "2", "2", "promotionReason_pm", "晋升理由", "Justification for promotion" } );
      //      VALIDATE_RULE.add( new String[] { "22", "1", "2", "2", "successors_pm", "接班人", "Successor" } );
      //      VALIDATE_RULE.add( new String[] { "23", "1", "2", "1", "rating_pm", "主管建议", "Manager’s rating" } );
      //      VALIDATE_RULE.add( new String[] { "24", "1", "2", "1", "nextYearGoalsAndPlans_pm", "目标设定", "Goals" } );
      //
      //      VALIDATE_RULE.add( new String[] { "25", "1", "2", "1", "rating_bu", "BU Head’s Rating", "BU Head’s Rating" } );
   }

   /***
    * 导入 - 员工自评
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

      // 校验对象<行下标，对象>
      final Map< Integer, SelfAssessmentVO > objectsMap = new HashMap< Integer, SelfAssessmentVO >();
      // 校验规则<列下标，规则数组>
      final Map< Integer, String[] > validateMap = new HashMap< Integer, String[] >();
      // 错误消息
      final List< String > errorMsgList = new ArrayList< String >();

      for ( int i = 0; i < VALIDATE_RULE.size(); i++ )
      {
         validateMap.put( i, VALIDATE_RULE.get( i ) );
      }

      // 下拉框异动原因
      final List< MappingVO > isPromotions = KANUtil.getMappings( request.getLocale(), "emp.self.assessment.isPromotion_pms" );

      // 初始化EmployeeDao
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
               // 只读取第一个sheet
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
                     // 验证是否必填
                     if ( validateRuleArray[ 3 ].equals( "1" ) && !StringUtils.isNotEmpty( tempCellData.getValue() ) )
                     {
                        errorMsgList.add( "第" + ( curRow + 1 ) + "行，第" + ( i + 1 ) + "列[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]的值不能为空；" );
                     }

                     if ( KANUtil.filterEmpty( tempCellData.getValue() ) == null )
                     {
                        continue;
                     }

                     // 如果是下拉框
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
                           errorMsgList.add( "第" + ( curRow + 1 ) + "行，第" + ( i + 1 ) + "列[ " + ( lang_zh ? validateRuleArray[ 5 ] : validateRuleArray[ 6 ] ) + " ]的值不是系统有效值；" );
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
         final SelfAssessmentVO tempVO = objectsMap.get( intKey );

         final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( tempVO.getEmployeeId() );

         if ( employeeVO == null )
         {
            errorMsgList.add( "第" + intKey + "行，ID:[ " + tempVO.getEmployeeId() + " ]的员工不存在；" );
         }
         else
         {

            final Map< String, Object > paramters = new HashMap< String, Object >();
            paramters.put( "year", year );
            paramters.put( "employeeId", employeeVO.getEmployeeId() );

            List< Object > list = selfAssessmentDao.getSelfAssessmentVOsByMapParameter( paramters );
            if ( list != null && list.size() > 0 )
            {
               errorMsgList.add( "第" + intKey + "行，ID:[ " + tempVO.getEmployeeId() + " ]的员工已经参与了[ " + year + " ]年的员工自评；" );
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

      // 通过所有验证，insertDB
      if ( validateFlag )
      {
         List< SelfAssessmentVO > listTempVO = new ArrayList< SelfAssessmentVO >();
         listTempVO.addAll( objectsMap.values() );
         SelfAssessmentService selfAssessmentService = ( SelfAssessmentService ) ServiceLocator.getService( "selfAssessmentService" );
         successRows = selfAssessmentService.insertBatchSelfAssessment( listTempVO );
      }

      UploadFileAction.setStatusMsg( request, "4", "共" + ( objectsMap.size() ) + "条，" + successRows + "条导入成功！" );
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
         if ( ( mappingVO.getMappingValue() != null && mappingVO.getMappingValue().equals( mappingValue ) )
               || ( mappingVO.getMappingTemp() != null && mappingVO.getMappingTemp().equals( mappingValue ) ) )
         {
            return mappingVO.getMappingId();
         }
      }

      return null;
   }

}
