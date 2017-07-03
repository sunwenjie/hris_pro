<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle">
	        	<logic:equal name="cbBatchForm" property="statusFlag" value="preview">
	        		<bean:message bundle="cb" key="cb.preview.search.title" />
	        	</logic:equal>
	        	<logic:equal name="cbBatchForm" property="statusFlag" value="confirm">
	        		<bean:message bundle="cb" key="cb.confirm.search.title" />
	        	</logic:equal>
	        	<logic:equal name="cbBatchForm" property="statusFlag" value="submit">
	        		<bean:message bundle="cb" key="cb.submit.search.title" />
	        	</logic:equal>
	        </label>
	        <label class="recordId"> &nbsp; (ID: <bean:write name="cbBatchForm" property="batchId"/>)</label>
	    </div>
	    <div class="inner">
	        <div id="messageWrapper"></div>
	        <html:form action="cbAction.do?proc=to_cbDetail" styleClass="listDetail_form">
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
		            	<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.list" />" />
		            </kan:auth>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="cbDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="cbDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="cbDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="cbDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="cbBatchForm" property="encodedId" />" />	
					<input type="hidden" name="headerId" id="headerId" value="<bean:write name="cbHeaderVO" property="encodedId" />" />
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="cbBatchForm" property="pageFlag"/>" />
					<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="cbBatchForm" property="statusFlag"/>" />
				</div>
		        
		         <fieldset>
            		<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,2)" class="first"><bean:message bundle="cb" key="cb.batch" /> (ID:<span><bean:write name="cbBatchForm" property="batchId" /></span>)</li> 
							<%--<li id="tabMenu2" onClick="changeTab(2,3)" ><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>(ID:<span><bean:write name="cbContractVO" property="contractId" /></span>)</li> --%> 
							<li id="tabMenu3" onClick="changeTab(2,2)" class="hover"><bean:message bundle="cb" key="cb.header" /> (ID:<span><bean:write name="cbHeaderVO" property="headerId" /></span>)</li> 
						</ul> 
					</div>
					<div class="tabContent"> 
						<!-- Tab1-Batch Info -->
						<div id="tabContent1" class="kantab" style="display:none" >
			            	<ol class="auto" >
			            		<li><label><bean:message bundle="cb" key="cb.header.month" /></label><span><bean:write name="cbBatchForm" property="monthly"/></span></li>
			            	</ol>
			            	<ol class="auto">
								<li><label><bean:message bundle="cb" key="cb.batch.start.time" /></label><span><bean:write name="cbBatchForm" property="startDate"/></span></li>
								<li><label><bean:message bundle="cb" key="cb.batch.end.time" /></label><span><bean:write name="cbBatchForm" property="endDate"/></span></li>
		                	</ol>
			            	<ol class="auto" >
			            		<logic:notEmpty name="cbBatchForm" property="decodeEntityId">
			            			<logic:notEqual name="cbBatchForm" property="entityId" value="0">
		                				<li><label><bean:message bundle="security" key="security.entity" /></label><span><bean:write name="cbContractVO" property="decodeEntityId"/></span></li>
	                				</logic:notEqual>
                				</logic:notEmpty>
                				<logic:notEmpty name="cbBatchForm" property="decodeBusinessTypeId">
	            					<logic:notEqual name="cbBatchForm" property="businessTypeId" value="0">
		               					<li><label><bean:message bundle="security" key="security.business.type" /></label><span><bean:write name="cbContractVO" property="decodeBusinessTypeId"/></span></li>
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
			               				<span><bean:write name="cbBatchForm" property="orderId"/></span></li>
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
		                			<span><bean:write name="cbBatchForm" property="countContractId"/></span></li>
		                		<li><label><bean:message bundle="cb" key="cb.header.solution.number" /></label><span><bean:write name="cbBatchForm" property="countHeaderId"/></span></li>
		                		<li><label><bean:message bundle="cb" key="cb.header.item.number" /></label><span><bean:write name="cbBatchForm" property="countItemId"/></span></li>
		                	</ol>
			            	<ol class="auto">
			            		<logic:notEmpty name="cbBatchForm" property="description">
			               			<li><label><bean:message bundle="public" key="public.description" /></label><span><bean:write name="cbBatchForm" property="description"/></span></li>
			               		</logic:notEmpty>
			            	</ol>
			            	<ol class="auto">
			            		<li><label><bean:message bundle="cb" key="cb.header.amount.sales.price" /></label><span><bean:write name="cbBatchForm" property="decodeAmountSalesPrice"/></span></li>
			            		<li><label><bean:message bundle="cb" key="cb.header.amount.sales.cost" /></label><span><bean:write name="cbBatchForm" property="decodeAmountSalesCost"/></span></li>
			            	</ol>
		           	 	</div>
	                	<!-- Tab1-Batch Info -->
	                	
	                	<!-- Tab2-Contract Info -->
	                	<%--
	                	<div id="tabContent2" class="kantab" style="display:none" >
	                		<ol class="auto" >
			            		<li><label>商保申报月份</label><span><bean:write name="cbContractVO" property="monthly"/></span></li>
			            	</ol>
			            	<ol class="auto" >
		                		<logic:notEmpty name="cbContractVO" property="employeeId">
		                			<li>
		                				<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label>
		                				<span>
		                					<bean:write name="cbContractVO" property="employeeId"/>
		                					<logic:notEmpty name="cbContractVO" property="decodeEmployStatus">
		                						（<bean:write name="cbContractVO" property="decodeEmployStatus"/>）
		                					</logic:notEmpty>
	                					</span>
                					</li>
		                		</logic:notEmpty>
		                	</ol>
	                		<ol class="auto" >
		                		<logic:notEmpty name="cbContractVO" property="employeeNameZH">
		                			<li>
		                				<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label>
		                				<span>
		                					<bean:write name="cbContractVO" property="employeeNameZH"/>
		                					（<bean:write name="cbContractVO" property="decodeGender"/><logic:notEmpty name="cbContractVO" property="decodeMaritalStatus">，<bean:write name="cbContractVO" property="decodeMaritalStatus"/></logic:notEmpty>）
		                				</span>
		                			</li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="cbContractVO" property="employeeNameEN">
		                			<li><label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label><span><bean:write name="cbContractVO" property="employeeNameEN"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
	                		<ol class="auto" >
	                			<logic:equal name="role" value="1">
		                			<logic:notEmpty name="cbContractVO" property="clientId">
				                		<li><label>客户ID</label><span><bean:write name="cbContractVO" property="clientId"/></span></li>
			                		</logic:notEmpty>
			                		<logic:notEmpty name="cbContractVO" property="clientNameZH">
			                			<li><label>客户名称（中文）</label><span><bean:write name="cbContractVO" property="clientNameZH"/></span></li>
			                		</logic:notEmpty>
			                		<logic:notEmpty name="cbContractVO" property="clientNameEN">
			                			<li><label>客户名称（英文）</label><span><bean:write name="cbContractVO" property="clientNameEN"/></span></li>
			                		</logic:notEmpty>
	                			</logic:equal>
		                		<logic:notEmpty name="cbContractVO" property="orderId">
		                			<li><label><logic:equal name="role" value="1">订单</logic:equal><logic:equal name="role" value="2">结算规则</logic:equal>ID</label><span><bean:write name="cbContractVO" property="orderId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="cbContractVO" property="contractId">
		                			<li>
			                			<label><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="2">商保人次</logic:equal></label><span><bean:write name="cbBatchForm" property="countContractId"/></span>
			                		</li>
			                		<li>
			                			<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>数量</label><span><bean:write name="cbBatchForm" property="countEmployeeId"/></span>
			                		</li>
		                		</logic:notEmpty>
	                		</ol>
	                		<ol class="auto" >
	                			<logic:notEmpty name="cbContractVO" property="decodeEntityId">
			            			<logic:notEqual name="cbContractVO" property="entityId" value="0">
		                				<li><label>法务实体</label><span><bean:write name="cbContractVO" property="decodeEntityId"/></span></li>
	                				</logic:notEqual>
                				</logic:notEmpty>
                				<logic:notEmpty name="cbContractVO" property="decodeBusinessTypeId">
	            					<logic:notEqual name="cbContractVO" property="businessTypeId" value="0">
		               					<li><label>业务类型</label><span><bean:write name="cbContractVO" property="decodeBusinessTypeId"/></span></li>
	               					</logic:notEqual>
               					</logic:notEmpty>
		               		</ol>
		               		<ol class="auto">
								<logic:notEmpty name="cbContractVO" property="contractStartDate">
		                			<li><label>开始日期</label><span><bean:write name="cbContractVO" property="contractStartDate"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="cbContractVO" property="contractEndDate">
		                			<li><label>结束日期</label><span><bean:write name="cbContractVO" property="contractEndDate"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="cbContractVO" property="onboardDate">
		                			<li><label>入职日期</label><span><bean:write name="cbContractVO" property="onboardDate"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="cbContractVO" property="resignDate">
		               				<li><label>离职日期</label><span><bean:write name="cbContractVO" property="resignDate"/></span></li>
		               			</logic:notEmpty>
		               		</ol>
							<ol class="auto">
		                		<logic:notEmpty name="cbContractVO" property="decodeBranch">
		                			<li><label>所属部门</label><span><bean:write name="cbContractVO" property="decodeBranch"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="cbContractVO" property="decodeOwner">
		                			<li><label>所属人</label><span><bean:write name="cbContractVO" property="decodeOwner"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>商保方案数量</label><span><bean:write name="cbContractVO" property="countHeaderId"/></span></li>
		                		<li><label>科目数量</label><span><bean:write name="cbContractVO" property="countItemId"/></span></li>
		                	</ol>
	                		<ol class="auto" >
			            		<li><label>公司营收</label><span><bean:write name="cbContractVO" property="decodeAmountSalesPrice"/></span></li>
	                			<li><label>公司成本</label><span><bean:write name="cbContractVO" property="decodeAmountSalesCost"/></span></li>
	                		</ol>
                		</div>
                		 --%>
                		<!-- Tab2-Contract Info -->
                		
                		<!-- Tab3-Header Info -->
                		<div id="tabContent2" class="kantab" >
                			<ol class="auto" >
			            		<li><label><bean:message bundle="cb" key="cb.header.month" /></label><span><bean:write name="cbHeaderVO" property="monthly"/></span></li>
			            	</ol>
			            	<ol class="auto" >
		               			<li><label><bean:message bundle="cb" key="cb.batch.solution" /></label><span><bean:write name="cbHeaderVO" property="employeeCBId"/> - <bean:write name="cbHeaderVO" property="employeeCBName"/></span></li>
	                			<li><label><bean:message bundle="cb" key="cb.header.status" /></label><span><bean:write name="cbHeaderVO" property="decodeCbStatus"/></span></li>
	                			<li><label><bean:message bundle="cb" key="cb.header.start.date" /></label><span><bean:write name="cbHeaderVO" property="startDate"/></span></li>
	                			<li><label><bean:message bundle="cb" key="cb.header.end.date" /></label><span><bean:write name="cbHeaderVO" property="endDate"/></span></li>
	                			<li><label><bean:message bundle="public" key="public.certificate.type" /></label><span><bean:write name="cbHeaderVO" property="decodeCertificateType"/></span></li>
	                			<li><label><bean:message bundle="public" key="public.certificate.number" /></label><span><bean:write name="cbHeaderVO" property="certificateNumber"/></span></li>
	                			<li><label><bean:message bundle="cb" key="cb.header.residency.type" /></label><span><bean:write name="cbHeaderVO" property="decodeResidencyType"/></span></li>
	                			<li><label><bean:message bundle="cb" key="cb.header.residency.city" /></label><span><bean:write name="cbHeaderVO" property="decodeResidencyCityId"/></span></li>
	                			<li><label><bean:message bundle="cb" key="cb.header.residency.address" /></label><span><bean:write name="cbHeaderVO" property="residencyAddress"/></span></li>
	                		</ol>
                			<ol class="auto" >
			            		<li <logic:equal name="role" value="2">class="hide"</logic:equal>><label><bean:message bundle="cb" key="cb.header.amount.sales.price" /></label><span><bean:write name="cbHeaderVO" property="decodeAmountSalesPrice"/></span></li>
	                			<li><label><bean:message bundle="cb" key="cb.header.amount.sales.cost" /></label><span><bean:write name="cbHeaderVO" property="decodeAmountSalesCost"/></span></li>
	                		</ol>
                		</div>
                		<!-- Tab3-Header Info -->
               		</div>
               	</fieldset>
		        
			</html:form>   
			<!-- 包含商保方案明细列表信息 -->
           	<div id="tableWrapper">
				<jsp:include page="table/listDetailTable.jsp"></jsp:include>   
			</div> 
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单样式、按钮
		$('#menu_cb_Modules').addClass('current');			
		$('#menu_cb_Process').addClass('selected');
		<logic:equal name="cbBatchForm" property="statusFlag" value="preview">
			$('#menu_cb_PurchasePreview').addClass('selected');
			$('#btnApprove').show();
			// 批准事件
			$('#btnApprove').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.approve.records" />')){
						$('.listDetail_form').attr('action', 'cbAction.do?proc=submit_estimation');
						$('#subAction').val('approveObjects');
						submitForm('listDetail_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		<logic:equal name="cbBatchForm" property="statusFlag" value="confirm">
			$('#menu_cb_PurchaseConfirm').addClass('selected');
			$('#btnConfirm').show();
			// 确认事件
			$('#btnConfirm').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.confirm.records" />')){
						$('.listDetail_form').attr('action', 'cbAction.do?proc=submit_confirmation');
						$('#subAction').val('confirmObjects');
						submitForm('listDetail_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		<logic:equal name="cbBatchForm" property="statusFlag" value="submit">
			$('#menu_cb_SubmitToSettlement').addClass('selected');
			$('#btnSubmit').show();
			// 提交事件
			$('#btnSubmit').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
						$('.listDetail_form').attr('action', 'cbAction.do?proc=submit_settlement');
						$('#subAction').val('submitObjects');
						submitForm('listDetail_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>

		// 取消按钮返回list页面
		$('#btnCancel').click(function(){
			if (agreest())
			link('cbAction.do?proc=list_estimation&statusFlag=' + '<bean:write name="cbBatchForm" property="statusFlag"/>');
		});
	})(jQuery);
</script>

