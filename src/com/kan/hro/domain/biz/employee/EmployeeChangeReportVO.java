package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeChangeReportVO extends LogVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -8048010504304511790L;

   private String employeeId;

   private String changeType;

   private String changeContent;

   private List< MappingVO > changeTypes = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.changeTypes = KANUtil.getMappings( super.getLocale(), "business.employee.change.report.changeType" );
   }

   public List< MappingVO > getChangeTypes()
   {
      return changeTypes;
   }

   public void setChangeTypes( List< MappingVO > changeTypes )
   {
      this.changeTypes = changeTypes;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getChangeType()
   {
      return changeType;
   }

   public void setChangeType( String changeType )
   {
      this.changeType = changeType;
   }

   public String getChangeContent()
   {
      return changeContent;
   }

   public void setChangeContent( String changeContent )
   {
      this.changeContent = changeContent;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update( Object object ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   public String getDecodeChangeType()
   {
      return decodeField( changeType, changeTypes );
   }

   public String getExcelChangeContent()
   {
      if ( changeContent != null && !"".equals( changeContent.trim() ) )
      {
         return changeContent.replaceAll( "<br/>", "\n" );
      }
      return "";
   }
}
