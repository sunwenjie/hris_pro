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
import com.kan.hro.service.inf.biz.performance.BudgetSettingHeaderService;

public class BudgetSettingHeaderAction extends BaseAction
{

   public static final String ASSESS_ACTION = "HRO_PM_BUDGET_SETTING";
   
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final BudgetSettingHeaderVO budgetHeaderVO = ( BudgetSettingHeaderVO ) form;
         // 处理subAction
         dealSubAction( budgetHeaderVO, mapping, form, request, response );
         // 初始化Service接口
         final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder budgetSettingHeaderHolder = new PagedListHolder();
         // 传入当前页
         budgetSettingHeaderHolder.setPage( page );
         // 传入当前值对象
         budgetSettingHeaderHolder.setObject( budgetHeaderVO );
         // 设置页面记录条数
         budgetSettingHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         budgetSettingHeaderService.getBudgetSettingHeaderVOsByCondition( budgetSettingHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( budgetSettingHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "budgetSettingHeaderHolder", budgetSettingHeaderHolder );
         // Ajax调用，直接传值给table jsp页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBudgetSettingHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listBudgetSettingHeader" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );
      // 设置Sub Action
      ( ( BudgetSettingHeaderVO ) form ).setStatus( BudgetSettingHeaderVO.TRUE );
      ( ( BudgetSettingHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      return mapping.findForward( "manageBudgetSettingHeader" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化BudgetSettingDetailVO
         final BudgetSettingDetailVO budgetSettingDetailVO = new BudgetSettingDetailVO();
         budgetSettingDetailVO.reset( null, request );
         budgetSettingDetailVO.setStatus( "1" );
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
            // 获得当前FORM
            final BudgetSettingHeaderVO budgetSettingHeaderVO = ( BudgetSettingHeaderVO ) form;

            budgetSettingHeaderVO.setCreateBy( getUserId( request, response ) );
            budgetSettingHeaderVO.setModifyBy( getUserId( request, response ) );
            budgetSettingHeaderVO.setAccountId( getAccountId( request, response ) );
            budgetSettingHeaderService.insertBudgetSettingHeader( budgetSettingHeaderVO );
            budgetSettingDetailVO.setHeaderId( budgetSettingHeaderVO.getHeaderId() );

            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, budgetSettingHeaderVO, Operate.ADD, budgetSettingHeaderVO.getHeaderId(), null );
         }
         else
         {
            // 清空form
            ( ( BudgetSettingHeaderVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }

         return new BudgetSettingDetailAction().list_object( mapping, budgetSettingDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
            // 获取主键 - 需解码
            final String budgetSettingHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得BudgetSettingHeaderVO对象
            final BudgetSettingHeaderVO budgetSettingHeaderVO = budgetSettingHeaderService.getBudgetSettingHeaderVOByHeaderId( budgetSettingHeaderId );
            // 装载界面传值
            budgetSettingHeaderVO.update( ( BudgetSettingHeaderVO ) form );
            // 获取登录用户
            budgetSettingHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            budgetSettingHeaderService.updateBudgetSettingHeader( budgetSettingHeaderVO );
            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, budgetSettingHeaderVO, Operate.MODIFY, budgetSettingHeaderVO.getHeaderId(), null );
         }

         // 清空Action Form
         ( ( BudgetSettingHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      BudgetSettingDetailVO forwardForm = new BudgetSettingDetailVO();
      forwardForm.reset( null, request );
      return new BudgetSettingDetailAction().list_object( mapping, forwardForm, request, response );
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
         final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
         // 获得Action Form
         final BudgetSettingHeaderVO budgetSettingHeaderVO = ( BudgetSettingHeaderVO ) form;
         // 存在选中的ID
         if ( budgetSettingHeaderVO.getSelectedIds() != null && !budgetSettingHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : budgetSettingHeaderVO.getSelectedIds().split( "," ) )
            {
               // 根据ID获取对应的budgetSettingHeaderVO
               final BudgetSettingHeaderVO budgetSettingHeaderVOForDel = budgetSettingHeaderService.getBudgetSettingHeaderVOByHeaderId( selectedId );
               budgetSettingHeaderVOForDel.setModifyBy( getUserId( request, response ) );
               budgetSettingHeaderVOForDel.setModifyDate( new Date() );
               budgetSettingHeaderService.deleteBudgetSettingHeader( budgetSettingHeaderVOForDel );
            }

            insertlog( request, budgetSettingHeaderVO, Operate.DELETE, null, budgetSettingHeaderVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         budgetSettingHeaderVO.setSelectedIds( "" );
         budgetSettingHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
