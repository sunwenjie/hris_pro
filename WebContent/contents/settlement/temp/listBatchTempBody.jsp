<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementTempAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder batchTempHolder = (PagedListHolder) request.getAttribute("batchTempHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="batchTemp-information">
		<div class="head"><label>��������</label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="settlementTempAction.do?proc=list_estimation" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="batchTempHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="batchTempHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="batchTempHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="batchTempHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>����ID</label> 
							<html:text property="batchId" maxlength="12" styleClass="searchBatchTemp_batchId" /> 
						</li>
						<li>
							<label>����ʵ��</label> 
							<html:select property="entityId" styleClass="searchBatchTemp_entityId">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>ҵ������</label> 
							<html:select property="businessTypeId" styleClass="searchBatchTemp_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>�ͻ�ID</label> 
							<html:text property="clientId" maxlength="10" styleClass="searchBatchTemp_clientId" /> 
						</li>
						<li>
							<label>�������</label> 
							<html:text property="clientNumber" maxlength="10" styleClass="searchBatchTemp_clientNumber" /> 
						</li>
						<li>
							<label>�ͻ����ƣ����ģ�</label> 
							<html:text property="clientNameZH" maxlength="10" styleClass="searchBatchTemp_clientNameZH" /> 
						</li>
						<li>
							<label>�ͻ����ƣ�Ӣ�ģ�</label> 
							<html:text property="clientNameEN" maxlength="10" styleClass="searchBatchTemp_clientNameEN" /> 
						</li>
						<li>
							<label>����ID</label>
							<html:text property="orderId" maxlength="10" styleClass="searchBatchTemp_orderId" />
						</li>
						<li>
							<label>�˵��·�</label>
							<html:select property="monthly" styleClass="searchBatchTemp_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>�˵�����</label>
							<div style="width: 220px;">
								<span>
									<html:checkbox property="containSalary" value="1" styleClass="searchBatchTemp_containSalary"> ����</html:checkbox> &nbsp;&nbsp;&nbsp; 
									<html:checkbox property="containSB" value="1" styleClass="searchBatchTemp_containSB"> �籣������</html:checkbox> &nbsp;&nbsp;&nbsp; 
									<html:checkbox property="containCB" value="1" styleClass="searchBatchTemp_containCB"> �̱�</html:checkbox><br/>
									<html:checkbox property="containOther" value="1" styleClass="searchBatchTemp_containOther"> ����</html:checkbox> &nbsp;&nbsp;&nbsp; 
									<html:checkbox property="containServiceFee" value="1" styleClass="searchBatchTemp_containServiceFee"> �����</html:checkbox>
								</span>
							</div>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- BatchTemp-information -->
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
			<div class="top">
				<kan:auth right="new" action="<%=SettlementTempAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="����" onclick="link('settlementTempAction.do?proc=to_estimationNew');" />
				</kan:auth>
				<kan:auth right="posting" action="<%=SettlementTempAction.accessAction%>">
	            	<input type="button" class="function" name="btnConfirm" id="btnConfirm" value="����" />
	            </kan:auth>
	            <kan:auth right="back" action="<%=SettlementTempAction.accessAction%>">
	            	<input type="button" class="delete" name="btnConfirm" id="btnRollback" value="�˻�" />
	            </kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
				<jsp:include page="table/listBatchTempTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// ���ö����˵�ѡ����ʽ
		$('#menu_settlement_Modules').addClass('current');			
		$('#menu_settlement_OrderEstimate').addClass('selected');
		$('#searchDiv').hide();

		// �ύ��ť�¼�
		$('#btnConfirm').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���ύ���Σ�")){
					$('.list_form').attr('action', 'settlementTempAction.do?proc=submit_estimation');
					submitForm('list_form', "confirmObject", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ���˵ļ�¼��");
			}
		});
		
		// �˻ذ�ť�¼�
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻����Σ�")){
					$('.list_form').attr('action', 'settlementTempAction.do?proc=rollback_estimation');
					submitForm('list_form', "rollbackObject", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�˻صļ�¼��");
			}
		});
		
		<%
		final Boolean messageInfo = (Boolean) request.getAttribute("messageInfo");
			if(messageInfo!=null&&messageInfo){
		%>
			$('#messageWrapper').html('<div class="message success fadable">��������û�����ݣ�<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
	 			messageWrapperFada();
		<%	}%>
	})(jQuery);
	
	function resetForm() {
		$('.searchBatchTemp_batchId').val('');
		$('.searchBatchTemp_entityId').val('0');
		$('.searchBatchTemp_businessTypeId').val('0');
		$('.searchBatchTemp_clientId').val('');
		$('.searchBatchTemp_orderId').val('');
		$('.searchBatchTemp_clientNameZH').val('');
		$('.searchBatchTemp_clientNameEN').val('');
		$('.searchBatchTemp_monthly').val('0');
		$('.searchBatchTemp_containSalary').removeAttr('checked');
		$('.searchBatchTemp_containSB').removeAttr('checked');
		$('.searchBatchTemp_containCB').removeAttr('checked');
		$('.searchBatchTemp_containOther').removeAttr('checked');
		$('.searchBatchTemp_containServiceFee').removeAttr('checked');
	};
</script>
