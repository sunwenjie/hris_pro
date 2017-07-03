package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientInvoiceVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientInvoiceService;
import com.kan.hro.service.inf.biz.client.ClientService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientInvoiceAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-8  
 */

public class ClientInvoiceAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_INVOICE";

   /**  
    * List Object Options Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ������ѡ��
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ȡInvoiceAddressId
         final String invoiceAddressId = request.getParameter( "invoiceAddressId" );

         // ��ʼ��Service�ӿ�
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

         // ��ȡClientId
         final String clientId = request.getParameter( "clientId" );

         // ��ȡClientInvoiceVO�б�
         final List< Object > clientInvoiceVOs = clientInvoiceService.getClientInvoiceVOsByClientId( clientId );

         // ����������
         if ( clientInvoiceVOs != null && clientInvoiceVOs.size() > 0 )
         {
            for ( Object clientInvoiceVOObject : clientInvoiceVOs )
            {
               final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) clientInvoiceVOObject;

               // ��ʼ��MappingVO
               final MappingVO mappingVO = new MappingVO();

               mappingVO.setMappingId( clientInvoiceVO.getClientInvoiceId() );

               // ��ʼ����Ʊ��ַ
               String invoiceAddress = clientInvoiceVO.getAddress();
               // ����
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  invoiceAddress = invoiceAddress + "��" + clientInvoiceVO.getNameZH() + "��";
               }
               // ������
               else
               {
                  invoiceAddress = invoiceAddress + " (" + clientInvoiceVO.getNameEN() + ")";
               }
               mappingVO.setMappingValue( invoiceAddress );
               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "invoiceAddressId", invoiceAddressId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

   /**
    * list object json
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
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ�� Service
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

         // ��ȡ��ǰ��¼�û���accountId
         final String accountId = getAccountId( request, response );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientInvoiceService.getClientInvoiceBaseViews( accountId ) );

         // Send to clientInvoiceGroup
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

   /**
    * List clientInvoice
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
         // ��ʼ��Service�ӿ�
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );
         // ���Action Form
         final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) form;
         // ���SubAction
         final String subAction = getSubAction( form );

         // ����Զ�����������
         clientInvoiceVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // ���û��ָ��������Ĭ�ϰ�ClientInvoiceId����
         if ( clientInvoiceVO.getSortColumn() == null || clientInvoiceVO.getSortColumn().isEmpty() )
         {
            clientInvoiceVO.setSortColumn( "clientInvoiceId" );
            clientInvoiceVO.setSortOrder( "desc" );
         }

         // ����SubAction
         dealSubAction( clientInvoiceVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder clientInvoiceHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            clientInvoiceHolder.setPage( page );
            // ���뵱ǰֵ����
            clientInvoiceHolder.setObject( clientInvoiceVO );
            // ����ҳ���¼����
            clientInvoiceHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            clientInvoiceService.getClientInvoiceVOsByCondition( clientInvoiceHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( clientInvoiceHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", clientInvoiceHolder );
         // ����Return
         return dealReturn( accessAction, "listClientInvoice", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ���Client Id��Ϊ��
         if ( request.getParameter( "clientId" ) != null && !request.getParameter( "clientId" ).trim().equals( "" ) )
         {
            // ��ȡClient Id
            final String clientId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "clientId" ), "UTF-8" ) );
            // ��ʼ��ClientInvoiceVO
            ( ( ClientInvoiceVO ) form ).reset();
            // ����Client Id
            ( ( ClientInvoiceVO ) form ).setClientId( clientId );
            // ��ʼ���ӿ�
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            // ����Client Id ��ö�Ӧ��ClientVO
            final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
            // ����Client Name
            ( ( ClientInvoiceVO ) form ).setClientId( clientVO.getClientId() );
            ( ( ClientInvoiceVO ) form ).setClientNameZH( clientVO.getNameZH() );
            ( ( ClientInvoiceVO ) form ).setClientNameEN( clientVO.getNameEN() );
            // ���÷���ʵ��
            ( ( ClientInvoiceVO ) form ).setLegalEntity( clientVO.getLegalEntity() );
            // ���÷���̨ͷ
            if ( getLocale( request ).getLanguage().equalsIgnoreCase( "zh" ) )
            {
               ( ( ClientInvoiceVO ) form ).setTitle( clientVO.getNameZH() );
            }
            else
            {
               ( ( ClientInvoiceVO ) form ).setTitle( clientVO.getNameEN() );
            }
            ( ( ClientInvoiceVO ) form ).setTitle( clientVO.getNameZH() );
         }

         // ����SubAction ��Ĭ��ֵ
         ( ( ClientInvoiceVO ) form ).setSubAction( CREATE_OBJECT );
         ( ( ClientInvoiceVO ) form ).setStatus( ClientInvoiceVO.TRUE );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���½�����
      return mapping.findForward( "manageClientInvoice" );
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
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

         // ��õ�ǰ����
         String invoiceId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( invoiceId == null || invoiceId.trim().isEmpty() )
         {
            invoiceId = ( ( ClientInvoiceVO ) form ).getClientInvoiceId();
         }

         // ���ClientInvoiceVO
         final ClientInvoiceVO clientInvoiceVO = clientInvoiceService.getClientInvoiceVOByClientInvoiceId( invoiceId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         clientInvoiceVO.reset( null, request );

         // �������City Id�������Province Id
         if ( KANUtil.filterEmpty( clientInvoiceVO.getCityId(), "0" ) != null )
         {
            clientInvoiceVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( clientInvoiceVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            clientInvoiceVO.setCityIdTemp( clientInvoiceVO.getCityId() );
         }

         clientInvoiceVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientInvoiceForm", clientInvoiceVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientInvoice" );
   }

   /**
    * Add clientInvoice
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );
            // ���ҳ������ֵ
            checkClientId( mapping, form, request, response );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ��Service�ӿ�
            final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

            // ���ActionForm
            final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) form;
            clientInvoiceVO.setCreateBy( getUserId( request, response ) );
            clientInvoiceVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            clientInvoiceVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // �½�����
            clientInvoiceService.insertClientInvoice( clientInvoiceVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Client Invoice
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );
            // ���ҳ������ֵ
            checkClientId( mapping, form, request, response );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ��Service�ӿ�
            final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

            // ���������Ӧ����
            final ClientInvoiceVO clientInvoiceVO = clientInvoiceService.getClientInvoiceVOByClientInvoiceId( KANUtil.decodeString( request.getParameter( "id" ) ) );

            // ��ȡ��¼�û�
            clientInvoiceVO.update( ( ClientInvoiceVO ) form );
            // �����Զ���Column
            clientInvoiceVO.setRemark1( saveDefineColumns( request, accessAction ) );

            clientInvoiceVO.setModifyBy( getUserId( request, response ) );
            clientInvoiceVO.setModifyDate( new Date() );
            clientInvoiceVO.reset( mapping, request );

            // �޸Ķ���
            clientInvoiceService.updateClientInvoice( clientInvoiceVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete clientInvoice
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
      // No Use
   }

   /**
    * Delete clientInvoice list
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
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );
         // ���Action Form
         final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) form;

         // ����ѡ�е�ID
         if ( clientInvoiceVO.getSelectedIds() != null && !clientInvoiceVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientInvoiceVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ʵ����ClientInvoiceVO
                  final ClientInvoiceVO tempClientInvoiceVO = clientInvoiceService.getClientInvoiceVOByClientInvoiceId( KANUtil.decodeStringFromAjax( selectedId ) );
                  tempClientInvoiceVO.setModifyBy( getUserId( request, response ) );
                  tempClientInvoiceVO.setModifyDate( new Date() );

                  // ����ɾ���ӿ�
                  clientInvoiceService.deleteClientInvoice( tempClientInvoiceVO );
               }
            }
         }

         // ���Selected IDs����Action
         clientInvoiceVO.setSelectedIds( "" );
         clientInvoiceVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Delete Object Ajax
    * Tabɾ���˵���ַ
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

         // ��ȡ����
         final String invoiceId = KANUtil.decodeStringFromAjax( request.getParameter( "invoiceId" ) );

         // ��ȡClientInvoiceVO
         final ClientInvoiceVO clientInvoiceVO = clientInvoiceService.getClientInvoiceVOByClientInvoiceId( invoiceId );
         clientInvoiceVO.setModifyBy( getUserId( request, response ) );
         clientInvoiceVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientInvoiceService.deleteClientInvoice( clientInvoiceVO );

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
    * To PrePage
    * ҳ��������󷵻��ύҳ��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_prePage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ������趨һ���Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת��Manage����   
      return mapping.findForward( "manageClientInvoice" );
   }

   /**  
    * �������ClientId�Ƿ���Ч
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkClientId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final ClientService clientService = ( ClientService ) getService( "clientService" );
      // ��ȡForm
      final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) form;
      // ���ClientId
      final String clientId = KANUtil.filterEmpty( clientInvoiceVO.getClientId() );

      final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

      if ( clientVO == null )
      {
         request.setAttribute( "clientIdError", "�ͻ�ID������Ч��" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

}
