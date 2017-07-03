package com.kan.hro.web.actions.biz.attendance;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceBatchService;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceHeaderService;

public class TimesheetAllowanceHeaderAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public static String accessAction = "HRO_BIZ_TIMESHEET_HEADER";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ȡ����ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ��ʼ��Service�ӿ�
         final TimesheetAllowanceHeaderService timesheetHeaderService = ( TimesheetAllowanceHeaderService ) getService( "timesheetAllowanceHeaderService" );
         final TimesheetAllowanceBatchService timesheetBatchService = ( TimesheetAllowanceBatchService ) getService( "timesheetAllowanceBatchService" );

         // ���Action Form
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;

         // ���û��ָ��������Ĭ�ϰ� monthly����
         if ( timesheetHeaderVO.getSortColumn() == null || timesheetHeaderVO.getSortColumn().isEmpty() )
         {
            timesheetHeaderVO.setSortOrder( "desc" );
            timesheetHeaderVO.setSortColumn( "a.headerId" );
         }
         
         // ����subAction
         dealSubAction( timesheetHeaderVO, mapping, form, request, response );

         timesheetHeaderVO.setBatchId( batchId );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( timesheetHeaderVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         timesheetHeaderService.getTimesheetHeaderVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "timesheetHeaderHolder", pagedListHolder );
         //����״̬
         request.setAttribute("batchStatus", request.getParameter("batchStatus"));
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listTimesheetAllowanceHeaderTable" );
         }
         
         // ��ȡTimesheetBatchVO
         final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByBatchId( batchId );
         // д��request
         request.setAttribute( "timesheetBatchForm", timesheetBatchVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listTimesheetAllowanceHeader" );
   }

   public void updateAllowanceBase( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      // Config the response
      response.setContentType( "text/html" );
      response.setCharacterEncoding( "UTF-8" );
      // ��ʼ��PrintWrite����
      PrintWriter out = null;
      try
      {
         out = response.getWriter();
         final TimesheetAllowanceHeaderService timesheetHeaderService = ( TimesheetAllowanceHeaderService ) getService( "timesheetAllowanceHeaderService" );
         String allowanceId = request.getParameter( "allowanceId" );
         String base = request.getParameter( "base" );
         timesheetHeaderService.updateAllowanceBase( allowanceId, base );
         addSuccessAjax( out, null );
         out.flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
         addFailedAjax( out, null );
      }
      finally
      {
         if ( out != null )
         {
            out.close();
         }
      }
   }

   /**
    * �˻�,����ɾ��temp��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward back_header( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;

         // ��ʼ��Service�ӿ�
         final TimesheetAllowanceHeaderService timesheetAllowanceHeaderService = ( TimesheetAllowanceHeaderService ) getService( "timesheetAllowanceHeaderService" );

         // ��ù�ѡID
         final String allowanceIds = timesheetHeaderVO.getSelectedIds();

         int count = 0;

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( allowanceIds ) != null )
         {
            // �ָ�ѡ����
            final String[] allowanceIdsArray = allowanceIds.split( "," );

            //����ɾ�����ڽ�����ʱ��
            int[] returnValue = timesheetAllowanceHeaderService.backTimeSheetAllowanceTemp( allowanceIdsArray, timesheetHeaderVO.getBatchId() );
            count = returnValue[ 1 ];
         }

         // ���Selected IDs����Action
         timesheetHeaderVO.setSelectedIds( "" );
         timesheetHeaderVO.setSubAction( "" );
         if ( count == 0 )
         {

            return new ActionForward( "timesheetAllowanceBatchAction.do?proc=list_object", true );

         }
         else
         {
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

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
