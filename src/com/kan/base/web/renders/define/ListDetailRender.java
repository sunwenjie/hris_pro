package com.kan.base.web.renders.define;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ListDetailRender
{

   public static String getListDetailManageHtml( final HttpServletRequest request, final ColumnVO columnVO ) throws KANException
   {
      // 初始化StringBuffer对象
      final StringBuffer rs = new StringBuffer();
      // 初始化ListDetailVO对象
      final ListDetailVO listDetailVO = ( ListDetailVO ) request.getAttribute( "listDetailForm" );

      if ( listDetailVO != null )
      {
         // 如果ListHeaderId不为空，添加隐藏字段
         if ( listDetailVO.getListHeaderId() != null && !"".equals( listDetailVO.getListHeaderId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"listHeaderId\" name=\"listHeaderId\" value=\"" + listDetailVO.getListHeaderId() + "\"/>" );
         }
         // 如果ListDetailId不为空，添加隐藏字段
         if ( listDetailVO.getEncodedId() != null && !"".equals( listDetailVO.getEncodedId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"listDetailId\" name=\"listDetailId\" value=\"" + listDetailVO.getEncodedId() + "\"/>" );
         }
         // 如果ColumnId不为空，添加隐藏字段
         if ( listDetailVO.getColumnId() != null && !"".equals( listDetailVO.getColumnId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"columnId\" name=\"columnId\" value=\"" + listDetailVO.getColumnId() + "\"/>" );
         }

         rs.append( "<ol class=\"auto\">" );

         // 如果当前字段对象不为空
         if ( columnVO != null && columnVO.getValueType() != null && !columnVO.getValueType().equals( "" ) )
         {
            // 如果选择字段是数值类型
            if ( columnVO.getValueType().equals( "1" ) )
            {
               rs.append( "<ol class=\"auto\">" );
               // 精度
               rs.append( "<li>" );
               rs.append( "<label>精度 <img src=\"images/tips.png\" title=\"保留小数位数\" /></label>" );
               rs.append( KANUtil.getSelectHTML( listDetailVO.getAccuracys(), "accuracy", "manageListDetail_accuracy", listDetailVO.getAccuracy(), null, null ) );
               rs.append( "</li>" );

               // 取舍
               rs.append( "<li>" );
               rs.append( "<label>取舍 <img src=\"images/tips.png\" title=\"小数位数保留方式，截取 - 直接抹去待截取小数位，向上进位 - 正数进位、负数截取\" /></label>" );
               rs.append( KANUtil.getSelectHTML( listDetailVO.getRounds(), "round", "manageListDetail_round", listDetailVO.getRound(), null, null ) );
               rs.append( "</li>" );
               rs.append( "</ol>" );
            }
            // 如果选择字段是日期类型
            if ( columnVO.getValueType().equals( "3" ) )
            {
               rs.append( "<ol class=\"auto\">" );
               rs.append( "<li>" );
               rs.append( "<label>日期格式</label>" );
               rs.append( KANUtil.getSelectHTML( listDetailVO.getDatetimeFormats(), "datetimeFormat", "manageListDetail_datetimeFormat", listDetailVO.getDatetimeFormat(), null, null ) );
               rs.append( "</li>" );
               rs.append( "</ol>" );
            }
            rs.append( "<input type=\"hidden\" id=\"valueType\" id=\"valueType\" value=\"" + columnVO.getValueType() + "\"/>" );
         }

         // 字段名 - 中文
         rs.append( "<li>" );
         rs.append( "<label>字段名称（中文）<em> *</em></label>" );
         rs.append( "<input type=\"text\" id=\"nameZH\" name=\"nameZH\" maxlength=\"100\" class=\"manageListDetail_nameZH\" value=\""
               + ( listDetailVO.getNameEN() == null ? "" : listDetailVO.getNameZH() ) + "\"/>" );
         rs.append( "</li>" );

         // 字段名 - 英文
         rs.append( "<li>" );
         rs.append( "<label>字段名称（英文） <em> *</em></label>" );
         rs.append( "<input type=\"text\" id=\"nameEN\" name=\"nameEN\" maxlength=\"100\" class=\"manageListDetail_nameEN\" value=\""
               + ( listDetailVO.getNameEN() == null ? "" : listDetailVO.getNameEN() ) + "\"/>" );
         rs.append( "</li>" );

         // 字段宽度
         rs.append( "<li>" );
         rs.append( "<label>字段宽度 <img src=\"images/tips.png\" title=\"列表字段显示的宽度，可设置百分比或固定象素\" /></label> " );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getColumnWidthTypes(), "columnWidthType", "manageListDetail_columnWidthType small", listDetailVO.getColumnWidthType(), null, null ) );
         rs.append( "<input type=\"text\" id=\"columnWidth\" name=\"columnWidth\" maxlength=\"3\" class=\"manageListDetail_columnWidth small\" value=\""
               + ( listDetailVO.getColumnWidth() == null ? "" : listDetailVO.getColumnWidth() ) + "\"/>" );
         rs.append( "</li>" );

         // 排列顺序
         rs.append( "<li>" );
         rs.append( "<label>字段顺序 <img src=\"images/tips.png\" title=\"列表字段排列的顺序，一般为数值，例如：1、2、3、4、5...\" /><em> *</em></label> " );
         rs.append( "<input type=\"text\" id=\"columnIndex\" name=\"columnIndex\" maxlength=\"2\" class=\"manageListDetail_columnIndex\" value=\""
               + ( listDetailVO.getColumnIndex() == null ? "0" : listDetailVO.getColumnIndex() ) + "\"/>" );
         rs.append( "</li>" );

         // 字体大小
         rs.append( "<li>" );
         rs.append( "<label>字体大小（Size） <img src=\"images/tips.png\" title=\"列表字段字体的大小\" /></label> " );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFontSizes(), "fontSize", "manageListDetail_fontSize", listDetailVO.getFontSize(), null, null ) );
         rs.append( "</li>" );

         // 转译
         rs.append( "<li>" );
         rs.append( "<label>转译</label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFlags(), "isDecoded", "manageListDetail_isDecoded", listDetailVO.getIsDecoded(), null, null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // 超链接 
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>超链接</label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFlags(), "isLinked", "manageListDetail_isLinked", listDetailVO.getIsLinked(), "isLinkedChange();", null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );
         rs.append( "<ol id=\"linkedDetailOl\" style=\"display: none;\" class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>链接地址 <img src=\"images/tips.png\" title=\"如需传参依次使用${0}，${1}，${2}...\" /><em> *</em></label> " );
         rs.append( "<input type=\"text\" name=\"linkedAction\" maxlength=\"100\" class=\"manageListDetail_linkedAction\"  value=\""
               + ( listDetailVO.getLinkedAction() == null ? "" : listDetailVO.getLinkedAction() ) + "\"/>" );
         rs.append( "</li>" );
         rs.append( "<li>" );
         rs.append( "<label>链接目标</label> " );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getLinkedTargets(), "linkedTarget", "manageListDetail_linkedTarget", listDetailVO.getLinkedTarget(), null, null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>附加内容 <img src=\"images/tips.png\" title=\"可填写多对HTML标签，<a>...</a><a>...</a>...，如需传参依次使用${0}，${1}，${2}...\" /></label> " );
         rs.append( "<textarea name=\"appendContent\" class=\"manageListDetail_appendContent\">"
               + ( listDetailVO.getAppendContent() == null ? "" : listDetailVO.getAppendContent() ) + "</textarea>" );
         rs.append( "</li>" );
         rs.append( "<li>" );
         rs.append( "<label>动态参数 <img src=\"images/tips.png\" title=\"英文单字节“,”逗号分隔多个参数，与“链接地址”或“附加链接地址”中的参数对应，参数名称需要在VO对象中存在；例如，encodedId等\" /></label> " );
         rs.append( "<input type=\"text\" name=\"properties\" maxlength=\"100\" class=\"manageListDetail_properties\"  value=\""
               + ( listDetailVO.getProperties() == null ? "" : listDetailVO.getProperties() ) + "\"/>" );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // 对齐
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>对齐</label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getAligns(), "align", "manageListDetail_align", listDetailVO.getAlign(), null, null ) );
         rs.append( "</li>" );

         // 排序
         rs.append( "<li>" );
         rs.append( "<label>排序 <img src=\"images/tips.png\" title=\"字段是否需要排序功能（列表显示）\" /></label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFlags(), "sort", "manageListDetail_sort", listDetailVO.getSort(), null, null ) );
         rs.append( "</li>" );

         // 显示
         rs.append( "<li>" );
         rs.append( "<label>显示 <img src=\"images/tips.png\" title=\"隐藏字段列表不显示，但可以被导出\" /></label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFlags(), "display", "manageListDetail_display", listDetailVO.getDisplay(), null, null ) );
         rs.append( "</li>" );

         // 状态
         rs.append( "<li>" );
         rs.append( "<label>状态  <em>*</em></label> " );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getStatuses(), "status", "manageListDetail_status", listDetailVO.getStatus(), null, null ) );
         rs.append( "</li>" );

         // 描述
         rs.append( "<li>" );
         rs.append( "<label>描述</label> " );
         rs.append( "<textarea name=\"description\" class=\"manageListDetail_description\">" + ( listDetailVO.getDescription() == null ? "" : listDetailVO.getDescription() )
               + "</textarea>" );
         rs.append( "</li>" );
         rs.append( "</ol>" );
      }

      return rs.toString();
   }

}
