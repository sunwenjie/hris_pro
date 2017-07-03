package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.EducationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface EducationService
{
   public abstract PagedListHolder getEducationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EducationVO getEducationVOByEducationId( final String educationId ) throws KANException;

   public abstract int insertEducation( final EducationVO educationVO ) throws KANException;

   public abstract int updateEducation( final EducationVO educationVO ) throws KANException;

   public abstract int deleteEducation( final EducationVO educationVO ) throws KANException;

   public abstract List< Object > getEducationVOsByAccountId( final String accountId ) throws KANException;
}
