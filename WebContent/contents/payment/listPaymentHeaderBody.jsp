<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page import=" com.kan.hro.web.actions.biz.payment.PaymentAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
	        <label id="pageTitle"><bean:message bundle="payment" key="payment.estimate" /></label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="paymentBatchForm" property="batchId"/>)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchPaymentHeader_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="paymentHeaderAction.do?proc=list_object" method="post" styleClass="searchPaymentHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="paymentDTOHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="paymentDTOHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="paymentDTOHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentDTOHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="paymentBatchForm" property="encodedId" />" />	
				<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="statusFlag" />" />	
				<input type="hidden" name="searchHeader" id="searchHeader" class="searchHeader" value="true"/>
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" />
				<fieldset>
					<ol class="auto">
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
								</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchPaymentHeader_employeeId" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.no" /></logic:equal>
								</label>
								<html:text property="employeeNo" maxlength="50" styleClass="searchPaymentHeader_employeeNo" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
								</label>
								<html:text property="employeeNameZH" maxlength="100" styleClass="searchPaymentHeader_employeeNameZH" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
								</label>
								<html:text property="employeeNameEN" maxlength="100" styleClass="searchPaymentHeader_employeeNameEN" /> 
							</li>
							<li>
								<label><bean:message bundle="public" key="public.certificate.number" /></label>
								<html:text property="certificateNumber" maxlength="100" styleClass="searchPaymentHeader_certificateNumber" /> 
							</li> 
							<li>
								<label><bean:message bundle="payment" key="payment.header.residency.type" /></label> 
								<html:select property="residencyType" styleClass="searchPaymentHeader_residencyType">
									<html:optionsCollection property="residencyTypes" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
								</label> 
								<logic:equal name="role" value="1">
									<html:text property="orderId" styleId="orderId" maxlength="10" styleClass="searchPaymentHeader_orderId" />
								</logic:equal> 
								<logic:equal name="role" value="2">
									<html:select property="orderId" styleId="orderId" styleClass="searchPaymentHeader_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
								</logic:equal>
							</li>
			   				<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
								</label>
								<html:text property="contractId" maxlength="10" styleClass="searchPaymentHeader_contractId" /> 
							</li> 
						<li>
							<label><bean:message bundle="payment" key="payment.batch.month" /></label> 
							<html:select property="monthly" styleClass="searchCBHeader_detailMonthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	<!-- Information Manage Form -->
	<div class="box noHeader">
	    <div class="inner">
	        <div id="messageWrapper"></div>
            <div class="top">
            	<%String authAccessAction = (String)request.getAttribute("authAccessAction");%>
	        	<logic:equal name="statusFlag" value="preview">
					<kan:auth right="submit" action="<%=authAccessAction%>">
						<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
					</kan:auth>
					<kan:auth right="back" action="<%=authAccessAction%>">
						<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" />
					</kan:auth>
				</logic:equal>
				<logic:equal name="statusFlag" value="submit">
					<kan:auth right="grant" action="<%=authAccessAction%>">
						<input type="button" class="function" name="btnIssue" id="btnIssue" value="<bean:message bundle="public" key="button.issue" />" />
					</kan:auth>
				</logic:equal>
				<kan:auth right="list" action="<%=authAccessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
				<!-- Export Excel Button -->
	            <logic:equal name="isExportExcel" value="1">
	            	<kan:auth right="export" action="<%=authAccessAction%>">
	            		<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm('searchPaymentHeader_form', 'downloadObjects', 'paymentAction.do?proc=export_object', 'fileType=excel&javaObjectName=<%=PaymentAction.javaObjectName %>&templateType=1');"><img src="images/appicons/excel_16.png" /></a><img src="images/tips.png" style="float: right;" title="<bean:message bundle="payment" key="payment.export.tips" />" />
	            	</kan:auth>
	            </logic:equal>
	            <a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
	        </div>
	        <html:form action="paymentHeaderAction.do?proc=list_object" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="paymentDTOHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="paymentDTOHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="paymentDTOHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentDTOHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="paymentBatchForm" property="encodedId" />" />	
				<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="statusFlag" />" />	
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" />
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover"><bean:message bundle="payment" key="payment.batch" /> (ID:<span><bean:write name="paymentBatchForm" property="batchId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" >
							<ol class="auto">
								<li><label><bean:message bundle="payment" key="payment.batch.month" /></label><span><bean:write name="paymentBatchForm" property="monthly"/></span></li>
								<li><label><bean:message bundle="payment" key="payment.header.create.by" /></label><span><bean:write name="paymentBatchForm" property="decodeCreateBy"/></span></li>
							</ol>
							<ol class="auto">
								<li><label><bean:message bundle="payment" key="payment.batch.start.date" /></label><span><bean:write name="paymentBatchForm" property="startDate"/></span></li>
								<li><label><bean:message bundle="payment" key="payment.batch.end.date" /></label><span><bean:write name="paymentBatchForm" property="endDate"/></span></li>
		                	</ol>
							<ol class="auto">
								<logic:notEmpty name="paymentBatchForm" property="decodeEntityId">
		                			<li><label><bean:message bundle="security" key="security.entity" /></label><span><bean:write name="paymentBatchForm" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="paymentBatchForm" property="decodeBusinessTypeId">
		                			<li><label><bean:message bundle="security" key="security.business.type" /></label><span><bean:write name="paymentBatchForm" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
	                		</ol>
	                		<ol class="auto" <logic:equal name="role" value="2">style="display:none"</logic:equal>>
		                		<logic:notEmpty name="paymentBatchForm" property="orderId">
		                			<li>
		                				<label>
		                					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
		                					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
		                				</label><span><bean:write name="paymentBatchForm" property="orderId"/></span>
		                			</li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li>
		                			<label>
		                				<logic:equal name="role" value="1"><bean:message bundle="payment" key="payment.header.employee1.number" /></logic:equal>
		                				<logic:equal name="role" value="2"><bean:message bundle="payment" key="payment.header.employee2.number" /></logic:equal>
		                			</label>
		                			<span><bean:write name="paymentBatchForm" property="countEmployeeIds"/></span>
		                		</li>
<%-- 		                		<li><label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>数量</label><span id="countEmployeeIds"/></span></li> --%>
		                		<li>
		                			<label>
		                				<logic:equal name="role" value="1"><bean:message bundle="payment" key="payment.header.contract1.number" /></logic:equal>
		                				<logic:equal name="role" value="2"><bean:message bundle="payment" key="payment.header.contract2.number" /></logic:equal>
		                			</label>
		                			<span><bean:write name="paymentBatchForm" property="countContractIds"/></span>
		                		</li>
<%-- 		                		<li><label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>人次</label><span id="countContractIds"/></span></li> --%>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="paymentBatchForm" property="description">
		                			<li><label><bean:message bundle="public" key="public.description" /></label><span><bean:write name="paymentBatchForm" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li>
		                			<label>
		                				<logic:equal name="role" value="1"><bean:message bundle="payment" key="payment.header.order1.cost.amount.company" /></logic:equal>
		                				<logic:equal name="role" value="2"><bean:message bundle="payment" key="payment.header.order2.cost.amount.company" /></logic:equal>
		                			</label>
		                			<span><bean:write name="paymentBatchForm" property="costAmountCompany"/></span>
		                		</li>
<%-- 		                		<li><label><logic:equal name="role" value="1">订单</logic:equal><logic:equal name="role" value="2">公司</logic:equal>人工成本</label><span id="costAmountCompany"/></span></li> --%>
		                	</ol>
							<ol class="auto">
		                		<li><label><bean:message bundle="payment" key="payment.header.bill.amount.personal" /></label><span><bean:write name="paymentBatchForm" property="billAmountPersonal"/></span></li>
<!-- 		                		<li><label>工资应发合计</label><span id="billAmountPersonal"/></span></li> -->
		                		<li><label><bean:message bundle="payment" key="payment.header.cost.amount.personal" /></label><span><bean:write name="paymentBatchForm" property="costAmountPersonal"/></span></li>
<!-- 		                		<li><label>员工扣款项合计</label><span id="costAmountPersonal"></span></li> -->
		                		<li><label><bean:message bundle="payment" key="payment.header.tax.amount.personal" /></label><span><bean:write name="paymentBatchForm" property="taxAmountPersonal"/></span></li>
<!-- 		                		<li><label>个税合计</label><span id="taxAmountPersonal"/></span></li> -->
		                	</ol>
						</div>
					</div>
               	</fieldset>
            </html:form>
	        <!-- 包含工资详情列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="table/listPaymentHeaderTable.jsp"></jsp:include>          
			</div>
			<!-- tableWrapper -->
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
		
		if($('#statusFlag').val() == 'preview'){
			$('#menu_salary_Process').addClass('selected');
			$('#menu_salary_Estimate').addClass('selected');
		}else if($('#statusFlag').val() == 'submit'){
			$('#menu_salary_Process').addClass('selected');
			$('#menu_salary_Confirm').addClass('selected');
			$('#pageTitle').html('<bean:message bundle="payment" key="payment.confirm" />');
		}else if($('#statusFlag').val() == 'issue'){
			$('#menu_salary_PayConfirm').addClass('selected');
			$('#menu_salary_PaymentSubmited').addClass('selected');
			$('#pageTitle').html('<bean:message bundle="payment" key="payment.issue" />');
		}

		// 提交事件
		$('#btnSubmit').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
					$('.searchPaymentHeader_form').attr('action', 'paymentAction.do?proc=submit_estimation');
					submitForm('searchPaymentHeader_form', "submitObjects", null, null, null, null);
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		// 发放事件
		$('#btnIssue').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.issue.records" />')){
					$('.searchPaymentHeader_form').attr('action', 'paymentAction.do?proc=issue_Actual');
					submitForm('searchPaymentHeader_form', "issueObjects", null, null, null, null);
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});

		// 退回事件
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
					$('.searchPaymentHeader_form').attr('action', 'paymentAction.do?proc=rollback_estimation');
					submitForm('searchPaymentHeader_form', "rollbackObjects", null, null, null, null);
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		// 列表事件
		$('#btnList').click(function(){
			link('paymentAction.do?proc=list_object&statusFlag=' + $('#statusFlag').val());
		});
	})(jQuery);
	
	function resetForm(){
		$(".searchPaymentHeader_employeeId").val("");
		$(".searchPaymentHeader_employeeNo").val("");
		$(".searchPaymentHeader_employeeNameZH").val("");
		$(".searchPaymentHeader_employeeNameEN").val("");
		$(".searchPaymentHeader_certificateNumber").val("");
		$(".searchPaymentHeader_residencyType").val("0");
		$(".searchPaymentHeader_orderId").val("0");
		$(".searchPaymentHeader_contractId").val("");
		$(".searchCBHeader_detailMonthly").val("0");
		$(".searchCBHeader_status").val("0");
	};
</script>

