<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		 <div class="head">
	        <label id="pageTitle">批量离职</label>
	        <label class="recordId">&nbsp; (ID: <bean:write name="commonBatchVO" property="batchId" />)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchEmployeeContractResign_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="employeeContractResignAction.do?proc=list_object" method="post" styleClass="searchEmployeeContractResign_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeContractResignHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeContractResignHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeContractResignHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeContractResignForm" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="commonBatchVO" property="encodedId" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>员工ID</label>
							<html:text property="employeeId" maxlength="10" styleClass="searchEmployeeContractResign_employeeId" /> 
						</li>
						<li>
							<label>员工姓名（中文）</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchEmployeeContractResign_employeeNameZH" /> 
						</li>
						<li>
							<label>员工姓名（英文）</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchEmployeeContractResign_employeeNameEN" /> 
						</li>
						<li>
							<label>证件号码</label>
							<html:text property="certificateNumber" maxlength="100" styleClass="searchEmployeeContractResign_certificateNumber" /> 
						</li> 
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="inner">
	        <input type="hidden" name="definedMessage" id="definedMessage" value="true" />
	        <div class="top">
		        <input type="button" class="" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				<input type="button" class="delete" name="btnRollback" id="btnRollback" value="退回" />
	            <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<html:form action="sbFeedbackHeaderTempAction.do?proc=list_object" styleClass="listHeaderTemp_form">	
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeContractResignHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeContractResignHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeContractResignHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeContractResignHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="commonBatchVO" property="encodedId" />" />	
	            <fieldset>
            		<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">批次 (ID: <bean:write name="commonBatchVO" property="batchId" />)</li> 
						</ul> 
					</div>
					<div class="tabContent"> 
						<!-- Tab1-Batch Info -->
						<div id="tabContent1" class="kantab" >
			            	<ol class="auto" >
			            		<li><label>批次ID</label><span><bean:write name="commonBatchVO" property="batchId"/></span></li>
			            		<li><label>导入EXCEL名称</label><span><bean:write name="commonBatchVO" property="importExcelName"/></span></li>
			            		<li><label>描述</label><span><bean:write name="commonBatchVO" property="subStrDescription"/></span></li>
			            		<li><label>上传人</label><span><bean:write name="commonBatchVO" property="decodeCreateBy"/></span></li>
			            		<li><label>上传时间</label><span><bean:write name="commonBatchVO" property="decodeCreateDate"/></span></li>
			            	</ol>
			           	 </div>
	                	<!-- Tab1-Batch Info -->
               		</div>
               	</fieldset>
            </html:form>
	        <!-- 包含社保公积金方案列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="table/listHeaderTempTable.jsp"></jsp:include>          
			</div>
			<div class="bottom"><p/></div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_Contract_Resign').addClass('selected');
		
		// JS of the List
		kanList_init();
		kanCheckbox_init();

		// 批次更新事件
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定提交选中的项目？")){
					submitForm('searchEmployeeContractResign_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要提交的项目。");
			}
		});
		
		// 批次退回事件
		$('#btnRollback').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回选中的项目？")){
// 					submitForm('searchEmployeeContractResign_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('.searchEmployeeContractResign_form').attr('action', 'employeeContractResignAction.do?proc=rollback_batch');
					submitForm('searchEmployeeContractResign_form');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要退回的项目。");
			}
		});
		// 列表按钮返回list页面
		$('#btnList').click(function(){
			if (agreest())
			link('employeeContractResignImportAction.do?proc=list_object');
		});
		

		<%
		final Boolean messageInfo = (Boolean) request.getAttribute("messageInfo");
			if(messageInfo!=null && messageInfo){
		%>
			$('#messageWrapper').html('<div class="message success fadable">批次所有数据已退回。<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
	 			messageWrapperFada();
		<%	}%>
	})(jQuery);
	
	function resetForm() {
	    $('.searchEmployeeContractResign_employeeId').val('');
	    $('.searchEmployeeContractResign_sbSolutionId').val('0');
	    $('.searchEmployeeContractResign_monthly').val('');
	};
</script>

