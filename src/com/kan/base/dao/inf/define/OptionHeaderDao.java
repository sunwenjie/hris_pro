package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.OptionHeaderVO;
import com.kan.base.util.KANException;

public interface OptionHeaderDao
{
   public abstract int countOptionHeaderVOsByCondition( final OptionHeaderVO optionHeaderVO ) throws KANException;

   public abstract List< Object > getOptionHeaderVOsByCondition( final OptionHeaderVO optionHeaderVO ) throws KANException;

   public abstract List< Object > getOptionHeaderVOsByCondition( final OptionHeaderVO optionHeaderVO, RowBounds rowBounds ) throws KANException;

   public abstract OptionHeaderVO getOptionHeaderVOByOptionHeaderId( final String optionHeaderId ) throws KANException;

   public abstract int insertOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException;

   public abstract int updateOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException;

   public abstract int deleteOptionHeader( final String optionHeaderId ) throws KANException;

   public abstract List< Object > getOptionHeaderVOsByAccountId( final String accountId ) throws KANException;
}
