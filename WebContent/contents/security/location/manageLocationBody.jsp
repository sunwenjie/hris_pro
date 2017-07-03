<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.web.actions.security.LocationAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="systemLocation" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="security" key="security.office.location" />
			</label>
			<logic:notEmpty name="locationForm" property="locationId" >
				<label class="recordId"> &nbsp; (ID: <bean:write name="locationForm" property="locationId" />)</label>
			</logic:notEmpty>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message success fadable">
						<bean:write name="MESSAGE" />
			    		<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<logic:empty name="locationForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="locationForm" property="encodedId">
					<kan:auth right="modify" action="<%=LocationAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=LocationAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="secLocationAction.do?proc=add_object" styleClass="location_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="locationId" name="id" value="<bean:write name='locationForm' property='encodedId' />">
				<input type="hidden" id="cityIdTemp" name="cityIdTemp" class="location_cityIdTemp" value="<bean:write name='locationForm' property='cityId' />">
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="locationForm" property="subAction" />" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.office.location.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="100" styleClass="location_nameZH" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.office.location.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="location_nameEN" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.province.city" /><em> *</em></label> 
							<html:select property="provinceId" styleClass="location_provinceId" >
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.website" /></label> 
							<html:text property="website" maxlength="100" styleClass="location_website" />
						</li>
					</ol >
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.office.location.name.cn" /></label> 
							<html:text property="addressZH" maxlength="100" styleClass="location_addressZH" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.office.location.name.en" /></label> 
							<html:text property="addressEN" maxlength="100" styleClass="location_addressEN" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.telephone" /></label> 
							<html:text property="telephone" maxlength="25" styleClass="location_telephone" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.fax" /></label> 
							<html:text property="fax" maxlength="25" styleClass="location_fax" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.zip.code" /></label> 
							<html:text property="postcode" maxlength="25" styleClass="location_postcode" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.email.address" /></label> 
							<html:text property="email" maxlength="100" styleClass="location_email" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="location_description" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="location_status">
								<html:optionsCollection property="locationStatuses" value="mappingId" label="mappingValue" />
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
			</html:form>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_OrgManagement').addClass('selected');
		$('#menu_security_Location').addClass('selected');

		// 初始化省份控件
		provinceChange('location_provinceId', 'viewObject', $('.location_cityIdTemp').val(), 'cityId');
		
		// 绑定省Change事件
		$('.location_provinceId').change( function () { 
			provinceChange('location_provinceId', 'modifyObject', 0, 'cityId');
		});
	
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.location_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('location_form');
        		// 修改人、修改时间不可编辑
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// 更改Subaction
        		$('.location_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.location_form').attr('action', 'secLocationAction.do?proc=modify_object');
        		// 更换Page Title
    			$('#pageTitle').html('<bean:message bundle="security" key="security.office.location" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
        	}else{
        		var flag = 0;
        		//特殊格式验证
        		flag = flag + validate("location_postcode", false, "numeric", 25, 0);
        		flag = flag + validate("location_fax", false, "phone", 0, 0);
        		flag = flag + validate("location_telephone", false, "phone", 0, 0);
        		flag = flag + validate("location_email", false, "email", 0, 0);
        		flag = flag + validate("location_website", false, "website", 0, 0);
        		
        		//必选项
        		flag = flag + validate("location_nameZH", true, "common", 100, 0);
        		flag = flag + validate("location_status", true, "select", 0, 0);
        		flag = flag + validate("location_provinceId", true, "select", 0, 0);
        		flag = flag + validate("cityId", true, "select", 0, 0);
        		if( $('.location_provinceId').val() != '' || $('.location_provinceId').val() == '0' ){
        			flag = flag + validate("location_cityId", true, "select", 0, 0);
        		}
        		
        		flag = flag + validate("location_description", false, "common", 500, 0);
        		
        		if(flag == 0){
        			submit('location_form');
        		}
        	}
		});
		
		// 查看模式
		if($('.location_form input.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('location_form');
			// 设置页面初始化SubAction
			$('.location_form input.subAction').val('viewObject');
			// 更改按钮显示名
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="security" key="security.office.location" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
		};
		
		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// 创建模式
		if($('.location_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('secLocationAction.do?proc=list_object');
		});
	})(jQuery);
</script>