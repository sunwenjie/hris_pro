package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.util.KANException;

public interface ImportDetailDao
{
   public abstract int countImportDetailVOsByCondition( final ImportDetailVO importDetailVO ) throws KANException;

   public abstract List< Object > getImportDetailVOsByCondition( final ImportDetailVO importDetailVO ) throws KANException;

   public abstract List< Object > getImportDetailVOsByCondition( final ImportDetailVO importDetailVO, RowBounds rowBounds ) throws KANException;

   public abstract ImportDetailVO getImportDetailVOByImportDetailId( final String importDetailId ) throws KANException;

   public abstract int insertImportDetail( final ImportDetailVO importDetailVO ) throws KANException;

   public abstract int updateImportDetail( final ImportDetailVO importDetailVO ) throws KANException;

   public abstract int deleteImportDetail( final String importDetailId ) throws KANException;

   public abstract List< Object > getImportDetailVOsByImportHeaderId( final String importHeaderId ) throws KANException;
}
