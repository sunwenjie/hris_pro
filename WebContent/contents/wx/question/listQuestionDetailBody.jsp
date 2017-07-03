<%@page import="com.kan.wx.web.actions.QuestionHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="QuestionHeader-Information">
		<div class="head">
			<label id="pageTitle">Q & A</label>
			<logic:notEmpty name="questionHeaderForm" property="headerId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="questionHeaderForm" property="headerId" />)</label>
	        </logic:notEmpty>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE_HEADER">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<logic:empty name="questionHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditQuestionHeader" id="btnEditQuestionHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="questionHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=QuestionHeaderAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditQuestionHeader" id="btnEditQuestionHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=QuestionHeaderAction.accessAction%>">
					<input type="button" class="reset" name="btnListQuestionHeader" id="btnListQuestionHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/wx/question/form/manageQuestionHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<div class="box" id="questionDetail-Information" style="display: none;">	
		<!-- Inner -->
		<div class="head">
			<label>Option</label>
		</div>
		<div class="inner">	
			<div id="messageWrapper">
				<logic:present name="MESSAGE_DETAIL">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=QuestionHeaderAction.accessAction%>">
					<input type="button" class="editbutton" id="btnEditQuestionDetail" name="btnEditQuestionDetail" value="<bean:message bundle="public" key="button.add" />"  />
					<input type="button" class="reset" name="btnCancelQuestionDetail" id="btnCancelQuestionDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listQuestionDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>
			
			<div id="detailFormWrapper" style="display: none;">
				<jsp:include page="/contents/wx/question/form/manageQuesitonDetailForm.jsp" flush="true"/> 
			</div>
			<!-- if exist bean questionDetailHolder -->
			<logic:notEmpty name="questionDetailHolder">
				<html:form action="questionDetailAction.do?proc=list_object" styleClass="listQuestionDetail_form">
					<fieldset>		
						<input type="hidden" name="id" value="<bean:write name="questionHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="questionDetailHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="questionDetailHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="questionDetailHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="questionDetailHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp 包含table对应的jsp文件 -->  
					<jsp:include page="/contents/wx/question/table/listQuestionDetailTable.jsp" flush="true"/> 
				</div>
			</logic:notEmpty>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
		<!-- Inner End-->
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_interaction_Modules').addClass('current');
		$('#menu_interaction_QA').addClass('selected');
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		 // 编辑按钮点击事件 - List Header
		$('#btnEditQuestionHeader').click(function(){
			if( getSubAction() == 'viewObject' ){  
				// Enable整个Form
        		enableForm('manageQuestionHeader_form');
        		// 设置当前Form的SubAction为修改状态
        		$('.manageQuestionHeader_form input#subAction').val('modifyObject'); 
        		// 更改Form Action
        		$('.manageQuestionHeader_form').attr('action', 'questionHeaderAction.do?proc=modify_object');
        		// 修改按钮显示名称
        		$(this).val('<bean:message bundle="public" key="button.save" />');
			}else{
    			if( validate_manage_primary_form() == 0 ){
    				enableForm('manageQuestionHeader_form');
    				submit('manageQuestionHeader_form');
    			}
			}
		});
		 
		$('#btnEditQuestionDetail').click(function(){
			var detailSubAction = $('.manageQuestionDetail_form input#subAction').val();
			
			// 如果是添加添加 
			if( detailSubAction == '' ){
				// 显示Cancel按钮
				$('#btnCancelQuestionDetail').show();
				// 显示List Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.manageQuestionDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$(this).val('<bean:message bundle="public" key="button.save" />');
			}
			// 查看
			else if( detailSubAction == 'viewObject'){
				// 编辑操作，Enable整个Form
				enableForm('manageQuestionDetail_form');
				// 修改按钮显示名称
				$(this).val('<bean:message bundle="public" key="button.save" />');
				// 设置SubAction为编辑
				$('.manageQuestionDetail_form input#subAction').val('modifyObject');
				// 更改Form Action
				$('.manageQuestionDetail_form').attr('action', 'questionDetailAction.do?proc=modify_object');
			}
			// 编辑
			else{
				// 通过JS验证，提交FORM
				if( validate_manage_secondary_form() == 0){
					enableForm('manageQuestionDetail_form');
					submit('manageQuestionDetail_form');
				}
			}
		});
		
		// 列表按钮点击事件 - Question Header 
		$('#btnListQuestionHeader').click( function () {
			if (agreest())
			link('questionHeaderAction.do?proc=list_object');
		});
		
		// 取消按钮点击事件 - Question Detail
		$('#btnCancelQuestionDetail').click(function(){
			if(agreest())
			link('quesitionDetailAction.do?proc=list_object&id=<bean:write name="questionHeaderForm" property="encodedId"/>');
		});	
		 
		if( getSubAction() != 'createObject' ) {
			disableForm('manageQuestionHeader_form');
			$('#questionDetail-Information').show();
			$('.manageQuestionHeader_form input#subAction').val('viewObject');
		} else if ( getSubAction() == 'createObject' ) {
		    $('#btnEditQuestionHeader').val('<bean:message bundle="public" key="button.save" />');
		}
	})(jQuery);
	
	// 点击超链接，ajax调用去修改页面
	function questionDetailModify( detailId ){
		// 显示Cancel按钮
		$('#btnCancelQuestionDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// 修改按钮显示名称
		$('#btnEditQuestionDetail').val('<bean:message bundle="public" key="button.edit" />');
		loadHtmlWithRecall('#detailFormWrapper', 'questionDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true, null);
	};
	
	// 当前是否需要Disable
	function getDisable(){
		return getSubAction() == 'viewObject' ? true : false;
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageQuestionHeader_form input#subAction').val();
	};
</script>