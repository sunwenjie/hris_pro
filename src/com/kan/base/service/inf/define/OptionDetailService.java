package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.OptionDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface OptionDetailService
{
   public abstract PagedListHolder getOptionDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract OptionDetailVO getOptionDetailVOByOptionDetailId( final String optionDetailId ) throws KANException;

   public abstract int insertOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException;

   public abstract int updateOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException;

   public abstract int deleteOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException;
   
   public abstract List< Object > getOptionDetailVOsByOptionHeaderId( final String optionHeaderId ) throws KANException;

   public abstract String getMaxOptionId( final String optionHeaderId ) throws KANException;
}
