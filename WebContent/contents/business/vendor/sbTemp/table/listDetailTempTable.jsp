<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder sbDetailTempHolder = (PagedListHolder) request.getAttribute("sbDetailTempHolder");
%>

<div id="messageWrapper">
	<logic:present name="MESSAGE">
		<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
			<bean:write name="MESSAGE" />
	  			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
		</div>
	</logic:present>
</div>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 5%" class="header <%=sbDetailTempHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'itemId', '<%=sbDetailTempHolder.getNextSortOrder("itemId")%>', 'tableWrapper');">科目ID</a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailTempHolder.getCurrentSortClass("itemNo")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'itemNo', '<%=sbDetailTempHolder.getNextSortOrder("itemNo")%>', 'tableWrapper');">科目编号</a>
			</th>
			<th style="width: 14%" class="header <%=sbDetailTempHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'nameZH', '<%=sbDetailTempHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">科目名称</a>
			</th>
			<th style="width: 6%" class="header <%=sbDetailTempHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'monthly', '<%=sbDetailTempHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">社保公积金申报月份</a>
			</th>
			<th style="width: 6%" class="header <%=sbDetailTempHolder.getCurrentSortClass("accountMonthly")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'accountMonthly', '<%=sbDetailTempHolder.getNextSortOrder("accountMonthly")%>', 'tableWrapper');">所属月份</a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailTempHolder.getCurrentSortClass("baseCompany")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'baseCompany', '<%=sbDetailTempHolder.getNextSortOrder("baseCompany")%>', 'tableWrapper');">基数（企）</a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailTempHolder.getCurrentSortClass("basePersonal")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'basePersonal', '<%=sbDetailTempHolder.getNextSortOrder("basePersonal")%>', 'tableWrapper');">基数（个）</a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailTempHolder.getCurrentSortClass("rateCompany")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'rateCompany', '<%=sbDetailTempHolder.getNextSortOrder("rateCompany")%>', 'tableWrapper');">比率（企）</a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailTempHolder.getCurrentSortClass("ratePersonal")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'ratePersonal', '<%=sbDetailTempHolder.getNextSortOrder("ratePersonal")%>', 'tableWrapper');">比率（个）</a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailTempHolder.getCurrentSortClass("fixCompany")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'fixCompany', '<%=sbDetailTempHolder.getNextSortOrder("fixCompany")%>', 'tableWrapper');">固定金（企）</a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailTempHolder.getCurrentSortClass("fixPersonal")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'fixPersonal', '<%=sbDetailTempHolder.getNextSortOrder("fixPersonal")%>', 'tableWrapper');">固定金（个）</a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailTempHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'amountCompany', '<%=sbDetailTempHolder.getNextSortOrder("amountCompany")%>', 'tableWrapper');">社保公积金（企）</a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailTempHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'amountPersonal', '<%=sbDetailTempHolder.getNextSortOrder("amountPersonal")%>', 'tableWrapper');">社保公积金（个）</a>
			</th>
			<th style="width: 6%" class="header <%=sbDetailTempHolder.getCurrentSortClass("tempStatus")%>">
				<a onclick="submitForm('listDetailTemp_form', null, null, 'tempStatus', '<%=sbDetailTempHolder.getNextSortOrder("tempStatus")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sbDetailTempHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="sbDetailTempVO" name="sbDetailTempHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" <logic:equal name="sbDetailTempVO" property="tempStatus" value="2">class="hide"</logic:equal> id="kanList_chkSelectRecord_<bean:write name="sbDetailTempVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="sbDetailTempVO" property="encodedId"/>" />
					</td>
					<td class="left"><bean:write name="sbDetailTempVO" property="itemId" /></td>
					<td class="left"><bean:write name="sbDetailTempVO" property="itemNo" /></td>
					<td class="left"><bean:write name="sbDetailTempVO" property="nameZH" /></td>
					<td class="left"><bean:write name="sbDetailTempVO" property="monthly" /></td>
					<td class="left"><bean:write name="sbDetailTempVO" property="accountMonthly" /></td>
					<td class="right"><bean:write name="sbDetailTempVO" property="decodeBaseCompany" /></td>
					<td class="right"><bean:write name="sbDetailTempVO" property="decodeBasePersonal" /></td>
					<td class="right"><bean:write name="sbDetailTempVO" property="decodeRateCompany" /></td>
					<td class="right"><bean:write name="sbDetailTempVO" property="decodeRatePersonal" /></td>
					<td class="right"><bean:write name="sbDetailTempVO" property="decodeFixCompany" /></td>
					<td class="right"><bean:write name="sbDetailTempVO" property="decodeFixPersonal" /></td>
					<td class="right"><bean:write name="sbDetailTempVO" property="decodeAmountCompany" />
						<logic:equal name="sbDetailTempVO" property="tempStatus" value="1" >
							&nbsp;&nbsp;&nbsp;<a onclick="handleSBDetailTemp('<bean:write name="sbDetailTempVO" property="encodedId" />');" >修改</a>
						</logic:equal>
					</td>
					<td class="right"><bean:write name="sbDetailTempVO" property="decodeAmountPersonal" />
						<logic:equal name="sbDetailTempVO" property="tempStatus" value="1" >
							&nbsp;&nbsp;&nbsp;<a onclick="handleSBDetailTemp('<bean:write name="sbDetailTempVO" property="encodedId" />');" >修改</a>
						</logic:equal>
					</td>
					<td class="left"><bean:write name="sbDetailTempVO" property="decodeTempStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbDetailTempHolder">
		<tfoot>
			<tr class="total">
				<td colspan="15" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="sbDetailTempHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="sbDetailTempHolder" property="indexStart" /> - <bean:write name="sbDetailTempHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbDetailTempHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbDetailTempHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbDetailTempHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbDetailTempHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="sbDetailTempHolder" property="realPage" />/<bean:write name="sbDetailTempHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
</script>
