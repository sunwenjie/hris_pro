package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SocialBenefitSolutionHeaderService
{
   public abstract PagedListHolder getSocialBenefitSolutionHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SocialBenefitSolutionHeaderVO getSocialBenefitSolutionHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int updateSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

   public abstract int insertSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

   public abstract void deleteSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionHeaderVOsBySysHeaderId( final String sysHeaderId ) throws KANException;

   public abstract List< SocialBenefitSolutionHeaderVO > getSocialBenefitSolutionHeaderVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< SocialBenefitSolutionDTO > getSocialBenefitSolutionDTOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getSocialBenefitSolutionHeaderVOsBySysSBHeaderVO( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

}
