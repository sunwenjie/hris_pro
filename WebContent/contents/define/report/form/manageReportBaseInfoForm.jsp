<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.define.ReportHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

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
	<logic:empty name="reportHeaderForm" property="encodedId">
		 <input type="button" class="save" name="btnSaveStep1" id="btnSaveStep1" value="<bean:message bundle="public" key="button.save" />" />
	</logic:empty>
	<logic:notEmpty name="reportHeaderForm" property="encodedId">
		<kan:auth right="modify" action="<%=ReportHeaderAction.accessAction%>">
			 <input type="button" class="save" name="btnSaveStep1" id="btnSaveStep1" value="<bean:message bundle="public" key="button.save" />" />
		</kan:auth>
	</logic:notEmpty>
</div>
<html:form action="reportHeaderAction.do?proc=add_object" styleClass="manageReportHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="subAction" class="subAction" id="subAction" value="<bean:write name="reportHeaderForm" property="subAction" />"/>
	<input type="hidden" name="id" id="reportHeaderId" value="<bean:write name="reportHeaderForm" property="encodedId" />"/>
<%-- 	<input type="hidden" name="selectTableIds" id="selectTableIds" value="<bean:write name="reportHeaderForm" property="selectTableIds" />"/> --%>
	<!-- 用于记录子表 -->
<%-- 	<input type="hidden" name="tableRelations" id="tableRelations" value="<bean:write name="reportHeaderForm" property="tableRelations" />"/> --%>
    
	<input type="hidden" name="selectTablesJson" id="selectTablesJson" value="<bean:write name="reportHeaderForm" property="selectTablesJson" />"/>
	<input type="hidden" name="unSelectTablesJson" id="unSelectTablesJson" value="<bean:write name="reportHeaderForm" property="unSelectTablesJson" />"/>
	
	<fieldset>
		<ol class="auto">
			<li class="required">
				<label><em>* </em><bean:message bundle="public" key="required.field" /></label>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.report.header.select.table" /><em> *</em></label> 
				<html:select property="tableId" styleClass="manageReportHeader_tableId">
					 <html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li class="myli">
				<label><bean:message bundle="define" key="define.report.sub" /><img src="images/tips.png" title="<bean:message bundle="define" key="define.report.double.click.select" />" /></label> 
				<div class="mydiv">
					<div class="mydiv_header"><bean:message bundle="define" key="define.report.header.selecting.sub.table" /></div>
					<div class="mydiv_body" ><ul id="unSelectTables"></ul></div>
					<div class="mydiv_foot"></div>
				</div>
				<div class="mydivblank"></div>
				<div class="mydiv">
					<div class="mydiv_header"><bean:message bundle="define" key="define.report.header.selected.sub.table" /></div>
					<div class="mydiv_body"><ul id="selectTables"></ul></div>
					<div class="mydiv_foot"></div>
				</div>
			</li>
		</ol>
		<ol class="auto">
			<li class="ajaxNameZH">
				<label><bean:message bundle="define" key="define.report.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100"  styleClass="manageReportHeader_nameZH" /> 
			</li>
			<li class="ajaxNameEN">
				<label><bean:message bundle="define" key="define.report.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100"  styleClass="manageReportHeader_nameEN" />
			</li>	
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.list.header.search.first" /></label> 
				<html:select property="isSearchFirst" styleClass="manageReportHeader_isSearchFirst">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>
			<li>
				<label><bean:message bundle="define" key="define.report.header.export.excel.type" /></label> 
				<html:select property="exportExcelType" styleClass="manageReportHeader_exportExcelType">
					<html:optionsCollection property="exportExcelTypes" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>
			<li style="display: none;">
				<label>导出PDF</label> 
				<html:select property="isExportPDF" styleClass="manageReportHeader_isExportPDF">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.list.header.use.pagination" /></label>
				<html:checkbox property="usePagination" styleClass="manageReportHeader_usePagination" value="on" />	
			</li>
		</ol>	
		<ol id="paginationDetailOl" class="auto">
			<li>
				<label><bean:message bundle="define" key="define.list.header.page.size" /><em> *</em></label> 
				<html:text property="pageSize" maxlength="2" styleClass="manageReportHeader_pageSize" />
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.header.load.pages" /></label> 
				<html:text property="loadPages" maxlength="1" styleClass="manageReportHeader_loadPages" />
			</li>
		</ol>	
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" cols="3" styleClass="manageReportHeader_description"></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageReportHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
    var language = 'zh';
    language = '<%=request.getLocale().getLanguage()%>';
	var selectTables = "";
	var unSelectTables = "";
	(function($) {
		// 初始页面子表选择
		var subAction = $('.manageReportHeader_form #subAction').val();
		
		if( subAction == 'viewObject' ){
			//修改初始页面数据
			initData();
			disableUl("manageReportHeader_form");
		}
			
		
		// 选择表/视图change事件
		$('.manageReportHeader_tableId').change(function(){
			$.ajax({
				url:"reportHeaderAction.do?proc=tableId_change_ajax&tableId=" + $(this).val(),
				dataType:"json",
				success:function(data){
					if(data.success=='true'){
						$('.manageReportHeader_nameZH').val( data.nameZH );
						//未选择子视图赋值unSelectTables
						//清空右边框
						clearUl("selectTables");
						//左边框赋值初始化
						unSelectTables = data.unSelectTables;
						$("#unSelectTablesJson").val(JSON.stringify(data.unSelectTables));
						selectTables = new Array();
						initSelectTable(data.unSelectTables,"unSelectTables","toRight");
					}else{
						$('.manageReportHeader_nameZH').val('');
					}
				}
			});
		});
	})(jQuery);
	
	//清空table
	function clearUl(id){
	  $("#"+id).empty();
	}


	// 是否分页
	function switchPaginationDetailOl(){
		if($('.manageReportHeader_usePagination').is(':checked')) {
        	$('#paginationDetailOl').show();
        } else {
        	$('#paginationDetailOl').hide();
        }
	};
	//插入table 下 的 tr  data 数据，ulId 插入目标的tableid,ondbliclickfn 绑定的双击事件,tableId tableId
	function addLi(data,ulId,ondbliclickFn,tableId){
		var ul=document.getElementById(ulId);
		var li= document.createElement("li");
		ul.appendChild(li);
		li.setAttribute("ondblclick",ondbliclickFn+"(this)" );
		li.setAttribute("tableId",tableId );
		li.setAttribute("title",'<bean:message bundle="define" key="define.report.double.click.select" />' );
		li.innerHTML = data;
	}
	//初始table
	function initSelectTable(list,ulId,ondbliclickFn){
		clearUl(ulId);
		var tbn = null;
		for(var l in list){ 
			tbn = language=='en' ? list[l].slaveTableNameEN: list[l].slaveTableNameZH;
			addLi(tbn,ulId,ondbliclickFn,list[l].tableRelationId);
		}
	}
	
	
	//子表操作  unselect to select
	function toRight(li){
		//if( $('#btnSaveStep1').val()=="编辑") return ;
		li.parentNode.removeChild(li);
		//记录选择的子表的id
		var tableId = li.getAttribute("tableId");
		var text = "";
 		for(var l in unSelectTables){
			if(unSelectTables[l].tableRelationId==tableId){
 				text = language=='en' ? unSelectTables[l].slaveTableNameEN : unSelectTables[l].slaveTableNameZH;
 				selectTables.push(unSelectTables[l]);
 				unSelectTables.splice(l,1);
			}
		}
 		//更新提交数据
		$("#unSelectTablesJson").val(JSON.stringify(unSelectTables));
		$("#selectTablesJson").val(JSON.stringify(selectTables));
		
		addLi(text,"selectTables","toLeft",li.getAttribute("tableId"));
	}
	//子表操作  select to unsel ect
	function toLeft(li){
		//if( $('#btnSaveStep1').val()=="编辑") return ;
		//记录选择的子表的id
		var tableId = li.getAttribute("tableId");
		var text = "";
 		for(var l in selectTables){
			if(selectTables[l].tableRelationId==tableId){
				text = language=='en' ? unSelectTables[l].slaveTableNameEN : unSelectTables[l].slaveTableNameZH;
 				unSelectTables.push(selectTables[l]);
 				selectTables.splice(l,1);
			}
		}
		$("#unSelectTablesJson").val(JSON.stringify(unSelectTables));
		$("#selectTablesJson").val(JSON.stringify(selectTables));
		
		addLi(text,"unSelectTables","toRight",li.getAttribute("tableId"));
		li.parentNode.removeChild(li);
		
	}
	
	//修改时初始页面数据
	function initData(){
		selectTables = $("#selectTablesJson").val();
		unSelectTables = $("#unSelectTablesJson").val();
		if(selectTables==null||selectTables==''){
			selectTables = new Array();
		}else{
			selectTables = JSON.parse(selectTables);
		}
		if(unSelectTables==null||unSelectTables==''){
			unSelectTables = new Array();
		}else{
			unSelectTables = JSON.parse(unSelectTables);
		}
		
		initSelectTable(unSelectTables,"unSelectTables","toRight");
		initSelectTable(selectTables,"selectTables","toLeft");
	}
</script>
