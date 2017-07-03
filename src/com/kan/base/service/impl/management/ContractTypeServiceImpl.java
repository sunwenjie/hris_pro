package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ContractTypeDao;
import com.kan.base.domain.management.ContractTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ContractTypeService;
import com.kan.base.util.KANException;

public class ContractTypeServiceImpl extends ContextService implements ContractTypeService
{

   @Override
   public PagedListHolder getContractTypeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ContractTypeDao contractTypeDao = ( ContractTypeDao ) getDao();
      pagedListHolder.setHolderSize( contractTypeDao.countContractTypeVOsByCondition( ( ContractTypeVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( contractTypeDao.getContractTypeVOsByCondition( ( ContractTypeVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( contractTypeDao.getContractTypeVOsByCondition( ( ContractTypeVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ContractTypeVO getContractTypeVOByTypeId( final String typeId ) throws KANException
   {
      return ( ( ContractTypeDao ) getDao() ).getContractTypeVOByTypeId( typeId );
   }

   @Override
   public int insertContractType( final ContractTypeVO contractTypeVO ) throws KANException
   {
      return ( ( ContractTypeDao ) getDao() ).insertContractType( contractTypeVO );
   }

   @Override
   public int updateContractType( final ContractTypeVO contractTypeVO ) throws KANException
   {
      return ( ( ContractTypeDao ) getDao() ).updateContractType( contractTypeVO );
   }

   @Override
   public int deleteContractType( final ContractTypeVO contractTypeVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final ContractTypeVO modifyObject = ( ( ContractTypeDao ) getDao() ).getContractTypeVOByTypeId( contractTypeVO.getTypeId() );
      modifyObject.setDeleted( ContractTypeVO.FALSE );
      return ( ( ContractTypeDao ) getDao() ).updateContractType( modifyObject );
   }

   @Override
   public List< Object > getContractTypeVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( ContractTypeDao ) getDao() ).getContractTypeVOsByAccountId( accountId );
   }
}
