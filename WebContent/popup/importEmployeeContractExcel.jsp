<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="importExcelModalId">
    <div class="modal-header" title="<bean:message bundle="public" key="button.close" />">
        <a class="close" data-dismiss="modal" onclick="closeModal();">×</a>
        <label id="importExcelTitleLableId">Excel <bean:message bundle="business" key="employee.contract.batch.update1" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
    		<div id="importTips" class="message importTips success " style="font-size: 10px;">
				<div style="margin-right: 25px;">
					<bean:message bundle="business" key="employee.contract.batch.update.tips" /><br/><br/>
					<bean:message bundle="business" key="employee.contract.batch.update.tips.c1" /><br/>
					<bean:message bundle="business" key="employee.contract.batch.update.tips.c2" /><br/>
					<bean:message bundle="business" key="employee.contract.batch.update.tips.c3" /><br/>
					<bean:message bundle="business" key="employee.contract.batch.update.tips.c4" />
				</div>
				<div>
					<a onclick="$('#importTips').hide();" class="messageCloseButton" style="top: 5px;">&nbsp;</a>
				</div>
			</div>
			<label style="float: left;"><bean:message bundle="public" key="public.note" />：</label>
				<textarea id="description" name="description" style="width: 400px;height: 100px;"></textarea>
			<br/>
			<br/>
			<input type="button" class="save" name="btnImportExcelFile" id="btnImportExcelFile" value="<bean:message bundle="public" key="button.upload.file" />" />
	    	<input type="button" class="reset" name="btnHandle" id="btnHandle" value="<bean:message bundle="public" key="button.system.operation" />" style="display: none" disabled />
			<input type="button" class="function" name="btnDownload" id="btnDownload" value="<bean:message bundle="public" key="button.download.template" />" onclick="link('employeeContractBatchAction.do?proc=downloadBatchUpdateTemplate');" />
	    </div>
        <div id="attachmentsDiv"></div>
        <div id="uploadProgressPompDIV"></div>
    </div>
    <div class="modal-footer"></div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	 
	var uploadObjectExcel = createUploadExcel('btnImportExcelFile','uploadFileAction.do?proc=importExcel4EmployeeContract&description=' + $('#description').val(),'uploadFileAction.do?proc=getStatusMessage','common', 'tempForExcel','');
	$("#btnImportExcelFile").attr("onclick","uploadObjectExcel.submit();");

	// 弹出模态窗口
	function popupExcelImport(){
		$('#importExcelModalId').removeClass('hide');
    	$('#shield').show();
    	$('#importTips').show();
	};
	
	// 关闭模态窗口，是否刷新页面
	function closeModal(){
		$('#importExcelModalId').addClass('hide');
		$('#shield').hide();
		var importSuccess = $('#importSuccess').val();
	    if('1'==importSuccess){
			location.reload();
	    }
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#importExcelModalId').addClass('hide');
	    	$('#shield').hide();
	    	var importSuccess = $('#importSuccess').val();
	    	if('1'==importSuccess){
				location.reload();
	    	}
	    } 
	});
</script>