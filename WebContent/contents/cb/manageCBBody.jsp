<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="cbBatch" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="cb" key="cb.preview.search.title" /></label>
			<logic:notEmpty name="cbBatchForm" property="batchId" ><label class="recordId"> &nbsp; (ID: <bean:write name="cbBatchForm" property="batchId" />)</label></logic:notEmpty>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.create" />" /> 
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<html:form action="cbAction.do?proc=add_estimation" styleClass="manageCBBatch_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="batchId" id="batchId" value='<bean:write name="cbBatchForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="cbBatchForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="cb" key="cb.batch.month" /><em> *</em></label> 
							<html:select property="monthly" styleClass="manageCBBatch_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">				
						<li>
							<label><bean:message bundle="security" key="security.entity" /></label> 
							<html:select property="entityId" styleClass="manageCBBatch_entityId">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type" /></label> 
							<html:select property="businessTypeId" styleClass="manageCBBatch_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="cb" key="cb.batch.solution" /></label>
							<html:select property="cbId" styleClass="manageCBBatch_cbId">
								<html:optionsCollection property="cbIds" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">	
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户ID</label> 
							<html:text property="clientId" maxlength="10" styleId="clientId" styleClass="manageCBBatch_clientId" /> 
							<a onclick="popupClientSearch()" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
							</label>
							<logic:equal name="role" value="1">
								<html:text property="orderId" maxlength="10" styleId="orderId" styleClass="manageCBBatch_orderId" /> 
				   				<a onclick="popupOrderSearch()" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>
			   				</logic:equal>
							<logic:equal name="role" value="2">
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="manageCBBatch_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<input type="text" name="orderId" maxlength="10" class="manageCBBatch_orderId"/>
			   					</logic:empty>
		   					</logic:equal>
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label>
							<html:text property="contractId" maxlength="10" styleId="contractId" styleClass="manageCBBatch_contractId" /> 
							<a onclick="popupContractSearch()" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageCBBatch_description"></html:textarea>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<logic:equal name="role" value="1">
		<jsp:include page="/popup/searchClient.jsp"></jsp:include>
		<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	</logic:equal>
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_cb_Modules').addClass('current');			
		$('#menu_cb_Process').addClass('selected');
		$('#menu_cb_PurchasePreview').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
       		var flag = 0;
       		
   			flag = flag + validate("manageCBBatch_monthly", true, "select", 0, 0);
   			
   			if(flag == 0){
   				submit('manageCBBatch_form');
   			}
		});

		$('#btnList').click( function () {
			if (agreest())
			link('cbAction.do?proc=list_estimation');
		});
	})(jQuery);
</script>