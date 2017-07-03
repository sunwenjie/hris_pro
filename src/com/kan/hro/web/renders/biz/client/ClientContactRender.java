package com.kan.hro.web.renders.biz.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContactVO;
import com.kan.hro.domain.biz.client.ClientDTO;

public class ClientContactRender
{
   public static String getClientContactCombo( final HttpServletRequest request ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      // 从request域中获取clientDTO
      final ClientDTO clientDTO = ( ClientDTO ) request.getAttribute( "clientDTO" );

      if ( clientDTO != null && clientDTO.getClientContactVOs() != null && clientDTO.getClientContactVOs().size() > 0 )
      {
         // 从clientDTO中获取clientContactVO list
         final List< ClientContactVO > clientContactVOs = clientDTO.getClientContactVOs();

         // 遍历clientContactVO生成
         for ( ClientContactVO clientContactVO : clientContactVOs )
         {
            String clientContactName = "";
            String clientContractId = clientContactVO.getClientContactId();
            
            // 按照语言设置ClientContactName
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               clientContactName = clientContactVO.getNameZH();
            }
            else
            {
               clientContactName = clientContactVO.getNameEN();
            }

            rs.append( "<li id=\"mannageClientContact_" + clientContactVO.getClientContactId() + "\">" +
            		"<input type=\"hidden\" id=\"clientContactIdArray\" name=\"clientContactIdArray\" value=\"" + clientContactVO.getClientContactId() + "\">" +
            		"<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" +
            		"<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" style=\"display: none;\" name=\"warning_img\" onClick=\"removeClientContact('"+ clientContactVO.getClientContactId() + "');\"/>" +
            		"&nbsp;&nbsp;<a onclick=link('clientContactAction.do?proc=to_objectModify&id=" + clientContactVO.getEncodedId() + "') >" + clientContactName + "(ID:" + clientContractId + ")" + "</a>" +
            		"</li>" );
         }
      }
      return rs.toString();
   }
}
