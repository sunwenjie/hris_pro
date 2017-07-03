package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.BaseHistoryDao;
import com.kan.base.domain.security.StaffVO;

public class HistoryStaffDaoImpl extends Context implements BaseHistoryDao< StaffVO >
{

   @Override
   public int insertHistory( StaffVO baseVO )
   {
      return insert( "insertHistoryStaff", baseVO );
   }

   @Override
   public int updateHistory( StaffVO baseVO )
   {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public int deleteHistory( String objectId )
   {
      // TODO Auto-generated method stub
      return 0;
   }


   @Override
   public List< StaffVO > getObjectsByWorkflowId( String workflowId )
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public StaffVO getHistoryByHistoryId( String objectId )
   {
      // TODO Auto-generated method stub
      return ( StaffVO ) select( "getHistoryStaffVOByHistoryId", objectId );
   }
   
}
