<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_BIZ_CLIENT_CONTRACT", "clientContractForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_BIZ_CLIENT_CONTRACT" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_BIZ_CLIENT_CONTRACT" ) %>
		
		// 初始化加载合同模板下拉框
		loadBusinessContractTemplateOptions('<bean:write name="clientContractForm" property="accountId"/>');
		
	})(jQuery);

	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_CLIENT_CONTRACT" ) %>

	// 加载合同模板下拉框事件
	function loadBusinessContractTemplateOptions(accountId){
		loadHtml('#templateId', 'businessContractTemplateAction.do?proc=list_object_options_ajax&accountId=' + accountId, false );
	};	
	
	// 自定义函数
	function submit_object( id ){
		link('clientContractAction.do?proc=submit_object&id=' + id); 
	};
</script>
