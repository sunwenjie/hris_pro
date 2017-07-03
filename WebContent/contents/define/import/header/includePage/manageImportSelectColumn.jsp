<%@page import="com.kan.base.domain.define.ImportDetailVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- 包含step2：选择字段form页面 -->
<div id="detailForm1">
	<jsp:include page="/contents/define/import/header/form/manageImportSelectColumnForm.jsp" flush="true"></jsp:include>
</div>

<div class="bottom"><p></p></div>

<!-- 包含step2：选择字段table页面 -->
<div id="tableWrapper2">
	<jsp:include page="/contents/define/import/header/table/listImportDetailTable.jsp" flush="true"></jsp:include>	
</div>	

<script type="text/javascript">
<logic:notEmpty name="MESSAGE">
	messageWrapperFada();
</logic:notEmpty>
	// 初始化按钮
	if($('.manageImportDetail_form input#subAction').val() == 'viewObject'){
		$('#btnSaveStep2').val("<bean:message bundle="public" key="button.edit" />");
		// 禁用Form
		disableForm('manageImportDetail_form');
	}
</script>