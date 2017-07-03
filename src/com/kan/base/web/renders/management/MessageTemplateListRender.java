package com.kan.base.web.renders.management;

import java.util.Locale;

import com.kan.base.domain.message.MessageTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class MessageTemplateListRender
{
   public static String getMessageTemplateList( final Locale locale, final PagedListHolder messageTemplateHolder ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 20%\" class=\"header " + messageTemplateHolder.getCurrentSortClass( "nameZH" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listmessageTemplate_form', null, null, 'nameZH', '" + messageTemplateHolder.getNextSortOrder( "nameZH" )
            + "', 'tableWrapper');\">模板名（中文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 18%\" class=\"header " + messageTemplateHolder.getCurrentSortClass( "nameEN" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listmessageTemplate_form', null, null, 'nameEN', '" + messageTemplateHolder.getNextSortOrder( "nameEN" )
            + "', 'tableWrapper');\">模板名（英文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 16%\" class=\"header " + messageTemplateHolder.getCurrentSortClass( "templateType" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listmessageTemplate_form', null, null, 'templateType', '" + messageTemplateHolder.getNextSortOrder( "templateType" )
            + "', 'tableWrapper');\">模板类型</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 20%\" class=\"header " + messageTemplateHolder.getCurrentSortClass( "contentType" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listmessageTemplate_form', null, null, 'contentType', '" + messageTemplateHolder.getNextSortOrder( "contentType" )
            + "', 'tableWrapper');\">模板内容类型</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 26%\" class=\"header " + messageTemplateHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listmessageTemplate_form', null, null, 'status', '" + messageTemplateHolder.getNextSortOrder( "status" )
            + "', 'tableWrapper');\">状态</a>" );
      rs.append( "</th>" );
      rs.append( "</tr>" );
      rs.append( "</thead>" );
      if ( messageTemplateHolder != null && messageTemplateHolder.getHolderSize() != 0 )
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < messageTemplateHolder.getSource().size(); number++ )
         {
            MessageTemplateVO messageTemplateVO = ( MessageTemplateVO ) messageTemplateHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );
            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + messageTemplateVO.getTemplateId() + "\" name=\"chkSelectRow[]\" value=\""
                  + ( messageTemplateVO.getAccountId().trim().equals( "0" ) ? "" : messageTemplateVO.getAccountId() ) + "\" "
                  + ( messageTemplateVO.getAccountId().trim().equals( "0" ) ? " disabled=\"disabled\"" : "" ) + "/>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" );
            rs.append( "<a href=\"#\" onclick=\"link('messageTemplateAction.do?proc=to_objectModify&messageTemplateId=" + messageTemplateVO.getEncodedId() + "');\">"
                  + messageTemplateVO.getNameZH() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" );
            rs.append( "<a href=\"#\" onclick=\"link('messageTemplateAction.do?proc=to_objectModify&messageTemplateId=" + messageTemplateVO.getEncodedId() + "');\">"
                  + messageTemplateVO.getNameEN() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + messageTemplateVO.getDecodeTemplateType() + "</td>" );
            rs.append( "<td class=\"left\">" + messageTemplateVO.getDecodeContentType() + "</td>" );
            rs.append( "<td class=\"left\">" + messageTemplateVO.getDecodeStatus() + "</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( messageTemplateHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td></td>" );
         rs.append( "<td colspan=\"7\" class=\"right\">" );
         rs.append( "<label>&nbsp;" + KANUtil.getProperty( locale, "page.total" ) + "： " + messageTemplateHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;" + KANUtil.getProperty( locale, "page.current" ) + "：" + messageTemplateHolder.getIndexStart() + " - "
               + messageTemplateHolder.getIndexEnd() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listmessageTemplate_form', null, '" + messageTemplateHolder.getFirstPage()
               + "', null, null, 'tableWrapper');\">" + KANUtil.getProperty( locale, "page.first" ) + "</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listmessageTemplate_form', null, '" + messageTemplateHolder.getPreviousPage()
               + "', null, null, 'tableWrapper');\">" + KANUtil.getProperty( locale, "page.previous" ) + "</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listmessageTemplate_form', null, '" + messageTemplateHolder.getNextPage()
               + "', null, null, 'tableWrapper');\">" + KANUtil.getProperty( locale, "page.next" ) + "</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listmessageTemplate_form', null, '" + messageTemplateHolder.getLastPage()
               + "', null, null, 'tableWrapper');\">" + KANUtil.getProperty( locale, "page.last" ) + "</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;" + KANUtil.getProperty( locale, "page.pagination" ) + "：" + messageTemplateHolder.getRealPage() + "/"
               + messageTemplateHolder.getPageCount() + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );
      }
      rs.append( "</table>" );

      return rs.toString();
   }
}
