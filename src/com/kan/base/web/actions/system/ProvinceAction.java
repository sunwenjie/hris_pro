package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.system.CityVO;
import com.kan.base.domain.system.CountryVO;
import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.CountryService;
import com.kan.base.service.inf.system.ProvinceService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ProvinceAction extends BaseAction
{

   /**  
    * List province options by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
   */
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 获取省份Id
         final String provinceId = request.getParameter( "provinceId" );
         
         // 从常量中获取当前国家对应的Province MappingVOs
         final List< MappingVO > provinceMappingVOs = KANConstants.LOCATION_DTO.getProvinces( request.getLocale().getLanguage() );
         // 添加“请选择”选项
         provinceMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // Send to client
         out.println( KANUtil.getOptionHTML( provinceMappingVOs, "provinceId", provinceId ) );
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

   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final ProvinceVO provinceVO = ( ProvinceVO ) form;

         // 如果子Action是删除列表
         if ( provinceVO.getSubAction() != null && provinceVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化Service接口
         final CountryService countryService = ( CountryService ) getService( "countryService" );
         // 需要获得ProvinceVO列表，所以还需初始化 ProvinceService
         final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );

         // 获得参数 countryId
         final String countryId = KANUtil.decodeStringFromAjax( request.getParameter( "countryId" ) );
         // 调用CountryService获得CountryVO
         final CountryVO countryVO = countryService.getCountryVOByCountryId( Integer.valueOf( countryId ) );
         // 初始化CountryVO对象         
         countryVO.reset( null, request );
         request.setAttribute( "countryForm", countryVO );

         // 调用ProvinceService获得ProvinceVOList
         // 此处分页代码
         PagedListHolder provinceHolder = new PagedListHolder();
         provinceHolder.setPage( page );
         provinceVO.setCountryId( countryId );
         provinceHolder.setObject( provinceVO );
         provinceHolder.setPageSize( listPageSize );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         provinceService.getProvinceVOByCondition( provinceHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( provinceHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "provinceHolder", provinceHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listProvinceTable" );
         }

         // New ProvinceVO的时候，初始化Status为True
         ( ( ProvinceVO ) form ).setStatus( ProvinceVO.TRUE );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listProvince" );
   }

   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
            // 获取国家的ID
            final String countryId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "countryId" ), "UTF-8" ) );

            // 获得ActionForm
            final ProvinceVO provinceVO = ( ProvinceVO ) form;
            provinceVO.setCountryId( countryId );
            provinceVO.setCreateBy( getUserId( request, response ) );
            provinceVO.setModifyBy( getUserId( request, response ) );

            // 新建对象
            provinceService.insertProvince( provinceVO );

            // 清空Form条件
            ( ( ProvinceVO ) form ).reset();
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
            // 获得当前主键
            final String provinceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "provinceId" ), "UTF-8" ) );
            // 获得主键对应对象
            final ProvinceVO provinceVO = provinceService.getProvinceVOByProvinceId( Integer.valueOf( provinceId ) );

            // 装载界面传值
            provinceVO.update( ( ProvinceVO ) form );

            // 获取登录用户
            provinceVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            provinceService.updateProvince( provinceVO );

            // 清空Form条件
            ( ( ProvinceVO ) form ).reset();
         }

         // 操作成功跳往Province List界面
         return new CityAction().list_object( mapping, new CityVO(), request, response );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

      try
      {
         // 初始化Service接口
         final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
         // 初始化Action Form
         final ProvinceVO provinceVO = ( ProvinceVO ) form;
         // 存在选中的ID
         if ( provinceVO.getSelectedIds() != null && !provinceVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : provinceVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               provinceVO.setProvinceId( selectedId );
               provinceVO.setModifyBy( getUserId( request, response ) );
               provinceService.deleteProvince( provinceVO );
            }
         }
         // 清除Selected IDs和子Action
         provinceVO.setSelectedIds( "" );
         provinceVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

}
