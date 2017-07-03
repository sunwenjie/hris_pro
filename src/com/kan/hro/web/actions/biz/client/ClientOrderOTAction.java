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
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderOTVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderOTService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientOrderOTAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientOrderOTAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_ORDER_OT";
   public final static String accessAction_in_house = "HRO_BIZ_CLIENT_ORDER_OT_IN_HOUSE";

   /**
    * List client Order Header
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
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );
         // ���Action Form
         final ClientOrderOTVO clientOrderOTVO = ( ClientOrderOTVO ) form;
         clientOrderOTVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientOrderOTVO.getSubAction() != null && clientOrderOTVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientOrderOTVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientOrderOTVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientOrderOTVO.getSubAction() != null )
         {
            subAction = clientOrderOTVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderOTService.getClientOrderOTVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_OT" ) );
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
      return mapping.findForward( "listClientOrderOT" );
   }

   /**
    * To object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ��ʼ��Service�ӿ�
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

      // ���Order Header Id
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

      // ���ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

      ( ( ClientOrderOTVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ClientOrderOTVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderOTVO ) form ).setBase( "0.00" );
      ( ( ClientOrderOTVO ) form ).setDiscount( "100.00" );
      ( ( ClientOrderOTVO ) form ).setMultiple( "1" );
      ( ( ClientOrderOTVO ) form ).setResultCap( "0.00" );
      ( ( ClientOrderOTVO ) form ).setResultFloor( "0.00" );
      ( ( ClientOrderOTVO ) form ).setStartDate( clientOrderHeaderVO.getStartDate() );
      ( ( ClientOrderOTVO ) form ).setEndDate( clientOrderHeaderVO.getEndDate() );
      ( ( ClientOrderOTVO ) form ).setStatus( ClientOrderOTVO.TRUE );
      ( ( ClientOrderOTVO ) form ).reset( null, request );

      // Attribute Setting
      request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );

      // ��ת���½�����
      return mapping.findForward( "manageClientOrderOT" );
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
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

         // ��õ�ǰ����
         String orderOTId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderOTId == null || orderOTId.trim().isEmpty() )
         {
            orderOTId = ( ( ClientOrderOTVO ) form ).getOrderOTId();
         }

         // �����������Ҷ�Ӧ��clientOrderOTVO
         final ClientOrderOTVO clientOrderOTVO = clientOrderOTService.getClientOrderOTVOByClientOrderOTId( orderOTId );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderOTVO.reset( null, request );
         // ����Sub Action
         clientOrderOTVO.setSubAction( VIEW_OBJECT );

         // Attribute Setting
         request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderOTVO.getOrderHeaderId() ) );
         request.setAttribute( "clientOrderOTForm", clientOrderOTVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientOrderOT" );
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
            final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

            // ���ActionForm
            final ClientOrderOTVO clientOrderOTVO = ( ClientOrderOTVO ) form;
            // ��ȡ��¼�û�
            clientOrderOTVO.setCreateBy( getUserId( request, response ) );
            clientOrderOTVO.setModifyBy( getUserId( request, response ) );

            // �½�����
            clientOrderOTService.insertClientOrderOT( clientOrderOTVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, clientOrderOTVO, Operate.ADD, clientOrderOTVO.getOrderOTId(), null );
         }

         // ���Form����
         ( ( ClientOrderOTVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת����ѯ����
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
            final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

            // ��õ�ǰ����
            final String orderOTId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���������Ӧ����
            final ClientOrderOTVO clientOrderOTVO = clientOrderOTService.getClientOrderOTVOByClientOrderOTId( orderOTId );

            // ��ȡ��¼�û�
            clientOrderOTVO.update( ( ClientOrderOTVO ) form );
            clientOrderOTVO.setModifyBy( getUserId( request, response ) );
            clientOrderOTVO.reset( mapping, request );

            // �޸Ķ���
            clientOrderOTService.updateClientOrderOT( clientOrderOTVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, clientOrderOTVO, Operate.MODIFY, clientOrderOTVO.getOrderOTId(), null );
         }

         // ���Form����
         ( ( ClientOrderOTVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת����ѯ����
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
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

         // ��ȡ����
         final String clientOrderOTId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderOTId" ) );

         // ����������ö�ӦVO
         final ClientOrderOTVO clientOrderOTVO = clientOrderOTService.getClientOrderOTVOByClientOrderOTId( clientOrderOTId );
         clientOrderOTVO.setModifyBy( getUserId( request, response ) );
         clientOrderOTVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientOrderOTService.deleteClientOrderOT( clientOrderOTVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, clientOrderOTVO, Operate.DELETE, clientOrderOTVO.getOrderOTId(), "ajax delete" );
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
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );
         // ���Action Form
         ClientOrderOTVO clientOrderOTVO = ( ClientOrderOTVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderOTVO.getSelectedIds() != null && !clientOrderOTVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderOTVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientOrderOTId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderOTVO clientOrderOTVOForDel = clientOrderOTService.getClientOrderOTVOByClientOrderOTId( clientOrderOTId );
                  clientOrderOTVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderOTVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderOTService.deleteClientOrderOT( clientOrderOTVOForDel );
               }

               insertlog( request, clientOrderOTVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( clientOrderOTVO.getSelectedIds() ) );
            }
         }

         // ���Selected IDs����Action
         clientOrderOTVO.setSelectedIds( "" );
         clientOrderOTVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
