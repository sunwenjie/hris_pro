package com.kan.hro.domain.biz.settlement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.util.KANUtil;

/**
  * 类名称：SettlementDTO  
  * 类描述：用于结算 - Order Go批次生成对象实例
  * 创建人：Kevin  
  * 创建时间：2013-9-11  
  */
public class SettlementTempDTO implements Serializable
{

   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 7046362359416158893L;

   // ServiceContractTempVO
   private ServiceContractTempVO serviceContractTempVO;

   // OrderDetailTempVO List
   private List< OrderDetailTempVO > orderDetailTempVOs = new ArrayList< OrderDetailTempVO >();

   public ServiceContractTempVO getServiceContractTempVO()
   {
      return serviceContractTempVO;
   }

   public void setServiceContractTempVO( ServiceContractTempVO serviceContractTempVO )
   {
      this.serviceContractTempVO = serviceContractTempVO;
   }

   public List< OrderDetailTempVO > getOrderDetailTempVOs()
   {
      return orderDetailTempVOs;
   }

   public void setOrderDetailTempVOs( List< OrderDetailTempVO > orderDetailTempVOs )
   {
      this.orderDetailTempVOs = orderDetailTempVOs;
   }

   public double getBillAmountCompany()
   {
      return getAmount( "BC" );
   }

   public double getBillAmountPersonal()
   {
      return getAmount( "BP" );
   }

   public double getCostAmountCompany()
   {
      return getAmount( "CC" );
   }

   public double getCostAmountPersonal()
   {
      return getAmount( "CP" );
   }

   private double getAmount( final String flag )
   {
      double amount = 0;

      if ( flag != null && this.getOrderDetailTempVOs() != null && this.getOrderDetailTempVOs().size() > 0 )
      {
         for ( OrderDetailTempVO orderDetailTempVO : this.getOrderDetailTempVOs() )
         {
            if ( flag.equals( "BC" ) && KANUtil.filterEmpty( orderDetailTempVO.getBillAmountCompany() ) != null )
            {
               amount = amount + Double.valueOf( orderDetailTempVO.getBillAmountCompany() );
            }
            else if ( flag.equals( "BP" ) && KANUtil.filterEmpty( orderDetailTempVO.getBillAmountPersonal() ) != null )
            {
               amount = amount + Double.valueOf( orderDetailTempVO.getBillAmountPersonal() );
            }
            else if ( flag.equals( "CC" ) && KANUtil.filterEmpty( orderDetailTempVO.getCostAmountCompany() ) != null )
            {
               amount = amount + Double.valueOf( orderDetailTempVO.getCostAmountCompany() );
            }
            else if ( flag.equals( "CP" ) && KANUtil.filterEmpty( orderDetailTempVO.getCostAmountPersonal() ) != null )
            {
               amount = amount + Double.valueOf( orderDetailTempVO.getCostAmountPersonal() );
            }
         }
      }

      return amount;
   }

}
