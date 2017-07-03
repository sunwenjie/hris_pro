package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.TimesheetAllowanceHeaderDao;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;

public class TimesheetAllowanceHeaderDaoImpl extends Context implements TimesheetAllowanceHeaderDao
{

   @Override
   public int countTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countTimesheetAllowanceHeaderVOsByCondition", timesheetHeaderVO );
   }

   @Override
   public List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return selectList( "getTimesheetAllowanceHeaderVOsByCondition", timesheetHeaderVO );
   }

   @Override
   public List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTimesheetAllowanceHeaderVOsByCondition", timesheetHeaderVO, rowBounds );
   }

	@Override
	public void updateAllowanceBase(String allowanceId, String base) {
		
		TimesheetAllowanceHeaderVO allowanceHeaderVO = new TimesheetAllowanceHeaderVO();
		allowanceHeaderVO.setAllowanceId(Integer.parseInt(allowanceId));
		allowanceHeaderVO.setBase(base);
		update( "updateAllowanceBase", allowanceHeaderVO);
	}

	@Override
	public int deleteAllowanceTempByAllowanceIds(List<String> allowanceIds)
			throws KANException {
		return delete("deleteAllowanceTempByAllowanceIds", allowanceIds);
		
	}

	@Override
	public void deleteEmptyAllowanceHeaderTempBybatchId(String batchId)
			throws KANException {
		  delete("deleteEmptyAllowanceHeaderTempBybatchId", batchId);
		
	}

	@Override
	public int getHeaderCountByBatchId(String batchId) {
		return ( Integer ) select( "getHeaderCountByBatchId", batchId );
	}
}
