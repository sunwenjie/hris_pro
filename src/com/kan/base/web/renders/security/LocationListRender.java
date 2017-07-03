package com.kan.base.web.renders.security;

import java.util.Locale;

import com.kan.base.domain.security.LocationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public class LocationListRender
{

   public static String getLocationList( final Locale locale, final PagedListHolder locationHolder ) throws KANException
   {
      
      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + locationHolder.getCurrentSortClass( "nameZH" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listlocation_form', null, null, 'nameZH', '" + locationHolder.getNextSortOrder( "nameZH" ) + "', 'tableWrapper');\">中文名称</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + locationHolder.getCurrentSortClass( "nameEN" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listlocation_form', null, null, 'nameEN', '" + locationHolder.getNextSortOrder( "nameEN" ) + "', 'tableWrapper');\">英文名称</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + locationHolder.getCurrentSortClass( "cityId" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listlocation_form', null, null, 'cityId', '" + locationHolder.getNextSortOrder( "cityId" ) + "', 'tableWrapper');\">城市</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + locationHolder.getCurrentSortClass( "telephone" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listlocation_form', null, null, 'telephone', '" + locationHolder.getNextSortOrder( "telephone" ) + "', 'tableWrapper');\">电话</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + locationHolder.getCurrentSortClass( "fax" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listlocation_form', null, null, 'fax', '" + locationHolder.getNextSortOrder( "fax" ) + "', 'tableWrapper');\">传真</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + locationHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listlocation_form', null, null, 'status', '" + locationHolder.getNextSortOrder( "status" ) + "', 'tableWrapper');\">状态</a>" );
      rs.append( "</th>" );
      rs.append( "</thead>" );
      if ( locationHolder != null && locationHolder.getHolderSize() != 0 )
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < locationHolder.getSource().size(); number++ )
         {
            LocationVO locationVO = ( LocationVO ) locationHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );

            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + locationVO.getLocationId() + "\" name=\"chkSelectRow[]\" value=\""
                  + ( locationVO.getLocationId() ) + "\"" + "/>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('secLocationAction.do?proc=to_objectModify&locationId=" + locationVO.getEncodedId() + "');\">" + locationVO.getNameZH()
                  + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('secLocationAction.do?proc=to_objectModify&locationId=" + locationVO.getEncodedId() + "');\">" + locationVO.getNameEN()
                  + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + locationVO.getDecodeCity() + "</td>" );
            rs.append( "<td class=\"left\">" + locationVO.getTelephone() + "</td>" );
            rs.append( "<td class=\"left\">" + locationVO.getFax() + "</td>" );
            rs.append( "<td class=\"left\">" + locationVO.getDecodeStatus() + "</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( locationHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td colspan=\"7\" class=\"left\">" );
         rs.append( "<label>&nbsp;总共： " + locationHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;当前：" + locationHolder.getIndexStart() + " - " + locationHolder.getIndexEnd() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listlocation_form', null, '" + locationHolder.getFirstPage()
               + "', null, null, 'tableWrapper');\">首页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listlocation_form', null, '" + locationHolder.getPreviousPage()
               + "', null, null, 'tableWrapper');\">上页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listlocation_form', null, '" + locationHolder.getNextPage()
               + "', null, null, 'tableWrapper');\">下页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listlocation_form', null, '" + locationHolder.getLastPage()
               + "', null, null, 'tableWrapper');\">末页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;页数：" + locationHolder.getRealPage() + "/" + locationHolder.getPageCount() + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );
      }
      rs.append( "</table>" );

      return rs.toString();
   }
}
