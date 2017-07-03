<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%					
	final PagedListHolder positionGradeCurrencyHolder = (PagedListHolder)request.getAttribute("positionGradeCurrencyHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 32%" class="header <%=positionGradeCurrencyHolder.getCurrentSortClass("currencyType")%>">
				<a onclick="submitForm('listPositionGradeCurrency_form', null, null, 'currencyType', '<%=positionGradeCurrencyHolder.getNextSortOrder("currencyType")%>', 'tableWrapper');">币种</a>
			</th>
			<th style="width: 30%" class="header <%=positionGradeCurrencyHolder.getCurrentSortClass("minSalary")%>">
				<a onclick="submitForm('listPositionGradeCurrency_form', null, null, 'minSalary', '<%=positionGradeCurrencyHolder.getNextSortOrder("minSalary")%>', 'tableWrapper');">最低薪资</a>
			</th>
			<th style="width: 30%" class="header <%=positionGradeCurrencyHolder.getCurrentSortClass("maxSalary")%>">
				<a onclick="submitForm('listPositionGradeCurrency_form', null, null, 'maxSalary', '<%=positionGradeCurrencyHolder.getNextSortOrder("maxSalary")%>', 'tableWrapper');">最高薪资</a>
			</th>	
			<th style="width: 8%" class="header <%=positionGradeCurrencyHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listPositionGradeCurrency_form', null, null, 'status', '<%=positionGradeCurrencyHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>					
		</tr>
	</thead>
	<logic:notEqual name="positionGradeCurrencyHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="positionGradeCurrencyVO" name="positionGradeCurrencyHolder" property="source" indexId="number">
				<input type="hidden" class="resultTable_currencyType" id="currencyType" name="currencyType" value="<bean:write name="positionGradeCurrencyVO" property="currencyType" />" />
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="positionGradeCurrencyVO" property="currencyId"/>" name="chkSelectRow[]" value="<bean:write name="positionGradeCurrencyVO"  property="currencyId"/>" /></td>
					<td class="left">
						<a onclick="to_positionGradeCurrencyModify('<bean:write name="positionGradeCurrencyVO" property="encodedId"/>');"><bean:write name="positionGradeCurrencyVO" property="decodeCurrencyType" /></a>
					</td>
					<td class="left"><bean:write name="positionGradeCurrencyVO" property="minSalary" /></td>
					<td class="left"><bean:write name="positionGradeCurrencyVO" property="maxSalary" /></td>
					<td class="left"><bean:write name="positionGradeCurrencyVO" property="decodeStatus" /></td>									
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="positionGradeCurrencyHolder">
		<tfoot>
			<tr class="total">
				<td colspan="5" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="positionGradeCurrencyHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="positionGradeCurrencyHolder" property="indexStart" /> - <bean:write name="positionGradeCurrencyHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listPositionGradeCurrency_form', null, '<bean:write name="positionGradeCurrencyHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listPositionGradeCurrency_form', null, '<bean:write name="positionGradeCurrencyHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listPositionGradeCurrency_form', null, '<bean:write name="positionGradeCurrencyHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listPositionGradeCurrency_form', null, '<bean:write name="positionGradeCurrencyHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="positionGradeCurrencyHolder" property="realPage" />/<bean:write name="positionGradeCurrencyHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>