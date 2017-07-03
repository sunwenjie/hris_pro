package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class TaxTemplateDTO implements Serializable
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = 324460864779677420L;

   // ��˰ģ�������� TaxTemplateHeaderVO
   private TaxTemplateHeaderVO taxTemplateHeaderVO = new TaxTemplateHeaderVO();

   // ��˰ģ��Ӷ��� TaxTemplateDetailVO List
   private List< TaxTemplateDetailVO > taxTemplateDetailVOs = new ArrayList< TaxTemplateDetailVO >();

   public TaxTemplateHeaderVO getTaxTemplateHeaderVO()
   {
      return taxTemplateHeaderVO;
   }

   public void setTaxTemplateHeaderVO( TaxTemplateHeaderVO taxTemplateHeaderVO )
   {
      this.taxTemplateHeaderVO = taxTemplateHeaderVO;
   }

   public List< TaxTemplateDetailVO > getTaxTemplateDetailVOs()
   {
      return taxTemplateDetailVOs;
   }

   public void setTaxTemplateDetailVOs( List< TaxTemplateDetailVO > taxTemplateDetailVOs )
   {
      this.taxTemplateDetailVOs = taxTemplateDetailVOs;
   }

   // ��ȡģ������
   public String getTaxTemplateName( final HttpServletRequest request )
   {
      if ( taxTemplateHeaderVO != null )
      {
         if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return taxTemplateHeaderVO.getNameZH();
         }
         else
         {
            return taxTemplateHeaderVO.getNameEN();
         }
      }

      return "";
   }

}
