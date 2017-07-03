package com.kan.hro.service.impl.biz.client;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.client.ClientContractDao;
import com.kan.hro.dao.inf.biz.client.ClientContractPropertyDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.service.inf.biz.client.ClientContractService;
import com.kan.hro.service.inf.cp.biz.client.CPClientContractService;
import com.kan.hro.web.actions.biz.client.ClientContractAction;

public class ClientContractServiceImpl extends ContextService implements ClientContractService, CPClientContractService
{
   // 对象类名
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.client.ClientContractVO";

   // Service Name
   public static final String SERVICE_BEAN = "clientContractService";

   // 注入ClientContractPropertyDao
   private ClientContractPropertyDao clientContractPropertyDao;

   // 注入ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // 注入WorkflowService
   private WorkflowService workflowService;

   public ClientContractPropertyDao getClientContractPropertyDao()
   {
      return clientContractPropertyDao;
   }

   public void setClientContractPropertyDao( ClientContractPropertyDao clientContractPropertyDao )
   {
      this.clientContractPropertyDao = clientContractPropertyDao;
   }

   public final WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public final void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
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
   public PagedListHolder getClientContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientContractDao clientContractDao = ( ClientContractDao ) getDao();
      pagedListHolder.setHolderSize( clientContractDao.countClientContractVOsByCondition( ( ClientContractVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientContractDao.getClientContractVOsByCondition( ( ClientContractVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientContractDao.getClientContractVOsByCondition( ( ClientContractVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientContractVO getClientContractVOByContractId( final String contractId ) throws KANException
   {
      return ( ( ClientContractDao ) getDao() ).getClientContractVOByContractId( contractId );
   }

   @Override
   public int updateClientContract( final ClientContractVO clientContractVO ) throws KANException
   {
      return ( ( ClientContractDao ) getDao() ).updateClientContract( clientContractVO );
   }

   @Override
   public int updateClientContract( final ClientContractVO clientContractVO, final List< ConstantVO > constantVOs ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // Update ClientContractVO
         ( ( ClientContractDao ) getDao() ).updateClientContract( clientContractVO );

         // Get ClientContractPropertyVO List
         final List< Object > objects = this.clientContractPropertyDao.getClientContractPropertyVOsByContractId( clientContractVO.getContractId() );

         // 先物理删除商务合同对应的Properties
         if ( objects != null )
         {
            for ( Object object : objects )
            {
               this.clientContractPropertyDao.deleteClientContractProperty( ( ( ClientContractPropertyVO ) object ).getPropertyId() );
            }
         }

         // 逐个插入商务合同对应的Properties
         if ( constantVOs != null )
         {
            for ( ConstantVO constantVO : constantVOs )
            {
               // 生成ClientContractPropertyVO对象
               final ClientContractPropertyVO clientContractPropertyVO = new ClientContractPropertyVO();
               clientContractPropertyVO.setContractId( clientContractVO.getContractId() );
               clientContractPropertyVO.setConstantId( constantVO.getConstantId() );
               clientContractPropertyVO.setPropertyName( constantVO.getPropertyName() );
               clientContractPropertyVO.setPropertyValue( constantVO.getContent() );
               clientContractPropertyVO.setStatus( ClientContractPropertyVO.TRUE );
               clientContractPropertyVO.setCreateBy( clientContractVO.getCreateBy() );
               clientContractPropertyVO.setModifyBy( clientContractVO.getModifyBy() );
               this.clientContractPropertyDao.insertClientContractProperty( clientContractPropertyVO );
            }
         }

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
   public int insertClientContract( final ClientContractVO clientContractVO ) throws KANException
   {
      return ( ( ClientContractDao ) getDao() ).insertClientContract( clientContractVO );
   }

   @Override
   public int deleteClientContract( final ClientContractVO clientContractVO ) throws KANException
   {
      // 标记删除ClientContractVO
      clientContractVO.setDeleted( ClientContractVO.FALSE );
      return ( ( ClientContractDao ) getDao() ).updateClientContract( clientContractVO );
   }

   @Override
   public List< Object > getClientContractBaseViews( final String accountId ) throws KANException
   {
      return ( ( ClientContractDao ) getDao() ).getClientContractBaseViews( accountId );
   }

   @Override
   public List< Object > getClientContractVOsByClientId( final String clientId ) throws KANException
   {
      return ( ( ClientContractDao ) getDao() ).getClientContractVOsByClientId( clientId );
   }

   @Override
   public int submitClientContract( final ClientContractVO clientContractVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( clientContractVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( clientContractVO );
         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( clientContractVO );
         if ( workflowActualDTO != null )
         {
            // Service的方法
            historyVO.setServiceMethod( "submitClientContract" );
            historyVO.setObjectId( clientContractVO.getContractId() );
            // 批准状态
            clientContractVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( clientContractVO ).toString();
            // 退回状态
            clientContractVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( clientContractVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, clientContractVO );

            // 状态改为待审核
            clientContractVO.setStatus( "2" );
            return updateClientContract( clientContractVO, clientContractVO.getConstantVOs() );
         }
         else
         {
            clientContractVO.setStatus( "3" );
            return updateClientContract( clientContractVO, clientContractVO.getConstantVOs() );
         }
      }

      return updateClientContract( clientContractVO );
   }

   // Generate HistoryVO
   private HistoryVO generateHistoryVO( final ClientContractVO clientContractVO )
   {
      final HistoryVO history = clientContractVO.getHistoryVO();
      history.setAccessAction( ClientContractAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( ClientContractAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getClientContractVOByContractId" );
      // “2”表示是工作流的
      history.setObjectType( "2" );
      history.setAccountId( clientContractVO.getAccountId() );
      history.setNameZH( clientContractVO.getNameZH() );
      history.setNameEN( clientContractVO.getNameEN() );
      return history;
   }

   @Override
   public int chopClientContract( final ClientContractVO clientContractVO ) throws KANException
   {
      final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
      clientOrderHeaderVO.setAccountId( clientContractVO.getAccountId() );
      clientOrderHeaderVO.setContractId( clientContractVO.getContractId() );
      clientOrderHeaderVO.setStatus( "3" );
      final List< Object > clientOrderHeaderVOs = this.clientOrderHeaderDao.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

      // 修改订单状态
      if ( clientOrderHeaderVOs != null && clientOrderHeaderVOs.size() > 0 )
      {
         for ( Object object : clientOrderHeaderVOs )
         {
            final ClientOrderHeaderVO tempClientOrderHeaderVO = ( ClientOrderHeaderVO ) object;
            // 修改为"生效"
            tempClientOrderHeaderVO.setStatus( "5" );

            tempClientOrderHeaderVO.setModifyBy( clientContractVO.getModifyBy() );
            tempClientOrderHeaderVO.setModifyDate( new Date() );
            this.clientOrderHeaderDao.updateClientOrderHeader( tempClientOrderHeaderVO );
         }
      }

      return ( ( ClientContractDao ) getDao() ).updateClientContract( clientContractVO );
   }

   @Override
   public int getArchiveClientContractCount( final String clientId )
   {
      final ClientContractDao clientContractDao = ( ClientContractDao ) getDao();
      return clientContractDao.getArchiveClientContractCount( clientId );
   }
}
