package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

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
import com.kan.hro.domain.biz.client.ClientOrderLeaveVO;
import com.kan.hro.service.inf.biz.client.ClientOrderLeaveService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientOrderLeaveAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientOrderLeaveAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_ORDER_LEAVE";
   public final static String accessAction_in_house = "HRO_BIZ_CLIENT_ORDER_LEAVE_IN_HOUSE";

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
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );
         // ���Action Form
         final ClientOrderLeaveVO clientOrderLeaveVO = ( ClientOrderLeaveVO ) form;
         clientOrderLeaveVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientOrderLeaveVO.getSubAction() != null && clientOrderLeaveVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientOrderLeaveVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientOrderLeaveVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientOrderLeaveVO.getSubAction() != null )
         {
            subAction = clientOrderLeaveVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderLeaveService.getClientOrderLeaveVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_LEAVE" ) );
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
      return mapping.findForward( "listClientOrderLeave" );
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

      // ���OrderHeaderId
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

      ( ( ClientOrderLeaveVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ClientOrderLeaveVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderLeaveVO ) form ).setLegalQuantity( "0" );
      ( ( ClientOrderLeaveVO ) form ).setBenefitQuantity( "0" );
      ( ( ClientOrderLeaveVO ) form ).setCycle( "1" );
      ( ( ClientOrderLeaveVO ) form ).setProbationUsing( ClientOrderLeaveVO.TRUE );
      ( ( ClientOrderLeaveVO ) form ).setDelayUsing( ClientOrderLeaveVO.FALSE );
      ( ( ClientOrderLeaveVO ) form ).setStatus( ClientOrderLeaveVO.TRUE );

      // ���ʻ�
      ( ( ClientOrderLeaveVO ) form ).reset( null, request );

      // ��ת���½�����
      return mapping.findForward( "manageClientOrderLeave" );
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
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );

         // ��õ�ǰ����
         String orderLeaveId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderLeaveId == null || orderLeaveId.trim().isEmpty() )
         {
            orderLeaveId = ( ( ClientOrderLeaveVO ) form ).getOrderLeaveId();
         }

         // ���ClientOrderLeaveVO
         final ClientOrderLeaveVO clientOrderLeaveVO = clientOrderLeaveService.getClientOrderLeaveVOByClientOrderLeaveId( orderLeaveId );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderLeaveVO.reset( null, request );
         // ����Sub Action
         clientOrderLeaveVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderLeaveForm", clientOrderLeaveVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientOrderLeave" );
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
            final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );

            // ���ActionForm
            final ClientOrderLeaveVO clientOrderLeaveVO = ( ClientOrderLeaveVO ) form;
            // ��ȡ��¼�û�
            clientOrderLeaveVO.setCreateBy( getUserId( request, response ) );
            clientOrderLeaveVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            clientOrderLeaveVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �½�����
            clientOrderLeaveService.insertClientOrderLeave( clientOrderLeaveVO );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, clientOrderLeaveVO, Operate.ADD, clientOrderLeaveVO.getOrderLeaveId(), null );
         }

         // ���Form����
         ( ( ClientOrderLeaveVO ) form ).reset();
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
            final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );
            // ��õ�ǰ����
            final String orderLeaveId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final ClientOrderLeaveVO clientOrderLeaveVO = clientOrderLeaveService.getClientOrderLeaveVOByClientOrderLeaveId( orderLeaveId );
            // ��ȡ��¼�û�
            clientOrderLeaveVO.update( ( ClientOrderLeaveVO ) form );
            clientOrderLeaveVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            clientOrderLeaveVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �޸Ķ���
            clientOrderLeaveService.updateClientOrderLeave( clientOrderLeaveVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, clientOrderLeaveVO, Operate.MODIFY, clientOrderLeaveVO.getOrderLeaveId(), null );
         }

         // ���Form����
         ( ( ClientOrderLeaveVO ) form ).reset();
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
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );

         // ��ȡ����
         final String clientOrderLeaveId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderLeaveId" ) );

         // ����������ö�ӦVO
         final ClientOrderLeaveVO clientOrderLeaveVO = clientOrderLeaveService.getClientOrderLeaveVOByClientOrderLeaveId( clientOrderLeaveId );
         clientOrderLeaveVO.setModifyBy( getUserId( request, response ) );
         clientOrderLeaveVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientOrderLeaveService.deleteClientOrderLeave( clientOrderLeaveVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, clientOrderLeaveVO, Operate.DELETE, clientOrderLeaveVO.getOrderLeaveId(), "ajax delete" );
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
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );
         // ���Action Form
         ClientOrderLeaveVO clientOrderLeaveVO = ( ClientOrderLeaveVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderLeaveVO.getSelectedIds() != null && !clientOrderLeaveVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderLeaveVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientOrderLeaveId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderLeaveVO clientOrderLeaveVOForDel = clientOrderLeaveService.getClientOrderLeaveVOByClientOrderLeaveId( clientOrderLeaveId );
                  clientOrderLeaveVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderLeaveVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderLeaveService.deleteClientOrderLeave( clientOrderLeaveVOForDel );
               }
            }

            insertlog( request, clientOrderLeaveVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( clientOrderLeaveVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         clientOrderLeaveVO.setSelectedIds( "" );
         clientOrderLeaveVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // У���ݼ����ò����ظ�
   private boolean checkClientOrderLeave( final List< Object > clientOrderLeaveVOs, final String itemId, final String year, final String orderLeaveId )
   {
      boolean flag = true;
      if ( clientOrderLeaveVOs != null && clientOrderLeaveVOs.size() > 0 )
      {
         for ( Object o : clientOrderLeaveVOs )
         {
            final ClientOrderLeaveVO tempClientOrderLeaveVO = ( ClientOrderLeaveVO ) o;
            if ( orderLeaveId != null && tempClientOrderLeaveVO.getOrderLeaveId().equals( orderLeaveId ) )
               continue;

            // ��������
            if ( "41".equals( itemId ) )
            {
               if ( tempClientOrderLeaveVO.getItemId().equals( itemId ) && tempClientOrderLeaveVO.getYear().equals( year ) )
               {
                  flag = false;
                  break;
               }
            }
            else
            {
               if ( tempClientOrderLeaveVO.getItemId().equals( itemId ) )
               {
                  flag = false;
                  break;
               }
            }
         }
      }

      return flag;
   }

   public void checkClientOrderLeave_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );

         final String orderHeaderId = request.getParameter( "orderHeaderId" );
         final String itemId = request.getParameter( "itemId" );
         final String year = request.getParameter( "year" );
         final String orderLeaveId = request.getParameter( "orderLeaveId" );

         final List< Object > clientOrderLeaveVOs = clientOrderLeaveService.getClientOrderLeaveVOsByOrderHeaderId( orderHeaderId );

         boolean exist = checkClientOrderLeave( clientOrderLeaveVOs, itemId, year, orderLeaveId );

         out.print( exist ? "1" : "2" );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

}
