<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANConstants" %>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<% request.setAttribute("title", KANConstants.PRODUCT_NAME_INH );%>

<tiles:insert definition="com.kan.hro.logon" flush="true">
	<tiles:put name="title" value="${title} - Login" />
	<tiles:put name="body" value="/popup/changePassword.jsp" />
</tiles:insert>
<script type="text/javascript">
	(function($) {
		popupChangePassword();
		var html = "<div>";
		html += "<p style='text-align: left'><img src='images/tips.png'>����ϵͳ��ȫ����,��������ǿ�Ƚ���,�����޸��������µ�¼,лл!</p>";
		html += "<p style='text-align: left'><img src='images/tips.png'>As the system security upgrade, your password is weak.Please change your password and reLogin,thinks!</p>";
		html += "</div>";
		$(".modal-body").append(html);
		$(".close").hide();
	})(jQuery);	
</script>