package com.kan.base.web.actions.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.management.ShiftExceptionVO;
import com.kan.base.domain.management.ShiftHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ShiftExceptionService;
import com.kan.base.service.inf.management.ShiftHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ShiftExceptionAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得Action Form
         final ShiftExceptionVO shiftExceptionVO = ( ShiftExceptionVO ) form;

         // 处理SubAction
         dealSubAction( shiftExceptionVO, mapping, form, request, response );

         // 初始化Service接口
         final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );

         // 获得主表主键
         String headerId = request.getParameter( "headerId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = shiftExceptionVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         shiftExceptionVO.setHeaderId( headerId );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder shiftExceptionHolder = new PagedListHolder();

         // 传入当前页
         shiftExceptionHolder.setPage( page );
         // 传入当前值对象
         shiftExceptionHolder.setObject( shiftExceptionVO );
         // 设置页面记录条数
         shiftExceptionHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         shiftExceptionService.getShiftExceptionVOsByCondition( shiftExceptionHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( shiftExceptionHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "shiftExceptionHolder", shiftExceptionHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listShiftExceptionTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
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
         // 初始化ShiftDet
         final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );
            // 获得当前FORM
            final ShiftExceptionVO shiftExceptionVO = ( ShiftExceptionVO ) form;

            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            shiftDetailVO.setHeaderId( headerId );
            shiftExceptionVO.setHeaderId( headerId );
            shiftExceptionVO.setCreateBy( getUserId( request, response ) );
            shiftExceptionVO.setModifyBy( getUserId( request, response ) );
            shiftExceptionVO.setAccountId( getAccountId( request, response ) );

            shiftExceptionService.insertShiftException( shiftExceptionVO );

            // 重新加载到缓存中
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_EXCEPTION" );
         }

         // 清空Form
         ( ( ShiftExceptionVO ) form ).reset();

         // 跳转排班详情
         return new ShiftDetailAction().list_object( mapping, shiftDetailVO, request, response );
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

   public ActionForward to_objectModify_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );

         // 主键主表ID
         final String exceptionId = KANUtil.decodeString( request.getParameter( "exceptionId" ) );

         // 获得ShiftExceptionVO对象
         final ShiftExceptionVO shiftExceptionVO = shiftExceptionService.getShiftExceptionVOByExceptionId( exceptionId );

         // 获得ShiftHeaderVO对象
         final ShiftHeaderVO shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( shiftExceptionVO.getHeaderId() );

         // 国际化传值
         shiftExceptionVO.reset( null, request );

         // 区分修改添加
         shiftExceptionVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "shiftHeaderForm", shiftHeaderVO );
         request.setAttribute( "shiftExceptionForm", shiftExceptionVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageShiftExceptionForm" );
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
         // 初始化ShiftDet
         final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();

         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );
            // 主键获取需解码
            final String exceptionId = KANUtil.decodeString( request.getParameter( "exceptionId" ) );
            // 获取ShiftExceptionVO
            final ShiftExceptionVO shiftExceptionVO = shiftExceptionService.getShiftExceptionVOByExceptionId( exceptionId );
            // 装载界面传值
            shiftExceptionVO.update( ( ShiftExceptionVO ) form );
            // 获取登录用户
            shiftExceptionVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            shiftExceptionService.updateShiftException( shiftExceptionVO );

            shiftDetailVO.setHeaderId( shiftExceptionVO.getHeaderId() );

            // 重新加载到缓存中
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_EXCEPTION" );
         }

         // 清空Form
         ( ( ShiftExceptionVO ) form ).reset();

         // 跳转排班详情
         return new ShiftDetailAction().list_object( mapping, shiftDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );
         // 获得当前form
         ShiftExceptionVO shiftExceptionVO = ( ShiftExceptionVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( shiftExceptionVO.getSelectedIds() ) != null )
         {
            // 分割
            for ( String selectedId : shiftExceptionVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               shiftExceptionVO = shiftExceptionService.getShiftExceptionVOByExceptionId( KANUtil.decodeStringFromAjax( selectedId ) );
               shiftExceptionVO.setModifyBy( getUserId( request, response ) );
               shiftExceptionVO.setModifyDate( new Date() );
               // 调用删除接口
               shiftExceptionService.deleteShiftExceptionl( shiftExceptionVO );
            }

            // 重新加载到缓存中
            constantsInit( "initShiftHeader", getAccountId( request, response ) );
         }
         // 清除Selected IDs和子Action
         shiftExceptionVO.setSelectedIds( "" );
         shiftExceptionVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
