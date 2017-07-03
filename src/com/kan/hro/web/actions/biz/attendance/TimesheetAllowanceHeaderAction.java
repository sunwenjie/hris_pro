package com.kan.hro.web.actions.biz.attendance;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceBatchService;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceHeaderService;

public class TimesheetAllowanceHeaderAction extends BaseAction
{

   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_TIMESHEET_HEADER";

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
         final TimesheetAllowanceHeaderService timesheetHeaderService = ( TimesheetAllowanceHeaderService ) getService( "timesheetAllowanceHeaderService" );
         final TimesheetAllowanceBatchService timesheetBatchService = ( TimesheetAllowanceBatchService ) getService( "timesheetAllowanceBatchService" );

         // 获得Action Form
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;

         // 如果没有指定排序则默认按 monthly排序
         if ( timesheetHeaderVO.getSortColumn() == null || timesheetHeaderVO.getSortColumn().isEmpty() )
         {
            timesheetHeaderVO.setSortOrder( "desc" );
            timesheetHeaderVO.setSortColumn( "a.headerId" );
         }
         
         // 处理subAction
         dealSubAction( timesheetHeaderVO, mapping, form, request, response );

         timesheetHeaderVO.setBatchId( batchId );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( timesheetHeaderVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         timesheetHeaderService.getTimesheetHeaderVOsByCondition( pagedListHolder, true );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "timesheetHeaderHolder", pagedListHolder );
         //批次状态
         request.setAttribute("batchStatus", request.getParameter("batchStatus"));
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listTimesheetAllowanceHeaderTable" );
         }
         
         // 获取TimesheetBatchVO
         final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByBatchId( batchId );
         // 写入request
         request.setAttribute( "timesheetBatchForm", timesheetBatchVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listTimesheetAllowanceHeader" );
   }

   public void updateAllowanceBase( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      // Config the response
      response.setContentType( "text/html" );
      response.setCharacterEncoding( "UTF-8" );
      // 初始化PrintWrite对象
      PrintWriter out = null;
      try
      {
         out = response.getWriter();
         final TimesheetAllowanceHeaderService timesheetHeaderService = ( TimesheetAllowanceHeaderService ) getService( "timesheetAllowanceHeaderService" );
         String allowanceId = request.getParameter( "allowanceId" );
         String base = request.getParameter( "base" );
         timesheetHeaderService.updateAllowanceBase( allowanceId, base );
         addSuccessAjax( out, null );
         out.flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
         addFailedAjax( out, null );
      }
      finally
      {
         if ( out != null )
         {
            out.close();
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
   public ActionForward back_header( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;

         // 初始化Service接口
         final TimesheetAllowanceHeaderService timesheetAllowanceHeaderService = ( TimesheetAllowanceHeaderService ) getService( "timesheetAllowanceHeaderService" );

         // 获得勾选ID
         final String allowanceIds = timesheetHeaderVO.getSelectedIds();

         int count = 0;

         // 存在勾选ID
         if ( KANUtil.filterEmpty( allowanceIds ) != null )
         {
            // 分割选择项
            final String[] allowanceIdsArray = allowanceIds.split( "," );

            //批量删除考勤津贴临时表
            int[] returnValue = timesheetAllowanceHeaderService.backTimeSheetAllowanceTemp( allowanceIdsArray, timesheetHeaderVO.getBatchId() );
            count = returnValue[ 1 ];
         }

         // 清除Selected IDs和子Action
         timesheetHeaderVO.setSelectedIds( "" );
         timesheetHeaderVO.setSubAction( "" );
         if ( count == 0 )
         {

            return new ActionForward( "timesheetAllowanceBatchAction.do?proc=list_object", true );

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
