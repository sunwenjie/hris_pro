package com.kan.base.web.actions.define;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.OptionDetailVO;
import com.kan.base.domain.define.OptionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.OptionDetailService;
import com.kan.base.service.inf.define.OptionHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class OptionDetailAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_OPTION";

   @Override
   // Code reviewed by Kevin at 2013-07-02
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final OptionDetailVO optionDetailVO = ( OptionDetailVO ) form;

         // 如果没有指定排序则默认按 optionIndex排序
         if ( optionDetailVO.getSortColumn() == null || optionDetailVO.getSortColumn().isEmpty() )
         {
            optionDetailVO.setSortColumn( "optionIndex" );
            optionDetailVO.setSortOrder( "asc" );
         }

         // 处理SubAction
         dealSubAction( optionDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );
         final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );

         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = optionDetailVO.getOptionHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获取OptionHeaderVO
         final OptionHeaderVO optionHeaderVO = optionHeaderService.getOptionHeaderVOByOptionHeaderId( headerId );
         optionHeaderVO.setSubAction( VIEW_OBJECT );
         optionHeaderVO.reset( null, request );
         // 传入request对象
         request.setAttribute( "optionHeaderForm", optionHeaderVO );

         // 根据OptionHeaderId查找并得到OptionDetailVO集合
         optionDetailVO.setOptionHeaderId( headerId );

         // 此处分页代码
         final PagedListHolder optionDetailHolder = new PagedListHolder();
         // 传入当前页
         optionDetailHolder.setPage( page );
         // 传入当前值对象
         optionDetailHolder.setObject( optionDetailVO );
         // 设置页面记录条数
         optionDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         optionDetailService.getOptionDetailVOsByCondition( optionDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( optionDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "optionDetailHolder", optionDetailHolder );
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listOptionDetailTable" );
         }

         ( ( OptionDetailVO ) form ).setSubAction( "" );
         ( ( OptionDetailVO ) form ).setStatus( OptionDetailVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listOptionDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No use
      return null;
   }

   @Override
   // Code reviewed by Kevin at 2013-07-01
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service 接口
            final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );

            // 获得外键optionHeaderId
            final String optionHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得ActionForm
            final OptionDetailVO optionDetailVO = ( OptionDetailVO ) form;
            // 获取登录用户
            optionDetailVO.setOptionHeaderId( optionHeaderId );
            optionDetailVO.setAccountId( getAccountId( request, response ) );
            optionDetailVO.setCreateBy( getUserId( request, response ) );
            optionDetailVO.setModifyBy( getUserId( request, response ) );

            String optionId = optionDetailService.getMaxOptionId( optionHeaderId );

            if ( KANUtil.filterEmpty( optionId ) == null )
            {
               optionId = "0";
            }

            //选项值累加
            optionDetailVO.setOptionId( String.valueOf( ( Integer.parseInt( optionId ) + 1 ) ) );
            optionDetailVO.setOptionValue( optionDetailVO.getOptionId() );
            optionDetailService.insertOptionDetail( optionDetailVO );
            insertlog( request, optionDetailVO, Operate.ADD, optionDetailVO.getOptionDetailId(), null );
            
            // 初始化常量持久对象
            constantsInit( "initColumnOption", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

          
         }

         // 清空Form
         ( ( OptionDetailVO ) form ).reset();
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
      // No use
      return null;
   }

   // Code reviewed by Kevin at 2013-07-01
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );
         final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );
         // 主键获取需解码
         final String optionDetailId = KANUtil.decodeString( request.getParameter( "optionDetailId" ) );
         // 获得OptionDetailVO对象
         final OptionDetailVO optionDetailVO = optionDetailService.getOptionDetailVOByOptionDetailId( optionDetailId );
         // 获得OptionHeaderVO对象
         final OptionHeaderVO optionHeaderVO = optionHeaderService.getOptionHeaderVOByOptionHeaderId( optionDetailVO.getOptionHeaderId() );

         optionDetailVO.reset( null, request );
         optionDetailVO.setSubAction( VIEW_OBJECT );
         // 传入request对象
         request.setAttribute( "optionHeaderForm", optionHeaderVO );
         request.setAttribute( "optionDetailForm", optionDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageOptionDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Code reviewed by Kevin at 2013-07-01  修改
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );

            // 主键获取需解码
            final String optionDetailId = KANUtil.decodeString( request.getParameter( "optionDetailId" ) );
            // 获取主键对象
            final OptionDetailVO optionDetailVO = optionDetailService.getOptionDetailVOByOptionDetailId( optionDetailId );

            ( ( OptionDetailVO ) form ).setOptionId( optionDetailVO.getOptionId() );
            ( ( OptionDetailVO ) form ).setOptionValue( optionDetailVO.getOptionValue() );
            // 装载界面传值
            optionDetailVO.update( ( OptionDetailVO ) form );
            // 获取登录用户
            optionDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            optionDetailService.updateOptionDetail( optionDetailVO );

            // 初始化常量持久对象
            constantsInit( "initColumnOption", getAccountId( request, response ) );

            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, optionDetailVO, Operate.MODIFY, optionDetailVO.getOptionDetailId(), null );
         }

         // 清空Form
         ( ( OptionDetailVO ) form ).reset();
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
   // Code review by Kevin Jin at 2013-07-02
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );

         // 获得当前form
         OptionDetailVO optionDetailVO = ( OptionDetailVO ) form;
         // 存在选中的ID
         if ( optionDetailVO.getSelectedIds() != null && !optionDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : optionDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               final OptionDetailVO tempOptionDetailVO = optionDetailService.getOptionDetailVOByOptionDetailId( selectedId );
               tempOptionDetailVO.setModifyBy( getUserId( request, response ) );
               tempOptionDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               optionDetailService.deleteOptionDetail( tempOptionDetailVO );
            }

            insertlog( request, optionDetailVO, Operate.DELETE, null, optionDetailVO.getSelectedIds() );
            // 初始化常量持久对象
            constantsInit( "initColumnOption", getAccountId( request, response ) );
         }
         // 清除Selected IDs和子Action
         optionDetailVO.setSelectedIds( "" );
         optionDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
