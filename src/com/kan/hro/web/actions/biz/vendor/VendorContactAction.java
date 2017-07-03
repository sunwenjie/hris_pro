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
   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_VENDOR_CONTACT";

   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

         // 初始化 JSONArray
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

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // 获得Action Form
         final VendorContactVO vendorContactVO = ( VendorContactVO ) form;
         // 需要设置当前用户AccountId
         vendorContactVO.setAccountId( getAccountId( request, response ) );

         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, vendorContactVO );

         // 如果没有指定排序则默认按 vendorId排序
         if ( vendorContactVO.getSortColumn() == null || vendorContactVO.getSortColumn().isEmpty() )
         {
            vendorContactVO.setSortColumn( "vendorContactId" );
            vendorContactVO.setSortOrder( "desc" );
         }

         // 获得SubAction
         final String subAction = getSubAction( form );
         // 添加自定义搜索内容 
         vendorContactVO.setRemark1( generateDefineListSearches( request, accessAction ) );
         // 处理SubAction
         dealSubAction( vendorContactVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder vendorContactPagedListHolder = new PagedListHolder();
         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS )
               || subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
         {
            // 传入当前页
            vendorContactPagedListHolder.setPage( page );
            // 传入当前值对象
            vendorContactPagedListHolder.setObject( vendorContactVO );
            // 设置页面记录条数
            vendorContactPagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            vendorContactService.getVendorContactVOsByCondition( vendorContactPagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( vendorContactPagedListHolder, request );

         }
         request.setAttribute( "accountId", getAccountId( request, null ) );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", vendorContactPagedListHolder );
         // 处理Return
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
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化下拉选项
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 初始化联系人
         String vendorContactId = request.getParameter( "contactId" );

         // 如果VendorId不为空
         if ( request.getParameter( "vendorId" ) != null && !request.getParameter( "vendorId" ).trim().isEmpty() )
         {
            // 初始化Service接口
            final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );

            // 获取VendorVO
            final VendorVO vendorVO = vendorService.getVendorVOByVendorId( KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) ) );

            // 联系人为空的情况，默认取主要联系人
            if ( vendorContactId == null || vendorContactId.trim().isEmpty() )
            {
               vendorContactId = vendorVO.getMainContact();
            }

            final List< Object > vendorContactVOs = vendorContactService.getVendorContactVOsByVendorId( KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) ) );

            if ( vendorContactVOs != null && vendorContactVOs.size() > 0 )
            {
               // 遍历
               for ( Object vendorContactVOObject : vendorContactVOs )
               {
                  final VendorContactVO tempVendorContactVO = ( VendorContactVO ) vendorContactVOObject;

                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( tempVendorContactVO.getVendorContactId() );

                  // 中文
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

      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         this.saveToken( request );

         // 初始化PositionVO
         final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

         // 获得ActionForm 
         final VendorContactVO vendorContactVO = ( VendorContactVO ) form;

         // 如果是Vendor页面添加
         if ( KANUtil.filterEmpty( request.getParameter( "vendorId" ) ) != null && KANUtil.filterEmpty( vendorContactVO.getSubAction() ) == null )
         {
            vendorContactVO.setVendorId( Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "vendorId" ), "UTF-8" ) ) );
         }

         // 设置所属人所属部门
         if ( positionVO != null )
         {
            vendorContactVO.setBranch( positionVO.getBranchId() );
            vendorContactVO.setOwner( positionVO.getPositionId() );
         }

         // 设置Sub Action 
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
            // 初始化Service接口
            final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

            // 获得当前FORM
            final VendorContactVO vendorContactVO = ( VendorContactVO ) form;

            // 返回页面vendorId
            final String vendorId = vendorContactVO.getVendorId();

            // 校验当前form中vendorId是否有效
            checkEmployeeId( mapping, vendorContactVO, request, response );

            // 不合法的vendorId跳转新增页面
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "vendorIdErrorMsg" ) ) != null )
            {
               vendorContactVO.reset();
               vendorContactVO.setVendorId( vendorId );
               return to_objectNew( mapping, vendorContactVO, request, response );
            }

            // 设定当前用户
            vendorContactVO.setAccountId( getAccountId( request, response ) );
            vendorContactVO.setCreateBy( getUserId( request, response ) );
            vendorContactVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            vendorContactVO.setRemark1( saveDefineColumns( request, accessAction ) );

            vendorContactVO.setRole( getRole( request, response ) );

            // 调用添加方法
            vendorContactService.insertVendorContact( vendorContactVO );

            // 查找该联系人是否是供应商第一个联系人，如果是该新建联系人为供应商默认联系人 
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

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, vendorContactVO, Operate.ADD, vendorContactVO.getVendorContactId(), null );
         }
         else
         {
            // 清空form
            ( ( VendorContactVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转查看界面
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // 主键获取需解码
         String vendorContactId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( vendorContactId == null || vendorContactId.trim().isEmpty() )
         {
            vendorContactId = ( ( VendorContactVO ) form ).getVendorContactId();
         }

         // 获得VendorContactVO对象
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );
         // 设置SubAction
         vendorContactVO.setSubAction( VIEW_OBJECT );
         vendorContactVO.reset( null, request );
         // 如果City Id，则填充Province Id
         if ( vendorContactVO.getCityId() != null && !vendorContactVO.getCityId().trim().equals( "" ) && !vendorContactVO.getCityId().trim().equals( "0" ) )
         {
            vendorContactVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorContactVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorContactVO.setCityIdTemp( vendorContactVO.getCityId() );
         }
         // 将ColumnVO传入request对象
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
    *	供应商登录显示供应商联系人信息
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    * Add By：Jack  
    * Add Date：2014-4-14  
    */
   public ActionForward to_objectModify_inVendor( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // 主键获取需解码
         String vendorContactId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得VendorContactVO对象
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );
         // 设置SubAction
         vendorContactVO.setSubAction( VIEW_OBJECT );
         vendorContactVO.reset( null, request );

         // 如果City Id，则填充Province Id
         if ( vendorContactVO.getCityId() != null && !vendorContactVO.getCityId().trim().equals( "" ) && !vendorContactVO.getCityId().trim().equals( "0" ) )
         {
            vendorContactVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorContactVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorContactVO.setCityIdTemp( vendorContactVO.getCityId() );
         }

         // 将ColumnVO传入request对象
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
            // 初始化Service接口
            final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
            // 主键获取需解码
            final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得VendorContactVO对象
            final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorId );
            // 装载界面传值
            vendorContactVO.update( ( ( VendorContactVO ) form ) );
            // systemId 发邮件用
            vendorContactVO.setSystemId( ( ( VendorContactVO ) form ).getSystemId() );
            // 获取登录用户
            vendorContactVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            vendorContactVO.setRemark1( saveDefineColumns( request, accessAction ) );

            vendorContactVO.setRole( getRole( request, response ) );

            // 调用修改方法
            vendorContactService.updateVendorContact( vendorContactVO );
            // 返回编辑成功标记
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
         // 初始化Service接口
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // 获得删除对象
         final String vendorContactId = request.getParameter( "vendorContactId" );
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );
         vendorContactVO.setModifyBy( getUserId( request, response ) );
         vendorContactVO.setModifyDate( new Date() );
         // 调用删除接口
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
         // 初始化Service接口
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

         // 获得Action Form
         VendorContactVO vendorContactVO = ( VendorContactVO ) form;
         final String selectedIds = vendorContactVO.getSelectedIds();
         if ( selectedIds != null && !selectedIds.equals( "" ) )
         {
            // 分割
            for ( String selectedId : selectedIds.split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // 调用删除接口
                  vendorContactVO.setModifyBy( getUserId( request, response ) );
                  vendorContactVO.setModifyDate( new Date() );
                  vendorContactService.deleteVendorContact( vendorContactVO );
               }
            }

            insertlog( request, vendorContactVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( selectedIds ) );
         }

         // 清除Selected IDs和子Action
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
         // 初始化Row
         long rows = 0;

         // 初始化Service接口
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

         // 获取主键
         final String vendorContactId = KANUtil.decodeStringFromAjax( request.getParameter( "vendorContactId" ) );

         // 获取VendorContactVO
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );
         vendorContactVO.setModifyBy( getUserId( request, response ) );
         vendorContactVO.setModifyDate( new Date() );
         // 调用删除接口
         rows = vendorContactService.deleteVendorContact( vendorContactVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
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
         // 初始化Service接口
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );

         request.setAttribute( "vendorContactForm", vendorContactVO );
         // Ajax调用
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
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化 Service
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );

         // 获取VendorContactId
         final String vendorContactId = request.getParameter( "vendorContactId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         // 初始化VendorContactVO
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

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   /**  
    * 检查输入VendorId是否有效
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
      // 初始化Service接口
      final VendorService vendorService = ( VendorService ) getService( "vendorService" );

      // 获得ActionForm
      final VendorContactVO vendorContactVO = ( VendorContactVO ) form;

      // 试图获取VendorVO 
      final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorContactVO.getVendorId() );

      // 不存在VendorVO或AccountId不匹配当前
      if ( vendorVO == null
            || ( vendorVO != null && KANUtil.filterEmpty( vendorVO.getAccountId() ) != null && !vendorVO.getAccountId().equals( getAccountId( request, response ) ) ) )
      {
         request.setAttribute( "vendorIdErrorMsg", "供应商ID输入无效！" );
      }

   }

}
