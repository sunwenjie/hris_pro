<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementTempAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
	   	   <div class="head">
	        <label id="pageTitle">�������� - ����</label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="orderHeaderTempVO" property="orderId" />)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchContractTemp_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="settlementContractTempAction.do?proc=list_object" method="post" styleClass="searchContractTemp_form">
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="contractTempHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="contractTempHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="contractTempHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="contractTempHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchTempForm" property="encodedId" />" />	
					<input type="hidden" name="orderHeaderId" id="orderHeaderId" value="<bean:write name="orderHeaderTempVO" property="encodedId" />" />	
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="batchTempForm" property="pageFlag" />" />
				<fieldset>
					<ol class="auto">
							<li>
								<label>��ԱID</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchContractTemp_employeeId" /> 
							</li>
							<li>
								<label>��Ա���������ģ�</label>
								<html:text property="employeeNameZH" maxlength="100" styleClass="searchContractTemp_employeeNameZH" /> 
							</li>
							<li>
								<label>��Ա������Ӣ�ģ�</label>
								<html:text property="employeeNameEN" maxlength="100" styleClass="searchContractTemp_employeeNameEN" /> 
							</li>
							<li>
								<label>����Э��ID</label>
								<html:text property="employeeContractId" maxlength="100" styleClass="searchContractTemp_employeeContractId" /> 
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
	       <html:form action="settlementContractTempAction.do?proc=list_object" styleClass="listContract_form">
		        <div class="top">
		        	<kan:auth right="posting" action="<%=SettlementTempAction.accessAction%>">
		            	<input type="button" class="function" name="btnConfirm" id="btnConfirm" value="����" />
		            </kan:auth>
		            <kan:auth right="back" action="<%=SettlementTempAction.accessAction%>">
		            	<input type="button" class="delete" name="btnRollback" id="btnRollback" value="�˻�" />
		            </kan:auth>
		            <kan:auth right="list" action="<%=SettlementTempAction.accessAction%>">
		            	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
		            </kan:auth>
		             <a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
		        </div>
	        
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,2)" class="first">���� (ID: <span><bean:write name="batchTempForm" property="batchId" /></span>)</li> 
							<li id="tabMenu2" onClick="changeTab(2,2)" class="hover">���� (ID: <span><bean:write name="orderHeaderTempVO" property="orderId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" style="display:none">
							<ol class="auto">
								<li><label>�����·�</label><span><bean:write name="batchTempForm" property="monthly"/></span></li>
								<li><label>����ڼ�</label><span><bean:write name="batchTempForm" property="accountPeriod"/></span></li>
								<li><label>������Ա</label><span><bean:write name="batchTempForm" property="decodeCreateBy"/></span></li>
							</ol>
							<ol class="auto">
								<li><label>���㿪ʼʱ��</label><span><bean:write name="batchTempForm" property="startDate"/></span></li>
								<li><label>�������ʱ��</label><span><bean:write name="batchTempForm" property="endDate"/></span></li>
		                	</ol>
							<ol class="auto">
								<logic:notEmpty name="batchTempForm" property="decodeEntityId">
		                			<li><label>����ʵ��</label><span><bean:write name="batchTempForm" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="batchTempForm" property="decodeBusinessTypeId">
		                			<li><label>ҵ������</label><span><bean:write name="batchTempForm" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:present name="clientVO">
			                		<li><label>�ͻ�ID</label><span><bean:write name="clientVO" property="clientId"/></span></li>
			                		<li><label>�ͻ����ƣ����ģ�</label><span><bean:write name="clientVO" property="nameZH"/></span></li>
			                		<li><label>�ͻ����ƣ�Ӣ�ģ�</label><span><bean:write name="clientVO" property="nameEN"/></span></li>
		                		</logic:present>
		                		<logic:notEmpty name="batchTempForm" property="orderId">
		                			<li><label>����ID</label><span><bean:write name="batchTempForm" property="orderId"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li>
									<label>���㷶Χ</label>
									<div style="width: 220px;">
										<span>
											<input type="checkbox" disabled <logic:equal name="batchTempForm" property="containSalary" value="1">checked</logic:equal>> ���� &nbsp;&nbsp;&nbsp; 
											<input type="checkbox" disabled <logic:equal name="batchTempForm" property="containSB" value="1">checked</logic:equal>> �籣������ &nbsp;&nbsp;&nbsp; 
											<input type="checkbox" disabled <logic:equal name="batchTempForm" property="containCB" value="1">checked</logic:equal>> �̱�<br/>
											<input type="checkbox" disabled <logic:equal name="batchTempForm" property="containOther" value="1">checked</logic:equal>> ���� &nbsp;&nbsp;&nbsp; 
											<input type="checkbox" disabled <logic:equal name="batchTempForm" property="containServiceFee" value="1">checked</logic:equal>> �����
										</span>
									</div>
								</li>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>�ͻ�����</label><span><bean:write name="batchTempForm" property="countClientId"/></span></li>
		                		<li><label>��������</label><span><bean:write name="batchTempForm" property="countOrderId"/></span></li>
		                		<li><label>��Ա�˴�</label><span><bean:write name="batchTempForm" property="countContractId"/></span></li>
		                		<li><label>��Ŀ����</label><span><bean:write name="batchTempForm" property="countItemId"/></span></li>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="batchTempForm" property="description">
		                			<li><label>����</label><span><bean:write name="batchTempForm" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
							<ol class="auto">
		                		<li><label>��˾Ӫ��</label><span><bean:write name="batchTempForm" property="billAmountCompany"/></span></li>
		                		<li><label>��˾�ɱ�</label><span><bean:write name="batchTempForm" property="costAmountCompany"/></span></li>
		                		<li><label>��������</label><span><bean:write name="batchTempForm" property="billAmountPersonal"/></span></li>
		                		<li><label>����֧��</label><span><bean:write name="batchTempForm" property="costAmountPersonal"/></span></li>
		                	</ol>
						</div>
						<div id="tabContent2" class="kantab" >
							<ol class="auto">
								<li><label>�����·�</label><span><bean:write name="batchTempForm" property="monthly"/></span></li>
								<li><label>����ڼ�</label><span><bean:write name="batchTempForm" property="accountPeriod"/></span></li>
							</ol>
							<ol class="auto">
								<logic:notEmpty name="orderHeaderTempVO" property="decodeEntityId">
		                			<li><label>����ʵ��</label><span><bean:write name="orderHeaderTempVO" property="decodeEntityId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderTempVO" property="decodeBusinessTypeId">
		                			<li><label>ҵ������</label><span><bean:write name="orderHeaderTempVO" property="decodeBusinessTypeId"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderTempVO" property="clientId">
			                		<li><label>�ͻ�ID</label><span><bean:write name="orderHeaderTempVO" property="clientId"/></span></li>
			                		<li><label>�ͻ�����</label><span><bean:write name="orderHeaderTempVO" property="clientName"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderTempVO" property="orderId">
			                		<li><label>����ID</label><span><bean:write name="orderHeaderTempVO" property="orderId"/></span></li>
			                	</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderTempVO" property="taxName">
		                			<li><label>˰��</label><span><bean:write name="orderHeaderTempVO" property="taxName"/> <logic:notEmpty name="orderHeaderTempVO" property="taxRemark">(<bean:write name="orderHeaderTempVO" property="taxRemark"/>)</logic:notEmpty></span></li>
		                		</logic:notEmpty>
		                	</ol>
							<ol class="auto">
								<logic:notEmpty name="orderHeaderTempVO" property="description">
		                			<li><label>����</label><span><bean:write name="orderHeaderTempVO" property="description"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
								<logic:notEmpty name="orderHeaderTempVO" property="startDate">
		                			<li><label>��ʼ����</label><span><bean:write name="orderHeaderTempVO" property="startDate"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderTempVO" property="endDate">
		                			<li><label>��������</label><span><bean:write name="orderHeaderTempVO" property="endDate"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<logic:notEmpty name="orderHeaderTempVO" property="decodeBranch">
		                			<li><label>��������</label><span><bean:write name="orderHeaderTempVO" property="decodeBranch"/></span></li>
		                		</logic:notEmpty>
		                		<logic:notEmpty name="orderHeaderTempVO" property="decodeOwner">
		                			<li><label>������</label><span><bean:write name="orderHeaderTempVO" property="decodeOwner"/></span></li>
		                		</logic:notEmpty>
		                	</ol>
		                	<ol class="auto">
		                		<li><label>��Ա�˴�</label><span><bean:write name="orderHeaderTempVO" property="countContractId"/></span></li>
		                		<li><label>��Ŀ����</label><span><bean:write name="orderHeaderTempVO" property="countItemId"/></span></li>
		                	</ol>
							<ol class="auto">
		                		<li><label>��˾Ӫ��</label><span><bean:write name="orderHeaderTempVO" property="billAmountCompany"/></span></li>
		                		<li><label>��˾�ɱ�</label><span><bean:write name="orderHeaderTempVO" property="costAmountCompany"/></span></li>
		                		<li><label>��������</label><span><bean:write name="orderHeaderTempVO" property="billAmountPersonal"/></span></li>
		                		<li><label>����֧��</label><span><bean:write name="orderHeaderTempVO" property="costAmountPersonal"/></span></li>
							</ol>
						</div>
					</div>
               	</fieldset>
             </html:form>
			<!-- ��������Э���б��Tabҳ�� -->
			<div id="tableWrapper">
				<jsp:include page="table/listContractTempTable.jsp"></jsp:include>
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_settlement_Modules').addClass('current');			
		$('#menu_settlement_OrderEstimate').addClass('selected');

		// ȷ���¼�
		$('#btnConfirm').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���ύ����Э�飿")){
					$('.searchContractTemp_form').attr('action', 'settlementTempAction.do?proc=submit_estimation');
					submitForm('searchContractTemp_form');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ���˵ļ�¼��");
			}
		});
		
		// �˻��¼�
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻ط���Э�飿")){
					$('.searchContractTemp_form').attr('action', 'settlementTempAction.do?proc=rollback_estimation');
					submitForm('searchContractTemp_form');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�˻صļ�¼��");
			}
		});
		
		// �б����¼�
		$('#btnList').click(function(){
			if (agreest())
			link('settlementTempAction.do?proc=list_estimation');
		});
	})(jQuery);
	
	function resetForm(){
		$(".searchContractTemp_employeeId").val("");
		$(".searchContractTemp_employeeNameZH").val("");
		$(".searchContractTemp_employeeNameEN").val("");
		$(".searchContractTemp_employeeContractId").val("");
	}
</script>

