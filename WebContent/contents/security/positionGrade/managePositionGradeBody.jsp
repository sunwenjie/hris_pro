<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.security.PositionGradeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<!-- PositionGrade-Information -->
	<div id="systemPositionGrade" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="security" key="security.position.grade" />
			</label>
			<logic:notEmpty name="positionGradeForm" property="positionGradeId" >
				<label class="recordId"> &nbsp; (ID: <bean:write name="positionGradeForm" property="positionGradeId" />)</label>
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
				<logic:empty name="positionGradeForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="positionGradeForm" property="encodedId">
					<kan:auth right="modify" action="<%=PositionGradeAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=PositionGradeAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="positionGradeAction.do?proc=add_object" styleClass="positionGrade_Form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="positionGradeId" name="id" value='<bean:write name="positionGradeForm" property="encodedId" />' /> 
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="positionGradeForm" property="subAction" />" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.position.grade.name.cn" /><em> *</em></label> 
							<html:text property="gradeNameZH" maxlength="50" styleClass="positionGrade_gradeNameZH" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.grade.name.en" /></label> 
							<html:text property="gradeNameEN" maxlength="50" styleClass="positionGrade_gradeNameEN" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.grade.weight" /><em> *</em><img title="<bean:message bundle="security" key="security.position.grade.weight.tips" />" src="images/tips.png"/></label> 
							<html:text property="weight" maxlength="3" styleClass="positionGrade_weight" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="positionGrade_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="positionGrade_description" />
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
		// ���ò˵�ѡ����ʽ
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_OrgManagement').addClass('selected');
		$('#menu_security_PositionGrade').addClass('selected');
		$('#grade_moreinfo').hide();
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
	    		enableForm('positionGrade_Form');
	    		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
				// ����Subaction
	    		$('.subAction').val('modifyObject');
				// ���ı�����ʾ����
				$('#pageTitle').html('<bean:message bundle="security" key="security.position.grade" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
				// ���İ�ť��ʾ��
	    		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
	    		$('.positionGrade_Form').attr('action', 'positionGradeAction.do?proc=modify_object');
	    	}else{
	    		var flag = 0;
				flag = flag + validate("positionGrade_status", true, "select", 0, 0);
				flag = flag + validate("positionGrade_weight", true, "numeric", 3, 0);
				flag = flag + validate("positionGrade_gradeNameZH", true, "common", 100, 0);
				flag = flag + validate("positionGrade_description", false, "common", 500, 0);
				
				if(flag == 0){
					submit('positionGrade_Form');
				}
	    	}
		});
		// �鿴ģʽ
		if($('.positionGrade_Form input.subAction').val() != 'createObject'){
			// ��Form��ΪDisable
			disableForm('positionGrade_Form');
			// ����SubAction
			$('.positionGrade_Form input.subAction').val('viewObject');
			// ���İ�ť��ʾ��
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// ����Page Title
			$('#pageTitle').html('<bean:message bundle="security" key="security.position.grade" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
		}

		// ����ģʽ
		if($('.positionGrade_Form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		
		// ȡ����ť����¼�
		$('#btnList').click( function () {
			if (agreest())
			link('positionGradeAction.do?proc=list_object');
		});		
	})(jQuery);
</script>