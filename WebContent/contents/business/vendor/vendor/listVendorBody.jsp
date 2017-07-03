<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_BIZ_VENDOR", "vendorForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_BIZ_VENDOR" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_BIZ_VENDOR" ) %>
		
		var owner = '<bean:write name="vendorForm" property="owner" />';
		// ��ʼ�����ſؼ�
		branchChange('HRO_BIZ_VENDOR_branch', null, owner, 'HRO_BIZ_VENDOR_owner');
		
		// �󶨲���Change�¼�
		$('.HRO_BIZ_VENDOR_branch').change( function () { 
			branchChange('HRO_BIZ_VENDOR_branch', null, 0, 'HRO_BIZ_VENDOR_owner');
		});	
		
		var cityId = '<bean:write name="vendorForm" property="cityId" />';
		
		//��ʼ��ʡ�ݿؼ�
		provinceChange('HRO_BIZ_VENDOR_provinceId', 'searchObject', cityId, 'cityId');
		
		//��ʡ��Change�¼�
		$('.HRO_BIZ_VENDOR_provinceId').change( function () { 
			provinceChange('HRO_BIZ_VENDOR_provinceId', 'searchObject', 0, 'cityId');
		});
		
		// ��������
		$('#resetBtn').click(function(){
			$('#provinceId').val('0');
			$('#provinceId').change();
		});
	
	})(jQuery);
	
	/**
	 * �Զ��庯��
	 **/
	function submit_object( id ){
		link('vendorAction.do?proc=submit_object&id=' + id); 
	};
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_VENDOR" ) %>
</script>
