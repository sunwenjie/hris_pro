package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;

public interface SBHeaderTempDao
{
   public abstract int countSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract int countSBContractVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract int countAmountVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract int countVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract List< Object > getSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract List< Object > getSBContractVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract List< Object > getSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getSBContractVOsByCondition( final SBHeaderTempVO sbHeaderTempVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getAmountVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO, final RowBounds rowBounds ) throws KANException;

   public abstract SBHeaderTempVO getSBHeaderTempVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertSBHeaderTemp( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract int updateSBHeaderTemp( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract int deleteSBHeaderTemp( final String sbHeaderId ) throws KANException;

   public abstract List< Object > getSBHeaderTempVOsByBatchId( final String batchId ) throws KANException;

   public abstract List< Object > getAmountVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract List< Object > getVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract int countEmployeeSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;
   
   public abstract List< Object > getMonthliesByEmployeeId( final String employeeId ) throws KANException;
}
