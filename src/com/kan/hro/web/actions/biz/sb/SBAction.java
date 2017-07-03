package com.kan.hro.web.actions.biz.sb;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.sb.SBBatchService;
import com.kan.hro.service.inf.biz.sb.SBDetailService;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;
import com.kan.hro.service.inf.biz.vendor.VendorService;
import com.kan.hro.web.actions.biz.vendor.VendorAction;

/**   
 * �����ƣ�SBAction  
 * ���������籣����
 * �����ˣ�Kevin  
 * ����ʱ�䣺2013-9-13  
 */
public class SBAction extends BaseAction
{
   // Access Action of Preview
   public static String ACCESS_ACTION_PREVIEW = "HRO_SB_BATCH_PREVIEW";
   // Access Action of Confirm
   public static String ACCESS_ACTION_CONFIRM = "HRO_SB_BATCH_CONFIRM";
   // Access Action of Submit
   public static String ACCESS_ACTION_SUBMIT = "HRO_SB_BATCH_SUBMIT";
   // ��ǰAction��Ӧ��JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";

   /**
    * List Estimation
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
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward list_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // �ж��б��Ƿ���Ҫ��ӵ�������
         if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
               && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
         {
            request.setAttribute( "isExportExcel", "1" );
         }

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         // ��ȡҳ���ǣ���Ԥ���� - preview����ȷ�� - ��confirm�����ύ �� - submit��
         String statusFlag = request.getParameter( "statusFlag" );

         if ( statusFlag == null )
         {
            statusFlag = SBBatchService.STATUS_FLAG_PREVIEW;
         }

         // ����PageFlag��StatusFlag
         ( ( SBBatchVO ) form ).setStatusFlag( statusFlag );
         ( ( SBBatchVO ) form ).setPageFlag( SBBatchService.PAGE_FLAG_BATCH );

         // ���Action Form
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;

         String accessAction = "HRO_SB_BATCH_PREVIEW";
         if ( statusFlag.equals( SBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            accessAction = "HRO_SB_BATCH_PREVIEW";
         }
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_SB_BATCH_CONFIRM";
         }
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_SB_BATCH_SUBMIT";
            final Iterator< MappingVO > iterators = sbBatchVO.getStatuses().iterator();
            //�ύ�����ѯ3,4,5 ״̬
            while ( iterators.hasNext() )
            {
               MappingVO mappingVO = iterators.next();
               if ( !mappingVO.getMappingId().equals( "0" ) && !mappingVO.getMappingId().equals( "3" ) && !mappingVO.getMappingId().equals( "4" )
                     && !mappingVO.getMappingId().equals( "5" ) )
               {
                  iterators.remove();
               }

            }

         }
         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, sbBatchVO );
         setDataAuth( request, response, sbBatchVO );
         sbBatchVO.setOrderId( KANUtil.filterEmpty( sbBatchVO.getOrderId(), "0" ) );

         if ( KANUtil.filterEmpty( sbBatchVO.getStatus(), "0" ) == null )
         {
            // ����Statusֵ������StatusFlag��
            sbBatchVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );
         }

         // ���õ�ǰ�û�AccountId
         sbBatchVO.setAccountId( getAccountId( request, response ) );

         decodedObject( sbBatchVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbBatchHolder = new PagedListHolder();

         // ���뵱ǰҳ
         sbBatchHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� BatchId����
         if ( sbBatchVO.getSortColumn() == null || sbBatchVO.getSortColumn().isEmpty() )
         {
            sbBatchVO.setSortColumn( "batchId" );
            sbBatchVO.setSortOrder( "desc" );
         }

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // ��ӹ�Ӧ��������
         sbBatchVO.setVendors( new VendorAction().list_option( mapping, form, request, response ) );

         // ���뵱ǰֵ����
         sbBatchHolder.setObject( sbBatchVO );
         // ����ҳ���¼����
         sbBatchHolder.setPageSize( listPageSize_medium );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbBatchService.getSBBatchVOsByCondition( sbBatchHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( sbBatchHolder, request );

         // Holder��д��Request����
         request.setAttribute( "sbBatchHolder", sbBatchHolder );
         // д��pageFlag
         request.setAttribute( "pageFlag", SBBatchService.PAGE_FLAG_BATCH );

         /**
          * ҳ��ת����
          */
         // �걨Ԥ���б�  
         if ( statusFlag.equals( SBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            request.setAttribute( "statusFlag", SBBatchService.STATUS_FLAG_PREVIEW );

            // �����Ajax����
            if ( new Boolean( ajax ) )
            {
               // д��Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listSBBatchTablePreview" );
            }

            return mapping.findForward( "listSBBatchPreview" );
         }
         // �걨ȷ���б�
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            request.setAttribute( "statusFlag", SBBatchService.STATUS_FLAG_CONFIRM );

            // �����Ajax����
            if ( new Boolean( ajax ) )
            {
               // д��Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listSBBatchTableConfirm" );
            }

            return mapping.findForward( "listSBBatchConfirm" );
         }
         // �ύ�����б�
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            request.setAttribute( "statusFlag", SBBatchService.STATUS_FLAG_SUBMIT );

            // �����Ajax����
            if ( new Boolean( ajax ) )
            {
               // д��Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listSBBatchTableSubmit" );
            }

            return mapping.findForward( "listSBBatchSubmit" );
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
    * To Estimation New
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
   // Reviewed by Kevin Jin at 2013-10-09
   public ActionForward to_estimationNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      SBBatchVO sbBatchVO = ( SBBatchVO ) form;
      // ����Sub Action
      sbBatchVO.setSubAction( CREATE_OBJECT );
      // ����״̬Ĭ��ֵ
      sbBatchVO.setStatus( "1" );

      // ����Ǵӿͻ��˵�¼
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         ( ( SBBatchVO ) form ).setCorpId( getCorpId( request, null ) );

         // ���������б�
         passClientOrders( request, response );
      }

      // �������������ҳ��
      request.setAttribute( "pageFlag", "none" );

      // ��ת���½�����
      return mapping.findForward( "manageSB" );
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
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         // ��ʼ������ֵ����
         request.setAttribute( "errorCount", 0 );

         // ���ҳ������ֵ���������ֵ��Ϊ�ղ���Ҫ��֤��
         if ( KANUtil.filterEmpty( ( ( SBBatchVO ) form ).getClientId(), "0" ) != null )
         {
            checkClientId( mapping, form, request, response );
         }
         if ( KANUtil.filterEmpty( ( ( SBBatchVO ) form ).getOrderId(), "0" ) != null )
         {
            checkOrderId( mapping, form, request, response );
         }
         if ( KANUtil.filterEmpty( ( ( SBBatchVO ) form ).getContractId(), "0" ) != null )
         {
            checkEmployeeContractId( mapping, form, request, response );
         }

         // �����Ƿ��д�����תҳ��
         if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
         {
            return to_estimationNew( mapping, form, request, response );
         }

         // ��ȡForm
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;

         sbBatchVO.setOrderId( KANUtil.filterEmpty( sbBatchVO.getOrderId(), "0" ) );
         sbBatchVO.setCreateBy( getUserId( request, response ) );
         sbBatchVO.setModifyBy( getUserId( request, response ) );

         // ����ִ�п�ʼʱ��
         sbBatchVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

         // ���ս�������������ȡ���ϵķ���Э��DTO
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, null ) );
         employeeContractVO.setEntityId( sbBatchVO.getEntityId() );
         employeeContractVO.setBusinessTypeId( sbBatchVO.getBusinessTypeId() );
         employeeContractVO.setSbCityId( sbBatchVO.getCityId() );
         employeeContractVO.setClientId( sbBatchVO.getClientId() );
         employeeContractVO.setCorpId( sbBatchVO.getCorpId() );
         employeeContractVO.setOrderId( sbBatchVO.getOrderId() );
         employeeContractVO.setContractId( sbBatchVO.getContractId() );
         // EmployeeId��ʱ����δ��
         employeeContractVO.setEmployeeId( null );
         employeeContractVO.setMonthly( sbBatchVO.getMonthly() );
         employeeContractVO.setSbStartDate( KANUtil.formatDate( KANUtil.getLastDate( sbBatchVO.getMonthly() ), "yyyy-MM-dd" ) );
         employeeContractVO.setSbEndDate( KANUtil.formatDate( KANUtil.getFirstDate( sbBatchVO.getMonthly() ), "yyyy-MM-dd" ) );

         //�籣��������
         employeeContractVO.setSbType( sbBatchVO.getSbType() );
         //Ա���籣״̬
         if ( sbBatchVO.getSbStatusArray() != null && sbBatchVO.getSbStatusArray().length > 0 )
         {
            employeeContractVO.setSbStatusArray( sbBatchVO.getSbStatusArray() );
         }
         else
         {
            employeeContractVO.setSbStatusArray( null );
         }

         //��������Ȩ��
         //String accessAction = "HRO_SB_BATCH_PREVIEW";
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractVO );
         setDataAuth( request, response, employeeContractVO );

         final List< ServiceContractDTO > serviceContractDTOs = employeeContractService.getServiceContractDTOsByCondition( employeeContractVO, EmployeeContractService.FLAG_SB );

         // ����������������Э��
         if ( serviceContractDTOs != null && serviceContractDTOs.size() > 0 )
         {
            for ( ServiceContractDTO serviceContractDTO : serviceContractDTOs )
            {
               serviceContractDTO.calculateSB( request );
            }

            // ����ִ�н���ʱ��
            sbBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

            // ����Service�����洢�籣���ݣ���Ҫ����Transaction��
            final int rows = sbBatchService.insertSBBatch( sbBatchVO, serviceContractDTOs );

            if ( rows > 0 )
            {
               // ������ӳɹ����
               success( request, null, "�ɹ��������� " + sbBatchVO.getBatchId() + " ��" );
               insertlog( request, sbBatchVO, Operate.ADD, sbBatchVO.getBatchId(), "�걨Ԥ��" );
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
      ( ( SBBatchVO ) form ).reset();
      ( ( SBBatchVO ) form ).setBatchId( "" );

      return list_estimation( mapping, form, request, response );
   }

   /**
    * Submit Estimation
    * 
    * �ύԤ�����Σ�״̬Ϊ��׼��ͨ����׼��ҵ����Ա������
    * ��׼���籣������Ա���԰����λ����Э���˻�
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward submit_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchVO
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         sbBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         // �ύ����׼
         sbBatchService.submit( sbBatchVO );

         insertlog( request, sbBatchVO, Operate.SUBMIT, null, "submit_estimation:" + KANUtil.decodeSelectedIds( sbBatchVO.getSelectedIds() ) );

         // ����pageFlag ��ת
         return forward( sbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * submit_confirmation
    * 
    * �ύ�籣���� - ���籣������״̬Ϊȷ��
    * �ύ���޷��˻أ�����������籣����������ƫ���������
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward submit_confirmation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchVO
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         sbBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         //���ɹ�������Ҫ�Ĳ���
         sbBatchService.generateHistoryVOForWorkflow( sbBatchVO );
         // �ύ����׼
         sbBatchService.submit( sbBatchVO );

         insertlog( request, sbBatchVO, Operate.SUBMIT, null, "submit_confirmation:" + KANUtil.decodeSelectedIds( sbBatchVO.getSelectedIds() ) );

         if ( getRole( request, response ).equals( KANConstants.ROLE_VENDOR ) )
         {
            // ����ǹ�Ӧ�̵�¼
            return to_sbDetail_inVendor( mapping, form, request, response );
         }

         // ����pageFlag ��ת
         return forward( sbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * submit_settlement
    * 
    * �ύ�籣���� - �����㣬״̬Ϊ�ύ
    * �ύ�����ģ��ɼ�
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward submit_settlement( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchVO
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         sbBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         // �ύ����׼
         sbBatchService.submit( sbBatchVO );

         insertlog( request, sbBatchVO, Operate.SUBMIT, null, "submit_settlement:" + KANUtil.decodeSelectedIds( sbBatchVO.getSelectedIds() ) );

         // ����pageFlag ��ת
         return forward( sbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Rollback
    * 
    * �˻�Ԥ�����λ����Э�� - ֻ���½�����׼״̬�����˻ع���
    * �˻ؼ�¼ - �籣������������ɾ��������Э���е��籣״̬��Ϊ�����籣��
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
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         // ���Action Form
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         sbBatchVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );

         Map< String, String > statusMap = new HashMap< String, String >();
         statusMap.put( "statusAdd", request.getParameter( "statusAddHidden" ) );
         statusMap.put( "statusBack", request.getParameter( "statusBackHidden" ) );

         // ɾ����Ӧ��
         sbBatchService.rollback( sbBatchVO, statusMap );

         insertlog( request, sbBatchVO, Operate.ROllBACK, null, "rollback" + KANUtil.decodeSelectedIds( sbBatchVO.getSelectedIds() ) );

         // ���Selected IDs����Action
         ( ( SBBatchVO ) form ).setSelectedIds( "" );

         // ����pageFlag ��ת
         return forward( sbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Forward
    * ����Page Flag��ת
    *
    * @param pageFlag
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   private ActionForward forward( String pageFlag, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
      {
         SBHeaderVO sbHeader = new SBHeaderVO();
         sbHeader.setBatchId( "" );
         sbHeader.setStatus( getStatusesByStatusFlag( ( ( SBBatchVO ) form ).getStatusFlag() ) );
         return new SBHeaderAction().list_object( mapping, sbHeader, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_DETAIL ) )
      {
         return to_sbDetail( mapping, form, request, response );
      }
      else
      {
         return list_estimation( mapping, form, request, response );
      }
   }

   /**
    * To SBDetail
    * 
    * ��ʾ���Ρ�����Э�顢�籣���������Ϣ���籣������ϸ�б�
    * ��Ҫ��ʾ��ǰ�籣�����Ļ�����
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward to_sbDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // �ж��б��Ƿ���Ҫ��ӵ�������
         if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
               && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
         {
            request.setAttribute( "isExportExcel", "1" );
         }

         // ��ʼ��Service�ӿ�
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );

         // ��õ�������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ��÷���Э������ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // ����籣��������ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );

         // ��ʼ��SBHeaderVO
         SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setBatchId( batchId );
         sbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbHeaderVO.setContractId( contractId );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // ����HeaderId
         sbHeaderVO.setHeaderId( headerId );

         final List< Object > sbHeaderVOs = sbHeaderService.getSBHeaderVOsByCondition( sbHeaderVO );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            sbHeaderVO = ( SBHeaderVO ) sbHeaderVOs.get( 0 );
         }

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         sbHeaderVO.reset( null, request );

         // ��ʼ��SBBatchVO
         SBBatchVO sbBatchVO = new SBBatchVO();
         sbBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbBatchVO.setBatchId( batchId );
         sbBatchVO.setAccountId( getAccountId( request, response ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setCorpId( getCorpId( request, response ) );
         }
         final List< Object > sbBatchVOs = sbBatchService.getSBBatchVOsByCondition( sbBatchVO );

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            sbBatchVO = ( SBBatchVO ) sbBatchVOs.get( 0 );
         }

         sbBatchVO.setPageFlag( SBBatchService.PAGE_FLAG_DETAIL );
         sbBatchVO.setStatusFlag( request.getParameter( "statusFlag" ) );
         sbBatchVO.reset( null, request );
         request.setAttribute( "sbBatchForm", sbBatchVO );
         request.setAttribute( "sbHeaderVO", sbHeaderVO );

         String accessAction = "HRO_SB_BATCH_PREVIEW";
         if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            accessAction = "HRO_SB_BATCH_PREVIEW";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_SB_BATCH_CONFIRM";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_SB_BATCH_SUBMIT";
         }
         request.setAttribute( "authAccessAction", accessAction );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbDetailHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         sbDetailHolder.setPage( page );

         // ��ʼ��SBDetailVO
         final SBDetailVO sbDetailVO = new SBDetailVO();
         sbDetailVO.setHeaderId( headerId );
         sbDetailVO.setAccountId( getAccountId( request, response ) );
         sbDetailVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );

         // ������������ֶ�
         sbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // ���û��ָ��������Ĭ�ϰ���ĿID����
         if ( sbDetailVO.getSortColumn() == null || sbDetailVO.getSortColumn().isEmpty() )
         {
            sbDetailVO.setSortColumn( "itemId" );
         }

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // ���뵱ǰֵ����
         sbDetailHolder.setObject( sbDetailVO );
         // ����ҳ���¼����
         sbDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbDetailService.getSBDetailVOsByCondition( sbDetailHolder, true );
         refreshHolder( sbDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "sbDetailHolder", sbDetailHolder );

         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

         // ����������籣��ϸ����
         if ( sbDetailHolder == null || sbDetailHolder.getHolderSize() == 0 )
         {
            SBHeaderVO sbHeader = new SBHeaderVO();
            sbHeader.reset();
            sbHeader.setBatchId( "" );
            return new SBHeaderAction().list_object( mapping, sbHeader, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listDetail" );
   }

   /**  
    * To SBDetail InVendor
    * ��Ӧ�̵�¼�鿴SBDetail��Ϣ
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_sbDetail_inVendor( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );

         // ����籣��������ID sbHeaderId
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );
         // ��ù�Ӧ��ID
         final String vendorId = KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) );
         // ���״̬��ʾ
         final String additionalStatus = request.getParameter( "additionalStatus" );

         // ��ù�Ӧ��
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         request.setAttribute( "vendorVO", vendorVO );

         // ��ʼ��SBHeaderVO
         SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         sbHeaderVO.setHeaderId( headerId );
         sbHeaderVO.setStatus( additionalStatus );

         // ��ȡ�籣����
         final List< Object > sbHeaderVOs = sbHeaderService.getSBHeaderVOsByCondition( sbHeaderVO );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            sbHeaderVO = ( SBHeaderVO ) sbHeaderVOs.get( 0 );
         }

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         sbHeaderVO.reset( null, request );

         // ��ʼ��SBBatchVO
         final String batchId = sbHeaderVO.getBatchId();
         SBBatchVO sbBatchVO = new SBBatchVO();
         sbBatchVO.setStatus( additionalStatus );
         sbBatchVO.setBatchId( batchId );
         sbBatchVO.setAccountId( getAccountId( request, response ) );

         final List< Object > sbBatchVOs = sbBatchService.getSBBatchVOsByCondition( sbBatchVO );

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            sbBatchVO = ( SBBatchVO ) sbBatchVOs.get( 0 );
         }

         sbBatchVO.setPageFlag( SBBatchService.PAGE_FLAG_DETAIL );
         sbBatchVO.setStatusFlag( SBBatchService.STATUS_FLAG_CONFIRM );
         sbBatchVO.setStatus( additionalStatus );
         sbBatchVO.reset( null, request );
         request.setAttribute( "sbBatchForm", sbBatchVO );
         request.setAttribute( "sbHeaderVO", sbHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbDetailHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         sbDetailHolder.setPage( page );

         // ��ʼ��SBDetailVO
         final SBDetailVO sbDetailVO = new SBDetailVO();
         sbDetailVO.setHeaderId( headerId );
         sbDetailVO.setAccountId( getAccountId( request, response ) );
         sbDetailVO.setStatus( additionalStatus );

         // ������������ֶ�
         sbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // ���û��ָ��������Ĭ�ϰ���ĿID����
         if ( sbDetailVO.getSortColumn() == null || sbDetailVO.getSortColumn().isEmpty() )
         {
            sbDetailVO.setSortColumn( "itemId" );
         }

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // ���뵱ǰֵ����
         sbDetailHolder.setObject( sbDetailVO );
         // ����ҳ���¼����
         sbDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbDetailService.getSBDetailVOsByCondition( sbDetailHolder, true );
         refreshHolder( sbDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "sbDetailHolder", sbDetailHolder );

         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

         // ����������籣��ϸ����
         //         if ( sbDetailHolder == null || sbDetailHolder.getHolderSize() == 0 )
         //         {
         //            SBHeaderVO sbHeader = new SBHeaderVO();
         //            sbHeader.reset();
         //            sbHeader.setBatchId( "" );
         //            return new SBHeaderAction().list_object( mapping, sbHeader, request, response );
         //         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listDetail" );
   }

   // ����StatusFlag��ȡ״ֵ̬Status
   // Reviewed by Kevin Jin at 2013-10-31
   private String getStatusesByStatusFlag( final String statusFlag )
   {
      // ��ʼ����Ĭ��Ϊ��Ԥ����
      String status = "1";

      if ( statusFlag != null && !statusFlag.isEmpty() )
      {
         if ( statusFlag.equalsIgnoreCase( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            return "2";
         }
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            return "3,4,5";
         }
      }

      return status;
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
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         // ���Action Form
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         setDataAuth( request, response, sbBatchVO );
         // ����״̬
         sbBatchVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰֵ����
         pagedListHolder.setObject( sbBatchVO );
         // ����Service���������ö��󷵻�
         sbBatchService.getSBDTOsByCondition( pagedListHolder );
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

   /**  
    * �������ClientId�Ƿ���Ч
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkClientId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final ClientService clientService = ( ClientService ) getService( "clientService" );
      // ��ȡForm
      final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
      // ���ClientId
      final String clientId = KANUtil.filterEmpty( sbBatchVO.getClientId() );

      final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

      if ( clientVO == null )
      {
         request.setAttribute( "clientIdError", "�ͻ�ID������Ч��" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   /**  
    * �������OrderId�Ƿ���Ч
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkOrderId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
      // ��ȡForm
      final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
      // ���OrderId
      final String clientOrderHeaderId = KANUtil.filterEmpty( sbBatchVO.getOrderId() );

      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderHeaderId );

      if ( clientOrderHeaderVO == null )
      {
         request.setAttribute( "orderIdError", "����ID������Ч��" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   /**  
    * �������EmployeeContractId�Ƿ���Ч
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkEmployeeContractId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      // ��ȡForm
      final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
      // ���ContractId
      final String contractId = KANUtil.filterEmpty( sbBatchVO.getContractId() );

      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

      if ( employeeContractVO == null )
      {

         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            request.setAttribute( "contractIdError", "�Ͷ���ͬID������Ч��" );
         }
         else
         {
            request.setAttribute( "contractIdError", "����Э��ID������Ч��" );
         }

         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );

      }

   }

   public void checkSBStatus( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         final PrintWriter out = response.getWriter();
         String addCount = "0";
         String backCount = "0";
         String selectedIds = request.getParameter( "selectedIds" );

         if ( StringUtils.isNotEmpty( selectedIds ) )
         {
            String[] ids = selectedIds.split( "," );
            String[] batchId = new String[ ids.length ];

            for ( int i = 0; i < batchId.length; i++ )
            {
               batchId[ i ] = KANUtil.decodeStringFromAjax( ids[ i ] );
            }

            addCount = sbBatchService.getSBToApplyForMoreStatusCountByBatchIds( batchId );
            backCount = sbBatchService.getSBToApplyForResigningStatusCountByBatchIds( batchId );
         }

         final JSONObject jsonObject = new JSONObject();
         jsonObject.put( "addCount", addCount );
         jsonObject.put( "backCount", backCount );
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * To SBDetail
    * 
    * ��ʾ���Ρ�����Э�顢�籣���������Ϣ���籣������ϸ�б�
    * ��Ҫ��ʾ��ǰ�籣�����Ļ�����
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward to_sbDetail_workflow( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {

         // ��ʼ��Service�ӿ�
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );

         // ��õ�������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ��÷���Э������ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // ����籣��������ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );

         // ��ʼ��SBHeaderVO
         SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setBatchId( batchId );
         sbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbHeaderVO.setContractId( contractId );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // ����HeaderId
         sbHeaderVO.setHeaderId( headerId );

         final List< Object > sbHeaderVOs = sbHeaderService.getSBHeaderVOsByCondition( sbHeaderVO );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            sbHeaderVO = ( SBHeaderVO ) sbHeaderVOs.get( 0 );
         }

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         sbHeaderVO.reset( null, request );

         // ��ʼ��SBBatchVO
         SBBatchVO sbBatchVO = new SBBatchVO();
         sbBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbBatchVO.setBatchId( batchId );
         sbBatchVO.setAccountId( getAccountId( request, response ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setCorpId( getCorpId( request, response ) );
         }
         final List< Object > sbBatchVOs = sbBatchService.getSBBatchVOsByCondition( sbBatchVO );

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            sbBatchVO = ( SBBatchVO ) sbBatchVOs.get( 0 );
         }

         sbBatchVO.setPageFlag( SBBatchService.PAGE_FLAG_DETAIL );
         sbBatchVO.setStatusFlag( request.getParameter( "statusFlag" ) );
         sbBatchVO.reset( null, request );
         request.setAttribute( "sbBatchForm", sbBatchVO );
         request.setAttribute( "sbHeaderVO", sbHeaderVO );

         String accessAction = "HRO_SB_BATCH_PREVIEW";
         if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            accessAction = "HRO_SB_BATCH_PREVIEW";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_SB_BATCH_CONFIRM";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_SB_BATCH_SUBMIT";
         }
         request.setAttribute( "authAccessAction", accessAction );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbDetailHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         sbDetailHolder.setPage( page );

         // ��ʼ��SBDetailVO
         final SBDetailVO sbDetailVO = new SBDetailVO();
         sbDetailVO.setHeaderId( headerId );
         sbDetailVO.setAccountId( getAccountId( request, response ) );
         sbDetailVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );

         // ������������ֶ�
         sbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // ���û��ָ��������Ĭ�ϰ���ĿID����
         if ( sbDetailVO.getSortColumn() == null || sbDetailVO.getSortColumn().isEmpty() )
         {
            sbDetailVO.setSortColumn( "itemId" );
         }

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // ���뵱ǰֵ����
         sbDetailHolder.setObject( sbDetailVO );
         // ����ҳ���¼����
         sbDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbDetailService.getSBDetailVOsByCondition( sbDetailHolder, true );
         refreshHolder( sbDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "sbDetailHolder", sbDetailHolder );

         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

         // ����������籣��ϸ����
         if ( sbDetailHolder == null || sbDetailHolder.getHolderSize() == 0 )
         {
            SBHeaderVO sbHeader = new SBHeaderVO();
            sbHeader.reset();
            sbHeader.setBatchId( "" );
            return new SBHeaderAction().list_object( mapping, sbHeader, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listDetailWorkflow" );
   }
}
