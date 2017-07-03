package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.IClickEmployeeReportView;

public interface IClickEmployeeReportDao
{
   public abstract int countFullEmployeeReportViewsByCondition( final IClickEmployeeReportView employeeReportView ) throws KANException;

   public abstract List< Object > getFullEmployeeReportViewsByCondition( final IClickEmployeeReportView employeeReportView ) throws KANException;

   public abstract List< Object > getFullEmployeeReportViewsByCondition( final IClickEmployeeReportView employeeReportView, final RowBounds rowBounds ) throws KANException;

   public abstract int countFullEmployeeReportViewsByCondition_r4( final IClickEmployeeReportView employeeReportView ) throws KANException;

   public abstract List< Object > getFullEmployeeReportViewsByCondition_r4( final IClickEmployeeReportView employeeReportView ) throws KANException;

   public abstract List< Object > getFullEmployeeReportViewsByCondition_r4( final IClickEmployeeReportView employeeReportView, final RowBounds rowBounds ) throws KANException;

}
