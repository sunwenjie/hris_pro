package com.kan.hro.domain.biz.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANUtil;

public class EmployeeContractTempVO extends EmployeeContractVO
{
   // Serial Version UID
   private static final long serialVersionUID = 51444555545L;

   private String batchId;

   private String tempStatus;

   private List< MappingVO > tempStatuses;

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.tempStatuses = KANUtil.getMappings( request.getLocale(), "def.common.batch.status" );
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getTempStatus()
   {
      return tempStatus;
   }

   public void setTempStatus( String tempStatus )
   {
      this.tempStatus = tempStatus;
   }

   public List< MappingVO > getTempStatuses()
   {
      return tempStatuses;
   }

   public void setTempStatuses( List< MappingVO > tempStatuses )
   {
      this.tempStatuses = tempStatuses;
   }

   public String getDecodeTempStatus()
   {
      return decodeField( this.tempStatus, this.tempStatuses );
   }

}
