<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.security.StaffRender"%>
<%@ page import="com.kan.base.web.renders.security.GroupRender"%>
<%@ page import="com.kan.base.web.renders.system.ModuleRender"%>
<%@ page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page import="com.kan.base.web.actions.security.PositionAction"%>
<%@ page import="com.kan.base.web.actions.security.GroupAction"%>
<%@ page import="com.kan.base.domain.security.PositionVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PositionVO positionVO = (PositionVO) request.getAttribute("positionForm");
	String positionId = null;
	
	if(positionVO != null && positionVO.getPositionId() != null){
	   positionId = positionVO.getPositionId();
	}
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
	<div id="securityPosition" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="security" key="security.position" />
			</label>
			<logic:notEmpty name="positionForm" property="positionId" >
				<label class="recordId"> &nbsp; (ID: <bean:write name="positionForm" property="positionId" />)</label>
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
				<logic:empty name="positionForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
				</logic:empty>
				<logic:notEmpty name="positionForm" property="encodedId">
					<kan:auth right="modify" action="<%=PositionAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<logic:equal value="2" name="positionPrefer">
					<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.t.chart" />" />
				</logic:equal> 
				<logic:notEqual value="2" name="positionPrefer">
					<input type="button" class="reset" name="btnCancel" id="btnCancel" value="<bean:message bundle="public" key="button.o.chart" />" />
				</logic:notEqual> 
			</div>
			<html:form action="positionAction.do?proc=add_object" styleClass="position_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="moduleIdArray" name="moduleIdArray" value="" />
				<input type="hidden" id="positionId" name="positionId" class="position_positionId" value='<bean:write name="positionForm" property="encodedId" />' /> 
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="positionForm" property="subAction" />" /> 
				<input type="hidden" id="forwardURL" name="forwardURL" class="forwardURL" value="" />
				<input type="hidden" name="rootPositionId" id="rootPositionId" value="<%=request.getAttribute("rootPositionId") %>" />  
				<input type="hidden" name="tempPositionPrefer" id="tempPositionPrefer" value="<%=request.getAttribute("tempPositionPrefer") %>" />  
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.position.name.cn" /><em> *</em></label> 
							<html:text property="titleZH" maxlength="100" styleClass="position_titleZH" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.name.en" /></label> 
							<html:text property="titleEN" maxlength="100" styleClass="position_titleEN" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.office.location" /></label> 
							<html:select property="locationId" styleClass="position_locationId">
								<html:optionsCollection property="locations" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.branch" /></label> 
							<html:select property="branchId" styleClass="position_branchId">
								<html:optionsCollection property="branchs" value="mappingId" label="mappingValue" filter="false" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.budget.HC" /><a title="<bean:message bundle="security" key="security.position.budget.HC.tips" />"><img src="images/tips.png" /></a></label> 
							<html:text property="isVacant" maxlength="4" styleClass="position_isVacant" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.independent.show" /></label> 
							<logic:equal value="1" name="positionForm" property="isIndependentDisplay">
								<input type="checkbox" name="isIndependentDisplay" class="position_styleClass" value="1" checked="checked"/>
							</logic:equal>
							<logic:notEqual value="1" name="positionForm" property="isIndependentDisplay">
								<input type="checkbox" name="isIndependentDisplay" class="position_styleClass" value="1" />
							</logic:notEqual>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.release" /><a title="<bean:message bundle="security" key="security.position.release.tips" />"><img src="images/tips.png" /></a></label> 
							<html:select property="needPublish" styleClass="position_needPublish">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.grade" /><em> *</em></label> 
							<html:select property="positionGradeId" styleClass="position_positionGradeId">
								<html:optionsCollection property="positionGrades" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.parent.position" /></label> 
							<html:text property="parentPositionName" maxlength="20" styleClass="position_parentPositionName" /> 
							<html:hidden property="parentPositionId" styleClass="position_parentPositionId" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.description" />（JD）</label> 
							<html:textarea property="description" styleClass="position_description" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="position_status">
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
					<div id="tab"> 
						<div class="tabMenu"> 
							<ul>
								<li id="tabMenu1" onClick="changeTab(1,4)" class="hover first"><bean:message bundle="public" key="menu.table.title.employee" /> (<bean:write name="staffCount"/>)</li>
								<li id="tabMenu2" onClick="changeTab(2,4)" ><bean:message bundle="public" key="menu.table.title.position.group" /> (<bean:write name="positionGroupCount"/>)</li>
								<li id="tabMenu3" onClick="changeTab(3,4)"><bean:message bundle="public" key="menu.table.title.right" /></li> 
								<li id="tabMenu4" onClick="changeTab(4,4)"><bean:message bundle="public" key="menu.table.title.attachment" /> (<span id="numberOfAttachment"><bean:write name="attachmentCount"/></span>)</li>
							</ul> 
						</div> 
						<div class="tabContent"> 
							<div id="tabContent1" class="kantab">
								<span><a name="createStaff" id="createStaff" onclick="createEmployee();" class="kanhandle"><bean:message bundle="public" key="link.new.employee" /></a></span>
								<ol class="auto">
									<%=StaffRender.getStaffThinkingCombo( request )%>
								</ol>
								<ol id="staffOL" class="auto">
									<%=StaffRender.getStaffsByPositionId( request, positionId )%>
								</ol>
							</div>
							<div id="tabContent2" class="kantab" style="display:none">
								<div>
									<div class="kantab">
										<input type="text" id="searchDataPosition" name="searchDataPosition" >
										<input type="button" name="searchButton" value="页内查找" onclick="findInPage(this)">
									</div>
								</div>
								<kan:auth right="new" action="<%=GroupAction.accessAction%>">
									<span><a name="createGroup" id="createGroup" onclick="link('groupAction.do?proc=to_objectNew');" class="kanhandle"><bean:message bundle="public" key="link.new.position.group" /></a></span>
								</kan:auth>
								<div class="kantab kantree" id="positionTreeDiv">
									<ol id="groupOL" class="auto">
										<%=GroupRender.getGroupMultipleChoiceByPositionId( request, positionId )%>
									</ol>
								</div>
							</div>
							<div id="tabContent3" class="kantab kantree" style="display: none;">
								<div id="messageWrapper"></div>
								<div>
									<div class="kantab">
										<input type="text" id="searchDataModule" name="searchDataModule" >
										<input type="button" name="searchButton" value="页内查找" onclick="findInPage(this)">
									</div>
								</div>
								<div class="kantab kantree" id="moduleTreeDiv">
									<ol class="auto">
										<li style="margin: 0px;">
											<div class="module_tree_div" style="width: 100%; border: 0px;">
												<ol class="static">
													<%= ModuleRender.getModuleTreeByPositionId( request, false, positionId ) %>
												</ol>
											</div>
										</li>
									</ol>
								</div>
								<div id="positionId_ajax_sendback" style="display: none;"></div>
							</div>
							<div id="tabContent4" class="kantab" style="display: none;">
								<span><a name="uploadAttachment" id="uploadAttachment" onclick="uploadObject.submit();"><bean:message bundle="public" key="link.upload.attachment" /></a></span>	
								<ol id="attachmentsOL" class="auto">
									<%= AttachmentRender.getAttachments(request, positionVO.getAttachmentArray(), null) %>
								</ol>
							</div>
						</div> 
					</div>
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
		// 初始化选中菜单样式
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_PositionManagement').addClass('selected');
		$('#menu_security_Position').addClass('selected');
		
		$(".tabMenu li").first().addClass('hover');
		$(".tabMenu li").first().addClass('first');
		$("#tabContent .kantab").first().show();
		
		// 模态框Form添加PositionId
		$('.module_form input[id="subAction"]').after('<input type="hidden" name="positionId" id="positionId" value=\"' + $('.position_form input[id=positionId]').val() + '" />');
		
		// 如果是新建树形结构不允许编辑
		if(!$('#positionId').val()){
			$('.module_tree_div a.kanhandle').each(function(){
				$(this).attr('disabled', true);
				if($(this).attr("onclick") != null && $(this).attr("onclick").indexOf('X') < 0){
					$(this).attr("onclick", 'X' + $(this).attr("onclick"));
				}
				$(this).addClass("disabled");
			});
		}
		
		// 绑定联想框到员工姓名和上级职位控件
		bindThinkingToStaffName();
		bindThinkingToParentPositionName();
		
		// Disable Module树的Checkbox
		disableDiv('module_tree_div');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if($('.position_form input.subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('position_form');
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
        		$('.position_form input.subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.position_form').attr('action', 'positionAction.do?proc=modify_object');
        		// 更换Page Title
    			$('#pageTitle').html('<bean:message bundle="security" key="security.position" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
				// Enable删除小图标
       			$('img[id^=warning_img]').each(function(i){
       				$(this).show();
       			});
       			$('img[id^=disable_img]').each(function(i){
       				$(this).hide();
       			});
        	}else{
        		btnSubmit();
        	}
		});
		
		// 非新建模式
		if($('.position_form input.subAction').val() != 'createObject'){
			// 将Form设为Disable
			disableForm('position_form');
			// 更改Subaction
    		$('.position_form input.subAction').val('viewObject');
			// Disable操作按钮并添加Disabled CSS
			$('a[id^=manageModule]').each(function(i){
   				$(this).attr("disabled", true);
   				$(this).addClass("disabled");
   			});
			// 更改按钮显示名
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="security" key="security.position" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
		};
		
		$('#btnCancel').click( function () {
			if (agreest())
			link("positionAction.do?proc=list_object&rootPositionId="+$("#rootPositionId").val()+"&tempPositionPrefer="+$("#tempPositionPrefer").val());
		});

		// 修改人、修改时间不可编辑
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');

		// 创建模式
		if($('.position_form input.subAction').val() == 'createObject'){
			$('.decodeModifyDate').val('');
		}

		var uploadObject = createUploadObject('uploadAttachment', 'common', '<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_POSITION %>/<bean:write name="accountId" />/<bean:write name="username" />/');
		
	})(jQuery);
	
	// 按钮提交事件
	function btnSubmit() {
		var flag = 0;
		flag = flag + validate("position_titleZH", true, "common", 100, 0);
		flag = flag + validate("position_status", true, "select", 0, 0);
		flag = flag + validate("position_branchId", true, "select", 0, 0);
		flag = flag + validate("position_positionGradeId", true, "select", 0, 0);
		flag = flag + validate("position_description", false, "common", 500, 0);
		flag = flag + validate("position_isVacant", false, "numeric", 0, 0);
		
		if(flag == 0){
			submit('position_form');
		}
	};
    
 	// 添加员工 - Add by Kevin
	function addStaff() {
 		var array =$("input[name='staffIdArray']");
 		var count=0;
 		if($('.thinking_staffType').val() == '1'){
 		$(array).each(function(){
 	       if(this.value.split("_")[1]==1){
 	    	  count++;
 	       }                      
 	    }); 
 		var isVacant = <bean:write name="positionForm" property="isVacant"/>;
 		if(count >= isVacant && isVacant>0){
			alert("编制已满，如果继续添加请先修改编制人数");
			$(".q" ).val('');
       		$(".thinking_staffId" ).val('');
       		$(".wdate_beginTime" ).val('');
       		$(".wdate_endTime" ).val('');
       		$('.thinking_staffType').val("0");
       		$('.thinking_staffName').trigger("blur");
       		changeStaffType();
			return false;
		}}
 		
 		var validateError = 0;
 		if( $('#staffId').val() == ''){
 			validateError = validateError + 1;
 			if(!$('.thinking_staffName').hasClass('error'))
 				addError('thinking_staffName', '联想无效；');
 		}else{
 			cleanError('thinking_staffName');
 		}
 		
 		validateError = validateError + validate("thinking_staffType", true, "select", 0, 0);
 		if( $('.thinking_staffType').val() == '2' ){
 			validateError = validateError + validate("wdate_beginTime", true, "common", 20, 0);
 			validateError = validateError + validate("wdate_endTime", true, "common", 20, 0);
 		}
 		
 		if( validateError == 0 ){
 			var exist = false;
 			$('input[class^="thinking_staffIds"]').each(function(i) {
 				if( $(this).val().indexOf($('.thinking_staffId' ).val() + '_') == 0 ){
 					exist = true;
 				}
       		});
 			
 			if(!exist){
 				// 判断当前添加的是否是代理员工
 				var agent = '';
 				var num='0';
 				var  staffType=$('.thinking_staffType').val();
 				var staffId=$('.thinking_staffId' ).val();
 				var beginTime=$('.wdate_beginTime' ).val();
 				var endTime=$('.wdate_endTime' ).val();
 				var staffName=$('.thinking_staffName' ).val();
 				if(staffType == '2'){
 					agent = (lang_en ? '(agent)' : '（代理）') + '&nbsp;&nbsp; ' + beginTime + ' - ' + endTime;
	 				$('#staffOL').html($('#staffOL').html() + '<li id="thinking_staff_' + staffId + '" style=\"margin: 5px 0 0 0;\"><input type="hidden" id="staffIdArray" name="staffIdArray" class="thinking_staffIds" value="' + staffId + '_' + staffType + '_' + beginTime +' '+ '_' + endTime +' '+ '"><img src="images/warning-btn.png" onclick="removeStaff(\'thinking_staff_' +staffId + '\');"/> &nbsp;&nbsp; ' + $(".thinking_staffName" ).val() + ' &nbsp;&nbsp; ' + agent + '</li>');
 				}else{
 					$.ajax({
						 url : 'positionAction.do?proc=check_ajax&staffId='+$('.thinking_staffId' ).val(),
						 dataType : "json",
						 success : function(data){
							 if(data.success==false){
								 $('#staffOL').html($('#staffOL').html() + '<li id="thinking_staff_' + staffId + '" style=\"margin: 5px 0 0 0;\"><input type="hidden" id="staffIdArray" name="staffIdArray" class="thinking_staffIds" value="' + staffId + '_' + staffType + '_' + beginTime +' '+ '_' + endTime +' '+ '"><img src="images/warning-btn.png" onclick="removeStaff(\'thinking_staff_' +staffId + '\');"/> &nbsp;&nbsp; ' + staffName + ' &nbsp;&nbsp; ' + agent + '</li>');
							 }else{
								 alert('<bean:message bundle="public" key="popup.employee.exists.main.position" />');
							 }
						 }
					});
 				}
 			}else{
 				alert('<bean:message bundle="public" key="popup.already.exists" />');
 			}
 			$('.thinking_staffType').val("0");
       		$(".thinking_staffName" ).val('');
       		$(".thinking_staffId" ).val('');
       		$(".wdate_beginTime" ).val('');
       		$(".wdate_endTime" ).val('');
       		$('.thinking_staffName').trigger("blur");
 		}
 		changeStaffType();
    };
    
 	// 添加规则 - Add by Kevin
	function addRule() {
 		if( $('.combo_ruleTypeId').val() != '0' && $('.combo_ruleId').val() != '0'){
 			var exist = false;
 			
 			$('input[id^="ruleIdArray_account"]').each(function(i) {
 				if( $(this).val().indexOf($('.combo_ruleTypeId' ).val() + '_') >= 0 ){
 					exist = true;
 				}
       		});
 			
 			$('input[id^="ruleIdArray"]').each(function(i) {
 				if( $(this).val().indexOf($('.combo_ruleTypeId' ).val() + '_') >= 0 ){
 					exist = true;
 				}
       		});
 			
 			if(!exist){	
	 			$('#combo_rule').append('<li id="rule_' + $('.combo_ruleId' ).val() + '" style="margin: 2px 0px;"><input type="hidden" id="ruleIdArray" name="ruleIdArray" value="' + $('.combo_ruleTypeId' ).val() + '_' + $('.combo_ruleId' ).val() + '"><img src="images/warning-btn.png" onclick="removeRule(\'rule_' + $('.combo_ruleId' ).val() + '\');"/> &nbsp;&nbsp; ' + $('#option_ruleTypeId_' + $('.combo_ruleTypeId').val() ).html() + ' - ' + $('#option_ruleId_' + $('.combo_ruleId').val() ).html() + '</li>');
 			}else{
 				alert('<bean:message bundle="public" key="popup.already.exists" />');
 			}
 			
 		}else{
 			alert('<bean:message bundle="public" key="popup.not.input.required.information" />');
 		}
    };
    var removeStaffMSG = null;
	// 移除员工事件 - Add b1y Kevin
	function removeStaff(id){
		// 判断职位上是否还有未审批的工作流？
		var positionId = '<bean:write name="positionForm" property="positionId" />';
		if(removeStaffMSG==null){
			$.post("workflowActualAction.do?proc=validateRemovePosition_ajax",{"positionId":positionId},function(data){
				if(data.size>0){
					removeStaffMSG = data.message;
				}else{
					removeStaffMSG = '<bean:message bundle="public" key="popup.confirm.delete" />';
				}
				if(confirm(removeStaffMSG)){
					$('#' + id).remove();
				}
			},"json");
		}else{
			if(confirm(removeStaffMSG)){
				$('#' + id).remove();
			}
		}
	}; 
	
	// 移除规则事件 - Add by Kevin
	function removeRule(id){
		if(confirm('<bean:message bundle="public" key="popup.confirm.delete" />')){
			$('#' + id).remove();
		}
	}; 
	
	// 切换员工类型
	function changeStaffType() {
		if($('.thinking_staffType').val() == '1'||$('.thinking_staffType').val()=='0'){
			$('.wdate_beginTime').hide();
			$('.wdate_endTime').hide();
		}else{
			$('.wdate_beginTime').show();
			$('.wdate_endTime').show();
		}
	};
	
	// 切换规则类型
	function changeRuleType(moduleId) {
		loadHtml('.rulecombo', 'ruleAction.do?proc=list_object_html_select&moduleId=' + moduleId + '&ruleType=' + $('.combo_ruleTypeId').val(), false);
	};
	
	// 点击Module树节点
	function manageModule(element, positionId, moduleId){
		var disable = false;
		
		if($('.subAction').val() == 'viewObject'){
			disable = true;
		}else{
			// 模态框moduleId 赋值
			$('.module_form input[id=moduleId]').val(moduleId);
			// 当点击模块，加载Right和Rule的模态框
			loadHtmlWithRecall('#rightAndRuleDiv', 'moduleAction.do?proc=list_authority_combo_position_ajax&positionId=' + positionId + '&moduleId=' + moduleId, disable, showPopup());
		}
		
		if(positionId == null || positionId == ''){
			positionId = $('#positionId_ajax_sendback').html();
		}
		
		$('input[type^="hidden"]#moduleIdArray').val(moduleId);
		
		($("a[id='manageModule']")).each(function(i) {
			$(this).removeClass('current');
		});
		$(element).addClass('current');
	};

	function bindThinkingToStaffName(){
		// Use the common thinking
		kanThinking_column('thinking_staffName', 'thinking_staffId', 'staffAction.do?proc=list_object_json&staffType='+$("#staffType").val());
	};
	
	function bindThinkingToParentPositionName(){
		// Use the common thinking
		kanThinking_column('position_parentPositionName', 'position_parentPositionId', 'positionAction.do?proc=list_object_json');
	};
	
	// 保存规则权限
	function saveRightRule(){
		submitForm('module_form', 'modifyObject', null, null, null, '', 'positionAction.do?proc=modify_object_ajax_popup', 'hidePopup();checkImg();enableBtn();', true);
	}
	
	function createEmployee(){
		// 页面修改数据保存提醒
		if(!confirm('<bean:message bundle="public" key="popup.leave.and.save.current.page" />')){
			return false;
		}
		
		$("#forwardURL").val("<%= BaseAction.getRole(request, response).equals(KANConstants.ROLE_IN_HOUSE) ? "employeeAction.do?proc=to_objectNew" : "staffAction.do?proc=to_objectNew" %>");
		
		// Enable the form
		enableForm('position_form');
		submit('position_form');
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
 			if($('#positionTreeDiv').is(":visible")==true){
 				doHighLignt($('#positionTreeDiv'),searchText);
 			}
 			if($('#moduleTreeDiv').is(":visible")==true){
 				doHighLignt($('#moduleTreeDiv'),searchText);
 			}
        }

    };
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
	};
	
</script>