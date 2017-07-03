package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.RecordVO;

public interface RecordDao
{
   public abstract int countRecordVOsByCondition( final RecordVO recordVO ) throws KANException;

   public abstract List< Object > getRecordVOsByCondition( final RecordVO recordVO ) throws KANException;

   public abstract List< Object > getRecordVOsByCondition( final RecordVO recordVO, final RowBounds rowBounds ) throws KANException;

   public abstract RecordVO getRecordVOByRecordId( final String recordId ) throws KANException;

   public abstract int insertRecord( final RecordVO recordVO ) throws KANException;

   public abstract int updateRecord( final RecordVO recordVO ) throws KANException;

   public abstract int deleteRecord( final RecordVO recordVO ) throws KANException;

}
