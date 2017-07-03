package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.TimesheetAllowanceBatchDao;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;

public class TimesheetAllowanceBatchDaoImpl extends Context implements TimesheetAllowanceBatchDao
{

   @Override
   public int countTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return ( Integer ) select( "countTimesheetAllowanceBatchVOsByCondition", timesheetBatchVO );
   }

   @Override
   public List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return selectList( "getTimesheetAllowanceBatchVOsByCondition", timesheetBatchVO );
   }

   @Override
   public List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTimesheetAllowanceBatchVOsByCondition", timesheetBatchVO, rowBounds );
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( TimesheetBatchVO ) select( "getTimesheetAllowanceBatchVOByBatchId", batchId );
   }

	@Override
	public void updateAllowanceBaseByBatch(TimesheetBatchVO submitObject) throws KANException{
		 update( "updateAllowanceBaseByBatch", submitObject );
	}

	@Override
	public void deleteAllowanceTempByBatchId(String batchId)throws KANException {
		delete("deleteAllowanceTempByBatchId", batchId);
		
	}

	@Override
	public void deleteTimeSheetHeaderTempByBatchId(String batchId)throws KANException {
		delete("deleteTimeSheetHeaderTempByBatchId", batchId);
		
	}

	@Override
	public void deleteCommonBatchById(String batchId)throws KANException {
		delete("deleteCommonBatchById", batchId);
	}

	@Override
	public void updateBathStatus(TimesheetBatchVO submitObject) {
		update( "updateBathStatus", submitObject );
	}
	
}
