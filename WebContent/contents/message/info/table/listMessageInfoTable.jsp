<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.actions.message.MessageInfoAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder messageInfoHolder = (PagedListHolder) request.getAttribute("messageInfoHolder");
%>

<table class="table hover" id="resultTable">
	<thead>  
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 10%" class="header <%=messageInfoHolder.getCurrentSortClass("reception")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'reception', '<%=messageInfoHolder.getNextSortOrder("reception")%>', 'tableWrapper');"><bean:message bundle="message" key="message.system.info.recipient" /></a>
			</th>
			<th style="width: 15%" class="header <%=messageInfoHolder.getCurrentSortClass("title")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'title', '<%=messageInfoHolder.getNextSortOrder("title")%>', 'tableWrapper');"><bean:message bundle="message" key="message.system.info.title" /></a>
			</th>
			<th style="width: 37%" class="header <%=messageInfoHolder.getCurrentSortClass("content")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'content', '<%=messageInfoHolder.getNextSortOrder("content")%>', 'tableWrapper');"><bean:message bundle="message" key="message.system.info.content" /></a>
			</th>
			<th style="width: 15%" class="header <%=messageInfoHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'createDate', '<%=messageInfoHolder.getNextSortOrder("createDate")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.create.date" /></a>
			</th>
			<th style="width: 15%" class="header <%=messageInfoHolder.getCurrentSortClass("expiredTime")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'expiredTime', '<%=messageInfoHolder.getNextSortOrder("expiredTime")%>', 'tableWrapper');"><bean:message bundle="message" key="message.system.info.expired.time" /></a>
			</th>
			<th style="width: 8%" class="header <%=messageInfoHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'status', '<%=messageInfoHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="messageInfoHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="messageInfoVO" name="messageInfoHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal value="1" name="messageInfoVO" property="status" >
							<logic:equal name="messageInfoVO" property="infoId" value="0">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="messageInfoVO" property="infoId"/>" name="chkSelectRow[]" value="" disabled="disabled" />
							</logic:equal>
							<logic:notEqual name="messageInfoVO" property="infoId" value="0">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="messageInfoVO" property="infoId"/>" name="chkSelectRow[]" value="<bean:write name="messageInfoVO" property="infoId"/>" />
							</logic:notEqual>
						</logic:equal>
					</td>
					<td><bean:write name="messageInfoVO" property="staffName" /></td>
					<td class="left">
					<kan:auth right="modify"  action="<%=MessageInfoAction.accessAction%>">
					<a onclick="link('messageInfoAction.do?proc=to_objectModify&infoId=<bean:write name="messageInfoVO" property="encodedId"/>');">
					</kan:auth>
					<bean:write name="messageInfoVO" property="title" />
					<kan:auth right="modify"  action="<%=MessageInfoAction.accessAction%>">
					</a>
					</kan:auth>
					</td>
					<td class="left">
					<kan:auth right="modify"  action="<%=MessageInfoAction.accessAction%>">
					<a onclick="link('messageInfoAction.do?proc=to_objectModify&infoId=<bean:write name="messageInfoVO" property="encodedId"/>');">
					</kan:auth>
					<bean:write name="messageInfoVO" property="content" />
					<kan:auth right="modify"  action="<%=MessageInfoAction.accessAction%>">
					</a>
					</kan:auth>
					</td>
					<td class="left"><bean:write name="messageInfoVO" property="decodeCreateDate" /></td>
					<td class="left"><bean:write name="messageInfoVO" property="decodeExpiredTime" /></td>
					<td class="left"><bean:write name="messageInfoVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="messageInfoHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="messageInfoHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="messageInfoHolder" property="indexStart" /> - <bean:write name="messageInfoHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listmessageInfo_form', null, '<bean:write name="messageInfoHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageInfo_form', null, '<bean:write name="messageInfoHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageInfo_form', null, '<bean:write name="messageInfoHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageInfo_form', null, '<bean:write name="messageInfoHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="messageInfoHolder" property="realPage" />/<bean:write name="messageInfoHolder" 	property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />£º<input type="text" id="forwardPage_render" class="forwardPage_render" style="width:23px;" value="<bean:write name='messageInfoHolder' property='realPage' />" onkeydown="if(event.keyCode == 13){pageForward();}" />Ò³</label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>