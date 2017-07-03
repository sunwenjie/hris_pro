<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) + " - " + KANUtil.getProperty( request.getLocale(), "performance.self.assessment" ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title}" />
  
  <logic:equal name="pageRole" value="1">
 	<tiles:put name="body" value="/contents/performance/manageSelfAssessmentBody_emp.jsp" />
  </logic:equal>
  <logic:notEqual name="pageRole" value="1">
 	<tiles:put name="body" value="/contents/performance/manageSelfAssessmentBody_pm.jsp" />
  </logic:notEqual>
</tiles:insert>