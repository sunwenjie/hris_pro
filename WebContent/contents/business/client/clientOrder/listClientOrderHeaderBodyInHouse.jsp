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
		
		// ����ӡ�����ɾ������ťȥ��
		$('#btnAdd').remove();
		$('#btnDelete').remove();
		 
		// �󶨲���Change�¼�
		$('.branch').change(function (){ 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
	})(jQuery);
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE" ) %>
	
	// �Զ��庯��
	function submit_object( id ){
		$('.list_form').attr('action','clientOrderHeaderAction.do?proc=submit_object&id=' + id);
		submitForm('list_form', 'submitObject', null, null, null, 'tableWrapper');
	};
</script>
