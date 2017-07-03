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
import com.kan.base.service.inf.management.ShiftDetailService;
import com.kan.base.service.inf.management.ShiftHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ShiftDetailAction extends BaseAction
{
   public static final String accessAction = "HRO_ATTENDANCE_SHIFT";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得Action Form
         final ShiftDetailVO shiftDetailVO = ( ShiftDetailVO ) form;

         // 处理subAction
         dealSubAction( shiftDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) != null )
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }
         else
         {
            headerId = ( ( ShiftDetailVO ) form ).getHeaderId();
         }

         // 获得主表对象
         final ShiftHeaderVO shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( headerId );
         // 刷新国际化
         shiftHeaderVO.reset( null, request );
         // 区分修改添加
         shiftHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "shiftHeaderForm", shiftHeaderVO );

         shiftDetailVO.setHeaderId( headerId );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder shiftDetailHolder = new PagedListHolder();
         // 传入当前页
         shiftDetailHolder.setPage( page );
         // 传入当前值对象
         shiftDetailHolder.setObject( shiftDetailVO );
         // 设置页面记录条数
         shiftDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         shiftDetailService.getShiftDetailVOsByCondition( shiftDetailHolder, false );
         // 刷新Holder，国际化传值
         refreshHolder( shiftDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "shiftDetailHolder", shiftDetailHolder );

         int maxPeriod = 0;
         int minPeriod = 48;
         if ( shiftDetailHolder != null && shiftDetailHolder.getSource() != null && shiftDetailHolder.getSource().size() > 0 )
         {
            for ( Object shiftDetailObject : shiftDetailHolder.getSource() )
            {
               final ShiftDetailVO shiftDetail = ( ShiftDetailVO ) shiftDetailObject;
               if ( KANUtil.filterEmpty( shiftDetail.getShiftPeriod() ) != null )
               {
                  String[] periodArray = KANUtil.jasonArrayToStringArray( shiftDetail.getShiftPeriod() );
                  if ( Integer.valueOf( periodArray[ 0 ] ) < minPeriod )
                  {
                     minPeriod = Integer.valueOf( periodArray[ 0 ] );
                  }

                  if ( Integer.valueOf( periodArray[ periodArray.length - 1 ] ) > maxPeriod )
                  {
                     maxPeriod = Integer.valueOf( periodArray[ periodArray.length - 1 ] );
                  }
               }
            }
         }

         request.setAttribute( "minPeriod", minPeriod );
         request.setAttribute( "maxPeriod", maxPeriod );

         // 初始化ShiftExceptionVO
         final ShiftExceptionVO shiftExceptionVO = new ShiftExceptionVO();
         shiftExceptionVO.setHeaderId( headerId );
         shiftExceptionVO.setExceptionType( "2" );
         shiftExceptionVO.setStatus( "1" );
         shiftExceptionVO.reset( null, request );
         request.setAttribute( "shiftExceptionForm", shiftExceptionVO );

         //* 加载Shift Exception Info
         new ShiftExceptionAction().list_object( mapping, shiftExceptionVO, request, response );

         // 清空subAction
         ( ( ShiftDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listShiftDetail" );
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
            // 初始化 Service接口
            final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
            // 获得当前FORM
            final ShiftDetailVO shiftDetailVO = ( ShiftDetailVO ) form;

            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            shiftDetailVO.setHeaderId( headerId );
            shiftDetailVO.setCreateBy( getUserId( request, response ) );
            shiftDetailVO.setModifyBy( getUserId( request, response ) );
            shiftDetailVO.setAccountId( getAccountId( request, response ) );

            shiftDetailService.insertShiftDetail( shiftDetailVO );

            // 重新加载到缓存中
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
            insertlog( request, shiftDetailVO, Operate.ADD, shiftDetailVO.getDetailId(), null );
         }

         // 清空Form
         ( ( ShiftDetailVO ) form ).reset();
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
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );

         // 主键主表ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );

         // 获得ShiftDetailVO对象
         final ShiftDetailVO shiftDetailVO = shiftDetailService.getShiftDetailVOByDetailId( detailId );

         // 获得ShiftHeaderVO对象
         final ShiftHeaderVO shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( shiftDetailVO.getHeaderId() );

         // 国际化传值
         shiftDetailVO.reset( null, request );

         // 区分修改添加
         shiftDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "shiftHeaderForm", shiftHeaderVO );
         request.setAttribute( "shiftDetailForm", shiftDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageShiftDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
            // 主键获取需解码
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // 获取主键对象
            final ShiftDetailVO shiftDetailVO = shiftDetailService.getShiftDetailVOByDetailId( detailId );
            // 装载界面传值
            shiftDetailVO.update( ( ShiftDetailVO ) form );
            // 获取登录用户
            shiftDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            shiftDetailService.updateShiftDetail( shiftDetailVO );

            // 重新加载到缓存中
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
            insertlog( request, shiftDetailVO, Operate.MODIFY, shiftDetailVO.getDetailId(), null );
         }

         // 清空Form
         ( ( ShiftDetailVO ) form ).reset();
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
         final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
         // 获得当前form
         ShiftDetailVO shiftDetailVO = ( ShiftDetailVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( shiftDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, shiftDetailVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( shiftDetailVO.getSelectedIds() ) );
            // 分割
            for ( String selectedId : shiftDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               shiftDetailVO = shiftDetailService.getShiftDetailVOByDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
               shiftDetailVO.setModifyBy( getUserId( request, response ) );
               shiftDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               shiftDetailService.deleteShiftDetail( shiftDetailVO );
            }

            // 重新加载到缓存中
            constantsInit( "initShiftHeader", getAccountId( request, response ) );
         }
         // 清除Selected IDs和子Action
         shiftDetailVO.setSelectedIds( "" );
         shiftDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}