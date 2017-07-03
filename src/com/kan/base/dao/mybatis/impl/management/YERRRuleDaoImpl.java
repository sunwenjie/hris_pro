package com.kan.base.dao.mybatis.impl.management;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.YERRRuleDao;
import com.kan.base.domain.management.YERRRuleVO;
import com.kan.base.util.KANException;

public class YERRRuleDaoImpl extends Context implements YERRRuleDao
{

   @Override
   public int countYERRRuleVOsByCondition( final YERRRuleVO yerrRuleVO ) throws KANException
   {
      return ( Integer ) select( "countYERRRuleVOsByCondition", yerrRuleVO );
   }

   @Override
   public List< Object > getYERRRuleVOsByCondition( final YERRRuleVO yerrRuleVO ) throws KANException
   {
      return selectList( "getYERRRuleVOsByCondition", yerrRuleVO );
   }

   @Override
   public List< Object > getYERRRuleVOsByCondition( final YERRRuleVO yerrRuleVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getYERRRuleVOsByCondition", yerrRuleVO, rowBounds );
   }

   @Override
   public YERRRuleVO getYERRRuleVOByRuleId( final String ruleId ) throws KANException
   {
      return ( YERRRuleVO ) select( "getYERRRuleVOByRuleId", ruleId );
   }

   @Override
   public int insertYERRRule( final YERRRuleVO yerrRuleVO ) throws KANException
   {
      return insert( "insertYERRRule", yerrRuleVO );
   }

   @Override
   public int updateYERRRule( final YERRRuleVO yerrRuleVO ) throws KANException
   {
      return update( "updateYERRRule", yerrRuleVO );
   }

   @Override
   public int deleteYERRRule( final YERRRuleVO yerrRuleVO ) throws KANException
   {
      return delete( "deleteYERRRule", yerrRuleVO );
   }

   @Override
   public List< Object > getYERRRuleVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getYERRRuleVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getYERRRuleVOsByMapParameter( Map< String, Object > parameterMap ) throws KANException
   {
      return selectList( "getYERRRuleVOsByMapParameter", parameterMap );
   }

}
