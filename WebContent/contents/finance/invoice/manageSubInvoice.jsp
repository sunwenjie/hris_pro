<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GB2312"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="systemInvoiceHeaderAction.do?proc=add_object"  styleClass="subInvoice_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="batchId" name="batchId" value="<bean:write name='headerForm' property='encodedBatchId'/>" />
	<input type="hidden" id="invoiceId" name="invoiceId" value="<bean:write name='headerForm' property='encodedId'/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name='headerForm' property='subAction'/>" /> 
		<fieldset>
 		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label>发票月份<em> *</em></label> 
				<html:select property="monthly" styleId="monthly" styleClass="subInvoice_monthly">
					<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li><label>法务实体</label> 
				<html:select property="entityId" styleId="entityId" styleClass="subInvoice_entityId">
					<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label>业务类型</label> 
				<html:select property="businessTypeId" styleId="businessTypeId" styleClass="subInvoice_businessTypeId">
					<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li <logic:equal name="role" value="2"> style="display:none"</logic:equal>>
				<label>客户ID<em> *</em></label> 
				<html:text property="clientId" 	styleId="clientId" maxlength="10" styleClass="subInvoice_clientId" readonly="true"/> 
				<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="搜索客户记录" /></a>
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1">订单</logic:equal>
					<logic:equal name="role" value="2">帐套</logic:equal>ID<em> *</em>
				</label> 
				<logic:equal name="role" value="1">
					<html:text property="orderId" styleId="orderId" maxlength="10" styleClass="subInvoice_orderId" readonly="true"/> 
					<a onclick="popupOrderSearch();" class="kanhandle"><img src="images/search.png" title="搜索订单记录" /></a> 
				</logic:equal>
				<logic:equal name="role" value="2">
					<html:select property="orderId" styleId="orderId" styleClass="subInvoice_orderId">
					<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" /></html:select>
				</logic:equal>
			</li>
			<li>
				<label>公司营收（元）<em> *</em></label> 
				<html:text property="billAmountCompany" maxlength="10" styleClass="subInvoice_billAmountCompany" />
			</li>
			<li>
				<label>公司成本（元）<em> *</em></label> 
				<html:text property="costAmountCompany" maxlength="10" styleClass="subInvoice_costAmountCompany" />
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="subInvoice_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="subInvoice_description" ></html:textarea>
			</li>
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// Detail JS验证
		validate_manage_secondary_form = function() {
		    var flag = 0;
			flag = flag + validate("subInvoice_monthly", false, "select", 0, 0);
			flag = flag + validate("subInvoice_clientId", true, "common", 0, 0);
			flag = flag + validate("subInvoice_orderId", true, "common", 0, 0);
			flag = flag + validate("subInvoice_status", true, "select", 0, 0);
			flag = flag + validate("subInvoice_billAmountCompany", true, "numeric", 0,0,<bean:write name="headerForm" property="billAmountCompany"/>,0);
			flag = flag + validate("subInvoice_costAmountCompany", true, "numeric", 0, 0,<bean:write name="headerForm" property="costAmountCompany"/>,0);
		    return flag;
		};
	})(jQuery);
</script>