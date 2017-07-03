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
 * ��Ŀ���ƣ�HRO_V1 �����ƣ�WorkFlowDefineStepsVO �������� �����ˣ�Jixiang ����ʱ�䣺2013-6-28
 * ����11:49:27 �޸��ˣ�Jixiang �޸�ʱ�䣺2013-6-28 ����11:49:27 �޸ı�ע��
 * 
 * @version
 */

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�WorkflowDefineStepsVO  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2014-4-1 ����11:33:36  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2014-4-1 ����11:33:36  
* �޸ı�ע��  
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
   // ���̲���ID
   private String stepId;

   // ������ID
   private String defineId;

   // ��������
   private String auditType;

   // ��������
   private String stepType;

   // ������ְλ
   private String positionId;

   // ָ��������userID
   private String staffId;

   // ���뷽ʽ 
   private String joinType;

   // �ο�ְλ
   private String referPositionId;

   // �ο�ְ��
   private String referPositionGrade;

   // ����˳��
   private String joinOrderType;

   // ������˳�� 
   private int stepIndex;

   // �Ƿ���Ҫ�����ʼ�
   private String sendEmail;

   // �Ƿ���Ҫ���Ͷ���
   private String sendSMS;

   // �Ƿ���Ҫ����ϵͳ��Ϣ
   private String sendInfo;

   // �ʼ�ģ��ID
   private String emailTemplateId;

   // ����ģ��ID 
   private String smsTemplateId;

   // ϵͳ��Ϣģ��
   private String infoTemplateId;

   /**
    * For Application
    */
   @JsonIgnore
   // ��������б�
   private List< MappingVO > auditTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���������б�
   private List< MappingVO > stepTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���뷽ʽ 
   private List< MappingVO > joinTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ����˳��
   private List< MappingVO > joinOrderTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��Ϣģ��
   private List< MappingVO > emailTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > smsTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > infoTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   // ְ�����ڲ���
   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();
   @JsonIgnore
   // ְ�����ⲿ��
   private List< MappingVO > employeePositionGrades = new ArrayList< MappingVO >();

   // Ȩ��
   private double weight;

   private String titleZH;

   private String titleEN;

   // ��Χ
   private String scope;
   @JsonIgnore
   // ��Ϣ��������
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
         // ��ó����е�StaffBaseView����
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

   // �������ְλ
   public String getDecodePositionId()
   {
      return decodePositionId( this.positionId );
   }

   // ����ο�ְλ
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

   // ������������
   public String getDecodeAuditType()
   {
      return decodeField( this.auditType, this.auditTypes );
   }

   // ���벽������
   public String getDecodeStepType()
   {
      return decodeField( stepType, stepTypes );
   }

   // ������뷽ʽ
   public String getDecodeJoinType()
   {
      return decodeField( this.joinType, this.joinTypes );
   }

   // ����ο�ְ��
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

   // ������Ϣ����
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
