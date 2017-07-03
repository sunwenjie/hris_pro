<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4", "iClickEmployeeReportForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4", true ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4" ) %>
		useFixColumn();
	})(jQuery);
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "ICLICK_ACCESS_ACTION_FULL_EMPLOYEE_REPORT_R4" ) %>
</script>
