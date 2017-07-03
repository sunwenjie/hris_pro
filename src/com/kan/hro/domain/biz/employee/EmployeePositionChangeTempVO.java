package com.kan.hro.domain.biz.employee;

import java.util.List;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANUtil;

public class EmployeePositionChangeTempVO extends EmployeePositionChangeVO
{

   /**
    * 
    */
   private static final long serialVersionUID = -2898107698205160397L;

   // Åú´ÎID
   private String batchId;

   // For app
   private String locationId;

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getLocationId()
   {
      return locationId;
   }

   public void setLocationId( String locationId )
   {
      this.locationId = locationId;
   }

   @Override
   public List< MappingVO > getStatuses()
   {
      return KANUtil.getMappings( super.getLocale(), "def.common.batch.status" );
   }

   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), getStatuses() );
   }

}
