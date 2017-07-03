<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementHeaderTempAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
	        <label id="pageTitle">�������� - ����</label>
	        <label class="recordId">&nbsp;(ID: <bean:write name="batchTempForm" property="batchId"/>)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchHeaderTemp_form', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="settlementHeaderTempAction.do?proc=list_object" method="post" styleClass="searchHeaderTemp_form">
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="headerTempListHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="headerTempListHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="headerTempListHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="headerTempListHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<input type="hidden" name="searchHeader" id="searchHeader" class="searchHeader" value="true"/>
					<input type="hidden" name="batchId" id="batchId" value="<bean:write name="batchTempForm" property="encodedId" />" />	
					<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="batchTempForm" property="pageFlag" />" />
				<fieldset>
					<ol class="auto">
							<li>
								<label>�ͻ�ID</label>
								<html:text property="clientId" maxlength="10" styleClass="searchHeaderTemp_clientId" /> 
							</li>
							<li>
								<label>�ͻ����ƣ����ģ�</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchHeaderTemp_clientNameZH" /> 
							</li>
							<li>
								<label>�ͻ����ƣ�Ӣ�ģ�</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchHeaderTemp_clientNameEN" /> 
							</li>
							<li>
								<label>����ID</label>
								<html:text property="orderId" maxlength="100" styleClass="searchHeaderTemp_orderId" /> 
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
	        <html:form action="settlementHeaderTempAction.do?proc=list_object" styleClass="listHeader_form">
		        <div class="top">
		        	<kan:auth right="posting" action="<%=SettlementHeaderTempAction.accessAction%>">
						<input type="button" class="function" name="btnConfirm" id="btnConfirm" value="����" />
					</kan:auth>
					<kan:auth right="back" action="<%=SettlementHeaderTempAction.accessAction%>">
						<input type="button" class="delete" name="btnRollback" id="btnRollback" value="�˻�" />
					</kan:auth>
					<kan:auth right="list" action="<%=SettlementHeaderTempAction.accessAction%>">
						<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.list" />" />
					</kan:auth>
					<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
		        </div>
	            <fieldset>
					<div class="tabMenu"> 
						<ul> 
							<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">���� (ID: <span><bean:write name="batchTempForm" property="batchId" /></span>)</li> 
						</ul> 
					</div> 
		            <div class="tabContent"> 
						<div id="tabContent1" class="kantab" >
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
					</div>
               	</fieldset>
            </html:form>
	        <!-- �����籣�����𷽰��б���Ϣ -->
			<div id="tableWrapper">
				<jsp:include page="table/listHeaderTempTable.jsp"></jsp:include>          
			</div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_settlement_Modules').addClass('current');			
		$('#menu_settlement_OrderEstimate').addClass('selected');

		// ȷ�Ϲ���
		$('#btnConfirm').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���ύ������")){
					$('.searchHeaderTemp_form').attr('action', 'settlementTempAction.do?proc=submit_estimation');
					submitForm('searchHeaderTemp_form');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ���˵ļ�¼��");
			}
		});
		
		// �˻��¼�
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻ض�����")){
					$('.searchHeaderTemp_form').attr('action', 'settlementTempAction.do?proc=rollback_estimation');
					submitForm('searchHeaderTemp_form');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�˻صļ�¼��");
			}
		});
		
		// ȡ���¼�
		$('#btnCancel').click(function(){
			if (agreest())
			link('settlementTempAction.do?proc=list_estimation');
		});
	})(jQuery);
	
	function resetForm(){
		$(".searchHeaderTemp_clientId").val("");
		$(".searchHeaderTemp_clientNameZH").val("");
		$(".searchHeaderTemp_clientNameEN").val("");
		$(".searchHeaderTemp_orderId").val("");
	}
</script>

