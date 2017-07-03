package com.kan.base.web.renders.system;

import java.util.Locale;

import com.kan.base.domain.system.CountryVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public class CountryListRender
{
   public static String getCountryList( final Locale locale, final PagedListHolder countryHolder ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 30%\" class=\"header " + countryHolder.getCurrentSortClass( "countryNameZH" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcountry_form', null, null, 'countryNameZH', '" + countryHolder.getNextSortOrder( "countryNameZH" ) + "', 'tableWrapper');\">国家名（中文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 30%\" class=\"header " + countryHolder.getCurrentSortClass( "countryNameEN" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcountry_form', null, null, 'countryNameEN', '" + countryHolder.getNextSortOrder( "countryNameEN" ) + "', 'tableWrapper');\">国家名（英文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 11%\" class=\"header " + countryHolder.getCurrentSortClass( "countryNumber" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcountry_form', null, null, 'countryNumber', '" + countryHolder.getNextSortOrder( "countryNumber" ) + "', 'tableWrapper');\">国家编号</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 11%\" class=\"header " + countryHolder.getCurrentSortClass( "countryCode" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcountry_form', null, null, 'countryCode', '" + countryHolder.getNextSortOrder( "countryCode" ) + "', 'tableWrapper');\">国家编码（简写）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 11%\" class=\"header " + countryHolder.getCurrentSortClass( "countryISO3" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcountry_form', null, null, 'countryISO3', '" + countryHolder.getNextSortOrder( "countryISO3" ) + "', 'tableWrapper');\">国家编码（ISO3）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 7%\" class=\"header " + countryHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcountry_form', null, null, 'status', '" + countryHolder.getNextSortOrder( "status" ) + "', 'tableWrapper');\">状态</a>" );
      rs.append( "</th>" );
      rs.append( "</thead>" );
      if ( countryHolder != null && countryHolder.getHolderSize() != 0 )         
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < countryHolder.getSource().size(); number++ )
         {
            CountryVO countryVO = ( CountryVO ) countryHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );
            
            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + countryVO.getCountryId() + "\" name=\"chkSelectRow[]\" value=\""
                  + ( countryVO.getCountryId() ) + "\"" + "/>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a onclick=\"link('provinceAction.do?proc=list_object&countryId=" + countryVO.getEncodedId() + "');\">" + countryVO.getCountryNameZH() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a onclick=\"link('provinceAction.do?proc=list_object&countryId=" + countryVO.getEncodedId() + "');\">" + countryVO.getCountryNameEN() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + countryVO.getCountryNumber() + "</td>" );
            rs.append( "<td class=\"left\">" + countryVO.getCountryCode() + "</td>" );
            rs.append( "<td class=\"left\">" + countryVO.getCountryISO3() + "</td>" );
            rs.append( "<td class=\"left\">" + countryVO.getDecodeStatus() + "</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( countryHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td colspan=\"7\" class=\"left\">" );
         rs.append( "<label>&nbsp;总共： " + countryHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;当前：" + countryHolder.getIndexStart() + " - " + countryHolder.getIndexEnd() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a onclick=\"submitForm('listcountry_form', null, '" + countryHolder.getFirstPage()
               + "', null, null, 'tableWrapper');\">首页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listcountry_form', null, '" + countryHolder.getPreviousPage()
               + "', null, null, 'tableWrapper');\">上页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listcountry_form', null, '" + countryHolder.getNextPage()
               + "', null, null, 'tableWrapper');\">下页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listcountry_form', null, '" + countryHolder.getLastPage()
               + "', null, null, 'tableWrapper');\">末页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;页数：" + countryHolder.getRealPage() + "/" + countryHolder.getPageCount() + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );         
       
      }
      rs.append( "</table>" );
      return rs.toString();
   }

}
