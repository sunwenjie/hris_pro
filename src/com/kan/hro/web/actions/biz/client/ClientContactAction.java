package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientContactVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientContactService;
import com.kan.hro.service.inf.biz.client.ClientService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientContactAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-8  
 */
public class ClientContactAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_CONTACT";

   /**  
    * Get Object Json
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

         // ��ȡClientContactId
         final String clientContactId = request.getParameter( "clientContactId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         // ��ʼ��ClientContactVO
         final ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( clientContactId );
         if ( clientContactVO != null && clientContactVO.getAccountId() != null && clientContactVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            clientContactVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( clientContactVO );
            jsonObject.put( "success", "true" );
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to front
         out.println( jsonObject.toString() );
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
    * list object json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
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
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

         // ��ȡ��ǰ��¼�û���accountId
         final String clientId = KANUtil.decodeString( request.getParameter( "clientId" ) );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientContactService.getClientContactVOsByClientId( clientId ) );

         // Send to clientContactGroup
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
    *	List Object Options Ajax
    * ��ʾ��ϵ��������
    * 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
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

         // ��ʼ����ϵ��
         String clientContactId = request.getParameter( "contactId" );

         // ���ClientId��Ϊ��
         if ( request.getParameter( "clientId" ) != null && !request.getParameter( "clientId" ).trim().isEmpty() )
         {
            // ��ʼ��Service�ӿ�
            final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );
            final ClientService clientService = ( ClientService ) getService( "clientService" );

            // ��ȡClientVO
            final ClientVO clientVO = clientService.getClientVOByClientId( KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) ) );

            // ��ϵ��Ϊ�յ������Ĭ��ȡ��Ҫ��ϵ��
            if ( clientContactId == null || clientContactId.trim().isEmpty() )
            {
               clientContactId = clientVO.getMainContact();
            }

            final List< Object > clientContactVOs = clientContactService.getClientContactVOsByClientId( KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) ) );

            if ( clientContactVOs != null && clientContactVOs.size() > 0 )
            {
               // ����
               for ( Object clientContactVOObject : clientContactVOs )
               {
                  final ClientContactVO tempClientContactVO = ( ClientContactVO ) clientContactVOObject;

                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( tempClientContactVO.getClientContactId() );

                  // ����
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( tempClientContactVO.getNameZH() );
                  }
                  else
                  {
                     mappingVO.setMappingValue( tempClientContactVO.getNameEN() );
                  }

                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "clientContactId", clientContactId ) );
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
    * List ClientContact
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
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );
         // ���Action Form
         final ClientContactVO clientContactVO = ( ClientContactVO ) form;
         // ���SubAction
         final String subAction = getSubAction( form );

         // ����Զ�����������
         clientContactVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // ���û��ָ��������Ĭ�ϰ�ClientcontactId����
         if ( clientContactVO.getSortColumn() == null || clientContactVO.getSortColumn().isEmpty() )
         {
            clientContactVO.setSortColumn( "clientContactId" );
            clientContactVO.setSortOrder( "desc" );
         }

         // ����SubAction
         dealSubAction( clientContactVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder clientContactHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            clientContactHolder.setPage( page );
            // ���뵱ǰֵ����
            clientContactHolder.setObject( clientContactVO );
            // ����ҳ���¼����
            clientContactHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            clientContactService.getClientContactVOsByCondition( clientContactHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( clientContactHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", clientContactHolder );
         // ����Return
         return dealReturn( accessAction, "listClientContact", mapping, form, request, response );
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

         // ����SubAction ��Ĭ��ֵ
         ( ( ClientContactVO ) form ).setSubAction( CREATE_OBJECT );
         ( ( ClientContactVO ) form ).setStatus( ClientContactVO.TRUE );

         // ��ʼ��PositionVO
         final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

         if ( positionVO != null )
         {
            ( ( ClientContactVO ) form ).setBranch( positionVO.getBranchId() );
            ( ( ClientContactVO ) form ).setOwner( positionVO.getPositionId() );
         }

         // ���Client Id��Ϊ��
         if ( request.getParameter( "clientId" ) != null && !request.getParameter( "clientId" ).trim().equals( "" ) )
         {
            // ��ȡClient Id
            final String clientId = KANUtil.decodeString( request.getParameter( "clientId" ) );
            // ����Client Id
            ( ( ClientContactVO ) form ).setClientId( clientId );
            // ��ʼ���ӿ�
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            // ����Client Id ��ö�Ӧ��ClientVO
            final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
            // ����Client Name
            ( ( ClientContactVO ) form ).setClientId( clientVO.getClientId() );
            ( ( ClientContactVO ) form ).setClientNameZH( clientVO.getNameZH() );
            ( ( ClientContactVO ) form ).setClientNameEN( clientVO.getNameEN() );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���½�����
      return mapping.findForward( "manageClientContact" );
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
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

         // ��õ�ǰ����
         String contactId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( contactId == null || contactId.trim().isEmpty() )
         {
            contactId = ( ( ClientContactVO ) form ).getClientContactId();
         }

         // ���ClientContactVO
         final ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( contactId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         clientContactVO.reset( null, request );

         // �������City Id�������Province Id
         if ( KANUtil.filterEmpty( clientContactVO.getCityId(), "0" ) != null )
         {
            clientContactVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( clientContactVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            clientContactVO.setCityIdTemp( clientContactVO.getCityId() );
         }

         clientContactVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientContactForm", clientContactVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientContact" );
   }

   /**
    * Add Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
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
            final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

            // ���ActionForm
            final ClientContactVO clientContactVO = ( ClientContactVO ) form;
            clientContactVO.setCreateBy( getUserId( request, response ) );
            clientContactVO.setModifyBy( getUserId( request, response ) );
            clientContactVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            // �����Զ���Column
            clientContactVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // �½�����
            clientContactService.insertClientContact( clientContactVO );

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
    * Modify clientContact
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
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
            final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

            // ��� ClientContactVO
            final ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( KANUtil.decodeString( request.getParameter( "id" ) ) );

            clientContactVO.update( ( ClientContactVO ) form );
            // �����Զ���Column
            clientContactVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // ��ȡ��¼�û�
            clientContactVO.setModifyBy( getUserId( request, response ) );
            clientContactVO.setModifyDate( new Date() );
            clientContactVO.reset( mapping, request );
            clientContactVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            // �޸Ķ���
            clientContactService.updateClientContact( clientContactVO );

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
    * Delete clientContact
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
    * Delete clientContact list
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
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );
         // ���Action Form
         final ClientContactVO clientContactVO = ( ClientContactVO ) form;

         // ����ѡ�е�ID
         if ( clientContactVO.getSelectedIds() != null && !clientContactVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientContactVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ʵ����ClientContactVO��ɾ��
                  ClientContactVO tempClientContactVO = clientContactService.getClientContactVOByClientContactId( KANUtil.decodeStringFromAjax( selectedId ) );
                  tempClientContactVO.setModifyBy( getUserId( request, response ) );
                  tempClientContactVO.setModifyDate( new Date() );

                  // ����ɾ���ӿ�
                  clientContactService.deleteClientContact( tempClientContactVO );
               }
            }
         }

         // ���Selected IDs����Action
         clientContactVO.setSelectedIds( "" );
         clientContactVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Delete Object Ajax
    * Tabɾ����ϵ��
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
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

         // ��ȡ����
         final String contactId = KANUtil.decodeStringFromAjax( request.getParameter( "contactId" ) );

         // ��ȡClientContactVO
         final ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( contactId );
         clientContactVO.setModifyBy( getUserId( request, response ) );
         clientContactVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientContactService.deleteClientContact( clientContactVO );

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

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {

         String username = request.getParameter( "username" );
         if ( username != null )
         {
            username = new String( username.getBytes( "ISO8859_1" ), "GBK" );
         }
         final String clientUserId = request.getParameter( "clientUserId" );

         request.setAttribute( "username", username );
         request.setAttribute( "clientUserId", clientUserId );
         // Ajax����
         return mapping.findForward( "listSpecialInfo" );
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
      return mapping.findForward( "manageClientContact" );
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
      final ClientContactVO clientContactVO = ( ClientContactVO ) form;
      // ���ClientId
      final String clientId = KANUtil.filterEmpty( clientContactVO.getClientId() );

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
