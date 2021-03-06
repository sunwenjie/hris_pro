<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%
	PagedListHolder provinceHolder = (PagedListHolder)request.getAttribute("provinceHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="province-information">
		<div class="head">
			<label>国家</label>
		</div>
		<div class="inner">
			<html:form action="countryAction.do?proc=modify_object" styleClass="country_form">
			<%= BaseAction.addToken( request ) %>
				<fieldset>
					<ol>	
						<li>
							<label>国家名（中文）<em> *</em></label> 
							<html:text property="countryNameZH" maxlength="50" styleClass="country_countryNameZH" /> 
						</li>
						<li>
							<label>国家名（英文）<em> *</em></label> 
							<html:text property="countryNameEN" maxlength="50" styleClass="country_countryNameEN" /> 
						</li>
						<li>
							<label>国家编号<em> *</em></label> 
							<html:text property="countryNumber" maxlength="3" styleClass="country_countryNumber" />
						</li>
						<li>
							<label>国家编码（简写）<em> *</em></label> 
							<html:text property="countryCode" maxlength="2" styleClass="country_countryCode" />
						</li>
						<li>
							<label>国家编码（ISO3）<em> *</em></label> 
							<html:text property="countryISO3" maxlength="3" styleClass="country_countryISO3" />
						</li>
						<li>
							<label>状态  <em>*</em></label> 
							<html:select property="status" styleClass="country_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>					
						<li class="required"><label><em>* </em>Required field</label></li>
					</ol>
					<p>
						<input type="hidden" name="countryId" id="countryId" value="<bean:write name="countryForm" property="encodedId"/>"/>
						<input type="hidden" name="editAction" id="editAction" value="viewObject" />
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="编辑" /> 
						<input type="button" class="reset" name="btnCancel" id="btnCancel" value="取消" />
					</p>
				</fieldset>
			</html:form>
		</div>	
	</div>
		
	<!-- inner -->
	<div class="inner">
		<html:form action="provinceAction.do?proc=list_object" styleClass="listprovince_form">
			<input type="hidden" name="countryId" id="countryId" value="<bean:write name="countryForm" property="encodedId"/>"/>			
			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="provinceHolder" property="sortColumn" />" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="provinceHolder" property="sortOrder" />" />
			<input type="hidden" name="page" id="page" value="<bean:write name="provinceHolder" property="page" />" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="provinceHolder" property="selectedIds" />" />
			<input type="hidden" name="subAction" id="subAction" value="" />					
		</html:form>		
	</div>
	<!-- Country-information -->
	<div class="box" id="search-results">
		<div class="head">
			<label><bean:write name="countryForm" property="countryNameZH"/> &gt;&gt; 省份</label>
		</div>
		<!-- Inner -->
		<div class="inner">
			<div class="top">
				<!-- 先隐藏添加省份 -->
				<div id="provinceDiv" style="display:none" >
					<html:form action="provinceAction.do?proc=add_object" styleClass="province_form">
						<%= BaseAction.addToken( request ) %>
						<fieldset>
							<ol class="auto">						
								<li>
									<label>省份名（中文）<em> *</em></label> 
									<html:text property="provinceNameZH" maxlength="50" styleClass="province_provinceNameZH" />
									<input type="hidden" name="countryId" id="countryId" value="<bean:write name="countryForm" property="encodedId"/>"/>
								</li>
								<li>
									<label>省份名（英文）<em> *</em></label> 
									<html:text property="provinceNameEN" maxlength="50" styleClass="province_provinceNameEN" />
								</li>					
								<li>
									<label>状态  <em>*</em></label> 
									<html:select property="status" styleClass="province_status">
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
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />"  />
				<input type="button" class="reset" name="btnCancelProvince" id="btnCancelProvince" value="取消" style="display:none" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listprovince_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</div>
			<!-- top -->
			<div id="helpText" class="helpText"></div>
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/system/province/table/listProvinceTable.jsp" flush="true"/>
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
		
		// 将Form设为Disable
		disableForm('country_form');
		// 编辑按钮点击事件			
        $('#btnEdit').click(function(){
        	if($('#editAction').val() == 'viewObject'){   
        		enableForm('country_form');
        		$('#editAction').val('modifyObject');       		
        		$('#btnEdit').val('保存');
        	}else{
        		var flag = 0;
        		
        		flag = flag + validate("country_countryNameZH", true, "common", 50, 0);
    			flag = flag + validate("country_countryNameEN", true, "common", 50, 0);
    			flag = flag + validate("country_countryNumber", true, "numeric", 3, 0);
    			flag = flag + validate("country_countryCode", true, "character", 2, 0);
    			flag = flag + validate("country_countryISO3", true, "character", 3, 0);
    			flag = flag + validate("country_status", true, "common", 0, 0);
    			
    			if(flag == 0){
    				$('.country_form').submit();
    			}
        	}
        });
		
		//添加按钮点击事件
		$('#btnAdd').click(function(){
			if( $('#provinceDiv').is(":visible") ) {
				var flag = 0;
				
				flag = flag + validate("province_provinceNameZH", true, "common", 50, 0);
				flag = flag + validate("province_provinceNameEN", true, "common", 50, 0);
				flag = flag + validate("province_status", true, "common", 0, 0);
				
				if(flag == 0){
					$(".province_form").submit();
				}
			}else{
				//显示取消按钮及Province添加的界面
				$('#btnCancelProvince').show();
				$('#provinceDiv').show();
				$('#btnAdd').val('保存');
			}
		});
		
		//取消按钮点击事件
		$('#btnCancelProvince').click(function(){
			$('#provinceDiv').hide();
			$('#btnCancelProvince').hide();
			$('#btnAdd').val('添加');
		});
		
		// 省份按钮取消事件
		$("#btnCancel").click( function () {
			// 如果是编辑状态，Disable Form，修改Edit Action的值，修改按钮文字
			if($('#editAction').val() == 'modifyObject'){
				disableForm('country_form');
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