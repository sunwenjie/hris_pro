package com.kan.base.web.renders.security;

import java.util.Locale;

import com.kan.base.domain.security.UserVO;
import com.kan.base.util.KANException;

public class UserRender
{
   public static String getHistoryUserVO( final Locale locale, final UserVO userVO ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      rs.append( "<ol>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">Ա��������</label><label></label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">�û�����</label><label>" + userVO.getUsername() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">���룺</label><label>" + userVO.getPassword() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">��IP��</label><label>" + userVO.getBindIP() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">����¼ʱ�䣺</label><label>" + userVO.getDecodeLastLogin() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">����¼IP��</label><label>" + userVO.getLastLoginIP() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">״̬��</label><label>" + userVO.getDecodeStatus() + "</label>" );
      rs.append( "</li>" );
      rs.append( "</ol>" );

      return rs.toString();
   }
}
