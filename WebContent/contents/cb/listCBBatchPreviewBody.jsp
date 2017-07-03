<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.cb.CBAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	PagedListHolder cbBatchHolder = (PagedListHolder) request.getAttribute("cbBatchHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="cbBatch-information">
		<div class="head">
			<label><bean:message bundle="cb" key="cb.preview.search.title" /></label>
		</div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="cbAction.do?proc=list_estimation" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="cbBatchHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="cbBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="cbBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="cbBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" id="pageFlag" name="pageFlag" value="<bean:write name="cbBatchForm" property="pageFlag" />" />
				<input type="hidden" id="statusFlag" name="statusFlag" value="<bean:write name="cbBatchForm" property="statusFlag" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="cb" key="cb.batch.id" /></label> 
							<html:text property="batchId" maxlength="10" styleClass="searchCBBatch_batchId" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity" /></label> 
							<html:select property="entityId" styleClass="searchCBBatch_entityId">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type" /></label> 
							<html:select property="businessTypeId" styleClass="searchCBBatch_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户</label> 
							<html:text property="clientId" maxlength="10" styleClass="searchCBBatch_clientId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
							</label>
							<logic:equal name="role" value="1">
								<html:text property="orderId" maxlength="10" styleClass="searchCBBatch_orderId" />
							</logic:equal>
							<logic:equal name="role" value="2">
								<html:select property="orderId" styleId="orderId" styleClass="managePaymentBatch_orderId">
									<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
								</html:select>
							</logic:equal>
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label>
							<html:text property="contractId" maxlength="10" styleClass="searchCBBatch_contractId" />
						</li>
						<li>
							<label><bean:message bundle="cb" key="cb.batch.month" /></label>
							<html:select property="monthly" styleClass="searchCBBatch_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- CBBatch-information -->
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
				<kan:auth right="new" action="HRO_CB_BATCH_PREVIEW">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.create" />" onclick="link('cbAction.do?proc=to_estimationNew');"/>
				</kan:auth>
				<kan:auth right="approve" action="HRO_CB_BATCH_PREVIEW">
					<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.approve" />" />
				</kan:auth>
				<kan:auth right="back" action="HRO_CB_BATCH_PREVIEW">
					 <input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
				<logic:equal name="isExportExcel" value="1">
					<kan:auth right="export" action="HRO_CB_BATCH_CONFIRM">
	            		<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="showPopup('<%=CBAction.javaObjectName %>');"><img src="images/appicons/excel_16.png" /></a>
	            	</kan:auth>
	            </logic:equal>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/cb/table/listCBBatchTablePreview.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper --><!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
			<!-- List Component -->
		</div>
	</div>
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/selectCBSolution.jsp"></jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 根据"statusFlag"设置顶部菜单选择样式
		$('#menu_cb_Modules').addClass('current');			
		$('#menu_cb_Process').addClass('selected');
		$('#menu_cb_PurchasePreview').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();

		<logic:equal name="cbBatchForm" property="statusFlag" value="preview">
			$('#subAction').val('approveObjects');
		</logic:equal>
		<logic:equal name="cbBatchForm" property="statusFlag" value="confirm">
			$('#subAction').val('confirmObjects');
		</logic:equal>
		<logic:equal name="cbBatchForm" property="statusFlag" value="submit">
			$('#subAction').val('submitObjects');
		</logic:equal>
		
		/**
		 *	按钮绑定事件
		 */
		// 批准事件
		$('#btnApprove').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.approve.selected.batch" />')){
					$('.list_form').attr('action', 'cbAction.do?proc=submit_estimation');
					submitForm('list_form', "approveObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});

		// 退回事件
		$('#btnRollback').click(function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.selected.batch" />')){
					$('.list_form').attr('action', 'cbAction.do?proc=rollback');
					submitForm('list_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
	
		<%
		final Boolean messageInfo = (Boolean) request.getAttribute("messageInfo");
			if(messageInfo!=null&&messageInfo){
		%>
			$('#messageWrapper').html('<div class="message success fadable">该批次下没有数据！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
	 			messageWrapperFada();
		<%	}%>
	})(jQuery);
	
	// Button点击校验事件
	function chkSelect(){
		var selectedIds = $('#selectedIds').val();
		if(selectedIds != ''){
			return true;
		}else{
			alert("请选中项后再提交！");
			return false;
		};
	};
	
	function resetForm() {
		$('.searchCBBatch_batchId').val('');
		$('.searchCBBatch_clientId').val('');
		<logic:equal name="role" value="1">
			$('.searchCBBatch_orderId').val('');
		</logic:equal>
		<logic:equal name="role" value="2">
			$('.searchCBBatch_orderId').val('0');
		</logic:equal>
		$('.searchCBBatch_entityId').val('0');
		$('.searchCBBatch_contractId').val('');
		$('.searchCBBatch_businessTypeId').val('0');
		$('.searchCBBatch_monthly').val('0');
	};
</script>
