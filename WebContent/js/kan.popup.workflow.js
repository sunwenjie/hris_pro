function popupWorkflow(workflowId){
	if(workflowId){
		$.post("workflowActualStepsAction.do?proc=list_object_ajax",{"workflowId":workflowId},function(data){
			$("#workflowPopupWrapper").html(data);
			$("#shield").show();
			$("#workflowListModalId").show();
		},"text");
	}else{
		alert(lang_en ? "No Workflow!" : "û�й�������");
	}
}

function loadWorkflowSeach( objId, workflowId )
{
	if(workflowId){
		var seekWorkflowHTML_Start = "&nbsp;&nbsp;<a onclick=\"";
		var seekWorkflowHTML_end = "\"><img title=\"" + (lang_en ? "View Workflow Steps" : "�鿴����������") + "\" src=\"images/magnifer.png\"></a>";
		var seekWorkflowHTML_onclickfunction ="popupWorkflow('"+workflowId+"')";
		$("#"+ objId ).after(seekWorkflowHTML_Start+seekWorkflowHTML_onclickfunction+seekWorkflowHTML_end);
	}
}

	
// Esc�����¼� - ���ص�����
$(document).keyup(function(e){
    if (e.keyCode == 27) 
    {
    	$('#workflowListModalId').hide();
    	$('#shield').hide();
    }
});

$(document).ready(function(){
	if($('#footer')){
		$('#footer').before('<div id="workflowPopupWrapper"></div>');
	}else{
		$('.kanMenuDiv menu_s').after('<div id="workflowPopupWrapper"></div>');
	}
});