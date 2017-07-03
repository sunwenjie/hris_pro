<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.domain.define.ListHeaderVO"%>
<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.ListDetailAction"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.renders.define.ListDetailRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PagedListHolder listDetailHolder = (PagedListHolder)request.getAttribute("listDetailHolder");
%>

<div id="content">
	<div class="box" id="listDeatail-information">
		<div class="head">
			<label id="pageTitle"></label>
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
				<logic:empty name="listHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditListHeader" id="btnEditListHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="listHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ListDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditListHeader" id="btnEditListHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ListDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnListListHeader" id="btnListListHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
				<logic:equal name="listHeaderForm" property="subAction" value="viewObject">
					<logic:equal name="listHeaderForm" property="useJavaObject" value="on">
						<a id="quickColumnIndex" name="quickColumnIndex" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.quick.set.list.sequence" />" onclick="alert('<bean:message bundle="public" key="popup.img.quick.set.list.sequence" />');"><img src="images/trans.png" /></a>
					</logic:equal>
					<logic:notEqual name="listHeaderForm" property="useJavaObject" value="on">
						<a id="quickColumnIndex" name="quickColumnIndex" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.quick.set.list.sequence" />" onclick="popupIndexQuick();";><img src="images/trans.png" /></a>
					</logic:notEqual>
				</logic:equal>
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/define/list/header/form/manageListHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- ListHeader-information -->
	<div class="box" id="listDetail-information" style="display: none;">	
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="define" key="define.list.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=ListDetailAction.accessAction%>">
					<input type="button" class="editbutton" id="btnEditListDetail" name="btnEditListDetail" value="<bean:message bundle="public" key="button.add" />"  />
					<input type="button" class="reset" name="btnCancelListDetail" id="btnCancelListDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listListDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>	
			<div id="detailFormWrapper" style="display:none">
				<jsp:include page="/contents/define/list/detail/form/manageListDetailForm.jsp" flush="true"/> 
			</div>																	
			<!-- ListnDetail - information -->	
			<!-- if exits bean listDetailHolder -->
			<logic:notEmpty name="listDetailHolder">
				<html:form action="listDetailAction.do?proc=list_object" styleClass="listListDetail_form">
					<fieldset>		
						<input type="hidden" name="listHeaderId" value="<bean:write name="listHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="listDetailHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="listDetailHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="listDetailHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="listDetailHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp 包含table对应的jsp文件 -->  
					<jsp:include page="/contents/define/list/detail/table/listListDetailTable.jsp" flush="true"/> 
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
<!-- 快速设置列表顺序popup -->
<div id="popupWrapper">
	<%
		final ListHeaderVO listHeaderVO = ( ListHeaderVO )request.getAttribute( "listHeaderForm" );
		if( listHeaderVO != null && KANUtil.filterEmpty( listHeaderVO.getUseJavaObject() ) == null )
		{
		   out.println( ListRender.generateQuickColumnIndexPopup( request, listHeaderVO.getListHeaderId(), "listDetailAction.do?proc=list_object&listHeaderId=" + listHeaderVO.getEncodedId() ) );
		}
	%>
</div>
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_define_Modules').addClass('current');			
		$('#menu_define_List').addClass('selected');
		
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		// 编辑按钮点击事件 - List Header
		$('#btnEditListHeader').click(function(){
			if($('.listHeader_form input#subAction').val() == 'viewObject'){  
				// Enable整个Form
        		enableForm('listHeader_form');
        		// 设置当前Form的SubAction为修改状态
        		$('.listHeader_form input#subAction').val('modifyObject'); 
        		// 更改Form Action
        		$('.listHeader_form').attr('action', 'listHeaderAction.do?proc=modify_object');
        		// 修改按钮显示名称
        		$('#btnEditListHeader').val('<bean:message bundle="public" key="button.save" />');
        		// 修改Page Title
        		$('#pageTitle').html('<bean:message bundle="define" key="define.list" /> <bean:message bundle="public" key="oper.edit" />');
        		// Table、UseJavaObject字段依旧Disable
        		$('.manageListHeader_tableId').attr('disabled','disabled');
        		$('.manageListHeader_useJavaObject').attr('disabled','disabled');
        		if( !$('.manageListHeader_useJavaObject').is(':checked') ){
        			$('.manageListHeader_parentId').attr('disabled','disabled');
        		}
			}else{
    			
    			if( validate_manage_primary_form() == 0){
    				enableForm('listHeader_form');
    				submit('listHeader_form');
    			}
			}
		});
		
		// 编辑按钮点击事件 - List Detail
		// Code reviewed by Kevin at 2013-07-08
		$('#btnEditListDetail').click(function(){
			// 判断是添加、查看还是修改
			if($('.listDetail_form input#subAction').val() == ''){
				// 显示Cancel按钮
				$('#btnCancelListDetail').show();
				// 显示List Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.listDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnEditListDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.listDetail_form input#subAction').val() == 'viewObject'){
				// Enable整个Form
				enableForm('listDetail_form');
				// 设置SubAction为编辑
				$('.listDetail_form input#subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnEditListDetail').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.listDetail_form').attr('action', 'listDetailAction.do?proc=modify_object');
				// 字段依旧Disable
        		$('.manageListDetail_columnId').attr('disabled','disabled');	
			}else{
				if( validate_manage_secondary_form() == 0 ){
					submit('listDetail_form');
				}
			}
		});
		
		// 列表按钮点击事件 - List Header 
		$('#btnListListHeader').click( function () {
			if (agreest())
			link('listHeaderAction.do?proc=list_object');
		});	
		
		// 取消按钮点击事件 - List Detail
		$('#btnCancelListDetail').click(function(){
			if (agreest())
			link('listDetailAction.do?proc=list_object&listHeaderId=<bean:write name="listHeaderForm" property="encodedId"/>');
		});	
		
		if( getSubAction() != 'createObject' ){
			disableForm('listHeader_form');
			$('#listDetail-information').show();
			$('#pageTitle').html('<bean:message bundle="define" key="define.list" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('<bean:message bundle="public" key="oper.new" /> <bean:message bundle="define" key="define.list" />');
		    $('#btnEditListHeader').val('<bean:message bundle="public" key="button.save" />');
		}
		
	})(jQuery);
	
	// 超链接OnChange事件
	function isLinkedChange(){
		if($('.manageListDetail_isLinked').val() == '1'){
			$('#linkedDetailOl').show();
		}else{
			$('#linkedDetailOl').hide();
		}
	};
	
	//点击List Detail 的链接，Ajax调用修改页面	
	// Code reviewed by Kevin at 2013-07-08
	function objectModify( listDetailId , columnId ){
		var callback = "$('.manageListDetail_columnId').val( '" + columnId + "');$('.manageListDetail_columnId').trigger('change');";
		loadHtmlWithRecall('#detailFormWrapper', 'listDetailAction.do?proc=to_objectModify_ajax&listDetailId=' + listDetailId, true, callback );
		// 显示Cancel按钮
		$('#btnCancelListDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// 设定SubAction值，区分Add和Modify
		$('.listDetail_form input#subAction').val('viewObject');
		// 修改按钮显示名称
		$('#btnEditListDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// Get SubAction
	function getSubAction(){
		return $('.listHeader_form input#subAction').val();
	};
</script>