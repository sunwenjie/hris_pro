package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.util.KANException;

public interface CommercialBenefitSolutionDetailDao
{
   public abstract int countCommercialBenefitSolutionDetailVOsByCondition( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException;

   public abstract List< Object > getCommercialBenefitSolutionDetailVOsByCondition( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException;

   public abstract List< Object > getCommercialBenefitSolutionDetailVOsByCondition( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO,
         final RowBounds rowBounds ) throws KANException;

   public abstract CommercialBenefitSolutionDetailVO getCommercialBenefitSolutionDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException;

   public abstract int updateCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException;

   public abstract int deleteCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException;

   public abstract List< Object > getCommercialBenefitSolutionDetailVOsByHeaderId( final String headerId ) throws KANException;
}
