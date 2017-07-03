package com.kan.hro.service.impl.biz.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.ContractTemplateClientVo;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.client.ClientContactDao;
import com.kan.hro.dao.inf.biz.client.ClientContractDao;
import com.kan.hro.dao.inf.biz.client.ClientDao;
import com.kan.hro.dao.inf.biz.client.ClientInvoiceDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.domain.biz.client.ClientBaseView;
import com.kan.hro.domain.biz.client.ClientContactVO;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientDTO;
import com.kan.hro.domain.biz.client.ClientInvoiceVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.cp.biz.client.CPClientService;
import com.kan.hro.web.actions.biz.client.ClientAction;

public class ClientServiceImpl extends ContextService implements ClientService, CPClientService
{
   // ��������
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.client.ClientVO";

   // Service Name
   public static final String SERVICE_BEAN = "clientService";

   // ע��WorkflowService
   private WorkflowService workflowService;

   // ע��ClientContactDao
   private ClientContactDao clientContactDao;

   // ע��ClientContractDao
   private ClientContractDao clientContractDao;

   // ע��ClientInvoiceDao
   private ClientInvoiceDao clientInvoiceDao;

   // ע��ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   private ModuleDao moduleDao;

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   public ClientContactDao getClientContactDao()
   {
      return clientContactDao;
   }

   public void setClientContactDao( ClientContactDao clientContactDao )
   {
      this.clientContactDao = clientContactDao;
   }

   public ClientContractDao getClientContractDao()
   {
      return clientContractDao;
   }

   public void setClientContractDao( ClientContractDao clientContractDao )
   {
      this.clientContractDao = clientContractDao;
   }

   public ClientInvoiceDao getClientInvoiceDao()
   {
      return clientInvoiceDao;
   }

   public void setClientInvoiceDao( ClientInvoiceDao clientInvoiceDao )
   {
      this.clientInvoiceDao = clientInvoiceDao;
   }

   public ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   @Override
   public PagedListHolder getClientVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientDao clientDao = ( ClientDao ) getDao();
      pagedListHolder.setHolderSize( clientDao.countClientVOsByCondition( ( ClientVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientDao.getClientVOsByCondition( ( ClientVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientDao.getClientVOsByCondition( ( ClientVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientVO getClientVOByClientId( final String clientId ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).getClientVOByClientId( clientId );
   }

   @Override
   public ClientVO getClientVOByClientIdForPdf( String clientId ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).getClientVOByClientIdForPdf( clientId );
   }

   @Override
   public ClientVO getClientVOByCorpId( final String corpId ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).getClientVOByCorpId( corpId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-12
   public int updateClient( final ClientVO clientVO ) throws KANException
   {
      try
      {
         this.startTransaction();
         ( ( ClientDao ) getDao() ).updateClient( clientVO );

         ModuleVO clientModuleVO = new ModuleVO();
         clientModuleVO.setModuleIdArray( clientVO.getModuleIdArray() );
         clientModuleVO.setAccountId( clientVO.getAccountId() );
         clientModuleVO.setMenuRole( KANConstants.ROLE_CLIENT );
         clientModuleVO.setClientId( clientVO.getClientId() );
         clientModuleVO.setCreateBy( clientVO.getCreateBy() );
         clientModuleVO.setCreateDate( clientVO.getCreateDate() );
         updateClientModule( clientModuleVO );

         //delete employee menu
         ModuleVO employeeModuleVO = new ModuleVO();
         employeeModuleVO.setModuleIdArray( clientVO.getEmployeeModuleIdArray() );
         employeeModuleVO.setAccountId( clientVO.getAccountId() );
         employeeModuleVO.setMenuRole( KANConstants.ROLE_EMPLOYEE );
         employeeModuleVO.setClientId( clientVO.getClientId() );
         employeeModuleVO.setCreateBy( clientVO.getCreateBy() );
         employeeModuleVO.setCreateDate( clientVO.getCreateDate() );
         updateClientModule( employeeModuleVO );
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   /**  
    * Delete ClientContactVO
    * ���ɾ����ϵ��
    *
    * @param clientVO
    * @throws KANException
    */
   private void deleteClientContactVOs( final ClientVO clientVO ) throws KANException
   {
      // ʵ����ClientContactVO
      ClientContactVO clientContactVO = new ClientContactVO();
      clientContactVO.setAccountId( clientVO.getAccountId() );
      clientContactVO.setClientId( clientVO.getClientId() );
      clientContactVO.setCorpId( clientVO.getCorpId() );
      // ��ÿͻ���ӦClientContactVO �б�
      final List< Object > clientContactVOs = this.clientContactDao.getClientContactVOsByCondition( clientContactVO );

      // ɾ��
      if ( clientContactVOs != null && clientContactVOs.size() > 0 )
      {
         for ( Object object : clientContactVOs )
         {
            ClientContactVO tempClientContactVO = ( ClientContactVO ) object;
            tempClientContactVO.setDeleted( ClientContactVO.FALSE );
            tempClientContactVO.setModifyBy( clientVO.getModifyBy() );
            tempClientContactVO.setModifyDate( new Date() );
            this.clientContactDao.updateClientContact( tempClientContactVO );
         }
      }
   }

   /**  
    * Delete ClientContractVO
    * ���ɾ�������ͬ
    *
    * @param clientVO
    * @throws KANException
    */
   private void deleteClientContractVOs( final ClientVO clientVO ) throws KANException
   {
      // ʵ����ClientContractVO
      ClientContractVO clientContractVO = new ClientContractVO();
      clientContractVO.setAccountId( clientVO.getAccountId() );
      clientContractVO.setClientId( clientVO.getClientId() );
      clientContractVO.setCorpId( clientVO.getCorpId() );
      // ��ÿͻ���ӦClientContractVO �б�
      final List< Object > clientContractVOs = this.clientContractDao.getClientContractVOsByCondition( clientContractVO );

      // ɾ��
      if ( clientContractVOs != null && clientContractVOs.size() > 0 )
      {
         for ( Object object : clientContractVOs )
         {
            ClientContractVO tempClientContractVO = ( ClientContractVO ) object;
            tempClientContractVO.setDeleted( ClientContractVO.FALSE );
            tempClientContractVO.setModifyBy( clientVO.getModifyBy() );
            tempClientContractVO.setModifyDate( new Date() );
            this.clientContractDao.updateClientContract( tempClientContractVO );
         }
      }

   }

   /**  
    * Delete ClientOrderHeaderVO
    * ���ɾ���ͻ�����
    *
    * @param clientVO
    * @throws KANException
    */
   private void deleteClientOrderHeaderVOs( final ClientVO clientVO ) throws KANException
   {
      // ʵ����ClientOrderHeaderVO
      ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
      clientOrderHeaderVO.setAccountId( clientVO.getAccountId() );
      clientOrderHeaderVO.setClientId( clientVO.getClientId() );
      clientOrderHeaderVO.setCorpId( clientVO.getCorpId() );
      // ��ÿͻ���ӦClientOrderHeaderVO �б�
      final List< Object > clientOrderHeaderVOs = this.clientOrderHeaderDao.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

      // ɾ��
      if ( clientOrderHeaderVOs != null && clientOrderHeaderVOs.size() > 0 )
      {
         for ( Object object : clientOrderHeaderVOs )
         {
            ClientOrderHeaderVO tempClientOrderHeaderVO = ( ClientOrderHeaderVO ) object;
            tempClientOrderHeaderVO.setDeleted( ClientOrderHeaderVO.FALSE );
            tempClientOrderHeaderVO.setModifyBy( clientVO.getModifyBy() );
            tempClientOrderHeaderVO.setModifyDate( new Date() );
            this.clientOrderHeaderDao.updateClientOrderHeader( tempClientOrderHeaderVO );
         }
      }

   }

   /**  
    * Delete ClientInvoiceVO
    * ���ɾ���˵���ַ
    *
    * @param clientVO
    * @throws KANException
    */
   private void deleteClientInvoiceVOs( final ClientVO clientVO ) throws KANException
   {
      // ʵ����ClientInvoiceVO
      ClientInvoiceVO clientInvoiceVO = new ClientInvoiceVO();
      clientInvoiceVO.setAccountId( clientVO.getAccountId() );
      clientInvoiceVO.setClientId( clientVO.getClientId() );
      clientInvoiceVO.setCorpId( clientVO.getCorpId() );
      // ��ÿͻ���ӦClientInvoiceVO �б�
      final List< Object > clientInvoiceVOs = this.clientInvoiceDao.getClientInvoiceVOsByCondition( clientInvoiceVO );

      // ɾ��
      if ( clientInvoiceVOs != null && clientInvoiceVOs.size() > 0 )
      {
         for ( Object object : clientInvoiceVOs )
         {
            ClientInvoiceVO tempClientInvoiceVO = ( ClientInvoiceVO ) object;
            tempClientInvoiceVO.setDeleted( ClientInvoiceVO.FALSE );
            tempClientInvoiceVO.setModifyBy( clientVO.getModifyBy() );
            tempClientInvoiceVO.setModifyDate( new Date() );
            this.clientInvoiceDao.updateClientInvoice( tempClientInvoiceVO );
         }
      }

   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-13
   public void insertClient( final ClientVO clientVO ) throws KANException
   {
      try
      {
         clientVO.setStatus( "1" );
         clientVO.setDeleted( "1" );
         ( ( ClientDao ) getDao() ).insertClient( clientVO );
         if ( clientVO.getSubAction() != null && clientVO.getSubAction().trim().equals( BaseAction.SUBMIT_OBJECT ) )
         {
            submitClient( clientVO );
         }
         else
         {
            this.startTransaction();
            ModuleVO clientModuleVO = new ModuleVO();
            clientModuleVO.setModuleIdArray( clientVO.getModuleIdArray() );
            clientModuleVO.setAccountId( clientVO.getAccountId() );
            clientModuleVO.setMenuRole( KANConstants.ROLE_CLIENT );
            clientModuleVO.setClientId( clientVO.getClientId() );
            clientModuleVO.setCreateBy( clientVO.getCreateBy() );
            clientModuleVO.setCreateDate( clientVO.getCreateDate() );
            updateClientModule( clientModuleVO );

            //delete employee menu
            ModuleVO employeeModuleVO = new ModuleVO();
            employeeModuleVO.setModuleIdArray( clientVO.getEmployeeModuleIdArray() );
            employeeModuleVO.setAccountId( clientVO.getAccountId() );
            employeeModuleVO.setMenuRole( KANConstants.ROLE_EMPLOYEE );
            employeeModuleVO.setClientId( clientVO.getClientId() );
            employeeModuleVO.setCreateBy( clientVO.getCreateBy() );
            employeeModuleVO.setCreateDate( clientVO.getCreateDate() );
            updateClientModule( employeeModuleVO );
            this.commitTransaction();
         }
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-13
   public int deleteClient( final ClientVO clientVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // ���ɾ��clientVO
         clientVO.setDeleted( ClientVO.FALSE );
         ( ( ClientDao ) getDao() ).updateClient( clientVO );

         // ���ɾ����ͬ
         deleteClientContractVOs( clientVO );

         // ���ɾ������
         deleteClientOrderHeaderVOs( clientVO );

         // ���ɾ����ϵ��
         deleteClientContactVOs( clientVO );

         // ���ɾ���˵���ַ
         deleteClientInvoiceVOs( clientVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public List< Object > getClientBaseViews( final String accountId ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).getClientBaseViews( accountId );
   }

   @Override
   public List< Object > getClientBaseViewsByCondition( final ClientBaseView clientBaseView ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).getClientBaseViewsByCondition( clientBaseView );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-13
   public ClientDTO getClientDTOByClientVO( final ClientVO clientVO ) throws KANException
   {
      // ʵ����ClientDTO
      final ClientDTO clientDTO = new ClientDTO();

      try
      {
         // ��������
         this.startTransaction();

         // �����������Ҷ�Ӧ��ClientVO
         final ClientVO clientVOForAdd = ( ( ClientDao ) getDao() ).getClientVOByClientId( clientVO.getClientId() );

         // ��clientVO��ӵ�clientDTO��
         clientDTO.setClientVO( clientVOForAdd );

         // ClientDTO��Ӷ�ӦClientVO��ClientContactVO List
         fetchClientContactVOs( clientDTO, clientVO );

         // ClientDTO��Ӷ�ӦClientVO��ClientInvoiceVO List
         fetchClientInvoiceVOs( clientDTO, clientVO );

         // ClientDTO��Ӷ�ӦClientVO��ClientContractVO List
         fetchClientContractVOs( clientDTO, clientVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return clientDTO;
   }

   /**  
    * Fetch ClientContactVO
    * ���Ҳ������ϵ��
    *
    * @param clientDTO
    * @param clientVO
    * @throws KANException
    */
   private void fetchClientContactVOs( final ClientDTO clientDTO, final ClientVO clientVO ) throws KANException
   {
      // ʵ����ClientContactVO
      final ClientContactVO clientContactVO = new ClientContactVO();
      clientContactVO.setAccountId( clientVO.getAccountId() );
      clientContactVO.setClientId( clientVO.getClientId() );
      clientContactVO.setCorpId( clientVO.getCorpId() );
      // ��ѯClient��Ӧ��ClientContactVOs
      final List< Object > clientContactVOs = this.clientContactDao.getClientContactVOsByCondition( clientContactVO );

      if ( clientContactVOs != null && clientContactVOs.size() > 0 )
      {
         // ����
         for ( Object clientContactVOObject : clientContactVOs )
         {
            ClientContactVO tempClientContactVO = ( ClientContactVO ) clientContactVOObject;
            // ��ӵ�clientDTO��
            clientDTO.getClientContactVOs().add( tempClientContactVO );
         }
      }
   }

   /**  
    * Fetch ClientContractVO
    * ���Ҳ���������ͬ
    * @param clientDTO
    * @param clientVO
    * @throws KANException
    */
   private void fetchClientContractVOs( final ClientDTO clientDTO, final ClientVO clientVO ) throws KANException
   {

      // ʵ����ClientContractVO
      final ClientContractVO clientContractVO = new ClientContractVO();
      clientContractVO.setAccountId( clientVO.getAccountId() );
      clientContractVO.setClientId( clientVO.getClientId() );
      clientContractVO.setCorpId( clientVO.getCorpId() );
      // ��ѯClient��Ӧ��ClientContractVOs
      final List< Object > clientContractVOs = this.clientContractDao.getClientContractVOsByCondition( clientContractVO );

      if ( clientContractVOs != null && clientContractVOs.size() > 0 )
      {
         // ����
         for ( Object clientContractVOObject : clientContractVOs )
         {
            ClientContractVO tempClientContractVO = ( ClientContractVO ) clientContractVOObject;
            // ��ӵ�clientDTO��
            clientDTO.getClientContractVOs().add( tempClientContractVO );
         }
      }

   }

   /**  
    * Fetch ClientInvoiceVO
    * ���Ҳ�����˵���ַ
    * @param clientDTO
    * @param clientVO
    * @throws KANException
    */
   private void fetchClientInvoiceVOs( final ClientDTO clientDTO, final ClientVO clientVO ) throws KANException
   {
      // ʵ����ClientInvoiceVO
      final ClientInvoiceVO clientInvoiceVO = new ClientInvoiceVO();
      clientInvoiceVO.setAccountId( clientVO.getAccountId() );
      clientInvoiceVO.setClientId( clientVO.getClientId() );
      clientInvoiceVO.setCorpId( clientVO.getCorpId() );

      // ��ѯClient��Ӧ��ClientInvoiceVOs
      final List< Object > clientInvoiceVOs = this.clientInvoiceDao.getClientInvoiceVOsByCondition( clientInvoiceVO );

      if ( clientInvoiceVOs != null && clientInvoiceVOs.size() > 0 )
      {
         // ����
         for ( Object clientInvoiceVOObject : clientInvoiceVOs )
         {
            ClientInvoiceVO tempClientInvoiceVO = ( ClientInvoiceVO ) clientInvoiceVOObject;
            // ��ӵ�clientDTO��
            clientDTO.getClientInvoiceVOs().add( tempClientInvoiceVO );
         }
      }

   }

   @Override
   public List< Object > getClientFullViews( final String accountId ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).getClientFullViews( accountId );
   }

   @Override
   public ClientDTO getClientDTOByClientId( final String clientId ) throws KANException
   {
      final ClientDTO clientDTO = new ClientDTO();

      // װ��ClientVO
      clientDTO.setClientVO( this.getClientVOByClientId( clientId ) );

      // װ��ClientContactVO
      final List< Object > clientContactVOs = this.getClientContactDao().getClientContactVOsByClientId( clientId );
      if ( clientContactVOs != null && clientContactVOs.size() > 0 )
      {
         for ( Object clientContactVOObject : clientContactVOs )
         {
            clientDTO.getClientContactVOs().add( ( ClientContactVO ) clientContactVOObject );
         }
      }

      // װ��ClientInvoiceVO
      final List< Object > clientInvoiceVOs = this.getClientInvoiceDao().getClientInvoiceVOsByClientId( clientId );
      if ( clientInvoiceVOs != null && clientInvoiceVOs.size() > 0 )
      {
         for ( Object clientInvoiceVOObject : clientInvoiceVOs )
         {
            clientDTO.getClientInvoiceVOs().add( ( ClientInvoiceVO ) clientInvoiceVOObject );
         }
      }

      // װ��ClientContractVO
      final List< Object > clientContractVOs = this.getClientContractDao().getClientContractVOsByClientId( clientId );
      if ( clientContractVOs != null && clientContractVOs.size() > 0 )
      {
         for ( Object clientContractVOObject : clientContractVOs )
         {
            clientDTO.getClientContractVOs().add( ( ClientContractVO ) clientContractVOObject );
         }
      }

      return clientDTO;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-13
   public int submitClient( final ClientVO clientVO ) throws KANException
   {
      int row = -1;
      try
      {
         if ( !WorkflowService.isPassObject( clientVO ) )
         {
            final HistoryVO historyVO = generateHistoryVO( clientVO );
            // Ȩ��Id
            historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

            final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( clientVO );
            // ���ڹ�����
            if ( workflowActualDTO != null )
            {
               if ( clientVO.getStatus() != null && !clientVO.getStatus().trim().equals( "3" ) )
               {
                  // ״̬��Ϊ�����
                  clientVO.setStatus( "2" );
                  updateClient( clientVO );
               }

               // Service�ķ���
               historyVO.setServiceMethod( "submitClient" );
               historyVO.setObjectId( clientVO.getClientId() );

               // ��׼״̬
               clientVO.setStatus( "3" );
               final String passObject = KANUtil.toJSONObject( clientVO ).toString();

               // �˻�״̬
               clientVO.setStatus( "4" );
               final String failObject = KANUtil.toJSONObject( clientVO ).toString();

               historyVO.setPassObject( passObject );
               historyVO.setFailObject( failObject );

               workflowService.createWorkflowActual( workflowActualDTO, clientVO );
            }
            // û�й�����
            else
            {
               clientVO.setStatus( "3" );
               row = updateClient( clientVO );
            }
         }
         else
         {
            row = updateClient( clientVO );
         }
         this.startTransaction();
         ModuleVO clientModuleVO = new ModuleVO();
         clientModuleVO.setModuleIdArray( clientVO.getModuleIdArray() );
         clientModuleVO.setAccountId( clientVO.getAccountId() );
         clientModuleVO.setMenuRole( KANConstants.ROLE_CLIENT );
         clientModuleVO.setClientId( clientVO.getClientId() );
         clientModuleVO.setCreateBy( clientVO.getCreateBy() );
         clientModuleVO.setCreateDate( clientVO.getCreateDate() );
         updateClientModule( clientModuleVO );

         //delete employee menu
         ModuleVO employeeModuleVO = new ModuleVO();
         employeeModuleVO.setModuleIdArray( clientVO.getEmployeeModuleIdArray() );
         employeeModuleVO.setAccountId( clientVO.getAccountId() );
         employeeModuleVO.setMenuRole( KANConstants.ROLE_EMPLOYEE );
         employeeModuleVO.setClientId( clientVO.getClientId() );
         employeeModuleVO.setCreateBy( clientVO.getCreateBy() );
         employeeModuleVO.setCreateDate( clientVO.getCreateDate() );
         updateClientModule( employeeModuleVO );
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return row;
   }

   private HistoryVO generateHistoryVO( final ClientVO clientVO )
   {
      final HistoryVO history = clientVO.getHistoryVO();
      history.setAccessAction( ClientAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( ClientAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getClientVOByClientId" );
      // ��ʾ�ǹ�������
      history.setObjectType( "2" );
      history.setAccountId( clientVO.getAccountId() );
      history.setNameZH( clientVO.getNameZH() );
      history.setNameEN( clientVO.getNameEN() );
      return history;
   }

   @Override
   public List< Object > getClientVOsByEmployeeId( final String employeeId ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).getClientVOsByEmployeeId( employeeId );
   }

   @Override
   public int delClientAndGroupRelationByClientId( final ClientVO clientVO ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).delClientAndGroupRelationByClientId( clientVO );
   }

   //jzy add 2014/04/09
   @Override
   public List< Object > getClientByAccountId( String accountId ) throws KANException
   {

      final ClientDao clientDao = ( ClientDao ) getDao();
      return clientDao.getClientByAccountId( accountId );
   }

   //jzy add 2014/04/09
   @Override
   public List< ContractTemplateClientVo > clientIdsForTab( String templateId, String clientIds ) throws KANException
   {

      List< ContractTemplateClientVo > contractTemplateClientVoList = new ArrayList< ContractTemplateClientVo >();
      if ( StringUtils.isNotEmpty( clientIds ) )
      {
         String[] clientIdArray = KANUtil.jasonArrayToStringArray( clientIds );
         for ( String clientId : clientIdArray )
         {
            final ClientDao clientDao = ( ClientDao ) getDao();
            ClientBaseView clientBaseView = clientDao.getClientNameById( clientId );
            if ( clientBaseView != null )
            {
               ContractTemplateClientVo vo = new ContractTemplateClientVo();
               vo.setClientId( clientId );
               vo.setClientName( clientBaseView.getName() );
               contractTemplateClientVoList.add( vo );
            }
         }
      }
      return contractTemplateClientVoList;
   }

   @Override
   public ClientVO getClientVOByTitle( String title ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).getClientVOByTitle( title );
   }

   @Override
   public ClientVO getClientVOByName( final String name ) throws KANException
   {
      return ( ( ClientDao ) getDao() ).getClientVOByName( name );
   }

   public ModuleDao getModuleDao()
   {
      return moduleDao;
   }

   public void setModuleDao( ModuleDao moduleDao )
   {
      this.moduleDao = moduleDao;
   }

   private void updateClientModule( final ModuleVO moduleVO ) throws KANException
   {
      //����ͻ��˵�
      String[] moduleIdArray = moduleVO.getModuleIdArray();
      ModuleVO module = new ModuleVO();
      module.setAccountId( moduleVO.getAccountId() );
      module.setMenuRole( moduleVO.getMenuRole() );
      module.setClientId( moduleVO.getClientId() );
      moduleDao.deleteClientModuleByClient( module );
      if ( moduleIdArray != null )
      {
         for ( String moduleId : moduleIdArray )
         {
            module = new ModuleVO();
            module.setModuleId( moduleId );
            module.setAccountId( moduleVO.getAccountId() );
            module.setClientId( moduleVO.getClientId() );
            module.setMenuRole( moduleVO.getMenuRole() );
            module.setCreateBy( moduleVO.getCreateBy() );
            module.setCreateDate( moduleVO.getCreateDate() );
            moduleDao.insertClientModule( module );
         }
      }
   }
}
