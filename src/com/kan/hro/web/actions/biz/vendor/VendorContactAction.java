package com.kan.hro.web.actions.biz.vendor;

import java.io.PrintWriter;
import java.net.URLDecoder;
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
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.vendor.VendorContactVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.vendor.VendorContactService;
import com.kan.hro.service.inf.biz.vendor.VendorService;

public class VendorContactAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public static String accessAction = "HRO_BIZ_VENDOR_CONTACT";

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

         // ��ʼ��Service
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( vendorContactService.getVendorContactBaseViewsByAccountId( getAccountId( request, response ) ) );

         // Send to client
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

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // ���Action Form
         final VendorContactVO vendorContactVO = ( VendorContactVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         vendorContactVO.setAccountId( getAccountId( request, response ) );

         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, vendorContactVO );

         // ���û��ָ��������Ĭ�ϰ� vendorId����
         if ( vendorContactVO.getSortColumn() == null || vendorContactVO.getSortColumn().isEmpty() )
         {
            vendorContactVO.setSortColumn( "vendorContactId" );
            vendorContactVO.setSortOrder( "desc" );
         }

         // ���SubAction
         final String subAction = getSubAction( form );
         // ����Զ����������� 
         vendorContactVO.setRemark1( generateDefineListSearches( request, accessAction ) );
         // ����SubAction
         dealSubAction( vendorContactVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder vendorContactPagedListHolder = new PagedListHolder();
         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS )
               || subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
         {
            // ���뵱ǰҳ
            vendorContactPagedListHolder.setPage( page );
            // ���뵱ǰֵ����
            vendorContactPagedListHolder.setObject( vendorContactVO );
            // ����ҳ���¼����
            vendorContactPagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            vendorContactService.getVendorContactVOsByCondition( vendorContactPagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( vendorContactPagedListHolder, request );

         }
         request.setAttribute( "accountId", getAccountId( request, null ) );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", vendorContactPagedListHolder );
         // ����Return
         return dealReturn( accessAction, "listVendorContact", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Options Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
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
         String vendorContactId = request.getParameter( "contactId" );

         // ���VendorId��Ϊ��
         if ( request.getParameter( "vendorId" ) != null && !request.getParameter( "vendorId" ).trim().isEmpty() )
         {
            // ��ʼ��Service�ӿ�
            final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );

            // ��ȡVendorVO
            final VendorVO vendorVO = vendorService.getVendorVOByVendorId( KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) ) );

            // ��ϵ��Ϊ�յ������Ĭ��ȡ��Ҫ��ϵ��
            if ( vendorContactId == null || vendorContactId.trim().isEmpty() )
            {
               vendorContactId = vendorVO.getMainContact();
            }

            final List< Object > vendorContactVOs = vendorContactService.getVendorContactVOsByVendorId( KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) ) );

            if ( vendorContactVOs != null && vendorContactVOs.size() > 0 )
            {
               // ����
               for ( Object vendorContactVOObject : vendorContactVOs )
               {
                  final VendorContactVO tempVendorContactVO = ( VendorContactVO ) vendorContactVOObject;

                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( tempVendorContactVO.getVendorContactId() );

                  // ����
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( tempVendorContactVO.getNameZH() );
                  }
                  else
                  {
                     mappingVO.setMappingValue( tempVendorContactVO.getNameEN() );
                  }

                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to vendor
         out.println( KANUtil.getOptionHTML( mappingVOs, "vendorContactId", vendorContactId ) );
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

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         this.saveToken( request );

         // ��ʼ��PositionVO
         final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

         // ���ActionForm 
         final VendorContactVO vendorContactVO = ( VendorContactVO ) form;

         // �����Vendorҳ�����
         if ( KANUtil.filterEmpty( request.getParameter( "vendorId" ) ) != null && KANUtil.filterEmpty( vendorContactVO.getSubAction() ) == null )
         {
            vendorContactVO.setVendorId( Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "vendorId" ), "UTF-8" ) ) );
         }

         // ������������������
         if ( positionVO != null )
         {
            vendorContactVO.setBranch( positionVO.getBranchId() );
            vendorContactVO.setOwner( positionVO.getPositionId() );
         }

         // ����Sub Action 
         vendorContactVO.setSubAction( CREATE_OBJECT );
         vendorContactVO.setStatus( VendorContactVO.TRUE );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageVendorContact" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

            // ��õ�ǰFORM
            final VendorContactVO vendorContactVO = ( VendorContactVO ) form;

            // ����ҳ��vendorId
            final String vendorId = vendorContactVO.getVendorId();

            // У�鵱ǰform��vendorId�Ƿ���Ч
            checkEmployeeId( mapping, vendorContactVO, request, response );

            // ���Ϸ���vendorId��ת����ҳ��
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "vendorIdErrorMsg" ) ) != null )
            {
               vendorContactVO.reset();
               vendorContactVO.setVendorId( vendorId );
               return to_objectNew( mapping, vendorContactVO, request, response );
            }

            // �趨��ǰ�û�
            vendorContactVO.setAccountId( getAccountId( request, response ) );
            vendorContactVO.setCreateBy( getUserId( request, response ) );
            vendorContactVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            vendorContactVO.setRemark1( saveDefineColumns( request, accessAction ) );

            vendorContactVO.setRole( getRole( request, response ) );

            // ������ӷ���
            vendorContactService.insertVendorContact( vendorContactVO );

            // ���Ҹ���ϵ���Ƿ��ǹ�Ӧ�̵�һ����ϵ�ˣ�����Ǹ��½���ϵ��Ϊ��Ӧ��Ĭ����ϵ�� 
            if ( vendorContactService.getVendorContactVOsByVendorId( vendorContactVO.getVendorId() ).size() == 1 )
            {
               final VendorService vendorService = ( VendorService ) getService( "vendorService" );
               final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorContactVO.getVendorId() );

               if ( vendorVO != null )
               {
                  vendorVO.setMainContact( vendorContactVO.getVendorContactId() );
                  vendorVO.setModifyBy( getUserId( request, response ) );
                  vendorVO.setModifyDate( new Date() );
                  vendorService.updateVendor( vendorVO );
               }

            }

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, vendorContactVO, Operate.ADD, vendorContactVO.getVendorContactId(), null );
         }
         else
         {
            // ���form
            ( ( VendorContactVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת�鿴����
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �趨�Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // ������ȡ�����
         String vendorContactId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( vendorContactId == null || vendorContactId.trim().isEmpty() )
         {
            vendorContactId = ( ( VendorContactVO ) form ).getVendorContactId();
         }

         // ���VendorContactVO����
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );
         // ����SubAction
         vendorContactVO.setSubAction( VIEW_OBJECT );
         vendorContactVO.reset( null, request );
         // ���City Id�������Province Id
         if ( vendorContactVO.getCityId() != null && !vendorContactVO.getCityId().trim().equals( "" ) && !vendorContactVO.getCityId().trim().equals( "0" ) )
         {
            vendorContactVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorContactVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorContactVO.setCityIdTemp( vendorContactVO.getCityId() );
         }
         // ��ColumnVO����request����
         request.setAttribute( "vendorContactForm", vendorContactVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageVendorContact" );
   }

   /**  
    * To ObjectModify InVendor
    *	��Ӧ�̵�¼��ʾ��Ӧ����ϵ����Ϣ
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    * Add By��Jack  
    * Add Date��2014-4-14  
    */
   public ActionForward to_objectModify_inVendor( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �趨�Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // ������ȡ�����
         String vendorContactId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���VendorContactVO����
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );
         // ����SubAction
         vendorContactVO.setSubAction( VIEW_OBJECT );
         vendorContactVO.reset( null, request );

         // ���City Id�������Province Id
         if ( vendorContactVO.getCityId() != null && !vendorContactVO.getCityId().trim().equals( "" ) && !vendorContactVO.getCityId().trim().equals( "0" ) )
         {
            vendorContactVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorContactVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorContactVO.setCityIdTemp( vendorContactVO.getCityId() );
         }

         // ��ColumnVO����request����
         request.setAttribute( "vendorContactForm", vendorContactVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageVendorContact" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
            // ������ȡ�����
            final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���VendorContactVO����
            final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorId );
            // װ�ؽ��洫ֵ
            vendorContactVO.update( ( ( VendorContactVO ) form ) );
            // systemId ���ʼ���
            vendorContactVO.setSystemId( ( ( VendorContactVO ) form ).getSystemId() );
            // ��ȡ��¼�û�
            vendorContactVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            vendorContactVO.setRemark1( saveDefineColumns( request, accessAction ) );

            vendorContactVO.setRole( getRole( request, response ) );

            // �����޸ķ���
            vendorContactService.updateVendorContact( vendorContactVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, vendorContactVO, Operate.MODIFY, vendorContactVO.getVendorContactId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // ���ɾ������
         final String vendorContactId = request.getParameter( "vendorContactId" );
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );
         vendorContactVO.setModifyBy( getUserId( request, response ) );
         vendorContactVO.setModifyDate( new Date() );
         // ����ɾ���ӿ�
         vendorContactService.deleteVendorContact( vendorContactVO );
         insertlog( request, vendorContactVO, Operate.DELETE, vendorContactId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

         // ���Action Form
         VendorContactVO vendorContactVO = ( VendorContactVO ) form;
         final String selectedIds = vendorContactVO.getSelectedIds();
         if ( selectedIds != null && !selectedIds.equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : selectedIds.split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // ����ɾ���ӿ�
                  vendorContactVO.setModifyBy( getUserId( request, response ) );
                  vendorContactVO.setModifyDate( new Date() );
                  vendorContactService.deleteVendorContact( vendorContactVO );
               }
            }

            insertlog( request, vendorContactVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( selectedIds ) );
         }

         // ���Selected IDs����Action
         vendorContactVO.setSelectedIds( "" );
         vendorContactVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Row
         long rows = 0;

         // ��ʼ��Service�ӿ�
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

         // ��ȡ����
         final String vendorContactId = KANUtil.decodeStringFromAjax( request.getParameter( "vendorContactId" ) );

         // ��ȡVendorContactVO
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );
         vendorContactVO.setModifyBy( getUserId( request, response ) );
         vendorContactVO.setModifyDate( new Date() );
         // ����ɾ���ӿ�
         rows = vendorContactService.deleteVendorContact( vendorContactVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, vendorContactVO, Operate.DELETE, vendorContactId, "delete_object_ajax" );
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
         final String vendorContactId = KANUtil.decodeStringFromAjax( request.getParameter( "vendorContactId" ) );
         // ��ʼ��Service�ӿ�
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );

         request.setAttribute( "vendorContactForm", vendorContactVO );
         // Ajax����
         return mapping.findForward( "listSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

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
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

         // ��ȡVendorContactId
         final String vendorContactId = request.getParameter( "vendorContactId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         // ��ʼ��VendorContactVO
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );
         if ( vendorContactVO != null && vendorContactVO.getAccountId() != null && vendorContactVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            vendorContactVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( vendorContactVO );
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
    * �������VendorId�Ƿ���Ч
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkEmployeeId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final VendorService vendorService = ( VendorService ) getService( "vendorService" );

      // ���ActionForm
      final VendorContactVO vendorContactVO = ( VendorContactVO ) form;

      // ��ͼ��ȡVendorVO 
      final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorContactVO.getVendorId() );

      // ������VendorVO��AccountId��ƥ�䵱ǰ
      if ( vendorVO == null
            || ( vendorVO != null && KANUtil.filterEmpty( vendorVO.getAccountId() ) != null && !vendorVO.getAccountId().equals( getAccountId( request, response ) ) ) )
      {
         request.setAttribute( "vendorIdErrorMsg", "��Ӧ��ID������Ч��" );
      }

   }

}
