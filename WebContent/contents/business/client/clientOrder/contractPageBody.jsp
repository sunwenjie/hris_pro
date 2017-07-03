<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="contract" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">�����ͬ��Ϣ</label>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<div class="kantab" style="width: 649px; padding: 75px 56px 75px 56px;">
				<html:form action="clientContractAction.do?proc=add_object" styleClass="manageContract_form">
					<bean:write name="contractForm" property="content" filter="false" />
				</html:form>
			</div>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_client_Modules').addClass('current');	
		$('#menu_client_Business').addClass('selected');
		$('#menu_client_Contract').addClass('selected');

		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			submit('manageContract_form');
		});

		//	ȡ���¼�
		$('#btnCancel').click(function(){
			if (agreest())
			javascript:history.go(-1);
		});
		
	})(jQuery);
</script>

