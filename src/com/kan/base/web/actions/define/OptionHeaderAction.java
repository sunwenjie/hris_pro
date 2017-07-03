package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.OptionDetailVO;
import com.kan.base.domain.define.OptionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.OptionHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class OptionHeaderAction extends BaseAction
{

   public static String accessAction = "HRO_DEFINE_OPTION";

   @Override
   // Code review by Kevin Jin at 2013-07-01
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final OptionHeaderVO optionHeaderVO = ( OptionHeaderVO ) form;

         // 处理SubAction
         dealSubAction( optionHeaderVO, mapping, form, request, response );

         // 初始化Service接口
         final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder optionHeaderHolder = new PagedListHolder();
         // 传入当前页
         optionHeaderHolder.setPage( page );
         // 传入当前值对象
         optionHeaderHolder.setObject( optionHeaderVO );
         // 设置页面记录条数
         optionHeaderHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         optionHeaderVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         optionHeaderService.getOptionHeaderVOsByCondition( optionHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( optionHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "optionHeaderHolder", optionHeaderHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listOptionHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listOptionHeader" );
   }

   @Override
   // Code review by Kevin Jin at 2013-07-01
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action及默认状态为启用
      ( ( OptionHeaderVO ) form ).setStatus( OptionHeaderVO.TRUE );
      ( ( OptionHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "managerOptionHeader" );
   }

   @Override
   // Code review by Kevin Jin at 2013-07-01
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化OptionDetailVO
         final OptionDetailVO optionDetailVO = new OptionDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );

            // 获得当前FORM
            final OptionHeaderVO optionHeaderVO = ( OptionHeaderVO ) form;
            optionHeaderVO.setAccountId( getAccountId( request, response ) );
            optionHeaderVO.setCreateBy( getUserId( request, response ) );
            optionHeaderVO.setModifyBy( getUserId( request, response ) );
            optionHeaderService.insertOptionHeader( optionHeaderVO );

            optionDetailVO.setOptionHeaderId( optionHeaderVO.getOptionHeaderId() );
            // 初始化常量持久对象
            constantsInit( "initColumnOption", getAccountId( request, response ) );

            // 返回保存成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, optionHeaderVO, Operate.ADD, optionHeaderVO.getOptionHeaderId(), null );
         }
         else
         {
            // 清空Form条件
            ( ( OptionHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // 跳转到列表界面
            return list_object( mapping, form, request, response );
         }

         return new OptionDetailAction().list_object( mapping, optionDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Code review by Kevin Jin at 2013-07-01
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );

            // 获得主键
            final String optionHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            final OptionHeaderVO optionHeaderVO = optionHeaderService.getOptionHeaderVOByOptionHeaderId( optionHeaderId );

            // 装载界面传值
            optionHeaderVO.update( ( OptionHeaderVO ) form );
            // 获取登录用户
            optionHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            optionHeaderService.updateOptionHeader( optionHeaderVO );

            // 初始化常量持久对象
            constantsInit( "initColumnOption", getAccountId( request, response ) );

            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, optionHeaderVO, Operate.MODIFY, optionHeaderVO.getOptionHeaderId(), null );
         }

         // 清空ActionForm
         ( ( OptionHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new OptionDetailAction().list_object( mapping, new OptionDetailVO(), request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Code review by Kevin Jin at 2013-07-01
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );

         // 获得Action Form
         OptionHeaderVO optionHeaderVO = ( OptionHeaderVO ) form;

         // 存在选中的ID
         if ( optionHeaderVO.getSelectedIds() != null && !optionHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : optionHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               final OptionHeaderVO tempOptionHeaderVO = optionHeaderService.getOptionHeaderVOByOptionHeaderId( selectedId );
               tempOptionHeaderVO.setModifyBy( getUserId( request, response ) );
               tempOptionHeaderVO.setModifyDate( new Date() );
               // 调用删除接口
               optionHeaderService.deleteOptionHeader( tempOptionHeaderVO );
            }

            insertlog( request, optionHeaderVO, Operate.DELETE, null, optionHeaderVO.getSelectedIds() );
            // 初始化常量持久对象
            constantsInit( "initColumnOption", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         optionHeaderVO.setSelectedIds( "" );
         optionHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
