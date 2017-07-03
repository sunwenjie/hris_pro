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

   // 当前Action对应的Access Action
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

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化下拉选项
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 获得服务ID
         final String serviceId = request.getParameter( "serviceId" );

         // 获取供应商ID
         final String vendorId = request.getParameter( "vendorId" );

         // 获取社保方案ID
         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         if ( KANUtil.filterEmpty( vendorId, "0" ) != null && KANUtil.filterEmpty( sbSolutionId, "0" ) != null )
         {
            // 初始化Service接口
            final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );

            // 实例化VendorServiceVO
            final VendorServiceVO vendorServiceVO = new VendorServiceVO();
            vendorServiceVO.setVendorId( vendorId );
            vendorServiceVO.setSbHeaderId( sbSolutionId );

            // 获得VendorServiceVO列表
            final List< Object > vendorServiceVOs = vendorServiceService.getVendorServiceVOsByCondition( vendorServiceVO );

            // 生成下拉框
            if ( vendorServiceVOs != null && vendorServiceVOs.size() > 0 )
            {
               for ( Object vendorServiceVOObject : vendorServiceVOs )
               {
                  final VendorServiceVO tempVendorServiceVO = ( VendorServiceVO ) vendorServiceVOObject;
                  tempVendorServiceVO.reset( mapping, request );

                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( tempVendorServiceVO.getServiceId() );

                  // 如果是中文环境
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( tempVendorServiceVO.getDecodeServiceIds() );
                  }
                  // 如果是中文环境
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

      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
         // 获得Action Form
         final VendorServiceVO vendorServiceVO = ( VendorServiceVO ) form;

         // 如果子Action是删除
         if ( vendorServiceVO.getSubAction() != null && vendorServiceVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 需要设置当前用户AccountId
         vendorServiceVO.setAccountId( getAccountId( request, response ) );

         // 获得vendorId
         final String vendorId = KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) );
         vendorServiceVO.setVendorId( vendorId );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder vendorServicePagedListHolder = new PagedListHolder();

         // 传入当前页
         vendorServicePagedListHolder.setPage( page );
         // 传入当前值对象
         vendorServicePagedListHolder.setObject( vendorServiceVO );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         vendorServiceService.getVendorServiceVOsByCondition( vendorServicePagedListHolder, false );
         // 刷新Holder，国际化传值
         refreshHolder( vendorServicePagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", vendorServicePagedListHolder );

         // 如果是AJAX调用，则直接传值给table JSP页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listVendorServiceTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
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
            // 初始化Service接口
            final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
            final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得当前FORM
            final VendorServiceVO vendorServiceVO = ( VendorServiceVO ) form;
            final String cityServiceId = request.getParameter( "cityServiceId" );
            vendorServiceVO.setCityId( cityServiceId );
            // 设定当前用户
            vendorServiceVO.setAccountId( getAccountId( request, response ) );
            vendorServiceVO.setCreateBy( getUserId( request, response ) );
            vendorServiceVO.setModifyBy( getUserId( request, response ) );
            vendorServiceVO.setVendorId( vendorId );
            // 调用添加方法
            vendorServiceService.insertVendorService( vendorServiceVO );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, vendorServiceVO, Operate.ADD, vendorServiceVO.getServiceId(), null );
         }
         // 清空FORM
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
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // 主键获取需解码
         final String serviceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // 获得VendorServiceVO对象
         final VendorServiceVO vendorServiceVO = vendorServiceService.getVendorServiceVOByServiceId( serviceId );
         // 获取VendorVO
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorServiceVO.getVendorId() );
         vendorVO.reset( null, request );
         vendorServiceVO.reset( null, request );
         // 如果City Id，则填充Province Id
         if ( vendorServiceVO.getCityId() != null && !vendorServiceVO.getCityId().trim().equals( "" ) && !vendorServiceVO.getCityId().trim().equals( "0" ) )
         {
            vendorServiceVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorServiceVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorServiceVO.setCityIdTemp( vendorServiceVO.getCityId() );
         }
         // 区分修改添加
         vendorServiceVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
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
            // 初始化Service接口
            final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
            // 主键获取需解码
            final String serviceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "serviceId" ), "UTF-8" ) );
            // 获得VendorServiceVO对象
            final VendorServiceVO vendorServiceVO = vendorServiceService.getVendorServiceVOByServiceId( serviceId );
            final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            final String cityServiceId = request.getParameter( "cityServiceId" );
            // 装载界面传值
            vendorServiceVO.update( ( ( VendorServiceVO ) form ) );
            vendorServiceVO.setCityId( cityServiceId );
            vendorServiceVO.setVendorId( vendorId );
            // 获取登录用户
            vendorServiceVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            vendorServiceService.updateVendorService( vendorServiceVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, vendorServiceVO, Operate.MODIFY, vendorServiceVO.getServiceId(), null );
         }
         // 清空FORM
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
         // 初始化Service接口
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
         // 获得Action Form
         VendorServiceVO vendorServiceVO = ( VendorServiceVO ) form;
         final String selectedIds = vendorServiceVO.getSelectedIds();
         if ( selectedIds != null && !selectedIds.equals( "" ) )
         {
            // 分割
            for ( String selectedId : selectedIds.split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  vendorServiceVO = vendorServiceService.getVendorServiceVOByServiceId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // 调用删除接口
                  vendorServiceVO.setModifyBy( getUserId( request, response ) );
                  vendorServiceVO.setModifyDate( new Date() );
                  vendorServiceService.deleteVendorService( vendorServiceVO );
               }
            }

            insertlog( request, vendorServiceVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( selectedIds ) );
         }
         // 清除Selected IDs和子Action
         vendorServiceVO.setSelectedIds( "" );
         vendorServiceVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // 审批加载供应商服务
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 主键获取需解码
         final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         // 初始化employeeContractService接口
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );

         final PagedListHolder pagedListHolder = new PagedListHolder();
         final VendorServiceVO vendorServiceVO = new VendorServiceVO();
         vendorServiceVO.setVendorId( vendorId );
         vendorServiceVO.setSortOrder( "ASC" );
         vendorServiceVO.setSortColumn( "cityId,sbHeaderId" );
         pagedListHolder.setObject( vendorServiceVO );
         vendorServiceService.getVendorServiceVOsByCondition( pagedListHolder, false );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );
         request.setAttribute( "pagedListHolder", pagedListHolder );
         // 不要超链接
         request.setAttribute( "noLink", "true" );

         return mapping.findForward( "listVendorServiceTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
