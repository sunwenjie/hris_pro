package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.SocialBenefitSolutionHeaderDao;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.util.KANException;

public class SocialBenefitSolutionHeaderDaoImpl extends Context implements SocialBenefitSolutionHeaderDao
{

   @Override
   public int countSocialBenefitSolutionHeaderVOsByCondition( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSocialBenefitSolutionHeaderVOsByCondition", socialBenefitSolutionHeaderVO );
   }

   @Override
   public List< Object > getSocialBenefitSolutionHeaderVOsByCondition( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return selectList( "getSocialBenefitSolutionHeaderVOsByCondition", socialBenefitSolutionHeaderVO );
   }

   @Override
   public List< Object > getSocialBenefitSolutionHeaderVOsByCondition( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO, final RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getSocialBenefitSolutionHeaderVOsByCondition", socialBenefitSolutionHeaderVO, rowBounds );
   }

   @Override
   public SocialBenefitSolutionHeaderVO getSocialBenefitSolutionHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( SocialBenefitSolutionHeaderVO ) select( "getSocialBenefitSolutionHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return insert( "insertSocialBenefitSolutionHeader", socialBenefitSolutionHeaderVO );
   }

   @Override
   public int updateSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return update( "updateSocialBenefitSolutionHeader", socialBenefitSolutionHeaderVO );
   }

   @Override
   public int deleteSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return delete( "deleteSocialBenefitSolutionHeader", socialBenefitSolutionHeaderVO );
   }

   @Override
   public List< Object > getSocialBenefitSolutionHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getSocialBenefitSolutionHeaderVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getSocialBenefitSolutionHeaderVOsBySysHeaderId( final String sysHeaderId ) throws KANException
   {
      return selectList( "getSocialBenefitSolutionHeaderVOsBySysHeaderId", sysHeaderId );
   }

   @Override
   public List< Object > getSocialBenefitSolutionHeaderVOsBySysSBHeaderVO( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return selectList( "getSocialBenefitSolutionHeaderVOsBySysSBHeaderVO", socialBenefitSolutionHeaderVO );
   }

}
