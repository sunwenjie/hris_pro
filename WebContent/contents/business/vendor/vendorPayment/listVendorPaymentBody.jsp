<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.service.inf.biz.sb.SBHeaderService"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder sbHeaderHolder = (PagedListHolder) request.getAttribute("sbHeaderHolder");
	final String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";
%>

<div id="content">
	<div class="box searchForm toggableForm" id="sbHeader-information">
		<div class="head">
			<label><bean:message bundle="business" key="business.vendor.payment.search.title" /></label>
		</div>
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions" />">&gt;</a>
		<div id="searchDiv" class="inner hide">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="vendorPaymentAction.do?proc=list_estimation" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbHeaderHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="pageFlag" id="pageFlag" value="<%=SBHeaderService.PAGE_FLAG_VENDOR %>" />
				<input type="hidden" name="javaObjectName" id="javaObjectName" value="<%=javaObjectName %>" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="business.vendor.payment.vendor" /></label> 
							<html:select property="vendorId" styleClass="searchSBHeader_vendorId">
								<html:optionsCollection property="vendors" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="business" key="business.vendor.payment.sb.monthly" /></label>
							<html:select property="monthly" styleClass="searchSBHeader_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	
	<!-- SBHeader-information -->
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
			<logic:present name="MESSAGE">
				<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
					<bean:write name="MESSAGE" />
	    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
				</div>
			</logic:present>
			<div class="top">
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
	            <logic:equal name="isExportExcel" value="1">
					<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm('list_form', 'downloadObjects', null, 'fileType=excel&javaObjectName=' + '<%=javaObjectName %>');"><img src="images/appicons/excel_16.png" /></a> 
				</logic:equal>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/business/vendor/vendorPayment/table/listVendorPaymentTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		<!-- List Component -->
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_vendor_Modules').addClass('current');			
		$('#menu_vendor_Payment').addClass('selected');
	})(jQuery);

	function resetForm() {
		$('.searchSBHeader_vendorId').val('0');
		$('.searchSBHeader_monthly').val('0');
	};
</script>