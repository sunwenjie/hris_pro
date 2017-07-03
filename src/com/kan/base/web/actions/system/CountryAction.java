package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.system.CountryVO;
import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.CountryService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

public class CountryAction extends BaseAction
{

   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final CountryVO countryVO = ( CountryVO ) form;
         // 初始化Service接口
         final CountryService countryService = ( CountryService ) getService( "countryService" );

         // 如果子Action是删除列表
         if ( countryVO.getSubAction() != null && countryVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder countryHolder = new PagedListHolder();

         // 传入当前页
         countryHolder.setPage( page );
         // 传入当前值对象
         countryHolder.setObject( countryVO );
         // 设置页面记录条数
         countryHolder.setPageSize( listPageSize );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         countryHolder = countryService.getCountryVOByCondition( countryHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( countryHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "countryHolder", countryHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listCountryTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listCountry" );
   }

   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( CountryVO ) form ).setStatus( AccountVO.TRUE );
      ( ( CountryVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageCountry" );
   }

   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final CountryService countryService = ( CountryService ) getService( "countryService" );
            // 获得ActionForm
            final CountryVO countryVO = ( CountryVO ) form;

            countryVO.setCreateBy( getUserId( request, response ) );
            countryVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            countryService.insertCountry( countryVO );

            // 清空Form条件
            ( ( CountryVO ) form ).reset();
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
         final CountryService countryService = ( CountryService ) getService( "countryService" );
         // 获得Action Form
         final CountryVO countryVO = ( CountryVO ) form;

         // 存在选中的ID
         if ( countryVO.getSelectedIds() != null && !countryVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : countryVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               countryVO.setCountryId( selectedId );
               countryVO.setModifyBy( getUserId( request, response ) );
               countryService.deleteCountry( countryVO );
            }
         }

         // 清除Selected IDs和子Action
         countryVO.setSelectedIds( "" );
         countryVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            final CountryService countryService = ( CountryService ) getService( "countryService" );
            final String countryId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "countryId" ), "GBK" ) );
            final CountryVO countryVO = countryService.getCountryVOByCountryId( Integer.valueOf( countryId ) );

            // 装载界面传值
            countryVO.update( ( CountryVO ) form );

            // 获取登录用户
            countryVO.setModifyBy( getUserId( request, response ) );
            countryService.updateCountry( countryVO );

            // 清空Form条件
            ( ( CountryVO ) form ).reset();
         }

         // 操作成功跳往Province List界面
         return new ProvinceAction().list_object( mapping, new ProvinceVO(), request, response );
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
