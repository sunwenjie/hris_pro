<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="descriptionModalId">
    <div class="modal-header" id="descriptionHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="popupClose();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label><bean:message bundle="workflow" key="workflow.audit.opinion" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnConfirm" id="btnConfirm" value="<bean:message bundle="public" key="button.confirm" />" onclick="searchVendor();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="popupClose();" value="<bean:message bundle="public" key="button.cancel" />" />
	    </div>
	    <form class="tempForm">
		<fieldset>
			<ol>
				<li class="auto">
					<label><bean:message bundle="workflow" key="workflow.audit.opinion" /></label>
					<textarea name="tempDescription" class="tempDescription" id="tempDescription" ></textarea>
				</li>
			</ol>
		</fieldset>
		</form>
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 搜索区域重置
	function resetVendorSearch(){
		$('.searchVendor_descriptionId').val('');
		$('.searchVendor_nameZH').val('');
		$('.searchVendor_nameEN').val('');
	};
	
	// 弹出模态窗口
	function popupDescription(){
		$('#descriptionModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#descriptionModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
	function popupClose(){
		link('workflowActualAction.do?proc=list_object_unfinished&actualStepStatus=2');
	};
</script>