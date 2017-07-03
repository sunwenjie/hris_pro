<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 15%" class="header-nosort">
				�籣��������
			</th>
			<th style="width: 25%" class="header-nosort">
				�籣���������ƣ����ģ�
			</th>
			<th style="width: 10%" class="header-nosort">
				�����·�
			</th>
			<th style="width: 10%" class="header-nosort">
				�����·�
			</th>
			<th style="width: 15%" class="header-nosort">
				��������˾��
			</th>
			<th style="width: 15%" class="header-nosort">
				���������ˣ�
			</th>	
			<th style="width: 10%" class="header-nosort">
				״̬
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sbAdjustmentImportDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="sbAdjustmentDetailVO" name="sbAdjustmentImportDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td class="left"><bean:write name="sbAdjustmentDetailVO" property="itemNo" /></td>
					<td class="left"><bean:write name="sbAdjustmentDetailVO" property="nameZH" /></td>
					<td class="left"><bean:write name="sbAdjustmentDetailVO" property="monthly" /></td>
					<td class="left"><bean:write name="sbAdjustmentDetailVO" property="accountMonthly" /></td>
					<td class="right"><bean:write name="sbAdjustmentDetailVO" property="amountCompany" /></td>
					<td class="right"><bean:write name="sbAdjustmentDetailVO" property="amountPersonal" /></td>
					<td class="left"><bean:write name="sbAdjustmentDetailVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbAdjustmentImportDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />�� <bean:write name="sbAdjustmentImportDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />��<bean:write name="sbAdjustmentImportDetailHolder" property="indexStart" /> - <bean:write name="sbAdjustmentImportDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listSBAdjustmentImportDetail_form', null, '<bean:write name="sbAdjustmentImportDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSBAdjustmentImportDetail_form', null, '<bean:write name="sbAdjustmentImportDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSBAdjustmentImportDetail_form', null, '<bean:write name="sbAdjustmentImportDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSBAdjustmentImportDetail_form', null, '<bean:write name="sbAdjustmentImportDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />��<bean:write name="sbAdjustmentImportDetailHolder" property="realPage" />/<bean:write name="sbAdjustmentImportDetailHolder" property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;��ת����<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="sbAdjustmentImportDetailHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />ҳ</label>&nbsp;
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
		// ���ҳ����Ч�Զ���ת����һҳ
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('listSBAdjustmentImportDetail_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>