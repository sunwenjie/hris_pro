package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.BankTemplateDetailVO;
import com.kan.base.util.KANException;

public interface BankTemplateDetailDao
{
   public abstract int countBankTemplateDetailVOsByCondition( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException;

   public abstract List< Object > getBankTemplateDetailVOsByCondition( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException;

   public abstract List< Object > getBankTemplateDetailVOsByCondition( final BankTemplateDetailVO bankTemplateDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract BankTemplateDetailVO getBankTemplateDetailVOByTemplateDetailId( final String templateDetailId ) throws KANException;

   public abstract int insertBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException;

   public abstract int updateBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException;

   public abstract int deleteBankTemplateDetail( final String templateDetailId ) throws KANException;

   public abstract List< Object > getBankTemplateDetailVOsByTemplateHeaderId( final String templateHeaderId ) throws KANException;
}
