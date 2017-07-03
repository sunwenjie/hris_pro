<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_BIZ_VENDOR_CONTACT", "vendorContactForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_BIZ_VENDOR_CONTACT" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_BIZ_VENDOR_CONTACT" ) %>
		
		var owner = '<bean:write name="vendorContactForm" property="owner" />';
		
		// ��ʼ�����ſؼ�
		branchChange('HRO_BIZ_VENDOR_CONTACT_branch', null, owner, 'HRO_BIZ_VENDOR_CONTACT_owner');
		
		// �󶨲���Change�¼�
		$('.HRO_BIZ_VENDOR_CONTACT_branch').change( function () { 
			branchChange('HRO_BIZ_VENDOR_CONTACT_branch', null, 0, 'HRO_BIZ_VENDOR_CONTACT_owner');
		});	
		
		var cityId = '<bean:write name="vendorContactForm" property="cityId" />';
		
		//��ʼ��ʡ�ݿؼ�
		provinceChange('HRO_BIZ_VENDOR_CONTACT_provinceId', 'searchObject', cityId, 'cityId');
		
		//��ʡ��Change�¼�
		$('.HRO_BIZ_VENDOR_CONTACT_provinceId').change( function () { 
			provinceChange('HRO_BIZ_VENDOR_CONTACT_provinceId', 'searchObject', 0, 'cityId');
		});
		
		// ��������
		$('#resetBtn').click(function(){
			$('#provinceId').val('0');
			$('#provinceId').change();
		});
		
	})(jQuery);
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_VENDOR_CONTACT" ) %>
</script>
