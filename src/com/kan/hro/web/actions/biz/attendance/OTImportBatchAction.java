package com.kan.hro.web.actions.biz.attendance;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.OTImportHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.OTDetailService;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;
import com.kan.hro.service.inf.biz.attendance.OTImportBatchService;
import com.kan.hro.service.inf.biz.attendance.OTImportHeaderService;

public class OTImportBatchAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_ATTENDANCE_OT_BATCH_IMPORT";

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
         final OTImportBatchService timesheetBatchService = ( OTImportBatchService ) getService( "otImportBatchService" );

         // 获得Action Form
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         timesheetBatchVO.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.import.batch.statuses" ) );

         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), OTImportBatchAction.accessAction, timesheetBatchVO );
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
            return mapping.findForward( "otImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "otImportBatch" );
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
         final OTImportBatchService otImportBatchService = ( OTImportBatchService ) getService( "otImportBatchService" );
         final OTImportHeaderService otImportHeaderService = ( OTImportHeaderService ) getService( "otImportHeaderService" );
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

         // 获得勾选ID
         final String batchIds = timesheetBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final TimesheetBatchVO submitObject = otImportBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );
               submitObject.setModifyDate( new Date() );

               if ( !check_batch( otImportHeaderService, submitObject ) )
               {
                  rows = rows + otImportBatchService.updateBatch( submitObject );

                  // 盛竞特例 Add by siuvan 2015-03-20
                  if ( "100045".equals( getAccountId( request, null ) ) )
                  {
                     final List< Object > otImportHeaderVOs = otImportHeaderService.getOTImportHeaderVOsByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
                     if ( otImportHeaderVOs != null && otImportHeaderVOs.size() > 0 )
                     {
                        for ( Object o : otImportHeaderVOs )
                        {
                           final OTImportHeaderVO otImportHeaderVO = ( OTImportHeaderVO ) o;
                           final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTImportHeaderId( otImportHeaderVO.getOtHeaderId() );
                           if ( otHeaderVO != null )
                           {
                              fetchOTHoursDetail( otHeaderVO, mapping, form, request, response );
                           }
                        }
                     }
                  }
               }
               else
               {
                  rows = rows + 1;
               }
            }

            if ( rows == 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, timesheetBatchVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( batchIds ) );
            }
            else if ( rows > 0 )
            {
               error( request, MESSAGE_TYPE_SUBMIT, "加班时间重叠，请检查批次中的加班信息！" );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
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
         final OTImportBatchService otImportBatchService = ( OTImportBatchService ) getService( "otImportBatchService" );

         // 获得勾选ID
         final String batchIds = timesheetBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final TimesheetBatchVO submitObject = otImportBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + otImportBatchService.backBatch( submitObject );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE, "退回成功!" );
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

   // 用以检查Batch中Header数据有无重叠
   // Add by siuvan@2014-09-07
   private boolean check_batch( final OTImportHeaderService otImportHeaderService, final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      boolean flag = false;

      final List< Object > otImportHeaderVOs = otImportHeaderService.getOTImportHeaderVOsByBatchId( timesheetBatchVO.getBatchId() );

      if ( otImportHeaderVOs != null && otImportHeaderVOs.size() > 1 )
      {
         for ( Object otImportHeaderVOObject1 : otImportHeaderVOs )
         {
            final OTImportHeaderVO tempOTImportHeaderVO1 = ( OTImportHeaderVO ) otImportHeaderVOObject1;

            for ( Object otImportHeaderVOObject2 : otImportHeaderVOs )
            {
               final OTImportHeaderVO tempOTImportHeaderVO2 = ( OTImportHeaderVO ) otImportHeaderVOObject2;

               if ( tempOTImportHeaderVO1.getOtHeaderId().equals( tempOTImportHeaderVO2.getOtHeaderId() )
                     || !tempOTImportHeaderVO1.getContractId().equals( tempOTImportHeaderVO2.getContractId() ) )
               {
                  continue;
               }

               // 如果当前请假时间段与原有记录交叉
               if ( KANUtil.createDate( tempOTImportHeaderVO1.getEstimateStartDate() ).getTime() >= KANUtil.createDate( tempOTImportHeaderVO2.getEstimateEndDate() ).getTime()
                     || KANUtil.createDate( tempOTImportHeaderVO1.getEstimateEndDate() ).getTime() <= KANUtil.createDate( tempOTImportHeaderVO2.getEstimateStartDate() ).getTime() )
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

   // 优化审批加班页面，加班详细状况。例如：OT1.5 - 2小时；OT3.0 - 4小时
   // Added by siuvan @2014-09-10
   private void fetchOTHoursDetail( final OTHeaderVO otHeaderVO, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
      final OTDetailService otDetailService = ( OTDetailService ) getService( "otDetailService" );

      final OTHeaderVO tempOTHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderVO.getOtHeaderId() );
      tempOTHeaderVO.reset( mapping, request );

      final List< Object > otDetailVOs = otDetailService.getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );
      if ( otDetailVOs != null && otDetailVOs.size() > 0 )
      {
         double tempOTHours = 0;
         final JSONObject jsonObject = new JSONObject();
         for ( Object otDetailVOObject : otDetailVOs )
         {
            final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;

            if ( KANUtil.filterEmpty( otDetailVO.getActualHours() ) != null )
            {
               tempOTHours = Double.valueOf( otDetailVO.getActualHours() );
            }
            else if ( KANUtil.filterEmpty( otDetailVO.getEstimateHours() ) != null )
            {
               tempOTHours = Double.valueOf( otDetailVO.getEstimateHours() );
            }

            if ( jsonObject.get( otDetailVO.getItemId() ) == null )
            {
               jsonObject.put( otDetailVO.getItemId(), String.valueOf( tempOTHours ) );
            }
            else
            {
               jsonObject.put( otDetailVO.getItemId(), String.valueOf( Double.valueOf( String.valueOf( jsonObject.get( otDetailVO.getItemId() ) ) ) + tempOTHours ) );
            }
         }

         tempOTHeaderVO.setOtDetail( jsonObject.toString() );
         tempOTHeaderVO.setDescription( tempOTHeaderVO.getDecodeOTDetail() );

         otHeaderService.updateOTHeader_onlyUP( tempOTHeaderVO );
      }
   }

}
