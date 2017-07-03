package com.kan.hro.dao.mybatis.impl.biz.settlement;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentHeaderTempDao;

public class AdjustmentHeaderTempDaoImpl extends Context implements AdjustmentHeaderTempDao
{

   @Override
   public int updateAdjustmentHeaderTempReplenish( final String batchId ) throws KANException
   {
      return update( "updateAdjustmentHeaderTempReplenish", batchId );
   }

}
