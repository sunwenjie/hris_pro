package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.util.KANException;

public interface ColumnGroupDao
{
   public abstract int countColumnGroupVOsByCondition( final ColumnGroupVO columnGroupVO ) throws KANException;

   public abstract List< Object > getColumnGroupVOsByCondition( final ColumnGroupVO columnGroupVO ) throws KANException;

   public abstract List< Object > getColumnGroupVOsByCondition( final ColumnGroupVO columnGroupVO, RowBounds rowBounds ) throws KANException;

   public abstract ColumnGroupVO getColumnGroupVOByGroupId( final String groupId ) throws KANException;

   public abstract int insertColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException;

   public abstract int updateColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException;

   public abstract int deleteColumnGroup( final String groupId ) throws KANException;

   public abstract List< Object > getColumnGroupVOsByAccountId( final String accountId ) throws KANException;
}
