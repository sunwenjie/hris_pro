package com.kan.hro.web.actions.biz.attendance;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.SecurityAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.OTDetailService;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderOTService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

public class OTHeaderAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public static String accessAction = "HRO_BIZ_ATTENDANCE_OT_HEADER";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ȡsubAction
         final String subAction = getSubAction( form );
         // ��ʼ��Service�ӿ�
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         // ���Action Form
         final OTHeaderVO otHeaderVO = ( OTHeaderVO ) form;
         // TODO ��ʱ���⴦��
         if ( KANUtil.filterEmpty( otHeaderVO.getNotFormatEstimateStartDate() ) != null )
         {
            otHeaderVO.setEstimateStartDate( URLDecoder.decode( URLDecoder.decode( ( String ) otHeaderVO.getNotFormatEstimateStartDate(), "UTF-8" ), "UTF-8" ) );
         }
         if ( KANUtil.filterEmpty( otHeaderVO.getNotFormatEstimateEndDate() ) != null )
         {
            otHeaderVO.setEstimateEndDate( URLDecoder.decode( URLDecoder.decode( ( String ) otHeaderVO.getNotFormatEstimateEndDate(), "UTF-8" ), "UTF-8" ) );
         }
         if ( new Boolean( ajax ) )
         {
            decodedObject( otHeaderVO );
         }

         //��������Ȩ��
         setDataAuth( request, response, otHeaderVO );

         // ���û��ָ��������Ĭ�ϰ� LeaveId����
         if ( otHeaderVO.getSortColumn() == null || otHeaderVO.getSortColumn().isEmpty() )
         {
            otHeaderVO.setSortColumn( "otHeaderId" );
            otHeaderVO.setSortOrder( "desc" );
         }

         // ����ɾ������
         if ( otHeaderVO.getSubAction() != null && otHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( otHeaderVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( otHeaderVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         otHeaderService.getOTHeaderVOsByCondition( pagedListHolder, ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "otHeaderHolder", pagedListHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               pagedListHolder.setSource( otHeaderService.exportOTDetailByCondition( otHeaderVO ) );
               // ˢ�¹��ʻ�
               refreshHolder( pagedListHolder, request );
               request.setAttribute( "holderName", "otHeaderHolder" );
               request.setAttribute( "fileName", "�Ӱ�" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );

               // �����ļ�
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listOTTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listOT" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // �����ظ��ύ
      this.saveToken( request );

      // InHouse����£�Ĭ�ϵ�ǰ��¼�û��Ӱ�
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // ��ʼ��Service�ӿ�
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( OTHeaderVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
      }
      else if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
      {
         ( ( OTHeaderVO ) form ).setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
      }

      // ����Sub Action
      ( ( OTHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( OTHeaderVO ) form ).setStatus( OTHeaderVO.TRUE );

      ( ( OTHeaderVO ) form ).setSpecialOT( "2" );

      return mapping.findForward( "manageOT" );
   }

   public ActionForward to_objectNew_for_timesheet( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String employeeId = request.getParameter( "employeeId" );
         final String clientId = request.getParameter( "clientId" );
         final String contractId = request.getParameter( "contractId" );
         final String currDay = request.getParameter( "currDay" );
         final String timesheetId = request.getParameter( "timesheetId" );
         ( ( OTHeaderVO ) form ).setTimesheetId( timesheetId );
         ( ( OTHeaderVO ) form ).setEmployeeId( employeeId );
         ( ( OTHeaderVO ) form ).setClientId( clientId );
         ( ( OTHeaderVO ) form ).setCorpId( getCorpId( request, response ) );
         ( ( OTHeaderVO ) form ).setContractId( contractId );
         ( ( OTHeaderVO ) form ).setSubAction( CREATE_OBJECT );
         ( ( OTHeaderVO ) form ).setStatus( OTHeaderVO.TRUE );
         ( ( OTHeaderVO ) form ).setItemId( null );
         request.setAttribute( "currDay", currDay + " 00:00" );
         request.setAttribute( "nextDay", KANUtil.formatDate( KANUtil.getDate( currDay, 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageOT" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

            // ��ȡ��ǰFORM
            final OTHeaderVO otHeaderVO = ( OTHeaderVO ) form;
            otHeaderVO.setDataFrom( "1" );

            // ����ҳ��employeeId
            final String employeeId = otHeaderVO.getEmployeeId();
            // ���ڱ��浽passObject Ա����Ϣ
            final EmployeeVO tempEmployeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
            if ( tempEmployeeVO != null )
            {
               otHeaderVO.setEmployeeNameZH( tempEmployeeVO.getNameZH() );
               otHeaderVO.setEmployeeNameEN( tempEmployeeVO.getNameEN() );
               otHeaderVO.setEmployeeNo( tempEmployeeVO.getEmployeeNo() );
               otHeaderVO.setCertificateNumber( tempEmployeeVO.getCertificateNumber() );
            }

            // У��employeeId�Ƿ����
            checkEmployeeId( mapping, otHeaderVO, request, response );

            // ���Ϸ���employeeId��ת����ҳ��
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "employeeIdErrorMsg" ) ) != null )
            {
               otHeaderVO.reset();
               otHeaderVO.setEmployeeId( employeeId );
               otHeaderVO.setEmployeeNameZH( "" );
               otHeaderVO.setEmployeeNameEN( "" );
               return to_objectNew( mapping, otHeaderVO, request, response );
            }

            if ( KANUtil.filterEmpty( otHeaderVO.getClientId() ) == null )
            {
               final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( otHeaderVO.getContractId() );

               if ( employeeContractVO != null )
               {
                  otHeaderVO.setClientId( employeeContractVO.getClientId() );
               }
            }

            // �趨��ǰ�û�
            otHeaderVO.setAccountId( getAccountId( request, response ) );
            otHeaderVO.setCreateBy( getUserId( request, response ) );
            otHeaderVO.setModifyBy( getUserId( request, response ) );

            if ( KANUtil.filterEmpty( otHeaderVO.getEstimateHours() ) != null && Double.valueOf( otHeaderVO.getEstimateHours() ) == 0 )
            {
               warning( request, null, "����δ��������Ч�ļӰ�Сʱ����" );
            }
            else
            {
               // ������ӷ���
               if ( otHeaderService.insertOTHeader( otHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, otHeaderVO, Operate.SUBMIT, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_ADD );
                  insertlog( request, otHeaderVO, Operate.ADD, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), null );
               }
            }

            fetchOTHoursDetail( otHeaderVO, mapping, form, request, response );
         }
         // ���FORM
         ( ( OTHeaderVO ) form ).reset();
         ( ( OTHeaderVO ) form ).setEmployeeNameZH( "" );
         ( ( OTHeaderVO ) form ).setEmployeeNameEN( "" );
         ( ( OTHeaderVO ) form ).setNumber( "" );
         ( ( OTHeaderVO ) form ).setCertificateNumber( "" );
         ( ( OTHeaderVO ) form ).setEmployeeNo( "" );
         final String from = request.getParameter( "mobile" );
         if ( KANUtil.filterEmpty( from ) != null )
         {
            return new SecurityAction().index_mobile( mapping, new UserVO(), request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת�б����
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �趨�Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         // ������ȡ�����
         final String otHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
         // ��ȡOTHeaderVO����
         final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderId );
         if ( KANUtil.filterEmpty( otHeaderVO.getActualStartDate() ) != null && KANUtil.filterEmpty( otHeaderVO.getActualEndDate() ) != null
               && KANUtil.filterEmpty( otHeaderVO.getActualHours() ) != null )
         {
            otHeaderVO.setEstimateStartDate( otHeaderVO.getActualStartDate() );
            otHeaderVO.setEstimateEndDate( otHeaderVO.getActualEndDate() );
            otHeaderVO.setEstimateHours( otHeaderVO.getActualHours() );
         }
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         otHeaderVO.reset( null, request );
         // �����޸����
         otHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "otHeaderForm", otHeaderVO );
         request.setAttribute( "hasDeleteRight", hasDeleteRight( otHeaderVO ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageOT" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
            // ������ȡ�����
            final String otHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ��ȡOTHeaderVO����
            final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderId );
            // װ�ؽ��洫ֵ
            otHeaderVO.update( ( ( OTHeaderVO ) form ) );
            // �������׼״̬�ύ��д��ʵ��ʱ��
            if ( otHeaderVO.getStatus().equals( "3" ) )
            {
               otHeaderVO.setActualStartDate( ( ( OTHeaderVO ) form ).getEstimateStartDate() );
               otHeaderVO.setActualEndDate( ( ( OTHeaderVO ) form ).getEstimateEndDate() );
               otHeaderVO.setActualHours( ( ( OTHeaderVO ) form ).getEstimateHours() );
            }
            else
            {
               otHeaderVO.setEstimateStartDate( ( ( OTHeaderVO ) form ).getEstimateStartDate() );
               otHeaderVO.setEstimateEndDate( ( ( OTHeaderVO ) form ).getEstimateEndDate() );
               otHeaderVO.setEstimateHours( ( ( OTHeaderVO ) form ).getEstimateHours() );

               // ����Ǿܾ�״̬�����Actual
               if ( "6".equals( otHeaderVO.getStatus() ) )
               {
                  otHeaderVO.setActualStartDate( null );
                  otHeaderVO.setActualEndDate( null );
                  otHeaderVO.setActualHours( null );
               }
            }

            // ��ȡ��¼�û�
            otHeaderVO.setModifyBy( getUserId( request, response ) );
            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );

            otHeaderVO.setItemId( "0" );
            // ����ǿͻ��ύ
            if ( subAction != null && ( subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) || subAction.trim().equalsIgnoreCase( CONFIRM_OBJECT ) ) )
            {
               otHeaderVO.reset( mapping, request );
               if ( otHeaderService.submitOTHeader( otHeaderVO ) == -1 )
               {
                  success( request, null, ( subAction.equalsIgnoreCase( SUBMIT_OBJECT ) ? "�ύ" : "ȷ��" ) + "�ɹ���" );
                  insertlog( request, otHeaderVO, Operate.SUBMIT, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), ( subAction.equalsIgnoreCase( SUBMIT_OBJECT ) ? "�ύ"
                        : "ȷ��" ) );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, otHeaderVO, Operate.MODIFY, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), null );
               }
            }
            else
            {
               otHeaderService.updateOTHeader( otHeaderVO );
               success( request, MESSAGE_TYPE_UPDATE );
            }

            if ( KANUtil.filterEmpty( otHeaderVO.getDataFrom(), "1" ) == null )
               fetchOTHoursDetail( otHeaderVO, mapping, form, request, response );
         }
         // ���FORM
         ( ( OTHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   // ɾ���Ӱ��¼
   // Add by siuvan.xia @2014-07-04
   public ActionForward delete_ot( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

         // ��ȡҪɾ����ID
         final String otHeaderId = request.getParameter( "otHeaderId" );

         // ��ȡLeaveHeaderVO
         final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderId );
         otHeaderVO.setModifyBy( getUserId( request, response ) );
         otHeaderVO.setModifyDate( new Date() );

         if ( otHeaderService.deleteOTHeader_cleanTS( otHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_DELETE );
            insertlog( request, otHeaderVO, Operate.DELETE, otHeaderId, null );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         // ��ȡAction Form
         OTHeaderVO oTHeaderVO = ( OTHeaderVO ) form;
         // ����ѡ�е�ID
         if ( oTHeaderVO.getSelectedIds() != null && !oTHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, oTHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( oTHeaderVO.getSelectedIds() ) );
            // �ָ�
            for ( String selectedId : oTHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡɾ������
               oTHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               oTHeaderVO.setModifyBy( getUserId( request, response ) );
               oTHeaderVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               otHeaderService.deleteOTHeader( oTHeaderVO );
            }
         }

         // ���Selected IDs����Action
         ( ( OTHeaderVO ) form ).setSelectedIds( "" );
         ( ( OTHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final String subAction = request.getParameter( "subAction" );
         // ��ȡ���������
         final String otHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
         // ��ȡOTVO����
         final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderId );
         // ��ȡ��¼�û�
         otHeaderVO.setModifyBy( getUserId( request, response ) );
         otHeaderVO.setModifyDate( new Date() );
         // �߹�����
         otHeaderVO.reset( null, request );

         // �������׼״̬�ύ��д��ʵ��ʱ��
         if ( otHeaderVO.getStatus().equals( "3" ) )
         {
            otHeaderVO.setActualStartDate( otHeaderVO.getEstimateStartDate() );
            otHeaderVO.setActualEndDate( otHeaderVO.getEstimateEndDate() );
            otHeaderVO.setActualHours( otHeaderVO.getEstimateHours() );
         }

         if ( otHeaderService.submitOTHeader( otHeaderVO ) == -1 )
         {
            success( request, null, ( subAction.equalsIgnoreCase( SUBMIT_OBJECT ) ? "�ύ" : "ȷ��" ) + "�ɹ���" );
            insertlog( request, otHeaderVO, Operate.SUBMIT, otHeaderId, subAction.equalsIgnoreCase( SUBMIT_OBJECT ) ? "�ύ" : "ȷ��" );
         }
         else
         {
            error( request, MESSAGE_TYPE_SUBMIT );
         }

         fetchOTHoursDetail( otHeaderVO, mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   // �����ύ
   // Added by siuxia at 2014-06-04
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡActionForm 
         final OTHeaderVO otHeaderVO = ( OTHeaderVO ) form;

         // ��ʼ��Service�ӿ�
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

         // ��ù�ѡID
         final String otHeaderIds = otHeaderVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( otHeaderIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = otHeaderIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String selectId : selectedIdArray )
            {
               // ��ȡOTVO����
               final OTHeaderVO submitOTHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( KANUtil.decodeStringFromAjax( selectId ) );
               // ��ȡ��¼�û�
               submitOTHeaderVO.setModifyBy( getUserId( request, response ) );
               submitOTHeaderVO.setModifyDate( new Date() );
               // �߹�����
               submitOTHeaderVO.reset( null, request );

               // ����������ύ��ֻ�ύ״̬Ϊ���½�����
               // ���������ȷ�ϣ�ֻȷ��״̬Ϊ����׼����
               if ( ( otHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) && submitOTHeaderVO.getStatus().equals( "1" ) )
                     || otHeaderVO.getSubAction().equalsIgnoreCase( CONFIRM_OBJECTS ) && submitOTHeaderVO.getStatus().equals( "3" ) )
               {
                  // �������׼״̬�ύ��д��ʵ��ʱ��
                  if ( submitOTHeaderVO.getStatus().equals( "3" ) )
                  {
                     submitOTHeaderVO.setActualStartDate( submitOTHeaderVO.getEstimateStartDate() );
                     submitOTHeaderVO.setActualEndDate( submitOTHeaderVO.getEstimateEndDate() );
                     submitOTHeaderVO.setActualHours( submitOTHeaderVO.getEstimateHours() );
                  }

                  rows = rows + otHeaderService.submitOTHeader( submitOTHeaderVO );

                  fetchOTHoursDetail( submitOTHeaderVO, mapping, form, request, response );
               }
            }

            if ( rows == 0 )
            {
               request.setAttribute( "definedMessage", "true" );
            }

            insertlog( request, otHeaderVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( otHeaderIds ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /***
    * ѡ������Э�飬ajax��̨
    */
   public ActionForward contract_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡ����Э��ID
         final String contractId = request.getParameter( "contractId" );

         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // ��ȡEmployeeContractOTVO�б�
         final List< Object > employeeContractOTVOs = employeeContractOTService.getEmployeeContractOTVOsByContractId( contractId );

         // ��ȡclientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO == null ? "0"
               : employeeContractVO.getOrderId() );

         // ���ClientOrderOTVO�б�
         final List< Object > clientOrderOTVOs = clientOrderOTService.getClientOrderOTVOsByClientOrderHeaderId( clientOrderHeaderVO == null ? "0"
               : clientOrderHeaderVO.getOrderHeaderId() );

         final JSONObject jsonObject = new JSONObject();

         // Ĭ���д���
         boolean error = true;

         // �Ͷ���ͬ��ʼʱ��Ϊ�趨��
         if ( employeeContractVO == null || ( employeeContractVO != null && KANUtil.filterEmpty( employeeContractVO.getStartDate() ) == null ) )
         {
            error = false;
         }

         if ( error )
         {
            error = true;
            jsonObject.put( "contractStartDate", employeeContractVO.getStartDate() );
            jsonObject.put( "contractEndDate", employeeContractVO.getEndDate() );

            if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
               employeeContractVO.setEndDate( "2199-12-31 00:00:00" );

            // ����Ƿ����
            boolean exist = false;

            final String contractEndDate = employeeContractVO.getStatus().equals( "7" ) ? employeeContractVO.getResignDate() : employeeContractVO.getEndDate();
            // �Ͷ���ͬ�д��ڼӰ�����
            if ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 )
            {
               error = false;
               // �������һ��״̬Ϊ����
               for ( Object employeeContractTOVOObject : employeeContractOTVOs )
               {
                  if ( ( ( EmployeeContractOTVO ) employeeContractTOVOObject ).getStatus().equals( "1" ) )
                  {
                     exist = true;
                     break;
                  }
               }

               if ( exist )
               {
                  jsonObject.put( "startDate", KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" ) + " 00:00" );
                  jsonObject.put( "endDate", KANUtil.formatDate( KANUtil.getDate( contractEndDate, 0, 0, 1 ), null ) + " 00:00" );
               }
            }

            // �����д��ڼӰ�����
            if ( !exist && clientOrderOTVOs != null && clientOrderOTVOs.size() > 0 )
            {
               error = false;
               // �������һ��״̬Ϊ����
               for ( Object clientOrderOTVOObject : clientOrderOTVOs )
               {
                  if ( ( ( ClientOrderOTVO ) clientOrderOTVOObject ).getStatus().equals( "1" ) )
                  {
                     jsonObject.put( "startDate", KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" ) + " 00:00" );
                     jsonObject.put( "endDate", KANUtil.formatDate( KANUtil.getDate( contractEndDate, 0, 0, 1 ), null ) + " 00:00" );
                     break;
                  }
               }
            }

            // �������ڲ����ȡ����ʱ
            if ( jsonObject.get( "startDate" ) != null && jsonObject.get( "endDate" ) != null )
            {
               // ��ʼ��Contract Start Calendar
               final Calendar startCalendar = KANUtil.createCalendar( String.valueOf( jsonObject.get( "startDate" ) ) );

               // ��ʼ��Contract End Calendar
               final Calendar endCalendar = KANUtil.createCalendar( String.valueOf( jsonObject.get( "endDate" ) ) );

               // Ĭ��ѡ�е�ǰʱ��
               jsonObject.put( "defDate", KANUtil.formatDate( new Date(), "yyyy-MM-dd" + " 00:00:00" ) );

               // �����ǰʱ�䲻����ٷ�Χ��
               if ( KANUtil.getDays( new Date() ) > KANUtil.getDays( endCalendar ) || KANUtil.getDays( new Date() ) < KANUtil.getDays( startCalendar ) )
               {
                  jsonObject.discard( "defDate" );
                  jsonObject.put( "defDate", KANUtil.formatDate( jsonObject.get( "startDate" ), "yyyy-MM-dd" + " 00:00:00" ) );
               }
            }
         }

         String messageError = error ? "���߱��Ӱ�����������ԭ��1��" + ( getRole( request, null ).equals( "1" ) ? "������Ϣ" : "�Ͷ���ͬ" ) + "��ʼʱ��δ�趨��2��û����Ч�ļӰ����ã�" : "";
         jsonObject.put( "messageError", messageError );

         // Send to client
         out.println( jsonObject.toString() );
         out.flush();
         out.close();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /**
    * ���ڿؼ�ʧȥ���㣬ajax���ú�̨
    * ��ʼ���������ڶ���Ϊ��
    */
   public ActionForward calculate_ot_hours_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡ����Э��ID
         final String contractId = request.getParameter( "contractId" );

         // ��ʼʱ��
         final String startDate = request.getParameter( "startDate" );

         // ����ʱ��
         final String endDate = request.getParameter( "endDate" );

         // �޸�״̬�´��ϱ���ʣ��ʱ��
         String extraOTHours = "0";
         /*String extraOTHours = request.getParameter( "otHours" );
         if ( extraOTHours == null || extraOTHours.isEmpty() || KANUtil.filterEmpty( extraOTHours, "null" ) == null )
         {
            extraOTHours = "0";
         }*/

         // ��ʼ��Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final OTDetailService otDetailService = ( OTDetailService ) getService( "otDetailService" );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // ���ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         // ��ȡ��н��ʼ��������
         String circleStartDay = clientOrderHeaderVO.getCircleStartDay();
         String circleEndDay = clientOrderHeaderVO.getCircleEndDay();

         // �������ļ�н����Ĭ�Ͽ�ʼ��1����������31��
         if ( KANUtil.filterEmpty( circleStartDay, "0" ) == null || KANUtil.filterEmpty( circleEndDay, "0" ) == null )
         {
            circleStartDay = "1";
            circleEndDay = "31";
         }

         // ��ȡ��Ч�ļӰ�ÿ������Сʱ��
         String otLimitByMonth = employeeContractVO.getOtLimitByMonth();
         if ( KANUtil.filterEmpty( otLimitByMonth ) == null && clientOrderHeaderVO != null )
         {
            otLimitByMonth = clientOrderHeaderVO.getOtLimitByMonth();
         }

         // �������ļӰ�ÿ������Сʱ��Ĭ�������ơ�0��
         if ( KANUtil.filterEmpty( otLimitByMonth ) == null )
         {
            otLimitByMonth = "0";
         }

         // ��ȡÿ�ռӰ�Сʱ������
         String otLimitHoursByDay = employeeContractVO.getOtLimitByDay();
         if ( KANUtil.filterEmpty( otLimitHoursByDay, "0" ) == null && clientOrderHeaderVO != null )
         {
            otLimitHoursByDay = clientOrderHeaderVO.getOtLimitByDay();
         }

         // ��������ÿ�ռӰ�Сʱ������ͳĬ�ϡ�24h��
         if ( KANUtil.filterEmpty( otLimitHoursByDay, "0" ) == null )
         {
            otLimitHoursByDay = "24";
         }

         // ��ȡ��Ч�������Ű�ID
         String calendarId = employeeContractVO.getCalendarId();
         if ( KANUtil.filterEmpty( calendarId, "0" ) == null && clientOrderHeaderVO != null )
         {
            calendarId = clientOrderHeaderVO.getCalendarId();
         }

         String shiftId = employeeContractVO.getShiftId();
         if ( KANUtil.filterEmpty( shiftId, "0" ) == null && clientOrderHeaderVO != null )
         {
            shiftId = clientOrderHeaderVO.getShiftId();
         }

         // ����ֵ
         double reusltOTHours = 0;

         // ���ÿ�¼Ӱ�Сʱ���������ƣ�ֱ�Ӽ���
         if ( otLimitByMonth.equals( "0" ) )
         {
            reusltOTHours = new TimesheetDTO().getOTHours( getAccountId( request, response ), calendarId, shiftId, startDate, endDate, Double.valueOf( otLimitHoursByDay ) );
         }
         // �������������⴦��
         else
         {
            final Calendar startCalendar = KANUtil.createCalendar( startDate );
            final Calendar endCalendar = KANUtil.createCalendar( endDate );

            // ��ʼ��OTDetailVO�б�
            final List< Object > otDetailVOs = otDetailService.getOTDetailVOsByContractId( contractId );

            // ��ȡ�Ӱ������
            final long gap = KANUtil.getGapDays( endCalendar, startCalendar );

            // ��������
            for ( int i = 0; i <= gap; i++ )
            {
               String sDate = i == 0 ? startDate : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00";
               String eDate = i == gap ? endDate : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00";

               startCalendar.add( Calendar.DATE, 1 );

               if ( !sDate.equals( eDate ) )
               {
                  // ��ȡ��ǰ��Ӱ�Сʱ��
                  double currDayOTHours = new TimesheetDTO().getOTHours( getAccountId( request, response ), calendarId, shiftId, sDate, eDate, Double.valueOf( otLimitHoursByDay ) );

                  // �������������������
                  if ( Double.valueOf( otLimitHoursByDay ) > 0 && currDayOTHours > Double.valueOf( otLimitHoursByDay ) )
                  {
                     currDayOTHours = Double.valueOf( otLimitHoursByDay );
                  }

                  // ��ȡ��ǰ�������·�
                  final String currMonthly = KANUtil.getMonthlyByCondition( circleEndDay, sDate );

                  // ÿ�����޴���
                  reusltOTHours = reusltOTHours
                        + new TimesheetDTO().getAvailableOTHours( otDetailVOs, currMonthly, reusltOTHours, currDayOTHours, Double.valueOf( extraOTHours ), otLimitByMonth, circleEndDay );

               }
            }
         }

         // Send to client
         out.println( reusltOTHours );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡ����Э��ID
         final String contractId = request.getParameter( "contractId" );

         // Tab��ʾ
         final String noTab = request.getParameter( "noTab" );

         if ( KANUtil.filterEmpty( contractId, "0" ) != null )
         {
            // ��ʼ��Service
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

            // ��ȡOTHeaderVO�б�
            final List< OTHeaderVO > otHeaderVOs = otHeaderService.getOTHeaderVOsByContracrId( contractId );

            if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
            {
               for ( OTHeaderVO otHeaderVO : otHeaderVOs )
               {
                  otHeaderVO.reset( null, request );
               }
               request.setAttribute( "otHeaderVOs", otHeaderVOs );
               request.setAttribute( "countOTHeaderVO", otHeaderVOs.size() );
            }
         }

         if ( KANUtil.filterEmpty( noTab ) != null && KANUtil.filterEmpty( noTab ).equals( "true" ) )
         {
            return mapping.findForward( "manageOTSpecialTable" );
         }

         return mapping.findForward( "manageOTSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void list_special_info_html_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();
         // ��ȡ����Э��ID
         final String contractId = request.getParameter( "contractId" );

         final JSONArray jsonArray = new JSONArray();

         if ( KANUtil.filterEmpty( contractId, "0" ) != null )
         {
            // ��ʼ��Service
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

            // ��ȡOTHeaderVO�б�
            final List< OTHeaderVO > otHeaderVOs = otHeaderService.getOTHeaderVOsByContracrId( contractId );

            if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
            {
               for ( OTHeaderVO otHeaderVO : otHeaderVOs )
               {
                  otHeaderVO.reset( null, request );
                  final JSONObject jsonObject = JSONObject.fromObject( otHeaderVO );
                  jsonArray.add( jsonObject );
               }
            }
         }

         out.print( String.valueOf( jsonArray.toString() ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * �������EmployeeId�Ƿ���Ч
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkEmployeeId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

      // ���ActionForm
      final OTHeaderVO otHeaderVO = ( OTHeaderVO ) form;

      // ��ͼ��ȡEmployeeVO 
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( otHeaderVO.getEmployeeId() );

      // ������EmployeeVO��AccountId��ƥ�䵱ǰ
      if ( employeeVO == null
            || ( employeeVO != null && KANUtil.filterEmpty( employeeVO.getAccountId() ) != null && !employeeVO.getAccountId().equals( getAccountId( request, response ) ) ) )
      {
         request.setAttribute( "employeeIdErrorMsg", ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "Ա��" : "��Ա" ) + "ID��Ч��" );
      }

      if ( !LeaveHeaderAction.checkIsPass( request, otHeaderVO.getEmployeeId() ) )
      {
         request.setAttribute( "employeeIdErrorMsg", "��HRְ��ֻ��Ϊ�Լ���ͬһ����Ա���Ӱࣻ" );
      }

   }

   // check_ot_date�����������Ƿ��ظ�
   public void check_ot_date( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

         // ��ȡ����
         final String otHeaderId = request.getParameter( "otHeaderId" );
         final String employeeId = request.getParameter( "employeeId" );
         final String contractId = request.getParameter( "contractId" );
         final String startDate = request.getParameter( "startDate" );
         final String endDate = request.getParameter( "endDate" );

         final long startDate_s = KANUtil.createDate( startDate ).getTime();
         final long endDate_s = KANUtil.createDate( endDate ).getTime();

         // ʵ����OTHeaderVO
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setAccountId( getAccountId( request, null ) );
         otHeaderVO.setCorpId( getCorpId( request, null ) );
         otHeaderVO.setEmployeeId( employeeId );
         otHeaderVO.setContractId( contractId );

         boolean flag = false;

         // ��ȡOTHeaderVO�б�
         final List< Object > otHeaderVOs = otHeaderService.getOTHeaderVOsByCondition( otHeaderVO );

         // ����OTHeaderVO�б�
         if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
         {
            for ( Object otHeaderVOObject : otHeaderVOs )
            {
               final OTHeaderVO tempOTHeaderVO = ( OTHeaderVO ) otHeaderVOObject;
               if ( KANUtil.filterEmpty( otHeaderId ) != null && tempOTHeaderVO.getOtHeaderId().trim().equals( KANUtil.decodeString( otHeaderId ) ) )
               {
                  continue;
               }

               if ( startDate_s >= KANUtil.createDate( tempOTHeaderVO.getEstimateEndDate() ).getTime()
                     || endDate_s <= KANUtil.createDate( tempOTHeaderVO.getEstimateStartDate() ).getTime() )
               {
                  flag = false;
               }
               else
               {
                  flag = true;
                  break;
               }
            }
         }

         out.print( String.valueOf( flag ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward to_objectNew_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // �����ظ��ύ
      this.saveToken( request );

      // InHouse����£�Ĭ�ϵ�ǰ��¼�û��Ӱ�
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // ��ʼ��Service�ӿ�
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( OTHeaderVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
      }

      // ����Sub Action
      ( ( OTHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( OTHeaderVO ) form ).setStatus( OTHeaderVO.TRUE );

      return mapping.findForward( "manageOT_mobile" );
   }

   // ��ȡ�Լ�����ٺͼӰ�
   public ActionForward readMessage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setAccountId( getAccountId( request, response ) );
         otHeaderVO.setCreateBy( getUserId( request, response ) );
         otHeaderService.read_OT( otHeaderVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return null;
   }

   // �Ӱ�ɾ��Ȩ��
   // Add by siuvan.xia at 2014-07-03
   private boolean hasDeleteRight( final OTHeaderVO otHeaderVO ) throws KANException
   {
      // ������½�״̬
      if ( KANUtil.filterEmpty( otHeaderVO.getStatus() ) != null && otHeaderVO.getStatus().equals( "1" ) )
         return true;

      // �Ƿ��й�����
      if ( KANUtil.filterEmpty( otHeaderVO.getWorkflowId() ) != null )
         return false;

      // ��ʼ��OTDetailService
      final OTDetailService otDetailService = ( OTDetailService ) getService( "otDetailService" );

      // ��ȡOTDetailVO�б�
      final List< Object > otDetailVOs = otDetailService.getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );

      int count = 0;
      if ( otDetailVOs != null && otDetailVOs.size() > 0 )
      {
         // ��ʼ��LeaveHeaderService
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // �Ӱ�itemΪ���Ӱ໻�ݡ���Ѱ�����ޡ��Ӱ໻�ݡ�ȥ���
         for ( Object otDetailVOObject : otDetailVOs )
         {
            if ( KANUtil.filterEmpty( ( ( OTDetailVO ) otDetailVOObject ).getItemId() ) != null && ( ( OTDetailVO ) otDetailVOObject ).getItemId().equals( "25" ) )
            {
               final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
               leaveHeaderVO.setAccountId( otHeaderVO.getAccountId() );
               leaveHeaderVO.setCorpId( otHeaderVO.getCorpId() );
               leaveHeaderVO.setEmployeeId( otHeaderVO.getEmployeeId() );
               leaveHeaderVO.setContractId( otHeaderVO.getContractId() );
               leaveHeaderVO.setItemId( "25" );

               final List< LeaveHeaderVO > leaveHeaderVOs = leaveHeaderService.getLeaveHeaderVOsByCondition( leaveHeaderVO );

               if ( leaveHeaderVOs != null && leaveHeaderVOs.size() > 0 )
                  return false;
            }
         }

         // ��ʼ��TimesheetHeaderService
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // ���������ڱ���Ϊ���½������˻ء�״̬
         for ( Object otDetailVOObject : otDetailVOs )
         {
            if ( KANUtil.filterEmpty( ( ( OTDetailVO ) otDetailVOObject ).getTimesheetId() ) != null )
            {
               final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( ( ( OTDetailVO ) otDetailVOObject ).getTimesheetId() );
               if ( timesheetHeaderVO != null && ( timesheetHeaderVO.getStatus().equals( "1" ) || timesheetHeaderVO.getStatus().equals( "4" ) ) )
                  count++;
               else
                  return false;
            }
         }

         if ( count == otDetailVOs.size() )
            return true;
      }

      return false;
   }

   // ������ͷ
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );

      nameZHs.add( "�Ӱ�ID" );
      nameZHs.add( "�Ӱ����" );
      nameZHs.add( "��ʼʱ��" );
      nameZHs.add( "����ʱ��" );
      nameZHs.add( "�Ӱ�Сʱ" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "���������ģ�" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "������Ӣ�ģ�" );
      nameZHs.add( ( role.equals( "1" ) ? "������Ϣ" : "�Ͷ���ͬ" ) + "ID" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "�ͻ�ID" );
         nameZHs.add( "�ͻ�����" );
      }
      if ( "100056".equals( getAccountId( request, response ) ) )
      {
         nameZHs.add( "����Ӱ�" );
      }

      nameZHs.add( "״̬" );
      nameZHs.add( "�޸���" );
      nameZHs.add( "�޸�ʱ��" );

      return KANUtil.stringListToArray( nameZHs );
   }

   // ��������
   private String[] getNameSysArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameSyses = new ArrayList< String >();
      final String role = getRole( request, response );
      nameSyses.add( "specialOTHeaderId" );
      nameSyses.add( "decodeItemId" );
      nameSyses.add( "estimateStartDate" );
      nameSyses.add( "estimateEndDate" );
      nameSyses.add( "specialOTHours" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "contractId" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
         nameSyses.add( "clientName" );
      }
      if ( "100056".equals( getAccountId( request, response ) ) )
      {
         nameSyses.add( "decodeSpecialOT" );
      }
      nameSyses.add( "decodeStatus" );
      nameSyses.add( "decodeModifyBy" );
      nameSyses.add( "decodeModifyDate" );
      return KANUtil.stringListToArray( nameSyses );
   }

   public ActionForward add_object_nativeAPP( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         String result = "�Ӱ�����ʧ��";

         // ��ʼ��Service�ӿ�
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ȡ��ǰFORM
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setUnread( request.getParameter( "unread" ) );
         otHeaderVO.setContractId( request.getParameter( "contractId" ) );
         otHeaderVO.setEstimateStartDate( request.getParameter( "estimateStartDate" ) );
         otHeaderVO.setEstimateEndDate( request.getParameter( "estimateEndDate" ) );
         otHeaderVO.setEstimateHours( request.getParameter( "estimateHours" ) );
         otHeaderVO.setDescription( request.getParameter( "description" ) );
         otHeaderVO.setStatus( "1" );
         otHeaderVO.setCorpId( getCorpId( request, response ) );
         otHeaderVO.setEmployeeId( getEmployeeId( request, null ) );

         if ( KANUtil.filterEmpty( otHeaderVO.getClientId() ) == null )
         {
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( otHeaderVO.getContractId() );

            if ( employeeContractVO != null )
            {
               otHeaderVO.setClientId( employeeContractVO.getClientId() );
            }
         }

         // �趨��ǰ�û�
         otHeaderVO.setAccountId( getAccountId( request, response ) );
         otHeaderVO.setCreateBy( getUserId( request, response ) );
         otHeaderVO.setModifyBy( getUserId( request, response ) );

         if ( KANUtil.filterEmpty( otHeaderVO.getEstimateHours() ) != null && Double.valueOf( otHeaderVO.getEstimateHours() ) == 0 )
         {
            result = "����δ��������Ч�ļӰ�Сʱ����";
         }
         else
         {
            // ������ӷ���
            otHeaderService.insertOTHeader( otHeaderVO );
            otHeaderService.submitOTHeader( otHeaderVO );
            result = "success";
            insertlog( request, otHeaderVO, Operate.ADD, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), "Mobile Add" );
         }

         out.print( result );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );

      }

      return null;
   }

   // �Ż������Ӱ�ҳ�棬�Ӱ���ϸ״�������磺OT1.5 - 2Сʱ��OT3.0 - 4Сʱ
   // Added by siuvan @2014-09-10
   private void fetchOTHoursDetail( final OTHeaderVO otHeaderVO, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
      final OTDetailService otDetailService = ( OTDetailService ) getService( "otDetailService" );

      final OTHeaderVO tempOTHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderVO.getOtHeaderId() );
      tempOTHeaderVO.reset( mapping, request );

      final List< Object > otDetailVOs = otDetailService.getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );
      if ( otDetailVOs != null && otDetailVOs.size() > 0 )
      {
         double tempOTHours = 0;
         final JSONObject jsonObject = new JSONObject();
         for ( Object otDetailVOObject : otDetailVOs )
         {
            final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;

            if ( KANUtil.filterEmpty( otDetailVO.getActualHours() ) != null )
            {
               tempOTHours = Double.valueOf( otDetailVO.getActualHours() );
            }
            else if ( KANUtil.filterEmpty( otDetailVO.getEstimateHours() ) != null )
            {
               tempOTHours = Double.valueOf( otDetailVO.getEstimateHours() );
            }

            if ( jsonObject.get( otDetailVO.getItemId() ) == null )
            {
               jsonObject.put( otDetailVO.getItemId(), String.valueOf( tempOTHours ) );
            }
            else
            {
               jsonObject.put( otDetailVO.getItemId(), String.valueOf( Double.valueOf( String.valueOf( jsonObject.get( otDetailVO.getItemId() ) ) ) + tempOTHours ) );
            }
         }

         tempOTHeaderVO.setOtDetail( jsonObject.toString() );
         tempOTHeaderVO.setDescription( tempOTHeaderVO.getDecodeOTDetail() );

         otHeaderService.updateOTHeader_onlyUP( tempOTHeaderVO );
      }
   }

}
