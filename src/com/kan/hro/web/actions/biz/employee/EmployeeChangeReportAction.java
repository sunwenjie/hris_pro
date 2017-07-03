package com.kan.hro.web.actions.biz.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeeChangeReportVO;
import com.kan.hro.service.inf.biz.employee.EmployeeChangeReportService;

public class EmployeeChangeReportAction extends BaseAction
{

   public static final String ACCESS_ACTION = "HRO_BIZ_EMPLOYEE_CHANGE_REPORT";

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
         final EmployeeChangeReportService employeeChangeReportService = ( EmployeeChangeReportService ) getService( "employeeChangeReportService" );
         // 获得Action Form
         final EmployeeChangeReportVO employeeChangeReportVO = ( EmployeeChangeReportVO ) form;
         decodedObject(employeeChangeReportVO);
         setDataAuth( request, response, employeeChangeReportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeChangeReportHolder = new PagedListHolder();
         // 传入当前页
         employeeChangeReportHolder.setPage( page );
         // 传入当前值对象
         employeeChangeReportHolder.setObject( employeeChangeReportVO );
         // 设置页面记录条数
         employeeChangeReportHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeChangeReportService.getEmployeeChangeReportVOsByCondition( employeeChangeReportHolder, getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( employeeChangeReportHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", employeeChangeReportHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax导出Excel
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               final String[] nameSyses = new String[] { "employeeId", "employeeNameZH", "employeeNameEN", "decodeChangeReason", "decodeChangeType", "excelChangeContent",
                     "operateBy", "decodeOperateType", "decodeOperateTime" };
               request.setAttribute( "holderName", "pagedListHolder" );
               request.setAttribute( "fileName", "Employee Change" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", nameSyses );

               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }

            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listEmployeeChangeReportTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listEmployeeChangeReport" );
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

   // 导出表头
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final String[] nameZHs = new String[ 9 ];
      nameZHs[ 0 ] = KANUtil.getProperty( request.getLocale(), "public.employee2.id" );
      nameZHs[ 1 ] = KANUtil.getProperty( request.getLocale(), "public.employee2.name.cn" );
      nameZHs[ 2 ] = KANUtil.getProperty( request.getLocale(), "public.employee2.name.en" );
      nameZHs[ 3 ] = KANUtil.getProperty( request.getLocale(), "employee.position.change.description" );
      nameZHs[ 4 ] = KANUtil.getProperty( request.getLocale(), "employee.change.report.changeType" );
      nameZHs[ 5 ] = KANUtil.getProperty( request.getLocale(), "employee.change.report.changeContent" );
      nameZHs[ 6 ] = KANUtil.getProperty( request.getLocale(), "employee.change.report.operateBy" );
      nameZHs[ 7 ] = KANUtil.getProperty( request.getLocale(), "employee.change.report.operateType" );
      nameZHs[ 8 ] = KANUtil.getProperty( request.getLocale(), "employee.change.report.operateTime" );
      return nameZHs;
   }

}
