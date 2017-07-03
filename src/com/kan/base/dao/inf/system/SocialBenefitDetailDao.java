package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.util.KANException;

public interface SocialBenefitDetailDao
{
   public abstract int countSocialBenefitDetailVOsByCondition( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException;

   public abstract List< Object > getSocialBenefitDetailVOsByCondition( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException;

   public abstract List< Object > getSocialBenefitDetailVOsByCondition( final SocialBenefitDetailVO socialBenefitDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract SocialBenefitDetailVO getSocialBenefitDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException;

   public abstract int updateSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException;

   public abstract int deleteSocialBenefitDetail( final String detailId ) throws KANException;

   public abstract List< Object > getSocialBenefitDetailVOsByHeaderId( final String headerId ) throws KANException;
}
