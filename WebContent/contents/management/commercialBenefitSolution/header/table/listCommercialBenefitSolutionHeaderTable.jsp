<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder commercialBenefitSolutionHeaderHolder = (PagedListHolder) request.getAttribute("commercialBenefitSolutionHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=commercialBenefitSolutionHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, null, 'headerId', '<%=commercialBenefitSolutionHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.header.id" /></a>
			</th>
			<th style="width: 25%" class="header <%=commercialBenefitSolutionHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, null, 'nameZH', '<%=commercialBenefitSolutionHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.header.name.cn" /></a>
			</th>
			<th style="width: 25%" class="header <%=commercialBenefitSolutionHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, null, 'nameEN', '<%=commercialBenefitSolutionHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.header.name.en" /></a>
			</th>
			<th style="width: 12%" class="header <%=commercialBenefitSolutionHeaderHolder.getCurrentSortClass("validFrom")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, null, 'validFrom', '<%=commercialBenefitSolutionHeaderHolder.getNextSortOrder("validFrom")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.header.start.date" /></a>
			</th>
			<th style="width: 12%" class="header <%=commercialBenefitSolutionHeaderHolder.getCurrentSortClass("validEnd")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, null, 'validEnd', '<%=commercialBenefitSolutionHeaderHolder.getNextSortOrder("validEnd")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.header.end.date" /></a>
			</th>
			<th style="width: 8%" class="header <%=commercialBenefitSolutionHeaderHolder.getCurrentSortClass("calculateType")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, null, 'calculateType', '<%=commercialBenefitSolutionHeaderHolder.getNextSortOrder("calculateType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.header.calculate.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=commercialBenefitSolutionHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, null, 'status', '<%=commercialBenefitSolutionHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="commercialBenefitSolutionHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="commercialBenefitSolutionHeaderVO" name="commercialBenefitSolutionHeaderHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<!-- Super -->
						<logic:equal name="accountId" value="1">
							<logic:equal name="commercialBenefitSolutionHeaderVO" property="extended" value="2">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="commercialBenefitSolutionHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="commercialBenefitSolutionHeaderVO" property="headerId"/>" />
							</logic:equal>
						</logic:equal>
						
						<!-- Common Account -->
						<logic:notEqual name="accountId" value="1">
							<logic:notEqual name="commercialBenefitSolutionHeaderVO" property="accountId" value="1">
								<logic:equal name="commercialBenefitSolutionHeaderVO" property="extended" value="2">
									<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="commercialBenefitSolutionHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="commercialBenefitSolutionHeaderVO" property="headerId"/>" />
								</logic:equal>
							</logic:notEqual>
						</logic:notEqual>
					</td>
					<td class="left">
						<a onclick="link('commercialBenefitSolutionDetailAction.do?proc=list_object&id=<bean:write name="commercialBenefitSolutionHeaderVO" property="encodedId"/>');"><bean:write name="commercialBenefitSolutionHeaderVO" property="headerId"/></a>
					</td>
					<td class="left">
						<a onclick="link('commercialBenefitSolutionDetailAction.do?proc=list_object&id=<bean:write name="commercialBenefitSolutionHeaderVO" property="encodedId"/>');"><bean:write name="commercialBenefitSolutionHeaderVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('commercialBenefitSolutionDetailAction.do?proc=list_object&id=<bean:write name="commercialBenefitSolutionHeaderVO" property="encodedId"/>');"><bean:write name="commercialBenefitSolutionHeaderVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="commercialBenefitSolutionHeaderVO" property="validFrom"/></td>
					<td class="left"><bean:write name="commercialBenefitSolutionHeaderVO" property="validEnd"/></td>
					<td class="left"><bean:write name="commercialBenefitSolutionHeaderVO" property="decodeCalculateType"/></td>
					<td class="left"><bean:write name="commercialBenefitSolutionHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="commercialBenefitSolutionHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="commercialBenefitSolutionHeaderHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="commercialBenefitSolutionHeaderHolder" property="indexStart" /> - <bean:write name="commercialBenefitSolutionHeaderHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, '<bean:write name="commercialBenefitSolutionHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, '<bean:write name="commercialBenefitSolutionHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, '<bean:write name="commercialBenefitSolutionHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, '<bean:write name="commercialBenefitSolutionHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="commercialBenefitSolutionHeaderHolder" property="realPage" />/<bean:write name="commercialBenefitSolutionHeaderHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>