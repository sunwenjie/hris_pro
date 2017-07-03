package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.OTImportHeaderVO;

public interface OTImportHeaderDao
{
   public abstract int countOTImportHeaderVOsByCondition( final OTImportHeaderVO otImportHeaderVO ) throws KANException;

   public abstract List< Object > getOTImportHeaderVOsByCondition( final OTImportHeaderVO otImportHeaderVO ) throws KANException;

   public abstract List< Object > getOTImportHeaderVOsByCondition( final OTImportHeaderVO otImportHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract int insertOTImportHeaderToOTHeader( final String batchId ) throws KANException;

   public abstract int insertOTDetailtempToOTDetail( final String batchId ) throws KANException;

   public abstract void deleteDetailTempRecord( final String[] ids );

   public abstract void deleteHeaderTempRecord( final String[] ids );

   public abstract List< Object > getOTImportHeaderVOsByBatchId( final String batchId ) throws KANException;

   public abstract int updateOTImportHeader( final OTImportHeaderVO otImportHeaderVO ) throws KANException;

   public abstract int insertOTImportHeaderToOTHeader_shengjoy( final String batchId ) throws KANException;

   public abstract int insertOTDetailtempToOTDetail_shengjoy( final String batchId ) throws KANException;

}
