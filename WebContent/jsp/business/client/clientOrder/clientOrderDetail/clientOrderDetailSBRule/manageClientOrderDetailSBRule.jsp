<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>
	<tiles:insert definition="com.kan.hro.classical" flush="true">
		<tiles:put name="title" value="${title} - �ͻ� - �������籣�����𲹽ɹ���" />
		<tiles:put name="body" value="/contents/business/client/clientOrder/clientOrderDetail/clientOrderDetailSBRule/manageClientOrderDetailSBRuleBody.jsp" />
	</tiles:insert>
