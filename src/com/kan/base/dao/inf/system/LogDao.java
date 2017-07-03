package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.LogVO;
import com.kan.base.util.KANException;

public interface LogDao
{
   public abstract int insertLog( final LogVO logVO ) throws KANException;

   public abstract int countLogVOsByCondition( final LogVO logVO ) throws KANException;

   public abstract List< Object > getLogVOsByCondition( final LogVO logVO ) throws KANException;

   public abstract List< Object > getLogVOsByCondition( final LogVO logVO, RowBounds rowBounds ) throws KANException;

   public abstract LogVO getLogVOById( final String id ) throws KANException;

   public abstract List< Object > getLogModules() throws KANException;

   public abstract LogVO getPreLog( final LogVO logVO ) throws KANException;

}