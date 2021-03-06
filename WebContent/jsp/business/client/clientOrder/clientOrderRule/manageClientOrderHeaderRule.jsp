<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>

<logic:equal name="__COOKIE_USER_JSON" property="role" value="1">
	<tiles:insert definition="com.kan.hro.classical" flush="true">
		<tiles:put name="title" value="${title} - 客户 - 管理订单规则" />
		<tiles:put name="body" value="/contents/business/client/clientOrder/clientOrderRule/manageClientOrderHeaderRuleBody.jsp" />
	</tiles:insert>
</logic:equal>
<logic:equal name="__COOKIE_USER_JSON" property="role" value="2">
	<tiles:insert definition="com.kan.hro.classical" flush="true">
		<tiles:put name="title" value="${title} - 管理结算规则规则" />
		<tiles:put name="body" value="/contents/business/client/clientOrder/clientOrderRule/manageClientOrderHeaderRuleBody.jsp" />
	</tiles:insert>
</logic:equal>