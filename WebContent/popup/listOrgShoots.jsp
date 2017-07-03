<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="listOrgShootModalId">
    <div class="modal-header" id="orgChartHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="$('#listOrgShootModalId').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="security" key="security.branch.history.organization.chart" /> </label>
    </div>
    <div class="modal-body">
    	<div class="top" id="list_orgShoot_content">
	   		
	    </div>
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	
	// 弹出模态窗口
	function popupOrgShootList(){
		$('#listOrgShootModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#listOrgShootModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>