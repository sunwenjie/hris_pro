<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.management.SkillVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.actions.management.SkillAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final SkillVO skillVO = (SkillVO) request.getAttribute("skillForm");
	String skillId = null;
	
	if(skillVO != null && skillVO.getSkillId() != null){
	   skillId = skillVO.getSkillId();
	}
%>
<div id="content">
	<div id="Skill" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.skill" /></label>
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
				<logic:empty name="skillForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="skillForm" property="encodedId">
					<kan:auth right="modify" action="<%=SkillAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=SkillAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="skillAction.do?proc=add_object" styleClass="modifySkill_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="moduleIdArray" name="moduleIdArray" value="" />
				<input type="hidden" id="skillId" name="skillId" class="modifySkill_skillId" value='<bean:write name="skillForm" property="encodedId" />' /> 
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="skillForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol  class="auto">
						<li>
							<label><bean:message bundle="management" key="management.skill.name.cn" /><em> *</em></label> 
							<html:text property="skillNameZH" maxlength="100" styleClass="modifySkill_skillNameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.skill.name.en" /></label> 
							<html:text property="skillNameEN" maxlength="100" styleClass="modifySkill_skillNameEN" /> 
						</li>
						<li id="parentDiv">
							<label><bean:message bundle="management" key="management.skill.parent" /></label> 
							<html:text property="parentSkillName" maxlength="20" styleClass="modifySkill_parentSkillName" /> 
							<html:hidden property="parentSkillId" styleClass="modifySkill_parentSkillId" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="modifySkill_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="modifySkill_description" />
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
		$('#menu_employee_Modules').addClass('current');			
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_Skills').addClass('selected');
		
		$("#tree").css("float", "left");
		$("#skillInfo").css("float", "right");
		$("#newSkillInfo").css("float", "right");
		
		$('#newSkillInfo').hide();
		
		// ������򵽸����ڵ㼼�ܿؼ�
		bindThinkingToParentSkillName();
		
		// Disable Module����Checkbox
		disableDiv('module_tree_div');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Form�ɱ༭
        		enableForm('modifySkill_form');
        		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// ����Subaction
        		$('.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.modifySkill_form').attr('action', 'skillAction.do?proc=modify_object');
        		// ����Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.skill" /> <bean:message bundle="public" key="oper.edit" />');
        	}else{
        		btnSubmit(false);
        	}
		});
		
		// �鿴ģʽ
		if($('.subAction').val() != 'createObject'){
			// ��Form��ΪDisable
			disableForm('modifySkill_form');
			// ���İ�ť��ʾ��
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// ����Subaction
    		$('.modifySkill_form input.subAction').val('viewObject');
			// ����Page Title
			$('#pageTitle').html('<bean:message bundle="management" key="management.skill" /> <bean:message bundle="public" key="oper.view" />');
		};

		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// ����ģʽ
		if($('.modifySkill_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		$('#btnList').click( function () {
			if (agreest())
			link('skillAction.do?proc=list_object');
		});
	})(jQuery);
	
	// ��ť�ύ�¼�
	function btnSubmit(useAjax) {
		var flag = 0;
		
		flag = flag + validate("modifySkill_skillNameZH", true, "common", 100, 0);
		flag = flag + validate("modifySkill_status", true, "select", 0, 0);
		//flag = flag + validate("modifySkill_parentSkillId", true, "thinking", 0, 0);
		
		flag = flag + validate("modifySkill_description", false, "common", 500, 0);
		
		if(flag == 0){
			submit('modifySkill_form');
		}
	};
    
    //	Think�¼�
	function bindThinkingToParentSkillName(){
		// Use the common thinking
		kanThinking_column('modifySkill_parentSkillName', 'modifySkill_parentSkillId', 'skillAction.do?proc=list_object_json');
	};
</script>
