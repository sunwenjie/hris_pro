<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.util.KANUtil"%>

<% request.setAttribute("title_2", BaseAction.getTitle( request, response ) + " - " + KANUtil.getProperty( request.getLocale(), "client.order.inhouse.sb" ) );%>
<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>

<logic:equal name="__COOKIE_USER_JSON" property="role" value="1">
	<tiles:insert definition="com.kan.hro.classical" flush="true">
		<tiles:put name="title" value="${title} - 客户 - 管理订单社保公积金方案" />
		<tiles:put name="body" value="/contents/business/client/clientOrder/clientOrderSB/manageClientOrderSBBody.jsp" />
	</tiles:insert>
</logic:equal>
<logic:equal name="__COOKIE_USER_JSON" property="role" value="2">
	<tiles:insert definition="com.kan.hro.classical" flush="true">
		<tiles:put name="title" value="${title_2}" />
		<tiles:put name="body" value="/contents/business/client/clientOrder/clientOrderSB/manageClientOrderSBBody.jsp" />
	</tiles:insert>
</logic:equal>