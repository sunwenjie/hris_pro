<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>

<div id="content">
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_SEC_STAFF", "staffForm" ) %>
	
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_SEC_STAFF" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_SEC_STAFF" ) %>
	})(jQuery);

	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_SEC_STAFF" ) %>
</script>