<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementAdjustmentImportBatchAction"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.SettlementAdjustmentImportHeaderAction"%>
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
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchSettlementAdjustmentImportBatch_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="settlementAdjustmentImportBatchAction.do?proc=list_object" method="post" styleClass="searchSettlementAdjustmentImportBatch_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="settlementAdjustmentImportBatchHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="settlementAdjustmentImportBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="settlementAdjustmentImportBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="settlementAdjustmentImportBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>ID</label>
							<html:text property="employeeId" maxlength="11" styleClass="searchSettlementAdjustmentImportBatch_employeeId" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���������ģ�</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchSettlementAdjustmentImportBatch_employeeNameZH" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>������Ӣ�ģ�</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchSettlementAdjustmentImportBatch_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="1">
							<li>
								<label>�ͻ�ID</label>
								<html:text property="clientId" maxlength="11" styleClass="searchSettlementAdjustmentImportBatch_clientId" /> 
							</li> 
							<li>
								<label>�ͻ����ƣ����ģ�</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchSettlementAdjustmentImportBatch_clientNameZH" /> 
							</li>
							<li>
								<label>�ͻ����ƣ�Ӣ�ģ�</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchSettlementAdjustmentImportBatch_clientNameEN" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="1">
							<li>
								<label>����ID</label>
								<html:text property="orderId" maxlength="10" styleClass="searchSettlementAdjustmentImportBatch_orderId" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="2">
							<li>
								<label>�������ID</label>
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchSettlementAdjustmentImportBatch_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchSettlementAdjustmentImportBatch_orderId" /> 
			   					</logic:empty>
		   					</li>
		   				</logic:equal>
		   				<logic:equal name="role" value="1">
							<li>
								<label>������ϢID</label>
								<html:text property="contractId" maxlength="11" styleClass="searchSettlementAdjustmentImportBatch_contractId" /> 
							</li> 
						</logic:equal>
						<li>
							<label>�����·�</label> 
							<html:select property="monthly" styleClass="searchSettlementAdjustmentImportBatch_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSettlementAdjustmentImportBatch_status">
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
				<kan:auth right="submit" action="<%=SettlementAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="back" action="<%=SettlementAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="function" name="btnBack" id="btnBack" value="�˻�">
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				   <kan:auth right="import" action="<%=SettlementAdjustmentImportBatchAction.accessAction%>">
	            	<img style="float:right" src="images/import.png" onclick="popupExcelImport();" title="�����������">
	            </kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP ����table��Ӧ��JSP�ļ� -->  
				<jsp:include page="/contents/settlement/adjustment/batch/table/listSettlementAdjustmentImportBatchTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>
<div id="popupWrapper">
	<jsp:include page="/popup/importExcel.jsp" >
		<jsp:param value="<%=SettlementAdjustmentImportHeaderAction.accessAction%>" name="accessAction"/>
		<jsp:param value="true" name="closeRefesh"/>
	</jsp:include>
</div>
<script type="text/javascript">
	(function($) {
		$('#menu_settlement_Modules').addClass('current');
		$('#menu_settlement_adjustment_import').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		//�б�˫��
		$("tbody tr").dblclick(function(){ 
			 kanlist_dbclick($(this),"searchSettlementAdjustmentImportBatch_form");
		}); 
		
		// �����˻��¼�
		$('#btnBack').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻�ѡ�е����Σ�")){
					$('.searchSettlementAdjustmentImportBatch_form').attr('action', 'settlementAdjustmentImportBatchAction.do?proc=back_batch');
					submitForm('searchSettlementAdjustmentImportBatch_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�˻ص����Ρ�");
			}
		});
		
		// ���θ����¼�
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���ύѡ�е����Σ�")){
					$('.searchSettlementAdjustmentImportBatch_form').attr('action', 'settlementAdjustmentImportBatchAction.do?proc=submit_batch');
					submitForm('searchSettlementAdjustmentImportBatch_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�ύ�����Ρ�");
			}
		});
		
	})(jQuery);
	
	function resetForm() {
	    $('.searchSettlementAdjustmentImportBatch_employeeId').val('');
	    $('.searchSettlementAdjustmentImportBatch_employeeNameZH').val('');
	    $('.searchSettlementAdjustmentImportBatch_employeeNameEN').val('');
	    $('.searchSettlementAdjustmentImportBatch_clientId').val('');
	    $('.searchSettlementAdjustmentImportBatch_clientNameZH').val('');
	    $('.searchSettlementAdjustmentImportBatch_clientNameEN').val('');
	    $('.searchSettlementAdjustmentImportBatch_orderId').val('');
	    $('.searchSettlementAdjustmentImportBatch_contractId').val('');
	    $('.searchSettlementAdjustmentImportBatch_monthly').val('0');
	    $('.searchSettlementAdjustmentImportBatch_status').val('0');
	}; 
</script>
