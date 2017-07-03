package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.EducationDao;
import com.kan.base.domain.management.EducationVO;
import com.kan.base.util.KANException;

public class EducationDaoImpl extends Context implements EducationDao
{

   @Override
   public int countEducationVOsByCondition( final EducationVO educationVO ) throws KANException
   {
      return ( Integer ) select( "countEducationVOsByCondition", educationVO );
   }

   @Override
   public List< Object > getEducationVOsByCondition( final EducationVO educationVO ) throws KANException
   {
      return selectList( "getEducationVOsByCondition", educationVO );
   }

   @Override
   public List< Object > getEducationVOsByCondition( final EducationVO educationVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEducationVOsByCondition", educationVO, rowBounds );
   }

   @Override
   public EducationVO getEducationVOByEducationId( final String educationId ) throws KANException
   {
      return ( EducationVO ) select( "getEducationVOByEducationId", educationId );
   }

   @Override
   public int insertEducation( final EducationVO educationVO ) throws KANException
   {
      return insert( "insertEducation", educationVO );
   }

   @Override
   public int updateEducation( final EducationVO educationVO ) throws KANException
   {
      return update( "updateEducation", educationVO );
   }

   @Override
   public int deleteEducation( final EducationVO educationVO ) throws KANException
   {
      return delete( "deleteEducation", educationVO );
   }

   @Override
   public List< Object > getEducationVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getEducationVOsByAccountId", accountId );
   }

}
