<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder commercialBenefitSolutionDetailHolder = (PagedListHolder)request.getAttribute("commercialBenefitSolutionDetailHolder");
	final String role = ( String )request.getAttribute( "role" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=commercialBenefitSolutionDetailHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, null, 'itemId', '<%=commercialBenefitSolutionDetailHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.detail.item.id" /></a>
			</th>
			<th style="width: 10%" class="header <%=commercialBenefitSolutionDetailHolder.getCurrentSortClass("itemNo")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, null, 'itemNo', '<%=commercialBenefitSolutionDetailHolder.getNextSortOrder("itemNo")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.detail.item.no" /></a>
			</th>
			<th style="width: <%=role.equals( "1" ) ? "20" : "30"%>%" class="header <%=commercialBenefitSolutionDetailHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, null, 'itemId', '<%=commercialBenefitSolutionDetailHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.detail.item.name" /></a>
			</th>
			<th style="width: 10%" class="header <%=commercialBenefitSolutionDetailHolder.getCurrentSortClass("purchaseCost")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, null, 'purchaseCost', '<%=commercialBenefitSolutionDetailHolder.getNextSortOrder("purchaseCost")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.detail.purchase.cost" /></a>
			</th>
			<logic:equal name="role" value="1">
				<th style="width: 10%" class="header <%=commercialBenefitSolutionDetailHolder.getCurrentSortClass("salesCost")%>">
					<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, null, 'salesCost', '<%=commercialBenefitSolutionDetailHolder.getNextSortOrder("salesCost")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.detail.sales.cost" /></a>
				</th>
				<th style="width: 10%" class="header <%=commercialBenefitSolutionDetailHolder.getCurrentSortClass("salesPrice")%>">
					<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, null, 'salesPrice', '<%=commercialBenefitSolutionDetailHolder.getNextSortOrder("salesPrice")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.detail.sales.price" /></a>
				</th>
			</logic:equal>
			
			<th style="width: <%=role.equals( "1" ) ? "20" : "30"%>%" class="header <%=commercialBenefitSolutionDetailHolder.getCurrentSortClass("calculateType")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionHeader_form', null, null, 'calculateType', '<%=commercialBenefitSolutionDetailHolder.getNextSortOrder("calculateType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.cb.solution.detail.calculate.type" /></a>
			</th>
			<th style="width: 10%" class="header <%=commercialBenefitSolutionDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, null, 'status', '<%=commercialBenefitSolutionDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="commercialBenefitSolutionDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="commercialBenefitSolutionDetailVO" name="commercialBenefitSolutionDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="commercialBenefitSolutionDetailVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="commercialBenefitSolutionDetailVO" property="detailId"/>" />
						<input type="hidden" id="hiddenItem" value="<bean:write name="commercialBenefitSolutionDetailVO" property="itemId"/>">
					</td>
					<td class="left"><bean:write name="commercialBenefitSolutionDetailVO" property="itemId"/></td>
					<td class="left"><a onclick="commercialBenefitSolutionDetailModify('<bean:write name="commercialBenefitSolutionDetailVO" property="encodedId"/>');"><bean:write name="commercialBenefitSolutionDetailVO" property="itemNo"/></a></td>
					<td class="left"><a onclick="commercialBenefitSolutionDetailModify('<bean:write name="commercialBenefitSolutionDetailVO" property="encodedId"/>');"><bean:write name="commercialBenefitSolutionDetailVO" property="decodeItem"/></a></td>
					<td class="right"><bean:write name="commercialBenefitSolutionDetailVO" property="purchaseCost"/></td>
					<logic:equal name="role" value="1">
						<td class="right"><bean:write name="commercialBenefitSolutionDetailVO" property="salesCost"/></td>
						<td class="right"><bean:write name="commercialBenefitSolutionDetailVO" property="salesPrice"/></td>
					</logic:equal>
					<td class="left"><bean:write name="commercialBenefitSolutionDetailVO" property="decodeCalculateType"/></td>
					<td class="left"><bean:write name="commercialBenefitSolutionDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="commercialBenefitSolutionDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="commercialBenefitSolutionDetailHolder" property="indexStart" /> - <bean:write name="commercialBenefitSolutionDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, '<bean:write name="commercialBenefitSolutionDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, '<bean:write name="commercialBenefitSolutionDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, '<bean:write name="commercialBenefitSolutionDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCommercialBenefitSolutionDetail_form', null, '<bean:write name="commercialBenefitSolutionDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="commercialBenefitSolutionDetailHolder" property="realPage" />/<bean:write name="commercialBenefitSolutionDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
</table>