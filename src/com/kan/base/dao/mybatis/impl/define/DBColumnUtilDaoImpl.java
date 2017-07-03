package com.kan.base.dao.mybatis.impl.define;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.DBColumnUtilDao;
import com.kan.base.domain.define.DBColumnUtilVO;
import com.kan.base.util.KANException;

public class DBColumnUtilDaoImpl  extends Context implements DBColumnUtilDao
{


   @Override
   public DBColumnUtilVO getDBColumnUtilVO( DBColumnUtilVO dbColumnUtilVO ) throws KANException
   {
      // TODO Auto-generated method stub
      return ( DBColumnUtilVO ) select( "getDBColumnUtilVOByCondition", dbColumnUtilVO );
   }


   
}
