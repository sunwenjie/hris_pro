<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import=" com.kan.base.web.actions.management.BankAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder bankHolder = (PagedListHolder) request.getAttribute("bankHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header <%=bankHolder.getCurrentSortClass("bankId")%>">
				<a onclick="submitForm('listBank_form', null, null, 'bankId', '<%=bankHolder.getNextSortOrder("bankId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.bank.id" /></a>
			</th>
			<th style="width: 20%" class="header <%=bankHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listBank_form', null, null, 'nameZH', '<%=bankHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.bank.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=bankHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listBank_form', null, null, 'nameEN', '<%=bankHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.bank.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=bankHolder.getCurrentSortClass("cityId")%>">
				<a onclick="submitForm('listBank_form', null, null, 'cityId', '<%=bankHolder.getNextSortOrder("cityId")%>', 'tableWrapper');"><bean:message bundle="public" key="public.city" /></a>
			</th>
			<th style="width: 17%" class="header <%=bankHolder.getCurrentSortClass("telephone")%>">
				<a onclick="submitForm('listBank_form', null, null, 'telephone', '<%=bankHolder.getNextSortOrder("telephone")%>', 'tableWrapper');"><bean:message bundle="management" key="management.bank.telephone" /></a>
			</th>
			<th style="width: 17%" class="header <%=bankHolder.getCurrentSortClass("website")%>">
				<a onclick="submitForm('listBank_form', null, null, 'website', '<%=bankHolder.getNextSortOrder("website")%>', 'tableWrapper');"><bean:message bundle="management" key="management.bank.website" /></a>
			</th>
			<th style="width: 8%" class="header <%=bankHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listBank_form', null, null, 'status', '<%=bankHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="bankHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="bankVO" name="bankHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<!-- Super -->
						<logic:equal name="accountId" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="bankVO" property="bankId"/>" name="chkSelectRow[]" value="<bean:write name="bankVO" property="bankId"/>" />
						</logic:equal>
						
						<!-- Account -->
						<logic:notEqual name="accountId" value="1">
							<logic:equal name="bankVO" property="accountId" value="1">
								<input type="checkbox" disabled="disabled" />
							</logic:equal>
							<logic:notEqual name="bankVO" property="accountId" value="1">
								<logic:equal name="bankVO" property="extended" value="2">
									<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="bankVO" property="bankId"/>" name="chkSelectRow[]" value="<bean:write name="bankVO" property="bankId"/>" />
								</logic:equal>
							</logic:notEqual>
						</logic:notEqual>
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=BankAction.accessAction%>">
						<a onclick="link('bankAction.do?proc=to_objectModify&id=<bean:write name="bankVO" property="encodedId"/>');">
					</kan:auth>	
						<bean:write name="bankVO" property="bankId"/>
					<kan:auth right="modify" action="<%=BankAction.accessAction%>">	
						</a>
					</kan:auth>	
					</td>
					<td class="left"><bean:write name="bankVO" property="nameZH"/></td>
					<td class="left"><bean:write name="bankVO" property="nameEN"/></td>
					<td class="left"><bean:write name="bankVO" property="decodeCity"/></td>
					<td class="left"><bean:write name="bankVO" property="telephone"/></td>
					<td class="left"><bean:write name="bankVO" property="website"/></td>
					<td class="left"><bean:write name="bankVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="bankHolder">
		<tfoot>
			<tr class="total">
			  	<td colspan="9" class="left"> 
				  	<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="bankHolder" property="holderSize" /></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="bankHolder" property="indexStart" /> - <bean:write name="bankHolder" property="indexEnd" /></label>
				  	<label>&nbsp;&nbsp;<a onclick="submitForm('listBank_form', null, '<bean:write name="bankHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listBank_form', null, '<bean:write name="bankHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listBank_form', null, '<bean:write name="bankHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listBank_form', null, '<bean:write name="bankHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="bankHolder" property="realPage" />/<bean:write name="bankHolder" property="pageCount" /></label>&nbsp;
				 </td>					
		   </tr>
		</tfoot>
	</logic:present>
</table>