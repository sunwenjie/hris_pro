<%@page import="com.kan.base.domain.define.ImportDetailVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- ����step2��ѡ���ֶ�formҳ�� -->
<div id="detailForm1">
	<jsp:include page="/contents/define/import/header/form/manageImportSelectColumnForm.jsp" flush="true"></jsp:include>
</div>

<div class="bottom"><p></p></div>

<!-- ����step2��ѡ���ֶ�tableҳ�� -->
<div id="tableWrapper2">
	<jsp:include page="/contents/define/import/header/table/listImportDetailTable.jsp" flush="true"></jsp:include>	
</div>	

<script type="text/javascript">
<logic:notEmpty name="MESSAGE">
	messageWrapperFada();
</logic:notEmpty>
	// ��ʼ����ť
	if($('.manageImportDetail_form input#subAction').val() == 'viewObject'){
		$('#btnSaveStep2').val("<bean:message bundle="public" key="button.edit" />");
		// ����Form
		disableForm('manageImportDetail_form');
	}
</script>