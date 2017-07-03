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
import com.kan.hro.domain.biz.client.ClientOrderDetailSBRuleVO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailSBRuleService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientOrderDetailRuleAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientOrderDetailSBRuleAction extends BaseAction
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
         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );
         // ���Action Form
         final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = ( ClientOrderDetailSBRuleVO ) form;
         clientOrderDetailSBRuleVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientOrderDetailSBRuleVO.getSubAction() != null && clientOrderDetailSBRuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientOrderDetailSBRuleVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientOrderDetailSBRuleVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientOrderDetailSBRuleVO.getSubAction() != null )
         {
            subAction = clientOrderDetailSBRuleVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_DETAIL_SB_RULE" ) );
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
      return mapping.findForward( "listClientOrderDetailSBRule" );
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

         // ����Sub Action
         ( ( ClientOrderDetailSBRuleVO ) form ).setSubAction( CREATE_OBJECT );
         // ��ø��ڵ�����
         final String orderDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "orderDetailId" ), "UTF-8" ) );

         // ���OrderDetailId ����
         if ( orderDetailId != null && !orderDetailId.isEmpty() )
         {
            ( ( ClientOrderDetailSBRuleVO ) form ).setOrderDetailId( orderDetailId );
         }

         ( ( ClientOrderDetailSBRuleVO ) form ).setStatus( ClientOrderDetailSBRuleVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���½�����
      return mapping.findForward( "manageClientOrderDetailSBRule" );
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
         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

         // ��õ�ǰ����
         String sbRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( sbRuleId == null || sbRuleId.trim().isEmpty() )
         {
            sbRuleId = ( ( ClientOrderDetailSBRuleVO ) form ).getSbRuleId();
         }

         // ���ClientOrderDetailRuleVO
         final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( sbRuleId );
         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderDetailSBRuleVO.reset( null, request );
         clientOrderDetailSBRuleVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderDetailSBRuleForm", clientOrderDetailSBRuleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientOrderDetailSBRule" );
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
            final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

            // ���ActionForm
            final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = ( ClientOrderDetailSBRuleVO ) form;
            // ��ȡ��¼�û�
            clientOrderDetailSBRuleVO.setCreateBy( getUserId( request, response ) );
            clientOrderDetailSBRuleVO.setModifyBy( getUserId( request, response ) );

            // �½�����
            clientOrderDetailSBRuleService.insertClientOrderDetailSBRule( clientOrderDetailSBRuleVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Form����
         ( ( ClientOrderDetailSBRuleVO ) form ).reset();
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
            final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

            // ��õ�ǰ����
            final String sbRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���������Ӧ����
            final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( sbRuleId );

            // ��ȡ��¼�û�
            clientOrderDetailSBRuleVO.update( ( ClientOrderDetailSBRuleVO ) form );
            clientOrderDetailSBRuleVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            clientOrderDetailSBRuleService.updateClientOrderDetailSBRule( clientOrderDetailSBRuleVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form����
         ( ( ClientOrderDetailSBRuleVO ) form ).reset();
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
         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

         // ��ȡ����
         final String sbRuleId = KANUtil.decodeStringFromAjax( request.getParameter( "sbRuleId" ) );

         // ����������ö�ӦVO
         final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( sbRuleId );
         clientOrderDetailSBRuleVO.setModifyBy( getUserId( request, response ) );
         clientOrderDetailSBRuleVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientOrderDetailSBRuleService.deleteClientOrderDetailSBRule( clientOrderDetailSBRuleVO );

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
         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );
         // ���Action Form
         ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = ( ClientOrderDetailSBRuleVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderDetailSBRuleVO.getSelectedIds() != null && !clientOrderDetailSBRuleVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderDetailSBRuleVO.getSelectedIds().split( "," ) )
            {

               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String sbRuleId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderDetailSBRuleVO tempSBRuleVO = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( sbRuleId );
                  tempSBRuleVO.setModifyBy( getUserId( request, response ) );
                  tempSBRuleVO.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderDetailSBRuleService.deleteClientOrderDetailSBRule( tempSBRuleVO );
               }

            }
         }

         // ���Selected IDs����Action
         clientOrderDetailSBRuleVO.setSelectedIds( "" );
         clientOrderDetailSBRuleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
