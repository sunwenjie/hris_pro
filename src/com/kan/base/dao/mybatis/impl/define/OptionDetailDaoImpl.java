package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.OptionDetailDao;
import com.kan.base.domain.define.OptionDetailVO;
import com.kan.base.util.KANException;

public class OptionDetailDaoImpl extends Context implements OptionDetailDao
{

   @Override
   public int countOptionDetailVOsByCondition( final OptionDetailVO optionDetailVO ) throws KANException
   {
      return ( Integer ) select( "countOptionDetailVOsByCondition", optionDetailVO );
   }

   @Override
   public List< Object > getOptionDetailVOsByCondition( final OptionDetailVO optionDetailVO ) throws KANException
   {
      return selectList( "getOptionDetailVOsByCondition", optionDetailVO );
   }

   @Override
   public List< Object > getOptionDetailVOsByCondition( final OptionDetailVO optionDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOptionDetailVOsByCondition", optionDetailVO, rowBounds );
   }

   @Override
   public OptionDetailVO getOptionDetailVOByOptionDetailId( final String optionDetailId ) throws KANException
   {
      return ( OptionDetailVO ) select( "getOptionDetailVOByOptionDetailId", optionDetailId );
   }

   @Override
   public int insertOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException
   {
      return insert( "insertOptionDetail", optionDetailVO );
   }

   @Override
   public int updateOptionDetail( final OptionDetailVO optionDetailVO ) throws KANException
   {
      return update( "updateOptionDetail", optionDetailVO );
   }

   @Override
   public int deleteOptionDetail( final String optionDetailId ) throws KANException
   {
      return delete( "deleteOptionDetail", optionDetailId );
   }

   @Override
   public List< Object > getOptionDetailVOsByOptionHeaderId( final String optionHeaderId ) throws KANException
   {
      return selectList( "getOptionDetailVOsByOptionHeaderId", optionHeaderId );
   }

   @Override
   public String getMaxOptionId( final String optionHeaderId ) throws KANException
   {
      return (String)select( "getMaxOptionId", optionHeaderId );
   }

}
