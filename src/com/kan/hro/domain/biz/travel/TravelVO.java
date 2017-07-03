package com.kan.hro.domain.biz.travel;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class TravelVO extends BaseVO
{

   private static final long serialVersionUID = -7544867851628818310L;

   // 差旅Id
   private String travelId;
   
   // 雇员Id
   private String employeeId;
   
   // 个人描述
   private String description;

   // 中文名
   private String nameZH;

   // 英文名
   private String nameEN;

   // 英文名
   private String startDate;
   
   // 英文名
   private String endDate;
  

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( travelId );
   }

   
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }
   @Override
   public void reset() throws KANException
   {
      this.travelId = "";
      this.employeeId = "";
      this.startDate = "";
      this.endDate = "";
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      super.setStatus( "0" );

   }

   @Override
   public void update( Object object ) throws KANException
   {
      final TravelVO travelVO = ( TravelVO ) object;
      this.employeeId = travelVO.getEmployeeId();
      this.nameZH = travelVO.getNameZH();
      this.nameEN = travelVO.getNameEN();
      this.description = travelVO.getDescription();

   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.travel.statuses" ) );
    
   }
   
   
   public String getTravelId()
   {
      return travelId;
   }

   public void setTravelId( String travelId )
   {
      this.travelId = travelId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getStartDate()
   {
      return startDate;
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return endDate;
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

}
