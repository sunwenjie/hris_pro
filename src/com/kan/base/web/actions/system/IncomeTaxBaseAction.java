package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxBaseService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class IncomeTaxBaseAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
         // 获得Action Form
         final IncomeTaxBaseVO incomeTaxBaseVO = ( IncomeTaxBaseVO ) form;

         // 处理subAction
         dealSubAction( incomeTaxBaseVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder incomeTaxBaseHolder = new PagedListHolder();
         // 传入当前页
         incomeTaxBaseHolder.setPage( page );
         // 传入当前值对象
         incomeTaxBaseHolder.setObject( incomeTaxBaseVO );
         // 设置页面记录条数
         incomeTaxBaseHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         incomeTaxBaseService.getIncomeTaxBaseVOsByCondition( incomeTaxBaseHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( incomeTaxBaseHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "incomeTaxBaseHolder", incomeTaxBaseHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回IncomeTaxBase JSP
            return mapping.findForward( "listIncomeTaxBaseTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listIncomeTaxBase" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( IncomeTaxBaseVO ) form ).setStatus( IncomeTaxBaseVO.TRUE );
      ( ( IncomeTaxBaseVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面  
      return mapping.findForward( "manageIncomeTaxBase" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // 获得当前FORM
            final IncomeTaxBaseVO incomeTaxBaseVO = ( IncomeTaxBaseVO ) form;
            // Checkbox处理
            incomeTaxBaseVO.setIsDefault( ( incomeTaxBaseVO.getIsDefault() != null && incomeTaxBaseVO.getIsDefault().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE : BaseVO.FALSE );
            incomeTaxBaseVO.setCreateBy( getUserId( request, response ) );
            incomeTaxBaseVO.setModifyBy( getUserId( request, response ) );
            incomeTaxBaseService.insertIncomeTaxBase( incomeTaxBaseVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            // 刷新常量
            constants.initIncomeTaxBase();
         }
         else
         {
            // 返回重复提交标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // 清空Form
            ( ( IncomeTaxBaseVO ) form ).reset();

            return list_object( mapping, form, request, response );
         }

         return to_objectModify( mapping, form, request, response );
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
      try
      {
         // 添加需设定一个记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );

         // 主键获取
         String baseId = request.getParameter( "id" );
         if ( baseId == null || baseId.trim().isEmpty() )
         {
            baseId = ( ( IncomeTaxBaseVO ) form ).getBaseId();
         }
         else
         {
            baseId = Cryptogram.decodeString( URLDecoder.decode( baseId, "UTF-8" ) );
         }

         // 获得IncomeTaxBaseVO对象
         final IncomeTaxBaseVO incomeTaxBaseVO = incomeTaxBaseService.getIncomeTaxBaseVOByBaseId( baseId );
         // 刷新对象，初始化对象列表及国际化
         incomeTaxBaseVO.reset( null, request );
         // 区分修改添加
         incomeTaxBaseVO.setSubAction( VIEW_OBJECT );
         // Checkbox处理
         incomeTaxBaseVO.setIsDefault( ( incomeTaxBaseVO.getIsDefault() != null && incomeTaxBaseVO.getIsDefault().equalsIgnoreCase( BaseVO.TRUE ) ) ? "on" : "" );
         // 写入request对象
         request.setAttribute( "incomeTaxBaseForm", incomeTaxBaseVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageIncomeTaxBase" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // 主键获取需解码
            final String baseId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取IncomeTaxBaseVO对象
            final IncomeTaxBaseVO incomeTaxBaseVO = incomeTaxBaseService.getIncomeTaxBaseVOByBaseId( baseId );
            // 装载界面传值
            incomeTaxBaseVO.update( ( IncomeTaxBaseVO ) form );
            // 获取登录用户
            incomeTaxBaseVO.setModifyBy( getUserId( request, response ) );
            // Checkbox处理
            incomeTaxBaseVO.setIsDefault( ( incomeTaxBaseVO.getIsDefault() != null && incomeTaxBaseVO.getIsDefault().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE : BaseVO.FALSE );
            // 调用修改方法
            incomeTaxBaseService.updateIncomeTaxBase( incomeTaxBaseVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            // 刷新常量
            constants.initIncomeTaxBase();
         }
         // 清空Form
         ( ( IncomeTaxBaseVO ) form ).reset();

         return to_objectModify( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // 获得Action Form
         IncomeTaxBaseVO incomeTaxBaseVO = ( IncomeTaxBaseVO ) form;
         // 存在选中的ID
         if ( incomeTaxBaseVO.getSelectedIds() != null && !incomeTaxBaseVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : incomeTaxBaseVO.getSelectedIds().split( "," ) )
            {
               // 获得要删除的对象
               incomeTaxBaseVO = incomeTaxBaseService.getIncomeTaxBaseVOByBaseId( selectedId );
               // 调用删除接口
               incomeTaxBaseVO.setBaseId( selectedId );
               incomeTaxBaseVO.setAccountId( getAccountId( request, response ) );
               incomeTaxBaseVO.setModifyBy( getUserId( request, response ) );
               incomeTaxBaseService.deleteIncomeTaxBase( incomeTaxBaseVO );
            }

            // 刷新常量
            constants.initIncomeTaxBase();
         }

         // 清除Selected IDs和子Action
         incomeTaxBaseVO.setSelectedIds( "" );
         incomeTaxBaseVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
