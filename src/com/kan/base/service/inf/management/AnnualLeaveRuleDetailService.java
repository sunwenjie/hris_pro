package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface AnnualLeaveRuleDetailService
{
   public abstract PagedListHolder getAnnualLeaveRuleDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract AnnualLeaveRuleDetailVO getAnnualLeaveRuleDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertAnnualLeaveRuleDetail( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException;

   public abstract int updateAnnualLeaveRuleDetail( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException;

   public abstract int deleteAnnualLeaveRuleDetail( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException;

   public abstract List< Object > getAnnualLeaveRuleDetailVOsByHeaderId( final String headerId ) throws KANException;
}
