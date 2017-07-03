package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.TaxTemplateHeaderVO;
import com.kan.base.util.KANException;

public interface TaxTemplateHeaderDao
{
   public abstract int countTaxTemplateHeaderVOsByCondition( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException;

   public abstract List< Object > getTaxTemplateHeaderVOsByCondition( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException;

   public abstract List< Object > getTaxTemplateHeaderVOsByCondition( final TaxTemplateHeaderVO taxTemplateHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract TaxTemplateHeaderVO getTaxTemplateHeaderVOByTemplateHeaderId( final String templateHeaderId ) throws KANException;

   public abstract int insertTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException;

   public abstract int updateTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException;

   public abstract int deleteTaxTemplateHeader( final String templateHeaderId ) throws KANException;

   public abstract List< Object > getTaxTemplateHeaderVOsByAccountId( final String accountId ) throws KANException;
}
