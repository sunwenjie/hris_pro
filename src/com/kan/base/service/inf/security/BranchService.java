package com.kan.base.service.inf.security;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface BranchService
{
   public abstract PagedListHolder getBranchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BranchVO getBranchVOByBranchId( final String branchId ) throws KANException;

   public abstract int updateBranch( final BranchVO branchVO ) throws KANException;

   public abstract int insertBranch( final BranchVO branchVO ) throws KANException;

   public abstract int deleteBranch( final BranchVO branchVO ) throws KANException;

   public abstract List< Object > getBranchVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< BranchDTO > getBranchDTOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getBUFuction();

   public abstract Set< String > copyO_Chart( final Locale locale, final Map< String, String > parameterMap ) throws KANException;
}
