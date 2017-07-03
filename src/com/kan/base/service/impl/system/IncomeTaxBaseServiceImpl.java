package com.kan.base.service.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.IncomeTaxBaseDao;
import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxBaseService;
import com.kan.base.util.KANException;

public class IncomeTaxBaseServiceImpl extends ContextService implements IncomeTaxBaseService
{
   @Override
   public List< Object > getIncomeTaxBaseVOsByCondition( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException
   {
      return ( ( IncomeTaxBaseDao ) getDao() ).getIncomeTaxBaseVOsByCondition( incomeTaxBaseVO );
   }

   @Override
   public PagedListHolder getIncomeTaxBaseVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final IncomeTaxBaseDao constantDao = ( IncomeTaxBaseDao ) getDao();
      pagedListHolder.setHolderSize( constantDao.countIncomeTaxBaseVOsByCondition( ( IncomeTaxBaseVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( constantDao.getIncomeTaxBaseVOsByCondition( ( IncomeTaxBaseVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( constantDao.getIncomeTaxBaseVOsByCondition( ( IncomeTaxBaseVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public IncomeTaxBaseVO getIncomeTaxBaseVOByBaseId( final String baseId ) throws KANException
   {
      return ( ( IncomeTaxBaseDao ) getDao() ).getIncomeTaxBaseVOByBaseId( baseId );
   }

   @Override
   public int insertIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException
   {
      return ( ( IncomeTaxBaseDao ) getDao() ).insertIncomeTaxBase( incomeTaxBaseVO );
   }

   @Override
   public int updateIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException
   {
      return ( ( IncomeTaxBaseDao ) getDao() ).updateIncomeTaxBase( incomeTaxBaseVO );
   }

   @Override
   public int deleteIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      incomeTaxBaseVO.setDeleted( IncomeTaxBaseVO.FALSE );
      return updateIncomeTaxBase( incomeTaxBaseVO );
   }

}
