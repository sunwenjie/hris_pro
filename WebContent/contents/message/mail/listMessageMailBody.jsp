<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.message.MessageMailAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="messageMail-information">
		<div class="head">
			<label><bean:message bundle="message" key="message.mail.search.title" /></label>
		</div>
		<!-- inner -->
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listmessageMail_form', 'searchObject', null, null, null, null);" />	
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="messageMailAction.do?proc=list_object" styleClass="listmessageMail_form">
				<input type="hidden" name="mailId" id="mailId" value="" />
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="messageMailHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="messageMailHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="messageMailHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="messageMailHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.mail.subject" /></label> 
							<html:text property="title" maxlength="25" styleClass="search_messageMail_title"/> 
						</li>
						<li>
							<label><bean:message bundle="message" key="message.mail.reception" /></label> 
							<html:text property="reception" maxlength="25" styleClass="search_messageMail_reception" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="search_messageMail_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.mail.create.date.after" /></label> 
							<input type="text" class="afterCreateDate Wdate" id="afterCreateDate" name="afterCreateDate" readonly="readonly"  value="<bean:write name="messageMailHolder" property="object.afterCreateDate" />"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'beforeCreateDate\')||\'%y-%M-%d\'}'})"/>
						</li>
						<li>
							<label><bean:message bundle="message" key="message.mail.create.date.before" /></label> 
							<input type="text" class="beforeCreateDate Wdate" id="beforeCreateDate" name="beforeCreateDate" readonly="readonly" value="<bean:write name="messageMailHolder" property="object.beforeCreateDate" />" onFocus="WdatePicker({maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'afterCreateDate\')}'})"/>
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
			<kan:auth right="new" action="<%=MessageMailAction.accessAction%>">
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('messageMailAction.do?proc=to_objectNew');" /> 
			</kan:auth>
			<kan:auth right="delete" action="<%=MessageMailAction.accessAction%>">
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listmessageMail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- incloud table jsp 包含tabel对应的jsp文件-->  
			<jsp:include page="/contents/message/mail/table/listMessageMailTable.jsp" flush="true"/> 
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
		$('#menu_message_Mail').addClass('selected');
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
		$('.search_messageMail_title').val('');
		$('.search_messageMail_reception').val('');
		$('.search_messageMail_status').val('0');
		$('.afterCreateDate').val('');
		$('.beforeCreateDate').val('');
	};
	
	function cancleSend(mailId){
		sendHelp(mailId,"cancelSend","取消发送成功！");
	};
	
	function continueSend(mailId){
		sendHelp(mailId,"continueSend","操作成功，系统稍后发送！");
	};
	
	function sendHelp(mailId,subAction,message){
		$("#subAction").val(subAction);
		$("#mailId").val(mailId);
		var url = $(".listmessageMail_form" ).attr('action')+'&' + getFormString("listmessageMail_form") + '&ajax=true&date=' + new Date();
		$.ajax({
			url:url,
			success:function (html){
				$("#tableWrapper").html(html);
				if(!message) message = "操作成功！";
				$("#messageWrapper").html('<div class="message success fadable">'+message+'<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
				messageWrapperFada();
				$("#subAction").val('');$("#mailId").val('');
				
				kanList_init();
				kanCheckbox_init();
			}
		});
	};
	
	function pageForward() {
		var value = Number($('#forwardPage_render').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage_render = Number($('#forwardPage_render').val()) - 1;
		submitForm('listmessageMail_form', null, forwardPage_render, null, null, 'tableWrapper');
	};
</script>