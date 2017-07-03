package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.SMSConfigVO;
import com.kan.base.util.KANException;

public interface SMSConfigDao
{

   public abstract int countSMSConfigVOsByCondition( final SMSConfigVO configVO ) throws KANException;

   public abstract List< Object > getSMSConfigVOsByCondition( final SMSConfigVO configVO ) throws KANException;

   public abstract List< Object > getSMSConfigVOsByCondition( final SMSConfigVO configVO, RowBounds rowBounds ) throws KANException;

   public abstract SMSConfigVO getSMSConfigVOByConfigId( final String configId ) throws KANException;

   public abstract int updateSMSConfig( final SMSConfigVO configVO ) throws KANException;

   public abstract int insertSMSConfig( final SMSConfigVO configVO ) throws KANException;

   public abstract int deleteSMSConfig( final SMSConfigVO configVO ) throws KANException;
   
   public abstract List< Object > getSMSConfigVOs() throws KANException;

}