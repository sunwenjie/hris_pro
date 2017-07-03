package com.kan.base.web.actions.management;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.domain.management.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.PositionGradeCurrencyService;
import com.kan.base.service.inf.management.PositionGradeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class PositionGradeCurrencyAction extends BaseAction
{
   /**
    * AccessAction
    */
   public static final String accessAction = "HRO_EMPLOYEE_POSITIONGRADES";

   @Override
   // Code reviewed by Siuvan Xia at 2014-07-17
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否AJAX调用
         final String ajax = request.getParameter( "ajax" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;

         // 如果子Action是删除
         if ( positionGradeCurrencyVO.getSubAction() != null && positionGradeCurrencyVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( positionGradeCurrencyVO );
         }

         // 初始化PositionGradeService
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );

         //获得positionGradeId
         String positionGradeId = request.getParameter( "positionGradeId" );
         if ( KANUtil.filterEmpty( positionGradeId ) == null )
         {
            positionGradeId = positionGradeCurrencyVO.getPositionGradeId();
         }
         else
         {
            positionGradeId = KANUtil.decodeStringFromAjax( positionGradeId );
         }

         // 根据主键获得 positionGradeVO对象
         final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );
         positionGradeVO.setSubAction( VIEW_OBJECT );
         positionGradeVO.reset( null, request );
         request.setAttribute( "mgtPositionGradeForm", positionGradeVO );

         // 根据外键查找，得到SearchDetailVO集合
         positionGradeCurrencyVO.setPositionGradeId( positionGradeId );

         // 此处分页代码
         final PagedListHolder positionGradeCurrencyHolder = new PagedListHolder();
         // 传入当前页
         positionGradeCurrencyHolder.setPage( page );
         // 传入当前值对象
         positionGradeCurrencyHolder.setObject( positionGradeCurrencyVO );
         // 设置页面记录条数
         positionGradeCurrencyHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         positionGradeCurrencyService.getPositionGradeCurrencyVOsByCondition( positionGradeCurrencyHolder, true );
         refreshHolder( positionGradeCurrencyHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "positionGradeCurrencyHolder", positionGradeCurrencyHolder );

         // 初始化字段
         positionGradeCurrencyVO.setSubAction( "" );
         positionGradeCurrencyVO.setStatus( PositionGradeCurrencyVO.TRUE );
         request.setAttribute( "positionGradeCurrencyForm", positionGradeCurrencyVO );

         // 如果是调用则返回ajax
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPositionGradeCurrencyTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转页面
      return mapping.findForward( "listPositionGradeCurrency" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );

            // 获取positionGradeId
            final String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionGradeId" ), "UTF-8" ) );
            // 获得ActionFrom
            final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;
            // 设置positionGradeId
            positionGradeCurrencyVO.setPositionGradeId( positionGradeId );
            positionGradeCurrencyVO.setAccountId( getAccountId( request, response ) );
            positionGradeCurrencyVO.setCreateBy( getUserId( request, response ) );
            positionGradeCurrencyVO.setModifyBy( getUserId( request, response ) );
            // 调用insert方法
            positionGradeCurrencyService.insertPositionGradeCurrency( positionGradeCurrencyVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // 清空Action Form
         ( ( PositionGradeCurrencyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // no use
      return null;
   }

   // Add by siuvan at 2014-07-17
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );

         // 获取currencyId
         final String currencyId = KANUtil.decodeString( request.getParameter( "currencyId" ) );

         // 获取PositionGradeCurrencyVO 
         final PositionGradeCurrencyVO positionGradeCurrencyVO = positionGradeCurrencyService.getPositionGradeCurrencyVOByCurrencyId( currencyId );
         // 国际化传值
         positionGradeCurrencyVO.reset( null, request );
         // 设置SubAction
         positionGradeCurrencyVO.setSubAction( VIEW_OBJECT );

         // 获取PositionGradeVO 
         final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeCurrencyVO.getPositionGradeId() );

         // 写入Request
         request.setAttribute( "mgtPositionGradeCurrencyForm", positionGradeCurrencyVO );
         request.setAttribute( "mgtPositionGradeForm", positionGradeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "managePositionGradeCurrencyForm" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );
            // 获得当前主键
            final String currencyId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "currencyId" ), "UTF-8" ) );
            // 根据ID获取对应的positionGradeCurrencyVO
            final PositionGradeCurrencyVO positionGradeCurrencyVO = positionGradeCurrencyService.getPositionGradeCurrencyVOByCurrencyId( currencyId );
            // 更新positionGradeCurrencyVO
            positionGradeCurrencyVO.update( ( PositionGradeCurrencyVO ) form );
            // 获得登录用户
            positionGradeCurrencyVO.setModifyBy( getUserId( request, response ) );
            // 更新数据库
            positionGradeCurrencyService.updatePositionGradeCurrency( positionGradeCurrencyVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }

         // 清空Form
         ( ( PositionGradeCurrencyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   @Override
   public void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取service接口
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );
         // 获得当前form
         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;
         // 存在选中的ID
         if ( positionGradeCurrencyVO.getSelectedIds() != null && !positionGradeCurrencyVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : positionGradeCurrencyVO.getSelectedIds().split( "," ) )
            {
               // 根据ID查找数据库中对应的VO
               final PositionGradeCurrencyVO positionGradeCurrencyVOForDel = positionGradeCurrencyService.getPositionGradeCurrencyVOByCurrencyId( selectedId );
               // 设置positionGradeCurrencyVO的相关属性值
               positionGradeCurrencyVOForDel.setModifyBy( getUserId( request, response ) );
               positionGradeCurrencyVOForDel.setModifyDate( new Date() );
               // 调用删除方法
               positionGradeCurrencyService.deletePositionGradeCurrency( positionGradeCurrencyVOForDel );
            }
         }

         // 清除Selected IDs和子Action
         ( ( PositionGradeCurrencyVO ) form ).setSelectedIds( "" );
         ( ( PositionGradeCurrencyVO ) form ).setSubAction( "" );

         // 清空Form条件
         ( ( PositionGradeCurrencyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use

   }

}
