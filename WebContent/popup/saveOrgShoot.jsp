<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="orgShootModalId">
    <div class="modal-header" id="orgChartHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="$('#orgShootModalId').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="security" key="security.branch.snapshot.chart" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save" />" onclick="btnSaveClick();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="btnResetClick()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
	    <div id="orgShootMessageWrapper"></div>
		<form action="shootAction.do?proc=saveOrgShoot" method="post" class="orgShootForm">
		<fieldset>
			<ol class="auto">
				<li>
					<label><bean:message bundle="security" key="security.branch.snapshot.name.cn" /></label> 
					<input type="text" name="nameZH" id="nameZH" maxlength="50" class="orgShoot_nameZH" />
				</li>
				<li>
					<label><bean:message bundle="security" key="security.branch.snapshot.name.en" /></label>
					<input type="text" name="nameEN" id="nameEN" maxlength="50" class="orgShoot_nameEN" />
				</li>
				<li>
					<label><bean:message bundle="public" key="public.note" /></label>
					<textarea name="description" id="description" class="orgShoot_description" ></textarea>
				</li>
			</ol>
		</fieldset>
		</form>
    </div>
    
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 搜索区域重置
	function btnResetClick(){
		$('#nameZH').val('');
		$('#nameEN').val('');
		$('#description').val('');
	};
	
	function btnSaveClick(){
		var pageType = $("#pageType").val();
		var url = "";
		if("branch"==pageType){
			url = "shootAction.do?proc=saveOrgShoot&pageType=branch";
		}else{
			url = "shootAction.do?proc=saveOrgShoot&pageType=position";			
		}
		var nameZH = encodeURI(encodeURI($("#nameZH").val()));
		var nameEN = encodeURI(encodeURI($("#nameEN").val()));
		var description = encodeURI(encodeURI($("#description").val()));
		$.post(url,{'nameZH':nameZH,'nameEN':nameEN,'description':description},function(data){
			if(data=='success'){
				$('#orgShootMessageWrapper').html('<div class="message success fadable">添加成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
				messageWrapperFada();
			}
		},'text');
	}
	
	// 弹出模态窗口
	function popupOrgShootSave(){
		$('#orgShootModalId').removeClass('hide');
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