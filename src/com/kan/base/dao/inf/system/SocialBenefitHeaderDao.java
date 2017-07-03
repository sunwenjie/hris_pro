package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.util.KANException;

public interface SocialBenefitHeaderDao
{
   public abstract int countSocialBenefitHeaderVOsByCondition( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException;

   public abstract List< Object > getSocialBenefitHeaderVOsByCondition( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException;

   public abstract List< Object > getSocialBenefitHeaderVOsByCondition( final SocialBenefitHeaderVO socialBenefitHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract SocialBenefitHeaderVO getSocialBenefitHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException;

   public abstract int updateSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException;

   public abstract int deleteSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException;

   public abstract List< Object > getSocialBenefitHeaderVOsByCityId( final String cityId ) throws KANException;
}
