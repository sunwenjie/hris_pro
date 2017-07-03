<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.security.BranchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="systemBranch" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="security" key="security.branch" /> <bean:message bundle="public" key="oper.new" />
			</label>
			<logic:notEmpty name="branchForm" property="branchId" >
				<label class="recordId"> &nbsp; (ID: <bean:write name="branchForm" property="branchId" />)</label>
			</logic:notEmpty>
		</div>
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
				<logic:empty name="branchForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="branchForm" property="encodedId">
					<kan:auth right="modify" action="<%=BranchAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=BranchAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<logic:equal value="2" name="branchPrefer"><bean:message bundle="public" key="button.list" /></logic:equal><logic:notEqual value="2" name="branchPrefer"><bean:message bundle="security" key="security.branch.organization.chart" /></logic:notEqual>" /> 
				</kan:auth>
			</div>
			<html:form action="branchAction.do?proc=add_object" styleClass="branch_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="branchId" name="id" value="<bean:write name='branchForm' property='encodedId' />">
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="branchForm" property="subAction" />" />
				<input type="hidden" name="rootBranchId" id="rootBranchId" value="<%=request.getAttribute("rootBranchId") %>" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.branch.code" /><em> *</em></label> 
							<html:text property="branchCode" maxlength="20" styleClass="branch_branchCode" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.branch.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="branch_nameZH" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.branch.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="branch_nameEN" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity" /><em> *</em></label> 
							<html:select property="entityId" styleClass="branch_entityId">
								<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>Function Code</label> 
							<html:select property="businessTypeId" styleClass="branch_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>Cost Center</label> 
							<html:select property="settlementBranch" styleClass="branch_settlementBranch">
								<html:optionsCollection property="branchs" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>BU/Function</label> 
							<html:text property="parentBranchName" maxlength="20" styleClass="branch_parentBranchName" /> 
							<html:hidden property="parentBranchId" styleClass="branch_parentBranchId" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="branch_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="branch_description" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.modify.by" /></label> 
							<html:text property="decodeModifyBy" maxlength="100" disabled="disabled" styleClass="decodeModifyBy" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.modify.date" /></label> 
							<html:text property="decodeModifyDate" maxlength="100" disabled="disabled" styleClass="decodeModifyDate" /> 
						</li>
					</ol>
				</fieldset>
			</html:form>
			<div class="buttom">
					<p />
					<p />
			</div>
			<logic:present name="staffListHolder">
				<div  class="kantab">
					<div id="tableWrapper">
						<!-- Include table jsp 包含tabel对应的jsp文件  -->
						<jsp:include page="/contents/security/branch/extend/listBranchStaffTable.jsp" flush="false"/>
					</div>  
					<div class="buttom">
						<p />
					</div>
				</div>
			</logic:present>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_OrgManagement').addClass('selected');
		$('#menu_security_Branch').addClass('selected');
		
		$('.branch_settlementBranch').find('option').each( function(i){
			$(this).html($(this).text());
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('branch_form');
				// 修改人、修改时间不可编辑
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.branch_form').attr('action', 'branchAction.do?proc=modify_object');
        		// 更换Page Title
    			$('#pageTitle').html('<bean:message bundle="security" key="security.branch" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
        	}else{
        		var flag = 0;
        		
        		flag = flag + validate("branch_nameZH", true, "common", 100, 0);
        		flag = flag + validate("branch_branchCode", true, "common", 20, 0);
        		flag = flag + validate("branch_status", true, "select", 0, 0);
        		flag = flag + validate("branch_entityId", true, "select", 0, 0);
        		flag = flag + validate("branch_description", false, "common", 500, 0);
        		
        		if(flag == 0){
        			submit('branch_form');
        		}
        	}
		});
		
		// 查看模式
		if($('.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('branch_form');
			// 更改Subaction
    		$('.branch_form input.subAction').val('viewObject');
			// 更改按钮显示名
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="security" key="security.branch" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
		}

		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// 创建模式
		if($('.branch_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link("branchAction.do?proc=list_object&rootBranchId="+$("#rootBranchId").val());
		});
		
		// 绑定上级部门名称
		bindThinkingToParentBranchName();
	})(jQuery);
	
	function bindThinkingToParentBranchName(){
		// Use the common thinking
		kanThinking_column('branch_parentBranchName', 'branch_parentBranchId', 'branchAction.do?proc=list_object_json');
	};
</script>