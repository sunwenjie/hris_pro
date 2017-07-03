package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.domain.management.SickLeaveSalaryHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SickLeaveSalaryHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SickLeaveSalaryHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_SALARY_SICKLEAVE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
         // 获得Action Form
         final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = ( SickLeaveSalaryHeaderVO ) form;
         // 需要设置当前用户AccountId
         sickLeaveSalaryHeaderVO.setAccountId( getAccountId( request, response ) );

         // 调用删除方法
         if ( sickLeaveSalaryHeaderVO.getSubAction() != null && sickLeaveSalaryHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( sickLeaveSalaryHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sickLeaveSalaryHeaderHolder = new PagedListHolder();
         // 传入当前页
         sickLeaveSalaryHeaderHolder.setPage( page );
         // 传入当前值对象
         sickLeaveSalaryHeaderHolder.setObject( sickLeaveSalaryHeaderVO );
         // 设置页面记录条数
         sickLeaveSalaryHeaderHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         sickLeaveSalaryHeaderVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOsByCondition( sickLeaveSalaryHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( sickLeaveSalaryHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "sickLeaveSalaryHeaderHolder", sickLeaveSalaryHeaderHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table调用，直接传回ShiftHeader JSP
            return mapping.findForward( "listSickLeaveSalaryHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listSickLeaveSalaryHeader" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      ( ( SickLeaveSalaryHeaderVO ) form ).reset( mapping, request );

      // 设置Sub Action
      ( ( SickLeaveSalaryHeaderVO ) form ).setStatus( BaseVO.TRUE );
      ( ( SickLeaveSalaryHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面  
      return mapping.findForward( "manageSickLeaveSalayHeader" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = new SickLeaveSalaryDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
            // 获得当前FORM
            final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = ( SickLeaveSalaryHeaderVO ) form;

            sickLeaveSalaryHeaderVO.setCreateBy( getUserId( request, response ) );
            sickLeaveSalaryHeaderVO.setModifyBy( getUserId( request, response ) );
            sickLeaveSalaryHeaderService.insertSickLeaveSalaryHeader( sickLeaveSalaryHeaderVO );

            // 重新加载缓存
            constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, sickLeaveSalaryHeaderVO, Operate.ADD, sickLeaveSalaryHeaderVO.getHeaderId(), null );

            sickLeaveSalaryDetailVO.setHeaderId( sickLeaveSalaryHeaderVO.getHeaderId() );
         }
         else
         {
            // 清空Action Form
            ( ( SickLeaveSalaryHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new SickLeaveSalaryDetailAction().list_object( mapping, sickLeaveSalaryDetailVO, request, response );
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
            final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取ShiftHeaderVO对象
            final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOByHeaderId( headerId );
            // 装载界面传值
            sickLeaveSalaryHeaderVO.update( ( SickLeaveSalaryHeaderVO ) form );
            // 获取登录用户
            sickLeaveSalaryHeaderVO.setModifyBy( getUserId( request, response ) );

            // 调用修改方法
            sickLeaveSalaryHeaderService.updateSickLeaveSalaryHeader( sickLeaveSalaryHeaderVO );

            // 重新加载缓存
            constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, sickLeaveSalaryHeaderVO, Operate.MODIFY, sickLeaveSalaryHeaderVO.getHeaderId(), null );
         }

         // 清空Action Form
         ( ( SickLeaveSalaryHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new SickLeaveSalaryDetailAction().list_object( mapping, new SickLeaveSalaryDetailVO(), request, response );
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
         final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
         // 获得Action Form
         SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = ( SickLeaveSalaryHeaderVO ) form;

         // 存在选中的ID
         if ( KANUtil.filterEmpty( sickLeaveSalaryHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, sickLeaveSalaryHeaderVO, Operate.DELETE, null, sickLeaveSalaryHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : sickLeaveSalaryHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               sickLeaveSalaryHeaderVO = sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOByHeaderId( selectedId );
               sickLeaveSalaryHeaderVO.setHeaderId( selectedId );
               sickLeaveSalaryHeaderVO.setAccountId( getAccountId( request, response ) );
               sickLeaveSalaryHeaderVO.setModifyBy( getUserId( request, response ) );
               sickLeaveSalaryHeaderService.deleteSickLeaveSalaryHeader( sickLeaveSalaryHeaderVO );
            }
         }

         // 清除Selected IDs和子Action
         sickLeaveSalaryHeaderVO.setSelectedIds( "" );
         sickLeaveSalaryHeaderVO.setSubAction( "" );

         // 重新加载缓存
         constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
