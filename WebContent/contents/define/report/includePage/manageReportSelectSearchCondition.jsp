<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- ����step3��ѡ����������formҳ�� -->
<div id="detailForm2">
	<jsp:include page="/contents/define/report/form/manageReportSelectSearchConditionForm.jsp" flush="true"></jsp:include>
</div>
<div class="bottom">
	<p></p>
</div>
<!-- ����step3��ѡ����������tableҳ�� -->
<div id="tableWrapper2">
	<jsp:include page="/contents/define/report/table/listReportSearchDetailTable.jsp" flush="true"></jsp:include>	
</div>	