package com.kan.hro.dao.inf.biz.cb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.cb.CBDetailVO;

public interface CBDetailDao
{
   public abstract int countCBDetailVOsByCondition( final CBDetailVO cbDetailVO ) throws KANException;

   public abstract List< Object > getCBDetailVOsByCondition( final CBDetailVO cbDetailVO ) throws KANException;

   public abstract List< Object > getCBDetailVOsByCondition( final CBDetailVO cbDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract CBDetailVO getCBDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertCBDetail( final CBDetailVO cbDetailVO ) throws KANException;

   public abstract int updateCBDetail( final CBDetailVO cbDetailVO ) throws KANException;

   public abstract int deleteCBDetail( final String cbDetailId ) throws KANException;

   public abstract List< Object > getCBDetailVOsByHeaderId( final String headerId ) throws KANException;

   public abstract List< Object > getCBDetailVOsByBatchId( final String batchId ) throws KANException;

}
