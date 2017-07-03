<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title",  BaseAction.getTitle( request, response ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" type="String">
  	<%
  		if(request.getAttribute("role").equals( KANConstants.ROLE_IN_HOUSE)){
  		   out.print( BaseAction.getTitle( request, response ) +" - 职位列表");
  		}else{
  		   out.print( BaseAction.getTitle( request, response ) +" - 职位（外部）列表");
  		}
  	%>
  </tiles:put>
  <tiles:put name="body" value="/contents/management/position/listPositionBody.jsp" />
</tiles:insert>