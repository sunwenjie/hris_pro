package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.AnnualLeaveRuleHeaderVO;
import com.kan.base.util.KANException;

public interface AnnualLeaveRuleHeaderDao
{
   public abstract int countAnnualLeaveRuleHeaderVOsByCondition( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException;

   public abstract List< Object > getAnnualLeaveRuleHeaderVOsByCondition( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException;

   public abstract List< Object > getAnnualLeaveRuleHeaderVOsByCondition( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract AnnualLeaveRuleHeaderVO getAnnualLeaveRuleHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertAnnualLeaveRuleHeader( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException;

   public abstract int updateAnnualLeaveRuleHeader( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException;

   public abstract int deleteAnnualLeaveRuleHeader( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException;

   public abstract List< Object > getAnnualLeaveRuleHeaderVOsByAccountId( final String accountId ) throws KANException;
}
