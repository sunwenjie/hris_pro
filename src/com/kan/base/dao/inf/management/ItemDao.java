package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.KANException;

public interface ItemDao
{
   public abstract int countItemVOsByCondition( final ItemVO itemVO ) throws KANException;

   public abstract List< Object > getItemVOsByCondition( final ItemVO itemVO ) throws KANException;

   public abstract List< Object > getItemVOsByCondition( final ItemVO itemVO, final RowBounds rowBounds ) throws KANException;

   public abstract ItemVO getItemVOByItemId( final String itemId ) throws KANException;

   public abstract int insertItem( final ItemVO itemVO ) throws KANException;

   public abstract int updateItem( final ItemVO itemVO ) throws KANException;

   public abstract int deleteItem( final ItemVO itemVO ) throws KANException;

   public abstract List< Object > getItemVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getItemBaseViewsByAccountId( final String accountId ) throws KANException;
}
