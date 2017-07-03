/*
 * Created on 2013-05-13
 */
package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.WorkflowModuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.WorkflowModuleService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.system.RightRender;

/**
 * @author Jixiang.hu
 */
public class WorkflowModuleAction extends BaseAction
{

   /**
    * List WorkflowModule
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
         // ���Action Form
         final WorkflowModuleVO workflowModuleVO = ( WorkflowModuleVO ) form;

         boolean isDelete = workflowModuleVO != null && workflowModuleVO.getSubAction() != null && workflowModuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );

         // �����Action��ɾ����Ϣģ��
         if ( isDelete )
         {
            // ����ɾ����Ϣģ���Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ����������������򡢷�ҳ�򵼳�������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( workflowModuleVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder workflowModuleHolder = new PagedListHolder();

         // ���뵱ǰҳ
         workflowModuleHolder.setPage( page );
         // ���뵱ǰֵ����
         workflowModuleHolder.setObject( workflowModuleVO );
         // ����ҳ���¼����
         workflowModuleHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowModuleService.getWorkflowModuleVOsByCondition( workflowModuleHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( workflowModuleHolder, request );

         // Holder��д��Request����
         request.setAttribute( "workflowModuleHolder", workflowModuleHolder );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) || isDelete )
         {
            // Ajax��������ת
            return mapping.findForward( "listWorkflowModuleTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listWorkflowModule" );
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
         final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
         // ��õ�ǰ����
         String workflowModuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowModuleId" ), "UTF-8" ) );
         // ���������Ӧ����
         final WorkflowModuleVO workflowModuleVO = workflowModuleService.getWorkflowModuleVOByModuleId( workflowModuleId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         workflowModuleVO.reset( null, request );
         workflowModuleVO.setSubAction( VIEW_OBJECT );
         workflowModuleVO.setModuleTitle( KANConstants.getModuleTitleByModuleId( workflowModuleVO.getModuleId() ) );

         request.setAttribute( "workflowModuleForm", workflowModuleVO );
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
      ( ( WorkflowModuleVO ) form ).setStatus( WorkflowModuleVO.TRUE );
      ( ( WorkflowModuleVO ) form ).setSubAction( CREATE_OBJECT );
      request.setAttribute( "sysMoldueNamelList", "" );

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

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
            // ���ActionForm
            final WorkflowModuleVO workflowModuleVO = ( WorkflowModuleVO ) form;
            // ����systeId
            workflowModuleVO.setSystemId( KANConstants.SYSTEM_ID );
            // ��ȡ��¼�û�  - ���ô����û�id
            workflowModuleVO.setCreateBy( getUserId( request, response ) );
            // ��ȡ��¼�û��˻�  �����˻�id
            workflowModuleVO.setAccountId( getAccountId( request, response ) );
            workflowModuleVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            workflowModuleService.insertWorkflowModule( workflowModuleVO );

            //���³�ʼ��WorkflowModule
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            constants.initWorkflowModule();
         }

         // ���Form����
         ( ( WorkflowModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   public ActionForward list_Module_rightIds_html_checkBox( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         String workflowModuleId = request.getParameter( "workflowModuleId" );

         WorkflowModuleVO workflowModuleVO = null;
         for ( WorkflowModuleVO workflowModuleVOTemp : KANConstants.WORKFLOW_MOFDULE_VO )
         {
            if ( workflowModuleVOTemp.getWorkflowModuleId().equals( workflowModuleId ) )
            {
               workflowModuleVO = workflowModuleVOTemp;
               break;
            }
         }

         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();

         if ( workflowModuleVO != null )
         {
            String[] rightIdsArray = workflowModuleVO.getRightIdsArray();
            final String checkBoxName = request.getParameter( "checkBoxName" );
            final String rightIds = request.getParameter( "rightIds" );
            final String selectRightIds[] = KANUtil.jasonArrayToStringArray( rightIds );

            out.println( RightRender.getRightHorizontalMultipleChoice( request, rightIdsArray, checkBoxName, selectRightIds ) );

         }
         else
         {
            out.println( "<font color=red >" + KANUtil.getProperty( request.getLocale(), "workflow.define.trigger.right.load.fial" ) + "</font>" );
         }
         out.flush();
         out.close();
         // Ajax��������ת
         return null;

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
            final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
            // ��õ�ǰ����
            final String workflowModuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowModuleId" ), "UTF-8" ) );
            // ���������Ӧ����
            final WorkflowModuleVO workflowModuleVO = workflowModuleService.getWorkflowModuleVOByModuleId( workflowModuleId );
            // װ�ؽ��洫ֵ
            workflowModuleVO.update( ( WorkflowModuleVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            workflowModuleVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            workflowModuleService.updateWorkflowModule( workflowModuleVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            //���³�ʼ��WorkflowModule
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            constants.initWorkflowModule();
         }
         // ���Form����
         ( ( WorkflowModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
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
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
         final WorkflowModuleVO workflowModuleVO = new WorkflowModuleVO();
         // ��õ�ǰ����
         final String workflowModuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowModuleId" ), "GBK" ) );

         // ɾ��������Ӧ����
         workflowModuleVO.setWorkflowModuleId( workflowModuleId );
         workflowModuleVO.setModifyBy( getUserId( request, response ) );
         workflowModuleService.deleteWorkflowModule( workflowModuleVO );
         //���³�ʼ��WorkflowModule
         final KANConstants constants = ( KANConstants ) getService( "constants" );
         constants.initWorkflowModule();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
         // ���Action Form
         WorkflowModuleVO workflowModuleVO = ( WorkflowModuleVO ) form;
         // ����ѡ�е�ID
         if ( workflowModuleVO.getSelectedIds() != null && !workflowModuleVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : workflowModuleVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               workflowModuleVO.setWorkflowModuleId( selectedId );
               workflowModuleVO.setModifyBy( getUserId( request, response ) );
               workflowModuleService.deleteWorkflowModule( workflowModuleVO );
            }

            //���³�ʼ��WorkflowModule
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            constants.initWorkflowModule();
         }
         // ���Selected IDs����Action
         workflowModuleVO.setSelectedIds( "" );
         workflowModuleVO.setSubAction( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}