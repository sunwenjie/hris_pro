<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>

<%@ page import=" com.kan.hro.web.actions.biz.payment.SalaryAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>


<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle">工资结算</label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="salaryHeaderForm" property="salaryHeaderId"/>)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper"></div>
	        <html:form action="salaryAction.do?proc=to_salaryDetail" styleClass="list_form">
		        <div class="top">
		            <input type="button" class="reset" name="btnList" id="btnList" value="返回上一层" />
		            
		            <logic:equal name="salaryHeaderForm" property="status" value="1">
						<kan:auth right="submit" action="<%=SalaryAction.accessAction%>">
							<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.submit" />" />
						</kan:auth>
						<kan:auth right="back" action="<%=SalaryAction.accessAction%>">
							<input type="button" class="delete" name="btnRollback" id="btnRollback" value="退回" />
						</kan:auth>
					</logic:equal>
	            	
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="salaryDTOHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="salaryDTOHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="salaryDTOHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="salaryDTOHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="pageFlag" id="pageFlag" value="header" />
					<input type="hidden" name="salaryHeaderId" id="salaryHeaderId" value="<bean:write name="salaryHeaderForm" property="encodedId" />" />	
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="salaryHeaderForm" property="encodedBatchId" />" />	
		        </div>
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">工资详情 (ID:<span><bean:write name="salaryHeaderForm" property="salaryHeaderId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" >
							<ol class="auto">
								<li><label>工资月份</label><span><bean:write name="salaryHeaderForm" property="monthly"/></span></li>
								<li><label>结算人员</label><span><bean:write name="salaryHeaderForm" property="decodeCreateBy"/></span></li>
							</ol>
							<ol class="auto">
								<li><label>工资开始日期</label><span><bean:write name="salaryHeaderForm" property="startDate"/></span></li>
								<li><label>工资结束日期</label><span><bean:write name="salaryHeaderForm" property="endDate"/></span></li>
							</ol>
							<ol class="auto">
								<logic:notEmpty name="salaryHeaderForm" property="decodeEntityId">
		                			<li><label>法务实体</label><span><bean:write name="salaryHeaderForm" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="salaryHeaderForm" property="decodeBusinessTypeId">
		                			<li><label>业务类型</label><span><bean:write name="salaryHeaderForm" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
	                		</ol>
	                		<ol class="auto" <logic:equal name="role" value="2">style="display:none"</logic:equal>>
	                			<logic:notEmpty name="salaryHeaderForm" property="clientId">
		                			<li><label>客户ID</label><span><bean:write name="salaryHeaderForm" property="clientId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="salaryHeaderForm" property="orderId">
		                			<li><label><logic:equal name="role" value="1">订单ID</logic:equal><logic:equal name="role" value="2">结算规则ID</logic:equal></label>
		                			<span><bean:write name="salaryHeaderForm" property="orderId"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li><label><logic:equal name="role" value="1">雇员ID</logic:equal><logic:equal name="role" value="2">员工ID</logic:equal></label><span>
		                		<bean:write name="salaryHeaderForm" property="employeeId"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="salaryHeaderForm" property="description">
		                			<li><label>描述</label><span><bean:write name="salaryHeaderForm" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>公司成本</label><span><bean:write name="salaryHeaderForm" property="costAmountCompany"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>个人收入</label><span><bean:write name="salaryHeaderForm" property="billAmountPersonal"/></span></li>
		                		<li><label>个人支出</label><span><bean:write name="salaryHeaderForm" property="costAmountPersonal"/></span></li>
		                		<li><label>个税合计</label><span><bean:write name="salaryHeaderForm" property="taxAmountPersonal"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>银行名称</label><span>
		                			<bean:write name="salaryHeaderForm" property="bankNameZH"/>
		                			 <logic:notEqual name="salaryHeaderForm" property="bankNameEN" value="">
		                			（<bean:write name="salaryHeaderForm" property="bankNameEN"/>）
		                			</logic:notEqual>
		                		</span></li>
		                		<li><label>银行账户</label><span><bean:write name="salaryHeaderForm" property="bankAccount"/></span></li>
		                	</ol>
						</div>
					</div>
               	</fieldset>
            </html:form>
	        <!-- 包含工资详情列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="table/listSalaryDetailTable.jsp" flush="true"/> 
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
			$('#pageTitle').html('工资台账');
		}else if($('#statusFlag').val() == 'issue'){
			$('#menu_salary_PayConfirm').addClass('selected');
			$('#pageTitle').html('发放确认');
		}
		
		
		// 批准事件
		$('#btnApprove').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定提交工资？")){
					$('.list_form').attr('action', 'salaryAction.do?proc=submit_salary');
					$('#subAction').val('approveObjects');
					submitForm('list_form');
				}
				}else{
					alert("请选择要提交的记录！");
				}
			
		});
		
		
		$('#btnRollback').click(function(){
			
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回工资？")){
					$('.list_form').attr('action', 'salaryAction.do?proc=submit_salary');
					$('#subAction').val('rollback');
					submitForm('list_form');
				}
			}else{
				alert("请选择要退回的记录！");
			}
		});

		// 提交事件
		$('#btnSubmit').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定提交批次？")){
					$('.list_form').attr('action', 'paymentAction.do?proc=submit_estimation');
					submitForm('list_form', "submitObjects", null, null, null, null);
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要提交的记录！");
			}
		});
		
		// 发放事件
		$('#btnIssue').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定发放批次？")){
					$('.list_form').attr('action', 'paymentAction.do?proc=issue_Actual');
					submitForm('list_form', "issueObjects", null, null, null, null);
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要发放的记录！");
			}
		});

		// 退回事件
/* 		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回批次？")){
					$('.list_form').attr('action', 'paymentAction.do?proc=rollback_estimation');
					submitForm('list_form', "rollbackObjects", null, null, null, null);
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要退回的记录！");
			}
		}); */
		
		// 列表事件
		$('#btnList').click(function(){
			link('salaryAction.do?proc=to_salaryHeader&batchId=<bean:write name="salaryHeaderForm" property="encodedBatchId"/>');
		});
	})(jQuery);
</script>

