package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.TaxVO;
import com.kan.base.util.KANException;

public interface TaxDao
{
   public abstract int countTaxVOsByCondition( final TaxVO taxVO ) throws KANException;

   public abstract List< Object > getTaxVOsByCondition( final TaxVO taxVO ) throws KANException;

   public abstract List< Object > getTaxVOsByCondition( final TaxVO taxVO, final RowBounds rowBounds ) throws KANException;

   public abstract TaxVO getTaxVOByTaxId( final String taxId ) throws KANException;

   public abstract int insertTax( final TaxVO taxVO ) throws KANException;

   public abstract int updateTax( final TaxVO taxVO ) throws KANException;

   public abstract int deleteTax( final TaxVO taxVO ) throws KANException;

   public abstract List< Object > getTaxVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getTaxVOsByTaxVO( final TaxVO taxVO ) throws KANException;

}
