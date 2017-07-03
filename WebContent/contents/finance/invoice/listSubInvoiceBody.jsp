<%@ page pageEncoding="GB2312"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div class="box">
		<div class="head">
			<label>系统发票 - 拆分</label>
		</div>
	    <div class="inner">
	        <div id="messageWrapper"></div>
	        <html:form action="systemInvoiceHeaderAction.do?proc=list_object" styleClass="listHeader_form">
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">系统发票(ID: <span><bean:write name="headerForm" property="invoiceId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" >
							<ol class="auto">
								<li><label>结算月份:</label><span><bean:write name="headerForm" property="monthly"/></span></li>
							</ol>	
							<ol class="auto">
		                			<li><label>法务实体</label><span><bean:write name="headerForm" property="decodeEntityId"/></span></li>
		                			<li><label>业务类型</label><span><bean:write name="headerForm" property="decodeBusinessTypeId"/></span></li>
			                		<li><label>客户ID</label><span><bean:write name="headerForm" property="clientId"/></span></li>
			                		<li><label>客户名称（中文）</label><span><bean:write name="headerForm" property="clientNameZH"/></span></li>
			                		<li><label>客户名称（英文）</label><span><bean:write name="headerForm" property="clientNameEN"/></span></li>
		                			<li><label>订单ID</label><span><bean:write name="headerForm" property="orderId"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                			<li><label>描述</label><span><bean:write name="headerForm" property="description"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>公司营收</label><span><bean:write name="headerForm" property="billAmountCompany"/></span></li>
		                		<li><label>公司成本</label><span><bean:write name="headerForm" property="costAmountCompany"/></span></li>
		                		<li><label>个税</label><span><bean:write name="headerForm" property="taxAmount"/></span></li>
		                	</ol>
						</div>
					</div>
               	</fieldset>
              </html:form>
         </div>
  	</div>
  	
  	<!-- subInvoice-Information -->
  	<div class="box" id="subInvoice-Information" >
  		<div class="head">
			<label>系统发票拆分明细</label>
		</div>
		<div class="inner">	
			<div id="messageWrapper">
			</div>
			<div class="top">						
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				<input type="button" class="btnAdd" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" >
				<input type="button" class="delete" name="btnCancel" id="btnCancel" value="取消" style="display:none;"/>
			</div>	
			 <div id="detailFormWrapper" style="display:none" >
				<!-- Include Form JSP 包含Form对应的jsp文件 --> 
				<jsp:include page="/contents/finance/invoice/manageSubInvoice.jsp" flush="true"/>
			</div>
			
			<logic:notEmpty name="headerListHolder">														
				<html:form action="systemInvoiceHeaderAction.do?proc=list_object" styleClass="listsickLeaveSalaryDetail_form">
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="headerListHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="headerListHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="headerListHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="headerListHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp 包含tabel对应的jsp文件 -->  
					<jsp:include page="/contents/finance/invoice/table/listSubInvoiceTable.jsp" flush="true"/>
				</div>
			</logic:notEmpty>	
		</div>
  	</div>
</div>

<!-- Employee Popup Box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchClient.jsp"></jsp:include>
	<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_finance_Modules').addClass('current');			
		
		if($('#pageFlag').val()=="Preview"){
			$('#menu_finance_Preview').addClass('selected');
		}else if($('#pageFlag').val()=="Split"){
			$('#menu_finance_Split').addClass('selected');
		}else if($('#pageFlag').val()=="Merge"){
			$('#menu_finance_Merge').addClass('selected');
		}
		
		// 列表点击事件
		$('#btnList').click(function(){
			if (agreest())
			link('systemInvoiceAction.do?proc=list_object&pageFlag=Split');
		});
		
		
		$('#btnCancel').click(function(){
			$('managesickLeaveSalaryDetail_status').val(0);
			$('subInvoice_monthly').val(0);
			$('subInvoice_entityId').val(0);
			$('subInvoice_businessTypeId').val(0);
			$('subInvoice_clientId').val("");
			$('subInvoice_orderId').val("");
			$('subInvoice_billAmountCompany').val("0");
			$('subInvoice_costAmountCompany').val("0");
			$('subInvoice_status').val(0);
			$('subInvoice_description').val("");
			$('#detailFormWrapper').hide();	
			$('#btnCancel').hide();
			$('#btnAdd').val("添加");
		});
		
		$('#btnAdd').click(function(){
			if($('#btnAdd').val()=="添加"){
				$('#detailFormWrapper').show();	
				$('#btnAdd').val("保存");
				$('#btnCancel').show();
				$('.subInvoice_status').val(1);
				$('.subInvoice_billAmountCompany').val("0");
				$('.subInvoice_costAmountCompany').val("0");
			}else if($('#btnAdd').val()=="保存"){
				if( validate_manage_secondary_form() == 0 ){
					submit('subInvoice_form');
				}
			}
		});
	})(jQuery);
</script>

