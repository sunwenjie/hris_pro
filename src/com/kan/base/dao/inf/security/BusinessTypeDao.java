package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.BusinessTypeVO;
import com.kan.base.util.KANException;

public interface BusinessTypeDao
{
   public abstract int countBusinessTypeVOsByCondition( final BusinessTypeVO businessTypeVO ) throws KANException;

   public abstract List< Object > getBusinessTypeVOsByCondition( final BusinessTypeVO businessTypeVO ) throws KANException;

   public abstract List< Object > getBusinessTypeVOsByCondition( final BusinessTypeVO businessTypeVO, final RowBounds rowBounds ) throws KANException;

   public abstract BusinessTypeVO getBusinessTypeVOByBusinessTypeId( final String businessTypeId ) throws KANException;

   public abstract int insertBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException;

   public abstract int updateBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException;

   public abstract int deleteBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException;

   public abstract List< Object > getBusinessTypeVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getBusinessTypeBaseViews( final String accountId ) throws KANException;
}
