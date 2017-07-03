<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.actions.message.MessageSmsAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="messageSms-information">
		<div class="head">
			<label><bean:message bundle="message" key="message.sms.search.title" /></label>
		</div>
		<!-- inner -->
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listmessageSms_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="messageSmsAction.do?proc=list_object" styleClass="listmessageSms_form">
				<input type="hidden" name="smsId" id="smsId" value="" />
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="messageSmsHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="messageSmsHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="messageSmsHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="messageSmsHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.sms.reception" /></label> 
							<html:text property="reception" maxlength="25" styleClass="search_messageSms_reception" /> 
						</li>
						<li>
							<label><bean:message bundle="message" key="message.sms.content" /></label> 
							<html:text property="content" maxlength="25" styleClass="search_messageSms_content" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="search_messageSms_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.mail.create.date.after" /></label> 
							<input type="text" class="afterCreateDate Wdate" id="afterCreateDate" name="afterCreateDate" readonly="readonly"  value="<bean:write name="messageSmsHolder" property="object.afterCreateDate" />"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'beforeCreateDate\')||\'%y-%M-%d\'}'})"/>
						</li>
						<li>
							<label><bean:message bundle="message" key="message.mail.create.date.before" /></label> 
							<input type="text" class="beforeCreateDate Wdate" id="beforeCreateDate" name="beforeCreateDate" readonly="readonly" value="<bean:write name="messageSmsHolder" property="object.beforeCreateDate" />" onFocus="WdatePicker({maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'afterCreateDate\')}'})"/>
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
			<kan:auth right="new" action="<%=MessageSmsAction.accessAction%>">
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('messageSmsAction.do?proc=to_objectNew');" /> 
			</kan:auth>
			<kan:auth right="delete" action="<%=MessageSmsAction.accessAction%>">			
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listmessageSms_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</kan:auth>	
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper"> 
			<!-- incloud table jsp 包含tabel对应的jsp文件-->  
			<jsp:include page="/contents/message/sms/table/listMessageSmsTable.jsp" flush="true"/> 
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
		$('#menu_message_SMS').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
		
		var refreshTime = 10*1000;//刷新时间10秒
		//定时刷新列表页面
		setInterval(function(){
			submitForm('listmessageMail_form', null, null, null, null, 'tableWrapper');
		},refreshTime);
	})(jQuery);

	function resetForm(){
		$('.search_messageSms_reception').val('');
		$('.search_messageSms_content').val('');
		$('.search_messageSms_status').val('0');
		$('.afterCreateDate').val('');
		$('.beforeCreateDate').val('');
	};
	
	function cancleSend(smsId){
		sendHelp(smsId,"cancelSend","取消发送成功！");
	}
	function continueSend(smsId){
		sendHelp(smsId,"continueSend","操作成功，系统稍后发送！");
	}
	
	function sendHelp(smsId,subAction,message){
		$("#subAction").val(subAction);
		$("#smsId").val(smsId);
		var url = $(".listmessageSms_form" ).attr('action') + getFormString("listmessageSms_form") + '&ajax=true&date=' + new Date();
		$.ajax({
			url:url,
			success:function (html){
				$("#tableWrapper").html(html);
				if(!message) message = "操作成功！";
				$("#messageWrapper").html('<div class="message success fadable">'+message+'<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
				messageWrapperFada();
				$("#subAction").val('');$("#smsId").val('');
				
				kanList_init();
				kanCheckbox_init();
			}
		});
	}
</script>