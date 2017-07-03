package com.kan.hro.web.actions.biz.performance;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.performance.BudgetSettingDetailVO;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;
import com.kan.hro.service.inf.biz.performance.BudgetSettingDetailService;
import com.kan.hro.service.inf.biz.performance.BudgetSettingHeaderService;

public class BudgetSettingDetailAction extends BaseAction
{

   public static final String ASSESS_ACTION = "HRO_PM_BUDGET_SETTING";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         //获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final BudgetSettingDetailVO budgetSettingDetailVO = ( BudgetSettingDetailVO ) form;
         // 调用删除方法
         if ( budgetSettingDetailVO.getSubAction() != null && budgetSettingDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化Service接口
         final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
         final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );

         // 获得当下主键  
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = budgetSettingDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获取BudgetSettingDetailVO
         final BudgetSettingHeaderVO budgetSettingHeaderVO = budgetSettingHeaderService.getBudgetSettingHeaderVOByHeaderId( headerId );
         budgetSettingHeaderVO.setSubAction( VIEW_OBJECT );
         budgetSettingHeaderVO.reset( null, request );
         request.setAttribute( "budgetSettingHeaderForm", budgetSettingHeaderVO );
         budgetSettingDetailVO.setHeaderId( headerId );

         //此处分页代码
         final PagedListHolder budgetSettingDetailHolder = new PagedListHolder();
         // 传入当前页
         budgetSettingDetailHolder.setPage( page );
         // 传入当前值对象
         budgetSettingDetailHolder.setObject( budgetSettingDetailVO );
         // 设置页面记录条数
         budgetSettingDetailHolder.setPageSize( listPageSize_large );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         budgetSettingDetailService.getBudgetSettingDetailVOsByCondition( budgetSettingDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( budgetSettingDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "budgetSettingDetailHolder", budgetSettingDetailHolder );

         // BudgetSettingDetail写入Request对象
         request.setAttribute( "budgetSettingDetailForm", budgetSettingDetailVO );

         // 如果是AJAX调用，则直接传值给table JSP页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBudgetSettingDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listBudgetSettingDetail" );
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
            // 初始化Service 接口
            final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
            // 获得headerId
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得当前form
            final BudgetSettingDetailVO budgetSettingDetailVO = ( BudgetSettingDetailVO ) form;
            // 初始化BudgetSettingDetailVO对象
            budgetSettingDetailVO.setHeaderId( headerId );
            budgetSettingDetailVO.setCreateBy( getUserId( request, response ) );
            budgetSettingDetailVO.setModifyBy( getUserId( request, response ) );
            budgetSettingDetailVO.setAccountId( getAccountId( request, response ) );
            budgetSettingDetailService.insertBudgetSettingDetail( budgetSettingDetailVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, budgetSettingDetailVO, Operate.ADD, budgetSettingDetailVO.getDetailId(), null );
         }

         // 清空Action Form
         ( ( BudgetSettingDetailVO ) form ).setSubAction( "" );
         ( ( BudgetSettingDetailVO ) form ).reset();
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

   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
         final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
         // 获取主键 - 需解码
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // 获取BudgetSettingDetailVO对象
         final BudgetSettingDetailVO budgetSettingDetailVO = budgetSettingDetailService.getBudgetSettingDetailVOByDetailId( detailId );
         // 获取BudgetSettingHeaderVO对象
         final BudgetSettingHeaderVO budgetSettingHeaderVO = budgetSettingHeaderService.getBudgetSettingHeaderVOByHeaderId( budgetSettingDetailVO.getHeaderId() );
         // 国际化传值
         budgetSettingDetailVO.reset( null, request );
         // 设置SubAction
         budgetSettingDetailVO.setSubAction( VIEW_OBJECT );
         // 写入Request
         request.setAttribute( "budgetSettingHeaderForm", budgetSettingHeaderVO );
         request.setAttribute( "budgetSettingDetailForm", budgetSettingDetailVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // AJAX调用跳转FORM页面
      return mapping.findForward( "manageBudgetSettingDetail" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
            // 获取主键 - 需解码
            final String detailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "detailId" ), "UTF-8" ) );
            // 获得BudgetSettingDetailVO对象
            final BudgetSettingDetailVO budgetSettingDetailVO = budgetSettingDetailService.getBudgetSettingDetailVOByDetailId( detailId );
            // 装载界面传值
            budgetSettingDetailVO.update( ( BudgetSettingDetailVO ) form );
            // 获取登录用户
            budgetSettingDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            budgetSettingDetailService.updateBudgetSettingDetail( budgetSettingDetailVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
            insertlog( request, budgetSettingDetailVO, Operate.MODIFY, budgetSettingDetailVO.getDetailId(), null );
         }

         // 清空Form
         ( ( BudgetSettingDetailVO ) form ).reset();
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
         final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
         // 获得当前form
         BudgetSettingDetailVO budgetSettingDetailVO = ( BudgetSettingDetailVO ) form;
         // 存在选中的ID
         if ( budgetSettingDetailVO.getSelectedIds() != null && !budgetSettingDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : budgetSettingDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               final BudgetSettingDetailVO tempBudgetSettingDetailVO = budgetSettingDetailService.getBudgetSettingDetailVOByDetailId( selectedId );
               tempBudgetSettingDetailVO.setModifyBy( getUserId( request, response ) );
               tempBudgetSettingDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               budgetSettingDetailService.deleteBudgetSettingDetail( tempBudgetSettingDetailVO );
            }

            insertlog( request, budgetSettingDetailVO, Operate.DELETE, null, budgetSettingDetailVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         budgetSettingDetailVO.setSelectedIds( "" );
         budgetSettingDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
