package com.kan.hro.domain.biz.settlement;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kan.hro.domain.biz.sb.SpecialDTO;

public class OrderDTO implements Serializable, SpecialDTO< Object, List< ? > >
{

   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 10463623594538893L;

   private OrderHeaderVO orderHeaderVO;

   private List< ServiceContractVO > serviceContractVOs = new ArrayList< ServiceContractVO >();

   public OrderHeaderVO getOrderHeaderVO()
   {
      return orderHeaderVO;
   }

   public void setOrderHeaderVO( OrderHeaderVO orderHeaderVO )
   {
      this.orderHeaderVO = orderHeaderVO;
   }

   public List< ServiceContractVO > getServiceContractVOs()
   {
      return serviceContractVOs;
   }

   public void setServiceContractVOs( List< ServiceContractVO > serviceContractVOs )
   {
      this.serviceContractVOs = serviceContractVOs;
   }

   @Override
   public Object getHeaderVO()
   {
      return orderHeaderVO;
   }

   @Override
   public List< ? > getDetailVOs()
   {
      return serviceContractVOs;
   }

   @Override
   public Map< ?, ? > getFlags()
   {
      return null;
   }

   public void calAmountCompany()
   {
      Double billSum = 0d;
      Double costSum = 0d;
      for ( ServiceContractVO serviceContractVO : serviceContractVOs )
      {
         billSum += Double.parseDouble( serviceContractVO.getBillAmountCompany() );
         costSum += Double.parseDouble( serviceContractVO.getCostAmountCompany() );
      }
      DecimalFormat df = new DecimalFormat( "##0.00" );
      orderHeaderVO.setBillAmountCompany( df.format( billSum ) );
      orderHeaderVO.setCostAmountCompany( df.format( costSum ) );
   }

}
