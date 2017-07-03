<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_BIZ_CLIENT", "clientForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_BIZ_CLIENT" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_BIZ_CLIENT" ) %>
		
		// 初始化部门控件
		branchChange('HRO_BIZ_CLIENT_branch', null, $('#temp_owner').val(), 'HRO_BIZ_CLIENT_owner');
		
		// 绑定部门Change事件
		$('.HRO_BIZ_CLIENT_branch').change( function () { 
			branchChange('HRO_BIZ_CLIENT_branch', null, 0, 'HRO_BIZ_CLIENT_owner');
		});		
	})(jQuery);
	
	/**
	 * 自定义函数
	 **/
	function submit_object( id ){
		link('clientAction.do?proc=submit_object&id=' + id); 
	};
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_CLIENT" ) %>
</script>
