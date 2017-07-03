<%@ page import="com.kan.hro.web.actions.biz.sb.VendorSBAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="sbTempBatch-information">
		<div class="head"><label id="itleLable">社保公积金方案导入</label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="vendorSBAction.do?proc=list_object" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="vendorSBBatchHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="vendorSBBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="vendorSBBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="vendorSBBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>批次ID</label> 
							<html:text property="batchId" maxlength="12" styleClass="searchSbTempTemp_BatchId" /> 
						</li>
						<li>
							<label>导入EXCEL名称</label> 
							<html:text property="importExcelName" maxlength="12" styleClass="searchSbTempTemp_importExcelName" /> 
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- vendorBatch-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div class="top">
	       		<input type="hidden" name="definedMessage" id="definedMessage" value="true" />
				<input type="button" class="" name="btnSubmit" id="btnSubmit" value="更新" />
				<input type="button" class="delete" name="btnRollback" id="btnRollback" value="退回" />
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
				<img style='float:right' src='images/import.png' onclick='popupExcelImport();' title='供应商社保公积金方案导入' />
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 --> 
				<jsp:include page="table/listBatchTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<% 
		final String accessAction = VendorSBAction.ACCESSACTION;
		request.setAttribute("accessActionForImport",accessAction);
	%>
	<jsp:include page="/popup/importExcel.jsp" flush = "true">
		<jsp:param  name="accessAction" value="${accessActionForImport}"/>
	</jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_vendor_Modules').addClass('current');
		$('#menu_vendor_VendorImport').addClass('selected');
		
		$('#searchDiv').hide();
		$('#importExcelTitleLableId').html('供应商社保公积金方案导入');

		// 批次更新事件
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定更新选中的批次？")){
					submitForm('list_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要更新的批次。");
			}
		});
		
		// 批次退回事件
		$('#btnRollback').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回选中的批次？")){
					submitForm('list_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要退回的批次。");
			}
		});
		
	})(jQuery);
	
	function resetForm() {
		$(".searchSbTempTemp_BatchId").val("");
		$(".searchSbTempTemp_importExcelName").val("");
	};
</script>
