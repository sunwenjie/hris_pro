package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.util.KANException;

public interface ListHeaderDao
{
   public abstract int countListHeaderVOsByCondition( final ListHeaderVO listHeaderVO ) throws KANException;

   public abstract List< Object > getListHeaderVOsByCondition( final ListHeaderVO listHeaderVO ) throws KANException;

   public abstract List< Object > getListHeaderVOsByCondition( final ListHeaderVO listHeaderVO, RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getAccountListHeaderVOsByCondition( final ListHeaderVO listHeaderVO ) throws KANException;

   public abstract ListHeaderVO getListHeaderVOByListHeaderId( final String listHeaderId ) throws KANException;

   public abstract int insertListHeader( final ListHeaderVO listHeaderVO ) throws KANException;

   public abstract int updateListHeader( final ListHeaderVO listHeaderVO ) throws KANException;

   public abstract int deleteListHeader( final String listHeaderId ) throws KANException;

   public abstract List< Object > getListHeaderVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getListHeaderVOsByJavaObjcet( final ListHeaderVO listHeaderVO ) throws KANException;
}
