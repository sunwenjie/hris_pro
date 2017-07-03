<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.util.KANUtil"%>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) + " - " + KANUtil.getProperty( request.getLocale(), "workflow.todo.detail" ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" type="String">${title}</tiles:put>
  <logic:equal name="historyVO" property="objectType"  value="3">
  	<tiles:put name="body" value="/contents/workflow/actualSteps/listWorkflowActualStepsBodyNew.jsp" />
  </logic:equal>
  <logic:equal name="historyVO" property="objectType"  value="31">
  	<tiles:put name="body" value="/contents/workflow/actualSteps/listWorkflowActualStepsBodyNew.jsp" />
  </logic:equal>
  <logic:equal name="historyVO" property="objectType"  value="32">
  	<tiles:put name="body" value="/contents/workflow/actualSteps/listWorkflowActualStepsBodyNew.jsp" />
  </logic:equal>
   <logic:notEqual name="historyVO" property="objectType"  value="3">
    <logic:notEqual name="historyVO" property="objectType"  value="31">
    <logic:notEqual name="historyVO" property="objectType"  value="32">
  	<tiles:put name="body" value="/contents/workflow/actualSteps/listWorkflowActualStepsBody.jsp" />
  	</logic:notEqual>
  	</logic:notEqual>
  </logic:notEqual>
</tiles:insert>