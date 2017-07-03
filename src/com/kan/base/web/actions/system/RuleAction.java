/*
 * Created on 2013-05-28
 */
package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.RuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.RuleService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.system.RuleRender;

/**
 * @author Kevin Jin
 */
public class RuleAction extends BaseAction
{

   /**
    * List Rules
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
         final RuleService ruleService = ( RuleService ) getService( "ruleService" );
         // ���Action Form
         final RuleVO ruleVO = ( RuleVO ) form;

         // �����Action��ɾ���û��б�
         if ( ruleVO != null && ruleVO.getSubAction() != null && ruleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( ruleVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder ruleHolder = new PagedListHolder();

         // ���뵱ǰҳ
         ruleHolder.setPage( page );
         // ���뵱ǰֵ����
         ruleHolder.setObject( ruleVO );
         // ����ҳ���¼����
         ruleHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         ruleService.getRuleVOsByCondition( ruleHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( ruleHolder, request );

         // Holder��д��Request����
         request.setAttribute( "ruleHolder", ruleHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listRuleTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listRule" );
   }

   /**
    * To rule modify page
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
         final RuleService ruleService = ( RuleService ) getService( "ruleService" );
         // ��õ�ǰ����
         final String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "ruleId" ), "GBK" ) );
         // ���������Ӧ����
         RuleVO ruleVO = ruleService.getRuleVOByRuleId( ruleId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         ruleVO.reset( null, request );

         ruleVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "ruleForm", ruleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageRule" );
   }

   /**
    * To rule new page
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
      ( ( RuleVO ) form ).setStatus( RuleVO.TRUE );
      ( ( RuleVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageRule" );
   }

   /**
    * Add rule
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
            final RuleService ruleService = ( RuleService ) getService( "ruleService" );
            // ���ActionForm
            final RuleVO ruleVO = ( RuleVO ) form;

            // ��ȡ��¼�û�
            ruleVO.setCreateBy( getUserId( request, response ) );
            ruleVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            ruleService.insertRule( ruleVO );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }
         // ���form
         ( ( RuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify rule
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
            final RuleService ruleService = ( RuleService ) getService( "ruleService" );
            // ��õ�ǰ����
            final String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "ruleId" ), "GBK" ) );
            // ���������Ӧ����
            final RuleVO ruleVO = ruleService.getRuleVOByRuleId( ruleId );

            // װ�ؽ��洫ֵ
            ruleVO.update( ( RuleVO ) form );

            // ��ȡ��¼�û�
            ruleVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            ruleService.updateRule( ruleVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
         //���form
         ( ( RuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete rule
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
         final RuleService ruleService = ( RuleService ) getService( "ruleService" );
         final RuleVO ruleVO = new RuleVO();
         // ��õ�ǰ����
         String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "ruleId" ), "GBK" ) );

         // ɾ��������Ӧ����
         ruleVO.setRuleId( ruleId );
         ruleVO.setModifyBy( getUserId( request, response ) );
         ruleService.deleteRule( ruleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete rule list
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
         final RuleService ruleService = ( RuleService ) getService( "ruleService" );
         // ���Action Form
         RuleVO ruleVO = ( RuleVO ) form;
         // ����ѡ�е�ID
         if ( ruleVO.getSelectedIds() != null && !ruleVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : ruleVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               ruleVO.setRuleId( selectedId );
               ruleVO.setModifyBy( getUserId( request, response ) );
               ruleService.deleteRule( ruleVO );
            }
         }
         // ���Selected IDs����Action
         ruleVO.setSelectedIds( "" );
         ruleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * list_object_html_thinking_management
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_html_select( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ���ModuleId
         final String moduleId = request.getParameter( "moduleId" );

         // ���RuleType
         final String ruleType = request.getParameter( "ruleType" );

         // Render����
         out.println( RuleRender.getRuleSelectByModuleId( request.getLocale(), ruleType, moduleId ) );
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

}