package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class BankTemplateDTO implements Serializable
{

   /**  
   * Serial Version UID
   */

   private static final long serialVersionUID = -7540714260259512977L;

   // ����ģ�������� - BankTemplateHeaderVO
   private BankTemplateHeaderVO bankTemplateHeaderVO = new BankTemplateHeaderVO();

   // ����ģ��Ӷ��� - BankTemplateDetailVO
   private List< BankTemplateDetailVO > bankTemplateDetailVOs = new ArrayList< BankTemplateDetailVO >();

   public BankTemplateHeaderVO getBankTemplateHeaderVO()
   {
      return bankTemplateHeaderVO;
   }

   public void setBankTemplateHeaderVO( BankTemplateHeaderVO bankTemplateHeaderVO )
   {
      this.bankTemplateHeaderVO = bankTemplateHeaderVO;
   }

   public List< BankTemplateDetailVO > getBankTemplateDetailVOs()
   {
      return bankTemplateDetailVOs;
   }

   public void setBankTemplateDetailVOs( List< BankTemplateDetailVO > bankTemplateDetailVOs )
   {
      this.bankTemplateDetailVOs = bankTemplateDetailVOs;
   }

   // ��ȡģ������
   public String getBankTemplateName( final HttpServletRequest request )
   {
      if ( bankTemplateHeaderVO != null )
      {
         if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return bankTemplateHeaderVO.getNameZH();
         }
         else
         {
            return bankTemplateHeaderVO.getNameEN();
         }
      }

      return "";
   }

}
