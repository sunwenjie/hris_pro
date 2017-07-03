<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<tr>
			<th style="width: 6%" class="header-nosort">
				���
			</th>
			<th style="width: 8%" class="header-nosort">
				��ĿID
			</th>
			<th style="width: 8%" class="header-nosort">
				��Ŀ���
			</th>
			<th style="width: 20%" class="header-nosort">
				��Ŀ���ƣ����ģ�
			</th>
			<th style="width: 20%" class="header-nosort">
				��Ŀ���ƣ�Ӣ�ģ�
			</th>
			<th style="width: 8%" class="header-nosort">
				��˾Ӫ��
			</th>	
			<th style="width: 8%" class="header-nosort">
				��˾�ɱ�
			</th>
			<th style="width: 8%" class="header-nosort">
				��������
			</th>
			<th style="width: 8%" class="header-nosort">
				����֧��
			</th>
			<th style="width: 6%" class="header-nosort">
				״̬
			</th>
		</tr>
	</thead>
	<logic:notEqual name="settlementAdjustmentImportDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="settlementAdjustmentDetailVO" name="settlementAdjustmentImportDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td class="left"><bean:write name="settlementAdjustmentDetailVO" property="adjustmentDetailId" /></td>
					<td class="left"><bean:write name="settlementAdjustmentDetailVO" property="itemId" /></td>
					<td class="left"><bean:write name="settlementAdjustmentDetailVO" property="itemNo" /></td>
					<td class="left"><bean:write name="settlementAdjustmentDetailVO" property="nameZH" /></td>
					<td class="left"><bean:write name="settlementAdjustmentDetailVO" property="nameEN" /></td>
					<td class="right"><bean:write name="settlementAdjustmentDetailVO" property="billAmountCompany" /></td>
					<td class="right"><bean:write name="settlementAdjustmentDetailVO" property="costAmountCompany" /></td>
					<td class="right"><bean:write name="settlementAdjustmentDetailVO" property="billAmountPersonal" /></td>
					<td class="right"><bean:write name="settlementAdjustmentDetailVO" property="costAmountPersonal" /></td>
					<td class="left"><bean:write name="settlementAdjustmentDetailVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="settlementAdjustmentImportDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />�� <bean:write name="settlementAdjustmentImportDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />��<bean:write name="settlementAdjustmentImportDetailHolder" property="indexStart" /> - <bean:write name="settlementAdjustmentImportDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listSettlementAdjustmentImportDetail_form', null, '<bean:write name="settlementAdjustmentImportDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSettlementAdjustmentImportDetail_form', null, '<bean:write name="settlementAdjustmentImportDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSettlementAdjustmentImportDetail_form', null, '<bean:write name="settlementAdjustmentImportDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSettlementAdjustmentImportDetail_form', null, '<bean:write name="settlementAdjustmentImportDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />��<bean:write name="settlementAdjustmentImportDetailHolder" property="realPage" />/<bean:write name="settlementAdjustmentImportDetailHolder" property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;��ת����<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="settlementAdjustmentImportDetailHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />ҳ</label>&nbsp;
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
		submitForm('listSettlementAdjustmentImportDetail_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>