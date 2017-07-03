package com.kan.base.web.actions.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxRangeDetailService;
import com.kan.base.service.inf.system.IncomeTaxRangeHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class IncomeTaxRangeDetailAction extends BaseAction
{

   @Override
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
         final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = ( IncomeTaxRangeDetailVO ) form;

         // 处理subAction
         dealSubAction( incomeTaxRangeDetailVO, mapping, form, request, response );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( headerId == null || headerId.trim().isEmpty() )
         {
            headerId = incomeTaxRangeDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 初始化Service接口
         final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
         final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );

         // 获得主表对象
         final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( headerId );

         incomeTaxRangeHeaderVO.reset( null, request );
         incomeTaxRangeHeaderVO.setSubAction( VIEW_OBJECT );
         incomeTaxRangeHeaderVO.setIsDefault( ( incomeTaxRangeHeaderVO.getIsDefault() != null && incomeTaxRangeHeaderVO.getIsDefault().equalsIgnoreCase( "1" ) ) ? "on" : "" );

         // 写入request对象
         request.setAttribute( "incomeTaxRangeHeaderForm", incomeTaxRangeHeaderVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder incomeTaxRangeDetailHolder = new PagedListHolder();
         // 传入当前页
         incomeTaxRangeDetailHolder.setPage( page );
         // 传入主表ID
         incomeTaxRangeDetailVO.setHeaderId( headerId );
         // 传入当前值对象
         incomeTaxRangeDetailHolder.setObject( incomeTaxRangeDetailVO );
         // 设置页面记录条数
         incomeTaxRangeDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         incomeTaxRangeDetailService.getIncomeTaxRangeDetailVOsByCondition( incomeTaxRangeDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( incomeTaxRangeDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "incomeTaxRangeDetailHolder", incomeTaxRangeDetailHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回SocialBenetifDetail JSP
            return mapping.findForward( "listIncomeTaxRangeDetailTable" );
         }
         ( ( IncomeTaxRangeDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listIncomeTaxRangeDetail" );
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            // 获得当前FORM
            final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = ( IncomeTaxRangeDetailVO ) form;
            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            incomeTaxRangeDetailVO.setHeaderId( headerId );
            incomeTaxRangeDetailVO.setCreateBy( getUserId( request, response ) );
            incomeTaxRangeDetailVO.setModifyBy( getUserId( request, response ) );
            incomeTaxRangeDetailVO.setAccountId( getAccountId( request, response ) );
            incomeTaxRangeDetailService.insertIncomeTaxRangeDetail( incomeTaxRangeDetailVO );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            // 刷新常量
            constants.initIncomeTaxRange();
         }

         // 清空Form
         ( ( IncomeTaxRangeDetailVO ) form ).reset();
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
      // No Use
      return null;
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化HeaderService接口
         final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );
         final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
         // 主键主表ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // 获得IncomeTaxRangeDetailVO对象
         final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = incomeTaxRangeDetailService.getIncomeTaxRangeDetailVOByDetailId( detailId );
         // 获得IncomeTaxRangeHeaderVO对象
         final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( incomeTaxRangeDetailVO.getHeaderId() );
         // 国际化传值
         incomeTaxRangeDetailVO.reset( null, request );
         // 区分修改添加
         incomeTaxRangeDetailVO.setSubAction( BaseAction.VIEW_OBJECT );
         incomeTaxRangeDetailVO.setStatus( IncomeTaxRangeHeaderVO.TRUE );
         // 传入request对象
         request.setAttribute( "incomeTaxRangeHeaderForm", incomeTaxRangeHeaderVO );
         request.setAttribute( "incomeTaxRangeDetailForm", incomeTaxRangeDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageIncomeTaxRangeDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
            final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            // 从表ID
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // 获取IncomeTaxRangeDetailVO对象
            final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = incomeTaxRangeDetailService.getIncomeTaxRangeDetailVOByDetailId( detailId );
            // 装载界面传值
            incomeTaxRangeDetailVO.update( ( IncomeTaxRangeDetailVO ) form );
            // 获取登录用户
            incomeTaxRangeDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            incomeTaxRangeDetailService.updateIncomeTaxRangeDetail( incomeTaxRangeDetailVO );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            // 刷新常量
            constants.initIncomeTaxRange();
         }
         // 清空Form
         ( ( IncomeTaxRangeDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
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
         final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );
         // 获得Action Form
         IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = ( IncomeTaxRangeDetailVO ) form;
         // 存在选中的ID
         if ( incomeTaxRangeDetailVO.getSelectedIds() != null && !incomeTaxRangeDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : incomeTaxRangeDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               incomeTaxRangeDetailVO = incomeTaxRangeDetailService.getIncomeTaxRangeDetailVOByDetailId( selectedId );
               // 调用删除接口
               incomeTaxRangeDetailVO.setDetailId( selectedId );
               incomeTaxRangeDetailVO.setAccountId( getAccountId( request, response ) );
               incomeTaxRangeDetailVO.setModifyBy( getUserId( request, response ) );
               incomeTaxRangeDetailService.deleteIncomeTaxRangeDetail( incomeTaxRangeDetailVO );
            }

            // 刷新常量
            constants.initIncomeTaxRange();
         }

         // 清除Selected IDs和子Action
         incomeTaxRangeDetailVO.setSelectedIds( "" );
         incomeTaxRangeDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
