package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.TaxTemplateDetailVO;
import com.kan.base.util.KANException;

public interface TaxTemplateDetailDao
{
   public abstract int countTaxTemplateDetailVOsByCondition( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException;

   public abstract List< Object > getTaxTemplateDetailVOsByCondition( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException;

   public abstract List< Object > getTaxTemplateDetailVOsByCondition( final TaxTemplateDetailVO taxTemplateDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract TaxTemplateDetailVO getTaxTemplateDetailVOByTemplateDetailId( final String templateDetailId ) throws KANException;

   public abstract int insertTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException;

   public abstract int updateTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException;

   public abstract int deleteTaxTemplateDetail( final String templateDetailId ) throws KANException;

   public abstract List< Object > getTaxTemplateDetailVOsByTemplateHeaderId( final String templateHeaderId ) throws KANException;
}
