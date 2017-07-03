package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SocialBenefitSolutionDetailService
{
   public abstract PagedListHolder getSocialBenefitSolutionDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SocialBenefitSolutionDetailVO getSocialBenefitSolutionDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int updateSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException;

   public abstract int insertSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException;

   public abstract void deleteSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionDetailVOsByHeaderId( final String headerId ) throws KANException;
}
