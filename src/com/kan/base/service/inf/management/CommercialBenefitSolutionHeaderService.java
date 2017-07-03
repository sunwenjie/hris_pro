package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.CommercialBenefitSolutionDTO;
import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CommercialBenefitSolutionHeaderService
{
   public abstract PagedListHolder getCommercialBenefitSolutionHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CommercialBenefitSolutionHeaderVO getCommercialBenefitSolutionHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int updateCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException;

   public abstract int insertCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException;

   public abstract void deleteCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException;

   public abstract List< CommercialBenefitSolutionDTO > getCommercialBenefitSolutionDTOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getCommercialBenefitSolutionHeaderViewsByAccountId( final String accountId ) throws KANException;
}
