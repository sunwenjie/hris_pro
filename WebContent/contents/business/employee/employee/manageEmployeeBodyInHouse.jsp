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
			// ��Ӳ���
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
		* define  �������
		**/
		
		// ��Աid 
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
		* loadHtml ����ҳ��
		**/
		// ��ʼ��ʡ�ݿؼ�
		provinceChange('residencyProvinceId', 'viewObject', temp_residencyCityId, 'residencyCityId');
		// ��ʼ�����ſؼ�
		branchChange('branch', null, '<bean:write name="employeeForm" property="owner" />', 'owner');
		// ����Tabҳ��
		loadHtml('#special_info', 'employeeAction.do?proc=list_special_info_html&employeeId=' + employeeId, disable);
		
		/**
		* bind ���¼�
		**/
		
		// ��ʡ��Change�¼�
		$('.residencyProvinceId').change( function () { 
			provinceChange('residencyProvinceId', 'modifyObject', 0, 'residencyCityId');
		});
		
		// �󶨲���Change�¼�
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// �� ����˾�ҵ���֤Change�¼�
		$('.hasForeignerWorkLicence').change(function(){
			if(this.value==1){
				$('#foreignerWorkLicenceNoLI,#foreignerWorkLicenceEndDateLI').show();
			}else{
				$('#foreignerWorkLicenceNoLI,#foreignerWorkLicenceEndDateLI').hide();
			}
		});
		
		// �� ��ס֤Change�¼�
		$('.hasResidenceLicence').change(function(){
			if(this.value==1){
				$('#residenceNoLI,#residenceStartDateLI,#residenceEndDateLI').show();
			}else{
				$('#residenceNoLI,#residenceStartDateLI,#residenceEndDateLI').hide();
			}
		});
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if(getSubAction() == 'modifyObject'){
				// �ϴ�������Ч
				$('#uploadAttachment').attr("disabled", false);
				$('#uploadAttachment').removeClass("disabled");
				// Enableɾ��Сͼ��
	   			$('img[id^=warning_img]').each(function(i){
	   				$(this).show();
	   			});
	   			$('img[id^=disable_img]').each(function(i){
	   				$(this).hide();
	   			});
	   			// ������ʾģ̬����ѡcheckbox���ò��⿪
	   			$('div[id="skill_level_div"] input[type="checkbox"]').each(function(i){
	   				if($(this).attr("checked")){
	   					$(this).attr("disabled", "disabled");
	   				}
	   			});
			}
		});
		
		/**
		* init ��ʼ�����JS
		**/
		
		//��ʼ������˾�ҵ���֤
		$('.hasForeignerWorkLicence').change();
		
		//��ʼ��
		$('.hasResidenceLicence').change();
		
		//��ʼ�� ���� ʹ�� ckeditor 
		$('ol:has(.resumeZH)').removeClass("auto").addClass("static");//ռһ����
		$('ol:has(.resumeEN)').removeClass("auto").addClass("static");//ռһ����
		$('.resumeZH').ckeditor();
		$('.resumeEN').ckeditor();
		
		// �鿴ģʽ �������
		if(getSubAction() == 'viewObject'){
			$('#uploadAttachment').attr("disabled", true);
			$('#uploadAttachment').addClass("disabled");
		};
		
		/**
		* other ��������
		**/
		var status = '<bean:write name="employeeForm" property="status"/>';
		var containValidContractFlag = '<bean:write name="employeeForm" property="containValidContractFlag"/>';
		
		// ���Ϊ����ļ�����ߡ���ѡ��״̬��������ְ��
		if (( status == 2 ) || ( status == 4 ) )
		{
		   $('#status option[value="1"]').remove();
		   $('#status option[value="3"]').remove();
		}
		// ���Ϊ����ְ���������޸�Ϊ����ļ�����ߡ���ѡ��
		else if( status == 1 ){
			$('#status option[value="2"]').remove();
			$('#status option[value="4"]').remove();
		}
		
		// ����С���Ӷ״̬��Ϊ��ְ�ķ���Э����������ְ��
		$('#status').change(function(){
			cleanError('status');
			var tempStatus = $('#status').val();
			
			if( containValidContractFlag == 1 && tempStatus == 3){
				addError('status', '��<logic:equal name='role' value='1'>��Ա</logic:equal><logic:equal name='role' value='2'>Ա��</logic:equal>���ڷ����ڼ䣬���ܡ���ְ����');
				$('#status').val(status);
			}
			
		});
		
		// ����ʵʱУ��
		$("#email1").keyup( function(){
			validate_email_ajax();
		});
		
		$("#certificateNumber").blur(function(){
			cleanError('certificateNumber');
			
			// ��������֤�����Һ�����Ч
			if( ($('#certificateType').val() == 1) && (validate("certificateNumber", true, "idcard", 100, 0) == 0) )
			{
				// ��֤�Ƿ����
				$.post("employeeAction.do?proc=get_count_byCertificateNumber",{"certificateNumber":$("#certificateNumber").val(),"id":$("#id").val()},function(data){
					if(data==0){
						var card = new clsIDCard($('#certificateNumber').val());
						$('#birthday').val(card.GetBirthDate());
						$('#salutation').val(card.GetSex()==0?2:1);
					}else{
						addError('certificateNumber','���֤�ѱ�ע��');
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
	* �Զ��庯��
	**/
	function validateIDCard(){
		// ɾ��������������
		$("#certificateNumber_error").remove();
		//֤������Ϊ���֤ ���� �������ݲ�Ϊ�ղ���֤
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
		// ɾ��������������
		$(".bankId_error").remove();
		$("#bankId").removeClass('error');
		//
		if($('#bankId').val()== '0' && $('#bankAccount').val()!=''){
			$("#bankId").addClass('error');
			$("#bankId").after('<label class="error bankId_error">&#8226; ��ѡ������ID</label>');
			$("#bankId").focus();
			return 1;
		}else{
			return 0;
		}
	};
	
	function validateUserName(){
		// ��Ϊ��
		var flag = 0;
		if($('#username').val()!='' && $("#email1").val()=='' && $("#email2").val()=='' ){ 
			flag = flag + validate('email1',true, 'email', 0 ,0, 0,0);
			flag = flag + validate('user_username', true, 'common', 20, 2);
		}
		// �ʼ�1��Ϊ��
		else if($('#username').val()!='' && $("#email1").val()!=''){
			flag = flag + validate('email1',true, 'email', 0 ,0, 0,0);
			flag = flag + validate('user_username', true, 'common', 20, 2);
		}
		// �ʼ�2��Ϊ��
		else if($('#username').val()!='' && $("#email2").val()!=''){
			flag = flag + validate('email2',true, 'emai2', 0 ,0, 0,0);
			flag = flag + validate('user_username', true, 'common', 20, 2);
		}
		return flag;
	};
	
	function validateBankAccount(){
		// ɾ��������������
		$(".bankAccount_error").remove();
		$("#bankAccount").removeClass('error');
		//
		if($('#bankId').val()!= '0' && $('#bankAccount').val()==''){
			$("#bankAccount").addClass('error');
			$("#bankAccount").after('<label class="error bankAccount_error">&#8226; �����������˺�</label>');
			$("#bankAccount").focus();
			return 1;
		}else{
			return 0;
		}
	};
	
	//phone2����ж��ž���ӻس�
	function fixPhone2(){
		/* var phone2 = $("#phone2").val();
		if(phone2!=''){
			var targetValue = "";
			var phone2s="";
			if(phone2.indexOf(",") > 0){
				var phone2s = phone2.split(",");
			}else if( phone2.indexOf("��") > 0){
				var phone2s = phone2.split("��");
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

    // Esc�����¼� - ���ص�����
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
			var tooltip = "<div style='position: absolute;z-index: 1001';  id='tooltip'><img src='"+ $(this).find('img').attr('src') +"'/><\/div>"; //���� div Ԫ��
			$("body").append(tooltip);	//����׷�ӵ��ĵ���
			// ����һ����ʱλ�ô��ͼƬ��С
			$("<img/>").attr("src", $(this).find('img').attr('src')).load(function() {
				y = this.height;
			});
			$("#tooltip")
				.css({
					"top": (e.pageY-y)+"px",
					"left": (e.pageX+x)+"px"
				}).show("fast");	  //����x�����y���꣬������ʾ
	    }).mouseout(function(){
			this.title = this.myTitle;	
			$("#tooltip").remove();	 //�Ƴ� 
	    }).mousemove(function(e){
			$("#tooltip")
				.css({
					"top": (e.pageY-y+250) + "px",
					"left":  (e.pageX+x)  + "px"
				});
		});

	}
	
	function initPhoto(){
		//�Ƴ��ı���
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
			// �ļ��ϴ�Action
			action : 'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom,
			// ����
			name : 'file',
			// �Զ��ϴ�
			autoSubmit : true,
			// ����Text��ʽ����
			responseType : false,
			// �ϴ�����
			onSubmit : function(filename, ext) {
				createEmployeePhotosDIV(filename);
				disableLinkById(id);
				//$("#progress_debug").append('action = '+'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom);
				setTimeout("ajaxBackStateInfo(true,'"+"uploadFileAction.do?proc=getStatusMessage&postfixRandom="+postfixRandom+"',200)");
				// �ϴ�������
				// $('#' + id).disable();
			},

			// �ϴ���ɺ�ȡ���ļ���filenameΪ����ȡ�õ��ļ�����msgΪ���������ص���Ϣ
			onComplete : function(filename, msg) {
				// �ϴ�����
				// $('#' + id).enable();
				enableLinkById(id);
			}
		});
		return upLoadPhoto;
	};
	
	function ajaxBackStateInfo(first,progressURL) {
		// Ajax���ò�ˢ�µ�ǰ�ϴ�״̬
		$.post(progressURL+'&first=' + first+ '&date=' + new Date(),null,
				function(result) {
					var obj = result;
					var readedBytes = obj["readedBytes"];
					var totalBytes = obj["totalBytes"];
					var statusMsg = obj["statusMsg"];
					upLoadPhotosProgressbar(readedBytes,totalBytes);

					//$("#progress_debug").append('info = '+obj["info"]+' readedBytes'+readedBytes+'    totalBytes='+totalBytes+'    progressURL='+progressURL+'<br/>');
					
					// ����״̬
					if (obj["info"] == "0") {
						setTimeout("ajaxBackStateInfo(false,'"+progressURL+"',200)");
					}
					// �ϴ�ʧ�ܣ���ʾʧ��ԭ��
					else if (obj["info"] == "1") {
						alert(obj["statusMsg"]);
						$('#btnAddAttachment').attr("disabled", false);
						// ɾ���ϴ�ʧ�ܵ��ļ��ͽ�����Ϣ
						$('#uploadFileLi').remove();
					}
					// �ϴ����
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
	
	// ����������DIV
	function createEmployeePhotosDIV(fileName) {
		$("#employeePhotoDiv").append("<div id='employeePhotoProgressBarDiv'><div id='progressBarIn' style='width: 0; font-size:10px;color:gray; height: 12px; background-color: #d4e4ff; margin: 0; padding: 0;'></div>"
				+"<span id='progressSize'></span><div id='msgContent'></div></div>");
	};

	// ���½�����״̬
	function upLoadPhotosProgressbar(len, total) {
		var lengthString = '';
		var totalString = '';
		var percetage = 0;

		// ת��Len��λ
		if (len / 1024 / 1024 > 1) {
			lengthString = Math.round(len / 1024 / 1024) + 'M';
		} else if (len / 1024 > 1) {
			lengthString = Math.round(len / 1024) + 'K';
		} else {
			lengthString = len + 'B';
		}

		// ת��Total��λ
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
	
	// У������
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

