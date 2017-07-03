<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.actions.message.MessageMailAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder messageMailHolder = (PagedListHolder) request.getAttribute("messageMailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>  
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 19%" class="header <%=messageMailHolder.getCurrentSortClass("title")%>">
				<a onclick="submitForm('listmessageMail_form', null, null, 'title', '<%=messageMailHolder.getNextSortOrder("title")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.subject" /></a>
			</th>
			<th style="width: 13%" class="header <%=messageMailHolder.getCurrentSortClass("reception")%>">
				<a onclick="submitForm('listmessageMail_form', null, null, 'reception', '<%=messageMailHolder.getNextSortOrder("reception")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.reception" /></a>
			</th>
			<th style="width: 13%" class="header <%=messageMailHolder.getCurrentSortClass("sentAs")%>">
				<a onclick="submitForm('listmessageMail_form', null, null, 'sentAs', '<%=messageMailHolder.getNextSortOrder("sentAs")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.sender" /></a>
			</th>
			<th style="width: 13%" class="header <%=messageMailHolder.getCurrentSortClass("showName")%>">
				<a onclick="submitForm('listmessageMail_form', null, null, 'showName', '<%=messageMailHolder.getNextSortOrder("showName")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.show.name" /></a>
			</th>
			<th style="width: 12%" class="header <%=messageMailHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('listmessageMail_form', null, null, 'createDate', '<%=messageMailHolder.getNextSortOrder("createDate")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.create.date" /></a>
			</th>
			<th style="width: 12%" class="header <%=messageMailHolder.getCurrentSortClass("sentTime")%>">
				<a onclick="submitForm('listmessageMail_form', null, null, 'sentTime', '<%=messageMailHolder.getNextSortOrder("sentTime")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.send.out.time" /></a>
			</th>
			<th style="width: 8%" class="header <%=messageMailHolder.getCurrentSortClass("sendingTimes")%>">
				<a onclick="submitForm('listmessageMail_form', null, null, 'sendingTimes', '<%=messageMailHolder.getNextSortOrder("sendingTimes")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.send.out.frequence" /></a>
			</th>
			<th style="width: 12%" class="header <%=messageMailHolder.getCurrentSortClass("lastSendingTime")%>">
				<a onclick="submitForm('listmessageMail_form', null, null, 'lastSendingTime', '<%=messageMailHolder.getNextSortOrder("lastSendingTime")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.last.send.out.time" /></a>
			</th>
			<th style="width: 10%" class="header <%=messageMailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listmessageMail_form', null, null, 'status', '<%=messageMailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="messageMailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="messageMailVO" name="messageMailHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
				<td>
						<logic:equal value="4" name="messageMailVO" property="status">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="messageMailVO" property="mailId"/>" name="chkSelectRow[]" value="<bean:write name="messageMailVO" property="mailId"/>" />
						</logic:equal>
						<logic:equal value="5" name="messageMailVO" property="status">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="messageMailVO" property="mailId"/>" name="chkSelectRow[]" value="<bean:write name="messageMailVO" property="mailId"/>" />
						</logic:equal>					
					</td>
					<td>
					<kan:auth right="modify" action="<%=MessageMailAction.accessAction%>">
					<a onclick="link('messageMailAction.do?proc=to_objectModify&mailId=<bean:write name="messageMailVO" property="encodedId"/>');">
					</kan:auth>
					<bean:write name="messageMailVO" property="title" />
					<kan:auth right="modify" action="<%=MessageMailAction.accessAction%>">
					</a>
					</kan:auth>
					</td>
					
					<td class="left"><bean:write name="messageMailVO" property="reception" /></td>
					<td class="left"><bean:write name="messageMailVO" property="showName" /></td>
					<td class="left"><bean:write name="messageMailVO" property="sentAs" /></td>
					<td class="left"><bean:write name="messageMailVO" property="decodeCreateDate" /></td>
					<td class="left"><bean:write name="messageMailVO" property="decodeSentTime"   /></td>
					<td class="left"><bean:write name="messageMailVO" property="sendingTimes" /></td>
					<td class="left"><bean:write name="messageMailVO" property="decodeLastSendingTime" /></td>
					<td class="left"><bean:write name="messageMailVO" property="decodeStatus" />
						<logic:equal name="messageMailVO" property="status" value="1">(<a  onclick="cancleSend('<bean:write name="messageMailVO" property="encodedId"/>')"><bean:message bundle="message" key="message.mail.link.pause.send" /></a>)</logic:equal>
						<logic:equal name="messageMailVO" property="status" value="4">(<a  onclick="continueSend('<bean:write name="messageMailVO" property="encodedId"/>')"><bean:message bundle="message" key="message.mail.link.continue.send" /></a>)</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="messageMailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="messageMailHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="messageMailHolder" property="indexStart" /> - <bean:write name="messageMailHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listmessageMail_form', null, '<bean:write name="messageMailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageMail_form', null, '<bean:write name="messageMailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageMail_form', null, '<bean:write name="messageMailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageMail_form', null, '<bean:write name="messageMailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="messageMailHolder" property="realPage" />/<bean:write name="messageMailHolder" 	property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />£º<input type="text" id="forwardPage_render" class="forwardPage_render" style="width:23px;" value="<bean:write name='messageMailHolder' property='realPage' />" onkeydown="if(event.keyCode == 13){pageForward();}" />Ò³</label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>