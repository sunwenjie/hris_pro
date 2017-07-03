package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.RuleVO;
import com.kan.base.util.KANException;

public interface RuleDao
{

   public abstract int countRuleVOsByCondition( final RuleVO ruleVO ) throws KANException;

   public abstract List< Object > getRuleVOsByCondition( final RuleVO ruleVO ) throws KANException;

   public abstract List< Object > getRuleVOsByCondition( final RuleVO ruleVO, RowBounds rowBounds ) throws KANException;

   public abstract RuleVO getRuleVOByRuleId( final String ruleId ) throws KANException;

   public abstract int updateRule( final RuleVO ruleVO ) throws KANException;

   public abstract int insertRule( final RuleVO ruleVO ) throws KANException;

   public abstract int deleteRule( final RuleVO ruleVO ) throws KANException;
   
   public abstract List< Object > getRuleVOs() throws KANException;

}