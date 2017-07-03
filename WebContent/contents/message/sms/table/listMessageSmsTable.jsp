<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.actions.message.MessageSmsAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder messageSmsHolder = (PagedListHolder) request.getAttribute("messageSmsHolder");
%>

<table class="table hover" id="resultTable">
	<thead>  
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 12%" class="header <%=messageSmsHolder.getCurrentSortClass("reception")%>">
				<a onclick="submitForm('listmessageSms_form', null, null, 'reception', '<%=messageSmsHolder.getNextSortOrder("reception")%>', 'tableWrapper');"><bean:message bundle="message" key="message.sms.reception" /></a>
			</th>
			<th style="width: 36%" class="header <%=messageSmsHolder.getCurrentSortClass("content")%>">
				<a onclick="submitForm('listmessageSms_form', null, null, 'content', '<%=messageSmsHolder.getNextSortOrder("content")%>', 'tableWrapper');"><bean:message bundle="message" key="message.sms.content" /></a>
			</th>
			<th style="width: 12%" class="header <%=messageSmsHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('listmessageSms_form', null, null, 'createDate', '<%=messageSmsHolder.getNextSortOrder("createDate")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.create.date" /></a>
			</th>
			<th style="width: 12%" class="header <%=messageSmsHolder.getCurrentSortClass("sentTime")%>">
				<a onclick="submitForm('listmessageSms_form', null, null, 'sentTime', '<%=messageSmsHolder.getNextSortOrder("sentTime")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.send.out.time" /></a>
			</th>
			<th style="width: 8%" class="header <%=messageSmsHolder.getCurrentSortClass("sendingTimes")%>">
				<a onclick="submitForm('listmessageSms_form', null, null, 'sendingTimes', '<%=messageSmsHolder.getNextSortOrder("sendingTimes")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.send.out.frequence" /></a>
			</th>
			<th style="width: 12%" class="header <%=messageSmsHolder.getCurrentSortClass("lastSendingTime")%>">
				<a onclick="submitForm('listmessageSms_form', null, null, 'lastSendingTime', '<%=messageSmsHolder.getNextSortOrder("lastSendingTime")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.last.send.out.time" /></a>
			</th>
			<th style="width: 10%" class="header <%=messageSmsHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listmessageSms_form', null, null, 'status', '<%=messageSmsHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="messageSmsHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="messageSmsVO" name="messageSmsHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal value="4" name="messageSmsVO" property="status">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="messageSmsVO" property="smsId"/>" name="chkSelectRow[]" value="<bean:write name="messageSmsVO" property="smsId"/>" />
						</logic:equal>
					</td>
					<td><bean:write name="messageSmsVO" property="reception" /></td>
					<td class="left">
					<kan:auth right="modify" action="<%=MessageSmsAction.accessAction%>">
					<a onclick="link('messageSmsAction.do?proc=to_objectModify&smsId=<bean:write name="messageSmsVO" property="encodedId"/>');">
					</kan:auth>
					<bean:write name="messageSmsVO" property="content" />
					<kan:auth right="modify" action="<%=MessageSmsAction.accessAction%>">
					</a>
					</kan:auth>
					</td>
					<td class="left"><bean:write name="messageSmsVO" property="decodeCreateDate" /></td>
					<td class="left"><bean:write name="messageSmsVO" property="decodeSentTime" /></td>
					<td class="left"><bean:write name="messageSmsVO" property="sendingTimes" /></td>
					<td class="left"><bean:write name="messageSmsVO" property="decodeLastSendingTime" /></td>
					<td class="left"><bean:write name="messageSmsVO" property="decodeStatus" />
						<logic:equal name="messageSmsVO" property="status" value="1">(<a  onclick="cancleSend('<bean:write name="messageSmsVO" property="encodedId"/>')"><bean:message bundle="message" key="message.mail.link.pause.send" /></a>)</logic:equal>
						<logic:equal name="messageSmsVO" property="status" value="4">(<a  onclick="continueSend('<bean:write name="messageSmsVO" property="encodedId"/>')"><bean:message bundle="message" key="message.mail.link.continue.send" /></a>)</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="messageSmsHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="messageSmsHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="messageSmsHolder" property="indexStart" /> - <bean:write name="messageSmsHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listmessageSms_form', null, '<bean:write name="messageSmsHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageSms_form', null, '<bean:write name="messageSmsHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageSms_form', null, '<bean:write name="messageSmsHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageSms_form', null, '<bean:write name="messageSmsHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="messageSmsHolder" property="realPage" />/<bean:write name="messageSmsHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>