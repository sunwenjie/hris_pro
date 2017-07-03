<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementTempAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder batchTempHolder = (PagedListHolder) request.getAttribute("batchTempHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="batchTemp-information">
		<div class="head"><label>订单结算</label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="settlementTempAction.do?proc=list_estimation" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="batchTempHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="batchTempHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="batchTempHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="batchTempHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>批次ID</label> 
							<html:text property="batchId" maxlength="12" styleClass="searchBatchTemp_batchId" /> 
						</li>
						<li>
							<label>法务实体</label> 
							<html:select property="entityId" styleClass="searchBatchTemp_entityId">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>业务类型</label> 
							<html:select property="businessTypeId" styleClass="searchBatchTemp_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>客户ID</label> 
							<html:text property="clientId" maxlength="10" styleClass="searchBatchTemp_clientId" /> 
						</li>
						<li>
							<label>财务编码</label> 
							<html:text property="clientNumber" maxlength="10" styleClass="searchBatchTemp_clientNumber" /> 
						</li>
						<li>
							<label>客户名称（中文）</label> 
							<html:text property="clientNameZH" maxlength="10" styleClass="searchBatchTemp_clientNameZH" /> 
						</li>
						<li>
							<label>客户名称（英文）</label> 
							<html:text property="clientNameEN" maxlength="10" styleClass="searchBatchTemp_clientNameEN" /> 
						</li>
						<li>
							<label>订单ID</label>
							<html:text property="orderId" maxlength="10" styleClass="searchBatchTemp_orderId" />
						</li>
						<li>
							<label>账单月份</label>
							<html:select property="monthly" styleClass="searchBatchTemp_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>账单内容</label>
							<div style="width: 220px;">
								<span>
									<html:checkbox property="containSalary" value="1" styleClass="searchBatchTemp_containSalary"> 工资</html:checkbox> &nbsp;&nbsp;&nbsp; 
									<html:checkbox property="containSB" value="1" styleClass="searchBatchTemp_containSB"> 社保公积金</html:checkbox> &nbsp;&nbsp;&nbsp; 
									<html:checkbox property="containCB" value="1" styleClass="searchBatchTemp_containCB"> 商保</html:checkbox><br/>
									<html:checkbox property="containOther" value="1" styleClass="searchBatchTemp_containOther"> 其他</html:checkbox> &nbsp;&nbsp;&nbsp; 
									<html:checkbox property="containServiceFee" value="1" styleClass="searchBatchTemp_containServiceFee"> 服务费</html:checkbox>
								</span>
							</div>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- BatchTemp-information -->
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
				<kan:auth right="new" action="<%=SettlementTempAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="创建" onclick="link('settlementTempAction.do?proc=to_estimationNew');" />
				</kan:auth>
				<kan:auth right="posting" action="<%=SettlementTempAction.accessAction%>">
	            	<input type="button" class="function" name="btnConfirm" id="btnConfirm" value="过账" />
	            </kan:auth>
	            <kan:auth right="back" action="<%=SettlementTempAction.accessAction%>">
	            	<input type="button" class="delete" name="btnConfirm" id="btnRollback" value="退回" />
	            </kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="table/listBatchTempTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_settlement_Modules').addClass('current');			
		$('#menu_settlement_OrderEstimate').addClass('selected');
		$('#searchDiv').hide();

		// 提交按钮事件
		$('#btnConfirm').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定提交批次？")){
					$('.list_form').attr('action', 'settlementTempAction.do?proc=submit_estimation');
					submitForm('list_form', "confirmObject", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要过账的记录！");
			}
		});
		
		// 退回按钮事件
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回批次？")){
					$('.list_form').attr('action', 'settlementTempAction.do?proc=rollback_estimation');
					submitForm('list_form', "rollbackObject", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要退回的记录！");
			}
		});
		
		<%
		final Boolean messageInfo = (Boolean) request.getAttribute("messageInfo");
			if(messageInfo!=null&&messageInfo){
		%>
			$('#messageWrapper').html('<div class="message success fadable">该批次下没有数据！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
	 			messageWrapperFada();
		<%	}%>
	})(jQuery);
	
	function resetForm() {
		$('.searchBatchTemp_batchId').val('');
		$('.searchBatchTemp_entityId').val('0');
		$('.searchBatchTemp_businessTypeId').val('0');
		$('.searchBatchTemp_clientId').val('');
		$('.searchBatchTemp_orderId').val('');
		$('.searchBatchTemp_clientNameZH').val('');
		$('.searchBatchTemp_clientNameEN').val('');
		$('.searchBatchTemp_monthly').val('0');
		$('.searchBatchTemp_containSalary').removeAttr('checked');
		$('.searchBatchTemp_containSB').removeAttr('checked');
		$('.searchBatchTemp_containCB').removeAttr('checked');
		$('.searchBatchTemp_containOther').removeAttr('checked');
		$('.searchBatchTemp_containServiceFee').removeAttr('checked');
	};
</script>
