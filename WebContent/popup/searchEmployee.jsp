<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="employeeModalId">
    <div class="modal-header" id="employeeHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="$('#employeeModalId').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label>
        	<logic:equal name="role" value="1"><bean:message bundle="business" key="business.employee1.search" /></logic:equal>
        	<logic:notEqual name="role" value="1"><bean:message bundle="business" key="business.employee2.search" /></logic:notEqual>
        </label>
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
			<input type="hidden" name="ignoreDataAuth" id="ignoreDataAuth" value="false" />
			<input type="hidden" name="forward" id="forward" value="popupTable" />
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
						<label><bean:message bundle="public" key="public.certificate.number" /></label>
						<input type="text" name="certificateNumber" maxlength="20" class="searchEmployee_certificateNumber" />
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
					<li>
						<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.short.name" /></logic:equal>
							<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.short.name" /></logic:notEqual>
						</label>
						<input type="text" name="remark1" maxlength="100" class="searchEmployee_remark1" />
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
    <div class="modal-body-appand">
		<div id="employeeTableWrapper">
			<logic:present name="employeeHolder">
				<!-- 包含Tab页面 -->
				<jsp:include page="/popup/table/searchEmployeeTable.jsp"></jsp:include>
			</logic:present>
		</div>
	</div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 搜索区域重置
	function resetEmployeeSearch(){
		$('.searchEmployee_employeeId').val('');
		$('.searchEmployee_certificateNumber').val('');
		$('.searchEmployee_nameZH').val('');
		$('.searchEmployee_nameEN').val('');
		$('.searchEmployee_remark1').val('');
	};
	
	// “确定”选择事件
	function selectEmployee(){
		var selectedId = $('#employeeTableWrapper input[id^="kanList_chkSelectRecord_"]:checked').val();
		
		if(selectedId != null && selectedId != ''){
			$('#employeeId').val( selectedId );
			$('#employeeId').trigger('keyup');
			$('#employeeModalId').addClass('hide');
			$('#shield').hide();
			// 清除错误消息
			$('#employeeId').next('label').remove();
			$('#employeeId').next('label').removeClass('error');
		}else{
			alert('<bean:message bundle="public" key="popup.not.selected.records" />');
		}
	};

	// 验证数据
	function checkSearchEmployee(){
		var flag = false;
		
		// 验证下拉框数据
		$('select[class^="searchEmployee_"]').each(function(){
			if($(this).val() && $(this).val() != "0"){
		 		flag = true;
				return false;
			}
		});
		
		// 验证文本框数据
		$('input[class^="searchEmployee_"]').each(function(){
			<logic:equal name="role" value="2">
				if($(this).attr('class') != 'searchEmployee_clientId'){
			</logic:equal>
			if($(this).val()){
		 		flag = true;
				return false;
			}
			<logic:equal name="role" value="2">
				}
			</logic:equal>
		});

		if(!flag){
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
	function popupEmployeeSearch(){
		$('#employeeModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#employeeModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>