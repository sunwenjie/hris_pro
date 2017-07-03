package com.kan.base.dao.inf.management;

import com.kan.base.domain.management.SettingVO;
import com.kan.base.util.KANException;

public interface SettingDao
{

   public abstract SettingVO getSettingVOBySettingId( final String settingId ) throws KANException;

   public abstract SettingVO getSettingVOByUserId( final String userId ) throws KANException;

   public abstract int updateSetting( final SettingVO settingVO ) throws KANException;

   public abstract int insertSetting( final SettingVO settingVO ) throws KANException;

   public abstract int deleteSetting( final SettingVO settingVO ) throws KANException;

}