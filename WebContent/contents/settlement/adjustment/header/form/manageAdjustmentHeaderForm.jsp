<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="adjustmentHeaderAction.do?proc=add_object" styleClass="manageAdjustmentHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="adjustmentHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="adjustmentHeaderForm" property="subAction"/>" /> 
		<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em>必填字段 <img src="images/tips.png" title="保存后可添加调整明细。" /></label></li>
		</ol>
		<ol class="auto">
			<li>
				<label>调整月份<em> *</em></label> 
				<html:select property="monthly" styleClass="manageAdjustmentHeader_monthly">
					<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label>调整日期<em> *</em></label> 
				<html:text property="adjustmentDate" maxlength="12" styleClass="manageAdjustmentHeader_adjustmentDate" styleId="adjustmentDate"/> 
			</li>
		</ol>
		<ol class="auto">	
			<li id="employeeIdLI">
				<label><logic:equal name="role" value="1">派送协议</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID<em> *</em></label> 
				<html:text property="contractId" maxlength="11" styleClass="manageAdjustmentHeader_contractId" styleId="contractId"/> 
			</li>
		</ol>
		<ol class="auto" style="display: none;">
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>法务实体</label> 
				<html:select property="entityId" styleClass="manageAdjustmentHeader_entityId">
					<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>业务类型</label> 
				<html:select property="businessTypeId" styleClass="manageAdjustmentHeader_businessTypeId">
					<html:optionsCollection property="businessTypies" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
		</ol>
		<ol class="auto">	
			<li id="employeeIdLI">
				<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label> 
				<html:text property="employeeId" maxlength="11" styleClass="manageAdjustmentHeader_employeeId" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label> 
				<html:text property="employeeNameZH" maxlength="100" styleClass="manageAdjustmentHeader_employeeNameZH" /> 
			</li>
			<li>
				<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label> 
				<html:text property="employeeNameEN" maxlength="100" styleClass="manageAdjustmentHeader_employeeNameEN" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li id="clientIdLI" <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>客户ID</label> 
				<html:text property="clientId" maxlength="11" styleClass="manageAdjustmentHeader_clientId" /> 
			</li>
			<li>
				<label>订单ID</label> 
				<html:text property="orderId" maxlength="11" styleClass="manageAdjustmentHeader_orderId" /> 
			</li>
		</ol>	
		<ol class="auto">		
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>客户名称（中文）</label> 
				<html:text property="clientNameZH" maxlength="100" styleClass="manageAdjustmentHeader_clientNameZH" /> 
			</li>
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>客户名称（英文）</label> 
				<html:text property="clientNameEN" maxlength="100" styleClass="manageAdjustmentHeader_clientNameEN" /> 
			</li>
		</ol>
		<ol class="adjustment_info auto">
			<li>
				<label>公司营收</label> 
				<html:text property="billAmountCompany" maxlength="10" styleClass="manageAdjustmentHeader_billAmountCompany" /> 
			</li>
			<li>
				<label>公司成本</label> 
				<html:text property="costAmountCompany" maxlength="10" styleClass="manageAdjustmentHeader_costAmountCompany" /> 
			</li>	
			<li>
				<label>个人收入</label> 
				<html:text property="billAmountPersonal" maxlength="10" styleClass="manageAdjustmentHeader_billAmountPersonal" /> 
			</li>
			<li>
				<label>个人支出</label> 
				<html:text property="costAmountPersonal" maxlength="10" styleClass="manageAdjustmentHeader_costAmountPersonal" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageAdjustmentHeader_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageAdjustmentHeader_status" styleId="status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>	
		<ol class="auto" style="display: none;">	
			<li>
				<label>所属部门</label> 
				<html:select property="branch" styleClass="branch" styleId="branch" >
					<html:optionsCollection property="branchs" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
			<li>
				<label>所属人</label> 
				<input type="hidden" id="temp_owner" name="temp_owner" class="temp_owner" value="" />
				<select id="owner" name="owner" class="owner">
					<option value="0">请选择</option>
				</select>
			</li>				
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	validate_manage_primary_form = function() {
	    var flag = 0;
	    
	    flag = flag + validate('manageAdjustmentHeader_monthly', true, 'select', 0, 0, 0, 0);
	    flag = flag + validate('manageAdjustmentHeader_adjustmentDate', true, 'common', 12, 0, 0, 0);
	    flag = flag + validate('manageAdjustmentHeader_contractId', true, 'common', 11, 0, 0, 0);
	    flag = flag + validate('manageAdjustmentHeader_description', false, 'common', 500, 0, 0, 0);
	    flag = flag + validate('manageAdjustmentHeader_status', true, 'select', 0, 0, 0, 0);
	    
	    if (flag == 0) {
	        enableForm('manageAdjustmentHeader_form');
	    }
	    
	    return flag;
	};
</script>