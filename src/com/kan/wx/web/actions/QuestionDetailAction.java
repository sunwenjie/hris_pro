package com.kan.wx.web.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.wx.domain.QuestionDetailVO;
import com.kan.wx.domain.QuestionHeaderVO;
import com.kan.wx.service.inf.QuestionDetailService;
import com.kan.wx.service.inf.QuestionHeaderService;

public class QuestionDetailAction extends BaseAction
{
   public final static String accessAction = "HRO_QUESITION_DETAIL";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
         final QuestionDetailVO questionDetailVO = ( QuestionDetailVO ) form;

         // 如果没有指定排序则默认按 QuestionIndex排序
         if ( questionDetailVO.getSortColumn() == null || questionDetailVO.getSortColumn().isEmpty() )
         {
            questionDetailVO.setSortColumn( "optionIndex" );
            questionDetailVO.setSortOrder( "asc" );
         }

         // 处理SubAction
         dealSubAction( questionDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );
         final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );

         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = questionDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获取QuestionHeaderVO
         final QuestionHeaderVO questionHeaderVO = questionHeaderService.getQuestionHeaderVOByHeaderId( headerId );
         questionHeaderVO.setSubAction( VIEW_OBJECT );
         questionHeaderVO.reset( null, request );
         // 传入request对象
         request.setAttribute( "questionHeaderForm", questionHeaderVO );

         questionDetailVO.setHeaderId( headerId );
         // 此处分页代码
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( questionDetailVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         questionDetailService.getQuestionDetailVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "questionDetailHolder", pagedListHolder );
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listQuestionDetailTable" );
         }

         ( ( QuestionDetailVO ) form ).setSubAction( "" );
         ( ( QuestionDetailVO ) form ).setStatus( QuestionDetailVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listQuestionDetail" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
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
            final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );

            // 获得外键headerId
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得ActionForm
            final QuestionDetailVO questionDetailVO = ( QuestionDetailVO ) form;
            // 获取登录用户
            questionDetailVO.setHeaderId( headerId );
            questionDetailVO.setAccountId( getAccountId( request, response ) );
            questionDetailVO.setCreateBy( getUserId( request, response ) );
            questionDetailVO.setModifyBy( getUserId( request, response ) );

            String optionIndex = questionDetailService.getMaxOptionIndexByHeaderId( headerId );
            if ( KANUtil.filterEmpty( optionIndex ) == null )
            {
               optionIndex = "0";
            }

            //选项值累加
            questionDetailVO.setOptionIndex( String.valueOf( ( Integer.parseInt( optionIndex ) + 1 ) ) );
            questionDetailService.insertQuestionDetail( questionDetailVO );
            insertlog( request, questionDetailVO, Operate.ADD, questionDetailVO.getDetailId(), null );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // 清空Form
         ( ( QuestionDetailVO ) form ).reset();
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

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );
         final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );
         // 主键获取需解码
         final String detailId = KANUtil.decodeString( request.getParameter( "id" ) );
         // 获得QuestionDetailVO对象
         final QuestionDetailVO questionDetailVO = questionDetailService.getQuestionDetailVOByDetailId( detailId );
         // 获得QuestionHeaderVO对象
         final QuestionHeaderVO questionHeaderVO = questionHeaderService.getQuestionHeaderVOByHeaderId( questionDetailVO.getHeaderId() );

         questionDetailVO.reset( null, request );
         questionDetailVO.setSubAction( VIEW_OBJECT );
         // 传入request对象
         request.setAttribute( "questionHeaderForm", questionHeaderVO );
         request.setAttribute( "questionDetailForm", questionDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "managerQuestionDetail" );
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
            // 初始化Service接口
            final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );

            // 主键获取需解码
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // 获取主键对象
            final QuestionDetailVO questionDetailVO = questionDetailService.getQuestionDetailVOByDetailId( detailId );

            ( ( QuestionDetailVO ) form ).setOptionIndex( questionDetailVO.getOptionIndex() );
            // 装载界面传值
            questionDetailVO.update( ( QuestionDetailVO ) form );
            // 获取登录用户
            questionDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            questionDetailService.updateQuestionDetail( questionDetailVO );
            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, questionDetailVO, Operate.MODIFY, questionDetailVO.getDetailId(), null );
         }

         // 清空Form
         ( ( QuestionDetailVO ) form ).reset();
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
         final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );

         // 获得当前form
         QuestionDetailVO questionDetailVO = ( QuestionDetailVO ) form;
         // 存在选中的ID
         if ( questionDetailVO.getSelectedIds() != null && !questionDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : questionDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               final QuestionDetailVO tempQuestionDetailVO = questionDetailService.getQuestionDetailVOByDetailId( selectedId );
               tempQuestionDetailVO.setModifyBy( getUserId( request, response ) );
               tempQuestionDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               questionDetailService.deleteQuestionDetail( tempQuestionDetailVO );
            }

            insertlog( request, questionDetailVO, Operate.DELETE, null, questionDetailVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
         questionDetailVO.setSelectedIds( "" );
         questionDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
