package com.kan.base.web.actions.workflow;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.core.ServiceLocator;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.service.inf.workflow.WorkflowActualService;
import com.kan.base.service.inf.workflow.WorkflowActualStepsService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class WorkflowActualStepsAction extends BaseAction
{
   public static final String ACCESSACTION = "HRO_SEC_WORKFLOW_SEARCH";

   /***
    * ����б�
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final boolean ajax = new Boolean( request.getParameter( "ajax" ) );
         // ��ʼ��Service�ӿ�
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // ���Action Form
         final WorkflowActualStepsVO workflowActualStepsVO = ( WorkflowActualStepsVO ) form;
         // ��õ�ǰ����
         final String workflowId;
         if ( ajax )
         {
            workflowId = Cryptogram.decodeString( URLDecoder.decode( URLDecoder.decode( request.getParameter( "workflowId" ), "UTF-8" ), "UTF-8" ) );
         }
         else
         {
            workflowId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowId" ), "UTF-8" ) );
         }
         if ( ajax )
         {
            decodedObject( workflowActualStepsVO );
         }
         workflowActualStepsVO.setWorkflowId( workflowId );
         // Ĭ�ϰ�����������������
         workflowActualStepsVO.setSortColumn( "stepIndex" );
         workflowActualStepsVO.setSortOrder( "asc" );
         request.setAttribute( "enCodeWorkflowId", request.getParameter( "workflowId" ) );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder workflowActualStepsHolder = new PagedListHolder();

         // ���뵱ǰҳ
         workflowActualStepsHolder.setPage( page );
         // ���뵱ǰֵ����
         workflowActualStepsHolder.setObject( workflowActualStepsVO );
         // ����ҳ���¼����
         workflowActualStepsHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowActualStepsService.getWorkflowActualStepsVOByCondition( workflowActualStepsHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( workflowActualStepsHolder, request );

         // Holder��д��Request����
         request.setAttribute( "workflowActualStepsHolder", workflowActualStepsHolder );

         // ��õ�ǰ�û�����ӵ�е�positionId ������**�����Ȩ��
         final String staffId = getStaffId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         StaffDTO staffDTO = accountConstants.getStaffDTOByStaffId( staffId );
         List< String > positionIds = new ArrayList< String >();
         List< PositionStaffRelationVO > positonStaffRelationVOs = staffDTO.getPositionStaffRelationVOs();
         for ( PositionStaffRelationVO positionStaffRelationVO : positonStaffRelationVOs )
         {
            positionIds.add( positionStaffRelationVO.getPositionId() );
         }
         request.setAttribute( "positionIds", positionIds );

         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );

         request.setAttribute( "workflowActualVO", workflowActualVO );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( ajax )
         {
            // Ajax��������ת
            return mapping.findForward( "listWorkflowActualStepsTable" );
         }
         else
         {
            //����object����
            HistoryService historyService = ( HistoryService ) getService( "historyService" );
            HistoryVO historyVO = historyService.getHistoryVOByWorkflowId( workflowId );
            final String objectClassStr = historyVO.getObjectClass();
            Class< ? > objectClass = Class.forName( objectClassStr );

            String passObjStr = historyVO.getPassObject();
            if ( passObjStr != null && !passObjStr.trim().isEmpty() )
            {
               final BaseVO passObject = ( BaseVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );
               passObject.reset( mapping, request );
               request.setAttribute( "passObject", passObject );
            }

            String failObjStr = historyVO.getFailObject();
            if ( failObjStr != null && !failObjStr.trim().isEmpty() )
            {
               final BaseVO failObject = ( BaseVO ) JSONObject.toBean( JSONObject.fromObject( failObjStr ), objectClass );
               failObject.reset( mapping, request );
               request.setAttribute( "originalObject", failObject );
            }

            //            final String serviceBeanId = historyVO.getServiceBean();
            //            final String serviceGetObjByIdMethod = historyVO.getServiceGetObjByIdMethod();
            //            if ( StringUtils.isNotBlank( serviceBeanId ) && StringUtils.isNotBlank( serviceGetObjByIdMethod ) )
            //            {
            //               Object targetService = ServiceLocator.getService( serviceBeanId );
            //
            //               final Class< ? > targetServiceClass = targetService.getClass();
            //               Method method = targetServiceClass.getMethod( serviceGetObjByIdMethod, String.class );
            //               BaseVO originalObject = ( BaseVO ) method.invoke( targetService, historyVO.getObjectId() );
            //               if ( originalObject != null )
            //               {
            //                  originalObject.reset( mapping, request );
            //                  request.setAttribute( "originalObject", originalObject );
            //               }
            //            }
            request.setAttribute( "historyVO", historyVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listWorkflowActualSteps" );
   }

   public ActionForward list_object_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // ���Action Form
         final WorkflowActualStepsVO workflowActualStepsVO = ( WorkflowActualStepsVO ) form;
         // ��õ�ǰ����
         final String workflowId = request.getParameter( "workflowId" );
         workflowActualStepsVO.setWorkflowId( workflowId );
         // Ĭ�ϰ�����������������
         workflowActualStepsVO.setSortColumn( "stepIndex" );
         workflowActualStepsVO.setStepType( "1,2" );
         workflowActualStepsVO.setSortOrder( "asc" );
         request.setAttribute( "enCodeWorkflowId", request.getParameter( "workflowId" ) );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder workflowActualStepsHolder = new PagedListHolder();

         // ���뵱ǰҳ
         workflowActualStepsHolder.setPage( page );
         // ���뵱ǰֵ����
         workflowActualStepsHolder.setObject( workflowActualStepsVO );
         // ����ҳ���¼����
         workflowActualStepsHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowActualStepsService.getWorkflowActualStepsVOByCondition( workflowActualStepsHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( workflowActualStepsHolder, request );

         // Holder��д��Request����
         request.setAttribute( "workflowActualStepsHolder", workflowActualStepsHolder );

         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );

         request.setAttribute( "workflowActualVO", workflowActualVO );
         // Ajax��������ת
         return mapping.findForward( "popupListWorkflowActual" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * To workflowModule modify page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // ��õ�ǰ����
         String stepId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "UTF-8" ) );
         // ���������Ӧ����
         WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsService.getWorkflowActualStepsVOByStepsId( stepId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         workflowActualStepsVO.reset( null, request );

         workflowActualStepsVO.setSubAction( VIEW_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageWorkflowModule" );
   }

   /**
    * To workflowModule new page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );
      // ��ʼ��Service�ӿ�
      // ����Sub Action
      ( ( WorkflowActualStepsVO ) form ).setStatus( WorkflowActualStepsVO.TRUE );
      ( ( WorkflowActualStepsVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageWorkflowModule" );
   }

   /**
    * Add workflowModule
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      // ��ת���б����
      return null;
   }

   /**
    * Modify workflowModule
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
            // ��õ�ǰ����
            final String stepId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "UTF-8" ) );
            // ���������Ӧ����
            final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsService.getWorkflowActualStepsVOByStepsId( stepId );
            // װ�ؽ��洫ֵ
            workflowActualStepsVO.update( ( WorkflowActualStepsVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            workflowActualStepsVO.setModifyBy( getUserId( request, response ) );

            // ���Ȩ����֤?????

            // �޸Ķ���
            workflowActualStepsVO.reset( null, request );
            workflowActualStepsService.updateWorkflowActualStepsVO( workflowActualStepsVO );

            insertlog( request, workflowActualStepsVO, workflowActualStepsVO.getStatus().equals( "3" ) ? Operate.APPROVE : Operate.REJECT, workflowActualStepsVO.getStepId(), ( workflowActualStepsVO.getStatus().equals( "3" ) ? "ͬ��"
                  : "�ܾ�" )
                  + " workflow����" );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // ���Form����
         ( ( WorkflowActualStepsVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   // �ʼ�����
   public ActionForward modify_object_byMail( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // ��õ�ǰ����
         final String stepId = request.getParameter( "stepId" );
         final String workflowId = request.getParameter( "workflowId" );
         final String status = request.getParameter( "status" );
         final String randomKey = request.getParameter( "randomKey" );
         final String userId = request.getParameter( "userId" );

         if ( StringUtils.isNotEmpty( userId ) )
         {
            request.setAttribute( "userId_mail", userId );
         }

         // ���workflowActualVO
         final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );
         // ���workflowActualStepsVO
         final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsService.getWorkflowActualStepsVOByStepsId( stepId );

         if ( workflowActualVO != null && workflowActualStepsVO != null && randomKey.equals( workflowActualStepsVO.getRandomKey() ) )
         {
            workflowActualStepsVO.setAccountId( workflowActualVO.getAccountId() );
            // ��������
            workflowActualStepsVO.setRandomKey( "" );
            workflowActualStepsVO.setModifyDate( new Date() );
            workflowActualStepsVO.setStatus( status );
            request.setAttribute( "workflowMSG", "�����ɹ���" + workflowActualVO.getNameZH() + "���ѱ�" + ( "3".equals( status ) ? "��׼" : "�˻�" ) + "��" );

            workflowActualStepsVO.reset( mapping, request );
            workflowActualStepsService.updateWorkflowActualStepsVO( workflowActualStepsVO );

            insertlog( request, workflowActualStepsVO, workflowActualStepsVO.getStatus().equals( "3" ) ? Operate.APPROVE : Operate.REJECT, workflowActualStepsVO.getStepId(), ( workflowActualStepsVO.getStatus().equals( "3" ) ? "ͬ��"
                  : "�ܾ�" )
                  + " workflow����(�����ʼ��Ĳ���)" );
         }
         else
         {
            request.setAttribute( "workflowMSG", "���Ѿ������� " + workflowActualVO.getNameZH() + "�������ظ�������" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "auditMessage" );
   }

   /**
    * Delete workflowModule
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

   }

   /**
    * Delete workflowModule list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
   }

   public ActionForward list_object_mobile( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // ��õ�ǰ����
         final String workflowId = KANUtil.decodeString( request.getParameter( "workflowId" ) );
         final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );
         workflowActualVO.reset( null, request );

         final List< Object > stepVOs = workflowActualStepsService.getWorkflowActualStepsVOsByWorkflowId( workflowId );

         // ��õ�ǰ�û�����ӵ�е�positionId ������**�����Ȩ��
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         StaffDTO staffDTO = accountConstants.getStaffDTOByStaffId( getStaffId( request, response ) );
         List< String > positionIds = new ArrayList< String >();
         List< PositionStaffRelationVO > positonStaffRelationVOs = staffDTO.getPositionStaffRelationVOs();
         for ( PositionStaffRelationVO positionStaffRelationVO : positonStaffRelationVOs )
         {
            positionIds.add( positionStaffRelationVO.getPositionId() );
         }

         // �ҵ�workflowActualStepsVO
         for ( Object obj : stepVOs )
         {
            WorkflowActualStepsVO tempVO = ( WorkflowActualStepsVO ) obj;
            // �ڲ�ְλ
            if ( "1".equals( tempVO.getAuditType() ) && positionIds.contains( tempVO.getAuditTargetId() ) )
            {
               request.setAttribute( "workflowActualStepsForm", tempVO );
               break;
            }
            // �ڲ�Ա��
            else if ( "4".equals( tempVO.getAuditType() ) && tempVO.getAuditTargetId().equals( getStaffId( request, response ) ) )
            {
               request.setAttribute( "workflowActualStepsForm", tempVO );
               break;
            }
         }

         request.setAttribute( "workflowActualVO", workflowActualVO );

         //����object����
         HistoryService historyService = ( HistoryService ) getService( "historyService" );
         HistoryVO historyVO = historyService.getHistoryVOByWorkflowId( workflowId );
         final String objectClassStr = historyVO.getObjectClass();
         Class< ? > objectClass = Class.forName( objectClassStr );

         String passObjStr = historyVO.getPassObject();
         if ( passObjStr != null && !passObjStr.trim().isEmpty() )
         {
            final BaseVO passObject = ( BaseVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );
            passObject.reset( mapping, request );
            request.setAttribute( "passObject", passObject );
         }

         final String serviceBeanId = historyVO.getServiceBean();
         final String serviceGetObjByIdMethod = historyVO.getServiceGetObjByIdMethod();
         Object targetService = ServiceLocator.getService( serviceBeanId );

         final Class< ? > targetServiceClass = targetService.getClass();
         Method method = targetServiceClass.getMethod( serviceGetObjByIdMethod, String.class );
         BaseVO originalObject = ( BaseVO ) method.invoke( targetService, historyVO.getObjectId() );
         if ( originalObject != null )
         {
            originalObject.reset( mapping, request );
         }
         request.setAttribute( "originalObject", originalObject );

         request.setAttribute( "historyVO", historyVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "taskDetail" );
   }

   public ActionForward modify_object_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
            // ��õ�ǰ����
            final String stepId = request.getParameter( "stepId" );
            // ���������Ӧ����
            final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsService.getWorkflowActualStepsVOByStepsId( stepId );
            // װ�ؽ��洫ֵ
            workflowActualStepsVO.update( ( WorkflowActualStepsVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            workflowActualStepsVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            workflowActualStepsVO.reset( mapping, request );
            workflowActualStepsService.updateWorkflowActualStepsVO( workflowActualStepsVO );
            insertlog( request, workflowActualStepsVO, workflowActualStepsVO.getStatus().equals( "3" ) ? Operate.APPROVE : Operate.REJECT, workflowActualStepsVO.getStepId(), ( workflowActualStepsVO.getStatus().equals( "3" ) ? "ͬ��"
                  : "�ܾ�" )
                  + " workflow����(����΢�ŵĲ���)" );
         }
         // ���Form����
         ( ( WorkflowActualStepsVO ) form ).reset();
         return new WorkflowActualAction().list_object_unfinished_mobile( mapping, new WorkflowActualVO(), request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void re_send_mail( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final String workflowId = request.getParameter( "workflowId" );
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         boolean success = workflowActualStepsService.reSendApprovalMail( workflowId );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         out.print( success ? KANUtil.getProperty( request.getLocale(), "wx.leave.list.confirm.resend.success" )
               : KANUtil.getProperty( request.getLocale(), "wx.leave.list.confirm.resend.fail" ) );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

}
