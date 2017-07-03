<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="importExcelModalId">
    <div class="modal-header" id="clientHeader" title="<bean:message bundle="public" key="button.close" />">
        <a class="close" data-dismiss="modal" onclick="closeModal();">×</a>
        <label id="importExcelTitleLableId"><bean:message bundle="public" key="img.title.tips.import.excel" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
		<input type="button" class="save" name="btnImportExcelFile" id="btnImportExcelFile" value="<bean:message bundle="public" key="button.upload.file" />" />
	    </div>
        <div id="attachmentsDiv"></div>
        <div id="uploadProgressPompDIV"></div>
    </div>
    <div class="modal-footer"></div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	 
	var uploadObjectExcel = createUploadExcel('btnImportExcelFile','uploadFileAction.do?proc=importExcel4Performance','uploadFileAction.do?proc=getStatusMessage','common', 'tempForExcel','');
	$("#btnImportExcelFile").attr("onclick","uploadObjectExcel.submit();");

	// 弹出模态窗口
	function popupExcelImport(){
		$('#importExcelModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// 关闭模态窗口，是否刷新页面
	function closeModal(){
		$('#importExcelModalId').addClass('hide');$('#shield').hide();
		var importSuccess = $('#importSuccess').val();
	    if('1'==importSuccess){
			location.reload();
	    }
	}
	
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