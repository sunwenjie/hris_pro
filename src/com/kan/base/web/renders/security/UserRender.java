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
      rs.append( "<label class=\"columnName\">员工姓名：</label><label></label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">用户名：</label><label>" + userVO.getUsername() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">密码：</label><label>" + userVO.getPassword() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">绑定IP：</label><label>" + userVO.getBindIP() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">最后登录时间：</label><label>" + userVO.getDecodeLastLogin() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">最后登录IP：</label><label>" + userVO.getLastLoginIP() + "</label>" );
      rs.append( "</li>" );
      rs.append( "<li>" );
      rs.append( "<label class=\"columnName\">状态：</label><label>" + userVO.getDecodeStatus() + "</label>" );
      rs.append( "</li>" );
      rs.append( "</ol>" );

      return rs.toString();
   }
}
