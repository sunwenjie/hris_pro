package com.kan.hro.domain.biz.employee;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANUtil;

public class EmployeeSalaryAdjustmentTempVO extends EmployeeSalaryAdjustmentVO
{

   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 1310421858372761561L;

   // ����ID
   private String batchId;

   /**
    * For Application
    */
   // �������ID
   @JsonIgnore
   private String orderId;

   // ��Ҫ�½�н�귽��
   @JsonIgnore
   private boolean needNewSalarySolution;

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public boolean getNeedNewSalarySolution()
   {
      return needNewSalarySolution;
   }

   public void setNeedNewSalarySolution( boolean needNewSalarySolution )
   {
      this.needNewSalarySolution = needNewSalarySolution;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   @Override
   public List< MappingVO > getStatuses()
   {
      return KANUtil.getMappings( super.getLocale(), "def.common.batch.status" );
   }

   @Override
   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), getStatuses() );
   }
}
