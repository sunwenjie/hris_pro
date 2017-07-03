package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialBenefitSolutionDTO implements Serializable
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = -2753130394100680220L;

   // 商保方案主表
   private CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO;
   // 商保方案从表集合
   private List< CommercialBenefitSolutionDetailVO > commercialBenefitSolutionDetailVOs = new ArrayList< CommercialBenefitSolutionDetailVO >();

   public CommercialBenefitSolutionHeaderVO getCommercialBenefitSolutionHeaderVO()
   {
      return commercialBenefitSolutionHeaderVO;
   }

   public void setCommercialBenefitSolutionHeaderVO( CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO )
   {
      this.commercialBenefitSolutionHeaderVO = commercialBenefitSolutionHeaderVO;
   }

   public List< CommercialBenefitSolutionDetailVO > getCommercialBenefitSolutionDetailVOs()
   {
      return commercialBenefitSolutionDetailVOs;
   }

   public void setCommercialBenefitSolutionDetailVOs( List< CommercialBenefitSolutionDetailVO > commercialBenefitSolutionDetailVOs )
   {
      this.commercialBenefitSolutionDetailVOs = commercialBenefitSolutionDetailVOs;
   }

}
