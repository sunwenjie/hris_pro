package com.kan.hro.web.actions.biz.settlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.TaxVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientOrderDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.BatchVO;
import com.kan.hro.domain.biz.settlement.OrderDetailTempVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderDetailTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderTempService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractTempService;

/**   
 * �����ƣ�SettlementAction  
 * ���������������
 * �����ˣ�Kevin  
 * ����ʱ�䣺2013-9-13  
 */
public class SettlementTempAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_SETTLE_ORDER_BATCH_TEMP";

   /**
    * List Estimation
    * 
    *	��ʾԤ�������б�����TEMP
    * ������Ա���԰����λ����Э���˻أ��κ��˻�����ɾ��TEMP������
    * TEMP���ж�Ӧ�ķ���Э�鲻�ܱ��޸ģ���������Э�飬ֻ�ܲ鿴��
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward list_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );

         // ���Action Form
         final BatchTempVO batchTempVO = ( BatchTempVO ) form;
         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, batchTempVO );
         
         // �����������
         batchTempVO.setPageFlag( BatchTempService.BATCH );
         // ��Ҫ���õ�ǰ�û�AccountId
         batchTempVO.setAccountId( getAccountId( request, response ) );
         // ���SubAction
         final String subAction = getSubAction( form );
         // ����Զ�����������
         batchTempVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         decodedObject( batchTempVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder batchTempHolder = new PagedListHolder();
         // ���뵱ǰҳ
         batchTempHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� BatchId����
         if ( batchTempVO.getSortColumn() == null || batchTempVO.getSortColumn().isEmpty() )
         {
            batchTempVO.setSortColumn( "batchId" );
            batchTempVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         batchTempHolder.setObject( batchTempVO );
         // ����ҳ���¼����
         batchTempHolder.setPageSize( listPageSize_medium );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         batchTempService.getBatchTempVOsByCondition( batchTempHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( batchTempHolder, request );

         // Holder��д��Request����
         request.setAttribute( "batchTempHolder", batchTempHolder );
         // д��pageFlag
         request.setAttribute( "pageFlag", BatchTempService.BATCH );

         // �����ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBatchTempTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listBatchTemp" );
   }

   /**
    * To Estimation Creation Page
    * 
    * ת�򴴽�Ԥ�����ν��棬����TEMP
    * ѡ�������ο�Batch
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_estimationNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ��ʼ��Service�ӿ�
      final BatchService batchService = ( BatchService ) getService( "batchService" );
      final BatchVO BatchVO = batchService.getLastestBatchVOByAccountId( getAccountId( request, response ) );

      // ����Account Period
      if ( BatchVO != null && BatchVO.getAccountPeriod() != null )
      {
         ( ( BatchTempVO ) form ).setAccountPeriod( BatchVO.getAccountPeriod() );
      }

      // ����Sub Action
      ( ( BatchTempVO ) form ).setSubAction( CREATE_OBJECT );

      // �������������ҳ��
      request.setAttribute( "pageFlag", "none" );

      // ��ת���½�����
      return mapping.findForward( "manageBatchTemp" );
   }

   /**
    * Add Estimation
    * 
    * ����Ԥ�����Σ�����TEMP
    * ������Ա���԰����λ����Э���˻أ��κ��˻�����ɾ��TEMP������
    * TEMP���ж�Ӧ�ķ���Э�鲻�ܱ��޸ģ���������Э�飬ֻ�ܲ鿴��
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward add_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            final List< String > flags = new ArrayList< String >();

            // ��ʼ��Service�ӿ�
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );

            // ���ActionForm
            final BatchTempVO batchTempVO = ( BatchTempVO ) form;
            batchTempVO.setCreateBy( getUserId( request, response ) );
            batchTempVO.setModifyBy( getUserId( request, response ) );

            // �������㿪ʼʱ������
            batchTempVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

            // �����Զ���Column
            batchTempVO.setRemark1( saveDefineColumns( request, "" ) );

            // ���ս�������������ȡ���ϵĶ���DTO
            final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
            clientOrderHeaderVO.setAccountId( batchTempVO.getAccountId() );
            clientOrderHeaderVO.setEntityId( batchTempVO.getEntityId() );
            clientOrderHeaderVO.setBusinessTypeId( batchTempVO.getBusinessTypeId() );
            clientOrderHeaderVO.setClientId( batchTempVO.getClientId() );
            clientOrderHeaderVO.setOrderHeaderId( batchTempVO.getOrderId() );
            clientOrderHeaderVO.setEmployeeContractId( batchTempVO.getContractId() );
            clientOrderHeaderVO.setMonthly( batchTempVO.getMonthly() );
            // �жϽ����Ƿ��������
            if ( batchTempVO.getContainSalary() != null && batchTempVO.getContainSalary().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SALARY );
            }
            // �жϽ����Ƿ�����籣
            if ( batchTempVO.getContainSB() != null && batchTempVO.getContainSB().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SB );
            }
            // �жϽ����Ƿ�����̱�
            if ( batchTempVO.getContainCB() != null && batchTempVO.getContainCB().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_CB );
            }
            // �жϽ����Ƿ��������
            if ( batchTempVO.getContainOther() != null && batchTempVO.getContainOther().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_OTHER );
            }
            // �жϽ����Ƿ���������
            if ( batchTempVO.getContainServiceFee() != null && batchTempVO.getContainServiceFee().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SERVICE_FEE );
            }
            clientOrderHeaderVO.setSettlementFlags( flags );
            
            //��������Ȩ��
            setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientOrderHeaderVO );
            

            final List< ClientOrderDTO > clientOrderDTOs = clientOrderHeaderService.getClientOrderDTOsByCondition( clientOrderHeaderVO );

            // ������������㶩��
            if ( clientOrderDTOs != null && clientOrderDTOs.size() > 0 )
            {
               for ( ClientOrderDTO clientOrderDTO : clientOrderDTOs )
               {
                  clientOrderDTO.calculateSettlement( flags );
               }

               // �����������ʱ�����ã�Ԥ�裩
               batchTempVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

               // ����Service�����洢���ݣ���Ҫ����Transaction��
               final int rows = batchTempService.insertBatchTemp( batchTempVO, clientOrderDTOs );

               if ( rows > 0 )
               {
                  // ������ӳɹ����
                  success( request, null, "�ɹ��������� " + batchTempVO.getBatchId() + " ��" );
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
         ( ( BatchTempVO ) form ).reset();
         ( ( BatchTempVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_estimation( mapping, form, request, response );
   }

   /**
    * Submit Estimation
    * 
    * �ύԤ�����Σ�TEMP�����ݱ����Ƶ����ñ���TEMP���ݱ�ɾ��
    * �ύ�������޷����޸ģ��κβ��ֻ�ܵ���
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

         // ��ȡ�� BatchTempVO
         final BatchTempVO batchTempVO = ( BatchTempVO ) form;
         batchTempVO.setCreateBy( getUserId( request, response ) );
         batchTempVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );

         // �ύ
         batchTempService.submitBatchTemp( batchTempVO );

         // ���Selected IDs����Action
         ( ( BatchTempVO ) form ).setSelectedIds( "" );

         // ����pageFlag ��ת
         return forward( batchTempVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Rollback Estimation
    * 
    * �˻�Ԥ�����λ����Э�� - TEMP�����ݱ��������
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-30
   public ActionForward rollback_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���Action Form
         final BatchTempVO batchTempVO = ( BatchTempVO ) form;
         batchTempVO.setCreateBy( getUserId( request, response ) );
         batchTempVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );

         // �˻�
         batchTempService.rollbackBatchTemp( batchTempVO );

         // ���Selected IDs����Action
         ( ( BatchTempVO ) form ).setSelectedIds( "" );

         // ����pageFlag ��ת
         return forward( batchTempVO.getPageFlag(), mapping, form, request, response );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Forward
    *
    * ����pageFlag ��ת
    * @param pageFlag
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-29
   private ActionForward forward( final String pageFlag, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ��������
      ( ( BatchTempVO ) form ).setSortColumn( null );

      if ( pageFlag.equalsIgnoreCase( BatchTempService.HEADER ) )
      {
         OrderHeaderTempVO orderHeaderTemp = new OrderHeaderTempVO();
         orderHeaderTemp.reset();
         orderHeaderTemp.setBatchId( "" );
         return new SettlementHeaderTempAction().list_object( mapping, orderHeaderTemp, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( BatchTempService.CONTRACT ) )
      {
         ServiceContractTempVO serviceContractTemp = new ServiceContractTempVO();
         serviceContractTemp.reset();
         serviceContractTemp.setBatchId( "" );
         return new SettlementContractTempAction().list_object( mapping, serviceContractTemp, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( BatchTempService.DETAIL ) )
      {
         return to_contractDetail( mapping, form, request, response );
      }
      else
      {
         return list_estimation( mapping, form, request, response );
      }
   }

   /**
    * To Batch Detail
    * 
    * ��ʾ���μ������б�
    * ��Ҫ��ʾ��ǰ���εĻ�����
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_batchDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );

         // ��õ�ǰ������������Ϣ��
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ��õ�ǰ��������
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         if ( batchTempVO != null )
         {
            // ����ҳ���PageFlag
            batchTempVO.setPageFlag( BatchTempService.HEADER );
            // ˢ��VO���󣬳�ʼ�������б����ʻ�
            batchTempVO.reset( null, request );
            request.setAttribute( "batchTempForm", batchTempVO );

            // ��������ǰ��ͻ�����ģ�װ�ؿͻ�����
            if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
            {
               final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
               clientVO.reset( null, request );
               request.setAttribute( "clientVO", clientVO );
            }
         }

         /**
          * ��ȡ�����б�
          */
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder headerTempListHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         headerTempListHolder.setPage( page );
         // ��ʼ����ѯ����
         OrderHeaderTempVO orderHeaderTempVO = new OrderHeaderTempVO();
         // �����������ֵ
         orderHeaderTempVO.setBatchId( batchId );
         orderHeaderTempVO.setAccountId( getAccountId( request, response ) );
         // ������������ֶ�
         orderHeaderTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderHeaderTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // Ĭ�ϰ� BatchId����
         if ( orderHeaderTempVO.getSortColumn() == null || orderHeaderTempVO.getSortColumn().trim().equals( "" ) )
         {
            orderHeaderTempVO.setSortColumn( "batchId" );
         }

         // ���뵱ǰֵ����
         headerTempListHolder.setObject( orderHeaderTempVO );
         // ����ҳ���¼����
         headerTempListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         orderHeaderTempService.getOrderHeaderTempVOsByCondition( headerTempListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( headerTempListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "headerTempListHolder", headerTempListHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listHeaderTempTable" );
         }

         if ( headerTempListHolder == null || headerTempListHolder.getHolderSize() == 0 )
         {
            ( ( BatchTempVO ) form ).reset();
            ( ( BatchTempVO ) form ).setBatchId( "" );
            return list_estimation( mapping, form, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listHeaderTemp" );
   }

   /**
    * To Header Detail
    * 
    * ��ʾ���Ρ����������Ϣ������Э���б�
    * ��Ҫ��ʾ��ǰ�������Ļ�����
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_headerDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );
         final ServiceContractTempService serviceContractTempService = ( ServiceContractTempService ) getService( "serviceContractTempService" );

         // �������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ������ζ���
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         // ����ҳ���PageFlag

         batchTempVO.setPageFlag( BatchTempService.CONTRACT );
         // ��ʼ�����󼰹��ʻ�
         batchTempVO.reset( null, request );
         request.setAttribute( "batchTempForm", batchTempVO );

         // ��������ǰ��ͻ�����ģ�װ�ؿͻ�����
         if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // ��ö�����ˮID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // ��ö�����ˮ����
         final OrderHeaderTempVO orderHeaderTempVO = orderHeaderTempService.getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );

         if ( orderHeaderTempVO != null )
         {
            // ��ʼ�����󼰹��ʻ�
            orderHeaderTempVO.reset( null, request );
            request.setAttribute( "orderHeaderTempVO", orderHeaderTempVO );
            // �����������TaxId��װ��Tax����
            if ( orderHeaderTempVO.getTaxId() != null && !orderHeaderTempVO.getTaxId().trim().equals( "" ) )
            {
               final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderTempVO.getTaxId() );
               taxVO.reset( null, request );
               request.setAttribute( "taxVO", taxVO );
            }
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder contractTempHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         contractTempHolder.setPage( page );
         // ��ʼ����ѯ����
         final ServiceContractTempVO serviceContractTempVO = new ServiceContractTempVO();
         // �����������ֵ
         serviceContractTempVO.setOrderHeaderId( orderHeaderId );
         // ������������ֶ�
         serviceContractTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         serviceContractTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // Ĭ�ϰ� ������ˮID����
         if ( serviceContractTempVO.getSortColumn() == null || serviceContractTempVO.getSortColumn().trim().equals( "" ) )
         {
            serviceContractTempVO.setSortColumn( "orderHeaderId" );
            serviceContractTempVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         contractTempHolder.setObject( serviceContractTempVO );
         // ����ҳ���¼����
         contractTempHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         serviceContractTempVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         serviceContractTempService.getServiceContractTempVOsByCondition( contractTempHolder, true );
         refreshHolder( contractTempHolder, request );
         // Holder��д��Request����
         request.setAttribute( "contractTempHolder", contractTempHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listContractTempTable" );
         }

         if ( contractTempHolder == null || contractTempHolder.getHolderSize() == 0 )
         {
            ServiceContractTempVO serviceContractTemp = new ServiceContractTempVO();
            serviceContractTemp.reset();
            serviceContractTemp.setBatchId( "" );
            return new SettlementContractTempAction().list_object( mapping, serviceContractTemp, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listContractTemp" );
   }

   /**
    * To Contract Detail
    * 
    * ��ʾ���Ρ������ͷ���Э�������Ϣ����ϸ�б�
    * ��Ҫ��ʾ��ǰ����Э��Ļ�����
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_contractDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );
         final ServiceContractTempService serviceContractTempService = ( ServiceContractTempService ) getService( "serviceContractTempService" );
         final OrderDetailTempService orderDetailTempService = ( OrderDetailTempService ) getService( "orderDetailTempService" );

         // �������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ������ζ���
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         if ( batchTempVO != null )
         {
            // ����ҳ���PageFlag
            batchTempVO.setPageFlag( BatchTempService.DETAIL );
            // ��ʼ�����󼰹��ʻ�
            batchTempVO.reset( null, request );
            request.setAttribute( "batchTempForm", batchTempVO );

            // ��������ǰ��ͻ�����ģ�װ�ؿͻ�����
            if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
            {
               final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
               clientVO.reset( null, request );
               request.setAttribute( "clientVO", clientVO );
            }
         }

         // ��ö�����ˮID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // ��ö�����ˮ����
         final OrderHeaderTempVO orderHeaderTempVO = orderHeaderTempService.getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );

         if ( orderHeaderTempVO != null )
         {
            // ��ʼ�����󼰹��ʻ�
            orderHeaderTempVO.reset( null, request );
            request.setAttribute( "orderHeaderTempVO", orderHeaderTempVO );

            // �����������TaxId��װ��Tax����
            if ( orderHeaderTempVO.getTaxId() != null && !orderHeaderTempVO.getTaxId().trim().equals( "" ) )
            {
               final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderTempVO.getTaxId() );
               taxVO.reset( null, request );
               request.setAttribute( "taxVO", taxVO );
            }
         }

         // ��÷���Э����ˮID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // ��÷���Э����ˮ����
         final ServiceContractTempVO serviceContractTempVO = serviceContractTempService.getServiceContractTempVOByContractId( contractId );

         if ( serviceContractTempVO != null )
         {
            // ��ʼ�����󼰹��ʻ�
            serviceContractTempVO.reset( null, request );
            request.setAttribute( "serviceContractTempVO", serviceContractTempVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder orderDetailTempHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         orderDetailTempHolder.setPage( page );
         // ��ʼ����ѯ����
         final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
         // �����������ֵ
         orderDetailTempVO.setContractId( contractId );
         // ������������ֶ�
         orderDetailTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderDetailTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // Ĭ�ϰ�����Э����ˮID����
         if ( orderDetailTempVO.getSortColumn() == null || orderDetailTempVO.getSortColumn().trim().equals( "" ) )
         {
            orderDetailTempVO.setSortColumn( "itemId" );
         }

         // ���뵱ǰֵ����
         orderDetailTempHolder.setObject( orderDetailTempVO );
         // ����ҳ���¼����
         orderDetailTempHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         orderDetailTempVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         orderDetailTempService.getOrderDetailTempVOsByCondition( orderDetailTempHolder, true );
         refreshHolder( orderDetailTempHolder, request );
         // Holder��д��Request����
         request.setAttribute( "orderDetailTempHolder", orderDetailTempHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listDetailTempTable" );
         }

         if ( orderDetailTempHolder == null || orderDetailTempHolder.getHolderSize() == 0 )
         {
            ServiceContractTempVO serviceContractTemp = new ServiceContractTempVO();
            serviceContractTemp.reset();
            serviceContractTemp.setBatchId( "" );
            return new SettlementContractTempAction().list_object( mapping, serviceContractTemp, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listDetailTemp" );
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
