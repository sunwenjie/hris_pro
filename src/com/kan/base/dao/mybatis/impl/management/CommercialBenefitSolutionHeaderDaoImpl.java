package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.CommercialBenefitSolutionHeaderDao;
import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.util.KANException;

public class CommercialBenefitSolutionHeaderDaoImpl extends Context implements CommercialBenefitSolutionHeaderDao
{

   @Override
   public int countCommercialBenefitSolutionHeaderVOsByCondition( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countCommercialBenefitSolutionHeaderVOsByCondition", commercialBenefitSolutionHeaderVO );
   }

   @Override
   public List< Object > getCommercialBenefitSolutionHeaderVOsByCondition( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException
   {
      return selectList( "getCommercialBenefitSolutionHeaderVOsByCondition", commercialBenefitSolutionHeaderVO );
   }

   @Override
   public List< Object > getCommercialBenefitSolutionHeaderVOsByCondition( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO, final RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getCommercialBenefitSolutionHeaderVOsByCondition", commercialBenefitSolutionHeaderVO, rowBounds );
   }

   @Override
   public CommercialBenefitSolutionHeaderVO getCommercialBenefitSolutionHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( CommercialBenefitSolutionHeaderVO ) select( "getCommercialBenefitSolutionHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException
   {
      return insert( "insertCommercialBenefitSolutionHeader", commercialBenefitSolutionHeaderVO );
   }

   @Override
   public int updateCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException
   {
      return update( "updateCommercialBenefitSolutionHeader", commercialBenefitSolutionHeaderVO );
   }

   @Override
   public int deleteCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException
   {
      return delete( "deleteCommercialBenefitSolutionHeader", commercialBenefitSolutionHeaderVO );
   }

   @Override
   public List< Object > getCommercialBenefitSolutionHeaderVOsAccountId( final String accountId ) throws KANException
   {
      return selectList( "getCommercialBenefitSolutionHeaderVOsAccountId", accountId );
   }

}
