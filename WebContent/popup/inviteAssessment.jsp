<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div class="modal midsize content hide" id="invite-employee-modal">
	<div class="modal-header" id="employeeHeader"> 
		<a class="close" data-dismiss="modal" onclick="popupInviteBox(false);" title="<bean:message bundle="public" key="button.close" />">×</a>
		<label><bean:message bundle='performance' key='invite.peer.assessment' /></label>
	</div>
	
	<div class="modal-body">
		<div class="top">
   		 	<input type="button" class="save" name="btnSearch" id="btnSearch" value="<bean:message bundle="public" key="button.search" />" onclick="searchEmployee();" />
    		<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetEmployeeSearch()" value="<bean:message bundle="public" key="button.reset" />" />
   		</div>
   		
   		<html:form action="employeeAction.do?proc=list_object_ajax" styleClass="listEmployee_form">
			<input type="hidden" name="sortColumn" id="sortColumn" value="" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="" />
			<input type="hidden" name="page" id="page" value="" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<input type="hidden" name="ignoreDataAuth" id="ignoreDataAuth" value="true" />
			<input type="hidden" name="forward" id="forward" value="invitePopupTable" />
			<fieldset>
				<ol class="auto">
					<li>
						<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
							<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.id" /></logic:notEqual>
							&nbsp;&nbsp;<img title="<bean:message bundle="public" key="popup.common.id.tips" />" src="images/tips.png"/>
						</label>
						<input type="text" name="employeeId" maxlength="10" class="searchEmployee_employeeId" />
					</li>
					<li>
						<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.short.name" /></logic:equal>
							<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.short.name" /></logic:notEqual>
						</label>
						<input type="text" name="remark1" maxlength="100" class="searchEmployee_remark1" />
					</li>
				</ol>	
				<ol class="auto">	
					<li>
						<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
							<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:notEqual>
						</label>
						<input type="text" name="nameZH" maxlength="100" class="searchEmployee_nameZH" />
					</li>
					<li>
						<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
							<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name.en" /></logic:notEqual>
						</label>
						<input type="text" name="nameEN" maxlength="100" class="searchEmployee_nameEN" />
					</li>
				</ol>
			</fieldset>
		</html:form >
	</div>
	
	<div class="modal-body-appand">
		<div id="employeeTableWrapper">
			<logic:present name="employeeHolder">
				<!-- 包含Tab页面 -->
				<jsp:include page="/popup/table/searchInviteEmployeeTable.jsp"></jsp:include>
			</logic:present>
		</div>
	</div>

</div>

<script type="text/javascript">
	// 搜索区域重置
	function resetEmployeeSearch(){
		$('.searchEmployee_employeeId').val('');
		$('.searchEmployee_nameZH').val('');
		$('.searchEmployee_nameEN').val('');
		$('.searchEmployee_remark1').val('');
	};
	
	// “确定”选择事件
	function selectEmployee(){
		var selectedId = $('#employeeTableWrapper input[id^="kanList_chkSelectRecord_"]:checked').val();
		var assessmentId = '<bean:write name="selfAssessmentForm" property="assessmentId" />';
		var selfEmployeeId = '<bean:write name="selfAssessmentForm" property="employeeId" />';
		if(selectedId != null && selectedId != ''){
			$.ajax({
				url: 'inviteAssessmentAction.do?proc=add_object_ajax',
				data: {"assessmentId" : assessmentId, "inviteEmployeeId" : selectedId, "selfEmployeeId" : selfEmployeeId},
				type: "GET",
				success: function(html){
					popupInviteBox(false);
					load_invite_assessment_list_noParatemer();
					alert(html);
				}
			})
		}else{
			alert('<bean:message bundle="public" key="popup.not.selected.records" />');
		}  
	};

	// 验证数据
	function checkSearchEmployee(){
		var flag = true;
		
		if( $('.searchEmployee_employeeId').val() == '' &&
				$('.searchEmployee_nameZH').val() == '' &&
				$('.searchEmployee_nameEN').val() == '' &&
				$('.searchEmployee_remark1').val() == ''){
			flag = false;
			$('#employeeTableWrapper').html('');
			alert('<bean:message bundle="public" key="popup.not.input.search.condition" />');
		}		
		return flag;
	}
	
	// “搜索”事件
	function searchEmployee(){
		if(checkSearchEmployee()){
			submitForm('listEmployee_form', null, null, 'employeeId', 'desc', 'employeeTableWrapper');
		}
	};
	
	// 弹出模态窗口
	function popupInviteBox(display){
		if(display){
			$('#invite-employee-modal form fieldset input').val('');
			$('#invite-employee-modal').removeClass('hide');
			$('#employeeTableWrapper').html('');
	    	$('#shield').show();
		}else{
			$('#invite-employee-modal').addClass('hide');
	    	$('#shield').hide();
		}
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) {
	    	popupInviteBox(false);
	    }
	});
</script>