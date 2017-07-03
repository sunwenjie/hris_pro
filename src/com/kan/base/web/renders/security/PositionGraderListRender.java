package com.kan.base.web.renders.security;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class PositionGraderListRender
{

   public static String getPositionGradeList( final Locale locale, final PagedListHolder positionGradeHolder ) throws KANException
   {

      final StringBuffer rs = new StringBuffer();
      rs.append( "<table class=\"table hover\" id=\"resultTable\">" );
      rs.append( "<thead>" );
      rs.append( "<tr>" );
      rs.append( "<th class=\"checkbox-col\">" );
      rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 20%\" class=\"header " + positionGradeHolder.getCurrentSortClass( "gradeNameZH" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listPositionGrade_form', null, null, 'gradeNameZH', '" + positionGradeHolder.getNextSortOrder( "gradeNameZH" )
            + "', 'tableWrapper');\">中文名称</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 20%\" class=\"header " + positionGradeHolder.getCurrentSortClass( "gradeNameEN" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listPositionGrade_form', null, null, 'gradeNameEN', '" + positionGradeHolder.getNextSortOrder( "gradeNameEN" )
            + "', 'tableWrapper');\">英文名称</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 50%\" class=\"header " + positionGradeHolder.getCurrentSortClass( "description" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listPositionGrade_form', null, null, 'description', '" + positionGradeHolder.getNextSortOrder( "description" )
            + "', 'tableWrapper');\">描述</a>" );
      rs.append( "</th>" );
      rs.append( "<th style=\"width: 10%\" class=\"header " + positionGradeHolder.getCurrentSortClass( "status" ) + "\">" );
      rs.append( "<a href=\"#\" onclick=\"submitForm('listPositionGrade_form', null, null, 'status', '" + positionGradeHolder.getNextSortOrder( "status" )
            + "', 'tableWrapper');\">状态</a>" );
      rs.append( "</th>" );
      rs.append( "</tr>" );
      rs.append( "</thead>" );
      if ( positionGradeHolder != null && positionGradeHolder.getHolderSize() != 0 )
      {
         rs.append( "<tbody>" );
         for ( int number = 0; number < positionGradeHolder.getSource().size(); number++ )
         {
            PositionGradeVO positionGradeVO = ( PositionGradeVO ) positionGradeHolder.getSource().get( number );
            rs.append( "<tr class=\"" + ( number % 2 == 1 ? "odd" : "even" ) + "\">" );
            rs.append( "<td>" );
            rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + positionGradeVO.getPositionGradeId() + "\" name=\"chkSelectRow[]\" value=\""
                  + ( positionGradeVO.getPositionGradeId() ) + "\"" + "/>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('secPositionGradeAction.do?proc=to_objectModify&positionGradeId=" + positionGradeVO.getEncodedId() + "');\">"
                  + positionGradeVO.getGradeNameZH() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td>" );
            rs.append( "<a href=\"#\" onclick=\"link('secPositionGradeAction.do?proc=to_objectModify&positionGradeId=" + positionGradeVO.getEncodedId() + "');\">"
                  + positionGradeVO.getGradeNameEN() + "</a>" );
            rs.append( "</td>" );
            rs.append( "<td class=\"left\">" + positionGradeVO.getDescription() + "</td>" );
            rs.append( "<td class=\"left\">" + positionGradeVO.getDecodeStatus() + "</td>" );
            rs.append( "</tr>" );
         }
         rs.append( "</tbody>" );
      }
      if ( positionGradeHolder != null )
      {
         rs.append( "<tfoot>" );
         rs.append( "<tr class=\"total\">" );
         rs.append( "<td colspan=\"5\" class=\"left\">" );
         rs.append( "<label>&nbsp;总共： " + positionGradeHolder.getHolderSize() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;当前：" + positionGradeHolder.getIndexStart() + " - " + positionGradeHolder.getIndexEnd() + "</label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listPositionGrade_form', null, '" + positionGradeHolder.getFirstPage()
               + "', null, null, 'tableWrapper');\">首页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listPositionGrade_form', null, '" + positionGradeHolder.getPreviousPage()
               + "', null, null, 'tableWrapper');\">上页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listPositionGrade_form', null, '" + positionGradeHolder.getNextPage()
               + "', null, null, 'tableWrapper');\">下页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;<a href=\"#\" onclick=\"submitForm('listPositionGrade_form', null, '" + positionGradeHolder.getLastPage()
               + "', null, null, 'tableWrapper');\">末页</a></label>" );
         rs.append( "<label>&nbsp;&nbsp;&nbsp;页数：" + positionGradeHolder.getRealPage() + "/" + positionGradeHolder.getPageCount() + "</label>&nbsp;" );
         rs.append( "</td>" );
         rs.append( "</tr>" );
         rs.append( "</tfoot>" );
      }
      rs.append( "</table>" );

      return rs.toString();
   }

   // 生成PositionGrade的列表
   public static String getBasePositionGradeHtml( final HttpServletRequest request ) throws KANException
   {
      // 获得当前AccountId职位组
      final List< MappingVO > positionGradeMappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionGrades( request.getLocale().getLanguage(), BaseAction.getCorpId( request, null ) );

      final StringBuffer rs = new StringBuffer();
      rs.append( "<li>" + KANUtil.getProperty( request.getLocale(), "message.click.position.garde.name" ) + "</li>" );
      if ( positionGradeMappingVOs != null && positionGradeMappingVOs.size() > 0 )
      {
         for ( MappingVO mappingVO : positionGradeMappingVOs )
         {

            rs.append( "<li style=\"width:33%;float:left;margin-top:10px\" >" );
            rs.append( "<a style=\"cursor:pointer\" onclick=\"addContactPositionGrade('" + mappingVO.getMappingId() + "','" + mappingVO.getMappingValue() + "'); \"><span>"
                  + mappingVO.getMappingValue() + "</span></a>" );
            rs.append( "</li>" );
         }

      }

      return rs.toString();
   }

}
