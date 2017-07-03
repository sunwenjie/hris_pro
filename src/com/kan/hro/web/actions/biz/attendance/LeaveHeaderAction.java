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

import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.workflow.WorkflowActualStepsService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.SecurityAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.LeaveDetailService;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

public class LeaveHeaderAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public static String accessAction = "HRO_BIZ_ATTENDANCE_LEAVE_HEADER";

   // ��ǰAction��Ӧ��Access Action - In House
   public static String accessActionInHouse = "HRO_BIZ_ATTENDANCE_LEAVE_IN_HOUSE";

   // ��ǰAction��Ӧ��Access Action
   public static String getAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( !getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return accessAction;
      }
      else
      {
         return accessActionInHouse;
      }
   }

   // ���ְλ����People Manager
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
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         // ���Action Form
         final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;

         // TODO ��ʱ���⴦��
         if ( KANUtil.filterEmpty( leaveHeaderVO.getNotFormatEstimateStartDate() ) != null )
         {
            leaveHeaderVO.setEstimateStartDate( URLDecoder.decode( URLDecoder.decode( ( String ) leaveHeaderVO.getNotFormatEstimateStartDate(), "UTF-8" ), "UTF-8" ) );
         }

         if ( KANUtil.filterEmpty( leaveHeaderVO.getNotFormatEstimateEndDate() ) != null )
         {
            leaveHeaderVO.setEstimateEndDate( URLDecoder.decode( URLDecoder.decode( ( String ) leaveHeaderVO.getNotFormatEstimateEndDate(), "UTF-8" ), "UTF-8" ) );
         }

         if ( new Boolean( ajax ) )
         {
            decodedObject( leaveHeaderVO );
         }

         // ���û��ָ��������Ĭ�ϰ� LeaveId����
         if ( leaveHeaderVO.getSortColumn() == null || leaveHeaderVO.getSortColumn().isEmpty() )
         {
            leaveHeaderVO.setSortColumn( "leaveHeaderId" );
            leaveHeaderVO.setSortOrder( "desc" );
         }

         //         // �����inHouse
         //         if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         //         {
         //            // �������HRְ��
         //            if ( !isHRFunction( request, response ) )
         //            {
         //               // �����ˡ��޸���
         //               leaveHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         //               leaveHeaderVO.setCreateBy( getUserId( request, response ) );
         //               leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         //            }
         //            //��Ա��¼
         //         }
         //         else if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
         //         {
         //            leaveHeaderVO.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
         //         }

         // Ȩ������
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, leaveHeaderVO );
         setDataAuth( request, response, leaveHeaderVO );

         // ����ɾ������
         if ( leaveHeaderVO.getSubAction() != null && leaveHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( leaveHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( leaveHeaderVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         leaveHeaderService.getLeaveHeaderVOsByCondition( pagedListHolder, ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "leaveHeaderHolder", pagedListHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               pagedListHolder.setSource( leaveHeaderService.exportLeaveDetailByCondition( leaveHeaderVO ) );
               // ˢ�¹��ʻ�
               refreshHolder( pagedListHolder, request );
               request.setAttribute( "holderName", "leaveHeaderHolder" );
               request.setAttribute( "fileName", "���" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );

               // �����ļ�
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }

            isPeopleManager( request );
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listLeaveTable" );
         }

         isPeopleManager( request );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listLeave" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // �����ظ��ύ
      this.saveToken( request );

      // InHouse����£�Ĭ�ϵ�ǰ��¼�û����
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // ��ʼ��Service�ӿ�
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( LeaveHeaderVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
      }
      else if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
      {
         ( ( LeaveHeaderVO ) form ).setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
      }

      // ����Sub Action
      ( ( LeaveHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( LeaveHeaderVO ) form ).setStatus( LeaveHeaderVO.TRUE );

      // ��ת��������
      return mapping.findForward( "manageLeave" );
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
         ( ( LeaveHeaderVO ) form ).setTimesheetId( timesheetId );
         ( ( LeaveHeaderVO ) form ).setEmployeeId( employeeId );
         ( ( LeaveHeaderVO ) form ).setClientId( clientId );
         ( ( LeaveHeaderVO ) form ).setCorpId( getCorpId( request, response ) );
         ( ( LeaveHeaderVO ) form ).setContractId( contractId );
         ( ( LeaveHeaderVO ) form ).setSubAction( CREATE_OBJECT );
         ( ( LeaveHeaderVO ) form ).setStatus( LeaveHeaderVO.TRUE );
         ( ( LeaveHeaderVO ) form ).setItemId( null );
         request.setAttribute( "currDay", currDay + " 00:00" );
         request.setAttribute( "nextDay", KANUtil.formatDate( KANUtil.getDate( currDay, 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageLeave" );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-27
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         //�ж��ǲ�������΢�ŵ����
         final String from = request.getParameter( "mobile" );
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // ��ȡ��ǰForm
            final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;
            // ���渽��
            leaveHeaderVO.setAttachmentArray( request.getParameterValues( "attachmentArray" ) );
            leaveHeaderVO.setDataFrom( "1" );

            // ����ҳ��employeeId
            final String employeeId = leaveHeaderVO.getEmployeeId();
            // ���ڱ��浽passObject Ա����Ϣ
            final EmployeeVO tempEmployeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
            if ( tempEmployeeVO != null )
            {
               leaveHeaderVO.setEmployeeNameZH( tempEmployeeVO.getNameZH() );
               leaveHeaderVO.setEmployeeNameEN( tempEmployeeVO.getNameEN() );
               leaveHeaderVO.setEmployeeNo( tempEmployeeVO.getEmployeeNo() );
               leaveHeaderVO.setCertificateNumber( tempEmployeeVO.getCertificateNumber() );
            }

            // У��employeeId�Ƿ����
            checkEmployeeId( mapping, leaveHeaderVO, request, response );

            // ���Ϸ���employeeId��ת����ҳ��
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "employeeIdErrorMsg" ) ) != null )
            {
               leaveHeaderVO.reset();
               leaveHeaderVO.setEmployeeId( employeeId );
               leaveHeaderVO.setEmployeeNameZH( "" );
               leaveHeaderVO.setEmployeeNameEN( "" );

               return to_objectNew( mapping, leaveHeaderVO, request, response );
            }

            if ( KANUtil.filterEmpty( leaveHeaderVO.getClientId() ) == null )
            {
               final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );

               if ( employeeContractVO != null )
               {
                  leaveHeaderVO.setClientId( employeeContractVO.getClientId() );
               }
            }

            leaveHeaderVO.setActualLegalHours( "0" );
            leaveHeaderVO.setActualBenefitHours( "0" );
            leaveHeaderVO.setRetrieveStatus( "1" );
            leaveHeaderVO.setCreateBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            leaveHeaderVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

            if ( ( "41".equals( leaveHeaderVO.getItemId() ) && KANUtil.filterEmpty( leaveHeaderVO.getEstimateBenefitHours() ) != null
                  && KANUtil.filterEmpty( leaveHeaderVO.getEstimateLegalHours() ) != null && Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) == 0 && Double.valueOf( leaveHeaderVO.getEstimateLegalHours() ) == 0 )
                  || ( "48".equals( leaveHeaderVO.getItemId() ) && KANUtil.filterEmpty( leaveHeaderVO.getEstimateLegalHours() ) != null && Double.valueOf( leaveHeaderVO.getEstimateLegalHours() ) == 0 )
                  || ( "49".equals( KANUtil.filterEmpty( leaveHeaderVO.getEstimateBenefitHours() ) != null ) && Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) == 0 ) )
            {
               warning( request, null, "����δ��������Ч�����Сʱ����" );
            }
            else
            {
               // ���
               int result = leaveHeaderService.insertLeaveHeader( leaveHeaderVO );
               String remark = KANUtil.filterEmpty( from ) != null ? "����΢�ŵĲ���" : null;
               if ( result == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveHeaderVO.getLeaveHeaderId(), remark );
               }
               else
               {
                  success( request, MESSAGE_TYPE_ADD );
                  insertlog( request, leaveHeaderVO, Operate.ADD, leaveHeaderVO.getLeaveHeaderId(), remark );
               }
            }
         }

         // ���FORM
         ( ( LeaveHeaderVO ) form ).reset();
         ( ( LeaveHeaderVO ) form ).setEmployeeNameZH( "" );
         ( ( LeaveHeaderVO ) form ).setEmployeeNameEN( "" );
         ( ( LeaveHeaderVO ) form ).setNumber( "" );
         ( ( LeaveHeaderVO ) form ).setEmployeeNo( "" );
         ( ( LeaveHeaderVO ) form ).setCertificateNumber( "" );

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
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         // ������ȡ�����
         String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( leaveId == null || leaveId.trim().isEmpty() )
         {
            leaveId = ( ( LeaveHeaderVO ) form ).getLeaveHeaderId();
         }

         // ���LeaveVO����
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );

         // �������
         if ( KANUtil.filterEmpty( leaveHeaderVO.getActualStartDate() ) != null && KANUtil.filterEmpty( leaveHeaderVO.getActualEndDate() ) != null )
         {
            leaveHeaderVO.setEstimateStartDate( leaveHeaderVO.getActualStartDate() );
            leaveHeaderVO.setEstimateEndDate( leaveHeaderVO.getActualEndDate() );
            leaveHeaderVO.setEstimateLegalHours( leaveHeaderVO.getActualLegalHours() );
            leaveHeaderVO.setEstimateBenefitHours( leaveHeaderVO.getActualBenefitHours() );
         }

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         leaveHeaderVO.reset( null, request );
         // �����޸����
         leaveHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "leaveHeaderForm", leaveHeaderVO );

         // ��ʼ��leftHours
         double leftHours = 0.0;

         // ��ʼ��hours
         double hours = Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) + Double.valueOf( leaveHeaderVO.getEstimateLegalHours() );

         // ��ȡEmployeeContractLeaveVO�б�
         final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( leaveHeaderVO.getContractId() );

         if ( employeeContractLeaveVOs != null )
         {
            for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
            {
               if ( employeeContractLeaveVO.getItemId().equals( leaveHeaderVO.getItemId() ) )
               {
                  boolean isLimit = false;

                  // �ݼ��Ƿ�������
                  if ( ( "41".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getLegalQuantity(), "0" ) == null && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null )
                        || ( "48".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getLegalQuantity(), "0" ) == null )
                        || ( "49".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null )
                        || ( !"41".equals( employeeContractLeaveVO.getItemId() ) && !"48".equals( employeeContractLeaveVO.getItemId() )
                              && !"49".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null ) )
                  {
                     isLimit = true;
                  }

                  if ( !isLimit )
                  {
                     leftHours = Double.valueOf( employeeContractLeaveVO.getLeftLegalQuantity() ) + Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() );
                  }
                  else
                  {
                     leftHours = 1000;
                  }
               }
            }
         }

         final String leaveEndDate = getLeaveEndDate( leaveHeaderVO.getItemId(), getAccountId( request, null ), leaveHeaderVO.getContractId(), leaveHeaderVO.getEstimateStartDate(), leftHours, hours, null );
         // ���������
         isPeopleManager( request );
         request.setAttribute( "hasDeleteRight", hasDeleteRight( leaveHeaderVO ) );
         // д��request
         request.setAttribute( "leaveEndDate", leaveEndDate );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת�޸�ҳ��
      return mapping.findForward( "manageLeave" );
   }

   @Override
   // Reviewed by Kevin Jin at 2014-04-20
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );
            // ������ȡ�����
            final String leaveHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ���LeaveHeaderVO����
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );

            // װ�ؽ��洫ֵ
            leaveHeaderVO.update( ( ( LeaveHeaderVO ) form ) );
            // ���渽��
            leaveHeaderVO.setAttachmentArray( request.getParameterValues( "attachmentArray" ) );

            // ��������٣�д��ʵ��ʱ��
            if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( subAction ).equalsIgnoreCase( "SICK_LEAVE" ) )
            {
               leaveHeaderVO.setActualStartDate( ( ( LeaveHeaderVO ) form ).getEstimateStartDate() );
               leaveHeaderVO.setActualEndDate( ( ( LeaveHeaderVO ) form ).getEstimateEndDate() );
               leaveHeaderVO.setActualLegalHours( ( ( LeaveHeaderVO ) form ).getEstimateLegalHours() );
               leaveHeaderVO.setActualBenefitHours( ( ( LeaveHeaderVO ) form ).getEstimateBenefitHours() );
            }
            else
            {
               leaveHeaderVO.setEstimateStartDate( ( ( LeaveHeaderVO ) form ).getEstimateStartDate() );
               leaveHeaderVO.setEstimateEndDate( ( ( LeaveHeaderVO ) form ).getEstimateEndDate() );
               leaveHeaderVO.setEstimateLegalHours( ( ( LeaveHeaderVO ) form ).getEstimateLegalHours() );
               leaveHeaderVO.setEstimateBenefitHours( ( ( LeaveHeaderVO ) form ).getEstimateBenefitHours() );
            }

            // ��ȡ��¼�û�
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyDate( new Date() );

            // ����ǿͻ��ύ
            if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( subAction ).equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               leaveHeaderVO.reset( mapping, request );

               if ( leaveHeaderService.submitLeaveHeader( leaveHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveHeaderVO.getLeaveHeaderId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, leaveHeaderVO, Operate.MODIFY, leaveHeaderVO.getLeaveHeaderId(), null );
               }
            }
            // ����ͻ�������
            else if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( subAction ).equalsIgnoreCase( "SICK_LEAVE" ) )
            {
               leaveHeaderVO.reset( mapping, request );

               if ( leaveHeaderService.sickLeaveHeader( leaveHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveHeaderVO.getLeaveHeaderId(), "����" );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, leaveHeaderVO, Operate.MODIFY, leaveHeaderVO.getLeaveHeaderId(), "����" );
               }
            }
            else
            {
               leaveHeaderService.updateLeaveHeader( leaveHeaderVO );
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, leaveHeaderVO, Operate.MODIFY, leaveHeaderVO.getLeaveHeaderId(), null );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      if ( "mobile".equalsIgnoreCase( request.getParameter( "from" ) ) )
      {
         return this.list_object_mobile( mapping, form, request, response );
      }
      else
      {
         return to_objectModify( mapping, form, request, response );
      }
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   // Leave detailҳ��ɾ����ټ�¼
   // Add by siuvan.xia @ 2014-07-03
   public ActionForward delete_leave( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // ��ȡҪɾ����ID
         final String leaveHeaderId = request.getParameter( "leaveHeaderId" );

         // ��ȡLeaveHeaderVO
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         leaveHeaderVO.setModifyDate( new Date() );

         if ( leaveHeaderService.deleteLeaveHeader_cleanTS( leaveHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_DELETE );
            insertlog( request, leaveHeaderVO, Operate.DELETE, leaveHeaderId, null );
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         // ���Action Form
         LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;

         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( leaveHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, leaveHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( leaveHeaderVO.getSelectedIds() ) );
            // �ָ�
            for ( String selectedId : leaveHeaderVO.getSelectedIds().split( "," ) )
            {
               leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               leaveHeaderVO.setModifyBy( getUserId( request, response ) );
               leaveHeaderVO.setModifyDate( new Date() );

               if ( leaveHeaderService.deleteLeaveHeader( leaveHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_DELETE );
               }
            }
         }

         // ���Selected IDs����Action
         ( ( LeaveHeaderVO ) form ).setSelectedIds( "" );
         ( ( LeaveHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // Reviewed by Kevin Jin at 2013-11-26
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // ������������
         final String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���LeaveVO
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         leaveHeaderVO.setModifyDate( new Date() );
         leaveHeaderVO.reset( mapping, request );

         if ( leaveHeaderService.submitLeaveHeader( leaveHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
            insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveId, null );
         }
         else
         {
            error( request, MESSAGE_TYPE_SUBMIT );
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // �����ύ
   // Added by siuxia at 2014-06-04
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡActionForm
         final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;
         // ��ʼ��Service�ӿ�
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // ��ù�ѡID
         final String leaveHeaderIds = leaveHeaderVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( leaveHeaderIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = leaveHeaderIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String selectId : selectedIdArray )
            {
               // ���LeaveVO
               final LeaveHeaderVO submitLeaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( KANUtil.decodeStringFromAjax( selectId ) );
               submitLeaveHeaderVO.setModifyBy( getUserId( request, response ) );
               submitLeaveHeaderVO.setModifyDate( new Date() );
               submitLeaveHeaderVO.reset( mapping, request );

               rows = rows + leaveHeaderService.submitLeaveHeader( submitLeaveHeaderVO );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, leaveHeaderVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( leaveHeaderIds ) );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, leaveHeaderVO, Operate.MODIFY, null, KANUtil.decodeSelectedIds( leaveHeaderIds ) );
            }
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // Reviewed by Kevin Jin at 2013-11-25
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡleaveHeaderId
         final String leaveHeaderId = request.getParameter( "leaveHeaderId" );

         // ��ȡ����Э��ID
         final String contractId = request.getParameter( "contractId" );

         // ��ʼ��Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // ��ʾTab
         final String noTab = request.getParameter( "noTab" );

         request.setAttribute( "listAttachmentCount", "0" );

         if ( KANUtil.filterEmpty( contractId ) != null )
         {
            // ��ȡEmployeeContractLeaveVO�б�
            final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( contractId );

            if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
            {
               for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
               {
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "en" ) && employeeContractLeaveVO.getEmployeeLeaveId().contains( "���Խ����������" ) )
                  {
                     employeeContractLeaveVO.setEmployeeLeaveId( employeeContractLeaveVO.getEmployeeLeaveId().replace( "���Խ����������", "From Calculation Rules" ) );
                  }
                  employeeContractLeaveVO.reset( mapping, request );
               }

               request.setAttribute( "employeeContractLeaveVOs", employeeContractLeaveVOs );
               request.setAttribute( "countEmployeeContractLeaveVO", employeeContractLeaveVOs.size() );
            }
         }

         if ( KANUtil.filterEmpty( leaveHeaderId ) != null )
         {
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );
            if ( leaveHeaderVO != null )
            {
               request.setAttribute( "listAttachmentCount", leaveHeaderVO.getAttachmentArray().length );
            }
            request.setAttribute( "leaveHeaderForm", leaveHeaderVO );
         }

         if ( KANUtil.filterEmpty( noTab ) != null && KANUtil.filterEmpty( noTab ).equals( "true" ) )
         {
            return mapping.findForward( "manageLeaveSpecialTable" );
         }

         return mapping.findForward( "manageLeaveSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // Reviewed by Kevin Jin at 2013-11-25
   public ActionForward item_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��÷���Э��ID
         final String contractId = request.getParameter( "contractId" );

         // ��ĿID
         final String itemId = request.getParameter( "itemId" );

         // ��ʼ�� Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ���EmployeeContractLeaveVO�б�
         final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( contractId );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // ���ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         // �������·�
         String probationMonth = "0";
         if ( employeeContractVO != null && KANUtil.filterEmpty( employeeContractVO.getProbationMonth(), "0" ) != null )
         {
            probationMonth = employeeContractVO.getProbationMonth();
         }

         if ( KANUtil.filterEmpty( probationMonth, "0" ) == null && clientOrderHeaderVO != null )
         {
            probationMonth = clientOrderHeaderVO.getProbationMonth();
         }

         // ��ʼ��JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( employeeContractVO == null || ( employeeContractVO != null && KANUtil.filterEmpty( employeeContractVO.getStartDate() ) == null ) )
         {
            jsonObject.put( "startDate", "" );
            jsonObject.put( "endDate", "" );
         }
         else
         {
            if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
               employeeContractVO.setEndDate( "2199-12-31 00:00:00" );

            final String contractEndDate = employeeContractVO.getStatus().equals( "7" ) ? employeeContractVO.getResignDate() : employeeContractVO.getEndDate();
            if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
            {
               for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
               {
                  final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject;

                  if ( employeeContractLeaveVO.getItemId() != null && employeeContractLeaveVO.getItemId().equals( itemId ) && employeeContractLeaveVO.getStatus() != null
                        && employeeContractLeaveVO.getStatus().equals( "1" ) )
                  {
                     int year = 0;
                     String startDate = null;
                     String endDate = null;

                     // ��������
                     if ( "41".equals( itemId ) )
                     {
                        endDate = employeeContractLeaveVO.getYear() + "-12-31 00:00";
                        year = Integer.valueOf( employeeContractLeaveVO.getYear() );

                        if ( KANUtil.filterEmpty( employeeContractLeaveVO.getUseNextYearHours() ) == null || !"1".equals( employeeContractLeaveVO.getUseNextYearHours() ) )
                        {
                           endDate = extendAnnualLeaveUsedPeriod( endDate, year, ( employeeContractLeaveVO.getBenefitQuantityDelayMonth() == null ? 0
                                 : Integer.valueOf( employeeContractLeaveVO.getBenefitQuantityDelayMonth() ) ) );
                        }
                     }
                     // �������٣�ȥ�꣩
                     else if ( "48".equals( itemId ) )
                     {
                        Date legalDelayDate = KANUtil.createDate( employeeContractLeaveVO.getLeftLastYearLegalQuantityEndDate() );
                        endDate = KANUtil.formatDate( legalDelayDate, null ) + " 00:00";
                        year = Integer.valueOf( employeeContractLeaveVO.getYear() ) + 1;
                     }
                     else if ( "49".equals( itemId ) )
                     {
                        Date benefitDelayDate = KANUtil.createDate( employeeContractLeaveVO.getLeftLastYearBenefitQuantityEndDate() );
                        endDate = KANUtil.formatDate( benefitDelayDate, null ) + " 00:00";
                        year = Integer.valueOf( employeeContractLeaveVO.getYear() ) + 1;
                     }
                     else
                     {
                        endDate = KANUtil.formatDate( KANUtil.getDate( contractEndDate, 0, 0, 1 ), null ) + " 00:00";
                     }

                     if ( "41".equals( itemId ) || "48".equals( itemId ) || "49".equals( itemId ) )
                     {
                        // �����ڲ�����
                        if ( "2".equalsIgnoreCase( employeeContractLeaveVO.getProbationUsing() ) )
                        {
                           // ���3���²�����
                           Calendar probationEndCalendar = KANUtil.getCalendar( KANUtil.getDate( employeeContractVO.getStartDate(), 0, 3 ) );
                           startDate = KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
                        }
                        else
                        {
                           startDate = KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" );
                        }

                        // ����Ǵ������
                        if ( year == Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) ) + 1 )
                        {
                           startDate = year + "-01-01";
                        }
                     }
                     else
                     {
                        startDate = getAvailableLeaveStartDate( employeeContractVO.getStartDate(), employeeContractLeaveVO.getCycle(), employeeContractLeaveVO.getProbationUsing(), probationMonth, employeeContractVO.getProbationEndDate(), String.valueOf( year ) );
                     }

                     jsonObject.put( "startDate", startDate + " 00:00" );
                     jsonObject.put( "endDate", endDate );
                     jsonObject.put( "itemId", employeeContractLeaveVO.getItemId() );
                     break;
                  }
               }
            }

            // �������ڲ�����٣�ֻ���ڵ�ǰʱ��ǰһ����
            if ( jsonObject.get( "startDate" ) != null )
            {
               Date nowBeforeOneMonth = KANUtil.getDate( new Date(), 0, -1 );
               Date currStartDate = KANUtil.createDate( jsonObject.get( "startDate" ).toString() );

               if ( nowBeforeOneMonth.getTime() > currStartDate.getTime() )
               {
                  jsonObject.put( "startDate", KANUtil.formatDate( nowBeforeOneMonth, "yyyy-MM-dd" ) + " 00:00" );
               }
            }

            // �������ڲ����ȡ����ʱ��Ĭ��ѡ�����ڲ�����ٷ�Χ�ڣ�
            if ( jsonObject.get( "startDate" ) != null )
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

         // ����ٴ���Ĭ��ʱ�䣨���Ű�����Add by fox
         if ( jsonObject.get( "defDate" ) != null )
         {
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

            final Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime( KANUtil.createDate( jsonObject.get( "defDate" ).toString() ) );

            ShiftDetailVO shiftDetailVO = new TimesheetDTO().getShiftDetailVO( getAccountId( request, null ), calendarId, shiftId, KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ), TimesheetDTO.EXCEPTION_TYPE_LEAVE );

            if ( shiftDetailVO != null && shiftDetailVO.getDayType() != null && shiftDetailVO.getDayType().trim().equals( "1" ) )
            {
               final String[] shiftPeriods = KANUtil.jasonArrayToStringArray( shiftDetailVO.getShiftPeriod() );
               int startPeriod = Integer.valueOf( shiftPeriods[ 0 ] ) - 1;
               int endPeriod = Integer.valueOf( shiftPeriods[ shiftPeriods.length - 1 ] );
               String tempStartDate = KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " " + Integer.valueOf( startPeriod ) / 2 + ":" + Integer.valueOf( startPeriod )
                     % 2;
               String tempEndDate = KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " " + Integer.valueOf( endPeriod ) / 2 + ":" + Integer.valueOf( endPeriod ) % 2
                     * 30;

               jsonObject.put( "defDate", KANUtil.formatDate( tempStartDate, "yyyy-MM-dd HH:mm:ss" ) );
               jsonObject.put( "defEndDate", KANUtil.formatDate( tempEndDate, "yyyy-MM-dd HH:mm:ss" ) );
            }
         }

         // �ж����Ĭ�ϵ�ʱ���Ƿ񳬹���ǰ���ʣ��Сʱ���Ľ���ʱ�� Add by siuvan
         if ( jsonObject.get( "defEndDate" ) != null )
         {
            double leftHours = 0;
            if ( employeeContractLeaveVOs != null )
            {
               for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
               {
                  if ( employeeContractLeaveVO.getItemId().equals( itemId ) )
                  {
                     boolean isLimit = false;

                     // �ݼ��Ƿ�������
                     if ( ( "41".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getLegalQuantity(), "0" ) == null && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null )
                           || ( "48".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getLegalQuantity(), "0" ) == null )
                           || ( "49".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null )
                           || ( !"41".equals( employeeContractLeaveVO.getItemId() ) && !"48".equals( employeeContractLeaveVO.getItemId() )
                                 && !"49".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null ) )
                     {
                        isLimit = true;
                     }

                     if ( !isLimit )
                     {
                        leftHours = Double.valueOf( employeeContractLeaveVO.getLeftLegalQuantity() ) + Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() );
                     }
                     else
                     {
                        leftHours = 1000;
                     }
                  }
               }
            }

            String tmpLeaveEndDate = getLeaveEndDate( itemId, getAccountId( request, null ), contractId, jsonObject.getString( "defDate" ), leftHours, 0, null );
            if ( KANUtil.createCalendar( jsonObject.getString( "defEndDate" ) ).getTime().getTime() > KANUtil.createCalendar( tmpLeaveEndDate ).getTime().getTime() )
            {
               jsonObject.discard( "defEndDate" );
               jsonObject.put( "defEndDate", KANUtil.formatDate( tmpLeaveEndDate, "yyyy-MM-dd HH:mm:ss" ) );
            }

            jsonObject.put( "tmpLeaveEndDate", tmpLeaveEndDate );
         }

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

   /****
    * extendAnnualLeaveUsedPeriod �������ʹ���ڼ�
    * 1��ÿ��10�·ݲ����䣻
    * 2����������Ϊ����ӳ�ʹ������
    * ����1���������ʹ�ý�����
    * ����2����ǰ���
    * ����3������ӳ�����
    */
   private String extendAnnualLeaveUsedPeriod( String initEndDate, int year, int delayMonth )
   {
      if ( Integer.valueOf( KANUtil.formatDate( new Date(), "MM" ) ) >= 10 )
      {
         return KANUtil.formatDate( KANUtil.getDate( year + "-12-31", 0, delayMonth ), "yyyy-MM-dd HH:mm" );
      }

      return initEndDate;
   }

   // ���������ʣʱ�䣬�����ٽ���ʱ��
   public String getLeaveEndDate( final String itemId, final String accountId, final String contractId, final String startDate, final double leftHours, final double hours,
         final String maxDate ) throws KANException
   {
      // ��ʼ������ֵ
      String leaveEndDate = "";

      // ��ʼ�����ʣ��ʱ��
      double leaveLeftHours = 0.0;
      leaveLeftHours = leftHours;

      if ( leftHours != -1 && KANUtil.filterEmpty( hours ) != null )
      {
         leaveLeftHours = leaveLeftHours + hours;
      }

      if ( leaveLeftHours == -1 || leaveLeftHours > 0 )
      {
         // ��ʼ��Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��÷���Э��
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
            employeeContractVO.setEndDate( "2199-12-31 00:00:00" );

         // ��÷��񶩵�
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         final String contractEndDate = employeeContractVO.getStatus().equals( "7" ) ? employeeContractVO.getResignDate() : employeeContractVO.getEndDate();

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

         if ( leaveLeftHours == -1 && employeeContractVO != null && KANUtil.filterEmpty( calendarId, "0" ) != null && KANUtil.filterEmpty( shiftId, "0" ) != null )
         {
            return KANUtil.formatDate( KANUtil.getDate( contractEndDate, 0, 0, 1 ), "yyyy-MM-dd HH:mm:ss" );
         }

         if ( KANUtil.filterEmpty( calendarId, "0" ) != null && KANUtil.filterEmpty( shiftId, "0" ) != null )
         {
            String endLeaveDatetime = new TimesheetDTO().getEndLeaveDatetime( itemId, accountId, calendarId, shiftId, startDate, Double.valueOf( leftHours ) );

            String endDate = maxDate == null ? contractEndDate : maxDate;

            if ( KANUtil.getDays( KANUtil.createCalendar( endLeaveDatetime ) ) < KANUtil.getDays( KANUtil.getDate( endDate, 0, 0, 1 ) ) )
            {
               leaveEndDate = endLeaveDatetime;
            }
            else
            {
               leaveEndDate = KANUtil.formatDate( KANUtil.getDate( endDate, 0, 0, 1 ), "yyyy-MM-dd HH:mm:ss" );
            }

            if ( "13:".equals( KANUtil.formatDate( leaveEndDate, "HH:" ) ) )
            {
               leaveEndDate = leaveEndDate.replace( "13:", "14:" );
            }
         }
      }

      return leaveEndDate;
   }

   /**
    * ��ʼ���ڿؼ�ʧȥ���㣬ajax���ú�̨
    * ���������ʣʱ�䣬�������ʱ��
    */
   // Reviewed by Kevin Jin at 2013-11-26
   public ActionForward get_endDate_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡContractId
         final String contractId = request.getParameter( "contractId" );

         // ��ÿ�ʼʱ��
         final String startDate = request.getParameter( "date" );

         final String itemId = request.getParameter( "itemId" );

         // ��ȡʣ��Сʱ��
         Double leftHours = Double.valueOf( request.getParameter( "leftHours" ) );

         // ��ȡ�޸�ʱ�õ�Сʱ��
         double hours = 0.0;
         if ( KANUtil.filterEmpty( request.getParameter( "hours" ) ) != null )
         {
            hours = Double.valueOf( request.getParameter( "hours" ) );
         }

         // forTimesheet
         final String maxDate = request.getParameter( "maxDate" );

         if ( leftHours != -1 && KANUtil.filterEmpty( hours ) != null )
         {
            leftHours = leftHours + Double.valueOf( hours );
         }

         // Send to client
         out.println( getLeaveEndDate( itemId, getAccountId( request, null ), contractId, startDate, leftHours, hours, maxDate ) );
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
    * ��ʼ���������ڶ���Ϊ�գ������ٿ��ʱ�䣨Сʱ��
    */
   // Reviewed by Kevin Jin at 2013-11-26
   public ActionForward calculate_leave_hours_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��÷���Э��ID
         final String contractId = request.getParameter( "contractId" );

         // ��ʼʱ��
         final String startDate = request.getParameter( "startDate" );

         // ����ʱ��
         final String endDate = request.getParameter( "endDate" );

         final String itemId = request.getParameter( "itemId" );

         // ��ʼ��Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // ���ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

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

         double hours = new TimesheetDTO().getLeaveHours( itemId, getAccountId( request, response ), calendarId, shiftId, startDate, endDate );

         // Send to client
         out.print( hours );
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
    * 
   * 	Get Available Leave Start Date
   *  ��ȡ��Ч��ʼʱ��
   *	
   *	@param startDate
   *	@param cycle
   *	@param probationUsing
   *	@param months
   *  @param probationEndDate
   *	@return
   *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-26
   private static String getAvailableLeaveStartDate( final String startDate, final String cycle, final String probationUsing, final String months, final String probationEndDate,
         final String year ) throws KANException
   {
      try
      {
         // ���ǵ��������·�
         Calendar probationEndCalendar = KANUtil.getCalendar( KANUtil.getDate( startDate, 0, KANUtil.filterEmpty( probationUsing, "0" ) != null
               && probationUsing.trim().equals( "1" ) ? 0 : Integer.parseInt( KANUtil.filterEmpty( months ) != null ? months : "0" ) ) );

         if ( KANUtil.filterEmpty( probationUsing, "0" ) != null && KANUtil.filterEmpty( probationUsing, "0" ).equals( "2" ) && KANUtil.filterEmpty( probationEndDate ) != null )
         {
            if ( KANUtil.getDays( probationEndCalendar ) > KANUtil.getDays( KANUtil.createCalendar( probationEndDate ) ) )
            {
               probationEndCalendar = KANUtil.createCalendar( probationEndDate );
            }
         }

         // �����ڣ�����Э�鿪ʼ������ʱ��
         if ( KANUtil.filterEmpty( cycle ) != null && cycle.equals( "1" ) )
         {
            return KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
         }
         // ������
         else if ( KANUtil.filterEmpty( cycle ) != null && cycle.equals( "2" ) )
         {
            if ( probationEndCalendar.get( Calendar.YEAR ) >= KANUtil.getCalendar( new Date() ).get( Calendar.YEAR ) )
            {
               return KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
            }
            else
            {
               return KANUtil.getCalendar( new Date() ).get( Calendar.YEAR ) + "-01-01";
            }
         }
         // ������
         else if ( KANUtil.filterEmpty( cycle ) != null && cycle.equals( "3" ) )
         {
            final Calendar nextYearStartDateCalendar = KANUtil.getCalendar( KANUtil.getDate( startDate, 1 ) );

            if ( KANUtil.getDays( nextYearStartDateCalendar ) > KANUtil.getDays( new Date() ) )
            {
               return KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
            }
            else
            {
               while ( KANUtil.getDays( nextYearStartDateCalendar ) <= KANUtil.getDays( new Date() ) )
               {
                  nextYearStartDateCalendar.add( Calendar.YEAR, 1 );
               }

               // ����
               nextYearStartDateCalendar.add( Calendar.YEAR, -1 );
               return KANUtil.formatDate( nextYearStartDateCalendar.getTime(), "yyyy-MM-dd" );
            }
         }
         // �̶���
         else if ( KANUtil.filterEmpty( cycle ) != null && cycle.equals( "5" ) )
         {
            Calendar leaveStartCalendar = KANUtil.createCalendar( year + "-01-01" );
            if ( leaveStartCalendar.getTimeInMillis() < probationEndCalendar.getTimeInMillis() )
            {
               return KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
            }
            else
            {
               return KANUtil.formatDate( leaveStartCalendar.getTime(), "yyyy-MM-dd" );
            }
         }
         else
         {
            return startDate;
         }
      }
      catch ( Exception e )
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
      final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;

      // ��ͼ��ȡEmployeeVO 
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( leaveHeaderVO.getEmployeeId() );

      // ������EmployeeVO��AccountId��ƥ�䵱ǰ
      if ( employeeVO == null
            || ( employeeVO != null && KANUtil.filterEmpty( employeeVO.getAccountId() ) != null && !employeeVO.getAccountId().equals( getAccountId( request, response ) ) ) )
      {
         request.setAttribute( "employeeIdErrorMsg", ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "Ա��" : "��Ա" ) + "ID��Ч��" );
      }

      if ( !checkIsPass( request, leaveHeaderVO.getEmployeeId() ) )
      {
         request.setAttribute( "employeeIdErrorMsg", "��HRְ��ֻ��Ϊ�Լ���ͬһ����Ա����٣�" );
      }

   }

   /**  
    * checkIsPass
    * �Ӱࡢ������루�Լ��Լ�ͬ���ţ�inHouse��hrBranch�����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public static boolean checkIsPass( final HttpServletRequest request, final String employeeId ) throws KANException
   {
      // ��InHouse������֤ͨ��
      if ( !getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         return true;
      }

      // InHouse�¾���HRְ�ܿ�Ϊ�κ������
      if ( isHRFunction( request, null ) )
      {
         return true;
      }
      // InHouse�·�HR����ְ��Ϊ�Լ�����ͬ����Ա�����
      else
      {
         // ������Լ����
         if ( KANUtil.filterEmpty( getEmployeeId( request, null ) ) != null && KANUtil.filterEmpty( employeeId ) != null && getEmployeeId( request, null ).equals( employeeId ) )
         {
            return true;
         }
         // ��ͬһ����Ա�����
         else
         {
            // ��ȡbranchId
            final String branchId = getBranchId( request, null );

            // ����employeeId��ȡStaffDTO�б�
            final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getStaffDTOsByEmployeeId( employeeId );

            // ����StaffDTO�б�
            if ( staffDTOs != null && staffDTOs.size() > 0 )
            {
               // Ĭ��ȥ��һ��StaffDTO
               final StaffDTO staffDTO = staffDTOs.get( 0 );

               // ����staffId��ȡPositionDTO�б�
               final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionDTOsByStaffId( staffDTO.getStaffVO().getStaffId() );

               // ����PositionDTO�б�
               if ( branchId != null && positionDTOs != null && positionDTOs.size() > 0 )
               {
                  // ���������Ƿ�һ��
                  for ( PositionDTO positionDTO : positionDTOs )
                  {
                     if ( positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getBranchId() ) != null
                           && positionDTO.getPositionVO().getBranchId().equals( branchId ) )
                     {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   // check_leave_date�����������Ƿ��ظ�
   public void check_leave_date( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // ��ȡ����
         final String leaveHeaderId = request.getParameter( "leaveHeaderId" );
         final String employeeId = request.getParameter( "employeeId" );
         final String contractId = request.getParameter( "contractId" );
         final String startDate = request.getParameter( "startDate" );
         final String endDate = request.getParameter( "endDate" );

         final long startDate_s = KANUtil.createDate( startDate ).getTime();
         final long endDate_s = KANUtil.createDate( endDate ).getTime();

         // ʵ����LeaveHeaderVO
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setAccountId( getAccountId( request, null ) );
         leaveHeaderVO.setCorpId( getCorpId( request, null ) );
         leaveHeaderVO.setEmployeeId( employeeId );
         leaveHeaderVO.setContractId( contractId );

         boolean flag = false;

         // ��ȡLeaveHeaderVO�б�
         final List< LeaveHeaderVO > leaveHeaderVOs = leaveHeaderService.getLeaveHeaderVOsByCondition( leaveHeaderVO );

         // ����LeaveHeaderVO�б�
         if ( leaveHeaderVOs != null && leaveHeaderVOs.size() > 0 )
         {
            for ( LeaveHeaderVO tempLeaveHeaderVO : leaveHeaderVOs )
            {
               if ( KANUtil.filterEmpty( leaveHeaderId ) != null && tempLeaveHeaderVO.getLeaveHeaderId().trim().equals( KANUtil.decodeString( leaveHeaderId ) ) )
               {
                  continue;
               }

               if ( "4".equals( tempLeaveHeaderVO.getStatus() ) )
                  continue;

               // �����ǰ���ʱ�����ԭ�м�¼����
               if ( startDate_s >= KANUtil.createDate( tempLeaveHeaderVO.getEstimateEndDate() ).getTime()
                     || endDate_s <= KANUtil.createDate( tempLeaveHeaderVO.getEstimateStartDate() ).getTime() )
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

      // InHouse����£�Ĭ�ϵ�ǰ��¼�û����
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // ��ʼ��Service�ӿ�
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( LeaveHeaderVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
         boolean isForeign = false;
         PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionVOByPositionId( getPositionId( request, null ) );
         if ( employeeVO != null )
         {
            String remark1 = employeeVO.getRemark1();
            JSONObject remarkObject = JSONObject.fromObject( remark1 );
            Object locobject = remarkObject.get( "bangongdidian" );
            if ( locobject != null )
            {
               String loc = String.valueOf( locobject );
               // 57 �Ĵ�����,106�׶�
               if ( "57".equals( loc ) || "106".equals( loc ) )
               {
                  isForeign = true;
               }
            }
         }

         // BU > iclick SEA > 09:00 ~ 18:00
         if ( positionVO != null )
         {
            BranchVO branchVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getBranchVOByBranchId( positionVO.getBranchId() );
            if ( branchVO != null )
            {
               BranchVO parentBranchVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getBranchVOByBranchId( branchVO.getParentBranchId() );
               if ( parentBranchVO != null && "1766".equals( parentBranchVO.getBranchId() ) )
               {
                  isForeign = false;
               }
            }
         }
         request.setAttribute( "isForeign", isForeign );
      }

      // ����Sub Action
      ( ( LeaveHeaderVO ) form ).setSubAction( SUBMIT_OBJECT );
      ( ( LeaveHeaderVO ) form ).setStatus( LeaveHeaderVO.TRUE );

      // ��ת��������
      return mapping.findForward( "manageLeaveMobile" );
   }

   public ActionForward list_special_info_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡ����Э��ID
         final String contractId = request.getParameter( "contractId" );

         // ��ʼ��Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         
         JSONObject jsonResult = new JSONObject();

         JSONArray jsonArray = new JSONArray();

         if ( KANUtil.filterEmpty( contractId ) != null )
         {
            // ��ȡEmployeeContractLeaveVO�б�
            final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( contractId );

            if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
            {
               for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
               {
                  employeeContractLeaveVO.reset( mapping, request );
                  JSONObject jsonObject = JSONObject.fromObject( employeeContractLeaveVO );
                  jsonArray.add( jsonObject );
               }

            }
        
             // �������
             jsonResult.put("employeeContractLeaveData", jsonArray);
             // ��������, ���������ڲ��������
             jsonResult.put("inProbationAndCannotUse", inProbationAndCannotUse(contractId,employeeContractLeaveVOs));
             out.print( jsonResult.toString() );
         }
         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
   
   /**
    * 
    * @param employeeContractLeaveVOs
    * @return ��������, ���������ڲ��������
    * true �����������, ���������ڲ��������, ���ҳ���¼�ѡ��
    * false �������ҳ���¼�ѡ��
    */
   private boolean inProbationAndCannotUse(String contractId,List< EmployeeContractLeaveVO > employeeContractLeaveVOs) throws Exception{
     boolean probationCannotUse = false;
     if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 ){
       final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
       EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId(contractId);
       for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
       {
          final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject;
          String itemId = employeeContractLeaveVO.getItemId();
          if ( "41".equals( itemId ) || "48".equals( itemId ) || "49".equals( itemId ) ){
            // �����ڲ�����
            if ( "2".equalsIgnoreCase( employeeContractLeaveVO.getProbationUsing() ) )
            {
              Calendar probationEndCalendar = KANUtil.getCalendar( KANUtil.getDate( employeeContractVO.getStartDate(), 0, 3 ) );
              Calendar now = Calendar.getInstance();
              probationCannotUse = now.before(probationEndCalendar);
              if(probationCannotUse){
                break;
              }
            }
          }
       }
     }
     return probationCannotUse;
   }

   public ActionForward submit_object_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

            // ������������
            final String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���LeaveVO
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyDate( new Date() );
            leaveHeaderVO.reset( mapping, request );
         }
         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   private void appendAuditor_mobile( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      if ( ( "2".equals( leaveHeaderVO.getStatus() ) || "2".equals( leaveHeaderVO.getRetrieveStatus() ) ) && KANUtil.filterEmpty( leaveHeaderVO.getWorkflowId() ) != null )
      {
         // ��ʼ��Service�ӿ�
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // ���Action Form
         final WorkflowActualStepsVO workflowActualStepsVO = new WorkflowActualStepsVO();
         workflowActualStepsVO.setAccountId( leaveHeaderVO.getAccountId() );
         workflowActualStepsVO.setCorpId( leaveHeaderVO.getCorpId() );
         // ��õ�ǰ����
         workflowActualStepsVO.setWorkflowId( leaveHeaderVO.getWorkflowId() );
         // Ĭ�ϰ�����������������
         workflowActualStepsVO.setSortColumn( "stepIndex" );
         workflowActualStepsVO.setStepType( "1,2" );
         workflowActualStepsVO.setSortOrder( "asc" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder workflowActualStepsHolder = new PagedListHolder();
         // ���뵱ǰֵ����
         workflowActualStepsHolder.setObject( workflowActualStepsVO );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowActualStepsService.getWorkflowActualStepsVOByCondition( workflowActualStepsHolder, false );

         if ( workflowActualStepsHolder != null && workflowActualStepsHolder.getSource() != null && workflowActualStepsHolder.getSource().size() > 0 )
         {
            for ( Object o : workflowActualStepsHolder.getSource() )
            {
               final WorkflowActualStepsVO vo = ( WorkflowActualStepsVO ) o;
               if ( "2".equals( vo.getStatus() ) )
               {
                  leaveHeaderVO.setAuditorZH( vo.getPositionTitleZH() );
                  leaveHeaderVO.setAuditorEN( vo.getPositionTitleEN() );
                  break;
               }
            }
         }
      }
   }

   // ��ȡ�Լ�����ٺͼӰ�
   public ActionForward list_object_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final String page = request.getParameter( "page" );
         final PagedListHolder pagedListHolderLeave = new PagedListHolder();
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setEmployeeId( getEmployeeId( request, null ) );
         leaveHeaderVO.setAccountId( getAccountId( request, null ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            leaveHeaderVO.setCorpId( getCorpId( request, null ) );
         }
         leaveHeaderVO.setSortColumn( "estimateStartDate" );
         leaveHeaderVO.setSortOrder( "desc" );
         pagedListHolderLeave.setObject( leaveHeaderVO );
         pagedListHolderLeave.setPageSize( listPageSize );
         pagedListHolderLeave.setPage( page );
         leaveHeaderService.getLeaveHeaderVOsByCondition( pagedListHolderLeave, true );
         for ( Object object : pagedListHolderLeave.getSource() )
         {
            ( ( LeaveHeaderVO ) object ).reset( null, request );
            appendAuditor_mobile( ( LeaveHeaderVO ) object );
         }

         // �Ӱ��
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final PagedListHolder pagedListHolderOT = new PagedListHolder();
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setEmployeeId( getEmployeeId( request, null ) );
         otHeaderVO.setAccountId( getAccountId( request, null ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            otHeaderVO.setCorpId( getCorpId( request, null ) );
         }
         otHeaderVO.setSortColumn( "unread desc,estimateStartDate" );
         otHeaderVO.setSortOrder( "desc" );
         pagedListHolderOT.setObject( otHeaderVO );
         pagedListHolderOT.setPageSize( listPageSize );
         pagedListHolderOT.setPage( page );
         otHeaderService.getOTHeaderVOsByCondition( pagedListHolderOT, true );
         for ( Object object : pagedListHolderOT.getSource() )
         {
            ( ( OTHeaderVO ) object ).reset( null, request );
         }

         request.setAttribute( "pagedListHolderLeave", pagedListHolderLeave );
         request.setAttribute( "pagedListHolderOT", pagedListHolderOT );
         if ( new Boolean( request.getParameter( "ajax" ) ) )
         {
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // ��ʼ��PrintWrite����
            final PrintWriter out = response.getWriter();
            final JSONObject jsonObject = new JSONObject();
            final JSONArray leaveJsonArray = JSONArray.fromObject( pagedListHolderLeave.getSource() );
            final JSONArray otJsonArray = JSONArray.fromObject( pagedListHolderOT.getSource() );
            jsonObject.put( "leave", leaveJsonArray );
            jsonObject.put( "ot", otJsonArray );
            // Ĭ����ٱȽϴ�  ��Ϊ��ټӰ�д��һ��ҳ��
            final boolean muchLeave = pagedListHolderLeave.getPageCount() > pagedListHolderOT.getPageCount();
            jsonObject.put( "page", Integer.parseInt( page ) );
            jsonObject.put( "realPage", muchLeave ? pagedListHolderLeave.getRealPage() : pagedListHolderOT.getRealPage() );
            jsonObject.put( "leave_pageCount", pagedListHolderLeave.getPageCount() );
            jsonObject.put( "ot_pageCount", pagedListHolderOT.getPageCount() );
            jsonObject.put( "nextPage", muchLeave ? pagedListHolderLeave.getNextPage() : pagedListHolderOT.getNextPage() );
            jsonObject.put( "pageSize", listPageSize );
            out.print( jsonObject.toString() );
            out.flush();
            out.close();
            return null;

         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listLeaveOT" );
   }

   // ��ȡ�Լ�����ٺͼӰ�
   public ActionForward readMessage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setAccountId( getAccountId( request, response ) );
         leaveHeaderVO.setCreateBy( getUserId( request, response ) );
         leaveHeaderService.read_Leave( leaveHeaderVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return null;
   }

   // ���ɾ��Ȩ��
   // Add by siuvan.xia at 2014-07-03
   private boolean hasDeleteRight( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      // ������½�״̬
      if ( KANUtil.filterEmpty( leaveHeaderVO.getStatus() ) != null && leaveHeaderVO.getStatus().equals( "1" ) )
         return true;

      // �Ƿ��й�����
      if ( KANUtil.filterEmpty( leaveHeaderVO.getWorkflowId() ) != null )
         return false;

      // �Ƿ�������
      if ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ) != null && Integer.valueOf( leaveHeaderVO.getRetrieveStatus() ) > 1 )
         return false;

      // ��ʼ��LeaveDetailService
      final LeaveDetailService leaveDetailService = ( LeaveDetailService ) getService( "leaveDetailService" );

      // ��ȡleaveDetailVO�б�
      final List< Object > leaveDetailVOs = leaveDetailService.getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

      int count = 0;
      // ���������ڱ���Ϊ���½������˻ء�״̬
      if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
      {
         // ��ʼ��TimesheetHeaderService
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         for ( Object leaveDetailVOObject : leaveDetailVOs )
         {
            if ( KANUtil.filterEmpty( ( ( LeaveDetailVO ) leaveDetailVOObject ).getTimesheetId() ) != null )
            {
               final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( ( ( LeaveDetailVO ) leaveDetailVOObject ).getTimesheetId() );
               if ( timesheetHeaderVO != null && ( timesheetHeaderVO.getStatus().equals( "1" ) || timesheetHeaderVO.getStatus().equals( "4" ) ) )
                  count++;
               else
                  return false;
            }
         }
      }

      if ( count == leaveDetailVOs.size() )
         return true;

      return true;
   }

   // ������ͷ
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );

      nameZHs.add( "���ID" );
      nameZHs.add( "������" );
      nameZHs.add( "��ʼʱ��" );
      nameZHs.add( "����ʱ��" );
      nameZHs.add( "���Сʱ" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "���������ģ�" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "������Ӣ�ģ�" );
      nameZHs.add( ( role.equals( "1" ) ? "������Ϣ" : "�Ͷ���ͬ" ) + "ID" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "�ͻ�ID" );
         nameZHs.add( "�ͻ�����" );
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
      nameSyses.add( "leaveHeaderId" );
      nameSyses.add( "decodeItemId" );
      nameSyses.add( "specialStartDate" );
      nameSyses.add( "specialEndDate" );
      nameSyses.add( "useHours" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "contractId" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
         nameSyses.add( "clientName" );
      }
      nameSyses.add( "decodeStatus" );
      nameSyses.add( "decodeModifyBy" );
      nameSyses.add( "decodeModifyDate" );
      return KANUtil.stringListToArray( nameSyses );
   }

   public ActionForward list_object_nativeApp( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final JSONObject jsonObject = new JSONObject();
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final PagedListHolder pagedListHolderLeave = new PagedListHolder();
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setEmployeeId( getEmployeeId( request, null ) );
         leaveHeaderVO.setAccountId( getAccountId( request, null ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            leaveHeaderVO.setCorpId( getCorpId( request, null ) );
         }
         leaveHeaderVO.setSortColumn( "unread desc,modifyDate" );
         leaveHeaderVO.setSortOrder( "desc" );
         pagedListHolderLeave.setObject( leaveHeaderVO );
         leaveHeaderService.getLeaveHeaderVOsByCondition( pagedListHolderLeave, false );
         final JSONArray leaveJsonArray = new JSONArray();
         for ( Object object : pagedListHolderLeave.getSource() )
         {
            ( ( LeaveHeaderVO ) object ).reset( null, request );
            final JSONObject tempObject = JSONObject.fromObject( ( LeaveHeaderVO ) object );
            leaveJsonArray.add( tempObject );
         }
         jsonObject.put( "leaveJsonArray", leaveJsonArray );

         // �Ӱ��
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final PagedListHolder pagedListHolderOT = new PagedListHolder();
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setEmployeeId( getEmployeeId( request, null ) );
         otHeaderVO.setAccountId( getAccountId( request, null ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            otHeaderVO.setCorpId( getCorpId( request, null ) );
         }
         otHeaderVO.setSortColumn( "unread desc,createDate" );
         otHeaderVO.setSortOrder( "desc" );
         pagedListHolderOT.setObject( otHeaderVO );
         otHeaderService.getOTHeaderVOsByCondition( pagedListHolderOT, false );
         final JSONArray otJsonArray = new JSONArray();
         for ( Object object : pagedListHolderOT.getSource() )
         {
            ( ( OTHeaderVO ) object ).reset( null, request );
            final JSONObject tempObject = JSONObject.fromObject( ( OTHeaderVO ) object );
            otJsonArray.add( tempObject );
         }
         jsonObject.put( "otJsonArray", otJsonArray );

         out.print( jsonObject.toString() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return null;
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
         String result = "���ʧ��";
         // ��ʼ��Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ȡ��ǰForm
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();

         leaveHeaderVO.setUnread( request.getParameter( "unread" ) );
         leaveHeaderVO.setContractId( request.getParameter( "contractId" ) );
         leaveHeaderVO.setItemId( request.getParameter( "itemId" ) );
         leaveHeaderVO.setEstimateStartDate( request.getParameter( "estimateStartDate" ) );
         leaveHeaderVO.setEstimateEndDate( request.getParameter( "estimateEndDate" ) );
         leaveHeaderVO.setEstimateLegalHours( request.getParameter( "estimateLegalHours" ) );
         leaveHeaderVO.setEstimateBenefitHours( request.getParameter( "estimateBenefitHours" ) );
         leaveHeaderVO.setDescription( KANUtil.decodeURLFromAjax( request.getParameter( "description" ) ) );
         leaveHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         leaveHeaderVO.setAccountId( getAccountId( request, response ) );
         leaveHeaderVO.setCorpId( getCorpId( request, response ) );

         if ( KANUtil.filterEmpty( leaveHeaderVO.getClientId() ) == null )
         {
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );
            if ( employeeContractVO != null )
            {
               leaveHeaderVO.setClientId( employeeContractVO.getClientId() );
            }
         }

         leaveHeaderVO.setActualLegalHours( "0" );
         leaveHeaderVO.setActualBenefitHours( "0" );
         leaveHeaderVO.setRetrieveStatus( "1" );
         leaveHeaderVO.setCreateBy( getUserId( request, response ) );
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );

         // �����Զ���Column
         leaveHeaderVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

         if ( ( "41".equals( leaveHeaderVO.getItemId() ) && KANUtil.filterEmpty( leaveHeaderVO.getEstimateBenefitHours() ) != null
               && KANUtil.filterEmpty( leaveHeaderVO.getEstimateLegalHours() ) != null && Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) == 0 && Double.valueOf( leaveHeaderVO.getEstimateLegalHours() ) == 0 )
               || ( "48".equals( leaveHeaderVO.getItemId() ) && KANUtil.filterEmpty( leaveHeaderVO.getEstimateLegalHours() ) != null && Double.valueOf( leaveHeaderVO.getEstimateLegalHours() ) == 0 )
               || ( "49".equals( KANUtil.filterEmpty( leaveHeaderVO.getEstimateBenefitHours() ) != null ) && Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) == 0 ) )
         {
            result = "����δ��������Ч�����Сʱ����";
         }
         else
         {
            // ���
            leaveHeaderService.insertLeaveHeader( leaveHeaderVO );
            leaveHeaderService.submitLeaveHeader( leaveHeaderVO );
            result = "success";
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

   public ActionForward to_objectModify_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �趨�Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         // ������ȡ�����
         String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( leaveId == null || leaveId.trim().isEmpty() )
         {
            leaveId = ( ( LeaveHeaderVO ) form ).getLeaveHeaderId();
         }

         // ���LeaveVO����
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );

         // �������
         if ( KANUtil.filterEmpty( leaveHeaderVO.getActualStartDate() ) != null && KANUtil.filterEmpty( leaveHeaderVO.getActualEndDate() ) != null )
         {
            leaveHeaderVO.setEstimateStartDate( leaveHeaderVO.getActualStartDate() );
            leaveHeaderVO.setEstimateEndDate( leaveHeaderVO.getActualEndDate() );
            leaveHeaderVO.setEstimateLegalHours( leaveHeaderVO.getActualLegalHours() );
            leaveHeaderVO.setEstimateBenefitHours( leaveHeaderVO.getActualBenefitHours() );
         }

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         leaveHeaderVO.reset( null, request );
         // �����޸����
         leaveHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "leaveHeaderForm", leaveHeaderVO );

         // ��ʼ��leftHours
         double leftHours = 0.0;

         // ��ʼ��hours
         double hours = Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) + Double.valueOf( leaveHeaderVO.getEstimateLegalHours() );

         final String leaveEndDate = getLeaveEndDate( leaveHeaderVO.getItemId(), getAccountId( request, null ), leaveHeaderVO.getContractId(), leaveHeaderVO.getEstimateStartDate(), leftHours, hours, null );

         request.setAttribute( "hasDeleteRight", hasDeleteRight( leaveHeaderVO ) );
         // д��request
         request.setAttribute( "leaveEndDate", leaveEndDate );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת�޸�ҳ��
      return mapping.findForward( "manageLeaveMobile" );
   }

   public ActionForward deleteLeaveOT_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String type = request.getParameter( "type" );
         final String id = request.getParameter( "id" );
         if ( "leave".equalsIgnoreCase( type ) )
         {
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( id );

            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyDate( new Date() );

            if ( leaveHeaderService.deleteLeaveHeader_cleanTS( leaveHeaderVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_DELETE );
               insertlog( request, leaveHeaderVO, Operate.DELETE, id, "delete leave for WeChar" );
            }
         }
         else if ( "ot".equalsIgnoreCase( type ) )
         {
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
            final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( id );
            otHeaderVO.setDeleted( "2" );
            otHeaderService.updateOTHeader( otHeaderVO );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /***
    * �����ύsick_leave
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward sick_leave( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Token��֤
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
            // ������ȡ�����
            final String leaveHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ���LeaveHeaderVO����
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );
            // ��������ʱ��
            leaveHeaderVO.setActualStartDate( ( ( LeaveHeaderVO ) form ).getEstimateStartDate() );
            leaveHeaderVO.setActualEndDate( ( ( LeaveHeaderVO ) form ).getEstimateEndDate() );
            leaveHeaderVO.setActualLegalHours( ( ( LeaveHeaderVO ) form ).getEstimateLegalHours() );
            leaveHeaderVO.setActualBenefitHours( ( ( LeaveHeaderVO ) form ).getEstimateBenefitHours() );

            if ( leaveHeaderService.sick_leave( leaveHeaderVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveHeaderVO.getLeaveHeaderId(), "����" );
            }
         }

         ( ( LeaveHeaderVO ) form ).reset();
         ( ( LeaveHeaderVO ) form ).setEmployeeNameZH( "" );
         ( ( LeaveHeaderVO ) form ).setEmployeeNameEN( "" );
         ( ( LeaveHeaderVO ) form ).setCertificateNumber( "" );
         ( ( LeaveHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

}
