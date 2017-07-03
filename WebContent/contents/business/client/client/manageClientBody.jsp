<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.hro.web.actions.biz.client.ClientAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>
<%@page import="com.kan.hro.domain.biz.client.ClientGroupVO"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT", "clientForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		var status = $('#status').val();
		var subAction = getSubAction();
		
		//状态（1:新建，2:待审核，3:批准，4:退回，5:锁定）
		if( status == '1' || status == '4'){
		 	$('#btnSubmit').show(); 	
		}else if( status == '2' ){
			$('#btnEdit').hide();
		}
		
		var disable = true;
		if(getSubAction() == 'createObject'){
			disable = false;
		};
		
		// 添加“复制信息”按钮
		if($('#clientId').val()){
			<kan:auth right="new" action="<%=ClientAction.accessAction%>">
				$('#btnList').after('<a name="copyInfo" title="复制信息" class="commonTools" id="copyInfo" onclick="if(confirm(\'是否确定复制并创建相同 “信息客户”？\')){link(\'clientAction.do?proc=copy_object&clientId=<bean:write name="clientForm" property="encodedId"/>&com.kan.token=' + $("#com.kan.token").val() + '\');}"><u>复制</u></a>');
			</kan:auth>
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
			editCallBack.append( "if( $('#status').val() != 3 ){" );
			editCallBack.append( "disableLinkById(\"#addContract\");disableLinkById(\"#addOrder\");" );
			editCallBack.append( "}" );
			editCallBack.append( "if( status == '3' ){");
			editCallBack.append( "$('#btnSubmit').show();");
			editCallBack.append( "$('#btnEdit').hide();");
			editCallBack.append( "}");
			editCallBack.append( "if( $('#groupId').val() ){" );
			editCallBack.append( "$('#groupName').attr('disabled', 'disabled');");
			editCallBack.append( "$('#groupNameLI img').removeClass('hide');;");
			editCallBack.append( "}" );
			/* // 如果是已归档，则财务编码是不能修改
			if((Boolean)request.getAttribute("isFiling")){
			   editCallBack.append("$('#number').attr('disabled', 'disabled');");
			} */
			editCallBack.append( "$('img.deleteImg').each(function(i){$(this).show();});" );
			editCallBack.append( "var uploadObject = _createUploadObject('addPhoto', 'image', '/"+KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT+"/"+BaseAction.getAccountId(request, response)+"/');" );
			editCallBack.append( "var uploadLogo = _createUploadLogo('uploadAttachment', 'image', '/"+KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT+"/"+BaseAction.getAccountId(request, response)+"/');" );
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT", "initCallBack();", editCallBack.toString(), null, submitAdditionalCallback.toString() ) %>
		
		// 非新增模式添加“查看集团信息”
		if(getSubAction() != 'createObject' && $('#groupId').val()){
			$('#groupNameLI label:eq(0)').append('<a onclick="link(\'clientGroupAction.do?proc=to_objectModify&id=<bean:write name="clientForm" property="encodedGroupId" />\');" ><img src="images/find.png" title="查看集团记录" /></a>');
		}
	
		// 提交按钮事件
		$('#btnSubmit').click( function () { 
			if(validate_manage_primary_form() == 0){
				// 更改当前Form的SubAction
				if(getSubAction() != 'createObject'){
					$('.manage_primary_form').attr('action', 'clientAction.do?proc=modify_object');
				}
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
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
		
		// 加载tab
		loadHtml('#special_info', 'clientAction.do?proc=list_special_info_html&clientId=<bean:write name="clientForm" property="encodedId"/>', disable);
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
		
		isArchiveClient();
	})(jQuery);	
	
	function initCallBack(){
		var $label = $("#logoFileLI").find("label");
		$label.html($label.text()+"<a title='推荐图片尺寸160*38'><img src='images/tips.png'></a>");
		$("#logoFileLI").find("input").remove();
		var html = "";
		html = html + "<span id='attachmentsLogoOL'>";
		<logic:notEmpty name="clientForm" property="logoFile">
		html = html + "<input type='hidden' id='logoFile' name='logoFile' value='<bean:write name='clientForm' property='logoFile'/>' /> ";
		html = html + "<input type='hidden' id='logoFile' name='logoFileSize' value='<bean:write name='clientForm' property='logoFileSize'/>' /> ";
		html = html + "<img name='disable_img' width='12' height='12' id='disable_img'  src='images/disable-btn.png'> ";
		html = html + "<img name='warning_img' width='12' height='12' id='warning_img' style='display: none;' onclick=\"$('#_attachmentSpan').remove();\" src='images/warning-btn.png'> ";
		html = html + "&nbsp;<bean:write name='clientForm' property='logoFileName'/>";
		</logic:notEmpty>
		html = html + "</span>";
		html = html + "<span><a name='uploadAttachment' id='uploadAttachment' class='disabled' >上传图片</a></span>";
		$("#logoFileLI").append(html);
	}
	
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
				// 上传不可用
				// $('#' + id).disable();
			},

			// 上传完成后取得文件名filename为本地取得的文件名，msg为服务器返回的信息
			onComplete : function(filename, msg) {
				// 上传可用
				// $('#' + id).enable();
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

					//$("#progress_debug").append('info = '+obj["info"]+' readedBytes'+readedBytes+'    totalBytes='+totalBytes+'    progressURL='+progressURL+'<br/>');
					
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
	
	function isArchiveClient(){

		if ($('#clientId').val() != null && $('#clientId').val() !=""){
			$.ajax({
				url: 'clientContractAction.do?proc=getArchiveClientContractCount', 
				type: 'POST', 
				traditional: true,
				data:'clientId='+$('#clientId').val(),
				dataType : 'json',
				async:false,
				success: function(result){
					if (result != "0") {
						$('#number').attr("readonly","readonly");
						$('#number').css("background-color", "#e5e5e5");
					}
				} 
			});
		}
	}
</script>