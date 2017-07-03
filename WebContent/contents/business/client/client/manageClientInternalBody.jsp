<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientGroupVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_IN_HOUSE", "clientForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		// 根据"StatusFlag"设置顶部菜单选择样式
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_ClientInformation').addClass('selected');
		
		$('#menu_client_Modules').removeClass('current');			
		$('#menu_client_Client').removeClass('selected');
		$('#menu_client_ClientInfo').removeClass('selected');
		
		// 企业名称、编号不可以修改
		$('#nameZH').attr('disabled', 'disabled');
		$('#nameEN').attr('disabled', 'disabled');
		$('#number').attr('disabled', 'disabled');
		
		var status = $('#status').val();
		var subAction = getSubAction();
		
		// 列表按钮隐藏
		$('#btnList').hide();
		// 集团信息隐藏
		$('#groupNameLI').hide();
		
		var disable = true;
		if(getSubAction() == 'createObject'){
			disable = false;
		};

		// 隐藏提示集团提示信息
		$('#groupNameLI img').addClass('hide');
		// JS of the List
		<%
			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append( "if( $('#im1Type').val() != 0 ){" );
			submitAdditionalCallback.append( "flag = flag + validate(\"im1\", true, \"common\", 20, 0);" );
			submitAdditionalCallback.append( "}" );
			submitAdditionalCallback.append( "if( $('#im2Type').val() != 0 ){" );
			submitAdditionalCallback.append( "flag = flag + validate(\"im2\", true, \"common\", 20, 0);" );
			submitAdditionalCallback.append( "}" );
			submitAdditionalCallback.append( "if( flag==0 ){$('#status').attr('disabled', false);}" );

			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append( "$('#status').attr('disabled', 'disabled');");
			editCallBack.append( "$('#nameZH').attr('disabled', 'disabled');");
			editCallBack.append( "$('#nameEN').attr('disabled', 'disabled');");
			editCallBack.append( "$('#number').attr('disabled', 'disabled');");
			editCallBack.append( "$('img.deleteImg').each(function(i){$(this).show();});" );
			editCallBack.append( "var uploadObject = _createUploadObject('addPhoto', 'image', '/"+KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT+"/"+BaseAction.getAccountId(request, response)+"/');" );
			editCallBack.append( "var uploadLogo = _createUploadLogo('uploadAttachment', 'image', '/"+KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT+"/"+BaseAction.getAccountId(request, response)+"/');" );
			editCallBack.append( "$('img.deleteImg').each(function(i){$(this).show();});" );
			editCallBack.append( "$('#pageTitle').html('" + KANUtil.getProperty( request.getLocale(), "business.client.edit" ) + "');" );
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_IN_HOUSE", "initCallBack();", editCallBack.toString(), null, submitAdditionalCallback.toString() ) %>
		
		// Add by siuxia
		if( subAction == 'viewObject' ){
			$('#pageTitle').html('<bean:message bundle="business" key="business.client.view" />');
		}
		
		$('#branchLI').hide();
		$('#ownerLI').hide();
		
		// 非新增模式添加“查看集团信息”
		if(getSubAction() != 'createObject' && $('#groupId').val()){
			$('#groupNameLI label:eq(0)').append('<a onclick="link(\'clientGroupAction.do?proc=to_objectModify&id=<bean:write name="clientForm" property="encodedGroupId" />\');" ><img src="images/find.png" title="查看集团记录" /></a>');
		}
		
		// 主联系人更改自动修改部分信息
		$('#mainContact').bind('change', function(){
			$.ajax({url: 'clientContactAction.do?proc=get_object_json&clientContactId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'Json',
				success : function(data){
					cleanError('mainContact');
					if(data.success = 'true'){
						$('#phone').val(data.bizPhone);
						$('#mobile').val(data.bizMobile);
						$('#fax').val(data.fax);
						$('#email').val(data.bizEmail);
						$('#cityId').val(data.cityId);
						$('#address').val(data.address);
						$('#postcode').val(data.postcode);
						$('#im1Type').val(data.im1Type);
						$('#im1').val(data.im1);
						$('#im2Type').val(data.im2Type);
						$('#im2').val(data.im2);
						$('#branch').val(data.branch);
						$('#owner').val(data.owner);
					}
				}
			});
		});
		
		// 加载联系人下拉框
		loadHtml('#mainContact', 'clientContactAction.do?proc=list_object_options_ajax&clientId=<bean:write name="clientForm" property="encodedId"/>', true );

		// 加载行业类型下拉框
		loadHtml('#industry', 'industryTypeAction.do?proc=list_object_options_ajax&typeId=<bean:write name="clientForm" property="industry"/>', true );

		// 初始化省份控件
		provinceChange('provinceId', 'viewObject', $('#temp_cityId').val(), 'cityId');

		// 绑定省份Change事件
		$('.provinceId').change( function () { 
			provinceChange('provinceId', 'modifyObject', 0, 'cityId');
		});
		
		// 初始化部门控件
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// 绑定部门Change事件
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// 绑定省份Change事件
		$('#provinceId').change( function () { 
			loadHtml('#cityId', 'cityAction.do?proc=list_object_options_ajax&provinceId=' + $('#provinceId').val(), true );
		});
		
		// 加载客户形象照片
		var disable = true;
		if($('.subAction').val() == 'createObject'){
			disable = false;
		};
		
		loadHtml("#special_info","clientAction.do?proc=load_image_file&subAction=<bean:write name='clientForm' property='subAction' />&clientId=<bean:write name='clientForm' property='clientId' />",disable);
	})(jQuery);		
	
	function initCallBack(){
		var $label = $("#logoFileLI").find("label");
		$label.html($label.text() + "<a title='<bean:message bundle="business" key="business.client.logo.tips" />'><img src='images/tips.png'></a>");
		$("#logoFileLI").find("input").remove();
		
		var html = "<span id='attachmentsLogoOL'>";
		<logic:notEmpty name="clientForm" property="logoFile">
			html = html + "<input type='hidden' id='logoFile' name='logoFile' value='<bean:write name='clientForm' property='logoFile'/>' /> ";
			html = html + "<input type='hidden' id='logoFile' name='logoFileSize' value='<bean:write name='clientForm' property='logoFileSize'/>' /> ";
			html = html + "<img name='disable_img' width='12' height='12' id='disable_img'  src='images/disable-btn.png'> ";
			html = html + "<img name='warning_img' width='12' height='12' id='warning_img' style='display: none;' onclick=\"$('#_attachmentSpan').remove();\" src='images/warning-btn.png'> ";
			html = html + "&nbsp;<bean:write name='clientForm' property='logoFileName'/>";
		</logic:notEmpty>
		html = html + "</span>";
		html = html + ' <span><a name="uploadAttachment" id="uploadAttachment" class="disabled" ><bean:message bundle="public" key="link.upload.picture" /></a></span>';
		$("#logoFileLI").append(html);
	};
	
	function _createUploadLogo(id, fileType, attachmentFolder) {
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
				createFileProgressLogoDIV(filename);
				disableLinkById(id);
				setTimeout("_ajaxLogoBackState(true,'uploadFileAction.do?proc=getStatusMessage&postfixRandom="+postfixRandom+"')", 500);
			},

			// 上传完成后取得文件名filename为本地取得的文件名，msg为服务器返回的信息
			onComplete : function(filename, msg) {
				// 上传可用
				enableLinkById(id);
			}
		});

		return uploadObject;
	};

	function _ajaxLogoBackState(first,progressURL) {
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
						setTimeout("_ajaxLogoBackState(false,'"+progressURL+"')", 100);
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
						$("#attachmentsLogoOL").html('<li><input type="hidden" id="logoFile" name="logoFile" value="'
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
	
	function createFileProgressLogoDIV(fileName) {
		$("#attachmentsLogoOL").html($("#attachmentsLogoOL").html()
			+ '<li id="uploadFileLi" style="margin: 5px 0 0 0;"><div id="progressBarOut" style="text-align: center; width: 200px; height: 14px; padding: 1px 1px 1px 1px; font-size: 10px; border: solid #d4e4ff 1px;"><div id="progressBarIn" style="width: 0; height: 12px; background-color: #d4e4ff; margin: 0; padding: 0;"></div></div><div id="fileNameDiv" style="margin: 0 5px 0 5px; border: 0;">'
			+ fileName
			+ '</div> &nbsp; <div id="progressSize" style="margin: 0; border: 0;"></div></li>');
	};
</script>

