<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		 <div class="head">
	        <label id="pageTitle">������ְ</label>
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
							<label>Ա��ID</label>
							<html:text property="employeeId" maxlength="10" styleClass="searchEmployeeContractResign_employeeId" /> 
						</li>
						<li>
							<label>Ա�����������ģ�</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchEmployeeContractResign_employeeNameZH" /> 
						</li>
						<li>
							<label>Ա��������Ӣ�ģ�</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchEmployeeContractResign_employeeNameEN" /> 
						</li>
						<li>
							<label>֤������</label>
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
				<input type="button" class="delete" name="btnRollback" id="btnRollback" value="�˻�" />
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
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">���� (ID: <bean:write name="commonBatchVO" property="batchId" />)</li> 
						</ul> 
					</div>
					<div class="tabContent"> 
						<!-- Tab1-Batch Info -->
						<div id="tabContent1" class="kantab" >
			            	<ol class="auto" >
			            		<li><label>����ID</label><span><bean:write name="commonBatchVO" property="batchId"/></span></li>
			            		<li><label>����EXCEL����</label><span><bean:write name="commonBatchVO" property="importExcelName"/></span></li>
			            		<li><label>����</label><span><bean:write name="commonBatchVO" property="subStrDescription"/></span></li>
			            		<li><label>�ϴ���</label><span><bean:write name="commonBatchVO" property="decodeCreateBy"/></span></li>
			            		<li><label>�ϴ�ʱ��</label><span><bean:write name="commonBatchVO" property="decodeCreateDate"/></span></li>
			            	</ol>
			           	 </div>
	                	<!-- Tab1-Batch Info -->
               		</div>
               	</fieldset>
            </html:form>
	        <!-- �����籣�����𷽰��б���Ϣ -->
			<div id="tableWrapper">
				<jsp:include page="table/listHeaderTempTable.jsp"></jsp:include>          
			</div>
			<div class="bottom"><p/></div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ö����˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_Contract_Resign').addClass('selected');
		
		// JS of the List
		kanList_init();
		kanCheckbox_init();

		// ���θ����¼�
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���ύѡ�е���Ŀ��")){
					submitForm('searchEmployeeContractResign_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�ύ����Ŀ��");
			}
		});
		
		// �����˻��¼�
		$('#btnRollback').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻�ѡ�е���Ŀ��")){
// 					submitForm('searchEmployeeContractResign_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('.searchEmployeeContractResign_form').attr('action', 'employeeContractResignAction.do?proc=rollback_batch');
					submitForm('searchEmployeeContractResign_form');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�˻ص���Ŀ��");
			}
		});
		// �б�ť����listҳ��
		$('#btnList').click(function(){
			if (agreest())
			link('employeeContractResignImportAction.do?proc=list_object');
		});
		

		<%
		final Boolean messageInfo = (Boolean) request.getAttribute("messageInfo");
			if(messageInfo!=null && messageInfo){
		%>
			$('#messageWrapper').html('<div class="message success fadable">���������������˻ء�<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
	 			messageWrapperFada();
		<%	}%>
	})(jQuery);
	
	function resetForm() {
	    $('.searchEmployeeContractResign_employeeId').val('');
	    $('.searchEmployeeContractResign_sbSolutionId').val('0');
	    $('.searchEmployeeContractResign_monthly').val('');
	};
</script>

