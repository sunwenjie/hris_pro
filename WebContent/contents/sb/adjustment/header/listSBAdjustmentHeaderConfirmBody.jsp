<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page import="com.kan.hro.web.actions.biz.sb.SBAdjustmentHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label><bean:message bundle="sb" key="sb.adjustment.confirm.header.title" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchSBAdjustmentHeader_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="sbAdjustmentHeaderAction.do?proc=list_object_confirm" method="post" styleClass="searchSBAdjustmentHeader_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbAdjustmentHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbAdjustmentHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sbAdjustmentHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbAdjustmentHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label>
							<html:text property="employeeId" maxlength="11" styleClass="searchSBAdjustmentHeader_employeeId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchSBAdjustmentHeader_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchSBAdjustmentHeader_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="1">
							<li>
								<label>客户ID</label>
								<html:text property="clientId" maxlength="11" styleClass="searchSBAdjustmentHeader_clientId" /> 
							</li> 
							<li>
								<label>客户名称（中文）</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchSBAdjustmentHeader_clientNameZH" /> 
							</li>
							<li>
								<label>客户名称（英文）</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchSBAdjustmentHeader_clientNameEN" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="1">
							<li>
								<label><bean:message bundle="public" key="public.order1.id" /></label>
								<html:text property="orderId" maxlength="10" styleClass="searchSBAdjustmentHeader_orderId" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="2">
							<li>
								<label><bean:message bundle="public" key="public.order2.id" /></label>
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchSBAdjustmentHeader_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchSBAdjustmentHeader_orderId" /> 
			   					</logic:empty>
		   					</li>
		   				</logic:equal>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label>
							<html:text property="contractId" maxlength="11" styleClass="searchSBAdjustmentHeader_contractId" /> 
						</li> 
						<li>
							<label><bean:message bundle="sb" key="sb.adjustment.header.sb.solution" /></label> 
							<html:select property="sbSolutionId" styleClass="searchSBAdjustmentHeader_sbSolutionId">
								<html:optionsCollection property="sbSolutions" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="sb" key="sb.adjustment.header.monthly" /></label> 
							<html:select property="monthly" styleClass="searchSBAdjustmentHeader_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
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
			 	<kan:auth right="approve" action="HRO_SB_ADJUSTMENT_HEADER_CONFIRM">
	            	<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.approve" />" />
	            </kan:auth>
				<kan:auth right="back" action="HRO_SB_ADJUSTMENT_HEADER_CONFIRM">
	            	<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" />
	            </kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/sb/adjustment/header/table/listSBAdjustmentHeaderConfirmTable.jsp" flush="true"/> 
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
		// 初始化菜单样式
		$('#menu_sb_Modules').addClass('current');
		$('#menu_sb_Process').addClass('selected');
		$('#menu_sb_AdjustmentConfirm').addClass('selected');
		
		kanList_init();
		kanCheckbox_init();

		// 批准事件
		$('#btnApprove').click(function(){
			if(checkSelect()){
				if(confirm('<bean:message bundle="public" key="popup.confirm.approve.records" />')){
					$('.searchSBAdjustmentHeader_form').attr('action', 'sbAdjustmentHeaderAction.do?proc=approve_object');
					$('.searchSBAdjustmentHeader_form').submit();
				}
			};
		});
		
		// 退回事件
		$('#btnRollback').click(function(){
			if(checkSelect()){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
					$('.searchSBAdjustmentHeader_form').attr('action', 'sbAdjustmentHeaderAction.do?proc=rollback_object');
					$('.searchSBAdjustmentHeader_form').submit();
				}
			}
		});
	})(jQuery);
	
	// Button点击校验事件
	function checkSelect(){
		var selectedIds = $('#selectedIds').val();
		
		if(selectedIds != ''){
			return true;
		}else{
			alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			return false;
		}
	};
	
	function resetForm() {
	    $('.searchSBAdjustmentHeader_employeeId').val('');
	    $('.searchSBAdjustmentHeader_employeeNameZH').val('');
	    $('.searchSBAdjustmentHeader_employeeNameEN').val('');
	    $('.searchSBAdjustmentHeader_clientId').val('');
	    $('.searchSBAdjustmentHeader_clientNameZH').val('');
	    $('.searchSBAdjustmentHeader_clientNameEN').val('');
	    $('.searchSBAdjustmentHeader_orderId').val('');
	    $('.searchSBAdjustmentHeader_contractId').val('');
	    $('.searchSBAdjustmentHeader_monthly').val('0');
	    $('.searchSBAdjustmentHeader_sbSolutionId').val('0');
	};
</script>
