<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.sb.SBAdjustmentImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label>�籣������ - �籣�������걨��������</label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchSBAdjustmentImportBatch_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="sbAdjustmentImportBatchAction.do?proc=list_object" method="post" styleClass="searchSBAdjustmentImportBatch_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbAdjustmentImportBatchHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbAdjustmentImportBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbAdjustmentImportBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbAdjustmentImportBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>ID</label>
							<html:text property="employeeId" maxlength="11" styleClass="searchSBAdjustmentImportBatch_employeeId" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���������ģ�</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchSBAdjustmentImportBatch_employeeNameZH" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>������Ӣ�ģ�</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchSBAdjustmentImportBatch_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="1">
							<li>
								<label>�ͻ�ID</label>
								<html:text property="clientId" maxlength="11" styleClass="searchSBAdjustmentImportBatch_clientId" /> 
							</li> 
							<li>
								<label>�ͻ����ƣ����ģ�</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchSBAdjustmentImportBatch_clientNameZH" /> 
							</li>
							<li>
								<label>�ͻ����ƣ�Ӣ�ģ�</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchSBAdjustmentImportBatch_clientNameEN" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="1">
							<li>
								<label>����ID</label>
								<html:text property="orderId" maxlength="10" styleClass="searchSBAdjustmentImportBatch_orderId" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="2">
							<li>
								<label>�������ID</label>
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchSBAdjustmentImportBatch_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchSBAdjustmentImportBatch_orderId" /> 
			   					</logic:empty>
		   					</li>
		   				</logic:equal>
		   				<logic:equal name="role" value="1">
							<li>
								<label>������ϢID</label>
								<html:text property="contractId" maxlength="11" styleClass="searchSBAdjustmentImportBatch_contractId" /> 
							</li> 
						</logic:equal>
						<li>
							<label>�����·�</label> 
							<html:select property="monthly" styleClass="searchSBAdjustmentImportBatch_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>�籣�����𷽰�</label> 
							<html:select property="sbSolutionId" styleClass="searchSBAdjustmentImportBatch_sbSolutionId">
								<html:optionsCollection property="sbSolutions" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSBAdjustmentImportBatch_status">
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
				<kan:auth right="submit" action="<%=SBAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="back" action="<%=SBAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="function" name="btnBack" id="btnBack" value="�˻�">
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				<kan:auth right="import" action="<%=SBAdjustmentImportBatchAction.accessAction%>">
				<img style='float: right' src='images/import.png' onclick='popupExcelImport();' title='�籣�������������' />
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP ����table��Ӧ��JSP�ļ� -->  
				<jsp:include page="/contents/sb/adjustment/batch/table/listSBAdjustmentImportBatchTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/popup/importExcel.jsp" flush = "true">
		<jsp:param value="true" name="needRemark"/>
		<jsp:param value="HRO_SB_ADJUSTMENT_HEADER" name="accessAction"/>
		<jsp:param  name="closeRefesh" value="true"/>
</jsp:include>

<script type="text/javascript">
	(function($) {
		$('#menu_sb_Modules').addClass('current');
		$('#menu_sb_DeclarationAdjustment_Import').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		//�б�˫��
		$("tbody tr").dblclick(function(){ 
			 kanlist_dbclick($(this),"searchSBAdjustmentImportBatch_form");
		}); 
		
		// �����˻��¼�
		$('#btnBack').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻�ѡ�е����Σ�")){
					$('.searchSBAdjustmentImportBatch_form').attr('action', 'sbAdjustmentImportBatchAction.do?proc=back_batch');
					submitForm('searchSBAdjustmentImportBatch_form', "submitObjects", null, null, null, 'tableWrapper');
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
					$('.searchSBAdjustmentImportBatch_form').attr('action', 'sbAdjustmentImportBatchAction.do?proc=submit_batch');
					submitForm('searchSBAdjustmentImportBatch_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�ύ�����Ρ�");
			}
		});
		
	})(jQuery);
	
	function resetForm() {
	    $('.searchSBAdjustmentImportBatch_employeeId').val('');
	    $('.searchSBAdjustmentImportBatch_employeeNameZH').val('');
	    $('.searchSBAdjustmentImportBatch_employeeNameEN').val('');
	    $('.searchSBAdjustmentImportBatch_clientId').val('');
	    $('.searchSBAdjustmentImportBatch_clientNameZH').val('');
	    $('.searchSBAdjustmentImportBatch_clientNameEN').val('');
	    $('.searchSBAdjustmentImportBatch_orderId').val('');
	    $('.searchSBAdjustmentImportBatch_contractId').val('');
	    $('.searchSBAdjustmentImportBatch_monthly').val('0');
	    $('.searchSBAdjustmentImportBatch_status').val('0');
	}; 
</script>
