package com.kan.hro.web.renders.biz.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.tag.AuthConstants;
import com.kan.base.tag.AuthUtils;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientBaseView;
import com.kan.hro.domain.biz.client.ClientGroupDTO;
import com.kan.hro.web.actions.biz.client.ClientAction;

public class ClientRender
{
   public static String getClientNameCombo( final HttpServletRequest request ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // 从Request域中获取ClientGroupDTO
      final ClientGroupDTO clientGroupDTO = ( ClientGroupDTO ) request.getAttribute( "clientGroupDTO" );

      if ( clientGroupDTO != null && clientGroupDTO.getClientBaseViews() != null && clientGroupDTO.getClientBaseViews().size() > 0 )
      {
         // 从ClientGroupDTO中获取ClientBaseView List
         final List< ClientBaseView > clientBaseViews = clientGroupDTO.getClientBaseViews();
         // 遍历ClientBaseView生成
         for ( ClientBaseView clientBaseView : clientBaseViews )
         {
            rs.append( "<li id=\"mannageClient_" + clientBaseView.getId() + "\" >" );
            rs.append( "<input type=\"hidden\" id=\"clientIdArray\" name=\"clientIdArray\" value=\"" + clientBaseView.getId() + "\">" );
            if ( AuthUtils.hasAuthority( ClientAction.accessAction, AuthConstants.RIGHT_DELETE, "", request, null ) )
            {
               rs.append( "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" );
               rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onclick=\"removeExtraObject('clientAction.do?proc=delete_object_ajax&clientId="
                     + clientBaseView.getId() + "', this, '#numberOfClient');\"/>" );
            }
            rs.append( " &nbsp;&nbsp; " );
            if ( AuthUtils.hasAuthority( ClientAction.accessAction, AuthConstants.RIGHT_MODIFY, "", request, null ) )
            {
               rs.append( "<a onclick=link('clientAction.do?proc=to_objectModify&id=" + clientBaseView.getEncodedId() + "') >" + clientBaseView.getName() + "</a>" );
            }
            rs.append( "</li>" );
         }
      }
      return rs.toString();
   }
}
