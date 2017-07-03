package com.kan.hro.service.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractTempDao;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;
import com.kan.hro.service.inf.biz.settlement.ServiceContractTempService;

public class ServiceContractTempServiceImpl extends ContextService implements ServiceContractTempService
{

   @Override
   public PagedListHolder getServiceContractTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ServiceContractTempDao serviceContractTempDao = ( ServiceContractTempDao ) getDao();
      pagedListHolder.setHolderSize( serviceContractTempDao.countServiceContractTempVOsByCondition( ( ServiceContractTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( serviceContractTempDao.getServiceContractTempVOsByCondition( ( ServiceContractTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( serviceContractTempDao.getServiceContractTempVOsByCondition( ( ServiceContractTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ServiceContractTempVO getServiceContractTempVOByContractId( final String contractId ) throws KANException
   {
      return ( ( ServiceContractTempDao ) getDao() ).getServiceContractTempVOByContractId( contractId );
   }

   @Override
   public int updateServiceContractTemp( final ServiceContractTempVO serviceContractTempVO ) throws KANException
   {
      return ( ( ServiceContractTempDao ) getDao() ).updateServiceContractTemp( serviceContractTempVO );
   }

   @Override
   public int insertServiceContractTemp( final ServiceContractTempVO serviceContractTempVO ) throws KANException
   {
      return ( ( ServiceContractTempDao ) getDao() ).insertServiceContractTemp( serviceContractTempVO );
   }

   @Override
   public int deleteServiceContractTemp( final String contractTempId ) throws KANException
   {
      return ( ( ServiceContractTempDao ) getDao() ).deleteServiceContractTemp( contractTempId );
   }

   @Override
   public List< Object > getServiceContractTempVOsByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( ( ServiceContractTempDao ) getDao() ).getServiceContractTempVOsByOrderHeaderId( orderHeaderId );
   }

}
