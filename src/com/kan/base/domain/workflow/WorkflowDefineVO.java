package com.kan.base.domain.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.system.WorkflowModuleVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�WorkflowDefineVO  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-6-26 ����05:53:12  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2013-6-26 ����05:53:12  
* �޸ı�ע��  
* @version   
*   
*/
public class WorkflowDefineVO extends BaseVO
{
   // Serial Version UID
   private static final long serialVersionUID = -16874315848654L;

   /**
    * For DB
    */
   // defineId:����������ID
   private String defineId;

   // systemId:ϵͳId��HRO��HRM��
   private String systemId;

   // scope:���÷�Χ
   private int scope;

   // ������
   private String nameZH;

   // Ӣ����
   private String nameEN;

   // moduleId:  ������ģ��id
   private String workflowModuleId;

   // rightIds:��������������Ȩ��Ids��Json��ʽ�洢
   private String rightIds;

   // approvalType:������������
   private String approvalType;

   // topPositionGrade:�������ְ��
   private String topPositionGrade;

   // steps:������������ͨ����ָ�������������������벽��
   private int steps;

   // sendEmail:�Ƿ���Ҫ�����ʼ�
   private String sendEmail;

   // sendSMS:�Ƿ���Ҫ���Ͷ���
   private String sendSMS;

   // sendInfo:�Ƿ���Ҫ����ϵͳ��Ϣ
   private String sendInfo;

   //  �ʼ�ģ��ID
   private String emailTemplateId;

   //  ����ģ��ID
   private String smsTemplateId;

   // ϵͳ��Ϣģ��
   private String infoTemplateId;

   /***
    * For Application
    */
   @JsonIgnore
   // ������ģ��
   private List< MappingVO > workflowModules = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���ҷ�Χ
   private List< MappingVO > scopes = new ArrayList< MappingVO >();
   @JsonIgnore
   // �ڲ�ְ��
   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();
   @JsonIgnore
   // �ⲿְ��
   private List< MappingVO > employeePositionGrades = new ArrayList< MappingVO >();
   @JsonIgnore
   // Ȩ���б�MappingVO
   private String[] rightIdsArray = new String[] {};
   @JsonIgnore
   // ��������
   private List< MappingVO > approvalTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > employeeApprovalTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��Ϣģ��
   private List< MappingVO > emailTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > smsTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > infoTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��Ϣ��������
   private List< MappingVO > msgRemindTypes = new ArrayList< MappingVO >();

   public String getApprovalType()
   {
      return approvalType;
   }

   public List< MappingVO > getApprovalTypes()
   {
      return approvalTypes;
   }

   public String getDecodeScope()
   {
      return decodeField( String.valueOf( scope ), scopes );
   }

   public List< MappingVO > getEmployeeApprovalTypes()
   {
      return employeeApprovalTypes;
   }

   public void setEmployeeApprovalTypes( List< MappingVO > employeeApprovalTypes )
   {
      this.employeeApprovalTypes = employeeApprovalTypes;
   }

   public String getDecodeTopPositionGrade()
   {
      if ( scope == 2 )
      {
         return decodeField( topPositionGrade, employeePositionGrades ) + "��ְλ�ⲿ��";
      }
      else
      {
         return decodeField( topPositionGrade, positionGrades ) + "��ְλ�ڲ���";
      }
   }

   public String getDecodeApprovalType()
   {
      if ( scope == 2 )
      {
         return decodeField( approvalType, employeeApprovalTypes );
      }
      else
      {
         return decodeField( approvalType, approvalTypes );
      }
   }

   // ���빤����ģ��
   public String getDecodeWorkModuleId()
   {
      return decodeField( workflowModuleId, workflowModules );
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

   public String getDefineId()
   {
      return defineId;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( defineId );
   }

   public String getWorkflowModuleId()
   {
      return workflowModuleId;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public List< MappingVO > getPositionGrades()
   {
      return positionGrades;
   }

   public String getRightIds()
   {
      return rightIds;
   }

   public String[] getRightIdsArray()
   {
      return rightIdsArray;
   }

   public int getScope()
   {
      return scope;
   }

   public List< MappingVO > getScopes()
   {
      return scopes;
   }

   public String getSendEmail()
   {
      return sendEmail;
   }

   public String getSendInfo()
   {
      return sendInfo;
   }

   public String getSendSMS()
   {
      return sendSMS;
   }

   public int getSteps()
   {
      return steps;
   }

   public String getSystemId()
   {
      return systemId;
   }

   public String getTopPositionGrade()
   {
      return topPositionGrade;
   }

   public List< MappingVO > getWorkflowModules()
   {
      return workflowModules;
   }

   public List< MappingVO > getEmployeePositionGrades()
   {
      return employeePositionGrades;
   }

   public void setEmployeePositionGrades( List< MappingVO > employeePositionGrades )
   {
      this.employeePositionGrades = employeePositionGrades;
   }

   @Override
   public void reset() throws KANException
   {
      this.workflowModuleId = "0";
      this.nameZH = "";
      this.nameEN = "";
      this.scope = 0;
      this.rightIds = "";
      this.approvalType = "0";
      this.topPositionGrade = "0";
      this.steps = 0;
      this.sendEmail = "";
      this.sendInfo = "";
      this.sendSMS = "";
      this.emailTemplateId = "0";
      this.smsTemplateId = "0";
      this.infoTemplateId = "0";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.scopes = KANUtil.getMappings( request.getLocale(), "define.scope" );

      try
      {
         this.emailTemplateIds = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMessageTemplateByType( request.getLocale().getLanguage(), "1" );
         this.smsTemplateIds = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMessageTemplateByType( request.getLocale().getLanguage(), "2" );
         this.infoTemplateIds = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMessageTemplateByType( request.getLocale().getLanguage(), "3" );
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

      this.approvalTypes = KANUtil.getMappings( request.getLocale(), "define.approvalType" );
      this.employeeApprovalTypes = KANUtil.getMappings( request.getLocale(), "define.employeeApprovalType" );
      //��ѡ֧�ֹ���ģ��
      //1.�õ�accountConstants
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( this.getAccountId() );
      //2.�ж�accountConstantsģ�����Ƿ���ϵͳ��ģ��
      final List< MappingVO > workflowModules = new ArrayList< MappingVO >();
      //��ӿյ�MappingVO����
      workflowModules.add( getEmptyMappingVO() );

      //2.1����ϵͳ���й�����
      for ( WorkflowModuleVO workflowModuleVO : KANConstants.WORKFLOW_MOFDULE_VO )
      {
         // 1:HR Service ֻ����ScopeType=1�Ĺ�����ģ�飬2:In House ֻ����ScopeType=2�Ĺ�����ģ��
         if ( workflowModuleVO.getScopeType().equals( this.getRole() ) )
         {
            final String moduleId = workflowModuleVO.getModuleId();
            // �����account�и�ģ���Ȩ�� ����뵽workflowModules��
            if ( accountConstants.getAccountModuleDTOByModuleId( moduleId ) != null )
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( workflowModuleVO.getWorkflowModuleId() );

               if ( request.getLocale().getLanguage() != null && request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( workflowModuleVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( workflowModuleVO.getNameEN() );
               }
               workflowModules.add( mappingVO );
            }
         }
      }

      this.workflowModules = workflowModules;

      // ��˾�ڲ�ְ��
      try
      {
         if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            this.positionGrades = KANConstants.getKANAccountConstants( this.getAccountId() ).getPositionGrades( this.getLocale().getLanguage(), getCorpId() );
         }
         else
         {
            this.positionGrades = KANConstants.getKANAccountConstants( this.getAccountId() ).getPositionGrades( this.getLocale().getLanguage() );
         }
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }

      if ( this.positionGrades != null )
      {
         this.positionGrades.add( 0, super.getEmptyMappingVO() );
      }

      // ��˾�ⲿְ��
      this.employeePositionGrades = KANConstants.getKANAccountConstants( this.getAccountId() ).getEmployeePositionGrades( this.getLocale().getLanguage() );

      if ( this.employeePositionGrades != null )
      {
         this.employeePositionGrades.add( 0, super.getEmptyMappingVO() );
      }

      this.msgRemindTypes = KANUtil.getMappings( request.getLocale(), "define.msg.remind.type" );
   }

   public void setApprovalType( String approvalType )
   {
      this.approvalType = approvalType;
   }

   public void setApprovalTypes( List< MappingVO > approvalTypes )
   {
      this.approvalTypes = approvalTypes;
   }

   public void setDefineId( String defineId )
   {
      this.defineId = defineId;
   }

   public void setWorkflowModuleId( String workflowModuleId )
   {
      this.workflowModuleId = workflowModuleId;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public void setPositionGrades( List< MappingVO > positionGrades )
   {
      this.positionGrades = positionGrades;
   }

   public void setRightIds( String rightIds )
   {
      this.rightIds = rightIds;
      this.rightIdsArray = KANUtil.jasonArrayToStringArray( rightIds );
   }

   public void setRightIdsArray( String[] rightIdsArray )
   {
      this.rightIdsArray = rightIdsArray;
      this.rightIds = KANUtil.toJasonArray( rightIdsArray );
   }

   public void setScope( int scope )
   {
      this.scope = scope;
   }

   public void setScopes( List< MappingVO > scopes )
   {
      this.scopes = scopes;
   }

   public void setSendEmail( String sendEmail )
   {
      this.sendEmail = sendEmail;
   }

   public void setSendInfo( String sendInfo )
   {
      this.sendInfo = sendInfo;
   }

   public void setSendSMS( String sendSMS )
   {
      this.sendSMS = sendSMS;
   }

   public void setSteps( int steps )
   {
      this.steps = steps;
   }

   public void setSystemId( String systemId )
   {
      this.systemId = systemId;
   }

   public void setTopPositionGrade( String topPositionGrade )
   {
      this.topPositionGrade = topPositionGrade;
   }

   public void setWorkflowModules( List< MappingVO > workflowModules )
   {
      this.workflowModules = workflowModules;
   }

   @Override
   public void update( final Object object )
   {
      final WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) object;
      this.nameZH = workflowDefineVO.getNameZH();
      this.nameEN = workflowDefineVO.getNameEN();
      this.scope = workflowDefineVO.getScope();
      this.rightIds = workflowDefineVO.getRightIds();
      this.approvalType = workflowDefineVO.getApprovalType();
      this.topPositionGrade = workflowDefineVO.getTopPositionGrade();
      this.steps = workflowDefineVO.getSteps();
      this.sendEmail = workflowDefineVO.getSendEmail();
      this.sendInfo = workflowDefineVO.getSendInfo();
      this.sendSMS = workflowDefineVO.getSendSMS();
      this.emailTemplateId = workflowDefineVO.getEmailTemplateId();
      this.smsTemplateId = workflowDefineVO.getSmsTemplateId();
      this.infoTemplateId = workflowDefineVO.getInfoTemplateId();
      super.setStatus( workflowDefineVO.getStatus() );
      super.setModifyDate( new Date() );
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

   public void setInfoTemplateId( String infoTemplateType )
   {
      this.infoTemplateId = infoTemplateType;
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

   public List< MappingVO > getMsgRemindTypes()
   {
      return msgRemindTypes;
   }

   public void setMsgRemindTypes( List< MappingVO > msgRemindTypes )
   {
      this.msgRemindTypes = msgRemindTypes;
   }

}
