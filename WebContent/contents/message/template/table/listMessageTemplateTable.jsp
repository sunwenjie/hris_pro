<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder messageTemplateHolder = (PagedListHolder) request.getAttribute("messageTemplateHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 30%" class="header <%=messageTemplateHolder.getCurrentSortClass("nameZH")%>">
				<a href="#" onclick="submitForm('listmessageTemplate_form', null, null, 'nameZH', '<%=messageTemplateHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="message" key="message.template.name.zh" /></a>
			</th>
			<th style="width: 30%" class="header <%=messageTemplateHolder.getCurrentSortClass("nameEN")%>">
				<a href="#" onclick="submitForm('listmessageTemplate_form', null, null, 'nameEN', '<%=messageTemplateHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="message" key="message.template.name.en" /></a>
			</th>
			<th style="width: 15%" class="header <%=messageTemplateHolder.getCurrentSortClass("templateType")%>">
				<a href="#" onclick="submitForm('listmessageTemplate_form', null, null, 'templateType', '<%=messageTemplateHolder.getNextSortOrder("templateType")%>', 'tableWrapper');"><bean:message bundle="message" key="message.template.type" /></a>
			</th>
			<th style="width: 15%" class="header <%=messageTemplateHolder.getCurrentSortClass("contentType")%>">
				<a href="#" onclick="submitForm('listmessageTemplate_form', null, null, 'contentType', '<%=messageTemplateHolder.getNextSortOrder("contentType")%>', 'tableWrapper');"><bean:message bundle="message" key="message.template.content.type" /></a>
			</th>
			<th style="width: 10%" class="header <%=messageTemplateHolder.getCurrentSortClass("status")%>">
				<a href="#" onclick="submitForm('listmessageTemplate_form', null, null, 'status', '<%=messageTemplateHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /> </a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="messageTemplateHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="messageTemplateVO" name="messageTemplateHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal value="2" name="messageTemplateVO" property="extended">					  
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="messageTemplateVO" property="templateId"/>" name="chkSelectRow[]" value="<bean:write name="messageTemplateVO" property="templateId"/>" />
						</logic:equal>	
					</td>
					<td class="left"><a onclick="link('messageTemplateAction.do?proc=to_objectModify&id=<bean:write name="messageTemplateVO" property="encodedId"/>');"><bean:write name="messageTemplateVO" property="nameZH" /></a></td>
					<td class="left"><a onclick="link('messageTemplateAction.do?proc=to_objectModify&id=<bean:write name="messageTemplateVO" property="encodedId"/>');"><bean:write name="messageTemplateVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="messageTemplateVO" property="decodeTemplateType" /></td>
					<td class="left"><bean:write name="messageTemplateVO" property="decodeContentType" /></td>
					<td class="left"><bean:write name="messageTemplateVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="messageTemplateHolder">
		<tfoot>
			<tr class="total">
				<td colspan="6" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="messageTemplateHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="messageTemplateHolder" property="indexStart" /> - <bean:write name="messageTemplateHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listmessageTemplate_form', null, '<bean:write name="messageTemplateHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageTemplate_form', null, '<bean:write name="messageTemplateHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageTemplate_form', null, '<bean:write name="messageTemplateHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmessageTemplate_form', null, '<bean:write name="messageTemplateHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="messageTemplateHolder" property="realPage" />/<bean:write name="messageTemplateHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>