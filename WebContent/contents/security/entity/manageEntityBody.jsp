<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.security.EntityAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="entity" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="security" key="security.entity" />
			</label>
			<logic:notEmpty name="entityForm" property="entityId" >
				<label class="recordId"> &nbsp; (ID: <bean:write name="entityForm" property="entityId" />)</label>
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
				<logic:empty name="entityForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="entityForm" property="encodedId">
					<kan:auth right="modify" action="<%=EntityAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=EntityAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="entityAction.do?proc=add_object" styleClass="manageEntity_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="id" id="entityId" value='<bean:write name="entityForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="entityForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.entity.in.short" /><em> *</em></label> 
							<html:text property="title" maxlength="100" styleClass="manageEntity_title" /> 
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.entity.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="manageEntity_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="manageEntity_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity.office.location" /></label> 
							<html:select property="locationId" styleClass="manageEntity_locationId">
								<html:optionsCollection property="locations" value="mappingId" label="mappingValue" />
							</html:select>				
						</li>
						<%-- <li>
							<label><bean:message bundle="security" key="security.entity.business.type" /></label> 
							<html:select property="bizType" styleClass="manageEntity_bizType">
								<html:optionsCollection property="bizTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li> --%>
						<li class="hide">
							<label>独立报税</label> 
							<logic:equal value="1" name="entityForm" property="independenceTax">
								<input type="checkbox" name="independenceTax" checked="checked" value="1"/>
							</logic:equal>
							<logic:notEqual value="1" name="entityForm" property="independenceTax">
								<input type="checkbox" name="independenceTax"  value="1"/>
							</logic:notEqual>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageEntity_description"></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="manageEntity_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
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
				
				<div id="extendWrapper">
					<!-- Include extend jsp 包含extend对应的jsp文件 -->  
					<jsp:include page="/contents/security/entity/manageEntityInternalExtend.jsp" flush="true"/> 
				</div>
				
				
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_security_Modules').addClass('current');	
		$('#menu_seciruty_LegalEntity').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.manageEntity_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageEntity_form');
				// 修改人、修改时间不可编辑
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.manageEntity_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
        		$('#pageTitle').html('<bean:message bundle="security" key="security.entity" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
				// 更改Form Action
        		$('.manageEntity_form').attr('action', 'entityAction.do?proc=modify_object');
				
        		// 附件提交按钮事件
	   			var uploadObject = _createUploadObject('addPhoto', 'image', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_ACCOUNT %>/<%= BaseAction.getAccountId(request, response) %>/');
	   			$('img.deleteImg').each(function(i){
	   				$(this).show();
	   			});
				
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageEntity_title", true, "common", 100, 0);
    			flag = flag + validate("manageEntity_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageEntity_description", false, "common", 500, 0);
    			flag = flag + validate("manageEntity_status", true, "select", 0, 0);
    			
    			if(flag == 0){
    				submit('manageEntity_form');
    			}
        	}
		});
		
		// 查看模式
		if($('.manageEntity_form input.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('manageEntity_form');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="security" key="security.entity" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
			// 设置页面初始化SubAction
			$('.manageEntity_form input.subAction').val('viewObject');
			// 更换按钮Value
			$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
		}

		// 创建模式
		if($('.manageEntity_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		
		// "列表按钮事件"
		$('#btnList').click( function () {
			if (agreest())
			link('entityAction.do?proc=list_object');
		});
	})(jQuery);
</script>
