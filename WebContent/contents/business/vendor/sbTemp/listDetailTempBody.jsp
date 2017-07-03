<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    
	    <div class="inner">
	        <html:form action="sbDetailTempAction.do?proc=list_object" styleClass="listDetailTemp_form">
		        <div class="top">
		      		<input type="button" class="delete" name="btnRollback" id="btnRollback" value="退回" />
	          	  	<input type="button" class="reset" name="btnBack" id="btnBack" value="返回上一层" onclick="if(agreest()){link('sbHeaderTempAction.do?proc=list_object&batchId=<bean:write name="commonBatchVO" property="encodedId"/>')};" />
	          	  	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="if(agreest()) link('vendorSBAction.do?proc=list_object');" />
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbDetailTempHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbDetailTempHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="sbDetailTempHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbDetailTempHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="modifyObject" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="commonBatchVO" property="encodedId" />" />
					<input type="hidden" name="headerId" id="headerId" value="<bean:write name="sbHeaderTempVO" property="encodedId" />" />
				</div>
			</html:form>
			<form>
				<fieldset>
            		<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,2)" class="first">批次 (ID: <bean:write name="commonBatchVO" property="batchId" />)</li> 
							<li id="tabMenu2" onClick="changeTab(2,2)" class="hover">序号(ID: <bean:write name="sbHeaderTempVO" property="headerId" />)</li> 
						</ul> 
					</div>
					<div class="tabContent"> 
						<!-- Tab1-Batch Info -->
						<div id="tabContent1" class="kantab hide" >
							<ol class="auto" >
			            		<li><label>批次ID</label><span><bean:write name="commonBatchVO" property="batchId"/></span></li>
			            		<li><label>导入EXCEL名称</label><span><bean:write name="commonBatchVO" property="importExcelName"/></span></li>
			            		<li><label>描述</label><span><bean:write name="commonBatchVO" property="subStrDescription"/></span></li>
			            		<li><label>上传人</label><span><bean:write name="commonBatchVO" property="decodeCreateBy"/></span></li>
			            		<li><label>上传时间</label><span><bean:write name="commonBatchVO" property="decodeCreateDate"/></span></li>
			            	</ol>
		               	</div>
						<div id="tabContent2" class="kantab" >
							<ol class="auto" >
			            		<li><label>社保公积金申报月份</label><span><bean:write name="sbHeaderTempVO" property="monthly"/></span></li>
			            	</ol>
			            	<ol class="auto" >
			            		<li><label>社保公积金</label><span><bean:write name="sbHeaderTempVO" property="employeeSBId"/> - <bean:write name="sbHeaderTempVO" property="employeeSBName"/></span></li>
			            		<li><label>社保公积金状态</label><span><bean:write name="sbHeaderTempVO" property="decodeSbStatus"/></span></li>
			            		<li><label>加保日期</label><span><bean:write name="sbHeaderTempVO" property="startDate"/></span></li>
		               			<li><label>退保日期</label><span><bean:write name="sbHeaderTempVO" property="endDate"/></span></li>
		               			<li><label>证件类型</label><span><bean:write name="sbHeaderTempVO" property="decodeCertificateType"/></span></li>
		               			<li><label>证件号码</label><span><bean:write name="sbHeaderTempVO" property="certificateNumber"/></span></li>
		               			<logic:notEmpty name="sbHeaderTempVO" property="decodeResidencyType">
		               				<li><label>户口性质</label><span><bean:write name="sbHeaderTempVO" property="decodeResidencyType"/></span></li>
		               			</logic:notEmpty>
		               		</ol>
		               		<ol class="auto" >
		               			<logic:notEmpty name="sbHeaderTempVO" property="decodeResidencyCityId">
		               				<li><label>户籍城市</label><span><bean:write name="sbHeaderTempVO" property="decodeResidencyCityId"/></span></li>
		               			</logic:notEmpty>
		               			<logic:notEmpty name="sbHeaderTempVO" property="decodeCityId">
		               				<li><label>社保公积金城市</label><span><bean:write name="sbHeaderTempVO" property="decodeCityId"/></span></li>
		               			</logic:notEmpty>
		               			<logic:notEmpty name="sbHeaderTempVO" property="residencyAddress">
		               				<li><label>户籍地址</label><span><bean:write name="sbHeaderTempVO" property="residencyAddress"/></span></li>
		               			</logic:notEmpty>
			            	</ol>	
			            	<ol class="auto" >
			            		<logic:equal name="sbHeaderTempVO" property="needSBCard" value="1">
		               				<li><label>代办社保公积金卡</label><span><bean:write name="sbHeaderTempVO" property="decodeNeedSBCard"/></span></li>
		               			</logic:equal>
		               			<logic:equal name="sbHeaderTempVO" property="needMedicalCard" value="1">
		               				<li><label>代办医保卡</label><span><bean:write name="sbHeaderTempVO" property="decodeNeedMedicalCard"/></span></li>
		               			</logic:equal>
		               			<logic:notEmpty name="sbHeaderTempVO" property="sbNumber">
		               				<li><label>社保卡帐号</label><span><bean:write name="sbHeaderTempVO" property="sbNumber"/></span></li>
		               			</logic:notEmpty>
		               			<logic:notEmpty name="sbHeaderTempVO" property="medicalNumber">
		               				<li><label>医保卡帐号</label><span><bean:write name="sbHeaderTempVO" property="medicalNumber"/></span></li>
		               			</logic:notEmpty>
		               			<logic:notEmpty name="sbHeaderTempVO" property="fundNumber">
		               				<li><label>公积金帐号</label><span><bean:write name="sbHeaderTempVO" property="fundNumber"/></span></li>
		               			</logic:notEmpty>
		               		</ol>
                			<ol class="auto">
			            		<li><label>社保公积金（公司）</label><span><bean:write name="sbHeaderTempVO" property="decodeAmountCompany"/></span></li>
	                			<li><label>社保公积金（个人）</label><span><bean:write name="sbHeaderTempVO" property="decodeAmountPersonal"/></span></li>
	                		</ol>
		               	</div>
            		</div>
            	</fieldset>
			</form>
	       	<!-- 包含社保公积金方案明细列表信息 -->
	       	<div id="tableWrapper">
				<jsp:include page="table/listDetailTempTable.jsp"></jsp:include>
			</div>
			<div class="bottom"><p/></div>
		</div>
  	</div>
</div>

<div id="handlePopupWrapper">
	<jsp:include page="/popup/handleSBDetailTemp.jsp"></jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_vendor_Modules').addClass('current');
		$('#menu_vendor_VendorImport').addClass('selected');
		
		// JS of the List
		kanList_init();
		kanCheckbox_init();
		
		// 批次退回事件
		$('#btnRollback').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回选中的项目？")){
					submitForm('listDetailTemp_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要退回的项目。");
			}
		});
		
		// 列表按钮返回list页面
		$('#btnList').click(function(){
			if (agreest())
				link('sbFeedbackImportAction.do?proc=list_object');
		});
	})(jQuery);
</script>

