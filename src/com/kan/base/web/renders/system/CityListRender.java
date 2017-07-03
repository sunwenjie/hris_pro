package com.kan.base.web.renders.system;

import java.util.Locale;

import com.kan.base.domain.system.CityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public class CityListRender
{
   public static String getCityList( final Locale locale, final PagedListHolder cityHolder ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 30%\" class=\"header " + cityHolder.getCurrentSortClass( "cityNameZH" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcity_form', null, null, 'cityNameZH', '" + cityHolder.getNextSortOrder( "cityNameZH" ) + "', 'tableWrapper');\">城市名（中文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 30%\" class=\"header " + cityHolder.getCurrentSortClass( "cityNameEN" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcity_form', null, null, 'cityNameEN', '" + cityHolder.getNextSortOrder( "cityNameEN" ) + "', 'tableWrapper');\">城市名（英文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 15%\" class=\"header " + cityHolder.getCurrentSortClass( "cityCode" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcity_form', null, null, 'cityCode', '" + cityHolder.getNextSortOrder( "cityCode" ) + "', 'tableWrapper');\">城市编号</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 15%\" class=\"header " + cityHolder.getCurrentSortClass( "cityISO3" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcity_form', null, null, 'cityISO3', '" + cityHolder.getNextSortOrder( "cityISO3" ) + "', 'tableWrapper');\">城市编码</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 10%\" class=\"header " + cityHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listcity_form', null, null, 'status', '" + cityHolder.getNextSortOrder( "status" ) + "', 'tableWrapper');\">状态</a>" );
      rs.append( "</th>" );
      rs.append( "</thead>" );
      if ( cityHolder != null && cityHolder.getHolderSize() != 0 )
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < cityHolder.getSource().size(); number++ )
         {
            CityVO cityVO = ( CityVO ) cityHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );

            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + cityVO.getCityId() + "\" name=\"chkSelectRow[]\" value=\"" + ( cityVO.getCityId() ) + "\"" + "/>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a onclick=\"link('cityAction.do?proc=to_objectModify&cityId=" + cityVO.getCityId() + "&provinceId=" + cityVO.getEncodedId() + "');\">"
                  + cityVO.getCityNameZH() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a onclick=\"link('cityAction.do?proc=to_objectModify&cityId=" + cityVO.getCityId() + "&provinceId=" + cityVO.getEncodedId() + "');\">"
                  + cityVO.getCityNameEN() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + cityVO.getCityCode() + "</td>" );
            rs.append( "<td class=\"left\">" + cityVO.getCityISO3() + "</td>" );
            rs.append( "<td class=\"left\">" + cityVO.getDecodeStatus() + "</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( cityHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td colspan=\"8\" class=\"left\">" );
         rs.append( "<label>&nbsp;总共： " + cityHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;当前：" + cityHolder.getIndexStart() + " - " + cityHolder.getIndexEnd() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a onclick=\"submitForm('listcity_form', null, '" + cityHolder.getFirstPage()
               + "', null, null, 'tableWrapper');\">首页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listcity_form', null, '" + cityHolder.getPreviousPage()
               + "', null, null, 'tableWrapper');\">上页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listcity_form', null, '" + cityHolder.getNextPage()
               + "', null, null, 'tableWrapper');\">下页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listcity_form', null, '" + cityHolder.getLastPage()
               + "', null, null, 'tableWrapper');\">末页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;页数：" + cityHolder.getRealPage() + "/" + cityHolder.getPageCount() + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );

      }
      rs.append( "</table>" );

      return rs.toString();
   }
}
