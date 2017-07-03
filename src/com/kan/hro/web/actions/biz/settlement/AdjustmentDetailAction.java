package com.kan.hro.web.actions.biz.settlement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.settlement.AdjustmentDetailService;
import com.kan.hro.service.inf.biz.settlement.AdjustmentHeaderService;

public class AdjustmentDetailAction extends BaseAction
{
	public final static String accessAction = "HRO_SETTLE_ADJUSTMENT_DETAIL";
   /**
    * 账单调整
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 获得当前页
         final String page = getPage( request );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( headerId == null || "".equals( headerId ) )
         {
            headerId = ( ( AdjustmentDetailVO ) form ).getAdjustmentHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

         // 刷新国际化
         adjustmentHeaderVO.reset( null, request );
         // 区分修改添加
         adjustmentHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "adjustmentHeaderForm", adjustmentHeaderVO );
         // 获得Action Form
         final AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) form;
         adjustmentDetailVO.setAdjustmentHeaderId( headerId );
         // 处理SubAction
         dealSubAction( adjustmentDetailVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder adjustmentDetailHolder = new PagedListHolder();
         // 传入当前页
         adjustmentDetailHolder.setPage( page );
         // 传入当前值对象
         adjustmentDetailHolder.setObject( adjustmentDetailVO );
         // 设置页面记录条数
         adjustmentDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         adjustmentDetailService.getAdjustmentDetailVOsByCondition( adjustmentDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( adjustmentDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "adjustmentDetailHolder", adjustmentDetailHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回listAdjustmentDetailTable JSP
            return mapping.findForward( "listAdjustmentDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listAdjustmentDetail" );
   }

   /**
    * 调整确认
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_confirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 获得当前页
         final String page = getPage( request );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( headerId == null || "".equals( headerId ) )
         {
            headerId = ( ( AdjustmentDetailVO ) form ).getAdjustmentHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

         // 刷新国际化
         adjustmentHeaderVO.reset( null, request );
         // 区分修改添加
         adjustmentHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "adjustmentHeaderForm", adjustmentHeaderVO );
         // 获得Action Form
         final AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) form;
         adjustmentDetailVO.setAdjustmentHeaderId( headerId );
         // 处理SubAction
         dealSubAction( adjustmentDetailVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder adjustmentDetailHolder = new PagedListHolder();
         // 传入当前页
         adjustmentDetailHolder.setPage( page );
         // 传入当前值对象
         adjustmentDetailHolder.setObject( adjustmentDetailVO );
         // 设置页面记录条数
         adjustmentDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         adjustmentDetailService.getAdjustmentDetailVOsByCondition( adjustmentDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( adjustmentDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "adjustmentDetailHolder", adjustmentDetailHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回listAdjustmentDetailTable JSP
            return mapping.findForward( "listAdjustmentDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listAdjustmentDetailConfrim" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );
            // 获得当前FORM
            final AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) form;
            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            adjustmentDetailVO.setAdjustmentHeaderId( headerId );
            adjustmentDetailVO.setCreateBy( getUserId( request, response ) );
            adjustmentDetailVO.setModifyBy( getUserId( request, response ) );
            adjustmentDetailVO.setAccountId( getAccountId( request, response ) );
            adjustmentDetailService.insertAdjustmentDetail( adjustmentDetailVO );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // 清空Form
         ( ( AdjustmentDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
         // 初始化Service接口
         final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         // 主键主表ID
         final String detailId = KANUtil.decodeString( request.getParameter( "id" ) );
         // 获得AdjustmentDetailVO对象
         final AdjustmentDetailVO adjustmentDetailVO = adjustmentDetailService.getAdjustmentDetailVOByAdjustmentDetailId( detailId );
         // 获得AdjustmentHeaderVO对象
         final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentDetailVO.getAdjustmentHeaderId() );
         // 国际化传值
         adjustmentDetailVO.reset( null, request );
         // 区分修改添加
         adjustmentDetailVO.setSubAction( VIEW_OBJECT );
         adjustmentDetailVO.setStatus( AdjustmentDetailVO.TRUE );
         // 传入request对象
         request.setAttribute( "adjustmentHeaderForm", adjustmentHeaderVO );
         request.setAttribute( "adjustmentDetailForm", adjustmentDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageAdjustmentDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口AdjustmentDetailService
            final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );
            // 主键获取需解码
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // 获取主键对象
            final AdjustmentDetailVO adjustmentDetailVO = adjustmentDetailService.getAdjustmentDetailVOByAdjustmentDetailId( detailId );
            // 装载界面传值
            adjustmentDetailVO.update( ( AdjustmentDetailVO ) form );
            // 获取登录用户
            adjustmentDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            adjustmentDetailService.updateAdjustmentDetail( adjustmentDetailVO );
            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }

         // 清空Form
         ( ( AdjustmentDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );
         // 获得当前form
         AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) form;
         // 存在选中的ID
         if ( adjustmentDetailVO.getSelectedIds() != null && !adjustmentDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : adjustmentDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               adjustmentDetailVO = adjustmentDetailService.getAdjustmentDetailVOByAdjustmentDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
               adjustmentDetailVO.setModifyBy( getUserId( request, response ) );
               adjustmentDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               adjustmentDetailService.deleteAdjustmentDetail( adjustmentDetailVO );
            }
         }
         // 清除Selected IDs和子Action
         adjustmentDetailVO.setSelectedIds( "" );
         adjustmentDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
