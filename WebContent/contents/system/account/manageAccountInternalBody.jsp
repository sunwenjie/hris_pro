<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.system.ModuleRender"%>
<%@ page import="com.kan.base.domain.system.AccountVO"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.system.AccountAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final AccountVO accountVO = (AccountVO) request.getAttribute("accountForm");
	String accountId = null;
	
	if(accountVO != null && accountVO.getAccountId() != null){
	   accountId = accountVO.getAccountId();
	}
%>

<div id="content">
	<div id="systemAccount" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">账户信息查询</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=AccountAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" /> 
				</kan:auth>
			</div>
			<html:form action="accountAction.do?proc=modify_object_internal" styleClass="account_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="accountId" name="accountId" value="<bean:write name="accountForm" property="encodedId" />" />
				<input type="hidden" id="subAction" name="accountId" class="subAction" value="<bean:write name="accountForm" property="subAction" />" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em>Required field</label></li>
					</ol>
					<ol class="auto">
						<li>
							<label>账户名 （中文）<em> *</em></label> 
							<html:text property="nameCN" maxlength="25" styleClass="account_namecn" /> 
						</li>
						<li>
							<label>账户名 （英文）</label> 
							<html:text property="nameEN" maxlength="25" styleClass="account_nameen" />  
						</li>
						<li>
							<label>公司名称<em> *</em></label> 
							<html:text property="entityName" maxlength="100" styleClass="account_entityName" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>联系人</label> 
							<html:text property="linkman" maxlength="50" styleClass="account_linkman" />
						</li>
						<li>
							<label>性别</label> 
							<html:select property="salutation" styleClass="account_salutation">
								<html:optionsCollection property="salutations" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>职务</label>
							<html:text property="title" maxlength="100" styleClass="account_title"/>
						</li>
						<li>
							<label>部门</label> 
							<html:text property="department" maxlength="50" styleClass="account_department" />
						</li>
						<li>
							<label>工作电话</label> 
							<html:text property="bizPhone" maxlength="25" styleClass="account_bizPhone" />
						</li>
						<li>
							<label>私人电话</label> 
							<html:text property="personalPhone" maxlength="25" styleClass="account_personalPhone" />
						</li>
						<li>
							<label>工作手机</label> 
							<html:text property="bizMobile" maxlength="25" styleClass="account_bizMobile" />
						</li>
						<li>
							<label>私人手机</label> 
							<html:text property="personalMobile" maxlength="25" styleClass="account_personalMobile" />
						</li>
						<li>
							<label>其他电话</label> 
							<html:text property="otherPhone" maxlength="25" styleClass="account_otherPhone" />
						</li>
						<li>
							<label>传真</label> 
							<html:text property="fax" maxlength="25" styleClass="account_fax" />
						</li>
						<li>
							<label>工作邮箱</label> 
							<html:text property="bizEmail" maxlength="100" styleClass="account_bizEmail" />
						</li>
						<li>
							<label>私人邮箱</label> 
							<html:text property="personalEmail" maxlength="100" styleClass="account_personalEmail" />
						</li>
						<li>
							<label>网址</label> 
							<html:text property="website" maxlength="100" styleClass="account_website" />
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label>所在城市</label> 
							<html:hidden property="cityIdTemp" styleClass="account_cityIdTemp"/>
							<html:select property="provinceId" styleClass="account_provinceId">
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>地址</label> 
							<html:text property="address" maxlength="100" styleClass="account_address" />
						</li>
						<li>
							<label>邮编</label> 
							<html:text property="postcode" maxlength="25" styleClass="account_postcode" />
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label>绑定IP地址  <a title="使用“,”分割，网段用“x”代替"><img src="images/tips.png" /></a></label> 
							<html:text property="bindIP" maxlength="500" styleClass="account_bindIP" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="account_description" />
						</li>
						<li>
							<label>备注</label> 
							<html:textarea property="comment" styleClass="account_comment" />
						</li>
					</ol>
				</fieldset>
				
				<div id="extendWrapper">
					<!-- Include extend jsp 包含extend对应的jsp文件 -->  
					<jsp:include page="/contents/system/account/extend/manageAccountInternalExtend.jsp" flush="true"/> 
				</div>
				
			</html:form>
			
		</div>
	</div>
</div>


<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_security_Modules').addClass('current');
		$('#menu_security_BaseInformation').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('account_form');
				// 部分信息不允许编辑
        		$('.account_namecn').attr('disabled', 'disabled');
        		$('.account_nameen').attr('disabled', 'disabled');
        		$('.account_entityName').attr('disabled', 'disabled');
        		$('.account_comment').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				//	更换Page Title
        		$('#pageTitle').html('账户信息编辑');
				
        		// 上传链接生效
				$('#addPhoto').attr("disabled", false);
				$('#addPhoto').removeClass("disabled");
			
	   			$('img.deleteImg').each(function(i){
	   				$(this).show();
	   			});
	   			
	   			// 附件提交按钮事件
	   			var uploadObject = _createUploadObject('addPhoto', 'image', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_ACCOUNT %>/<%= BaseAction.getAccountId(request, response) %>/');
	   			
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("account_bizPhone", false, "phone", 0, 0);
    			flag = flag + validate("account_personalPhone", false, "phone", 0, 0);
    			flag = flag + validate("account_otherPhone", false, "phone", 0, 0);
    			flag = flag + validate("account_fax", false, "phone", 0, 0);
    			flag = flag + validate("account_bizMobile", false, "mobile", 0, 0);
    			flag = flag + validate("account_personalMobile", false, "mobile", 0, 0);
    			flag = flag + validate("account_bizEmail", false, "email", 0, 0);
    			flag = flag + validate("account_personalEmail", false, "email", 0, 0);
    			flag = flag + validate("account_website", false, "website", 0, 0);
    			flag = flag + validate("account_postcode", false, "numeric", 0, 0);
    			flag = flag + validate("account_bindIP", false, "ip", 0, 0);
    			flag = flag + validate("account_description", false, "common", 2500, 0);
    			
    			if(flag == 0){
    				submit('account_form');
    			}
        	}
		});
		
		// 绑定省Change事件
		$('.account_provinceId').change( function () { 
			provinceChange('account_provinceId', 'modifyObject', 0, '');
		});
		
		// 查看模式
		if($('.subAction').val() == 'viewObject'){
			// 触发省的Change事件,并绑定当前City
			if($('.account_provinceId').val() != '' && $('.account_provinceId').val() != "0"){
				provinceChange('account_provinceId', 'viewObject', $('.account_cityIdTemp').val(), '');
			}
			// 将Form设为Disable
			disableForm('account_form');
			
			$('#addPhoto').attr("disabled", true);
			$('#addPhoto').addClass("disabled");
		}
		
		$('#btnCancel').click( function () {
			link('accountAction.do?proc=to_objectModify_internal');
		});
	})(jQuery);
</script>
