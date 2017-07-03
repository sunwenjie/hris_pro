<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		 <div class="head">
	        <label id="pageTitle">社保公积金</label>
	        <label class="recordId">&nbsp; (ID: <bean:write name="commonBatchVO" property="batchId" />)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchSBHeaderTemp_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="sbHeaderTempAction.do?proc=list_object" method="post" styleClass="searchSBHeaderTemp_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbHeaderTempHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbHeaderTempHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbHeaderTempHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbHeaderTempForm" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="commonBatchVO" property="encodedId" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>员工ID</label>
							<html:text property="employeeId" maxlength="10" styleClass="searchSBHeaderTemp_employeeId" /> 
						</li>
						<!-- 
						<li>
							<label>员工编号</label>
							<html:text property="employeeNo" maxlength="50" styleClass="searchSBHeaderTemp_employeeNo" /> 
						</li>
						<li>
							<label>员工姓名（中文）</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchSBHeaderTemp_employeeNameZH" /> 
						</li>
						<li>
							<label>员工姓名（英文）</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchSBHeaderTemp_employeeNameEN" /> 
						</li>
						<li>
							<label>证件号码</label>
							<html:text property="certificateNumber" maxlength="100" styleClass="searchSBHeaderTemp_certificateNumber" /> 
						</li> 
						<li>
							<label>户口性质</label> 
							<html:select property="residencyType" styleClass="searchSBHeaderTemp_residencyType">
								<html:optionsCollection property="residencyTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						 -->
						<li>
							<label>社保公积金方案</label> 
							<html:select property="sbSolutionId" styleClass="searchSBHeaderTemp_sbSolutionId">
								<html:optionsCollection property="socialBenefitSolutions" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>社保公积金申报月份</label> 
							<html:select property="monthly" styleClass="searchSBHeaderTemp_detailMonthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<!-- 
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSBHeaderTemp_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						 -->
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="inner">
	        <input type="hidden" name="definedMessage" id="definedMessage" value="true" />
	        <div class="top">
		        <input type="button" class="" name="btnSubmit" id="btnSubmit" value="更新" />
				<input type="button" class="delete" name="btnRollback" id="btnRollback" value="退回" />
	            <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<html:form action="sbHeaderTempAction.do?proc=list_object" styleClass="listHeaderTemp_form">	
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbHeaderTempHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbHeaderTempHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbHeaderTempHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbHeaderTempHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="commonBatchVO" property="encodedId" />" />	
	            <fieldset>
            		<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">批次 (ID: <bean:write name="commonBatchVO" property="batchId" />)</li> 
						</ul> 
					</div>
					<div class="tabContent"> 
						<!-- Tab1-Batch Info -->
						<div id="tabContent1" class="kantab" >
			            	<ol class="auto" >
			            		<li><label>批次ID</label><span><bean:write name="commonBatchVO" property="batchId"/></span></li>
			            		<li><label>导入EXCEL名称</label><span><bean:write name="commonBatchVO" property="importExcelName"/></span></li>
			            		<li><label>描述</label><span><bean:write name="commonBatchVO" property="subStrDescription"/></span></li>
			            		<li><label>上传人</label><span><bean:write name="commonBatchVO" property="decodeCreateBy"/></span></li>
			            		<li><label>上传时间</label><span><bean:write name="commonBatchVO" property="decodeCreateDate"/></span></li>
			            	</ol>
			           	 </div>
	                	<!-- Tab1-Batch Info -->
               		</div>
               	</fieldset>
            </html:form>
	        <!-- 包含社保公积金方案列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="table/listHeaderTempTable.jsp"></jsp:include>          
			</div>
			<div class="bottom"><p/></div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_vendor_Modules').addClass('current');
		$('#menu_vendor_VendorImport').addClass('selected');
		
		// JS of the List
		kanList_init();
		kanCheckbox_init();

		// 批次更新事件
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定提交选中的项目？")){
					submitForm('searchSBHeaderTemp_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要提交的项目。");
			}
		});
		
		// 批次退回事件
		$('#btnRollback').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回选中的项目？")){
					submitForm('searchSBHeaderTemp_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要退回的项目。");
			}
		});
		// 列表按钮返回list页面
		$('#btnList').click(function(){
			if (agreest())
				link('vendorSBAction.do?proc=list_object');
		});
	})(jQuery);
	
	function resetForm() {
	    $('.searchSBHeaderTemp_employeeId').val('');
	    $('.searchSBHeaderTemp_sbSolutionId').val('0');
	    $('.searchSBHeaderTemp_monthly').val('');
	};
</script>

