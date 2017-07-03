package com.kan.base.service.inf.management;

import com.kan.base.domain.management.OptionsVO;
import com.kan.base.util.KANException;

public interface OptionsService
{

   public abstract OptionsVO getOptionsVOByOptionsId( final String optionsId ) throws KANException;
   
   public abstract OptionsVO getOptionsVOByAccountId( final String accountId ) throws KANException;

   public abstract int updateOptions( final OptionsVO optionsVO ) throws KANException;
   
   public abstract int updateOptionsSyncEntity( final OptionsVO optionsVO ) throws KANException;

   public abstract int insertOptions( final OptionsVO optionsVO ) throws KANException;

   public abstract void deleteOptions( final String optionsId ) throws KANException;

}
