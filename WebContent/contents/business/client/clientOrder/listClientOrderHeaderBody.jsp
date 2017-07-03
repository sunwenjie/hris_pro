<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_BIZ_CLIENT_ORDER_HEADER", "clientOrderHeaderForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_BIZ_CLIENT_ORDER_HEADER" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_BIZ_CLIENT_ORDER_HEADER" ) %>
		
		// �󶨲���Change�¼�
		$('.branch').change(function (){ 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// �����ð�ť
		$('#resetBtn').click(function(){
			resetForm_other();
		});
		
		//	�ı��ͬ��select��ʽ�ĳ�text
		$("fieldset li select[id='contractId']").replaceWith('<input name="contractId" type="text" maxlength="10" class="HRO_BIZ_CLIENT_ORDER_HEADER_contractId" id="contractId" value="<bean:write name='clientOrderHeaderForm' property='contractId'/>"></input>');
	})(jQuery);
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_CLIENT_ORDER_HEADER" ) %>
	
	// �Զ��庯��
	function submit_object( id ){
		$('.list_form').attr('action','clientOrderHeaderAction.do?proc=submit_object&id=' + id);
		submitForm('list_form', 'submitObject', null, null, null, 'tableWrapper');
	};
	
	function resetForm_other(){
		$('.HRO_BIZ_CLIENT_ORDER_HEADER_contractId').val('');
	};
</script>
