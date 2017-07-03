<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.MappingHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%String accession = (String)request.getAttribute("authAccessAction");%>
<div id="content">
	<div class="box" id="mappingHeader-information">
		<div class="head">
			<label id="pageTitle"></label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<logic:present name="MESSAGE_HEADER">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<logic:empty name="mappingHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditMappingHeader" id="btnEditMappingHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="mappingHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=accession%>">
						<input type="button" class="editbutton" name="btnEditMappingHeader" id="btnEditMappingHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=accession%>">
					<input type="button" class="reset" name="btnListMappingHeader" id="btnListMappingHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/define/mapping/header/form/manageMappingHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- MappingHeader-information -->
	<div class="box" id="mappingDetail-information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label><%=request.getAttribute( "flag" ).equals( "import" ) ? "导入" : "导出" %>字段</label>
		</div>		
		<div class="inner">	
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<logic:present name="MESSAGE_DETAIL">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">	
				<kan:auth right="modify" action="<%=accession%>">
					<input type="button" class="editbutton" name="btnEditMappingDetail" id="btnEditMappingDetail" value="<bean:message bundle="public" key="button.add" />" /> 
					<input type="button" class="reset" name="btnCancelMappingDetail" id="btnCancelMappingDetail" value="取消" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listmappingDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />		
				</kan:auth>			
			</div>	
			<!-- MappingDetail - information -->	
			<!-- if exits bean mappingDetailHolder -->
			<logic:notEmpty name="mappingDetailHolder">
				<div id="detailFormWrapper" style="display: none" >
					<!-- Include Form JSP 包含Form对应的jsp文件 -->  
					<jsp:include page="/contents/define/mapping/detail/form/manageMappingDetailForm.jsp" flush="true"/>
				</div>																
				<html:form action="mappingDetailAction.do?proc=list_object" styleClass="listmappingDetail_form">
					<input type="hidden" name="id" value="<bean:write name="mappingHeaderForm" property="encodedId"/>"/>			
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="mappingDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="mappingDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="mappingDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="mappingDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp 包含tabel对应的jsp文件 -->  
					<jsp:include page="/contents/define/mapping/detail/table/listMappingDetailTable.jsp" flush="true"/>
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
		// 初始化菜单
		$('#menu_client_Modules').addClass('current');	
		
		var pageTitle = "";
		if("<bean:write name="flag"/>" == "import"){
			$('#menu_client_Import').addClass('selected');
			pageTitle = "导入"
		}else{
			$('#menu_client_Export').addClass('selected');
			pageTitle = "导出";
		}
		
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		// 编辑按钮点击事件 - Mapping Header			
        $('#btnEditMappingHeader').click(function(){
        	if( getSubAction() == 'viewObject'){  
        		// 编辑操作，Enable整个Form
        		enableForm('mappingHeader_form');
        		// 设置当前Form的SubAction为修改状态
        		$('.mappingHeader_form input#subAction').val('modifyObject'); 
        		// 更改Form Action
        		$('.mappingHeader_form').attr('action', 'mappingHeaderAction.do?proc=modify_object&flag=<bean:write name='flag' />');
        		// 修改按钮显示名称
        		$('#btnEditMappingHeader').val('保存');
        		// 修改Page Title
        		$('#pageTitle').html( pageTitle + '编辑');
        	}else{
    			
    			if( validate_manage_primary_form() == 0 ){
    				submit('mappingHeader_form');
    			}
        	}
        });
    	    	
		// 编辑按钮点击事件 - Option Detail
		$('#btnEditMappingDetail').click(function(){
			// 判断是添加、查看还是修改
			if($('.mappingDetail_form input#subAction').val() == ''){
				// 显示Cancel按钮
				$('#btnCancelMappingDetail').show();
				// 显示Option Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.mappingDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnEditMappingDetail').val('保存');
			}else if($('.mappingDetail_form input#subAction').val() == 'viewObject'){
				// 编辑操作，Enable整个Form
				enableForm('mappingDetail_form');
				// 设置SubAction为编辑
				$('.mappingDetail_form input#subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnEditMappingDetail').val('保存');
				// 更改Form Action
        		$('.mappingDetail_form').attr('action', 'mappingDetailAction.do?proc=modify_object&flag=<bean:write name='flag' />');
			}else{
					
				if( validate_manage_secondary_form() == 0 ){
					submit('mappingDetail_form');
				}
			}
		});
		
		// 列表按钮点击事件 - Mapping Header
		$('#btnListMappingHeader').click( function () {
			if(agreest())
			link('mappingHeaderAction.do?proc=list_object&flag=<bean:write name="flag" />'); 
		});	
		
		// 取消按钮点击事件 - Mapping Detail
		$('#btnCancelMappingDetail').click(function(){
			if(agreest())
			link('mappingDetailAction.do?proc=list_object&flag=<bean:write name="flag" />&id=<bean:write name="mappingHeaderForm" property="encodedId"/>');
		});	
		
		if( getSubAction() != 'createObject' ) {
			disableForm('mappingHeader_form');
			$('#mappingDetail-information').show();
			$('.mappingHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html( pageTitle + '查询');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html( pageTitle + '新增');
		    $('#btnEditMappingHeader').val('保存');
		}
	})(jQuery);
	
	// 点击超链接，ajax调用去修改页面
	function mappingDetailModify( mappingDetailId, columnId ){
		// 显示Cancel按钮
		$('#btnCancelMappingDetail').show();
		// 显示Option Detail Form
		$('#detailFormWrapper').show();
		var callback = "$('.manageMappingDetail_columnId').val('" +columnId+ "');";
		// Ajax加载MappingVO Detail修改页面
		loadHtml('#detailFormWrapper', 'mappingDetailAction.do?proc=to_objectModify_ajax&flag=<bean:write name='flag' />&mappingDetailId=' + mappingDetailId, true, callback);
		// 修改按钮显示名称
		$('#btnEditMappingDetail').val('编辑');		
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.mappingHeader_form input#subAction').val();
	};
</script>