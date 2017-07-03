package com.kan.base.service.impl.define;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.DBColumnUtilDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.define.DBColumnUtilVO;
import com.kan.base.service.inf.define.DBColumnUtilService;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class DBColumnUtilServiceImpl extends ContextService implements DBColumnUtilService
{

   @Override
   public DBColumnUtilVO getDBColumnUtilVO( DBColumnUtilVO dbColumnUtilVO ) throws KANException
   {
      // TODO Auto-generated method stub
      return ((DBColumnUtilDao)this.getDao()).getDBColumnUtilVO( dbColumnUtilVO );
   }

   @Override
   public String getAuthStr( final HttpServletRequest request, final HttpServletResponse response, String tableName, String tableAccess ) throws KANException
   {
      BaseVO employeeVO = new EmployeeVO();
      final StringBuffer authSb = new StringBuffer();
      BaseAction.setDataAuth( request, response, employeeVO );
      DBColumnUtilVO dbColumnUtilVO = new DBColumnUtilVO();
      dbColumnUtilVO.setTableName( tableName );
      DBColumnUtilVO dbColumnUtilVOReturn = this.getDBColumnUtilVO( dbColumnUtilVO );
      String ids = null;
      if ( StringUtils.isNotBlank( employeeVO.getRulePublic() ) && "1".equals( employeeVO.getRulePublic() ) )
      {
         authSb.append( " AND ( false " );
         if ( dbColumnUtilVOReturn.getHaveEmployeeId() != "0" && employeeVO.getRulePrivateIds() != null && employeeVO.getRulePrivateIds().size() > 0 )
         {
            ids = employeeVO.getRulePrivateIds().toString().replace( "[", "(" ).replace( "]", ")" );
            authSb.append( " OR " + tableAccess + ".employeeId in " + ids );
         }

         if ( dbColumnUtilVOReturn.getHaveOwner() != "0" && employeeVO.getRulePositionIds() != null && employeeVO.getRulePositionIds().size() > 0 )
         {
            ids = employeeVO.getRulePositionIds().toString().replace( "[", "(" ).replace( "]", ")" );
            authSb.append( " OR " + tableAccess + ".owner in " + ids );
         }

         if ( dbColumnUtilVOReturn.getHaveBranch() != "0" && employeeVO.getRuleBranchIds() != null && employeeVO.getRuleBranchIds().size() > 0 )
         {
            ids = employeeVO.getRuleBranchIds().toString().replace( "[", "(" ).replace( "]", ")" );
            authSb.append( " OR " + tableAccess + ".branch in " + ids );
         }

         if ( dbColumnUtilVOReturn.getHaveBusinessTypeId() != "0" && employeeVO.getRuleBusinessTypeIds() != null && employeeVO.getRuleBusinessTypeIds().size() > 0 )
         {
            ids = employeeVO.getRuleBusinessTypeIds().toString().replace( "[", "(" ).replace( "]", ")" );
            authSb.append( " OR " + tableAccess + ".businessTypeId in " + ids );
         }

         if ( dbColumnUtilVOReturn.getHaveEntityId() != "0" && employeeVO.getRuleEntityIds() != null && employeeVO.getRuleEntityIds().size() > 0 )
         {
            ids = employeeVO.getRuleEntityIds().toString().replace( "[", "(" ).replace( "]", ")" );
            authSb.append( " OR " + tableAccess + ".entityId in " + ids );
         }
         authSb.append( " ) " );
      }
      // TODO Auto-generated method stub
      return authSb.toString();
   }
}
