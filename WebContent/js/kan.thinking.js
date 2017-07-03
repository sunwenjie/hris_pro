var hintClass = 'inputFormatHint';
var language = 'zh';
language = navigator.userLanguage||window.navigator.language;
var lang_en = language.toLocaleLowerCase().indexOf('en') > -1 ;

function kanThinking_column(nameClass, idClass, action, otherORM, callback){
	var typeHint = lang_en ? "Enter the keyword view tips..." : "����ؼ��ֲ鿴��ʾ...";
	return kanThinking_column_defTypeHint(nameClass, idClass, action, otherORM, callback,typeHint);
}

function kanThinking_column_defTypeHint(nameClass, idClass, action, otherORM, callback,typeHint) {

	$('.' + nameClass).focus(function() {
		if ($(this).hasClass(hintClass)) {
			$(this).val('');
			$(this).removeClass(hintClass);
		}
	});
	
	$('.' + nameClass).blur(function () { 
		if ($(this).val() == '') {
			$('.' + nameClass).val(typeHint).addClass(hintClass);
			$('.' + idClass).val('');
		}
	});

	var value = $('.' + nameClass).val().trim();
	$.ajax({
		url : action + '&date=' + new Date(),
		data : '',
		dataType : 'json',
		success : function(baseViews) {
			var idValue = $('.'+idClass).val();
			if(idValue){
				$.each(baseViews,function(index,item){
					if(item){
						if(item['id']==idValue){
							value = item['name'];
							$('.'+nameClass).val(item['name']);
							otherORM_Fun(event,item);
						}
					}
				});
			}
			  
			$('.' + nameClass).autocomplete(baseViews, {
				formatItem : function(item) {
					return $('<div/>').text(item['name']).html();
				},
				formatResult : function(item) {
					return item.name;
				},
				matchContains : true
			}).result(function(event, item) {
				$('.' + idClass).val(item.id);
				// ����ӳ�䣬ʹ��Jason���󴫲�
				otherORM_Fun(event,item);
				// �ص�
				eval(callback);
			});

			if (value == '' || value == typeHint) {
				$('.' + nameClass).val(typeHint).addClass(hintClass);
			} else {
				$('.' + nameClass).val(value).removeClass(hintClass);
			}
		}
	});
	
	var otherORM_Fun = function(event,item){
		if(otherORM && !jQuery.isEmptyObject(otherORM)){
			$.each(otherORM, function(idORMClass, property){
				var $ormObject = $('.'+idORMClass);
				if($ormObject[0]){
					if($ormObject[0].tagName == 'INPUT'){
						if($ormObject.attr('type')=='text' || $ormObject.attr('type')== 'hidden'){
							$ormObject.val(item[property]);
						}
					}else if($ormObject[0].tagName == 'SELECT'){
						$ormObject.val(item[property]);
					}	
				}
									
			});
		}else if( jQuery.isFunction(otherORM) ){
			//����ǻص�������ֱ�ӵ�������ص�����
			otherORM(event, item);
		}
	};
};
/**
 * 
 * @param nameClass �������ؼ���Class
 * @param idClass   �Զ����id�ؼ���Class
 * @param action    ���ʵ�URL
 * @returns
 */
function kanThinking_column_ajax(nameClass, idClass, action,otherORM,callback) {
	
	var typeHint = lang_en ? "Enter the keyword view tips..." : "����ؼ��ֲ鿴��ʾ...";

	$('.' + nameClass).focus(function() {
		if ($(this).hasClass(hintClass)) {
			$(this).val('');
			$(this).removeClass(hintClass);
		}
	});
	
	$('.' + nameClass).blur(function () { 
		if ($(this).val() == '') {
			$('.' + nameClass).val(typeHint).addClass(hintClass);
		}
	});
	
	$('.'+nameClass).autocomplete(action,{
		formatItem : function(item) {
			return $('<div/>').text(item.name).html();
		},
		dataType: "json",
		parse: function(data) {
			return $.map(data, function(row) {
				return {
					data: row,
					value: row.name,
					result: row.name
				};
			});
		},
		matchSubset : true,
		matchContains : true//�����Ƚ�ʱ�Ƿ�Ҫ���ַ����ڲ��鿴ƥ��,��ba�Ƿ���foo bar�е�baƥ��.ʹ�û���ʱ�Ƚ���Ҫ
	}).result(function(event, item) {
		if(idClass){$('.' + idClass).val(item.id);}
		// ����ӳ�䣬ʹ��Jason���󴫲�
		otherORM_Fun(event,item);
		// �ص�
		eval(callback);
	});
	
	if ($('.' + nameClass).val().trim() == '' || $('.' + nameClass).val().trim() == typeHint) {
		$('.' + nameClass).val(typeHint).addClass(hintClass);
	} else {
		$('.' + nameClass).removeClass(hintClass);
	}
	
	var otherORM_Fun = function(event,item){
		if(otherORM && !jQuery.isEmptyObject(otherORM)){
			$.each(otherORM, function(idORMClass, property){
				var $ormObject = $('.'+idORMClass);
				if($ormObject[0]){
					if($ormObject[0].tagName == 'INPUT'){
						if($ormObject.attr('type')=='text' || $ormObject.attr('type')== 'hidden'){
							$ormObject.val(item[property]);
						}
					}else if($ormObject[0].tagName == 'SELECT'){
						$ormObject.val(item[property]);
					}	
				}
									
			});
		}else if( jQuery.isFunction(otherORM) ){
			//����ǻص�������ֱ�ӵ�������ص�����
			otherORM(event, item);
		}
	};
}


