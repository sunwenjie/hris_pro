package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.SocialBenefitHeaderDao;
import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.util.KANException;

public class SocialBenefitHeaderDaoImpl extends Context implements SocialBenefitHeaderDao
{

   @Override
   public int countSocialBenefitHeaderVOsByCondition( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSocialBenefitHeaderVOsByCondition", socialBenefitHeaderVO );
   }

   @Override
   public List< Object > getSocialBenefitHeaderVOsByCondition( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException
   {
      return selectList( "getSocialBenefitHeaderVOsByCondition", socialBenefitHeaderVO );
   }

   @Override
   public List< Object > getSocialBenefitHeaderVOsByCondition( final SocialBenefitHeaderVO socialBenefitHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSocialBenefitHeaderVOsByCondition", socialBenefitHeaderVO, rowBounds );
   }

   @Override
   public SocialBenefitHeaderVO getSocialBenefitHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( SocialBenefitHeaderVO ) select( "getSocialBenefitHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException
   {
      return insert( "insertSocialBenefitHeader", socialBenefitHeaderVO );
   }

   @Override
   public int updateSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException
   {
      return update( "updateSocialBenefitHeader", socialBenefitHeaderVO );
   }

   @Override
   public int deleteSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException
   {
      return delete( "deleteSocialBenefitHeader", socialBenefitHeaderVO );
   }

   @Override
   public List< Object > getSocialBenefitHeaderVOsByCityId( final String cityId ) throws KANException
   {
      return selectList( "getSocialBenefitHeaderVOsByCityId", cityId );
   }

}
