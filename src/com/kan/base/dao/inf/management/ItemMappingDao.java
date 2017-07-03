package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ItemMappingVO;
import com.kan.base.util.KANException;

public interface ItemMappingDao
{
   public abstract int countItemMappingVOsByCondition( final ItemMappingVO itemMappingVO ) throws KANException;

   public abstract List< Object > getItemMappingVOsByCondition( final ItemMappingVO itemMappingVO ) throws KANException;

   public abstract List< Object > getItemMappingVOsByCondition( final ItemMappingVO itemMappingVO, final RowBounds rowBounds ) throws KANException;

   public abstract ItemMappingVO getItemMappingVOByMappingId( final String mappingId ) throws KANException;

   public abstract int insertItemMapping( final ItemMappingVO itemMappingVO ) throws KANException;

   public abstract int updateItemMapping( final ItemMappingVO itemMappingVO ) throws KANException;

   public abstract int deleteItemMapping( final ItemMappingVO itemMappingVO ) throws KANException;

   public abstract List< Object > getItemMappingVOsByAccountId( final String accountId ) throws KANException;
}
