package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.util.KANException;

public interface ColumnDao
{
   public abstract int countColumnVOsByCondition( final ColumnVO columnVO ) throws KANException;

   public abstract List< Object > getColumnVOsByCondition( final ColumnVO columnVO ) throws KANException;

   public abstract List< Object > getColumnVOsByCondition( final ColumnVO columnVO, final RowBounds rowBounds ) throws KANException;

   public abstract ColumnVO getColumnVOByColumnId( final String columnId ) throws KANException;

   public abstract int insertColumn( final ColumnVO columnVO ) throws KANException;

   public abstract int updateColumn( final ColumnVO columnVO ) throws KANException;

   public abstract int deleteColumn( final String columnId ) throws KANException;
   
   public abstract List< Object > getAccountColumnVOsByCondition( final ColumnVO columnVO ) throws KANException;
}
