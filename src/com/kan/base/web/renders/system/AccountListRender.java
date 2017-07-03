package com.kan.base.web.renders.system;

import java.util.Locale;

import com.kan.base.domain.system.AccountVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class AccountListRender
{
   public static String getAccountList( final Locale locale, final PagedListHolder accountHolder ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 14%\" class=\"header " + accountHolder.getCurrentSortClass( "nameCN" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listaccount_form', null, null, 'nameCN', '" + accountHolder.getNextSortOrder( "nameCN" ) + "', 'tableWrapper');\">账户名 （中文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 14%\" class=\"header " + accountHolder.getCurrentSortClass( "nameEN" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listaccount_form', null, null, 'nameEN', '" + accountHolder.getNextSortOrder( "nameEN" ) + "', 'tableWrapper');\">账户名 （英文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 24%\" class=\"header " + accountHolder.getCurrentSortClass( "entityName" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listaccount_form', null, null, 'entityName', '" + accountHolder.getNextSortOrder( "entityName" ) + "', 'tableWrapper');\">公司名称</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 9%\" class=\"header " + accountHolder.getCurrentSortClass( "linkman" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listaccount_form', null, null, 'linkman', '" + accountHolder.getNextSortOrder( "linkman" ) + "', 'tableWrapper');\">联系人</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 12%\" class=\"header " + accountHolder.getCurrentSortClass( "bizPhone" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listaccount_form', null, null, 'bizPhone', '" + accountHolder.getNextSortOrder( "bizPhone" ) + "', 'tableWrapper');\">工作电话</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 17%\" class=\"header " + accountHolder.getCurrentSortClass( "bizEmail" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listaccount_form', null, null, 'bizEmail', '" + accountHolder.getNextSortOrder( "bizEmail" ) + "', 'tableWrapper');\">工作邮件</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 10%\" class=\"header " + accountHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a onclick=\"submitForm('listaccount_form', null, null, 'status', '" + accountHolder.getNextSortOrder( "status" ) + "', 'tableWrapper');\">"
            + KANUtil.getProperty( locale, "public.status" ) + "</a>" );
      rs.append( "</th>" );
      rs.append( "</tr>" );
      rs.append( "</thead>" );
      if ( accountHolder != null && accountHolder.getHolderSize() != 0 )
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < accountHolder.getSource().size(); number++ )
         {
            AccountVO accountVO = ( AccountVO ) accountHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );
            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + accountVO.getAccountId() + "\" name=\"chkSelectRow[]\" value=\""
                  + ( accountVO.getAccountId().trim().equals( "1" ) ? "" : accountVO.getAccountId() ) + "\" "
                  + ( accountVO.getAccountId().trim().equals( "1" ) ? " disabled=\"disabled\"" : "" ) + "/>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" );
            rs.append( "<a onclick=\"link('accountAction.do?proc=to_accountModify&accountId=" + accountVO.getEncodedId() + "');\">" + accountVO.getNameCN() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" );
            rs.append( "<a onclick=\"link('accountAction.do?proc=to_accountModify&accountId=" + accountVO.getEncodedId() + "');\">" + accountVO.getNameEN() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + accountVO.getEntityName() + "</td>" );
            rs.append( "<td class=\"left\">" + accountVO.getLinkman() + "</td>" );
            rs.append( "<td class=\"left\">" + accountVO.getBizPhone() + "</td>" );
            rs.append( "<td class=\"left\">" + accountVO.getBizEmail() + "</td>" );
            rs.append( "<td class=\"left\">" + accountVO.getDecodeStatus() + " (" + accountVO.getDecodeInitialized() + ")</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( accountHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td></td>" );
         rs.append( "<td colspan=\"7\" class=\"right\">" );
         rs.append( "<label>&nbsp;" + KANUtil.getProperty( locale, "page.total" ) + "： " + accountHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;" + KANUtil.getProperty( locale, "page.current" ) + "：" + accountHolder.getIndexStart() + " - " + accountHolder.getIndexEnd()
               + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a onclick=\"submitForm('listaccount_form', null, '" + accountHolder.getFirstPage() + "', null, null, 'tableWrapper');\">"
               + KANUtil.getProperty( locale, "page.first" ) + "</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listaccount_form', null, '" + accountHolder.getPreviousPage() + "', null, null, 'tableWrapper');\">"
               + KANUtil.getProperty( locale, "page.previous" ) + "</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listaccount_form', null, '" + accountHolder.getNextPage() + "', null, null, 'tableWrapper');\">"
               + KANUtil.getProperty( locale, "page.next" ) + "</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('listaccount_form', null, '" + accountHolder.getLastPage() + "', null, null, 'tableWrapper');\">"
               + KANUtil.getProperty( locale, "page.last" ) + "</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;" + KANUtil.getProperty( locale, "page.pagination" ) + "：" + accountHolder.getRealPage() + "/" + accountHolder.getPageCount()
               + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );
      }
      rs.append( "</table>" );

      return rs.toString();
   }
}
