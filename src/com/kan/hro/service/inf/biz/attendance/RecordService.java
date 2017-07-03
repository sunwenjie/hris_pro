package com.kan.hro.service.inf.biz.attendance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.RecordVO;

public interface RecordService
{
   public abstract PagedListHolder getRecordVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract RecordVO getRecordVOByRecordId( final String recordId ) throws KANException;

   public abstract int insertRecord( final RecordVO recordVO ) throws KANException;

   public abstract int updateRecord( final RecordVO recordVO ) throws KANException;

   public abstract int deleteRecord( final RecordVO recordVO ) throws KANException;

   public abstract String syncRecord( final List< RecordVO > recordVOs, final String startDateStr ) throws KANException;

   public abstract int insertRecordVOs( final List< Object > recordVOs ) throws KANException;
}
