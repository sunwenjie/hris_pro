package com.kan.base.web.renders.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ItemRender
{
   public static String getItemThinkingManagement( final HttpServletRequest request, final String[] itemIdArray ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // �����л������ ItemVOs
      final List< MappingVO > allItemVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getItems( request.getLocale().getLanguage(), BaseAction.getCorpId( request, null ) );

      // ��������
      rs.append( "<div id=\"addItemDiv\">" );
      rs.append( "<input type=\"text\" id=\"itemName\" name=\"itemName\" class=\"thinking_itemName\"><input type=\"hidden\" id=\"itemId\" name=\"itemId\" class=\"thinking_itemId\">&nbsp&nbsp&nbsp" );
      rs.append( "<input type=\"button\" class=\"addbutton\" name=\"btnAddItem\" id=\"btnAddItem\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.add" )
            + "\" onclick=\"addItem();\"/> " );
      rs.append( "</div>" );

      rs.append( "<ol class=\"auto\" id=\"itemList\">" );

      if ( itemIdArray != null && itemIdArray.length > 0 )
      {
         // ��������itemIdArray
         for ( String itemId : itemIdArray )
         {
            // ��������ȡ�õ�allItemVOs
            for ( MappingVO itemMappingVO : allItemVOs )
            {
               // ����ҵ���Ӧ�� Item MappingVO
               if ( itemId.equals( itemMappingVO.getMappingId() ) )
               {
                  final String itemName = itemMappingVO.getMappingValue();

                  // ���ɱ༭ ItemVO������
                  rs.append( "<li id=\"mannageItem_" + itemId + "\" style=\"margin: 2px 0px;\">" );
                  rs.append( "<input type=\"hidden\" id=\"itemIdArray\" name=\"itemIdArray\" value=\"" + itemId + "\">" );
                  rs.append( "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" );
                  rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onClick=\"removeItem('mannageItem_"
                        + itemId + "');\"/>" + " &nbsp;&nbsp; " + itemName + "</li>" );
                  continue;
               }
            }
         }
      }
      rs.append( "</ol>" );

      return rs.toString();
   }
}
