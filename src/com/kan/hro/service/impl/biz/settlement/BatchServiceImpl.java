package com.kan.hro.service.impl.biz.settlement;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.BatchDao;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractDao;
import com.kan.hro.domain.biz.settlement.BatchVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.settlement.BatchService;

public class BatchServiceImpl extends ContextService implements BatchService
{
   // 注入 ServiceContractDao
   private ServiceContractDao serviceContractDao;

   public ServiceContractDao getServiceContractDao()
   {
      return serviceContractDao;
   }

   public void setServiceContractDao( ServiceContractDao serviceContractDao )
   {
      this.serviceContractDao = serviceContractDao;
   }

   @Override
   public PagedListHolder getBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final BatchDao batchDao = ( BatchDao ) getDao();
      pagedListHolder.setHolderSize( batchDao.countBatchVOsByCondition( ( BatchVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( batchDao.getBatchVOsByCondition( ( BatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( batchDao.getBatchVOsByCondition( ( BatchVO ) pagedListHolder.getObject() ) );
      }

      // 添加包含雇员人数
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object batchVOObject : pagedListHolder.getSource() )
         {
            final BatchVO batchVO = ( BatchVO ) batchVOObject;

            fetchSettlementBatchVO( batchVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * Fetch Settlement BatchVO
    *	
    *	@param batchVO
    *	@throws KANException
    */
   private void fetchSettlementBatchVO( final BatchVO batchVO ) throws KANException
   {
      // 初始化包含的雇员集合
      final List< MappingVO > employees = new ArrayList< MappingVO >();
      // 初始化查询对象
      final ServiceContractVO serviceContractVO = new ServiceContractVO();
      serviceContractVO.setAccountId( batchVO.getAccountId() );
      serviceContractVO.setBatchId( batchVO.getBatchId() );
      final List< Object > serviceContractVOs = this.serviceContractDao.getServiceContractVOsByCondition( serviceContractVO );

      if ( serviceContractVOs != null && serviceContractVOs.size() > 0 )
      {
         for ( Object object : serviceContractVOs )
         {
            ServiceContractVO tempServiceContractVO = ( ServiceContractVO ) object;

            if ( !isEmployeeIdExist( tempServiceContractVO, employees ) )
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( tempServiceContractVO.getEmployeeId() );
               mappingVO.setMappingValue( tempServiceContractVO.getEmployeeNameZH() );
               mappingVO.setMappingTemp( tempServiceContractVO.getEmployeeNameEN() );
               employees.add( mappingVO );
            }

         }
      }

      batchVO.setEmployees( employees );
   }

   /**  
    * IsEmployeeIdExist
    * 判断雇员ID是否存在
    *	@param tempServiceContractVO
    *	@param employees
    *	@return
    */
   private boolean isEmployeeIdExist( final ServiceContractVO tempServiceContractVO, final List< MappingVO > employees )
   {

      if ( tempServiceContractVO == null || tempServiceContractVO.getEmployeeId() == null )
      {
         return false;
      }

      if ( employees != null && employees.size() > 0 )
      {
         for ( MappingVO mappingVO : employees )
         {
            if ( mappingVO.getMappingId().equals( tempServiceContractVO.getEmployeeId() ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public BatchVO getBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( ( BatchDao ) getDao() ).getBatchVOByBatchId( batchId );
   }

   @Override
   public int updateBatch( final BatchVO batchVO ) throws KANException
   {
      return ( ( BatchDao ) getDao() ).updateBatch( batchVO );
   }

   @Override
   public int insertBatch( final BatchVO batchVO ) throws KANException
   {
      return ( ( BatchDao ) getDao() ).insertBatch( batchVO );
   }

   @Override
   public int deleteBatch( final String batchId ) throws KANException
   {
      return ( ( BatchDao ) getDao() ).deleteBatch( batchId );
   }

   @Override
   public List< Object > getBatchVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( BatchDao ) getDao() ).getBatchVOsByAccountId( accountId );
   }

   @Override
   public BatchVO getLastestBatchVOByAccountId( final String accountId ) throws KANException
   {
      return ( ( BatchDao ) getDao() ).getLastestBatchVOByAccountId( accountId );
   }

}
