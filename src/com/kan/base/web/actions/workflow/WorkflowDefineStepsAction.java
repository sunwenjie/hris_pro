package com.kan.base.web.actions.workflow;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.workflow.WorkflowDefineService;
import com.kan.base.service.inf.workflow.WorkflowDefineStepsService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class WorkflowDefineStepsAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�Ƿ�Ajax����
         final boolean ajax = new Boolean( request.getParameter( "ajax" ) );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ��ȡ��ǰҳ
         final String page = request.getParameter( "page" );

         // ��ȡAction Form
         final WorkflowDefineStepsVO workflowDefineStepsVO = ( WorkflowDefineStepsVO ) form;

         // �����Action��ɾ��
         if ( workflowDefineStepsVO.getSubAction() != null && workflowDefineStepsVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��Service�ӿ�
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );

         // ��ȡ�����������������������ã���form��ȡ������ajax��������Σ�       
         String defineId = request.getParameter( "defineId" );
         if ( KANUtil.filterEmpty( defineId ) == null )
         {
            defineId = ( ( WorkflowDefineStepsVO ) form ).getDefineId();
         }
         else
         {
            defineId = KANUtil.decodeStringFromAjax( defineId );
         }

         // ���request�в�����workflowDefineForm
         if ( request.getAttribute( "workflowDefineForm" ) == null )
         {
            // ��ȡWorkflowDefineVO
            final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( defineId );
            workflowDefineVO.reset( null, request );
            workflowDefineVO.setSubAction( VIEW_OBJECT );
            request.setAttribute( "workflowDefineForm", workflowDefineVO );
         }

         // ����defineId
         workflowDefineStepsVO.setDefineId( defineId );
         // �˴���ҳ����
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( workflowDefineStepsVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowDefineStepsService.getWorkflowDefineStepsVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );
         //Holder��д��Request����
         request.setAttribute( "workflowDefineStepsHolder", pagedListHolder );

         // Ĭ��Ϊ����Ϊ1
         workflowDefineStepsVO.setStepIndex( 1 );

         // Ajax Table����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ��� JSP
            return mapping.findForward( "listWorkflowDefineStepsTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���½�����
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
      {
         return mapping.findForward( "listWorkflowDefineStepsInHouse" );
      }
      else
      {
         return mapping.findForward( "listWorkflowDefineSteps" );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      //      // ��ȡ�ù�˾������ְ��
      //      final List< MappingVO > positionGrades = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionGrades( request.getLocale().getLanguage() );
      //      request.setAttribute( "positionGrades", positionGrades );

      // ����Sub Action
      ( ( WorkflowDefineStepsVO ) form ).setStatus( WorkflowDefineStepsVO.TRUE );
      ( ( WorkflowDefineStepsVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageWorkflowDefineSteps" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
            // ��ȡActionForm
            final WorkflowDefineStepsVO workflowDefineStepsVO = ( WorkflowDefineStepsVO ) form;
            final String defineId = workflowDefineStepsVO.getDefineId();
            workflowDefineStepsVO.setDefineId( Cryptogram.decodeString( URLDecoder.decode( defineId, "UTF-8" ) ) );
            // ��ȡ��¼�û�  - ���ô����û�id
            workflowDefineStepsVO.setCreateBy( getUserId( request, response ) );
            // ��ȡ��¼�û��˻�  �����˻�id
            workflowDefineStepsVO.setAccountId( getAccountId( request, response ) );
            workflowDefineStepsVO.setModifyBy( getUserId( request, response ) );

            if ( KANUtil.filterEmpty( workflowDefineStepsVO.getStaffId() ) == null )
            {
               workflowDefineStepsVO.setStaffId( "0" );
            }
            if ( KANUtil.filterEmpty( workflowDefineStepsVO.getPositionId() ) == null )
            {
               workflowDefineStepsVO.setPositionId( "0" );
            }

            // �½�����
            workflowDefineStepsService.insertWorkflowDefineSteps( workflowDefineStepsVO );

            // ������ӳɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_STEPS" );

            //���³�ʼ��AccounConstance��WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineStepsVO, Operate.ADD, workflowDefineStepsVO.getStepId(), null );
         }

         // ���Form����
         ( ( WorkflowDefineStepsVO ) form ).reset();
         request.getRequestDispatcher( "workflowDefineAction.do?proc=to_objectModify" ).forward( request, response );

         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
         // ��ȡ��ǰ����
         String workflowDefineStepsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "UTF-8" ) );
         // ��ȡ������Ӧ����
         WorkflowDefineStepsVO workflowDefineStepsVO = workflowDefineStepsService.getWorkflowDefineStepsVOByStepsId( workflowDefineStepsId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         workflowDefineStepsVO.reset( null, request );

         workflowDefineStepsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowDefineStepsForm", workflowDefineStepsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageWorkflowDefineSteps" );
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );

         // ��ȡ��ǰ����
         final String workflowDefineStepsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "UTF-8" ) );

         // ��ȡWorkflowDefineStepsVO
         final WorkflowDefineStepsVO workflowDefineStepsVO = workflowDefineStepsService.getWorkflowDefineStepsVOByStepsId( workflowDefineStepsId );

         // ��ȡWorkflowDefineVO
         final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( workflowDefineStepsVO.getDefineId() );

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         workflowDefineStepsVO.reset( null, request );

         workflowDefineStepsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowDefineForm", workflowDefineVO );
         request.setAttribute( "workflowDefineStepsForm", workflowDefineStepsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageWorkflowDefineStepsForm" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
            // ��ȡ��ǰ����
            final String workflowDefineStepsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "GBK" ) );
            // ��ȡ������Ӧ����
            final WorkflowDefineStepsVO workflowDefineStepsVO = workflowDefineStepsService.getWorkflowDefineStepsVOByStepsId( workflowDefineStepsId );
            // װ�ؽ��洫ֵ
            workflowDefineStepsVO.update( ( WorkflowDefineStepsVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            workflowDefineStepsVO.setModifyBy( getUserId( request, response ) );
            if ( KANUtil.filterEmpty( workflowDefineStepsVO.getStaffId() ) == null )
            {
               workflowDefineStepsVO.setStaffId( "0" );
            }
            if ( KANUtil.filterEmpty( workflowDefineStepsVO.getPositionId() ) == null )
            {
               workflowDefineStepsVO.setPositionId( "0" );
            }
            // �޸Ķ���
            workflowDefineStepsService.updateWorkflowDefineSteps( workflowDefineStepsVO );

            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_STEPS" );

            //���³�ʼ��AccounConstance��WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineStepsVO, Operate.MODIFY, workflowDefineStepsVO.getStepId(), null );
         }
         // ���Form����
         ( ( WorkflowDefineStepsVO ) form ).reset();

         request.getRequestDispatcher( "workflowDefineAction.do?proc=to_objectModify" ).forward( request, response );

         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
         final WorkflowDefineStepsVO workflowDefineStepsVO = new WorkflowDefineStepsVO();
         // ��ȡ��ǰ����
         final String stepId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "GBK" ) );

         // ɾ��������Ӧ����
         workflowDefineStepsVO.setStepId( stepId );
         workflowDefineStepsVO.setModifyBy( getUserId( request, response ) );
         workflowDefineStepsService.deleteWorkflowDefineSteps( workflowDefineStepsVO );
         constantsInit( "initWorkflow", getAccountId( request, response ) );

         insertlog( request, workflowDefineStepsVO, Operate.DELETE, stepId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
         // ��ȡAction Form
         WorkflowDefineStepsVO workflowDefineStepsVO = ( WorkflowDefineStepsVO ) form;
         final String selectedIdStr = request.getParameter( "selectedIds_steps" );
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( selectedIdStr ) != null )
         {
            final String selectedIds[] = selectedIdStr.split( "," );
            workflowDefineStepsService.deleteWorkflowDefineStepsByStepsId( getUserId( request, response ), selectedIds );

            //���³�ʼ��AccounConstance��WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineStepsVO, Operate.DELETE, null, selectedIdStr );
         }
         // ���Selected IDs����Action
         workflowDefineStepsVO.setSelectedIds( "" );
         workflowDefineStepsVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}