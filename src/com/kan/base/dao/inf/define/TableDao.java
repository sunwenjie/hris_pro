package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.TableVO;
import com.kan.base.util.KANException;

public interface TableDao
{
   public abstract int countTableVOsByCondition( final TableVO tableVO ) throws KANException;

   public abstract List< Object > getTableVOsByCondition( final TableVO tableVO ) throws KANException;

   public abstract List< Object > getTableVOsByCondition( final TableVO tableVO, final RowBounds rowBounds ) throws KANException;

   public abstract TableVO getTableVOByTableId( final String tableId ) throws KANException;

   public abstract int insertTable( final TableVO tableVO ) throws KANException;

   public abstract int updateTable( final TableVO tableVO ) throws KANException;

   public abstract int deleteTable( final String tableId ) throws KANException;
}
