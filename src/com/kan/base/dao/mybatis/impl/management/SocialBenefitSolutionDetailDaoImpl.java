package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.SocialBenefitSolutionDetailDao;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.util.KANException;

public class SocialBenefitSolutionDetailDaoImpl extends Context implements SocialBenefitSolutionDetailDao
{

   @Override
   public int countSocialBenefitSolutionDetailVOsByCondition( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException
   {
      return ( Integer ) select( "countSocialBenefitSolutionDetailVOsByCondition", socialBenefitSolutionDetailVO );
   }

   @Override
   public List< Object > getSocialBenefitSolutionDetailVOsByCondition( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException
   {
      return selectList( "getSocialBenefitSolutionDetailVOsByCondition", socialBenefitSolutionDetailVO );
   }

   @Override
   public List< Object > getSocialBenefitSolutionDetailVOsByCondition( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO, final RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getSocialBenefitSolutionDetailVOsByCondition", socialBenefitSolutionDetailVO, rowBounds );
   }

   @Override
   public SocialBenefitSolutionDetailVO getSocialBenefitSolutionDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( SocialBenefitSolutionDetailVO ) select( "getSocialBenefitSolutionDetailVOByDetailId", detailId );
   }

   @Override
   public int insertSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException
   {
      return insert( "insertSocialBenefitSolutionDetail", socialBenefitSolutionDetailVO );
   }

   @Override
   public int updateSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException
   {
      return update( "updateSocialBenefitSolutionDetail", socialBenefitSolutionDetailVO );
   }

   @Override
   public int deleteSocialBenefitSolutionDetail( final String detailId ) throws KANException
   {
      return delete( "deleteSocialBenefitSolutionDetail", detailId );
   }

   @Override
   public List< Object > getSocialBenefitSolutionDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getSocialBenefitSolutionDetailVOsByHeaderId", headerId );
   }

   @Override
   public List< Object > getSocialBenefitSolutionDetailVOsBySysDetailId( final String sysDetailId ) throws KANException
   {
      return selectList( "getSocialBenefitSolutionDetailVOsBySysDetailId", sysDetailId );
   }

}
