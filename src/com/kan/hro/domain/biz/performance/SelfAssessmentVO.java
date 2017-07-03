package com.kan.hro.domain.biz.performance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/***
 * 绩效 - 员工自评
 * 
 * @author Siuvan
 *
 */
public class SelfAssessmentVO extends BaseVO
{

   /**
    * SerialVersionUID
    */
   private static final long serialVersionUID = -3894413197814506937L;

   private String assessmentId;
   // 自评年份
   private int year;
   // BU/Function
   private String parentBranchId;
   // 职位ID
   private String selfPositionId;
   // 直线主管职位ID
   private String parentPositionId;
   // Biz leader职位ID
   private String bizLeaderPositionId;
   // BU leader职位ID
   private String buLeaderPositionId;
   // HR Owner职位ID
   private String hrOwnerPositionId;
   // 员工ID
   private String employeeId;
   // 员工姓名（中文）
   private String employeeNameZH;
   // 员工姓名（英文）
   private String employeeNameEN;
   // 直属主管姓名（中文）
   private String directLeaderNameZH;
   // 直属主管姓名（英文）
   private String directLeaderNameEN;

   // 自评 - 工作成果
   private String accomplishments;
   // 自评 - 优势长处
   private String areasOfStrengths;
   // 自评 - 待改善领域
   private String areasOfImprovement;
   // 自评 - 明年目标
   private String nextYearGoals;
   // 自评 - 明年计划
   private String nextYearPlans;
   // 自评 - 接班人
   private String successors;
   // 自评 - 其他意见
   private String otherComments;

   // 业务汇报人评 - 工作成果 bm = business manager
   private String accomplishments_bm;
   // 业务汇报人评 - 优势长处
   private String areasOfStrengths_bm;
   // 业务汇报人评 - 待改善领域
   private String areasOfImprovement_bm;
   // 业务汇报人评 - 明年目标/计划
   private String nextYearGoalsAndPlans_bm;
   // 业务汇报人评 - 是否晋升{1:是；2:否；3:没有意见}
   private String isPromotion_bm;
   // 主管评 - 晋升原因
   private String promotionReason_bm;
   // 业务汇报人评 - 接班人
   private String successors_bm;

   // 主管评 - 工作成果
   private String accomplishments_pm;
   // 主管评 - 优势长处
   private String areasOfStrengths_pm;
   // 主管评 - 待改善领域
   private String areasOfImprovement_pm;
   // 主管评 - 明年目标/计划
   private String nextYearGoalsAndPlans_pm;
   // 主管评 - 是否晋升{1:是；2:否；3:没有意见}
   private String isPromotion_pm;
   // 主管评 - 晋升原因
   private String promotionReason_pm;
   // 主管评 - 接班人
   private String successors_pm;

   // 主管评 - 工作成果
   private String accomplishments_bu;
   // 主管评 - 优势长处
   private String areasOfStrengths_bu;
   // 主管评 - 待改善领域
   private String areasOfImprovement_bu;
   // 主管评 - 明年目标/计划
   private String nextYearGoalsAndPlans_bu;
   // 主管评 - 是否晋升{1:是；2:否；3:没有意见}
   private String isPromotion_bu;
   // 主管评 - 晋升原因
   private String promotionReason_bu;
   // 主管评 - 接班人
   private String successors_bu;

   // 主管评 - 评分
   private String rating_pm;
   // 部门主管评 - 评分
   private String rating_bu;
   // 最终评分
   private String rating_final;
   // 业务主管的状态
   private String status_bm;
   // BUHead 状态
   private String status_bu;
   
   //people manager
   private String status_pm;

   // 除去直系主管，其他的主管职位ID
   private String pm1PositionId;
   private String pm2PositionId;
   private String pm3PositionId;
   private String pm4PositionId;

   // 除去直系主管，其他的主管内容
   private String pm1Content;
   private String pm2Content;
   private String pm3Content;
   private String pm4Content;

   // 除去直系主管，其他的主管状态
   private String status_pm1;
   private String status_pm2;
   private String status_pm3;
   private String status_pm4;

   @JsonIgnore
   private List< MappingVO > isPromption_pms = new ArrayList< MappingVO >();

   @JsonIgnore
   private List< MappingVO > branchs = new ArrayList< MappingVO >();

   @JsonIgnore
   private List< MappingVO > years = new ArrayList< MappingVO >();

   /***
    * 多个主管情况，接受数组，Json格式存储到数据库{ positionId : content }
    */
   //   @JsonIgnore
   //   private String[] accomplishments_pm_array = new String[] {};
   //   @JsonIgnore
   //   private String[] areasOfStrengths_pm_array = new String[] {};
   //   @JsonIgnore
   //   private String[] areasOfImprovement_pm_array = new String[] {};
   //   @JsonIgnore
   //   private String[] nextYearGoalsAndPlans_pm_array = new String[] {};
   //   @JsonIgnore
   //   private String[] isPromotion_pm_array = new String[] {};
   //   @JsonIgnore
   //   private String[] promotionReason_pm_array = new String[] {};
   //   @JsonIgnore
   //   private String[] successors_pm_array = new String[] {};
   //   @JsonIgnore
   //   private String[] rating_pm_array = new String[] {};

   /***
    * For app
    */
   // 查询直系下属
   private String isDirectSub;

   @Override
   // 0:请选择##1:新建##2:待评估##3:已评估##4:已同步##5:已调整##6:完成
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.setStatuses( KANUtil.getMappings( this.getLocale(), "emp.self.assessment.statuses" ) );
      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.branchs.add( 0, super.getEmptyMappingVO() );
      this.isPromption_pms.addAll( KANUtil.getMappings( this.getLocale(), "emp.self.assessment.isPromotion_pms" ) );
      int currYear = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
      years.add( getEmptyMappingVO() );
      years.addAll( KANUtil.getYears( currYear - 5, currYear ) );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( assessmentId );
   }

   @Override
   public void reset() throws KANException
   {

   }

   @Override
   // 员工修改
   public void update( Object object ) throws KANException
   {
      final SelfAssessmentVO selfAssessmentVO = ( SelfAssessmentVO ) object;
      this.accomplishments = selfAssessmentVO.getAccomplishments();
      this.areasOfStrengths = selfAssessmentVO.getAreasOfStrengths();
      this.areasOfImprovement = selfAssessmentVO.getAreasOfImprovement();
      this.nextYearGoals = selfAssessmentVO.getNextYearGoals();
      this.nextYearPlans = selfAssessmentVO.getNextYearPlans();
      this.successors = selfAssessmentVO.getSuccessors();
      this.otherComments = selfAssessmentVO.getOtherComments();
      super.setStatus( selfAssessmentVO.getStatus() );
   }

   // 业务主管修改
   public void update_bl( Object object ) throws KANException
   {
      final SelfAssessmentVO selfAssessmentVO = ( SelfAssessmentVO ) object;
      this.accomplishments_bm = selfAssessmentVO.getAccomplishments_bm();
      this.areasOfStrengths_bm = selfAssessmentVO.getAreasOfStrengths_bm();
      this.areasOfImprovement_bm = selfAssessmentVO.getAreasOfImprovement_bm();
      this.isPromotion_bm = selfAssessmentVO.getIsPromotion_bm();
      this.promotionReason_bm = selfAssessmentVO.getPromotionReason_bm();
      this.successors_bm = selfAssessmentVO.getSuccessors_bm();
      this.status_bm = selfAssessmentVO.getStatus_bm();
   }

   // 如果业务主管未提交，则临时清空业务主管的评价
   public void tempReset_bl()
   {
      if ( "1".equals( this.getStatus_bm() ) )
      {
         this.accomplishments_bm = "";
         this.areasOfStrengths_bm = "";
         this.areasOfImprovement_bm = "";
         this.isPromotion_bm = "";
         this.promotionReason_bm = "";
         this.successors_bm = "";
      }

      if ( "1".equals( this.getStatus_bu() ) )
      {
         this.accomplishments_bu = "";
         this.areasOfStrengths_bu = "";
         this.areasOfImprovement_bu = "";
         this.isPromotion_bu = "";
         this.promotionReason_bu = "";
         this.successors_bu = "";
         this.rating_bu = "";
      }

      if ( "1".equals( this.getStatus_pm1() ) )
      {
         this.pm1Content = null;
      }
      if ( "1".equals( this.getStatus_pm2() ) )
      {
         this.pm2Content = null;
      }
      if ( "1".equals( this.getStatus_pm3() ) )
      {
         this.pm3Content = null;
      }
      if ( "1".equals( this.getStatus_pm4() ) )
      {
         this.pm4Content = null;
      }
   }

   // 部门主管修改
   public void update_pm( Object object ) throws KANException
   {
      final SelfAssessmentVO selfAssessmentVO = ( SelfAssessmentVO ) object;
      this.accomplishments_pm = selfAssessmentVO.getAccomplishments_pm();
      this.areasOfStrengths_pm = selfAssessmentVO.getAreasOfStrengths_pm();
      this.areasOfImprovement_pm = selfAssessmentVO.getAreasOfImprovement_pm();
      this.isPromotion_pm = selfAssessmentVO.getIsPromotion_pm();
      this.promotionReason_pm = selfAssessmentVO.getPromotionReason_pm();
      this.successors_pm = selfAssessmentVO.getSuccessors_pm();
      this.rating_pm = selfAssessmentVO.getRating_pm();
      this.status_pm = selfAssessmentVO.getStatus_pm();
   }

   // BU Head修改
   public void update_buh( Object object ) throws KANException
   {
      final SelfAssessmentVO selfAssessmentVO = ( SelfAssessmentVO ) object;
      this.accomplishments_bu = selfAssessmentVO.getAccomplishments_bu();
      this.areasOfStrengths_bu = selfAssessmentVO.getAreasOfStrengths_bu();
      this.areasOfImprovement_bu = selfAssessmentVO.getAreasOfImprovement_bu();
      this.isPromotion_bu = selfAssessmentVO.getIsPromotion_bu();
      this.promotionReason_bu = selfAssessmentVO.getPromotionReason_bu();
      this.successors_bu = selfAssessmentVO.getSuccessors_bu();
      this.rating_bu = selfAssessmentVO.getRating_bu();
      this.setStatus_bu( selfAssessmentVO.getStatus_bu() );
   }

   // 非直系主管1修改
   public void update_pm_non_direct_1( HttpServletRequest request ) throws KANException
   {
      JSONObject jo = new JSONObject();
      jo.put( "accomplishments_pm1", request.getParameter( "accomplishments_pm1" ) );
      jo.put( "areasOfStrengths_pm1", request.getParameter( "areasOfStrengths_pm1" ) );
      jo.put( "areasOfImprovement_pm1", request.getParameter( "areasOfImprovement_pm1" ) );
      jo.put( "isPromotion_pm1", request.getParameter( "isPromotion_pm1" ) );
      jo.put( "promotionReason_pm1", request.getParameter( "promotionReason_pm1" ) );
      jo.put( "successors_pm1", request.getParameter( "successors_pm1" ) );
      jo.put( "rating_pm1", request.getParameter( "rating_pm1" ) );
      this.setPm1Content( jo.toString() );
      this.setStatus_pm1( request.getParameter( "status_pm1" ) );
   }

   // 非直系主管2修改
   public void update_pm_non_direct_2( HttpServletRequest request ) throws KANException
   {
      JSONObject jo = new JSONObject();
      jo.put( "accomplishments_pm2", request.getParameter( "accomplishments_pm2" ) );
      jo.put( "areasOfStrengths_pm2", request.getParameter( "areasOfStrengths_pm2" ) );
      jo.put( "areasOfImprovement_pm2", request.getParameter( "areasOfImprovement_pm2" ) );
      jo.put( "isPromotion_pm2", request.getParameter( "isPromotion_pm2" ) );
      jo.put( "promotionReason_pm2", request.getParameter( "promotionReason_pm2" ) );
      jo.put( "successors_pm2", request.getParameter( "successors_pm2" ) );
      jo.put( "rating_pm2", request.getParameter( "rating_pm2" ) );
      this.setPm2Content( jo.toString() );
      this.setStatus_pm2( request.getParameter( "status_pm2" ) );
   }

   // 非直系主管3修改
   public void update_pm_non_direct_3( HttpServletRequest request ) throws KANException
   {
      JSONObject jo = new JSONObject();
      jo.put( "accomplishments_pm3", request.getParameter( "accomplishments_pm3" ) );
      jo.put( "areasOfStrengths_pm3", request.getParameter( "areasOfStrengths_pm3" ) );
      jo.put( "areasOfImprovement_pm3", request.getParameter( "areasOfImprovement_pm3" ) );
      jo.put( "isPromotion_pm3", request.getParameter( "isPromotion_pm3" ) );
      jo.put( "promotionReason_pm3", request.getParameter( "promotionReason_pm3" ) );
      jo.put( "successors_pm3", request.getParameter( "successors_pm3" ) );
      jo.put( "rating_pm3", request.getParameter( "rating_pm3" ) );
      this.setPm3Content( jo.toString() );
      this.setStatus_pm3( request.getParameter( "status_pm3" ) );
   }

   // 非直系主管4修改
   public void update_pm_non_direct_4( HttpServletRequest request ) throws KANException
   {
      JSONObject jo = new JSONObject();
      jo.put( "accomplishments_pm4", request.getParameter( "accomplishments_pm4" ) );
      jo.put( "areasOfStrengths_pm4", request.getParameter( "areasOfStrengths_pm4" ) );
      jo.put( "areasOfImprovement_pm4", request.getParameter( "areasOfImprovement_pm4" ) );
      jo.put( "isPromotion_pm4", request.getParameter( "isPromotion_pm4" ) );
      jo.put( "promotionReason_pm4", request.getParameter( "promotionReason_pm4" ) );
      jo.put( "successors_pm4", request.getParameter( "successors_pm4" ) );
      jo.put( "rating_pm4", request.getParameter( "rating_pm4" ) );
      this.setPm4Content( jo.toString() );
      this.setStatus_pm4( request.getParameter( "status_pm4" ) );
   }

   public String getAssessmentId()
   {
      return assessmentId;
   }

   public void setAssessmentId( String assessmentId )
   {
      this.assessmentId = assessmentId;
   }

   public int getYear()
   {
      return year;
   }

   public void setYear( int year )
   {
      this.year = year;
   }

   public String getSelfPositionId()
   {
      return selfPositionId;
   }

   public void setSelfPositionId( String selfPositionId )
   {
      this.selfPositionId = selfPositionId;
   }

   public String getParentBranchId()
   {
      return parentBranchId;
   }

   public void setParentBranchId( String parentBranchId )
   {
      this.parentBranchId = parentBranchId;
   }

   public String getParentPositionId()
   {
      return KANUtil.filterEmpty( parentPositionId );
   }

   public void setParentPositionId( String parentPositionId )
   {
      this.parentPositionId = parentPositionId;
   }

   public String getBizLeaderPositionId()
   {
      return KANUtil.filterEmpty( bizLeaderPositionId );
   }

   public void setBizLeaderPositionId( String bizLeaderPositionId )
   {
      this.bizLeaderPositionId = bizLeaderPositionId;
   }

   public String getBuLeaderPositionId()
   {
      return KANUtil.filterEmpty( buLeaderPositionId );
   }

   public void setBuLeaderPositionId( String buLeaderPositionId )
   {
      this.buLeaderPositionId = buLeaderPositionId;
   }

   public String getHrOwnerPositionId()
   {
      return KANUtil.filterEmpty( hrOwnerPositionId );
   }

   public void setHrOwnerPositionId( String hrOwnerPositionId )
   {
      this.hrOwnerPositionId = hrOwnerPositionId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getEmployeeNameEN()
   {
      return employeeNameEN;
   }

   public void setEmployeeNameEN( String employeeNameEN )
   {
      this.employeeNameEN = employeeNameEN;
   }

   public String getEmployeeName()
   {
      return super.getLocale().getLanguage().equals( "zh" ) ? this.getEmployeeNameZH() : this.getEmployeeNameEN();
   }

   public String getDirectLeaderNameZH()
   {
      return directLeaderNameZH;
   }

   public void setDirectLeaderNameZH( String directLeaderNameZH )
   {
      this.directLeaderNameZH = directLeaderNameZH;
   }

   public String getDirectLeaderNameEN()
   {
      return directLeaderNameEN;
   }

   public void setDirectLeaderNameEN( String directLeaderNameEN )
   {
      this.directLeaderNameEN = directLeaderNameEN;
   }

   public String getAccomplishments()
   {
      return accomplishments;
   }

   public void setAccomplishments( String accomplishments )
   {
      this.accomplishments = accomplishments;
   }

   public String getAreasOfStrengths()
   {
      return areasOfStrengths;
   }

   public void setAreasOfStrengths( String areasOfStrengths )
   {
      this.areasOfStrengths = areasOfStrengths;
   }

   public String getAreasOfImprovement()
   {
      return areasOfImprovement;
   }

   public void setAreasOfImprovement( String areasOfImprovement )
   {
      this.areasOfImprovement = areasOfImprovement;
   }

   public String getNextYearGoals()
   {
      return nextYearGoals;
   }

   public void setNextYearGoals( String nextYearGoals )
   {
      this.nextYearGoals = nextYearGoals;
   }

   public String getNextYearPlans()
   {
      return nextYearPlans;
   }

   public void setNextYearPlans( String nextYearPlans )
   {
      this.nextYearPlans = nextYearPlans;
   }

   public String getSuccessors()
   {
      return successors;
   }

   public void setSuccessors( String successors )
   {
      this.successors = successors;
   }

   public String getOtherComments()
   {
      return otherComments;
   }

   public void setOtherComments( String otherComments )
   {
      this.otherComments = otherComments;
   }

   public String getAccomplishments_pm()
   {
      return accomplishments_pm;
   }

   public void setAccomplishments_pm( String accomplishments_pm )
   {
      this.accomplishments_pm = accomplishments_pm;
   }

   public String getAreasOfStrengths_pm()
   {
      return areasOfStrengths_pm;
   }

   public void setAreasOfStrengths_pm( String areasOfStrengths_pm )
   {
      this.areasOfStrengths_pm = areasOfStrengths_pm;
   }

   public String getAreasOfImprovement_pm()
   {
      return areasOfImprovement_pm;
   }

   public void setAreasOfImprovement_pm( String areasOfImprovement_pm )
   {
      this.areasOfImprovement_pm = areasOfImprovement_pm;
   }

   public String getNextYearGoalsAndPlans_pm()
   {
      return nextYearGoalsAndPlans_pm;
   }

   public void setNextYearGoalsAndPlans_pm( String nextYearGoalsAndPlans_pm )
   {
      this.nextYearGoalsAndPlans_pm = nextYearGoalsAndPlans_pm;
   }

   public String getIsPromotion_pm()
   {
      return isPromotion_pm;
   }

   public void setIsPromotion_pm( String isPromotion_pm )
   {
      this.isPromotion_pm = isPromotion_pm;
   }

   public String getPromotionReason_pm()
   {
      return promotionReason_pm;
   }

   public void setPromotionReason_pm( String promotionReason_pm )
   {
      this.promotionReason_pm = promotionReason_pm;
   }

   public String getSuccessors_pm()
   {
      return successors_pm;
   }

   public void setSuccessors_pm( String successors_pm )
   {
      this.successors_pm = successors_pm;
   }

   public String getRating_pm()
   {
      return rating_pm;
   }

   public void setRating_pm( String rating_pm )
   {
      this.rating_pm = rating_pm;
   }

   public String getRating_bu()
   {
      return rating_bu;
   }

   public void setRating_bu( String rating_bu )
   {
      this.rating_bu = rating_bu;
   }

   public String getAccomplishments_bm()
   {
      return accomplishments_bm;
   }

   public void setAccomplishments_bm( String accomplishments_bm )
   {
      this.accomplishments_bm = accomplishments_bm;
   }

   public String getAreasOfStrengths_bm()
   {
      return areasOfStrengths_bm;
   }

   public void setAreasOfStrengths_bm( String areasOfStrengths_bm )
   {
      this.areasOfStrengths_bm = areasOfStrengths_bm;
   }

   public String getAreasOfImprovement_bm()
   {
      return areasOfImprovement_bm;
   }

   public void setAreasOfImprovement_bm( String areasOfImprovement_bm )
   {
      this.areasOfImprovement_bm = areasOfImprovement_bm;
   }

   public String getNextYearGoalsAndPlans_bm()
   {
      return nextYearGoalsAndPlans_bm;
   }

   public void setNextYearGoalsAndPlans_bm( String nextYearGoalsAndPlans_bm )
   {
      this.nextYearGoalsAndPlans_bm = nextYearGoalsAndPlans_bm;
   }

   public String getIsPromotion_bm()
   {
      return isPromotion_bm;
   }

   public void setIsPromotion_bm( String isPromotion_bm )
   {
      this.isPromotion_bm = isPromotion_bm;
   }

   public String getPromotionReason_bm()
   {
      return promotionReason_bm;
   }

   public void setPromotionReason_bm( String promotionReason_bm )
   {
      this.promotionReason_bm = promotionReason_bm;
   }

   public String getSuccessors_bm()
   {
      return successors_bm;
   }

   public void setSuccessors_bm( String successors_bm )
   {
      this.successors_bm = successors_bm;
   }

   public String getAccomplishments_bu()
   {
      return accomplishments_bu;
   }

   public void setAccomplishments_bu( String accomplishments_bu )
   {
      this.accomplishments_bu = accomplishments_bu;
   }

   public String getAreasOfStrengths_bu()
   {
      return areasOfStrengths_bu;
   }

   public void setAreasOfStrengths_bu( String areasOfStrengths_bu )
   {
      this.areasOfStrengths_bu = areasOfStrengths_bu;
   }

   public String getAreasOfImprovement_bu()
   {
      return areasOfImprovement_bu;
   }

   public void setAreasOfImprovement_bu( String areasOfImprovement_bu )
   {
      this.areasOfImprovement_bu = areasOfImprovement_bu;
   }

   public String getNextYearGoalsAndPlans_bu()
   {
      return nextYearGoalsAndPlans_bu;
   }

   public void setNextYearGoalsAndPlans_bu( String nextYearGoalsAndPlans_bu )
   {
      this.nextYearGoalsAndPlans_bu = nextYearGoalsAndPlans_bu;
   }

   public String getIsPromotion_bu()
   {
      return isPromotion_bu;
   }

   public void setIsPromotion_bu( String isPromotion_bu )
   {
      this.isPromotion_bu = isPromotion_bu;
   }

   public String getPromotionReason_bu()
   {
      return promotionReason_bu;
   }

   public void setPromotionReason_bu( String promotionReason_bu )
   {
      this.promotionReason_bu = promotionReason_bu;
   }

   public String getSuccessors_bu()
   {
      return successors_bu;
   }

   public void setSuccessors_bu( String successors_bu )
   {
      this.successors_bu = successors_bu;
   }

   public String getStatus_bm()
   {
      return status_bm;
   }

   public void setStatus_bm( String status_bm )
   {
      this.status_bm = status_bm;
   }

   public String getStatus_bu()
   {
      return status_bu;
   }

   public void setStatus_bu( String status_bu )
   {
      this.status_bu = status_bu;
   }

   public String getRating_final()
   {
      return rating_final;
   }

   public void setRating_final( String rating_final )
   {
      this.rating_final = rating_final;
   }

   public String getPm1PositionId()
   {
      return pm1PositionId;
   }

   public void setPm1PositionId( String pm1PositionId )
   {
      this.pm1PositionId = pm1PositionId;
   }

   public String getPm2PositionId()
   {
      return pm2PositionId;
   }

   public void setPm2PositionId( String pm2PositionId )
   {
      this.pm2PositionId = pm2PositionId;
   }

   public String getPm3PositionId()
   {
      return pm3PositionId;
   }

   public void setPm3PositionId( String pm3PositionId )
   {
      this.pm3PositionId = pm3PositionId;
   }

   public String getPm4PositionId()
   {
      return pm4PositionId;
   }

   public void setPm4PositionId( String pm4PositionId )
   {
      this.pm4PositionId = pm4PositionId;
   }

   public String getPm1Content()
   {
      return pm1Content;
   }

   public void setPm1Content( String pm1Content )
   {
      this.pm1Content = pm1Content;
   }

   public String getPm2Content()
   {
      return pm2Content;
   }

   public void setPm2Content( String pm2Content )
   {
      this.pm2Content = pm2Content;
   }

   public String getPm3Content()
   {
      return pm3Content;
   }

   public void setPm3Content( String pm3Content )
   {
      this.pm3Content = pm3Content;
   }

   public String getPm4Content()
   {
      return pm4Content;
   }

   public void setPm4Content( String pm4Content )
   {
      this.pm4Content = pm4Content;
   }

   public String getStatus_pm1()
   {
      return status_pm1;
   }

   public void setStatus_pm1( String status_pm1 )
   {
      this.status_pm1 = status_pm1;
   }

   public String getStatus_pm2()
   {
      return status_pm2;
   }

   public void setStatus_pm2( String status_pm2 )
   {
      this.status_pm2 = status_pm2;
   }

   public String getStatus_pm3()
   {
      return status_pm3;
   }

   public void setStatus_pm3( String status_pm3 )
   {
      this.status_pm3 = status_pm3;
   }

   public String getStatus_pm4()
   {
      return status_pm4;
   }

   public void setStatus_pm4( String status_pm4 )
   {
      this.status_pm4 = status_pm4;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getIsPromption_pms()
   {
      return isPromption_pms;
   }

   public void setIsPromption_pms( List< MappingVO > isPromption_pms )
   {
      this.isPromption_pms = isPromption_pms;
   }

   public List< MappingVO > getYears()
   {
      return years;
   }

   public void setYears( List< MappingVO > years )
   {
      this.years = years;
   }

   public String getIsDirectSub()
   {
      return isDirectSub;
   }

   public void setIsDirectSub( String isDirectSub )
   {
      this.isDirectSub = isDirectSub;
   }

   public String getDecodeParentBranchId()
   {
      return decodeField( parentBranchId, branchs, true );
   }

   public String getDecodeIsPromotion_pm()
   {
      return decodeField( isPromotion_pm, isPromption_pms );
   }

  public String getStatus_pm() {
    return status_pm;
  }

  public void setStatus_pm(String status_pm) {
    this.status_pm = status_pm;
  }

}
