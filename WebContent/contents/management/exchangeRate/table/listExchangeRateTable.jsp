<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder exchangeRateHolder = (PagedListHolder)request.getAttribute("exchangeRateHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 20%" class="header <%=exchangeRateHolder.getCurrentSortClass("currencyNameZH")%>">
				<a onclick="submitForm('listexchangeRate_form', null, null, 'currencyNameZH', '<%=exchangeRateHolder.getNextSortOrder("currencyNameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.exchange.rate.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=exchangeRateHolder.getCurrentSortClass("currencyNameEN")%>">
				<a onclick="submitForm('listexchangeRate_form', null, null, 'currencyNameEN', '<%=exchangeRateHolder.getNextSortOrder("currencyNameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.exchange.rate.name.en" /></a>
			</th>
			<th style="width: 20%" class="header <%=exchangeRateHolder.getCurrentSortClass("currencyCode")%>">
				<a onclick="submitForm('listexchangeRate_form', null, null, 'currencyCode', '<%=exchangeRateHolder.getNextSortOrder("currencyCode")%>', 'tableWrapper');"><bean:message bundle="management" key="management.exchange.rate.currency.code" /></a>
			</th>
			<th style="width: 10%" class="header-nosort">
				USD/Local
			</th>
			<th style="width: 10%" class="header <%=exchangeRateHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listexchangeRate_form', null, null, 'status', '<%=exchangeRateHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 8%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 12%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>			
	<logic:notEqual name="exchangeRateHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="exchangeRateVO" name="exchangeRateHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<logic:equal value="2" name="exchangeRateVO" property="extended">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="exchangeRateVO" property="exchangeRateId"/>" name="chkSelectRow[]" value="<bean:write name="exchangeRateVO" property="exchangeRateId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('exchangeRateAction.do?proc=to_objectModify&encodedId=<bean:write name="exchangeRateVO" property="encodedId"/>');"><bean:write name="exchangeRateVO" property="currencyNameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('exchangeRateAction.do?proc=to_objectModify&encodedId=<bean:write name="exchangeRateVO" property="encodedId"/>');"><bean:write name="exchangeRateVO" property="currencyNameEN"/></a>
					</td>
					<td class="left"><bean:write name="exchangeRateVO" property="currencyCode"/></td>
					<td class="left"><bean:write name="exchangeRateVO" property="exchangeRate"/></td>
					<td class="left"><bean:write name="exchangeRateVO" property="decodeStatus"/></td>
					<td class="left"><bean:write name="exchangeRateVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="exchangeRateVO" property="decodeModifyDate"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="exchangeRateHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left"> 
					 <label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="exchangeRateHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="exchangeRateHolder" property="indexStart" /> - <bean:write name="exchangeRateHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listexchangeRate_form', null, '<bean:write name="exchangeRateHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listexchangeRate_form', null, '<bean:write name="exchangeRateHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listexchangeRate_form', null, '<bean:write name="exchangeRateHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listexchangeRate_form', null, '<bean:write name="exchangeRateHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="exchangeRateHolder" property="realPage" />/<bean:write name="exchangeRateHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>