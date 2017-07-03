package com.kan.hro.web.actions.biz.attendance;

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
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.LeaveImportBatchService;
import com.kan.hro.service.inf.biz.attendance.LeaveImportHeaderService;

public class LeaveImportHeaderAction extends BaseAction
{

   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_ATTENDANCE_LEAVE_HEADER";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获取批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 初始化Service接口
         final LeaveImportHeaderService timesheetHeaderService = ( LeaveImportHeaderService ) getService( "leaveImportHeaderService" );
         final LeaveImportBatchService timesheetBatchService = ( LeaveImportBatchService ) getService( "leaveImportBatchService" );

         // 获取TimesheetBatchVO
         final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByBatchId( batchId );
         // 写入request
         request.setAttribute( "timesheetBatchForm", timesheetBatchVO );

         // 获得Action Form
         final LeaveImportHeaderVO leaveImportHeaderVO = ( LeaveImportHeaderVO ) form;
         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), LeaveImportBatchAction.accessAction, leaveImportHeaderVO );
         setDataAuth( request, response, leaveImportHeaderVO );

         leaveImportHeaderVO.setBatchId( timesheetBatchVO.getBatchId() );
         // 如果没有指定排序则默认按 monthly排序
         if ( leaveImportHeaderVO.getSortColumn() == null || leaveImportHeaderVO.getSortColumn().isEmpty() )
         {
            leaveImportHeaderVO.setSortOrder( "desc" );
            leaveImportHeaderVO.setSortColumn( "a.leaveheaderId" );
         }

         // 设定查看更改权限
         setHRFunctionRole( mapping, form, request, response );

         // 处理subAction
         dealSubAction( leaveImportHeaderVO, mapping, form, request, response );

         leaveImportHeaderVO.setBatchId( batchId );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( leaveImportHeaderVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );

         //判断是否全选中
         if ( "1".equals( request.getParameter( "selected" ) ) )
         {
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            timesheetHeaderService.getLeaveImportHeaderVOsByCondition( pagedListHolder, false );
            String selectids = "";

            if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object pageObject : pagedListHolder.getSource() )
               {
                  selectids = selectids + ( ( LeaveImportHeaderVO ) pageObject ).getEncodedId() + ",";
               }
            }

            leaveImportHeaderVO.setSelectedIds( selectids );
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         timesheetHeaderService.getLeaveImportHeaderVOsByCondition( pagedListHolder, true );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "leaveImportHeaderHolder", pagedListHolder );
         //批次状态
         request.setAttribute( "batchStatus", request.getParameter( "batchStatus" ) );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            // 设定查看更改权限
            setHRFunctionRole( mapping, form, request, response );
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "leaveImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "leaveImportHeader" );
   }

   // Refresh the page holder for message resource
   public void refreshHolder( final PagedListHolder userHolder, final HttpServletRequest request )
   {
      if ( userHolder != null && userHolder.getSource() != null && userHolder.getSource().size() > 0 )
      {
         for ( Object pageObject : userHolder.getSource() )
         {
            ( ( ActionForm ) pageObject ).reset( null, request );
         }
      }
   }

   /**
    * 退回,物理删除temp表
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward back_heard( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         int rows = -1;
         // 获取ActionForm
         final LeaveImportHeaderVO leaveImportHeaderVO = ( LeaveImportHeaderVO ) form;
         leaveImportHeaderVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final LeaveImportHeaderService leaveImportHeaderService = ( LeaveImportHeaderService ) getService( "leaveImportHeaderService" );

         // 获得勾选ID
         final String selectedIds = leaveImportHeaderVO.getSelectedIds();
         leaveImportHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( leaveImportHeaderVO.getBatchId() ) );
         // 存在勾选ID
         if ( KANUtil.filterEmpty( selectedIds ) != null )
         {
            rows = leaveImportHeaderService.backHeader( leaveImportHeaderVO );

            if ( rows < 0 )
            {
               error( request, MESSAGE_TYPE_DELETE, "无退回记录!" );
            }
            else
            {
               success( request, MESSAGE_TYPE_ROLLBACK );
               insertlog( request, leaveImportHeaderVO, Operate.ROllBACK, null, KANUtil.decodeSelectedIds( selectedIds ) );
            }
         }

         if ( rows == 0 )
         {
            TimesheetBatchVO timesheetBatchVO = new TimesheetBatchVO();
            timesheetBatchVO.reset( mapping, request );
            return new LeaveImportBatchAction().list_object( mapping, timesheetBatchVO, request, response );
         }
         else
         {
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

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
      // No Use
      return null;
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
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

}
