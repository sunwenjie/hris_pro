package com.kan.hro.web.renders.biz.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientDTO;
import com.kan.hro.domain.biz.client.ClientInvoiceVO;

public class ClientInvoiceRender
{
   public static String getClientInvoiceCombo( final HttpServletRequest request ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      // 从request域中获取clientDTO
      final ClientDTO clientDTO = ( ClientDTO ) request.getAttribute( "clientDTO" );

      if ( clientDTO != null && clientDTO.getClientInvoiceVOs() != null && clientDTO.getClientInvoiceVOs().size() > 0 )
      {
         // 从clientDTO中获取clientInvoiceVO list
         final List< ClientInvoiceVO > clientInvoiceVOs = clientDTO.getClientInvoiceVOs();

         // 遍历clientInvoiceVO生成
         for ( ClientInvoiceVO clientInvoiceVO : clientInvoiceVOs )
         {
            String clientInvoiceName = "";
            String clientInvoiceId = clientInvoiceVO.getClientInvoiceId();
            
            // 按照语言设置ClientInvoiceName
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               clientInvoiceName = clientInvoiceVO.getNameZH();
            }
            else
            {
               clientInvoiceName = clientInvoiceVO.getNameEN();
            }

            rs.append( "<li id=\"mannageClientInvoice_" + clientInvoiceVO.getClientInvoiceId() + "\">" +
            		"<input type=\"hidden\" id=\"clientInvoiceIdArray\" name=\"clientInvoiceIdArray\" value=\"" + clientInvoiceVO.getClientInvoiceId() + "\">" +
            		"<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" +
            		"<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" style=\"display: none;\" name=\"warning_img\" onClick=\"removeClientInvoice('" + clientInvoiceVO.getClientInvoiceId() + "');\"/>" +
            		"&nbsp;&nbsp;<a onclick=link('clientInvoiceAction.do?proc=to_objectModify&id=" + clientInvoiceVO.getEncodedId() + "') >" + clientInvoiceName + "(ID:" + clientInvoiceId + ")" + "</a>" +
            		"</li>" );
         }
      }
      return rs.toString();
   }
}
