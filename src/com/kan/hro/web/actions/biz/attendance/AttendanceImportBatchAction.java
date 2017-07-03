package com.kan.hro.web.actions.biz.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.service.inf.biz.attendance.AttendanceImportBatchService;

public class AttendanceImportBatchAction extends BaseAction
{
   // ģ���ʶ
   public static final String ACCESS_ACCTION = "HRO_BIZ_ATTENDANCE_IMPORT_BATCH";

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
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         // ���Action Form
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;
         commonBatchVO.setAccessAction( ACCESS_ACCTION );

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( commonBatchVO.getSortColumn() == null || commonBatchVO.getSortColumn().isEmpty() )
         {
            commonBatchVO.setSortOrder( "desc" );
            commonBatchVO.setSortColumn( "batchId" );
         }

         // ����subAction
         dealSubAction( commonBatchVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( commonBatchVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         commonBatchService.getCommonBatchVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "attendanceImportBatchHolder", pagedListHolder );

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listAttendanceImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listAttendanceImportBatch" );
   }

   // �ύ
   public ActionForward submit_objects( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;
         // ��ʼ��Service�ӿ�
         final AttendanceImportBatchService attendanceImportBatchService = ( AttendanceImportBatchService ) getService( "attendanceImportBatchService" );
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );

         int batchCount = 0;
         int batchNum = 0;
         int timesheetNum = 0;
         final List< String > errorBatchIds = new ArrayList< String >();
         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( commonBatchVO.getSelectedIds() ) != null )
         {
            batchCount = commonBatchVO.getSelectedIds().split( "," ).length;
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempCommonBatchVO.setModifyBy( getUserId( request, null ) );
               tempCommonBatchVO.setModifyDate( new Date() );
               int rows = attendanceImportBatchService.submitObject( tempCommonBatchVO );
               if ( rows != -1 )
               {
                  batchNum++;
                  timesheetNum = timesheetNum + rows;
               }
               else
               {
                  errorBatchIds.add( tempCommonBatchVO.getBatchId() );
               }
            }
         }

         if ( batchCount == batchNum )
         {
            success( request, null, "�ύ�ɹ����ܹ������� " + timesheetNum + " ���������ݣ�" );
         }
         else
         {
            warning( request, null, "ע�⣬���ύ������ID��" + KANUtil.stringListToJasonArray( errorBatchIds, "��" ).replace( "{", "" ).replace( "}", "" ) + " δ�ɹ�������ԭ�����������ڲ�������" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   // �˻�
   public ActionForward rollback_objects( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;
         // ��ʼ��Service�ӿ�
         final AttendanceImportBatchService attendanceImportBatchService = ( AttendanceImportBatchService ) getService( "attendanceImportBatchService" );
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( commonBatchVO.getSelectedIds() ) != null )
         {
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempCommonBatchVO.setModifyBy( getUserId( request, null ) );
               tempCommonBatchVO.setModifyDate( new Date() );
               attendanceImportBatchService.rollbackObject( tempCommonBatchVO );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
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
