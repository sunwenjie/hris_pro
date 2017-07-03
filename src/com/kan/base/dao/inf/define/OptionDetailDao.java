package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.OptionDetailVO;
import com.kan.base.util.KANException;

public interface OptionDetailDao
{
   public abstract int countOptionDetailVOsByCondition( final OptionDetailVO optionDetailVO ) throws KANException;

   public abstract List< Object > getOptionDetailVOsByCondition( final OptionDetailVO optionDetailVO ) throws KANException;

   public abstract List< Object > getOptionDetailVOsByCondition( final OptionDetailVO optionDetailVO, RowBounds rowBounds ) throws KANException;

   public abstract OptionDetailVO getOptionDetailVOByOptionDetailId( final String optionDetailId ) throws KANException;

   public abstract int insertOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException;

   public abstract int updateOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException;

   public abstract int deleteOptionDetail( final String optionDetailId ) throws KANException;

   public abstract List< Object > getOptionDetailVOsByOptionHeaderId( final String optionHeaderId ) throws KANException;

   public abstract String getMaxOptionId( final String optionHeaderId ) throws KANException;
}
