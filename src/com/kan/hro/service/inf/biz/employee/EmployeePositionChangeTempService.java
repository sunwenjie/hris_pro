package com.kan.hro.service.inf.biz.employee;

import java.util.Locale;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface EmployeePositionChangeTempService
{
   public void getEmployeePositionChangeTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public int submitEmployeePositionChangeTempVOByPositionChangeIds( final String[] PositionChangeIds, final String userId, final String ip, final Locale locale )
         throws KANException;

   public int rollbackEmployeePositionChangeTempVOByPositionChangeIds( final String[] PositionChangeIds, final String userId ) throws KANException;
}
