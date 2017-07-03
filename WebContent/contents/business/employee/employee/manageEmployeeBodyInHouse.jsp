<%@page import="com.kan.hro.domain.biz.employee.EmployeeVO"%>
<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plugins/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="js/idCard.js"></script>
<style type="text/css">
	#employeePhotosOL{
		width: 122px;
		height: 162px;
	}
	#employeePhotoDiv{
		padding: 10px;
	}
</style>


<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_IN_HOUSE", "employeeForm", true ) %>
	
</div>
					
<script type="text/javascript">
	(function($) {
		// JS of the List
		<%
			final String submitAdditionalCallback = "flag +=validateIDCard();flag+=validateBankID();flag+=validateBankAccount();flag+=validateUserName();flag += validate_email_ajax();fixPhone2();";
			StringBuffer editCallBack = new StringBuffer("if($('.user_username').val() != ''){$('.user_username').attr('disabled', 'disabled');$('#resetPassword').show();}");
			editCallBack.append("if(CKEDITOR.instances){$.each(CKEDITOR.instances,function(id,instances){instances.setReadOnly(false);});}");
			editCallBack.append("if($('#status').val() == 3){");
			editCallBack.append("$('#status').attr('disabled', 'disabled');");
			editCallBack.append("}");
			editCallBack.append("$('#_tempBranchIds').attr('disabled', 'disabled');");
			editCallBack.append("$('#_tempPositionIds').attr('disabled', 'disabled');");
			editCallBack.append("$('#_tempParentBranchIds').attr('disabled', 'disabled');");
			editCallBack.append("$('#_tempPositionGradeIds').attr('disabled', 'disabled');");
			editCallBack.append("enableUploadPhoto();");
			
			StringBuffer initCallBack = new StringBuffer();
			initCallBack.append("if($('#subAction').val() == 'createObject'){");
			initCallBack.append("$('#status option[value=\"1\"]').remove();");
			initCallBack.append("$('#status option[value=\"3\"]').remove();");
			initCallBack.append("}");
			initCallBack.append("initPhoto();");
			initCallBack.append("appendPhone2();");
			// 添加部门
			final String parentBranchs = ((EmployeeVO)request.getAttribute("employeeForm")).getParentBranchs();
			
			if(request.getAttribute("certificationErrorMsg") != null)
			{
				final String employeeIdError = (String)request.getAttribute("certificationErrorMsg");
				initCallBack.append("addError('certificateNumber', '" + employeeIdError + "');");
			}
			
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_IN_HOUSE", initCallBack.toString(),editCallBack.toString() , null ,submitAdditionalCallback.toString()) %>
		
		// Add by siuxia
		$('#HRO_BIZ_EMPLOYEE_IN_HOUSE_CG13_Link').html('<bean:message bundle="business" key="business.employee2.resume" />');
		
		/**
		* define  定义变量
		**/
		
		// 雇员id 
		var employeeId = '<bean:write name="employeeForm" property="employeeId"/>';
		var temp_residencyCityId = 0;
		var disable = true;
		if($('.subAction').val() == 'createObject'){
			disable = false;
		}else if($('.subAction').val() != 'createObject'){
			var tempParentBranchName = '<bean:write name="employeeForm" property="decode_tempParentBranchIds" />';
			$('#_tempBranchIds').val('<bean:write name="employeeForm" property="decode_tempBranchIds" />');
			$('#_tempPositionIds').val('<bean:write name="employeeForm" property="decode_tempPositionIds" />');
			$('#_tempPositionGradeIds').val('<bean:write name="employeeForm" property="decode_tempPositionGradeIds" />');
			temp_residencyCityId = '<bean:write name="employeeForm" property="residencyCityId" />';
			if( tempParentBranchName != null && tempParentBranchName != '' && tempParentBranchName.indexOf('&#39;')>0){
				tempParentBranchName = tempParentBranchName.replace('&#39;',"'");
			}
			$('#_tempParentBranchIds').val(tempParentBranchName);
		}
	
		/**
		* loadHtml 加载页面
		**/
		// 初始化省份控件
		provinceChange('residencyProvinceId', 'viewObject', temp_residencyCityId, 'residencyCityId');
		// 初始化部门控件
		branchChange('branch', null, '<bean:write name="employeeForm" property="owner" />', 'owner');
		// 加载Tab页面
		loadHtml('#special_info', 'employeeAction.do?proc=list_special_info_html&employeeId=' + employeeId, disable);
		
		/**
		* bind 绑定事件
		**/
		
		// 绑定省份Change事件
		$('.residencyProvinceId').change( function () { 
			provinceChange('residencyProvinceId', 'modifyObject', 0, 'residencyCityId');
		});
		
		// 绑定部门Change事件
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// 绑定 外国人就业许可证Change事件
		$('.hasForeignerWorkLicence').change(function(){
			if(this.value==1){
				$('#foreignerWorkLicenceNoLI,#foreignerWorkLicenceEndDateLI').show();
			}else{
				$('#foreignerWorkLicenceNoLI,#foreignerWorkLicenceEndDateLI').hide();
			}
		});
		
		// 绑定 居住证Change事件
		$('.hasResidenceLicence').change(function(){
			if(this.value==1){
				$('#residenceNoLI,#residenceStartDateLI,#residenceEndDateLI').show();
			}else{
				$('#residenceNoLI,#residenceStartDateLI,#residenceEndDateLI').hide();
			}
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if(getSubAction() == 'modifyObject'){
				// 上传链接生效
				$('#uploadAttachment').attr("disabled", false);
				$('#uploadAttachment').removeClass("disabled");
				// Enable删除小图标
	   			$('img[id^=warning_img]').each(function(i){
	   				$(this).show();
	   			});
	   			$('img[id^=disable_img]').each(function(i){
	   				$(this).hide();
	   			});
	   			// 技能显示模态框已选checkbox禁用不解开
	   			$('div[id="skill_level_div"] input[type="checkbox"]').each(function(i){
	   				if($(this).attr("checked")){
	   					$(this).attr("disabled", "disabled");
	   				}
	   			});
			}
		});
		
		/**
		* init 初始化相关JS
		**/
		
		//初始化外国人就业许可证
		$('.hasForeignerWorkLicence').change();
		
		//初始化
		$('.hasResidenceLicence').change();
		
		//初始化 简历 使用 ckeditor 
		$('ol:has(.resumeZH)').removeClass("auto").addClass("static");//占一整行
		$('ol:has(.resumeEN)').removeClass("auto").addClass("static");//占一整行
		$('.resumeZH').ckeditor();
		$('.resumeEN').ckeditor();
		
		// 查看模式 附件添加
		if(getSubAction() == 'viewObject'){
			$('#uploadAttachment').attr("disabled", true);
			$('#uploadAttachment').addClass("disabled");
		};
		
		/**
		* other 其他代码
		**/
		var status = '<bean:write name="employeeForm" property="status"/>';
		var containValidContractFlag = '<bean:write name="employeeForm" property="containValidContractFlag"/>';
		
		// 如果为“招募”或者“候选”状态不允许“离职”
		if (( status == 2 ) || ( status == 4 ) )
		{
		   $('#status option[value="1"]').remove();
		   $('#status option[value="3"]').remove();
		}
		// 如果为“在职”不允许修改为“招募”或者“候选”
		else if( status == 1 ){
			$('#status option[value="2"]').remove();
			$('#status option[value="4"]').remove();
		}
		
		// 如果有“雇佣状态”为在职的服务协议则不允许“离职”
		$('#status').change(function(){
			cleanError('status');
			var tempStatus = $('#status').val();
			
			if( containValidContractFlag == 1 && tempStatus == 3){
				addError('status', '该<logic:equal name='role' value='1'>雇员</logic:equal><logic:equal name='role' value='2'>员工</logic:equal>正在服务期间，不能“离职”！');
				$('#status').val(status);
			}
			
		});
		
		// 邮箱实时校验
		$("#email1").keyup( function(){
			validate_email_ajax();
		});
		
		$("#certificateNumber").blur(function(){
			cleanError('certificateNumber');
			
			// 如果是身份证类型且号码有效
			if( ($('#certificateType').val() == 1) && (validate("certificateNumber", true, "idcard", 100, 0) == 0) )
			{
				// 验证是否存在
				$.post("employeeAction.do?proc=get_count_byCertificateNumber",{"certificateNumber":$("#certificateNumber").val(),"id":$("#id").val()},function(data){
					if(data==0){
						var card = new clsIDCard($('#certificateNumber').val());
						$('#birthday').val(card.GetBirthDate());
						$('#salutation').val(card.GetSex()==0?2:1);
					}else{
						addError('certificateNumber','身份证已被注册');
						//$("#certificateNumber").focus();
					}
				},"text");
			}
		});
		$('#_tempBranchIds').attr('disabled', 'disabled');
		$('#_tempPositionIds').attr('disabled', 'disabled');
		$('#_tempParentBranchIds').attr('disabled', 'disabled');
		$('#_tempPositionGradeIds').attr('disabled', 'disabled');
	})(jQuery);
	 
	/**
	* 自定义函数
	**/
	function validateIDCard(){
		// 删除错误提醒文字
		$("#certificateNumber_error").remove();
		//证件类型为身份证 并且 输入内容不为空才验证
		if($('#certificateType').val() == '1' && $.trim($('#certificateNumber').val()) != ''){
			var card = new clsIDCard($('#certificateNumber').val());
			if(!card.IsValid()){
				$("#certificateNumber").addClass('error');
				$("#certificateNumber").after('<label class="error certificateNumber_error">&#8226; ' + card.GetError() + '</label>');
				$("#certificateNumber").focus();

				return 1;
			}else{
				return 0;
			}
		}else{
			return 0;
		}	
	};
	
	function validateBankID(){
		// 删除错误提醒文字
		$(".bankId_error").remove();
		$("#bankId").removeClass('error');
		//
		if($('#bankId').val()== '0' && $('#bankAccount').val()!=''){
			$("#bankId").addClass('error');
			$("#bankId").after('<label class="error bankId_error">&#8226; 请选择银行ID</label>');
			$("#bankId").focus();
			return 1;
		}else{
			return 0;
		}
	};
	
	function validateUserName(){
		// 都为空
		var flag = 0;
		if($('#username').val()!='' && $("#email1").val()=='' && $("#email2").val()=='' ){ 
			flag = flag + validate('email1',true, 'email', 0 ,0, 0,0);
			flag = flag + validate('user_username', true, 'common', 20, 2);
		}
		// 邮件1不为空
		else if($('#username').val()!='' && $("#email1").val()!=''){
			flag = flag + validate('email1',true, 'email', 0 ,0, 0,0);
			flag = flag + validate('user_username', true, 'common', 20, 2);
		}
		// 邮件2不为空
		else if($('#username').val()!='' && $("#email2").val()!=''){
			flag = flag + validate('email2',true, 'emai2', 0 ,0, 0,0);
			flag = flag + validate('user_username', true, 'common', 20, 2);
		}
		return flag;
	};
	
	function validateBankAccount(){
		// 删除错误提醒文字
		$(".bankAccount_error").remove();
		$("#bankAccount").removeClass('error');
		//
		if($('#bankId').val()!= '0' && $('#bankAccount').val()==''){
			$("#bankAccount").addClass('error');
			$("#bankAccount").after('<label class="error bankAccount_error">&#8226; 请输入银行账号</label>');
			$("#bankAccount").focus();
			return 1;
		}else{
			return 0;
		}
	};
	
	//phone2如果有逗号就添加回车
	function fixPhone2(){
		/* var phone2 = $("#phone2").val();
		if(phone2!=''){
			var targetValue = "";
			var phone2s="";
			if(phone2.indexOf(",") > 0){
				var phone2s = phone2.split(",");
			}else if( phone2.indexOf("，") > 0){
				var phone2s = phone2.split("，");
			}
			if(phone2s.length>0){
				for(var i = 0 ; i<phone2s.length;i++){
					if(i>0){
						targetValue+=",\n";
					}
					targetValue+=phone2s[i];
				}
			}
			$("#hidePhone2").val(targetValue);
		} */ 
	};
	
	function appendPhone2(){
		//$(".manage_primary_form").append("<textarea name='hidePhone2' class='hidePhone2' id='hidePhone2' style='display: none;' />") 
	};

    // Esc按键事件 - 隐藏弹出框
    $(document).keyup(function(e) {
        if (e.keyCode == 27) {
            $('#modalId').addClass('hide');
        }
    });
	
	function imageMouseover(){
		var x = 100;
		var y = 100;
		$("a.tooltip").mouseover(function(e){ 
			this.myTitle = this.title;
			this.title = "";
			var tooltip = "<div style='position: absolute;z-index: 1001';  id='tooltip'><img src='"+ $(this).find('img').attr('src') +"'/><\/div>"; //创建 div 元素
			$("body").append(tooltip);	//把它追加到文档中
			// 创建一个临时位置存放图片大小
			$("<img/>").attr("src", $(this).find('img').attr('src')).load(function() {
				y = this.height;
			});
			$("#tooltip")
				.css({
					"top": (e.pageY-y)+"px",
					"left": (e.pageX+x)+"px"
				}).show("fast");	  //设置x坐标和y坐标，并且显示
	    }).mouseout(function(){
			this.title = this.myTitle;	
			$("#tooltip").remove();	 //移除 
	    }).mousemove(function(e){
			$("#tooltip")
				.css({
					"top": (e.pageY-y+250) + "px",
					"left":  (e.pageX+x)  + "px"
				});
		});

	}
	
	function initPhoto(){
		//移除文本框
		$("#photoLI label").append("<img src=\"images/tips.png\" title=\"<bean:message bundle="public" key="link.upload.picture.tips" />\">");
		$("#photoLI label").append("<span style=\"\"><a name=\"uploadPhoto\" id=\"uploadPhoto\" class=\"kanhandle\"><bean:message bundle="public" key="link.upload.picture" /></a></span>");
		var photoPath=$("#photoLI #photo").val();
		$("#photoLI textarea").remove();
		$("#photoLI").append("<div style=\"border:1xp;\" id=\"employeePhotoDiv\"><ol id=\"employeePhotosOL\"  class=\"auto\"></ol></div>");
		if(photoPath!=null&&photoPath!=""){
			var imageSrc = "downloadFileAction.do?proc=download&fileString=/" + encodeURI(encodeURI(photoPath));
			$("#employeePhotosOL").html('<li ><input type="hidden" id="imageFileArray" name="imageFileArray" value="'+ photoPath+ '" />'
					+' <a class="tooltip" href="'+imageSrc+'" > <img class="imgtooltip" src="'+imageSrc+'" width="120px" height="160px" /> </a>'
					+ "</li>");
		}else{
			$("#employeePhotosOL").html('<li >'
					+' <img class="imgtooltip" src="images/userDefault.png" width="120px" height="160px" />'
					+ "</li>");
		}
		enableUploadPhoto();
	}
	
	function enableUploadPhoto(){
		if($(".subAction").val()!='viewObject'){
			var upLoadPhoto= createUpLoadPhoto('uploadPhoto', 'image', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_EMPLOYEE %>/<%= BaseAction.getAccountId(request, response) %>/<%=java.net.URLEncoder.encode( java.net.URLEncoder.encode(BaseAction.getUsername(request, response),"utf-8"),"utf-8") %>/');
		}
	}
    
    function createUpLoadPhoto(id, fileType, attachmentFolder) {
		var postfixRandom = generateMixed(6);
		var upLoadPhoto = new AjaxUpload(id, {
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
				createEmployeePhotosDIV(filename);
				disableLinkById(id);
				//$("#progress_debug").append('action = '+'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom);
				setTimeout("ajaxBackStateInfo(true,'"+"uploadFileAction.do?proc=getStatusMessage&postfixRandom="+postfixRandom+"',200)");
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
		return upLoadPhoto;
	};
	
	function ajaxBackStateInfo(first,progressURL) {
		// Ajax调用并刷新当前上传状态
		$.post(progressURL+'&first=' + first+ '&date=' + new Date(),null,
				function(result) {
					var obj = result;
					var readedBytes = obj["readedBytes"];
					var totalBytes = obj["totalBytes"];
					var statusMsg = obj["statusMsg"];
					upLoadPhotosProgressbar(readedBytes,totalBytes);

					//$("#progress_debug").append('info = '+obj["info"]+' readedBytes'+readedBytes+'    totalBytes='+totalBytes+'    progressURL='+progressURL+'<br/>');
					
					// 正常状态
					if (obj["info"] == "0") {
						setTimeout("ajaxBackStateInfo(false,'"+progressURL+"',200)");
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
						$('#uploadPhoto').attr("disabled", false);
						var imageSrc = "downloadFileAction.do?proc=download&fileString=/" + encodeURI(encodeURI(statusMsg.split('##')[0]));
						$("#employeePhotosOL").html('<li ><input type="hidden" id="imageFileArray" name="imageFileArray" value="'
												+ statusMsg.split('##')[0]
												+ '" />'
												+' <a class="tooltip" href="'+imageSrc+'" > <img class="imgtooltip" src="'+imageSrc+'" width="120px" height="160px" /> </a>'
												+ "</li>");
						$('#employeePhotoProgressBarDiv').remove();
						$('img.deleteImg').each(function(i){
			   				$(this).show();
			   			});
						imageMouseover();
					}
				},'json');
	};
	
	// 创建进度条DIV
	function createEmployeePhotosDIV(fileName) {
		$("#employeePhotoDiv").append("<div id='employeePhotoProgressBarDiv'><div id='progressBarIn' style='width: 0; font-size:10px;color:gray; height: 12px; background-color: #d4e4ff; margin: 0; padding: 0;'></div>"
				+"<span id='progressSize'></span><div id='msgContent'></div></div>");
	};

	// 更新进度条状态
	function upLoadPhotosProgressbar(len, total) {
		var lengthString = '';
		var totalString = '';
		var percetage = 0;

		// 转换Len单位
		if (len / 1024 / 1024 > 1) {
			lengthString = Math.round(len / 1024 / 1024) + 'M';
		} else if (len / 1024 > 1) {
			lengthString = Math.round(len / 1024) + 'K';
		} else {
			lengthString = len + 'B';
		}

		// 转换Total单位
		if (total / 1024 / 1024 > 1) {
			totalString = Math.round(total / 1024 / 1024) + 'M';
		} else if (len / 1024 > 1) {
			totalString = Math.round(total / 1024) + 'K';
		} else {
			totalString = total + 'B';
		}

		if (total != 0) {
			percetage = len / total;
		}

		$('#progressBarIn').css('width', (Math.round(percetage * 21.1)) + 'px');
		$('#progressBarIn').html((Math.round(percetage * 100)) + '%');
		$('#progressSize').html(lengthString + '/' + totalString);
	};
	
	// 校验邮箱
	function validate_email_ajax(){
		cleanError('email1');
		var flag = 0;
		$.ajax({
			url : "employeeAction.do?proc=checkEmail_ajax&email1=" + $('#email1').val() + "&employeeId=" + $('#employeeId').val(), 
			type: 'POST',
			async:false,
			success : function(data){
				if(data == "true"){
					flag = 1;
					$("#email1").addClass("error");
					$("#email1LI").append('<label class="error email1_error">&#8226; <bean:message bundle="public" key="error.email.registered" /></label>');
					$("#email1").focus();
				}
			}
		});
		return flag;
	};
</script>

