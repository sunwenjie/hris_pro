package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.MappingDTO;
import com.kan.base.domain.define.MappingHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface MappingHeaderService
{
   public abstract PagedListHolder getMappingHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract MappingHeaderVO getMappingHeaderVOByMappingHeaderId( final String mappingHeaderId ) throws KANException;

   public abstract int insertMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException;

   public abstract int updateMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException;

   public abstract int deleteMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException;

   public abstract List< MappingDTO > getMappingDTOsByAccountId( final String accountId ) throws KANException;
}
