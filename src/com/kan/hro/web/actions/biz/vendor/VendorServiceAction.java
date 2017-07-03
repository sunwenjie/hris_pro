package com.kan.hro.web.actions.biz.vendor;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.vendor.VendorServiceVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.vendor.VendorService;
import com.kan.hro.service.inf.biz.vendor.VendorServiceService;

public class VendorServiceAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_VENDOR_SERVICE";

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
   // Reviewed by Kevin Jin at 2013-11-21
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

         // ��÷���ID
         final String serviceId = request.getParameter( "serviceId" );

         // ��ȡ��Ӧ��ID
         final String vendorId = request.getParameter( "vendorId" );

         // ��ȡ�籣����ID
         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         if ( KANUtil.filterEmpty( vendorId, "0" ) != null && KANUtil.filterEmpty( sbSolutionId, "0" ) != null )
         {
            // ��ʼ��Service�ӿ�
            final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );

            // ʵ����VendorServiceVO
            final VendorServiceVO vendorServiceVO = new VendorServiceVO();
            vendorServiceVO.setVendorId( vendorId );
            vendorServiceVO.setSbHeaderId( sbSolutionId );

            // ���VendorServiceVO�б�
            final List< Object > vendorServiceVOs = vendorServiceService.getVendorServiceVOsByCondition( vendorServiceVO );

            // ����������
            if ( vendorServiceVOs != null && vendorServiceVOs.size() > 0 )
            {
               for ( Object vendorServiceVOObject : vendorServiceVOs )
               {
                  final VendorServiceVO tempVendorServiceVO = ( VendorServiceVO ) vendorServiceVOObject;
                  tempVendorServiceVO.reset( mapping, request );

                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( tempVendorServiceVO.getServiceId() );

                  // ��������Ļ���
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( tempVendorServiceVO.getDecodeServiceIds() );
                  }
                  // ��������Ļ���
                  else
                  {
                     mappingVO.setMappingValue( tempVendorServiceVO.getDecodeServiceIds() );
                  }

                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "vendorServiceId", serviceId ) );
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
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
         // ���Action Form
         final VendorServiceVO vendorServiceVO = ( VendorServiceVO ) form;

         // �����Action��ɾ��
         if ( vendorServiceVO.getSubAction() != null && vendorServiceVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��Ҫ���õ�ǰ�û�AccountId
         vendorServiceVO.setAccountId( getAccountId( request, response ) );

         // ���vendorId
         final String vendorId = KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) );
         vendorServiceVO.setVendorId( vendorId );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder vendorServicePagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         vendorServicePagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         vendorServicePagedListHolder.setObject( vendorServiceVO );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         vendorServiceService.getVendorServiceVOsByCondition( vendorServicePagedListHolder, false );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( vendorServicePagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", vendorServicePagedListHolder );

         // �����AJAX���ã���ֱ�Ӵ�ֵ��table JSPҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listVendorServiceTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listVendorService" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
            final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��õ�ǰFORM
            final VendorServiceVO vendorServiceVO = ( VendorServiceVO ) form;
            final String cityServiceId = request.getParameter( "cityServiceId" );
            vendorServiceVO.setCityId( cityServiceId );
            // �趨��ǰ�û�
            vendorServiceVO.setAccountId( getAccountId( request, response ) );
            vendorServiceVO.setCreateBy( getUserId( request, response ) );
            vendorServiceVO.setModifyBy( getUserId( request, response ) );
            vendorServiceVO.setVendorId( vendorId );
            // ������ӷ���
            vendorServiceService.insertVendorService( vendorServiceVO );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, vendorServiceVO, Operate.ADD, vendorServiceVO.getServiceId(), null );
         }
         // ���FORM
         ( ( VendorServiceVO ) form ).reset();
         return new VendorAction().to_objectModify( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �趨�Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // ������ȡ�����
         final String serviceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // ���VendorServiceVO����
         final VendorServiceVO vendorServiceVO = vendorServiceService.getVendorServiceVOByServiceId( serviceId );
         // ��ȡVendorVO
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorServiceVO.getVendorId() );
         vendorVO.reset( null, request );
         vendorServiceVO.reset( null, request );
         // ���City Id�������Province Id
         if ( vendorServiceVO.getCityId() != null && !vendorServiceVO.getCityId().trim().equals( "" ) && !vendorServiceVO.getCityId().trim().equals( "0" ) )
         {
            vendorServiceVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorServiceVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorServiceVO.setCityIdTemp( vendorServiceVO.getCityId() );
         }
         // �����޸����
         vendorServiceVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "vendorServiceForm", vendorServiceVO );
         request.setAttribute( "vendorForm", vendorVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageVendorServiceForm" );
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
            final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
            // ������ȡ�����
            final String serviceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "serviceId" ), "UTF-8" ) );
            // ���VendorServiceVO����
            final VendorServiceVO vendorServiceVO = vendorServiceService.getVendorServiceVOByServiceId( serviceId );
            final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            final String cityServiceId = request.getParameter( "cityServiceId" );
            // װ�ؽ��洫ֵ
            vendorServiceVO.update( ( ( VendorServiceVO ) form ) );
            vendorServiceVO.setCityId( cityServiceId );
            vendorServiceVO.setVendorId( vendorId );
            // ��ȡ��¼�û�
            vendorServiceVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            vendorServiceService.updateVendorService( vendorServiceVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, vendorServiceVO, Operate.MODIFY, vendorServiceVO.getServiceId(), null );
         }
         // ���FORM
         ( ( VendorServiceVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return new VendorAction().to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
         // ���Action Form
         VendorServiceVO vendorServiceVO = ( VendorServiceVO ) form;
         final String selectedIds = vendorServiceVO.getSelectedIds();
         if ( selectedIds != null && !selectedIds.equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : selectedIds.split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  vendorServiceVO = vendorServiceService.getVendorServiceVOByServiceId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // ����ɾ���ӿ�
                  vendorServiceVO.setModifyBy( getUserId( request, response ) );
                  vendorServiceVO.setModifyDate( new Date() );
                  vendorServiceService.deleteVendorService( vendorServiceVO );
               }
            }

            insertlog( request, vendorServiceVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( selectedIds ) );
         }
         // ���Selected IDs����Action
         vendorServiceVO.setSelectedIds( "" );
         vendorServiceVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // �������ع�Ӧ�̷���
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ������ȡ�����
         final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         // ��ʼ��employeeContractService�ӿ�
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );

         final PagedListHolder pagedListHolder = new PagedListHolder();
         final VendorServiceVO vendorServiceVO = new VendorServiceVO();
         vendorServiceVO.setVendorId( vendorId );
         vendorServiceVO.setSortOrder( "ASC" );
         vendorServiceVO.setSortColumn( "cityId,sbHeaderId" );
         pagedListHolder.setObject( vendorServiceVO );
         vendorServiceService.getVendorServiceVOsByCondition( pagedListHolder, false );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );
         request.setAttribute( "pagedListHolder", pagedListHolder );
         // ��Ҫ������
         request.setAttribute( "noLink", "true" );

         return mapping.findForward( "listVendorServiceTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
