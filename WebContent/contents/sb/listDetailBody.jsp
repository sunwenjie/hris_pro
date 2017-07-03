<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<!-- Information Manage Form -->
	<div class="box noHeader">
	    <logic:equal name="role" value="3">
	    	<div class="head">
		        <label id="pageTitle">供应商</label>
		        <label class="recordId">&nbsp;(ID: <bean:write name="vendorVO" property="vendorId" />)</label>
		    </div>
	    </logic:equal>
	    
	    <div class="inner">
	        <div id="messageWrapper"></div>
	        <logic:notEqual name="role" value="3">
		        <html:form action="sbAction.do?proc=to_sbDetail" styleClass="listDetail_form">
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
			            <kan:auth right="list" action="<%=authAccessAction%>">
		          	  		<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
		          	  	</kan:auth>
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbDetailHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbDetailHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="sbDetailHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbDetailHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />
						<input type="hidden" name="batchId" id="batchId" value="<bean:write name="sbBatchForm" property="encodedId" />" />	
						<input type="hidden" name="headerId" id="headerId" value="<bean:write name="sbHeaderVO" property="encodedId" />" />
						<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="sbBatchForm" property="pageFlag"/>" />
						<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="sbBatchForm" property="statusFlag"/>" />
					</div>
	       		</html:form>
	       		<form>
					<fieldset>
	            		<div class="tabMenu"> 
							<ul> 
								<li id="tabMenu1" onClick="changeTab(1,2)" class="first"><bean:message bundle="sb" key="sb.batch" /> (ID: <bean:write name="sbBatchForm" property="batchId" />)</li> 
								<%-- <li id="tabMenu2" onClick="changeTab(2,2)" ><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal> (ID: <bean:write name="sbContractVO" property="contractId" />)</li> --%> 
								<li id="tabMenu2" onClick="changeTab(2,2)" class="hover"><bean:message bundle="sb" key="sb.number" /> (ID: <bean:write name="sbHeaderVO" property="headerId" />)</li> 
							</ul> 
						</div>
						<div class="tabContent"> 
							<!-- Tab1-Batch Info -->
							<div id="tabContent1" class="kantab" style="display:none" >
				            	<ol class="auto" >
				            		<li><label><bean:message bundle="sb" key="sb.bill.monthly" /></label><span><bean:write name="sbBatchForm" property="monthly"/></span></li>
				            	</ol>
				            	<ol class="auto">
									<li><label><bean:message bundle="sb" key="sb.oper.start.time" /></label><span><bean:write name="sbBatchForm" property="startDate"/></span></li>
									<li><label><bean:message bundle="sb" key="sb.oper.end.time" /></label><span><bean:write name="sbBatchForm" property="endDate"/></span></li>
			                	</ol>
				            	<ol class="auto" >
				            		<logic:notEmpty name="sbBatchForm" property="decodeEntityId">
				            			<logic:notEqual name="sbBatchForm" property="entityId" value="0">
			                				<li><label><bean:message bundle="security" key="security.entity" /></label><span><bean:write name="sbBatchForm" property="decodeEntityId"/></span></li>
		                				</logic:notEqual>
			                		</logic:notEmpty>
				            		<logic:notEmpty name="sbBatchForm" property="decodeBusinessTypeId">
			            				<logic:notEqual name="sbBatchForm" property="businessTypeId" value="0">
			                				<li><label><bean:message bundle="security" key="security.business.type" /></label><span><bean:write name="sbBatchForm" property="decodeBusinessTypeId"/></span></li>
		                				</logic:notEqual>
			                		</logic:notEmpty>
				            	</ol>
				            	<ol class="auto">
				            		<logic:notEmpty name="sbBatchForm" property="decodeCityId">
				               			<li><label><bean:message bundle="public" key="public.city" /></label><span><bean:write name="sbBatchForm" property="decodeCityId"/></span></li>
				               		</logic:notEmpty>
				               	</ol>
				            	<ol class="auto" >
			                		<logic:notEmpty name="sbBatchForm" property="orderId">
				               			<li>
				               				<label>
				               					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
			               						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
				               				</label><span><bean:write name="sbBatchForm" property="orderId"/></span>
				               			</li>
				               		</logic:notEmpty>
				            	</ol>
				            	<ol class="auto">
				            		<logic:equal name="role" value="1">
				                		<li><label>客户数量</label><span><bean:write name="sbBatchForm" property="countClientId"/></span></li>
				                	</logic:equal>
			                		<li><label><logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.order1.number" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.order2.number" /></logic:equal></label><span><bean:write name="sbBatchForm" property="countOrderId"/></span></li>
			                		<li><label><logic:equal name="role" value="1"><bean:message bundle="sb" key="sb.contract1.number" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="sb" key="sb.contract2.number" /></logic:equal></label><span><bean:write name="sbBatchForm" property="countContractId"/></span></li>
			                		<li><label><bean:message bundle="sb" key="sb.people.number" /></label><span><bean:write name="sbBatchForm" property="countHeaderId"/></span></li>
			                		<li><label><bean:message bundle="sb" key="sb.item.number" /></label><span><bean:write name="sbBatchForm" property="countItemId"/></span></li>
			                	</ol>
				            	<ol class="auto">
				            		<logic:notEmpty name="sbBatchForm" property="description">
				               			<li><label><bean:message bundle="public" key="public.description" /></label><span><bean:write name="sbBatchForm" property="description"/></span></li>
				               		</logic:notEmpty>
				            	</ol>
				            	<ol class="auto">
				            		<li><label><bean:message bundle="sb" key="sb.company" /></label><span><bean:write name="sbBatchForm" property="decodeAmountCompany"/></span></li>
				            		<li><label><bean:message bundle="sb" key="sb.personal" /></label><span><bean:write name="sbBatchForm" property="decodeAmountPersonal"/></span></li>
				            	</ol>
				           	 </div>
		                	<!-- Tab1-Batch Info -->
		                	
		                	<!-- Tab2-Contract Info -->
		                	<%--
		                	<div id="tabContent2" class="kantab" style="display:none">
		                		<ol class="auto" >
				            		<li><label>账单月份</label><span><bean:write name="sbContractVO" property="monthly"/></span></li>
				            	</ol>
				            	<ol class="auto" >
			                		<logic:notEmpty name="sbContractVO" property="employeeId">
			                			<li>
			                				<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label>
			                				<span>
			                					<bean:write name="sbContractVO" property="employeeId"/>
			                					<logic:notEmpty name="sbContractVO" property="decodeEmployStatus">
			                						（<bean:write name="sbContractVO" property="decodeEmployStatus"/>）
			                					</logic:notEmpty>
			                				</span>
			                			</li>
			                		</logic:notEmpty>
			                	</ol>
					            <ol class="auto" >
				                	<logic:notEmpty name="sbContractVO" property="employeeNameZH">
				                		<li>
				                			<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label>
				                			<span>
				                				<bean:write name="sbContractVO" property="employeeNameZH"/>
				                				（<bean:write name="sbContractVO" property="decodeGender"/><logic:notEmpty name="sbContractVO" property="decodeMaritalStatus">，<bean:write name="sbContractVO" property="decodeMaritalStatus"/></logic:notEmpty>）
				                			</span>
				                		</li>
				                	</logic:notEmpty>
				                	<logic:notEmpty name="sbContractVO" property="employeeNameEN">
				                		<li><label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label><span><bean:write name="sbContractVO" property="employeeNameEN"/></span></li>
				                	</logic:notEmpty>
			                	</ol>
			                	<ol class="auto" >
			                		<logic:equal name="role" value="1">
			                			<logic:notEmpty name="sbContractVO" property="clientId">
					                		<li><label>客户ID</label><span><bean:write name="sbContractVO" property="clientId"/></span></li>
					                	</logic:notEmpty>
					                </logic:equal>
					            </ol>
					            <ol class="auto" >
					                <logic:equal name="role" value="1">
					                	<logic:notEmpty name="sbContractVO" property="clientNameZH">
					                		<li><label>客户名称（中文）</label><span><bean:write name="sbContractVO" property="clientNameZH"/></span></li>
				                		</logic:notEmpty>
				                		<logic:notEmpty name="sbContractVO" property="clientNameEN">
					                		<li><label>客户名称（英文）</label><span><bean:write name="sbContractVO" property="clientNameEN"/></span></li>
				                		</logic:notEmpty>
				                	</logic:equal>
			                		<logic:notEmpty name="sbContractVO" property="orderId">
			                			<li><label><logic:equal name="role" value="1">订单</logic:equal><logic:equal name="role" value="2">结算规则</logic:equal>ID</label><span><bean:write name="sbContractVO" property="orderId"/></span></li>
			                		</logic:notEmpty>
			                		<logic:notEmpty name="sbContractVO" property="contractId">
			                			<li><label><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID</label><span><bean:write name="sbContractVO" property="contractId"/></span></li>
			                		</logic:notEmpty>
				                </ol>
			                	<ol class="auto" >
		                			<logic:notEmpty name="sbContractVO" property="decodeEntityId">
				            			<logic:notEqual name="sbContractVO" property="entityId" value="0">
			                				<li><label>法务实体</label><span><bean:write name="sbContractVO" property="decodeEntityId"/></span></li>
		                				</logic:notEqual>
			                		</logic:notEmpty>
				            		<logic:notEmpty name="sbContractVO" property="decodeBusinessTypeId">
			            				<logic:notEqual name="sbContractVO" property="businessTypeId" value="0">
			                				<li><label>业务类型</label><span><bean:write name="sbContractVO" property="decodeBusinessTypeId"/></span></li>
		                				</logic:notEqual>
			                		</logic:notEmpty>
			               		</ol>
		                		<ol class="auto">
									<logic:notEmpty name="sbContractVO" property="contractStartDate">
			                			<li><label>开始日期</label><span><bean:write name="sbContractVO" property="contractStartDate"/></span></li>
			                		</logic:notEmpty>
			                		<logic:notEmpty name="sbContractVO" property="contractEndDate">
			                			<li><label>结束日期</label><span><bean:write name="sbContractVO" property="contractEndDate"/></span></li>
			                		</logic:notEmpty>
			                	</ol>
			                	<ol class="auto">
			                		<logic:notEmpty name="sbContractVO" property="onboardDate">
			                			<li><label>入职日期</label><span><bean:write name="sbContractVO" property="onboardDate"/></span></li>
			                		</logic:notEmpty>
			                		<logic:notEmpty name="sbContractVO" property="resignDate">
			               				<li><label>离职日期</label><span><bean:write name="sbContractVO" property="resignDate"/></span></li>
			               			</logic:notEmpty>
			               		</ol>
								<ol class="auto">
			                		<logic:notEmpty name="sbContractVO" property="decodeBranch">
			                			<li><label>所属部门</label><span><bean:write name="sbContractVO" property="decodeBranch"/></span></li>
			                		</logic:notEmpty>
			                		<logic:notEmpty name="sbContractVO" property="decodeOwner">
			                			<li><label>所属人</label><span><bean:write name="sbContractVO" property="decodeOwner"/></span></li>
			                		</logic:notEmpty>
			                	</ol>
			                	<ol class="auto">
			                		<li><label>社保公积金方案数量</label><span><bean:write name="sbContractVO" property="countHeaderId"/></span></li>
			                		<li><label>科目数量</label><span><bean:write name="sbContractVO" property="countItemId"/></span></li>
			                	</ol>
		                		<ol class="auto" >
				            		<li><label>社保公积金（公司）</label><span><bean:write name="sbContractVO" property="decodeAmountCompany"/></span></li>
		                			<li><label>社保公积金（个人）</label><span><bean:write name="sbContractVO" property="decodeAmountPersonal"/></span></li>
		                		</ol>
	                		</div>
	                		 --%>
	                		<!-- Tab2-Contract Info -->
	                		
	                		<!-- Tab3-Header Info -->
	                		<div id="tabContent2" class="kantab">
	                			<ol class="auto" >
				            		<li><label><bean:message bundle="sb" key="sb.bill.monthly" /></label><span><bean:write name="sbHeaderVO" property="monthly"/></span></li>
			            			<logic:notEmpty name="sbHeaderVO" property="vendorId">
				            			<li><label><bean:message bundle="sb" key="sb.vendor.id" /></label><span><bean:write name="sbHeaderVO" property="vendorId"/></span></li>
				            		</logic:notEmpty>
			            			<logic:notEmpty name="sbHeaderVO" property="vendorNameZH">
				            			<li><label><bean:message bundle="sb" key="sb.vendor.name.cn" /></label><span><bean:write name="sbHeaderVO" property="vendorNameZH"/></span></li>
				            		</logic:notEmpty>
			            			<logic:notEmpty name="sbHeaderVO" property="vendorNameEN">
				            			<li><label><bean:message bundle="sb" key="sb.vendor.name.en" /></label><span><bean:write name="sbHeaderVO" property="vendorNameEN"/></span></li>
				            		</logic:notEmpty>
				            	</ol>
				            	<ol class="auto" >
				            		<li><label><bean:message bundle="sb" key="sb.solution.name" /></label><span><bean:write name="sbHeaderVO" property="employeeSBId"/> - <bean:write name="sbHeaderVO" property="employeeSBName"/></span></li>
				            		<li><label><bean:message bundle="sb" key="sb.status" /></label><span><bean:write name="sbHeaderVO" property="decodeSbStatus"/></span></li>
				            		<li><label><bean:message bundle="sb" key="sb.start.date" /></label><span><bean:write name="sbHeaderVO" property="startDate"/></span></li>
			               			<li><label><bean:message bundle="sb" key="sb.end.date" /></label><span><bean:write name="sbHeaderVO" property="endDate"/></span></li>
			               			<li><label><bean:message bundle="public" key="public.certificate.type" /></label><span><bean:write name="sbHeaderVO" property="decodeCertificateType"/></span></li>
			               			<li><label><bean:message bundle="public" key="public.certificate.number" /></label><span><bean:write name="sbHeaderVO" property="certificateNumber"/></span></li>
			               			<logic:notEmpty name="sbHeaderVO" property="decodeResidencyType">
			               				<li><label><bean:message bundle="sb" key="sb.employee.residency.type" /></label><span><bean:write name="sbHeaderVO" property="decodeResidencyType"/></span></li>
			               			</logic:notEmpty>
			               		</ol>
			               		<ol class="auto" >
			               			<logic:notEmpty name="sbHeaderVO" property="decodeResidencyCityId">
			               				<li><label><bean:message bundle="sb" key="sb.employee.residency.city" /></label><span><bean:write name="sbHeaderVO" property="decodeResidencyCityId"/></span></li>
			               			</logic:notEmpty>
			               			<logic:notEmpty name="sbHeaderVO" property="decodeCityId">
			               				<li><label><bean:message bundle="sb" key="sb.city" /></label><span><bean:write name="sbHeaderVO" property="decodeCityId"/></span></li>
			               			</logic:notEmpty>
			               			<logic:notEmpty name="sbHeaderVO" property="residencyAddress">
			               				<li><label><bean:message bundle="sb" key="sb.employee.residency.address" /></label><span><bean:write name="sbHeaderVO" property="residencyAddress"/></span></li>
			               			</logic:notEmpty>
				            	</ol>	
				            	<ol class="auto" >
				            		<logic:equal name="sbHeaderVO" property="needSBCard" value="1">
			               				<li><label><bean:message bundle="sb" key="sb.employee.need.sb.card" /></label><span><bean:write name="sbHeaderVO" property="decodeNeedSBCard"/></span></li>
			               			</logic:equal>
			               			<logic:equal name="sbHeaderVO" property="needMedicalCard" value="1">
			               				<li><label><bean:message bundle="sb" key="sb.employee.need.medical.card" /></label><span><bean:write name="sbHeaderVO" property="decodeNeedMedicalCard"/></span></li>
			               			</logic:equal>
			               			<logic:notEmpty name="sbHeaderVO" property="sbNumber">
			               				<li><label><bean:message bundle="sb" key="sb.employee.sb.number" /></label><span><bean:write name="sbHeaderVO" property="sbNumber"/></span></li>
			               			</logic:notEmpty>
			               			<logic:notEmpty name="sbHeaderVO" property="medicalNumber">
			               				<li><label><bean:message bundle="sb" key="sb.employee.medical.number" /></label><span><bean:write name="sbHeaderVO" property="medicalNumber"/></span></li>
			               			</logic:notEmpty>
			               			<logic:notEmpty name="sbHeaderVO" property="fundNumber">
			               				<li><label><bean:message bundle="sb" key="sb.employee.fund.number" /></label><span><bean:write name="sbHeaderVO" property="fundNumber"/></span></li>
			               			</logic:notEmpty>
			               		</ol>
	                			<ol class="auto">
				            		<li><label><bean:message bundle="sb" key="sb.company" /></label><span><bean:write name="sbHeaderVO" property="decodeAmountCompany"/></span></li>
		                			<li><label><bean:message bundle="sb" key="sb.personal" /></label><span><bean:write name="sbHeaderVO" property="decodeAmountPersonal"/></span></li>
		                		</ol>
	                		</div>
	                		<!-- Tab3-Header Info -->
	               		</div>
	               	</fieldset>
	       		</form>
       		</logic:notEqual>
       		
       		<logic:equal name="role" value="3">
		        <html:form action="sbAction.do?proc=to_sbDetail_inVendor" styleClass="listDetail_form">
			        <div class="top">
			        	<logic:equal name="sbBatchForm" property="additionalStatus" value="2">
				            <input type="button" class="function" name="btnConfirm" id="btnConfirm" value="<bean:message bundle="public" key="button.confirm" />" />
			        	</logic:equal>
		          	  	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.return" />" onclick="link('vendorAction.do?proc=to_objectModify_inVendor');" />
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbDetailHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbDetailHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="sbDetailHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbDetailHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />
						<input type="hidden" name="vendorId" id="vendorId" value="<bean:write name="vendorVO" property="encodedId" />" />
						<input type="hidden" name="batchId" id="batchId" value="<bean:write name="sbBatchForm" property="encodedId" />" />
						<input type="hidden" name="headerId" id="headerId" value="<bean:write name="sbHeaderVO" property="encodedId" />" />
						<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="sbBatchForm" property="pageFlag"/>" />
						<input type="hidden" name="statusFlag" id="statusFlag" value="<bean:write name="sbBatchForm" property="statusFlag"/>" />
						<input type="hidden" name="additionalStatus" id="additionalStatus" value="<bean:write name="sbBatchForm" property="additionalStatus" />" />
					</div>
				</html:form>
				<form>
					<fieldset>
	            		<div class="tabMenu"> 
							<ul> 
								<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover"><bean:message bundle="sb" key="sb.vendor" /> (ID: <bean:write name="vendorVO" property="vendorId" />)</li> 
							</ul> 
						</div>
						<div class="tabContent"> 
							<!-- Tab1-Batch Info -->
							<div id="tabContent1" class="kantab" >
	            				<ol class="auto" >
				            		<li><label><bean:message bundle="sb" key="sb.bill.monthly" /></label><span><bean:write name="sbHeaderVO" property="monthly"/></span></li>
			            			<logic:notEmpty name="sbHeaderVO" property="vendorId">
				            			<li><label><bean:message bundle="sb" key="sb.vendor.id" /></label><span><bean:write name="sbHeaderVO" property="vendorId"/></span></li>
				            		</logic:notEmpty>
			            			<logic:notEmpty name="sbHeaderVO" property="vendorNameZH">
				            			<li><label><bean:message bundle="sb" key="sb.vendor.name.cn" /></label><span><bean:write name="sbHeaderVO" property="vendorNameZH"/></span></li>
				            		</logic:notEmpty>
			            			<logic:notEmpty name="sbHeaderVO" property="vendorNameEN">
				            			<li><label><bean:message bundle="sb" key="sb.vendor.name.en" /></label><span><bean:write name="sbHeaderVO" property="vendorNameEN"/></span></li>
				            		</logic:notEmpty>
				            	</ol>
				            	<ol class="auto">
				            		<li><label><bean:message bundle="public" key="public.employee1.id" /></label><span><bean:write name="sbHeaderVO" property="employeeId"/></span></li>
				            	</ol>
				            	<ol class="auto">
				            		<li><label><bean:message bundle="public" key="public.employee1.name.cn" /></label><span><bean:write name="sbHeaderVO" property="employeeNameZH"/></span></li>
				            		<li><label><bean:message bundle="public" key="public.employee1.name.en" /></label><span><bean:write name="sbHeaderVO" property="employeeNameEN"/></span></li>
				            	</ol>
				            	<ol class="auto" >
				            		<li><label><bean:message bundle="sb" key="sb.solution" /></label><span><bean:write name="sbHeaderVO" property="employeeSBId"/> - <bean:write name="sbHeaderVO" property="employeeSBName"/></span></li>
				            		<li><label><bean:message bundle="sb" key="sb.status" /></label><span><bean:write name="sbHeaderVO" property="decodeSbStatus"/></span></li>
				            		<li><label><bean:message bundle="sb" key="sb.start.date" /></label><span><bean:write name="sbHeaderVO" property="startDate"/></span></li>
			               			<li><label><bean:message bundle="sb" key="sb.end.date" /></label><span><bean:write name="sbHeaderVO" property="endDate"/></span></li>
			               			<li><label><bean:message bundle="public" key="public.certificate.type" /></label><span><bean:write name="sbHeaderVO" property="decodeCertificateType"/></span></li>
			               			<li><label><bean:message bundle="public" key="public.certificate.number" /></label><span><bean:write name="sbHeaderVO" property="certificateNumber"/></span></li>
			               			<logic:notEmpty name="sbHeaderVO" property="decodeResidencyType">
			               				<li><label><bean:message bundle="sb" key="sb.employee.residency.type" /></label><span><bean:write name="sbHeaderVO" property="decodeResidencyType"/></span></li>
			               			</logic:notEmpty>
			               		</ol>
			               		<ol class="auto" >
			               			<logic:notEmpty name="sbHeaderVO" property="decodeResidencyCityId">
			               				<li><label><bean:message bundle="sb" key="sb.employee.residency.city" /></label><span><bean:write name="sbHeaderVO" property="decodeResidencyCityId"/></span></li>
			               			</logic:notEmpty>
			               			<logic:notEmpty name="sbHeaderVO" property="decodeCityId">
			               				<li><label><bean:message bundle="sb" key="sb.city" /></label><span><bean:write name="sbHeaderVO" property="decodeCityId"/></span></li>
			               			</logic:notEmpty>
			               			<logic:notEmpty name="sbHeaderVO" property="residencyAddress">
			               				<li><label><bean:message bundle="sb" key="sb.employee.residency.address" /></label><span><bean:write name="sbHeaderVO" property="residencyAddress"/></span></li>
			               			</logic:notEmpty>
				            	</ol>	
				            	<ol class="auto" >
				            		<logic:equal name="sbHeaderVO" property="needSBCard" value="1">
			               				<li><label><bean:message bundle="sb" key="sb.employee.need.sb.card" /></label><span><bean:write name="sbHeaderVO" property="decodeNeedSBCard"/></span></li>
			               			</logic:equal>
			               			<logic:equal name="sbHeaderVO" property="needMedicalCard" value="1">
			               				<li><label><bean:message bundle="sb" key="sb.employee.need.medical.card" /></label><span><bean:write name="sbHeaderVO" property="decodeNeedMedicalCard"/></span></li>
			               			</logic:equal>
			               			<logic:notEmpty name="sbHeaderVO" property="sbNumber">
			               				<li><label><bean:message bundle="sb" key="sb.employee.sb.number" /></label><span><bean:write name="sbHeaderVO" property="sbNumber"/></span></li>
			               			</logic:notEmpty>
			               			<logic:notEmpty name="sbHeaderVO" property="medicalNumber">
			               				<li><label><bean:message bundle="sb" key="sb.employee.medical.number" /></label><span><bean:write name="sbHeaderVO" property="medicalNumber"/></span></li>
			               			</logic:notEmpty>
			               			<logic:notEmpty name="sbHeaderVO" property="fundNumber">
			               				<li><label><bean:message bundle="sb" key="sb.employee.fund.number" /></label><span><bean:write name="sbHeaderVO" property="fundNumber"/></span></li>
			               			</logic:notEmpty>
			               		</ol>
		              			<ol class="auto" >
				            		<li><label><bean:message bundle="sb" key="sb.company" /></label><span><bean:write name="sbHeaderVO" property="decodeAmountCompany"/></span></li>
			               			<li><label><bean:message bundle="sb" key="sb.personal" /></label><span><bean:write name="sbHeaderVO" property="decodeAmountPersonal"/></span></li>
			               		</ol>
			               	</div>
	            		</div>
	            	</fieldset>
				</form>
			</logic:equal>
	       	<!-- 包含社保公积金方案明细列表信息 -->
	       	<div id="tableWrapper">
				<jsp:include page="/contents/sb/table/listDetailTable.jsp"></jsp:include>
			</div>
			<div class="bottom"><p/></div>
		</div>
  	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 初始化菜单样式、按钮
		$('#menu_sb_Modules').addClass('current');			
		$('#menu_sb_Process').addClass('selected');
		<logic:equal name="sbBatchForm" property="statusFlag" value="preview">
			$('#menu_sb_DeclarationPreview').addClass('selected');
			$('#btnApprove').show();
			// 批准事件
			$('#btnApprove').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.approve.records" />')){
						$('.listDetail_form').attr('action', 'sbAction.do?proc=submit_estimation');
						$('#subAction').val('approveObjects');
						submitForm('listDetail_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		<logic:equal name="sbBatchForm" property="statusFlag" value="confirm">
			$('#menu_sb_DeclarationConfirm').addClass('selected');
			$('#btnConfirm').show();
			// 确认事件
			$('#btnConfirm').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.confirm.records" />')){
						$('.listDetail_form').attr('action', 'sbAction.do?proc=submit_confirmation');
						$('#subAction').val('confirmObjects');
						submitForm('listDetail_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		<logic:equal name="sbBatchForm" property="statusFlag" value="submit">
			$('#menu_sb_SubmitToSettlement').addClass('selected');
			$('#btnSubmit').show();
			// 提交事件
			$('#btnSubmit').click(function(){
				if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
					if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
						$('.listDetail_form').attr('action', 'sbAction.do?proc=submit_settlement');
						$('#subAction').val('submitObjects');
						submitForm('listDetail_form');
					}
				}else{
					alert('<bean:message bundle="public" key="popup.not.selected.records" />');
				}
			});
		</logic:equal>
		
		// JS of the List
		kanList_init();
		kanCheckbox_init();

		// 列表按钮返回list页面
		<logic:notEqual name="role" value="3">
			$('#btnList').click(function(){
				if (agreest())
				link('sbAction.do?proc=list_estimation&statusFlag=' + '<bean:write name="sbBatchForm" property="statusFlag"/>');
			});
		</logic:notEqual>
	})(jQuery);
</script>

