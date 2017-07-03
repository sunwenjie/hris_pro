package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBDetailTempVO;

public interface SBDetailTempDao
{
   public abstract int countSBDetailTempVOsByCondition( final SBDetailTempVO sbDetailTempVO ) throws KANException;

   public abstract List< Object > getSBDetailTempVOsByCondition( final SBDetailTempVO sbDetailTempVO ) throws KANException;

   public abstract List< Object > getSBDetailTempVOsByCondition( final SBDetailTempVO sbDetailTempVO, final RowBounds rowBounds ) throws KANException;

   public abstract SBDetailTempVO getSBDetailTempVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertSBDetailTemp( final SBDetailTempVO sbDetailTempVO ) throws KANException;

   public abstract int updateSBDetailTemp( final SBDetailTempVO sbDetailTempVO ) throws KANException;

   public abstract int deleteSBDetailTemp( final String sbDetailId ) throws KANException;

   public abstract List< Object > getSBDetailTempVOsByHeaderId( final String headerId ) throws KANException;

   public abstract List< Object > getSBDetailTempVOsByBatchId( final String batchId ) throws KANException;
}
