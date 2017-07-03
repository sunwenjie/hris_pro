<%@page import="com.kan.wx.web.actions.QuestionHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<script src="js/ZeroClipboard.js">
//ZeroClipboard.setMoviePath( "js/ZeroClipboard.swf" ); 
</script>


<div id="content">
	<div class="box searchForm toggableForm" id="questionHeader-information">
		<div class="head">
			<label>Q & A</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listQuestionHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="/questionHeaderAction.do?proc=list_object" styleClass="listQuestionHeader_form">
				<input type="hidden" name="headerId" id="headerId" value="" />
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="questionHeaderHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="questionHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="questionHeaderHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="questionHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>Question Title (Chinese)</label>
							<html:text property="titleZH" styleId="searchQustionHeader_titleZH" styleClass="searchQustionHeader_titleZH" />
						</li>
						<li>
							<label>Question Title (English)</label>
							<html:text property="titleEN" styleId="searchQustionHeader_titleEN" styleClass="searchQustionHeader_titleEN" />
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=QuestionHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('questionHeaderAction.do?proc=to_objectNew');" /> 
				</kan:auth>

				<kan:auth right="delete" action="<%=QuestionHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listQuestionHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<jsp:include page="/contents/wx/question/table/listQuestionHeaderTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
			<!-- List Component -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/listAnswer.jsp"></jsp:include>
</div>	

<script type="text/javascript">
	(function($) {
		$('#menu_interaction_Modules').addClass('current');
		$('#menu_interaction_QA').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 重置按钮事件
	function resetForm(){
		$('.searchQustionHeader_titleZH').val('');
		$('.searchQustionHeader_titleEN').val('');
	};
</script>