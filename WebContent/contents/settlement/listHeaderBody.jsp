<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
	        <label id="pageTitle">订单台账 - 批次</label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="batchForm" property="batchId"/>)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchHeader_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="settlementHeaderAction.do?proc=list_object" method="post" styleClass="searchHeader_form">
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="headerListHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="headerListHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="headerListHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="headerListHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchForm" property="encodedId" />" />	
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="batchForm" property="pageFlag" />" />
				<fieldset>
					<ol class="auto">
							<li>
								<label>客户ID</label>
								<html:text property="clientId" maxlength="10" styleClass="searchHeader_clientId" /> 
							</li>
							<li>
								<label>客户名称（中文）</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchHeader_clientNameZH" /> 
							</li>
							<li>
								<label>客户名称（英文）</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchHeader_clientNameEN" /> 
							</li>
							<li>
								<label>订单ID</label>
								<html:text property="orderId" maxlength="100" styleClass="searchHeader_orderId" /> 
							</li> 
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="inner">
	        <div id="messageWrapper"></div>
	        <html:form action="settlementHeaderAction.do?proc=list_object" styleClass="listHeader_form">
		        <div class="top">
		        	<kan:auth right="new" action="<%=SettlementHeaderAction.accessAction%>">
						<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
					</kan:auth>
		            <input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="headerListHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="headerListHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="headerListHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="headerListHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchForm" property="encodedId" />" />	
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="batchForm" property="pageFlag" />" />
		            <a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
		        </div>
	        
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">批次 (ID: <span><bean:write name="batchForm" property="batchId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" >
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
					</div>
               	</fieldset>
            </html:form>
	        <!-- 包含社保公积金方案列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="table/listHeaderTable.jsp"></jsp:include>          
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_settlement_Modules').addClass('current');			
		$('#menu_settlement_OrderSubmited').addClass('selected');

		// 列表点击事件
		$('#btnList').click(function(){
			if (agreest())
			link('settlementAction.do?proc=list_estimation');
		});
	})(jQuery);
	
	
	function resetForm(){
		$(".searchHeader_clientId").val("");
		$(".searchHeader_clientNameZH").val("");
		$(".searchHeader_clientNameEN").val("");
		$(".searchHeader_orderId").val("");
	}
</script>

