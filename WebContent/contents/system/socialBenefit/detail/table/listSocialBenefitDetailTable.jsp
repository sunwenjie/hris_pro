<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder socialBenefitDetailHolder = (PagedListHolder) request.getAttribute("socialBenefitDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<logic:equal value="1" name="PAGE_ACCOUNT_ID">
				<th class="checkbox-col">
					<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
				</th>
			</logic:equal>
			<th style="width: 5%" class="header <%=socialBenefitDetailHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listsocialBenefitDetail_form', null, null, 'itemId', '<%=socialBenefitDetailHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.detail.item" /></a>
			</th>
			<th style="width: 10%" class="header <%=socialBenefitDetailHolder.getCurrentSortClass("itemNo")%>">
				<a onclick="submitForm('listsocialBenefitDetail_form', null, null, 'itemNo', '<%=socialBenefitDetailHolder.getNextSortOrder("itemNo")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.detail.item.no" /></a>
			</th>
			<th style="width: 15%" class="header <%=socialBenefitDetailHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listsocialBenefitDetail_form', null, null, 'itemId', '<%=socialBenefitDetailHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.detail.item.name" /></a>
			</th>
			<th style="width: 12%" class="header-nosort"><bean:message bundle="system" key="system.sb.detail.percent.company" /></th>
			<th style="width: 12%" class="header-nosort"><bean:message bundle="system" key="system.sb.detail.percent.personal" /></th>
			<th style="width: 12%" class="header-nosort"><bean:message bundle="system" key="system.sb.detail.floor.cap.company" /></th>
			<th style="width: 12%" class="header-nosort"><bean:message bundle="system" key="system.sb.detail.floor.cap.personal" /></th>
			<th style="width: 8%" class="header <%=socialBenefitDetailHolder.getCurrentSortClass("companyFixAmount")%>">
				<a onclick="submitForm('listsocialBenefitDetail_form', null, null, 'companyFixAmount', '<%=socialBenefitDetailHolder.getNextSortOrder("companyFixAmount")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.detail.fix.amount.company" /></a>
			</th>
			<th style="width: 8%" class="header <%=socialBenefitDetailHolder.getCurrentSortClass("personalFixAmount")%>">
				<a onclick="submitForm('listsocialBenefitDetail_form', null, null, 'personalFixAmount', '<%=socialBenefitDetailHolder.getNextSortOrder("personalFixAmount")%>', 'tableWrapper');"><bean:message bundle="system" key="system.sb.detail.fix.amount.personal" /></a>
			</th>
			<th style="width: 6%" class="header <%=socialBenefitDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listsocialBenefitDetail_form', null, null, 'status', '<%=socialBenefitDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="socialBenefitDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="socialBenefitDetailVO" name="socialBenefitDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<logic:equal value="1" name="PAGE_ACCOUNT_ID">
						<td>
							<logic:equal name="socialBenefitDetailVO" property="extended" value="2">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="socialBenefitDetailVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="socialBenefitDetailVO" property="detailId"/>" />
							</logic:equal>
						</td>
					</logic:equal>
					<td class="left"><a onclick="socialDetailModify('<bean:write name="socialBenefitDetailVO" property="encodedId"/>');"><bean:write name="socialBenefitDetailVO" property="itemId"/></a></td>
					<td class="left"><a onclick="socialDetailModify('<bean:write name="socialBenefitDetailVO" property="encodedId"/>');"><bean:write name="socialBenefitDetailVO" property="itemNo"/></a></td>
					<td class="left"><a onclick="socialDetailModify('<bean:write name="socialBenefitDetailVO" property="encodedId"/>');"><bean:write name="socialBenefitDetailVO" property="decodeItem"/></a></td>
					<td class="left"><bean:write name="socialBenefitDetailVO" property="companyPercentLow"/></td>
					<td class="left"><bean:write name="socialBenefitDetailVO" property="personalPercentLow"/></td>
					<td class="left"><bean:write name="socialBenefitDetailVO" property="companyFloor"/>&nbsp;~&nbsp;<bean:write name="socialBenefitDetailVO" property="companyCap"/></td>
					<td class="left"><bean:write name="socialBenefitDetailVO" property="personalFloor"/>&nbsp;~&nbsp;<bean:write name="socialBenefitDetailVO" property="personalCap"/></td>
					<td class="left"><bean:write name="socialBenefitDetailVO" property="companyFixAmount"/></td>
					<td class="left"><bean:write name="socialBenefitDetailVO" property="personalFixAmount"/></td>
					<td class="left"><bean:write name="socialBenefitDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="socialBenefitDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="12" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="socialBenefitDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="socialBenefitDetailHolder" property="indexStart" /> - <bean:write name="socialBenefitDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listsocialBenefitDetail_form', null, '<bean:write name="socialBenefitDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listsocialBenefitDetail_form', null, '<bean:write name="socialBenefitDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listsocialBenefitDetail_form', null, '<bean:write name="socialBenefitDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listsocialBenefitDetail_form', null, '<bean:write name="socialBenefitDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="socialBenefitDetailHolder" property="realPage" />/<bean:write name="socialBenefitDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>