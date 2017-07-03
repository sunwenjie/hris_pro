<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.service.inf.biz.sb.SBHeaderService"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle">
	        	<bean:message bundle="business" key="business.vendor.payment.sb.solution" />
			</label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="sbHeaderVO" property="contractId" />)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
            <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
            <input type="button" class="reset" name="btnBack" id="btnBack" value="<bean:message bundle="public" key="button.back.fh" />" onclick="link('vendorPaymentAction.do?proc=to_vendorDetail&vendorId=<bean:write name="vendorPaymentHeaderVO" property="encodedVendorId"/>&monthly=<bean:write name="vendorPaymentHeaderVO" property="monthly"/>')" />
	        <html:form action="vendorPaymentAction.do?proc=to_sbDetail" styleClass="listDetail_form">
		        <div class="top">
		        	<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="sbDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="vendorId" id="vendorId" value="<bean:write name="vendorPaymentHeaderVO" property="encodedVendorId" />" />
					<input type="hidden" name="monthly" id="monthly" value="<bean:write name="vendorPaymentHeaderVO" property="monthly" />" />
					<input type="hidden" name="additionalStatus" id="additionalStatus" value="<bean:write name="sbHeaderForm" property="additionalStatus" />" />
					<input type="hidden" name="subAction" id="subAction" value="<bean:write name="sbHeaderForm" property="subAction"/>" />
					<input type="hidden" name="pageFlag" id="pageFlag" value="<%=SBHeaderService.PAGE_FLAG_DETAIL %>" />
					<input type="hidden" name="headerId" id="headerId" value="<bean:write name="sbHeaderForm" property="encodedId"/>" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="sbHeaderVO" property="encodedBatchId"/>" />
				</div>
				
				<fieldset>
            		<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,2)" class="first"><bean:message bundle="business" key="business.vendor.payment.vendor" />(ID: <bean:write name="vendorPaymentHeaderVO" property="vendorId" />)</li> 
							<li id="tabMenu2" onClick="changeTab(2,2)" class="hover"><bean:message bundle="business" key="business.vendor.payment.header" />(ID： <bean:write name="sbHeaderVO" property="headerId" />)</li> 
						</ul> 
					</div>
					<div class="tabContent"> 
						<!-- Tab1-VendorPayment Info -->
						<div id="tabContent1" class="kantab" style="display:none">
			            	<ol class="auto" >
			            		<li><label><bean:message bundle="business" key="business.vendor.payment.vendor.id" /></label><span><bean:write name="vendorPaymentHeaderVO" property="vendorId"/></span></li>
			            	</ol>
			            	<ol class="auto">
								<li><label><bean:message bundle="business" key="business.vendor.payment.vendor.name.cn" /></label><span><bean:write name="vendorPaymentHeaderVO" property="vendorNameZH"/></span></li>
								<li><label><bean:message bundle="business" key="business.vendor.payment.vendor.name.en" /></label><span><bean:write name="vendorPaymentHeaderVO" property="vendorNameEN"/></span></li>
			               	</ol>
			            	<ol class="auto">
			            		<li><label><bean:message bundle="business" key="business.vendor.payment.sb.company" /></label><span><bean:write name="vendorPaymentHeaderVO" property="decodeAmountCompany"/></span></li>
			            		<li><label><bean:message bundle="business" key="business.vendor.payment.sb.personal" /></label><span><bean:write name="vendorPaymentHeaderVO" property="decodeAmountPersonal"/></span></li>
			            	</ol>
			            	<ol class="auto" >
		          				<li><label><bean:message bundle="business" key="business.vendor.payment.sb.monthly" /></label><span><bean:write name="vendorPaymentHeaderVO" property="monthly"/></span></li>
			            	</ol>
	            		</div>
		            	<!-- Tab1-VendorPayment Info -->
	                	
                		<!-- Tab2-Header Info -->
                		<div id="tabContent2" class="kantab" >
                			<ol class="auto" >
			            		<li><label><bean:message bundle="business" key="business.vendor.payment.sb.monthly" /></label><span><bean:write name="sbHeaderVO" property="monthly"/></span></li>
			            	</ol>
			            	<ol class="auto" >
			            		<li><label><bean:message bundle="business" key="business.vendor.payment.sb.solution" /></label><span><bean:write name="sbHeaderVO" property="employeeSBId"/> - <bean:write name="sbHeaderVO" property="employeeSBName"/></span></li>
			            		<li><label><bean:message bundle="business" key="business.vendor.payment.sb.status" /></label><span><bean:write name="sbHeaderVO" property="decodeSbStatus"/></span></li>
			            		<li><label><bean:message bundle="business" key="business.vendor.payment.start.date" /></label><span><bean:write name="sbHeaderVO" property="startDate"/></span></li>
		               			<li><label><bean:message bundle="business" key="business.vendor.payment.end.date" /></label><span><bean:write name="sbHeaderVO" property="endDate"/></span></li>
		               			<li><label><bean:message bundle="business" key="business.vendor.payment.certificate.type" /></label><span><bean:write name="sbHeaderVO" property="decodeCertificateType"/></span></li>
		               			<li><label><bean:message bundle="business" key="business.vendor.payment.certificate.number" /></label><span><bean:write name="sbHeaderVO" property="certificateNumber"/></span></li>
		               			<logic:notEmpty name="sbHeaderVO" property="decodeResidencyType">
		               				<li><label><bean:message bundle="business" key="business.vendor.payment.residency.type" /></label><span><bean:write name="sbHeaderVO" property="decodeResidencyType"/></span></li>
		               			</logic:notEmpty>
		               		</ol>
		               		<ol class="auto" >
		               			<logic:notEmpty name="sbHeaderVO" property="decodeResidencyCityId">
		               				<li><label><bean:message bundle="business" key="business.vendor.payment.residency.city" /></label><span><bean:write name="sbHeaderVO" property="decodeResidencyCityId"/></span></li>
		               			</logic:notEmpty>
		               			<logic:notEmpty name="sbHeaderVO" property="decodeCityId">
		               				<li><label><bean:message bundle="business" key="business.vendor.payment.sb.city" /></label><span><bean:write name="sbHeaderVO" property="decodeCityId"/></span></li>
		               			</logic:notEmpty>
		               			<logic:notEmpty name="sbHeaderVO" property="residencyAddress">
		               				<li><label><bean:message bundle="business" key="business.vendor.payment.residency.address" /></label><span><bean:write name="sbHeaderVO" property="residencyAddress"/></span></li>
		               			</logic:notEmpty>
			            	</ol>	
			            	<ol class="auto" >
			            		<logic:equal name="sbHeaderVO" property="needSBCard" value="1">
		               				<li><label><bean:message bundle="business" key="business.vendor.payment.need.sb.card" /></label><span><bean:write name="sbHeaderVO" property="decodeNeedSBCard"/></span></li>
		               			</logic:equal>
		               			<logic:equal name="sbHeaderVO" property="needMedicalCard" value="1">
		               				<li><label><bean:message bundle="business" key="business.vendor.payment.need.medical.card" /></label><span><bean:write name="sbHeaderVO" property="decodeNeedMedicalCard"/></span></li>
		               			</logic:equal>
		               			<logic:notEmpty name="sbHeaderVO" property="sbNumber">
		               				<li><label><bean:message bundle="business" key="business.vendor.payment.sb.number" /></label><span><bean:write name="sbHeaderVO" property="sbNumber"/></span></li>
		               			</logic:notEmpty>
		               			<logic:notEmpty name="sbHeaderVO" property="medicalNumber">
		               				<li><label><bean:message bundle="business" key="business.vendor.payment.medical.number" /></label><span><bean:write name="sbHeaderVO" property="medicalNumber"/></span></li>
		               			</logic:notEmpty>
		               			<logic:notEmpty name="sbHeaderVO" property="fundNumber">
		               				<li><label><bean:message bundle="business" key="business.vendor.payment.fund.number" /></label><span><bean:write name="sbHeaderVO" property="fundNumber"/></span></li>
		               			</logic:notEmpty>
		               		</ol>
                			<ol class="auto" >
			            		<li><label><bean:message bundle="business" key="business.vendor.payment.sb.company" /></label><span><bean:write name="sbHeaderVO" property="decodeAmountCompany"/></span></li>
	                			<li><label><bean:message bundle="business" key="business.vendor.payment.sb.personal" /></label><span><bean:write name="sbHeaderVO" property="decodeAmountPersonal"/></span></li>
	                		</ol>
                		</div>
                		<!-- Tab2-Header Info -->
               		</div>
               	</fieldset>
       		</html:form> 
	       	<!-- 包含社保公积金方案明细列表信息 -->
	       	<div id="tableWrapper">
				<jsp:include page="/contents/business/vendor/vendorPayment/table/listDetailTable.jsp"></jsp:include>
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
</script>

