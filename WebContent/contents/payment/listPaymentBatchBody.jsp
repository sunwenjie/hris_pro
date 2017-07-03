<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import=" com.kan.hro.web.actions.biz.payment.PaymentAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder paymentBatchHolder = (PagedListHolder) request.getAttribute("paymentBatchHolder");

	final String javaObjectName = "com.kan.hro.domain.biz.payment.PayslipDTO";
%>

<div id="content">
	<div class="box searchForm toggableForm" id="paymentBatch-information">
		<div class="head"><label id="titleLable"><bean:message bundle="payment" key="payment.estimate" /></label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="paymentAction.do?proc=list_object" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="paymentBatchHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="paymentBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="paymentBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" />
				<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="paymentBatchForm" property="statusFlag" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label> 
							<html:text property="employeeId" styleId="employeeId" maxlength="10" styleClass="searchBatchTemp_employeeId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label> 
							<html:text property="contractId" styleId="contractId"  maxlength="10" styleClass="searchBatchTemp_contractId" />
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
							</label>
							<logic:equal name="role" value="1">
								<html:text property="orderId" styleId="orderId" maxlength="10" styleClass="searchBatchTemp_orderId" />
							</logic:equal>
							<logic:equal name="role" value="2">
								<html:select property="orderId" styleId="orderId" styleClass="searchBatchTemp_orderId">
									<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
								</html:select>
							</logic:equal>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity" /></label> 
							<html:select property="entityId" styleClass="searchBatchTemp_entityId">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type" /></label> 
							<html:select property="businessTypeId" styleClass="searchBatchTemp_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="payment" key="payment.batch.month" /></label>
							<html:select property="monthly" styleClass="searchBatchTemp_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- paymentBatch-information -->
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
			<!-- top 如果是台账 没有添加和过账 退回操作-->
			<% String authAccessAction = (String)request.getAttribute("authAccessAction"); %>
			<div class="top">
				<logic:equal value="preview" name="statusFlag">
					<kan:auth right="new" action='<%=authAccessAction%>'>
						<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.create" />" onclick="link('paymentAction.do?proc=to_objectNew');" />
					</kan:auth>
					<kan:auth right="submit" action="<%=authAccessAction%>">
						<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
					</kan:auth>
					<kan:auth right="back" action="<%=authAccessAction%>">
						<input type="button" class="delete" name="btnConfirm" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" />
					</kan:auth>
				</logic:equal>
				<logic:equal value="submit" name="statusFlag">
					<kan:auth right="grant" action="<%=authAccessAction%>">
						<input type="button" class="function" name="btnIssue" id="btnIssue" value="<bean:message bundle="public" key="button.issue" />" />
					</kan:auth>
				</logic:equal>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			    <!-- Export Excel Button -->
		            <logic:equal name="isExportExcel" value="1">
		            	<kan:auth right="export" action="<%=authAccessAction%>">
		            		<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm('list_form', 'downloadObjects', 'paymentAction.do?proc=export_object', 'fileType=excel&javaObjectName=<%=PaymentAction.javaObjectName %>&templateType=1');"><img src="images/appicons/excel_16.png" /></a>
		            		<img src="images/tips.png" style="float: right;" title="<bean:message bundle="payment" key="payment.export.tips" />" />
		            	</kan:auth>
		            </logic:equal>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="table/listPaymentBatchTable.jsp" flush="true"/> 
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
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Process').addClass('selected');
		
		if('${statusFlag}' == 'preview'){
			$('#menu_salary_Estimate').addClass('selected');
		}else if('${statusFlag}' == 'submit'){
			$('#menu_salary_Confirm').addClass('selected');
			$('#titleLable').html('<bean:message bundle="payment" key="payment.confirm" />');
		}else if('${statusFlag}' == 'issue'){
			$('#menu_salary_PaymentSubmited').addClass('selected');
			$('#titleLable').html('<bean:message bundle="payment" key="payment.issue" />');
		}
		
		$('#searchDiv').hide();

		// 过账事件
		$('#btnSubmit').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.selected.batch" />')){
					$('.list_form').attr('action', 'paymentAction.do?proc=submit_estimation');
					submitForm('list_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		// 发放事件
		$('#btnIssue').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.issue.selected.batch" />')){
					$('.list_form').attr('action', 'paymentAction.do?proc=issue_Actual');
					submitForm('list_form', "issueObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});

		// 退回事件
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.selected.batch" />')){
					$('.list_form').attr('action', 'paymentAction.do?proc=rollback_estimation');
					submitForm('list_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
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
		$('.searchBatchTemp_clientId').val('');
		$('.searchBatchTemp_entityId').val('0');
		$('.searchBatchTemp_businessTypeId').val('0');
		$('.searchBatchTemp_orderId').val('');
		$('.searchBatchTemp_employeeId').val('');
		$('.searchBatchTemp_employeeNameZH').val('');
		$('.searchBatchTemp_contractId').val('');
		$('.searchBatchTemp_monthly').val('0');
	};
</script>
