package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.BankTemplateDetailDao;
import com.kan.base.domain.define.BankTemplateDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.BankTemplateDetailService;
import com.kan.base.util.KANException;

public class BankTemplateDetailServiceImpl extends ContextService implements BankTemplateDetailService
{

   @Override
   public PagedListHolder getBankTemplateDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final BankTemplateDetailDao bankTemplateDetailDao = ( BankTemplateDetailDao ) getDao();
      pagedListHolder.setHolderSize( bankTemplateDetailDao.countBankTemplateDetailVOsByCondition( ( BankTemplateDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( bankTemplateDetailDao.getBankTemplateDetailVOsByCondition( ( BankTemplateDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( bankTemplateDetailDao.getBankTemplateDetailVOsByCondition( ( BankTemplateDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public BankTemplateDetailVO getBankTemplateDetailVOByTemplateDetailId( final String templateDetailId ) throws KANException
   {
      return ( ( BankTemplateDetailDao ) getDao() ).getBankTemplateDetailVOByTemplateDetailId( templateDetailId );
   }

   @Override
   public int insertBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException
   {
      return ( ( BankTemplateDetailDao ) getDao() ).insertBankTemplateDetail( bankTemplateDetailVO );
   }

   @Override
   public int updateBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException
   {
      return ( ( BankTemplateDetailDao ) getDao() ).updateBankTemplateDetail( bankTemplateDetailVO );
   }

   @Override
   public int deleteBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException
   {
      bankTemplateDetailVO.setDeleted( BankTemplateDetailVO.FALSE );
      return ( ( BankTemplateDetailDao ) getDao() ).updateBankTemplateDetail( bankTemplateDetailVO );
   }

   @Override
   public List< Object > getBankTemplateDetailVOsByTemplateHeaderId( final String templateHeaderId ) throws KANException
   {
      return ( ( BankTemplateDetailDao ) getDao() ).getBankTemplateDetailVOsByTemplateHeaderId( templateHeaderId );
   }

}
