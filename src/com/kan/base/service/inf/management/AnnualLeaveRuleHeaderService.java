package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.AnnualLeaveRuleDTO;
import com.kan.base.domain.management.AnnualLeaveRuleHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface AnnualLeaveRuleHeaderService
{
   public abstract PagedListHolder getAnnualLeaveRuleHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract AnnualLeaveRuleHeaderVO getAnnualLeaveRuleHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertAnnualLeaveRuleHeader( final AnnualLeaveRuleHeaderVO annualLeaveHeaderRuleVO ) throws KANException;

   public abstract int updateAnnualLeaveRuleHeader( final AnnualLeaveRuleHeaderVO annualLeaveHeaderRuleVO ) throws KANException;

   public abstract int deleteAnnualLeaveRuleHeader( final AnnualLeaveRuleHeaderVO annualLeaveHeaderRuleVO ) throws KANException;

   public abstract List< AnnualLeaveRuleDTO > getAnnualLeaveRuleDTOsByAccountId( final String accountId ) throws KANException;
}
