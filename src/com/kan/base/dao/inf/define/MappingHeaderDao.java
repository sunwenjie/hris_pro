package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.MappingHeaderVO;
import com.kan.base.util.KANException;

public interface MappingHeaderDao
{
   public abstract int countMappingHeaderVOsByCondition( final MappingHeaderVO mappingHeaderVO ) throws KANException;

   public abstract List< Object > getMappingHeaderVOsByCondition( final MappingHeaderVO mappingHeaderVO ) throws KANException;

   public abstract List< Object > getMappingHeaderVOsByCondition( final MappingHeaderVO mappingHeaderVO, RowBounds rowBounds ) throws KANException;

   public abstract MappingHeaderVO getMappingHeaderVOByMappingHeaderId( final String mappingHeaderId ) throws KANException;

   public abstract int insertMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException;

   public abstract int updateMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException;

   public abstract int deleteMappingHeader( final String mappingHeaderId ) throws KANException;

   public abstract List< Object > getMappingHeaderVOsByAccountId( final String accountId ) throws KANException;
}
