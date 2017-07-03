package com.kan.base.web.renders.define;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class SearchDetailRender
{

   public static String getSearchDeatailManageHtml( final HttpServletRequest request, final ColumnVO columnVO ) throws KANException
   {
      // 初始化SearchDetailVO对象
      final SearchDetailVO searchDetailVO = ( SearchDetailVO ) request.getAttribute( "searchDetailForm" );
      // 初始化StringBuffer对象
      final StringBuffer rs = new StringBuffer();

      if ( searchDetailVO != null )
      {
         // 如果SearchHeaderId不为空，添加隐藏字段
         if ( searchDetailVO.getSearchHeaderId() != null && !"".equals( searchDetailVO.getSearchHeaderId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"searchHeaderId\" name=\"searchHeaderId\" value=\"" + searchDetailVO.getSearchHeaderId() + "\"/>" );
         }
         // 如果SearchDetailId不为空，添加隐藏字段
         if ( searchDetailVO.getEncodedId() != null && !"".equals( searchDetailVO.getEncodedId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"searchDetailId\" name=\"searchDetailId\" value=\"" + searchDetailVO.getEncodedId() + "\"/>" );
         }
         // 如果ColumnId不为空，添加隐藏字段
         if ( searchDetailVO.getColumnId() != null && !"".equals( searchDetailVO.getColumnId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"columnId\" name=\"columnId\" value=\"" + searchDetailVO.getColumnId() + "\"/>" );
         }

         // 字段名称 - 中文
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>搜索字段名称（中文）<em> *</em></label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getNameZH() == null ? "" : searchDetailVO.getNameZH() )
               + "\" id=\"nameZH\" name=\"nameZH\" maxlength=\"100\" class=\"manageSearchDetail_nameZH\" />" );
         rs.append( "</li>" );

         // 字段名称 - 英文
         rs.append( "<li>" );
         rs.append( "<label>搜索字段名称（英文）<em> *</em></label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getNameEN() == null ? "" : searchDetailVO.getNameEN() )
               + "\" id=\"nameEN\" name=\"nameEN\" maxlength=\"100\" class=\"manageSearchDetail_nameEN\" />" );
         rs.append( "</li>" );

         // 字段顺序
         rs.append( "<li>" );
         rs.append( "<label>字段顺序 <a title=\"列表字段排列的顺序，一般为数值，例如：1、2、3、4、5... \"><img src=\"images/tips.png\" /></a><em> *</em></label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getColumnIndex() == null ? "0" : searchDetailVO.getColumnIndex() )
               + "\" id=\"columnIndex\" name=\"columnIndex\" maxlength=\"2\" class=\"manageSearchDetail_columnIndex\" />" );
         rs.append( "</li>" );

         // 字体大小
         rs.append( "<li>" );
         rs.append( "<label>字体大小</label> " );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getFontSizes(), "fontSize", "manageSearchDetail_fontSize", searchDetailVO.getFontSize(), null, null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // 联想功能
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>启用联想 </label> " );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getFlags(), "useThinking", "manageSearchDetail_useThinking", searchDetailVO.getUseThinking(), "useThinkingChange()", null ) );
         rs.append( "</li>" );

         // 联想功能 - 访问路径
         rs.append( "<li id=\"thinkingActionLi\" style=\"display: none;\">" );
         rs.append( "<label>联想访问地址</label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getThinkingAction() == null ? "" : searchDetailVO.getThinkingAction() )
               + "\" id=\"thinkingAction\" name=\"thinkingAction\" maxlength=\"100\" class=\"manageSearchDetail_thinkingAction\" />" );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // 字段值类型
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>字段值类型 <a title=\"直接值 - 搜索时直接查询记录，区间 - 搜索时使用区间去匹配记录，便于快速初始化列表或报表\"><img src=\"images/tips.png\" /></a></label>" );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getContentTypes(), "contentType", "manageSearchDetail_contentType", searchDetailVO.getContentType(), null, null ) );
         rs.append( "</li>" );

         // 字段值
         rs.append( "<li id=\"manageSearchDetail_content_li\""
               + ( searchDetailVO.getContentType() != null && searchDetailVO.getContentType().trim().equals( "1" ) ? "" : "style=\"display: none;\"" ) + " >" );
         rs.append( "<label>字段值  <a title=\"搜索字段直接值\"><img src=\"images/tips.png\" /></a></label>" );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getContent() == null ? "" : searchDetailVO.getContent() )
               + "\" name=\"content\" maxlength=\"500\" class=\"manageSearchDetail_content\" />" );
         rs.append( "</li>" );

         // 搜索范围
         rs.append( "<li id=\"manageSearchDetail_range_li\""
               + ( searchDetailVO.getContentType() != null && searchDetailVO.getContentType().trim().equals( "2" ) ? "" : "style=\"display: none;\"" ) + " >" );
         rs.append( "<label>字段值  <a title=\"搜索字段区间值\"><img src=\"images/tips.png\" /></a></label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getRangeMin() == null ? "" : searchDetailVO.getRangeMin() )
               + "\" id=\"rangeMin\" name=\"rangeMin\" maxlength=\"100\" class=\"manageSearchDetail_rangeMin small\" />" );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getRangeMax() == null ? "" : searchDetailVO.getRangeMax() )
               + "\" id=\"rangeMax\" name=\"rangeMax\" maxlength=\"100\" class=\"manageSearchDetail_rangeMax small\" />" );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // 是否显示
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>是否显示</label>" );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getFlags(), "display", "manageSearchDetail_display", searchDetailVO.getDisplay(), null, null ) );
         rs.append( "</li>" );

         // 状态
         rs.append( "<li>" );
         rs.append( "<label>状态  <em>*</em></label> " );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getStatuses(), "status", "manageSearchDetail_status", searchDetailVO.getStatus(), null, null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // 描述
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>描述</label> " );
         rs.append( "<textarea id=\"description\" name=\"description\" class=\"manageSearchDetail_description\" >"
               + ( searchDetailVO.getDescription() == null ? "" : searchDetailVO.getDescription() ) + "</textarea>" );
         rs.append( "</li>" );
         rs.append( "</ol>" );
      }

      return rs.toString();
   }
}
