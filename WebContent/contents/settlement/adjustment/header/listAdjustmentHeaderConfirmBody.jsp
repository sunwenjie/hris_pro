<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.AdjustmentHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>
<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label>调整确认</label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchAdjustmentHeader_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="adjustmentHeaderAction.do?proc=list_object_confirm" method="post" styleClass="searchAdjustmentHeader_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="adjustmentHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="adjustmentHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="adjustmentHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="adjustmentHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label>
							<html:text property="employeeId" maxlength="11" styleClass="searchAdjustmentHeader_employeeId" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchAdjustmentHeader_employeeNameZH" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchAdjustmentHeader_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="1">
							<li>
								<label>客户ID</label>
								<html:text property="clientId" maxlength="11" styleClass="searchAdjustmentHeader_clientId" /> 
							</li> 
							<li>
								<label>客户名称（中文）</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchAdjustmentHeader_clientNameZH" /> 
							</li>
							<li>
								<label>客户名称（英文）</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchAdjustmentHeader_clientNameEN" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="1">
							<li>
								<label>订单ID</label>
								<html:text property="orderId" maxlength="11" styleClass="searchAdjustmentHeader_orderId" /> 
							</li> 
						</logic:equal>
						<logic:equal name="role" value="2">
							<li>
								<label>结算规则ID</label>
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchAdjustmentHeader_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchAdjustmentHeader_orderId" /> 
			   					</logic:empty>
		   					</li>
		   				</logic:equal>
						<li>
							<label><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID</label>
							<html:text property="contractId" maxlength="11" styleClass="searchAdjustmentHeader_contractId" /> 
						</li> 
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchAdjustmentHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<!-- Information Search Result -->
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
				<kan:auth right="approve" action="HRO_SETTLE_ADJUSTMENT_HEADER_CONFIRM">
					<input type="button" class="function" name="btnApprove" id="btnApprove" value="批准" />
				</kan:auth>
				<kan:auth right="back" action="HRO_SETTLE_ADJUSTMENT_HEADER_CONFIRM">
					<input type="button" class="delete" name="btnRollback" id="btnRollback" value="回退" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/settlement/adjustment/header/table/listAdjustmentHeaderConfirmTable.jsp" flush="true"/> 
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
		// JS of the List
		$('#menu_settlement_Modules').addClass('current');
		$('#menu_settlement_AdjustmentConfirm').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		
		// 批准事件
		$('#btnApprove').click(function(){
			if(checkSelect()){
				var array = $('#selectedIds').val().split(',');
				
				if(confirm("批准 " + array.length + " 条选中记录？")){
					$('.searchAdjustmentHeader_form').attr('action', 'adjustmentHeaderAction.do?proc=approve_object');
					$('.searchAdjustmentHeader_form').submit();
				}
			};
		});
		
		// 退回事件
		$('#btnRollback').click(function(){
			if(checkSelect()){
				var array = $('#selectedIds').val().split(',');
				
				if(confirm("退回 " + array.length + " 条选中记录？")){
					$('.searchAdjustmentHeader_form').attr('action', 'adjustmentHeaderAction.do?proc=rollback_object');
					$('.searchAdjustmentHeader_form').submit();
				}
			}
		});

	})(jQuery);
	
	// Button点击校验事件
	function checkSelect(){
		var selectedIds = $('#selectedIds').val();
		
		if(selectedIds != ''){
			return true;
		}else{
			alert("请选择要操作的记录！");
			return false;
		}
	};
	
	function resetForm() {
	    $('.searchAdjustmentHeader_employeeId').val('');
	    $('.searchAdjustmentHeader_employeeNameZH').val('');
	    $('.searchAdjustmentHeader_employeeNameEN').val('');
	    $('.searchAdjustmentHeader_clientId').val('');
	    $('.searchAdjustmentHeader_clientNameZH').val('');
	    $('.searchAdjustmentHeader_clientNameEN').val('');
	    $('.searchAdjustmentHeader_orderId').val('');
	    $('.searchAdjustmentHeader_contractId').val('');
	    $('.searchAdjustmentHeader_status').val('0');
	};
</script>
