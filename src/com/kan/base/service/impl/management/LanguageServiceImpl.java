package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.LanguageDao;
import com.kan.base.domain.management.LanguageVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPLanguageService;
import com.kan.base.service.inf.management.LanguageService;
import com.kan.base.util.KANException;

public class LanguageServiceImpl extends ContextService implements LanguageService,CPLanguageService
{

   @Override
   public PagedListHolder getLanguageVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final LanguageDao languageDao = ( LanguageDao ) getDao();
      pagedListHolder.setHolderSize( languageDao.countLanguageVOsByCondition( ( LanguageVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( languageDao.getLanguageVOsByCondition( ( LanguageVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( languageDao.getLanguageVOsByCondition( ( LanguageVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public LanguageVO getLanguageVOByLanguageId( final String languageId ) throws KANException
   {
      return ( ( LanguageDao ) getDao() ).getLanguageVOByLanguageId( languageId );
   }

   @Override
   public int insertLanguage( final LanguageVO languageVO ) throws KANException
   {
      return ( ( LanguageDao ) getDao() ).insertLanguage( languageVO );
   }

   @Override
   public int updateLanguage( final LanguageVO languageVO ) throws KANException
   {
      return ( ( LanguageDao ) getDao() ).updateLanguage( languageVO );
   }

   @Override
   public int deleteLanguage( final LanguageVO languageVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final LanguageVO modifyObject = ( ( LanguageDao ) getDao() ).getLanguageVOByLanguageId( languageVO.getLanguageId() );
      modifyObject.setDeleted( LanguageVO.FALSE );
      return ( ( LanguageDao ) getDao() ).updateLanguage( modifyObject );
   }

   @Override
   public List< Object > getLanguageVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( LanguageDao ) getDao() ).getLanguageVOsByAccountId( accountId );
   }

}
