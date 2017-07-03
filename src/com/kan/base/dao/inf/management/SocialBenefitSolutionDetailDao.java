package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.util.KANException;

public interface SocialBenefitSolutionDetailDao
{
   public abstract int countSocialBenefitSolutionDetailVOsByCondition( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionDetailVOsByCondition( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionDetailVOsByCondition( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO, final RowBounds rowBounds )
         throws KANException;

   public abstract SocialBenefitSolutionDetailVO getSocialBenefitSolutionDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException;

   public abstract int updateSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException;

   public abstract int deleteSocialBenefitSolutionDetail( final String detailId ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionDetailVOsByHeaderId( final String headerId ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionDetailVOsBySysDetailId( final String sysDetailId ) throws KANException;
}
