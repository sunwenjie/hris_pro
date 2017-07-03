<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_BIZ_EMPLOYEE_IN_HOUSE", "employeeForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_BIZ_EMPLOYEE_IN_HOUSE" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_BIZ_EMPLOYEE_IN_HOUSE" ) %>
		$('#btnQuick').show();
	 	// ɾ����ť����¼�
	 	$('#btnDelete').click(function(){
	 		validateForDelete();
	 	});
	})(jQuery);
	
	//�������ڹ�Աɾ����ʾ
	
	function validateForDelete(){
		var selectedIds = $("#selectedIds").val();
		var selectedIdArray = $("#selectedIds").val().split(",");
		var selectedSize = 0;
		
		if(selectedIds != ''){
			selectedSize = selectedIdArray.length;
			// �ж��Ƿ��й�Ա�ڷ�������
			$.post("employeeAction.do?proc=areEmpsDurServ",{selectedIds:selectedIds},function(data){
				if(data!=''){
					// ���ڹ�Ա�ڷ�������
					$("#messageWrapper").html('<div class="message error fadable">Ա��IDΪ :'+data+'�ڷ�������,���ܱ�ɾ�� <a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					return false;
				}else{
					if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')){
						
						submitForm('list_form', 'deleteObjects', null, null, null, 'tableWrapper');
						
					}
				}
			},"text");
		}
	};

	function importExcel(){
		$('#modalId').removeClass('hide');$('#shield').show();
	}
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_EMPLOYEE_IN_HOUSE" ) %>
	
	function changeExportExcelAction(){
		var js = "if(confirm(\"���'��'����Ա�����ݿ�,���'��'����Ա��������Ϣ\")){";
		js+="linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction=HRO_BIZ_EMPLOYEE_IN_HOUSE&exportAll=true');";
		js+="}else{linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction=HRO_BIZ_EMPLOYEE_IN_HOUSE');}";
		$("#exportExcel").attr("onclick",js);
	}
</script>
