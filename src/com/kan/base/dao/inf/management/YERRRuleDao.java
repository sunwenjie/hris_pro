package com.kan.base.dao.inf.management;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.YERRRuleVO;
import com.kan.base.util.KANException;

public interface YERRRuleDao
{
   public abstract int countYERRRuleVOsByCondition( final YERRRuleVO yerrRuleVO ) throws KANException;

   public abstract List< Object > getYERRRuleVOsByCondition( final YERRRuleVO yerrRuleVO ) throws KANException;

   public abstract List< Object > getYERRRuleVOsByCondition( final YERRRuleVO yerrRuleVO, final RowBounds rowBounds ) throws KANException;

   public abstract YERRRuleVO getYERRRuleVOByRuleId( final String ruleId ) throws KANException;

   public abstract int insertYERRRule( final YERRRuleVO yerrRuleVO ) throws KANException;

   public abstract int updateYERRRule( final YERRRuleVO yerrRuleVO ) throws KANException;

   public abstract int deleteYERRRule( final YERRRuleVO yerrRuleVO ) throws KANException;

   public abstract List< Object > getYERRRuleVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getYERRRuleVOsByMapParameter( final Map< String, Object > parameterMap ) throws KANException;
}
