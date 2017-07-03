package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBHeaderVO;

public interface SBHeaderDao
{
   public abstract int countSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int countSBContractVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int countAmountVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int countVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getSBContractVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getSBContractVOsByCondition( final SBHeaderVO sbHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getAmountVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract SBHeaderVO getSBHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int updateSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int deleteSBHeader( final String sbHeaderId ) throws KANException;

   public abstract List< Object > getSBHeaderVOsByBatchId( final String batchId ) throws KANException;

   public abstract List< Object > getAmountVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int countEmployeeSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getMonthliesBySBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int updateSBHeaderPaid( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int getSBToApplyForMoreStatusCountByHeaderIds( final String[] headerId ) throws KANException;
   
   public abstract int getSBToApplyForResigningStatusCountByByHeaderIds( final String[] headerId ) throws KANException;

   public abstract void updateSBStatusTONoSocialBenefitByHeaderId( final String[] headerId ) throws KANException;

   public abstract void updateSBStatusTONormalByHeaderId( final String[] headerId ) throws KANException;
}
