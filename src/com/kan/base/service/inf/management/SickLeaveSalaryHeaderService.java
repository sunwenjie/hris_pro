package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.SickLeaveSalaryDTO;
import com.kan.base.domain.management.SickLeaveSalaryHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SickLeaveSalaryHeaderService
{
   public abstract PagedListHolder getSickLeaveSalaryHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SickLeaveSalaryHeaderVO getSickLeaveSalaryHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertSickLeaveSalaryHeader(final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException;

   public abstract int updateSickLeaveSalaryHeader(final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException;

   public abstract int deleteSickLeaveSalaryHeader(final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException;

   public abstract List< Object > getAvailableSickLeaveSalaryHeaderVOs( final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException;

   public abstract List< SickLeaveSalaryDTO > getSickLeaveSalaryDTOsByAccountId( final String accountId ) throws KANException;
}
