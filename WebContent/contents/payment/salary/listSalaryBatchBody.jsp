<%@ page import="com.kan.hro.web.actions.biz.payment.SalaryAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import=" com.kan.hro.web.actions.biz.payment.SalaryAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="salaryBatch-information">
		<div class="head"><label id="itleLable"><bean:message bundle="payment" key="payment.salary.import.batch.search.title" /></label></div>
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="salaryAction.do?proc=list_object" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="salaryBatchHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="salaryBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="salaryBatchHolder" property="page" />" />
				<input type="hidden" name="pageFlag" id="pageFlag" value="batch" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="salaryBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="payment" key="payment.salary.import.batch.id" /></label> 
							<html:text property="batchId" maxlength="12" styleClass="searchSalaryTemp_BatchId" /> 
						</li>
						<li>
							<label><bean:message bundle="payment" key="payment.salary.import.excel.name" /></label> 
							<html:text property="importExcelName" maxlength="12" styleClass="searchSalaryTemp_importExcelName" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSalaryTemp_status">
								<html:optionsCollection property="batchStatuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- paymentBatch-information -->
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
				<kan:auth right="submit" action="<%=SalaryAction.accessAction%>">
					<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="back" action="<%=SalaryAction.accessAction%>">
					<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>

				<kan:auth right="import" action="<%=SalaryAction.accessAction%>">
					<img style='float: right' src='images/import.png' onclick='popupExcelImport();' title='<bean:message bundle="public" key="img.title.tips.import.excel" />' />
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="table/listSalaryBatchTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	
	<jsp:include page="/popup/importExcel.jsp" flush = "true">
		<jsp:param value="true" name="needRemark"/>
		<jsp:param value="HRO_SALARY_HEADER" name="accessAction"/>
		<jsp:param  name="closeRefesh" value="true"/>
	</jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Process').addClass('selected');
		$('#menu_salary_Import').addClass('selected');
		
		$('#searchDiv').hide();
		$('#importExcelTitleLableId').html('<bean:message bundle="payment" key="payment.salary.import" />');
		
	})(jQuery);
	
	// 批准事件
	$('#btnApprove').click(function(){
		if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
			if(confirm('<bean:message bundle="public" key="popup.confirm.submit.selected.batch" />')){
				$('.list_form').attr('action', 'salaryAction.do?proc=submit_salary');
				$('#subAction').val('approveObjects');
				submitForm('list_form');
			}
		}else{
			alert('<bean:message bundle="public" key="popup.not.selected.records" />');
		}
	});
	
	
	$('#btnRollback').click(function(){
		
		if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
			if(confirm('<bean:message bundle="public" key="popup.confirm.return.selected.batch" />')){
				$('.list_form').attr('action', 'salaryAction.do?proc=rollback_salary');
				$('#subAction').val('rollbackObject');
				submitForm('list_form');
			}
		}else{
			alert('<bean:message bundle="public" key="popup.not.selected.records" />');
		}
	});
	
	function resetForm() {
		$(".searchSalaryTemp_BatchId").val("");
		$(".searchSalaryTemp_importExcelName").val("");
		
	};
</script>
