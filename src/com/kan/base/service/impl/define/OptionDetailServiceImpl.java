package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.OptionDetailDao;
import com.kan.base.domain.define.OptionDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.OptionDetailService;
import com.kan.base.util.KANException;

public class OptionDetailServiceImpl extends ContextService implements OptionDetailService
{

   @Override
   public PagedListHolder getOptionDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final OptionDetailDao optionDetailDao = ( OptionDetailDao ) getDao();
      pagedListHolder.setHolderSize( optionDetailDao.countOptionDetailVOsByCondition( ( OptionDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( optionDetailDao.getOptionDetailVOsByCondition( ( OptionDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( optionDetailDao.getOptionDetailVOsByCondition( ( OptionDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public OptionDetailVO getOptionDetailVOByOptionDetailId( final String optionDetailId ) throws KANException
   {
      return ( ( OptionDetailDao ) getDao() ).getOptionDetailVOByOptionDetailId( optionDetailId );
   }

   @Override
   public int insertOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException
   {
      return ( ( OptionDetailDao ) getDao() ).insertOptionDetail( optionDetailVO );
   }

   @Override
   public int updateOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException
   {
      return ( ( OptionDetailDao ) getDao() ).updateOptionDetail( optionDetailVO );
   }

   @Override
   public int deleteOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      optionDetailVO.setDeleted( OptionDetailVO.FALSE );
      return ( ( OptionDetailDao ) getDao() ).updateOptionDetail( optionDetailVO );
   }

   @Override
   public List< Object > getOptionDetailVOsByOptionHeaderId( final String optionHeaderId ) throws KANException
   {
      return ( ( OptionDetailDao ) getDao() ).getOptionDetailVOsByOptionHeaderId( optionHeaderId );
   }

   @Override
   public String getMaxOptionId( final String optionHeaderId ) throws KANException
   {
      return ( ( OptionDetailDao ) getDao() ).getMaxOptionId( optionHeaderId );
   }

}
