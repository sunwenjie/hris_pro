<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.payment.PaymentAdjustmentImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<!-- PaymentAdjustmentHeader - information -->
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle">调整导入</label>
	        <logic:notEmpty name="paymentAdjustmentImportHeaderForm" property="adjustmentHeaderId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="paymentAdjustmentImportHeaderForm" property="adjustmentHeaderId" />)</label>
	        </logic:notEmpty>
	    </div>
	    <div class="inner">
	    	 <div id="messageWrapper">
				<logic:present name="MESSAGE_HEADER">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
		    <div class="top">
				<kan:auth right="list" action="<%=PaymentAdjustmentImportBatchAction.accessAction%>">
					 <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />"/>
				</kan:auth>
			</div>
			<form action="">
				<fieldset>
					<ol class="auto">	
						<li>
							<label>调整月份</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="monthly" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li id="contractIdLI">
							<label><logic:equal name="role" value="1">派送协议</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="contractId" /></span>
						</li>
						
					</ol>
					
					<ol class="auto">	
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="employeeId" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="employeeNameZH" /></span>
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="employeeNameEN" /></span>
						</li>
					</ol>
					<ol>	
						<li >
							<label>客户ID</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="clientId" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>订单ID</label>
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="orderId" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户名称（中文）</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="clientNameZH" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户名称（英文）</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="clientNameEN" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label>个人收入（合计）</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="billAmountPersonal" /></span>
						</li>
						<li>
							<label>个人支出（合计）</label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="costAmountPersonal" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="description" /></span>
						</li>	
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<span><bean:write name="paymentAdjustmentImportHeaderForm" property="status" /></span>
						</li>				
					</ol>	
				</fieldset>
			</form>
	    </div>
	</div>
</div>

<!-- PaymentAdjustmentDetail - information -->
<div class="box" id="PaymentAdjustmentDetail-information">		
	<div class="head">
		<label>调整明细</label>
	</div>		
	<div class="inner">		
		<div id="messageWrapper">
			<logic:present name="MESSAGE_DETAIL">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</logic:present>
		</div>			
		<div id="detailFormWrapper" style="display: none;">
			<!-- Include Form JSP 包含Form对应的jsp文件 --> 
			<%-- <jsp:include page="/contents/sb/adjustment/detail/form/managePaymentAdjustmentDetailForm.jsp" flush="true"></jsp:include> --%>
		</div>	
		<div id="helpText" class="helpText"></div>	
		<div id="scrollWrapper">
			<div id="scrollContainer"></div>
		</div>																	
		<logic:notEmpty name="paymentAdjustmentDetailHolder">	
			<html:form action="paymentAdjustmentDetailAction.do?proc=list_object" styleClass="listPaymentAdjustmentDetail_form">
				<input type="hidden" name="id" value="<bean:write name="paymentAdjustmentImportHeaderForm" property="encodedId"/>"/>
				<input type="hidden" name="employeeSBId" value="<bean:write name="paymentAdjustmentImportHeaderForm" property="encodedEmployeeSBId"/>"/>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="paymentAdjustmentDetailHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="paymentAdjustmentDetailHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="paymentAdjustmentDetailHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentAdjustmentDetailHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />					
			</html:form>	
		</logic:notEmpty>	
		<div id="tableWrapper">
			<!-- Include table jsp 包含table对应的jsp文件 -->  
			<jsp:include page="/contents/payment/adjustment/detail/table/listPaymentAdjustmentImportDetailTable.jsp" flush="true"></jsp:include>
		</div>	
		<!-- tableWrapper -->	
		<div class="bottom">
				<p>
		</div>	
	</div>		
	<!-- inner -->		
</div>		
<script type="text/javascript">
	(function($) {
		// 初始化菜单样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Adjustment_Import').addClass('selected');
		
		// PaymentAdjustment Header 列表Click
		$('#btnList').click(function(){
			if (agreest())
			link('paymentAdjustmentImportHeaderAction.do?proc=list_object&batchId=<bean:write name="paymentAdjustmentImportHeaderForm" property="encodedBatchId" />');
		});
		
	})(jQuery);
</script>

