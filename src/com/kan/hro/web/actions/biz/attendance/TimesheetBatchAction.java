package com.kan.hro.web.actions.biz.attendance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetBatchService;

public class TimesheetBatchAction extends BaseAction
{

   // 当前Action对应的Access Action - In House
   public static String accessActionInHouse = "HRO_BIZ_ATTENDANCE_TIMESHEET_BATCH";

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
         final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );

         // 获得Action Form
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;

         //处理数据权限
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessActionInHouse, timesheetBatchVO );
            setDataAuth( request, response, timesheetBatchVO );
         }

         // 如果没有指定排序则默认按 batchId排序
         if ( timesheetBatchVO.getSortColumn() == null || timesheetBatchVO.getSortColumn().isEmpty() )
         {
            timesheetBatchVO.setSortOrder( "desc" );
            timesheetBatchVO.setSortColumn( "batchId" );
         }

         // 如果是inHouse
         //         if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         //         {
         //            // 如果不具HR职能
         //            if ( !isHRFunction( request, response ) )
         //            {
         //               // 初始化TimesheetHeaderVO
         //               final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
         //               timesheetHeaderVO.setAccountId( getAccountId( request, null ) );
         //               timesheetHeaderVO.setCorpId( getCorpId( request, null ) );
         //               timesheetHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         //
         //               return new TimesheetHeaderAction().list_object( mapping, timesheetHeaderVO, request, response );
         //            }
         //         }

         // 处理subAction
         dealSubAction( timesheetBatchVO, mapping, form, request, response );

         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            timesheetBatchVO.setClientId( BaseAction.getClientId( request, response ) );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( timesheetBatchVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         timesheetBatchService.getTimesheetBatchVOsByCondition( pagedListHolder, true );

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "timesheetBatchHolder", pagedListHolder );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listTimesheetBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listTimesheetBatch" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 如果是In House登录，设置帐套数据
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         passClientOrders( request, response );
      }

      return mapping.findForward( "generateTimesheetBatch" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );

            // 获取ActionForm
            final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
            timesheetBatchVO.setOrderId( KANUtil.filterEmpty( timesheetBatchVO.getOrderId(), "0" ) );
            timesheetBatchVO.setCreateBy( getUserId( request, null ) );
            timesheetBatchVO.setModifyBy( getUserId( request, null ) );
            timesheetBatchVO.setStatus( BaseVO.TRUE );

            //处理数据权限
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), TimesheetBatchAction.accessActionInHouse, timesheetBatchVO );
            setDataAuth( request, response, timesheetBatchVO );

            // 生成Timesheet
            final int rows = timesheetBatchService.generateTimesheet( timesheetBatchVO );

            if ( rows > 0 )
            {
               // 返回添加成功标记
               success( request, null, "成功创建 " + rows + " 个批次！" );
               insertlog( request, timesheetBatchVO, Operate.ADD, timesheetBatchVO.getBatchId(), "批量创建考勤表" );
            }
            else
            {
               // 返回警告标记
               warning( request, null, "批次未创建。没有符合条件的数据！" );
            }
         }
         else
         {
            // 返回失败标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // 清空Form条件
         ( ( TimesheetBatchVO ) form ).reset();
         ( ( TimesheetBatchVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 处理Return
      return list_object( mapping, form, request, response );
   }

   // 提交批次
   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );

         // 获得勾选ID
         final String batchIds = timesheetBatchVO.getSelectedIds();

         // 存在勾选批次ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String batchSelectId : selectedIdArray )
            {
               final TimesheetBatchVO submitObject = timesheetBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( batchSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );
               rows = rows + timesheetBatchService.submit_batch( submitObject );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }
            insertlog( request, timesheetBatchVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( timesheetBatchVO.getSelectedIds() ) );
         }

         return list_object( mapping, form, request, response );
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
