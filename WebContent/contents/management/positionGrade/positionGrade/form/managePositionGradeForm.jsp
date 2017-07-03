<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="mgtPositionGradeAction.do?proc=add_object" styleClass="positionGrade_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="positionGradeId" id="positionGradeId" value='<bean:write name="mgtPositionGradeForm" property="encodedId"/>'/>
	<input type="hidden" name="subAction" id="subAction" value='<bean:write name="mgtPositionGradeForm" property="subAction"/>'/>
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em>必须填写</label></li>
		</ol>
		<ol class="auto">
			<li>
				<label>职位等级名称（中文）<em>*</em></label>
				<html:text property="gradeNameZH" maxlength="100" styleClass="managePositionGrade_gradeNameZH"/>
			</li>
			<li>
				<label>职位等级名称（英文）</label>
				<html:text property="gradeNameEN" maxlength="100" styleClass="managePositionGrade_gradeNameEN"/>
			</li>
			<li>
				<label>权重</label>
				<html:text property="weight" maxlength="100" styleClass="managePositionGrade_weight"/>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="managePositionGrade_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label>描述</label>
				<html:textarea property="description" styleClass="managePositionGrade_description"/>                    
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label>修改人</label> 
				<html:text property="decodeModifyBy" maxlength="100" disabled="disabled" styleClass="decodeModifyBy" /> 
			</li>
			<li>
				<label><bean:message bundle="public" key="public.modify.date" /></label> 
				<html:text property="decodeModifyDate" maxlength="100" disabled="disabled" styleClass="decodeModifyDate" /> 
			</li>
		</ol>
	</fieldset>
</html:form>