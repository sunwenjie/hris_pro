package com.kan.base.service.inf.define;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kan.base.domain.define.DBColumnUtilVO;
import com.kan.base.util.KANException;

public interface DBColumnUtilService
{
   public abstract DBColumnUtilVO getDBColumnUtilVO( final DBColumnUtilVO dbColumnUtilVO ) throws KANException;

   public abstract String getAuthStr( HttpServletRequest request, HttpServletResponse response, String tableName, String tableAccess ) throws KANException;
}
