package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBHeaderTempDao;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;

public class SBHeaderTempDaoImpl extends Context implements SBHeaderTempDao
{

   @Override
   public int countSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return ( Integer ) select( "countSBHeaderTempVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public int countSBContractVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return ( Integer ) select( "countSBContractVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public int countAmountVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return ( Integer ) select( "countAmountVendorSBHeaderTempVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public int countVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return ( Integer ) select( "countVendorSBHeaderTempVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public List< Object > getSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBHeaderTempVOsByCondition", sbHeaderTempVO, rowBounds );
   }

   @Override
   public List< Object > getSBContractVOsByCondition( final SBHeaderTempVO sbHeaderTempVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBContractVOsByCondition", sbHeaderTempVO, rowBounds );
   }

   @Override
   public List< Object > getAmountVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAmountVendorSBHeaderTempVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public List< Object > getVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getVendorSBHeaderTempVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public List< Object > getSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return selectList( "getSBHeaderTempVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public List< Object > getSBContractVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return selectList( "getSBContractVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public List< Object > getAmountVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return selectList( "getAmountVendorSBHeaderTempVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public List< Object > getVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return selectList( "getVendorSBHeaderTempVOsByCondition", sbHeaderTempVO );
   }

   @Override
   public SBHeaderTempVO getSBHeaderTempVOByHeaderId( final String headerId ) throws KANException
   {
      return ( SBHeaderTempVO ) select( "getSBHeaderTempVOByHeaderId", headerId );
   }

   @Override
   public int updateSBHeaderTemp( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return update( "updateSBHeaderTemp", sbHeaderTempVO );
   }

   @Override
   public int insertSBHeaderTemp( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return insert( "insertSBHeaderTemp", sbHeaderTempVO );
   }

   @Override
   public int deleteSBHeaderTemp( final String sbHeaderId ) throws KANException
   {
      return delete( "deleteSBHeaderTemp", sbHeaderId );
   }

   @Override
   public List< Object > getSBHeaderTempVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getSBHeaderTempVOsByBatchId", batchId );
   }

   @Override
   public int countEmployeeSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return ( Integer ) select( "countSBHeaderTempVOsByEmployeeSBId", sbHeaderTempVO );
   }

   @Override
   public List< Object > getMonthliesByEmployeeId( final String employeeId ) throws KANException
   {
      return selectList( "getSBMonthliesByEmployeeId", employeeId );
   }

}
