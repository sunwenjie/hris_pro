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

   // ����Ϣ��Ӧ��dao
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

   // ע��MessageTemplateDao
   private MessageTemplateDao messageTemplateDao;

   private KANSendMessageUtil()
   {
   }

   // �������˽�л�
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

   // �����������󣬸��ϱ��˷���Ϣ
   //* Add by siuvan.xia @2014-07-24  *//
   public void sendMessageToCreateBy( final WorkflowDefineVO workflowDefineVO, final WorkflowActualVO workflowActualVO, final WorkflowActualStepsVO workflowActualStepsVO,
         final String isPass ) throws KANException
   {
      final String accountId = workflowActualVO.getAccountId();
      final String corpId = workflowActualVO.getCorpId();

      // ��Ϣ/�ʼ�����
      final StringBuilder subject = new StringBuilder( workflowActualVO.getNameZH() + "�ѱ�" + ( isPass.equals( "1" ) ? "��׼" : "�˻�" ) );

      final StringBuilder remarkZH = new StringBuilder();
      final StringBuilder remarkEN = new StringBuilder();

      if ( isPass.equals( "2" ) )
      {
         if ( workflowActualStepsVO != null && KANUtil.filterEmpty( workflowActualStepsVO.getDescription() ) != null )
         {
            remarkZH.append( "<p>�˻�ԭ��" + workflowActualStepsVO.getDescription() + "</p>" );
            remarkEN.append( "<p>Reason: " + workflowActualStepsVO.getDescription() + "</p>" );
         }
      }

      remarkZH.append( "<p>�쵼�����" + ( isPass.equals( "1" ) ? "ͬ��" : "�ܾ�" ) + "�� </p>" );
      remarkEN.append( "<p>Leader's Comment: " + ( isPass.equals( "1" ) ? "Approved" : "Refuse" ) + "�� </p>" );

      final StringBuilder mailOrInfoContent = new StringBuilder( getMessageContent( accountId, corpId, workflowDefineVO.getEmailTemplateId(), workflowActualVO, workflowActualStepsVO, "1", true, remarkZH.toString(), remarkEN.toString() ) );
      final StringBuilder smsContent = new StringBuilder( "�������" + workflowActualVO.getNameZH() + "�ѱ�" + ( isPass.equals( "1" ) ? "��׼" : "�˻أ�" ) );

      // ��ʼ��MessageInfoVO
      final MessageInfoVO messageInfoVO = new MessageInfoVO();
      messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
      messageInfoVO.setAccountId( workflowActualVO.getAccountId() );
      messageInfoVO.setCorpId( workflowActualVO.getCorpId() );
      messageInfoVO.setTitle( subject.toString() );
      messageInfoVO.setContent( mailOrInfoContent.toString() );
      messageInfoVO.setStatus( "2" );//[��Է�����] 2������
      messageInfoVO.setReceptionStatus( "2" );//[��Խ�����] 2.δ��
      messageInfoVO.setCreateBy( "system" );
      messageInfoVO.setCreateDate( new Date() );

      // ��ʼ��MessageSmsVO
      final MessageSmsVO messageSmsVO = new MessageSmsVO();
      messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
      messageSmsVO.setAccountId( workflowActualVO.getAccountId() );
      messageSmsVO.setCorpId( workflowActualVO.getCorpId() );
      messageSmsVO.setContent( smsContent.toString() );
      messageSmsVO.setStatus( "1" );//������
      messageSmsVO.setCreateBy( "system" );
      messageSmsVO.setCreateDate( new Date() );

      // ��ʼ��MessageMailVO
      final MessageMailVO messageMailVO = new MessageMailVO();
      messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
      messageMailVO.setAccountId( workflowActualVO.getAccountId() );
      messageMailVO.setCorpId( workflowActualVO.getCorpId() );
      messageMailVO.setTitle( subject.toString() );
      messageMailVO.setContent( mailOrInfoContent.toString() );
      messageMailVO.setContentType( "2" );//
      messageMailVO.setStatus( "1" );//������
      messageMailVO.setCreateBy( "system" );
      messageMailVO.setCreateDate( new Date() );

      final StaffDTO staffDTO = KANConstants.getKANAccountConstants( workflowActualVO.getAccountId() ).getStaffDTOByUserId( workflowActualVO.getCreateBy() );

      if ( staffDTO != null && staffDTO.getUserVO() != null )
      {
         // ����վ����Ϣ
         if ( "1".equals( workflowDefineVO.getSendInfo() ) )
         {
            messageInfoVO.setReception( staffDTO.getUserVO().getUserId() );
            this.getMessageInfoDao().insertMessageInfo( messageInfoVO );
         }

         // ���Ͷ�����Ϣ
         if ( "1".equals( workflowDefineVO.getSendSMS() ) )
         {
            messageSmsVO.setReception( staffDTO.getStaffVO().getBizMobile() );
            this.getMessageSmsDao().insertMessageSms( messageSmsVO );
         }
         // �����ʼ���Ϣ
         if ( "1".equals( workflowDefineVO.getSendEmail() ) )
         {
            messageMailVO.setReception( staffDTO.getStaffVO().getBizEmail() );
            this.getMessageMailDao().insertMessageMail( messageMailVO );
         }
      }

   }

   private MessageInfoVO getMessageInfoVO( final String accountId, final String corpId, final String title, final String content )
   {
      // ��ʼ��MessageInfoVO
      final MessageInfoVO messageInfoVO = new MessageInfoVO();
      messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
      messageInfoVO.setAccountId( accountId );
      messageInfoVO.setCorpId( corpId );
      messageInfoVO.setTitle( title );
      messageInfoVO.setContent( content.toString() );
      messageInfoVO.setStatus( "2" );//[��Է�����] 2������
      messageInfoVO.setReceptionStatus( "2" );//[��Խ�����] 2.δ��
      messageInfoVO.setCreateBy( "system" );
      messageInfoVO.setCreateDate( new Date() );

      return messageInfoVO;
   }

   private MessageSmsVO getMessageSmsVO( final String accountId, final String corpId, final String content )
   {
      // ��ʼ��MessageSmsVO
      final MessageSmsVO messageSmsVO = new MessageSmsVO();
      messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
      messageSmsVO.setAccountId( accountId );
      messageSmsVO.setCorpId( corpId );
      messageSmsVO.setContent( content );
      messageSmsVO.setStatus( "1" );//������
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
      messageMailVO.setStatus( "1" );//������
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

         final String titleZH = workflowActualVO.getNameZH() + "�ȴ����";
         final String titleEN = workflowActualVO.getNameEN() + "wait for review";
         final StringBuilder contentNoticeZH = new StringBuilder();
         final StringBuilder contentNoticeEN = new StringBuilder();
         final StringBuilder contentZH = new StringBuilder();
         final StringBuilder contentEN = new StringBuilder();

         contentZH.append( workflowActualVO.getNameZH() + "��Ҫ������ˣ��뾡����ˡ�<br>" );
         contentEN.append( workflowActualVO.getNameEN() + "need you to review, please review as soon as possible.<br>" );

         //�����Ѿ���������Ա���������ţ�ְλ
         final String workflowId = workflowActualStepsVO.getWorkflowId();
         List workflowApprovedChainVOsList = null;
         WorkflowApprovedChainVO workflowApprovedChainVO = null;
         if ( StringUtils.isNotBlank( workflowId ) )
         {
            workflowApprovedChainVOsList = workflowActualStepsDao.getWorkflowApprovedChainByworkflowId( workflowId );
         }
         if ( workflowApprovedChainVOsList != null && workflowApprovedChainVOsList.size() > 0 )
         {
            contentZH.append( "������ʷ��" );
            contentEN.append( "Review history��" );
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
         // ��Ӷ����ʼ���Ϣ
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

         // �Ͷ���ͬ
         if ( "com.kan.hro.domain.biz.employee.EmployeeContractVO".equals( objectClass ) )
         {
            contentNoticeZH.append( "<p>���ף�" + jsonObject.getString( "orderId" ) + "��" + jsonObject.getString( "orderName" ) + "��</p>" );
            contentNoticeZH.append( "<p>�Ͷ���ͬ��" + jsonObject.getString( "contractId" ) + "��" + workflowActualVO.getNameZH() + "��" + "</p>" );
            contentNoticeZH.append( "--Easy Pay" );

            contentNoticeEN.append( "<p>Order��" + jsonObject.getString( "orderId" ) + "��" + jsonObject.getString( "orderName" ) + "��</p>" );
            contentNoticeEN.append( "<p>Contract��" + jsonObject.getString( "contractId" ) + "��" + workflowActualVO.getNameEN() + "��" + "</p>" );
         }

         // ��Ա�Ӱ࣬��ٹ����� ���ӵ�½��ַ
         if ( !KANConstants.ROLE_EMPLOYEE.equals( workflowActualVO.getRole() ) )
         {
            if ( corpId == null )
            {
               contentZH.append( "��¼��ַ�� <a href=\"" + domain + "/logon.do\">" + domain + "/logon.do</a> <br>" );
               contentEN.append( "Login address�� <a href=\"" + domain + "/logon.do\">" + domain + "/logon.do</a> <br>" );
            }
            else
            {
               contentZH.append( "��¼��ַΪ�� <a href=\"" + domain + "/logoni.do\">" + domain + "/logoni.do</a> <br>" );
               contentEN.append( "Login address��<a href=\"" + domain + "/logoni.do\">" + domain + "/logoni.do</a> <br>" );
            }

            contentZH.append( "��˵�ַΪ(���ȵ�¼)��<a href=\"" + domain + "/workflowActualStepsAction.do?proc=list_object&workflowId=" + workflowActualVO.getEncodedId() + "\">" + domain
                  + "/workflowActualStepsAction.do?proc=list_object&workflowId=" + workflowActualVO.getEncodedId() + "</a> <br>" );
            contentEN.append( "Approval (Login required):��<a href=\"" + domain + "/workflowActualStepsAction.do?proc=list_object&workflowId=" + workflowActualVO.getEncodedId()
                  + "\">" + domain + "/workflowActualStepsAction.do?proc=list_object&workflowId=" + workflowActualVO.getEncodedId() + "</a> <br>" );
         }
         else
         {
            if ( StringUtils.isNotBlank( objectClass ) )
            {
               //�Ӱ�
               if ( "com.kan.hro.domain.biz.attendance.OTHeaderVO".equals( objectClass ) )
               {
                  String employeeNameZH = jsonObject.getString( "employeeNameZH" );
                  String employeeNameEN = jsonObject.getString( "employeeNameEN" );
                  String estimateStartDate = jsonObject.getString( "estimateStartDate" );
                  String estimateEndDate = jsonObject.getString( "estimateEndDate" );
                  String estimateHours = jsonObject.getString( "estimateHours" );
                  String remark = jsonObject.getString( "remark" );

                  contentZH.append( "�Ӱ�������Ϣ<br>" );
                  contentEN.append( "Overtime application information<br>" );
                  String employeeId = jsonObject.getString( "employeeId" );
                  if ( StringUtils.isNotBlank( employeeId ) )
                  {
                     EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );
                     contentZH.append( "��Ա��ţ�" + employeeId + "<br>" );
                     contentZH.append( "��Ա���֤�ţ�" + employeeVO.getCertificateNumber() + "<br>" );

                     contentEN.append( "Employee number��" + employeeId + "<br>" );
                     contentEN.append( "Employee identity card��" + employeeVO.getCertificateNumber() + "<br>" );
                  }
                  contentZH.append( "��Ա������" + employeeNameZH + "<br>" );
                  contentZH.append( "�Ӱ�ʱ�䣺" + estimateStartDate + "~" + estimateEndDate + "<br>" );
                  contentZH.append( "�Ӱ�ʱ����" + estimateHours + " Сʱ<br>" );
                  contentZH.append( "��ע��" + remark + "<br>" );

                  contentEN.append( "Employee name��" + employeeNameEN + "<br>" );
                  contentEN.append( "Overtime period��" + estimateStartDate + "~" + estimateEndDate + "<br>" );
                  contentEN.append( "Overtime total��" + estimateHours + " hours<br>" );
                  contentEN.append( "Remark��" + remark + "<br>" );
               }
               // ���
               if ( "com.kan.hro.domain.biz.attendance.LeaveHeaderVO".equals( objectClass ) )
               {
                  String employeeNameZH = jsonObject.getString( "employeeNameZH" );
                  String employeeNameEN = jsonObject.getString( "employeeNameEN" );
                  String estimateStartDate = jsonObject.getString( "estimateStartDate" );
                  String estimateEndDate = jsonObject.getString( "estimateEndDate" );
                  String estimateBenefitHours = jsonObject.getString( "estimateBenefitHours" );
                  String employeeId = jsonObject.getString( "employeeId" );

                  contentZH.append( "���������Ϣ<br>" );
                  contentEN.append( "Leave application information<br>" );
                  if ( StringUtils.isNotBlank( employeeId ) )
                  {
                     EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );
                     contentZH.append( "��Ա��ţ�" + employeeId + "<br>" );
                     contentZH.append( "��Ա���֤�ţ�" + employeeVO.getCertificateNumber() + "<br>" );

                     contentEN.append( "Employee number��" + employeeId + "<br>" );
                     contentEN.append( "Employee identity card��" + employeeVO.getCertificateNumber() + "<br>" );
                  }
                  contentZH.append( "��Ա������" + employeeNameZH + "<br>" );
                  contentZH.append( "���ʱ�䣺" + estimateStartDate + "~" + estimateEndDate + "<br>" );
                  contentZH.append( "���ʱ����" + estimateBenefitHours + " Сʱ<br>" );

                  contentEN.append( "Employee name��" + employeeNameEN + "<br>" );
                  contentEN.append( "Overtime period��" + estimateStartDate + "~" + estimateEndDate + "<br>" );
                  contentEN.append( "Overtime total��" + estimateBenefitHours + " Сʱ<br>" );
               }
               //��н 
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

                  contentZH.append( "��н��Ϣ<br>" );
                  contentEN.append( "Salary adjustment information<br>" );
                  String employeeId = jsonObject.getString( "employeeId" );
                  if ( StringUtils.isNotBlank( employeeId ) )
                  {
                     EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );
                     contentZH.append( "��Ա��ţ�" + employeeId + "<br>" );
                     contentZH.append( "��Ա���֤�ţ�" + employeeVO.getCertificateNumber() + "<br>" );

                     contentEN.append( "Employee number��" + employeeId + "<br>" );
                     contentEN.append( "Employee identity card��" + employeeVO.getCertificateNumber() + "<br>" );
                  }
                  contentZH.append( "��Ա������" + employeeNameZH + "<br>" );
                  contentZH.append( "��ͬ���ƣ�" + employeeContractNameZH + "<br>" );
                  contentZH.append( "��Ŀ��" + itemNameZH + "<br>" );
                  contentZH.append( "����ǰ��Ϣ��" + oldBase + "    " + oldStartDate + "~" + oldEndDate + "<br>" );
                  contentZH.append( "��������Ϣ��" + newBase + "    " + newStartDate + "~" + newEndDate + "<br>" );

                  contentEN.append( "Employee name��" + employeeNameEN + "<br>" );
                  contentEN.append( "Employee contract name��" + employeeContractNameEN + "<br>" );
                  contentEN.append( "Item name��" + itemNameEN + "<br>" );
                  contentEN.append( "Old informatio��" + oldBase + "    " + oldStartDate + "~" + oldEndDate + "<br>" );
                  contentEN.append( "New informatio��" + newBase + "    " + newStartDate + "~" + newEndDate + "<br>" );
               }
            }
         }

         // ����һ�������������֤�ʼ�����
         final String randomKey = workflowActualStepsVO.getRandomKey();
         final String stepId = workflowActualStepsVO.getStepId();
         // 3ͬ�� 4��ͬ��
         final String status_3 = "3";
         final String status_4 = "4";
         //         final String workflowId = workflowActualStepsVO.getWorkflowId();

         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( workflowActualVO.getAccountId() );

         MessageInfoVO messageInfoVO = null;
         MessageSmsVO messageSmsVO = null;
         MessageMailVO messageMailVO = null;

         // ����ǲ��������ǲο�
         if ( workflowActualStepsVO.getStepType().equals( "3" ) )
         {
            messageInfoVO = getMessageInfoVO( workflowActualVO.getAccountId(), corpId, "�Ͷ���ͬ��׼֪ͨ - The notice of approval of labor contract", contentNoticeZH.toString()
                  + contentNoticeEN.toString() );
            messageSmsVO = getMessageSmsVO( workflowActualVO.getAccountId(), corpId, contentNoticeZH.toString() + contentNoticeEN.toString() );
            messageMailVO = getMessageMailVO( workflowActualVO.getAccountId(), corpId, "�Ͷ���ͬ��׼֪ͨ - The notice of approval of labor contract", contentNoticeZH.toString()
                  + contentNoticeEN.toString() );
         }
         else
         {
            messageInfoVO = getMessageInfoVO( workflowActualVO.getAccountId(), corpId, titleZH + " - " + titleEN, contentZH.toString() + contentEN.toString() );
            messageSmsVO = getMessageSmsVO( workflowActualVO.getAccountId(), corpId, workflowActualVO.getNameZH() + "��Ҫ�����ˣ��뾡����ˣ�" );
            contentZH.append( "ֱ�����(�����¼):<br/>" );
            contentZH.append( "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + stepId + "&workflowId=" + workflowId + "&status="
                  + status_3 + "&randomKey=" + randomKey + "\">ͬ�����</a> <br>" );
            contentZH.append( "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + stepId + "&workflowId=" + workflowId + "&status="
                  + status_4 + "&randomKey=" + randomKey + "\">�������</a> <br>" );

            contentEN.append( "Direct Approval (without login):<br/>" );
            contentEN.append( "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + stepId + "&workflowId=" + workflowId + "&status="
                  + status_3 + "&randomKey=" + randomKey + "\">Approved application</a> <br>" );
            contentEN.append( "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + stepId + "&workflowId=" + workflowId + "&status="
                  + status_4 + "&randomKey=" + randomKey + "\">Reject application</a> <br>" );

            //�Ѿ�������������
            messageMailVO = getMessageMailVO( workflowActualVO.getAccountId(), corpId, titleZH + " - " + titleEN, contentZH.toString() + contentEN.toString() );
         }

         // �������� 1:�ڲ�ְλID || ��������5���Խ���
         if ( "1".equals( workflowActualStepsVO.getAuditType() ) || "5".equals( workflowActualStepsVO.getAuditType() ) )
         {
            final List< StaffDTO > staffDTOs = accountConstants.getValidStaffDTOsByPositionId( workflowActualStepsVO.getAuditTargetId() );
            if ( staffDTOs != null && staffDTOs.size() > 0 )
            {
               for ( StaffDTO staffDTO : staffDTOs )
               {
                  if ( staffDTO.getUserVO() == null || staffDTO.getStaffVO() == null )
                     continue;

                  // ����վ����Ϣ
                  if ( "1".equals( workflowActualStepsVO.getSendInfo() ) )
                  {
                     messageInfoVO.setReception( staffDTO.getUserVO().getUserId() );
                     this.getMessageInfoDao().insertMessageInfo( messageInfoVO );
                  }

                  // ���Ͷ�����Ϣ
                  if ( "1".equals( workflowActualStepsVO.getSendSMS() ) )
                  {
                     messageSmsVO.setReception( staffDTO.getStaffVO().getBizMobile() );
                     this.getMessageSmsDao().insertMessageSms( messageSmsVO );
                  }

                  // �����ʼ���Ϣ
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
         // �������� 2:�ⲿְλID
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

                  // ���Ͷ�����Ϣ
                  if ( "1".equals( workflowActualStepsVO.getSendSMS() ) && KANUtil.filterEmpty( mobile ) != null )
                  {
                     messageSmsVO.setReception( mobile );
                     this.getMessageSmsDao().insertMessageSms( messageSmsVO );
                  }

                  // �����ʼ���Ϣ
                  if ( "1".equals( workflowActualStepsVO.getSendEmail() ) && KANUtil.filterEmpty( email ) != null )
                  {
                     messageMailVO.setReception( email );
                     this.getMessageMailDao().insertMessageMail( messageMailVO );
                  }
               }
            }
         }
         // ��������3:ֱ�߾����ͻ���
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
               {//���Ͷ�����Ϣ
                  messageSmsVO.setReception( mobile );
                  messageSmsDao.insertMessageSms( messageSmsVO );
               }
               if ( "1".equals( workflowActualStepsVO.getSendEmail() ) )
               {//�����ʼ���Ϣ
                  messageMailVO.setReception( email );
                  messageMailDao.insertMessageMail( messageMailVO );
               }
            }
         }
         // ��������4:�ڲ�Ա��ID
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
               // ���Ͷ�����Ϣ
               if ( "1".equals( workflowActualStepsVO.getSendSMS() ) )
               {
                  messageSmsVO.setReception( mobile );
                  messageSmsDao.insertMessageSms( messageSmsVO );
               }
               // �����ʼ���Ϣ
               if ( "1".equals( workflowActualStepsVO.getSendEmail() ) )
               {
                  messageMailVO.setReception( email );
                  messageMailDao.insertMessageMail( messageMailVO );
               }
            }
         }
      }
   }

   // ��Ӷ����ʼ���Ϣ
   private void addExtendInfo( final StringBuilder contentZH, final StringBuilder contentEN, final WorkflowActualVO workflowActualVO ) throws KANException
   {
      String objectClassName = null;
      final HistoryVO historyVO = this.getHistoryDao().getObjectByWorkflowId( workflowActualVO.getWorkflowId() );
      if ( historyVO != null )
      {
         objectClassName = historyVO.getObjectClass();
      }

      // ����Ǻ�ͬ���������������2����Ϣ
      if ( KANUtil.filterEmpty( workflowActualVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeeContractVO".equals( objectClassName ) )
      {
         final EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( workflowActualVO.getObjectId() );
         if ( employeeContractVO != null )
         {
            contentZH.append( "�ϼ����ţ�" + employeeContractVO.getParentBranchName() + "<br>" );
            contentZH.append( "ֱ�߾���" + employeeContractVO.getLineManager() + "<br>" );

            contentEN.append( "The superior department��" + employeeContractVO.getParentBranchName() + "<br>" );
            contentEN.append( "The line manager��" + employeeContractVO.getLineManager() + "<br>" );

         }
      }
      // �������ٻ����ǼӰ࣬��������ˣ��Ӱ࣬��ʱ��com.kan.hro.domain.biz.attendance.LeaveHeaderVO
      if ( KANUtil.filterEmpty( workflowActualVO.getObjectId() ) != null && "com.kan.hro.domain.biz.attendance.LeaveHeaderVO".equals( objectClassName ) )
      {
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderDao.getLeaveHeaderVOByLeaveHeaderId( workflowActualVO.getObjectId() );
         if ( leaveHeaderVO != null )
         {
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( leaveHeaderVO.getEmployeeId() );
            final ItemVO itemVO = KANConstants.getKANAccountConstants( leaveHeaderVO.getAccountId() ).getItemVOByItemId( leaveHeaderVO.getItemId() );
            contentZH.append( "��������ˣ�" + employeeVO.getNameZH() + "<br>" );
            contentZH.append( "������" + itemVO.getNameZH() + "<br>" );
            contentZH.append( "���ʱ�䣺" + leaveHeaderVO.getEstimateStartDate() + " ~ " + leaveHeaderVO.getEstimateEndDate() + "(�ϼ�: " + leaveHeaderVO.getUseHours() + " Сʱ)" + "<br>" );

            contentEN.append( "Applicant��" + employeeVO.getNameEN() + "<br>" );
            contentEN.append( "Leave Type��" + itemVO.getNameEN() + "<br>" );
            contentEN.append( "Period��" + leaveHeaderVO.getEstimateStartDate() + " ~ " + leaveHeaderVO.getEstimateEndDate() + "(Total: " + leaveHeaderVO.getUseHours() + " Hours)"
                  + "<br>" );
         }
      }
      if ( KANUtil.filterEmpty( workflowActualVO.getObjectId() ) != null && "com.kan.hro.domain.biz.attendance.OTHeaderVO".equals( objectClassName ) )
      {
         final OTHeaderVO otHeaderVO = otHeaderDao.getOTHeaderVOByOTHeaderId( workflowActualVO.getObjectId() );
         if ( otHeaderVO != null )
         {
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( otHeaderVO.getEmployeeId() );
            contentZH.append( "�Ӱ������ˣ�" + employeeVO.getNameZH() + "<br>" );
            contentZH.append( "�Ӱ�ʱ�䣺" + otHeaderVO.getEstimateStartDate() + " ~ " + otHeaderVO.getEstimateEndDate() + "(�ϼ�: " + otHeaderVO.getEstimateHours() + " Сʱ)" + "<br>" );

            contentEN.append( "Applicant��" + employeeVO.getNameZH() + "<br>" );
            contentEN.append( "Period��" + otHeaderVO.getEstimateStartDate() + " ~ " + otHeaderVO.getEstimateEndDate() + "(Total: " + otHeaderVO.getEstimateHours() + " Hours)"
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

   //---------------------------------�ָ���------------------------------------------------------------------//

   // �����ʼ�����ģ��
   public void sendMessageForWorkflow( final WorkflowActualVO workflowActualVO, final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException
   {
      final String accountId = KANUtil.filterEmpty( workflowActualVO.getAccountId() );
      final String corpId = KANUtil.filterEmpty( workflowActualVO.getCorpId() );
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );

      if ( workflowActualStepsVO.getStatus().equals( "2" ) || workflowActualStepsVO.getStatus().equals( "5" ) || workflowActualStepsVO.getStatus().equals( "6" ) )
      {
         // ��ʼ����Ϣ����
         final String titleZH = workflowActualVO.getNameZH() + "��Ҫ�������";

         // �ʼ������š�վ����Ϣ����
         final String mailContent = getMessageContent( accountId, corpId, workflowActualStepsVO.getEmailTemplateType(), workflowActualVO, workflowActualStepsVO, "1", false );
         final String smsContent = getMessageContent( accountId, corpId, workflowActualStepsVO.getSmsTemplateType(), workflowActualVO, workflowActualStepsVO, "2", false );
         final String infoContent = getMessageContent( accountId, corpId, workflowActualStepsVO.getInfoTemplateType(), workflowActualVO, workflowActualStepsVO, "3", false );

         // ��Ҫ��Ӹ���
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

   // ��ȡ��Ϣ������
   // Added by siuvan 2015-03-24
   private String getMessageContent( final String accountId, final String corpId, final String templateId, final WorkflowActualVO workflowActualVO,
         final WorkflowActualStepsVO workflowActualStepsVO, final String flag, final boolean toCreator, final String remarkZH, final String remarkEN ) throws KANException
   {
      // ��ʼ������ֵ
      final StringBuilder content = new StringBuilder();

      final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? ( KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME ) : ( KANConstants.HTTP
            + KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME );

      // ����
      final String messageSubjectZH = "<P>" + workflowActualVO.getNameZH() + "��Ҫ������ˣ��뾡����ˡ�</P>";
      final String messageSubjectEN = "<P>" + workflowActualVO.getNameEN() + " wait for approval, please operate as soon as possible.</P>";
      final String logonAddress = "<a href=\"" + domain + "/logoni.do\">" + domain + "/logoni.do</a>";
      final String approvalZH = "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + workflowActualStepsVO.getStepId() + "&workflowId="
            + workflowActualVO.getWorkflowId() + "&" + USERID_TODO + "&status=3&randomKey=" + workflowActualStepsVO.getRandomKey() + "\">ͬ�����</a>&nbsp;&nbsp;";
      final String approvalEN = "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + workflowActualStepsVO.getStepId() + "&workflowId="
            + workflowActualVO.getWorkflowId() + "&" + USERID_TODO + "&status=3&randomKey=" + workflowActualStepsVO.getRandomKey() + "\">Approve</a>&nbsp;&nbsp;";
      final String rejectZH = "<a href=\"" + domain + "/workflowActualStepsAction.do?proc=modify_object_byMail&stepId=" + workflowActualStepsVO.getStepId() + "&workflowId="
            + workflowActualVO.getWorkflowId() + "&" + USERID_TODO + "&status=4&randomKey=" + workflowActualStepsVO.getRandomKey() + "\">�������</a>";
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

      // ��ȡHistoryVO
      final HistoryVO historyVO = this.getHistoryDao().getObjectByWorkflowId( workflowActualVO.getWorkflowId() );

      // ��ʼ����Ϣģ��
      MessageTemplateVO messageTemplateVO = null;
      // ��ʼ�������޶���
      String objectClassName = "";

      // ��������Ҫ��Ϣģ��
      if ( KANUtil.filterEmpty( templateId, "0" ) != null )
      {
         messageTemplateVO = this.getMessageTemplateDao().getMessageTemplateVOByTemplateId( templateId );
      }

      // ������������Ϣģ��
      if ( messageTemplateVO != null && historyVO != null )
      {
         objectClassName = historyVO.getObjectClass();
         // ��ȡϵͳ����
         final List< ConstantVO > constantVOs = KANConstants.getKANAccountConstants( accountId ).getConstantVOsByScopeType( "1" );
         // ���Դ��VO�����滻ģ������õ�
         final List< Object > objects = new ArrayList< Object >();

         objects.add( stringMap );

         // �����˶�����EmployeeContractVO
         if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeeContractVO".equals( objectClassName ) )
         {
            // ��ȡEmployeeContractVO
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
         // �����˶�����LeaveHeaderVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.attendance.LeaveHeaderVO".equals( objectClassName ) )
         {
            // ��ȡLeaveHeaderVO
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
         // �����˶�����OTHeaderVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.attendance.OTHeaderVO".equals( objectClassName ) )
         {
            // ��ȡOTHeaderVO
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
                  otHeaderVO.setEstimateStartDate( "(Ԥ) " + otHeaderVO.getEstimateStartDate() );
                  otHeaderVO.setEstimateEndDate( "(Ԥ) " + otHeaderVO.getEstimateEndDate() );
               }
               objects.add( otHeaderVO );
            }
         }
         // �����˶�����EmployeeSalaryAdjustmentVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO".equals( objectClassName ) )
         {
            final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = this.getEmployeeSalaryAdjustmentDao().getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( historyVO.getObjectId() );
            if ( employeeSalaryAdjustmentVO != null )
            {
               objects.add( employeeSalaryAdjustmentVO );
            }
         }
         // �����˶�����EmployeeSalaryAdjustmentVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeePositionChangeVO".equals( objectClassName ) )
         {
            final EmployeePositionChangeVO employeePositionChangeVO = this.getEmployeePositionChangeDao().getEmployeePositionChangeVOByPositionChangeId( historyVO.getObjectId() );
            if ( employeePositionChangeVO != null )
            {
               objects.add( employeePositionChangeVO );
            }
         }
         // �������������EmployeeContractSalaryVO
         else if ( KANUtil.filterEmpty( historyVO.getObjectId() ) != null && "com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO".equals( objectClassName ) )
         {
            final EmployeeContractSalaryVO employeeContractSalaryVO = this.getEmployeeContractSalaryDao().getEmployeeContractSalaryVOByEmployeeSalaryId( historyVO.getObjectId() );
            if ( employeeContractSalaryVO != null )
            {
               objects.add( employeeContractSalaryVO );
            }
         }

         // �����Ϣ�������ʼ�
         if ( "1".equals( flag ) )
         {
            // ����ǲ��������ǲο�
            if ( workflowActualStepsVO.getStepType().equals( "3" ) )
            {
               // �ο����費��Ҫ��������
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
                  // �ο����費��Ҫ��������
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

   // ���������ʷ
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
            rs.append( "<br><span>������ʷ</span>" );
            rs.append( "<table>" );
            for ( int i = 0; i < approvedList.size(); i++ )
            {
               final WorkflowActualStepsVO tempWorkflowActualStepsVO = ( WorkflowActualStepsVO ) approvedList.get( i );
               rs.append( "<tr>" );
               rs.append( "<td>" + tempWorkflowActualStepsVO.getPositionTitleZH() + "</td>" );
               if ( "3".equals( tempWorkflowActualStepsVO.getStatus() ) )
               {
                  rs.append( "<td>" + KANUtil.formatDate( tempWorkflowActualStepsVO.getHandleDate(), "yyyy-MM-dd HH:mm:ss", true ) + "</td>" );
                  rs.append( "<td>ͬ��</td>" );
               }
               else
               {
                  rs.append( "<td>" + KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss", true ) + "</td>" );
                  rs.append( "<td>��֪ͨ</td>" );
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

   // ����MessageMailVO
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
         // ��ȡLeaveHeaderVO
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
            System.out.println( "�ʼ�������ȡʧ��" );
         }
      }

      return messageMailVO;
   }

   // ����MessageInfoVO
   // Added by siuvan 2015-03-24
   private MessageInfoVO getMessageInfoVO1( final String accountId, final String corpId, final String title, final String content )
   {
      // ��ʼ��MessageInfoVO
      final MessageInfoVO messageInfoVO = new MessageInfoVO();
      messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
      messageInfoVO.setAccountId( accountId );
      messageInfoVO.setCorpId( corpId );
      messageInfoVO.setTitle( title );
      messageInfoVO.setContent( content.toString() );
      messageInfoVO.setStatus( "2" );//[��Է�����] 2������
      messageInfoVO.setReceptionStatus( "2" );//[��Խ�����] 2.δ��
      messageInfoVO.setCreateBy( "system" );
      messageInfoVO.setCreateDate( new Date() );

      return messageInfoVO;
   }

   // ����MessageSmsVO
   // Added by siuvan 2015-03-24
   private MessageSmsVO getMessageSmsVO1( final String accountId, final String corpId, final String content )
   {
      // ��ʼ��MessageSmsVO
      final MessageSmsVO messageSmsVO = new MessageSmsVO();
      messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
      messageSmsVO.setAccountId( accountId );
      messageSmsVO.setCorpId( corpId );
      messageSmsVO.setContent( KANUtil.filterEmpty( content ) == null ? null : content.replaceAll( "<p>", "" ).replaceAll( "</p>", "" ) );
      messageSmsVO.setStatus( "1" );//������
      messageSmsVO.setCreateBy( "system" );
      messageSmsVO.setCreateDate( new Date() );
      messageSmsVO.setModifyBy( "system" );
      messageSmsVO.setModifyDate( new Date() );

      return messageSmsVO;
   }

   // ����Ϣ���ݲ��뵽ϵͳ���ݿ�
   // Added by siuvan 2015-03-24
   private void insertMessageVO( final KANAccountConstants accountConstants, final WorkflowActualVO workflowActualVO, final WorkflowActualStepsVO workflowActualStepsVO,
         final MessageMailVO messageMailVO, final MessageInfoVO messageInfoVO, final MessageSmsVO messageSmsVO ) throws KANException
   {
      // �������� 1:�ڲ�ְλID || ��������5���Խ���
      if ( "1".equals( workflowActualStepsVO.getAuditType() ) || "5".equals( workflowActualStepsVO.getAuditType() ) )
      {
         final List< StaffDTO > staffDTOs = accountConstants.getValidStaffDTOsByPositionId( workflowActualStepsVO.getAuditTargetId() );
         if ( staffDTOs != null && staffDTOs.size() > 0 )
         {
            for ( StaffDTO staffDTO : staffDTOs )
            {
               if ( staffDTO.getUserVO() == null || staffDTO.getStaffVO() == null )
                  continue;

               // ����վ����Ϣ
               if ( "1".equals( workflowActualStepsVO.getSendInfo() ) && messageInfoVO != null )
               {
                  messageInfoVO.setReception( staffDTO.getUserVO().getUserId() );
                  this.getMessageInfoDao().insertMessageInfo( messageInfoVO );
               }

               // ���Ͷ�����Ϣ
               if ( "1".equals( workflowActualStepsVO.getSendSMS() ) && messageSmsVO != null )
               {
                  messageSmsVO.setReception( staffDTO.getStaffVO().getBizMobile() );
                  this.getMessageSmsDao().insertMessageSms( messageSmsVO );
               }

               // �����ʼ���Ϣ
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
      // �������� 2:�ⲿְλID
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

               // ���Ͷ�����Ϣ
               if ( "1".equals( workflowActualStepsVO.getSendSMS() ) && KANUtil.filterEmpty( mobile ) != null && messageSmsVO != null )
               {
                  messageSmsVO.setReception( mobile );
                  this.getMessageSmsDao().insertMessageSms( messageSmsVO );
               }

               // �����ʼ���Ϣ
               if ( "1".equals( workflowActualStepsVO.getSendEmail() ) && KANUtil.filterEmpty( email ) != null && messageMailVO != null )
               {
                  messageMailVO.setReception( email );
                  this.getMessageMailDao().insertMessageMail( messageMailVO );
               }
            }
         }
      }
      // ��������3:ֱ�߾����ͻ���
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
            {//���Ͷ�����Ϣ
               messageSmsVO.setReception( mobile );
               messageSmsDao.insertMessageSms( messageSmsVO );
            }
            if ( "1".equals( workflowActualStepsVO.getSendEmail() ) && messageMailVO != null )
            {//�����ʼ���Ϣ
               messageMailVO.setReception( email );
               messageMailDao.insertMessageMail( messageMailVO );
            }
         }
      }
      // ��������4:�ڲ�Ա��ID
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

            // ����վ����Ϣ
            if ( "1".equals( workflowActualStepsVO.getSendInfo() ) && messageInfoVO != null )
            {
               messageInfoVO.setReception( staffDTO.getUserVO().getUserId() );
               this.getMessageInfoDao().insertMessageInfo( messageInfoVO );
            }
            // ���Ͷ�����Ϣ
            if ( "1".equals( workflowActualStepsVO.getSendSMS() ) && messageSmsVO != null )
            {
               messageSmsVO.setReception( mobile );
               messageSmsDao.insertMessageSms( messageSmsVO );
            }
            // �����ʼ���Ϣ
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
