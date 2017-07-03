package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.IndustryTypeDao;
import com.kan.base.domain.management.IndustryTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.IndustryTypeService;
import com.kan.base.util.KANException;

public class IndustryTypeServiceImpl extends ContextService implements IndustryTypeService
{

   @Override
   public PagedListHolder getIndustryTypeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final IndustryTypeDao industryTypeDao = ( IndustryTypeDao ) getDao();
      pagedListHolder.setHolderSize( industryTypeDao.countIndustryTypeVOsByCondition( ( IndustryTypeVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( industryTypeDao.getIndustryTypeVOsByCondition( ( IndustryTypeVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( industryTypeDao.getIndustryTypeVOsByCondition( ( IndustryTypeVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public IndustryTypeVO getIndustryTypeVOByIndustryTypeId( final String industryTypeId ) throws KANException
   {
      return ( ( IndustryTypeDao ) getDao() ).getIndustryTypeVOByIndustryTypeId( industryTypeId );
   }

   @Override
   public int insertIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException
   {
      return ( ( IndustryTypeDao ) getDao() ).insertIndustryType( industryTypeVO );
   }

   @Override
   public int updateIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException
   {
      return ( ( IndustryTypeDao ) getDao() ).updateIndustryType( industryTypeVO );
   }

   @Override
   public int deleteIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException
   {
      // 标记删除
      final IndustryTypeVO modifyObject = ( ( IndustryTypeDao ) getDao() ).getIndustryTypeVOByIndustryTypeId( industryTypeVO.getTypeId() );
      modifyObject.setDeleted( IndustryTypeVO.FALSE );
      return ( ( IndustryTypeDao ) getDao() ).updateIndustryType( modifyObject );
   }

   @Override
   public List< Object > getIndustryTypeVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( IndustryTypeDao ) getDao() ).getIndustryTypeVOsByAccountId( accountId );
   }

   @Override
   public List< IndustryTypeVO > getIndustryTypeVOsByIndustryTypeVO( IndustryTypeVO industryTypeVO ) throws KANException
   {
      List< IndustryTypeVO > industryTypeVOList = new ArrayList< IndustryTypeVO >();
      // 根据industryTypeVO查找包含Object的industryTypeVO的List
      List< Object > objectIndustryTypeVOList = ( ( IndustryTypeDao ) getDao() ).getIndustryTypeVOsByCondition( industryTypeVO );
      // 如果不为空，把为Object转换为IndustryTypeVO
      if ( objectIndustryTypeVOList != null && objectIndustryTypeVOList.size() > 0 )
      {
         for ( Object objectIndustryTypeVO : objectIndustryTypeVOList )
         {
            IndustryTypeVO industryTypeVOTemp = ( IndustryTypeVO ) objectIndustryTypeVO;
            industryTypeVOList.add( industryTypeVOTemp );
         }
      }
      return industryTypeVOList;
   }

}
