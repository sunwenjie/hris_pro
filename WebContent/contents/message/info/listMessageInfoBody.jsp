<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.actions.message.MessageInfoAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="messageInfo-information">
		<div class="head">
			<label><bean:message bundle="message" key="message.system.info.release" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listmessageInfo_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="messageInfoAction.do?proc=list_object" styleClass="listmessageInfo_form">
				<fieldset>
					<input type="hidden" name="infoId" id="accountId" value="infoId" />
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="messageInfoHolder" property="sortColumn" />" />
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="messageInfoHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="messageInfoHolder" property="page" />" /> 
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="messageInfoHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<ol class="auto" >
						<li>
							<label><bean:message bundle="message" key="message.system.info.recipient" /></label> 
							<html:text property="staffName" maxlength="25" styleClass="search_messageInfo_staffName" /> 
						</li>
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
							<html:select property="status" styleClass="search_messageInfo_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
						<li>
							<label><bean:message bundle="message" key="message.mail.create.date.after" /></label> 
							<input type="text" class="afterCreateDate Wdate" id="afterCreateDate" name="afterCreateDate" readonly="readonly"  value="<bean:write name="messageInfoHolder" property="object.afterCreateDate" />"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'beforeCreateDate\')||\'%y-%M-%d\'}'})"/>
						</li>
						<li>
							<label><bean:message bundle="message" key="message.mail.create.date.before" /></label> 
							<input type="text" class="beforeCreateDate Wdate" id="beforeCreateDate" name="beforeCreateDate" readonly="readonly" value="<bean:write name="messageInfoHolder" property="object.beforeCreateDate" />" onFocus="WdatePicker({maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'afterCreateDate\')}'})"/>
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
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">����ɹ���</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">�༭�ɹ���</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
			
			<kan:auth right="new" action="<%=MessageInfoAction.accessAction%>">
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('messageInfoAction.do?proc=to_objectNew');" /> 
			</kan:auth>	
			
			<kan:auth right="delete" action="<%=MessageInfoAction.accessAction%>">
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listmessageInfo_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</kan:auth>	
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper"> 
			<!-- incloud table jsp ����tabel��Ӧ��jsp�ļ�-->  
			<jsp:include page="/contents/message/info/table/listMessageInfoTable.jsp" flush="true"/> 
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
		
		var refreshTime = 10*1000;//ˢ��ʱ��10��
		//��ʱˢ���б�ҳ��
		setInterval(function(){
			submitForm('listmessageInfo_form', null, null, null, null, 'tableWrapper');
		},refreshTime);
		
	})(jQuery);

	function resetForm(){
		$('.search_messageInfo_reception').val('');
		$('.search_messageInfo_staffName').val('');
		$('.search_messageInfo_title').val('');
		$('.search_messageInfo_content').val('');
		$('.search_messageInfo_status').val('');
		$('.beforeCreateDate').val('');
		$('.afterCreateDate').val('');
	};
	
	function pageForward() {
		var value = Number($('#forwardPage_render').val());
		// ���ҳ����Ч�Զ���ת����һҳ
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage_render = Number($('#forwardPage_render').val()) - 1;
		submitForm('listmessageInfo_form', null, forwardPage_render, null, null, 'tableWrapper');
	};
</script>