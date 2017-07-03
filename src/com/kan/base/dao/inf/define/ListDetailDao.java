package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.util.KANException;

public interface ListDetailDao
{
   public abstract int countListDetailVOsByCondition( final ListDetailVO listDetailVO ) throws KANException;

   public abstract List< Object > getListDetailVOsByCondition( final ListDetailVO listDetailVO ) throws KANException;

   public abstract List< Object > getListDetailVOsByCondition( final ListDetailVO listDetailVO, RowBounds rowBounds ) throws KANException;

   public abstract ListDetailVO getListDetailVOByListDetailId( final String listDetailId ) throws KANException;

   public abstract int insertListDetail( final ListDetailVO listDetailVO ) throws KANException;

   public abstract int updateListDetail( final ListDetailVO listDetailVO ) throws KANException;

   public abstract int deleteListDetail( final String listDetailId ) throws KANException;

   public abstract List< Object > getListDetailVOsByListHeaderId( final String listHeaderId ) throws KANException;
}
