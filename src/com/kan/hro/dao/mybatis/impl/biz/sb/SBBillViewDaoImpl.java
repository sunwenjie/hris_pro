package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBBillViewDao;
import com.kan.hro.domain.biz.sb.SBBillDetailView;

public class SBBillViewDaoImpl extends Context implements SBBillViewDao
{

   @Override
   public List< Object > getSBBillHeaderViewsByCondition( final SBBillDetailView sbBillDetailView ) throws KANException
   {
      return selectList( "getSBBillHeaderViewsByCondition", sbBillDetailView );
   }

   @Override
   public List< Object > getSBBillHeaderViewsByCondition( final SBBillDetailView sbBillDetailView, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBBillHeaderViewsByCondition", sbBillDetailView, rowBounds );
   }

   @Override
   public List< Object > getSBBillDetailViewsByCondition( final SBBillDetailView sbBillDetailView ) throws KANException
   {
      return selectList( "getSBBillDetailViewsByCondition", sbBillDetailView );
   }

   @Override
   public int countSBBillHeaderViewsByCondition( SBBillDetailView sbBillDetailView ) throws KANException
   {
      return ( Integer ) select( "countSBBillHeaderViewsByCondition", sbBillDetailView );
   }

}
