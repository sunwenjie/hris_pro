package com.kan.hro.domain.biz.performance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class BudgetSettingDetailVO extends BaseVO
{

   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = -4966358413892925865L;

   /***
    * For db
    */
   private String detailId;
   private String headerId;
   private String parentBranchId;
   private String ttc;
   private String bonus;

   @JsonIgnore
   private List< MappingVO > depts = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.depts = KANConstants.getKANAccountConstants( getAccountId() ).getBUFunction();
      this.depts.add( 0, super.getEmptyMappingVO() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.parentBranchId = "0";
      this.ttc = "";
      this.bonus = "";
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final BudgetSettingDetailVO budgetSettingDetailVO = ( BudgetSettingDetailVO ) object;
      this.parentBranchId = budgetSettingDetailVO.getParentBranchId();
      this.ttc = budgetSettingDetailVO.getTtc();
      this.bonus = budgetSettingDetailVO.getBonus();
      super.setStatus( budgetSettingDetailVO.getStatus() );
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getParentBranchId()
   {
      return parentBranchId;
   }

   public void setParentBranchId( String parentBranchId )
   {
      this.parentBranchId = parentBranchId;
   }

   public String getTtc()
   {
      return formatNumber_2( ttc );
   }

   public void setTtc( String ttc )
   {
      this.ttc = ttc;
   }

   public String getBonus()
   {
      return formatNumber_2( bonus );
   }

   public void setBonus( String bonus )
   {
      this.bonus = bonus;
   }

   public List< MappingVO > getDepts()
   {
      return depts;
   }

   public void setDepts( List< MappingVO > depts )
   {
      this.depts = depts;
   }

   public String getDecodeParentBranchId()
   {
      return decodeField( parentBranchId, depts );
   }

}
