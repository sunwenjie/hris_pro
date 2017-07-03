<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.service.inf.biz.sb.SBHeaderService"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";
%>

<div id="content">
	<!-- Information Manage Form -->
	<div class="box searchForm toggableForm" id="sbHeader-information">
	    <div class="head">
	    	<label id="pageTitle"><bean:message bundle="business" key="business.vendor.payment.vendor" /> (ID: <bean:write name="vendorPaymentHeaderVO" property="vendorId" />)</label>
	    </div>
	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions" />">&gt;</a>
		<div id="searchDiv" class="inner hide">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="vendorPaymentAction.do?proc=to_vendorDetail" styleClass="listHeader_form">	
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="vendorId" id="vendorId" value="<bean:write name="vendorPaymentHeaderVO" property="encodedVendorId" />" />
				<input type="hidden" name="monthly" id="monthly" value="<bean:write name="vendorPaymentHeaderVO" property="monthly" />" />
				<input type="hidden" name="subAction" id="subAction" value="<bean:write name="sbHeaderForm" property="subAction"/>" />
				<input type="hidden" name="pageFlag" id="pageFlag" value="<%=SBHeaderService.PAGE_FLAG_HEADER %>" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="business.vendor.payment.sb.solution" /></label> 
							<html:select property="sbSolutionId" styleClass="searchSBHeader_sbSolutionId">
								<html:optionsCollection property="socialBenefitSolutions" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<logic:equal name="role" value="2">
								<logic:notEmpty name="orderDescription">
									<li>
										<label><bean:message bundle="public" key="public.order2" /></label>
										<span><html:text property="orderDescription" styleClass="searchSBHeader_orderDescription"></html:text></span>
									</li>
								</logic:notEmpty>
						</logic:equal>
						<li>
							<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal></label>
							<span><html:text property="contractId" styleClass="searchSBHeader_contractId"></html:text></span>
						</li>
						<li>
							<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal></label>
							<span><html:text property="employeeId" styleClass="searchSBHeader_employeeId"></html:text></span>
						</li>
						<li>
							<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal></label>
							<span><html:text property="employeeNameZH" styleClass="searchSBHeader_employeeNameZH"></html:text></span>
						</li>
						<li>
							<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.status" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.status" /></logic:equal></label> 
							<html:select property="employStatus" styleClass="searchSBHeader_employStatus">
								<html:optionsCollection property="employStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="business" key="business.vendor.payment.sb.status" /></label> 
							<html:select property="sbStatus" styleClass="searchSBHeader_sbStatus">
								<html:optionsCollection property="sbStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
			
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
	            <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
	            <a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
	            <logic:equal name="isExportExcel" value="1">
					<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm('listHeader_form', 'downloadObjects', null, 'fileType=excel&javaObjectName=' + '<%=javaObjectName %>');"><img src="images/appicons/excel_16.png" /></a> 
				</logic:equal>
			</div>
			<html:form action="vendorPaymentAction.do?proc=to_contractDetail" styleClass="info_form">	
	           <fieldset>
		           	<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover"><bean:message bundle="business" key="business.vendor.payment.vendor" /></li> 
						</ul> 
					</div>
					<div class="tabContent"> 
						<!-- Tab1-VendorPayment Info -->
						<div id="tabContent1" class="kantab" >
		            	<ol class="auto" >
		            		<li><label><bean:message bundle="business" key="business.vendor.payment.vendor.id" /></label><span><bean:write name="vendorPaymentHeaderVO" property="vendorId"/></span></li>
		            	</ol>
		            	<ol class="auto">
							<li><label><bean:message bundle="business" key="business.vendor.payment.vendor.name.cn" /></label><span><bean:write name="vendorPaymentHeaderVO" property="vendorNameZH"/></span></li>
							<li><label><bean:message bundle="business" key="business.vendor.payment.vendor.name.en" /></label><span><bean:write name="vendorPaymentHeaderVO" property="vendorNameEN"/></span></li>
		               	</ol>
		               	<br/>
		            	<ol class="auto">
		            		<li><label><bean:message bundle="business" key="business.vendor.payment.toal.sb.company" /></label><span><bean:write name="vendorPaymentHeaderVO" property="decodeAmountCompany"/></span></li>
		            		<li><label><bean:message bundle="business" key="business.vendor.payment.toal.sb.personal" /></label><span><bean:write name="vendorPaymentHeaderVO" property="decodeAmountPersonal"/></span></li>
		            	</ol>
		            	<ol class="auto" >
	          				<li><label><bean:message bundle="business" key="business.vendor.payment.sb.monthly" /></label><span><bean:write name="vendorPaymentHeaderVO" property="monthly"/></span></li>
		            	</ol>
		            	</div>
		               <!-- Tab1-VendorPayment Info -->
	            	</div>
	             </fieldset>
             </html:form>
	         <!-- 包含社保公积金方案列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="/contents/business/vendor/vendorPayment/table/listHeaderTable.jsp"></jsp:include>          
			</div>
			<div class="bottom"><p/></div>
         </div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_vendor_Modules').addClass('current');			
		$('#menu_vendor_Payment').addClass('selected');
	
		// JS of the List
		kanList_init();
		kanCheckbox_init();

		// 列表按钮返回list页面
		$('#btnList').click(function(){
			link('vendorPaymentAction.do?proc=list_estimation');
		});
	})(jQuery);

	function resetForm() {
		$('.searchSBHeader_sbSolutionId').val('0');
		$('.searchSBHeader_orderId').val('0');
		$('.searchSBHeader_employStatus').val('0');
		$('.searchSBHeader_sbStatus').val('0');
		$('.searchSBHeader_contractId').val('');
		$('.searchSBHeader_employeeId').val('');
		$('.searchSBHeader_employeeNameZH').val('');
		$('.searchSBHeader_employeeNameEN').val('');
	};
</script>

