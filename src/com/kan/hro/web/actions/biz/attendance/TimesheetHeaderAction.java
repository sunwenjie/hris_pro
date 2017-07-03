package com.kan.hro.web.actions.biz.attendance;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceService;
import com.kan.hro.service.inf.biz.attendance.TimesheetBatchService;
import com.kan.hro.service.inf.biz.attendance.TimesheetDetailService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

public class TimesheetHeaderAction extends BaseAction
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
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );

         // ��ȡTimesheetBatchVO modify by jacksun ͳ����Ϣ�޷�������Ȩ��
         /* final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByBatchId( batchId );*/

         // ���Action Form
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;

         //��������Ȩ��
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), TimesheetBatchAction.accessActionInHouse, timesheetHeaderVO );
            setDataAuth( request, response, timesheetHeaderVO );
         }

         final TimesheetBatchVO condition = new TimesheetBatchVO();
         condition.setBatchId( batchId );
         condition.setAccountId( timesheetHeaderVO.getAccountId() );
         condition.setCorpId( timesheetHeaderVO.getCorpId() );
         //         condition.setHasIn( timesheetHeaderVO.getHasIn() );
         //         condition.setNotIn( timesheetHeaderVO.getNotIn() );
         condition.setRulePublic( timesheetHeaderVO.getRulePublic() );
         condition.setRulePrivateIds( timesheetHeaderVO.getRulePrivateIds() );
         condition.setRulePositionIds( timesheetHeaderVO.getRulePositionIds() );
         condition.setRuleBranchIds( timesheetHeaderVO.getRuleBranchIds() );
         condition.setRuleBusinessTypeIds( timesheetHeaderVO.getRuleBusinessTypeIds() );
         condition.setRuleEntityIds( timesheetHeaderVO.getRuleEntityIds() );
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            condition.setClientId( BaseAction.getClientId( request, response ) );
         }
         final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByTimesheetBatchVO( condition );

         if ( timesheetBatchVO != null )
         {
            // д��request
            request.setAttribute( "timesheetBatchForm", timesheetBatchVO );
         }

         // ���û��ָ��������Ĭ�ϰ� monthly����
         if ( timesheetHeaderVO.getSortColumn() == null || timesheetHeaderVO.getSortColumn().isEmpty() )
         {
            timesheetHeaderVO.setSortOrder( "desc" );
            timesheetHeaderVO.setSortColumn( "a.headerId" );
         }

         //         // �����inHouse
         //         if ( getRole( request, null ).equals( "2" ) )
         //         {
         //            // �����HR����
         //            if ( !isHRFunction( request, response ) )
         //            {
         //               // ֻ�ܲ鿴�Լ��Ŀ��ڱ�
         //               timesheetHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         //            }
         //         }

         // ����subAction
         dealSubAction( timesheetHeaderVO, mapping, form, request, response );

         timesheetHeaderVO.setBatchId( batchId );

         // ����ɾ������
         if ( timesheetHeaderVO.getSubAction() != null && timesheetHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( timesheetHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( timesheetHeaderVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );

         //ȫ��ѡ��
         if ( "1".equals( request.getParameter( "selected" ) ) )
         {
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            timesheetHeaderService.getTimesheetHeaderVOsByCondition( pagedListHolder, false );
            String selectids = "";

            if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object pageObject : pagedListHolder.getSource() )
               {
                  selectids = selectids + ( ( TimesheetHeaderVO ) pageObject ).getEncodedId() + ",";
               }
            }
            timesheetHeaderVO.setSelectedIds( selectids );

         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         timesheetHeaderService.getTimesheetHeaderVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "timesheetHeaderHolder", pagedListHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listTimesheetHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listTimesheetHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // �����In House��¼��������������
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // ����Return
         return dealReturn( null, "generateTimesheetHeader", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

            // ��ȡForm
            final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
            timesheetHeaderVO.setOrderId( KANUtil.filterEmpty( timesheetHeaderVO.getOrderId(), "0" ) );
            timesheetHeaderVO.setCreateBy( getUserId( request, null ) );
            timesheetHeaderVO.setModifyBy( getUserId( request, null ) );

            // ����Timesheet
            final int rows = timesheetHeaderService.generateTimesheet( timesheetHeaderVO );

            if ( rows > 0 )
            {
               // ������ӳɹ����
               success( request, null, "�ɹ����� " + rows + " �����ڱ�" );
            }
            else
            {
               // ���ؾ�����
               if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
               {
                  warning( request, null, "���ڱ�δ������û�з������������ݣ�����Э��״̬��������׼�����¡��鵵���½��������������������Űࣻ����״̬��������׼����Ч��" );
               }
               else if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
               {
                  warning( request, null, "���ڱ�δ������û�з������������ݣ��Ͷ���ͬ״̬��������׼�����¡��鵵���½��������������������Űࣻ�������״̬��������׼����Ч��" );
               }
            }
         }
         else
         {
            // ����ʧ�ܱ��
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // ���Form����
         ( ( TimesheetHeaderVO ) form ).reset();
         ( ( TimesheetHeaderVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ����Return
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ����һ��token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // ������ȡ�����
         String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( headerId == null || headerId.trim().isEmpty() )
         {
            headerId = ( ( TimesheetHeaderVO ) form ).getHeaderId();
         }

         // ���TimesheetHeaderVO
         final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );
         timesheetHeaderVO.setSubAction( VIEW_OBJECT );
         timesheetHeaderVO.reset( null, request );

         // ��ȡ����Э���б�
         final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( timesheetHeaderVO.getContractId() );

         // ��Ա����Э��>н�귽�� - ���������Ƿ�Сʱ��
         if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
         {
            for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
            {
               final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
               if ( employeeContractSalaryVO.getItemId().equals( "1" ) && employeeContractSalaryVO.getSalaryType().equals( "2" ) )
               {
                  request.setAttribute( "byHour", true );
                  break;
               }
            }
         }
         // д��������
         request.setAttribute( "timesheetHeaderForm", timesheetHeaderVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תҳ��
      return mapping.findForward( "manageTimesheetHeader" );
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
            final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

            // ��ȡActionForm
            final TimesheetHeaderVO timesheetHeaderForm = ( TimesheetHeaderVO ) form;

            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ��ȡTimesheetHeaderVO
            final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );

            // ��ֵ
            timesheetHeaderVO.setTotalWorkHours( timesheetHeaderForm.getTotalWorkHours() );
            timesheetHeaderVO.setTotalWorkDays( timesheetHeaderForm.getTotalWorkDays() );
            timesheetHeaderVO.setTotalFullHours( timesheetHeaderForm.getTotalFullHours() );
            timesheetHeaderVO.setTotalFullDays( timesheetHeaderForm.getTotalFullDays() );
            timesheetHeaderVO.setWorkHoursArray( timesheetHeaderForm.getWorkHoursArray() );
            timesheetHeaderVO.setBaseArray( timesheetHeaderForm.getBaseArray() );
            timesheetHeaderVO.setDayTypeArray( timesheetHeaderForm.getDayTypeArray() );
            timesheetHeaderVO.setDescription( timesheetHeaderForm.getDescription() );
            // ��ȡ��¼�û�
            timesheetHeaderVO.setModifyBy( getUserId( request, response ) );
            timesheetHeaderVO.setModifyDate( new Date() );

            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );

            // ����ǿͻ��ύ
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               timesheetHeaderVO.reset( mapping, request );

               if ( timesheetHeaderService.submit_header( timesheetHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
               }
            }
            else
            {
               timesheetHeaderService.updateTimesheetHeader( timesheetHeaderVO );
               success( request, MESSAGE_TYPE_UPDATE );
            }

         }
         // ���FORM
         ( ( TimesheetHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   // �����ύ  - ��header
   public ActionForward submit_header( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡActionForm
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
         timesheetHeaderVO.setModifyBy( getUserId( request, null ) );

         // ��ù�ѡID��headerId��
         final String headerIds = timesheetHeaderVO.getSelectedIds();

         // ��ʼ��Service�ӿ�
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // ���ڹ�ѡID��headerId��
         if ( KANUtil.filterEmpty( headerIds ) != null )
         {
            for ( String headerId : headerIds.split( "," ) )
            {
               // ��ȡTimesheetHeaderVO
               final TimesheetHeaderVO tempTimesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( headerId ) );
               tempTimesheetHeaderVO.reset( null, request );
               tempTimesheetHeaderVO.setModifyBy( getUserId( request, response ) );
               tempTimesheetHeaderVO.setModifyDate( new Date() );

               // ����ύTimesheetHeaderVO
               if ( timesheetHeaderService.submit_header( tempTimesheetHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
               }
            }

            insertlog( request, timesheetHeaderVO, Operate.SUBMIT, null, "batchId:" + timesheetHeaderVO.getBatchId() + " headerIds:" + KANUtil.decodeSelectedIds( headerIds ) );
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // �����ύ  - ��header
   public ActionForward submit_header1( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡActionForm
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
         timesheetHeaderVO.setModifyBy( getUserId( request, null ) );

         // ��ù�ѡID��headerId��
         final String headerIds = timesheetHeaderVO.getSelectedIds();
         String deocodeHeaderIds = "";
         String employeeNameList = "";

         // ��ʼ��Service�ӿ�
         final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // ���ڹ�ѡID��headerId��
         if ( KANUtil.filterEmpty( headerIds ) != null )
         {
            for ( String headerId : headerIds.split( "," ) )
            {
               // ��ȡTimesheetHeaderVO
               final TimesheetHeaderVO tempTimesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( headerId ) );
               if ( tempTimesheetHeaderVO != null )
               {
                  if ( KANUtil.filterEmpty( employeeNameList ) == null )
                  {
                     employeeNameList = tempTimesheetHeaderVO.getEmployeeNameZH();
                  }
                  else
                  {
                     employeeNameList = employeeNameList + "��" + tempTimesheetHeaderVO.getEmployeeNameZH();
                  }
               }

               if ( KANUtil.filterEmpty( deocodeHeaderIds ) == null )
               {
                  deocodeHeaderIds = KANUtil.decodeStringFromAjax( headerId );
               }
               else
               {
                  deocodeHeaderIds = deocodeHeaderIds + "," + KANUtil.decodeStringFromAjax( headerId );
               }
            }
            // ���ݹ�ѡ��HeaderId��ȡTimesheetBatchVO
            final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByHeaderIds( deocodeHeaderIds );
            timesheetBatchVO.setDescription( employeeNameList );
            timesheetBatchVO.reset( null, request );
            timesheetBatchVO.setModifyBy( getUserId( request, null ) );
            timesheetBatchVO.setHeaderIds( headerIds );

            if ( timesheetBatchService.submit_batch( timesheetBatchVO ) == -1 )
               success( request, MESSAGE_TYPE_SUBMIT );
            else
               success( request, MESSAGE_TYPE_UPDATE );
         }

         return list_object( mapping, form, request, response );
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
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // ������������
         final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���ClientVO����
         final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );

         // ��������
         timesheetHeaderVO.setModifyBy( getUserId( request, response ) );
         timesheetHeaderVO.setModifyDate( new Date() );
         timesheetHeaderVO.reset( null, request );

         if ( timesheetHeaderService.submitTimesheetHeader( timesheetHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
            insertlog( request, timesheetHeaderVO, Operate.SUBMIT, headerId, null );
         }
         else
         {
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, timesheetHeaderVO, Operate.MODIFY, headerId, null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Add by siuvan.xia @ 2014-07-04
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // ���ActionForm
         TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( timesheetHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, timesheetHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( timesheetHeaderVO.getSelectedIds() ) );
            // �ָ�
            for ( String selectedId : timesheetHeaderVO.getSelectedIds().split( "," ) )
            {
               timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

               // ɾ��ֻ�ܡ��½����͡��˻ء�״̬�Ŀ����
               if ( timesheetHeaderVO != null && KANUtil.filterEmpty( timesheetHeaderVO.getStatus() ) != null
                     && ( timesheetHeaderVO.getStatus().equals( "1" ) || timesheetHeaderVO.getStatus().equals( "4" ) ) )
               {
                  timesheetHeaderVO.setModifyBy( getUserId( request, response ) );
                  timesheetHeaderVO.setModifyDate( new Date() );
                  timesheetHeaderService.deleteTimesheetHeader( timesheetHeaderVO );
               }
            }
         }

         // ���Selected IDs����Action
         ( ( TimesheetHeaderVO ) form ).setSelectedIds( "" );
         ( ( TimesheetHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         final TimesheetDetailService timesheetDetailService = ( TimesheetDetailService ) getService( "timesheetDetailService" );
         final TimesheetAllowanceService timesheetAllowanceService = ( TimesheetAllowanceService ) getService( "timesheetAllowanceService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // ID��ȡ�����
         final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( KANUtil.filterEmpty( headerId ) != null )
         {
            // ��ȡTimesheetHeaderVO
            final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );

            request.setAttribute( "timesheetHeaderForm", timesheetHeaderVO );

            // ��ȡEmployeeContractSalaryVO�б�
            final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( timesheetHeaderVO.getContractId() );

            // ��Ա����Э��>н�귽�� - ���������Ƿ�Сʱ��
            if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
            {
               for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
               {
                  if ( ( ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject ).getItemId().equals( "1" )
                        && ( ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject ).getSalaryType().equals( "2" ) )
                  {
                     request.setAttribute( "byHour", true );
                     break;
                  }
               }
            }

            // ��ʼ��PagedListHolder
            final PagedListHolder timesheetDetailHolder = new PagedListHolder();

            // ��ʼ�����������ѯ����
            final TimesheetDetailVO timesheetDetailVO = new TimesheetDetailVO();
            timesheetDetailVO.setHeaderId( headerId );
            timesheetDetailVO.setStatus( BaseVO.TRUE );
            timesheetDetailHolder.setObject( timesheetDetailVO );
            timesheetDetailService.getTimesheetDetailVOsByCondition( timesheetDetailHolder, false );

            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( timesheetDetailHolder, request );
            request.setAttribute( "timesheetDetailHolder", timesheetDetailHolder );

            // ��ʼ��PagedListHolder
            final PagedListHolder timesheetAllowanceHolder = new PagedListHolder();
            // ��ʼ��������ѯ����
            final TimesheetAllowanceVO timesheetAllowanceVO = new TimesheetAllowanceVO();
            timesheetAllowanceVO.setHeaderId( headerId );
            timesheetAllowanceVO.setStatus( BaseVO.TRUE );
            timesheetAllowanceHolder.setObject( timesheetAllowanceVO );
            timesheetAllowanceService.getTimesheetAllowanceVOsByCondition( timesheetAllowanceHolder, false );

            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( timesheetAllowanceHolder, request );
            // ���ؿ�������Info
            request.setAttribute( "timesheetAllowanceHolder", timesheetAllowanceHolder );

         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSpecialInfo" );
   }

   // ��١��Ӱ�ʱ�����Ƿ�����½����˻صĿ��ڱ�
   // Add by Siuvan Xia at 2014-8-28
   public void existAvailableTimesheet( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

         // �ύ��ʽ��1��detail�ύ��2��list�����ύ��3��list�����ύ
         final String submitType = request.getParameter( "submitType" );

         // �ύʱĬ��û����ʾ
         String flag = "2";

         // ��ʼ��һЩ����
         String accountId = getAccountId( request, null );
         String corpId = getCorpId( request, null );

         // 1��detail�ύ
         // 2��link�ύ��
         if ( submitType.equals( "1" ) || submitType.equals( "2" ) )
         {
            // ��ȡ����
            final String employeeId = request.getParameter( "employeeId" );
            final String contractId = request.getParameter( "contractId" );
            final String startDate = request.getParameter( "startDate" );
            flag = getFlag( accountId, corpId, employeeId, contractId, startDate );
         }
         // 3��checkBox�����ύ
         else if ( submitType.equals( "3" ) )
         {
            // ��ȡ����
            final String selectedIds = request.getParameter( "selectedIds" );
            final String objectName = request.getParameter( "objectName" );

            // ��������
            if ( objectName.equals( "leave" ) )
            {
               for ( String seletctedId : selectedIds.split( "," ) )
               {
                  final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( KANUtil.decodeStringFromAjax( seletctedId ) );
                  flag = getFlag( accountId, corpId, leaveHeaderVO.getEmployeeId(), leaveHeaderVO.getContractId(), leaveHeaderVO.getEstimateStartDate() );
                  if ( flag.equals( "1" ) )
                  {
                     break;
                  }
               }
            }
            // ����ǼӰ�
            else if ( objectName.equals( "ot" ) )
            {
               for ( String seletctedId : selectedIds.split( "," ) )
               {
                  final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( KANUtil.decodeStringFromAjax( seletctedId ) );
                  flag = getFlag( getAccountId( request, null ), getCorpId( request, null ), otHeaderVO.getEmployeeId(), otHeaderVO.getContractId(), otHeaderVO.getEstimateStartDate() );

                  if ( flag.equals( "1" ) )
                  {
                     break;
                  }
               }
            }
         }

         // Send to client
         out.print( flag );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // ���ڱ��Ƿ�Ϊ�½����˻�״̬
   private String getFlag( final String accountId, final String corpId, final String employeeId, final String contractId, final String startDate ) throws KANException
   {
      // �ύʱĬ��û����ʾ
      String flag = "2";
      // ��н��������
      String circleEndDay = "31";
      // �����·�
      String monthly = "";

      // ��ʼ��Service
      final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

      // ��ȡEmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
      // ��ȡEmployeeContractVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO == null ? "0"
            : employeeContractVO.getOrderId() );

      if ( clientOrderHeaderVO != null && KANUtil.filterEmpty( clientOrderHeaderVO.getCircleEndDay(), "0" ) != null )
      {
         circleEndDay = clientOrderHeaderVO.getCircleEndDay();
      }

      // �����·�
      monthly = KANUtil.getMonthlyByCondition( circleEndDay, startDate );

      final TimesheetHeaderVO searchTimesheetHeaderVO = new TimesheetHeaderVO();
      searchTimesheetHeaderVO.setAccountId( accountId );
      searchTimesheetHeaderVO.setCorpId( corpId );
      searchTimesheetHeaderVO.setEmployeeId( employeeId );
      searchTimesheetHeaderVO.setContractId( contractId );
      searchTimesheetHeaderVO.setMonthly( monthly );

      final List< Object > timesheetHeaderVOs = timesheetHeaderService.getTimesheetHeaderVOsByCondition( searchTimesheetHeaderVO );
      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetVOObject : timesheetHeaderVOs )
         {
            // ���½����˻�״̬
            if ( !( ( TimesheetHeaderVO ) timesheetVOObject ).getStatus().equals( "1" ) && !( ( TimesheetHeaderVO ) timesheetVOObject ).getStatus().equals( "4" ) )
            {
               flag = "1";
               break;
            }
         }
      }

      return flag;
   }
}
