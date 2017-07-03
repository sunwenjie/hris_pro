package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.MappingDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface MappingDetailService
{
   public abstract PagedListHolder getMappingDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract MappingDetailVO getMappingDetailVOByMappingDetailId( final String mappingDetailId ) throws KANException;

   public abstract int insertMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException;

   public abstract int updateMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException;

   public abstract int deleteMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException;

   public abstract List< Object > getMappingDetailVOsByMappingHeaderId( final String mappingHeaderId ) throws KANException;

   public abstract int deleteMappingDetail( final String mappingHeaderId ) throws KANException;
}
