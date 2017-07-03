package com.kan.base.dao.inf.management;

import com.kan.base.domain.management.OptionsVO;
import com.kan.base.util.KANException;

public interface OptionsDao
{

   public abstract OptionsVO getOptionsVOByOptionsId( final String optionsId ) throws KANException;
   
   public abstract OptionsVO getOptionsVOByAccountId( final String accountId ) throws KANException;

   public abstract int updateOptions( final OptionsVO optionsVO ) throws KANException;

   public abstract int insertOptions( final OptionsVO optionsVO ) throws KANException;

   public abstract int deleteOptions( final String optionsId ) throws KANException;

}