<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.util.KANUtil"%>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) + " - " + KANUtil.getProperty( request.getLocale(), "define.column.group.detail" ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title} - 字段分组管理" />
  <tiles:put name="body" value="/contents/define/columnGroup/manageColumnGroupBody.jsp" />
</tiles:insert>