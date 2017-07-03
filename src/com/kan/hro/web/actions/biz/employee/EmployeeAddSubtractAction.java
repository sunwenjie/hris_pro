package com.kan.hro.web.actions.biz.employee;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeAddSubtract;
import com.kan.hro.service.inf.biz.employee.EmployeeAddSubtractService;

public class EmployeeAddSubtractAction extends BaseAction
{
   // 当前Action对应的Access Action
   public static final String accessAction = "EMPLOYEE_ADDSUBTRACT_REPORTS";

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
         final EmployeeAddSubtractService employeeAddSubtractService = ( EmployeeAddSubtractService ) getService( "employeeAddSubtractService" );

         // 获得Action Form
         final EmployeeAddSubtract employeeAddSubtract = ( EmployeeAddSubtract ) form;

         //默认操作增员
         if ( org.apache.commons.lang3.StringUtils.isBlank( employeeAddSubtract.getOpType() ) )
         {
            employeeAddSubtract.setOpType( "1" );
         }
         //默认操作员工派送协议
         if ( org.apache.commons.lang3.StringUtils.isBlank( employeeAddSubtract.getType() ) )
         {
            employeeAddSubtract.setType( "5" );
         }

         // 如果没有指定排序则默认按 batchId排序
         if ( employeeAddSubtract.getSortColumn() == null || employeeAddSubtract.getSortColumn().isEmpty() )
         {
            employeeAddSubtract.setSortOrder( "desc" );
            employeeAddSubtract.setSortColumn( "a.contractId" );
         }
         
         String nowDate= new SimpleDateFormat( "yyyy/MM" ).format( new Date() );
         
         request.setAttribute( "nowDate", nowDate );
         
         if ( employeeAddSubtract.getMonth() == null  )
         {
            employeeAddSubtract.setMonth( nowDate );
         }

         // 处理subAction
         dealSubAction( employeeAddSubtract, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( employeeAddSubtract );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeAddSubtractService.getEmployeeAddSubtractsByCondition( pagedListHolder, true );

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeAddSubtractHolder", pagedListHolder );

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listEmployeeAddSubtractTable" );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listEmployeeAddSubtract" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   /**
    * 导出导入模板
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward exportReport( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeAddSubtractService employeeAddSubtractService = ( EmployeeAddSubtractService ) getService( "employeeAddSubtractService" );

         // 初始化PagedListHolder
         final PagedListHolder employeeAddSubtractHolder = new PagedListHolder();

         // 初始化考勤详情查询条件
         final EmployeeAddSubtract employeeAddSubtract = ( EmployeeAddSubtract ) form;
         
         // 解码
         decodedObject( employeeAddSubtract );

         if ( null == employeeAddSubtract.getOpType() )
         {
            employeeAddSubtract.setOpType( "1" );
         }

         if ( null == employeeAddSubtract.getType() )
         {
            employeeAddSubtract.setType( "5" );
         }

         employeeAddSubtractHolder.setObject( employeeAddSubtract );
         employeeAddSubtractService.getEmployeeAddSubtractsByCondition( employeeAddSubtractHolder, false );
         employeeAddSubtract.reset( mapping, request );
         if ( employeeAddSubtractHolder.getSource() != null )
         {
            // 刷新国际化
            refreshHolder( employeeAddSubtractHolder, request );
            final SXSSFWorkbook workbook = employeeAddSubtractService.employeeAddSubtractReport( employeeAddSubtractHolder );
            // 初始化OutputStream
            final OutputStream os = response.getOutputStream();

            // 设置返回文件下载
            response.setContentType( "application/x-msdownload" );

            // 解决文件中文名下载问题
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "增减员表.xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) );

            // Excel文件写入OutputStream
            workbook.write( os );

            // 输出OutputStream
            os.flush();
            //关闭流  
            os.close();

         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );

      }
      return mapping.findForward( "" );

   }
}
