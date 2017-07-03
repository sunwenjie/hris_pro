package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ItemGroupVO;
import com.kan.base.util.KANException;

public interface ItemGroupDao
{
   public abstract int countItemGroupVOsByCondition( final ItemGroupVO itemGroupVO ) throws KANException;

   public abstract List< Object > getItemGroupVOsByCondition( final ItemGroupVO itemGroupVO ) throws KANException;

   public abstract List< Object > getItemGroupVOsByCondition( final ItemGroupVO itemGroupVO, final RowBounds rowBounds ) throws KANException;

   public abstract ItemGroupVO getItemGroupVOByItemGroupId( final String itemGroupId ) throws KANException;

   public abstract int insertItemGroup( final ItemGroupVO itemGroupVO ) throws KANException;

   public abstract int updateItemGroup( final ItemGroupVO itemGroupVO ) throws KANException;

   public abstract int deleteItemGroup( final ItemGroupVO itemGroupVO ) throws KANException;
   
   public abstract List< Object > getItemGroupVOsByAccountId( final String accountId ) throws KANException;
}
