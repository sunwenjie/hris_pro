<%@ page pageEncoding="GB2312"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
	final PagedListHolder listDetailHolder = (PagedListHolder) request.getAttribute("listDetailHolder");
%>
<div id="content">
	<!-- Information Manage Form -->
	<div class="box toggableForm">
	   <div class="head">
	        <label id="pageTitle">系统发票  - <span id="titles"></span> </label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="HeaderForm" property="invoiceId"/>)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper"></div>
	       <html:form action="systemInvoiceDetailAction.do?proc=list_object" styleClass="listDetail_form">
		        <div class="top">
		            <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="listDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="listDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="listDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="listDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchForm" property="encodedId" />" />	
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" />
					<input type="hidden" name="invoiceId" id="invoiceId" value="<bean:write name="HeaderForm" property="encodedId" />" />
		        </div>
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,3)" class="first">批次 (ID: <span><bean:write name="batchForm" property="batchId" /></span>)</li> 
							<li id="tabMenu2" onClick="changeTab(2,3)" class="hover">发票 (ID: <span><bean:write name="HeaderForm" property="invoiceId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" style="display:none">
							<ol class="auto">
								<li><label>结算月份</label><span><bean:write name="batchForm" property="monthly"/></span></li>
							</ol>
							<ol class="auto">
								<li><label>结算开始时间</label><span><bean:write name="batchForm" property="startDate"/></span></li>
								<li><label>结算结束时间</label><span><bean:write name="batchForm" property="endDate"/></span></li>
		                	</ol>
							<ol class="auto">
		                			<li><label>法务实体</label><span><bean:write name="batchForm" property="decodeEntityId"/></span></li>
		                			<li><label>业务类型</label><span><bean:write name="batchForm" property="decodeBusinessTypeId"/></span></li>
			                		<li><label>客户ID</label><span><bean:write name="clientVO" property="clientId"/></span></li>
			                		<li><label>客户名称（中文）</label><span><bean:write name="clientVO" property="nameZH"/></span></li>
			                		<li><label>客户名称（英文）</label><span><bean:write name="clientVO" property="nameEN"/></span></li>
		                			<li><label>订单ID</label><span><bean:write name="batchForm" property="orderId"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                			<li><label>描述</label><span><bean:write name="batchForm" property="description"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>公司营收</label><span><bean:write name="batchForm" property="billAmountCompany"/></span></li>
		                		<li><label>公司成本</label><span><bean:write name="batchForm" property="costAmountCompany"/></span></li>
		                		<li><label>个税</label><span><bean:write name="batchForm" property="taxAmount"/></span></li>
		                	</ol>
						</div>
						<div id="tabContent2" class="kantab" >
							<ol class="auto">
								<li><label>结算月份</label><span><bean:write name="HeaderForm" property="monthly"/></span></li>
							</ol>
							<ol class="auto">
		                			<li><label>法务实体</label><span><bean:write name="HeaderForm" property="decodeEntityId"/></span></li>
		                			<li><label>业务类型</label><span><bean:write name="HeaderForm" property="decodeBusinessTypeId"/></span></li>
			                		<li><label>客户ID</label><span><bean:write name="HeaderForm" property="clientId"/></span></li>
			                		<li><label>客户名称</label><span><bean:write name="HeaderForm" property="clientName"/></span></li>
			                		<li><label>订单ID</label><span><bean:write name="HeaderForm" property="orderId"/></span></li>
		                	</ol>
							<ol class="auto">
		                			<li><label>描述</label><span><bean:write name="HeaderForm" property="description"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                			<li><label>所属部门</label><span><bean:write name="HeaderForm" property="decodeBranch"/></span></li>
		                			<li><label>所属人</label><span><bean:write name="HeaderForm" property="decodeOwner"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>公司营收</label><span><bean:write name="HeaderForm" property="billAmountCompany"/></span></li>
		                		<li><label>公司成本</label><span><bean:write name="HeaderForm" property="costAmountCompany"/></span></li>
		                		<li><label>个税</label><span><bean:write name="HeaderForm" property="taxAmount"/></span></li>
							</ol>
						</div>
					</div>
               	</fieldset>
             </html:form>
			<!-- -->
			<div id="tableWrapper">
				<jsp:include page="table/listInvoiceDetailTable.jsp"></jsp:include>
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_finance_Modules').addClass('current');			
		if($('#pageFlag').val()=="Preview"){
			$('#menu_finance_Preview').addClass('selected');
			$('#titles').text('预览');
		}else if($('#pageFlag').val()=="Split"){
			$('#menu_finance_Split').addClass('selected');
			$('#titles').text('拆分');
		}else if($('#pageFlag').val()=="Merge"){
			$('#menu_finance_Merge').addClass('selected');
			$('#titles').text('合并');
		}
		// 取消事件
		$('#btnList').click(function(){
			link('systemInvoiceHeaderAction.do?proc=list_object&pageFlag=<bean:write name="pageFlag" />&batchId=<bean:write name="batchForm" property="encodedId"/>');
		});
	})(jQuery);
</script>

