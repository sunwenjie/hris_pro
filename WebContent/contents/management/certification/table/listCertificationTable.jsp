<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder certificationHolder = (PagedListHolder) request.getAttribute("certificationHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>							
			<th style="width: 35%" class="header <%=certificationHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listcertification_form', null, null, 'nameZH', '<%=certificationHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.certification.name.cn" /></a>
			</th>
			<th style="width: 35%" class="header <%=certificationHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listcertification_form', null, null, 'nameEN', '<%=certificationHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.certification.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=certificationHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listcertification_form', null, null, 'status', '<%=certificationHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 8%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 12%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>
	<logic:notEqual name="certificationHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="certificationVO" name="certificationHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>">
					<td>
						<logic:equal value="2" name="certificationVO" property="extended">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="certificationVO" property="certificationId"/>" name="chkSelectRow[]" value="<bean:write name="certificationVO" property="certificationId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('certificationAction.do?proc=to_objectModify&certificationId=<bean:write name="certificationVO" property="encodedId"/>');"><bean:write name="certificationVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('certificationAction.do?proc=to_objectModify&certificationId=<bean:write name="certificationVO" property="encodedId"/>');"><bean:write name="certificationVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="certificationVO" property="decodeStatus"/></td>
					<td class="left"><bean:write name="certificationVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="certificationVO" property="decodeModifyDate"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="certificationHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="6" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="certificationHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="certificationHolder" property="indexStart" /> - <bean:write name="certificationHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a onclick="submitForm('listcertification_form', null, '<bean:write name="certificationHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listcertification_form', null, '<bean:write name="certificationHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listcertification_form', null, '<bean:write name="certificationHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listcertification_form', null, '<bean:write name="certificationHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="certificationHolder" property="realPage" />/<bean:write name="certificationHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>