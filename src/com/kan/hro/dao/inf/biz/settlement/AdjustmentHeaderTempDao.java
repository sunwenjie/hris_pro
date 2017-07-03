package com.kan.hro.dao.inf.biz.settlement;

import com.kan.base.util.KANException;

public interface AdjustmentHeaderTempDao
{

   public abstract int updateAdjustmentHeaderTempReplenish( final String batchid) throws KANException;
}
