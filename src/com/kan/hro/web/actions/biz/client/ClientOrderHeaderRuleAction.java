package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderHeaderRuleVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderRuleService;

public class ClientOrderHeaderRuleAction extends BaseAction
{
   public static String accessAction = "HRO_BIZ_CLIENT_ORDER_HEADER_RULE";

   /**
    * List Object
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
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );
         // ���Action Form
         final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = ( ClientOrderHeaderRuleVO ) form;
         clientOrderHeaderRuleVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientOrderHeaderRuleVO.getSubAction() != null && clientOrderHeaderRuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientOrderHeaderRuleVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientOrderHeaderRuleVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientOrderHeaderRuleVO.getSubAction() != null )
         {
            subAction = clientOrderHeaderRuleVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderHeaderRuleService.getClientOrderHeaderRuleVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pageListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pageListHolder );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            if ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().exportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // ��ʼ��PrintWrite����
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_HEADER_RULE" ) );
               out.flush();
               out.close();
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listClientOrderHeaderRule" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-21
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ��ø���������Id
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

      ( ( ClientOrderHeaderRuleVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ClientOrderHeaderRuleVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderHeaderRuleVO ) form ).setStatus( ClientOrderHeaderRuleVO.TRUE );
      // ���ʻ�
      ( ( ClientOrderHeaderRuleVO ) form ).reset( null, request );

      return mapping.findForward( "manageClientOrderHeaderRule" );
   }

   /**
    * To Object Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-21
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );

         // ��õ�ǰ����
         String orderHeaderRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderHeaderRuleId == null || orderHeaderRuleId.trim().isEmpty() )
         {
            orderHeaderRuleId = ( ( ClientOrderHeaderRuleVO ) form ).getOrderHeaderRuleId();
         }

         // ��õ�ǰ������Ӧ����
         final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( orderHeaderRuleId );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderHeaderRuleVO.reset( null, request );
         // ����Sub Action
         clientOrderHeaderRuleVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderHeaderRuleForm", clientOrderHeaderRuleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientOrderHeaderRule" );
   }

   /**  
    * Add Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );

            // ���ActionForm
            final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = ( ClientOrderHeaderRuleVO ) form;
            // ��ȡ��¼�û�
            clientOrderHeaderRuleVO.setCreateBy( getUserId( request, response ) );
            clientOrderHeaderRuleVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            clientOrderHeaderRuleVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �½�����
            clientOrderHeaderRuleService.insertClientOrderHeaderRule( clientOrderHeaderRuleVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Form����
         ( ( ClientOrderHeaderRuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );
            // ��õ�ǰ����
            final String orderHeaderRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( orderHeaderRuleId );
            // ��ȡ��¼�û�
            clientOrderHeaderRuleVO.update( ( ClientOrderHeaderRuleVO ) form );
            // �����Զ���Column
            clientOrderHeaderRuleVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientOrderHeaderRuleVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            clientOrderHeaderRuleService.updateClientOrderHeaderRule( clientOrderHeaderRuleVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form����
         ( ( ClientOrderHeaderRuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Object
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
      // no use
   }

   /**
    * Delete Object Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-10-19
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );

         // ��ȡ����
         final String clientOrderHeaderRuleId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderHeaderRuleId" ) );

         // ����������ö�ӦVO
         final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( clientOrderHeaderRuleId );
         clientOrderHeaderRuleVO.setModifyBy( getUserId( request, response ) );
         clientOrderHeaderRuleVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientOrderHeaderRuleService.deleteClientOrderHeaderRule( clientOrderHeaderRuleVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
         }
         else
         {
            deleteFailedAjax( out, null );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Object List
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
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );
         // ���Action Form
         ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = ( ClientOrderHeaderRuleVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderHeaderRuleVO.getSelectedIds() != null && !clientOrderHeaderRuleVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderHeaderRuleVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientOrderHeaderRuleId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderHeaderRuleVO clientOrderHeaderRuleVOForDel = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( clientOrderHeaderRuleId );
                  clientOrderHeaderRuleVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderHeaderRuleVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderHeaderRuleService.deleteClientOrderHeaderRule( clientOrderHeaderRuleVOForDel );
               }
            }
         }

         // ���Selected IDs����Action
         clientOrderHeaderRuleVO.setSelectedIds( "" );
         clientOrderHeaderRuleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
