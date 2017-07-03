package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.BusinessContractTemplateDao;
import com.kan.base.domain.management.BusinessContractTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.BusinessContractTemplateService;
import com.kan.base.util.KANException;

public class BusinessContractTemplateServiceImpl extends ContextService implements BusinessContractTemplateService
{

   @Override
   public PagedListHolder getBusinessContractTemplateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final BusinessContractTemplateDao businessContractTemplateDao = ( BusinessContractTemplateDao ) getDao();
      pagedListHolder.setHolderSize( businessContractTemplateDao.countBusinessContractTemplateVOsByCondition( ( BusinessContractTemplateVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( businessContractTemplateDao.getBusinessContractTemplateVOsByCondition( ( BusinessContractTemplateVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( businessContractTemplateDao.getBusinessContractTemplateVOsByCondition( ( BusinessContractTemplateVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public BusinessContractTemplateVO getBusinessContractTemplateVOByBusinessContractTemplateId( final String businessContractTemplateId ) throws KANException
   {
      return ( ( BusinessContractTemplateDao ) getDao() ).getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateId );
   }

   @Override
   public int insertBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException
   {
      return ( ( BusinessContractTemplateDao ) getDao() ).insertBusinessContractTemplate( businessContractTemplateVO );
   }

   @Override
   public int updateBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException
   {
      return ( ( BusinessContractTemplateDao ) getDao() ).updateBusinessContractTemplate( businessContractTemplateVO );
   }

   @Override
   public int deleteBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final BusinessContractTemplateVO modifyObject = ( ( BusinessContractTemplateDao ) getDao() ).getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateVO.getTemplateId() );
      modifyObject.setDeleted( BusinessContractTemplateVO.FALSE );
      return ( ( BusinessContractTemplateDao ) getDao() ).updateBusinessContractTemplate( modifyObject );
   }

   @Override
   public List< Object > getBusinessContractTemplateVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( BusinessContractTemplateDao ) getDao() ).getBusinessContractTemplateVOsByAccountId( accountId );
   }

}
