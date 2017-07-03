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

   // ��ٱ��� - accessAction
   public final static String ACCESS_ACTION_ANNUAL_LEAVE = "HRO_BIZ_ATTENDANCE_ANNUAL_LEAVE_REPORT";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final EmployeeContractLeaveReportVO employeeContractLeaveReportVO = ( EmployeeContractLeaveReportVO ) form;

         // HR_Service��¼��IN_House��¼
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, employeeContractLeaveReportVO );
         }
         // Ա����¼
         else if ( KANConstants.ROLE_EMPLOYEE.equals( BaseAction.getRole( request, response ) ) )
         {
            employeeContractLeaveReportVO.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
         }

         // ��ʼ��Service�ӿ�
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         // ����������Ҫ���롣
         decodedObject( employeeContractLeaveReportVO );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder employeeContractLeaveReportHolder = new PagedListHolder();
         // ���뵱ǰҳ
         employeeContractLeaveReportHolder.setPage( page );
         // ���뵱ǰֵ����
         setDataAuth( request, response, employeeContractLeaveReportVO );
         employeeContractLeaveReportHolder.setObject( employeeContractLeaveReportVO );
         // ����ҳ���¼����
         employeeContractLeaveReportHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractLeaveService.getEmployeeContractLeaveReportVOsByCondition( employeeContractLeaveReportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
               : true ) );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( employeeContractLeaveReportHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeContractLeaveReportHolder", employeeContractLeaveReportHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               final XSSFWorkbook workbook = employeeContractLeaveService.exportEmployeeContractLeaveReport( getTitleMapping( request, response ), employeeContractLeaveReportHolder, request.getLocale().getLanguage() );
               // �����ļ�
               new DownloadFileAction().download( response, workbook, ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "�ݼ��������.xlsx" : "Leave Detail Report.xlsx" ) );
               return null;
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table���ã�ֱ�Ӵ���Item JSP
               return mapping.findForward( "listEmployeeContractLeaveReportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listEmployeeContractLeaveReport" );
   }

   /***
    * ����ܶ��
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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final EmployeeContractLeaveReportVO employeeContractLeaveReportVO = ( EmployeeContractLeaveReportVO ) form;
         employeeContractLeaveReportVO.setCurrYear( KANUtil.formatDate( new Date(), "yyyy" ) );
         setDataAuth( request, response, employeeContractLeaveReportVO );
         // ��ʼ��Service�ӿ�
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         // ����������Ҫ���롣
         decodedObject( employeeContractLeaveReportVO );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder annualLeaveDetailsHolder = new PagedListHolder();
         // ���뵱ǰҳ
         annualLeaveDetailsHolder.setPage( page );
         // ���뵱ǰֵ����
         setDataAuth( request, response, employeeContractLeaveReportVO );
         annualLeaveDetailsHolder.setObject( employeeContractLeaveReportVO );
         // ����ҳ���¼����
         annualLeaveDetailsHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractLeaveService.getAnnualLeaveDetailsByCondition( annualLeaveDetailsHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( annualLeaveDetailsHolder, request );
         // Holder��д��Request����
         request.setAttribute( "annualLeaveDetailsHolder", annualLeaveDetailsHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               final XSSFWorkbook workbook = employeeContractLeaveService.exportAnnualLeaveDetails( getAnnualLeaveReportTitleMapping( request, response ), annualLeaveDetailsHolder, request.getLocale() );
               // �����ļ�
               new DownloadFileAction().download( response, workbook, ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "����������.xlsx"
                     : "Annual Leave Detail Report.xlsx" ) );
               return null;
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table���ã�ֱ�Ӵ���Item JSP
               return mapping.findForward( "listAnnualLeaveDetailsTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
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
