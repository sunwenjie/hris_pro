<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder socialBenefitHeaderHolder = (PagedListHolder) request.getAttribute("socialBenefitHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<logic:equal value="1" name="PAGE_ACCOUNT_ID">
				<th class="checkbox-col">
					<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
				</th>
			</logic:equal>
			<th style="width: 8%" class="header <%=socialBenefitHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listSocialBenefitHeader_form', null, null, 'headerId', '<%=socialBenefitHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.header.id" /></a>
			</th>
			<th style="width: 20%" class="header <%=socialBenefitHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listSocialBenefitHeader_form', null, null, 'nameZH', '<%=socialBenefitHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.header.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=socialBenefitHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listSocialBenefitHeader_form', null, null, 'nameEN', '<%=socialBenefitHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.header.name.en" /></a>
			</th>
			<th style="width: 8%" class="header <%=socialBenefitHeaderHolder.getCurrentSortClass("cityId")%>">
				<a onclick="submitForm('listSocialBenefitHeader_form', null, null, 'cityId', '<%=socialBenefitHeaderHolder.getNextSortOrder("cityId")%>', 'tableWrapper');"><bean:message bundle="public" key="public.province.city" /></a>
			</th>
			<th style="width: 20%" class="header <%=socialBenefitHeaderHolder.getCurrentSortClass("residency")%>">
				<a onclick="submitForm('listSocialBenefitHeader_form', null, null, 'residency', '<%=socialBenefitHeaderHolder.getNextSortOrder("residency")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.header.residency.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=socialBenefitHeaderHolder.getCurrentSortClass("adjustMonth")%>">
				<a onclick="submitForm('listSocialBenefitHeader_form', null, null, 'adjustMonth', '<%=socialBenefitHeaderHolder.getNextSortOrder("adjustMonth")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.header.adjustment.month" /></a>
			</th>
			<th style="width: 8%" class="header <%=socialBenefitHeaderHolder.getCurrentSortClass("makeup")%>">
				<a onclick="submitForm('listSocialBenefitHeader_form', null, null, 'makeup', '<%=socialBenefitHeaderHolder.getNextSortOrder("makeup")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.header.makeup" /></a>
			</th>
			<th style="width: 8%" class="header <%=socialBenefitHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listSocialBenefitHeader_form', null, null, 'status', '<%=socialBenefitHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="socialBenefitHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="socialBenefitHeaderVO" name="socialBenefitHeaderHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<logic:equal value="1" name="PAGE_ACCOUNT_ID">
						<td>
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="socialBenefitHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="socialBenefitHeaderVO" property="headerId"/>" />
						</td>
					</logic:equal>
					<td class="left"><bean:write name="socialBenefitHeaderVO" property="headerId"/></td>
					<td class="left">
						<a onclick="link('socialBenefitDetailAction.do?proc=list_object&id=<bean:write name="socialBenefitHeaderVO" property="encodedId"/>');"><bean:write name="socialBenefitHeaderVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('socialBenefitDetailAction.do?proc=list_object&id=<bean:write name="socialBenefitHeaderVO" property="encodedId"/>');"><bean:write name="socialBenefitHeaderVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="socialBenefitHeaderVO" property="decodeCity"/></td>
					<td class="left"><bean:write name="socialBenefitHeaderVO" property="decodeResidency"/></td>
					<td class="left"><bean:write name="socialBenefitHeaderVO" property="decodeAdjustMonth"/></td>
					<td class="left"><bean:write name="socialBenefitHeaderVO" property="decodeMakeup"/></td>
					<td class="left"><bean:write name="socialBenefitHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="socialBenefitHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="socialBenefitHeaderHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="socialBenefitHeaderHolder" property="indexStart" /> - <bean:write name="socialBenefitHeaderHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listSocialBenefitHeader_form', null, '<bean:write name="socialBenefitHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSocialBenefitHeader_form', null, '<bean:write name="socialBenefitHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSocialBenefitHeader_form', null, '<bean:write name="socialBenefitHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSocialBenefitHeader_form', null, '<bean:write name="socialBenefitHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="socialBenefitHeaderHolder" property="realPage" />/<bean:write name="socialBenefitHeaderHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>