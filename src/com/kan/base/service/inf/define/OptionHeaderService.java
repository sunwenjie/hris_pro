package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.OptionDTO;
import com.kan.base.domain.define.OptionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface OptionHeaderService
{
   public abstract PagedListHolder getOptionHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract OptionHeaderVO getOptionHeaderVOByOptionHeaderId( final String optionHeaderId ) throws KANException;

   public abstract int insertOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException;

   public abstract int updateOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException;

   public abstract int deleteOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException;

   public abstract List< OptionDTO > getOptionDTOsByAccountId( final String accountId ) throws KANException;
}
