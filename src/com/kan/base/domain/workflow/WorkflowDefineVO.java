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
* 项目名称：HRO_V1  
* 类名称：WorkflowDefineVO  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-6-26 下午05:53:12  
* 修改人：Jixiang  
* 修改时间：2013-6-26 下午05:53:12  
* 修改备注：  
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
   // defineId:工作流定义ID
   private String defineId;

   // systemId:系统Id，HRO、HRM等
   private String systemId;

   // scope:作用范围
   private int scope;

   // 中文名
   private String nameZH;

   // 英文名
   private String nameEN;

   // moduleId:  工作流模块id
   private String workflowModuleId;

   // rightIds:工作流触发操作权限Ids，Json方式存储
   private String rightIds;

   // approvalType:流程审批类型
   private String approvalType;

   // topPositionGrade:最高审批职级
   private String topPositionGrade;

   // steps:审批步骤数，通常是指审批级数，不包含必须步骤
   private int steps;

   // sendEmail:是否需要发送邮件
   private String sendEmail;

   // sendSMS:是否需要发送短信
   private String sendSMS;

   // sendInfo:是否需要发送系统信息
   private String sendInfo;

   //  邮件模板ID
   private String emailTemplateId;

   //  短信模板ID
   private String smsTemplateId;

   // 系统消息模板
   private String infoTemplateId;

   /***
    * For Application
    */
   @JsonIgnore
   // 工作流模块
   private List< MappingVO > workflowModules = new ArrayList< MappingVO >();
   @JsonIgnore
   // 左右范围
   private List< MappingVO > scopes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 内部职级
   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();
   @JsonIgnore
   // 外部职级
   private List< MappingVO > employeePositionGrades = new ArrayList< MappingVO >();
   @JsonIgnore
   // 权限列表MappingVO
   private String[] rightIdsArray = new String[] {};
   @JsonIgnore
   // 审批类型
   private List< MappingVO > approvalTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > employeeApprovalTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 消息模板
   private List< MappingVO > emailTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > smsTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > infoTemplateIds = new ArrayList< MappingVO >();
   @JsonIgnore
   // 消息提醒类型
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
         return decodeField( topPositionGrade, employeePositionGrades ) + "（职位外部）";
      }
      else
      {
         return decodeField( topPositionGrade, positionGrades ) + "（职位内部）";
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

   // 解译工作流模块
   public String getDecodeWorkModuleId()
   {
      return decodeField( workflowModuleId, workflowModules );
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
      //可选支持工作模块
      //1.得到accountConstants
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( this.getAccountId() );
      //2.判断accountConstants模块中是否有系统的模块
      final List< MappingVO > workflowModules = new ArrayList< MappingVO >();
      //添加空的MappingVO对象
      workflowModules.add( getEmptyMappingVO() );

      //2.1遍历系统所有工作流
      for ( WorkflowModuleVO workflowModuleVO : KANConstants.WORKFLOW_MOFDULE_VO )
      {
         // 1:HR Service 只加载ScopeType=1的工作流模块，2:In House 只加载ScopeType=2的工作流模块
         if ( workflowModuleVO.getScopeType().equals( this.getRole() ) )
         {
            final String moduleId = workflowModuleVO.getModuleId();
            // 如果该account有该模块的权限 则加入到workflowModules中
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

      // 公司内部职级
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

      // 公司外部职级
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
