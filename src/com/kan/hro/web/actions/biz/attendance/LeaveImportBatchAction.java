package com.kan.hro.web.actions.biz.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.LeaveImportBatchService;
import com.kan.hro.service.inf.biz.attendance.LeaveImportHeaderService;

public class LeaveImportBatchAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action - In House
   public static String accessAction = "HRO_BIZ_ATTENDANCE_LEAVE_BATCH_IMPORT";

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
         final LeaveImportBatchService timesheetBatchService = ( LeaveImportBatchService ) getService( "leaveImportBatchService" );

         // ���Action Form
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         timesheetBatchVO.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.import.batch.statuses" ) );

         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, timesheetBatchVO );
         setDataAuth( request, response, timesheetBatchVO );

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( timesheetBatchVO.getSortColumn() == null || timesheetBatchVO.getSortColumn().isEmpty() )
         {
            timesheetBatchVO.setSortOrder( "desc" );
            timesheetBatchVO.setSortColumn( "batchId" );
         }

         // ����subAction
         dealSubAction( timesheetBatchVO, mapping, form, request, response );

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
            return mapping.findForward( "leaveImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "leaveImportBatch" );
   }

   /**
    * ����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final LeaveImportBatchService leaveImportBatchService = ( LeaveImportBatchService ) getService( "leaveImportBatchService" );
         final LeaveImportHeaderService leaveImportHeaderService = ( LeaveImportHeaderService ) getService( "leaveImportHeaderService" );

         // ��ù�ѡID
         final String batchIds = timesheetBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            // �ռ�������ʱ���ص���batchId
            final List< String > overlapBatchIds = new ArrayList< String >();

            // �ռ�����ʵ���д���ʱ���ͻ��leaveHeaderId
            final Map< String, List< String > > overlapBatchIdMap = new HashMap< String, List< String > >();

            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               final TimesheetBatchVO submitObject = leaveImportBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               final List< String > leaveHeaderIds = new ArrayList< String >();
               if ( !check_batch( leaveImportHeaderService, submitObject ) )
               {
                  leaveHeaderIds.addAll( leaveImportBatchService.updateBatch( submitObject ) );
               }
               else
               {
                  overlapBatchIds.add( submitObject.getBatchId() );
               }

               if ( leaveHeaderIds != null && leaveHeaderIds.size() > 0 )
               {
                  overlapBatchIdMap.put( submitObject.getBatchId(), leaveHeaderIds );
               }
            }

            // �����ͻ��˴�����Ϣ
            final StringBuilder errorMsg = new StringBuilder();

            // �����б���ʱ���ͻ
            if ( overlapBatchIds.size() > 0 )
            {
               String overlapBatchIds_str = KANUtil.stringListToJasonArray( overlapBatchIds, ", " );
               String message = KANUtil.getProperty( request.getLocale(), "error.leave.import.submit.time.overlap1" );
               errorMsg.append( message.replace( "{0}", overlapBatchIds_str ) );
               errorMsg.append( "<br/>" );
            }

            // ��������ʵ�ʱ�ʱ���ͻ
            if ( overlapBatchIdMap != null && overlapBatchIdMap.size() > 0 )
            {
               for ( String overlapBatchIdKey : overlapBatchIdMap.keySet() )
               {
                  String overlapLeaveHeaderIds_str = KANUtil.stringListToJasonArray( overlapBatchIdMap.get( overlapBatchIdKey ), ", " );
                  String message = KANUtil.getProperty( request.getLocale(), "error.leave.import.submit.time.overlap2" );
                  errorMsg.append( message.replace( "{0}", overlapBatchIdKey ).replace( "{1}", overlapLeaveHeaderIds_str ) );
                  errorMsg.append( "<br/>" );
               }
            }

            if ( KANUtil.filterEmpty( errorMsg.toString() ) != null )
            {
               error( request, null, errorMsg.toString() );
            }
            else
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, timesheetBatchVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( batchIds ) );
            }
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
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
   public ActionForward back_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final LeaveImportBatchService leaveImportBatchService = ( LeaveImportBatchService ) getService( "leaveImportBatchService" );

         // ��ù�ѡID
         final String batchIds = timesheetBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = -1;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               final TimesheetBatchVO submitObject = leaveImportBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + leaveImportBatchService.backBatch( submitObject );
            }
            if ( rows < 0 )
            {
               error( request, MESSAGE_TYPE_DELETE, "�˻�ʧ��!" );
            }
            else
            {
               success( request, MESSAGE_TYPE_ROLLBACK );
               insertlog( request, timesheetBatchVO, Operate.ROllBACK, null, KANUtil.decodeSelectedIds( batchIds ) );
            }
         }
         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // ���Լ��Batch��Header��������ʱ���ص�
   // Add by siuvan@2014-09-07
   private boolean check_batch( final LeaveImportHeaderService leaveImportHeaderService, final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      boolean flag = false;

      final List< Object > leaveImportHeaderVOs = leaveImportHeaderService.getLeaveImportHeaderVOsByBatchId( timesheetBatchVO.getBatchId() );

      if ( leaveImportHeaderVOs != null && leaveImportHeaderVOs.size() > 1 )
      {
         for ( Object leaveImportHeaderVOObject1 : leaveImportHeaderVOs )
         {
            final LeaveImportHeaderVO tempLeaveImportHeaderVO1 = ( LeaveImportHeaderVO ) leaveImportHeaderVOObject1;

            for ( Object leaveImportHeaderVOObject2 : leaveImportHeaderVOs )
            {
               final LeaveImportHeaderVO tempLeaveImportHeaderVO2 = ( LeaveImportHeaderVO ) leaveImportHeaderVOObject2;

               if ( tempLeaveImportHeaderVO1.getLeaveHeaderId().equals( tempLeaveImportHeaderVO2.getLeaveHeaderId() )
                     || !tempLeaveImportHeaderVO1.getContractId().equals( tempLeaveImportHeaderVO2.getContractId() ) )
               {
                  continue;
               }

               // �����ǰ���ʱ�����ԭ�м�¼����
               if ( KANUtil.createDate( tempLeaveImportHeaderVO1.getEstimateStartDate() ).getTime() >= KANUtil.createDate( tempLeaveImportHeaderVO2.getEstimateEndDate() ).getTime()
                     || KANUtil.createDate( tempLeaveImportHeaderVO1.getEstimateEndDate() ).getTime() <= KANUtil.createDate( tempLeaveImportHeaderVO2.getEstimateStartDate() ).getTime() )
                  flag = false;
               else
                  flag = true;
               break;
            }

            if ( flag )
               break;
         }
      }

      return flag;
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
