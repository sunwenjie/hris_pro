package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;

public interface ClientOrderHeaderService
{
   public static String SETTLEMENT_FLAG_SALARY = "1";

   public static String SETTLEMENT_FLAG_SB = "2";

   public static String SETTLEMENT_FLAG_CB = "3";

   public static String SETTLEMENT_FLAG_OTHER = "4";

   public static String SETTLEMENT_FLAG_SERVICE_FEE = "5";

   public abstract PagedListHolder getClientOrderHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract List< Object > getClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract ClientOrderHeaderVO getClientOrderHeaderVOByOrderHeaderId( final String orderHeaderId ) throws KANException;

   public abstract int updateClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract int submitClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract int insertClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract int deleteClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract List< Object > getClientOrderHeaderBaseViewsByClientId( final String clientId ) throws KANException;

   public abstract ClientOrderHeaderDTO getClientOrderHeaderDTOByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

   public abstract List< ClientOrderDTO > getClientOrderDTOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract int copyClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract void updateEmployeeSBBaseBySolution( final String orderId, final String sbSolutionId, final String accountId ) throws KANException;

   public abstract int calculateEmployeeAnnualLeave( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

}
