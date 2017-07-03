package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderDetailRuleVO;
import com.kan.hro.domain.biz.client.ClientOrderDetailVO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailRuleService;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientOrderDetailRuleAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientOrderDetailRuleAction extends BaseAction
{

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
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );
         // ���Action Form
         final ClientOrderDetailRuleVO clientOrderDetailRuleVO = ( ClientOrderDetailRuleVO ) form;
         clientOrderDetailRuleVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientOrderDetailRuleVO.getSubAction() != null && clientOrderDetailRuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientOrderDetailRuleVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientOrderDetailRuleVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientOrderDetailRuleVO.getSubAction() != null )
         {
            subAction = clientOrderDetailRuleVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderDetailRuleService.getClientOrderDetailRuleVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_DETAIL_RULE" ) );
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
      return mapping.findForward( "listClientOrderDetailRule" );
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
   // Code review by Kevin Jin at 2013-10-21
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );

         // ����Sub Action
         ( ( ClientOrderDetailRuleVO ) form ).setSubAction( CREATE_OBJECT );
         // ��ø��ڵ�����
         final String orderDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "orderDetailId" ), "UTF-8" ) );

         // ���OrderDetailId ����
         if ( orderDetailId != null && !orderDetailId.isEmpty() )
         {
            ( ( ClientOrderDetailRuleVO ) form ).setOrderDetailId( orderDetailId );
            // ��ø��ڵ�������Ӧ����
            final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( orderDetailId );

            // ��õ�ǰItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( clientOrderDetailVO.getItemId() );

            // ItemVO��д��Request����
            request.setAttribute( "itemVO", itemVO );
         }

         ( ( ClientOrderDetailRuleVO ) form ).setStatus( ClientOrderDetailRuleVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���½�����
      return mapping.findForward( "manageClientOrderDetailRule" );
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
   // Code review by Kevin Jin at 2013-10-21
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

         // ��õ�ǰ����
         String orderDetailRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderDetailRuleId == null || orderDetailRuleId.trim().isEmpty() )
         {
            orderDetailRuleId = ( ( ClientOrderDetailRuleVO ) form ).getOrderDetailRuleId();
         }

         // ���ClientOrderDetailRuleVO
         final ClientOrderDetailRuleVO clientOrderDetailRuleVO = clientOrderDetailRuleService.getClientOrderDetailRuleVOByClientOrderDetailRuleId( orderDetailRuleId );
         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderDetailRuleVO.reset( null, request );
         clientOrderDetailRuleVO.setSubAction( VIEW_OBJECT );

         // ��ø��ڵ����
         final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailRuleVO.getOrderDetailId() );

         // ��õ�ǰItemVO
         final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( clientOrderDetailVO.getItemId() );

         // ItemVO��ActionFrom��д��Request����
         request.setAttribute( "itemVO", itemVO );
         request.setAttribute( "clientOrderDetailRuleForm", clientOrderDetailRuleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientOrderDetailRule" );
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
            final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

            // ���ActionForm
            final ClientOrderDetailRuleVO clientOrderDetailRuleVO = ( ClientOrderDetailRuleVO ) form;
            // ��ȡ��¼�û�
            clientOrderDetailRuleVO.setCreateBy( getUserId( request, response ) );
            clientOrderDetailRuleVO.setModifyBy( getUserId( request, response ) );

            // �½�����
            clientOrderDetailRuleService.insertClientOrderDetailRule( clientOrderDetailRuleVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Form����
         ( ( ClientOrderDetailRuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
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
            final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

            // ��õ�ǰ����
            final String orderDetailRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���������Ӧ����
            final ClientOrderDetailRuleVO clientOrderDetailRuleVO = clientOrderDetailRuleService.getClientOrderDetailRuleVOByClientOrderDetailRuleId( orderDetailRuleId );

            // ��ȡ��¼�û�
            clientOrderDetailRuleVO.update( ( ClientOrderDetailRuleVO ) form );
            clientOrderDetailRuleVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            clientOrderDetailRuleService.updateClientOrderDetailRule( clientOrderDetailRuleVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form����
         ( ( ClientOrderDetailRuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
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
   // Added by Kevin Jin at 2013-10-20
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

         // ��ȡ����
         final String clientOrderDetailRuleId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderDetailRuleId" ) );

         // ����������ö�ӦVO
         final ClientOrderDetailRuleVO clientOrderDetailRuleVO = clientOrderDetailRuleService.getClientOrderDetailRuleVOByClientOrderDetailRuleId( clientOrderDetailRuleId );
         clientOrderDetailRuleVO.setModifyBy( getUserId( request, response ) );
         clientOrderDetailRuleVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientOrderDetailRuleService.deleteClientOrderDetailRule( clientOrderDetailRuleVO );

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
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );
         // ���Action Form
         ClientOrderDetailRuleVO clientOrderDetailRuleVO = ( ClientOrderDetailRuleVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderDetailRuleVO.getSelectedIds() != null && !clientOrderDetailRuleVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderDetailRuleVO.getSelectedIds().split( "," ) )
            {

               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientOrderDetailRuleId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderDetailRuleVO clientOrderDetailRuleVOForDel = clientOrderDetailRuleService.getClientOrderDetailRuleVOByClientOrderDetailRuleId( clientOrderDetailRuleId );
                  clientOrderDetailRuleVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderDetailRuleVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderDetailRuleService.deleteClientOrderDetailRule( clientOrderDetailRuleVOForDel );
               }

            }
         }

         // ���Selected IDs����Action
         clientOrderDetailRuleVO.setSelectedIds( "" );
         clientOrderDetailRuleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
