package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.LanguageVO;
import com.kan.base.util.KANException;

public interface LanguageDao
{
   public abstract int countLanguageVOsByCondition( final LanguageVO languageVO ) throws KANException;

   public abstract List< Object > getLanguageVOsByCondition( final LanguageVO languageVO ) throws KANException;

   public abstract List< Object > getLanguageVOsByCondition( final LanguageVO languageVO, RowBounds rowBounds ) throws KANException;

   public abstract LanguageVO getLanguageVOByLanguageId( final String languageId ) throws KANException;

   public abstract int insertLanguage( final LanguageVO languageVO ) throws KANException;

   public abstract int updateLanguage( final LanguageVO languageVO ) throws KANException;

   public abstract int deleteLanguage( final LanguageVO languageVO ) throws KANException;

   public abstract List< Object > getLanguageVOsByAccountId( final String accountId ) throws KANException;
}
