<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="answerModalId">
    <div class="modal-header" id="vendorHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="$('#answerModalId').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label>Answer List</label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnSearch" id="btnSearch" value="<bean:message bundle="public" key="button.search" />" onclick="searchAnswer();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetAnswer()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="answerAction.do?proc=list_object_ajax" styleClass="listAnswer_form">
			<input type="hidden" name="sortColumn" id="sortColumn" value="" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="" />
			<input type="hidden" name="page" id="page" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<input type="hidden" name="headerId" id="questionHeaderId" value="" />
			<input type="hidden" name="titleNameList" id="titleNameList" value="" />
			<input type="hidden" name="titleIdList" id="titleIdList" value="" />
			<input type="hidden" name="export" id="export" value="2" />
			<fieldset>
				<ol class="auto">
					<li>
						<label>WeChat ID</label>
						<html:text property="weChatId" styleClass="search_weChatId" />
					</li>
					<li>
						<label>Answer</label>
						<html:text property="answer" styleClass="search_answer" />
					</li>
				</ol>
				
			</fieldset>
		</html:form >
    </div>
    <div class="modal-body-appand">
		<div id="answerTableWrapper">
			<logic:present name="answerHolder">
				<!-- 包含Tab页面 -->
				<jsp:include page="/popup/table/listAnswerTable.jsp"></jsp:include>
			</logic:present>
		</div>
	</div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 搜索区域重置
	function resetAnswer(){
		$('.search_weChatId').val('');
		$('.search_answer').val('');
	};
	
	// “搜索”事件 
	function searchAnswer(){
		submitForm('listAnswer_form', null, null, null, null, 'answerTableWrapper');
	};
	
	// 弹出模态窗口
	function showAnswerModal(headerId){
		$('#answerModalId').removeClass('hide');
    	$('#shield').show();
    	$('#questionHeaderId').val(headerId);
    	var cellback = "$('#titleNameList').val(getTabTitle());$('#titleIdList').val(getTabDB());";
    	submitFormWithActionAndCallback('listAnswer_form', null, null, null, null, 'answerTableWrapper', null, cellback);
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#answerModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
	function export_answer(){
		isExport(1);
		submitFormForDownload('listAnswer_form');
		isExport(2);
	};
	
	function isExport( yesOrNo){
		$('#export').val(yesOrNo);
	};
	
	function getTabTitle(){
		var oArray = new Array();
		var oTTFirstTr = $('table thead tr th:visible span');
		oTTFirstTr.each( function (i){
			oArray[i] = $(this).html();
		});
		return oArray;
	};
	
	function getTabDB(){
		var oArray = new Array();
		var oTTFirstTr = $('table thead tr th:visible span');
		oTTFirstTr.each( function (i){
			oArray[i] = $(this).attr('id');
		});
		return oArray;
	};
</script>