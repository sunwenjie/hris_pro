package com.kan.hro.service.inf.biz.cb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.cb.CBBillVO;

public interface CBBillService
{
   public abstract PagedListHolder getCBBillVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SXSSFWorkbook cbBillReport( final List< String > itemIdList, final PagedListHolder pagedListHolder, final CBBillVO cbBillVO, final HttpServletRequest request )
         throws KANException;

   public abstract List< Object > getCBBillDetailByHeaderIds( final List< String > list ) throws KANException;

   public abstract List< Object > getCBBillDetailByHeaderId( final String headerId ) throws KANException;
}
