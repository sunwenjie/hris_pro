<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%
	PagedListHolder cityHolder = (PagedListHolder)request.getAttribute("cityHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="city-information">
		<div class="head">
			<label>省份</label>
		</div>
		<div class="inner">
			<html:form action="provinceAction.do?proc=modify_object" styleClass="province_form">
				<fieldset>
					<ol>
						<li>
							<label>省份名（中文）<em> *</em></label> 
							<html:text property="provinceNameZH" maxlength="50" styleClass="province_provinceNameZH" /> 
						</li>
						<li>
							<label>省份名（英文）<em> *</em></label> 
							<html:text property="provinceNameEN" maxlength="50" styleClass="province_provinceNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
							<html:select property="status" styleClass="province_status">
								<logic:notEqual  name="mappingId" value="0">
									<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
								</logic:notEqual>
							</html:select>
						</li>						
						<li class="required"><label><em>* </em>Required field</label></li>
					</ol>
					<p>
						<input type="hidden" name="provinceId" id="provinceId" value="<bean:write name="provinceForm" property="encodedId"/>"/>
						<input type="hidden" name="editAction" id="editAction" value="viewObject" />
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="编辑" /> 
						<input type="button" class="reset" name="btnCancelProvince" id="btnCancelProvince" value="取消" />
						<%= BaseAction.addToken( request ) %>
					</p>
				</fieldset>
			</html:form>
		</div>	
	</div>
		
	<!-- inner -->
	<div class="inner">
		<html:form action="cityAction.do?proc=list_object" styleClass="listcity_form">
			<input type="hidden" name="provinceId" name="provinceId" value="<bean:write name="provinceForm" property="encodedId"/>"/>			
			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="cityHolder" property="sortColumn" />" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="cityHolder" property="sortOrder" />" />
			<input type="hidden" name="page" id="page" value="<bean:write name="cityHolder" property="page" />" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="cityHolder" property="selectedIds" />" />
			<input type="hidden" name="subAction" id="subAction" value="" />			
		</html:form>
	</div>
	<!-- Country-information -->
	<div class="box" id="search-results">
		<div class="head">
			<label><bean:write name="countryNameZH" /> &gt;&gt; <bean:write name="provinceForm" property="provinceNameZH"/> &gt;&gt; 城市</label>
		</div>
		<!-- Inner -->
		<div class="inner">
			<div class="top">
				<!-- 隐藏的添加城市 -->
				<div id="cityDiv" style="display:none" >
					<html:form action="cityAction.do?proc=add_object" styleClass="city_form">
						<%= BaseAction.addToken( request ) %>
						<fieldset>
							<ol class="auto">						
								<li>
									<label>城市名（中文）<em> *</em></label>
									<html:text property="cityNameZH" maxlength="50" styleClass="city_cityNameZH" />
									<input type="hidden" name="provinceId" id="provinceId" value="<bean:write name="provinceForm" property="encodedId"/>"/>
								</li>
								<li>
									<label>城市名（英文）<em> *</em></label> 
									<html:text property="cityNameEN" maxlength="50" styleClass="city_cityNameEN" />
								</li>
								<li>
									<label>城市编号<em> *</em></label> 
									<html:text property="cityCode" maxlength="4" styleClass="city_cityCode" />
								</li>
								<li>
									<label>城市编码（ISO3）<em> *</em></label> 
									<html:text property="cityISO3" maxlength="3" styleClass="city_cityISO3" />
								</li>					
								<li>
									<label>状态  <em>*</em></label> 
									<html:select property="status" styleClass="city_status">
										<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
									</html:select>
								</li>
							</ol>
							<ol>
								<li class="required"><label><em>* </em>Required field</label></li>
							</ol>
							<p/>
						</fieldset>
					</html:form>
				</div>
				<input type="hidden" name="saveAction" id="saveAction" value="createObject" />
				<input type="button" class="addbutton" name="btnAdd" id="btnAdd" value="<bean:message bundle="public" key="button.add" />" /> 
				<input type="button" class="reset" name="btnCancelCity" id="btnCancelCity" value="取消" style="display:none" />	
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listcity_form', 'deleteObjects', null, null, null, 'tableWrapper');" />	
		  	</div>
			<!-- top -->
			<div id="helpText" class="helpText"></div>
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/system/city/table/listCityTable.jsp" flush="true"/>
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
			<!-- frmList_ohrmListComponent -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->		
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_system_Modules').addClass('current');			
		$('#menu_system_City').addClass('selected');
		
		//隐藏FORM
		disableForm('province_form');
		
		// 编辑按钮点击事件			
        $('#btnEdit').click(function(){
        	if($('#editAction').val() == 'viewObject'){   
        		enableForm('province_form');
        		$('#editAction').val('modifyObject');       		
        		$('#btnEdit').val('保存');
        	}else{
        		var flag = 0;
    			
        		flag = flag + validate("province_provinceNameZH", true, "common", 50, 0);
        		flag = flag + validate("province_provinceNameEN", true, "common", 50, 0);
        		flag = flag + validate("province_status", true, "common", 0, 0);
        		
    			if(flag == 0){
    				$('.province_form').submit();
    			}
        	}
        });
		
		//添加按钮点击事件
		$('#btnAdd').click(function(){
			if( $('#cityDiv').is(":visible") ) {
				var flag = 0;
				
				flag = flag + validate("city_cityNameZH", true, "common", 50, 0);
				flag = flag + validate("city_cityNameEN", true, "common", 50, 0);
				flag = flag + validate("city_cityCode", false, "numeric", 4, 0);
				flag = flag + validate("city_cityISO3", false, "character", 3, 0);
				flag = flag + validate("city_status", true, "common", 0, 0);
				
				if(flag == 0){
					$(".city_form").submit();
				}
			}else{
				//显示取消按钮及Province添加的界面
				$('#btnCancelCity').show();
				$('#cityDiv').show();
				$('#btnAdd').val('保存');
			}
		});
		
		//取消按钮点击事件
		$('#btnCancelCity').click(function(){
			$('#cityDiv').hide();
			$('#btnCancelCity').hide();
			$('#btnAdd').val('添加');
		});
		
		// 省份按钮取消事件
		$("#btnCancelProvince").click( function () {
			// 如果是编辑状态，Disable Form，修改Edit Action的值，修改按钮文字
			if($('#editAction').val() == 'modifyObject'){
				disableForm('province_form');
				$('#editAction').val('viewObject');
				$('#btnEdit').val('编辑');
			}
			// 如果是查看状态直接返回
			else{
				back();
			}
		});
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
</script>