package com.kan.base.web.renders.management;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class PositionGradeCurrencyRender
{
   public static String getPositionGradeCurrencyRender( final HttpServletRequest request, final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      if ( positionGradeCurrencyVO != null )
      {
         if ( positionGradeCurrencyVO.getEncodedId() != null && !"".equals( positionGradeCurrencyVO.getEncodedId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"currencyId\" name=\"currencyId\" value=\"" + positionGradeCurrencyVO.getEncodedId() + "\"/>" );
         }

         rs.append( "<ol class=\"auto\">" );
         // 货币类型
         rs.append( "<li>" );
         rs.append( "<label>币种名 <em> *</em></label>" );
         rs.append( KANUtil.getSelectHTML( positionGradeCurrencyVO.getCurrencyTypes(), "currencyType", "managePositionGradeCurrency_currencyType", positionGradeCurrencyVO.getCurrencyType(), null, null ) );
         rs.append( "</li>" );

         // 最高薪资
         rs.append( "<li>" );
         rs.append( "<label>最高薪资</label>" );
         rs.append( "<input type=\"text\" value=\"" + positionGradeCurrencyVO.getMaxSalary()
               + "\" name=\"maxSalary\" id=\"maxSalary\" maxlength=\"100\" class=\"managePositionGradeCurrency_maxSalary\" />" );
         rs.append( "</li>" );

         // 最低薪资
         rs.append( "<li>" );
         rs.append( "<label>最低薪资</label>" );
         rs.append( "<input type=\"text\" value=\"" + positionGradeCurrencyVO.getMinSalary()
               + "\" name=\"minSalary\" id=\"minSalary\" maxlength=\"100\" class=\"managePositionGradeCurrency_minSalary\" />" );
         rs.append( "</li>" );

         // 状态
         rs.append( "<li>" );
         rs.append( "<label>状态  <em>*</em></label> " );
         rs.append( KANUtil.getSelectHTML( positionGradeCurrencyVO.getStatuses(), "status", "managePositionGradeCurrency_status", positionGradeCurrencyVO.getStatus(), null, null ) );
         rs.append( "</li>" );

         rs.append( "</ol>" );
      }
      return rs.toString();
   }
}
