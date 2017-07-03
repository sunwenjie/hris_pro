<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<!-- Information Manage Form -->
	<div class="box toggableForm">
	   <div class="head">
	        <label id="pageTitle">订单台账 - 雇员</label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="serviceContractVO" property="employeeId"/>)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper"></div>
	       <html:form action="settlementAction.do?proc=to_contractDetail" styleClass="listDetail_form">
		        <div class="top">
		        	<kan:auth right="new" action="<%=SettlementHeaderAction.accessAction%>">
		            	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
		            </kan:auth>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="orderDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="orderDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="orderDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="orderDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchForm" property="encodedId" />" />	
					<input type="hidden" name="orderHeaderId" id="orderHeaderId" value="<bean:write name="orderHeaderVO" property="encodedId" />" />	
					<input type="hidden" name="contractId" id="contractId" value="<bean:write name="serviceContractVO" property="encodedId" />" />
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="batchForm" property="pageFlag" />" />	
		        </div>
	        
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,3)" class="first">批次 (ID: <span><bean:write name="batchForm" property="batchId" /></span>)</li> 
							<li id="tabMenu2" onClick="changeTab(2,3)" >订单 (ID: <span><bean:write name="orderHeaderVO" property="orderId" /></span>)</li> 
							<li id="tabMenu3" onClick="changeTab(3,3)" class="hover">雇员 (ID: <span><bean:write name="serviceContractVO" property="employeeId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" style="display:none">
							<ol class="auto">
								<li><label>结算月份</label><span><bean:write name="batchForm" property="monthly"/></span></li>
								<li><label>会计期间</label><span><bean:write name="batchForm" property="accountPeriod"/></span></li>
								<li><label>结算人员</label><span><bean:write name="batchForm" property="decodeCreateBy"/></span></li>
							</ol>
							<ol class="auto">
								<li><label>结算开始时间</label><span><bean:write name="batchForm" property="startDate"/></span></li>
								<li><label>结算结束时间</label><span><bean:write name="batchForm" property="endDate"/></span></li>
		                	</ol>
							<ol class="auto">
								<logic:notEmpty name="batchForm" property="decodeEntityId">
		                			<li><label>法务实体</label><span><bean:write name="batchForm" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="batchForm" property="decodeBusinessTypeId">
		                			<li><label>业务类型</label><span><bean:write name="batchForm" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:present name="clientVO">
			                		<li><label>客户ID</label><span><bean:write name="clientVO" property="clientId"/></span></li>
			                		<li><label>客户名称（中文）</label><span><bean:write name="clientVO" property="nameZH"/></span></li>
			                		<li><label>客户名称（英文）</label><span><bean:write name="clientVO" property="nameEN"/></span></li>
		                		</logic:present>
		                		<logic:notEmpty name="batchForm" property="orderId">
		                			<li><label>订单ID</label><span><bean:write name="batchForm" property="orderId"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li>
									<label>结算范围</label>
									<div style="width: 220px;">
										<span>
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containSalary" value="1">checked</logic:equal>> 工资 &nbsp;&nbsp;&nbsp; 
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containSB" value="1">checked</logic:equal>> 社保公积金 &nbsp;&nbsp;&nbsp; 
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containCB" value="1">checked</logic:equal>> 商保<br/>
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containOther" value="1">checked</logic:equal>> 其他 &nbsp;&nbsp;&nbsp; 
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containServiceFee" value="1">checked</logic:equal>> 服务费
										</span>
									</div>
								</li>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>客户数量</label><span><bean:write name="batchForm" property="countClientId"/></span></li>
		                		<li><label>订单数量</label><span><bean:write name="batchForm" property="countOrderId"/></span></li>
		                		<li><label>雇员人次</label><span><bean:write name="batchForm" property="countContractId"/></span></li>
		                		<li><label>科目数量</label><span><bean:write name="batchForm" property="countItemId"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="batchForm" property="description">
		                			<li><label>描述</label><span><bean:write name="batchForm" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
							<ol class="auto">
		                		<li><label>公司营收</label><span><bean:write name="batchForm" property="billAmountCompany"/></span></li>
		                		<li><label>公司成本</label><span><bean:write name="batchForm" property="costAmountCompany"/></span></li>
		                		<li><label>个人收入</label><span><bean:write name="batchForm" property="billAmountPersonal"/></span></li>
		                		<li><label>个人支出</label><span><bean:write name="batchForm" property="costAmountPersonal"/></span></li>
		                	</ol>
						</div>
						<div id="tabContent2" class="kantab" style="display:none">
							<ol class="auto">
								<li><label>结算月份</label><span><bean:write name="batchForm" property="monthly"/></span></li>
								<li><label>会计期间</label><span><bean:write name="batchForm" property="accountPeriod"/></span></li>
							</ol>
							<ol class="auto">
								<logic:notEmpty name="orderHeaderVO" property="decodeEntityId">
		                			<li><label>法务实体</label><span><bean:write name="orderHeaderVO" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="decodeBusinessTypeId">
		                			<li><label>业务类型</label><span><bean:write name="orderHeaderVO" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="clientId">
			                		<li><label>客户ID</label><span><bean:write name="orderHeaderVO" property="clientId"/></span></li>
			                		<li><label>客户名称</label><span><bean:write name="orderHeaderVO" property="clientName"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="orderId">
			                		<li><label>订单ID</label><span><bean:write name="orderHeaderVO" property="orderId"/></span></li>
			                	</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="taxName">
		                			<li><label>税率</label><span><bean:write name="orderHeaderVO" property="taxName"/> <logic:notEmpty name="orderHeaderVO" property="taxRemark">(<bean:write name="orderHeaderVO" property="taxRemark"/>)</logic:notEmpty></span></li>
		                		</logic:notEmpty>
		                	</ol>
							<ol class="auto">
								<logic:notEmpty name="orderHeaderVO" property="description">
		                			<li><label>描述</label><span><bean:write name="orderHeaderVO" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
								<logic:notEmpty name="orderHeaderVO" property="startDate">
		                			<li><label>开始日期</label><span><bean:write name="orderHeaderVO" property="startDate"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="endDate">
		                			<li><label>结束日期</label><span><bean:write name="orderHeaderVO" property="endDate"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="orderHeaderVO" property="decodeBranch">
		                			<li><label>所属部门</label><span><bean:write name="orderHeaderVO" property="decodeBranch"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="decodeOwner">
		                			<li><label>所属人</label><span><bean:write name="orderHeaderVO" property="decodeOwner"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>雇员人次</label><span><bean:write name="orderHeaderVO" property="countContractId"/></span></li>
		                		<li><label>科目数量</label><span><bean:write name="orderHeaderVO" property="countItemId"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>公司营收</label><span><bean:write name="orderHeaderVO" property="billAmountCompany"/></span></li>
		                		<li><label>公司成本</label><span><bean:write name="orderHeaderVO" property="costAmountCompany"/></span></li>
		                		<li><label>个人收入</label><span><bean:write name="orderHeaderVO" property="billAmountPersonal"/></span></li>
		                		<li><label>个人支出</label><span><bean:write name="orderHeaderVO" property="costAmountPersonal"/></span></li>
							</ol>
						</div>
						<div id="tabContent3" class="kantab">
							<ol class="auto">
								<li><label>结算月份</label><span><bean:write name="batchForm" property="monthly"/></span></li>
								<li><label>会计期间</label><span><bean:write name="batchForm" property="accountPeriod"/></span></li>
							</ol>
							<ol class="auto">
								<logic:notEmpty name="serviceContractVO" property="employeeId">
			                		<li><label>雇员ID</label><span><bean:write name="serviceContractVO" property="employeeId"/></span></li>
			                	</logic:notEmpty>
			                </ol>
							<ol class="auto">
								<logic:notEmpty name="serviceContractVO" property="employeeId">
			                		<li><label>雇员姓名（中文）</label><span><bean:write name="serviceContractVO" property="employeeNameZH"/></span></li>
			                		<li><label>雇员姓名（英文）</label><span><bean:write name="serviceContractVO" property="employeeNameEN"/></span></li>
		                		</logic:notEmpty>
			                </ol>
							<ol class="auto">
								<logic:notEmpty name="serviceContractVO" property="employeeContractId">
		                			<li><label>派送信息ID</label><span><bean:write name="serviceContractVO" property="employeeContractId"/></span></li>
		                		</logic:notEmpty>
								<logic:notEmpty name="serviceContractVO" property="timesheetId">
		                			<li><label>考勤表ID</label><span><bean:write name="serviceContractVO" property="timesheetId"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
							<ol class="auto">
								<logic:notEmpty name="serviceContractVO" property="startDate">
		                			<li><label>开始日期</label><span><bean:write name="serviceContractVO" property="startDate"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="serviceContractVO" property="endDate">
		                			<li><label>结束日期</label><span><bean:write name="serviceContractVO" property="endDate"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="serviceContractVO" property="decodeBranch">
		                			<li><label>所属部门</label><span><bean:write name="serviceContractVO" property="decodeBranch"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="serviceContractVO" property="decodeOwner">
		                			<li><label>所属人</label><span><bean:write name="serviceContractVO" property="decodeOwner"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
							<ol class="auto">
		                		<li><label>公司营收</label><span><bean:write name="serviceContractVO" property="billAmountCompany"/></span></li>
		                		<li><label>公司成本</label><span><bean:write name="serviceContractVO" property="costAmountCompany"/></span></li>
		                		<li><label>个人收入</label><span><bean:write name="serviceContractVO" property="billAmountPersonal"/></span></li>
		                		<li><label>个人支出</label><span><bean:write name="serviceContractVO" property="costAmountPersonal"/></span></li>
							</ol>
						</div>
					</div>
               	</fieldset>
             </html:form>
			<!-- 包含服务协议列表的Tab页面 -->
			<div id="tableWrapper">
				<jsp:include page="table/listDetailTable.jsp"></jsp:include>
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_settlement_Modules').addClass('current');			
		$('#menu_settlement_OrderSubmited').addClass('selected');

		// 取消事件
		$('#btnList').click(function(){
			if (agreest())
			link('settlementAction.do?proc=list_estimation');
		});
	})(jQuery);
</script>

