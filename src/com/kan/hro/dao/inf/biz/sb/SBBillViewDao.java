package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBBillDetailView;

public interface SBBillViewDao
{
   public abstract List< Object > getSBBillHeaderViewsByCondition( final SBBillDetailView sbBillDetailView ) throws KANException;
   
   public abstract int  countSBBillHeaderViewsByCondition( final SBBillDetailView sbBillDetailView ) throws KANException;

   public abstract List< Object > getSBBillHeaderViewsByCondition( final SBBillDetailView sbBillDetailView, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getSBBillDetailViewsByCondition( final SBBillDetailView sbBillDetailView ) throws KANException;

}
