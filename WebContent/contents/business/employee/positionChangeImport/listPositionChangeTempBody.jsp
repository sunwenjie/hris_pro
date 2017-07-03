<%@	page import="com.kan.hro.web.actions.biz.employee.EmployeePositionChangeBatchAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="employeePositionChangeBatch-information">
		<div class="head">
			<label id="itleLable">
				<bean:message bundle="business" key="employee.position.adjustment.import.batch" />
			</label>
		</div>
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions" />">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('positionChangeTemp_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeePositionChangeTempAction.do?proc=list_object" styleClass="positionChangeTemp_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeePositionChangeTempHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeePositionChangeTempHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeePositionChangeTempHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeePositionChangeTempHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="employeePositionChangeTempForm" property="batchId" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.employee2.id" /></label>
							<html:text property="employeeId" maxlength="50" styleClass="searchPositionChange_employeeId" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.no" /></label>
							<html:text property="employeeNo" maxlength="50" styleClass="searchPositionChange_employeeNo" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.name.cn" /></label> 
							<html:text property="employeeNameZH" maxlength="50" styleClass="searchPositionChange_employeeNameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.name.en" /></label> 
							<html:text property="employeeNameEN" maxlength="50" styleClass="searchPositionChange_employeeNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.certificate.number" /></label>
							<html:text property="employeeCertificateNumber" maxlength="50" styleClass="searchPositionChange_employeeCertificateNumber" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchPositionChange_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="submit" action="<%=EmployeePositionChangeBatchAction.ACCESS_ACTION%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>	
				<kan:auth right="back" action="<%=EmployeePositionChangeBatchAction.ACCESS_ACTION%>">	
					<input type="button" class="delete" name="btnDelete" id="btnDelete" value="<bean:message bundle="public" key="button.return" />" />
				</kan:auth>
				<kan:auth right="list" action="<%=EmployeePositionChangeBatchAction.ACCESS_ACTION%>">	
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="link('employeePositionChangeBatchAction.do?proc=list_object')" />
				</kan:auth>		
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
				<jsp:include page="/contents/business/employee/positionChangeImport/table/listPositionChangeTempTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/importEmployeePositionChange.jsp" flush = "true">
		<jsp:param name="accessAction" value="<%=EmployeePositionChangeBatchAction.ACCESS_ACTION %>"/>
		<jsp:param name="needRemark" value="true"/>
		<jsp:param name="closeRefesh" value="true"/>
	</jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// ���ö����˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_batch_position_change').addClass('selected');
		
		$('#searchDiv').hide();
		
		// ���θ����¼�
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.selected.batch" />')){
					$('.positionChangeTemp_form').attr('action', 'employeePositionChangeTempAction.do?proc=submit_temp');
					submitForm('positionChangeTemp_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		// �����˻��¼�
		$('#btnDelete').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.selected.batch" />')){
					$('.positionChangeTemp_form').attr('action', 'employeePositionChangeTempAction.do?proc=rollback_temp');
					submitForm('positionChangeTemp_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
	})(jQuery);
	
	function resetForm() {
	   var oForm = $('.positionChangeTemp_form');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').val('0');
	};
</script>