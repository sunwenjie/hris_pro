<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
	   <div class="head">
	        <label id="pageTitle">����̨�� - ����</label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="orderHeaderVO" property="orderId" />)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchContract_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="settlementContractAction.do?proc=list_object" method="post" styleClass="searchContract_form">
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="contractHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="contractHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="contractHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="contractHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchForm" property="encodedId" />" />	
					<input type="hidden" name="orderHeaderId" id="orderHeaderId" value="<bean:write name="orderHeaderVO" property="encodedId" />" />	
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="batchForm" property="pageFlag" />" />
				<fieldset>
					<ol class="auto">
							<li>
								<label>��ԱID</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchContract_employeeId" /> 
							</li>
							<li>
								<label>��Ա���������ģ�</label>
								<html:text property="employeeNameZH" maxlength="100" styleClass="searchContract_employeeNameZH" /> 
							</li>
							<li>
								<label>��Ա������Ӣ�ģ�</label>
								<html:text property="employeeNameEN" maxlength="100" styleClass="searchContract_employeeNameEN" /> 
							</li>
							<li>
								<label>����Э��ID</label>
								<html:text property="employeeContractId" maxlength="100" styleClass="searchContract_employeeContractId" /> 
							</li> 
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="inner">
	        <div id="messageWrapper"></div>
	       <html:form action="settlementContractAction.do?proc=list_object" styleClass="listContract_form">
		        <div class="top">
		        	<kan:auth right="new" action="<%=SettlementHeaderAction.accessAction%>">
		            	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
		            </kan:auth>
		             <a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
		        </div>
	        
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,2)" class="first">���� (ID: <span><bean:write name="batchForm" property="batchId" /></span>)</li> 
							<li id="tabMenu2" onClick="changeTab(2,2)" class="hover">���� (ID: <span><bean:write name="orderHeaderVO" property="orderId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" style="display:none">
							<ol class="auto">
								<li><label>�����·�</label><span><bean:write name="batchForm" property="monthly"/></span></li>
								<li><label>����ڼ�</label><span><bean:write name="batchForm" property="accountPeriod"/></span></li>
								<li><label>������Ա</label><span><bean:write name="batchForm" property="decodeCreateBy"/></span></li>
							</ol>
							<ol class="auto">
								<li><label>���㿪ʼʱ��</label><span><bean:write name="batchForm" property="startDate"/></span></li>
								<li><label>�������ʱ��</label><span><bean:write name="batchForm" property="endDate"/></span></li>
		                	</ol>
							<ol class="auto">
								<logic:notEmpty name="batchForm" property="decodeEntityId">
		                			<li><label>����ʵ��</label><span><bean:write name="batchForm" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="batchForm" property="decodeBusinessTypeId">
		                			<li><label>ҵ������</label><span><bean:write name="batchForm" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:present name="clientVO">
			                		<li><label>�ͻ�ID</label><span><bean:write name="clientVO" property="clientId"/></span></li>
			                		<li><label>�ͻ����ƣ����ģ�</label><span><bean:write name="clientVO" property="nameZH"/></span></li>
			                		<li><label>�ͻ����ƣ�Ӣ�ģ�</label><span><bean:write name="clientVO" property="nameEN"/></span></li>
		                		</logic:present>
		                		<logic:notEmpty name="batchForm" property="orderId">
		                			<li><label>����ID</label><span><bean:write name="batchForm" property="orderId"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li>
									<label>���㷶Χ</label>
									<div style="width: 220px;">
										<span>
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containSalary" value="1">checked</logic:equal>> ���� &nbsp;&nbsp;&nbsp; 
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containSB" value="1">checked</logic:equal>> �籣������ &nbsp;&nbsp;&nbsp; 
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containCB" value="1">checked</logic:equal>> �̱�<br/>
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containOther" value="1">checked</logic:equal>> ���� &nbsp;&nbsp;&nbsp; 
											<input type="checkbox" disabled <logic:equal name="batchForm" property="containServiceFee" value="1">checked</logic:equal>> �����
										</span>
									</div>
								</li>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>�ͻ�����</label><span><bean:write name="batchForm" property="countClientId"/></span></li>
		                		<li><label>��������</label><span><bean:write name="batchForm" property="countOrderId"/></span></li>
		                		<li><label>��Ա�˴�</label><span><bean:write name="batchForm" property="countContractId"/></span></li>
		                		<li><label>��Ŀ����</label><span><bean:write name="batchForm" property="countItemId"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="batchForm" property="description">
		                			<li><label>����</label><span><bean:write name="batchForm" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
							<ol class="auto">
		                		<li><label>��˾Ӫ��</label><span><bean:write name="batchForm" property="billAmountCompany"/></span></li>
		                		<li><label>��˾�ɱ�</label><span><bean:write name="batchForm" property="costAmountCompany"/></span></li>
		                		<li><label>��������</label><span><bean:write name="batchForm" property="billAmountPersonal"/></span></li>
		                		<li><label>����֧��</label><span><bean:write name="batchForm" property="costAmountPersonal"/></span></li>
		                	</ol>
						</div>
						<div id="tabContent2" class="kantab" >
							<ol class="auto">
								<li><label>�����·�</label><span><bean:write name="batchForm" property="monthly"/></span></li>
								<li><label>����ڼ�</label><span><bean:write name="batchForm" property="accountPeriod"/></span></li>
							</ol>
							<ol class="auto">
								<logic:notEmpty name="orderHeaderVO" property="decodeEntityId">
		                			<li><label>����ʵ��</label><span><bean:write name="orderHeaderVO" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="decodeBusinessTypeId">
		                			<li><label>ҵ������</label><span><bean:write name="orderHeaderVO" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="clientId">
			                		<li><label>�ͻ�ID</label><span><bean:write name="orderHeaderVO" property="clientId"/></span></li>
			                		<li><label>�ͻ�����</label><span><bean:write name="orderHeaderVO" property="clientName"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="orderId">
			                		<li><label>����ID</label><span><bean:write name="orderHeaderVO" property="orderId"/></span></li>
			                	</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="taxName">
		                			<li><label>˰��</label><span><bean:write name="orderHeaderVO" property="taxName"/> <logic:notEmpty name="orderHeaderVO" property="taxRemark">(<bean:write name="orderHeaderVO" property="taxRemark"/>)</logic:notEmpty></span></li>
		                		</logic:notEmpty>
		                	</ol>
							<ol class="auto">
								<logic:notEmpty name="orderHeaderVO" property="description">
		                			<li><label>����</label><span><bean:write name="orderHeaderVO" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
								<logic:notEmpty name="orderHeaderVO" property="startDate">
		                			<li><label>��ʼ����</label><span><bean:write name="orderHeaderVO" property="startDate"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="endDate">
		                			<li><label>��������</label><span><bean:write name="orderHeaderVO" property="endDate"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="orderHeaderVO" property="decodeBranch">
		                			<li><label>��������</label><span><bean:write name="orderHeaderVO" property="decodeBranch"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderVO" property="decodeOwner">
		                			<li><label>������</label><span><bean:write name="orderHeaderVO" property="decodeOwner"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>��Ա�˴�</label><span><bean:write name="orderHeaderVO" property="countContractId"/></span></li>
		                		<li><label>��Ŀ����</label><span><bean:write name="orderHeaderVO" property="countItemId"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>��˾Ӫ��</label><span><bean:write name="orderHeaderVO" property="billAmountCompany"/></span></li>
		                		<li><label>��˾�ɱ�</label><span><bean:write name="orderHeaderVO" property="costAmountCompany"/></span></li>
		                		<li><label>��������</label><span><bean:write name="orderHeaderVO" property="billAmountPersonal"/></span></li>
		                		<li><label>����֧��</label><span><bean:write name="orderHeaderVO" property="costAmountPersonal"/></span></li>
							</ol>
						</div>
					</div>
               	</fieldset>
             </html:form>
			<!-- ��������Э���б��Tabҳ�� -->
			<div id="tableWrapper">
				<jsp:include page="table/listContractTable.jsp"></jsp:include>
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_settlement_Modules').addClass('current');			
		$('#menu_settlement_OrderSubmited').addClass('selected');
		
		// ȡ���¼�
		$('#btnList').click(function(){
			if (agreest())
			link('settlementAction.do?proc=list_estimation');
		});
	})(jQuery);
	
	function resetForm(){
		$(".searchContract_employeeId").val("");
		$(".searchContract_employeeNameZH").val("");
		$(".searchContract_employeeNameEN").val("");
		$(".searchContract_employeeContractId").val("");
	}
</script>

