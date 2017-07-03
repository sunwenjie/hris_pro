<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="sbBatch" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="sb" key="sb.batch.create" /></label>
			<logic:notEmpty name="sbBatchForm" property="batchId" ><label class="recordId"> &nbsp; (ID: <bean:write name="sbBatchForm" property="batchId" />)</label></logic:notEmpty>
		</div>
		<div class="inner">
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.create" />" /> 
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<html:form action="sbAction.do?proc=add_estimation" styleClass="manageSBBatch_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="batchId" id="batchId" value='<bean:write name="sbBatchForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="sbBatchForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="sb" key="sb.monthly" /><em> *</em></label> 
							<html:select property="monthly" styleClass="manageSBBatch_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">				
						<li>
							<label><bean:message bundle="security" key="security.entity" /></label> 
							<html:select property="entityId" styleClass="manageSBBatch_entityId">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type" /></label> 
							<html:select property="businessTypeId" styleClass="manageSBBatch_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.city" /></label> 
							<html:select property="provinceId" styleClass="location_provinceId" >
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.type" /></label> 
							<html:select property="sbType" styleClass="manageSBBatch_sbType">
								<html:optionsCollection property="sbTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">	
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户ID</label> 
							<html:text property="clientId" maxlength="10" styleId="clientId" styleClass="manageSBBatch_clientId" /> 
							<logic:notEmpty name="clientIdError">
								<label class="error manageSBBatch_clientId_error">&#8226;<bean:write name="clientIdError"/></label>
							</logic:notEmpty>
							<a onclick="popupClientSearch()" class="kanhandle"><img src="images/search.png" title="搜索客户记录" /></a> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
							</label> 
							<logic:equal name="role" value="1">
								<html:text property="orderId" maxlength="10" styleId="orderId" styleClass="manageSBBatch_orderId" /> 
								<logic:notEmpty name="orderIdError">
									<label class="error manageSBBatch_orderId_error">&#8226;<bean:write name="orderIdError"/></label>
								</logic:notEmpty>
				   				<a onclick="popupOrderSearch()" class="kanhandle"><img src="images/search.png" title="搜索<logic:equal name="role" value="1">订单</logic:equal><logic:equal name="role" value="2">结算规则</logic:equal>记录" /></a>
			   				</logic:equal>
							<logic:equal name="role" value="2">
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="manageSBBatch_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<input type="text" name="orderId" maxlength="20" class="manageSBBatch_orderId"/>
			   					</logic:empty>
		   					</logic:equal>
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label> 
							<html:text property="contractId" maxlength="10" styleId="contractId" styleClass="manageSBBatch_contractId" /> 
							<logic:notEmpty name="contractIdError">
								<label class="error manageSBBatch_contractId_error">&#8226;<bean:write name="contractIdError"/></label>
							</logic:notEmpty>
							<a onclick="popupContractSearch()" class="kanhandle"><img src="images/search.png" title="搜索<logic:equal name="role" value="1">派送协议</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>记录" /></a>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.status" /></label> 
							<div style="width: 235px;">
								<logic:iterate id="sbStatus" name="sbBatchForm" property="sbStatuses" indexId="index">
									<label>
										<input type="checkbox" name="sbStatusArray" id="sbStatus_<bean:write name="sbStatus" property="mappingId" />" class="sbStatus_<bean:write name="sbStatus" property="mappingId" />" value="<bean:write name="sbStatus" property="mappingId" />" />
										<bean:write name="sbStatus" property="mappingValue" />
									</label>
									<br/>
								</logic:iterate>
							</div>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageSBBatch_description"></html:textarea>
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
		$('#menu_sb_Modules').addClass('current');
		$('#menu_sb_Process').addClass('selected');
		$('#menu_sb_DeclarationPreview').addClass('selected');
		
		// 初始化省份控件
		provinceChange('location_provinceId', 'viewObject', $('.location_cityIdTemp').val(), 'cityId');
		
		// 绑定省Change事件
		$('.location_provinceId').change( function () { 
			provinceChange('location_provinceId', 'modifyObject', 0, 'cityId');
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
       		var flag = 0;
       		
   			flag = flag + validate("manageSBBatch_monthly", true, "select", 0, 0);
//    			flag = flag + validate("manageSBBatch_sbType", true, "select", 0, 0);
   			
   			if(flag == 0){
   				submit('manageSBBatch_form');
   			}
		});

		$('#btnList').click( function () {
			if (agreest())
			link('sbAction.do?proc=list_estimation');
		});
	})(jQuery);
</script>