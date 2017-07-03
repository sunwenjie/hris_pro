package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.KANException;

public interface StaffDao
{
   public abstract int countStaffVOsByCondition( final StaffVO staffVO ) throws KANException;

   public abstract List< Object > getStaffVOsByCondition( final StaffVO staffVO ) throws KANException;

   public abstract List< Object > getStaffVOsByCondition( final StaffVO staffVO, RowBounds rowBounds ) throws KANException;

   public abstract StaffVO getStaffVOByStaffId( final String staffId ) throws KANException;

   public abstract int insertStaff( final StaffVO staffVO ) throws KANException;

   public abstract int insertHistoryStaff( final StaffVO staffVO ) throws KANException;

   public abstract int updateStaff( final StaffVO staffVO ) throws KANException;

   public abstract int deleteStaff( final String staffId ) throws KANException;

   public abstract List< Object > getStaffVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getStaffBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getActiveStaffBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getActiveStaffVOsByCorpId( final StaffVO staffVO ) throws KANException;

   public abstract List< Object > getActiveStaffVOsByAccountId( final String accountId ) throws KANException;

   public abstract StaffVO getStaffVOByEmployeeId( final String employeeId ) throws KANException;

   public abstract int getCountStaffVOsByBranchId( final String branchId ) throws KANException;

   public abstract List< Object > getStaffVOsByBranchId( final String branchId ) throws KANException;

   public abstract List< Object > getStaffVOsByBranchId( final String branchId, RowBounds rowBounds ) throws KANException;

   public abstract StaffBaseView getStaffBaseViewByStaffId( final String staffId ) throws KANException;

   public abstract List<Object> getStaffVOsCascadeByAccountId(String accountId) throws KANException;

   public abstract List< Object > logon( final StaffVO staffVO ) throws KANException;
}
