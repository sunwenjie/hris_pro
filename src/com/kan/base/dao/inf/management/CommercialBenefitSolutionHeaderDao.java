package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.util.KANException;

public interface CommercialBenefitSolutionHeaderDao
{
   public abstract int countCommercialBenefitSolutionHeaderVOsByCondition( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException;

   public abstract List< Object > getCommercialBenefitSolutionHeaderVOsByCondition( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException;

   public abstract List< Object > getCommercialBenefitSolutionHeaderVOsByCondition( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO,
         final RowBounds rowBounds ) throws KANException;

   public abstract CommercialBenefitSolutionHeaderVO getCommercialBenefitSolutionHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException;

   public abstract int updateCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException;

   public abstract int deleteCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException;
                                        
   public abstract List< Object > getCommercialBenefitSolutionHeaderVOsAccountId( final String accountId ) throws KANException;
}
