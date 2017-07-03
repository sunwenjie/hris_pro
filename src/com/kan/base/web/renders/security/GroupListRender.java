package com.kan.base.web.renders.security;

import java.util.Locale;

import com.kan.base.domain.security.GroupVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public class GroupListRender
{

   public static String getGroupList( final Locale locale, final PagedListHolder groupHolder ) throws KANException
   {

      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + groupHolder.getCurrentSortClass( "nameZH" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listgroup_form', null, null, 'nameZH', '" + groupHolder.getNextSortOrder( "nameZH" ) + "', 'tableWrapper');\">职位分组名称（中文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + groupHolder.getCurrentSortClass( "nameEN" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listgroup_form', null, null, 'nameEN', '" + groupHolder.getNextSortOrder( "nameEN" ) + "', 'tableWrapper');\">职位分组名称（英文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + groupHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listgroup_form', null, null, 'status', '" + groupHolder.getNextSortOrder( "status" ) + "', 'tableWrapper');\">状态</a>" );
      rs.append( "</th>" );
      rs.append( "</thead>" );
      if ( groupHolder != null && groupHolder.getHolderSize() != 0 )
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < groupHolder.getSource().size(); number++ )
         {
            GroupVO groupVO = ( GroupVO ) groupHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );
            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + groupVO.getGroupId() + "\" name=\"chkSelectRow[]\" value=\""
                  + ( groupVO.getGroupId() ) + "\"" + "/>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('groupAction.do?proc=to_objectModify&groupId=" + groupVO.getEncodedId() + "');\">" + groupVO.getNameZH()
                  + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('groupAction.do?proc=to_objectModify&groupId=" + groupVO.getEncodedId() + "');\">" + groupVO.getNameEN()
                  + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + groupVO.getDecodeStatus() + "</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( groupHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td colspan=\"4\" class=\"left\">" );
         rs.append( "<label>&nbsp;总共： " + groupHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;当前：" + groupHolder.getIndexStart() + " - " + groupHolder.getIndexEnd() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listgroup_form', null, '" + groupHolder.getFirstPage()
               + "', null, null, 'tableWrapper');\">首页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listgroup_form', null, '" + groupHolder.getPreviousPage()
               + "', null, null, 'tableWrapper');\">上页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listgroup_form', null, '" + groupHolder.getNextPage()
               + "', null, null, 'tableWrapper');\">下页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listgroup_form', null, '" + groupHolder.getLastPage()
               + "', null, null, 'tableWrapper');\">末页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;页数：" + groupHolder.getRealPage() + "/" + groupHolder.getPageCount() + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );
      }
      rs.append( "</table>" );

      return rs.toString();
   }
}
