package com.kan.hro.web.actions.biz.attendance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.AttendanceImportHeaderVO;
import com.kan.hro.service.inf.biz.attendance.AttendanceImportHeaderService;

public class AttendanceImportHeaderAction extends BaseAction
{

   // 当前Action对应的Access Action - In House
   public static final String ACCESS_ACCTION = "HRO_BIZ_TEMPSHEET_IMPORT_HEADER";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获取批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得Action Form
         final AttendanceImportHeaderVO attendanceImportHeaderVO = ( AttendanceImportHeaderVO ) form;
         attendanceImportHeaderVO.setBatchId( batchId );
         // 初始化Service接口
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final AttendanceImportHeaderService attendanceImportHeaderService = ( AttendanceImportHeaderService ) getService( "attendanceImportHeaderService" );

//         // 处理数据权限
//         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
//         {
//            setDataAuth( request, response, attendanceImportHeaderVO );
//         }

         final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );
         if ( commonBatchVO != null )
         {
            request.setAttribute( "attendanceImportBatchForm", commonBatchVO );
         }

         // 处理subAction
         dealSubAction( attendanceImportHeaderVO, mapping, form, request, response );

         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            attendanceImportHeaderVO.setClientId( BaseAction.getClientId( request, response ) );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( attendanceImportHeaderVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         attendanceImportHeaderService.getAttendanceImportHeaderVOsByCondition( pagedListHolder, true );

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "attendanceImportHeaderHolder", pagedListHolder );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listAttendanceImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listAttendanceImportHeader" );
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

      return mapping.findForward( "generateTimesheetTemp" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 处理Return
      return list_object( mapping, form, request, response );
   }

   // 提交考勤表
   public ActionForward submit( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
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
