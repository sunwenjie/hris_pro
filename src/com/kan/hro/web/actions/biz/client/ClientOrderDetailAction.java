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
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderDetailSBRuleVO;
import com.kan.hro.domain.biz.client.ClientOrderDetailVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailRuleService;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailSBRuleService;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientOrderDetailAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientOrderDetailAction extends BaseAction
{
   public static String accessAction = "HRO_BIZ_CLIENT_ORDER_DETAIL";

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
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         // ���Action Form
         final ClientOrderDetailVO clientOrderDetailVO = ( ClientOrderDetailVO ) form;
         clientOrderDetailVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientOrderDetailVO.getSubAction() != null && clientOrderDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientOrderDetailVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientOrderDetailVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientOrderDetailVO.getSubAction() != null )
         {
            subAction = clientOrderDetailVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderDetailService.getClientOrderDetailVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_DETAIL" ) );
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
      return mapping.findForward( "listClientOrderDetail" );
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

      // ���OrderHeaderId
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );
      // ���ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

      ( ( ClientOrderDetailVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderDetailVO ) form ).setPackageType( "1" );
      ( ( ClientOrderDetailVO ) form ).setDivideType( "1" );
      ( ( ClientOrderDetailVO ) form ).setCalculateType( "4" );
      ( ( ClientOrderDetailVO ) form ).setBase( "0.00" );
      ( ( ClientOrderDetailVO ) form ).setResultCap( "0.00" );
      ( ( ClientOrderDetailVO ) form ).setResultFloor( "0.00" );
      ( ( ClientOrderDetailVO ) form ).setCycle( "1" );
      ( ( ClientOrderDetailVO ) form ).setStartDate( clientOrderHeaderVO.getStartDate() );
      ( ( ClientOrderDetailVO ) form ).setEndDate( clientOrderHeaderVO.getEndDate() );
      ( ( ClientOrderDetailVO ) form ).setStatus( ClientOrderDetailVO.TRUE );
      request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );
      // ����Sub Action
      ( ( ClientOrderDetailVO ) form ).setSubAction( CREATE_OBJECT );

      // ���ʻ�
      ( ( ClientOrderDetailVO ) form ).reset( null, request );

      // ��ת���½�����
      return mapping.findForward( "manageClientOrderDetail" );
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
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // ��õ�ǰ����
         String orderDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderDetailId == null || orderDetailId.trim().isEmpty() )
         {
            orderDetailId = ( ( ClientOrderDetailVO ) form ).getOrderDetailId();
         }

         // ���ClientOrderDetailVO
         final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( orderDetailId );
         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderDetailVO.reset( null, request );
         clientOrderDetailVO.setSubAction( VIEW_OBJECT );
         // ��ȡheader
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderDetailVO.getOrderHeaderId() );
         request.setAttribute( "clientOrderDetailForm", clientOrderDetailVO );
         request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientOrderDetail" );
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
            final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
            // ���ActionForm
            final ClientOrderDetailVO clientOrderDetailVO = ( ClientOrderDetailVO ) form;
            // ��ȡ��¼�û�
            clientOrderDetailVO.setCreateBy( getUserId( request, response ) );
            clientOrderDetailVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            clientOrderDetailVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �½�����
            clientOrderDetailService.insertClientOrderDetail( clientOrderDetailVO );

            // �ж��Ƿ���Ҫת��
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // ����ת���ַ
               forwardURL = forwardURL + ( ( ClientOrderDetailVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Form����
         ( ( ClientOrderDetailVO ) form ).reset();
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
   // Reviewed by Kevin Jin at 2013-10-20
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
            // ��õ�ǰ����
            final String clientOrderDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailId );
            clientOrderDetailVO.reset( null, request );
            // ��ȡ��¼�û�
            clientOrderDetailVO.update( ( ClientOrderDetailVO ) form );
            // �����Զ���Column
            clientOrderDetailVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientOrderDetailVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            clientOrderDetailService.updateClientOrderDetail( clientOrderDetailVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form����
         ( ( ClientOrderDetailVO ) form ).reset();
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
   // Added by Kevin Jin at 2013-10-19
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );

         // ��ȡ����
         final String clientOrderDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderDetailId" ) );

         // ����������ö�ӦVO
         final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailId );
         clientOrderDetailVO.reset( mapping, request );
         clientOrderDetailVO.setModifyBy( getUserId( request, response ) );
         clientOrderDetailVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientOrderDetailService.deleteClientOrderDetail( clientOrderDetailVO );

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
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         // ���Action Form
         ClientOrderDetailVO clientOrderDetailVO = ( ClientOrderDetailVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderDetailVO.getSelectedIds() != null && !clientOrderDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderDetailVO.getSelectedIds().split( "," ) )
            {

               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientOrderDetailId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderDetailVO clientOrderDetailVOForDel = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailId );
                  clientOrderDetailVO.reset( mapping, request );
                  clientOrderDetailVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderDetailVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderDetailService.deleteClientOrderDetail( clientOrderDetailVOForDel );
               }

            }
         }

         // ���Selected IDs����Action
         clientOrderDetailVO.setSelectedIds( "" );
         clientOrderDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡδ���� Client Order Detail Id
         final String decodeClientOrderDetailId = request.getParameter( "orderDetailId" );
         // ��ʼ��clientOrderDetailId
         String clientOrderDetailId = "";

         if ( decodeClientOrderDetailId != null && !decodeClientOrderDetailId.trim().equals( "" ) )
         {
            clientOrderDetailId = KANUtil.decodeString( decodeClientOrderDetailId );

            // ��ʼ��Service�ӿ�
            final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
            // �����������Ҷ�Ӧ��clientOrderDetailVO
            final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailId );

            // ˢ��VO���󣬳�ʼ�������б����ʻ�
            clientOrderDetailVO.reset( null, request );
            request.setAttribute( "clientOrderDetailForm", clientOrderDetailVO );
         }
         else
         {
            request.setAttribute( "clientOrderDetailForm", form );
         }

         // ��ʼ��Client Order Detail Rule�ӿ�
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

         final List< Object > clientOrderDetailRuleList = clientOrderDetailRuleService.getClientOrderDetailRuleVOsByClientOrderDetailId( clientOrderDetailId );

         if ( clientOrderDetailRuleList != null && clientOrderDetailRuleList.size() > 0 )
         {
            for ( Object obj : clientOrderDetailRuleList )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         //  ���ؽ��������
         request.setAttribute( "clientOrderDetailRuleList", clientOrderDetailRuleList );
         // �������鳤��
         request.setAttribute( "clientOrderDetailRuleListSize", ( clientOrderDetailRuleList == null ) ? ( "0" ) : ( clientOrderDetailRuleList.size() ) );

         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

         final List< Object > clientOrderDetailSBRuleList = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOsByClientOrderDetailId( clientOrderDetailId );

         if ( clientOrderDetailSBRuleList != null && clientOrderDetailSBRuleList.size() > 0 )
         {
            for ( Object obj : clientOrderDetailSBRuleList )
            {
               final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = ( ClientOrderDetailSBRuleVO ) obj;
               clientOrderDetailSBRuleVO.setSbRuleTypes( KANUtil.getMappings( request.getLocale(), "business.client.order.detail.sb.rule" ) );
               clientOrderDetailSBRuleVO.setSbSolutionIds( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
            }
         }

         // �����籣���ɹ���
         request.setAttribute( "clientOrderDetailSBRuleList", clientOrderDetailSBRuleList );

         request.setAttribute( "clientOrderDetailSBRuleListSize", ( clientOrderDetailSBRuleList == null ) ? ( "0" ) : ( clientOrderDetailSBRuleList.size() ) );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageClientOrderDetailSpecialInfo" );
   }

}
