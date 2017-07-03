package com.kan.hro.web.renders.biz.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientDTO;

public class ClientContractRender
{
   public static String getClientContractCombo( final HttpServletRequest request ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      // 从request域中获取clientDTO
      final ClientDTO clientDTO = ( ClientDTO ) request.getAttribute( "clientDTO" );

      if ( clientDTO != null && clientDTO.getClientContractVOs() != null && clientDTO.getClientContractVOs().size() > 0 )
      {
         // 从clientDTO中获取clientContractVO list
         final List< ClientContractVO > clientContractVOs = clientDTO.getClientContractVOs();

         // 遍历clientContractVO生成
         for ( ClientContractVO clientContractVO : clientContractVOs )
         {
            String clientContractName = "";
            String clientContractId = clientContractVO.getContractId();
            
            // 按照语言设置ClientContractName
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               clientContractName = clientContractVO.getNameZH();
            }
            else
            {
               clientContractName = clientContractVO.getNameEN();
            }

            rs.append( "<li id=\"mannageClientContract_" + clientContractVO.getContractId() + "\">" +
            		"<input type=\"hidden\" id=\"clientContractIdArray\" name=\"clientContractIdArray\" value=\"" + clientContractVO.getContractId() + "\">" +
            		"<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" +
            		"<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" style=\"display: none;\" name=\"warning_img\" onClick=\"removeClientContract('" + clientContractVO.getContractId() + "');\"/>" +
            		"&nbsp;&nbsp;<a onclick=link('clientContractAction.do?proc=to_objectModify&id=" + clientContractVO.getEncodedId() + "') >" + clientContractName + "(ID:" + clientContractId + ")" + "</a>" +
            		"</li>" );
         }
      }
      return rs.toString();
   }
}
