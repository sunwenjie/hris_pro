package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.RuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface RuleService
{
   public abstract PagedListHolder getRuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract RuleVO getRuleVOByRuleId( final String ruleId ) throws KANException;

   public abstract int updateRule( final RuleVO ruleVO ) throws KANException;

   public abstract int insertRule( final RuleVO ruleVO ) throws KANException;

   public abstract void deleteRule( final RuleVO ruleVO ) throws KANException;
   
   public abstract List< Object > getRuleVOs() throws KANException;

}
