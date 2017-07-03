package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.util.KANException;

public interface ManagerHeaderDao
{
   public abstract int countManagerHeaderVOsByCondition( final ManagerHeaderVO managerHeaderVO ) throws KANException;

   public abstract List< Object > getManagerHeaderVOsByCondition( final ManagerHeaderVO managerHeaderVO ) throws KANException;

   public abstract List< Object > getManagerHeaderVOsByCondition( final ManagerHeaderVO managerHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract ManagerHeaderVO getManagerHeaderVOByManagerHeaderId( final String managerHeaderId ) throws KANException;

   public abstract int insertManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException;

   public abstract int updateManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException;

   public abstract int deleteManagerHeader( final String managerHeaderId ) throws KANException;

   public abstract List< Object > getManagerHeaderVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getAccountManagerHeaderVOsByCondition( final ManagerHeaderVO managerHeaderVO ) throws KANException;
}
