<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>
	
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title} - ְλ�ܹ�ͼ����" />
  <tiles:put name="body" value="/contents/security/position/position_orgShoot_detail.jsp" />
</tiles:insert>