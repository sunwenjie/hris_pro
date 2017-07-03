package com.kan.hro.service.inf.biz.attendance;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.util.KANException;

public interface AttendanceImportBatchService
{
   public abstract int submitObject( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract int rollbackObject( final CommonBatchVO commonBatchVO ) throws KANException;
}
