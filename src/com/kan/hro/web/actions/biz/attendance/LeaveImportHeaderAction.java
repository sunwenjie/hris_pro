package com.kan.hro.web.actions.biz.attendance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.LeaveImportBatchService;
import com.kan.hro.service.inf.biz.attendance.LeaveImportHeaderService;

public class LeaveImportHeaderAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public static String accessAction = "HRO_BIZ_ATTENDANCE_LEAVE_HEADER";

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
         final LeaveImportHeaderService timesheetHeaderService = ( LeaveImportHeaderService ) getService( "leaveImportHeaderService" );
         final LeaveImportBatchService timesheetBatchService = ( LeaveImportBatchService ) getService( "leaveImportBatchService" );

         // ��ȡTimesheetBatchVO
         final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByBatchId( batchId );
         // д��request
         request.setAttribute( "timesheetBatchForm", timesheetBatchVO );

         // ���Action Form
         final LeaveImportHeaderVO leaveImportHeaderVO = ( LeaveImportHeaderVO ) form;
         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), LeaveImportBatchAction.accessAction, leaveImportHeaderVO );
         setDataAuth( request, response, leaveImportHeaderVO );

         leaveImportHeaderVO.setBatchId( timesheetBatchVO.getBatchId() );
         // ���û��ָ��������Ĭ�ϰ� monthly����
         if ( leaveImportHeaderVO.getSortColumn() == null || leaveImportHeaderVO.getSortColumn().isEmpty() )
         {
            leaveImportHeaderVO.setSortOrder( "desc" );
            leaveImportHeaderVO.setSortColumn( "a.leaveheaderId" );
         }

         // �趨�鿴����Ȩ��
         setHRFunctionRole( mapping, form, request, response );

         // ����subAction
         dealSubAction( leaveImportHeaderVO, mapping, form, request, response );

         leaveImportHeaderVO.setBatchId( batchId );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( leaveImportHeaderVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );

         //�ж��Ƿ�ȫѡ��
         if ( "1".equals( request.getParameter( "selected" ) ) )
         {
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            timesheetHeaderService.getLeaveImportHeaderVOsByCondition( pagedListHolder, false );
            String selectids = "";

            if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object pageObject : pagedListHolder.getSource() )
               {
                  selectids = selectids + ( ( LeaveImportHeaderVO ) pageObject ).getEncodedId() + ",";
               }
            }

            leaveImportHeaderVO.setSelectedIds( selectids );
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         timesheetHeaderService.getLeaveImportHeaderVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "leaveImportHeaderHolder", pagedListHolder );
         //����״̬
         request.setAttribute( "batchStatus", request.getParameter( "batchStatus" ) );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            // �趨�鿴����Ȩ��
            setHRFunctionRole( mapping, form, request, response );
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "leaveImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "leaveImportHeader" );
   }

   // Refresh the page holder for message resource
   public void refreshHolder( final PagedListHolder userHolder, final HttpServletRequest request )
   {
      if ( userHolder != null && userHolder.getSource() != null && userHolder.getSource().size() > 0 )
      {
         for ( Object pageObject : userHolder.getSource() )
         {
            ( ( ActionForm ) pageObject ).reset( null, request );
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
   public ActionForward back_heard( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         int rows = -1;
         // ��ȡActionForm
         final LeaveImportHeaderVO leaveImportHeaderVO = ( LeaveImportHeaderVO ) form;
         leaveImportHeaderVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final LeaveImportHeaderService leaveImportHeaderService = ( LeaveImportHeaderService ) getService( "leaveImportHeaderService" );

         // ��ù�ѡID
         final String selectedIds = leaveImportHeaderVO.getSelectedIds();
         leaveImportHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( leaveImportHeaderVO.getBatchId() ) );
         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( selectedIds ) != null )
         {
            rows = leaveImportHeaderService.backHeader( leaveImportHeaderVO );

            if ( rows < 0 )
            {
               error( request, MESSAGE_TYPE_DELETE, "���˻ؼ�¼!" );
            }
            else
            {
               success( request, MESSAGE_TYPE_ROLLBACK );
               insertlog( request, leaveImportHeaderVO, Operate.ROllBACK, null, KANUtil.decodeSelectedIds( selectedIds ) );
            }
         }

         if ( rows == 0 )
         {
            TimesheetBatchVO timesheetBatchVO = new TimesheetBatchVO();
            timesheetBatchVO.reset( mapping, request );
            return new LeaveImportBatchAction().list_object( mapping, timesheetBatchVO, request, response );
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
