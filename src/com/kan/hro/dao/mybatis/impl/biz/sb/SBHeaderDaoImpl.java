package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.domain.biz.sb.SBHeaderVO;

public class SBHeaderDaoImpl extends Context implements SBHeaderDao
{

   @Override
   public int countSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSBHeaderVOsByCondition", sbHeaderVO );
   }

   @Override
   public int countSBContractVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSBContractVOsByCondition", sbHeaderVO );
   }

   @Override
   public int countAmountVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countAmountVendorSBHeaderVOsByCondition", sbHeaderVO );
   }

   @Override
   public int countVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countVendorSBHeaderVOsByCondition", sbHeaderVO );
   }

   @Override
   public List< Object > getSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBHeaderVOsByCondition", sbHeaderVO, rowBounds );
   }

   @Override
   public List< Object > getSBContractVOsByCondition( final SBHeaderVO sbHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBContractVOsByCondition", sbHeaderVO, rowBounds );
   }

   @Override
   public List< Object > getAmountVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAmountVendorSBHeaderVOsByCondition", sbHeaderVO, rowBounds );
   }

   @Override
   public List< Object > getVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getVendorSBHeaderVOsByCondition", sbHeaderVO, rowBounds );
   }

   @Override
   public List< Object > getSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return selectList( "getSBHeaderVOsByCondition", sbHeaderVO );
   }

   @Override
   public List< Object > getSBContractVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return selectList( "getSBContractVOsByCondition", sbHeaderVO );
   }

   @Override
   public List< Object > getAmountVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return selectList( "getAmountVendorSBHeaderVOsByCondition", sbHeaderVO );
   }

   @Override
   public List< Object > getVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return selectList( "getVendorSBHeaderVOsByCondition", sbHeaderVO );
   }

   @Override
   public SBHeaderVO getSBHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( SBHeaderVO ) select( "getSBHeaderVOByHeaderId", headerId );
   }

   @Override
   public int updateSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return update( "updateSBHeader", sbHeaderVO );
   }

   @Override
   public int insertSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return insert( "insertSBHeader", sbHeaderVO );
   }

   @Override
   public int deleteSBHeader( final String sbHeaderId ) throws KANException
   {
      return delete( "deleteSBHeader", sbHeaderId );
   }

   @Override
   public List< Object > getSBHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getSBHeaderVOsByBatchId", batchId );
   }

   @Override
   public int countEmployeeSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSBHeaderVOsByEmployeeSBId", sbHeaderVO );
   }

   @Override
   public List< Object > getMonthliesBySBHeaderVO( final SBHeaderVO SBHeaderVO ) throws KANException
   {
      return selectList( "getSBMonthliesBySBHeaderVO4Mobile", SBHeaderVO );
   }

   @Override
   public int updateSBHeaderPaid( SBHeaderVO sbHeaderVO ) throws KANException
   {
      return update( "updateSBHeaderPaid", sbHeaderVO );
   }

   @Override
   public int getSBToApplyForMoreStatusCountByHeaderIds( final String[] headerId ) throws KANException
   {
      return ( Integer ) select( "getSBToApplyForMoreStatusCountByHeaderIds", headerId );
   }
   
   @Override
   public int getSBToApplyForResigningStatusCountByByHeaderIds( final String[] headerId ) throws KANException
   {
      return ( Integer ) select( "getSBToApplyForResigningStatusCountByByHeaderIds", headerId );
   }

   @Override
   public void updateSBStatusTONoSocialBenefitByHeaderId( final String[] headerId ) throws KANException
   {
      update( "updateSBStatusTONoSocialBenefitByHeaderId", headerId );
   }

   @Override
   public void updateSBStatusTONormalByHeaderId( final String[] headerId ) throws KANException
   {
      update( "updateSBStatusTONormalByHeaderId", headerId );
   }
}
