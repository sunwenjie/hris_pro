package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.util.KANException;

public interface ImportHeaderDao
{
   public abstract int countImportHeaderVOsByCondition( final ImportHeaderVO importHeaderVO ) throws KANException;

   public abstract List< Object > getImportHeaderVOsByCondition( final ImportHeaderVO importHeaderVO ) throws KANException;

   public abstract List< Object > getImportHeaderVOsByCondition( final ImportHeaderVO importHeaderVO, RowBounds rowBounds ) throws KANException;
   public abstract List< Object > getImportHeaderVOsByAccountId( final String accountId) throws KANException;

   public abstract ImportHeaderVO getImportHeaderVOByImportHeaderId( final String importHeaderId ) throws KANException;

   public abstract int insertImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException;

   public abstract int updateImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException;

   public abstract int deleteImportHeader( final String importHeaderId ) throws KANException;
}
