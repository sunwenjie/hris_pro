package com.kan.hro.dao.inf.biz.cb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.cb.CBHeaderVO;

public interface CBHeaderDao
{
   public abstract int countCBHeaderVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract int countContractVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract List< Object > getCBHeaderVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract List< Object > getContractVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract List< Object > getCBHeaderVOsByCondition( final CBHeaderVO cbHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getContractVOsByCondition( final CBHeaderVO cbHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract CBHeaderVO getCBHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertCBHeader( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract int updateCBHeader( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract int deleteCBHeader( final String cbHeaderId ) throws KANException;

   public abstract List< Object > getCBHeaderVOsByBatchId( final String batchId ) throws KANException;

   public abstract List< Object > getCBContractVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException;

}
