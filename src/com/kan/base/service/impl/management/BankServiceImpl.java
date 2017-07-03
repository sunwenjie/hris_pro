package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.BankDao;
import com.kan.base.domain.management.BankVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.BankService;
import com.kan.base.util.KANException;

public class BankServiceImpl extends ContextService implements BankService
{

   @Override
   public PagedListHolder getBankVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final BankDao bankDao = ( BankDao ) getDao();
      pagedListHolder.setHolderSize( bankDao.countBankVOsByCondition( ( BankVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( bankDao.getBankVOsByCondition( ( BankVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize()
               + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( bankDao.getBankVOsByCondition( ( BankVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public BankVO getBankVOByBankId( final String bankId ) throws KANException
   {
      return ( ( BankDao ) getDao() ).getBankVOByBankId( bankId );
   }

   @Override
   public int insertBank( final BankVO bankVO ) throws KANException
   {
      return ( ( BankDao ) getDao() ).insertBank( bankVO );
   }

   @Override
   public int updateBank( final BankVO bankVO ) throws KANException
   {
      return ( ( BankDao ) getDao() ).updateBank( bankVO );
   }

   @Override
   public int deleteBank( final BankVO bankVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final BankVO modifyObject = ( ( BankDao ) getDao() ).getBankVOByBankId( bankVO.getBankId() );
      modifyObject.setDeleted( BankVO.FALSE );
      return ( ( BankDao ) getDao() ).updateBank( modifyObject );
   }

   @Override
   public List< Object > getBankVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( BankDao ) getDao() ).getBankVOsByAccountId( accountId );
   }

}
