package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxRangeHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class IncomeTaxRangeHeaderAction extends BaseAction
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
         final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
         // 获得Action Form
         final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = ( IncomeTaxRangeHeaderVO ) form;

         // 处理subAction
         dealSubAction( incomeTaxRangeHeaderVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder incomeTaxRangeHeaderHolder = new PagedListHolder();
         // 传入当前页
         incomeTaxRangeHeaderHolder.setPage( page );
         // 传入当前值对象
         incomeTaxRangeHeaderHolder.setObject( incomeTaxRangeHeaderVO );
         // 设置页面记录条数
         incomeTaxRangeHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOsByCondition( incomeTaxRangeHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( incomeTaxRangeHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "incomeTaxRangeHeaderHolder", incomeTaxRangeHeaderHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回IncomeTaxRangeHeader JSP
            return mapping.findForward( "listIncomeTaxRangeHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listIncomeTaxRangeHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( IncomeTaxRangeHeaderVO ) form ).setStatus( IncomeTaxRangeHeaderVO.TRUE );
      ( ( IncomeTaxRangeHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面  
      return mapping.findForward( "manageIncomeTaxRangeHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = new IncomeTaxRangeDetailVO();
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // 获得当前FORM
            final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = ( IncomeTaxRangeHeaderVO ) form;
            // Checkbox处理
            incomeTaxRangeHeaderVO.setIsDefault( ( incomeTaxRangeHeaderVO.getIsDefault() != null && incomeTaxRangeHeaderVO.getIsDefault().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE
                  : BaseVO.FALSE );
            incomeTaxRangeHeaderVO.setCreateBy( getUserId( request, response ) );
            incomeTaxRangeHeaderVO.setModifyBy( getUserId( request, response ) );
            incomeTaxRangeHeaderService.insertIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );
            incomeTaxRangeDetailVO.setHeaderId( ( ( IncomeTaxRangeHeaderVO ) form ).getHeaderId() );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            // 刷新常量
            constants.initIncomeTaxRange();
         }
         else
         {
            // 返回重复标记的标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            // 清空Form
            ( ( IncomeTaxRangeHeaderVO ) form ).reset();

            return list_object( mapping, form, request, response );
         }

         return new IncomeTaxRangeDetailAction().list_object( mapping, incomeTaxRangeDetailVO, request, response );
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
            final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取IncomeTaxRangeHeaderVO对象
            final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( headerId );
            // 装载界面传值
            incomeTaxRangeHeaderVO.update( ( IncomeTaxRangeHeaderVO ) form );
            // 获取登录用户
            incomeTaxRangeHeaderVO.setModifyBy( getUserId( request, response ) );
            // Checkbox处理
            incomeTaxRangeHeaderVO.setIsDefault( ( incomeTaxRangeHeaderVO.getIsDefault() != null && incomeTaxRangeHeaderVO.getIsDefault().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE
                  : BaseVO.FALSE );
            // 调用修改方法
            incomeTaxRangeHeaderService.updateIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            // 刷新常量
            constants.initIncomeTaxRange();
         }
         // 清空Form
         ( ( IncomeTaxRangeHeaderVO ) form ).reset();

         return new IncomeTaxRangeDetailAction().list_object( mapping, new IncomeTaxRangeDetailVO(), request, response );
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
         final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );
         // 获得Action Form
         IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = ( IncomeTaxRangeHeaderVO ) form;
         // 存在选中的ID
         if ( incomeTaxRangeHeaderVO.getSelectedIds() != null && !incomeTaxRangeHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : incomeTaxRangeHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获得要删除的对象
               incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( selectedId );
               // 调用删除接口
               incomeTaxRangeHeaderVO.setHeaderId( selectedId );
               incomeTaxRangeHeaderVO.setAccountId( getAccountId( request, response ) );
               incomeTaxRangeHeaderVO.setModifyBy( getUserId( request, response ) );
               incomeTaxRangeHeaderService.deleteIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );
            }

            // 刷新常量
            constants.initIncomeTaxRange();
         }

         // 清除Selected IDs和子Action
         incomeTaxRangeHeaderVO.setSelectedIds( "" );
         incomeTaxRangeHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
