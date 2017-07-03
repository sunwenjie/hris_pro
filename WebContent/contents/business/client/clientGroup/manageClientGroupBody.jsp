<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_GROUP", "clientGroupForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_GROUP", null, null, null ) %>
		
		loadHtml('#special_info', 'clientGroupAction.do?proc=list_special_info_html&clientGroupId=<bean:write name="clientGroupForm" property="encodedId"/>', !isCreate());
	})(jQuery);
	
	// 当前是否新建情况
	function isCreate(){
		if(getSubAction() == 'createObject'){
			return true;
		}else{
			return false;
		}
	};
</script>

