/*
 * Created on 2013-05-28
 */
package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.domain.system.RightVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.ModuleService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.system.ModuleRender;
import com.kan.base.web.renders.system.RightRender;

/**
 * @author Kevin Jin
 */
public class ModuleAction extends BaseAction
{
   /**
    * To Account Module Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_accountModuleModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         return mapping.findForward( "accountSetting" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * Modify Account Module
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_accountModule_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // ��ȡǰ�˴�������ModuleVO
         final ModuleVO moduleVO = ( ModuleVO ) form;

         // ��ȡ��ǰѡ�е�moduleId
         final String[] moduleIdArray = moduleVO.getModuleIdArray();

         // ��ȡҪ�޸ĵ�moduleVO��Ӧ��moduleId
         final String moduleId = moduleIdArray[ 0 ];

         if ( moduleId != null && !"".equals( moduleId ) )
         {
            // moduleVO����moduleId
            moduleVO.setModuleId( moduleId );

            // ��ȡ��¼�û�
            moduleVO.setModifyBy( getUserId( request, response ) );

            // ת��Array����洢���ݿ�
            moduleVO.setRightIds( KANUtil.toJasonArray( moduleVO.getRightIdArray() ) );
            moduleVO.setRuleIds( KANUtil.toJasonArray( moduleVO.getRuleIdArray() ) );

            // �޸Ķ���
            moduleService.updateAccountModuleRelation( moduleVO );
         }

         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

         constants.initModule();

         // ���Form�л�������ݣ�����List��������
         ( ( ModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����,������תҳ��
      return mapping.findForward( "" );
   }

   /**
    * List Modules
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
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         // ���Action Form
         final ModuleVO moduleVO = ( ModuleVO ) form;

         // �����Action��ɾ���û��б�
         if ( moduleVO != null && moduleVO.getSubAction() != null && moduleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ����������������򡢷�ҳ�򵼳�������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( moduleVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder moduleHolder = new PagedListHolder();

         // ���뵱ǰҳ
         moduleHolder.setPage( page );
         // ���뵱ǰֵ����
         moduleHolder.setObject( moduleVO );
         // ����ҳ���¼����
         moduleHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         moduleService.getModuleVOsByCondition( moduleHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( moduleHolder, request );

         // Holder��д��Request����
         request.setAttribute( "moduleHolder", moduleHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listModuleTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listModule" );
   }

   /**
    * To Module Modify
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
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         // ��õ�ǰ����
         String moduleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "moduleId" ), "GBK" ) );
         // ���������Ӧ����
         final ModuleVO moduleVO = moduleService.getModuleVOByModuleId( moduleId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         moduleVO.reset( null, request );
         moduleVO.setSubAction( VIEW_OBJECT );
         // ������Request��
         request.setAttribute( "moduleForm", moduleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageModule" );
   }

   /**
    * To Module Create
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

      // ����Sub Action
      ( ( ModuleVO ) form ).setStatus( ModuleVO.TRUE );
      ( ( ModuleVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageModule" );
   }

   /**
    * Add Module
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
            final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // ���ActionForm
            final ModuleVO moduleVO = ( ModuleVO ) form;

            // ��ȡ��¼�û�
            moduleVO.setCreateBy( getUserId( request, response ) );
            moduleVO.setModifyBy( getUserId( request, response ) );
            // ת��Array����洢���ݿ�
            moduleVO.setRightIds( KANUtil.toJasonArray( moduleVO.getRightIdArray() ) );
            moduleVO.setRuleIds( KANUtil.toJasonArray( moduleVO.getRuleIdArray() ) );
            // �½�����
            moduleService.insertModule( moduleVO );

            // ���¼���Module
            constants.initModule();

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Form�л�������ݣ�����List��������
         ( ( ModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify Module
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
            final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // ��õ�ǰ����
            final String moduleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "moduleId" ), "GBK" ) );
            // ���������Ӧ����
            final ModuleVO moduleVO = moduleService.getModuleVOByModuleId( moduleId );

            // װ�ؽ��洫ֵ
            moduleVO.update( ( ModuleVO ) form );

            // ��ȡ��¼�û�
            moduleVO.setModifyBy( getUserId( request, response ) );
            // ת��Array����洢���ݿ�
            moduleVO.setRightIds( KANUtil.toJasonArray( moduleVO.getRightIdArray() ) );
            moduleVO.setRuleIds( KANUtil.toJasonArray( moduleVO.getRuleIdArray() ) );

            // �޸Ķ���
            moduleService.updateModule( moduleVO );

            // ���¼���Module
            constants.initModule();

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form�л�������ݣ�����List��������
         ( ( ModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete Module
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         final ModuleVO moduleVO = new ModuleVO();
         // ��õ�ǰ����
         String moduleId = request.getParameter( "moduleId" );

         // ɾ��������Ӧ����
         moduleVO.setModuleId( moduleId );
         moduleVO.setModifyBy( getUserId( request, response ) );
         moduleService.deleteModule( moduleVO );

         // ���¼���Module
         constants.initModule();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Module List
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
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // ���Action Form
         ModuleVO moduleVO = ( ModuleVO ) form;
         // ����ѡ�е�ID
         if ( moduleVO.getSelectedIds() != null && !moduleVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : moduleVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               moduleVO.setModuleId( selectedId );
               moduleVO.setModifyBy( getUserId( request, response ) );
               moduleService.deleteModule( moduleVO );
            }
         }
         // ���Selected IDs����Action
         moduleVO.setSelectedIds( "" );
         moduleVO.setSubAction( "" );

         // ���¼���Module
         constants.initModule();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Authority Combo HTML for Position
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_authority_combo_position_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ���PositionId��ModuleId
         String positionId = request.getParameter( "positionId" );
         final String moduleId = request.getParameter( "moduleId" );

         // PostionId����
         if ( positionId != null && !positionId.trim().equals( "" ) )
         {
            positionId = Cryptogram.decodeString( URLDecoder.decode( positionId, "UTF-8" ) );
         }

         if ( moduleId != null && !moduleId.trim().equals( "" ) )
         {
            out.println( ModuleRender.getAuthorityComboByPositionId( request, positionId, moduleId ) );
         }

         out.flush();
         out.close();

         // Ajax��������ת
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Authority Combo HTML for Group
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_authority_combo_group_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ���GroupId��ModuleId
         String groupId = request.getParameter( "groupId" );
         final String moduleId = request.getParameter( "moduleId" );

         // GroupId����
         if ( groupId != null && !groupId.trim().equals( "" ) )
         {
            groupId = Cryptogram.decodeString( URLDecoder.decode( groupId, "UTF-8" ) );
         }

         if ( moduleId != null && !moduleId.trim().equals( "" ) )
         {
            out.println( ModuleRender.getAuthorityComboByGroupId( request, groupId, moduleId ) );
         }

         out.flush();
         out.close();

         // Ajax��������ת
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Authority Combo for Account
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_authority_combo_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ���ModuleId�������ж��Ƿ���Ա����ʾ��
         final String moduleId = request.getParameter( "moduleId" );

         if ( moduleId != null && !moduleId.trim().equals( "" ) )
         {
            out.println( ModuleRender.getAuthorityComboByAccountId( request, moduleId ) );
         }

         out.flush();
         out.close();

         // Ajax��������ת
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List ModuleBaseViews by Jason format
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( KANConstants.MODULE_BASEVIEW );

         // Send to client
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

   public ActionForward list_Module_rightIds_html_checkBox( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         String moduleId = request.getParameter( "moduleId" );

         ModuleDTO moduleDTO = KANConstants.getModuleDTOByModuleId( moduleId );

         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();
         if ( moduleDTO != null )
         {
            List< RightVO > rightVOs = moduleDTO.getModuleVO().getRightVOs();
            if ( rightVOs != null )
            {
               final String checkBoxName = request.getParameter( "checkBoxName" );
               final String rightIds = request.getParameter( "rightIds" );
               final String selectRightIds[] = KANUtil.jasonArrayToStringArray( rightIds );

               // Render����
               // ���ε��鿴���б��Ȩ��
               //final String exceptRightIds[] = { "1", "2", "3", "4", "5" };
               out.println( RightRender.getRightHorizontalMultipleChoice( request, rightVOs, checkBoxName, selectRightIds ) );
            }
            else
            {
               out.println( "<font color=red >û�п�ѡ��Ȩ�ޣ�</font>" );
            }
         }
         else
         {
            out.println( "<font color=red >δ�ҵ���Ӧģ�飡</font>" );
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

}