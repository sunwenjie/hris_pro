<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementAdjustmentImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<!-- SettlementAdjustmentHeader - information -->
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle">申报调整导入</label>
	        <logic:notEmpty name="settlementAdjustmentImportHeaderForm" property="adjustmentHeaderId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="settlementAdjustmentImportHeaderForm" property="adjustmentHeaderId" />)</label>
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
				<kan:auth right="list" action="<%=SettlementAdjustmentImportBatchAction.accessAction%>">
					 <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />"/>
				</kan:auth>
			</div>
			<form action="">
				<fieldset>
					<ol class="auto">	
						<li>
							<label>调整月份</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="monthly" /></span>
						</li>
						<li>
							<label>调整日期</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="adjustmentDate" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li id="contractIdLI">
							<label><logic:equal name="role" value="1">派送协议</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="contractId" /></span>
						</li>
					</ol>
					<ol class="auto">
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>法务实体</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="decodeLegalEntity" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>业务类型</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="decodeBusinessType" /></span>
						</li>	
					</ol>
					<ol class="auto">	
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="employeeId" /></span>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="employeeNameZH" /></span>
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="employeeNameEN" /></span>
						</li>
					</ol>
					<ol>	
						<li >
							<label>客户ID</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="clientId" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>订单ID</label>
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="orderId" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户名称（中文）</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="clientNameZH" /></span>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户名称（英文）</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="clientNameEN" /></span>
						</li>
					</ol>
					 <ol class="auto">
								<li><label>公司营收</label><span><bean:write name="settlementAdjustmentImportHeaderForm" property="billAmountCompany" /></span></li>
								<li><label>公司成本</label><span><bean:write name="settlementAdjustmentImportHeaderForm" property="costAmountCompany" /></span></li>
								<li><label>个人收入</label><span><bean:write name="settlementAdjustmentImportHeaderForm" property="billAmountPersonal" /></span></li>
								<li><label>个人支出</label><span><bean:write name="settlementAdjustmentImportHeaderForm" property="costAmountPersonal" /></span></li>
				     </ol>
					<ol class="auto">	
						<li>
							<label>描述:</label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="description" /></span>
						</li>	
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<span><bean:write name="settlementAdjustmentImportHeaderForm" property="decodeStatus" /></span>
						</li>				
					</ol>	
				</fieldset>
			</form>
	    </div>
	</div>
</div>

<!-- SettlementAdjustmentDetail - information -->
<div class="box" id="SettlementAdjustmentDetail-information">		
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
			<%-- <jsp:include page="/contents/sb/adjustment/detail/form/manageSettlementAdjustmentDetailForm.jsp" flush="true"></jsp:include> --%>
		</div>	
		<div id="helpText" class="helpText"></div>	
		<div id="scrollWrapper">
			<div id="scrollContainer"></div>
		</div>																	
		<logic:notEmpty name="settlementAdjustmentDetailHolder">	
			<html:form action="settlementAdjustmentDetailAction.do?proc=list_object" styleClass="listSettlementAdjustmentDetail_form">
				<input type="hidden" name="id" value="<bean:write name="settlementAdjustmentImportHeaderForm" property="encodedId"/>"/>
				<input type="hidden" name="employeeSBId" value="<bean:write name="settlementAdjustmentImportHeaderForm" property="encodedEmployeeSBId"/>"/>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="settlementAdjustmentDetailHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="settlementAdjustmentDetailHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="settlementAdjustmentDetailHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="settlementAdjustmentDetailHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />					
			</html:form>	
		</logic:notEmpty>	
		<div id="tableWrapper">
			<!-- Include table jsp 包含table对应的jsp文件 -->  
			<jsp:include page="/contents/settlement/adjustment/detail/table/listSettlementAdjustmentImportDetailTable.jsp" flush="true"></jsp:include>
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
		$('#menu_settlement_Modules').addClass('current');
		$('#menu_settlement_adjustment_import').addClass('selected');
		// SettlementAdjustment Header 列表Click
		$('#btnList').click(function(){
			if (agreest())
			link('settlementAdjustmentImportHeaderAction.do?proc=list_object&batchId=<bean:write name="settlementAdjustmentImportHeaderForm" property="encodedBatchId" />');
		});
		
	})(jQuery);
</script>

