package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.SkillDao;
import com.kan.base.domain.management.SkillVO;
import com.kan.base.util.KANException;

public class SkillDaoImpl extends Context implements SkillDao
{

   @Override
   public int countSkillVOsByCondition( final SkillVO skillVO ) throws KANException
   {
      return ( Integer ) select( "countSkillVOsByCondition", skillVO );
   }

   @Override
   public List< Object > getSkillVOsByCondition( final SkillVO skillVO ) throws KANException
   {
      return selectList( "getSkillVOsByCondition", skillVO );
   }

   @Override
   public List< Object > getSkillVOsByCondition( final SkillVO skillVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSkillVOsByCondition", skillVO, rowBounds );
   }

   @Override
   public SkillVO getSkillVOBySkillId( final String skillId ) throws KANException
   {
      if ( skillId == null || "".equals( skillId.trim() ) )
      {
         return ( SkillVO ) select( "getSkillVOBySkillId", "1" );
      }
      else
      {
         return ( SkillVO ) select( "getSkillVOBySkillId", skillId );
      }
   }

   @Override
   public int updateSkill( final SkillVO skillVO ) throws KANException
   {
      return update( "updateSkill", skillVO );
   }

   @Override
   public int insertSkill( final SkillVO skillVO ) throws KANException
   {
      return insert( "insertSkill", skillVO );
   }

   @Override
   public int deleteSkill( final SkillVO skillVO ) throws KANException
   {
      return delete( "deleteSkill", skillVO );
   }

   @Override
   public List< Object > getSkillVOsByParentSkillId( final SkillVO skillVO ) throws KANException
   {
      return selectList( "getSkillVOsByParentSkillId", skillVO );
   }

   @Override
   public List< Object > getSkillBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getSkillBaseViewsByAccountId", accountId );
   }

   @Override
   public List< Object > getSkillBaseViewsByClientId( SkillVO skillVO ) throws KANException
   {
      return selectList( "getSkillBaseViewsByClientId", skillVO );
   }

}
