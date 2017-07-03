package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;

public interface SBDetailDao
{
   public abstract int countSBDetailVOsByCondition( final SBDetailVO sbDetailVO ) throws KANException;

   public abstract List< Object > getSBDetailVOsByCondition( final SBDetailVO sbDetailVO ) throws KANException;

   public abstract List< Object > getSBDetailVOsByCondition( final SBDetailVO sbDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract SBDetailVO getSBDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertSBDetail( final SBDetailVO sbDetailVO ) throws KANException;

   public abstract int updateSBDetail( final SBDetailVO sbDetailVO ) throws KANException;

   public abstract int deleteSBDetail( final String sbDetailId ) throws KANException;
   
   public abstract int getMinSBDetailStatusBySBHeaderId( final String sbHeaderId ) throws KANException;

   public abstract List< Object > getSBDetailVOsByHeaderId( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getSBDetailVOsByBatchId( final SBBatchVO sbBatchVO ) throws KANException;

   public abstract List< Object > getSBDetailVOsBySbHeaderCond( final SBHeaderVO sbHeaderVO ) throws KANException;

}
