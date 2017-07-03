function createUploadObject(id, fileType, attachmentFolder) {
	var postfixRandom = generateMixed(6);
	var uploadObject = new AjaxUpload(id, {
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
			createFileProgressDIV(filename);
			setTimeout("ajaxBackState('uploadFileAction.do?proc=getStatusMessage&postfixRandom=" + postfixRandom + "')", 500);
			// �ϴ�������
			$('#' + id).hide();
			$('#' + id).after("<span id='uploading'>�ϴ���...</span>");
		},

		// �ϴ���ɺ�ȡ���ļ���filenameΪ����ȡ�õ��ļ�����msgΪ���������ص���Ϣ
		onComplete : function(filename, msg) {
			// �ϴ�����
			$('#' + id).show();
			$('#uploading').remove();
		}
	});

	return uploadObject;
};

function ajaxBackState(progressURL) {
	// Ajax���ò�ˢ�µ�ǰ�ϴ�״̬
	$.post(progressURL+'&date=' + new Date(), null, 
		function(result) {
			var obj = result;
			var readedBytes = obj["readedBytes"];
			var totalBytes = obj["totalBytes"];
			var statusMsg = obj["statusMsg"];

			progressbar(readedBytes, totalBytes);
			
			// ����״̬
			if (obj["info"] == "0") {
				setTimeout("ajaxBackState('" + progressURL + "')", 100);
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
				$('#uploadAttachment').attr("disabled", false);
				$("#attachmentsOL").html($("#attachmentsOL").html()
					+ '<li><input type="hidden" id="attachmentArray" name="attachmentArray" value="'
					+ statusMsg
					+ '" /><img onclick=\"removeAttachment(this);\" src=\"images/warning-btn.png\"> &nbsp; '
					+ statusMsg.split('##')[1]
					+ "</li>");
				$('#uploadFileLi').remove();

				if ($('#numberOfAttachment').html() != null) {
					$('#numberOfAttachment').html(eval(parseInt($('#numberOfAttachment').html()) + 1));
				}
			}
		},'json');
};

// ���½�����״̬
function progressbar(len, total) {
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

	$('#progressBarIn').css('width', (Math.round(percetage * 198)) + 'px');
	$('#progressBarIn').html((Math.round(percetage * 100)) + '%');
	$('#progressSize').html(lengthString + '/' + totalString);
};

function createFileProgressDIV(fileName) {
	$("#attachmentsOL").html($("#attachmentsOL").html()
		+ '<li id="uploadFileLi" style="margin: 5px 0 0 0;"><div id="progressBarOut" style="text-align: center; width: 200px; height: 14px; padding: 1px 1px 1px 1px; font-size: 10px; border: solid #d4e4ff 1px;"><div id="progressBarIn" style="width: 0; height: 12px; background-color: #d4e4ff; margin: 0; padding: 0;"></div></div><div id="fileNameDiv" style="margin: 0 5px 0 5px; border: 0;">'
		+ fileName
		+ '</div> &nbsp; <div id="progressSize" style="margin: 0; border: 0;"></div></li>');
};

function createFileProgressPompDIV(fileName) {
	$("#uploadProgressPompDIV").html(
		'<span id="fileNameSpan">'
		+ fileName
		+ '</span> <span id="progressSize"></span><div id="msgContent"><input type="hidden" id="importSuccess"></input></div>');
};

function removeAttachment(name) {
	if (confirm("ɾ��������")) {
		$(name).parent('li').remove();

		if ($('#numberOfAttachment').html() != null) {
			$('#numberOfAttachment').html(eval(parseInt($('#numberOfAttachment').html()) - 1));
		}
	}
};

function createUploadExcel(id, uploadFileURL, progressURL, fileType, attachmentFolder,confirmMsg) {
	var postfixRandom = generateMixed(6);
	var uploadObject = new AjaxUpload(id, {
		// �ļ��ϴ�Action
		action : uploadFileURL + '&fileItem=1&extType=' + fileType + '&folder=' + attachmentFolder + '&postfixRandom=' + postfixRandom, 
		// ����
		name : 'file',
		// �Զ��ϴ�
		autoSubmit : true,
		// ����Text��ʽ����
		responseType : false,
		// �ϴ�����
		onSubmit : function(filename, ext) {
			// ���������ϴ����ļ���ʽ
			if (!(ext && /^(xlsx)$/.test(ext))) {
				alert('��ѡ��2007�����ϰ汾��Excel�ļ���');
				return false;
			}
			if(confirmMsg && !confirm(confirmMsg)){
				return false;
			}
			
			//��ӱ�ע
			if($('#description').val() != undefined && $('#description').val() !=null){
				var description = $('#description').val();
				this.setData({description:description});
			}
		
			
			createFileProgressPompDIV(filename);
			setTimeout("ajaxBackStateExcel('"+progressURL + "&postfixRandom=" + postfixRandom+"','"+ filename+"')",500);
			$('#btnImportExcelFile').hide();
			$('#btnHandle').show();
		},

		// �ϴ���ɺ�ȡ���ļ���filenameΪ����ȡ�õ��ļ�����msgΪ���������ص���Ϣ
		onComplete : function(filename, msg) {
		}
	});

	return uploadObject;
};

function ajaxBackStateExcel(progressURL, filename) {
	$.post(progressURL + '&date=' + new Date(), null, function(result) {
		var obj = result;
		readedBytes = obj["readedBytes"];
		totalBytes = obj["totalBytes"];
		statusMsg = obj["statusMsg"].replaceAll('\n', '<br/>');
		errorMsg = obj["errorMsg"].replaceAll('\n', '<br/>');
		warningMsg = obj["warningMsg"].replaceAll('\n', '<br/>');
		extraMsg = obj["extraMsg"].replaceAll('\n', '<br/>');
		errorMsgHTML = "<font color='red'>" + errorMsg + "</font>";
		warningMsgHTML = "<font color='#c8d406'>" + warningMsg + "</font>";
		extraMsgHTML = "<font color='#32CD32'>" + extraMsg + "</font>";
		$('#importSuccess').val('0');
		progressbar(obj["readedBytes"], obj["totalBytes"]);
		//$('#debugAcctachment').append("info="+obj["info"]+"  progressURL="+progressURL);
		// ������
		if (obj["info"] == "0") {
			setTimeout("ajaxBackStateExcel('"+progressURL+"','"+filename+"')",500);
		}
		// ʧ�ܣ���ʾʧ��ԭ��
		else if (obj["info"] == "1") {
			if(errorMsg != ''){
				$("#msgContent").html($("#msgContent").html() + date2str(new Date()) + " # " + errorMsgHTML);
			}
			$('#btnImportExcelFile').show();
			$('#btnHandle').hide();
		}
		// �ϴ����
		else if (obj["info"] == "2") {
			setTimeout("ajaxBackStateExcel('"+progressURL+"','"+filename+"')",500);
			$("#attachmentsDiv").html('<span>' + filename + ' ' + $('#progressSize').html() + "</span>");
			$("#msgContent").html($("#msgContent").html() + date2str(new Date()) + " # �ļ��ϴ��ɹ���<br/>");
			$("#fileNameSpan").remove();
			$("#progressSize").remove();
		} 
		// ������
		else if (obj["info"] == "3") {
			setTimeout("ajaxBackStateExcel('"+progressURL+"','"+filename+"')",500);
			//var arr = $("#msgContent").html().split("#");
			if(statusMsg != ''){
//				if(arr[arr.length-1].replaceAll('<br>', '').replace(/^\s+|\s+$/g,"")!=statusMsg){
//					$("#msgContent").html($("#msgContent").html() + date2str(new Date()) + " # " + statusMsg+"<br/>");
//				}else{
//					$("#msgContent").html($("#msgContent").html().substring(0,$("#msgContent").html().indexOf(statusMsg)-22) + date2str(new Date()) + " # " + statusMsg+"<br/>");
//				}
				
				$("#msgContent").html($("#msgContent").html() + date2str(new Date()) + " # " + statusMsg+"<br/>");
			}
			if(errorMsg != ''){
				$("#msgContent").html($("#msgContent").html() + date2str(new Date()) + " # " + errorMsgHTML+"<br/>");
			}
			if(warningMsg != ''){
				$("#msgContent").html($("#msgContent").html() + date2str(new Date()) + " # " + warningMsgHTML+"<br/>");
			}
			if(extraMsg != ''){
				$("#msgContent").html($("#msgContent").html() + date2str(new Date()) + " # " + extraMsgHTML);
			}
		} 
		// ����ɹ�
		else if (obj["info"] == "4") {
			//����ɹ���Ҫˢ��table
			$('#importSuccess').val('1');
			if(statusMsg != ''){
				$("#msgContent").html($("#msgContent").html() + date2str(new Date()) + " # " + statusMsg);
			}else{
				$("#msgContent").html($("#msgContent").html() + date2str(new Date()) + " # �ļ�����ɹ���");
			}
			
			$('#btnImportExcelFile').show();
			$('#btnHandle').hide();
		}
	},"json");
};

var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

function generateMixed(n) {
     var res = "";
     
     for(var i = 0; i < n ; i ++) {
         var id = Math.ceil(Math.random()*35);
         res += chars[id];
     }
     
     return res;
};

String.prototype.replaceAll = function(s1, s2) { 
    return this.replace(new RegExp(s1, "gm"), s2); 
};

function date2str(d) {
    var ret = d.getFullYear() + "-";
    ret += ("00" + (d.getMonth() + 1)).slice(-2) + "-";
    ret += ("00" + d.getDate()).slice(-2) + " ";
    ret += ("00" + d.getHours()).slice(-2) + ":";
    ret += ("00" + d.getMinutes()).slice(-2) + ":";
    ret += ("00" + d.getSeconds()).slice(-2);
    return ret;
};