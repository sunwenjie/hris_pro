<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.actions.management.OptionsAction"%>
<%@ page import="com.kan.base.domain.management.OptionsVO"%>
<%@ page import="com.kan.base.web.renders.system.ModuleRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<div id="content">
	<div id="options" class="box toggableForm">
		<div class="head">
			<label><bean:message bundle="system" key="system.options" /></label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=OptionsAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />" />  
				</kan:auth>
			</div>
			<html:form action="optionsAction.do?proc=modify_object" styleClass="options_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="subAction" id="subAction" value="viewObject" />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.options.page.style" /></label> 
							<html:select property="pageStyle" styleClass="options_pageStyle">
								<html:optionsCollection property="pageStyles" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.options.branch.prefer" /></label>
							<html:select property="branchPrefer" styleClass="options_branchPrefer">
								<html:optionsCollection property="branchPrefers" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.branch.sum" /></label>
							<html:select property="isSumSubBranchHC" styleClass="options_isSumSubBranchHC">
								<html:optionsCollection property="isSumSubBranchHCs" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.position.prefer" /></label>
							<html:select property="positionPrefer" styleClass="options_positionPrefer">
								<html:optionsCollection property="positionPrefers" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.month.avg" /></label>
							<html:text property="monthAvg" styleClass="options_monthAvg"></html:text>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.date.format" /></label> 
							<html:select property="dateFormat" styleClass="options_dateFormat">
								<html:optionsCollection property="dateFormats" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.time.format" /></label> 
							<html:select property="timeFormat" styleClass="options_timeFormat">
								<html:optionsCollection property="timeFormats" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.accuracy" /></label> 
							<html:select property="accuracy" styleClass="options_accuracy">
								<html:optionsCollection property="accuracys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.round" /></label> 
							<html:select property="round" styleClass="options_round">
								<html:optionsCollection property="rounds" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.sms.getway" /></label> 
							<html:select property="smsGetway" styleClass="options_smsGetway">
								<html:optionsCollection property="smsGetways" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.language" /></label> 
							<html:select property="language" styleClass="options_language">
								<html:optionsCollection property="languages" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<html:checkbox property="useBrowserLanguage" styleClass="options_usebrowserlanguage" /> 
							<span><bean:message bundle="system" key="system.options.use.browser.language" /></span>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.options.company.LOGO" /> <a title="<bean:message bundle="system" key="system.options.company.LOGO.tips" />"><img src="images/tips.png" /></a></label> 
							<span id="attachmentsOL">
								<logic:notEmpty name="optionsForm" property="logoFile">
									<input type="hidden" id="logoFile" name="logoFile" value='<bean:write name="optionsForm" property="logoFile"/>' />
									<input type="hidden" id="logoFile" name="logoFileSize" value="<bean:write name="optionsForm" property="logoFileSize"/>" />
									<img name="disable_img" width="12" height="12" id="disable_img"  src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="$('#_attachmentSpan').remove();" src="images/warning-btn.png">
									&nbsp;<bean:write name="optionsForm" property="logoFileName"/>
								</logic:notEmpty>
							</span>
							<span><a name="uploadAttachment" id="uploadAttachment" class="kanhandle" ><bean:message bundle="public" key="link.upload.picture" /></a></span>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.options.order.bind.contract" />  <img src="images/tips.png" title="<bean:message bundle="system" key="system.options.order.bind.contract.tips" />" /></label> 
							<html:select property="orderBindContract" styleClass="options_orderBindContract">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.sb.genreate.condition" /></label> 
							<html:select property="sbGenerateCondition" styleClass="small options_sbGenerateCondition">
								<html:optionsCollection property="sbGenerateConditions" value="mappingId" label="mappingValue" />
							</html:select>
							<html:select property="sbGenerateConditionSC" styleClass="small options_sbGenerateConditionSC">
								<html:optionsCollection property="sbGenerateConditionSCs" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.cb.genreate.condition" /></label> 
							<html:select property="cbGenerateCondition" styleClass="small options_cbGenerateCondition">
								<html:optionsCollection property="cbGenerateConditions" value="mappingId" label="mappingValue" />
							</html:select>
							<html:select property="cbGenerateConditionSC" styleClass="small options_cbGenerateConditionSC">
								<html:optionsCollection property="cbGenerateConditionSCs" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.settlement.condition" /></label> 
							<html:select property="settlementCondition" styleClass="small options_settlementCondition">
								<html:optionsCollection property="settlementConditions" value="mappingId" label="mappingValue" />
							</html:select>
							<html:select property="settlementConditionSC" styleClass="small options_settlementConditionSC">
								<html:optionsCollection property="settlementConditionSCs" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.options.ot.min.unit" /></label>
							<html:select property="otMinUnit" styleClass="options_otMinUnit">
								<html:optionsCollection property="otMinUnits" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">	
						<li>
							<label><bean:message bundle="system" key="system.options.independence.tax" /></label> 
							<logic:equal value="1" name="optionsForm" property="independenceTax">
								<input type="checkbox" name="independenceTax" checked="checked" value="1"/>
							</logic:equal>
							<logic:notEqual value="1" name="optionsForm" property="independenceTax">
								<input type="checkbox" name="independenceTax"  value="1"/>
							</logic:notEqual>
						</li>
						<%-- <li>
							<label><bean:message bundle="system" key="system.options.mobile.module.right" /></label>
							<% final OptionsVO optionsVO = (OptionsVO)request.getAttribute("optionsForm");
								System.out.println(optionsVO.getMobileModuleRights().size());
							%>
							<%=KANUtil.getCheckBoxHTML(optionsVO.getMobileModuleRights(), "mobileModuleRightIds", optionsVO.getMobileModuleRightIds(), optionsVO.getSubAction()) %>
						</li> --%>
					</ol>
				</fieldset>
				
				<%-- <div id="tab"> 
						<div class="tabMenu"> 
							<ul> 
								<li id="tabMenu1" onClick="changeTab(1,2)" class="hover first"><bean:message bundle="public" key="menu.table.title.client.menu.rule" /></li>
								<li id="tabMenu2" onClick="changeTab(2,2)"><bean:message bundle="public" key="menu.table.title.employee.menu.rule" /></li> 
							</ul> 
						</div> 
						<div class="tabContent"> 
							<div id="tabContent1" class="kantab kantree">
								<ol class="static">
									<%= ModuleRender.getClientTree( request,response,true,"4",null) %>
								</ol>
							</div>
							<div id="tabContent2" class="kantab kantree" style="display: none;">
								<ol class="static">
									<%= ModuleRender.getClientTree( request,response,true,"5",null) %>
								</ol>
							</div> 
						</div> 
				</div> --%>
			</html:form>
			<div id="progress_debug"></div>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_management_Modules').addClass('current');			
		$('#menu_management_Options').addClass('selected');
		
		$(".tabMenu li").first().addClass('hover');
		$(".tabMenu li").first().addClass('first');
		
		// 将Form设为Disable
		disableForm('options_form');
		
		// 编辑按钮点击事件
        $('#btnEdit').click(function(){
        	if($('#subAction').val() == 'viewObject'){
        		enableForm('options_form');
        		$('#subAction').val('modifyObject');
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		
        		var uploadObject = _createUploadObject('uploadAttachment', 'image', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_ACCOUNT %>/<%= BaseAction.getAccountId(request, response) %>/');
        		$('img[id^=warning_img]').show();
        		$('img[id^=disable_img]').hide();
        	}else{
        		var flag = 0;
    			
    			if(flag == 0){
    				submit('options_form');
    			}
        	}
        });
		// 取消按钮点击事件
		$('#btnCancel').click( function () {
			back();
		});
	})(jQuery);
	
	function _createUploadObject(id, fileType, attachmentFolder) {
		var postfixRandom = generateMixed(6);
		var uploadObject = new AjaxUpload(id, {
			// 文件上传Action
			action : 'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom,
			// 名称
			name : 'file',
			// 自动上传
			autoSubmit : true,
			// 返回Text格式数据
			responseType : false,
			// 上传处理
			onSubmit : function(filename, ext) {
				createFileProgressDIV(filename);
				disableLinkById(id);
				setTimeout("_ajaxBackState(true,'uploadFileAction.do?proc=getStatusMessage&postfixRandom="+postfixRandom+"')", 500);
			},

			// 上传完成后取得文件名filename为本地取得的文件名，msg为服务器返回的信息
			onComplete : function(filename, msg) {
				enableLinkById(id);
			}
		});

		return uploadObject;
	};

	function _ajaxBackState(first, progressURL) {
		// Ajax调用并刷新当前上传状态
		$.post(progressURL+'&first=' + first+ '&date=' + new Date(),null,
				function(result) {
					var obj = result;
					var readedBytes = obj["readedBytes"];
					var totalBytes = obj["totalBytes"];
					var statusMsg = obj["statusMsg"];

					progressbar(readedBytes,totalBytes);

					// 正常状态
					if (obj["info"] == "0") {
						setTimeout("_ajaxBackState(false,'"+progressURL+"')", 100);
					}
					// 上传失败，提示失败原因
					else if (obj["info"] == "1") {
						alert(obj["statusMsg"]);
						$('#btnAddAttachment').attr("disabled", false);
						// 删除上传失败的文件和进度信息
						$('#uploadFileLi').remove();
					}
					// 上传完成
					else if (obj["info"] == "2") {
						$('#uploadAttachment').attr("disabled", false);
						$("#attachmentsOL").html('<li><input type="hidden" id="logoFile" name="logoFile" value="'
												+ statusMsg.split('##')[0]
												+ '" /><input type="hidden" id="logoFile" name="logoFileSize" value="'
												+ statusMsg.split('##')[2]
												+ '" /><img onclick=\"removeAttachment(this);\" src=\"images/warning-btn.png\"> &nbsp; '
												+ statusMsg.split('##')[1]
												+ "</li>");
						$('#uploadFileLi').remove();

					}
				},'json');
	};
</script>
