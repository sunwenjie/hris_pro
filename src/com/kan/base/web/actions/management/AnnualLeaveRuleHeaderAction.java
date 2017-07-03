package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.domain.management.AnnualLeaveRuleHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.AnnualLeaveRuleHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class AnnualLeaveRuleHeaderAction extends BaseAction
{
   // Module ASSCESS_ACTION
   public static String ACCESS_ACTION = "HRO_SALARY_ANNUALLEAVE_RULE";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
         // 获得Action Form
         final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = ( AnnualLeaveRuleHeaderVO ) form;

         // 调用删除方法
         if ( annualLeaveRuleHeaderVO.getSubAction() != null && annualLeaveRuleHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( annualLeaveRuleHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder annualLeaveRuleHeaderHolder = new PagedListHolder();
         // 传入当前页
         annualLeaveRuleHeaderHolder.setPage( page );
         // 传入当前值对象
         annualLeaveRuleHeaderHolder.setObject( annualLeaveRuleHeaderVO );
         // 设置页面记录条数
         annualLeaveRuleHeaderHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         annualLeaveRuleHeaderVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOsByCondition( annualLeaveRuleHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( annualLeaveRuleHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "annualLeaveRuleHeaderHolder", annualLeaveRuleHeaderHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table调用，直接传回ShiftHeader JSP
            return mapping.findForward( "listAnnualLeaveRuleHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listAnnualLeaveRuleHeader" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      ( ( AnnualLeaveRuleHeaderVO ) form ).reset( mapping, request );

      // 设置Sub Action
      ( ( AnnualLeaveRuleHeaderVO ) form ).setStatus( BaseVO.TRUE );
      ( ( AnnualLeaveRuleHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面  
      return mapping.findForward( "manageAnnualLeaveRuleHeader" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = new AnnualLeaveRuleDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
            // 获得当前FORM
            final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = ( AnnualLeaveRuleHeaderVO ) form;
            annualLeaveRuleHeaderVO.setCreateBy( getUserId( request, response ) );
            annualLeaveRuleHeaderVO.setModifyBy( getUserId( request, response ) );
            annualLeaveRuleHeaderService.insertAnnualLeaveRuleHeader( annualLeaveRuleHeaderVO );

            // 重新加载缓存
            constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, annualLeaveRuleHeaderVO, Operate.ADD, annualLeaveRuleHeaderVO.getHeaderId(), null );

            annualLeaveRuleDetailVO.setHeaderId( annualLeaveRuleHeaderVO.getHeaderId() );
         }
         else
         {
            // 清空Action Form
            ( ( AnnualLeaveRuleHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new AnnualLeaveRuleDetailAction().list_object( mapping, annualLeaveRuleDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取ShiftHeaderVO对象
            final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( headerId );
            // 装载界面传值
            annualLeaveRuleHeaderVO.update( ( AnnualLeaveRuleHeaderVO ) form );
            // 获取登录用户
            annualLeaveRuleHeaderVO.setModifyBy( getUserId( request, response ) );

            // 调用修改方法
            annualLeaveRuleHeaderService.updateAnnualLeaveRuleHeader( annualLeaveRuleHeaderVO );

            // 重新加载缓存
            // constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         }

         // 清空Action Form
         ( ( AnnualLeaveRuleHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new AnnualLeaveRuleDetailAction().list_object( mapping, new AnnualLeaveRuleDetailVO(), request, response );
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
            final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取ShiftHeaderVO对象
            final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( headerId );
            // 装载界面传值
            annualLeaveRuleHeaderVO.update( ( AnnualLeaveRuleHeaderVO ) form );
            // 获取登录用户
            annualLeaveRuleHeaderVO.setModifyBy( getUserId( request, response ) );

            // 调用修改方法
            annualLeaveRuleHeaderService.updateAnnualLeaveRuleHeader( annualLeaveRuleHeaderVO );

            // 重新加载缓存
            constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, annualLeaveRuleHeaderVO, Operate.MODIFY, annualLeaveRuleHeaderVO.getHeaderId(), null );
         }

         // 清空Action Form
         ( ( AnnualLeaveRuleHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new AnnualLeaveRuleDetailAction().list_object( mapping, new AnnualLeaveRuleDetailVO(), request, response );
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
         final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
         // 获得Action Form
         AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = ( AnnualLeaveRuleHeaderVO ) form;

         // 存在选中的ID
         if ( KANUtil.filterEmpty( annualLeaveRuleHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, annualLeaveRuleHeaderVO, Operate.DELETE, null, annualLeaveRuleHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : annualLeaveRuleHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( selectedId );
               annualLeaveRuleHeaderVO.setHeaderId( selectedId );
               annualLeaveRuleHeaderVO.setAccountId( getAccountId( request, response ) );
               annualLeaveRuleHeaderVO.setModifyBy( getUserId( request, response ) );
               annualLeaveRuleHeaderService.deleteAnnualLeaveRuleHeader( annualLeaveRuleHeaderVO );
            }
         }

         // 清除Selected IDs和子Action
         annualLeaveRuleHeaderVO.setSelectedIds( "" );
         annualLeaveRuleHeaderVO.setSubAction( "" );

         // 重新加载缓存
         constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
