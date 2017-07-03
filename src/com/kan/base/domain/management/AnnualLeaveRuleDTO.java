package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnnualLeaveRuleDTO implements Serializable
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = -6856700080446565638L;

   // ��ٹ��� - ����
   private AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO;

   // ��ٹ��� - �ӱ�
   private List< AnnualLeaveRuleDetailVO > annualLeaveRuleDetailVOs = new ArrayList< AnnualLeaveRuleDetailVO >();

   public AnnualLeaveRuleHeaderVO getAnnualLeaveRuleHeaderVO()
   {
      return annualLeaveRuleHeaderVO;
   }

   public void setAnnualLeaveRuleHeaderVO( AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO )
   {
      this.annualLeaveRuleHeaderVO = annualLeaveRuleHeaderVO;
   }

   public List< AnnualLeaveRuleDetailVO > getAnnualLeaveRuleDetailVOs()
   {
      return annualLeaveRuleDetailVOs;
   }

   public void setAnnualLeaveRuleDetailVOs( List< AnnualLeaveRuleDetailVO > annualLeaveRuleDetailVOs )
   {
      this.annualLeaveRuleDetailVOs = annualLeaveRuleDetailVOs;
   }

}
