package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.BankTemplateHeaderVO;
import com.kan.base.util.KANException;

public interface BankTemplateHeaderDao
{
   public abstract int countBankTemplateHeaderVOsByCondition( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException;

   public abstract List< Object > getBankTemplateHeaderVOsByCondition( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException;

   public abstract List< Object > getBankTemplateHeaderVOsByCondition( final BankTemplateHeaderVO bankTemplateHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract BankTemplateHeaderVO getBankTemplateHeaderVOByTemplateHeaderId( final String templateHeaderId ) throws KANException;

   public abstract int insertBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException;

   public abstract int updateBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException;

   public abstract int deleteBankTemplateHeader( final String templateHeaderId ) throws KANException;

   public abstract List< Object > getBankTemplateHeaderVOsByAccountId( final String accountId ) throws KANException;
}
