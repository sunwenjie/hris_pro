package com.kan.base.service.inf.cp.management;

import java.util.List;

import com.kan.base.domain.management.SkillBaseView;
import com.kan.base.domain.management.SkillDTO;
import com.kan.base.domain.management.SkillVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CPSkillService
{
   public abstract PagedListHolder getSkillVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SkillVO getSkillVOBySkillId( final String skillId ) throws KANException;

   public abstract List< SkillVO > getAllSkills() throws KANException;

   public abstract int updateSkill( final SkillVO skillVO ) throws KANException;

   public abstract int insertSkill( final SkillVO skillVO ) throws KANException;

   public abstract int deleteSkill( final SkillVO skillVO ) throws KANException;

   public abstract List< SkillDTO > getSkillDTOsByAccountId( final String accountId ) throws KANException;

   public abstract List< SkillBaseView > getSkillBaseViewsByAccountId( final String accountId ) throws KANException;
}
