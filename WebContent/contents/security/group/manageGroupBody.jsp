<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.system.ModuleRender"%>
<%@ page import="com.kan.base.web.renders.security.PositionRender"%>
<%@ page import="com.kan.base.web.renders.security.BranchRender"%>
<%@ page import="com.kan.base.domain.security.GroupVO"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.security.GroupAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final GroupVO groupVO = (GroupVO) request.getAttribute("groupForm");

	String groupId = null;
	if(groupVO != null && groupVO.getGroupId() != null){
	   groupId = groupVO.getGroupId();
	}

	final PagedListHolder groupHolder = (PagedListHolder) request.getAttribute("groupHolder");
%>
<style type="text/css">
.myhighlight { 
	background: yellow; 
	color: red;
	position: relative!important;
	top: 0px!important;
}
</style>

<div id="content">
	<div id="systemBranch" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="security" key="security.position.group" />
			</label>
			<logic:notEmpty name="groupForm" property="groupId" >
				<label class="recordId"> &nbsp; (ID: <bean:write name="groupForm" property="groupId" />)</label>
			</logic:notEmpty>
		</div>
		<div id="manageFormDiv" class="inner">
			<div class="top">
				<logic:empty name="groupForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="groupForm" property="encodedId">
					<kan:auth right="modify" action="<%=GroupAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=GroupAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="groupAction.do?proc=add_object" styleClass="group_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="moduleIdArray" name="moduleIdArray" value="" />
				<input type="hidden" id="groupId" name="groupId" class="group_groupId" value="<bean:write name="groupForm" property="encodedId" />" />
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="groupForm" property="subAction" />" /> 
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.position.group.name.cn" /><em> *</em></label> 
							<html:text property="nameZH" maxlength="50" styleClass="group_nameZH" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.group.name.en" /></label> 
							<html:text property="nameEN" maxlength="50" styleClass="group_nameEN" />
						</li>
						<logic:equal name="role" value="2">
							<%-- <li>
								<label><bean:message bundle="security" key="security.position.group.data.right" /> 
								<img src="images/tips.png" title="<bean:message bundle="security" key="security.position.group.data.right.title" />"></label> 
								<html:checkbox property="hrFunction" styleClass="group_hrFunction" />	
							</li> --%>
							<li>
								<label><bean:message bundle="security" key="security.position.group.data.role" /></label> 
								<html:select property="dataRole" styleClass="group_dataRole" styleId="dataRole" onchange="showRoleDataDiv()">
									<html:optionsCollection property="dataRoles" value="mappingId" label="mappingValue" />
								</html:select>
								<input type="hidden" id="dataRoleHidden" name="dataRoleHidden"/>
							</li>
						</logic:equal>
					</ol>	
					<ol class="auto">	
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="group_description" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="group_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<div id="tab"> 
						<div class="tabMenu"> 
							<ul> 
								<li id="tabMenu1" onClick="changeTab(1,3)" class="hover first"><bean:message bundle="public" key="menu.table.title.position" /> (<bean:write name="positionCount"/>)</li> 
								<li id="tabMenu2" onClick="changeTab(2,3)" ><bean:message bundle="public" key="menu.table.title.right" /></li>
								<li id="tabMenu3" onClick="changeTab(3,3)" ><bean:message bundle="public" key="menu.table.title.data.right" /></li> 
							</ul> 
						</div> 
						<div class="tabContent"> 
							<div id="tabContent1" class="kantab">
								<div>
									<div class="kantab">
										<input type="text" id="searchDataPosition" name="searchDataPosition" >
										<input type="button" name="searchButton" value="<bean:message bundle="public" key="button.page.find" />" onclick="findInPage(this)">
									</div>
								</div>
								<div class="kantab kantree" id="positionTreeDiv">
									<ol class="static">
										<%= PositionRender.getPositionTreeByGroupId( request, groupId ) %>
									</ol>
								</div>
							</div>
							<div id="tabContent2" class="kantab" style="display: none;">
								<div id="messageWrapper"></div>
								<div>
									<div class="kantab">
										<input type="text" id="searchDataModule" name="searchDataModule" >
										<input type="button" name="searchButton" value="<bean:message bundle="public" key="button.page.find" />" onclick="findInPage(this)">
									</div>
								</div>
								<div class="kantab kantree" >
									<ol class="auto">
										<li>
											<div class="module_tree_div" style="width: 100%; border: 0px;" id="moduleTreeDiv">
												<ol class="static">
													<%= ModuleRender.getModuleTreeByGroupId( request, false, groupId ) %>
												</ol>
											</div>
										</li>
										<li>
											<div id="right_rule" style="padding: 10px; display: none;"></div>
											<div id="groupId_ajax_sendback" style="display: none;"></div>
										</li>
									</ol>
								</div>
							</div>
							<div id="tabContent3" class="kantab kantree" style="display: none;">
								<label class="dataRoleErrorMessage"></label>
								<div>
									<div class="kantab">
										<input type="text" id="searchDataRole" name="searchDataRole" >
										<input type="button" name="searchButton" value="<bean:message bundle="public" key="button.page.find" />" onclick="findInPage(this)">
									</div>
								</div>
								<div>
									<label><bean:message bundle="security" key="security.group.select.rule" /></label>
									<div class="kantab" id="roleDataDiv"></div>
									<div style="display: none" id="departDiv">
										<label><bean:message bundle="public" key="public.please.select" /></label>
										<div class="kantab kantree">
											<%= BranchRender.getBranchTree( request, true ) %>
										</div>
									</div>
									
									<div style="display: none" id="positionDiv">
										<label><bean:message bundle="public" key="public.please.select" /></label>
										<div class="kantab kantree">
											<%= PositionRender.getPositionTree( request, true ) %>
										</div>
									</div>
									
									<div style="display: none" id="positionGradeDiv">
										<label><bean:message bundle="public" key="public.please.select" /></label>
										<div class="kantab" id="positionGradeListDiv"></div>
									</div>
									
									<div style="display: none" id="businessTypeDiv">
										<label><bean:message bundle="public" key="public.please.select" /></label>
										<div class="kantab" id="businessTypeListDiv"></div>
									</div>
									<div style="display: none" id="entityDiv">
										<label><bean:message bundle="public" key="public.please.select" /></label>
										<div class="kantab" id="entityListDiv">
										</div>
									</div>
								</div>
							</div>
						</div> 
					</div>
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
<div id="popupWrapper">
	<jsp:include page="/popup/security/rightRulePopup.jsp"></jsp:include>
</div>
							
<script type="text/javascript">
	
	(function($) {
		//id="searchDataPosition"
		$('#searchDataPosition').keydown(function(event){
            if(event.which == "13")    
            {
            	var searchText = $(this).val();
				var div = $(this).parent().parent().parent();
			    //判断搜索字符是否为空  
			    if (searchText == ""){  
			       // alert('搜索字符串为空');  
			    }else{
			    	clearHighLignt(div);
			    	highLignt(div,searchText);
			    } 
            }
        });
		
		$('#searchDataRole').keydown(function(event){
	            if(event.which == "13")    
	            {
	            	var searchText = $(this).val();
					var div = $(this).parent().parent().parent();
				    //判断搜索字符是否为空  
				    if (searchText == ""){  
				       // alert('搜索字符串为空');  
				    }else{
				    	clearHighLignt(div);
				    	highLignt(div,searchText);
				    } 
	            }
	        });
		
		$('#searchDataModule').keydown(function(event){
            if(event.which == "13")    
            {
            	var searchText = $(this).val();
				var div = $(this).parent().parent().parent();
			    //判断搜索字符是否为空  
			    if (searchText == ""){  
			       // alert('搜索字符串为空');  
			    }else{
			    	clearHighLignt(div);
			    	highLignt(div,searchText);
			    } 
            }
        });
		
		
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_PositionManagement').addClass('selected');
		$('#menu_security_PositionGroup').addClass('selected');
		
		if($('.subAction').val() == 'viewObject'){
			$("#dataRoleHidden").val($("#dataRole").val());
		}
		showRoleDataDiv();

		// 模态框Form添加groupId
		$('.module_form input[id="subAction"]').after('<input type="hidden" name="groupId" id="groupId" value=\"' + $('.group_form input[id=groupId]').val() + '" />');
		
		// 如果是新建树形结构不允许编辑
		if(!$('#groupId').val()){
			$('.module_tree_div a.kanhandle').each(function(){
				$(this).attr('disabled', true);
				if($(this).attr("onclick") != null && $(this).attr("onclick").indexOf('X') < 0){
					$(this).attr("onclick", 'X' + $(this).attr("onclick"));
				}
				$(this).addClass("disabled");
			});
		}
		
		// Disable Module树的Checkbox
		disableDiv('module_tree_div');

		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('group_form');
        		// 修改人、修改时间不可编辑
	    		$('.decodeModifyBy').attr('disabled', 'disabled');
	    		$('.decodeModifyDate').attr('disabled', 'disabled');
        		// Module树依旧Disable
        		disableDiv('module_tree_div');
        		// Enable操作按钮
				$('a[id^=manageModule]').each(function(i){
	   				$(this).attr("disabled", false);
	   				$(this).removeClass("disabled");
	   			});
				// 更改Subaction
        		$('.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.group_form').attr('action', 'groupAction.do?proc=modify_object');
        		// 更换Page Title
    			$('#pageTitle').html('<bean:message bundle="security" key="security.position.group" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
    			// Module权限局部保存按钮显示
        		$('#divSaveRightRule').show();
        	}else{
        		btnSubmit(false);
        	}
		});
		
		// 查看模式
		if($('.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('group_form');
			// Disable操作按钮并添加Disabled CSS
			$('a[id^=manageModule]').each(function(i){
   				$(this).attr("disabled", true);
   				$(this).addClass("disabled");
   			});
			// 更改SubAction
			$('.group_form input.subAction').val('viewObject');
			// 更改按钮显示名
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="security" key="security.position.group" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
		};

		// 创建模式
		if($('.group_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
		
		// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('groupAction.do?proc=list_object');
		});
	})(jQuery);
	
	// 按钮提交事件
	function btnSubmit(useAjax) {
		var flag = 0;
		
		flag = flag + validate("group_status", true, "status", 0, 0);
		flag = flag + validate("group_nameZH", true, "common", 100, 0);
		flag = flag + validate("group_description", false, "common", 500, 0);
		flag = flag + checkRule();
		
		if(flag == 0){
			submit('group_form');
		}
	};
	
	 
 	// 添加规则 - Add by Kevin
	function addRule() {
 		if( $('.combo_ruleTypeId').val() != '0' && $('.combo_ruleId').val() != '0'){
 			var exist = false;
 			
 			$('input[id^="ruleIdArray"]').each(function(i) {
 				if( $(this).val().indexOf($('.combo_ruleTypeId' ).val() + '_') >= 0 ){
 					exist = true;
 				}
       		});
 			
 			if(!exist){	
	 			$('#combo_rule').append('<li id="rule_' + $('.combo_ruleId' ).val() + '" style="margin: 2px 0px;"><input type="hidden" id="ruleIdArray" name="ruleIdArray" value="' + $('.combo_ruleTypeId' ).val() + '_' + $('.combo_ruleId' ).val() + '"><img src="images/warning-btn-s.png" onclick="removeRule(\'rule_' + $('.combo_ruleId' ).val() + '\');"/> &nbsp;&nbsp; ' + $('#option_ruleTypeId_' + $('.combo_ruleTypeId').val() ).html() + ' - ' + $('#option_ruleId_' + $('.combo_ruleId').val() ).html() + '</li>');
 			}else{
 				alert('<bean:message bundle="security" key="security.position.group.add.rule.repeat" />');
 			}
 		}else{
 			alert('<bean:message bundle="security" key="security.position.group.add.rule.empty" />');
 		}
    };
    
 	// 查看、新建或修改Position
	function managePosition(positionId){
		link('positionAction.do?proc=to_objectModify&positionId=' + positionId);
	};
    
	// 移除规则事件 - Add by Kevin
	function removeRule(id){
		if(confirm('<bean:message bundle="security" key="security.position.group.delete.rule.confirm" />')){
			$('#' + id).remove();
		}
	}; 
	
	// 切换规则类型
	function changeRuleType(moduleId) {
		loadHtml('.rulecombo', 'ruleAction.do?proc=list_object_html_select&moduleId=' + moduleId + '&ruleType=' + $('.combo_ruleTypeId').val(), false);
	};
	
	// 点击Module树节点
	function manageModule(element, groupId, moduleId){
		var disable = false;
		
		if($('.subAction').val() == 'viewObject'){
			disable = true;
		}else{
			// 模态框moduleId 赋值
			$('.module_form input[id=moduleId]').val(moduleId);
			// 当点击模块，加载Right和Rule的模态框
			loadHtmlWithRecall('#rightAndRuleDiv', 'moduleAction.do?proc=list_authority_combo_group_ajax&groupId=' + groupId + '&moduleId=' + moduleId, disable, showPopup());
		}

		$('input[type^="hidden"]#moduleIdArray').val(moduleId);
		
		($("a[id='manageModule']")).each(function(i) {
			$(this).removeClass('current');
		});
		$(element).addClass('current');
	};
	
	// 保存规则权限
	function saveRightRule(){
		submitForm('module_form', 'modifyObject', null, null, null, '', 'groupAction.do?proc=modify_object_ajax_popup', 'hidePopup();checkImg();enableBtn();', true);
	};
	
	function showRoleDataDiv(){
		
		$("#positionDiv").css("display","none");
		$("#departDiv").css("display","none");
		$("#positionGradeDiv").css("display","none");
		$("#businessTypeDiv").css("display","none");
		$("#entityDiv").css("display","none");
		
		if ($("#dataRole").val() != "0") {
			$("#tabMenu3").css("display","block");
			var dataRoleDivHtml = "<ol class=\"auto\">";
			
			dataRoleDivHtml = dataRoleDivHtml + "<li>";
			dataRoleDivHtml = dataRoleDivHtml + "<label>" + "<bean:message bundle='security' key='security.group.rule0' />" + "</label>";
			dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"0\" onclick=\"chooseRuleData(this,'')\">";
			dataRoleDivHtml = dataRoleDivHtml + "</li>";
			
			if ($("#dataRole").val() == "1") {
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule2' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"2\" onclick=\"chooseRuleData(this,'')\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule4' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"3\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_branck\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";

				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule5' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"4\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_position\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";

				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule6' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"5\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_positionGrade\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule7' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"6\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_businessType\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule8' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"7\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_entity\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";

				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule9' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"9\" onclick=\"chooseRuleData(this,'')\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
			}
			if ($("#dataRole").val() == "2") {
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule1' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"1\" onclick=\"chooseRuleData(this,'')\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule2' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"2\" onclick=\"chooseRuleData(this,'')\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule3' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"8\" onclick=\"chooseRuleData(this,'')\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule4' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"3\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_branck\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";

				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule5' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"4\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_position\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";

				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule6' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"5\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_positionGrade\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule7' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"6\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_businessType\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule8' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"7\" onclick=\"chooseRuleData(this,'')\" class=\"group_rule_entity\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
				
				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule9' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"9\" onclick=\"chooseRuleData(this,'')\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
			}
			
			if ($("#dataRole").val() == "4") {

				dataRoleDivHtml = dataRoleDivHtml + "<li>";
				dataRoleDivHtml = dataRoleDivHtml + "<label><bean:message bundle='security' key='security.group.rule1' /></label>";
				dataRoleDivHtml = dataRoleDivHtml + "<input type=\"checkbox\" name=\"ruleIds\" value=\"1\" onclick=\"chooseRuleData(this,'')\">";
				dataRoleDivHtml = dataRoleDivHtml + "</li>";
			}
			$("#roleDataDiv").html(dataRoleDivHtml);
			
			if($('.subAction').val() == 'viewObject' || $('.subAction').val() == 'modifyObject'){
				$.ajax({
					url: 'groupAction.do?proc=getRulesByGroupId', 
					type: 'POST', 
					traditional: true,
					data:'groupId='+$("#groupId").val(),
					dataType : 'json',
					async:false,
					success: function(result){
						for(var i = 0 ; i < result.length ; i++){
							if($("#dataRole").val()==$("#dataRoleHidden").val()){
								var checkIdArray = new Array();
								$("input[name='ruleIds'][value='"+result[i].ruleId+"']").attr("checked","checked");
								if(result[i].remark1 !=""){
									checkIdArray = result[i].remark1.replace("{","").replace("}","").split(":");
								}
								chooseRuleData($("input[name='ruleIds'][value='"+result[i].ruleId+"']")[0],checkIdArray);
							}
						}
					}
				});
			}
		}else{
			$("#tabMenu3").css("display","none");
			changeTab(1,3);
		}
	}
	
	function chooseRuleData(obj,checkIdArray){
		
		if (obj.value == "9"){
			$("input[name='ruleIds']").each(function(index){
				if (obj.checked) {
					if ($(this).val() != "9") {
						
						$(this).attr("checked",false);
						$(this).attr("disabled",true);
						
						$("#positionDiv").css("display","none");
						$("#departDiv").css("display","none");
						$("#positionGradeDiv").css("display","none");
						$("#businessTypeDiv").css("display","none");
						$("#entityDiv").css("display","none");
					}
				}else{
					$(this).attr("disabled",false);
				}
			});
		}else{
			if (obj.value == "3"){
				if (obj.checked){
					for(var i = 0 ; i < checkIdArray.length ; i++){
						$("input[name='branchIds'][value='"+checkIdArray[i]+"']").attr("checked","checked");
					}
					$("#departDiv").css("display","block");
				}else{
					$("#departDiv").css("display","none");
				}
			}
			if (obj.value == "4"){
				if (obj.checked){
					for(var i = 0 ; i < checkIdArray.length ; i++){
						$("input[name='positionIds'][value='"+checkIdArray[i]+"']").attr("checked","checked");
					}
					$("#positionDiv").css("display","block");
				}else{
					$("#positionDiv").css("display","none");
				}
			}
			if (obj.value == "5"){
				if (obj.checked){
					$("#positionGradeDiv").css("display","block");
					getPositionGradeList(checkIdArray);
				}else{
					$("#positionGradeDiv").css("display","none");
				}
			}
			if (obj.value == "6"){
				if (obj.checked){
					$("#businessTypeDiv").css("display","block");
					getBusinessTypeList(checkIdArray);
				}else{
					$("#businessTypeDiv").css("display","none");
				}
			}
			if (obj.value == "7"){
				if (obj.checked){
					$("#entityDiv").css("display","block");
					getEntityList(checkIdArray);
				}else{
					$("#entityDiv").css("display","none");
				}
			}
		}
		
	}
	
	function getPositionGradeList(checkIdArray){
		
		var innerHTML = "<ol>";
		$.ajax({
			url: 'positionGradeAction.do?proc=getPositionGradeList', 
			type: 'POST', 
			traditional: true,
			dataType : 'json',
			async:false,
			success: function(result){
				if (result.length > 0) {
					for(var i = 0 ; i < result.length ; i++){
						
						innerHTML = innerHTML + "<li>";
						innerHTML = innerHTML + "<label>"+result[i].name+"</label>";
						if(checkIdArray.indexOf(result[i].id) >= 0){
							innerHTML = innerHTML + "<input type=\"checkBox\" name=\"positionGradeIds\" value=\""+result[i].id+"\" checked=\"checked\">";
						}else{
							innerHTML = innerHTML + "<input type=\"checkBox\" name=\"positionGradeIds\" value=\""+result[i].id+"\">";
						}
						innerHTML = innerHTML + "</li>";
					}
				}else{
					innerHTML = innerHTML + "<li>";
					innerHTML = innerHTML + "<label>没有可选择的职级</label>";
					innerHTML = innerHTML + "</li>";
				}
			} 
		});
		innerHTML = innerHTML + "</ol>";
		$("#positionGradeListDiv").html(innerHTML);
	}
	
	function getBusinessTypeList(checkIdArray){
		
		var innerHTML = "<ol>";
		$.ajax({
			url: 'businessTypeAction.do?proc=getBusinessTypeList', 
			type: 'POST', 
			traditional: true,
			dataType : 'json',
			async:false,
			success: function(result){
				
				if (result.length > 0) {
					for(var i = 0 ; i < result.length ; i++){
						innerHTML = innerHTML + "<li>";
						innerHTML = innerHTML + "<label>"+result[i].name+"</label>";
						if(checkIdArray.indexOf(result[i].id) >= 0){
							innerHTML = innerHTML + "<input type=\"checkBox\" name=\"businessTypeIds\" value=\""+result[i].id+"\" checked=\"checked\">";
						}else{
							innerHTML = innerHTML + "<input type=\"checkBox\" name=\"businessTypeIds\" value=\""+result[i].id+"\">";
						}
						innerHTML = innerHTML + "</li>";
					}
				}else{
					innerHTML = innerHTML + "<li>";
					innerHTML = innerHTML + "<label>没有可选择的项目</label>";
					innerHTML = innerHTML + "</li>";
				}
			} 
		});
		innerHTML = innerHTML + "</ol>";
		$("#businessTypeListDiv").html(innerHTML);
	}
	
	function getEntityList(checkIdArray){
		
		var innerHTML = "<ol>";
		$.ajax({
			url: 'entityAction.do?proc=getEntityList', 
			type: 'POST', 
			traditional: true,
			dataType : 'json',
			async:false,
			success: function(result){
				if (result.length > 0) {
					for(var i = 0 ; i < result.length ; i++){
						
						innerHTML = innerHTML + "<li>";
						innerHTML = innerHTML + "<label style=\"width:35%\">"+result[i].name+"</label>";
						if(checkIdArray.indexOf(result[i].id) >= 0){
							innerHTML = innerHTML + "<input type=\"checkBox\" name=\"entityIds\" value=\""+result[i].id+"\" checked=\"checked\">";
						}else{
							innerHTML = innerHTML + "<input type=\"checkBox\" name=\"entityIds\" value=\""+result[i].id+"\">";
						}
						innerHTML = innerHTML + "</li>";
					}
				}else{
					innerHTML = innerHTML + "<li>";
					innerHTML = innerHTML + "<label>没有可选择的法务实体</label>";
					innerHTML = innerHTML + "</li>";
				}
			} 
		});
		innerHTML = innerHTML + "</ol>";
		$("#entityListDiv").html(innerHTML);
	}
	
	function checkRule(){
		var flag = 0;
		if($("input[name='ruleIds'][value='3']").attr("checked") && $("input[name='branchIds']:checked").length == 0){
			addError("dataRoleErrorMessage","请选择具体的部门!");
			flag = 1;
		}
		if($("input[name='ruleIds'][value='4']").attr("checked") && $("input[name='positionIds']:checked").length == 0){
			addError("dataRoleErrorMessage","请选择具体的职位!");
			flag = 1;
		}
		if($("input[name='ruleIds'][value='5']").attr("checked") && $("input[name='positionGradeIds']:checked").length == 0){
			addError("dataRoleErrorMessage","请选择具体的职级!");
			flag = 1;
		}
		if($("input[name='ruleIds'][value='6']").attr("checked") && $("input[name='businessTypeIds']:checked").length == 0){
			addError("dataRoleErrorMessage","请选择具体的业务类型!");
			flag = 1;
		}
		if($("input[name='ruleIds'][value='7']").attr("checked") && $("input[name='entityIds']:checked").length == 0){
			addError("dataRoleErrorMessage","请选择具体的法务实体!");
			flag = 1;
		}
		return flag;
	}
	
	function findInPage(tar) {
		
		var searchText = $(tar).prev().val();
		var div = $(tar).parent().parent().parent();
	    //判断搜索字符是否为空  
	    if (searchText == ""){  
	       // alert('搜索字符串为空');  
	    }else{
	    	clearHighLignt(div);
	    	highLignt(div,searchText);
	    } 
	}
	function clearHighLignt(div){
		clearObject($(div).find("#roleDataDiv"));
		clearObject($(div).find("#departDiv"));
		clearObject($(div).find("#positionDiv"));
		clearObject($(div).find("#positionGradeDiv"));
		clearObject($(div).find("#businessTypeDiv"));
		clearObject($(div).find("#entityDiv"));
		clearObject($(div).find("#positionTreeDiv"));
		clearObject($(div).find("#moduleTreeDiv"));
	}
	function clearObject(tar){
		tar.each(function () {
            $(this).find('.myhighlight').each(function () {
                $(this).replaceWith($(this).html());
             });
         });
	}
	
	function highLignt(div,searchText) {

		//var searchText = $("#searchDataRole").val();

        if ($.trim(searchText) != "") {
 			if($(div).find('#roleDataDiv').is(":visible")==true){
 				doHighLignt($(div).find('#roleDataDiv'),searchText);
 			}
 			if($('#departDiv').is(":visible")==true){
 				doHighLignt($('#departDiv'),searchText);
 			}
 			if($('#positionDiv').is(":visible")==true){
 				doHighLignt($('#positionDiv'),searchText);
 			}
 			if($('#positionGradeDiv').is(":visible")==true){
 				doHighLignt($('#positionGradeDiv'),searchText);
 			}
 			if($('#businessTypeDiv').is(":visible")==true){
 				doHighLignt($('#businessTypeDiv'),searchText);
 			}
 			if($('#entityDiv').is(":visible")==true){
 				doHighLignt($('#entityDiv'),searchText);
 			}
 			if($('#positionTreeDiv').is(":visible")==true){
 				doHighLignt($('#positionTreeDiv'),searchText);
 			}
 			if($('#moduleTreeDiv').is(":visible")==true){
 				doHighLignt($('#moduleTreeDiv'),searchText);
 			}
        }

    }
	function doHighLignt(tar,searchText){
		 if ($.trim(searchText) != "" ) {
			 searchText = $.trim(searchText);
	        var regExp = new RegExp(searchText, 'g');
			tar.each(function () {
	            var html = $(this).html();
					html = html.replace(regExp, '<span class="myhighlight">' + searchText + '</span>');
	            $(this).html(html);
	
	        });
		 }
	}
	
	
	
</script>
