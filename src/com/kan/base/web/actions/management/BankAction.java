package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.BankVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.BankService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class BankAction extends BaseAction
{

   public static final String accessAction = "HRO_MGT_BANK";

   @Override
   // Reviewed by Kevin Jin at 2013-12-29
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final BankService bankService = ( BankService ) getService( "bankService" );

         // 获得Action Form
         final BankVO bankVO = ( BankVO ) form;

         // 需要设置当前用户AccountId
         bankVO.setAccountId( getAccountId( request, response ) );

         // 调用删除方法
         if ( bankVO.getSubAction() != null && bankVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( bankVO );
         }

         // 如果没有指定排序则默认按BankId排序
         if ( KANUtil.filterEmpty( bankVO.getSortColumn() ) == null )
         {
            bankVO.setSortColumn( "bankId" );
            bankVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder bankHolder = new PagedListHolder();
         // 传入当前页
         bankHolder.setPage( page );
         // 传入当前值对象
         bankHolder.setObject( bankVO );
         // 设置页面记录条数
         bankHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         bankService.getBankVOsByCondition( bankHolder, true );

         if ( bankHolder != null && bankHolder.getHolderSize() > 0 )
         {
            for ( Object bankVOObject : bankHolder.getSource() )
            {
               final BankVO tempBankVO = ( BankVO ) bankVOObject;

               // 系统科目
               if ( tempBankVO.getAccountId().equals( "1" ) )
               {
                  tempBankVO.reset( null, request );
                  tempBankVO.setAccountId( "1" );
               }
               else
               {
                  tempBankVO.reset( null, request );
               }
            }
         }

         // Holder需写入Request对象
         request.setAttribute( "bankHolder", bankHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Bank JSP
            request.setAttribute( "accountId", getAccountId( request, null ) );
            return mapping.findForward( "listBankTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listBank" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( BankVO ) form ).setStatus( BankVO.TRUE );
      ( ( BankVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageBank" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前FORM
         final BankVO bankVO = ( BankVO ) form;

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final BankService bankService = ( BankService ) getService( "bankService" );
            bankVO.setCreateBy( getUserId( request, response ) );
            bankVO.setModifyBy( getUserId( request, response ) );
            bankVO.setAccountId( getAccountId( request, response ) );
            bankService.insertBank( bankVO );

            // 初始化常量持久对象
            constantsInit( "initBank", getAccountId( request, response ) );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, bankVO, Operate.ADD, bankVO.getBankId(), null );
         }

         // 清空Form
         ( ( BankVO ) form ).reset();

         return to_objectModify( mapping, bankVO, request, response );
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
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final BankService bankService = ( BankService ) getService( "bankService" );

         String bankId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( bankId ) != null )
         {
            bankId = Cryptogram.decodeString( URLDecoder.decode( bankId, "UTF-8" ) );
         }
         else
         {
            bankId = ( ( BankVO ) form ).getBankId();
         }
         // 获得BankVO对象
         final BankVO bankVO = bankService.getBankVOByBankId( bankId );
         // 刷新对象，初始化对象列表及国际化
         bankVO.reset( null, request );
         // 如果City Id，则填充Province Id
         if ( bankVO.getCityId() != null && !bankVO.getCityId().trim().equals( "" ) && !bankVO.getCityId().trim().equals( "0" ) )
         {
            bankVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( bankVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            bankVO.setCityIdTemp( bankVO.getCityId() );
         }
         String accountId = "";
         // 系统科目
         if ( bankVO.getAccountId().equals( "1" ) )
         {
            accountId = "1";
         }
         // 区分Add和Update
         bankVO.setSubAction( VIEW_OBJECT );
         bankVO.reset( null, request );
         if ( !accountId.equals( "" ) )
         {
            bankVO.setAccountId( accountId );
         }
         // 将BankVO传入request对象
         request.setAttribute( "bankForm", bankVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageBank" );
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
            final BankService bankService = ( BankService ) getService( "bankService" );
            // 主键获取需解码
            final String bankId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取BankVO对象
            final BankVO bankVO = bankService.getBankVOByBankId( bankId );
            // 装载界面传值
            bankVO.update( ( BankVO ) form );
            // 获取登录用户
            bankVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            bankService.updateBank( bankVO );
            // 初始化常量持久对象
            constantsInit( "initBank", getAccountId( request, response ) );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, bankVO, Operate.MODIFY, bankVO.getBankId(), null );
         }
         // 清空Form
         ( ( BankVO ) form ).reset();
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
         final BankService bankService = ( BankService ) getService( "bankService" );
         // 获得Action Form
         final BankVO bankVO = ( BankVO ) form;
         // 存在选中的ID
         if ( bankVO.getSelectedIds() != null && !bankVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : bankVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               bankVO.setBankId( selectedId );
               bankVO.setAccountId( getAccountId( request, response ) );
               bankVO.setModifyBy( getUserId( request, response ) );
               bankService.deleteBank( bankVO );
            }

            // 初始化常量持久对象
            constantsInit( "initBank", getAccountId( request, response ) );

            insertlog( request, bankVO, Operate.DELETE, null, bankVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
         bankVO.setSelectedIds( "" );
         bankVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
