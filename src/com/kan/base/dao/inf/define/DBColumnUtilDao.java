package com.kan.base.dao.inf.define;

import com.kan.base.domain.define.DBColumnUtilVO;
import com.kan.base.util.KANException;

public interface DBColumnUtilDao
{
   public abstract DBColumnUtilVO getDBColumnUtilVO( final DBColumnUtilVO dbColumnUtilVO ) throws KANException;

}
