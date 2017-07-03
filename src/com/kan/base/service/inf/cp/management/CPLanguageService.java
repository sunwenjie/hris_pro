package com.kan.base.service.inf.cp.management;

import java.util.List;

import com.kan.base.domain.management.LanguageVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CPLanguageService
{
   public abstract PagedListHolder getLanguageVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract LanguageVO getLanguageVOByLanguageId( final String languageId ) throws KANException;

   public abstract int insertLanguage( final LanguageVO languageVO ) throws KANException;

   public abstract int updateLanguage( final LanguageVO languageVO ) throws KANException;

   public abstract int deleteLanguage( final LanguageVO languageVO ) throws KANException;

   public abstract List< Object > getLanguageVOsByAccountId( final String accountId ) throws KANException;
}
