package com.kan.hro.web.actions.biz.attendance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.AttendanceImportDetailVO;
import com.kan.hro.domain.biz.attendance.AttendanceImportHeaderVO;
import com.kan.hro.service.inf.biz.attendance.AttendanceImportDetailService;
import com.kan.hro.service.inf.biz.attendance.AttendanceImportHeaderService;

public class AttendanceImportDetailAction extends BaseAction
{

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
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );
         // 获得Action Form
         final AttendanceImportDetailVO attendanceImportDetailVO = ( AttendanceImportDetailVO ) form;
         attendanceImportDetailVO.setHeaderId( headerId );
         // 初始化Service接口
         final AttendanceImportHeaderService attendanceImportHeaderService = ( AttendanceImportHeaderService ) getService( "attendanceImportHeaderService" );
         final AttendanceImportDetailService attendanceImportDetailService = ( AttendanceImportDetailService ) getService( "attendanceImportDetailService" );

         final AttendanceImportHeaderVO attendanceImportHeaderForm = attendanceImportHeaderService.getAttendanceImportHeaderVOByHeaderId( headerId );
         if ( attendanceImportHeaderForm != null )
         {
            request.setAttribute( "attendanceImportHeaderForm", attendanceImportHeaderForm );
         }

         // 处理subAction
         dealSubAction( attendanceImportDetailVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( attendanceImportDetailVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         attendanceImportDetailService.getAttendanceImportDetailVOsByCondition( pagedListHolder, true );

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "attendanceImportDetailHolder", pagedListHolder );
         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listAttendanceImportDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listAttendanceImportDetail" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}
