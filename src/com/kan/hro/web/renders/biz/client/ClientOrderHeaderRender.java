package com.kan.hro.web.renders.biz.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;

public class ClientOrderHeaderRender
{
   @SuppressWarnings("unchecked")
   public static String getClientOrderHeaderCombo( final HttpServletRequest request ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      // 从request域中获取ClientOrderHeaderVO List
      final List< Object > clientOrderHeaderVOObjects = ( List< Object > ) request.getAttribute( "clientOrderHeaderVOs" );

      if ( clientOrderHeaderVOObjects != null && clientOrderHeaderVOObjects.size() > 0 )
      {
         // 遍历ClientOrderHeaderVO List
         for ( Object clientOrderHeaderVOObject : clientOrderHeaderVOObjects )
         {
            ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) clientOrderHeaderVOObject;
            
            final String clientOrderHeaderId = clientOrderHeaderVO.getOrderHeaderId();
            final String startDate =  clientOrderHeaderVO.decodeDatetime( clientOrderHeaderVO.getStartDate() );
            final String endDate = clientOrderHeaderVO.decodeDatetime( clientOrderHeaderVO.getEndDate() );
            final String decodeStatus = clientOrderHeaderVO.getDecodeStatus();

            rs.append( "<li id=\"mannageClientOrderHeader_" + clientOrderHeaderVO.getOrderHeaderId() + "\">" +
                  "<input type=\"hidden\" id=\"clientOrderHeaderIdArray\" name=\"clientOrderHeaderIdArray\" value=\"" + clientOrderHeaderVO.getOrderHeaderId() + "\">" +
                  "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" +
                  "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" style=\"display: none;\" name=\"warning_img\" onClick=\"removeClientOrderHeader('" + clientOrderHeaderVO.getOrderHeaderId() + "');\"/>" +
                  "&nbsp;&nbsp;<a onclick=link('clientOrderHeaderAction.do?proc=to_objectModify&id=" + clientOrderHeaderVO.getEncodedId() + "') >" + clientOrderHeaderId + "(" + startDate + "/" + endDate + ", " + decodeStatus + ")" + "</a>" +
                  "</li>" );
         }
      }
      return rs.toString();
   }
}
