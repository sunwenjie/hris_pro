package com.kan.hro.web.actions.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveReportVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractLeaveService;

public class EmployeeContractLeaveReportAction extends BaseAction
{

   // Module AccessAction
   public final static String ACCESS_ACTION = "HRO_BIZ_ATTENDANCE_LEAVE_REPORT";

   // 年假报表 - accessAction
   public final static String ACCESS_ACTION_ANNUAL_LEAVE = "HRO_BIZ_ATTENDANCE_ANNUAL_LEAVE_REPORT";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final EmployeeContractLeaveReportVO employeeContractLeaveReportVO = ( EmployeeContractLeaveReportVO ) form;

         // HR_Service登录、IN_House登录
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, employeeContractLeaveReportVO );
         }
         // 员工登录
         else if ( KANConstants.ROLE_EMPLOYEE.equals( BaseAction.getRole( request, response ) ) )
         {
            employeeContractLeaveReportVO.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
         }

         // 初始化Service接口
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         // 搜索内容需要解码。
         decodedObject( employeeContractLeaveReportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeContractLeaveReportHolder = new PagedListHolder();
         // 传入当前页
         employeeContractLeaveReportHolder.setPage( page );
         // 传入当前值对象
         setDataAuth( request, response, employeeContractLeaveReportVO );
         employeeContractLeaveReportHolder.setObject( employeeContractLeaveReportVO );
         // 设置页面记录条数
         employeeContractLeaveReportHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractLeaveService.getEmployeeContractLeaveReportVOsByCondition( employeeContractLeaveReportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
               : true ) );
         // 刷新Holder，国际化传值
         refreshHolder( employeeContractLeaveReportHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeContractLeaveReportHolder", employeeContractLeaveReportHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               final XSSFWorkbook workbook = employeeContractLeaveService.exportEmployeeContractLeaveReport( getTitleMapping( request, response ), employeeContractLeaveReportHolder, request.getLocale().getLanguage() );
               // 导出文件
               new DownloadFileAction().download( response, workbook, ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "休假情况报表.xlsx" : "Leave Detail Report.xlsx" ) );
               return null;
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table调用，直接传回Item JSP
               return mapping.findForward( "listEmployeeContractLeaveReportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listEmployeeContractLeaveReport" );
   }

   /***
    * 年假总额报表
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward annual_leave_report( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final EmployeeContractLeaveReportVO employeeContractLeaveReportVO = ( EmployeeContractLeaveReportVO ) form;
         employeeContractLeaveReportVO.setCurrYear( KANUtil.formatDate( new Date(), "yyyy" ) );
         setDataAuth( request, response, employeeContractLeaveReportVO );
         // 初始化Service接口
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         // 搜索内容需要解码。
         decodedObject( employeeContractLeaveReportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder annualLeaveDetailsHolder = new PagedListHolder();
         // 传入当前页
         annualLeaveDetailsHolder.setPage( page );
         // 传入当前值对象
         setDataAuth( request, response, employeeContractLeaveReportVO );
         annualLeaveDetailsHolder.setObject( employeeContractLeaveReportVO );
         // 设置页面记录条数
         annualLeaveDetailsHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractLeaveService.getAnnualLeaveDetailsByCondition( annualLeaveDetailsHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // 刷新Holder，国际化传值
         refreshHolder( annualLeaveDetailsHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "annualLeaveDetailsHolder", annualLeaveDetailsHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               final XSSFWorkbook workbook = employeeContractLeaveService.exportAnnualLeaveDetails( getAnnualLeaveReportTitleMapping( request, response ), annualLeaveDetailsHolder, request.getLocale() );
               // 导出文件
               new DownloadFileAction().download( response, workbook, ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "年假情况报表.xlsx"
                     : "Annual Leave Detail Report.xlsx" ) );
               return null;
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table调用，直接传回Item JSP
               return mapping.findForward( "listAnnualLeaveDetailsTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listAnnualLeaveDetails" );
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

   private List< MappingVO > getAnnualLeaveReportTitleMapping( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< MappingVO > titleMappingVOs = new ArrayList< MappingVO >();
      MappingVO mappingVO = null;
      mappingVO = new MappingVO( "employeeId", KANUtil.getProperty( request.getLocale(), "public.employee2.id" ) );
      titleMappingVOs.add( mappingVO );
      mappingVO = new MappingVO( "employeeNameZH", KANUtil.getProperty( request.getLocale(), "public.employee2.name.cn" ) );
      titleMappingVOs.add( mappingVO );
      mappingVO = new MappingVO( "employeeNameEN", KANUtil.getProperty( request.getLocale(), "public.employee2.name.en" ) );
      titleMappingVOs.add( mappingVO );
      mappingVO = new MappingVO( "shortName", KANUtil.getProperty( request.getLocale(), "public.employee2.short.name" ) );
      titleMappingVOs.add( mappingVO );
      mappingVO = new MappingVO( "lastLeaveDetails", ( Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) ) - 1 )
            + KANUtil.getProperty( request.getLocale(), "annual.leave.report.annualLeave" ) );
      titleMappingVOs.add( mappingVO );
      mappingVO = new MappingVO( "thisLeaveDetails", KANUtil.formatDate( new Date(), "yyyy" ) + KANUtil.getProperty( request.getLocale(), "annual.leave.report.annualLeave" ) );
      titleMappingVOs.add( mappingVO );
      mappingVO = new MappingVO( "decodeContractStatus", KANUtil.getProperty( request.getLocale(), "public.contract2.status" ) );
      titleMappingVOs.add( mappingVO );
      return titleMappingVOs;
   }

   private List< MappingVO > getTitleMapping( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< MappingVO > titleMappingVOs = new ArrayList< MappingVO >();
      MappingVO mappingVO = null;
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         mappingVO = new MappingVO( "employeeId", KANUtil.getProperty( request.getLocale(), "public.employee2.id" ) );
         titleMappingVOs.add( mappingVO );
         mappingVO = new MappingVO( "employeeNameZH", KANUtil.getProperty( request.getLocale(), "public.employee2.name.cn" ) );
         titleMappingVOs.add( mappingVO );
         mappingVO = new MappingVO( "employeeNameEN", KANUtil.getProperty( request.getLocale(), "public.employee2.name.en" ) );
         titleMappingVOs.add( mappingVO );
         mappingVO = new MappingVO( "contractId", KANUtil.getProperty( request.getLocale(), "public.contract2.id" ) );
         titleMappingVOs.add( mappingVO );
         mappingVO = new MappingVO( "orderId", KANUtil.getProperty( request.getLocale(), "public.order2.id" ) );
         titleMappingVOs.add( mappingVO );
         mappingVO = new MappingVO( "employeeShortName", KANUtil.getProperty( request.getLocale(), "business.employee.report.nick.name" ) );
         titleMappingVOs.add( mappingVO );
      }
      else
      {
         mappingVO = new MappingVO( "employeeId", KANUtil.getProperty( request.getLocale(), "public.employee1.id" ) );
         titleMappingVOs.add( mappingVO );
         mappingVO = new MappingVO( "employeeNameZH", KANUtil.getProperty( request.getLocale(), "public.employee1.name.cn" ) );
         titleMappingVOs.add( mappingVO );
         mappingVO = new MappingVO( "employeeNameEN", KANUtil.getProperty( request.getLocale(), "public.employee1.name.en" ) );
         titleMappingVOs.add( mappingVO );
         mappingVO = new MappingVO( "contractId", KANUtil.getProperty( request.getLocale(), "public.contract1.id" ) );
         titleMappingVOs.add( mappingVO );
         mappingVO = new MappingVO( "orderId", KANUtil.getProperty( request.getLocale(), "public.order1.id" ) );
         titleMappingVOs.add( mappingVO );
      }
      return titleMappingVOs;
   }
}
