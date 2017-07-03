package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.LaborContractTemplateDao;
import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPLaborContractTemplateService;
import com.kan.base.service.inf.management.LaborContractTemplateService;
import com.kan.base.util.KANException;

public class LaborContractTemplateServiceImpl extends ContextService implements LaborContractTemplateService,CPLaborContractTemplateService
{

   @Override
   public PagedListHolder getLaborContractTemplateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final LaborContractTemplateDao laborContractTemplateDao = ( LaborContractTemplateDao ) getDao();
      pagedListHolder.setHolderSize( laborContractTemplateDao.countLaborContractTemplateVOsByCondition( ( LaborContractTemplateVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( laborContractTemplateDao.getLaborContractTemplateVOsByCondition( ( LaborContractTemplateVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( laborContractTemplateDao.getLaborContractTemplateVOsByCondition( ( LaborContractTemplateVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public LaborContractTemplateVO getLaborContractTemplateVOByLaborContractTemplateId( final String laborContractTemplateId ) throws KANException
   {
      return ( ( LaborContractTemplateDao ) getDao() ).getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateId );
   }

   @Override
   public int insertLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException
   {
      return ( ( LaborContractTemplateDao ) getDao() ).insertLaborContractTemplate( laborContractTemplateVO );
   }

   @Override
   public int updateLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException
   {
      return ( ( LaborContractTemplateDao ) getDao() ).updateLaborContractTemplate( laborContractTemplateVO );
   }

   @Override
   public int deleteLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final LaborContractTemplateVO modifyObject = ( ( LaborContractTemplateDao ) getDao() ).getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateVO.getTemplateId() );
      modifyObject.setDeleted( LaborContractTemplateVO.FALSE );
      return ( ( LaborContractTemplateDao ) getDao() ).updateLaborContractTemplate( modifyObject );
   }

   @Override
   public List< Object > getLaborContractTemplateVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( LaborContractTemplateDao ) getDao() ).getLaborContractTemplateVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getLaborContractTemplateVOsByContractTypeId( String contractTypeId ) throws KANException
   {
      return ( ( LaborContractTemplateDao ) getDao() ).getLaborContractTemplateVOsByContractTypeId( contractTypeId );
   }

}
