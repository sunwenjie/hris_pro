package com.kan.base.web.renders.security;

import java.util.Locale;

import com.kan.base.domain.security.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public class PositionListRender
{

   public static String getPositionList( final Locale locale, final PagedListHolder positionHolder ) throws KANException
   {
      
      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + positionHolder.getCurrentSortClass( "titleZH" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listposition_form', null, null, 'titleZH', '" + positionHolder.getNextSortOrder( "titleZH" ) + "', 'tableWrapper');\">职位名称（中文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + positionHolder.getCurrentSortClass( "titleEN" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listposition_form', null, null, 'titleEN', '" + positionHolder.getNextSortOrder( "titleEN" ) + "', 'tableWrapper');\">职位名称（英文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + positionHolder.getCurrentSortClass( "description" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listposition_form', null, null, 'description', '" + positionHolder.getNextSortOrder( "description" ) + "', 'tableWrapper');\">职位描述（JD）</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + positionHolder.getCurrentSortClass( "isVacant" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listposition_form', null, null, 'isVacant', '" + positionHolder.getNextSortOrder( "isVacant" ) + "', 'tableWrapper');\">是否是空缺职位</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + positionHolder.getCurrentSortClass( "needPublish" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listposition_form', null, null, 'needPublish', '" + positionHolder.getNextSortOrder( "needPublish" ) + "', 'tableWrapper');\">是否需要发布</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + positionHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listposition_form', null, null, 'status', '" + positionHolder.getNextSortOrder( "status" ) + "', 'tableWrapper');\">状态</a>" );
      rs.append( "</th>" );
      rs.append( "</thead>" );
      if ( positionHolder != null && positionHolder.getHolderSize() != 0 )
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < positionHolder.getSource().size(); number++ )
         {
            PositionVO positionVO = ( PositionVO ) positionHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );
            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + positionVO.getPositionId() + "\" name=\"chkSelectRow[]\" value=\""
                  + ( positionVO.getPositionId() ) + "\"" + "/>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('secPositionAction.do?proc=to_objectModify&PositionId=" + positionVO.getEncodedId() + "');\">" + positionVO.getTitleZH()
                  + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('secPositionAction.do?proc=to_objectModify&PositionId=" + positionVO.getEncodedId() + "');\">" + positionVO.getTitleZH()
                  + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('secPositionAction.do?proc=to_objectModify&PositionId=" + positionVO.getEncodedId() + "');\">" + positionVO.getDescription()
                  + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + positionVO.getDecodeFlag( positionVO.getIsVacant() ) + "</td>" );
            rs.append( "<td class=\"left\">" + positionVO.getDecodeFlag( positionVO.getNeedPublish() ) + "</td>" );
            rs.append( "<td class=\"left\">" + positionVO.getDecodeStatus() + "</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( positionHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td colspan=\"7\" class=\"left\">" );
         rs.append( "<label>&nbsp;总共： " + positionHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;当前：" + positionHolder.getIndexStart() + " - " + positionHolder.getIndexEnd() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listposition_form', null, '" + positionHolder.getFirstPage()
               + "', null, null, 'tableWrapper');\">首页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listposition_form', null, '" + positionHolder.getPreviousPage()
               + "', null, null, 'tableWrapper');\">上页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listposition_form', null, '" + positionHolder.getNextPage()
               + "', null, null, 'tableWrapper');\">下页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listposition_form', null, '" + positionHolder.getLastPage()
               + "', null, null, 'tableWrapper');\">末页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;页数：" + positionHolder.getRealPage() + "/" + positionHolder.getPageCount() + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );
      }
      rs.append( "</table>" );

      return rs.toString();
   }
}
