<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal large content hide" id="assessmentModalId">
    <div class="modal-header" id="assessmentHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="$('#assessmentModalId').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label>
        	<bean:message bundle="performance" key="performance.assessment.progress" />
        </label>
    </div>
    <div class="modal-body">
        
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">

	// 弹出模态窗口
	function popupSelfAssessment(assessmentId){
		$.post("selfAssessmentAction.do?proc=getSelfAssessment_ajax&assessmentId="+assessmentId,{},function(data){
			$("#assessmentModalId .modal-body").html(data);
		},"html");
		$('#assessmentModalId').removeClass('hide');
	    $('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#assessmentModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>