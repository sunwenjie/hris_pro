package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.BankTemplateDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface BankTemplateDetailService
{
   public abstract PagedListHolder getBankTemplateDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BankTemplateDetailVO getBankTemplateDetailVOByTemplateDetailId( final String templateDetailId ) throws KANException;

   public abstract int insertBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException;

   public abstract int updateBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException;

   public abstract int deleteBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException;

   public abstract List< Object > getBankTemplateDetailVOsByTemplateHeaderId( final String templateHeaderId ) throws KANException;
}
