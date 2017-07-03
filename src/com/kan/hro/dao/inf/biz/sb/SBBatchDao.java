package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBBatchVO;

public interface SBBatchDao
{
   public abstract int countSBBatchVOsByCondition( final SBBatchVO sbBatchVO ) throws KANException;

   public abstract List< Object > getSBBatchVOsByCondition( final SBBatchVO sbBatchVO ) throws KANException;

   public abstract List< Object > getSBBatchVOsByCondition( final SBBatchVO sbBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract SBBatchVO getSBBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int insertSBBatch( final SBBatchVO sbBatchVO ) throws KANException;

   public abstract int updateSBBatch( final SBBatchVO sbBatchVO ) throws KANException;

   public abstract int deleteSBBatch( final String sbBatchId ) throws KANException;

   public abstract int getSBToApplyForMoreStatusCountByBatchIds( final String[] batchId ) throws KANException;

   public abstract int getSBToApplyForResigningStatusCountByBatchIds( final String[] batchId ) throws KANException;

   public abstract void updateSBStatusTONoSocialBenefitByBatchId( final String[] batchId ) throws KANException;

   public abstract void updateSBStatusTONormalByBatchId( final String[] batchId ) throws KANException;

   public abstract int updateSBBatchStatus( SBBatchVO sbBatchVO ) throws KANException;
   
   public abstract int updateSBHeaderStatus( SBBatchVO sbBatchVO ) throws KANException;
   
   public abstract int updateSBDetailStatus( SBBatchVO sbBatchVO ) throws KANException;

   public abstract String getSBBatchId( SBBatchVO sbBatchVO ) throws KANException;

}
