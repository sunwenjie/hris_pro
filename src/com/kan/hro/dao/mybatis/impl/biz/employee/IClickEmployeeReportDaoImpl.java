package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.IClickEmployeeReportDao;
import com.kan.hro.domain.biz.employee.IClickEmployeeReportView;

public class IClickEmployeeReportDaoImpl extends Context implements IClickEmployeeReportDao
{

   @Override
   public List< Object > getFullEmployeeReportViewsByCondition( IClickEmployeeReportView employeeReportView ) throws KANException
   {
      return selectList( "getFullEmployeeReportViewsByCondition", employeeReportView );
   }

   @Override
   public List< Object > getFullEmployeeReportViewsByCondition( IClickEmployeeReportView employeeReportView, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getFullEmployeeReportViewsByCondition", employeeReportView, rowBounds );
   }

   @Override
   public int countFullEmployeeReportViewsByCondition( IClickEmployeeReportView employeeReportView ) throws KANException
   {
      return ( Integer ) select( "countFullEmployeeReportViewsByCondition", employeeReportView );
   }

   @Override
   public List< Object > getFullEmployeeReportViewsByCondition_r4( IClickEmployeeReportView employeeReportView ) throws KANException
   {
      return selectList( "getFullEmployeeReportViewsByCondition_r4", employeeReportView );
   }

   @Override
   public List< Object > getFullEmployeeReportViewsByCondition_r4( IClickEmployeeReportView employeeReportView, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getFullEmployeeReportViewsByCondition_r4", employeeReportView, rowBounds );
   }

   @Override
   public int countFullEmployeeReportViewsByCondition_r4( IClickEmployeeReportView employeeReportView ) throws KANException
   {
      return ( Integer ) select( "countFullEmployeeReportViewsByCondition_r4", employeeReportView );
   }

}
