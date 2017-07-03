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
   // ����ID
   private String stepId;

   // ������ID
   private String workflowId;

   // ��������
   private String stepType;

   // ��������
   private String auditType;

   // ���ID 
   private String auditTargetId;

   // ������˳��
   private int stepIndex;

   // �Ƿ���Ҫ�����ʼ�
   private String sendEmail;

   // �Ƿ���Ҫ���Ͷ���
   private String sendSMS;

   // �Ƿ���Ҫ����ϵͳ��Ϣ
   private String sendInfo;

   // �ʼ�ģ��ID
   private String emailTemplateType;

   // ����ģ��ID
   private String smsTemplateType;

   // ϵͳ��Ϣģ��
   private String infoTemplateType;

   // �ʼ����������
   private String randomKey;

   // ����ʱ��
   private Date handleDate;

   // ����ʱ������
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
