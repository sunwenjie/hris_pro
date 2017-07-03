package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ResignTemplateDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.ResignTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ResignTemplateService;
import com.kan.base.util.KANException;

public class ResignTemplateServiceImpl extends ContextService implements ResignTemplateService
{

   @Override
   public PagedListHolder getResignTemplateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ResignTemplateDao ResignTemplateDao = ( ResignTemplateDao ) getDao();
      pagedListHolder.setHolderSize( ResignTemplateDao.countResignTemplateVOsByCondition( ( ResignTemplateVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( ResignTemplateDao.getResignTemplateVOsByCondition( ( ResignTemplateVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( ResignTemplateDao.getResignTemplateVOsByCondition( ( ResignTemplateVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ResignTemplateVO getResignTemplateVOByResignTemplateId( final String resignTemplateId ) throws KANException
   {
      return ( ( ResignTemplateDao ) getDao() ).getResignTemplateVOByResignTemplateId( resignTemplateId );
   }

   @Override
   public int insertResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException
   {
      return ( ( ResignTemplateDao ) getDao() ).insertResignTemplate( ResignTemplateVO );
   }

   @Override
   public int updateResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException
   {
      return ( ( ResignTemplateDao ) getDao() ).updateResignTemplate( ResignTemplateVO );
   }

   @Override
   public int deleteResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final ResignTemplateVO modifyObject = ( ( ResignTemplateDao ) getDao() ).getResignTemplateVOByResignTemplateId( ResignTemplateVO.getTemplateId() );
      modifyObject.setDeleted( BaseVO.FALSE );
      return ( ( ResignTemplateDao ) getDao() ).updateResignTemplate( modifyObject );
   }

   @Override
   public List< Object > getResignTemplateVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( ResignTemplateDao ) getDao() ).getResignTemplateVOsByAccountId( accountId );
   }

}
