package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.BankTemplateDetailVO;
import com.kan.base.domain.define.BankTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.BankTemplateDetailService;
import com.kan.base.service.inf.define.BankTemplateHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class BankTemplateDetailAction extends BaseAction
{
   public static final String accessAction = "HRO_SALARY_TEMPLATE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );

         // 获得是否Ajax调用
         final String ajax = getAjax( request );

         // 获得Action Form
         final BankTemplateDetailVO bankTemplateDetailVO = ( BankTemplateDetailVO ) form;

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 处理SubAction
         dealSubAction( bankTemplateDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );
         final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = bankTemplateDetailVO.getTemplateHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final BankTemplateHeaderVO bankTemplateHeaderVO = bankTemplateHeaderService.getBankTemplateHeaderVOByTemplateHeaderId( headerId );

         // 刷新国际化
         bankTemplateHeaderVO.reset( null, request );
         // 设置SubAction
         bankTemplateHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "bankTemplateHeaderForm", bankTemplateHeaderVO );

         // 设置templateHeaderId
         bankTemplateDetailVO.setTemplateHeaderId( headerId );

         // 如果没有指定排序则默认按 列表字段顺序排序
         if ( bankTemplateDetailVO.getSortColumn() == null || bankTemplateDetailVO.getSortColumn().isEmpty() )
         {
            bankTemplateDetailVO.setSortColumn( "columnIndex" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder bankTemplateDetailHolder = new PagedListHolder();
         // 传入当前页
         bankTemplateDetailHolder.setPage( page );
         // 传入当前值对象
         bankTemplateDetailHolder.setObject( bankTemplateDetailVO );
         // 设置页面记录条数
         bankTemplateDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         bankTemplateDetailService.getBankTemplateDetailVOsByCondition( bankTemplateDetailHolder, true );

         // 设置BankTemplateDetailForm初始值
         bankTemplateDetailVO.setColumnIndex( "0" );
         bankTemplateDetailVO.setFontSize( "13" );
         bankTemplateDetailVO.setAlign( BankTemplateDetailVO.TRUE );
         bankTemplateDetailVO.setStatus( BankTemplateDetailVO.TRUE );

         // 刷新Holder，国际化传值
         refreshHolder( bankTemplateDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "bankTemplateDetailHolder", bankTemplateDetailHolder );

         // Ajax Table调用，直接传回Detail JSP
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBankTemplateDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listBankTemplateDetail" );
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
            // 初始化Service 接口
            final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

            // 获得templateHeaderId
            final String templateHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateHeaderId" ), "UTF-8" ) );

            // 获得当前Form
            final BankTemplateDetailVO bankTemplateDetailVO = ( BankTemplateDetailVO ) form;

            // 初始化BankTemplateDetailVO对象
            bankTemplateDetailVO.setTemplateHeaderId( templateHeaderId );
            bankTemplateDetailVO.setCreateBy( getUserId( request, response ) );
            bankTemplateDetailVO.setModifyBy( getUserId( request, response ) );
            bankTemplateDetailVO.setAccountId( getAccountId( request, response ) );

            // 添加BankTemplateDetailVO
            bankTemplateDetailService.insertBankTemplateDetail( bankTemplateDetailVO );

            // 重新加载常量中的BankTemplateHeader
            constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, bankTemplateDetailVO, Operate.ADD, bankTemplateDetailVO.getTemplateDetailId(), null );
         }

         // 清空form
         ( ( BankTemplateDetailVO ) form ).reset();
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
         // 获取templateDetailId
         final String templateDetailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 初始化Service接口
         final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );
         final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

         // 获取BankTemplateDetailVO
         final BankTemplateDetailVO bankTemplateDetailVO = bankTemplateDetailService.getBankTemplateDetailVOByTemplateDetailId( templateDetailId );

         // 获取BankTemplateHeaderVO
         final BankTemplateHeaderVO bankTemplateHeaderVO = bankTemplateHeaderService.getBankTemplateHeaderVOByTemplateHeaderId( bankTemplateDetailVO.getTemplateHeaderId() );

         // 国际化传值
         bankTemplateDetailVO.reset( null, request );
         // 设置SubAction
         bankTemplateDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "bankTemplateHeaderForm", bankTemplateHeaderVO );
         request.setAttribute( "bankTemplateDetailForm", bankTemplateDetailVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax Form调用，直接传回Form JSP
      return mapping.findForward( "manageBankTemplateDetailForm" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 主键获取 - 需解码
            final String templateDetailId = KANUtil.decodeString( request.getParameter( "templateDetailId" ) );

            // 初始化 Service接口
            final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

            // 获取BankTemplateDetailVO
            final BankTemplateDetailVO bankTemplateDetailVO = bankTemplateDetailService.getBankTemplateDetailVOByTemplateDetailId( templateDetailId );

            // 装载界面传值
            bankTemplateDetailVO.update( ( BankTemplateDetailVO ) form );

            // 修改BankTemplateDetailVO
            bankTemplateDetailVO.setModifyBy( getUserId( request, response ) );
            bankTemplateDetailService.updateBankTemplateDetail( bankTemplateDetailVO );

            // 重新加载常量中的BankTemplateHeader
            constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, bankTemplateDetailVO, Operate.MODIFY, bankTemplateDetailVO.getTemplateDetailId(), null );
         }

         // 清空Form
         ( ( BankTemplateDetailVO ) form ).reset();
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
         final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

         // 获得Action Form
         BankTemplateDetailVO bankTemplateDetailVO = ( BankTemplateDetailVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( bankTemplateDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, bankTemplateDetailVO, Operate.DELETE, null, bankTemplateDetailVO.getSelectedIds() );
            // 分割
            for ( String selectedId : bankTemplateDetailVO.getSelectedIds().split( "," ) )
            {
               // 获得删除对象
               bankTemplateDetailVO = bankTemplateDetailService.getBankTemplateDetailVOByTemplateDetailId( selectedId );
               bankTemplateDetailVO.setModifyBy( getUserId( request, response ) );
               bankTemplateDetailVO.setModifyDate( new Date() );
               bankTemplateDetailService.deleteBankTemplateDetail( bankTemplateDetailVO );
            }
         }

         // 重新加载常量中的BankTemplateHeader
         constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         ( ( BankTemplateDetailVO ) form ).setSelectedIds( "" );
         ( ( BankTemplateDetailVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
