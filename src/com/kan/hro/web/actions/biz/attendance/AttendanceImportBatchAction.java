package com.kan.hro.web.actions.biz.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.service.inf.biz.attendance.AttendanceImportBatchService;

public class AttendanceImportBatchAction extends BaseAction
{
   // 模块标识
   public static final String ACCESS_ACCTION = "HRO_BIZ_ATTENDANCE_IMPORT_BATCH";

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
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         // 获得Action Form
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;
         commonBatchVO.setAccessAction( ACCESS_ACCTION );

         // 如果没有指定排序则默认按 batchId排序
         if ( commonBatchVO.getSortColumn() == null || commonBatchVO.getSortColumn().isEmpty() )
         {
            commonBatchVO.setSortOrder( "desc" );
            commonBatchVO.setSortColumn( "batchId" );
         }

         // 处理subAction
         dealSubAction( commonBatchVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( commonBatchVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         commonBatchService.getCommonBatchVOsByCondition( pagedListHolder, true );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "attendanceImportBatchHolder", pagedListHolder );

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listAttendanceImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listAttendanceImportBatch" );
   }

   // 提交
   public ActionForward submit_objects( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;
         // 初始化Service接口
         final AttendanceImportBatchService attendanceImportBatchService = ( AttendanceImportBatchService ) getService( "attendanceImportBatchService" );
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );

         int batchCount = 0;
         int batchNum = 0;
         int timesheetNum = 0;
         final List< String > errorBatchIds = new ArrayList< String >();
         // 存在勾选ID
         if ( KANUtil.filterEmpty( commonBatchVO.getSelectedIds() ) != null )
         {
            batchCount = commonBatchVO.getSelectedIds().split( "," ).length;
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempCommonBatchVO.setModifyBy( getUserId( request, null ) );
               tempCommonBatchVO.setModifyDate( new Date() );
               int rows = attendanceImportBatchService.submitObject( tempCommonBatchVO );
               if ( rows != -1 )
               {
                  batchNum++;
                  timesheetNum = timesheetNum + rows;
               }
               else
               {
                  errorBatchIds.add( tempCommonBatchVO.getBatchId() );
               }
            }
         }

         if ( batchCount == batchNum )
         {
            success( request, null, "提交成功，总共生成了 " + timesheetNum + " 个考勤数据！" );
         }
         else
         {
            warning( request, null, "注意，您提交的批次ID：" + KANUtil.stringListToJasonArray( errorBatchIds, "、" ).replace( "{", "" ).replace( "}", "" ) + " 未成功，具体原因请在批次内部查明！" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   // 退回
   public ActionForward rollback_objects( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;
         // 初始化Service接口
         final AttendanceImportBatchService attendanceImportBatchService = ( AttendanceImportBatchService ) getService( "attendanceImportBatchService" );
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         // 存在勾选ID
         if ( KANUtil.filterEmpty( commonBatchVO.getSelectedIds() ) != null )
         {
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempCommonBatchVO.setModifyBy( getUserId( request, null ) );
               tempCommonBatchVO.setModifyDate( new Date() );
               attendanceImportBatchService.rollbackObject( tempCommonBatchVO );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
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
