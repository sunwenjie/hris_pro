<%@page import="com.kan.base.web.renders.security.PositionGraderListRender"%>
<%@page import="com.kan.base.web.renders.security.BranchListRender"%>
<%@page import="com.kan.base.web.renders.security.GroupRender"%>
<%@page import="com.kan.base.web.renders.security.PositionRender"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<style type="text/css">
	
</style>

<!-- Module Box HTML: Begins -->
<div class="modal large content hide" id="listHistoryStaffId">
    <div class="modal-header" id="contactHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="$('#listHistoryStaffId').addClass('hide');$('#shield').hide();">×</a>
        <label id="listHistoryStaffId_label"></label>
    </div>
    <div class="modal-body">
    	<!-- <div class="top">
	    	
		</div>
	 	-->
		<div class="contact-body historyStaffNameDIV">

		</div>
  </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	
	// 弹出模态窗口
	function popupListHistoryStaff(){
		$('#listHistoryStaffId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#listHistoryStaffId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>