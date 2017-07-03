package com.kan.wx.web.actions;

import java.net.URLDecoder;
import java.util.Date;
import java.util.UUID;

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
import com.kan.base.util.TokenThread;
import com.kan.base.util.WXUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.wx.domain.QuestionDetailVO;
import com.kan.wx.domain.QuestionHeaderVO;
import com.kan.wx.service.inf.QuestionHeaderService;

public class QuestionHeaderAction extends BaseAction
{
   public final static String accessAction = "HRO_QUESITION_HEADER";

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
         final QuestionHeaderVO questionHeaderVO = ( QuestionHeaderVO ) form;
         // 处理SubAction
         dealSubAction( questionHeaderVO, mapping, form, request, response );
         // 初始化Service接口
         final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( questionHeaderVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         questionHeaderService.getQuestionHeaderVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "questionHeaderHolder", pagedListHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listQuestionHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listQuestionHeader" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action及默认状态为启用
      ( ( QuestionHeaderVO ) form ).setStatus( QuestionHeaderVO.TRUE );
      ( ( QuestionHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "managerQuestionHeader" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化QuestionDetailVO
         final QuestionDetailVO questionDetailVO = new QuestionDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );

            // 获得当前FORM
            final QuestionHeaderVO questionHeaderVO = ( QuestionHeaderVO ) form;
            questionHeaderVO.setAccountId( getAccountId( request, response ) );
            questionHeaderVO.setCreateBy( getUserId( request, response ) );
            questionHeaderVO.setModifyBy( getUserId( request, response ) );
            questionHeaderService.insertQuestionHeader( questionHeaderVO );
            questionDetailVO.setHeaderId( questionHeaderVO.getHeaderId() );
            // 返回保存成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            insertlog( request, questionHeaderVO, Operate.ADD, questionHeaderVO.getHeaderId(), null );
         }
         else
         {
            // 清空Form条件
            ( ( QuestionHeaderVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            // 跳转到列表界面
            return list_object( mapping, form, request, response );
         }

         return new QuestionDetailAction().list_object( mapping, questionDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
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
            final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );
            // 获得主键
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            final QuestionHeaderVO questionHeaderVO = questionHeaderService.getQuestionHeaderVOByHeaderId( headerId );
            // 装载界面传值
            questionHeaderVO.update( ( QuestionHeaderVO ) form );
            // 获取登录用户
            questionHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            questionHeaderService.updateQuestionHeader( questionHeaderVO );
            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, questionHeaderVO, Operate.MODIFY, questionHeaderVO.getHeaderId(), null );
         }

         // 清空ActionForm
         ( ( QuestionHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new QuestionDetailAction().list_object( mapping, new QuestionDetailVO(), request, response );
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
         final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );

         // 获得Action Form
         QuestionHeaderVO questionHeaderVO = ( QuestionHeaderVO ) form;

         // 存在选中的ID
         if ( questionHeaderVO.getSelectedIds() != null && !questionHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : questionHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               final QuestionHeaderVO tempQuestionHeaderVO = questionHeaderService.getQuestionHeaderVOByHeaderId( selectedId );
               tempQuestionHeaderVO.setModifyBy( getUserId( request, response ) );
               tempQuestionHeaderVO.setModifyDate( new Date() );
               // 调用删除接口
               questionHeaderService.deleteQuestionHeader( tempQuestionHeaderVO );
            }

            insertlog( request, questionHeaderVO, Operate.DELETE, null, questionHeaderVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         questionHeaderVO.setSelectedIds( "" );
         questionHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward wxtest( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
     long time = System.currentTimeMillis()/1000;    
     String randomStr = UUID.randomUUID().toString();  
     //特别注意的是调用微信js，url必须是当前页面(转发的不行)  
     String jsApiTicket =WXUtil.JSApiTIcket();
     String str = "jsapi_ticket="+jsApiTicket+"&noncestr="+randomStr+"&timestamp="+time+"&url=https://hris.luoxxy.com/questionHeaderAction.do?proc=wxtest";    
     System.out.println(str);
     String signature = WXUtil.sha1Encrypt(str);    
     String accerssToken =TokenThread.accessToken.getToken();  
     request.setAttribute("time", time);  
     request.setAttribute("randomStr", randomStr);  
     request.setAttribute("signature", signature);  
     request.setAttribute("accessToken", accerssToken);  
     request.setAttribute("jsapi_ticket", jsApiTicket);  
     return mapping.findForward( "wxtest" );
   }
}
