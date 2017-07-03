package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.MappingDetailVO;
import com.kan.base.util.KANException;

public interface MappingDetailDao
{
   public abstract int countMappingDetailVOsByCondition( final MappingDetailVO mappingDetailVO ) throws KANException;

   public abstract List< Object > getMappingDetailVOsByCondition( final MappingDetailVO mappingDetailVO ) throws KANException;

   public abstract List< Object > getMappingDetailVOsByCondition( final MappingDetailVO mappingDetailVO, RowBounds rowBounds ) throws KANException;

   public abstract MappingDetailVO getMappingDetailVOByMappingDetailId( final String mappingDetailId ) throws KANException;

   public abstract int insertMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException;

   public abstract int updateMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException;

   public abstract int deleteMappingDetail( final String mappingDetailId ) throws KANException;

   public abstract List< Object > getMappingDetailVOsByMappingHeaderId( final String mappingHeaderId ) throws KANException;
}
