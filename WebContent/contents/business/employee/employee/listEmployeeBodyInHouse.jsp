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
	 	// 删除按钮点击事件
	 	$('#btnDelete').click(function(){
	 		validateForDelete();
	 	});
	})(jQuery);
	
	//服务期内雇员删除提示
	
	function validateForDelete(){
		var selectedIds = $("#selectedIds").val();
		var selectedIdArray = $("#selectedIds").val().split(",");
		var selectedSize = 0;
		
		if(selectedIds != ''){
			selectedSize = selectedIdArray.length;
			// 判断是否有雇员在服务期内
			$.post("employeeAction.do?proc=areEmpsDurServ",{selectedIds:selectedIds},function(data){
				if(data!=''){
					// 存在雇员在服务期内
					$("#messageWrapper").html('<div class="message error fadable">员工ID为 :'+data+'在服务期内,不能被删除 <a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
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
		var js = "if(confirm(\"点击'是'导出员工数据库,点击'否'导出员工基本信息\")){";
		js+="linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction=HRO_BIZ_EMPLOYEE_IN_HOUSE&exportAll=true');";
		js+="}else{linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction=HRO_BIZ_EMPLOYEE_IN_HOUSE');}";
		$("#exportExcel").attr("onclick",js);
	}
</script>
