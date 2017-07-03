package com.kan.base.domain.workflow;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class WorkflowActualStepsVO extends BaseVO
{
   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = 9077017736965833155L;

   /**
    * For DB
    */
   // 步骤ID
   private String stepId;

   // 工作流ID
   private String workflowId;

   // 步骤类型
   private String stepType;

   // 审批类型
   private String auditType;

   // 审核ID 
   private String auditTargetId;

   // 工作流顺序
   private int stepIndex;

   // 是否需要发送邮件
   private String sendEmail;

   // 是否需要发送短信
   private String sendSMS;

   // 是否需要发送系统信息
   private String sendInfo;

   // 邮件模板ID
   private String emailTemplateType;

   // 短信模板ID
   private String smsTemplateType;

   // 系统消息模板
   private String infoTemplateType;

   // 邮件审批随机码
   private String randomKey;

   // 处理时间
   private Date handleDate;

   // 操作时的描述
   private String description;

   /**
    * For App
    */
   private String positionTitle;

   private String positionTitleZH;

   private String positionTitleEN;

   public String getStepId()
   {
      return stepId;
   }

   public void setStepId( String stepId )
   {
      this.stepId = stepId;
   }

   public String getWorkflowId()
   {
      return workflowId;
   }

   public void setWorkflowId( String workflowId )
   {
      this.workflowId = workflowId;
   }

   public String getAuditType()
   {
      return auditType;
   }

   public void setAuditType( String auditType )
   {
      this.auditType = auditType;
   }

   public void setAuditTargetId( String auditTargetId )
   {
      this.auditTargetId = auditTargetId;
   }

   public String getAuditTargetId()
   {
      return auditTargetId;
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

   public Date getHandleDate()
   {
      return handleDate;
   }

   public void setHandleDate( Date handleDate )
   {
      this.handleDate = handleDate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.stepId );
   }

   @Override
   public void reset() throws KANException
   {
      this.description = "";
      this.stepId = "";
      this.stepIndex = 0;
      setStatus( "" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "actual.steps.status" ) );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final WorkflowActualStepsVO workflowActualStepsVO = ( WorkflowActualStepsVO ) object;
      setStatus( workflowActualStepsVO.getStatus() );
      setModifyBy( workflowActualStepsVO.getModifyBy() );
      setModifyDate( new Date() );
      this.description = workflowActualStepsVO.getDescription();
   }

   public String getDecodeHandleDate()
   {
      return decodeDatetime( handleDate );
   }

   public String getRandomKey()
   {
      return randomKey;
   }

   public void setRandomKey( String randomKey )
   {
      this.randomKey = randomKey;
   }

   public String getEmailTemplateType()
   {
      return emailTemplateType;
   }

   public void setEmailTemplateType( String emailTemplateType )
   {
      this.emailTemplateType = emailTemplateType;
   }

   public String getSmsTemplateType()
   {
      return smsTemplateType;
   }

   public void setSmsTemplateType( String smsTemplateType )
   {
      this.smsTemplateType = smsTemplateType;
   }

   public String getInfoTemplateType()
   {
      return infoTemplateType;
   }

   public void setInfoTemplateType( String infoTemplateType )
   {
      this.infoTemplateType = infoTemplateType;
   }

   public String getStepType()
   {
      return stepType;
   }

   public void setStepType( String stepType )
   {
      this.stepType = stepType;
   }

   public String getPositionTitle()
   {
      return positionTitle;
   }

   public void setPositionTitle( String positionTitle )
   {
      this.positionTitle = positionTitle;
   }

   public String getPositionTitleZH()
   {
      return positionTitleZH;
   }

   public void setPositionTitleZH( String positionTitleZH )
   {
      this.positionTitleZH = positionTitleZH;
   }

   public String getPositionTitleEN()
   {
      return positionTitleEN;
   }

   public void setPositionTitleEN( String positionTitleEN )
   {
      this.positionTitleEN = positionTitleEN;
   }

}
