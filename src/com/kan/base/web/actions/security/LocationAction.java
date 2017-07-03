package com.kan.base.web.actions.security;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.LocationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.LocationService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class LocationAction extends BaseAction
{
   public static String accessAction = "HRO_SYS_LOCATION";

   /**
    * List location
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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );
         // 获得Action Form
         final LocationVO locationVO = ( LocationVO ) form;

         // 如果子Action是删除用户列表
         if ( locationVO.getSubAction() != null && locationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( locationVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder locationHolder = new PagedListHolder();

         // 传入当前页
         locationHolder.setPage( page );
         // 传入当前值对象
         locationHolder.setObject( locationVO );
         // 设置页面记录条数
         locationHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         locationService.getLocationVOsByCondition( locationHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( locationHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "locationHolder", locationHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax调用无跳转
            return mapping.findForward( "listLocationTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listLocation" );
   }

   /**
    * to_objectNew
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( LocationVO ) form ).setStatus( LocationVO.TRUE );
      ( ( LocationVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageLocation" );
   }

   /**
    * Add location
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final LocationService locationService = ( LocationService ) getService( "secLocationService" );

            // 获得ActionForm
            final LocationVO locationVO = ( LocationVO ) form;
            // 获取登录用户

            locationVO.setAccountId( getAccountId( request, response ) );
            locationVO.setCreateBy( getUserId( request, response ) );
            locationVO.setModifyBy( getUserId( request, response ) );

            final String positionId = request.getParameter( "positionId" );
            // 新建对象
            locationService.insertLocation( locationVO, positionId );

            // 初始化常量持久对象
            constantsInit( "initLocation", getAccountId( request, response ) );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, locationVO, Operate.ADD, locationVO.getLocationId(), null );
         }
         else
         {
            // 清空Form
            ( ( LocationVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return to_objectModify( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * to_objectModify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );
         // 获得当前主键
         String locationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( KANUtil.filterEmpty( locationId ) == null )
         {
            locationId = ( ( LocationVO ) form ).getLocationId();
         }

         // 获得主键对应对象
         final LocationVO locationVO = locationService.getLocationVOByLocationId( locationId );
         // 刷新对象，初始化对象列表及国际化
         locationVO.reset( null, request );

         // 如果City Id，则填充Province Id
         if ( locationVO.getCityId() != null && !locationVO.getCityId().trim().equals( "" ) && !locationVO.getCityId().trim().equals( "0" ) )
         {
            locationVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( locationVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
         }

         locationVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "locationForm", locationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageLocation" );
   }

   /**
    * Modify location
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final LocationService locationService = ( LocationService ) getService( "secLocationService" );
            // 获得当前主键
            final String locationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得LocationVO
            final LocationVO locationVO = locationService.getLocationVOByLocationId( locationId );
            // 获取登录用户
            locationVO.update( ( LocationVO ) form );
            locationVO.setModifyBy( getUserId( request, response ) );
            final String positionId = request.getParameter( "positionId" );
            // 修改对象
            locationService.updateLocation( locationVO, positionId );

            // 初始化常量持久对象
            constantsInit( "initLocation", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, locationVO, Operate.MODIFY, locationVO.getLocationId(), null );
         }

         // 清空Form条件
         ( ( LocationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete location
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );

         final LocationVO locationVO = new LocationVO();
         // 获得当前主键
         String locationId = request.getParameter( "locationId" );

         // 删除主键对应对象
         locationVO.setLocationId( locationId );
         locationVO.setModifyBy( getUserId( request, response ) );
         // 删除对象
         locationService.deleteLocation( locationVO );

         insertlog( request, locationVO, Operate.DELETE, locationId, null );

         // 初始化常量持久对象
         constantsInit( "initLocation", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete location list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );

         // 获得Action Form
         LocationVO locationVO = ( LocationVO ) form;
         // 存在选中的ID
         if ( locationVO.getSelectedIds() != null && !locationVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : locationVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               locationVO.setLocationId( selectedId );
               locationVO.setModifyBy( getUserId( request, response ) );

               locationService.deleteLocation( locationVO );
            }

            insertlog( request, locationVO, Operate.DELETE, null, locationVO.getSelectedIds() );
         }

         // 初始化常量持久对象
         constantsInit( "initLocation", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         locationVO.setSelectedIds( "" );
         locationVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
