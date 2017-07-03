package com.kan.hro.web.actions.biz.employee;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;
import com.kan.hro.service.inf.biz.employee.EmployeeReportService;

public class EmployeeReportAction extends BaseAction
{
   // Module AccessAction
   public static final String ACCESS_ACTION_NEW = "HRO_BIZ_EMPLOYEE_NEW_REPORT";

   public static final String ACCESS_ACTION_DIMISSION = "HRO_BIZ_EMPLOYEE_DIMISSION_REPORT";

   // 人员信息全表1
   public static final String ACCESS_ACTION_FULL = "HRO_BIZ_EMPLOYEE_FULL_REPORT";

   // 人员信息全表2
   public static final String ACCESS_ACTION_FULL_2 = "HRO_BIZ_EMPLOYEE_FULL_REPORT_2";

   // 人员信息全表3
   public static final String ACCESS_ACTION_FULL_3 = "HRO_BIZ_EMPLOYEE_FULL_REPORT_3";

   public static final String ACCESS_ACTION_PERFORMANCE = "HRO_BIZ_EMPLOYEE_PERFORMANCE_REPORT";

   private void isPeopleManager( HttpServletRequest request ) throws KANException
   {
      final String positionId = getPositionId( request, null );
      PositionDTO positionDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionDTOByPositionId( positionId );

      if ( positionDTO != null && positionDTO.getPositionGroupRelationVOs().size() > 0 )
      {
         for ( Object o : positionDTO.getPositionGroupRelationVOs() )
         {
            if ( "203".equals( ( ( PositionGroupRelationVO ) o ).getGroupId() ) )
            {
               request.setAttribute( "pm_hide", "1" );
               break;
            }
         }
      }
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      String returnPage = "";
      String returnPageTable = "";
      String exportFileName = "";

      isPeopleManager( request );
      
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final EmployeeReportVO employeeReportVO = ( EmployeeReportVO ) form;
         // 获取报表类型
         final String rt = request.getParameter( "rt" );
         // 获取报表角色
         final String rl = request.getParameter( "rl" );
         // 初始化Service接口
         final EmployeeReportService employeeReportService = ( EmployeeReportService ) getService( "employeeReportService" );
         final List< MappingVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, response ) );
         employeeReportVO.setSalarys( itemVOs );

         // HR_Service登录、IN_House登录
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, employeeReportVO );
         }

         // 新入职员工报表condition
         if ( KANUtil.filterEmpty( rt ) != null && rt.equals( "new" ) )
         {
            final String date = KANUtil.formatDate( new Date() );
            String monthBegin = KANUtil.formatDate( KANUtil.getFirstDate( date ) );
            String monthEnd = KANUtil.formatDate( KANUtil.getLastDate( date ) );
            if ( KANUtil.filterEmpty( employeeReportVO.getMonthBegin() ) != null )
               monthBegin = employeeReportVO.getMonthBegin();
            if ( KANUtil.filterEmpty( employeeReportVO.getMonthEnd() ) != null )
               monthEnd = employeeReportVO.getMonthEnd();
            employeeReportVO.setMonthBegin( monthBegin );
            employeeReportVO.setMonthEnd( monthEnd );
            employeeReportVO.setContractStatus( "3" );

            returnPage = "listEmployeeNewReport";
            returnPageTable = "listEmployeeNewReportTable";
            exportFileName = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "新入职员工报表" : "New Employee Report";
         }
         // 离职员工报表condition
         else if ( KANUtil.filterEmpty( rt ) != null && rt.equals( "dimission" ) )
         {
            employeeReportVO.setEmployStatus( "1" );
            returnPage = "listEmployeeDimissionReport";
            returnPageTable = "listEmployeeDimissionReportTable";
            exportFileName = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "离职员工报表" : "Dimission Employee Report";
         }
         else if ( KANUtil.filterEmpty( rt ) != null && rt.equals( "full" ) )
         {
            returnPage = "listEmployeeFullReport" + ( KANUtil.filterEmpty( rl ) == null ? "" : rl );
            returnPageTable = "listEmployeeFullReportTable" + ( KANUtil.filterEmpty( rl ) == null ? "" : rl );
            exportFileName = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "员工信息全表报表" : "Full Employee Report";
         }
         else if ( KANUtil.filterEmpty( rt ) != null && rt.equals( "performance" ) )
         {
            returnPage = "listEmployeePerformanceReport";
            returnPageTable = "listEmployeePerformanceReportTable";
            exportFileName = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "员工绩效报表" : "Performance Employee Report";
         }
         // 搜索内容需要解码。
         decodedObject( employeeReportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeReportHolder = new PagedListHolder();
         // 传入当前页
         employeeReportHolder.setPage( page );
         // 传入当前值对象
         employeeReportHolder.setObject( employeeReportVO );
         // 设置页面记录条数
         employeeReportHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeReportService.getEmployeeReportVOsByCondition( employeeReportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // 刷新Holder，国际化传值
         refreshHolder( employeeReportHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeReportHolder", employeeReportHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            isPeopleManager( request );
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               arrayToRequest( request, employeeReportVO, rl == null ? "1" : rl );
               request.setAttribute( "holderName", "employeeReportHolder" );
               request.setAttribute( "fileName", exportFileName );
               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, true );
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table调用，直接传回Item JSP
               return mapping.findForward( returnPageTable );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( returnPage );
   }

   public ActionForward list_performance( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final EmployeeReportVO employeeReportVO = ( EmployeeReportVO ) form;
         // 初始化Service接口
         final EmployeeReportService employeeReportService = ( EmployeeReportService ) getService( "employeeReportService" );
         final List< MappingVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, response ) );
         employeeReportVO.setSalarys( itemVOs );

         // HR_Service登录、IN_House登录
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, employeeReportVO );
         }

         // 默认查询当前年绩效
         if ( KANUtil.filterEmpty( employeeReportVO.getYearly() ) == null )
            employeeReportVO.setYearly( KANUtil.formatDate( new Date(), "yyyy" ) );

         // 搜索内容需要解码。
         decodedObject( employeeReportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeReportHolder = new PagedListHolder();
         // 传入当前页
         employeeReportHolder.setPage( page );
         // 传入当前值对象
         employeeReportHolder.setObject( employeeReportVO );
         // 设置页面记录条数
         employeeReportHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeReportService.getEmployeePerformanceReportVOsByCondition( employeeReportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // 刷新Holder，国际化传值
         refreshHolder( employeeReportHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeReportHolder", employeeReportHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               // 生成Excel文件
               final XSSFWorkbook workbook = employeeReportService.generatePerformanceReport( employeeReportHolder );

               // 初始化OutputStream
               final OutputStream os = response.getOutputStream();
               // 设置返回文件下载
               response.setContentType( "application/x-msdownload" );
               // 解决文件中文名下载问题
               response.setHeader( "Content-Disposition", "attachment;filename=\""
                     + new String( URLDecoder.decode( "Performance Reports " + KANUtil.formatDate( new Date(), "yyyyMMdd hhmmss" ) + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" )
                     + "\"" );
               // Excel文件写入OutputStream
               workbook.write( os );
               // 输出OutputStream
               os.flush();
               //关闭流  
               os.close();

               return mapping.findForward( "" );
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table调用，直接传回Item JSP
               return mapping.findForward( "listEmployeePerformanceReportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listEmployeePerformanceReport" );
   }

   public void arrayToRequest( final HttpServletRequest request, final EmployeeReportVO employeeReportVO, final String rl )
   {
      String titleNameList = employeeReportVO.getTitleNameList();
      String titleIdList = employeeReportVO.getTitleIdList();

      if ( !"3".equals( rl ) && employeeReportVO != null && "full".equals( employeeReportVO.getRt() ) && employeeReportVO.getSalarys() != null
            && employeeReportVO.getSalarys().size() > 0 )
      {
         for ( MappingVO vo : employeeReportVO.getSalarys() )
         {
            titleNameList = titleNameList + "," + vo.getMappingValue();
            titleIdList = titleIdList + "," + "salaryItem_" + vo.getMappingId();
         }
      }

      request.setAttribute( "nameZHArray", titleNameList.split( "," ) );
      request.setAttribute( "nameSysArray", titleIdList.split( "," ) );
   }

   public ActionForward list_employee_salary_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final EmployeeReportVO employeeReportVO = ( EmployeeReportVO ) form;
         // 初始化Service接口
         final EmployeeReportService employeeReportService = ( EmployeeReportService ) getService( "employeeReportService" );

         //final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // HR_Service登录、IN_House登录
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, employeeReportVO );
         }

         final List< MappingVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, response ) );
         // 搜索内容需要解码。
         decodedObject( employeeReportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeReportHolder = new PagedListHolder();
         // 传入当前页
         employeeReportHolder.setPage( page );
         // 传入当前值对象
         employeeReportHolder.setObject( employeeReportVO );
         // 设置页面记录条数
         employeeReportHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeReportService.getEmployeeSalaryReportVOsByCondition( employeeReportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ), itemVOs );
         // 刷新Holder，国际化传值
         refreshHolder( employeeReportHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeReportHolder", employeeReportHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               employeeReportVO.setRt( "full" );
               arrayToRequest( request, employeeReportVO, "1" );
               request.setAttribute( "holderName", "employeeReportHolder" );
               request.setAttribute( "fileName", request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "员工薪资" : "Employee Salary" );
               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, true );
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table调用，直接传回Item JSP
               return mapping.findForward( "listEmployeeSalaryReportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      // 跳转JSP页面
      return mapping.findForward( "listEmployeeSalaryReport" );
   }

   public ActionForward list_dimission( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final EmployeeReportVO employeeReportVO = ( EmployeeReportVO ) form;
         // 初始化Service接口
         final EmployeeReportService employeeReportService = ( EmployeeReportService ) getService( "employeeReportService" );
         employeeReportVO.setEmployStatus( "1" );
         // 搜索内容需要解码。
         decodedObject( employeeReportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeReportHolder = new PagedListHolder();
         // 传入当前页
         employeeReportHolder.setPage( page );
         // 传入当前值对象
         employeeReportHolder.setObject( employeeReportVO );
         // 设置页面记录条数
         employeeReportHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeReportService.getEmployeeReportVOsByCondition( employeeReportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // 刷新Holder，国际化传值
         refreshHolder( employeeReportHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeReportHolder", employeeReportHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "employeeReportHolder" );
               request.setAttribute( "fileName", "离职员工报表" );
               request.setAttribute( "nameZHArray", employeeReportVO.getTitleNameList().split( "," ) );
               request.setAttribute( "nameSysArray", employeeReportVO.getTitleIdList().split( "," ) );
               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, true );
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table调用，直接传回Item JSP
               return mapping.findForward( "listEmployeeReportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listEmployeeReport" );
   }

   public ActionForward getContacts( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final EmployeeReportVO employeeReportVO = ( EmployeeReportVO ) form;
         // 初始化Service接口
         final EmployeeReportService employeeReportService = ( EmployeeReportService ) getService( "employeeReportService" );

         // 搜索内容需要解码。
         decodedObject( employeeReportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeReportHolder = new PagedListHolder();
         // 传入当前页
         employeeReportHolder.setPage( page );
         // 传入当前值对象
         employeeReportHolder.setObject( employeeReportVO );
         // 设置页面记录条数
         employeeReportHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeReportService.getContactsByCondition( employeeReportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // 刷新Holder，国际化传值
         refreshHolder( employeeReportHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeReportHolder", employeeReportHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "fileName", "contacts" );
               // 导出文件
               final String downloadType = request.getParameter( "downloadType" );
               if ( "wx".equalsIgnoreCase( downloadType ) )
               {
                  return new DownloadFileAction().exportWXContacts( mapping, form, request, response );
               }
               else
               {
                  return new DownloadFileAction().exportContacts( mapping, form, request, response );
               }
            }
            else
            {
               // Ajax Table调用，直接传回Item JSP
               return mapping.findForward( "listContactsTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listContacts" );
   }

   public ActionForward syncAllWXContact( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeReportService employeeReportService = ( EmployeeReportService ) getService( "employeeReportService" );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeReportHolder = new PagedListHolder();
         // 传入当前值对象
         final EmployeeReportVO employeeReportVO = new EmployeeReportVO();
         employeeReportVO.setAccountId( KANConstants.DEFAULT_ACCOUNTID );
         employeeReportHolder.setObject( employeeReportVO );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeReportService.getContactsByCondition( employeeReportHolder, false );
         String successids = "";
         String errorids = "";
         for ( int i = 0; i < employeeReportHolder.getSource().size(); i++ )
         {
            final EmployeeReportVO tmpVO = ( EmployeeReportVO ) employeeReportHolder.getSource().get( i );
            int result = BaseAction.syncWXContacts( tmpVO.getEmployeeId() );
            if ( result == 0 )
            {
               successids += tmpVO.getEmployeeId() + ",";
            }
            else
            {
               errorids += tmpVO.getEmployeeId() + ",";
            }
         }
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         StringBuffer sb = new StringBuffer();
         sb.append( "total:" + employeeReportHolder.getSource().size() + "\r\n" );
         sb.append( "error:[" + errorids + "]" + "\r\n" );
         sb.append( "success:[" + successids + "]" + "\r\n" );
         out.println( sb.toString() );
         out.flush();
         out.close();

      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return null;
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
