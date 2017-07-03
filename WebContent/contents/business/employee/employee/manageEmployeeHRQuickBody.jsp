<%@page import="com.kan.hro.domain.biz.employee.EmployeeVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final EmployeeVO employeeVO = ( EmployeeVO ) request.getAttribute( "employeeForm" );
%>

<div id="content">
	<div id="entity" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="business" key="business.employee.quick.create" />
			</label>
			<logic:notEmpty name="employeeForm" property="employeeId" >
				<label class="recordId"> &nbsp; (ID: <bean:write name="employeeForm" property="employeeId" />)</label>
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
				<logic:empty name="employeeForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="if(agreest()) link('employeeAction.do?proc=list_object');" /> 
			</div>
			<html:form action="employeeAction.do?proc=quick_add_object" styleClass="manageEmployee_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="id" id="employeeId" value='<bean:write name="employeeForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="employeeForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:notEqual>
								<em> *</em>
							</label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageEmployee_nameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name.en" /></logic:notEqual>
							</label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageEmployee_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.salutation" /></label> 
							<html:select property="salutation" styleClass="manageEmployee_salutation">
								<html:optionsCollection property="salutations" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
								<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.no" /></logic:notEqual>
							</label> 
							<html:text property="employeeNo" maxlength="100" styleClass="manageEmployee_employeeNo" /> 
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.email1" /><em> *</em></label> 
							<html:text property="email1" maxlength="100" styleClass="manageEmployee_email1" /> 
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.email2" /></label> 
							<html:text property="email2" maxlength="100" styleClass="manageEmployee_email2" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageEmployee_status" disabled="true" >
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.user.name" /><em> *</em></label>
							<input type="text" name="username" class="manageEmployee_username" id="manageEmployee_username" />
							<input type="hidden" id="usernameBackup" name="usernameBackup" value="" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.position" /><em> *</em></label>
							<input type="hidden" name="positionIdArray" id="tempPositionIdArray" value="" />
							<%=KANUtil.getSelectHTML( employeeVO.getBranchs(), "branchId", "small", null, "loadPositions();", null)%>
							<select class="small manageEmployee_positionId" id="positionId">
								<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
							</select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($){
		// 设置菜单选择样式
		$('#menu_employee_Modules').addClass('current');	
		$('#menu_employee_Employee').addClass('selected');
		
		// Edit button click event
		$('#btnEdit').click( function(){
			if( getSubAction() == 'createObject' ){
				if( validate_form() == 0 ) {
					enableForm('manageEmployee_form');
					if( $('#positionId').val()!= '' ){
						$('#tempPositionIdArray').val($('#positionId').val()+"_1_null_null");
					}
					submit('manageEmployee_form');
				}
			}
		});
		
		// 验证用户名是否重复 - 焦点失去事件
		$('.manageEmployee_username').blur( function () { 
			usernameExistError();
		});
		
		// 验证用户名是否重复 - 键盘敲击事件
		$('.manageEmployee_username').keyup( function () { 
			usernameExistError();
		});
	})(jQuery);
	
	// Validate form data
	function validate_form(){
		var flag = 0;
		flag = flag + validate('manageEmployee_nameZH', true, 'common', 100, 0, 0, 0 );
		flag = flag + validate('manageEmployee_username', true, 'common', 100, 0, 0, 0 );
		if( $('.manageEmployee_username').val() != '' ) {
			usernameExistError();
			flag = flag + validate('manageEmployee_email1', true, 'email', 100, 0, 0, 0 );
			flag = flag + validate('manageEmployee_positionId', true, 'select', 0, 0, 0, 0 );
		}
		validate_email_ajax();
		if( $('form input').hasClass('error')){
			flag++;
		}
		return flag;
	};
	
	// Username exist 
	function usernameExistError() {
		// 清除页面出错样式
		cleanError( 'manageEmployee_username' );
		var username = $('.manageEmployee_username').val();
		var errorMsg = "<bean:message bundle='public' key='error.username.already.exist' />";
		errorMsg = errorMsg.replace('&#8226;','').replace('XX',username);
		if( checkUsernameExist() == true ) {
			addError( 'manageEmployee_username', errorMsg );
		}
	};
	
	// Check username exist
	function checkUsernameExist(){
		var returnValue = false;
		var parameters = encodeURI(encodeURI('username=' + $('.manageEmployee_username').val()+'&employeeId='+ $("#employeeId").val()));
		$.ajax({
			url: 'employeeAction.do?proc=username_keyup_ajax&'+parameters+'&date=' + new Date(),
			type: 'POST',
			async: false,
			success : function(html){
				if(html == "exist"){
					returnValue = true;
				}
			}
		});
		return returnValue;
	};
	
	// 校验邮箱
	function validate_email_ajax(){
		cleanError('email1');
		var flag = 0;
		$.ajax({
			url : "employeeAction.do?proc=checkEmail_ajax&email1=" + $('.manageEmployee_email1').val() + "&employeeId=" + $('#employeeId').val(), 
			type: 'POST',
			async:false,
			success : function(data){
				if(data == "true"){
					flag = 1;
					$(".manageEmployee_email1").addClass("error");
					$(".manageEmployee_email1").after('<label class="error email1_error">&#8226; <bean:message bundle="public" key="error.email.registered" /></label>');
				}
			}
		});
		return flag;
	};
	
	// Load position list
	function loadPositions(){
		loadHtml('#positionId', 'positionAction.do?proc=load_html_options_ajax&branchId=' + $('#branchId').val(), null, null );
	};
	
	// Get SubAction
	function getSubAction(){
		return $('.subAction').val();
	};
</script>
