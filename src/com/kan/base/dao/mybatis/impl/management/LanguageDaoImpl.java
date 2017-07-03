package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.LanguageDao;
import com.kan.base.domain.management.LanguageVO;
import com.kan.base.util.KANException;

public class LanguageDaoImpl extends Context implements LanguageDao
{

   @Override
   public int countLanguageVOsByCondition( final LanguageVO languageVO ) throws KANException
   {
      return ( Integer ) select( "countLanguageVOsByCondition", languageVO );
   }

   @Override
   public List< Object > getLanguageVOsByCondition( final LanguageVO languageVO ) throws KANException
   {
      return selectList( "getLanguageVOsByCondition", languageVO );
   }

   @Override
   public List< Object > getLanguageVOsByCondition( final LanguageVO languageVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getLanguageVOsByCondition", languageVO, rowBounds );
   }

   @Override
   public LanguageVO getLanguageVOByLanguageId( final String languageId ) throws KANException
   {
      return ( LanguageVO ) select( "getLanguageVOByLanguageId", languageId );
   }

   @Override
   public int insertLanguage( final LanguageVO languageVO ) throws KANException
   {
      return insert( "insertLanguage", languageVO );
   }

   @Override
   public int updateLanguage( final LanguageVO languageVO ) throws KANException
   {
      return update( "updateLanguage", languageVO );
   }

   @Override
   public int deleteLanguage( final LanguageVO languageVO ) throws KANException
   {
      return delete( "deleteLanguage", languageVO );
   }

   @Override
   public List< Object > getLanguageVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getLanguageVOsByAccountId", accountId );
   }

}
