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

   // HR��ȡԱ����Ϣ
   public ActionForward object_full( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ���Action Form
         final IClickEmployeeReportView iClickEmployeeReportView = ( IClickEmployeeReportView ) form;
         // HR_Service��¼��IN_House��¼
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, iClickEmployeeReportView );
         }
         // ����Զ�����������
         iClickEmployeeReportView.setRemark1Set( generateDefineRemarkSet( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT, TableService.SOME_SPECIAL_VIEW_ID_MAP.get( "118" )[ 0 ] ) );
         iClickEmployeeReportView.setRemark2Set( generateDefineRemarkSet( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT, TableService.SOME_SPECIAL_VIEW_ID_MAP.get( "118" )[ 0 ] ) );
         // ���SubAction
         final String subAction = getSubAction( form );
         // ��ʼ��Service�ӿ�
         final IClickEmployeeReportService iClickEmployeeReportService = ( IClickEmployeeReportService ) getService( "iClickEmployeeReportService" );
         final List< MappingVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, response ) );
         iClickEmployeeReportView.setSalarys( itemVOs );
         // ����SubAction
         dealSubAction( iClickEmployeeReportView, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ������������ȣ���ôSubAction������Search Object
         if ( !isSearchFirst( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            // ���뵱ǰҳ
            pagedListHolder.setPage( page );
            // ���뵱ǰֵ����
            pagedListHolder.setObject( iClickEmployeeReportView );
            // ����ҳ���¼����
            pagedListHolder.setPageSize( getPageSize( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            iClickEmployeeReportService.getFullEmployeeReportViewsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( pagedListHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "useFixColumn", true );
         // ����Return
         return dealReturn( ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT, "listFullEmployeeReport", mapping, form, request, response );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // r4 �ط���Ƹ��ȡԱ����Ϣ ʵ������createBy
   public ActionForward object_full_r4( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ���Action Form
         final IClickEmployeeReportView iClickEmployeeReportView = ( IClickEmployeeReportView ) form;
         // HR_Service��¼��IN_House��¼
         iClickEmployeeReportView.setCreateBy( getUserId( request, null ) );
         // ����Զ�����������
         iClickEmployeeReportView.setRemark1Set( generateDefineRemarkSet( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4, TableService.SOME_SPECIAL_VIEW_ID_MAP.get( "119" )[ 0 ] ) );
         iClickEmployeeReportView.setRemark2Set( generateDefineRemarkSet( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4, TableService.SOME_SPECIAL_VIEW_ID_MAP.get( "119" )[ 1 ] ) );
         iClickEmployeeReportView.setRemark1( generateDefineListSearches( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4 ) );
         // ���SubAction
         final String subAction = getSubAction( form );
         // ��ʼ��Service�ӿ�
         final IClickEmployeeReportService iClickEmployeeReportService = ( IClickEmployeeReportService ) getService( "iClickEmployeeReportService" );
         final List< MappingVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, response ) );
         iClickEmployeeReportView.setSalarys( itemVOs );
         // ����SubAction
         dealSubAction( iClickEmployeeReportView, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ������������ȣ���ôSubAction������Search Object
         if ( !isSearchFirst( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4 ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            // ���뵱ǰҳ
            pagedListHolder.setPage( page );
            // ���뵱ǰֵ����
            pagedListHolder.setObject( iClickEmployeeReportView );
            // ����ҳ���¼����
            pagedListHolder.setPageSize( getPageSize( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4 ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            iClickEmployeeReportService.getFullEmployeeReportViewsByCondition_r4( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4 ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( pagedListHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "useFixColumn", true );
         // ����Return
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

         // ���ͻ��˴����ļ���
         // ��ʼ��OutputStream
         final OutputStream os = response.getOutputStream();

         // ���÷����ļ�����
         response.setContentType( "application/x-msdownload" );

         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "11.xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) );

         // Excel�ļ�д��OutputStream
         generatePerformanceReport.write( os );

         // ���OutputStream
         os.flush();
         //�ر���  
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
