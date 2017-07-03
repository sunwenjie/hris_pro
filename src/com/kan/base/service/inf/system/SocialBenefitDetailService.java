package com.kan.base.service.inf.system;

import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SocialBenefitDetailService
{
   public abstract PagedListHolder getSocialBenefitDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SocialBenefitDetailVO getSocialBenefitDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int updateSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException;

   public abstract int insertSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException;

   public abstract void deleteSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException;
}
