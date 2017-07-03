<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_BIZ_CLIENT_INVOICE", "clientInvoiceForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_BIZ_CLIENT_INVOICE" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_BIZ_CLIENT_INVOICE" ) %>
		
	})(jQuery);
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_CLIENT_INVOICE" ) %>
</script>
