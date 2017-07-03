package com.kan.base.web.renders.security;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class BranchListRender
{

   public static String getBranchList( final Locale locale, final PagedListHolder branchHolder ) throws KANException
   {

      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + branchHolder.getCurrentSortClass( "branchCode" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listbranch_form', null, null, 'branchCode', '" + branchHolder.getNextSortOrder( "branchCode" )
            + "', 'tableWrapper');\">部门编号</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + branchHolder.getCurrentSortClass( "nameZH" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listbranch_form', null, null, 'nameZH', '" + branchHolder.getNextSortOrder( "nameZH" ) + "', 'tableWrapper');\">部门名称（中文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + branchHolder.getCurrentSortClass( "nameEN" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listbranch_form', null, null, 'nameEN', '" + branchHolder.getNextSortOrder( "nameEN" ) + "', 'tableWrapper');\">部门名称（英文）</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + branchHolder.getCurrentSortClass( "description" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listbranch_form', null, null, 'description', '" + branchHolder.getNextSortOrder( "description" )
            + "', 'tableWrapper');\">部门描述</a>" );
      rs.append( "</th>" );
      rs.append( "<th class=\"header " + branchHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listbranch_form', null, null, 'status', '" + branchHolder.getNextSortOrder( "status" ) + "', 'tableWrapper');\">状态</a>" );
      rs.append( "</th>" );
      rs.append( "</thead>" );
      if ( branchHolder != null && branchHolder.getHolderSize() != 0 )
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < branchHolder.getSource().size(); number++ )
         {
            BranchVO branchVO = ( BranchVO ) branchHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );
            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + branchVO.getBranchId() + "\" name=\"chkSelectRow[]\" value=\"" + ( branchVO.getBranchId() )
                  + "\"" + "/>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('branchAction.do?proc=to_objectModify&branchId=" + branchVO.getEncodedId() + "');\">" + branchVO.getBranchCode() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('branchAction.do?proc=to_objectModify&branchId=" + branchVO.getEncodedId() + "');\">" + branchVO.getNameZH() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('branchAction.do?proc=to_objectModify&branchId=" + branchVO.getEncodedId() + "');\">" + branchVO.getNameEN() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + branchVO.getDescription() + "</td>" );
            rs.append( "<td class=\"left\">" + branchVO.getDecodeStatus() + "</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( branchHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td colspan=\"6\" class=\"left\">" );
         rs.append( "<label>&nbsp;总共： " + branchHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;当前：" + branchHolder.getIndexStart() + " - " + branchHolder.getIndexEnd() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listbranch_form', null, '" + branchHolder.getFirstPage()
               + "', null, null, 'tableWrapper');\">首页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listbranch_form', null, '" + branchHolder.getPreviousPage()
               + "', null, null, 'tableWrapper');\">上页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listbranch_form', null, '" + branchHolder.getNextPage()
               + "', null, null, 'tableWrapper');\">下页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listbranch_form', null, '" + branchHolder.getLastPage()
               + "', null, null, 'tableWrapper');\">末页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;页数：" + branchHolder.getRealPage() + "/" + branchHolder.getPageCount() + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );
      }
      rs.append( "</table>" );

      return rs.toString();
   }

   public static String getBaseBranchHtml( final HttpServletRequest request ) throws KANException
   {
      List< BranchDTO > branchDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).BRANCH_DTO;
      return "<li>" + KANUtil.getProperty( request.getLocale(), "messgae.click.branch.name" ) + "</li>" + getBaseBranchHtml( request, branchDTOs );
   }

   // 生成Branch的列表
   public static String getBaseBranchHtml( final HttpServletRequest request, List< BranchDTO > branchDTOs ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      final String localeLanguage = request.getLocale().getLanguage();
      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         for ( BranchDTO branchDTO : branchDTOs )
         {
            String branchName = "";
            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               branchName = branchDTO.getBranchVO().getNameZH();
            }
            else
            {
               branchName = branchDTO.getBranchVO().getNameEN();
            }
            branchDTO.getBranchVO().getBranchId();
            rs.append( "<li style=\"width:33%;float:left;margin-top:10px\" >" );
            rs.append( "<a style=\"cursor:pointer\" onclick=\"addContactBranch('" + branchDTO.getBranchVO().getBranchId() + "','" + branchName + "'); \"><span>" + branchName
                  + "</span></a>" );
            rs.append( "</li>" );
            if ( branchDTO.getBranchDTOs() != null && branchDTO.getBranchDTOs().size() > 0 )
            {
               rs.append( getBaseBranchHtml( request, branchDTO.getBranchDTOs() ) );
            }
         }

      }

      return rs.toString();
   }

}
