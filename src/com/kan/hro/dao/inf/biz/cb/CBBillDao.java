package com.kan.hro.dao.inf.biz.cb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.cb.CBBillVO;

public interface CBBillDao
{
   public abstract int countCBBillVOsByCondition( final CBBillVO cbBillVO ) throws KANException;

   public abstract List< Object > getCBBillVOsByCondition( final CBBillVO cbBillVO ) throws KANException;

   public abstract List< Object > getCBBillVOsByCondition( final CBBillVO cbBillVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getCBBillDetailByHeaderIds( final List< String > list ) throws KANException;
}
