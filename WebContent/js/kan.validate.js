
function validate(className, required, type, maxlength, minlength, maxRange, minRange) {
	return validate_nowords(className, required, type, maxlength, minlength, false, maxRange, minRange);
};

function validate_nowords (className, required, type, maxlength, minlength, nowords, maxRange, minRange) {
	// Type {select, email, passwrod, passwrodconfirm, numeric, currency, character, common, phone, mobile, idcard, website, ip}
	var language = 'zh';
	var lang_en = false;
	var Browser_Agent=navigator.userAgent;
	//�ж�������Ƿ�Ϊie
	if(Browser_Agent.indexOf("MSIE")!=-1){
		language = navigator.userLanguage||window.navigator.language;
		if (typeof(language) == "undefined"){
			// for IE10
			language = window.navigator.systemLanguage;
		}
		lang_en = language.toLocaleLowerCase().indexOf('en') > -1; 
	}else{
		if(navigator.languages != null && navigator.languages.length > 0 ){
			language = navigator.languages[0];
		}else{
			language = navigator.language;
		}
		lang_en = language.toLocaleLowerCase().indexOf('en') > -1; 
	}
	
	var value = $("." + className).val();
	var security = "weak";
	var error = "";
	if(required == true && (value == "" || value < 0) ){
		if(type == "currency"){
			if(value == ""){
				error = lang_en ? "Can not be empty;" : "����Ϊ�գ�";
			}
		}else{
			if(value == "" || value < 0){
				error = lang_en ? "Can not be empty;" : "����Ϊ�գ�";
			}
		}
	}
	
	if(required == true && $("." + className).hasClass(hintClass)){
		error = lang_en ? "Can not be empty;" : "����Ϊ�գ�";
	}
	
	if(required == true){
		if(type == "select" && (value == "" || value < 1)){
			error = lang_en ? "Please select;" : "��ѡ��";
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "thinking" && (value == "")){
			error = lang_en ? "Invalid;" : "��Ч�ģ�";
		}
	}
	
	if((required == false && value != "") || required == true){
		if(maxlength != 0 && minlength == 0 && value.length > maxlength){
			error = error + ( lang_en ? ("Length less than or equal to " + maxlength + " letters, characters or numbers;" ) : ("������С�ڵ���" + maxlength + "����ĸ���ַ������֣�") );
		}
	}
	
	if((required == false && value != "") || required == true){
		if(maxlength == 0 && minlength != 0 && value.length < minlength){
			error = error + ( lang_en ? ("Length greater than or equal to " + minlength + " letters, characters or numbers;") : ("��������ڵ���" + minlength + "����ĸ���ַ������֣�") );
		}
	}
	
	if((required == false && value != "") || required == true){
		if(maxlength != 0 && minlength != 0 && (value.length > maxlength || value.length < minlength)){
			error = error + ( lang_en ? ("Length greater than or equal to " + minlength + " and less than or equal to " + maxlength + " letters, characters or numbers;" ) : ("��������ڵ���" + minlength + "����С�ڵ���" + maxlength + "����ĸ���ַ������֣�") );
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "numeric"){
			if(/[^0-9]+/.test(value)){
				error = error + (lang_en ? "Please enter an integer;" : "������������");
			}else{
				//��֤��С
				var flag = true;
				if(maxRange != 0 || minRange != 0){
					if(value > maxRange){	
						flag = false;
					}
					if(value < minRange){
						flag = false;
					}
				}
				if(!flag){
					error = error + (lang_en ? ("Please enter an integer between " + minRange + " ~ " + maxRange + ";") : "�������������� " + minRange + " ~ " + maxRange + "��");
				}
			}
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "currency" ){
			if(/[^0-9]+/.test(value.replace(/\./,'').replace(/\-/,''))){
				error = error + (lang_en ? "Please enter a decimal;" : "������С����");
			}else if(value.indexOf(".") >= 0 && value.length - value.indexOf(".") - 1 > 4){
				error = error + (lang_en ? "Small digital please control in 4 or less than 4;" : "С��λ�������4λ����4λ���ڣ�");
			}else{
				//��֤��С
				var flag = true;
				if(maxRange != 0 || minRange != 0){
					if(value > maxRange){	
						flag = false;
					}
					if(value < minRange){
						flag = false;
					}
				}
				if(!flag){
					error = error + (lang_en ? ("Please enter a decimal number between " + minRange + " ~ " +  maxRange + ";") : "������С������ " + minRange + " ~ " + maxRange + "��");
				}
			}
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "character" && /[^\w\.\/]/ig.test(value)){
			error = error + (lang_en ? "Please input character;" : "�������ַ���");
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "phone" && (/[^0-9]+/.test(value.replace(/\-/g,'')) || validatePhone(value))){
			error = error + (lang_en ? "Please input the correct telephone formats, for example: 64282085021-64282085021-64282085-201;" : "��������ȷ�绰��ʽ�����磺64282085��021-64282085��021-64282085-201��");
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "mobile" && (/[^0-9]+/.test(value) || value.length != 11 || value.indexOf("1") != 0)){
			error = error + (lang_en ? "Please input the correct mobile phone format, for example: 18930534082;" : "��������ȷ�ֻ���ʽ�����磺18930534082��");
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "idcard" && (/[^0-9]+/.test(value.replace(/\X/i,'')) || value.length != 18 
				|| (value.indexOf("X") != -1 && value.indexOf("X") != 17) || (value.indexOf("x") != -1 && value.indexOf("x") != 17))){
			error = error + (lang_en ? "Please enter a valid identity card format, for example: 31010119800101123X;" : "��������ȷ���֤��ʽ�����磺31010119800101123X��");
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "website" && (value.indexOf(".") <= 0 || value.lastIndexOf(".") == value.length -1)){
			error = error + (lang_en ? "Please input the correct web site address format, for example: http://www.kangroup.com.cn;" : "��������ȷ��վ��ַ��ʽ�����磺http://www.kangroup.com.cn��");
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "ip" && (/[^0-9]+/.test(value.replace(/\./g,'').replace(/\x/ig,'').replace(/\,/ig,'')) || validateIp(value))){
			error = error + (lang_en ? "Please enter a valid IP address format, for example: 192.168.1.1 or 192.168.x.x;" : "��������ȷ��IP��ַ��ʽ�����磺192.168.1.1����192.168.x.x��");
		}
	}
	
	if((required == false && value != "") || required == true){
		if(type == "email" && (value.indexOf("@") <= 0 || value.indexOf(".") <= 0 || value.indexOf("@") > value.lastIndexOf("."))){
			error = error + (lang_en ? "Please enter a valid email address, for example: admin@i-click.com;" : "��������ȷ���ʼ���ַ�����磺admin@i-click.com��");
		}
	}
	
	if(type == "passwordconfirm" && value != $("." + className.replace('confirm', '')).val()){
		error = error + (lang_en ? "Please ensure that the password and the confirmation of identical input;" : "��ȷ������������ȷ������һ�£�");
	}
	
	// ��, �е�, ǿ, ��ȫ
	if(type == "password"){
		var weight = 0;
		
		if( /(?=^.{8,25}$)(?=(?:.*?\d))(?=.*[a-z])(?=(?:.*?[A-Z]){1})(?=(?:.*?[!@#$%*()_+^&}{:;?.]){1})(?!.*\s)[0-9a-zA-Z!@#$%*()_+^&]*$/.test(value)){
			weight = weight + 3;
		}
		if(value.length >= 18){
			weight = weight + 1;
		}
		/*
		if(/\d/.test(value)){
			weight = weight + 1;
		}
		
		if(/[a-z]/.test(value)){
			weight = weight + 1;
		}
		
		if(/[A-Z]/.test(value)){
			weight = weight + 1;
		}
		
		if(!/^[_0-9a-zA-Z]{0,20}$/.test(value)){
			weight = weight + 1;
		}
		*/
		if(value.length < 8){
			security = "weak";
		}else if(weight == 1){
			security = "weak";
		}else if(weight == 2){
			security = "medium";
		}else if(weight == 3){
			security = "strong";
		}else if(weight == 4){
			security = "security";
		}
		
		if(weight<3){
			flag = false;
			error = error + (lang_en ? "longer than eight characters,must contain capital letters, lowercase letters, numbers and symbols." : "����8λ�����������д��ĸ,Сд��ĸ,���ֺͷ���");
		}
	}
	
	// ɾ��������������
	$("." + className + "_error").remove();
	
	// �ж��Ƿ���Ҫ��ʾ������������
	if(nowords == false){
		if(type == "password"){
			if(value.length != 0 && error == ""){
				$("." + className).after('<label class="error ' + className +'_error"><img src="images/password/password_' + security + '.png" /></label>');
			}else if(value.length != 0 && error != ""){
				$("." + className).after('<label class="error ' + className +'_error"><img src="images/password/password_' + security + '.png" /> &#8226; ' + error + '</label>');
			}else{
				$("." + className).after('<label class="error ' + className +'_error">&#8226; ' + error + '</label>');
			}
		}else if(error != ""){
			if(className.indexOf('login_') >= 0){
				$("." + className).after('<label class="loginError ' + className +'_error">&#8226; ' + error + '</label>');
			}else{
				$("." + className).after('<label class="error ' + className +'_error">&#8226; ' + error + '</label>');
			}
		}
	}
	
	// ������ڴ���ʹ�ռ��쳣��ʾ
    $("." + className).removeClass("error");
    if(error != ""){
    	$("." + className).addClass("error");
    }
 
    if(error != ""){
    	return 1;
    }else{
    	return 0;
    }
};

function cleanError(className){
	$('.' + className + '_error').remove();
	$('.' + className).removeClass('error');
};

function addError(className, error){
	$('.' + className).after('<label class="error ' + className + '_error">&#8226; ' + error + '</label>');
	$('.' + className).addClass("error");
};

function validatePhone (value) {
	var sections = value.split("-");
	
	if(sections.length == 1){
		if(sections[0].length < 7 || sections[0].length > 8){
			return true;
		}
		if(sections[0].indexOf("0") == 0){
			return true;
		}
	}
	
	if(sections.length > 1){
		if(sections[0].length < 3 || sections[0].length > 4){
			return true;
		}
		if(sections[0].indexOf("0") != 0){
			return true;
		}
		if(sections[1].length < 7 || sections[1].length > 8){
			return true;
		}
		if(sections[1].indexOf("0") == 0){
			return true;
		}
	}
	
	if(sections.length > 2){
		if(sections[2].length > 6){
			return true;
		}
		if(sections[2].indexOf("0") == 0){
			return true;
		}
	};
	
	return false;
};

function validateIp (value) {
	var ips = value.split(",");
	
	for (i = 0; i < ips.length; i++) {
		var sections = ips[i].split(".");
		
		if(sections.length != 4){
			return true;
		}
		
		if(sections[0] > 255 || sections[1] > 255 || sections[2] > 255 || sections[3] > 255 
				|| sections[0] == '' || sections[1] == '' || sections[2] == '' || sections[3] == ''){
			return true;
		}
	}
	
	return false;
};
