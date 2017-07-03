package com.kan.hro.dao.mybatis.impl.biz.cb;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.cb.CBBillDao;
import com.kan.hro.domain.biz.cb.CBBillVO;

public class CBBillDaoImpl extends Context implements CBBillDao
{

   @Override
   public int countCBBillVOsByCondition( final CBBillVO cbBillVO ) throws KANException
   {
      return ( Integer ) select( "countCBBillVOsByCondition", cbBillVO );
   }

   @Override
   public List< Object > getCBBillVOsByCondition( final CBBillVO cbBillVO ) throws KANException
   {
      return selectList( "getCBBillVOsByCondition", cbBillVO );
   }

   @Override
   public List< Object > getCBBillVOsByCondition( final CBBillVO cbBillVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getCBBillVOsByCondition", cbBillVO, rowBounds );
   }

   @Override
   public List< Object > getCBBillDetailByHeaderIds( final List< String > list ) throws KANException
   {
      return list.size() == 0 ? new ArrayList< Object >() :selectList( "getCBBillDetailByHeaderIds", list.toArray() );
   }
}
