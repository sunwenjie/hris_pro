package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.SkillVO;
import com.kan.base.util.KANException;

public interface SkillDao
{
   public abstract int countSkillVOsByCondition( final SkillVO skillVO ) throws KANException;

   public abstract List< Object > getSkillVOsByCondition( final SkillVO skillVO ) throws KANException;

   public abstract List< Object > getSkillVOsByCondition( final SkillVO skillVO, RowBounds rowBounds ) throws KANException;

   public abstract SkillVO getSkillVOBySkillId( final String skillId ) throws KANException;

   public abstract int updateSkill( final SkillVO skillVO ) throws KANException;

   public abstract int insertSkill( final SkillVO skillVO ) throws KANException;

   public abstract int deleteSkill( final SkillVO skillVO ) throws KANException;

   public abstract List< Object > getSkillVOsByParentSkillId( final SkillVO skillVO ) throws KANException;

   public abstract List< Object > getSkillBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getSkillBaseViewsByClientId( final SkillVO skillVO ) throws KANException;
}
