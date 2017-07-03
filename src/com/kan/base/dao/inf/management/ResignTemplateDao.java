package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ResignTemplateVO;
import com.kan.base.util.KANException;

public interface ResignTemplateDao
{
   public abstract int countResignTemplateVOsByCondition( final ResignTemplateVO ResignTemplateVO ) throws KANException;

   public abstract List< Object > getResignTemplateVOsByCondition( final ResignTemplateVO ResignTemplateVO ) throws KANException;

   public abstract List< Object > getResignTemplateVOsByCondition( final ResignTemplateVO ResignTemplateVO, final RowBounds rowBounds ) throws KANException;

   public abstract ResignTemplateVO getResignTemplateVOByResignTemplateId( final String resignTemplateId ) throws KANException;

   public abstract int insertResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException;

   public abstract int updateResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException;

   public abstract int deleteResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException;

   public abstract List< Object > getResignTemplateVOsByAccountId( final String accountId ) throws KANException;

}
