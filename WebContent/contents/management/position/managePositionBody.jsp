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
				<logic:equal value="1" name="role">职位（外部）添加</logic:equal>
				<logic:equal value="2" name="role">职位添加</logic:equal> 	
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
							<label>职位名称（中文）<em> *</em></label> 
							<html:text property="titleZH" maxlength="100" styleClass="managePosition_titleZH" /> 
						</li>
						<li>
							<label>职位名称（英文）</label> 
							<html:text property="titleEN" maxlength="100" styleClass="managePosition_titleEN" /> 
						</li>
						<li>
							<label>职级<em> *</em></label> 
							<html:select property="positionGradeId" styleClass="managePosition_positionGradeId">
								<html:optionsCollection property="positionGrades" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>上级职位</label> 
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
							<label>备注</label> 
							<html:textarea property="note" styleClass="managePosition_note" />
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
					<div class="bottom">
						<p>
					</div> 
					<div id="tab">
						<div class="tabMenu"> 
							<ul> 
								<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first">技能 (<span id="numberOfSkillId"><bean:write name="skillIdArraySize" /></span>)</li> 
								<li id="tabMenu2" onClick="changeTab(2,2)" >附件 (<span id="numberOfAttachment"><bean:write name="attachmentArraySize" /></span>)</li> 
							</ul> 
						</div>
						<div id="tabContent">
							<div id="tabContent1" class="kantab">
								<span id="addSkillSpan" ><a onclick="$('#modalId').removeClass('hide');$('#shield').show();" class="kanhandle" >添加技能</a></span>
								<%= SkillRender.getSkillNameCombo( request, skillArray ) %>
							</div>
							<div id="tabContent2" class="kantab" style="display:none" >
								<span id="uploadAttachmentSpan"><a name="uploadAttachment" id="uploadAttachment" onclick="uploadObject.submit();" >上传附件</a></span>	
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
		// 初始化菜单样式
		$('#menu_employee_Modules').addClass('current');			
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_Position').addClass('selected');
		
		// 绑定联想框到员工姓名和上级职位控件
		bindThinkingToParentPositionName();
		
		// Disable Module树的Checkbox
		disableDiv('module_tree_div');
		
		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		
		var uploadObject = createUploadObject('uploadAttachment', 'common', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT_ORDER %>/<%= BaseAction.getAccountId(request, response) %>/<%= BaseAction.getUsername(request, response) %>/');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if( getSubAction() == 'viewObject'){
				// Form可编辑
        		enableForm('managePosition_form');
				// 更改Subaction
        		$('.subAction').val('modifyObject');
        		// 修改人、修改时间不可编辑
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.managePosition_form').attr('action', 'mgtPositionAction.do?proc=modify_object');
        		// 更换Page Title
        		if('<bean:write name="role"/>' == 1){
	    			$('#pageTitle').html('职位（外部）编辑');
        		}else{
	    			$('#pageTitle').html('职位编辑');
        		}
       			//	显示移除技能图标
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
		
		// 查看模式
		if($('.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('managePosition_form');
			// 更改按钮显示名
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			
		};

		//	skill区域取消事件
		$('#btn_cancel_lv').click(function(){
			$('#modalId').addClass('hide');$('#shield').hide();
		});
		
		// 列表按钮事件
		$('#btnList').click( function () {
			if (agreest())
				link('mgtPositionAction.do?proc=list_object');
		});
		
		// 新建、编辑模式下JS动作
		if( getSubAction() != 'createObject' ){
			// 将Form设为Disable
			disableForm('managePosition_form');
			// 更改按钮显示名
    		$('#btnEdit').val('编辑');
    		// 更换Page Title
			if('<bean:write name="role"/>' == 1){
				$('#pageTitle').html('职位（外部）查询');
			}else{
				$('#pageTitle').html('职位查询');
			}
		}else if( getSubAction() == 'createObject' ){
			// 更改按钮显示名
			$('#btnEdit').val('保存');
			if('<bean:write name="role"/>' == 1){
				$('#pageTitle').html('职位（外部）新增');
			}else{
				$('#pageTitle').html('职位新增');
			}
		}
		
	})(jQuery);
	
	// 按钮提交事件
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
    
    //	Think事件
	function bindThinkingToParentPositionName(){
		// Use the common thinking
		kanThinking_column('managePosition_parentPositionName', 'managePosition_parentPositionId', 'mgtPositionAction.do?proc=list_object_json');
	};
	
	// Get SubAction
	function getSubAction(){
		return $('.managePosition_form input#subAction').val();
	};
</script>
