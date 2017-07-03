package com.kan.hro.web.actions.biz.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.LeaveImportBatchService;
import com.kan.hro.service.inf.biz.attendance.LeaveImportHeaderService;

public class LeaveImportBatchAction extends BaseAction
{

   // 当前Action对应的Access Action - In House
   public static String accessAction = "HRO_BIZ_ATTENDANCE_LEAVE_BATCH_IMPORT";

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
         final LeaveImportBatchService timesheetBatchService = ( LeaveImportBatchService ) getService( "leaveImportBatchService" );

         // 获得Action Form
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         timesheetBatchVO.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.import.batch.statuses" ) );

         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, timesheetBatchVO );
         setDataAuth( request, response, timesheetBatchVO );

         // 如果没有指定排序则默认按 batchId排序
         if ( timesheetBatchVO.getSortColumn() == null || timesheetBatchVO.getSortColumn().isEmpty() )
         {
            timesheetBatchVO.setSortOrder( "desc" );
            timesheetBatchVO.setSortColumn( "batchId" );
         }

         // 处理subAction
         dealSubAction( timesheetBatchVO, mapping, form, request, response );

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
            return mapping.findForward( "leaveImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "leaveImportBatch" );
   }

   /**
    * 更新
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final LeaveImportBatchService leaveImportBatchService = ( LeaveImportBatchService ) getService( "leaveImportBatchService" );
         final LeaveImportHeaderService leaveImportHeaderService = ( LeaveImportHeaderService ) getService( "leaveImportHeaderService" );

         // 获得勾选ID
         final String batchIds = timesheetBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            // 收集本身有时间重叠的batchId
            final List< String > overlapBatchIds = new ArrayList< String >();

            // 收集与真实表中存在时间冲突的leaveHeaderId
            final Map< String, List< String > > overlapBatchIdMap = new HashMap< String, List< String > >();

            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final TimesheetBatchVO submitObject = leaveImportBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               final List< String > leaveHeaderIds = new ArrayList< String >();
               if ( !check_batch( leaveImportHeaderService, submitObject ) )
               {
                  leaveHeaderIds.addAll( leaveImportBatchService.updateBatch( submitObject ) );
               }
               else
               {
                  overlapBatchIds.add( submitObject.getBatchId() );
               }

               if ( leaveHeaderIds != null && leaveHeaderIds.size() > 0 )
               {
                  overlapBatchIdMap.put( submitObject.getBatchId(), leaveHeaderIds );
               }
            }

            // 发往客户端错误信息
            final StringBuilder errorMsg = new StringBuilder();

            // 批次中本身时间冲突
            if ( overlapBatchIds.size() > 0 )
            {
               String overlapBatchIds_str = KANUtil.stringListToJasonArray( overlapBatchIds, ", " );
               String message = KANUtil.getProperty( request.getLocale(), "error.leave.import.submit.time.overlap1" );
               errorMsg.append( message.replace( "{0}", overlapBatchIds_str ) );
               errorMsg.append( "<br/>" );
            }

            // 批次中与实际表时间冲突
            if ( overlapBatchIdMap != null && overlapBatchIdMap.size() > 0 )
            {
               for ( String overlapBatchIdKey : overlapBatchIdMap.keySet() )
               {
                  String overlapLeaveHeaderIds_str = KANUtil.stringListToJasonArray( overlapBatchIdMap.get( overlapBatchIdKey ), ", " );
                  String message = KANUtil.getProperty( request.getLocale(), "error.leave.import.submit.time.overlap2" );
                  errorMsg.append( message.replace( "{0}", overlapBatchIdKey ).replace( "{1}", overlapLeaveHeaderIds_str ) );
                  errorMsg.append( "<br/>" );
               }
            }

            if ( KANUtil.filterEmpty( errorMsg.toString() ) != null )
            {
               error( request, null, errorMsg.toString() );
            }
            else
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, timesheetBatchVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( batchIds ) );
            }
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
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
   public ActionForward back_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final LeaveImportBatchService leaveImportBatchService = ( LeaveImportBatchService ) getService( "leaveImportBatchService" );

         // 获得勾选ID
         final String batchIds = timesheetBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = -1;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final TimesheetBatchVO submitObject = leaveImportBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + leaveImportBatchService.backBatch( submitObject );
            }
            if ( rows < 0 )
            {
               error( request, MESSAGE_TYPE_DELETE, "退回失败!" );
            }
            else
            {
               success( request, MESSAGE_TYPE_ROLLBACK );
               insertlog( request, timesheetBatchVO, Operate.ROllBACK, null, KANUtil.decodeSelectedIds( batchIds ) );
            }
         }
         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // 用以检查Batch中Header数据有无时间重叠
   // Add by siuvan@2014-09-07
   private boolean check_batch( final LeaveImportHeaderService leaveImportHeaderService, final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      boolean flag = false;

      final List< Object > leaveImportHeaderVOs = leaveImportHeaderService.getLeaveImportHeaderVOsByBatchId( timesheetBatchVO.getBatchId() );

      if ( leaveImportHeaderVOs != null && leaveImportHeaderVOs.size() > 1 )
      {
         for ( Object leaveImportHeaderVOObject1 : leaveImportHeaderVOs )
         {
            final LeaveImportHeaderVO tempLeaveImportHeaderVO1 = ( LeaveImportHeaderVO ) leaveImportHeaderVOObject1;

            for ( Object leaveImportHeaderVOObject2 : leaveImportHeaderVOs )
            {
               final LeaveImportHeaderVO tempLeaveImportHeaderVO2 = ( LeaveImportHeaderVO ) leaveImportHeaderVOObject2;

               if ( tempLeaveImportHeaderVO1.getLeaveHeaderId().equals( tempLeaveImportHeaderVO2.getLeaveHeaderId() )
                     || !tempLeaveImportHeaderVO1.getContractId().equals( tempLeaveImportHeaderVO2.getContractId() ) )
               {
                  continue;
               }

               // 如果当前请假时间段与原有记录交叉
               if ( KANUtil.createDate( tempLeaveImportHeaderVO1.getEstimateStartDate() ).getTime() >= KANUtil.createDate( tempLeaveImportHeaderVO2.getEstimateEndDate() ).getTime()
                     || KANUtil.createDate( tempLeaveImportHeaderVO1.getEstimateEndDate() ).getTime() <= KANUtil.createDate( tempLeaveImportHeaderVO2.getEstimateStartDate() ).getTime() )
                  flag = false;
               else
                  flag = true;
               break;
            }

            if ( flag )
               break;
         }
      }

      return flag;
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
