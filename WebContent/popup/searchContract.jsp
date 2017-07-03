<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	
<%
	final PagedListHolder contractHolder = (PagedListHolder) request.getAttribute( "contractHolder" );
%>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="contractModalId">
    <div class="modal-header" id="contractHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#contractModalId').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label>
        	<logic:equal name="role" value="1"><bean:message bundle="business" key="business.employee.contract1.search" /></logic:equal>
        	<logic:equal name="role" value="2"><bean:message bundle="business" key="business.employee.contract2.search" /></logic:equal>
        </label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnSearch" id="btnSearch" value="<bean:message bundle="public" key="button.search" />" onclick="searchContract();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetContractSearch()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="employeeContractAction.do?proc=list_object_ajax" styleClass="listContract_form">
			<input type="hidden" name="sortColumn" id="sortColumn" value="" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="" />
			<input type="hidden" name="page" id="page" value="" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<fieldset>
				
				<ol class="auto">	
		   			<li id="searchContract_clientIdLI" <logic:equal name="role" value="2"> style="display: none" </logic:equal>>
		   				<label>客户ID <img title="客户ID不支持模糊查询，需输入精确值。" src="images/tips.png"/></label>
		   				<input type="text" name="clientId" maxlength="10" class="searchContract_clientId" value="<%=BaseAction.getClientId(request, response)%>"/>
		   			</li>
		   			<li id="searchContract_orderIdLI">
		   				<label>
		   					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
		   					<logic:equal name="role" value="2">
		   						<bean:message bundle="public" key="public.order2.id" />&nbsp;&nbsp;
		   						<img title="<bean:message bundle="public" key="popup.common.id.tips" />" src="images/tips.png"/>
		   					</logic:equal>
		   				</label>
		   				<logic:equal name="role" value="1">
			   				<input type="text" name="orderId" maxlength="10" class="searchContract_orderId"/>
		   				</logic:equal>
		   				<logic:equal name="role" value="2">
		   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
								<html:select property="orderId" styleClass="searchContract_orderId">
									<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
								</html:select>
		   					</logic:notEmpty>
		   					<logic:empty name="clientOrderHeaderMappingVOs">
		   						<input type="text" name="orderId" maxlength="10" class="searchContract_orderId"/>
		   					</logic:empty>
		   				</logic:equal>
		   			</li>
		   		</ol>	
				<ol class="auto" <logic:equal name="role" value="2"> style="display: none" </logic:equal>>
					<li id="searchContract_clientNameZH">
						<label>客户名称（中文）</label> 
						<input type="text" name="clientNameZH" maxlength="100" class="searchContract_clientNameZH" />
					</li>
					<li id="searchContract_clientNameEN">
						<label>客户名称（英文）</label> 
						<input type="text" name="clientNameEN" maxlength="100" class="searchContract_clientNameEN" />
					</li>
				</ol>
				
				<ol class="auto">
					<li id="searchContract_employeeId">
						<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
		   					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							&nbsp;&nbsp;<img title="<bean:message bundle="public" key="popup.common.id.tips" />" src="images/tips.png"/>
						</label> 
						<input type="text" name="employeeId" maxlength="10" class="searchContract_employeeId" />
					</li>
					<li>
						<label><bean:message bundle="public" key="public.certificate.number" /></label>
						<input type="text" name="certificateNumber" maxlength="25" class="searchContract_certificateNumber" />
					</li>
				</ol>
				<ol class="auto">
					<li id="searchContract_employeeNameZH">
		   				<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
							<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:notEqual>
						</label>
		   				<input type="text" name="employeeNameZH" maxlength="50" class="searchContract_employeeNameZH"  />
		   			</li>
					<li id="searchContract_employeeNameEN">
						<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
							<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name.en" /></logic:notEqual>
						</label>
						<input type="text" name="employeeNameEN" maxlength="50" class="searchContract_employeeNameEN" />
					</li>
				</ol>
				
			</fieldset>
		</html:form >
    </div>
    <div class="modal-body-appand">
		<div id="contractTableWrapper">
			<logic:present name="contractHolder">
				<!-- 包含Tab页面 -->
				<jsp:include page="/popup/table/searchContractTable.jsp"></jsp:include>
			</logic:present>
		</div>
	</div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 搜索区域重置
	function resetContractSearch(){
		<logic:equal name="role" value="1">
			$('.searchContract_clientId').val('');
		</logic:equal>
		$('.searchContract_orderId').val('');
		$('.searchContract_clientNameZH').val('');
		$('.searchContract_clientNameEN').val('');
		$('.searchContract_employeeId').val('');
		$('.searchContract_certificateNumber').val('');
		$('.searchContract_employeeNameZH').val('');
		$('.searchContract_employeeNameEN').val('');
	};
	
	// 验证数据
	function checkSearchContract(){
		var flag = false;
		
		// 验证下拉框数据
		$('select[class^="searchContract_"]').each(function(){
			if($(this).val() && $(this).val() != "0"){
		 		flag = true;
				return false;
			}
		});
		
		// 验证文本框数据
		$('input[class^="searchContract_"]').each(function(){
			<logic:equal name="role" value="2">
				if($(this).attr('class') != 'searchContract_clientId'){
			</logic:equal>
			if($(this).val()){
		 		flag = true;
				return false;
			}
			<logic:equal name="role" value="2">
				}
			</logic:equal>
		});

		if(!flag){
			$('#contractTableWrapper').html('');
			alert('<bean:message bundle="public" key="popup.not.input.search.condition" />');
		}
		
		return flag;
	}
	
	// “搜索”事件
	function searchContract(){
		if(checkSearchContract()){
			submitForm('listContract_form', null, null, 'contractId', 'desc', 'contractTableWrapper');
		}
	};
	
	// “确定”选择事件
	function selectContract(){
		var selectedId = $('#contractTableWrapper input[id^="kanList_chkSelectRecord_"]:checked').val();
		
		if(selectedId != null && selectedId.trim() != ''){
			$('#contractId').val( selectedId );
			$('#contractId').trigger('keyup');
			$('#contractModalId').addClass('hide');
			$('#shield').hide();
			// 清除错误消息
			$('#contractId').next('label').remove();
			$('#contractId').next('label').removeClass('error');
		}else{
			alert('<bean:message bundle="public" key="popup.not.selected.records" />');
		}
	};
	
	// 弹出模态窗口
	function popupContractSearch(){
		$('#contractModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#contractModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>