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
      // ��ʼ��SearchDetailVO����
      final SearchDetailVO searchDetailVO = ( SearchDetailVO ) request.getAttribute( "searchDetailForm" );
      // ��ʼ��StringBuffer����
      final StringBuffer rs = new StringBuffer();

      if ( searchDetailVO != null )
      {
         // ���SearchHeaderId��Ϊ�գ���������ֶ�
         if ( searchDetailVO.getSearchHeaderId() != null && !"".equals( searchDetailVO.getSearchHeaderId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"searchHeaderId\" name=\"searchHeaderId\" value=\"" + searchDetailVO.getSearchHeaderId() + "\"/>" );
         }
         // ���SearchDetailId��Ϊ�գ���������ֶ�
         if ( searchDetailVO.getEncodedId() != null && !"".equals( searchDetailVO.getEncodedId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"searchDetailId\" name=\"searchDetailId\" value=\"" + searchDetailVO.getEncodedId() + "\"/>" );
         }
         // ���ColumnId��Ϊ�գ���������ֶ�
         if ( searchDetailVO.getColumnId() != null && !"".equals( searchDetailVO.getColumnId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"columnId\" name=\"columnId\" value=\"" + searchDetailVO.getColumnId() + "\"/>" );
         }

         // �ֶ����� - ����
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>�����ֶ����ƣ����ģ�<em> *</em></label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getNameZH() == null ? "" : searchDetailVO.getNameZH() )
               + "\" id=\"nameZH\" name=\"nameZH\" maxlength=\"100\" class=\"manageSearchDetail_nameZH\" />" );
         rs.append( "</li>" );

         // �ֶ����� - Ӣ��
         rs.append( "<li>" );
         rs.append( "<label>�����ֶ����ƣ�Ӣ�ģ�<em> *</em></label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getNameEN() == null ? "" : searchDetailVO.getNameEN() )
               + "\" id=\"nameEN\" name=\"nameEN\" maxlength=\"100\" class=\"manageSearchDetail_nameEN\" />" );
         rs.append( "</li>" );

         // �ֶ�˳��
         rs.append( "<li>" );
         rs.append( "<label>�ֶ�˳�� <a title=\"�б��ֶ����е�˳��һ��Ϊ��ֵ�����磺1��2��3��4��5... \"><img src=\"images/tips.png\" /></a><em> *</em></label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getColumnIndex() == null ? "0" : searchDetailVO.getColumnIndex() )
               + "\" id=\"columnIndex\" name=\"columnIndex\" maxlength=\"2\" class=\"manageSearchDetail_columnIndex\" />" );
         rs.append( "</li>" );

         // �����С
         rs.append( "<li>" );
         rs.append( "<label>�����С</label> " );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getFontSizes(), "fontSize", "manageSearchDetail_fontSize", searchDetailVO.getFontSize(), null, null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // ���빦��
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>�������� </label> " );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getFlags(), "useThinking", "manageSearchDetail_useThinking", searchDetailVO.getUseThinking(), "useThinkingChange()", null ) );
         rs.append( "</li>" );

         // ���빦�� - ����·��
         rs.append( "<li id=\"thinkingActionLi\" style=\"display: none;\">" );
         rs.append( "<label>������ʵ�ַ</label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getThinkingAction() == null ? "" : searchDetailVO.getThinkingAction() )
               + "\" id=\"thinkingAction\" name=\"thinkingAction\" maxlength=\"100\" class=\"manageSearchDetail_thinkingAction\" />" );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // �ֶ�ֵ����
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>�ֶ�ֵ���� <a title=\"ֱ��ֵ - ����ʱֱ�Ӳ�ѯ��¼������ - ����ʱʹ������ȥƥ���¼�����ڿ��ٳ�ʼ���б�򱨱�\"><img src=\"images/tips.png\" /></a></label>" );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getContentTypes(), "contentType", "manageSearchDetail_contentType", searchDetailVO.getContentType(), null, null ) );
         rs.append( "</li>" );

         // �ֶ�ֵ
         rs.append( "<li id=\"manageSearchDetail_content_li\""
               + ( searchDetailVO.getContentType() != null && searchDetailVO.getContentType().trim().equals( "1" ) ? "" : "style=\"display: none;\"" ) + " >" );
         rs.append( "<label>�ֶ�ֵ  <a title=\"�����ֶ�ֱ��ֵ\"><img src=\"images/tips.png\" /></a></label>" );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getContent() == null ? "" : searchDetailVO.getContent() )
               + "\" name=\"content\" maxlength=\"500\" class=\"manageSearchDetail_content\" />" );
         rs.append( "</li>" );

         // ������Χ
         rs.append( "<li id=\"manageSearchDetail_range_li\""
               + ( searchDetailVO.getContentType() != null && searchDetailVO.getContentType().trim().equals( "2" ) ? "" : "style=\"display: none;\"" ) + " >" );
         rs.append( "<label>�ֶ�ֵ  <a title=\"�����ֶ�����ֵ\"><img src=\"images/tips.png\" /></a></label> " );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getRangeMin() == null ? "" : searchDetailVO.getRangeMin() )
               + "\" id=\"rangeMin\" name=\"rangeMin\" maxlength=\"100\" class=\"manageSearchDetail_rangeMin small\" />" );
         rs.append( "<input type=\"text\" value=\"" + ( searchDetailVO.getRangeMax() == null ? "" : searchDetailVO.getRangeMax() )
               + "\" id=\"rangeMax\" name=\"rangeMax\" maxlength=\"100\" class=\"manageSearchDetail_rangeMax small\" />" );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // �Ƿ���ʾ
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>�Ƿ���ʾ</label>" );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getFlags(), "display", "manageSearchDetail_display", searchDetailVO.getDisplay(), null, null ) );
         rs.append( "</li>" );

         // ״̬
         rs.append( "<li>" );
         rs.append( "<label>״̬  <em>*</em></label> " );
         rs.append( KANUtil.getSelectHTML( searchDetailVO.getStatuses(), "status", "manageSearchDetail_status", searchDetailVO.getStatus(), null, null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // ����
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>����</label> " );
         rs.append( "<textarea id=\"description\" name=\"description\" class=\"manageSearchDetail_description\" >"
               + ( searchDetailVO.getDescription() == null ? "" : searchDetailVO.getDescription() ) + "</textarea>" );
         rs.append( "</li>" );
         rs.append( "</ol>" );
      }

      return rs.toString();
   }
}
