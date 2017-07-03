<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.management.PositionVO"%>
<%@ page import="com.kan.base.web.renders.management.SkillRender"%>
<%@ page import="com.kan.base.web.actions.management.PositionAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PositionVO positionVO = (PositionVO) request.getAttribute("mgtPositionForm");
	String positionId = null;
	String[] skillArray = null;
	
	if(positionVO != null && positionVO.getSkill() != null && !positionVO.getSkill().trim().equals("")){
	   skillArray = positionVO.getSkill().split(",");
	}
	
	if(positionVO != null && positionVO.getPositionId() != null){
	   positionId = positionVO.getPositionId();
	}
%>

<div id="content">
	<div id="securityPosition" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<logic:equal value="1" name="role">ְλ���ⲿ�����</logic:equal>
				<logic:equal value="2" name="role">ְλ���</logic:equal> 	
			</label>
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
				<kan:auth right="modify" action="<%=PositionAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" />
				</kan:auth>
				
				<kan:auth right="list" action="<%=PositionAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="mgtPositionAction.do?proc=add_object" styleClass="managePosition_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="positionId" name="positionId" class="managePosition_positionId" value='<bean:write name="mgtPositionForm" property="encodedId" />' /> 
				<input type="hidden" id="skill" name="skill" class="managePosition_skill" value='<bean:write name="mgtPositionForm" property="skill" />' /> 
				<html:hidden property="subAction" styleClass="subAction" styleId="subAction" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label>ְλ���ƣ����ģ�<em> *</em></label> 
							<html:text property="titleZH" maxlength="100" styleClass="managePosition_titleZH" /> 
						</li>
						<li>
							<label>ְλ���ƣ�Ӣ�ģ�</label> 
							<html:text property="titleEN" maxlength="100" styleClass="managePosition_titleEN" /> 
						</li>
						<li>
							<label>ְ��<em> *</em></label> 
							<html:select property="positionGradeId" styleClass="managePosition_positionGradeId">
								<html:optionsCollection property="positionGrades" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>�ϼ�ְλ</label> 
							<html:text property="parentPositionName" maxlength="20" styleClass="managePosition_parentPositionName" /> 
							<html:hidden property="parentPositionId" styleClass="managePosition_parentPositionId" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="managePosition_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="managePosition_description" />
						</li>
						<li>
							<label>��ע</label> 
							<html:textarea property="note" styleClass="managePosition_note" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>�޸���</label> 
							<html:text property="decodeModifyBy" maxlength="100" disabled="disabled" styleClass="decodeModifyBy" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.modify.date" /></label> 
							<html:text property="decodeModifyDate" maxlength="100" disabled="disabled" styleClass="decodeModifyDate" /> 
						</li>
					</ol>
					<div class="bottom">
						<p>
					</div> 
					<div id="tab">
						<div class="tabMenu"> 
							<ul> 
								<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first">���� (<span id="numberOfSkillId"><bean:write name="skillIdArraySize" /></span>)</li> 
								<li id="tabMenu2" onClick="changeTab(2,2)" >���� (<span id="numberOfAttachment"><bean:write name="attachmentArraySize" /></span>)</li> 
							</ul> 
						</div>
						<div id="tabContent">
							<div id="tabContent1" class="kantab">
								<span id="addSkillSpan" ><a onclick="$('#modalId').removeClass('hide');$('#shield').show();" class="kanhandle" >��Ӽ���</a></span>
								<%= SkillRender.getSkillNameCombo( request, skillArray ) %>
							</div>
							<div id="tabContent2" class="kantab" style="display:none" >
								<span id="uploadAttachmentSpan"><a name="uploadAttachment" id="uploadAttachment" onclick="uploadObject.submit();" >�ϴ�����</a></span>	
								<div id="attachmentsDiv">
									<ol id="attachmentsOL" class="auto">
										<%= AttachmentRender.getAttachments(request, positionVO.getAttachmentArray(), null) %>
									</ol>
								</div>
							</div> 
						</div> 
					</div>
				</fieldset>
			</html:form>
			
			<div id="popupWrapper">
				<jsp:include page="/popup/skillPopup.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {	
		// ��ʼ���˵���ʽ
		$('#menu_employee_Modules').addClass('current');			
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_Position').addClass('selected');
		
		// �������Ա���������ϼ�ְλ�ؼ�
		bindThinkingToParentPositionName();
		
		// Disable Module����Checkbox
		disableDiv('module_tree_div');
		
		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		
		var uploadObject = createUploadObject('uploadAttachment', 'common', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT_ORDER %>/<%= BaseAction.getAccountId(request, response) %>/<%= BaseAction.getUsername(request, response) %>/');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if( getSubAction() == 'viewObject'){
				// Form�ɱ༭
        		enableForm('managePosition_form');
				// ����Subaction
        		$('.subAction').val('modifyObject');
        		// �޸��ˡ��޸�ʱ�䲻�ɱ༭
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.managePosition_form').attr('action', 'mgtPositionAction.do?proc=modify_object');
        		// ����Page Title
        		if('<bean:write name="role"/>' == 1){
	    			$('#pageTitle').html('ְλ���ⲿ���༭');
        		}else{
	    			$('#pageTitle').html('ְλ�༭');
        		}
       			//	��ʾ�Ƴ�����ͼ��
       			$('img[id^=warning_img]').each(function(i){
       				$(this).show();
       			});
       			$('img[id^=disable_img]').each(function(i){
       				$(this).hide();
       			});
        	}else{
        		btnSubmit(false);
        	}
		});
		
		// �鿴ģʽ
		if($('.subAction').val() != 'createObject'){
			// ��Form��ΪDisable
			disableForm('managePosition_form');
			// ���İ�ť��ʾ��
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			
		};

		//	skill����ȡ���¼�
		$('#btn_cancel_lv').click(function(){
			$('#modalId').addClass('hide');$('#shield').hide();
		});
		
		// �б�ť�¼�
		$('#btnList').click( function () {
			if (agreest())
				link('mgtPositionAction.do?proc=list_object');
		});
		
		// �½����༭ģʽ��JS����
		if( getSubAction() != 'createObject' ){
			// ��Form��ΪDisable
			disableForm('managePosition_form');
			// ���İ�ť��ʾ��
    		$('#btnEdit').val('�༭');
    		// ����Page Title
			if('<bean:write name="role"/>' == 1){
				$('#pageTitle').html('ְλ���ⲿ����ѯ');
			}else{
				$('#pageTitle').html('ְλ��ѯ');
			}
		}else if( getSubAction() == 'createObject' ){
			// ���İ�ť��ʾ��
			$('#btnEdit').val('����');
			if('<bean:write name="role"/>' == 1){
				$('#pageTitle').html('ְλ���ⲿ������');
			}else{
				$('#pageTitle').html('ְλ����');
			}
		}
		
	})(jQuery);
	
	// ��ť�ύ�¼�
	function btnSubmit(useAjax) {
		var flag = 0;
		flag = flag + validate("managePosition_titleZH", true, "common", 100, 0);
		flag = flag + validate("managePosition_positionGradeId", true, "select", 0, 0);
		flag = flag + validate("managePosition_status", true, "select", 0, 0);
		flag = flag + validate("managePosition_parentPositionId", true, "thinking", 0, 0);
		flag = flag + validate("managePosition_description", false, "common", 500, 0);
		
		if(flag == 0){
			submit('managePosition_form');
		}
	};
    
    //	Think�¼�
	function bindThinkingToParentPositionName(){
		// Use the common thinking
		kanThinking_column('managePosition_parentPositionName', 'managePosition_parentPositionId', 'mgtPositionAction.do?proc=list_object_json');
	};
	
	// Get SubAction
	function getSubAction(){
		return $('.managePosition_form input#subAction').val();
	};
</script>
