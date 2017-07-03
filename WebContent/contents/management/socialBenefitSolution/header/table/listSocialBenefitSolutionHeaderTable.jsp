<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder socialBenefitSolutionHeaderHolder = (PagedListHolder) request.getAttribute("socialBenefitSolutionHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header <%=socialBenefitSolutionHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, null, 'headerId', '<%=socialBenefitSolutionHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sb.solution.header.id" /></a>
			</th>
			<th style="width: 18%" class="header <%=socialBenefitSolutionHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, null, 'nameZH', '<%=socialBenefitSolutionHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sb.solution.header.name.cn" /></a>
			</th>
			<th style="width: 18%" class="header <%=socialBenefitSolutionHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, null, 'nameEN', '<%=socialBenefitSolutionHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sb.solution.header.name.en" /></a>
			</th>
			<th style="width: 8%" class="header <%=socialBenefitSolutionHeaderHolder.getCurrentSortClass("cityId")%>">
				<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, null, 'cityId', '<%=socialBenefitSolutionHeaderHolder.getNextSortOrder("cityId")%>', 'tableWrapper');"><bean:message bundle="public" key="public.city" /></a>
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="management" key="management.sb.name" />
			</th>
			<th style="width: 12%" class="header <%=socialBenefitSolutionHeaderHolder.getCurrentSortClass("sbType")%>">
				<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, null, 'sbType', '<%=socialBenefitSolutionHeaderHolder.getNextSortOrder("sbType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sb.solution.header.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=socialBenefitSolutionHeaderHolder.getCurrentSortClass("startDateLimit")%>">
				<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, null, 'startDateLimit', '<%=socialBenefitSolutionHeaderHolder.getNextSortOrder("startDateLimit")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sb.solution.header.start.date.limit" /></a>
			</th>
			<th style="width: 8%" class="header <%=socialBenefitSolutionHeaderHolder.getCurrentSortClass("endDateLimit")%>">
				<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, null, 'endDateLimit', '<%=socialBenefitSolutionHeaderHolder.getNextSortOrder("endDateLimit")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sb.solution.header.end.date.limit" /></a>
			</th>
			<th style="width: 8%" class="header <%=socialBenefitSolutionHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, null, 'status', '<%=socialBenefitSolutionHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="socialBenefitSolutionHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="socialBenefitSolutionHeaderVO" name="socialBenefitSolutionHeaderHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<logic:equal name="socialBenefitSolutionHeaderVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="socialBenefitSolutionHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="socialBenefitSolutionHeaderVO" property="headerId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('socialBenefitSolutionDetailAction.do?proc=list_object&headerId=<bean:write name="socialBenefitSolutionHeaderVO" property="encodedId"/>');"><bean:write name="socialBenefitSolutionHeaderVO" property="headerId"/></a>
					</td>
					<td class="left">
						<a onclick="link('socialBenefitSolutionDetailAction.do?proc=list_object&headerId=<bean:write name="socialBenefitSolutionHeaderVO" property="encodedId"/>');"><bean:write name="socialBenefitSolutionHeaderVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('socialBenefitSolutionDetailAction.do?proc=list_object&headerId=<bean:write name="socialBenefitSolutionHeaderVO" property="encodedId"/>');"><bean:write name="socialBenefitSolutionHeaderVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="socialBenefitSolutionHeaderVO" property="decodeCity"/></td>
					<td class="left"><bean:write name="socialBenefitSolutionHeaderVO" property="decodeSbName"/></td>
					<td class="left"><bean:write name="socialBenefitSolutionHeaderVO" property="decodeSbType"/></td>
					<td class="left"><bean:write name="socialBenefitSolutionHeaderVO" property="decodeStartDateLimit"/></td>
					<td class="left"><bean:write name="socialBenefitSolutionHeaderVO" property="decodeEndDateLimit"/></td>
					<td class="left"><bean:write name="socialBenefitSolutionHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="socialBenefitSolutionHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="socialBenefitSolutionHeaderHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="socialBenefitSolutionHeaderHolder" property="indexStart" /> - <bean:write name="socialBenefitSolutionHeaderHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, '<bean:write name="socialBenefitSolutionHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, '<bean:write name="socialBenefitSolutionHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, '<bean:write name="socialBenefitSolutionHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSocialBenefitSolutionHeader_form', null, '<bean:write name="socialBenefitSolutionHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="socialBenefitSolutionHeaderHolder" property="realPage" />/<bean:write name="socialBenefitSolutionHeaderHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>