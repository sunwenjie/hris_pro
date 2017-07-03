<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder adjustmentDetailHolder = (PagedListHolder) request.getAttribute("adjustmentDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 6%" class="header-nosort">
				序号
			</th>
			<th style="width: 8%" class="header-nosort">
				科目ID
			</th>
			<th style="width: 8%" class="header-nosort">
				科目编号
			</th>
			<th style="width: 20%" class="header-nosort">
				科目名称（中文）
			</th>
			<th style="width: 20%" class="header-nosort">
				科目名称（英文）
			</th>
			<th style="width: 8%" class="header-nosort">
				公司营收
			</th>	
			<th style="width: 8%" class="header-nosort">
				公司成本
			</th>
			<th style="width: 8%" class="header-nosort">
				个人收入
			</th>
			<th style="width: 8%" class="header-nosort">
				个人支出
			</th>
			<th style="width: 6%" class="header-nosort">
				状态
			</th>
		</tr>
	</thead>
	<logic:notEqual name="adjustmentDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="adjustmentDetailVO" name="adjustmentDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="adjustmentDetailVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="adjustmentDetailVO" property="encodedId"/>" />
					</td>
					<td class="left"><a onclick="adjustmentDetailModify('<bean:write name="adjustmentDetailVO" property="encodedId"/>');"><bean:write name="adjustmentDetailVO" property="adjustmentDetailId" /></a></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="itemId" /></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="itemNo" /></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="nameZH" /></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="nameEN" /></td>
					<td class="right"><bean:write name="adjustmentDetailVO" property="billAmountCompany" /></td>
					<td class="right"><bean:write name="adjustmentDetailVO" property="costAmountCompany" /></td>
					<td class="right"><bean:write name="adjustmentDetailVO" property="billAmountPersonal" /></td>
					<td class="right"><bean:write name="adjustmentDetailVO" property="costAmountPersonal" /></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="adjustmentDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="12" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="adjustmentDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="adjustmentDetailHolder" property="indexStart" /> - <bean:write name="adjustmentDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listAdjustmentDetail_form', null, '<bean:write name="adjustmentDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listAdjustmentDetail_form', null, '<bean:write name="adjustmentDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listAdjustmentDetail_form', null, '<bean:write name="adjustmentDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listAdjustmentDetail_form', null, '<bean:write name="adjustmentDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="adjustmentDetailHolder" property="realPage" />/<bean:write name="adjustmentDetailHolder" property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;跳转至：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="adjustmentDetailHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />页</label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	function forward() {
		var value = Number($('#forwardPage').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('listAdjustmentDetail_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>