<%@ page pageEncoding="GB2312"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder batchHolder = (PagedListHolder) request.getAttribute("batchHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="batch-information">
		<div class="head"><label>系统发票 - <span id="titles"></span></label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null, null, null, true);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="systemInvoiceAction.do?proc=list_object" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="batchHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="batchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="batchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="batchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
 				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" /> 
				<fieldset>
					<ol class="auto">
						<li>
							<label>批次ID</label> 
							<html:text property="batchId" maxlength="12" styleClass="searchBatch_batchId" /> 
						</li>
						<li>
							<label>法务实体</label> 
							<html:select property="entityId" styleClass="searchBatch_entityId">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>业务类型</label> 
							<html:select property="businessTypeId" styleClass="searchBatch_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>客户ID</label> 
							<html:text property="clientId" maxlength="10" styleClass="searchBatch_clientId" /> 
						</li>
						<li>
							<label>客户名称（中文）</label> 
							<html:text property="clientNameZH" maxlength="10" styleClass="searchBatch_clientNameZH" /> 
						</li>
						<li>
							<label>客户名称（英文）</label> 
							<html:text property="clientNameEN" maxlength="10" styleClass="searchBatch_clientNameEN" /> 
						</li>
						<li>
							<label>订单ID</label>
							<html:text property="orderId" maxlength="10" styleClass="searchBatch_orderId" />
						</li>
						<li>
							<label>账单月份</label>
							<html:select property="monthly" styleClass="searchBatch_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- Batch-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="table/listInvoiceBatchTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p></p>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_finance_Modules').addClass('current');			
		$('#searchDiv').hide();
		
		if($('#pageFlag').val().trim()=="Preview"){
			$('#menu_finance_Preview').addClass('selected');
			$('#titles').text('预览');
		}else if($('#pageFlag').val().trim()=="Split"){
			$('#menu_finance_Split').addClass('selected');
			$('#titles').text('拆分');
		}else if($('#pageFlag').val().trim()=="Merge"){
			$('#menu_finance_Merge').addClass('selected');
			$('#titles').text('合并');
		}
	})(jQuery);
	
	function resetForm() {
		$('.searchBatch_batchId').val('');
		$('.searchBatch_entityId').val('0');
		$('.searchBatch_businessTypeId').val('0');
		$('.searchBatch_clientId').val('');
		$('.searchBatch_orderId').val('');
		$('.searchBatch_clientNameZH').val('');
		$('.searchBatch_clientNameEN').val('');
		$('.searchBatch_monthly').val('0');
		$('.searchBatch_containSalary').removeAttr('checked');
		$('.searchBatch_containSB').removeAttr('checked');
		$('.searchBatch_containCB').removeAttr('checked');
		$('.searchBatch_containOther').removeAttr('checked');
		$('.searchBatch_containServiceFee').removeAttr('checked');
	};
</script>
