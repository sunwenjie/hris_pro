package com.kan.base.service.impl.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.kan.base.core.ServiceLocator;
import com.kan.base.dao.inf.HistoryDao;
import com.kan.base.dao.inf.message.MessageInfoDao;
import com.kan.base.dao.inf.message.MessageMailDao;
import com.kan.base.dao.inf.message.MessageSmsDao;
import com.kan.base.dao.inf.message.MessageTemplateDao;
import com.kan.base.dao.inf.workflow.WorkflowActualStepsDao;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.message.MessageInfoVO;
import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.domain.message.MessageSmsVO;
import com.kan.base.domain.message.MessageTemplateVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.domain.workflow.WorkflowApprovedChainVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.MatchUtil;
import com.kan.hro.dao.inf.biz.attendance.LeaveHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.OTHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientContactDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentDao;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.client.ClientContactVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class KANSendMessageUtil
{

   public static String INSTANCE_NAME = "kanSendMessageUtil";

   public static String USERID_TODO = "USERID_TODO";

   // 发消息对应的dao
   private MessageInfoDao messageInfoDao;
   private MessageSmsDao messageSmsDao;
   private MessageMailDao messageMailDao;
   private EmployeeDao employeeDao;
   private ClientContactDao clientContactDao;
   private EmployeeContractDao employeeContractDao;
   private LeaveHeaderDao leaveHeaderDao;
   private OTHeaderDao otHeaderDao;
   private HistoryDao historyDao;
   private WorkflowActualStepsDao workflowActualStepsDao;
   private EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao;
   private EmployeePositionChangeDao employeePositionChangeDao;
   private EmployeeContractSalaryDao employeeContractSalaryDao;

   // 注入MessageTemplateDao
   private MessageTemplateDao messageTemplateDao;

   private KANSendMessageUtil()
   {
   }

   // 构造对象私有化
   public static KANSendMessageUtil newInstance()
   {
      return ( KANSendMessageUtil ) ServiceLocator.getService( INSTANCE_NAME );
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public ClientContactDao getClientContactDao()
   {
      return clientContactDao;
   }

   public void setClientContactDao( ClientContactDao clientContactDao )
   {
      this.clientContactDao = clientContactDao;
   }

   public MessageInfoDao getMessageInfoDao()
   {
      return messageInfoDao;
   }

   public void setMessageInfoDao( MessageInfoDao messageInfoDao )
   {
      this.messageInfoDao = messageInfoDao;
   }

   public MessageSmsDao getMessageSmsDao()
   {
      return messageSmsDao;
   }

   public void setMessageSmsDao( MessageSmsDao messageSmsDao )
   {
      this.messageSmsDao = messageSmsDao;
   }

   public MessageMailDao getMessageMailDao()
   {
      return messageMailDao;
   }

   public void setMessageMailDao( MessageMailDao messageMailDao )
   {
      this.messageMailDao = messageMailDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public LeaveHeaderDao getLeaveHeaderDao()
   {
      return leaveHeaderDao;
   }

   public void setLeaveHeaderDao( LeaveHeaderDao leaveHeaderDao )
   {
      this.leaveHeaderDao = leaveHeaderDao;
   }

   public OTHeaderDao getOtHeaderDao()
   {
      return otHeaderDao;
   }

   public void setOtHeaderDao( OTHeaderDao otHeaderDao )
   {
      this.otHeaderDao = otHeaderDao;
   }

   // 工作流结束后，给上报人发消息
   //* Add by siuvan.xia @2014-07-24  *//
   public void sendMessageToCreateBy( final WorkflowDefineVO workflowDefineVO, final WorkflowActualVO workflowActualVO, final WorkflowActualStepsVO workflowActualStepsVO,
         final String isPass ) throws KANException
   {
      final String accountId = workflowActualVO.getAccountId();
      final String corpId = workflowActualVO.getCorpId();

      // 消息/邮件标题
      final StringBuilder subject = new StringBuilder( workflowActualVO.getNameZH() + "已被" + ( isPass.equals( "1" ) ? "批准" : "退回" ) );

      final StringBuilder remarkZH = new StringBuilder();
      final StringBuilder remarkEN = new StringBuilder();

      if ( isPass.equals( "2" ) )
      {
         if ( workflowActualStepsVO != null && KANUtil.filterEmpty( workflowActualStepsVO.getDescription() ) != null )
         {
            remarkZH.append( "<p>退回原因：" + workflowActualStepsVO.getDescription() + "</p>" );
            remarkEN.append( "<p>Reason: " + workflowActualStepsVO.getDescription() + "</p>" );
         }
      }

      remarkZH.append( "<p>领导意见：" + ( isPass.equals( "1" ) ? "同意" : "拒绝" ) + "！ </p>" );
      remarkEN.append( "<p>Leader's Comment: " + ( isPass.equals( "1" ) ? "Approved" : "Refuse" ) + "！ </p>" );

      final StringBuilder mailOrInfoContent = new StringBuilder( getMessageContent( accountId, corpId, workflowDefineVO.getEmailTemplateId(), workflowActualVO, workflowActualStepsVO, "1", true, remarkZH.toString(), remarkEN.toString() ) );
      final StringBuilder smsContent = new StringBuilder( "您申请的" + workflowActualVO.getNameZH() + "已被" + ( isPass.equals( "1" ) ? "批准" : "退回！" ) );

      // 初始化MessageInfoVO
      final MessageInfoVO messageInfoVO = new MessageInfoVO();
      messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
      messageInfoVO.setAccountId( workflowActualVO.getAccountId() );
      messageInfoVO.setCorpId( workflowActualVO.getCorpId() );
      messageInfoVO.setTitle( subject.toString() );
      messageInfoVO.setContent( mailOrInfoContent.toString() );
      messageInfoVO.setStatus( "2" );//[针对发送者] 2，发布
      messageInfoVO.setReceptionStatus( "2" );//[针对接收者] 2.未读
      messageInfoVO.setCreateBy( "system" );
      messageInfoVO.setCreateDate( new Date() );

      // 初始化MessageSmsVO
      final MessageSmsVO messageSmsVO = new MessageSmsVO();
      messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
      messageSmsVO.setAccountId( workflowActualVO.getAccountId() );
      messageSmsVO.setCorpId( workflowActualVO.getCorpId() );
      messageSmsVO.setContent( smsContent.toString() );
      messageSmsVO.setStatus( "1" );//待发送
      messageSmsVO.setCreateBy( "system" );
      messageSmsVO.setCreateDate( new Date() );

      // 初始化MessageMailVO
      final MessageMailVO messageMailVO = new MessageMailVO();
      messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
      messageMailVO.setAccountId( workflowActualVO.getAccountId() );
      messageMailVO.setCorpId( workflowActualVO.getCorpId() );
      messageMailVO.setTitle( subject.toString() );
      messageMailVO.setContent( mailOrInfoContent.toString() );
      messageMailVO.setContentType( "2" );//
      messageMailVO.setStatus( "1" );//待发送
      messageMailVO.setCreateBy( "system" );
      messageMailVO.setCreateDate( new Date() );

      final StaffDTO staffDTO = KANConstants.getKANAccountConstants( workflowActualVO.getAccountId() ).getStaffDTOByUserId( workflowActualVO.getCreateBy() );

      if ( staffDTO != null && staffDTO.getUserVO() != null )
      {
         // 发送站内消息
         if ( "1".equals( workflowDefineVO.getSendInfo() ) )
         {
            messageInfoVO.setReception( staffDTO.getUserVO().getUserId() );
            this.getMessageInfoDao().insertMessageInfo( messageInfoVO );
         }

         // 发送短信消息
         if ( "1".equals( workflowDefineVO.getSendSMS() ) )
         {
            messageSmsVO.setReception( staffDTO.getStaffVO().getBizMobile() );
            this.getMessageSmsDao().insertMessageSms( messageSmsVO );
         }
         // 发送邮件消息
         if ( "1".equals( workflowDefineVO.getSendEmail() ) )
         {
            messageMailVO.setReception( staffDTO.getStaffVO().getBizEmail() );
            this.getMessageMailDao().insertMessageMail( messageMailVO );
         }
      }

   }

   private MessageInfoVO getMessageInfoVO( final String accountId, final String corpId, final String title, final String content )
   {
      // 初始化MessageInfoVO
      final MessageInfoVO messageInfoVO = new MessageInfoVO();
      messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
      messageInfoVO.setAccountId( accountId );
      messageInfoVO.setCorpId( corpId );
      messageInfoVO.setTitle( title );
      messageInfoVO.setContent( content.toString() );
      messageInfoVO.setStatus( "2" );//[针对发送者] 2，发布
      messageInfoVO.setReceptionStatus( "2" );//[针对接收者] 2.未读
      messageInfoVO.setCreateBy( "system" );
      messageInfoVO.setCreateDate( new Date() );

      return messageInfoVO;
   }

   private MessageSmsVO getMessageSmsVO( final String accountId, final String corpId, final String content )
   {
      // 初始化MessageSmsVO
      final MessageSmsVO messageSmsVO = new MessageSmsVO();
      messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
      messageSmsVO.setAccountId( accountId );
      messageSmsVO.setCorpId( corpId );
      messageSmsVO.setContent( content );
      messageSmsVO.setStatus( "1" );//待发送
      messageSmsVO.setCreateBy( "system" );
      messageSmsVO.setCreateDate( new Date() );

      return messageSmsVO;
   }

   private MessageMailVO getMessageMailVO( final String accountId, final String corpId, final String title, final String content )
   {
      final MessageMailVO messageMailVO = new MessageMailVO();
      messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
      messageMailVO.setAccountId( accountId );
      messageMailVO.setCorpId( corpId );
      messageMailVO.setTitle( title );
      messageMailVO.setContent( content );
      messageMailVO.setContentType( "2" );//
      messageMailVO.setStatus( "1" );//待发送
      messageMailVO.setCreateBy( "system" );
      messageMailVO.setCreateDate( new Date() );

      return messageMailVO;
   }

   public void sendMessageForWorkflow1( final WorkflowActualVO workflowActualVO, final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException
   {
      if ( workflowActualStepsVO.getStatus().equals( "2" ) || workflowActualStepsVO.getStatus().equals( "6" ) )
      {
         final String corpId = KANUtil.filterEmpty( workflowActualVO.getCorpId() );
         final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? ( KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME ) : ( KANConstants.HTTP
               + KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME );

         final String titleZH = workflowActualVO.getNameZH() + "等待审核";
         final String titleEN = workflowActualVO.getNameEN() + "wait for review";
         final StringBuilder contentNoticeZH = new StringBuilder();
         final StringBuilder contentNoticeEN = new StringBuilder();
         final StringBuilder contentZH = new StringBuilder();
         final StringBuilder contentEN = new StringBuilder();

         contentZH.append( workflowActualVO.getNameZH() + "需要您的审核，请尽快审核。<br>" );
         contentEN.append( workflowActualVO.getNameEN() + "need you to review, please review as soon as possible.<br>" );

         //带出已经流经的人员姓名，部门，职位
         final String workflowId = workflowActualStepsVO.getWorkflowId();
         List workflowApprovedChainVOsList = null;
         WorkflowApprovedChainVO workflowApprovedChainVO = null;
         if ( StringUtils.isNotBlank( workflowId ) )
         {
            workflowApprovedChainVOsList = workflowActualStepsDao.getWorkflowApprovedChainByworkflowId( workflowId );
         }
         if ( workflowApprovedChainVOsList != null && workflowApprovedChainVOsList.size() > 0 )
         {
            contentZH.append( "审批历史：" );
            contentEN.append( "Review history：" );
            for ( int i = 0; i < workflowApprovedChainVOsList.size(); i++ )
            {
               workflowApprovedChainVO = ( WorkflowApprovedChainVO ) workflowApprovedChainVOsList.get( i );
               if ( StringUtils.isNotBlank( workflowApprovedChainVO.getApprovedChainZH() ) )
               {
                  contentZH.append( workflowApprovedChainVO.getApprovedChainZH() );
                  contentEN.append( workflowApprovedChainVO.getApprovedChainEN() );
                  if ( i != workflowApprovedChainVOsList.size() - 1 )
                  {
                     contentZH.append( "-->" );
                     contentEN.append( "-->" );
                  }
               }
            }
            contentZH.append( "<br>" );
            contentEN.append( "<br>" );

         }
         // 添加额外邮件信息
         addExtendInfo( contentZH, contentEN, workflowActualVO );

         String objectString = workflowActualStepsVO.getHistoryVO().getFailObject();
         JSONObject jsonObject = null;
         String objectClass = null;

         if ( StringUtils.isNotBlank( objectString ) )
         {
            jsonObject = JSONObject.fromObject( objectString );
         }

         if ( jsonObject != null )
         {
            objectClass = jsonObject.getString( "objectClass" );
         }

         // 劳动合同
         if ( "com.kan.hro.domain.biz.employee.EmployeeContractVO".equals( objectClass ) )
         {
            contentNoticeZH.append( "<p>帐套：" + jsonObject.getString( "orderId" ) + "（" + jsonObject.getString( "orderName" ) + "）</p>" );
            contentNoticeZH.append( "<p>劳动合同：" + jsonObject.getString( "contractId" ) + "（" + workflowActualVO.getNameZH() + "）" + "</p>" );
            contentNoticeZH.append( "--Easy Pay" );

            contentNoticeEN.append( "<p>Order：" + jsonObject.getString( "orderId" ) + "（" + jsonObject.getString( "orderName" ) + "）</p>" );
            contentNoticeEN.append( "<p>Contract：" + jsonObject.getString( "contractId" ) + "（" + workflowActualVO.getNameEN() + "）" + "</p>" );
         }

         // 雇员加班，请假工作流 不加登陆地址
         if ( !KANConstants.ROLE_EMPLOYEE.equals( workflowActualVO.getRole() ) )
         {
            if ( corpId == null )
            {
               contentZH.append( "登录地址： <a href=\"" + domain + "/logon.do\">" + domain + "/logon.do</a> <br>" );
               contentEN.append( "Login address： <a href=\"" + domain + "/logon.do\">" + domain + "/logon.do</a> <br>" );
            }
            else
            {
               contentZH.append( "登录地址为： <a href=\"" + domain + "/logoni.do\">" + domain + "/logoni.do</a> <br>" );
               contentEN.append( "Login address：<a href=\"" + domain + "/logoni.do\">" + domain + "/logoni.do</a> <br>" );
            }

            contentZH.append( "审核地址为(请先登录)：<a href=\"" + domain + "/workflowActualStepsAction.do?proc=list_object&workflowId=" + workflowActualVO.getEncodedId() + "\">" + domain
                  + "/workflowActualStepsAction.do?proc=list_object&workflowId=" + workflowActualVO.getEncodedId() + "</a> <br>" );
            contentEN.append( "Approval (Login required):：<a href=\"" + domain + "/workflowActualStepsAction.do?proc=list_object&workflowId=" + workflowActualVO.getEncodedId()
                  + "\">" + domain + "/workflowActualStepsAction.do?proc=list_object&workflowId=" + workflowActualVO.getEncodedId() + "</a> <br>" );
         }
         else
         {
            if ( StringUtils.isNotBlank( objectClass ) )
            {
               //加班
               if ( "com.kan.hro.domain.biz.attendance.OTHeaderVO".equals( objectClass ) )
               {
                  String employeeNameZH = jsonObject.getString( "employeeNameZH" );
                  String employeeNameEN = jsonObject.getString( "employeeNameEN" );
                  String estimateStartDate = jsonObject.getString( "estimateStartDate" );
                  String estimateEndDate = jsonObject.getString( "estimateEndDate" );
                  String estimateHours = jsonObject.getString( "estimateHours" );
                  String remark = jsonObject.getString( "remark" );

                  contentZH.append( "加班申请信息<br>" );
                  contentEN.append( "Overtime application information<br>" );
                  String employeeId = jsonObject.getString( "employeeId" );
                  if ( StringUtils.isNotBlank( employeeId ) )
                  {
                     EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );
                     contentZH.append( "雇员编号：" + employeeId + "<br>" );
                     contentZH.append( "雇员身份证号：" + employeeVO.getCertificateNumber() + "<br>" );

                     contentEN.append( "Employee number：" + employeeId + "<br>" );
                     contentEN.append( "Employee identity card：" + employeeVO.getCertificateNumber() + "<br>" );
                  }
                  contentZH.append( "雇员姓名：" + employeeNameZH + "<br>" );
                  contentZH.append( "加班时间：" + estimateStartDate + "~" + estimateEndDate + "<br>" );
                  contentZH.append( "加班时长：" + estimateHours + " 小时<br>" );
                  contentZH.append( "备注：" + remark + "<br>" );

                  contentEN.append( "Employee name：" + employeeNameEN + "<br>" );
                  contentEN.append( "Overtime period：" + estimateStartDate + "~" + estimateEndDate + "<br>" );
                  contentEN.append( "Overtime total：" + estimateHours + " hours<br>" );
                  contentEN.append( "Remark：" + remark + "<br>" );
               }
               // 请假
               if ( "com.kan.hro.domain.biz.attendance.LeaveHeaderVO".equals( objectClass ) )
               {
                  String employeeNameZH = jsonObject.getString( "employeeNameZH" );
                  String employeeNameEN = jsonObject.getString( "employeeNameEN" );
                  String estimateStartDate = jsonObject.getString( "estimateStartDate" );
                  String estimateEndDate = jsonObject.getString( "estimateEndDate" );
                  String estimateBenefitHours = jsonObject.getString( "estimateBenefitHours" );
                  String employeeId = jsonObject.getString( "employeeId" );

                  contentZH.append( "请假申请信息<br>" );
                  contentEN.append( "Leave application information<br>" );
                  if ( StringUtils.isNotBlank( employeeId ) )
                  {
                     EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );
                     contentZH.append( "雇员编号：" + employeeId + "<br>" );
                     contentZH.append( "雇员身份证号：" + employeeVO.getCertificateNumber() + "<br>" );

                     contentEN.append( "Employee number：" + employeeId + "<br>" );
                     contentEN.append( "Employee identity card：" + employeeVO.getCertificateNumber() + "<br>" );
                  }
                  contentZH.append( "雇员姓名：" + employeeNameZH + "<br>" );
                  contentZH.append( "请假时间：" + estimateStartDate + "~" + estimateEndDate + "<br>" );
                  contentZH.append( "请假时长：" + estimateBenefitHours + " 小时<br>" );

                  contentEN.append( "Employee name：" + employeeNameEN + "<br>" );
                  contentEN.append( "Overtime period：" + estimateStartDate + "~" + estimateEndDate + "<br>" );
                  contentEN.append( "Overtime total：" + estimateBenefitHours + " 小时<br>" );
               }
               //调薪 
               if ( "com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO".equals( objectClass ) )
               {
                  String employeeNameZH = jsonObject.getString( "employeeNameZH" );
                  String employeeNameEN = jsonObject.getString( "employeeNameEN" );
                  String employeeContractNameZH = jsonObject.getString( "employeeContractNameZH" );
                  String employeeContractNameEN = jsonObject.getString( "employeeContractNameEN" );

                  String oldBase = jsonObject.getString( "oldBase" );
                  String oldStartDate = jsonObject.getString( "oldStartDate" );
                  String oldEndDate = jsonObject.getString( "oldEndDate" );
                  String newBase = jsonObject.getString( "newBase" );
                  String newStartDate = jsonObject.getString( "newStartDate" );
                  String newEndDate = jsonObject.getString( "newEndDate" );

                  String itemNameZH = jsonObject.getString( "itemNameZH" );
                  String itemNameEN = jsonObject.getString( "itemNameEN" );

                  contentZH.append( "调薪信息<br>" );
                  contentEN.append( "Salary adjustment information<br>" );
                  String employeeId = jsonObject.getString( "employeeId" );
                  if ( StringUtils.isNotBlank( employeeId ) )
                  {
                     EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );
                     contentZH.append( "雇员编号：" + employeeId + "<br>" );
                     contentZH.append( "雇员身份证号：" + employeeVO.getCertificateNumber() + "<br>" );

                     contentEN.append( "Employee number：" + employeeId + "<br>" );
                     contentEN.append( "Employee identity card：" + employeeVO.getCertificateNumber() + "<br>" );
                  }
                  contentZH.append( "雇员姓名：" + employeeNameZH + "<br>" );
                  contentZH.append( "合同名称：" + employeeContractNameZH + "<br>" );
                  contentZH.append( "科目：" + itemNameZH + "<br>" );
                  contentZH.append( "调整前信息：" + oldBase + "    " + oldStartDate + "~" + oldEndDate + "<br>" );
                  contentZH.append( "调整后信息：" + newBase + "    " + newStartDate + "~" + newEndDate + "<br>" );

                  contentEN.append( "Employee name：" + employeeNameEN + "<br>" );
                  contentEN.append( "Employee contract name：" + employeeContractNameEN + "<br>" );
                  contentEN.append( "Item name：" + itemNameEN + "<br>" );
                  contentEN.append( "Old informatio：" + oldBase + "    " + oldStartDate + "~" + oldEndDate + "<br>" );
                  contentEN.append( "New informatio：" + newBase + "    " + newStartDate + "~" + newEndDate + "<br>" );
               }
            }
         }

         // 产生一个随机码用来验证邮件审批
         final String randomKey = workflowActualStepsVO.getRandomKey();
         final String stepId = workflowActualStepsVO.getStepId();
         // 3同意 4不同意
         final String status_3 = "3";
         final String status_4 = "4";
         //         final String workflowId = workflowActualStepsVO.getWorkflowId();

         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( workflowActualVO.getAccountId() );

         MessageInfoVO messageInfoVO = null;
         MessageSmsVO messageSmsVO = null;
         MessageMailVO messageMailVO = null;

         // 如果是步骤类型是参考
         if ( workflowActualStepsVO.getStepType().equals( "3" ) )
         {
            messageInfoVO = getMessageInfoVO( workflowActualVO.getAccountId(), corpId, "劳动合同批准通知 - The notice of approval of labor contract", contentNoticeZH.toString()
                  + contentNoticeEN.toString() );
            messageSmsVO = getMessageSmsVO( workflowActualVO.getAccountId(), corpId, contentNoticeZH.toString() + contentNoticeEN.toString() );
            messageMailVO = getMessageMailVO( workflowActualVO.getAccountId(), corpId, "劳动合同批准通知 - The notice of approval of labor contract", contentNoticeZH.toString()
                  + contentNoticeEN.toString() );
         }
         else
         {
            messageInfoVO = getMessageInfoVO( workflowActualVO.getAccountId(), corpId, titleZH + " - " + titleEN, contentZH.toString() + contentEN.toString() );
            messageSmsVO = getMessageSmsVO( workflowActualVO.getAccountId(), corpId, workflowActualVO.getNameZH() + "需要你的审核，请尽快审核！" );
            contentZH.append( "直接审核(无需登录):<br/>" );
            contentZH.append( "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + stepId + "&workflowId=" + workflowId + "&status="
                  + status_3 + "&randomKey=" + randomKey + "\">同意审核</a> <br>" );
            contentZH.append( "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + stepId + "&workflowId=" + workflowId + "&status="
                  + status_4 + "&randomKey=" + randomKey + "\">驳回审核</a> <br>" );

            contentEN.append( "Direct Approval (without login):<br/>" );
            contentEN.append( "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + stepId + "&workflowId=" + workflowId + "&status="
                  + status_3 + "&randomKey=" + randomKey + "\">Approved application</a> <br>" );
            contentEN.append( "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + stepId + "&workflowId=" + workflowId + "&status="
                  + status_4 + "&randomKey=" + randomKey + "\">Reject application</a> <br>" );

            //已经审批的审批链
            messageMailVO = getMessageMailVO( workflowActualVO.getAccountId(), corpId, titleZH + " - " + titleEN, contentZH.toString() + contentEN.toString() );
         }

         // 审批类型 1:内部职位ID || 审批类型5：对接人
         if ( "1".equals( workflowActualStepsVO.getAuditType() ) || "5".equals( workflowActualStepsVO.getAuditType() ) )
         {
            final List< StaffDTO > staffDTOs = accountConstants.getValidStaffDTOsByPositionId( workflowActualStepsVO.getAuditTargetId() );
            if ( staffDTOs != null && staffDTOs.size() > 0 )
            {
               for ( StaffDTO staffDTO : staffDTOs )
               {
                  if ( staffDTO.getUserVO() == null || staffDTO.getStaffVO() == null )
                     continue;

                  // 发送站内消息
                  if ( "1".equals( workflowActualStepsVO.getSendInfo() ) )
                  {
                     messageInfoVO.setReception( staffDTO.getUserVO().getUserId() );
                     this.getMessageInfoDao().insertMessageInfo( messageInfoVO );
                  }

                  // 发送短信消息
                  if ( "1".equals( workflowActualStepsVO.getSendSMS() ) )
                  {
                     messageSmsVO.setReception( staffDTO.getStaffVO().getBizMobile() );
                     this.getMessageSmsDao().insertMessageSms( messageSmsVO );
                  }

                  // 发送邮件消息
                  if ( "1".equals( workflowActualStepsVO.getSendEmail() ) )
                  {
                     String tempEmail = staffDTO.getStaffVO().getBizEmail();
                     if ( KANUtil.filterEmpty( tempEmail ) == null )
                     {
                        tempEmail = staffDTO.getStaffVO().getPersonalEmail();
                     }
                     if ( KANUtil.filterEmpty( tempEmail ) != null )
                     {
                        messageMailVO.setReception( staffDTO.getStaffVO().getBizEmail() );
                        this.getMessageMailDao().insertMessageMail( messageMailVO );
                     }
                  }
               }
            }
         }
         // 审批类型 2:外部职位ID
         else if ( "2".equals( workflowActualStepsVO.getAuditType() ) )
         {
            final List< Object > employeeVOs = this.getEmployeeDao().getEmployeeVOsByPositionId( workflowActualVO.getPositionId() );
            if ( employeeVOs != null && employeeVOs.size() > 0 )
            {
               for ( Object o : employeeVOs )
               {
                  final EmployeeVO employeeVO = ( EmployeeVO ) o;
                  String mobile = KANUtil.filterEmpty( employeeVO.getMobile1() ) == null ? employeeVO.getMobile2() : employeeVO.getMobile1();
                  String email = KANUtil.filterEmpty( employeeVO.getEmail1() ) == null ? employeeVO.getEmail2() : employeeVO.getEmail1();

                  // 发送短信消息
                  if ( "1".equals( workflowActualStepsVO.getSendSMS() ) && KANUtil.filterEmpty( mobile ) != null )
                  {
                     messageSmsVO.setReception( mobile );
                     this.getMessageSmsDao().insertMessageSms( messageSmsVO );
                  }

                  // 发送邮件消息
                  if ( "1".equals( workflowActualStepsVO.getSendEmail() ) && KANUtil.filterEmpty( email ) != null )
                  {
                     messageMailVO.setReception( email );
                     this.getMessageMailDao().insertMessageMail( messageMailVO );
                  }
               }
            }
         }
         // 审批类型3:直线经理（客户）
         else if ( "3".equals( workflowActualStepsVO.getAuditType() ) )
         {
            final ClientContactVO clientContact = clientContactDao.getClientContactVOByClientContactId( workflowActualStepsVO.getAuditTargetId() );
            if ( clientContact != null )
            {
               String mobile = clientContact.getBizMobile();
               if ( mobile == null || mobile.isEmpty() )
               {
                  mobile = clientContact.getPersonalMobile();
               }
               String email = clientContact.getBizEmail();
               if ( email == null || email.isEmpty() )
               {
                  email = clientContact.getPersonalEmail();
               }
               if ( "1".equals( workflowActualStepsVO.getSendSMS() ) )
               {//发送短信消息
                  messageSmsVO.setReception( mobile );
                  messageSmsDao.insertMessageSms( messageSmsVO );
               }
               if ( "1".equals( workflowActualStepsVO.getSendEmail() ) )
               {//发送邮件消息
                  messageMailVO.setReception( email );
                  messageMailDao.insertMessageMail( messageMailVO );
               }
            }
         }
         // 审批类型4:内部员工ID
         else if ( "4".equals( workflowActualStepsVO.getAuditType() ) )
         {
            final StaffDTO staffDTO = KANConstants.getKANAccountConstants( workflowActualVO.getAccountId() ).getStaffDTOByStaffId( workflowActualStepsVO.getAuditTargetId() );
            if ( staffDTO != null && staffDTO.getStaffVO() != null && staffDTO.getUserVO() != null )
            {
               String mobile = staffDTO.getStaffVO().getBizMobile();
               if ( mobile == null || mobile.isEmpty() )
               {
                  mobile = staffDTO.getStaffVO().getPersonalMobile();
               }
               String email = staffDTO.getStaffVO().getBizEmail();
               if ( email == null || email.isEmpty() )
               {
                  email = staffDTO.getStaffVO().getPersonalEmail();
               }
               // 发送短信消息
               if ( "1".equals( workflowActualStepsVO.getSendSMS() ) )
               {
                  messageSmsVO.setReception( mobile );
                  messageSmsDao.insertMessageSms( messageSmsVO );
               }
               // 发送邮件消息
               if ( "1".equals( workflowActualStepsVO.getSendEmail() ) )
               {
                  messageMailVO.setReception( email );
                  messageMailDao.insertMessageMail( messageMailVO );
               }
            }
         }
      }
   }

   // 添加额外邮件信息
   private void addExtendInfo( final StringBuilder contentZH, final StringBuilder contentEN, final WorkflowActualVO workflowActualVO ) throws KANException
   {
      String objectClassName = null;
      final HistoryVO historyVO = this.getHistoryDao().getObjectByWorkflowId( workflowActualVO.getWorkflowId() );
      if ( historyVO != null )
      {
         objectClassName = historyVO.getObjectClass();
      }

      // 如果是合同审批，则添加以下2条信息
      if ( KANUtil.filterEmpty( workflowActualVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeeContractVO".equals( objectClassName ) )
      {
         final EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( workflowActualVO.getObjectId() );
         if ( employeeContractVO != null )
         {
            contentZH.append( "上级部门：" + employeeContractVO.getParentBranchName() + "<br>" );
            contentZH.append( "直线经理：" + employeeContractVO.getLineManager() + "<br>" );

            contentEN.append( "The superior department：" + employeeContractVO.getParentBranchName() + "<br>" );
            contentEN.append( "The line manager：" + employeeContractVO.getLineManager() + "<br>" );

         }
      }
      // 如果是请假或者是加班，带出请假人，加班，和时间com.kan.hro.domain.biz.attendance.LeaveHeaderVO
      if ( KANUtil.filterEmpty( workflowActualVO.getObjectId() ) != null && "com.kan.hro.domain.biz.attendance.LeaveHeaderVO".equals( objectClassName ) )
      {
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderDao.getLeaveHeaderVOByLeaveHeaderId( workflowActualVO.getObjectId() );
         if ( leaveHeaderVO != null )
         {
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( leaveHeaderVO.getEmployeeId() );
            final ItemVO itemVO = KANConstants.getKANAccountConstants( leaveHeaderVO.getAccountId() ).getItemVOByItemId( leaveHeaderVO.getItemId() );
            contentZH.append( "请假申请人：" + employeeVO.getNameZH() + "<br>" );
            contentZH.append( "请假类别：" + itemVO.getNameZH() + "<br>" );
            contentZH.append( "请假时间：" + leaveHeaderVO.getEstimateStartDate() + " ~ " + leaveHeaderVO.getEstimateEndDate() + "(合计: " + leaveHeaderVO.getUseHours() + " 小时)" + "<br>" );

            contentEN.append( "Applicant：" + employeeVO.getNameEN() + "<br>" );
            contentEN.append( "Leave Type：" + itemVO.getNameEN() + "<br>" );
            contentEN.append( "Period：" + leaveHeaderVO.getEstimateStartDate() + " ~ " + leaveHeaderVO.getEstimateEndDate() + "(Total: " + leaveHeaderVO.getUseHours() + " Hours)"
                  + "<br>" );
         }
      }
      if ( KANUtil.filterEmpty( workflowActualVO.getObjectId() ) != null && "com.kan.hro.domain.biz.attendance.OTHeaderVO".equals( objectClassName ) )
      {
         final OTHeaderVO otHeaderVO = otHeaderDao.getOTHeaderVOByOTHeaderId( workflowActualVO.getObjectId() );
         if ( otHeaderVO != null )
         {
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( otHeaderVO.getEmployeeId() );
            contentZH.append( "加班申请人：" + employeeVO.getNameZH() + "<br>" );
            contentZH.append( "加班时间：" + otHeaderVO.getEstimateStartDate() + " ~ " + otHeaderVO.getEstimateEndDate() + "(合计: " + otHeaderVO.getEstimateHours() + " 小时)" + "<br>" );

            contentEN.append( "Applicant：" + employeeVO.getNameZH() + "<br>" );
            contentEN.append( "Period：" + otHeaderVO.getEstimateStartDate() + " ~ " + otHeaderVO.getEstimateEndDate() + "(Total: " + otHeaderVO.getEstimateHours() + " Hours)"
                  + "<br>" );

         }
      }

   }

   public HistoryDao getHistoryDao()
   {
      return historyDao;
   }

   public void setHistoryDao( HistoryDao historyDao )
   {
      this.historyDao = historyDao;
   }

   public WorkflowActualStepsDao getWorkflowActualStepsDao()
   {
      return workflowActualStepsDao;
   }

   public void setWorkflowActualStepsDao( WorkflowActualStepsDao workflowActualStepsDao )
   {
      this.workflowActualStepsDao = workflowActualStepsDao;
   }

   public MessageTemplateDao getMessageTemplateDao()
   {
      return messageTemplateDao;
   }

   public void setMessageTemplateDao( MessageTemplateDao messageTemplateDao )
   {
      this.messageTemplateDao = messageTemplateDao;
   }

   public EmployeeSalaryAdjustmentDao getEmployeeSalaryAdjustmentDao()
   {
      return employeeSalaryAdjustmentDao;
   }

   public void setEmployeeSalaryAdjustmentDao( EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao )
   {
      this.employeeSalaryAdjustmentDao = employeeSalaryAdjustmentDao;
   }

   public EmployeePositionChangeDao getEmployeePositionChangeDao()
   {
      return employeePositionChangeDao;
   }

   public void setEmployeePositionChangeDao( EmployeePositionChangeDao employeePositionChangeDao )
   {
      this.employeePositionChangeDao = employeePositionChangeDao;
   }

   public EmployeeContractSalaryDao getEmployeeContractSalaryDao()
   {
      return employeeContractSalaryDao;
   }

   public void setEmployeeContractSalaryDao( EmployeeContractSalaryDao employeeContractSalaryDao )
   {
      this.employeeContractSalaryDao = employeeContractSalaryDao;
   }

   //---------------------------------分割线------------------------------------------------------------------//

   // 发送邮件，含模板
   public void sendMessageForWorkflow( final WorkflowActualVO workflowActualVO, final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException
   {
      final String accountId = KANUtil.filterEmpty( workflowActualVO.getAccountId() );
      final String corpId = KANUtil.filterEmpty( workflowActualVO.getCorpId() );
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );

      if ( workflowActualStepsVO.getStatus().equals( "2" ) || workflowActualStepsVO.getStatus().equals( "5" ) || workflowActualStepsVO.getStatus().equals( "6" ) )
      {
         // 初始化消息标题
         final String titleZH = workflowActualVO.getNameZH() + "需要您的审核";

         // 邮件、短信、站内信息内容
         final String mailContent = getMessageContent( accountId, corpId, workflowActualStepsVO.getEmailTemplateType(), workflowActualVO, workflowActualStepsVO, "1", false );
         final String smsContent = getMessageContent( accountId, corpId, workflowActualStepsVO.getSmsTemplateType(), workflowActualVO, workflowActualStepsVO, "2", false );
         final String infoContent = getMessageContent( accountId, corpId, workflowActualStepsVO.getInfoTemplateType(), workflowActualVO, workflowActualStepsVO, "3", false );

         // 需要添加附件
         final MessageMailVO messageMailVO = getMessageMailVO1( accountId, corpId, titleZH, mailContent, workflowActualVO.getWorkflowId() );
         final MessageSmsVO messageSmsVO = getMessageSmsVO1( accountId, corpId, smsContent );
         final MessageInfoVO messageInfoVO = getMessageInfoVO1( accountId, corpId, titleZH, infoContent );

         insertMessageVO( accountConstants, workflowActualVO, workflowActualStepsVO, messageMailVO, messageInfoVO, messageSmsVO );
      }
   }

   private String getMessageContent( final String accountId, final String corpId, final String templateId, final WorkflowActualVO workflowActualVO,
         final WorkflowActualStepsVO workflowActualStepsVO, final String flag, final boolean toCreator ) throws KANException
   {
      return getMessageContent( accountId, corpId, templateId, workflowActualVO, workflowActualStepsVO, flag, toCreator, "", "" );
   }

   // 获取消息的内容
   // Added by siuvan 2015-03-24
   private String getMessageContent( final String accountId, final String corpId, final String templateId, final WorkflowActualVO workflowActualVO,
         final WorkflowActualStepsVO workflowActualStepsVO, final String flag, final boolean toCreator, final String remarkZH, final String remarkEN ) throws KANException
   {
      // 初始化返回值
      final StringBuilder content = new StringBuilder();

      final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? ( KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME ) : ( KANConstants.HTTP
            + KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME );

      // 主题
      final String messageSubjectZH = "<P>" + workflowActualVO.getNameZH() + "需要您的审核，请尽快审核。</P>";
      final String messageSubjectEN = "<P>" + workflowActualVO.getNameEN() + " wait for approval, please operate as soon as possible.</P>";
      final String logonAddress = "<a href=\"" + domain + "/logoni.do\">" + domain + "/logoni.do</a>";
      final String approvalZH = "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + workflowActualStepsVO.getStepId() + "&workflowId="
            + workflowActualVO.getWorkflowId() + "&" + USERID_TODO + "&status=3&randomKey=" + workflowActualStepsVO.getRandomKey() + "\">同意审核</a>&nbsp;&nbsp;";
      final String approvalEN = "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + workflowActualStepsVO.getStepId() + "&workflowId="
            + workflowActualVO.getWorkflowId() + "&" + USERID_TODO + "&status=3&randomKey=" + workflowActualStepsVO.getRandomKey() + "\">Approve</a>&nbsp;&nbsp;";
      final String rejectZH = "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + workflowActualStepsVO.getStepId() + "&workflowId="
            + workflowActualVO.getWorkflowId() + "&" + USERID_TODO + "&status=4&randomKey=" + workflowActualStepsVO.getRandomKey() + "\">驳回审核</a>";
      final String rejectEN = "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + workflowActualStepsVO.getStepId() + "&workflowId="
            + workflowActualVO.getWorkflowId() + "&" + USERID_TODO + "&status=4&randomKey=" + workflowActualStepsVO.getRandomKey() + "\">Reject</a>";
      final String auditHisroryZH = generateApprovalList( workflowActualVO.getWorkflowId(), "zh" );
      final String auditHisroryEN = generateApprovalList( workflowActualVO.getWorkflowId(), "en" );

      final Map< String, String > stringMap = new HashMap< String, String >();
      stringMap.put( "messageSubjectZH", messageSubjectZH );
      stringMap.put( "messageSubjectEN", messageSubjectEN );
      stringMap.put( "logonAddress", logonAddress );
      stringMap.put( "approvalZH", approvalZH );
      stringMap.put( "approvalEN", approvalEN );
      stringMap.put( "rejectZH", rejectZH );
      stringMap.put( "rejectEN", rejectEN );
      stringMap.put( "auditHisroryZH", auditHisroryZH );
      stringMap.put( "auditHisroryEN", auditHisroryEN );

      // 获取HistoryVO
      final HistoryVO historyVO = this.getHistoryDao().getObjectByWorkflowId( workflowActualVO.getWorkflowId() );

      // 初始化消息模板
      MessageTemplateVO messageTemplateVO = null;
      // 初始化对象限定名
      String objectClassName = "";

      // 工作流需要消息模板
      if ( KANUtil.filterEmpty( templateId, "0" ) != null )
      {
         messageTemplateVO = this.getMessageTemplateDao().getMessageTemplateVOByTemplateId( templateId );
      }

      // 工作流存在消息模板
      if ( messageTemplateVO != null && historyVO != null )
      {
         objectClassName = historyVO.getObjectClass();
         // 获取系统常量
         final List< ConstantVO > constantVOs = KANConstants.getKANAccountConstants( accountId ).getConstantVOsByScopeType( "1" );
         // 用以存放VO对象，替换模板参数用到
         final List< Object > objects = new ArrayList< Object >();

         objects.add( stringMap );

         // 如果审核对象是EmployeeContractVO
         if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeeContractVO".equals( objectClassName ) )
         {
            // 获取EmployeeContractVO
            final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( historyVO.getObjectId() );
            if ( employeeContractVO != null )
            {
               final JSONObject passObject = JSONObject.fromObject( historyVO.getPassObject() );
               if ( passObject != null && passObject.get( "resignDate" ) != null )
               {
                  employeeContractVO.setResignDate( passObject.get( "resignDate" ).toString() );
               }
               objects.add( employeeContractVO );
            }
         }
         // 如果审核对象是LeaveHeaderVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.attendance.LeaveHeaderVO".equals( objectClassName ) )
         {
            // 获取LeaveHeaderVO
            final LeaveHeaderVO leaveHeaderVO = this.getLeaveHeaderDao().getLeaveHeaderVOByLeaveHeaderId( historyVO.getObjectId() );
            if ( leaveHeaderVO != null )
            {
               if ( KANUtil.filterEmpty( leaveHeaderVO.getRemark2() ) != null || KANUtil.filterEmpty( leaveHeaderVO.getRemark3() ) != null )
               {
                  leaveHeaderVO.setActualStartDate( leaveHeaderVO.getRemark2() );
                  leaveHeaderVO.setActualEndDate( leaveHeaderVO.getRemark3() );
               }
               objects.add( leaveHeaderVO );
            }
         }
         // 如果审核对象是OTHeaderVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.attendance.OTHeaderVO".equals( objectClassName ) )
         {
            // 获取OTHeaderVO
            final OTHeaderVO otHeaderVO = this.getOtHeaderDao().getOTHeaderVOByOTHeaderId( historyVO.getObjectId() );
            if ( otHeaderVO != null )
            {
               if ( KANUtil.filterEmpty( otHeaderVO.getActualStartDate() ) != null || KANUtil.filterEmpty( otHeaderVO.getActualEndDate() ) != null )
               {
                  otHeaderVO.setEstimateStartDate( otHeaderVO.getActualStartDate() );
                  otHeaderVO.setEstimateEndDate( otHeaderVO.getActualEndDate() );
               }
               else
               {
                  otHeaderVO.setEstimateStartDate( "(预) " + otHeaderVO.getEstimateStartDate() );
                  otHeaderVO.setEstimateEndDate( "(预) " + otHeaderVO.getEstimateEndDate() );
               }
               objects.add( otHeaderVO );
            }
         }
         // 如果审核对象是EmployeeSalaryAdjustmentVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO".equals( objectClassName ) )
         {
            final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = this.getEmployeeSalaryAdjustmentDao().getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( historyVO.getObjectId() );
            if ( employeeSalaryAdjustmentVO != null )
            {
               objects.add( employeeSalaryAdjustmentVO );
            }
         }
         // 如果审核对象是EmployeeSalaryAdjustmentVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeePositionChangeVO".equals( objectClassName ) )
         {
            final EmployeePositionChangeVO employeePositionChangeVO = this.getEmployeePositionChangeDao().getEmployeePositionChangeVOByPositionChangeId( historyVO.getObjectId() );
            if ( employeePositionChangeVO != null )
            {
               objects.add( employeePositionChangeVO );
            }
         }
         // 如果审批对象是EmployeeContractSalaryVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO".equals( objectClassName ) )
         {
            final EmployeeContractSalaryVO employeeContractSalaryVO = this.getEmployeeContractSalaryDao().getEmployeeContractSalaryVOByEmployeeSalaryId( historyVO.getObjectId() );
            if ( employeeContractSalaryVO != null )
            {
               objects.add( employeeContractSalaryVO );
            }
         }

         // 如果消息类型是邮件
         if ( "1".equals( flag ) )
         {
            // 如果是步骤类型是参考
            if ( workflowActualStepsVO.getStepType().equals( "3" ) )
            {
               // 参考步骤不需要审批链接
               stringMap.remove( "logonAddress" );
               stringMap.remove( "approvalZH" );
               stringMap.remove( "approvalEN" );
               stringMap.remove( "rejectZH" );
               stringMap.remove( "rejectEN" );
               content.append( MatchUtil.generateMessageContent( messageTemplateVO.getContent(), constantVOs, objects, false ) );
            }
            else
            {

               if ( toCreator )
               {
                  // 参考步骤不需要审批链接
                  stringMap.remove( "messageSubjectZH" );
                  stringMap.remove( "messageSubjectEN" );
                  stringMap.remove( "logonAddress" );
                  stringMap.remove( "approvalZH" );
                  stringMap.remove( "approvalEN" );
                  stringMap.remove( "rejectZH" );
                  stringMap.remove( "rejectEN" );
                  stringMap.put( "remarkZH", remarkZH );
                  stringMap.put( "remarkEN", remarkEN );
                  content.append( MatchUtil.generateMessageContent( messageTemplateVO.getContent(), constantVOs, objects, false ) );
               }
               else
               {
                  content.append( MatchUtil.generateMessageContent( messageTemplateVO.getContent(), constantVOs, objects, false ) );
               }
            }
         }
         else if ( "3".equals( flag ) )
         {
            stringMap.remove( "logonAddress" );
            stringMap.remove( "approvalZH" );
            stringMap.remove( "approvalEN" );
            stringMap.remove( "rejectZH" );
            stringMap.remove( "rejectEN" );
            content.append( MatchUtil.generateMessageContent( messageTemplateVO.getContent(), constantVOs, objects, false ) );
         }
         else if ( "2".equals( flag ) )
         {
            stringMap.remove( "messageSubjectZH" );
            stringMap.remove( "messageSubjectEN" );
            stringMap.remove( "approvalZH" );
            stringMap.remove( "approvalEN" );
            stringMap.remove( "rejectZH" );
            stringMap.remove( "rejectEN" );
            content.append( MatchUtil.generateMessageContent( messageTemplateVO.getContent(), constantVOs, objects, true ) );
         }
      }

      return content.toString();
   }

   // 生成审核历史
   private String generateApprovalList( final String workflowId, final String language ) throws KANException
   {
      final StringBuilder rs = new StringBuilder();

      final WorkflowActualStepsVO workflowActualStepsVO = new WorkflowActualStepsVO();
      workflowActualStepsVO.setWorkflowId( workflowId );
      workflowActualStepsVO.setStatus( "3,5" );
      workflowActualStepsVO.setSortColumn( "stepIndex" );
      workflowActualStepsVO.setSortOrder( "asc" );

      final List< Object > approvedList = this.getWorkflowActualStepsDao().getWorkflowActualStepsVOsByCondition( workflowActualStepsVO );
      if ( approvedList != null && approvedList.size() > 0 )
      {
         if ( "zh".equalsIgnoreCase( language ) )
         {
            rs.append( "<br><span>审批历史</span>" );
            rs.append( "<table>" );
            for ( int i = 0; i < approvedList.size(); i++ )
            {
               final WorkflowActualStepsVO tempWorkflowActualStepsVO = ( WorkflowActualStepsVO ) approvedList.get( i );
               rs.append( "<tr>" );
               rs.append( "<td>" + tempWorkflowActualStepsVO.getPositionTitleZH() + "</td>" );
               if ( "3".equals( tempWorkflowActualStepsVO.getStatus() ) )
               {
                  rs.append( "<td>" + KANUtil.formatDate( tempWorkflowActualStepsVO.getHandleDate(), "yyyy-MM-dd HH:mm:ss", true ) + "</td>" );
                  rs.append( "<td>同意</td>" );
               }
               else
               {
                  rs.append( "<td>" + KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss", true ) + "</td>" );
                  rs.append( "<td>已通知</td>" );
               }
               rs.append( "</tr>" );
            }
            rs.append( "</table>" );
         }
         else
         {
            rs.append( "<br><span>History</span><br>" );
            rs.append( "<table>" );
            for ( int i = 0; i < approvedList.size(); i++ )
            {
               final WorkflowActualStepsVO tempWorkflowActualStepsVO = ( WorkflowActualStepsVO ) approvedList.get( i );
               rs.append( "<tr>" );
               rs.append( "<td>" + tempWorkflowActualStepsVO.getPositionTitleEN() + "</td>" );
               if ( "3".equals( tempWorkflowActualStepsVO.getStatus() ) )
               {
                  rs.append( "<td>" + KANUtil.formatDate( tempWorkflowActualStepsVO.getHandleDate(), "yyyy-MM-dd HH:mm:ss", true ) + "</td>" );
                  rs.append( "<td>Approved</td>" );
               }
               else
               {
                  rs.append( "<td>" + KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss", true ) + "</td>" );
                  rs.append( "<td>Has informed</td>" );
               }
               rs.append( "</tr>" );
            }
            rs.append( "</table>" );
         }

      }

      return rs.toString();
   }

   // 构建MessageMailVO
   // Added by siuvan 2015-03-24
   private MessageMailVO getMessageMailVO1( final String accountId, final String corpId, final String title, final String content, String workflowId )
   {
      final MessageMailVO messageMailVO = new MessageMailVO();
      messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
      messageMailVO.setAccountId( accountId );
      messageMailVO.setCorpId( corpId );
      messageMailVO.setTitle( title );
      messageMailVO.setContent( content );
      messageMailVO.setContentType( "2" );
      messageMailVO.setStatus( "1" );
      messageMailVO.setCreateBy( "system" );
      messageMailVO.setCreateDate( new Date() );
      messageMailVO.setModifyBy( "system" );
      messageMailVO.setModifyDate( new Date() );

      final HistoryVO historyVO = this.getHistoryDao().getObjectByWorkflowId( workflowId );
      if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.attendance.LeaveHeaderVO".equals( historyVO.getObjectClass() ) )
      {
         // 获取LeaveHeaderVO
         try
         {
            final LeaveHeaderVO leaveHeaderVO = this.getLeaveHeaderDao().getLeaveHeaderVOByLeaveHeaderId( historyVO.getObjectId() );
            if ( leaveHeaderVO != null )
            {
               String attachment = leaveHeaderVO.getAttachment();
               if ( StringUtils.isNotBlank( attachment ) )
               {
                  String[] attachments = KANUtil.jasonArrayToStringArray( attachment );
                  if ( attachments != null && attachments.length > 0 )
                  {
                     String[] targetAttachmentArray = new String[ attachments.length ];
                     for ( int i = 0; i < attachments.length; i++ )
                     {
                        String tempAttr = attachments[ i ];
                        targetAttachmentArray[ i ] = tempAttr.split( "##" )[ 0 ];
                     }
                     messageMailVO.setRemark1( KANUtil.toJasonArray( targetAttachmentArray ) );
                  }
               }
            }
         }
         catch ( Exception e )
         {
            e.printStackTrace();
            System.out.println( "邮件附件获取失败" );
         }
      }

      return messageMailVO;
   }

   // 构建MessageInfoVO
   // Added by siuvan 2015-03-24
   private MessageInfoVO getMessageInfoVO1( final String accountId, final String corpId, final String title, final String content )
   {
      // 初始化MessageInfoVO
      final MessageInfoVO messageInfoVO = new MessageInfoVO();
      messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
      messageInfoVO.setAccountId( accountId );
      messageInfoVO.setCorpId( corpId );
      messageInfoVO.setTitle( title );
      messageInfoVO.setContent( content.toString() );
      messageInfoVO.setStatus( "2" );//[针对发送者] 2，发布
      messageInfoVO.setReceptionStatus( "2" );//[针对接收者] 2.未读
      messageInfoVO.setCreateBy( "system" );
      messageInfoVO.setCreateDate( new Date() );

      return messageInfoVO;
   }

   // 构建MessageSmsVO
   // Added by siuvan 2015-03-24
   private MessageSmsVO getMessageSmsVO1( final String accountId, final String corpId, final String content )
   {
      // 初始化MessageSmsVO
      final MessageSmsVO messageSmsVO = new MessageSmsVO();
      messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
      messageSmsVO.setAccountId( accountId );
      messageSmsVO.setCorpId( corpId );
      messageSmsVO.setContent( KANUtil.filterEmpty( content ) == null ? null : content.replaceAll( "<p>", "" ).replaceAll( "</p>", "" ) );
      messageSmsVO.setStatus( "1" );//待发送
      messageSmsVO.setCreateBy( "system" );
      messageSmsVO.setCreateDate( new Date() );
      messageSmsVO.setModifyBy( "system" );
      messageSmsVO.setModifyDate( new Date() );

      return messageSmsVO;
   }

   // 将消息数据插入到系统数据库
   // Added by siuvan 2015-03-24
   private void insertMessageVO( final KANAccountConstants accountConstants, final WorkflowActualVO workflowActualVO, final WorkflowActualStepsVO workflowActualStepsVO,
         final MessageMailVO messageMailVO, final MessageInfoVO messageInfoVO, final MessageSmsVO messageSmsVO ) throws KANException
   {
      // 审批类型 1:内部职位ID || 审批类型5：对接人
      if ( "1".equals( workflowActualStepsVO.getAuditType() ) || "5".equals( workflowActualStepsVO.getAuditType() ) )
      {
         final List< StaffDTO > staffDTOs = accountConstants.getValidStaffDTOsByPositionId( workflowActualStepsVO.getAuditTargetId() );
         if ( staffDTOs != null && staffDTOs.size() > 0 )
         {
            for ( StaffDTO staffDTO : staffDTOs )
            {
               if ( staffDTO.getUserVO() == null || staffDTO.getStaffVO() == null )
                  continue;

               // 发送站内消息
               if ( "1".equals( workflowActualStepsVO.getSendInfo() ) && messageInfoVO != null )
               {
                  messageInfoVO.setReception( staffDTO.getUserVO().getUserId() );
                  this.getMessageInfoDao().insertMessageInfo( messageInfoVO );
               }

               // 发送短信消息
               if ( "1".equals( workflowActualStepsVO.getSendSMS() ) && messageSmsVO != null )
               {
                  messageSmsVO.setReception( staffDTO.getStaffVO().getBizMobile() );
                  this.getMessageSmsDao().insertMessageSms( messageSmsVO );
               }

               // 发送邮件消息
               if ( "1".equals( workflowActualStepsVO.getSendEmail() ) && messageMailVO != null )
               {
                  String tempEmail = staffDTO.getStaffVO().getBizEmail();
                  if ( KANUtil.filterEmpty( tempEmail ) == null )
                  {
                     tempEmail = staffDTO.getStaffVO().getPersonalEmail();
                  }
                  if ( KANUtil.filterEmpty( tempEmail ) != null )
                  {
                     messageMailVO.setReception( staffDTO.getStaffVO().getBizEmail() );
                     if ( KANUtil.filterEmpty( messageMailVO.getContent() ) != null && messageMailVO.getContent().contains( USERID_TODO ) )
                     {
                        messageMailVO.setContent( messageMailVO.getContent().replace( USERID_TODO, "userId=" + staffDTO.getUserVO().getUserId() ) );
                     }
                     this.getMessageMailDao().insertMessageMail( messageMailVO );
                  }
               }
            }
         }
      }
      // 审批类型 2:外部职位ID
      else if ( "2".equals( workflowActualStepsVO.getAuditType() ) )
      {
         final List< Object > employeeVOs = this.getEmployeeDao().getEmployeeVOsByPositionId( workflowActualVO.getPositionId() );
         if ( employeeVOs != null && employeeVOs.size() > 0 )
         {
            for ( Object o : employeeVOs )
            {
               final EmployeeVO employeeVO = ( EmployeeVO ) o;
               String mobile = KANUtil.filterEmpty( employeeVO.getMobile1() ) == null ? employeeVO.getMobile2() : employeeVO.getMobile1();
               String email = KANUtil.filterEmpty( employeeVO.getEmail1() ) == null ? employeeVO.getEmail2() : employeeVO.getEmail1();

               // 发送短信消息
               if ( "1".equals( workflowActualStepsVO.getSendSMS() ) && KANUtil.filterEmpty( mobile ) != null && messageSmsVO != null )
               {
                  messageSmsVO.setReception( mobile );
                  this.getMessageSmsDao().insertMessageSms( messageSmsVO );
               }

               // 发送邮件消息
               if ( "1".equals( workflowActualStepsVO.getSendEmail() ) && KANUtil.filterEmpty( email ) != null && messageMailVO != null )
               {
                  messageMailVO.setReception( email );
                  this.getMessageMailDao().insertMessageMail( messageMailVO );
               }
            }
         }
      }
      // 审批类型3:直线经理（客户）
      else if ( "3".equals( workflowActualStepsVO.getAuditType() ) )
      {
         final ClientContactVO clientContact = clientContactDao.getClientContactVOByClientContactId( workflowActualStepsVO.getAuditTargetId() );
         if ( clientContact != null )
         {
            String mobile = clientContact.getBizMobile();
            if ( mobile == null || mobile.isEmpty() )
            {
               mobile = clientContact.getPersonalMobile();
            }
            String email = clientContact.getBizEmail();
            if ( email == null || email.isEmpty() )
            {
               email = clientContact.getPersonalEmail();
            }
            if ( "1".equals( workflowActualStepsVO.getSendSMS() ) && messageSmsVO != null )
            {//发送短信消息
               messageSmsVO.setReception( mobile );
               messageSmsDao.insertMessageSms( messageSmsVO );
            }
            if ( "1".equals( workflowActualStepsVO.getSendEmail() ) && messageMailVO != null )
            {//发送邮件消息
               messageMailVO.setReception( email );
               messageMailDao.insertMessageMail( messageMailVO );
            }
         }
      }
      // 审批类型4:内部员工ID
      else if ( "4".equals( workflowActualStepsVO.getAuditType() ) )
      {
         final StaffDTO staffDTO = accountConstants.getStaffDTOByStaffId( workflowActualStepsVO.getAuditTargetId() );
         if ( staffDTO != null && staffDTO.getStaffVO() != null && staffDTO.getUserVO() != null )
         {
            String mobile = staffDTO.getStaffVO().getBizMobile();
            if ( mobile == null || mobile.isEmpty() )
            {
               mobile = staffDTO.getStaffVO().getPersonalMobile();
            }
            String email = staffDTO.getStaffVO().getBizEmail();
            if ( email == null || email.isEmpty() )
            {
               email = staffDTO.getStaffVO().getPersonalEmail();
            }

            // 发送站内消息
            if ( "1".equals( workflowActualStepsVO.getSendInfo() ) && messageInfoVO != null )
            {
               messageInfoVO.setReception( staffDTO.getUserVO().getUserId() );
               this.getMessageInfoDao().insertMessageInfo( messageInfoVO );
            }
            // 发送短信消息
            if ( "1".equals( workflowActualStepsVO.getSendSMS() ) && messageSmsVO != null )
            {
               messageSmsVO.setReception( mobile );
               messageSmsDao.insertMessageSms( messageSmsVO );
            }
            // 发送邮件消息
            if ( "1".equals( workflowActualStepsVO.getSendEmail() ) && messageMailVO != null )
            {
               messageMailVO.setReception( email );
               if ( KANUtil.filterEmpty( messageMailVO.getContent() ) != null && messageMailVO.getContent().contains( USERID_TODO ) )
               {
                  messageMailVO.setContent( messageMailVO.getContent().replace( USERID_TODO, "userId=" + staffDTO.getUserVO().getUserId() ) );
               }
               messageMailDao.insertMessageMail( messageMailVO );
            }
         }
      }
   }

}
