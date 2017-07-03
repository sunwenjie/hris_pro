package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;

public interface OTHeaderDao
{
   public abstract int countOTHeaderVOsByCondition( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract List< Object > getOTHeaderVOsByCondition( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract List< Object > getOTHeaderVOsByCondition( final OTHeaderVO otHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract OTHeaderVO getOTHeaderVOByOTHeaderId( final String otHeaderId ) throws KANException;

   public abstract int insertOTHeader( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int updateOTHeader( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int deleteOTHeader( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int count_OTUnread( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int read_OT( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract List< Object > exportOTDetailByCondition( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract OTHeaderVO getOTHeaderVOByOTImportHeaderId( final String otImportHeaderId ) throws KANException;
}
