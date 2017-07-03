<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="sb" key="sb.adjustment.detail.sb.item.no" />
			</th>
			<th style="width: 25%" class="header-nosort">
				<bean:message bundle="sb" key="sb.adjustment.detail.sb.item.name" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="sb" key="sb.adjustment.detail.monthly" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="sb" key="sb.adjustment.detail.account.monthly" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="sb" key="sb.adjustment.detail.money.company" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="sb" key="sb.adjustment.detail.money.personal" />
			</th>	
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="public" key="public.status" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sbAdjustmentDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="sbAdjustmentDetailVO" name="sbAdjustmentDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sbAdjustmentDetailVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="sbAdjustmentDetailVO" property="encodedId"/>" />
					</td>
					<td class="left"><a onclick="sbAdjustmentDetailModify('<bean:write name="sbAdjustmentDetailVO" property="encodedId"/>',<bean:write name="sbAdjustmentDetailVO" property="itemId"/>);"><bean:write name="sbAdjustmentDetailVO" property="itemNo" /></a></td>
					<td class="left"><a onclick="sbAdjustmentDetailModify('<bean:write name="sbAdjustmentDetailVO" property="encodedId"/>',<bean:write name="sbAdjustmentDetailVO" property="itemId"/>);"><bean:write name="sbAdjustmentDetailVO" property="nameZH" /></a></td>
					<td class="left"><bean:write name="sbAdjustmentDetailVO" property="monthly" /></td>
					<td class="left"><bean:write name="sbAdjustmentDetailVO" property="accountMonthly" /></td>
					<td class="right"><bean:write name="sbAdjustmentDetailVO" property="amountCompany" /></td>
					<td class="right"><bean:write name="sbAdjustmentDetailVO" property="amountPersonal" /></td>
					<td class="left"><bean:write name="sbAdjustmentDetailVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbAdjustmentDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="sbAdjustmentDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="sbAdjustmentDetailHolder" property="indexStart" /> - <bean:write name="sbAdjustmentDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listSBAdjustmentDetail_form', null, '<bean:write name="sbAdjustmentDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSBAdjustmentDetail_form', null, '<bean:write name="sbAdjustmentDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSBAdjustmentDetail_form', null, '<bean:write name="sbAdjustmentDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSBAdjustmentDetail_form', null, '<bean:write name="sbAdjustmentDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="sbAdjustmentDetailHolder" property="realPage" />/<bean:write name="sbAdjustmentDetailHolder" property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="sbAdjustmentDetailHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
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
		submitForm('listSBAdjustmentDetail_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>