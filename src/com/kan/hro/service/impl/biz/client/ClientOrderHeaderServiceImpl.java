package com.kan.hro.service.impl.biz.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.AnnualLeaveRuleDTO;
import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.client.ClientContractDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderCBDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailRuleDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderRuleDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderLeaveDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderOTDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderOtherDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;
import com.kan.hro.domain.biz.client.ClientOrderDTO;
import com.kan.hro.domain.biz.client.ClientOrderDetailRuleVO;
import com.kan.hro.domain.biz.client.ClientOrderDetailVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderRuleVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderLeaveVO;
import com.kan.hro.domain.biz.client.ClientOrderOTVO;
import com.kan.hro.domain.biz.client.ClientOrderOtherVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.web.actions.biz.client.ClientOrderHeaderAction;

public class ClientOrderHeaderServiceImpl extends ContextService implements ClientOrderHeaderService
{

   // �������������磬com.kan.base.domain.BaseVO��
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.client.ClientOrderHeaderVO";

   // Service Name�����磬Spring�����Bean���� spring�����ļ��� service��ӦBean��ID ��
   public static final String SERVICE_BEAN = "clientOrderHeaderService";

   // Access Action
   public static final String accessAction = "HRO_BIZ_CLIENT_ORDER_HEADER";

   private ClientOrderHeaderRuleDao clientOrderHeaderRuleDao;

   private ClientService clientService;

   private ClientOrderDetailService clientOrderDetailService;

   private ClientOrderSBDao clientOrderSBDao;

   private ClientOrderCBDao clientOrderCBDao;

   private ClientOrderLeaveDao clientOrderLeaveDao;

   private ClientOrderOTDao clientOrderOTDao;

   private ClientOrderOtherDao clientOrderOtherDao;

   private ClientContractDao clientContractDao;

   private ClientOrderDetailDao clientOrderDetailDao;

   private ClientOrderDetailRuleDao clientOrderDetailRuleDao;

   private EmployeeContractService employeeContractService;

   private EmployeeDao employeeDao;

   private EmployeeContractLeaveDao employeeContractLeaveDao;

   // ע��WorkflowService
   private WorkflowService workflowService;

   public EmployeeContractLeaveDao getEmployeeContractLeaveDao()
   {
      return employeeContractLeaveDao;
   }

   public void setEmployeeContractLeaveDao( EmployeeContractLeaveDao employeeContractLeaveDao )
   {
      this.employeeContractLeaveDao = employeeContractLeaveDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public ClientService getClientService()
   {
      return clientService;
   }

   public void setClientService( ClientService clientService )
   {
      this.clientService = clientService;
   }

   public ClientOrderHeaderRuleDao getClientOrderHeaderRuleDao()
   {
      return clientOrderHeaderRuleDao;
   }

   public void setClientOrderHeaderRuleDao( ClientOrderHeaderRuleDao clientOrderHeaderRuleDao )
   {
      this.clientOrderHeaderRuleDao = clientOrderHeaderRuleDao;
   }

   public ClientOrderDetailService getClientOrderDetailService()
   {
      return clientOrderDetailService;
   }

   public void setClientOrderDetailService( ClientOrderDetailService clientOrderDetailService )
   {
      this.clientOrderDetailService = clientOrderDetailService;
   }

   public ClientOrderSBDao getClientOrderSBDao()
   {
      return clientOrderSBDao;
   }

   public void setClientOrderSBDao( ClientOrderSBDao clientOrderSBDao )
   {
      this.clientOrderSBDao = clientOrderSBDao;
   }

   public ClientOrderCBDao getClientOrderCBDao()
   {
      return clientOrderCBDao;
   }

   public void setClientOrderCBDao( ClientOrderCBDao clientOrderCBDao )
   {
      this.clientOrderCBDao = clientOrderCBDao;
   }

   public ClientOrderLeaveDao getClientOrderLeaveDao()
   {
      return clientOrderLeaveDao;
   }

   public void setClientOrderLeaveDao( ClientOrderLeaveDao clientOrderLeaveDao )
   {
      this.clientOrderLeaveDao = clientOrderLeaveDao;
   }

   public ClientOrderOTDao getClientOrderOTDao()
   {
      return clientOrderOTDao;
   }

   public void setClientOrderOTDao( ClientOrderOTDao clientOrderOTDao )
   {
      this.clientOrderOTDao = clientOrderOTDao;
   }

   public ClientOrderOtherDao getClientOrderOtherDao()
   {
      return clientOrderOtherDao;
   }

   public void setClientOrderOtherDao( ClientOrderOtherDao clientOrderOtherDao )
   {
      this.clientOrderOtherDao = clientOrderOtherDao;
   }

   public ClientOrderDetailDao getClientOrderDetailDao()
   {
      return clientOrderDetailDao;
   }

   public void setClientOrderDetailDao( ClientOrderDetailDao clientOrderDetailDao )
   {
      this.clientOrderDetailDao = clientOrderDetailDao;
   }

   public ClientOrderDetailRuleDao getClientOrderDetailRuleDao()
   {
      return clientOrderDetailRuleDao;
   }

   public void setClientOrderDetailRuleDao( ClientOrderDetailRuleDao clientOrderDetailRuleDao )
   {
      this.clientOrderDetailRuleDao = clientOrderDetailRuleDao;
   }

   public EmployeeContractService getEmployeeContractService()
   {
      return employeeContractService;
   }

   public void setEmployeeContractService( EmployeeContractService employeeContractService )
   {
      this.employeeContractService = employeeContractService;
   }

   public ClientContractDao getClientContractDao()
   {
      return clientContractDao;
   }

   public void setClientContractDao( ClientContractDao clientContractDao )
   {
      this.clientContractDao = clientContractDao;
   }

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   @Override
   public PagedListHolder getClientOrderHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientOrderHeaderDao clientOrderHeaderDao = ( ClientOrderHeaderDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderHeaderDao.countClientOrderHeaderVOsByCondition( ( ClientOrderHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderHeaderDao.getClientOrderHeaderVOsByCondition( ( ClientOrderHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderHeaderDao.getClientOrderHeaderVOsByCondition( ( ClientOrderHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-12
   public ClientOrderHeaderVO getClientOrderHeaderVOByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( ( ClientOrderHeaderDao ) getDao() ).getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-12
   public int updateClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return ( ( ClientOrderHeaderDao ) getDao() ).updateClientOrderHeader( clientOrderHeaderVO );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-12
   public int submitClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( clientOrderHeaderVO ) )
      {
         // ��ʼ��ClientContractVO
         final ClientContractVO clientContractVO = clientContractDao.getClientContractVOByContractId( clientOrderHeaderVO.getContractId() );

         final HistoryVO historyVO = generateHistoryVO( clientOrderHeaderVO );
         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( clientOrderHeaderVO );
         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( clientOrderHeaderVO.getStatus() != null && !clientOrderHeaderVO.getStatus().trim().equals( "3" ) && !clientOrderHeaderVO.getStatus().trim().equals( "5" ) )
            {
               // ״̬��Ϊ�����
               clientOrderHeaderVO.setStatus( "2" );
               updateClientOrderHeader( clientOrderHeaderVO );
            }

            // Service�ķ���
            historyVO.setServiceMethod( "submitClientOrderHeader" );
            historyVO.setObjectId( clientOrderHeaderVO.getOrderHeaderId() );

            String passObject = null;
            // �Ѹ���״̬
            if ( clientContractVO != null && ( clientContractVO.getStatus().equals( "5" ) || clientContractVO.getStatus().equals( "6" ) ) )
            {
               clientOrderHeaderVO.setStatus( "5" );
               passObject = KANUtil.toJSONObject( clientOrderHeaderVO ).toString();
            }
            // ��׼״̬
            else
            {
               clientOrderHeaderVO.setStatus( "3" );
               passObject = KANUtil.toJSONObject( clientOrderHeaderVO ).toString();
            }

            // �˻�״̬
            clientOrderHeaderVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( clientOrderHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, clientOrderHeaderVO );

            return -1;
         }
         // û�й�����
         else
         {
            // �Ѹ���״̬
            if ( clientContractVO != null && ( clientContractVO.getStatus().equals( "5" ) || clientContractVO.getStatus().equals( "6" ) ) )
            {
               clientOrderHeaderVO.setStatus( "5" );
            }
            // ��׼״̬
            else
            {
               clientOrderHeaderVO.setStatus( "3" );
            }

            return updateClientOrderHeader( clientOrderHeaderVO );
         }
      }

      return updateClientOrderHeader( clientOrderHeaderVO );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-12
   public int insertClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      int rows = 0;

      clientOrderHeaderVO.setStatus( "1" );
      rows = ( ( ClientOrderHeaderDao ) getDao() ).insertClientOrderHeader( clientOrderHeaderVO );

      if ( clientOrderHeaderVO.getSubAction() != null && clientOrderHeaderVO.getSubAction().trim().equals( BaseAction.SUBMIT_OBJECT ) )
      {
         rows = submitClientOrderHeader( getClientOrderHeaderVOByOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() ) );
      }

      return rows;
   }

   @Override
   public int deleteClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      clientOrderHeaderVO.setDeleted( ClientOrderHeaderVO.FALSE );
      return updateClientOrderHeader( clientOrderHeaderVO );
   }

   @Override
   public List< Object > getClientOrderHeaderBaseViewsByClientId( final String clientId ) throws KANException
   {
      return ( ( ClientOrderHeaderDao ) getDao() ).getClientOrderHeaderBaseViewsByClientId( clientId );
   }

   @Override
   public List< ClientOrderDTO > getClientOrderDTOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      // ��ʼ��ClientOrderDTO List
      final List< ClientOrderDTO > clientOrderDTOs = new ArrayList< ClientOrderDTO >();

      // ����������ȡClientOrderHeaderVO List
      final List< Object > clientOrderHeaderVOs = ( ( ClientOrderHeaderDao ) getDao() ).getSettlementClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

      // �������ClientOrderHeaderVO List���ݣ�����
      if ( clientOrderHeaderVOs != null && clientOrderHeaderVOs.size() > 0 )
      {
         for ( Object clientOrderHeaderVOObject : clientOrderHeaderVOs )
         {
            final ClientOrderHeaderVO tempClientOrderHeaderVO = ( ClientOrderHeaderVO ) clientOrderHeaderVOObject;

            // ��ʼ��Monthly
            final String monthly = clientOrderHeaderVO.getMonthly();
            // ��ʼ��OrderId
            final String orderId = tempClientOrderHeaderVO.getOrderHeaderId();

            // ��ʼ��ClientOrderDTO����
            final ClientOrderDTO clientOrderDTO = new ClientOrderDTO();

            /** װ��DTO���ݶ��� */
            // װ��ClientDTO
            clientOrderDTO.setClientDTO( this.getClientService().getClientDTOByClientId( tempClientOrderHeaderVO.getClientId() ) );

            // װ��ClientOrderHeaderDTO
            clientOrderDTO.setClientOrderHeaderDTO( this.getClientOrderHeaderDTOByClientOrderHeaderId( orderId ) );

            // ����ClientOrderHeaderVO Monthly�ֶ�
            clientOrderDTO.getClientOrderHeaderDTO().getClientOrderHeaderVO().setMonthly( monthly );

            // װ��ClientOrderDetailDTO List ���Ż�
            clientOrderDTO.setClientOrderDetailDTOs( this.getClientOrderDetailService().getClientOrderDetailDTOsByClientOrderHeaderId( orderId ) );

            // װ��ClientOrderSBVO List
            clientOrderDTO.setClientOrderSBVOs( fetchClientOrderSB( orderId ) );

            // װ��ClientOrderCBVO List
            clientOrderDTO.setClientOrderCBVOs( fetchClientOrderCB( orderId ) );

            // װ��ClientOrderLeaveVO List
            clientOrderDTO.setClientOrderLeaveVOs( fetchClientOrderLeave( orderId ) );

            // װ��ClientOrderOTVO List
            clientOrderDTO.setClientOrderOTVOs( fetchClientOrderOT( orderId ) );

            // װ��ClientOrderOtherVO List
            clientOrderDTO.setClientOrderOtherVOs( fetchClientOrderOther( orderId ) );

            // װ��ServiceContractDTO List ���Ż�
            clientOrderDTO.setServiceContractDTOs( fetchServiceContract( clientOrderHeaderVO.getAccountId(), monthly, orderId, clientOrderHeaderVO.getEmployeeId(), clientOrderHeaderVO.getEmployeeContractId(), clientOrderHeaderVO.getCorpId() ) );

            clientOrderDTOs.add( clientOrderDTO );
         }
      }

      return clientOrderDTOs;
   }

   private List< ClientOrderSBVO > fetchClientOrderSB( final String orderHeaderId ) throws KANException
   {
      // ��ʼ��ClientOrderSBVO List
      final List< ClientOrderSBVO > clientOrderSBVOs = new ArrayList< ClientOrderSBVO >();
      // ��ʼ��ClientOrderSBVO Object List
      final List< Object > clientOrderSBVOObjects = this.getClientOrderSBDao().getClientOrderSBVOsByClientOrderHeaderId( orderHeaderId );

      // ����
      if ( clientOrderSBVOObjects != null && clientOrderSBVOObjects.size() > 0 )
      {
         for ( Object clientOrderSBVOObject : clientOrderSBVOObjects )
         {
            clientOrderSBVOs.add( ( ClientOrderSBVO ) clientOrderSBVOObject );
         }
      }

      return clientOrderSBVOs;
   }

   private List< ClientOrderCBVO > fetchClientOrderCB( final String orderHeaderId ) throws KANException
   {
      // ��ʼ��ClientOrderCBVO List
      final List< ClientOrderCBVO > clientOrderCBVOs = new ArrayList< ClientOrderCBVO >();
      // ��ʼ��ClientOrderCBVO Object List
      final List< Object > clientOrderCBVOObjects = this.getClientOrderCBDao().getClientOrderCBVOsByClientOrderHeaderId( orderHeaderId );

      // ����
      if ( clientOrderCBVOObjects != null && clientOrderCBVOObjects.size() > 0 )
      {
         for ( Object clientOrderCBVOObject : clientOrderCBVOObjects )
         {
            clientOrderCBVOs.add( ( ClientOrderCBVO ) clientOrderCBVOObject );
         }
      }

      return clientOrderCBVOs;
   }

   private List< ClientOrderLeaveVO > fetchClientOrderLeave( final String orderHeaderId ) throws KANException
   {
      // ��ʼ��ClientOrderLeaveVO List
      final List< ClientOrderLeaveVO > clientOrderLeaveVOs = new ArrayList< ClientOrderLeaveVO >();
      // ��ʼ��ClientOrderLeaveVO Object List
      final List< Object > clientOrderLeaveVOObjects = this.getClientOrderLeaveDao().getClientOrderLeaveVOsByOrderHeaderId( orderHeaderId );

      // ����
      if ( clientOrderLeaveVOObjects != null && clientOrderLeaveVOObjects.size() > 0 )
      {
         for ( Object clientOrderLeaveVOObject : clientOrderLeaveVOObjects )
         {
            clientOrderLeaveVOs.add( ( ClientOrderLeaveVO ) clientOrderLeaveVOObject );
         }
      }

      return clientOrderLeaveVOs;
   }

   private List< ClientOrderOTVO > fetchClientOrderOT( final String orderHeaderId ) throws KANException
   {
      // ��ʼ��ClientOrderOTVO List
      final List< ClientOrderOTVO > clientOrderOTVOs = new ArrayList< ClientOrderOTVO >();
      // ��ʼ��ClientOrderOTVO Object List
      final List< Object > clientOrderOTVOObjects = this.getClientOrderOTDao().getClientOrderOTVOsByClientOrderHeaderId( orderHeaderId );

      // ����
      if ( clientOrderOTVOObjects != null && clientOrderOTVOObjects.size() > 0 )
      {
         for ( Object clientOrderOTVOObject : clientOrderOTVOObjects )
         {
            clientOrderOTVOs.add( ( ClientOrderOTVO ) clientOrderOTVOObject );
         }
      }

      return clientOrderOTVOs;
   }

   private List< ClientOrderOtherVO > fetchClientOrderOther( final String orderHeaderId ) throws KANException
   {
      // ��ʼ��ClientOrderOtherVO List
      final List< ClientOrderOtherVO > clientOrderOtherVOs = new ArrayList< ClientOrderOtherVO >();
      // ��ʼ��ClientOrderOtherVO Object List
      final List< Object > clientOrderOtherVOObjects = this.getClientOrderOtherDao().getClientOrderOtherVOsByOrderHeaderId( orderHeaderId );

      // ����
      if ( clientOrderOtherVOObjects != null && clientOrderOtherVOObjects.size() > 0 )
      {
         for ( Object clientOrderOtherVOObject : clientOrderOtherVOObjects )
         {
            clientOrderOtherVOs.add( ( ClientOrderOtherVO ) clientOrderOtherVOObject );
         }
      }

      return clientOrderOtherVOs;
   }

   private List< ServiceContractDTO > fetchServiceContract( final String accountId, final String monthly, final String orderHeaderId, final String employeeId,
         final String employeeContractId, final String corpId ) throws KANException
   {
      // ��ʼ��ServiceContractDTO List
      final List< ServiceContractDTO > serviceContractDTOs = new ArrayList< ServiceContractDTO >();

      // ��ʼ��EmployeeContractVO
      final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
      employeeContractVO.setAccountId( accountId );
      employeeContractVO.setMonthly( monthly );
      employeeContractVO.setOrderId( orderHeaderId );
      employeeContractVO.setEmployeeId( employeeId );
      employeeContractVO.setContractId( employeeContractId );
      employeeContractVO.setCorpId( corpId );
      serviceContractDTOs.addAll( this.getEmployeeContractService().getServiceContractDTOsByCondition( employeeContractVO, EmployeeContractService.FLAG_SETTLEMENT ) );

      return serviceContractDTOs;
   }

   @Override
   public ClientOrderHeaderDTO getClientOrderHeaderDTOByClientOrderHeaderId( String clientOrderHeaderId ) throws KANException
   {
      // ��ʼ��ClientOrderHeaderDTO
      final ClientOrderHeaderDTO clientOrderHeaderDTO = new ClientOrderHeaderDTO();

      // ����ClientOrderHeaderVO
      clientOrderHeaderDTO.setClientOrderHeaderVO( this.getClientOrderHeaderVOByOrderHeaderId( clientOrderHeaderId ) );

      // ����ClientOrderHeaderRuleVO List
      final List< Object > clientOrderHeaderRuleVOs = this.getClientOrderHeaderRuleDao().getClientOrderHeaderRuleVOsByClientOrderHeaderId( clientOrderHeaderId );

      if ( clientOrderHeaderRuleVOs != null && clientOrderHeaderRuleVOs.size() > 0 )
      {
         for ( Object clientOrderHeaderRuleVOObject : clientOrderHeaderRuleVOs )
         {
            clientOrderHeaderDTO.getClientOrderHeaderRuleVOs().add( ( ClientOrderHeaderRuleVO ) clientOrderHeaderRuleVOObject );
         }
      }

      return clientOrderHeaderDTO;
   }

   @Override
   public List< Object > getClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return ( ( ClientOrderHeaderDao ) getDao() ).getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );
   }

   private HistoryVO generateHistoryVO( final ClientOrderHeaderVO clientOrderHeaderVO )
   {
      final HistoryVO history = clientOrderHeaderVO.getHistoryVO();
      history.setAccessAction( ClientOrderHeaderAction.getAccessActionForWorkFlow() );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( ClientOrderHeaderAction.getAccessActionForWorkFlow() ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getClientOrderHeaderVOByOrderHeaderId" );
      // ��ʾ�ǹ�������
      history.setObjectType( "2" );
      history.setAccountId( clientOrderHeaderVO.getAccountId() );
      history.setNameZH( clientOrderHeaderVO.getClientNameZH() + "(���񶩵�)" );
      history.setNameEN( clientOrderHeaderVO.getClientNameEN() + "(Order Service)" );
      return history;
   }

   /**  
    * copyClientOrderHeader
    *	 
    *	@param clientOrderHeaderVO
    *	@return
    *	@throws KANException
    */
   @Override
   public int copyClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      {
         // ��ʼ��Ӱ���¼��
         int count = 0;
         // ���ݿ�ԭ��OrderHeaderId
         final String clientOrderHeaderId = clientOrderHeaderVO.getOrderHeaderId();

         clientOrderHeaderVO.setOrderHeaderId( "" );
         count = ( ( ClientOrderHeaderDao ) getDao() ).insertClientOrderHeader( clientOrderHeaderVO );

         if ( count > 0 )
         {
            // �����ܹ���
            final List< Object > clientOrderHeaderRules = this.getClientOrderHeaderRuleDao().getClientOrderHeaderRuleVOsByClientOrderHeaderId( clientOrderHeaderId );

            if ( clientOrderHeaderRules != null && clientOrderHeaderRules.size() > 0 )
            {
               for ( Object object : clientOrderHeaderRules )
               {
                  final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = ( ClientOrderHeaderRuleVO ) object;
                  // ����������������ֵ
                  clientOrderHeaderRuleVO.setOrderHeaderRuleId( "" );
                  clientOrderHeaderRuleVO.setStatus( "1" );
                  clientOrderHeaderRuleVO.setOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() );
                  clientOrderHeaderRuleVO.setCreateBy( clientOrderHeaderVO.getCreateBy() );
                  clientOrderHeaderRuleVO.setCreateDate( new Date() );
                  clientOrderHeaderRuleVO.setModifyBy( clientOrderHeaderVO.getModifyBy() );
                  clientOrderHeaderRuleVO.setModifyDate( new Date() );
                  this.getClientOrderHeaderRuleDao().insertClientOrderHeaderRule( clientOrderHeaderRuleVO );
               }
            }

            // �����շ�
            final List< Object > clientOrderDetails = this.getClientOrderDetailDao().getClientOrderDetailVOsByClientOrderHeaderId( clientOrderHeaderId );

            if ( clientOrderDetails != null && clientOrderDetails.size() > 0 )
            {
               for ( Object object : clientOrderDetails )
               {
                  final ClientOrderDetailVO clientOrderDetailVO = ( ClientOrderDetailVO ) object;
                  // ���OrderDetailId
                  final String orderDetailId = clientOrderDetailVO.getOrderDetailId();

                  // ����������������ֵ
                  clientOrderDetailVO.setOrderDetailId( "" );
                  clientOrderDetailVO.setStatus( "1" );
                  clientOrderDetailVO.setOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() );
                  clientOrderDetailVO.setCreateBy( clientOrderHeaderVO.getCreateBy() );
                  clientOrderDetailVO.setCreateDate( new Date() );
                  clientOrderDetailVO.setModifyBy( clientOrderHeaderVO.getModifyBy() );
                  clientOrderDetailVO.setModifyDate( new Date() );
                  this.getClientOrderDetailDao().insertClientOrderDetail( clientOrderDetailVO );

                  // �����շѶ�Ӧ������Ϣ
                  final List< Object > clientOrderDetailRules = this.getClientOrderDetailRuleDao().getClientOrderDetailRuleVOsByClientOrderDetailId( orderDetailId );

                  if ( clientOrderDetailRules != null && clientOrderDetailRules.size() > 0 )
                  {
                     for ( Object object2 : clientOrderDetailRules )
                     {
                        final ClientOrderDetailRuleVO clientOrderDetailRuleVO = ( ClientOrderDetailRuleVO ) object2;
                        clientOrderDetailRuleVO.setOrderDetailId( clientOrderDetailVO.getOrderDetailId() );
                        clientOrderDetailRuleVO.setCreateBy( clientOrderDetailVO.getCreateBy() );
                        clientOrderDetailRuleVO.setCreateDate( new Date() );
                        clientOrderDetailRuleVO.setModifyBy( clientOrderDetailVO.getModifyBy() );
                        clientOrderDetailRuleVO.setModifyDate( new Date() );
                        clientOrderDetailRuleVO.setStatus( "1" );
                        this.getClientOrderDetailRuleDao().insertClientOrderDetailRule( clientOrderDetailRuleVO );
                     }
                  }
               }
            }

            // ����SB
            final List< Object > clientOrderHeaderSBs = this.getClientOrderSBDao().getClientOrderSBVOsByClientOrderHeaderId( clientOrderHeaderId );

            if ( clientOrderHeaderSBs != null && clientOrderHeaderSBs.size() > 0 )
            {
               for ( Object object : clientOrderHeaderSBs )
               {
                  final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) object;
                  // ����������������ֵ
                  clientOrderSBVO.setOrderSbId( "" );
                  clientOrderSBVO.setStatus( "1" );
                  clientOrderSBVO.setOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() );
                  clientOrderSBVO.setCreateBy( clientOrderHeaderVO.getCreateBy() );
                  clientOrderSBVO.setCreateDate( new Date() );
                  clientOrderSBVO.setModifyBy( clientOrderHeaderVO.getModifyBy() );
                  clientOrderSBVO.setModifyDate( new Date() );
                  this.getClientOrderSBDao().insertClientOrderSB( clientOrderSBVO );
               }
            }

            // ����CB
            final List< Object > clientOrderHeaderCBs = this.getClientOrderCBDao().getClientOrderCBVOsByClientOrderHeaderId( clientOrderHeaderId );

            if ( clientOrderHeaderCBs != null && clientOrderHeaderCBs.size() > 0 )
            {
               for ( Object object : clientOrderHeaderCBs )
               {
                  final ClientOrderCBVO clientOrderCBVO = ( ClientOrderCBVO ) object;

                  // ����������������ֵ
                  clientOrderCBVO.setOrderCbId( "" );
                  clientOrderCBVO.setStatus( "1" );
                  clientOrderCBVO.setOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() );
                  clientOrderCBVO.setCreateBy( clientOrderHeaderVO.getCreateBy() );
                  clientOrderCBVO.setCreateDate( new Date() );
                  clientOrderCBVO.setModifyBy( clientOrderHeaderVO.getModifyBy() );
                  clientOrderCBVO.setModifyDate( new Date() );
                  this.getClientOrderCBDao().insertClientOrderCB( clientOrderCBVO );
               }
            }

            // ����Leave
            final List< Object > clientOrderHeaderLeaves = this.getClientOrderLeaveDao().getClientOrderLeaveVOsByOrderHeaderId( clientOrderHeaderId );

            if ( clientOrderHeaderLeaves != null && clientOrderHeaderLeaves.size() > 0 )
            {
               for ( Object object : clientOrderHeaderLeaves )
               {
                  final ClientOrderLeaveVO clientOrderHeaderLeaveVO = ( ClientOrderLeaveVO ) object;

                  // ����������������ֵ
                  clientOrderHeaderLeaveVO.setOrderLeaveId( "" );
                  clientOrderHeaderLeaveVO.setStatus( "1" );
                  clientOrderHeaderLeaveVO.setOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() );
                  clientOrderHeaderLeaveVO.setCreateBy( clientOrderHeaderVO.getCreateBy() );
                  clientOrderHeaderLeaveVO.setCreateDate( new Date() );
                  clientOrderHeaderLeaveVO.setModifyBy( clientOrderHeaderVO.getModifyBy() );
                  clientOrderHeaderLeaveVO.setModifyDate( new Date() );
                  this.getClientOrderLeaveDao().insertClientOrderLeave( clientOrderHeaderLeaveVO );
               }
            }

            // ����OT
            final List< Object > clientOrderHeaderOTs = this.getClientOrderOTDao().getClientOrderOTVOsByClientOrderHeaderId( clientOrderHeaderId );

            if ( clientOrderHeaderOTs != null && clientOrderHeaderOTs.size() > 0 )
            {
               for ( Object object : clientOrderHeaderOTs )
               {
                  final ClientOrderOTVO clientOrderHeaderOTVO = ( ClientOrderOTVO ) object;

                  // ����������������ֵ
                  clientOrderHeaderOTVO.setOrderOTId( "" );
                  clientOrderHeaderOTVO.setStatus( "1" );
                  clientOrderHeaderOTVO.setOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() );
                  clientOrderHeaderOTVO.setCreateBy( clientOrderHeaderVO.getCreateBy() );
                  clientOrderHeaderOTVO.setCreateDate( new Date() );
                  clientOrderHeaderOTVO.setModifyBy( clientOrderHeaderVO.getModifyBy() );
                  clientOrderHeaderOTVO.setModifyDate( new Date() );
                  clientOrderHeaderOTVO.setEndDate( "" );
                  this.getClientOrderOTDao().insertClientOrderOT( clientOrderHeaderOTVO );
               }
            }

            // ����Other
            final List< Object > clientOrderHeaderOthers = this.getClientOrderOtherDao().getClientOrderOtherVOsByOrderHeaderId( clientOrderHeaderId );

            if ( clientOrderHeaderOthers != null && clientOrderHeaderOthers.size() > 0 )
            {
               for ( Object object : clientOrderHeaderOthers )
               {
                  final ClientOrderOtherVO clientOrderHeaderOtherVO = ( ClientOrderOtherVO ) object;

                  // ����������������ֵ
                  clientOrderHeaderOtherVO.setOrderOtherId( "" );
                  clientOrderHeaderOtherVO.setStatus( "1" );
                  clientOrderHeaderOtherVO.setOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() );
                  clientOrderHeaderOtherVO.setCreateBy( clientOrderHeaderVO.getCreateBy() );
                  clientOrderHeaderOtherVO.setCreateDate( new Date() );
                  clientOrderHeaderOtherVO.setModifyBy( clientOrderHeaderVO.getModifyBy() );
                  clientOrderHeaderOtherVO.setModifyDate( new Date() );
                  clientOrderHeaderOtherVO.setEndDate( "" );
                  this.getClientOrderOtherDao().insertClientOrderOther( clientOrderHeaderOtherVO );
               }
            }

         }
         return count;
      }
   }

   @Override
   public void updateEmployeeSBBaseBySolution( final String orderId, final String sbSolutionId, final String accountId ) throws KANException
   {
      ( ( ClientOrderHeaderDao ) getDao() ).updateEmployeeSBBaseBySolution( orderId, sbSolutionId, accountId );
   }

   @Override
   // ��������Ա�����
   // Modify by siuvan.xia @2015-03-17
   public int calculateEmployeeAnnualLeave( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      // ���������������
      int rows = 0;

      try
      {
         // ��ȡ�����ݼ�����
         final List< Object > clientOrderLeaveVOs = this.getClientOrderLeaveDao().getClientOrderLeaveVOsByOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() );
         // ��ȡ�����������
         final List< ClientOrderLeaveVO > clientOrderAnnualLeaveVOs = getClientOrderAnnualLeaves( clientOrderLeaveVOs );
         // ��ȡ��������Ա��
         final List< Object > employeeContractVOs = this.getEmployeeContractService().getServiceEmployeeContractVOsByOrderId( clientOrderHeaderVO.getOrderHeaderId() );

         // ���ڼ����������
         if ( clientOrderAnnualLeaveVOs != null && clientOrderAnnualLeaveVOs.size() > 0 && employeeContractVOs != null && employeeContractVOs.size() > 0 )
         {
            // ��������
            this.startTransaction();

            // ��������ÿ��Ա�����
            for ( ClientOrderLeaveVO clientOrderAnnualLeaveVO : clientOrderAnnualLeaveVOs )
            {
               // ����������ٹ���
               if ( KANUtil.filterEmpty( clientOrderAnnualLeaveVO.getAnnualLeaveRuleId(), "0" ) != null )
               {
                  // ��ȡ��ٹ���DTO
                  final AnnualLeaveRuleDTO annualLeaveRuleDTO = KANConstants.getKANAccountConstants( clientOrderHeaderVO.getAccountId() ).getAnnualLeaveRuleDTOByHeaderId( clientOrderAnnualLeaveVO.getAnnualLeaveRuleId() );
                  // ��ٹ���������
                  if ( annualLeaveRuleDTO != null && annualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs() != null && annualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs().size() > 0 )
                  {
                     // ����������
                     String year = clientOrderAnnualLeaveVO.getYear();
                     // �������
                     String baseOn = annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getBaseOn();
                     // ���㷽ʽ
                     String divideType = annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getDivideType();

                     // ֻ���㵱ǰ��ݻ������
                     if ( KANUtil.filterEmpty( year ) == null || Integer.valueOf( year ) < Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) ) )
                     {
                        continue;
                     }

                     // ��������ÿ��Ա�����
                     for ( Object employeeContractVOObject : employeeContractVOs )
                     {
                        final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;
                        // ֻ������ְ��ͬ
                        if ( !"1".equals( employeeContractVO.getEmployStatus() ) )
                        {
                           continue;
                        }
                        // ��ȡEmployeeVO
                        final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
                        // ��˾ʱ��/�״ν��뼯��ʱ��
                        final String baseDate = "1".equals( baseOn ) ? employeeVO.getOnboardDate() : employeeVO.getStartWorkDate();

                        // Ա����ְʱ��������ڵ�ǰ��ĩ
                        if ( KANUtil.filterEmpty( year ) != null && KANUtil.filterEmpty( baseDate ) != null
                              && KANUtil.createDate( year + "-12-31" ).getTime() > KANUtil.createDate( baseDate ).getTime() )
                        {
                           final EmployeeContractLeaveVO searchEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
                           searchEmployeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
                           searchEmployeeContractLeaveVO.setItemId( "41" );
                           searchEmployeeContractLeaveVO.setYear( year );

                           // ����Ա������Ƿ����
                           final EmployeeContractLeaveVO currYearEmployeeContractLeaveVO = this.getEmployeeContractLeaveDao().getLastYearAnnualLeaveByCondition( searchEmployeeContractLeaveVO );

                           // û���������Ҫϵͳ��������
                           if ( currYearEmployeeContractLeaveVO == null )
                           {
                              // �Ƿ���Ҫ����
                              boolean isDivide = false;
                              // ����Сʱ��
                              double tempLegalHours = 0;
                              // ����Сʱ��
                              double tempBenefitHours = 0;
                              // ��ְ����
                              int months = 0;
                              // ��ְ����
                              long days = 0;
                              // ����Ƿ��½�
                              boolean isNew = false;
                              // ��ְ������
                              int gapYear = Integer.valueOf( year ) - Integer.valueOf( KANUtil.formatDate( baseDate, "yyyy" ) );

                              // ��Ҫ����
                              if ( KANUtil.filterEmpty( divideType, "0" ) != null )
                              {
                                 if ( KANUtil.createDate( baseDate ).getTime() >= KANUtil.createDate( year + "-01-01" ).getTime()
                                       && KANUtil.createDate( baseDate ).getTime() <= KANUtil.createDate( year + "-12-31" ).getTime() )
                                 {
                                    isDivide = true;
                                 }
                              }

                              months = KANUtil.getAboutGapMonth( baseDate, year + "-12-31" );
                              days = KANUtil.getDays( KANUtil.createDate( year + "-12-31" ) ) - KANUtil.getDays( KANUtil.createDate( baseDate ) );

                              // ��ٰ�Ա��ְ������
                              if ( KANUtil.filterEmpty( employeeVO.get_tempPositionGradeIds() ) != null )
                              {
                                 // ���ڶ��ְ��ʱ���ҵ���ˬ����ٹ���
                                 for ( String positionGradeId : employeeVO.get_tempPositionGradeIds().split( "," ) )
                                 {
                                    // �ҵ����ʵ���ٹ�����ϸ
                                    final AnnualLeaveRuleDetailVO searchAnnualLeaveRule = searchAnnualLeaveRuleDetail( annualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs(), positionGradeId, gapYear );
                                    // ���ں��ʵ���ٹ�����ϸ
                                    if ( searchAnnualLeaveRule != null )
                                    {
                                       isNew = true;
                                       if ( tempLegalHours < Double.valueOf( searchAnnualLeaveRule.getLegalHours() ) )
                                       {
                                          tempLegalHours = Double.valueOf( searchAnnualLeaveRule.getLegalHours() );
                                       }
                                       if ( tempBenefitHours < Double.valueOf( searchAnnualLeaveRule.getBenefitHours() ) )
                                       {
                                          tempBenefitHours = Double.valueOf( searchAnnualLeaveRule.getBenefitHours() );
                                       }
                                    }
                                 }

                                 if ( isNew )
                                 {
                                    // ��Ҫ����
                                    if ( isDivide )
                                    {
                                       // ����
                                       if ( "1".equals( divideType ) )
                                       {
                                          if ( tempLegalHours > 0 )
                                          {
                                             tempLegalHours = tempLegalHours / 12 * months;
                                          }
                                          if ( tempBenefitHours > 0 )
                                          {
                                             tempBenefitHours = tempBenefitHours / 12 * months;
                                          }
                                       }
                                       // ����
                                       else if ( "2".equals( divideType ) )
                                       {
                                          if ( tempLegalHours > 0 )
                                          {
                                             tempLegalHours = tempLegalHours / 365 * days;
                                          }
                                          if ( tempBenefitHours > 0 )
                                          {
                                             tempBenefitHours = tempBenefitHours / 365 * days;
                                          }
                                       }
                                    }
                                    rows = rows
                                          + addEmployeeAnnualLeave( employeeContractVO, clientOrderAnnualLeaveVO, clientOrderHeaderVO.getModifyBy(), year, tempLegalHours, tempBenefitHours );
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            // �ύ����
            this.commitTransaction();
         }
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   /**  
    * ���Һ��ʵ���ٹ���
    * 
    * @param annualLeaveRuleDetailVOs ������ϸ
    * @param positionGradeId ְ��ID
    * @param monthNum ��ְ����
    * @return AnnualLeaveRuleDetailVO
    */
   private AnnualLeaveRuleDetailVO searchAnnualLeaveRuleDetail( final List< AnnualLeaveRuleDetailVO > annualLeaveRuleDetailVOs, final String positionGradeId, final double seniority )
   {
      // ���շ���ֵ
      AnnualLeaveRuleDetailVO finalResult = null;

      if ( annualLeaveRuleDetailVOs != null && annualLeaveRuleDetailVOs.size() > 0 )
      {
         for ( AnnualLeaveRuleDetailVO o : annualLeaveRuleDetailVOs )
         {
            if ( seniority >= Double.valueOf( o.getSeniority() ) )
            {
               if ( KANUtil.filterEmpty( o.getPositionGradeId(), "0" ) == null )
               {
                  finalResult = o;
               }
               else if ( KANUtil.filterEmpty( o.getPositionGradeId(), "0" ) != null && positionGradeId.equals( KANUtil.filterEmpty( o.getPositionGradeId() ) ) )
               {
                  if ( seniority - Double.valueOf( o.getSeniority() ) < 1 )
                     return o;
               }
            }
         }
      }

      return finalResult;
   }

   /**  
    * �½�Ա���ݼ�����
    * 
    * @param employeeContractVO �Ͷ���ͬ
    * @param clientOrderAnnualLeaveVO ��������������
    * @param userId �û�ID
    * @param year ���
    * @param legalHours ����Сʱ��
    * @param benefitHours ����Сʱ��
    * @return 1
    * @throws KANException 
    */

   public static void main( String[] args )
   {
      double d = 0;

      System.out.println( Math.rint( d ) == 0.0 ? 0 : ( d % 4 == 0 ? d : ( d - d % 4 + 4 ) ) );
   }

   private int addEmployeeAnnualLeave( final EmployeeContractVO employeeContractVO, final ClientOrderLeaveVO clientOrderAnnualLeaveVO, final String userId, final String year,
         final double legalHours, final double benefitHours ) throws KANException
   {
      double legalQuantity = Math.rint( legalHours );
      double benefitQuantity = Math.rint( benefitHours );

      legalQuantity = legalQuantity == 0.0 ? 0.0 : ( legalQuantity % 4 == 0 ? legalQuantity : ( legalQuantity - legalQuantity % 4 + 4 ) );
      benefitQuantity = benefitQuantity == 0.0 ? 0.0 : ( benefitQuantity % 4 == 0 ? benefitQuantity : ( benefitQuantity - benefitQuantity % 4 + 4 ) );

      final EmployeeContractLeaveVO newEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
      newEmployeeContractLeaveVO.setLegalQuantity( String.valueOf( legalQuantity ) );
      newEmployeeContractLeaveVO.setBenefitQuantity( String.valueOf( benefitQuantity ) );
      newEmployeeContractLeaveVO.setAccountId( employeeContractVO.getAccountId() );
      newEmployeeContractLeaveVO.setCorpId( employeeContractVO.getCorpId() );
      newEmployeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
      newEmployeeContractLeaveVO.setItemId( "41" );
      newEmployeeContractLeaveVO.setYear( year );
      newEmployeeContractLeaveVO.setCycle( clientOrderAnnualLeaveVO.getCycle() );
      newEmployeeContractLeaveVO.setProbationUsing( clientOrderAnnualLeaveVO.getProbationUsing() );
      newEmployeeContractLeaveVO.setDelayUsing( clientOrderAnnualLeaveVO.getDelayUsing() );
      newEmployeeContractLeaveVO.setLegalQuantityDelayMonth( clientOrderAnnualLeaveVO.getLegalQuantityDelayMonth() );
      newEmployeeContractLeaveVO.setBenefitQuantityDelayMonth( clientOrderAnnualLeaveVO.getBenefitQuantityDelayMonth() );
      newEmployeeContractLeaveVO.setDescription( "Calculation of system generation" );
      newEmployeeContractLeaveVO.setCreateBy( userId );
      newEmployeeContractLeaveVO.setCreateDate( new Date() );
      newEmployeeContractLeaveVO.setModifyBy( userId );
      newEmployeeContractLeaveVO.setModifyDate( new Date() );
      newEmployeeContractLeaveVO.setStatus( "1" );

      return this.getEmployeeContractLeaveDao().insertEmployeeContractLeave( newEmployeeContractLeaveVO );
   }

   // ��ȡ�������
   private List< ClientOrderLeaveVO > getClientOrderAnnualLeaves( final List< Object > clientOrderLeaveVOs )
   {
      final List< ClientOrderLeaveVO > reusltAnnualLeaves = new ArrayList< ClientOrderLeaveVO >();
      if ( clientOrderLeaveVOs != null && clientOrderLeaveVOs.size() > 0 )
      {
         for ( Object o : clientOrderLeaveVOs )
         {
            if ( "41".equals( ( ( ClientOrderLeaveVO ) o ).getItemId() ) )
            {
               reusltAnnualLeaves.add( ( ClientOrderLeaveVO ) o );
            }
         }
      }

      return reusltAnnualLeaves;
   }

}
