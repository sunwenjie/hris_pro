package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.KANException;

public class StaffDaoImpl extends Context implements StaffDao
{

   @Override
   public int countStaffVOsByCondition( final StaffVO staffVO ) throws KANException
   {
      return ( Integer ) select( "countStaffVOsByCondition", staffVO );
   }

   @Override
   public List< Object > getStaffVOsByCondition( final StaffVO staffVO ) throws KANException
   {
      return selectList( "getStaffVOsByCondition", staffVO );
   }

   @Override
   public List< Object > getStaffVOsByCondition( final StaffVO staffVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getStaffVOsByCondition", staffVO, rowBounds );
   }

   @Override
   public StaffVO getStaffVOByStaffId( final String staffId ) throws KANException
   {
      return ( StaffVO ) select( "getStaffVOByStaffId", staffId );
   }

   @Override
   public int updateStaff( final StaffVO staffVO ) throws KANException
   {
      return update( "updateStaff", staffVO );
   }

   @Override
   public int insertStaff( final StaffVO staffVO ) throws KANException
   {
      return insert( "insertStaff", staffVO );
   }

   @Override
   public int insertHistoryStaff( final StaffVO staffVO ) throws KANException
   {
      return insert( "insertHistoryStaff", staffVO );
   }

   @Override
   public int deleteStaff( final String staffId ) throws KANException
   {
      return delete( "deleteStaff", staffId );
   }

   @Override
   public List< Object > getStaffVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getStaffVOsByAccountId", accountId );
   }
   
   @Override
   public List< Object > getStaffVOsCascadeByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getStaffVOsCascadeByAccountId", accountId );
   }

   @Override
   public List< Object > getStaffBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getStaffBaseViewsByAccountId", accountId );
   }

   @Override
   public List< Object > getActiveStaffBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getActiveStaffBaseViewsByAccountId", accountId );
   }

   @Override
   public List< Object > getActiveStaffVOsByCorpId( final StaffVO staffVO ) throws KANException
   {
      return selectList( "getActiveStaffVOsByCorpId", staffVO );
   }
   
   @Override
   public List< Object > getActiveStaffVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getActiveStaffVOsByAccountId", accountId );
   }

   @Override
   public StaffVO getStaffVOByEmployeeId( String employeeId ) throws KANException
   {
      return ( StaffVO ) select( "getStaffVOByEmployeeId", employeeId );
   }

   @Override
   public List< Object > getStaffVOsByBranchId( String branchId ) throws KANException
   {
      return selectList( "getStaffVOsByCondition", branchId );
   }

   @Override
   public int getCountStaffVOsByBranchId( String branchId ) throws KANException
   {
      return ( Integer ) select( "getCountStaffVOsByBranchId", branchId );
   }

   @Override
   public List< Object > getStaffVOsByBranchId( String branchId, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getStaffVOsByBranchId", branchId, rowBounds );
   }

   @Override
   public StaffBaseView getStaffBaseViewByStaffId( String staffId ) throws KANException
   {
      return ( StaffBaseView ) select( "getStaffBaseViewByStaffId", staffId );
   }

   @Override
   public List< Object > logon(final StaffVO staffVO ) throws KANException
   {
      return selectList( "logon", staffVO );
   }

}
