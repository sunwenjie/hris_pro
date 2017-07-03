package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.IndustryTypeVO;
import com.kan.base.util.KANException;

public interface IndustryTypeDao
{
   public abstract int countIndustryTypeVOsByCondition( final IndustryTypeVO industryTypeVO ) throws KANException;

   public abstract List< Object > getIndustryTypeVOsByCondition( final IndustryTypeVO industryTypeVO ) throws KANException;

   public abstract List< Object > getIndustryTypeVOsByCondition( final IndustryTypeVO industryTypeVO, final RowBounds rowBounds ) throws KANException;

   public abstract IndustryTypeVO getIndustryTypeVOByIndustryTypeId( final String industryTypeId ) throws KANException;

   public abstract int insertIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException;

   public abstract int updateIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException;

   public abstract int deleteIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException;

   public abstract List< Object > getIndustryTypeVOsByAccountId( final String accountId ) throws KANException;

}
