package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.util.KANException;

public interface AnnualLeaveRuleDetailDao
{
   public abstract int countAnnualLeaveRuleDetailVOsByCondition( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException;

   public abstract List< Object > getAnnualLeaveRuleDetailVOsByCondition( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException;

   public abstract List< Object > getAnnualLeaveRuleDetailVOsByCondition( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract AnnualLeaveRuleDetailVO getAnnualLeaveRuleDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertAnnualLeaveRuleDetail( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException;

   public abstract int updateAnnualLeaveRuleDetail( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException;

   public abstract int deleteAnnualLeaveRuleDetail( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException;

   public abstract List< Object > getAnnualLeaveRuleDetailVOsByHeaderId( final String headerId ) throws KANException;
}
