package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.util.KANException;

public interface SocialBenefitSolutionHeaderDao
{
   public abstract int countSocialBenefitSolutionHeaderVOsByCondition( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionHeaderVOsByCondition( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionHeaderVOsByCondition( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO, final RowBounds rowBounds )
         throws KANException;

   public abstract SocialBenefitSolutionHeaderVO getSocialBenefitSolutionHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

   public abstract int updateSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

   public abstract int deleteSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionHeaderVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionHeaderVOsBySysHeaderId( final String sysHeaderId ) throws KANException;
   
   public abstract List< Object > getSocialBenefitSolutionHeaderVOsBySysSBHeaderVO( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;
}
