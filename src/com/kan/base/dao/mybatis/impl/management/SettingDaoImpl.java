package com.kan.base.dao.mybatis.impl.management;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.SettingDao;
import com.kan.base.domain.management.SettingVO;
import com.kan.base.util.KANException;

public class SettingDaoImpl extends Context implements SettingDao
{

   @Override
   public SettingVO getSettingVOBySettingId( String settingId ) throws KANException
   {
      return ( SettingVO ) select( "getsettingVOBySettingId", settingId );
   }

   @Override
   public SettingVO getSettingVOByUserId( String userId ) throws KANException
   {
      return ( SettingVO ) select( "getSettingVOByUserId", userId );
   }

   @Override
   public int updateSetting( SettingVO settingVO ) throws KANException
   {
      return update( "updateSetting", settingVO );
   }

   @Override
   public int insertSetting( SettingVO settingVO ) throws KANException
   {
      return insert( "insertSetting", settingVO );
   }

   @Override
   public int deleteSetting( SettingVO settingVO ) throws KANException
   {
      return delete( "deleteSetting", settingVO );
   }

}