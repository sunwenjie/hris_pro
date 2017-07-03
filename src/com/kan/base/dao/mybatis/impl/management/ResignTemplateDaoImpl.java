package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ResignTemplateDao;
import com.kan.base.domain.management.ResignTemplateVO;
import com.kan.base.util.KANException;

public class ResignTemplateDaoImpl extends Context implements ResignTemplateDao
{

   @Override
   public int countResignTemplateVOsByCondition( final ResignTemplateVO ResignTemplateVO ) throws KANException
   {
      return ( Integer ) select( "countResignTemplateVOsByCondition", ResignTemplateVO );
   }

   @Override
   public List< Object > getResignTemplateVOsByCondition( final ResignTemplateVO ResignTemplateVO ) throws KANException
   {
      return selectList( "getResignTemplateVOsByCondition", ResignTemplateVO );
   }

   @Override
   public List< Object > getResignTemplateVOsByCondition( final ResignTemplateVO ResignTemplateVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getResignTemplateVOsByCondition", ResignTemplateVO, rowBounds );
   }

   @Override
   public ResignTemplateVO getResignTemplateVOByResignTemplateId( final String resignTemplateId ) throws KANException
   {
      return ( ResignTemplateVO ) select( "getResignTemplateVOByResignTemplateId", resignTemplateId );
   }

   @Override
   public int insertResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException
   {
      return insert( "insertResignTemplate", ResignTemplateVO );
   }

   @Override
   public int updateResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException
   {
      return update( "updateResignTemplate", ResignTemplateVO );
   }

   @Override
   public int deleteResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException
   {
      return delete( "deleteResignTemplate", ResignTemplateVO );
   }

   @Override
   public List< Object > getResignTemplateVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getResignTemplateVOsByAccountId", accountId );
   }

}
