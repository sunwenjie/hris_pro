<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE", "clientOrderHeaderForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE" ) %>
		
		// “添加”、“删除”按钮去除
		$('#btnAdd').remove();
		$('#btnDelete').remove();
		 
		// 绑定部门Change事件
		$('.branch').change(function (){ 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// 初始化部门控件
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
	})(jQuery);
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE" ) %>
	
	// 自定义函数
	function submit_object( id ){
		$('.list_form').attr('action','clientOrderHeaderAction.do?proc=submit_object&id=' + id);
		submitForm('list_form', 'submitObject', null, null, null, 'tableWrapper');
	};
</script>
