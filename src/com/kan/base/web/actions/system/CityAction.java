package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import com.kan.base.service.inf.system.CityService;
import com.kan.base.service.inf.system.CountryService;
import com.kan.base.service.inf.system.ProvinceService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class CityAction extends BaseAction
{

   /**  
    * List city options by Ajax
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
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 获取省份Id和城市Id
         final String provinceId = request.getParameter( "provinceId" );
         final String cityId = request.getParameter( "cityId" );

         // 实例化CityMappingVOs
         List< MappingVO > cityMappingVOs = new ArrayList< MappingVO >();
         // 添加“请选择”选项
         cityMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 如果ProvinceId存在
         if ( provinceId != null )
         {
            // 从常量中获取当前省份对应的CityMappingVOs
            cityMappingVOs = KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( cityMappingVOs, "cityId", cityId ) );
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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final CityVO cityVO = ( CityVO ) form;

         // 如果子Action是删除列表
         if ( cityVO.getSubAction() != null && cityVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 初始化Service接口
         final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
         final CityService cityService = ( CityService ) getService( "cityService" );

         // 获得参数 provinceId，插入城市需要知道是哪个省份的
         final String provinceId = KANUtil.decodeStringFromAjax( request.getParameter( "provinceId" ) );

         // 查找到当前城市列表对应的省份对象
         final ProvinceVO provinceVO = provinceService.getProvinceVOByProvinceId( Integer.valueOf( provinceId ) );
         // 找到国家的中文名字，用于前端Navigation显示
         final CountryVO countryVO = ( ( CountryService ) getService( "countryService" ) ).getCountryVOByCountryId( Integer.valueOf( provinceVO.getCountryId() ) );
         //存入国家的名字
         request.setAttribute( "countryNameZH", countryVO.getCountryNameZH() );
         // 初始化ProvinceVO对象  
         provinceVO.reset( null, request );
         request.setAttribute( "provinceForm", provinceVO );

         //此处为城市列表
         PagedListHolder cityHolder = new PagedListHolder();
         cityHolder.setPage( page );
         cityVO.setProvinceId( provinceId );
         cityHolder.setObject( cityVO );
         cityHolder.setPageSize( listPageSize );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         cityService.getCityVOByCondition( cityHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( cityHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "cityHolder", cityHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listCityTable" );
         }

         // New CityVO的时候，初始化Status为True
         ( ( CityVO ) form ).setStatus( CityVO.TRUE );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listCity" );
   }

   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final CityService cityService = ( CityService ) getService( "cityService" );
            // 获得ActionForm
            final CityVO cityVO = ( CityVO ) form;
            // 获取省份的ID
            final String provinceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "provinceId" ), "GBK" ) );

            cityVO.setProvinceId( provinceId );
            cityVO.setCreateBy( getUserId( request, response ) );
            cityVO.setModifyBy( getUserId( request, response ) );

            // 新建对象
            cityService.insertCity( cityVO );

            // 清空Form条件
            ( ( CityVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final CityService cityService = ( CityService ) getService( "cityService" );
         // 获得当前主键
         final String cityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "cityId" ), "GBK" ) );
         // 获得当前城市对应的省份
         final String provinceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "provinceId" ), "GBK" ) );

         //找到该城市的省份
         final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
         final ProvinceVO provinceVO = provinceService.getProvinceVOByProvinceId( Integer.valueOf( provinceId ) );
         //存入request对象
         request.setAttribute( "provinceVO", provinceVO );

         //找到该城市的国家
         final CountryService countryService = ( CountryService ) getService( "countryService" );
         final CountryVO countryVO = countryService.getCountryVOByCountryId( Integer.valueOf( provinceVO.getCountryId() ) );
         //存入国家的名字
         request.setAttribute( "countryNameZH", countryVO.getCountryNameZH() );

         final CityVO cityVO = cityService.getCityVOByCityId( Integer.valueOf( cityId ) );
         cityVO.reset( null, request );
         request.setAttribute( "cityForm", cityVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageCity" );
   }

   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final CityService cityService = ( CityService ) getService( "cityService" );
            // 获得当前主键
            final String cityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "cityId" ), "GBK" ) );
            // 获得主键对应对象
            final CityVO cityVO = cityService.getCityVOByCityId( Integer.valueOf( cityId ) );

            // 装载界面传值
            cityVO.update( ( CityVO ) form );

            // 获取登录用户
            cityVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            cityService.updateCity( cityVO );

            // 清空Form条件
            ( ( CityVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final CityService cityService = ( CityService ) getService( "cityService" );
         // 初始化Action Form
         final CityVO cityVO = ( CityVO ) form;

         // 存在选中的ID
         if ( cityVO.getSubAction() != null && cityVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 分割
            for ( String selectedId : cityVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               cityVO.setCityId( selectedId );
               cityVO.setModifyBy( getUserId( request, response ) );
               cityService.deleteCity( cityVO );
            }
         }
         // 清除Selected IDs和子Action
         cityVO.setSelectedIds( "" );
         cityVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
