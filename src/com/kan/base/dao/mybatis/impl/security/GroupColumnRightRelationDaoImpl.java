package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.GroupColumnRightRelationDao;
import com.kan.base.domain.security.GroupColumnRightRelationVO;
import com.kan.base.util.KANException;

public class GroupColumnRightRelationDaoImpl extends Context implements GroupColumnRightRelationDao
{

   @Override
   public int countPositionGroupColumnRightRelationVOsByCondition( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO ) throws KANException
   {
      return ( Integer ) select( "countPositionGroupColumnRightRelationVOsByCondition", positionGroupColumnRightRelationVO );
   }

   @Override
   public List< Object > getPositionGroupColumnRightRelationVOsByCondition( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO ) throws KANException
   {
      return selectList( "getPositionGroupColumnRightRelationVOsByCondition", positionGroupColumnRightRelationVO );
   }

   @Override
   public List< Object > getPositionGroupColumnRightRelationVOsByCondition( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO, final RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getPositionGroupColumnRightRelationVOsByCondition", positionGroupColumnRightRelationVO, rowBounds );
   }

   @Override
   public GroupColumnRightRelationVO getPositionGroupColumnRightRelationVOByPositionGroupColumnRightRelationId( final String positionGroupStaffRelationId )
         throws KANException
   {
      return ( GroupColumnRightRelationVO ) select( "getPositionGroupColumnRightRelationVOByPositionGroupColumnRightRelationId", positionGroupStaffRelationId );
   }

   @Override
   public int updatePositionGroupColumnRightRelation( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO ) throws KANException
   {
      return update( "updatePositionGroupColumnRightRelation", positionGroupColumnRightRelationVO );
   }

   @Override
   public int insertPositionGroupColumnRightRelation( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO ) throws KANException
   {
      return insert( "insertPositionGroupColumnRightRelation", positionGroupColumnRightRelationVO );
   }

   @Override
   public int deletePositionGroupColumnRightRelationByCondition( final GroupColumnRightRelationVO positionGroupColumnRightRelationVO ) throws KANException
   {
      return delete( "deletePositionGroupColumnRightRelationByCondition", positionGroupColumnRightRelationVO );
   }

   @Override
   public List< Object > getPositionGroupColumnRightRelationVOsByGroupId( final String groupId ) throws KANException
   {
      return selectList( "getPositionGroupColumnRightRelationVOsByPositionGroupId", groupId );
   }

}
