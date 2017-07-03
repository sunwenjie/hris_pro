package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.RuleDao;
import com.kan.base.domain.system.RuleVO;
import com.kan.base.util.KANException;

public class RuleDaoImpl extends Context implements RuleDao
{

   @Override
   public int countRuleVOsByCondition( RuleVO ruleVO ) throws KANException
   {
      return ( Integer ) select( "countRuleVOsByCondition", ruleVO );
   }

   @Override
   public List< Object > getRuleVOsByCondition( RuleVO ruleVO ) throws KANException
   {
      return selectList( "getRuleVOsByCondition", ruleVO );
   }

   @Override
   public List< Object > getRuleVOsByCondition( RuleVO ruleVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getRuleVOsByCondition", ruleVO, rowBounds );
   }

   @Override
   public RuleVO getRuleVOByRuleId( String ruleId ) throws KANException
   {
      return ( RuleVO ) select( "getRuleVOByRuleId", ruleId );
   }

   @Override
   public int updateRule( RuleVO ruleVO ) throws KANException
   {
      return insert( "updateRule", ruleVO );
   }

   @Override
   public int insertRule( RuleVO ruleVO ) throws KANException
   {
      return insert( "insertRule", ruleVO );
   }

   @Override
   public int deleteRule( RuleVO ruleVO ) throws KANException
   {
      return delete( "deleteRule", ruleVO );
   }

   @Override
   public List< Object > getRuleVOs() throws KANException
   {
      return selectList( "getRuleVOs", null );
   }

}