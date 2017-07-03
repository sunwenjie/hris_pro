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
import com.kan.hro.domain.biz.client.ClientOrderOtherVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderOtherService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientOrderOtherAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientOrderOtherAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_ORDER_OTHER";
   public final static String accessAction_in_house = "HRO_BIZ_CLIENT_ORDER_OTHER_IN_HOUSE";

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
         final ClientOrderOtherService clientOrderOtherService = ( ClientOrderOtherService ) getService( "clientOrderOtherService" );
         // ���Action Form
         final ClientOrderOtherVO clientOrderOtherVO = ( ClientOrderOtherVO ) form;
         clientOrderOtherVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientOrderOtherVO.getSubAction() != null && clientOrderOtherVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientOrderOtherVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientOrderOtherVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientOrderOtherVO.getSubAction() != null )
         {
            subAction = clientOrderOtherVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderOtherService.getClientOrderOtherVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_Other" ) );
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
      return mapping.findForward( "listClientOrderOther" );
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

      ( ( ClientOrderOtherVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ClientOrderOtherVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderOtherVO ) form ).setBase( "0.00" );
      ( ( ClientOrderOtherVO ) form ).setResultCap( "0.00" );
      ( ( ClientOrderOtherVO ) form ).setResultFloor( "0.00" );
      ( ( ClientOrderOtherVO ) form ).setCycle( "1" );
      ( ( ClientOrderOtherVO ) form ).setStartDate( clientOrderHeaderVO.getStartDate() );
      ( ( ClientOrderOtherVO ) form ).setEndDate( clientOrderHeaderVO.getEndDate() );
      ( ( ClientOrderOtherVO ) form ).setStatus( ClientOrderOtherVO.TRUE );

      // ���ʻ�
      ( ( ClientOrderOtherVO ) form ).reset( null, request );

      // Attribute Setting
      request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );

      // ��ת���½�����
      return mapping.findForward( "manageClientOrderOther" );
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
         final ClientOrderOtherService clientOrderOtherService = ( ClientOrderOtherService ) getService( "clientOrderOtherService" );

         // ��õ�ǰ����
         String orderOtherId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderOtherId == null || orderOtherId.trim().isEmpty() )
         {
            orderOtherId = ( ( ClientOrderOtherVO ) form ).getOrderOtherId();
         }

         // �����������Ҷ�Ӧ��clientOrderOtherVO
         final ClientOrderOtherVO clientOrderOtherVO = clientOrderOtherService.getClientOrderOtherVOByClientOrderOtherId( orderOtherId );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderOtherVO.reset( null, request );
         // ����Sub Action
         clientOrderOtherVO.setSubAction( VIEW_OBJECT );

         // Attribute Setting
         request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderOtherVO.getOrderHeaderId() ) );
         request.setAttribute( "clientOrderOtherForm", clientOrderOtherVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientOrderOther" );
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
            final ClientOrderOtherService clientOrderOtherService = ( ClientOrderOtherService ) getService( "clientOrderOtherService" );

            // ���ActionForm
            final ClientOrderOtherVO clientOrderOtherVO = ( ClientOrderOtherVO ) form;
            // ��ȡ��¼�û�
            clientOrderOtherVO.setCreateBy( getUserId( request, response ) );
            clientOrderOtherVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            clientOrderOtherVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �½�����
            clientOrderOtherService.insertClientOrderOther( clientOrderOtherVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, clientOrderOtherVO, Operate.ADD, clientOrderOtherVO.getOrderOtherId(), null );
         }

         // ���Form����
         ( ( ClientOrderOtherVO ) form ).reset();
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
            final ClientOrderOtherService clientOrderOtherService = ( ClientOrderOtherService ) getService( "clientOrderOtherService" );
            // ��õ�ǰ����
            final String orderOtherId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final ClientOrderOtherVO clientOrderOtherVO = clientOrderOtherService.getClientOrderOtherVOByClientOrderOtherId( orderOtherId );
            // ��ȡ��¼�û�
            clientOrderOtherVO.update( ( ClientOrderOtherVO ) form );
            clientOrderOtherVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            clientOrderOtherVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientOrderOtherVO.reset( mapping, request );
            // �޸Ķ���
            clientOrderOtherService.updateClientOrderOther( clientOrderOtherVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, clientOrderOtherVO, Operate.MODIFY, clientOrderOtherVO.getOrderOtherId(), null );
         }

         // ���Form����
         ( ( ClientOrderOtherVO ) form ).reset();
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
         final ClientOrderOtherService clientOrderOtherService = ( ClientOrderOtherService ) getService( "clientOrderOtherService" );

         // ��ȡ����
         final String clientOrderOtherId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderOtherId" ) );

         // ����������ö�ӦVO
         final ClientOrderOtherVO clientOrderOtherVO = clientOrderOtherService.getClientOrderOtherVOByClientOrderOtherId( clientOrderOtherId );
         clientOrderOtherVO.setModifyBy( getUserId( request, response ) );
         clientOrderOtherVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientOrderOtherService.deleteClientOrderOther( clientOrderOtherVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, clientOrderOtherVO, Operate.DELETE, clientOrderOtherVO.getOrderOtherId(), "ajax delete" );
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
         final ClientOrderOtherService clientOrderOtherService = ( ClientOrderOtherService ) getService( "clientOrderOtherService" );
         // ���Action Form
         ClientOrderOtherVO clientOrderOtherVO = ( ClientOrderOtherVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderOtherVO.getSelectedIds() != null && !clientOrderOtherVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderOtherVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientOrderOtherId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderOtherVO clientOrderOtherVOForDel = clientOrderOtherService.getClientOrderOtherVOByClientOrderOtherId( clientOrderOtherId );
                  clientOrderOtherVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderOtherVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderOtherService.deleteClientOrderOther( clientOrderOtherVOForDel );
               }
            }

            insertlog( request, clientOrderOtherVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( clientOrderOtherVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         clientOrderOtherVO.setSelectedIds( "" );
         clientOrderOtherVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
