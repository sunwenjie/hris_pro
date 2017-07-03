package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.EducationVO;
import com.kan.base.util.KANException;

public interface EducationDao
{
   public abstract int countEducationVOsByCondition( final EducationVO educationVO ) throws KANException;

   public abstract List< Object > getEducationVOsByCondition( final EducationVO educationVO ) throws KANException;

   public abstract List< Object > getEducationVOsByCondition( final EducationVO educationVO, RowBounds rowBounds ) throws KANException;

   public abstract EducationVO getEducationVOByEducationId( final String educationId ) throws KANException;

   public abstract int insertEducation( final EducationVO educationVO ) throws KANException;

   public abstract int updateEducation( final EducationVO educationVO ) throws KANException;

   public abstract int deleteEducation( final EducationVO educationVO ) throws KANException;
   
   public abstract List< Object > getEducationVOsByAccountId( final String accountId ) throws KANException;
}
