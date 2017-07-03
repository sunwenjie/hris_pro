package com.kan.base.service.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.BusinessTypeDao;
import com.kan.base.domain.security.BusinessTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.BusinessTypeService;
import com.kan.base.util.KANException;

public class BusinessTypeServiceImpl extends ContextService implements BusinessTypeService
{

   @Override
   public PagedListHolder getBusinessTypeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final BusinessTypeDao businessTypeDao = ( BusinessTypeDao ) getDao();
      pagedListHolder.setHolderSize( businessTypeDao.countBusinessTypeVOsByCondition( ( BusinessTypeVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( businessTypeDao.getBusinessTypeVOsByCondition( ( BusinessTypeVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( businessTypeDao.getBusinessTypeVOsByCondition( ( BusinessTypeVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public BusinessTypeVO getBusinessTypeVOByBusinessTypeId( final String businessTypeId ) throws KANException
   {
      return ( ( BusinessTypeDao ) getDao() ).getBusinessTypeVOByBusinessTypeId( businessTypeId );
   }

   @Override
   public int insertBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException
   {
      return ( ( BusinessTypeDao ) getDao() ).insertBusinessType( businessTypeVO );
   }

   @Override
   public int updateBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException
   {
      return ( ( BusinessTypeDao ) getDao() ).updateBusinessType( businessTypeVO );
   }

   @Override
   public int deleteBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final BusinessTypeVO modifyObject = ( ( BusinessTypeDao ) getDao() ).getBusinessTypeVOByBusinessTypeId( businessTypeVO.getBusinessTypeId() );
      modifyObject.setDeleted( BusinessTypeVO.FALSE );
      return ( ( BusinessTypeDao ) getDao() ).updateBusinessType( modifyObject );
   }

   @Override
   public List< Object > getBusinessTypeVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( BusinessTypeDao ) getDao() ).getBusinessTypeVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getBusinessTypeBaseViews( final String accountId ) throws KANException
   {
      return ( ( BusinessTypeDao ) getDao() ).getBusinessTypeBaseViews( accountId );
   }

}
