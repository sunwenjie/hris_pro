package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SickLeaveSalaryDetailService 
{
   public abstract PagedListHolder getSickLeaveSalaryDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SickLeaveSalaryDetailVO getSickLeaveSalaryDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException;

   public abstract int updateSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException;

   public abstract int deleteSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException;

   public abstract List< Object > getAvailableSickLeaveSalaryDetailVOs( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException;
   
   public abstract List< Object > getSickLeaveSalaryDetailByHeaderId(final String headerId) throws KANException;
}
