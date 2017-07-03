package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ImportHeaderService
{
   public abstract PagedListHolder getImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ImportHeaderVO getImportHeaderVOByImportHeaderId( final String importHeaderId ) throws KANException;

   public abstract int insertImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException;

   public abstract int updateImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException;

   public abstract int deleteImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException;

   public abstract List< ImportDTO > getImportDTOsByAccountId( final String accountId ) throws KANException;
}
