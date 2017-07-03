package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.EducationDao;
import com.kan.base.domain.management.EducationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPEducationService;
import com.kan.base.service.inf.management.EducationService;
import com.kan.base.util.KANException;

public class EducationServiceImpl extends ContextService implements EducationService,CPEducationService
{

   @Override
   public PagedListHolder getEducationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final EducationDao educationDao = ( EducationDao ) getDao();
      pagedListHolder.setHolderSize( educationDao.countEducationVOsByCondition( ( EducationVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( educationDao.getEducationVOsByCondition( ( EducationVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( educationDao.getEducationVOsByCondition( ( EducationVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public EducationVO getEducationVOByEducationId( final String educationId ) throws KANException
   {
      return ( ( EducationDao ) getDao() ).getEducationVOByEducationId( educationId );
   }

   @Override
   public int insertEducation( final EducationVO educationVO ) throws KANException
   {
      return ( ( EducationDao ) getDao() ).insertEducation( educationVO );
   }

   @Override
   public int updateEducation( final EducationVO educationVO ) throws KANException
   {
      return ( ( EducationDao ) getDao() ).updateEducation( educationVO );
   }

   @Override
   public int deleteEducation( final EducationVO educationVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final EducationVO modifyObject = ( ( EducationDao ) getDao() ).getEducationVOByEducationId( educationVO.getEducationId() );
      modifyObject.setDeleted( EducationVO.FALSE );
      return ( ( EducationDao ) getDao() ).updateEducation( modifyObject );
   }

   @Override
   public List< Object > getEducationVOsByAccountId( String accountId ) throws KANException
   {
      return ( ( EducationDao ) getDao() ).getEducationVOsByAccountId( accountId );
   }

}
