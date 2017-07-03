package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.BankTemplateDTO;
import com.kan.base.domain.define.BankTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface BankTemplateHeaderService
{
   public abstract PagedListHolder getBankTemplateHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BankTemplateHeaderVO getBankTemplateHeaderVOByTemplateHeaderId( final String templateHeaderId ) throws KANException;

   public abstract int insertBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException;

   public abstract int updateBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException;

   public abstract int deleteBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException;

   public abstract List< BankTemplateDTO > getBankTemplateDTOsByAccountId( final String accountId ) throws KANException;

}
