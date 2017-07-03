<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_SEC_STAFF", "staffForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		// 加载需要特殊处理的内容
		var disable = true;
		if(getSubAction() == 'createObject'){
			disable = false;
		}
		loadHtml('#special_info', 'staffAction.do?proc=list_special_info_html&staffId=<bean:write name="staffForm" property="encodedId" />', disable);
		
		<% 
			final String staffTitle = KANUtil.getProperty( request.getLocale(), "security.staff" );
			final String newP = KANUtil.getProperty( request.getLocale(), "oper.new" );
			final String editP = KANUtil.getProperty( request.getLocale(), "oper.edit" );
			final String viewP = KANUtil.getProperty( request.getLocale(), "oper.view" );
			final boolean en_ = request.getLocale().getLanguage().equalsIgnoreCase( "EN" );
			
			final StringBuffer initCallBack = new StringBuffer();
		    initCallBack.append( "if(getSubAction()=='createObject'){$('#pageTitle').html('" + newP + ( en_ ? " " : "" ) + staffTitle + "');}else{" );
	        initCallBack.append( "$('#pageTitle').html('" + staffTitle + ( en_ ? " " : "" ) + viewP + "');}"  );
			
			final StringBuffer editCallBack = new StringBuffer();
		    editCallBack.append( "$('#pageTitle').html('" + staffTitle + ( en_ ? " " : "" ) + editP + "');"  );
		%>
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_SEC_STAFF", initCallBack.toString(), editCallBack.toString(), null, "if($('#username').val()!=''){ flag = flag + validate('bizEmail',true, 'email', 0 ,0, 0,0);}" ) %>
	})(jQuery);
</script>
