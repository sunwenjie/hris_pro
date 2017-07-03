package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.PositionGradeDao;
import com.kan.base.domain.management.PositionGradeVO;
import com.kan.base.util.KANException;

public class PositionGradeDaoImpl extends Context implements PositionGradeDao
{

   @Override
   public List< Object > getPositionGradeVOByCondition( PositionGradeVO positionGradeVO ) throws KANException
   {
      return selectList( "getMgtPositionGradeVOByCondition", positionGradeVO );
   }

   @Override
   public List< Object > getPositionGradeVOByCondition( PositionGradeVO positionGradeVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getMgtPositionGradeVOByCondition", positionGradeVO, rowBounds );
   }

   @Override
   public PositionGradeVO getPositionGradeVOByPositionGradeId( String positionGardeId ) throws KANException
   {
      return ( PositionGradeVO ) select( "getMgtPositionGradeVOByPositionGradeId", positionGardeId );
   }

   @Override
   public PositionGradeVO getPositionGradeVOByGradeName( String gradeName ) throws KANException
   {
      return ( PositionGradeVO ) select( "getMgtPositionGradeVOByGradeName", gradeName );
   }

   @Override
   public int insertPositionGrade( PositionGradeVO positionGradeVO ) throws KANException
   {
      return insert( "insertMgtPositionGrade", positionGradeVO );
   }

   @Override
   public int deletePositionGrade( PositionGradeVO positionGradeVO ) throws KANException
   {

      return delete( "deleteMgtPositionGrade", positionGradeVO );
   }

   @Override
   public int countPositionGradeVOByCondition( final PositionGradeVO positionGradeVO ) throws KANException
   {
      return ( Integer ) select( "countMgtPositionGradeVOByCondition", positionGradeVO );
   }

   @Override
   public int updatePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException
   {
      return update( "updateMgtPositionGrade", positionGradeVO );
   }

   @Override
   public List< Object > getPositionGradeVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getMgtPositionGradeVOsByAccountId", accountId );
   }
}
