package com.kan.hro.web.actions.biz.attendance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetBatchService;

public class TimesheetBatchAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action - In House
   public static String accessActionInHouse = "HRO_BIZ_ATTENDANCE_TIMESHEET_BATCH";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );

         // ���Action Form
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;

         //��������Ȩ��
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessActionInHouse, timesheetBatchVO );
            setDataAuth( request, response, timesheetBatchVO );
         }

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( timesheetBatchVO.getSortColumn() == null || timesheetBatchVO.getSortColumn().isEmpty() )
         {
            timesheetBatchVO.setSortOrder( "desc" );
            timesheetBatchVO.setSortColumn( "batchId" );
         }

         // �����inHouse
         //         if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         //         {
         //            // �������HRְ��
         //            if ( !isHRFunction( request, response ) )
         //            {
         //               // ��ʼ��TimesheetHeaderVO
         //               final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
         //               timesheetHeaderVO.setAccountId( getAccountId( request, null ) );
         //               timesheetHeaderVO.setCorpId( getCorpId( request, null ) );
         //               timesheetHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         //
         //               return new TimesheetHeaderAction().list_object( mapping, timesheetHeaderVO, request, response );
         //            }
         //         }

         // ����subAction
         dealSubAction( timesheetBatchVO, mapping, form, request, response );

         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            timesheetBatchVO.setClientId( BaseAction.getClientId( request, response ) );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( timesheetBatchVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         timesheetBatchService.getTimesheetBatchVOsByCondition( pagedListHolder, true );

         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "timesheetBatchHolder", pagedListHolder );

         // �����In House��¼��������������
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listTimesheetBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listTimesheetBatch" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // �����In House��¼��������������
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         passClientOrders( request, response );
      }

      return mapping.findForward( "generateTimesheetBatch" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );

            // ��ȡActionForm
            final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
            timesheetBatchVO.setOrderId( KANUtil.filterEmpty( timesheetBatchVO.getOrderId(), "0" ) );
            timesheetBatchVO.setCreateBy( getUserId( request, null ) );
            timesheetBatchVO.setModifyBy( getUserId( request, null ) );
            timesheetBatchVO.setStatus( BaseVO.TRUE );

            //��������Ȩ��
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), TimesheetBatchAction.accessActionInHouse, timesheetBatchVO );
            setDataAuth( request, response, timesheetBatchVO );

            // ����Timesheet
            final int rows = timesheetBatchService.generateTimesheet( timesheetBatchVO );

            if ( rows > 0 )
            {
               // ������ӳɹ����
               success( request, null, "�ɹ����� " + rows + " �����Σ�" );
               insertlog( request, timesheetBatchVO, Operate.ADD, timesheetBatchVO.getBatchId(), "�����������ڱ�" );
            }
            else
            {
               // ���ؾ�����
               warning( request, null, "����δ������û�з������������ݣ�" );
            }
         }
         else
         {
            // ����ʧ�ܱ��
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // ���Form����
         ( ( TimesheetBatchVO ) form ).reset();
         ( ( TimesheetBatchVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ����Return
      return list_object( mapping, form, request, response );
   }

   // �ύ����
   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );

         // ��ù�ѡID
         final String batchIds = timesheetBatchVO.getSelectedIds();

         // ���ڹ�ѡ����ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String batchSelectId : selectedIdArray )
            {
               final TimesheetBatchVO submitObject = timesheetBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( batchSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );
               rows = rows + timesheetBatchService.submit_batch( submitObject );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }
            insertlog( request, timesheetBatchVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( timesheetBatchVO.getSelectedIds() ) );
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
