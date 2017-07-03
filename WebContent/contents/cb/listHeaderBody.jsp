<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final String javaObjectName = "com.kan.hro.domain.biz.cb.CBDTO";
%>

<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		  <div class="head">
	        <label id="pageTitle"></label>
	        <label class="recordId">&nbsp; (ID: <bean:write name="cbBatchForm" property="batchId"/>)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchCBHeader_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="cbHeaderAction.do?proc=list_object" method="post" styleClass="searchCBHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="cbHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="cbHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="cbHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="cbHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="cbBatchForm" property="encodedId" />" />	
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="cbBatchForm" property="pageFlag"/>" />
				<input type="hidden" name="searchHeader" id="searchHeader" class="searchHeader" value="true"/>
				<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="cbBatchForm" property="statusFlag"/>" />
				<fieldset>
					<ol class="auto">
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
								</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchCBHeader_employeeId" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.no" /></logic:equal>
								</label>
								<html:text property="employeeNo" maxlength="50" styleClass="searchCBHeader_employeeNo" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
								</label>
								<html:text property="employeeNameZH" maxlength="100" styleClass="searchCBHeader_employeeNameZH" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
								</label>
								<html:text property="employeeNameEN" maxlength="100" styleClass="searchCBHeader_employeeNameEN" /> 
							</li>
							<li>
								<label><bean:message bundle="public" key="public.certificate.number" /></label>
								<html:text property="certificateNumber" maxlength="100" styleClass="searchCBHeader_certificateNumber" /> 
							</li> 
							<li>
								<label><bean:message bundle="cb" key="cb.header.residency.type" /></label> 
								<html:select property="residencyType" styleClass="searchCBHeader_residencyType">
									<html:optionsCollection property="residencyTypes" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
							<li>
								<logic:equal name="role" value="2">
									<label><bean:message bundle="public" key="public.order2.id" /></label>
									<logic:notEmpty name="clientOrderHeaderMappingVOs">
										<html:select property="orderId" styleClass="searchCBHeader_orderId">
											<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
										</html:select>
									</logic:notEmpty>
								</logic:equal>
								<logic:equal name="role" value="1">
									<label><bean:message bundle="public" key="public.order1.id" /></label>
									<logic:empty name="clientOrderHeaderMappingVOs">
			   							<html:text property="orderId" maxlength="10" styleClass="searchCBHeader_orderId" /> 
			   						</logic:empty>
								</logic:equal>
		   					</li>
			   				<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
								</label>
								<html:text property="contractId" maxlength="10" styleClass="searchCBHeader_contractId" /> 
							</li> 
						<li>
							<label><bean:message bundle="cb" key="cb.batch.solution" /></label> 
							<html:select property="cbId" styleClass="searchCBHeader_sbSolutionId">
								<html:optionsCollection property="commercialBenefitSolutions" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="cb" key="cb.header.month" /></label> 
							<html:select property="monthly" styleClass="searchCBHeader_detailMonthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<%-- 
						<li>
							<label>商保状态</label> 
							<html:select property="cbStatus" styleClass="searchCBHeader_cbStatus">
								<html:optionsCollection property="cbStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>--%>
						<li>
							<label><bean:message bundle="cb" key="cb.header.status" /></label> 
							<div style="width: 235px;">
								<logic:iterate id="cbStatus" name="cbHeaderForm" property="cbStatuses" indexId="index">
									<label class="auto">
										<input type="checkbox" name="cbStatusArray" id="cbStatus_<bean:write name="cbStatus" property="mappingId" />" class="cbStatus_<bean:write name="cbStatus" property="mappingId" />" value="<bean:write name="cbStatus" property="mappingId" />" />
										<bean:write name="cbStatus" property="mappingValue" />
									</label>
								</logic:iterate>
							</div>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>

	<!-- Information Manage Form -->
	<div class="box noHeader">
	    <div class="inner">
	        <div id="messageWrapper">
	        </div>
	        <div class="top">
	        	<%String authAccessAction = (String)request.getAttribute("authAccessAction");%>
	        	<kan:auth right="approve" action="<%=authAccessAction%>">
	            	<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.approve" />" style="display: none;" />
	            </kan:auth>
	            <kan:auth right="confirm" action="<%=authAccessAction%>">
	            	<input type="button" class="function" name="btnConfirm" id="btnConfirm" value="<bean:message bundle="public" key="button.confirm" />" style="display: none;" />
	            </kan:auth>
	            <kan:auth right="submit" action="<%=authAccessAction%>">
	            	<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;" />
	            </kan:auth>
	            <kan:auth right="back" action="<%=authAccessAction%>">
	            	<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" style="display: none;" />
	            </kan:auth>
	            <kan:auth right="list" action="<%=authAccessAction%>">
	            	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
	            </kan:auth>
	            <logic:equal name="isExportExcel" value="1">
	            	<kan:auth right="export" action="<%=authAccessAction%>">
	            		<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="showPopup('<%=javaObjectName %>');"><img src="images/appicons/excel_16.png" /></a>
	            	</kan:auth>
	            </logic:equal>	
	            <a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>	
	        <html:form action="cbHeaderAction.do?proc=list_object" styleClass="listHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="cbHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="cbHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="cbHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="cbHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="cbBatchForm" property="encodedId" />" />	
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="cbBatchForm" property="pageFlag"/>" />
				<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="cbBatchForm" property="statusFlag"/>" />
				<input type="hidden" name="cbId" id="cbId" value="" />
	            <fieldset>
            		<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover"><bean:message bundle="cb" key="cb.batch" /> (ID:<span><bean:write name="cbBatchForm" property="batchId" /></span>)</li> 
						</ul> 
					</div>
					<div class="tabContent"> 
						<!-- Tab1-Batch Info -->
						<div id="tabContent1" class="kantab" >
			            	<ol class="auto" >
			            		<li><label><bean:message bundle="cb" key="cb.header.month" /></label><span><bean:write name="cbBatchForm" property="monthly"/></span></li>
			            	</ol>
			            	<ol class="auto">
								<li><label><bean:message bundle="cb" key="cb.batch.start.time" /></label><span><bean:write name="cbBatchForm" property="startDate"/></span></li>
								<li><label><bean:message bundle="cb" key="cb.batch.end.time" /></label><span><bean:write name="cbBatchForm" property="endDate"/></span></li>
		                	</ol>
			            	<ol class="auto" >
			            		<logic:notEmpty name="cbBatchForm" property="decodeEntityId">
			            			<logic:equal name="cbBatchForm" property="entityId" value="0">
		                				<li><label><bean:message bundle="security" key="security.entity" /></label><span><bean:write name="cbBatchForm" property="decodeEntityId"/></span></li>
		                			</logic:equal>
		                		</logic:notEmpty>
			            		<logic:notEmpty name="cbBatchForm" property="decodeBusinessTypeId">
	            					<logic:notEqual name="cbBatchForm" property="businessTypeId" value="0">
		                				<li><label><bean:message bundle="security" key="security.business.type" /></label><span><bean:write name="cbBatchForm" property="decodeBusinessTypeId"/></span></li>
		                			</logic:notEqual>
		                		</logic:notEmpty>
			            	</ol>
			            	<ol class="auto" >
		                		<logic:notEmpty name="cbBatchForm" property="orderId">
			               			<li>
			               				<label>
			               					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
											<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
										</label>
			               				<span><bean:write name="cbBatchForm" property="orderId"/></span>
			               			</li>
			               		</logic:notEmpty>
			            	</ol>
			            	<ol class="auto">
			            		<logic:equal name="role" value="1">
		                			<li><label>客户数量</label><span><bean:write name="cbBatchForm" property="countClientId"/></span></li>
		                		</logic:equal>
		                		<li>
		                			<label>
		                				<logic:equal name="role" value="1"><bean:message bundle="cb" key="cb.header.order1.number" /></logic:equal>
		                				<logic:equal name="role" value="2"><bean:message bundle="cb" key="cb.header.order2.number" /></logic:equal>
		                			</label>
		                			<span><bean:write name="cbBatchForm" property="countOrderId"/></span>
		                		</li>
		                		<li>
		                			<label>
		                				<logic:equal name="role" value="1"><bean:message bundle="cb" key="cb.header.contract1.number" /></logic:equal>
		                				<logic:equal name="role" value="2"><bean:message bundle="cb" key="cb.header.contract2.number" /></logic:equal>
		                			</label>
		                			<span><bean:write name="cbBatchForm" property="countContractId"/></span>
		                		</li>
		                		<li>
		                			<label>
		                				<logic:equal name="role" value="1"><bean:message bundle="cb" key="cb.header.employee1.number" /></logic:equal>
		                				<logic:equal name="role" value="2"><bean:message bundle="cb" key="cb.header.employee2.number" /></logic:equal>
		                			</label>
		                			<span><bean:write name="cbBatchForm" property="countEmployeeId"/></span>
		                		</li>
		                		<li class="hide"><label><bean:message bundle="cb" key="cb.header.solution.number" /></label><span><bean:write name="cbBatchForm" property="countHeaderId"/></span></li>
		                		<li class="hide"><label><bean:message bundle="cb" key="cb.header.item.number" /></label><span><bean:write name="cbBatchForm" property="countItemId"/></span></li>
		                	</ol>
			            	<ol class="auto">
			            		<logic:notEmpty name="cbBatchForm" property="description">
			               			<li><label><bean:message bundle="public" key="public.description" /></label><span><bean:write name="cbBatchForm" property="description"/></span></li>
			               		</logic:notEmpty>
			            	</ol>
			            	<ol class="auto">
			            		<li>
			            			<label></label>
			            		</li>
			            	</ol>
			            	<ol class="auto">
			            		<li <logic:equal name="role" value="2">class="hide"</logic:equal>><label><bean:message bundle="cb" key="cb.header.amount.sales.price" /></label><span><bean:write name="cbBatchForm" property="decodeAmountSalesPrice"/></span></li>
			            		<li><label><bean:message bundle="cb" key="cb.header.amount.sales.cost" /></label><span><bean:write name="cbBatchForm" property="decodeAmountSalesCost"/></span></li>
			            	</ol>
		           	 	</div>
	                	<!-- Tab1-Batch Info -->
               		</div>
               	</fieldset>
            </html:form>
	         <!-- 包含商保方案列表信息 -->
			<div id="tableWrapper">
				<jsp:include page="table/listHeaderTable.jsp"></jsp:include>
			</div>
         </div>
  	</div>
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/selectCBSolution.jsp"></jsp:include>
</div>	
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单样式、按钮
		$('#menu_cb_Modules').addClass('current');			
		$('#menu_cb_Process').addClass('selected');
		<logic:equal name="cbBatchForm" property="statusFlag" value="preview">
			$('#menu_cb_PurchasePreview').addClass('selected');
			$('#pageTitle').html('<bean:message bundle="cb" key="cb.preview.search.title" />');
			$('#btnApprove').show();
			$('#btnRollback').show();
			$('#btnList').show();
			// 批准事件
			$('#btnApprove').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.approve.records" />')){
						$('.searchCBHeader_form').attr('action', 'cbAction.do?proc=submit_estimation');
						$('#subAction').val('approveObjects');
						submitForm('searchCBHeader_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		<logic:equal name="cbBatchForm" property="statusFlag" value="confirm">
			$('#menu_cb_PurchaseConfirm').addClass('selected');
			$('#pageTitle').html('<bean:message bundle="cb" key="cb.confirm.search.title" />');
			$('#btnConfirm').show();
			$('#btnRollback').show();
			// 确认事件
			$('#btnConfirm').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.confirm.records" />')){
						$('.searchCBHeader_form').attr('action', 'cbAction.do?proc=submit_confirmation');
						$('#subAction').val('confirmObjects');
						submitForm('searchCBHeader_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		<logic:equal name="cbBatchForm" property="statusFlag" value="submit">
			$('#menu_cb_SubmitToSettlement').addClass('selected');
			$('#pageTitle').html('<bean:message bundle="cb" key="cb.submit.search.title" />');
			$('#btnSubmit').show();
			// 提交事件
			$('#btnSubmit').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
						$('.searchCBHeader_form').attr('action', 'cbAction.do?proc=submit_settlement');
						$('#subAction').val('submitObjects');
						submitForm('searchCBHeader_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		
		// 退回事件
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
					$('.searchCBHeader_form').attr('action', 'cbAction.do?proc=rollback');
					submitForm('searchCBHeader_form');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		var cbStatus = '<bean:write name="cbHeaderForm" property="cbStatus" />';
		if( cbStatus != null && cbStatus != ''){
			var cbStatusArray = cbStatus.split(',');
			for( var arr in cbStatusArray){
				$('#cbStatus_' + cbStatusArray[arr]).attr('checked',true);
			}
		}

		// 取消按钮返回list页面
		$('#btnCancel').click(function(){
			if (agreest())
			link('cbAction.do?proc=list_estimation&statusFlag=' + '<bean:write name="cbBatchForm" property="statusFlag"/>');
		});
		
		$('#btnList').click(function(){
			if (agreest())
			link('cbAction.do?proc=list_estimation&statusFlag=' + '<bean:write name="cbBatchForm" property="statusFlag"/>');
		});
	})(jQuery);
	
	function resetForm(){
		$(".searchCBHeader_employeeId").val('');
		$(".searchCBHeader_employeeNo").val('');
		$(".searchCBHeader_employeeNameZH").val('');
		$(".searchCBHeader_employeeNameEN").val('');
		$(".searchCBHeader_certificateNumber").val('');
		$(".searchCBHeader_residencyType").val('0');
		$(".searchCBHeader_contractId").val('');
		$(".searchCBHeader_sbSolutionId").val('0');
		$(".searchCBHeader_detailMonthly").val('0');
		$(".searchCBHeader_cbStatus").get(0).selectedIndex=0 ;
	}
</script>

