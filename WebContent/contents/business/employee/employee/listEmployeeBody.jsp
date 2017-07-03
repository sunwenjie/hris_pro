<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, "HRO_BIZ_EMPLOYEE", "employeeForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, "HRO_BIZ_EMPLOYEE" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, "HRO_BIZ_EMPLOYEE" ) %>

	 	// ��д ɾ���¼�
	 	$('#btnDelete').attr('onclick','');
	 	$('#btnDelete').click(function(){
	 		employee_deleteValidate();
	 	});
	 	
		//�������ڹ�Աɾ����ʾ		
		var employee_deleteValidate = function(){
			var selectedIds = $("#selectedIds").val();
			var selectedIdArray = $("#selectedIds").val().split(",");
			var selectedSize = 0;
			
			if(selectedIds != ''){
				selectedSize = selectedIdArray.length;
				// �ж��Ƿ��й�Ա�ڷ�������
				$.post("employeeAction.do?proc=areEmpsDurServ",{selectedIds:selectedIds},function(data){
					if(data!=''){
						// ���ڹ�Ա�ڷ�������
						$("#messageWrapper").html('<div class="message error fadable">��ԱIDΪ :'+data+'�ڷ�������,���ܱ�ɾ�� <a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
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
	 	
	})(jQuery);
		
// 	kanThinking_column('clientName', 'clientId', 'clientAction.do?proc=list_object_json');

	function importExcel(){
		$('#modalId').removeClass('hide');$('#shield').show();//employeeAction.do
	}
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, "HRO_BIZ_EMPLOYEE" ) %>
	
	
</script>
