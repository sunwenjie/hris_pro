package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.SMSConfigDao;
import com.kan.base.domain.system.SMSConfigVO;
import com.kan.base.util.KANException;

public class SMSConfigDaoImpl extends Context implements SMSConfigDao
{

   @Override
   public int countSMSConfigVOsByCondition( SMSConfigVO configVO ) throws KANException
   {
      return ( Integer ) select( "countSMSConfigVOsByCondition", configVO );
   }

   @Override
   public List< Object > getSMSConfigVOsByCondition( SMSConfigVO configVO ) throws KANException
   {
      return selectList( "getSMSConfigVOsByCondition", configVO );
   }

   @Override
   public List< Object > getSMSConfigVOsByCondition( SMSConfigVO configVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSMSConfigVOsByCondition", configVO, rowBounds );
   }

   @Override
   public SMSConfigVO getSMSConfigVOByConfigId( String configId ) throws KANException
   {
      return ( SMSConfigVO ) select( "getSMSConfigVOByConfigId", configId );
   }

   @Override
   public int updateSMSConfig( SMSConfigVO configVO ) throws KANException
   {
      return insert( "updateSMSConfig", configVO );
   }

   @Override
   public int insertSMSConfig( SMSConfigVO configVO ) throws KANException
   {
      return insert( "insertSMSConfig", configVO );
   }

   @Override
   public int deleteSMSConfig( SMSConfigVO configVO ) throws KANException
   {
      return delete( "deleteSMSConfig", configVO );
   }

   @Override
   public List< Object > getSMSConfigVOs() throws KANException
   {
      return selectList( "getSMSConfigVOs", null );
   }

}