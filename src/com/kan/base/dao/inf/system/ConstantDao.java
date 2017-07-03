package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.ConstantVO;
import com.kan.base.util.KANException;

public interface ConstantDao
{
   public abstract int countConstantVOsByCondition( final ConstantVO constantVO ) throws KANException;

   public abstract List< Object > getConstantVOsByCondition( final ConstantVO constantVO ) throws KANException;

   public abstract List< Object > getConstantVOsByCondition( final ConstantVO constantVO, final RowBounds rowBounds ) throws KANException;

   public abstract ConstantVO getConstantVOByConstantId( final String constantId ) throws KANException;

   public abstract int insertConstant( final ConstantVO constantVO ) throws KANException;

   public abstract int updateConstant( final ConstantVO constantVO ) throws KANException;

   public abstract int deleteConstant( final ConstantVO constantVO ) throws KANException;
}
