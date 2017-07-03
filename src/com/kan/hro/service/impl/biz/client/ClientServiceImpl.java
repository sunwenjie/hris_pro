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
   // 对象类名
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.client.ClientVO";

   // Service Name
   public static final String SERVICE_BEAN = "clientService";

   // 注入WorkflowService
   private WorkflowService workflowService;

   // 注入ClientContactDao
   private ClientContactDao clientContactDao;

   // 注入ClientContractDao
   private ClientContractDao clientContractDao;

   // 注入ClientInvoiceDao
   private ClientInvoiceDao clientInvoiceDao;

   // 注入ClientOrderHeaderDao
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
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   /**  
    * Delete ClientContactVO
    * 标记删除联系人
    *
    * @param clientVO
    * @throws KANException
    */
   private void deleteClientContactVOs( final ClientVO clientVO ) throws KANException
   {
      // 实例化ClientContactVO
      ClientContactVO clientContactVO = new ClientContactVO();
      clientContactVO.setAccountId( clientVO.getAccountId() );
      clientContactVO.setClientId( clientVO.getClientId() );
      clientContactVO.setCorpId( clientVO.getCorpId() );
      // 获得客户对应ClientContactVO 列表
      final List< Object > clientContactVOs = this.clientContactDao.getClientContactVOsByCondition( clientContactVO );

      // 删除
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
    * 标记删除商务合同
    *
    * @param clientVO
    * @throws KANException
    */
   private void deleteClientContractVOs( final ClientVO clientVO ) throws KANException
   {
      // 实例化ClientContractVO
      ClientContractVO clientContractVO = new ClientContractVO();
      clientContractVO.setAccountId( clientVO.getAccountId() );
      clientContractVO.setClientId( clientVO.getClientId() );
      clientContractVO.setCorpId( clientVO.getCorpId() );
      // 获得客户对应ClientContractVO 列表
      final List< Object > clientContractVOs = this.clientContractDao.getClientContractVOsByCondition( clientContractVO );

      // 删除
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
    * 标记删除客户订单
    *
    * @param clientVO
    * @throws KANException
    */
   private void deleteClientOrderHeaderVOs( final ClientVO clientVO ) throws KANException
   {
      // 实例化ClientOrderHeaderVO
      ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
      clientOrderHeaderVO.setAccountId( clientVO.getAccountId() );
      clientOrderHeaderVO.setClientId( clientVO.getClientId() );
      clientOrderHeaderVO.setCorpId( clientVO.getCorpId() );
      // 获得客户对应ClientOrderHeaderVO 列表
      final List< Object > clientOrderHeaderVOs = this.clientOrderHeaderDao.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

      // 删除
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
    * 标记删除账单地址
    *
    * @param clientVO
    * @throws KANException
    */
   private void deleteClientInvoiceVOs( final ClientVO clientVO ) throws KANException
   {
      // 实例化ClientInvoiceVO
      ClientInvoiceVO clientInvoiceVO = new ClientInvoiceVO();
      clientInvoiceVO.setAccountId( clientVO.getAccountId() );
      clientInvoiceVO.setClientId( clientVO.getClientId() );
      clientInvoiceVO.setCorpId( clientVO.getCorpId() );
      // 获得客户对应ClientInvoiceVO 列表
      final List< Object > clientInvoiceVOs = this.clientInvoiceDao.getClientInvoiceVOsByCondition( clientInvoiceVO );

      // 删除
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
         // 回滚事务
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
         // 开启事务
         this.startTransaction();

         // 标记删除clientVO
         clientVO.setDeleted( ClientVO.FALSE );
         ( ( ClientDao ) getDao() ).updateClient( clientVO );

         // 标记删除合同
         deleteClientContractVOs( clientVO );

         // 标记删除订单
         deleteClientOrderHeaderVOs( clientVO );

         // 标记删除联系人
         deleteClientContactVOs( clientVO );

         // 标记删除账单地址
         deleteClientInvoiceVOs( clientVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
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
      // 实例化ClientDTO
      final ClientDTO clientDTO = new ClientDTO();

      try
      {
         // 开启事务
         this.startTransaction();

         // 根据主键查找对应的ClientVO
         final ClientVO clientVOForAdd = ( ( ClientDao ) getDao() ).getClientVOByClientId( clientVO.getClientId() );

         // 把clientVO添加到clientDTO中
         clientDTO.setClientVO( clientVOForAdd );

         // ClientDTO添加对应ClientVO的ClientContactVO List
         fetchClientContactVOs( clientDTO, clientVO );

         // ClientDTO添加对应ClientVO的ClientInvoiceVO List
         fetchClientInvoiceVOs( clientDTO, clientVO );

         // ClientDTO添加对应ClientVO的ClientContractVO List
         fetchClientContractVOs( clientDTO, clientVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return clientDTO;
   }

   /**  
    * Fetch ClientContactVO
    * 查找并添加联系人
    *
    * @param clientDTO
    * @param clientVO
    * @throws KANException
    */
   private void fetchClientContactVOs( final ClientDTO clientDTO, final ClientVO clientVO ) throws KANException
   {
      // 实例化ClientContactVO
      final ClientContactVO clientContactVO = new ClientContactVO();
      clientContactVO.setAccountId( clientVO.getAccountId() );
      clientContactVO.setClientId( clientVO.getClientId() );
      clientContactVO.setCorpId( clientVO.getCorpId() );
      // 查询Client对应的ClientContactVOs
      final List< Object > clientContactVOs = this.clientContactDao.getClientContactVOsByCondition( clientContactVO );

      if ( clientContactVOs != null && clientContactVOs.size() > 0 )
      {
         // 遍历
         for ( Object clientContactVOObject : clientContactVOs )
         {
            ClientContactVO tempClientContactVO = ( ClientContactVO ) clientContactVOObject;
            // 添加到clientDTO中
            clientDTO.getClientContactVOs().add( tempClientContactVO );
         }
      }
   }

   /**  
    * Fetch ClientContractVO
    * 查找并添加商务合同
    * @param clientDTO
    * @param clientVO
    * @throws KANException
    */
   private void fetchClientContractVOs( final ClientDTO clientDTO, final ClientVO clientVO ) throws KANException
   {

      // 实例化ClientContractVO
      final ClientContractVO clientContractVO = new ClientContractVO();
      clientContractVO.setAccountId( clientVO.getAccountId() );
      clientContractVO.setClientId( clientVO.getClientId() );
      clientContractVO.setCorpId( clientVO.getCorpId() );
      // 查询Client对应的ClientContractVOs
      final List< Object > clientContractVOs = this.clientContractDao.getClientContractVOsByCondition( clientContractVO );

      if ( clientContractVOs != null && clientContractVOs.size() > 0 )
      {
         // 遍历
         for ( Object clientContractVOObject : clientContractVOs )
         {
            ClientContractVO tempClientContractVO = ( ClientContractVO ) clientContractVOObject;
            // 添加到clientDTO中
            clientDTO.getClientContractVOs().add( tempClientContractVO );
         }
      }

   }

   /**  
    * Fetch ClientInvoiceVO
    * 查找并添加账单地址
    * @param clientDTO
    * @param clientVO
    * @throws KANException
    */
   private void fetchClientInvoiceVOs( final ClientDTO clientDTO, final ClientVO clientVO ) throws KANException
   {
      // 实例化ClientInvoiceVO
      final ClientInvoiceVO clientInvoiceVO = new ClientInvoiceVO();
      clientInvoiceVO.setAccountId( clientVO.getAccountId() );
      clientInvoiceVO.setClientId( clientVO.getClientId() );
      clientInvoiceVO.setCorpId( clientVO.getCorpId() );

      // 查询Client对应的ClientInvoiceVOs
      final List< Object > clientInvoiceVOs = this.clientInvoiceDao.getClientInvoiceVOsByCondition( clientInvoiceVO );

      if ( clientInvoiceVOs != null && clientInvoiceVOs.size() > 0 )
      {
         // 遍历
         for ( Object clientInvoiceVOObject : clientInvoiceVOs )
         {
            ClientInvoiceVO tempClientInvoiceVO = ( ClientInvoiceVO ) clientInvoiceVOObject;
            // 添加到clientDTO中
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

      // 装载ClientVO
      clientDTO.setClientVO( this.getClientVOByClientId( clientId ) );

      // 装载ClientContactVO
      final List< Object > clientContactVOs = this.getClientContactDao().getClientContactVOsByClientId( clientId );
      if ( clientContactVOs != null && clientContactVOs.size() > 0 )
      {
         for ( Object clientContactVOObject : clientContactVOs )
         {
            clientDTO.getClientContactVOs().add( ( ClientContactVO ) clientContactVOObject );
         }
      }

      // 装载ClientInvoiceVO
      final List< Object > clientInvoiceVOs = this.getClientInvoiceDao().getClientInvoiceVOsByClientId( clientId );
      if ( clientInvoiceVOs != null && clientInvoiceVOs.size() > 0 )
      {
         for ( Object clientInvoiceVOObject : clientInvoiceVOs )
         {
            clientDTO.getClientInvoiceVOs().add( ( ClientInvoiceVO ) clientInvoiceVOObject );
         }
      }

      // 装载ClientContractVO
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
            // 权限Id
            historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

            final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( clientVO );
            // 存在工作流
            if ( workflowActualDTO != null )
            {
               if ( clientVO.getStatus() != null && !clientVO.getStatus().trim().equals( "3" ) )
               {
                  // 状态改为待审核
                  clientVO.setStatus( "2" );
                  updateClient( clientVO );
               }

               // Service的方法
               historyVO.setServiceMethod( "submitClient" );
               historyVO.setObjectId( clientVO.getClientId() );

               // 批准状态
               clientVO.setStatus( "3" );
               final String passObject = KANUtil.toJSONObject( clientVO ).toString();

               // 退回状态
               clientVO.setStatus( "4" );
               final String failObject = KANUtil.toJSONObject( clientVO ).toString();

               historyVO.setPassObject( passObject );
               historyVO.setFailObject( failObject );

               workflowService.createWorkflowActual( workflowActualDTO, clientVO );
            }
            // 没有工作流
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
         // 回滚事务
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
      // 表示是工作流的
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
      //处理客户菜单
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
