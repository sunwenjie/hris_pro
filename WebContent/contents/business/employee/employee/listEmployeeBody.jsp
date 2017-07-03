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

	 	// 重写 删除事件
	 	$('#btnDelete').attr('onclick','');
	 	$('#btnDelete').click(function(){
	 		employee_deleteValidate();
	 	});
	 	
		//服务期内雇员删除提示		
		var employee_deleteValidate = function(){
			var selectedIds = $("#selectedIds").val();
			var selectedIdArray = $("#selectedIds").val().split(",");
			var selectedSize = 0;
			
			if(selectedIds != ''){
				selectedSize = selectedIdArray.length;
				// 判断是否有雇员在服务期内
				$.post("employeeAction.do?proc=areEmpsDurServ",{selectedIds:selectedIds},function(data){
					if(data!=''){
						// 存在雇员在服务期内
						$("#messageWrapper").html('<div class="message error fadable">雇员ID为 :'+data+'在服务期内,不能被删除 <a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
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
