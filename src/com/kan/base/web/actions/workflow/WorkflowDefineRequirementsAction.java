package com.kan.base.web.actions.workflow;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.AccountModuleDTO;
import com.kan.base.domain.workflow.WorkflowDefineDTO;
import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.domain.workflow.WorkflowModuleDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.service.inf.workflow.WorkflowDefineRequirementsService;
import com.kan.base.service.inf.workflow.WorkflowDefineService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class WorkflowDefineRequirementsAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ����Ƿ�Ajax����
         final boolean ajax = new Boolean( request.getParameter( "ajax" ) );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ���Action Form
         final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = ( WorkflowDefineRequirementsVO ) form;

         // �����Action��ɾ��
         if ( workflowDefineRequirementsVO.getSubAction() != null && workflowDefineRequirementsVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��Service�ӿ�
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );

         // ��������������������������ã���form��ȡ������ajax��������Σ�       
         String defineId = request.getParameter( "defineId" );
         if ( KANUtil.filterEmpty( defineId ) == null )
         {
            defineId = ( ( WorkflowDefineRequirementsVO ) form ).getDefineId();
         }
         else
         {
            defineId = KANUtil.decodeStringFromAjax( defineId );
         }

         // ���request�в�����workflowDefineForm
         if ( request.getAttribute( "workflowDefineForm" ) == null )
         {
            // ���WorkflowDefineVO
            final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( defineId );
            workflowDefineVO.reset( null, request );
            workflowDefineVO.setSubAction( VIEW_OBJECT );
            request.setAttribute( "workflowDefineForm", workflowDefineVO );
         }

         // loadWorkflowDefineObjectType
         loadWorkflowDefineObjectType( request );

         // ����defineId
         workflowDefineRequirementsVO.setDefineId( defineId );
         // �˴���ҳ����
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( workflowDefineRequirementsVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowDefineRequirementsService.getWorkflowDefineRequirementsVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );
         //Holder��д��Request����
         request.setAttribute( "workflowDefineRequirementsHolder", pagedListHolder );

         // Ĭ��˳��Ϊ1
         workflowDefineRequirementsVO.setColumnIndex( 1 );

         // Ajax Table����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ��� JSP
            return mapping.findForward( "listWorkflowDefineRequirementsTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���½�����
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
      {
         return mapping.findForward( "listWorkflowDefineRequirementsInHouse" );
      }
      else
      {
         return mapping.findForward( "listWorkflowDefineRequirements" );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      //      // ��øù�˾������ְ��
      //      final List< MappingVO > positionGrades = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionGrades( request.getLocale().getLanguage() );
      //      request.setAttribute( "positionGrades", positionGrades );

      // ����Sub Action
      ( ( WorkflowDefineRequirementsVO ) form ).setStatus( WorkflowDefineRequirementsVO.TRUE );
      ( ( WorkflowDefineRequirementsVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageWorkflowDefineRequirements" );
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
            final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );
            // ���ActionForm
            final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = ( WorkflowDefineRequirementsVO ) form;
            final String defineId = workflowDefineRequirementsVO.getDefineId();
            workflowDefineRequirementsVO.setDefineId( Cryptogram.decodeString( URLDecoder.decode( defineId, "UTF-8" ) ) );
            // ��ȡ��¼�û�  - ���ô����û�id
            workflowDefineRequirementsVO.setCreateBy( getUserId( request, response ) );
            // ��ȡ��¼�û��˻�  �����˻�id
            workflowDefineRequirementsVO.setAccountId( getAccountId( request, response ) );
            workflowDefineRequirementsVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            workflowDefineRequirementsService.insertWorkflowDefineRequirements( workflowDefineRequirementsVO );

            // ������ӳɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_REQUIREMENTS" );

            //���³�ʼ��AccounConstance��WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );
         }

         // ���Form����
         ( ( WorkflowDefineRequirementsVO ) form ).reset();

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
         final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );
         // ��õ�ǰ����
         String workflowDefineRequirementsId = KANUtil.decodeString( request.getParameter( "requirementId" ) );
         // ���������Ӧ����
         WorkflowDefineRequirementsVO workflowDefineRequirementsVO = workflowDefineRequirementsService.getWorkflowDefineRequirementsVOByRequirementsId( workflowDefineRequirementsId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         workflowDefineRequirementsVO.reset( null, request );

         workflowDefineRequirementsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowDefineRequirementsForm", workflowDefineRequirementsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageWorkflowDefineRequirements" );
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
         final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );

         // ��ȡ��ǰ����
         final String requirementId = KANUtil.decodeString( request.getParameter( "requirementId" ) );

         // ��ȡWorkflowDefineRequirementsVO
         final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = workflowDefineRequirementsService.getWorkflowDefineRequirementsVOByRequirementsId( requirementId );

         // ��ȡWorkflowDefineVO
         final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( workflowDefineRequirementsVO.getDefineId() );

         // ��ȡWorkflowDefineDTO
         final WorkflowDefineDTO workflowDefineDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getWorkflowDefineDTOByDefineId( workflowDefineRequirementsVO.getDefineId() );

         if ( workflowDefineDTO != null && workflowDefineDTO.getWorkflowDefineVO() != null )
         {
            WorkflowDefineAction.loadColumnVOs_toRequest( request, workflowDefineDTO.getWorkflowDefineVO().getWorkflowModuleId() );
         }
         else
         {
            if ( workflowDefineVO != null )
            {
               WorkflowDefineAction.loadColumnVOs_toRequest( request, workflowDefineVO.getWorkflowModuleId() );
            }
         }

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         workflowDefineRequirementsVO.reset( null, request );

         workflowDefineRequirementsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowDefineForm", workflowDefineVO );
         request.setAttribute( "workflowDefineRequirementsForm", workflowDefineRequirementsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageWorkflowDefineRequirementsForm" );
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
            final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );
            // ��õ�ǰ����
            final String workflowDefineRequirementsId = KANUtil.decodeString( request.getParameter( "requirementId" ) );
            // ���������Ӧ����
            final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = workflowDefineRequirementsService.getWorkflowDefineRequirementsVOByRequirementsId( workflowDefineRequirementsId );
            // װ�ؽ��洫ֵ
            workflowDefineRequirementsVO.update( ( WorkflowDefineRequirementsVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            workflowDefineRequirementsVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            workflowDefineRequirementsService.updateWorkflowDefineRequirements( workflowDefineRequirementsVO );

            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_REQUIREMENTS" );

            //���³�ʼ��AccounConstance��WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );
         }
         // ���Form����
         ( ( WorkflowDefineRequirementsVO ) form ).reset();

         // ��ת���б���� 
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
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );
         // ���Action Form
         WorkflowDefineRequirementsVO workflowDefineRequirementsVO = ( WorkflowDefineRequirementsVO ) form;
         final String selectedIdStr = request.getParameter( "selectedIds_req" );
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( selectedIdStr ) != null )
         {
            final String selectedIds[] = selectedIdStr.split( "," );
            workflowDefineRequirementsService.deleteWorkflowDefineRequirementsByRequirementsId( getUserId( request, response ), selectedIds );

            //���³�ʼ��AccounConstance��WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );
         }
         // ���Selected IDs����Action
         workflowDefineRequirementsVO.setSelectedIds( "" );
         workflowDefineRequirementsVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ���ع�������������
   private void loadWorkflowDefineObjectType( final HttpServletRequest request ) throws KANException
   {
      // ��ȡWorkflowDefineVO
      final WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) request.getAttribute( "workflowDefineForm" );

      // �������ٻ��ǼӰ�
      boolean isLeaveAccessAction = false, isOTAccessAction = false;

      // ��ȡWorkflowModuleDTO
      final WorkflowModuleDTO workflowModuleDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getWorkflowModuleDTOByWorkflowModuleId( workflowDefineVO.getWorkflowModuleId() );

      if ( workflowModuleDTO != null && workflowModuleDTO.getWorkflowModuleVO() != null )
      {
         AccountModuleDTO accountModuleDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getAccountModuleDTOByModuleId( workflowModuleDTO.getWorkflowModuleVO().getModuleId() );
         if ( accountModuleDTO != null )
         {
            String accessActionStr = accountModuleDTO.getModuleVO().getAccessAction();
            String accessActions[] = KANUtil.jasonArrayToStringArray( accessActionStr );
            if ( accessActions != null )
            {
               for ( String accessAction : accessActions )
               {
                  isLeaveAccessAction = isLeaveAccessAction || WorkflowService.isLeaveAccessAction( accessAction );
                  isOTAccessAction = isOTAccessAction || WorkflowService.isOTAccessAction( accessAction );
               }
            }
         }
      }

      request.setAttribute( "isLeaveAccessAction", isLeaveAccessAction );
      request.setAttribute( "isOTAccessAction", isOTAccessAction );
      request.setAttribute( "isLeaveOrOTAccessAction", isLeaveAccessAction || isOTAccessAction );
   }

}