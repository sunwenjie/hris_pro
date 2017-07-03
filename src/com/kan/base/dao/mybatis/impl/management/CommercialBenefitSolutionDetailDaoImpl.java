package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.CommercialBenefitSolutionDetailDao;
import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.util.KANException;

public class CommercialBenefitSolutionDetailDaoImpl extends Context implements CommercialBenefitSolutionDetailDao
{

   @Override
   public int countCommercialBenefitSolutionDetailVOsByCondition( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException
   {
      return ( Integer ) select( "countCommercialBenefitSolutionDetailVOsByCondition", commercialBenefitSolutionDetailVO );
   }

   @Override
   public List< Object > getCommercialBenefitSolutionDetailVOsByCondition( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException
   {
      return selectList( "getCommercialBenefitSolutionDetailVOsByCondition", commercialBenefitSolutionDetailVO );
   }

   @Override
   public List< Object > getCommercialBenefitSolutionDetailVOsByCondition( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO, final RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getCommercialBenefitSolutionDetailVOsByCondition", commercialBenefitSolutionDetailVO, rowBounds );
   }

   @Override
   public CommercialBenefitSolutionDetailVO getCommercialBenefitSolutionDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( CommercialBenefitSolutionDetailVO ) select( "getCommercialBenefitSolutionDetailVOByDetailId", detailId );
   }

   @Override
   public int insertCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException
   {
      return insert( "insertCommercialBenefitSolutionDetail", commercialBenefitSolutionDetailVO );
   }

   @Override
   public int updateCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException
   {
      return update( "updateCommercialBenefitSolutionDetail", commercialBenefitSolutionDetailVO );
   }

   @Override
   public int deleteCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException
   {
      return delete( "deleteCommercialBenefitSolutionDetail", commercialBenefitSolutionDetailVO );
   }

   @Override
   public List< Object > getCommercialBenefitSolutionDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getCommercialBenefitSolutionDetailVOsByHeaderId", headerId );
   }

}
