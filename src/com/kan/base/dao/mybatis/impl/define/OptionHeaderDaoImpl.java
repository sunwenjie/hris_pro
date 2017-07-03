package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.OptionHeaderDao;
import com.kan.base.domain.define.OptionHeaderVO;
import com.kan.base.util.KANException;

public class OptionHeaderDaoImpl extends Context implements OptionHeaderDao
{

   @Override
   public int countOptionHeaderVOsByCondition( final OptionHeaderVO optionHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countOptionHeaderVOsByCondition", optionHeaderVO );
   }

   @Override
   public List< Object > getOptionHeaderVOsByCondition( final OptionHeaderVO optionHeaderVO ) throws KANException
   {
      return selectList( "getOptionHeaderVOsByCondition", optionHeaderVO );
   }

   @Override
   public List< Object > getOptionHeaderVOsByCondition( final OptionHeaderVO optionHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOptionHeaderVOsByCondition", optionHeaderVO, rowBounds );
   }

   @Override
   public OptionHeaderVO getOptionHeaderVOByOptionHeaderId( final String optionHeaderId ) throws KANException
   {
      return ( OptionHeaderVO ) select( "getOptionHeaderVOByOptionHeaderId", optionHeaderId );
   }

   @Override
   public int insertOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException
   {
      return insert( "insertOptionHeader", optionHeaderVO );
   }

   @Override
   public int updateOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException
   {
      return update( "updateOptionHeader", optionHeaderVO );
   }

   @Override
   public int deleteOptionHeader( final String optionHeaderId ) throws KANException
   {
      return delete( "deleteOptionHeader", optionHeaderId );
   }

   @Override
   public List< Object > getOptionHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getOptionHeaderVOsByAccountId", accountId );
   }

}
