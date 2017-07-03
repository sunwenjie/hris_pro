<%@page import="com.kan.base.web.renders.security.PositionGraderListRender"%>
<%@page import="com.kan.base.web.renders.security.BranchListRender"%>
<%@page import="com.kan.base.web.renders.security.GroupRender"%>
<%@page import="com.kan.base.web.renders.security.PositionRender"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<style type="text/css">
	#tabContent1,#tabContent2,#tabContent3,#tabContent4 ,#tabContent5,#tabContent6{
		height: 200px;
		overflow: auto;
	}
	.contact-body{
		min-height: 50px;
		border: 1px solid #aaa;
		padding: 10px; 
		display: block;
		overflow: auto;
		-webkit-border-radius: 3px; 
		-moz-border-radius: 3px; 
		-o-border-radius: 3px; 
		border-radius: 3px;
	}
	
	span.holder span.bit-box { padding-right: 15px !important; position: relative;}
	span.holder span.bit-box { -moz-border-radius: 6px; -webkit-border-radius: 6px; border-radius: 6px;  padding: 1px 5px 2px; }
	span.holder span.bit-box, span.holder span.bit-input input { font: 13px "黑体", "Calibri"; }
	span.holder span { float: left; list-style-type: none; margin: 4px 5px 0px 0px !important; }
	span.holder { padding: 0}
	span.holder span.bit-box a.closebutton { position: absolute; right: 4px; top: 5px; display: block; width: 7px; height: 7px; font-size: 1px; background: url('images/close.gif'); }
	
	span.holder span.bit-box-userId{border: 1px solid #caf3cf; background: #d4efd7;}
	span.holder span.bit-box-positionId{border: 1px solid #CAD8F3; background: #DEE7F8;}
	span.holder span.bit-box-groupId{border: 1px solid #efd5c3; background: #efdfd4;}
	span.holder span.bit-box-branchId{border: 1px solid #f1b2e1; background: #f3daed;}
	span.holder span.bit-box-positionGradeId{border: 1px solid #f5babc; background: #f5d0d0;}
	span.holder span.bit-box-extend{border: 1px solid #f2edc1; background: #f4f2dd;}
	
	div.recLabel span.bit-box { padding-right: 15px !important; position: relative;}
	div.recLabel span.bit-box { -moz-border-radius: 6px; -webkit-border-radius: 6px; border-radius: 6px;  padding: 1px 5px 2px; }
	div.recLabel span.bit-box, span.holder span.bit-input input { font: 13px "黑体", "Calibri"; }
	div.recLabel span { float: left; list-style-type: none; margin: 4px 5px 0px 0px !important; }
	div.recLabel { padding: 0;overflow-X: auto;}
	div.recLabel span.bit-box a.closebutton { position: absolute; right: 4px; top: 5px; display: block; width: 7px; height: 7px; font-size: 1px; background: url('images/close.gif'); }
	div.recLabel span.bit-box {border: 1px solid #d6d3d3; background: #e9e8e8;}
	
</style>

<!-- Module Box HTML: Begins -->
<div class="modal large content hide" id="contactModalId">
    <div class="modal-header" id="contactHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="$('#contactModalId').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label><bean:message bundle="message" key="message.mail.contact.select" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
	    	<div id="tab" > 
				<div class="tabMenu">
					<ul>
						<li id="tabMenu1" onClick="changeTab(1,6)" class="first hover"><bean:message bundle="public" key="menu.table.title.name" /></li>
						<li id="tabMenu2" onClick="changeTab(2,6)" ><bean:message bundle="public" key="menu.table.title.position" /></li>
						<li id="tabMenu3" onClick="changeTab(3,6)" ><bean:message bundle="public" key="menu.table.title.position.group" /></li>
						<li id="tabMenu4" onClick="changeTab(4,6)" ><bean:message bundle="public" key="menu.table.title.branch" /></li>
						<li id="tabMenu5" onClick="changeTab(5,6)" ><bean:message bundle="public" key="menu.table.title.position.grade" /></li>
						<li id="tabMenu6" onClick="changeTab(6,6)" ><bean:message bundle="public" key="menu.table.title.input" /></li>
					</ul>
				</div>
			</div>
		
			<div class="tabContent" >
				<div id="tabContent1" class="kantab" >
					<div style="margin: 0px 0px 0px 10px;">
						<label><bean:message bundle="message" key="message.mail.contact.name" /></label>
						<input type="text" name="contactKeyWord" class="contactKeyWord">
					</div>
				</div>
				<div id="tabContent2" class="kantab" style="display: none">
					<div id="position_info" class="kantab kantree" style="border: none;padding-top: 0px!important;">
						<%= PositionRender.getBasePositionTree( request ) %>
					</div>
				</div>
				<div id="tabContent3" class="kantab" style="display: none">
					<div style="margin: 0px 0px 0px 10px;">
						<%=GroupRender.getBaseGroup( request, null )%>
					</div>
				</div>
				<div id="tabContent4" class="kantab" style="display: none">
					<div style="margin: 0px 0px 0px 10px;">
						<%=BranchListRender.getBaseBranchHtml(request) %>
					</div>
				</div>
				<div id="tabContent5" class="kantab" style="display: none">
					<div style="margin: 0px 0px 0px 10px;">
						<%=PositionGraderListRender.getBasePositionGradeHtml(request) %>
					</div>
				</div>
				<div id="tabContent6" class="kantab" style="display: none">
					<div style="margin: 0px 0px 0px 10px;">
						<label class="contactNum-label"><bean:message bundle="message" key="message.mail.contact.number" /></label>
						<input type="text" name="contactNum" class="contactNum">
					</div>
				</div>
			</div>
		</div>
	
		<div>
			<label><bean:message bundle="message" key="message.mail.contact.selected" /></label>
		</div>
		<div class="contact-body">
			<span class="holder"> 
			</span>
		</div>
  </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	
	// 弹出模态窗口
	function popupContactSearch(){
		$('#contactModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#employeeModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
	kanThinking_column_info("contactKeyWord", "contact_userIds","userAction.do?proc=list_object_json");

	function kanThinking_column_info(nameClass, idClass, action, otherORM,
			callback) {
		var typeHint = lang_en ? "Enter the keyword view tips..." : "输入关键字查看提示...";

		$('.' + nameClass).focus(function() {
			if ($(this).hasClass(hintClass)) {
				$(this).val('');
				$(this).removeClass(hintClass);
			}
		});

		$('.' + nameClass).blur(function() {
			if ($(this).val() == '') {
				$('.' + nameClass).val(typeHint).addClass(hintClass);
			}
		});

		var value = $('.' + nameClass).val().trim();
		$
				.ajax({
					url : action + '&date=' + new Date(),
					data : '',
					dataType : 'json',
					success : function(baseViews) {
						var idValue = $('.' + idClass).val();
						if (idValue) {
							$.each(baseViews, function(index, item) {
								if (item) {
									if (item['id'] == idValue) {
										value = item['name'];
										$('.' + nameClass).val(item['name']);
										otherORM_Fun(event, item);
									}
								}
							});
						}

						$('.' + nameClass)
								.autocomplete(
										baseViews,
										{
											formatItem : function(item) {
												return $('<div/>').text(
														item['name']).html();
											},
											formatResult : function(item) {
												return item.name;
											},
											matchContains : true
										})
								.result(
										function(event, item) {
											//$('.' + idClass).val(item.id);
											var flag = false;
											$(".holder span input.contact_userIds").each(
												function() {
													if ($(this).val() == item.id) {
														// 已存在
														flag = true;
													}
												});
											if (flag == false) {
												$(".holder").append('<span class="bit-box bit-box-userId">'+ item.name
													+ '<a class="closebutton" href="#" onclick="$(this).parent().remove();recLabelChange(); "></a><input value="'
													+item.id+'" type="hidden" class="contact_userIds" name="userIds"></span>');
												recLabelChange();
											}

											$(".contactKeyWord").val('');
											$(".contactKeyWord").focus();

											// 其他映射，使用Jason对象传参
											otherORM_Fun(event, item);
											// 回调
											eval(callback);
										});

						if (value == '' || value == typeHint) {
							$('.' + nameClass).val(typeHint)
									.addClass(hintClass);
						} else {
							$('.' + nameClass).val(value)
									.removeClass(hintClass);
						}
					}
				});

		var otherORM_Fun = function(event, item) {
			if (otherORM && !jQuery.isEmptyObject(otherORM)) {
				$.each(otherORM, function(idORMClass, property) {
					var $ormObject = $('.' + idORMClass);
					if ($ormObject[0]) {
						if ($ormObject[0].tagName == 'INPUT') {
							if ($ormObject.attr('type') == 'text'
									|| $ormObject.attr('type') == 'hidden') {
								$ormObject.val(item[property]);
							}
						} else if ($ormObject[0].tagName == 'SELECT') {
							$ormObject.val(item[property]);
						}
					}

				});
			} else if (jQuery.isFunction(otherORM)) {
				//如果是回调函数就直接调用这个回调函数
				otherORM(event, item);
			}
		};
	};
	
	function addContactPosition(positionId,positionName){
		var flag = false;
		$(".holder span input.contact_positionIds").each(function() {
			if ($(this).val() == positionId) {
				// 已存在
				flag = true;
			}
		});
		if(flag==false){
			$(".holder").append('<span class="bit-box bit-box-positionId">'+ positionName
					+ '<a class="closebutton" href="#" onclick="$(this).parent().remove();recLabelChange(); "></a><input value="'
					+positionId+'" type="hidden" class="contact_positionIds" name="positionIds"></span>');
			recLabelChange();
		}
	}
	
	function addContactGroup(groupId,groupName){
		var flag = false;
		$(".holder span input.contact_groupIds").each(function() {
			if ($(this).val() == groupId) {
				// 已存在
				flag = true;
			}
		});
		if(flag==false){
			$(".holder").append('<span class="bit-box bit-box-groupId">'+ groupName
					+ '<a class="closebutton" href="#" onclick="$(this).parent().remove();recLabelChange(); "></a><input value="'
					+groupId+'" type="hidden" class="contact_groupIds" name="groupIds"></span>');
			recLabelChange();
		}
	}
	
	function addContactBranch(branchId,branchName){
		var flag = false;
		$(".holder span input.contact_branchIds").each(function() {
			if ($(this).val() == branchId) {
				// 已存在
				flag = true;
			}
		});
		if(flag==false){
			$(".holder").append('<span class="bit-box bit-box-branchId">'+ branchName
					+ '<a class="closebutton" href="#" onclick="$(this).parent().remove();recLabelChange(); "></a><input value="'
					+branchId+'" type="hidden" class="contact_branchIds" name="branchIds"></span>');
			recLabelChange();
		}
	}
	
	function addContactPositionGrade(positionGradeId,positionGradeName){
		var flag = false;
		$(".holder span input.contact_positionGradeIds").each(function() {
			if ($(this).val() == positionGradeId) {
				// 已存在
				flag = true;
			}
		});
		if(flag==false){
			$(".holder").append('<span class="bit-box bit-box-positionGradeId">'+ positionGradeName
					+ '<a class="closebutton" href="#" onclick="$(this).parent().remove();recLabelChange(); "></a><input value="'
					+positionGradeId+'" type="hidden" class="contact_positionGradeIds" name="positionGradeIds"></span>');
			recLabelChange();
		}
	}
	
	(function($) {
		if($('.subAction').val()=="viewObject"){
			// 添加联系人失效
			$("#contactSearch").hide();
			var mail = $("#hiddenReception").val();
			var mailContent = mail+'<a class="closebutton" href="#" onclick="$(this).parent().remove();recLabelChange(); "></a><input value="${messageInfoForm.reception}" type="hidden" class="user_extends" name="extends">';
			$(".holder").append('<span class="bit-box bit-box-extend">'+mailContent+'</span>');
			$(".recLabel").append("<span class='bit-box'>"+mailContent+"</span>");
		};
		
		if($("#checkType").val()=="email"){
			$(".contactNum-label").html('<bean:message bundle="message" key="message.mail.contact.email" />');
		}else if($("#checkType").val()=="info"){
			$(".contactNum-label").html('<bean:message bundle="message" key="message.mail.contact.user.id" />');
		};
		
		$(".contactNum").keyup(function(e){
			var val = $(this).val();
			var lastChar = val.substr(val.length-1,val.length);
			if(lastChar == ";"){
				if($("#checkType").val()=="email"){
					var emails = val.split(";");
					for(var i = 0 ; i<emails.length-1;i++){
						var mail = emails[i];	
						if((mail.indexOf("@") <= 0 || mail.indexOf(".") <= 0 || mail.indexOf("@") > mail.lastIndexOf("."))){
							cleanError("contactNum");
							$('.contactNum').after('<label class="error contactNum_error"></label>');
							$('.contactNum').addClass("error");
						}else{
							// 遍历是否已存在地址
							var flag = false;
							$(".holder span input.user_extends").each(function(){
								if($(this).val()==mail){
									// 已存在
									flag = true;
								}
							});
							
							if(flag==false){
								$(".holder").append('<span class="bit-box bit-box-extend">'+mail+'<a class="closebutton" href="#" onclick="$(this).parent().remove();recLabelChange(); "></a><input value="'+mail+'" type="hidden" class="user_extends" name="extends"></span>');
								$(".contactNum").val("");
								recLabelChange();
							}
							// 删除提示
							cleanError("contactNum");
						}
					}
				}else if($("#checkType").val()=="sms"){
					// 如果是email
					var smses = val.split(";");
					for(var i = 0 ; i<smses.length-1;i++){
						var sms = smses[i];	
						if((/[^0-9]+/.test(sms) || sms.length != 11 || sms.indexOf("1") != 0)){
							cleanError("contactNum");
							$('.contactNum').after('<label class="error contactNum_error"></label>');
							$('.contactNum').addClass("error");
						}else{
							// 遍历是否已存在手机号码
							var flag = false;
							$(".holder span input.user_extends").each(function(){
								if($(this).val()==sms){
									// 已存在
									flag = true;
								}
							});
							
							if(flag==false){
								$(".holder").append('<span class="bit-box bit-box-extend">'+sms+'<a class="closebutton" href="#" onclick="$(this).parent().remove();recLabelChange(); "></a><input value="'+sms+'" type="hidden" class="user_extends" name="extends"></span>');
								$(".contactNum").val("");
								recLabelChange();
							}
							// 删除提示
							cleanError("contactNum");
						}
					}
				}else if($("#checkType").val()=="info"){
					var infos = val.split(";");
					var flag = false;
					for(var i = 0 ; i<infos.length-1;i++){
						var info = infos[i];
						$(".holder span input.user_extends").each(function(){
							if($(this).val()==info){
								// 已存在
								flag = true;
							}
						});
					}
					if(flag==false){
						$(".holder").append('<span class="bit-box bit-box-extend">'+info+'<a class="closebutton" href="#" onclick="$(this).parent().remove();recLabelChange(); "></a><input value="'+info+'" type="hidden" class="user_extends" name="extends"></span>');
						$(".contactNum").val("");
						recLabelChange();
					}
				}
			}
			if(e.keyCode==13){
				$(".contactNum").val($(".contactNum").val()+";");
				$(".contactNum").keyup();
			}
		}).blur(function(){
			// 如果不为空，且会后一个不是分号。则加上分号添加
			var val = $(".contactNum").val();
			var lastChar = val.substr(val.length-1,val.length);
			if(val!="" && lastChar!=";"){
				$(".contactNum").val($(".contactNum").val()+";");
				$(".contactNum").keyup();
			}
		});
		
		$(".recLabel").click(function(){
			if($(".subAction").val()!="viewObject"){
				popupContactSearch();
			}
		});
		
	})(jQuery);
	
	function recLabelChange(){
		$(".recLabel").html("");
		$("span.holder span.bit-box").each(function(i){
			$(".recLabel").show();
			if(i<3){
				$(".recLabel").append("<span class='bit-box'>" +$(this).text() +"</span>");
			}else if(i==3){
				$(".recLabel").append("...");
			}
		});
	}
</script>