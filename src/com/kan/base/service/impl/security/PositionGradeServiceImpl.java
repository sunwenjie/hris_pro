package com.kan.base.service.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.PositionGradeDao;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.PositionGradeService;
import com.kan.base.util.KANException;

public class PositionGradeServiceImpl extends ContextService implements PositionGradeService
{

   @Override
   public PagedListHolder getPositionGradeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final PositionGradeDao positionGradeDao = ( PositionGradeDao ) getDao();
      pagedListHolder.setHolderSize( positionGradeDao.countPositionGradeVOsByCondition( ( PositionGradeVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( positionGradeDao.getPositionGradeVOsByCondition( ( PositionGradeVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( positionGradeDao.getPositionGradeVOsByCondition( ( PositionGradeVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;

   }

   @Override
   public PositionGradeVO getPositionGradeVOByPositionGradeId( final String positionGardeId ) throws KANException
   {

      return ( ( PositionGradeDao ) getDao() ).getPositionGradeVOByPositionGradeId( positionGardeId );
   }

   @Override
   public void deletePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException
   {
      positionGradeVO.setDeleted( PositionGradeVO.FALSE );
      ( ( PositionGradeDao ) getDao() ).updatePositionGrade( positionGradeVO );
   }

   @Override
   public int updatePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException
   {
      return ( ( PositionGradeDao ) getDao() ).updatePositionGrade( positionGradeVO );
   }

   @Override
   public int insertPositionGrade( final PositionGradeVO positionGradeVO ) throws KANException
   {
      return ( ( PositionGradeDao ) getDao() ).insertPositionGrade( positionGradeVO );
   }

   @Override
   public List< Object > getPositionGradeVOsByAccountId( String accountId ) throws KANException
   {
      return ( ( PositionGradeDao ) getDao() ).getPositionGradeVOsByAccountId( accountId );
   }

}
