package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.SocialBenefitDTO;
import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SocialBenefitHeaderService
{
   public abstract PagedListHolder getSocialBenefitHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SocialBenefitHeaderVO getSocialBenefitHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int updateSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException;

   public abstract int insertSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException;

   public abstract void deleteSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException;

   public abstract List< Object > getSocialBenefitHeaderVOsByCityId( final String cityId ) throws KANException;

   public abstract List< SocialBenefitDTO > getSocialBenefitDTOs() throws KANException;
}
