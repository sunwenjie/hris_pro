<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.hro.service.inf.biz.sb.SBHeaderService"%>
<%@page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@page import="com.kan.hro.domain.biz.vendor.VendorVO"%>

<%@page import="com.kan.hro.web.actions.biz.vendor.VendorContactAction"%>
<%@page import="com.kan.hro.web.actions.biz.vendor.VendorPaymentAction"%>
<%@page import="com.kan.hro.web.actions.biz.vendor.VendorServiceAction"%>
<%@page import="com.kan.hro.web.actions.biz.vendor.VendorAction"%>

<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final VendorVO vendorVO = (VendorVO) request.getAttribute("vendorForm");
	String vendorId = null;
	if( vendorVO != null && vendorVO.getVendorId() != null){
	   vendorId = vendorVO.getVendorId();
	}
	final String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";
%>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<logic:notEqual name='role' value='3'>
				<li id="tabMenu1" onClick="changeTab(1,4)" class="first hover"><bean:message bundle="public" key="menu.table.title.service" />(<span id="numberOfVendorService"><bean:write name="listVendorServiceCount"/></span>)</li>
				<kan:auth right="view" action="<%=VendorContactAction.accessAction %>">	
					<li id="tabMenu2" onClick="changeTab(2,4)"><bean:message bundle="public" key="menu.table.title.contact" />(<span id="numberOfVendorContact"><bean:write name="listVendorContactCount"/></span>)</li> 
				</kan:auth>
				<li id="tabMenu3" onClick="changeTab(3,4)"><bean:message bundle="public" key="menu.table.title.check.sheet" />(<span id="numberOfVendorPayment"><bean:write name="listVendorPaymentCount"/></span>)</li> 
				<li id="tabMenu4" onClick="changeTab(4,4)"><bean:message bundle="public" key="menu.table.title.attachment" />(<span id="numberOfAttachmentCount"><bean:write name="attachmentCount"/></span>)</li> 
			</logic:notEqual>
			<logic:equal name='role' value='3'>
				<kan:auth right="view" action="<%=VendorContactAction.accessAction %>">	
					<li id="tabMenu1" onClick="changeTab(1,3)" class="first hover"><bean:message bundle="public" key="menu.table.title.contact" />(<span id="numberOfVendorContact"><bean:write name="listVendorContactCount"/></span>)</li> 
				</kan:auth>
				<li id="tabMenu2" onClick="changeTab(2,3)"><bean:message bundle="public" key="menu.table.title.check.sheet" />(<span id="numberOfVendorPayment"><bean:write name="listVendorPaymentCount"/></span>)</li> 
				<li id="tabMenu3" onClick="changeTab(3,3)"><bean:message bundle="public" key="menu.table.title.attachment" />(<span id="numberOfAttachmentCount"><bean:write name="attachmentCount"/></span>)</li> 
			</logic:equal>
		</ul> 
	</div> 
	<div class="tabContent"> 
		<logic:notEqual name='role' value='3'>
			<div id="tabContent1" class="kantab kanThinkingCombo">
				<div class="no-head">
				</div>
				<div id="messageWrapper">
					<logic:present name="SUCCESS_MESSAGE_DETAIL">
						<div class="message success fadable">
							<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
							<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present> 
				</div>
				<div class="top">
					<kan:auth  right="modify" action="<%=VendorAction.accessAction %>" owner="<%=vendorVO.getOwner() %>">
						<input type="button" class="button" id="btnAddVendorService" name="btnAddVendorService" value="<bean:message bundle="public" key="button.add" />"  />
						<input type="button" class="reset" name="btnCancelVendorService" id="btnCancelVendorService" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
						<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listVendorService_form', 'deleteObjects', null, null, null, 'tableWrapper', null, removeVendorService());" /> 
					</kan:auth>
				</div>
				<!-- 添加服务form -->
				<div id="vendorServiceFormWrapper" style="display: none;">
					<jsp:include page="/contents/business/vendor/vendor/form/manageVendorService.jsp" flush="true"/>
				</div>
				<html:form action="vendorServiceAction.do?proc=list_object" styleClass="listVendorService_form">
					<input type="hidden" name="vendorId" value="<bean:write name="vendorForm" property="encodedId"/>"/>			
					<input type="hidden" name="selectedIds" id="selectedIds" value="" />
					<input type="hidden" name="subAction" id="subAction" value="" />							
				</html:form>
				<!--VendorService Information Table--> 
				<div id="tableWrapper">
					<jsp:include page="/contents/business/vendor/vendor/table/manageVendorServiceTable.jsp" flush="true"/>
				</div>
			</div> 
		</logic:notEqual>
		<form>
			<kan:auth right="view" action="<%=VendorContactAction.accessAction %>">	
				<div 
					<logic:equal name='role' value='3'>id="tabContent1"</logic:equal><logic:notEqual name='role' value='3'>id="tabContent2" style="display:none"</logic:notEqual> class="kantab" >
					<kan:auth  right="new" action="<%=VendorContactAction.accessAction %>" owner="<%=vendorVO.getOwner() %>">
						<span id="addVendorContactSpan"><a onclick="addExtraObject('vendorContactAction.do?proc=to_objectNew&type=return&vendorId=<%=vendorVO.getEncodedId() %>', '<%=vendorVO.getEncodedId() %>');" id="addVendorContact" class="kanhandle"><bean:message bundle="public" key="link.add.vendor.contact" /></a></span>
					</kan:auth>
					<ol class="auto" id="vendorContactOL">
						<logic:notEmpty name="vendorContactVOs">
							<logic:iterate id="vendorContactVO" name="vendorContactVOs" indexId="number">
								<li>
									<kan:auth right="delete" action="<%=VendorContactAction.accessAction %>" owner="<%=vendorVO.getOwner() %>">
										<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
										<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('vendorContactAction.do?proc=delete_object_ajax&vendorContactId=<bean:write name="vendorContactVO" property="encodedId" />',this,'#numberOfVendorContact');" src="images/warning-btn.png">
									</kan:auth>
									&nbsp;&nbsp;
									<kan:auth  right="modify" action="<%=VendorContactAction.accessAction %>" owner="<%=vendorVO.getOwner() %>">
										<a onclick="link('vendorContactAction.do?proc=to_objectModify&id=<bean:write name="vendorContactVO" property="encodedId" />&type=return');">
									</kan:auth>	
									<bean:write name="vendorContactVO" property="nameZH" />
									<kan:auth  right="modify" action="<%=VendorContactAction.accessAction %>" owner="<%=vendorVO.getOwner() %>">
										</a>
									</kan:auth>
								</li>
							</logic:iterate>
						</logic:notEmpty>
					</ol>
				</div>
			</kan:auth>
		</form>
		<div <logic:equal name='role' value='3'>id="tabContent2"</logic:equal><logic:notEqual name='role' value='3'>id="tabContent3"</logic:notEqual> class="kantab" style="display:none">
			<div class="box searchForm toggableTabForm toggableForm" id="sbHeader-information">
				<div class="head"><label><bean:message bundle="sb" key="sb.solution" /></label></div>
				<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
				<div id="searchDiv" class="inner hide">
					<div class="top">
						<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listHeader_form', 'searchObject', null, null, null, 'sbHeaderTableWrapper');" />
						<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
					</div>
					<html:form action="vendorPaymentAction.do?proc=list_object_ajax" styleClass="listHeader_form">	
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sbHeaderHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sbHeaderHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="sbHeaderHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sbHeaderHolder" property="selectedIds" />" />
						<input type="hidden" name="vendorId" id="vendorId" value="<bean:write name="sbHeaderForm" property="encodedVendorId" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />
						<input type="hidden" name="pageFlag" id="pageFlag" value="<%=SBHeaderService.PAGE_FLAG_HEADER %>" />
						<fieldset>
							<ol >
								<li>
									<label><bean:message bundle="sb" key="sb.monthly" /></label> 
									<html:select property="monthly" styleClass="searchSBHeader_monthly">
										<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
									</html:select>
								</li>
								<li>
									<label><bean:message bundle="sb" key="sb.solution" /></label>  
									<html:select property="sbSolutionId" styleClass="searchSBHeader_sbSolutionId">
										<html:optionsCollection property="socialBenefitSolutions" value="mappingId" label="mappingValue" />
									</html:select>
								</li>
								<logic:notEqual name="role" value="3">
									<logic:notEqual name="role" value="1">
										<li>
											<label><bean:message bundle="public" key="public.order2.id" /></label>
											<html:select property="orderId" styleClass="searchSBHeader_orderId">
												<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
											</html:select>
										</li>
									</logic:notEqual>
								</logic:notEqual>
								<li>
									<label>
										<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
										<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
										<logic:equal name="role" value="3"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
									</label>
									<span><html:text property="employeeId" styleClass="searchSBHeader_employeeId"></html:text></span>
								</li>
								<li>
									<label>
										<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
										<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
										<logic:equal name="role" value="3"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
									</label>
									<span><html:text property="employeeNameZH" styleClass="searchSBHeader_employeeNameZH"></html:text></span>
								</li>
								<li>
									<label>
										<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
										<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
										<logic:equal name="role" value="3"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
									</label>
									<span><html:text property="employeeNameEN" styleClass="searchSBHeader_employeeNameEN"></html:text></span>
								</li>
								<li>
									<label>
										<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.status" /></logic:equal>
										<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.status" /></logic:equal>
										<logic:equal name="role" value="3"><bean:message bundle="public" key="public.employee2.status" /></logic:equal>
									</label> 
									<html:select property="employStatus" styleClass="searchSBHeader_employStatus">
										<html:optionsCollection property="employStatuses" value="mappingId" label="mappingValue" />
									</html:select>
								</li>
								<li>
									<label><bean:message bundle="sb" key="sb.status" /></label> 
									<html:select property="sbStatus" styleClass="searchSBHeader_sbStatus">
										<html:optionsCollection property="sbStatuses" value="mappingId" label="mappingValue" />
									</html:select>
								</li>
								<li>
									<label><bean:message bundle="public" key="public.status" /></label> 
									<html:select property="additionalStatus" styleClass="searchSBHeader_additionalStatus">
										<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
									</html:select>
								</li>
							</ol>
						</fieldset>
					</html:form>
				</div>
			</div>
			<!-- SearchDiv End -->
			<div class="box noHeader" id="search-results">
				<div class="inner">
					<div class="top">
						<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
						<span>
							<a id="exportExcel" name="exportExcel" style="float:right !important;" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm('listHeader_form', 'downloadObjects', null, 'fileType=excel&javaObjectName=' + '<%=javaObjectName %>');">
								<img src="images/appicons/excel_16.png" />
								<logic:equal name="role" value="3">
									<img title="只能导出'可操作'状态数据" src="images/tips.png">
								</logic:equal>
							</a>
						</span>
					</div>
					<div id="sbHeaderTableWrapper" style="overflow:auto;">
						<jsp:include page="/contents/business/vendor/vendorPayment/table/listHeaderTableForTab.jsp"></jsp:include>          
					</div>
					<div class="bottom">
						<p>
					</div>
				</div>
			</div>
		</div>
		<div <logic:equal name='role' value='3'>id="tabContent3"</logic:equal><logic:notEqual name='role' value='3'>id="tabContent4"</logic:notEqual> class="kantab" style="display: none;">
			<span><a name="uploadAttachment" id="uploadAttachment" onclick="uploadObject.submit();"><bean:message bundle="public" key="link.upload.attachment" /></a></span>	
			<ol id="attachmentsOL" class="auto">
				<%= AttachmentRender.getAttachments(request, vendorVO.getAttachmentArray(), null) %>
			</ol>
		</div>
	</div>
</div>


<script type="text/javascript">
	/**
	* define  定义变量
	**/
	var vendorId = '<bean:write name="vendorForm" property="encodedId"/>';
	if($('.manage_primary_form #status').val() == '2' ){
		$('#btnAddVendorService').hide();
		$('#btnDelete').hide();
		display_a('vendorServiceLink');
	}
	
	// 查看模式
	if(getSubAction() == 'viewObject'){
		$('#uploadAttachment').attr("style", 'display:none;');
		display_a('addVendorContact');
	}

	// 搜索选项框显示隐藏控制
	if ($('.toggableTabForm .tiptip').is(':hidden')) {
		$('.toggableTabForm .tiptip').tipTip({
			content : '<bean:message key="public.openoptions"/>'
		});
	} else {
		$('.toggableTabForm .tiptip').tipTip({
			content : '<bean:message key="public.hideoptions"/>'
		});
	}

	function display_a( className ){
		$('#' + className ).attr('disabled', true);
		$('#' + className ).attr("onclick", '#' + $('#' + className ).attr("onclick") );
		$('#' + className ).addClass("disabled");
	};
	
	(function($){
		kanList_init();
		kanCheckbox_init();

		/**
		* bind 绑定事件
		**/

		/* Toggling search form: Begins */
		$(".toggableTabForm .toggle").click(function() {
			$(".toggableTabForm #searchDiv").slideToggle('slow', function() {
				if ($(this).is(':hidden')) {
					$('.toggableTabForm .tiptip').tipTip({
						content : '<bean:message key="public.openoptions"/>'
					});
				} else {
					$('.toggableTabForm .tiptip').tipTip({
						content : '<bean:message key="public.hideoptions"/>'
					});
				}
			});
			$(this).toggleClass("activated");
		});
		/* Toggling search form: Ends */
				
		// 继续筛选记录
		$('#filterRecords').click( function () {
			if(!$('#searchDiv').is(':hidden')){
				$('#filterRecords').html('<bean:message bundle="public" key="set.filerts" />');
			}else{
				$('#filterRecords').html('<bean:message bundle="public" key="close.filerts" />');
			}
			$('.tiptip').trigger("click");
		});
		
		// 绑定省份Change事件
		$('.manageVendorCityService_provinceId1').change( function () { 
			provinceChange('manageVendorCityService_provinceId1', 'modifyObject', 0, 'cityId1');
		});
		
		// 绑定添加服务Click事件
		$('#btnAddVendorService').click(function(){
			if( vendorId != null && vendorId != ''){
				if($('.manageVendorService_form .subAction').val() == ''){
					$('#btnAddVendorService').val('<bean:message bundle="public" key="button.save" />');
					$('#btnCancelVendorService').show();
					$('#vendorServiceFormWrapper').show();
					$('.manageVendorService_form .subAction').val('createObject');
					$('.manageVendorService_status').val('1');
				}else if($('.manageVendorService_form .subAction').val() == 'viewObject'){
					// 编辑操作，Enable整个Form
					enableForm('manageVendorService_form');
					$('.manageVendorCityService_provinceId1').attr('disabled','disabled');
					$('.cityId1').attr('disabled','disabled');
					$('.manageVendorService_sbHeaderId').attr('disabled','disabled');
					$('input[id^="ckb_service_"]').attr('disabled','disabled');
					// 设置SubAction为编辑
					$('.manageVendorService_form input#subAction').val('modifyObject');
					// 修改按钮显示名称
					$('#btnAddVendorService').val('<bean:message bundle="public" key="button.save" />');
					// 更改Form Action
	        		$('.manageVendorService_form').attr('action', 'vendorServiceAction.do?proc=modify_object');
				}else{
					cleanError('service_div');	 
					cleanError('manageVendorService_sbHeaderId');	 
					var reg = $('.cityServiceId').val() + '_' + $('.manageVendorService_sbHeaderId').val() + '_' + getVal();
					if( $('.manageVendorService_form .subAction').val() == 'createObject' &&  regService(reg)  ){
						addError('service_div', '<bean:message bundle="public" key="error.already.exists" />');
					}else{
						var flag = 0;
						flag = flag + validate("manageVendorCityService_provinceId1", true, "select", 0, 0);
						flag = flag + validate("cityId1", true, "select", 0, 0);
						flag = flag + validateSBHeaderId();
						flag = flag + validate("manageVendorService_serviceFee", true, "currency", 10, 0);
						flag = flag + validate("manageVendorService_description", false, "common", 500, 0);
						flag = flag + validate("manageVendorService_status", true, "select", 0, 0);
						if( !checkCheckBox() ){
							addError('service_div', '<bean:message bundle="public" key="error.not.check" />');
							flag += 1;
						}
						if(flag == 0){
							$('.manageVendorService_form .manageVendorService_vendorId').val( vendorId );
							enableForm('manageVendorService_form');
							submit('manageVendorService_form');
						}
					}
				}	
			}else{
				if(validate_manage_primary_form()==0){
					enableForm('manage_primary_form');
		            submit('manage_primary_form');
				} 
			}
		});
		
		//绑定取消Click事件
		$('#btnCancelVendorService').click(function(){
			if(agreest())
			link('vendorAction.do?proc=to_objectModify&id=' + vendorId);
		});
		
		/***
		* init 初始化相关JS
		**/
		var uploadObject = createUploadObject('uploadAttachment', 'common', '<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_VENDOR %>/<%=BaseAction.getAccountId(request ,null)%>/<%=BaseAction.getUsername(request ,null)%>/');
	})(jQuery);
	
	/**
	* 自定义函数
	**/
	// 重置Add Service form
	function resetServiceForm(){
		$('.cityId1').val('0');
		$('.cityId1').change();
		$('.manageVendorCityService_provinceId1').val('0');
		$('.manageVendorCityService_provinceId1').change();
		$('.manageVendorService_form input:checkbox').attr('checked',false); 
	};
	
	// 判断是否添加重复服务；
	function regService( reg ){
		var flag = false;
		$('#mytab .city_service').each(function(){
			if( $(this).val() == reg ){
				flag = true;
			}
		});
		return flag;
	};
	
	// 获取已勾选服务的值； 
	function getVal(){
		var val = '';
		$( '.manageVendorService_form input:checkbox:checked').each(function() {
			if( $(this).val() != 'on' ){
				val += $(this).val();
				val += ",";
			}	
	   	});
		val = val.substring(0,val.length-1);
		return val;
	};
	
	// 检查复选框是否勾选；
	function checkCheckBox(){
		var flag = false;
		$( '.manageVendorService_form input:checkbox:checked').each(function() {
			if( $(this).val() != 'on' ){
				flag = true;	
			}	
	   	});
		return flag;
	};
	
	// 检查城市有无社保公积金方案
	function check_city_sb(){
		if($('.manageVendorService_sbHeaderId').find('option').length == '1'){
			cleanError('cityId1');
			addError('cityId1', '<bean:message bundle="public" key="error.city.not.sb.soltion" />');
		};
	};
	
	// 根据选定城市得到社保公积金类型
	function cityId1Change() { 
		var cityId = $('.cityId1').val();
		if(cityId!=''&&cityId!='0'){
			cleanError('cityId1');
			$('.cityServiceId').val(cityId);
			loadHtmlWithRecall('.manageVendorService_sbHeaderId', 'vendorAction.do?proc=getSBMappingVOsByCityId_html&cityId=' + cityId, null,"check_city_sb();");
		}
	};
	
	// 修改服务项目
	function to_objectModify( id, cityId, sbId, serviceContents ){
		var callback = "provinceChange('manageVendorCityService_provinceId1', 'viewObject', '" +cityId+ "', 'cityId1');";  
		callback += "loadHtmlWithRecall(\".manageVendorService_sbHeaderId\", \"vendorAction.do?proc=getSBMappingVOsByCityId_html&cityId="+cityId+"\", true, \"$('.cityServiceId').val("+cityId+");$('.manageVendorService_sbHeaderId').val(" +sbId+ ");\");";
		callback += "selectService('" +serviceContents+ "');";
		loadHtmlWithRecall('#vendorServiceFormWrapper', 'vendorServiceAction.do?proc=to_objectModify_ajax&id=' + id, true, callback );
		// 显示Cancel按钮
		$('#btnCancelVendorService').show();
		// 显示Vendor Service Detail Form
		$('#vendorServiceFormWrapper').show();	
		// 设定SubAction值，区分Add和Modify
		$('.manageVendorService_form input#subAction').val('viewObject');
		// 修改按钮显示名称
		$('#btnAddVendorService').val('<bean:message bundle="public" key="button.edit" />');	
	};
	
	function selectService(serviceContents){
		if(serviceContents!=''&&serviceContents!=null){
			var array = serviceContents.split(',');
			for(var s in array){
				$('input[id="ckb_service_'+array[s]+'"]').attr('checked',true);
			}
		}
	};
	
	// 添加联系人
	function addVendorContact(){
		addVendorContactExtObj('vendorContactAction.do?proc=to_objectNew&type=return');
	};
	
	// 删除服务回调事件
	function removeVendorService(){
		var ids = $("#selectedIds").val().split(',').length;
	   	if(ids != null){
	   		$('#numberOfVendorService').html((parseInt($('#numberOfVendorService').html()) - ids));
	 	}
	   	$('#btnCancelVendorService').trigger('click');
	};
	
	function resetForm() {
		$('.searchSBHeader_sbSolutionId').val('0');
		$('.searchSBHeader_orderId').val('0');
		$('.searchSBHeader_employStatus').val('0');
		$('.searchSBHeader_sbStatus').val('0');
		$('.searchSBHeader_contractId').val('');
		$('.searchSBHeader_employeeId').val('');
		$('.searchSBHeader_employeeNameZH').val('');
		$('.searchSBHeader_employeeNameEN').val('');
	};
	
	// 验证sbheaderId   如果服务内容勾选了代发工资，则社保公积金类型可以为空，未勾选则不能为空
	function validateSBHeaderId(){
		var result = 0;
		var proxyChecked = false;
		var errorMsg = lang_en ? 'Please Select;' : '请选择；';
		$("input[name='serviceArray']:checked").each(function(){
			if($(this).val()=='3'){
				proxyChecked = true;
			}
		});
		if(!proxyChecked && $(".manageVendorService_sbHeaderId").val() == '0'){
			addError('manageVendorService_sbHeaderId', errorMsg);
			result = 1;
		}
		return result;
	};
</script>