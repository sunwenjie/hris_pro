<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="messageInfo-information">
		<div class="head">
			<label><bean:message bundle="message" key="message.system.info.received" /></label>
		</div>
		<!-- inner -->
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listmessageInfo_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="messageInfoAction.do?proc=list_receive" styleClass="listmessageInfo_form">
				<fieldset>
					<input type="hidden" name="infoId" id="accountId" value="infoId" />
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="messageInfoHolder" property="sortColumn" />" />
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="messageInfoHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="messageInfoHolder" property="page" />" /> 
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="messageInfoHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.system.info.title" /></label> 
							<html:text property="title" maxlength="25" styleClass="search_messageInfo_title" /> 
						</li>
						<li>
							<label><bean:message bundle="message" key="message.system.info.content" /></label> 
							<html:text property="content" maxlength="25" styleClass="search_messageInfo_content" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="receptionStatus" styleClass="search_messageInfo_status">
								<html:optionsCollection property="receptionStatuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
						<li>
							<label><bean:message bundle="message" key="message.mail.create.date.after" /></label> 
							<input type="text" class="Wdate" id="beginTime" name="searchField.beginTime" readonly="readonly" value="<bean:write name="messageInfoHolder" property="object.searchField.beginTime" />" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{d:-1})||\'%y-%M-\#{%d-1}\'}'})"/>
						</li>
						<li>
							<label><bean:message bundle="message" key="message.mail.create.date.before" /></label> 
							<input type="text" class="Wdate" id="endTime" name="searchField.endTime" readonly="readonly"  value="<bean:write name="messageInfoHolder" property="object.searchField.endTime" />"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:1})}' , maxDate:'%y-%M-%d'})"/>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- User-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<logic:notEqual name="role" value="5">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('messageInfoAction.do?proc=to_objectNew');" /> 
				</logic:notEqual>
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listmessageInfo_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				<input type="button" class="reset" id="btnSetAllReaded" name="btnSetAllReaded" value="<bean:message bundle="public" key="button.mark.as.read" />" onclick="if (kanList_setAllReadedConfirm()) submitForm('listmessageInfo_form', 'setAllReadedObjects', null, null, null, 'tableWrapper',null,'$(\'#selectedIds\').val(\'\')');" />
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper"> 
			<!-- incloud table jsp 包含tabel对应的jsp文件-->  
			<jsp:include page="/contents/message/info/table/listReceiveMessageInfoTable.jsp" flush="true"/> 
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

<script type="text/javascript">
	(function($) {
		$('#menu_message_Modules').addClass('current');
		$('#menu_message_Info').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
		
		$.fn.fn_refresh = function(){submitForm('listmessageInfo_form', null, null, null, null, 'tableWrapper');};

		var refreshTime = 10*1000;//刷新时间10秒
		//定时刷新列表页面
		$().fn_refresh();
		setInterval($().fn_refresh,refreshTime);
		
		$('#filterRecords').after("<a onclick=$().fn_refresh();><img style='float:right;cursor:pointer' title='refresh' alt='立即刷新' src='images/reloadevery.png'></image></a>");

	})(jQuery);

	function resetForm(){
		$('.search_messageInfo_title').val('');
		$('.search_messageInfo_nameEN').val('');
		$('.search_messageInfo_templateType').val('');
		$('.search_messageInfo_contentType').val('');
		$('.search_messageInfo_status').val('');
	};
	
	function kanList_setAllReadedConfirm(){
		var selectedIds = $("#selectedIds").val();
		var selectedIdArray = $("#selectedIds").val().split(",");
		var selectedSize = 0;
		var msg = '<bean:message bundle="public" key="popup.confirm.mark.read.records" />';
		if(selectedIds != ''){
			selectedSize = selectedIdArray.length;
		}else{
			alert('<bean:message bundle="public" key="popup.select.mark.read.records" />');
			return false;
		}
		
		return confirm(msg.replace('X', ' ' + selectedSize + ' '));
	};
</script>