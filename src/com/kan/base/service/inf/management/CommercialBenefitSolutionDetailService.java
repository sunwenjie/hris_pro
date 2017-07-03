package com.kan.base.service.inf.management;

import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CommercialBenefitSolutionDetailService
{
   public abstract PagedListHolder getCommercialBenefitSolutionDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CommercialBenefitSolutionDetailVO getCommercialBenefitSolutionDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int updateCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException;

   public abstract int insertCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException;

   public abstract void deleteCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException;
}
