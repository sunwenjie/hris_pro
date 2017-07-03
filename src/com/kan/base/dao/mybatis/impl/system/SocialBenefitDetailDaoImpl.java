package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.SocialBenefitDetailDao;
import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.util.KANException;

public class SocialBenefitDetailDaoImpl extends Context implements SocialBenefitDetailDao
{

   @Override
   public int countSocialBenefitDetailVOsByCondition( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException
   {
      return ( Integer ) select( "countSocialBenefitDetailVOsByCondition", socialBenefitDetailVO );
   }

   @Override
   public List< Object > getSocialBenefitDetailVOsByCondition( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException
   {
      return selectList( "getSocialBenefitDetailVOsByCondition", socialBenefitDetailVO );
   }

   @Override
   public List< Object > getSocialBenefitDetailVOsByCondition( final SocialBenefitDetailVO socialBenefitDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSocialBenefitDetailVOsByCondition", socialBenefitDetailVO, rowBounds );
   }

   @Override
   public SocialBenefitDetailVO getSocialBenefitDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( SocialBenefitDetailVO ) select( "getSocialBenefitDetailVOByDetailId", detailId );
   }

   @Override
   public int insertSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException
   {
      return insert( "insertSocialBenefitDetail", socialBenefitDetailVO );
   }

   @Override
   public int updateSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException
   {
      return update( "updateSocialBenefitDetail", socialBenefitDetailVO );
   }

   @Override
   public int deleteSocialBenefitDetail( final String detailId ) throws KANException
   {
      return delete( "deleteSocialBenefitDetail", detailId );
   }

   @Override
   public List< Object > getSocialBenefitDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getSocialBenefitDetailVOsByHeaderId", headerId );
   }

}
