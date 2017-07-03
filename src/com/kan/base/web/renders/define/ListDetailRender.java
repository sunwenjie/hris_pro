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
      // ��ʼ��StringBuffer����
      final StringBuffer rs = new StringBuffer();
      // ��ʼ��ListDetailVO����
      final ListDetailVO listDetailVO = ( ListDetailVO ) request.getAttribute( "listDetailForm" );

      if ( listDetailVO != null )
      {
         // ���ListHeaderId��Ϊ�գ���������ֶ�
         if ( listDetailVO.getListHeaderId() != null && !"".equals( listDetailVO.getListHeaderId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"listHeaderId\" name=\"listHeaderId\" value=\"" + listDetailVO.getListHeaderId() + "\"/>" );
         }
         // ���ListDetailId��Ϊ�գ���������ֶ�
         if ( listDetailVO.getEncodedId() != null && !"".equals( listDetailVO.getEncodedId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"listDetailId\" name=\"listDetailId\" value=\"" + listDetailVO.getEncodedId() + "\"/>" );
         }
         // ���ColumnId��Ϊ�գ���������ֶ�
         if ( listDetailVO.getColumnId() != null && !"".equals( listDetailVO.getColumnId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"columnId\" name=\"columnId\" value=\"" + listDetailVO.getColumnId() + "\"/>" );
         }

         rs.append( "<ol class=\"auto\">" );

         // �����ǰ�ֶζ���Ϊ��
         if ( columnVO != null && columnVO.getValueType() != null && !columnVO.getValueType().equals( "" ) )
         {
            // ���ѡ���ֶ�����ֵ����
            if ( columnVO.getValueType().equals( "1" ) )
            {
               rs.append( "<ol class=\"auto\">" );
               // ����
               rs.append( "<li>" );
               rs.append( "<label>���� <img src=\"images/tips.png\" title=\"����С��λ��\" /></label>" );
               rs.append( KANUtil.getSelectHTML( listDetailVO.getAccuracys(), "accuracy", "manageListDetail_accuracy", listDetailVO.getAccuracy(), null, null ) );
               rs.append( "</li>" );

               // ȡ��
               rs.append( "<li>" );
               rs.append( "<label>ȡ�� <img src=\"images/tips.png\" title=\"С��λ��������ʽ����ȡ - ֱ��Ĩȥ����ȡС��λ�����Ͻ�λ - ������λ��������ȡ\" /></label>" );
               rs.append( KANUtil.getSelectHTML( listDetailVO.getRounds(), "round", "manageListDetail_round", listDetailVO.getRound(), null, null ) );
               rs.append( "</li>" );
               rs.append( "</ol>" );
            }
            // ���ѡ���ֶ�����������
            if ( columnVO.getValueType().equals( "3" ) )
            {
               rs.append( "<ol class=\"auto\">" );
               rs.append( "<li>" );
               rs.append( "<label>���ڸ�ʽ</label>" );
               rs.append( KANUtil.getSelectHTML( listDetailVO.getDatetimeFormats(), "datetimeFormat", "manageListDetail_datetimeFormat", listDetailVO.getDatetimeFormat(), null, null ) );
               rs.append( "</li>" );
               rs.append( "</ol>" );
            }
            rs.append( "<input type=\"hidden\" id=\"valueType\" id=\"valueType\" value=\"" + columnVO.getValueType() + "\"/>" );
         }

         // �ֶ��� - ����
         rs.append( "<li>" );
         rs.append( "<label>�ֶ����ƣ����ģ�<em> *</em></label>" );
         rs.append( "<input type=\"text\" id=\"nameZH\" name=\"nameZH\" maxlength=\"100\" class=\"manageListDetail_nameZH\" value=\""
               + ( listDetailVO.getNameEN() == null ? "" : listDetailVO.getNameZH() ) + "\"/>" );
         rs.append( "</li>" );

         // �ֶ��� - Ӣ��
         rs.append( "<li>" );
         rs.append( "<label>�ֶ����ƣ�Ӣ�ģ� <em> *</em></label>" );
         rs.append( "<input type=\"text\" id=\"nameEN\" name=\"nameEN\" maxlength=\"100\" class=\"manageListDetail_nameEN\" value=\""
               + ( listDetailVO.getNameEN() == null ? "" : listDetailVO.getNameEN() ) + "\"/>" );
         rs.append( "</li>" );

         // �ֶο��
         rs.append( "<li>" );
         rs.append( "<label>�ֶο�� <img src=\"images/tips.png\" title=\"�б��ֶ���ʾ�Ŀ�ȣ������ðٷֱȻ�̶�����\" /></label> " );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getColumnWidthTypes(), "columnWidthType", "manageListDetail_columnWidthType small", listDetailVO.getColumnWidthType(), null, null ) );
         rs.append( "<input type=\"text\" id=\"columnWidth\" name=\"columnWidth\" maxlength=\"3\" class=\"manageListDetail_columnWidth small\" value=\""
               + ( listDetailVO.getColumnWidth() == null ? "" : listDetailVO.getColumnWidth() ) + "\"/>" );
         rs.append( "</li>" );

         // ����˳��
         rs.append( "<li>" );
         rs.append( "<label>�ֶ�˳�� <img src=\"images/tips.png\" title=\"�б��ֶ����е�˳��һ��Ϊ��ֵ�����磺1��2��3��4��5...\" /><em> *</em></label> " );
         rs.append( "<input type=\"text\" id=\"columnIndex\" name=\"columnIndex\" maxlength=\"2\" class=\"manageListDetail_columnIndex\" value=\""
               + ( listDetailVO.getColumnIndex() == null ? "0" : listDetailVO.getColumnIndex() ) + "\"/>" );
         rs.append( "</li>" );

         // �����С
         rs.append( "<li>" );
         rs.append( "<label>�����С��Size�� <img src=\"images/tips.png\" title=\"�б��ֶ�����Ĵ�С\" /></label> " );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFontSizes(), "fontSize", "manageListDetail_fontSize", listDetailVO.getFontSize(), null, null ) );
         rs.append( "</li>" );

         // ת��
         rs.append( "<li>" );
         rs.append( "<label>ת��</label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFlags(), "isDecoded", "manageListDetail_isDecoded", listDetailVO.getIsDecoded(), null, null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // ������ 
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>������</label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFlags(), "isLinked", "manageListDetail_isLinked", listDetailVO.getIsLinked(), "isLinkedChange();", null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );
         rs.append( "<ol id=\"linkedDetailOl\" style=\"display: none;\" class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>���ӵ�ַ <img src=\"images/tips.png\" title=\"���贫������ʹ��${0}��${1}��${2}...\" /><em> *</em></label> " );
         rs.append( "<input type=\"text\" name=\"linkedAction\" maxlength=\"100\" class=\"manageListDetail_linkedAction\"  value=\""
               + ( listDetailVO.getLinkedAction() == null ? "" : listDetailVO.getLinkedAction() ) + "\"/>" );
         rs.append( "</li>" );
         rs.append( "<li>" );
         rs.append( "<label>����Ŀ��</label> " );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getLinkedTargets(), "linkedTarget", "manageListDetail_linkedTarget", listDetailVO.getLinkedTarget(), null, null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>�������� <img src=\"images/tips.png\" title=\"����д���HTML��ǩ��<a>...</a><a>...</a>...�����贫������ʹ��${0}��${1}��${2}...\" /></label> " );
         rs.append( "<textarea name=\"appendContent\" class=\"manageListDetail_appendContent\">"
               + ( listDetailVO.getAppendContent() == null ? "" : listDetailVO.getAppendContent() ) + "</textarea>" );
         rs.append( "</li>" );
         rs.append( "<li>" );
         rs.append( "<label>��̬���� <img src=\"images/tips.png\" title=\"Ӣ�ĵ��ֽڡ�,�����ŷָ�����������롰���ӵ�ַ���򡰸������ӵ�ַ���еĲ�����Ӧ������������Ҫ��VO�����д��ڣ����磬encodedId��\" /></label> " );
         rs.append( "<input type=\"text\" name=\"properties\" maxlength=\"100\" class=\"manageListDetail_properties\"  value=\""
               + ( listDetailVO.getProperties() == null ? "" : listDetailVO.getProperties() ) + "\"/>" );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // ����
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>����</label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getAligns(), "align", "manageListDetail_align", listDetailVO.getAlign(), null, null ) );
         rs.append( "</li>" );

         // ����
         rs.append( "<li>" );
         rs.append( "<label>���� <img src=\"images/tips.png\" title=\"�ֶ��Ƿ���Ҫ�����ܣ��б���ʾ��\" /></label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFlags(), "sort", "manageListDetail_sort", listDetailVO.getSort(), null, null ) );
         rs.append( "</li>" );

         // ��ʾ
         rs.append( "<li>" );
         rs.append( "<label>��ʾ <img src=\"images/tips.png\" title=\"�����ֶ��б���ʾ�������Ա�����\" /></label>" );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getFlags(), "display", "manageListDetail_display", listDetailVO.getDisplay(), null, null ) );
         rs.append( "</li>" );

         // ״̬
         rs.append( "<li>" );
         rs.append( "<label>״̬  <em>*</em></label> " );
         rs.append( KANUtil.getSelectHTML( listDetailVO.getStatuses(), "status", "manageListDetail_status", listDetailVO.getStatus(), null, null ) );
         rs.append( "</li>" );

         // ����
         rs.append( "<li>" );
         rs.append( "<label>����</label> " );
         rs.append( "<textarea name=\"description\" class=\"manageListDetail_description\">" + ( listDetailVO.getDescription() == null ? "" : listDetailVO.getDescription() )
               + "</textarea>" );
         rs.append( "</li>" );
         rs.append( "</ol>" );
      }

      return rs.toString();
   }

}
