package com.kan.base.web.renders.system;

import java.util.Locale;

import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public class ProvinceListRender
{
   public static String getProvinceList( final Locale locale, final PagedListHolder provinceHolder ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 45%\" class=\"header " + provinceHolder.getCurrentSortClass( "provinceNameZH" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listprovince_form', null, null, 'provinceNameZH', '" + provinceHolder.getNextSortOrder( "provinceNameZH" )
            + "', 'tableWrapper');\">省份名（中文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 45%\" class=\"header " + provinceHolder.getCurrentSortClass( "provinceNameEN" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listprovince_form', null, null, 'provinceNameEN', '" + provinceHolder.getNextSortOrder( "provinceNameEN" )
            + "', 'tableWrapper');\">省份名（英文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 10%\" class=\"header " + provinceHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listprovince_form', null, null, 'status', '" + provinceHolder.getNextSortOrder( "status" ) + "', 'tableWrapper');\">状态</a>" );
      rs.append( "</th>" );
      rs.append( "</thead>" );
      if ( provinceHolder != null && provinceHolder.getHolderSize() != 0 )
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < provinceHolder.getSource().size(); number++ )
         {
            ProvinceVO provinceVO = ( ProvinceVO ) provinceHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );

            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + provinceVO.getProvinceId() + "\" name=\"chkSelectRow[]\" value=\""
                  + ( provinceVO.getProvinceId() ) + "\"" + "/>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a onclick=\"link('cityAction.do?proc=list_object&provinceId=" + provinceVO.getEncodedId() + "');\">" + provinceVO.getProvinceNameZH() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a onclick=\"link('cityAction.do?proc=list_object&provinceId=" + provinceVO.getEncodedId() + "');\">" + provinceVO.getProvinceNameEN() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + provinceVO.getDecodeStatus() + "</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( provinceHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td colspan=\"8\" class=\"left\">" );
         rs.append( "<label>&nbsp;总共： " + provinceHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;当前：" + provinceHolder.getIndexStart() + " - " + provinceHolder.getIndexEnd() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a onclick=\"submitForm('listprovince_form', null, '" + provinceHolder.getFirstPage()
               + "', null, null, 'tableWrapper');\">首页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listprovince_form', null, '" + provinceHolder.getPreviousPage()
               + "', null, null, 'tableWrapper');\">上页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listprovince_form', null, '" + provinceHolder.getNextPage()
               + "', null, null, 'tableWrapper');\">下页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listprovince_form', null, '" + provinceHolder.getLastPage()
               + "', null, null, 'tableWrapper');\">末页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;页数：" + provinceHolder.getRealPage() + "/" + provinceHolder.getPageCount() + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );

      }
      rs.append( "</table>" );

      return rs.toString();
   }
}
