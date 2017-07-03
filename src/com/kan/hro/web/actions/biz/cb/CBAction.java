package com.kan.hro.web.actions.biz.cb;

import java.util.Date;
import java.util.List;

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
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.service.inf.biz.cb.CBBatchService;
import com.kan.hro.service.inf.biz.cb.CBDetailService;
import com.kan.hro.service.inf.biz.cb.CBHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**   
 * �����ƣ�CBAction  
 * ���������̱�����
 * �����ˣ�Kevin  
 * ����ʱ�䣺2013-9-13  
 */
public class CBAction extends BaseAction
{
   public static String accessAction = "HRO_CB_BATCH_PREVIEW";
   // ��ǰAction��Ӧ��JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.cb.CBDTO";

   // ����StatusFlag��ȡ״ֵ̬Status
   private String getStatusesByStatusFlag( final String statusFlag )
   {
      // ��ʼ����Ĭ��Ϊ��Ԥ����
      String status = "1";

      if ( statusFlag != null && !statusFlag.isEmpty() )
      {
         if ( statusFlag.equalsIgnoreCase( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            return "2";
         }
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            return "3,4,5";
         }
      }

      return status;
   }

   /**
    * list_estimation
    * 
    * ��ʾԤ�������б�
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         /**
          * ҳ����ʾCBBatchVO List(����"statusFlag")
          */
         // ��ȡҳ��list��ǣ���Ԥ����preview����ȷ�ϡ�confirm�����ύ��submit��
         String statusFlag = request.getParameter( "statusFlag" );
         // ���statusFlag Ϊnull,Ĭ��ΪԤ��ҳ��
         if ( statusFlag == null )
         {
            statusFlag = CBBatchService.STATUS_FLAG_PREVIEW;
         }

         // ����Form��PageFlag��StatusFlag
         ( ( CBBatchVO ) form ).setStatusFlag( statusFlag );
         ( ( CBBatchVO ) form ).setPageFlag( CBBatchService.PAGE_FLAG_BATCH );

         // ���Action Form
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;

         String accessAction = "HRO_CB_BATCH_PREVIEW";
         if ( statusFlag.equals( CBBatchService.STATUS_FLAG_APPROVE ) )
         {
            accessAction = "HRO_CB_BATCH_PREVIEW";
         }
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_CB_BATCH_CONFIRM";
         }
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_CB_BATCH_SUBMIT";
         }
         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, cbBatchVO );
         setDataAuth( request, response, cbBatchVO );

         cbBatchVO.setOrderId( KANUtil.filterEmpty( cbBatchVO.getOrderId(), "0" ) );
         // ����statusFlag����CBBatchVO��statusֵ��1:�½���2:��׼��3:ȷ�ϣ�4:�ύ��-����������Ӧ״̬��  CBBatchVO List
         cbBatchVO.setStatus( getStatusesByStatusFlag( cbBatchVO.getStatusFlag() ) );

         // ��Ҫ���õ�ǰ�û�AccountId
         cbBatchVO.setAccountId( getAccountId( request, response ) );

         decodedObject( cbBatchVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder cbBatchHolder = new PagedListHolder();

         // ���û��ָ��������Ĭ�ϰ� ������ˮ������
         if ( cbBatchVO.getSortColumn() == null || cbBatchVO.getSortColumn().trim().equals( "" ) )
         {
            cbBatchVO.setSortColumn( "batchId" );
            cbBatchVO.setSortOrder( "desc" );
         }

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbBatchVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // ���뵱ǰҳ
         cbBatchHolder.setPage( page );
         // ���뵱ǰֵ����
         cbBatchHolder.setObject( cbBatchVO );
         // ����ҳ���¼����
         cbBatchHolder.setPageSize( listPageSize_medium );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         cbBatchService.getCBBatchVOsByCondition( cbBatchHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( cbBatchHolder, request );

         // Holder��д��Request����
         request.setAttribute( "cbBatchHolder", cbBatchHolder );
         // д��pageFlag
         request.setAttribute( "pageFlag", CBBatchService.PAGE_FLAG_BATCH );

         // �Ƿ���ʾ������ť
         request.setAttribute( "javaObjectName", javaObjectName );
         showExportButton( mapping, form, request, response );

         /**
          * ҳ��ת�����
          */
         // �걨Ԥ���б�
         if ( statusFlag.equals( CBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            request.setAttribute( "statusFlag", CBBatchService.STATUS_FLAG_PREVIEW );
            // �����ajax����
            if ( new Boolean( ajax ) )
            {
               // д��Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listCBBatchTablePreview" );
            }
            return mapping.findForward( "listCBBatchPreview" );
         }
         // �걨ȷ���б�
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            request.setAttribute( "statusFlag", CBBatchService.STATUS_FLAG_CONFIRM );
            // �����ajax����
            if ( new Boolean( ajax ) )
            {
               // д��Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listCBBatchTableConfirm" );
            }
            return mapping.findForward( "listCBBatchConfirm" );
         }
         // �ύ�����б�
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            request.setAttribute( "statusFlag", CBBatchService.STATUS_FLAG_SUBMIT );
            // �����ajax����
            if ( new Boolean( ajax ) )
            {
               // д��Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listCBBatchTableSubmit" );
            }
            return mapping.findForward( "listCBBatchSubmit" );
         }
         else
         {
            return mapping.findForward( "" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * to_estimationNew
    * 
    * ת�򴴽�Ԥ�����ν���
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_estimationNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( CBBatchVO ) form ).setSubAction( CREATE_OBJECT );
      // ����״̬Ĭ��ֵ
      ( ( CBBatchVO ) form ).setStatus( "1" );

      // ����Ǵӿͻ��˵�¼
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         ( ( CBBatchVO ) form ).setCorpId( getCorpId( request, null ) );
         // ���������б�
         passClientOrders( request, response );
      }

      // �������������ҳ��
      request.setAttribute( "pageFlag", "none" );

      // ��ת���½�����
      return mapping.findForward( "manageCB" );
   }

   /**
    * Add Estimation
    * 
    * ����Ԥ�����Σ�״̬Ϊ�½�
    * �½���ҵ����Ա���԰����λ����Э���˻�
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-09
   public ActionForward add_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // �����ظ��ύ
      if ( this.isTokenValid( request, true ) )
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         // ��ȡForm
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setAccountId( getAccountId( request, response ) );
         cbBatchVO.setCreateBy( getUserId( request, response ) );
         cbBatchVO.setModifyBy( getUserId( request, response ) );

         // �����Զ���Column
         cbBatchVO.setRemark1( saveDefineColumns( request, "" ) );
         // ����ִ�п�ʼʱ��
         cbBatchVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

         // ���ս�������������ȡ���ϵķ���Э��DTO
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, null ) );
         employeeContractVO.setEntityId( cbBatchVO.getEntityId() );
         employeeContractVO.setBusinessTypeId( cbBatchVO.getBusinessTypeId() );
         employeeContractVO.setCbId( cbBatchVO.getCbId() );
         employeeContractVO.setClientId( cbBatchVO.getClientId() );
         employeeContractVO.setCorpId( cbBatchVO.getCorpId() );
         employeeContractVO.setOrderId( KANUtil.filterEmpty( cbBatchVO.getOrderId(), "0" ) );
         employeeContractVO.setContractId( cbBatchVO.getContractId() );
         // EmployeeId��ʱ����δ��
         employeeContractVO.setEmployeeId( null );
         employeeContractVO.setMonthly( cbBatchVO.getMonthly() );
         employeeContractVO.setCbStartDate( KANUtil.formatDate( KANUtil.getLastDate( cbBatchVO.getMonthly() ), "yyyy-MM-dd" ) );
         employeeContractVO.setCbEndDate( KANUtil.formatDate( KANUtil.getFirstDate( cbBatchVO.getMonthly() ), "yyyy-MM-dd" ) );

         //��������Ȩ��
         //String accessAction = "HRO_CB_BATCH_PREVIEW";
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractVO );
         setDataAuth( request, response, employeeContractVO );
         final List< ServiceContractDTO > serviceContractDTOs = employeeContractService.getServiceContractDTOsByCondition( employeeContractVO, EmployeeContractService.FLAG_CB );

         // ����������������Э��
         if ( serviceContractDTOs != null && serviceContractDTOs.size() > 0 )
         {
            for ( ServiceContractDTO serviceContractDTO : serviceContractDTOs )
            {
               serviceContractDTO.calculateCB( request );
            }

            // ����ִ�н���ʱ��
            cbBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

            // ����Service�����洢�̱����ݣ���Ҫ����Transaction��
            final int rows = cbBatchService.insertCBBatch( cbBatchVO, serviceContractDTOs );

            if ( rows > 0 )
            {
               // ������ӳɹ����
               success( request, null, "�ɹ��������� " + cbBatchVO.getBatchId() + " ��" );
               insertlog( request, cbBatchVO, Operate.ADD, cbBatchVO.getBatchId(), null );
            }
            else
            {
               // ���ؾ�����
               warning( request, null, "����δ������" + ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "��Ա" : "Ա��" ) + "�ѱ���������ݲ�������" );
            }
         }
         else
         {
            // ���ؾ�����
            warning( request, null, "����δ������" + ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "��Ա" : "Ա��" ) + "�ѱ���������ݲ�������" );
         }
      }
      else
      {
         // ����ʧ�ܱ��
         error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
      }

      // ���Form����
      ( ( CBBatchVO ) form ).reset();
      ( ( CBBatchVO ) form ).setBatchId( "" );

      return list_estimation( mapping, form, request, response );
   }

   /**
    * submit_estimation
    * 
    * �ύԤ�����Σ�״̬Ϊ��׼��ͨ����׼��ҵ����Ա������
    * ��׼���̱�������Ա���԰����λ����Э���˻�
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchVO
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         // �ύ����׼
         cbBatchService.submit( cbBatchVO );

         insertlog( request, cbBatchVO, Operate.SUBMIT, null, "submit_estimation:" + KANUtil.decodeSelectedIds( cbBatchVO.getSelectedIds() ) );

         // ����pageFlag ��ת
         return forward( cbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * doReturnActByPageFlag
    * ����pageFlag ��ת
    * @param pageFlag
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   private ActionForward forward( String pageFlag, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( pageFlag.equalsIgnoreCase( CBBatchService.PAGE_FLAG_HEADER ) )
      {
         CBHeaderVO cbHeader = new CBHeaderVO();
         cbHeader.reset();
         cbHeader.setBatchId( "" );
         cbHeader.setStatus( getStatusesByStatusFlag( ( ( CBBatchVO ) form ).getStatusFlag() ) );
         return new CBHeaderAction().list_object( mapping, cbHeader, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( CBBatchService.PAGE_FLAG_DETAIL ) )
      {
         return to_cbDetail( mapping, form, request, response );
      }
      else
      {
         return list_estimation( mapping, form, request, response );
      }
   }

   /**
    * rollback
    * 
    * �˻�Ԥ�����λ����Э�� - ֻ���½�����׼״̬�����˻ع���
    * �˻ؼ�¼ - �̱�������������ɾ��������Э���е��̱�״̬��Ϊ�����̱���
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );
         // ���Action Form
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setStatus( getStatusesByStatusFlag( cbBatchVO.getStatusFlag() ) );

         // �˻�
         cbBatchService.rollback( cbBatchVO );

         insertlog( request, cbBatchVO, Operate.ROllBACK, null, KANUtil.decodeSelectedIds( cbBatchVO.getSelectedIds() ) );

         // ���Selected IDs����Action
         ( ( CBBatchVO ) form ).setSelectedIds( "" );

         // ����pageFlag ��ת
         return forward( cbBatchVO.getPageFlag(), mapping, form, request, response );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * submit_confirmation
    * 
    * �ύ�̱����� - ���̱�������״̬Ϊȷ��
    * �ύ���޷��˻أ�����������̱�����������ƫ���������
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_confirmation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchVO
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         // �ύ����׼
         cbBatchService.submit( cbBatchVO );

         insertlog( request, cbBatchVO, Operate.SUBMIT, null, "submit_confirmation:" + KANUtil.decodeSelectedIds( cbBatchVO.getSelectedIds() ) );

         // ����pageFlag ��ת
         return forward( cbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * submit_settlement
    * 
    * �ύ�̱����� - �����㣬״̬Ϊ�ύ
    * �ύ�����ģ��ɼ�
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_settlement( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchVO
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         // �ύ����׼
         cbBatchService.submit( cbBatchVO );

         insertlog( request, cbBatchVO, Operate.SUBMIT, null, "submit_settlement:" + KANUtil.decodeSelectedIds( cbBatchVO.getSelectedIds() ) );

         // ����pageFlag ��ת
         return forward( cbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * to_cbDetail
    * 
    * ��ʾ���Ρ�����Э�顢�̱����������Ϣ���̱�������ϸ�б�
    * ��Ҫ��ʾ��ǰ�̱������Ļ�����
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_cbDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         /**
          * ��ȡ���Ρ�����Э�顢�̱����������Ϣ
          */
         // ��ʼ��Service�ӿ�
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );
         final CBHeaderService cbHeaderService = ( CBHeaderService ) getService( "cbHeaderService" );
         final CBDetailService cbDetailService = ( CBDetailService ) getService( "cbDetailService" );

         // ��õ�ǰ��������ID
         final String cbBatchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ��÷���Э������ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // ����̱���������HeaderID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );

         // ��ʼ��CBHeaderVO
         CBHeaderVO cbHeaderVO = new CBHeaderVO();
         cbHeaderVO.setBatchId( cbBatchId );
         cbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         cbHeaderVO.setContractId( contractId );
         cbHeaderVO.setAccountId( getAccountId( request, response ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // ����HeaderId
         cbHeaderVO.setHeaderId( headerId );

         // ��ȡ�̱�����
         final List< Object > cbHeaderVOs = cbHeaderService.getCBHeaderVOsByCondition( cbHeaderVO );

         if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
         {
            cbHeaderVO = ( CBHeaderVO ) cbHeaderVOs.get( 0 );
         }

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         cbHeaderVO.reset( null, request );

         // ��ʼ��CBBatchVO
         CBBatchVO cbBatchVO = new CBBatchVO();
         cbBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         cbBatchVO.setBatchId( cbBatchId );
         cbBatchVO.setAccountId( getAccountId( request, response ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbBatchVO.setCorpId( getCorpId( request, response ) );
         }
         final List< Object > cbBatchVOs = cbBatchService.getCBBatchVOsByCondition( cbBatchVO );

         if ( cbBatchVOs != null && cbBatchVOs.size() > 0 )
         {
            cbBatchVO = ( CBBatchVO ) cbBatchVOs.get( 0 );
         }

         cbBatchVO.setPageFlag( CBBatchService.PAGE_FLAG_DETAIL );
         cbBatchVO.setStatusFlag( request.getParameter( "statusFlag" ) );
         cbBatchVO.reset( null, request );

         String accessAction = "HRO_CB_BATCH_PREVIEW";
         if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_APPROVE ) )
         {
            accessAction = "HRO_CB_BATCH_PREVIEW";
         }
         else if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_CB_BATCH_CONFIRM";
         }
         else if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_CB_BATCH_SUBMIT";
         }
         request.setAttribute( "authAccessAction", accessAction );
         request.setAttribute( "cbBatchForm", cbBatchVO );
         request.setAttribute( "cbHeaderVO", cbHeaderVO );

         /**
          * ��ȡ�̱�������ϸ�б�
          */
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder cbDetailHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         cbDetailHolder.setPage( page );

         // �½�CBDetailVO���ڲ�ѯ
         final CBDetailVO cbDetailVO = new CBDetailVO();
         cbDetailVO.setHeaderId( headerId );
         cbDetailVO.setAccountId( getAccountId( request, response ) );
         cbDetailVO.setStatus( getStatusesByStatusFlag( cbBatchVO.getStatusFlag() ) );

         // ������������ֶ�
         cbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         cbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // ���û��ָ��������Ĭ�ϰ� �̱�������ϸ��ˮ������
         if ( cbDetailVO.getSortColumn() == null || cbDetailVO.getSortColumn().trim().equals( "" ) )
         {
            cbDetailVO.setSortColumn( "itemId" );
         }

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // ���뵱ǰֵ����
         cbDetailHolder.setObject( cbDetailVO );
         // ����ҳ���¼����
         cbDetailHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         cbDetailVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         cbDetailService.getCBDetailVOsByCondition( cbDetailHolder, true );
         refreshHolder( cbDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "cbDetailHolder", cbDetailHolder );
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

         // ����������̱���ϸ����
         if ( cbDetailHolder == null || cbDetailHolder.getHolderSize() == 0 )
         {
            CBHeaderVO cbHeader = new CBHeaderVO();
            cbHeader.reset();
            cbHeader.setBatchId( "" );
            return new CBHeaderAction().list_object( mapping, cbHeader, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // Ajax����
      return mapping.findForward( "listDetail" );
   }

   /**  
    * Export Object
    * ����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward export_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );
         // ���Action Form
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         // ����״ֵ̬
         cbBatchVO.setStatus( getStatusesByStatusFlag( cbBatchVO.getStatusFlag() ) );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰֵ����
         pagedListHolder.setObject( cbBatchVO );
         // ����Service���������ö��󷵻�
         cbBatchService.getCBDTOsByCondition( pagedListHolder );
         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         return new DownloadFileAction().specialExportList( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

}
