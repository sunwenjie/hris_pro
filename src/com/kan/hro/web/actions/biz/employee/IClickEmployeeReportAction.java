package com.kan.hro.web.actions.biz.employee;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TableService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.IClickEmployeeReportView;
import com.kan.hro.service.inf.biz.employee.IClickEmployeeReportService;

public class IClickEmployeeReportAction extends BaseAction
{
   public static final String ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT = "ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT";

   public static final String ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4 = "ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4";

   // HR获取员工信息
   public ActionForward object_full( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得Action Form
         final IClickEmployeeReportView iClickEmployeeReportView = ( IClickEmployeeReportView ) form;
         // HR_Service登录、IN_House登录
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, iClickEmployeeReportView );
         }
         // 添加自定义搜索内容
         iClickEmployeeReportView.setRemark1Set( generateDefineRemarkSet( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT, TableService.SOME_SPECIAL_VIEW_ID_MAP.get( "118" )[ 0 ] ) );
         iClickEmployeeReportView.setRemark2Set( generateDefineRemarkSet( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT, TableService.SOME_SPECIAL_VIEW_ID_MAP.get( "118" )[ 0 ] ) );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 初始化Service接口
         final IClickEmployeeReportService iClickEmployeeReportService = ( IClickEmployeeReportService ) getService( "iClickEmployeeReportService" );
         final List< MappingVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, response ) );
         iClickEmployeeReportView.setSalarys( itemVOs );
         // 处理SubAction
         dealSubAction( iClickEmployeeReportView, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 如果是搜索优先，那么SubAction必须是Search Object
         if ( !isSearchFirst( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            // 传入当前页
            pagedListHolder.setPage( page );
            // 传入当前值对象
            pagedListHolder.setObject( iClickEmployeeReportView );
            // 设置页面记录条数
            pagedListHolder.setPageSize( getPageSize( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            iClickEmployeeReportService.getFullEmployeeReportViewsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT ) );
            // 刷新Holder，国际化传值
            refreshHolder( pagedListHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "useFixColumn", true );
         // 处理Return
         return dealReturn( ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT, "listFullEmployeeReport", mapping, form, request, response );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // r4 地方招聘获取员工信息 实际上是createBy
   public ActionForward object_full_r4( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得Action Form
         final IClickEmployeeReportView iClickEmployeeReportView = ( IClickEmployeeReportView ) form;
         // HR_Service登录、IN_House登录
         iClickEmployeeReportView.setCreateBy( getUserId( request, null ) );
         // 添加自定义搜索内容
         iClickEmployeeReportView.setRemark1Set( generateDefineRemarkSet( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4, TableService.SOME_SPECIAL_VIEW_ID_MAP.get( "119" )[ 0 ] ) );
         iClickEmployeeReportView.setRemark2Set( generateDefineRemarkSet( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4, TableService.SOME_SPECIAL_VIEW_ID_MAP.get( "119" )[ 1 ] ) );
         iClickEmployeeReportView.setRemark1( generateDefineListSearches( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4 ) );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 初始化Service接口
         final IClickEmployeeReportService iClickEmployeeReportService = ( IClickEmployeeReportService ) getService( "iClickEmployeeReportService" );
         final List< MappingVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, response ) );
         iClickEmployeeReportView.setSalarys( itemVOs );
         // 处理SubAction
         dealSubAction( iClickEmployeeReportView, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 如果是搜索优先，那么SubAction必须是Search Object
         if ( !isSearchFirst( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4 ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            // 传入当前页
            pagedListHolder.setPage( page );
            // 传入当前值对象
            pagedListHolder.setObject( iClickEmployeeReportView );
            // 设置页面记录条数
            pagedListHolder.setPageSize( getPageSize( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4 ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            iClickEmployeeReportService.getFullEmployeeReportViewsByCondition_r4( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4 ) );
            // 刷新Holder，国际化传值
            refreshHolder( pagedListHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "useFixColumn", true );
         // 处理Return
         return dealReturn( ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4, "listFullEmployeeReport_r4", mapping, form, request, response );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward test( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException, IOException
   {
      final IClickEmployeeReportService iClickEmployeeReportService = ( IClickEmployeeReportService ) getService( "iClickEmployeeReportService" );
      try
      {
         XSSFWorkbook generatePerformanceReport = iClickEmployeeReportService.generatePerformanceReport( null );

         // 往客户端传送文件流
         // 初始化OutputStream
         final OutputStream os = response.getOutputStream();

         // 设置返回文件下载
         response.setContentType( "application/x-msdownload" );

         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "11.xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) );

         // Excel文件写入OutputStream
         generatePerformanceReport.write( os );

         // 输出OutputStream
         os.flush();
         //关闭流  
         os.close();

      }
      catch ( KANException e )
      {
         throw new KANException( e );
      }

      return null;
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
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
