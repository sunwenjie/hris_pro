package com.kan.base.domain.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class AnnualLeaveRuleHeaderVO extends BaseVO
{

   /**  
   * SerialVersionUID
   */
   private static final long serialVersionUID = 3400950446627155272L;

   // For DB
   // 年假规则主表ID
   private String headerId;

   // 年假规则中文名
   private String nameZH;

   // 年假规则英文名
   private String nameEN;

   // 折算方式
   private String divideType;

   // 规则基于
   private String baseOn;

   // 描述
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // 基于列表
   private List< MappingVO > baseOns = new ArrayList< MappingVO >();
   @JsonIgnore
   // 折算方式
   private List< MappingVO > divideTypes = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.baseOn = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.baseOns = KANUtil.getMappings( request.getLocale(), "sickleavesalarydetail.baseon.type" );
      this.divideTypes = KANUtil.getMappings( request.getLocale(), "annualleaveheader.divideType" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = ( AnnualLeaveRuleHeaderVO ) object;
      this.nameZH = annualLeaveRuleHeaderVO.getNameZH();
      this.nameEN = annualLeaveRuleHeaderVO.getNameEN();
      this.baseOn = annualLeaveRuleHeaderVO.getBaseOn();
      this.divideType = annualLeaveRuleHeaderVO.getDivideType();
      this.description = annualLeaveRuleHeaderVO.getDescription();
      super.setStatus( annualLeaveRuleHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
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

   public String getBaseOn()
   {
      return baseOn;
   }

   public void setBaseOn( String baseOn )
   {
      this.baseOn = baseOn;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getDecodebaseOn()
   {
      return decodeField( baseOn, baseOns );
   }

   public List< MappingVO > getBaseOns()
   {
      return baseOns;
   }

   public void setBaseOns( List< MappingVO > baseOns )
   {
      this.baseOns = baseOns;
   }

   public String getDivideType()
   {
      return divideType;
   }

   public void setDivideType( String divideType )
   {
      this.divideType = divideType;
   }

   public List< MappingVO > getDivideTypes()
   {
      return divideTypes;
   }

   public void setDivideTypes( List< MappingVO > divideTypes )
   {
      this.divideTypes = divideTypes;
   }

}
