<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementAdjustmentImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label>�����������</label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchSettlementAdjustmentImportHeader_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="settlementAdjustmentImportHeaderAction.do?proc=list_object" method="post" styleClass="searchSettlementAdjustmentImportHeader_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="settlementAdjustmentImportHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="settlementAdjustmentImportHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="settlementAdjustmentImportHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="settlementAdjustmentImportHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value='<bean:write name="settlementAdjustmentImportBatchVO" property="encodedBatchId" />' />
				<fieldset>
					<ol class="auto">
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>ID</label>
							<html:text property="employeeId" maxlength="11" styleClass="searchSettlementAdjustmentImportHeader_employeeId" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���������ģ�</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchSettlementAdjustmentImportHeader_employeeNameZH" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>������Ӣ�ģ�</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchSettlementAdjustmentImportHeader_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="1">
							<li>
								<label>�ͻ�ID</label>
								<html:text property="clientId" maxlength="11" styleClass="searchSettlementAdjustmentImportHeader_clientId" /> 
							</li> 
							<li>
								<label>�ͻ����ƣ����ģ�</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchSettlementAdjustmentImportHeader_clientNameZH" /> 
							</li>
							<li>
								<label>�ͻ����ƣ�Ӣ�ģ�</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchSettlementAdjustmentImportHeader_clientNameEN" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="1">
							<li>
								<label>����ID</label>
								<html:text property="orderId" maxlength="10" styleClass="searchSettlementAdjustmentImportHeader_orderId" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="2">
							<li>
								<label>�������ID</label>
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchSettlementAdjustmentImportHeader_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchSettlementAdjustmentImportHeader_orderId" /> 
			   					</logic:empty>
		   					</li>
		   				</logic:equal>
		   				<logic:equal name="role" value="1">
							<li>
								<label>������ϢID</label>
								<html:text property="contractId" maxlength="11" styleClass="searchSettlementAdjustmentImportHeader_contractId" /> 
							</li> 
						</logic:equal>
						<li>
							<label>�����·�</label> 
							<html:select property="monthly" styleClass="searchSettlementAdjustmentImportHeader_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSettlementAdjustmentImportHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<!-- Information Search Result -->
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
				<kan:auth right="list" action="<%=SettlementAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="reset" id="btnList" name="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="link('settlementAdjustmentImportBatchAction.do?proc=list_object');" />
				</kan:auth>
				<kan:auth right="back" action="<%=SettlementAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="function" name="btnBack" id="btnBack" value="�˻�">
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			
			<div class="tabMenu"> 
				<ul> 
					<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">���� (ID: <bean:write name="settlementAdjustmentImportBatchVO" property="batchId" />)</li> 
				</ul> 
			</div>
			<div class="tabContent"> 
				<div id="tabContent1" class="kantab" >
					<form action="">
						<fieldset>
						  
				            <ol class="auto">
				            	<li><label>��ͬ������</label><span><bean:write name="settlementAdjustmentImportBatchVO" property="countContractId"/></span></li>
								<li><label>����������</label><span><bean:write name="settlementAdjustmentImportBatchVO" property="countHeaderId" /></span></li>
								<li><label>��˾Ӫ�գ�</label><span><bean:write name="settlementAdjustmentImportBatchVO" property="billAmountCompany" /></span></li>
								<li><label>��˾�ɱ���</label><span><bean:write name="settlementAdjustmentImportBatchVO" property="costAmountCompany" /></span></li>
								<li><label>�������룺</label><span><bean:write name="settlementAdjustmentImportBatchVO" property="billAmountPersonal" /></span></li>
								<li><label>����֧����</label><span><bean:write name="settlementAdjustmentImportBatchVO" property="costAmountPersonal" /></span></li>
				            </ol>
		                </fieldset>
	               </form> 
				</div>
			</div>
			
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP ����table��Ӧ��JSP�ļ� -->  
				<jsp:include page="/contents/settlement/adjustment/header/table/listSettlementAdjustmentImportHeaderTable.jsp" flush="true"/>
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		$('#menu_settlement_Modules').addClass('current');
		$('#menu_settlement_adjustment_import').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		//�б�˫��
		$("tbody tr").dblclick(function(){ 
			 kanlist_dbclick($(this),"searchSettlementAdjustmentImportHeader_form");
		});
		// �����˻��¼�
		$('#btnBack').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻�ѡ�е����Σ�")){
					$('.searchSettlementAdjustmentImportHeader_form').attr('action', 'settlementAdjustmentImportHeaderAction.do?proc=backUpRecord');
					submitForm('searchSettlementAdjustmentImportHeader_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�˻ص����Ρ�");
			}
		});
	})(jQuery);
	
	function resetForm() {
	    $('.searchSettlementAdjustmentImportHeader_employeeId').val('');
	    $('.searchSettlementAdjustmentImportHeader_employeeNameZH').val('');
	    $('.searchSettlementAdjustmentImportHeader_employeeNameEN').val('');
	    $('.searchSettlementAdjustmentImportHeader_clientId').val('');
	    $('.searchSettlementAdjustmentImportHeader_clientNameZH').val('');
	    $('.searchSettlementAdjustmentImportHeader_clientNameEN').val('');
	    $('.searchSettlementAdjustmentImportHeader_orderId').val('');
	    $('.searchSettlementAdjustmentImportHeader_contractId').val('');
	    $('.searchSettlementAdjustmentImportHeader_monthly').val('0');
	    $('.searchSettlementAdjustmentImportHeader_status').val('0');
	}; 
</script>
