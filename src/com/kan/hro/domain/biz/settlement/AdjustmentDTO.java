package com.kan.hro.domain.biz.settlement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class AdjustmentDTO implements Serializable
{
   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 2404037808881018293L;

   // Settlement Adjustment Header
   private AdjustmentHeaderVO adjustmentHeaderVO;

   // ClientOrderHeaderVO
   private ClientOrderHeaderVO clientOrderHeaderVO;

   // EmployeeContractVO
   private EmployeeContractVO employeeContractVO;

   // Settlement Adjustment Detail
   private List< AdjustmentDetailVO > adjustmentDetailVOs = new ArrayList< AdjustmentDetailVO >();

   public final AdjustmentHeaderVO getAdjustmentHeaderVO()
   {
      return adjustmentHeaderVO;
   }

   public final void setAdjustmentHeaderVO( AdjustmentHeaderVO adjustmentHeaderVO )
   {
      this.adjustmentHeaderVO = adjustmentHeaderVO;
   }

   public final ClientOrderHeaderVO getClientOrderHeaderVO()
   {
      return clientOrderHeaderVO;
   }

   public final void setClientOrderHeaderVO( ClientOrderHeaderVO clientOrderHeaderVO )
   {
      this.clientOrderHeaderVO = clientOrderHeaderVO;
   }

   public final EmployeeContractVO getEmployeeContractVO()
   {
      return employeeContractVO;
   }

   public final void setEmployeeContractVO( EmployeeContractVO employeeContractVO )
   {
      this.employeeContractVO = employeeContractVO;
   }

   public final List< AdjustmentDetailVO > getAdjustmentDetailVOs()
   {
      return adjustmentDetailVOs;
   }

   public final void setAdjustmentDetailVOs( List< AdjustmentDetailVO > adjustmentDetailVOs )
   {
      this.adjustmentDetailVOs = adjustmentDetailVOs;
   }

}
