<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.actions.management.CommercialBenefitSolutionDetailAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="commercialBenefitSolutionHeader - information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.cb.solution" /></label>
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
				<logic:empty name="commercialBenefitSolutionHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditCommercialBenefitSolutionHeader" id="btnEditCommercialBenefitSolutionHeader" value="<bean:message bundle="public" key="button.edit" />" />
				</logic:empty>
				<logic:notEmpty name="commercialBenefitSolutionHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=CommercialBenefitSolutionDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditCommercialBenefitSolutionHeader" id="btnEditCommercialBenefitSolutionHeader" value="<bean:message bundle="public" key="button.edit" />" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=CommercialBenefitSolutionDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnCancelCommercialBenefitSolutionHeader" id="btnCancelCommercialBenefitSolutionHeader" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/management/commercialBenefitSolution/header/form/manageCommercialBenefitSolutionHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- CommercialBenefitSolutionDetail - information -->
	<div class="box" id="CommercialBenefitSolutionDetail-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.cb.solution.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=CommercialBenefitSolutionDetailAction.accessAction%>">					
					<input type="button" class="editbutton" name="btnEditCommercialBenefitSolutionDetail" id="btnEditCommercialBenefitSolutionDetail" value="<bean:message bundle="public" key="button.add" />" /> 
					<input type="button" class="reset" name="btnCancelCommercialBenefitSolutionDetail" id="btnCancelCommercialBenefitSolutionDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listCommercialBenefitSolutionDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>	
			<div id="detailFormWrapper" style="display:none" >
				<!-- Include Form JSP 包含Form对应的jsp文件 --> 
				<jsp:include page="/contents/management/commercialBenefitSolution/detail/form/manageCommercialBenenfitSolutionDetailForm.jsp" flush="true"/>
			</div>		
			<!-- if exist bean commercialBenefitSolutionDetailHolder -->	
			<logic:notEmpty name="commercialBenefitSolutionDetailHolder">													
				<html:form action="commercialBenefitSolutionDetailAction.do?proc=list_object" styleClass="listCommercialBenefitSolutionDetail_form">
					<input type="hidden" name="id" value="<bean:write name="commercialBenefitSolutionHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="commercialBenefitSolutionDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="commercialBenefitSolutionDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="commercialBenefitSolutionDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="commercialBenefitSolutionDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp 包含tabel对应的jsp文件 -->  
					<jsp:include page="/contents/management/commercialBenefitSolution/detail/table/listCommercialBenefitSolutionDetailTable.jsp" flush="true"/>
				</div>
			</logic:notEmpty>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->		
</div>
<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式，如果当前用户是Super
		if('<bean:write name="accountId"/>' == '1'){
			$('#menu_system_Modules').addClass('current');
			$('#menu_system_CB').addClass('selected');
		}else{
			$('#menu_cb_Modules').addClass('current');			
			$('#menu_cb_Configuration').addClass('selected');
			$('#menu_cb_Solution').addClass('selected');
		}
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		// 如果当前是系统商保方案并且当前用户非Super，隐藏按钮。 
		if('<bean:write name="commercialBenefitSolutionHeaderForm" property="accountId" />' == '1' && '<bean:write name="accountId" />' != '1'){
			$('#btnEditCommercialBenefitSolutionHeader').hide();
			$('#btnEditCommercialBenefitSolutionDetail').hide();
			$('#btnDelete').hide();
		}
		
		// 编辑按钮点击事件 - CommercialBenefitSolution Header
		$('#btnEditCommercialBenefitSolutionHeader').click(function(){
			if( getSubAction() == 'viewObject'){  
				// Enable整个Form
        		enableForm('manageCommercialBenefitSolutionHeader_form');
        		// 设置当前Form的SubAction为修改状态
        		$('.manageCommercialBenefitSolutionHeader_form input#subAction').val('modifyObject'); 
        		// 更改Form Action
        		$('.manageCommercialBenefitSolutionHeader_form').attr('action', 'commercialBenefitSolutionHeaderAction.do?proc=modify_object');
        		// 修改按钮显示名称
        		$('#btnEditCommercialBenefitSolutionHeader').val('<bean:message bundle="public" key="button.save" />');
        		// 修改Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.cb.solution" /> <bean:message bundle="public" key="oper.edit" />');
        		// 上传链接生效
				$('#uploadAttachment').show();
				// Enable删除小图标
	   			$('img[id^=warning_img]').each(function(i){
	   				$(this).show();
	   			});
	   			$('img[id^=disable_img]').each(function(i){
	   				$(this).hide();
	   			});
			}else{
				if( validate_manage_primary_form() == 0){
					submit('manageCommercialBenefitSolutionHeader_form');
				}
			}
		});
		
		// 编辑按钮点击事件 - CommercialBenefitSolution Detail
		$('#btnEditCommercialBenefitSolutionDetail').click(function(){	
			// 判断是添加、查看还是修改 
			if($('.manageCommercialBenefitSolutionDetail_form input#subAction').val() == ''){
				// 显示Cancel按钮
				$('#btnCancelCommercialBenefitSolutionDetail').show();
				// 显示List Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.manageCommercialBenefitSolutionDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnEditCommercialBenefitSolutionDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.manageCommercialBenefitSolutionDetail_form input#subAction').val() == 'viewObject'){	
				// 编辑操作，Enable整个Form
				enableForm('manageCommercialBenefitSolutionDetail_form');
				// 设置SubAction为编辑
				$('.manageCommercialBenefitSolutionDetail_form input#subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnEditCommercialBenefitSolutionDetail').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.manageCommercialBenefitSolutionDetail_form').attr('action', 'commercialBenefitSolutionDetailAction.do?proc=modify_object');
        		// 科目下拉框依旧Disable 
        		//$('.manageCommercialBenefitSolutionDetail_itemId').attr('disabled','disabled');	
			}else{
				if( validate_manage_secondary_form() == 0 ){
					submit('manageCommercialBenefitSolutionDetail_form');
				}
			}
		});
		
		// Header 列表
		$('#btnCancelCommercialBenefitSolutionHeader').click( function () {
			if (agreest()) {
				link('commercialBenefitSolutionHeaderAction.do?proc=list_object');
			}
		});	
		
		// Detail 取消
		$('#btnCancelCommercialBenefitSolutionDetail').click( function () {
			if(agreest())
			link('commercialBenefitSolutionDetailAction.do?proc=list_object&id=<bean:write name="commercialBenefitSolutionHeaderForm" property="encodedId"/>');
		});
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageCommercialBenefitSolutionHeader_form');
			$('#CommercialBenefitSolutionDetail-Information').show();
			$('.manageCommercialBenefitSolutionHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="management" key="management.cb.solution" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
		    $('#btnEditCommercialBenefitSolutionHeader').val('<bean:message bundle="public" key="button.save" />');
			$('#uploadAttachment').show();
		}
		
		var uploadObject = createUploadObject('uploadAttachment', 'common', '<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_CB %>/<bean:write name="accountId" />/<bean:write name="username" />/');
	})(jQuery);
	
	// 点击超链接，ajax调用去修改页面
	function commercialBenefitSolutionDetailModify( detailId ){
		// 显示Cancel按钮
		$('#btnCancelCommercialBenefitSolutionDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// Ajax加载CommercialBenefitSolution Detail修改页面
		loadHtml('#detailFormWrapper', 'commercialBenefitSolutionDetailAction.do?proc=to_objectModify_ajax&detailId=' + detailId, true );
		// 修改按钮显示名称
		$('#btnEditCommercialBenefitSolutionDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageCommercialBenefitSolutionHeader_form input#subAction').val();
	};
</script>