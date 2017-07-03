<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder messageInfoHolder = (PagedListHolder) request.getAttribute("messageInfoHolder");
%>

<table class="table hover" id="resultTable">
	<thead>  
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 12%" class="header <%=messageInfoHolder.getCurrentSortClass("title")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'title', '<%=messageInfoHolder.getNextSortOrder("title")%>', 'tableWrapper');"><bean:message bundle="message" key="message.system.info.title" /></a>
			</th>
			<th style="width: 12%" class="header <%=messageInfoHolder.getCurrentSortClass("createBy")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'createBy', '<%=messageInfoHolder.getNextSortOrder("createBy")%>', 'tableWrapper');"><bean:message bundle="message" key="message.system.info.sender" /></a>
			</th>
			<th style="width: 36%" class="header <%=messageInfoHolder.getCurrentSortClass("content")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'content', '<%=messageInfoHolder.getNextSortOrder("content")%>', 'tableWrapper');"><bean:message bundle="message" key="message.system.info.content" /></a>
			</th>
			<th style="width: 12%" class="header <%=messageInfoHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'createDate', '<%=messageInfoHolder.getNextSortOrder("createDate")%>', 'tableWrapper');"><bean:message bundle="message" key="message.mail.create.date" /></a>
			</th>
			<th style="width: 12%" class="header <%=messageInfoHolder.getCurrentSortClass("expiredTime")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'expiredTime', '<%=messageInfoHolder.getNextSortOrder("expiredTime")%>', 'tableWrapper');"><bean:message bundle="message" key="message.system.info.expired.time" /></a>
			</th>
			<th style="width: 10%" class="header <%=messageInfoHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listmessageInfo_form', null, null, 'status', '<%=messageInfoHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="messageInfoHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="messageInfoVO" name="messageInfoHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="messageInfoVO" property="infoId" value="0">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="messageInfoVO" property="infoId"/>" name="chkSelectRow[]" value="" disabled="disabled" />
						</logic:equal>
						<logic:notEqual name="messageInfoVO" property="infoId" value="0">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="messageInfoVO" property="infoId"/>" name="chkSelectRow[]" value="<bean:write name="messageInfoVO" property="infoId"/>" />
						</logic:notEqual>
					</td>
					<td class="left"><a onclick="link('messageInfoAction.do?proc=to_objectModify&infoId=<bean:write name="messageInfoVO" property="encodedId"/>');">
									<logic:equal value="2" name="messageInfoVO" property="receptionStatus">
										<img src="images/mail.png"></img>
									</logic:equal>
									<logic:notEqual value="2" name="messageInfoVO" property="receptionStatus">
										<img src="images/mail_readed.png"></img>
									</logic:notEqual>
									<bean:write name="messageInfoVO" property="title" /></a></td>
					<td><bean:write name="messageInfoVO" property="decodeCreateBy" /></td>
					<td class="left"><a onclick="link('messageInfoAction.do?proc=to_objectModify&infoId=<bean:write name="messageInfoVO" property="encodedId"/>');"><bean:write name="messageInfoVO" property="content" /></a></td>
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
				<td colspan="9" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="messageInfoHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="messageInfoHolder" property="indexStart" /> - <bean:write name="messageInfoHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listmessageInfo_form', null, '<bean:write name="messageInfoHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageInfo_form', null, '<bean:write name="messageInfoHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageInfo_form', null, '<bean:write name="messageInfoHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageInfo_form', null, '<bean:write name="messageInfoHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="messageInfoHolder" property="realPage" />/<bean:write name="messageInfoHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>