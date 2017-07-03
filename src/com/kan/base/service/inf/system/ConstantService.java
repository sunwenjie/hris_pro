package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.ConstantVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ConstantService
{
   public abstract PagedListHolder getConstantVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ConstantVO getConstantVOByConstantId( final String constantId ) throws KANException;

   public abstract int insertConstant( final ConstantVO constantVO ) throws KANException;

   public abstract int updateConstant( final ConstantVO constantVO ) throws KANException;

   public abstract int deleteConstant( final ConstantVO constantVO ) throws KANException;

   public abstract List< Object > getConstantVOsByAccountId( final String accountId ) throws KANException;
}
