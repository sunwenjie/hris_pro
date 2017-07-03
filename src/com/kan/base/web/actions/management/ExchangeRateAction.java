package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ExchangeRateService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ExchangeRateAction extends BaseAction
{

   public final static String accessAction = "HRO_MGT_EXCHANGE_RATE";

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
         final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );
         // 获得Action Form
         final ExchangeRateVO exchangeRateVO = ( ExchangeRateVO ) form;
         // 需要设置当前用户AccountId
         exchangeRateVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( exchangeRateVO.getSubAction() != null && exchangeRateVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder exchangeRateHolder = new PagedListHolder();
         // 传入当前页
         exchangeRateHolder.setPage( page );
         // 传入当前值对象
         exchangeRateHolder.setObject( exchangeRateVO );
         // 设置页面记录条数
         exchangeRateHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         exchangeRateService.getExchangeRateVOsByCondition( exchangeRateHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( exchangeRateHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "exchangeRateHolder", exchangeRateHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listExchangeRateTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listExchangeRate" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );
      // 设置Sub Action
      ( ( ExchangeRateVO ) form ).setStatus( ExchangeRateVO.TRUE );
      ( ( ExchangeRateVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageExchangeRate" );
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
            final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );

            // 获得当前FORM
            final ExchangeRateVO exchangeRateVO = ( ExchangeRateVO ) form;
            exchangeRateVO.setCreateBy( getUserId( request, response ) );
            exchangeRateVO.setModifyBy( getUserId( request, response ) );
            exchangeRateVO.setAccountId( getAccountId( request, response ) );
            exchangeRateService.insertExchangeRate( exchangeRateVO );

            // 初始化常量持久对象
            constantsInit( "initExchangeRate", getAccountId( request, response ) );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
            // 清空Action Form
            ( ( ExchangeRateVO ) form ).reset();
         }
         else
         {
            // 清空FORM
            ( ( ExchangeRateVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );
         // 主键获取需解码
         String exchangeRateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( exchangeRateId ) == null )
         {
            exchangeRateId = ( ( ExchangeRateVO ) form ).getExchangeRateId();
         }
         // 获得ExchangeRateVO对象
         ExchangeRateVO exchangeRateVO = exchangeRateService.getExchangeRateVOByExchangeRateId( exchangeRateId );
         exchangeRateVO.reset( null, request );
         // 区分Add和Update
         exchangeRateVO.setSubAction( VIEW_OBJECT );
         // 将ExchangeRateVO传入request对象
         request.setAttribute( "exchangeRateForm", exchangeRateVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageExchangeRate" );
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
            final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );

            // 主键获取需解码
            final String entityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // 获取ExchangeRateVO对象
            final ExchangeRateVO exchangeRateVO = exchangeRateService.getExchangeRateVOByExchangeRateId( entityId );
            // 装载界面传值
            exchangeRateVO.update( ( ExchangeRateVO ) form );
            // 获取登录用户
            exchangeRateVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            exchangeRateService.updateExchangeRate( exchangeRateVO );

            // 初始化常量持久对象
            constantsInit( "initExchangeRate", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Action Form
         ( ( ExchangeRateVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );

      }
      return to_objectModify( mapping, form, request, response );
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
         final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );

         // 获得Action Form
         final ExchangeRateVO exchangeRateVO = ( ExchangeRateVO ) form;
         // 存在选中的ID
         if ( exchangeRateVO.getSelectedIds() != null && !exchangeRateVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : exchangeRateVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               exchangeRateVO.setExchangeRateId( selectedId );
               exchangeRateVO.setModifyBy( getUserId( request, response ) );
               exchangeRateVO.setAccountId( getAccountId( request, response ) );
               exchangeRateService.deleteExchangeRate( exchangeRateVO );
            }

            // 初始化常量持久对象
            constantsInit( "initExchangeRate", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         exchangeRateVO.setSelectedIds( "" );
         exchangeRateVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
