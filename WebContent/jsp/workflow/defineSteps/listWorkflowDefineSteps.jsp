<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>

<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" type="String">${title} - 工作流- 作业步骤列表</tiles:put>
  <tiles:put name="body" value="/contents/workflow/defineSteps/listWorkflowDefineStepsBody.jsp" />
</tiles:insert>