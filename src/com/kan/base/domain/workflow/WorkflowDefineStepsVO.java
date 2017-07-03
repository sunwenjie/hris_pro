package com.kan.base.domain.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

/**
 * 项目名称：HRO_V1 类名称：WorkFlowDefineStepsVO 类描述： 创建人：Jixiang 创建时间：2013-6-28
 * 上午11:49:27 修改人：Jixiang 修改时间：2013-6-28 上午11:49:27 修改备注：
 * 
 * @version
 */

/**  
*   
* 项目名称：HRO_V1  
* 类名称：WorkflowDefineStepsVO  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2014-4-1 上午11:33:36  
* 修改人：Jixiang  
* 修改时间：2014-4-1 上午11:33:36  
* 修改备注：  
* @version   
*   
*/
public class WorkflowDefineStepsVO extends BaseVO
{
   // Serial Version UID
   private static final long serialVersionUID = -16874315848654L;

   /**
    * For DB
    */
   // 流程步骤ID
   private String stepId;

   // 工作流ID
   private String defineId;

   // 审批类型
   private String auditType;

   // 步骤类型
   private String stepType;

   // 工作流职位
   private String positionId;

   // 指定审批人userID
   private String staffId;

   // 参与方式 
   private String joinType;

   // 参考职位
   private String referPositionId;

   // 参考职级
   private String referPositionGrade;

   // 参与顺序
   private String joinOrderType;

   // 工作流顺序 
   private int stepIndex;

   // 是否需要发送邮件
   private String sendEmail;

   // 是否需要发送短信
   private String sendSMS;

   // 是否需要发送系统信息
   private String sendInfo;

   // 邮件模板ID
   private String emailTemplateId;

   // 短信模板ID 
   private String smsTemplateId;

   // 系统消息模板
   private String infoTemplateId;

   /**
    * For Application
    */
   @JsonIgnore
   // 审核类型列表
   private List< MappingVO > auditTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 步骤类型列表
   private List< MappingVO > stepTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 参与方式 
   private List< MappingVO > joinTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 参与顺序
   private List< MappingVO > joinOrderTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 消息模板
   private List< MappingVO > emailTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > smsTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > infoTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   // 职级（内部）
   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();
   @JsonIgnore
   // 职级（外部）
   private List< MappingVO > employeePositionGrades = new ArrayList< MappingVO >();

   // 权重
   private double weight;

   private String titleZH;

   private String titleEN;

   // 范围
   private String scope;
   @JsonIgnore
   // 消息提醒类型
   private List< MappingVO > msgRemindTypes = new ArrayList< MappingVO >();

   @Override
   public void reset() throws KANException
   {
      this.stepIndex = 1;
      this.positionId = "";
      this.staffId = "";
      this.positionId = "";
      this.stepType = "";
      this.auditType = "";
      this.joinType = "";
      this.joinOrderType = "";
      this.joinOrderType = "";
      this.referPositionId = "";
      this.referPositionGrade = "";
      this.sendInfo = "";
      this.sendSMS = "";
      this.emailTemplateId = "";
      this.smsTemplateId = "";
      this.infoTemplateId = "";
      this.setStepIndex( 0 );
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.stepTypes = KANUtil.getMappings( request.getLocale(), "define.steps.stepType" );
      this.auditTypes = KANUtil.getMappings( request.getLocale(), "define.steps.audiyType" );
      this.joinTypes = KANUtil.getMappings( request.getLocale(), "define.steps.joinType" );
      this.joinOrderTypes = KANUtil.getMappings( request.getLocale(), "define.steps.joinOrderType" );

      try
      {
         this.emailTemplateIds = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMessageTemplateByType( request.getLocale().getLanguage(), "1" );
         this.smsTemplateIds = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMessageTemplateByType( request.getLocale().getLanguage(), "2" );
         this.infoTemplateIds = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMessageTemplateByType( request.getLocale().getLanguage(), "3" );
         this.positionGrades = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionGrades( this.getLocale().getLanguage(), BaseAction.getCorpId( request, null ) );
         this.employeePositionGrades = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getEmployeePositionGrades( this.getLocale().getLanguage() );
         employeePositionGrades.add( 0, getEmptyMappingVO() );
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }
      if ( emailTemplateIds == null )
      {
         emailTemplateIds = new ArrayList< MappingVO >();
         emailTemplateIds.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }
      else
      {
         emailTemplateIds.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }
      if ( smsTemplateIds == null )
      {
         smsTemplateIds = new ArrayList< MappingVO >();
         smsTemplateIds.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }
      else
      {
         smsTemplateIds.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }
      if ( infoTemplateIds == null )
      {
         infoTemplateIds = new ArrayList< MappingVO >();
         infoTemplateIds.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }
      else
      {
         infoTemplateIds.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }
      if ( positionGrades == null )
      {
         positionGrades = new ArrayList< MappingVO >();
         positionGrades.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }
      else
      {
         positionGrades.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }

      this.msgRemindTypes = KANUtil.getMappings( request.getLocale(), "define.msg.remind.type" );
   }

   @Override
   public void update( final Object object )
   {
      final WorkflowDefineStepsVO workflowDefineVO = ( WorkflowDefineStepsVO ) object;

      super.setModifyBy( workflowDefineVO.getModifyBy() );
      super.setModifyDate( new Date() );
      this.stepIndex = workflowDefineVO.getStepIndex();
      this.positionId = workflowDefineVO.getPositionId();
      this.staffId = workflowDefineVO.getStaffId();
      this.auditType = workflowDefineVO.getAuditType();
      this.stepType = workflowDefineVO.getStepType();
      this.joinType = workflowDefineVO.getJoinType();
      this.joinOrderType = workflowDefineVO.getJoinOrderType();
      this.referPositionId = workflowDefineVO.getReferPositionId();
      this.referPositionGrade = workflowDefineVO.getReferPositionGrade();
      this.sendEmail = workflowDefineVO.getSendEmail();
      this.sendInfo = workflowDefineVO.getSendInfo();
      this.sendSMS = workflowDefineVO.getSendSMS();
      this.emailTemplateId = workflowDefineVO.getEmailTemplateId();
      this.smsTemplateId = workflowDefineVO.getSmsTemplateId();
      this.infoTemplateId = workflowDefineVO.getInfoTemplateId();
      super.setStatus( workflowDefineVO.getStatus() );
   }

   public String getStepId()
   {
      return stepId;
   }

   public void setStepId( String stepId )
   {
      this.stepId = stepId;
   }

   public String getDefineId()
   {
      return defineId;
   }

   public void setDefineId( String defindId )
   {
      this.defineId = defindId;
   }

   public String getStepType()
   {
      return stepType;
   }

   public void setStepType( String stepType )
   {
      this.stepType = stepType;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public int getStepIndex()
   {
      return stepIndex;
   }

   public void setStepIndex( int stepIndex )
   {
      this.stepIndex = stepIndex;
   }

   public String getSendEmail()
   {
      return sendEmail;
   }

   public void setSendEmail( String sendEmail )
   {
      this.sendEmail = sendEmail;
   }

   public String getSendSMS()
   {
      return sendSMS;
   }

   public void setSendSMS( String sendSMS )
   {
      this.sendSMS = sendSMS;
   }

   public String getSendInfo()
   {
      return sendInfo;
   }

   public void setSendInfo( String sendInfo )
   {
      this.sendInfo = sendInfo;
   }

   public double getWeight()
   {
      return weight;
   }

   public void setWeight( double weight )
   {
      this.weight = weight;
   }

   public String getTitleZH()
   {
      return titleZH;
   }

   public void setTitleZH( String titleZH )
   {
      this.titleZH = titleZH;
   }

   public String getTitleEN()
   {
      return titleEN;
   }

   public void setTitleEN( String titleEN )
   {
      this.titleEN = titleEN;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( stepId );
   }

   public List< MappingVO > getStepTypes()
   {
      return stepTypes;
   }

   public void setStepTypes( List< MappingVO > stepTypes )
   {
      this.stepTypes = stepTypes;
   }

   public String getJoinType()
   {
      return joinType;
   }

   public String getStaffId()
   {
      return staffId;
   }

   public void setStaffId( String staffId )
   {
      this.staffId = staffId;
   }

   public void setJoinType( String joinType )
   {
      this.joinType = joinType;
   }

   public String getJoinOrderType()
   {
      return joinOrderType;
   }

   public void setJoinOrderType( String joinOrderType )
   {
      this.joinOrderType = joinOrderType;
   }

   public String getEmailTemplateId()
   {
      return emailTemplateId;
   }

   public void setEmailTemplateId( String emailTemplateId )
   {
      this.emailTemplateId = emailTemplateId;
   }

   public String getSmsTemplateId()
   {
      return smsTemplateId;
   }

   public void setSmsTemplateId( String smsTemplateId )
   {
      this.smsTemplateId = smsTemplateId;
   }

   public String getInfoTemplateId()
   {
      return infoTemplateId;
   }

   public void setInfoTemplateId( String infoTemplateId )
   {
      this.infoTemplateId = infoTemplateId;
   }

   public List< MappingVO > getJoinTypes()
   {
      return joinTypes;
   }

   public void setJoinTypes( List< MappingVO > joinTypes )
   {
      this.joinTypes = joinTypes;
   }

   public List< MappingVO > getJoinOrderTypes()
   {
      return joinOrderTypes;
   }

   public void setJoinOrderTypes( List< MappingVO > joinOrderTypes )
   {
      this.joinOrderTypes = joinOrderTypes;
   }

   public List< MappingVO > getEmailTemplateIds()
   {
      return emailTemplateIds;
   }

   public void setEmailTemplateIds( List< MappingVO > emailTemplateIds )
   {
      this.emailTemplateIds = emailTemplateIds;
   }

   public List< MappingVO > getSmsTemplateIds()
   {
      return smsTemplateIds;
   }

   public void setSmsTemplateIds( List< MappingVO > smsTemplateIds )
   {
      this.smsTemplateIds = smsTemplateIds;
   }

   public List< MappingVO > getInfoTemplateIds()
   {
      return infoTemplateIds;
   }

   public void setInfoTemplateIds( List< MappingVO > infoTemplateIds )
   {
      this.infoTemplateIds = infoTemplateIds;
   }

   public String getReferPositionId()
   {
      return KANUtil.filterEmpty( referPositionId );
   }

   public void setReferPositionId( String referPositionId )
   {
      this.referPositionId = referPositionId;
   }

   public String getAuditType()
   {
      return auditType;
   }

   public void setAuditType( String auditType )
   {
      this.auditType = auditType;
   }

   public List< MappingVO > getAuditTypes()
   {
      return auditTypes;
   }

   public void setAuditTypes( List< MappingVO > auditTypes )
   {
      this.auditTypes = auditTypes;
   }

   public String getReferPositionGrade()
   {
      return referPositionGrade;
   }

   public void setReferPositionGrade( String referPositionGrade )
   {
      this.referPositionGrade = referPositionGrade;
   }

   public List< MappingVO > getPositionGrades()
   {
      return positionGrades;
   }

   public void setPositionGrades( List< MappingVO > positionGrades )
   {
      this.positionGrades = positionGrades;
   }

   public List< MappingVO > getEmployeePositionGrades()
   {
      return employeePositionGrades;
   }

   public void setEmployeePositionGrades( List< MappingVO > employeePositionGrades )
   {
      this.employeePositionGrades = employeePositionGrades;
   }

   public String getScope()
   {
      return scope;
   }

   public void setScope( String scope )
   {
      this.scope = scope;
   }

   public String getDecodeStaffId( String staffId )
   {
      if ( staffId != null )
      {
         // 获得常量中的StaffBaseView集合
         final List< StaffBaseView > staffBaseViews = KANConstants.getKANAccountConstants( getAccountId() ).STAFF_BASEVIEW;

         for ( StaffBaseView staffBaseView : staffBaseViews )
         {
            if ( staffId.equals( staffBaseView.getId() ) )
            {
               if ( getLocale() != null && getLocale().getLanguage() != null && getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  return staffBaseView.getNameZH();
               }
               else
               {
                  return staffBaseView.getNameEN();
               }
            }
         }
      }

      return "";
   }

   // 解译审核职位
   public String getDecodePositionId()
   {
      return decodePositionId( this.positionId );
   }

   // 解译参考职位
   public String getDecodeReferPositionId()
   {
      return decodePositionId( this.referPositionId );
   }

   public String getDecodeJoinObject()
   {
      if ( "1".equals( this.joinType ) )
      {
         return getDecodeReferPositionId();
      }
      else if ( "2".equals( joinType ) )
      {
         return getDecodeReferPositionGrade();
      }
      return "";
   }

   public String getDecodeJoinOrderType()
   {
      return decodeField( this.joinOrderType, this.joinOrderTypes );
   }

   public String getDecodeAuditObject()
   {
      if ( "1".equals( this.auditType ) )
      {
         return getDecodePositionId();
      }
      else if ( "4".equals( auditType ) )
      {
         return getDecodeStaffId( staffId );
      }
      return "";
   }

   public void stepIndexAddOne()
   {
      this.stepIndex++;
   }

   public void stepIndexSubOne()
   {
      this.stepIndex--;
   }

   // 解译审批类型
   public String getDecodeAuditType()
   {
      return decodeField( this.auditType, this.auditTypes );
   }

   // 解译步骤类型
   public String getDecodeStepType()
   {
      return decodeField( stepType, stepTypes );
   }

   // 解译参与方式
   public String getDecodeJoinType()
   {
      return decodeField( this.joinType, this.joinTypes );
   }

   // 解译参考职级
   public String getDecodeReferPositionGrade()
   {
      if ( "1".equals( scope ) )
      {
         return decodeField( this.referPositionGrade, this.positionGrades );
      }
      else
      {
         return decodeField( this.referPositionGrade, this.employeePositionGrades );
      }
   }

   // 解译消息提醒
   public String getDecodeMessageRemind()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( sendEmail ) != null && sendEmail.equals( "1" ) )
      {
         returnString = msgRemindTypes.get( 0 ).getMappingValue();
      }

      if ( KANUtil.filterEmpty( sendSMS ) != null && sendSMS.equals( "1" ) )
      {
         if ( KANUtil.filterEmpty( returnString ) == null )
            returnString = msgRemindTypes.get( 1 ).getMappingValue();
         else
            returnString = returnString + " + " + msgRemindTypes.get( 1 ).getMappingValue();
      }

      if ( KANUtil.filterEmpty( sendInfo ) != null && sendInfo.equals( "1" ) )
      {
         if ( KANUtil.filterEmpty( returnString ) == null )
            returnString = msgRemindTypes.get( 2 ).getMappingValue();
         else
            returnString = returnString + " + " + msgRemindTypes.get( 2 ).getMappingValue();
      }

      return returnString;
   }

   private String decodePositionId( final String positionId )
   {
      if ( KANUtil.filterEmpty( positionId ) != null )
      {
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId() );
         if ( accountConstants != null )
         {
            if ( "1".equals( scope ) )
            {
               PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( positionId );
               if ( positionDTO != null && positionDTO.getPositionVO() != null )
               {
                  return positionDTO.getPositionVO().getTitleZH();
               }
            }
            else if ( "2".equals( scope ) )
            {
               com.kan.base.domain.management.PositionDTO positionDTO = accountConstants.getEmployeePositionDTOByPositionId( positionId );
               if ( positionDTO != null && positionDTO.getPositionVO() != null )
               {
                  return positionDTO.getPositionVO().getTitleZH();
               }
            }
         }
      }

      return "";
   }

   public List< MappingVO > getMsgRemindTypes()
   {
      return msgRemindTypes;
   }

   public void setMsgRemindTypes( List< MappingVO > msgRemindTypes )
   {
      this.msgRemindTypes = msgRemindTypes;
   }

}
