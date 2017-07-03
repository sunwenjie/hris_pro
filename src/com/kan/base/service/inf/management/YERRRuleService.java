package com.kan.base.service.inf.management;

import java.util.List;
import java.util.Map;

import com.kan.base.domain.management.YERRRuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface YERRRuleService
{
   public abstract PagedListHolder getYERRRuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract YERRRuleVO getYERRRuleVOByRuleId( final String ruleId ) throws KANException;

   public abstract int insertYERRRule( final YERRRuleVO yerrRuleVO ) throws KANException;

   public abstract int updateYERRRule( final YERRRuleVO yerrRuleVO ) throws KANException;

   public abstract int deleteYERRRule( final YERRRuleVO yerrRuleVO ) throws KANException;

   public abstract List< Object > getYERRRuleVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getYERRRuleVOsByMapParameter( final Map< String, Object > parameterMap ) throws KANException;
}
