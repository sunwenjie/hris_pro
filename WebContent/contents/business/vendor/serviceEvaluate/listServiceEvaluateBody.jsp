<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="item-information">
		<div class="head">
			<label>供应商评价</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
	</div>
	
	<!-- Item - information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				
			</div>
			<div class="top"> 
				<input style="float: left;" type="button" class="save" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="addServiceEvaluate();" />
				<input style="display: none; float: left;" type="button" class="reset" id="btnCancel" name="btnCancel" value="取消" onclick="cancel();"  />
				<input style="float: left;" type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) dynamicDeleteRow()" />
			</div>
			<!-- form -->
			<div id="formWrapper" style="display: none;">
				<form class="serviceEvaluateForm">
					<input type="hidden" name="subAction" class="subAction" id="subAction" value="" />
					<input type="hidden" name="selectedIds" class="selectedIds" id="selectedIds" value="" />
					<fieldset>
						<ol class="auto">
							<li id="vendorIdLI">
								<label>供应商ID<em> *</em></label> 
								<input type="text" name="vendorId" class="manageServiceEvaluateVendorId important" id="vendorId" />
							</li>
						</ol>
						<ol class="auto">
							<li>
								<label>供应商名称（中文）</label> 
								<input type="text" name="vendorNameZH" disabled="disabled" class="manageServiceEvaluateVendorNameZH" id="vendorNameZH" />
							</li>
							<li>
								<label>供应商名称（英文）</label> 
								<input type="text" name="vendorNameEN" disabled="disabled" class="manageServiceEvaluateVendorNameEN" id="vendorNameEN" />
							</li>
						</ol>
						<ol class="auto">
							<li>
								<label>服务月份 </label>
								<select name="serviceMonthly" class="manageServiceEvaluateServiceMonthly" id="manageServiceEvaluateServiceMonthly">
									<option value="2014/07">默认当月</option>
									<option value="2014/06">2014/06</option>
									<option value="2014/05">2014/05</option>
									<option value="2014/04">2014/04</option>
									<option value="2014/03">2014/03</option>
									<option value="2014/02">2014/02</option>
									<option value="2014/01">2014/01</option>
									<option value="2013/12">2013/12</option>
									<option value="2013/11">2013/11</option>
									<option value="2013/10">2013/10</option>
									<option value="2013/09">2013/09</option>
									<option value="2013/08">2013/08</option>
									<option value="2013/07">2013/07</option>
								</select>
							</li>
						</ol>
						<ol class="auto">
							<li>
								<label>员工投诉次数 <em> *</em></label>
								<div class="complaintNumberDiv">
									<span><input name="complaintNumber" class="manageComplaintNumber" type="radio" value="25" />无</span>
									<span><input name="complaintNumber" class="manageComplaintNumber" type="radio" value="15" />3次以下</span>
									<span><input name="complaintNumber" class="manageComplaintNumber" type="radio" value="5" />5次以下</span>
									<span><input name="complaintNumber" class="manageComplaintNumber" type="radio" value="0" />5次以上</span>
								</div>
							</li>
						</ol>
						<ol class="auto">
							<li>
								<label>处理差错次数 <em> *</em></label>
								<div class="errorNumberDiv">
									<span><input name="errorNumber" type="radio" value="25" />无</span>
									<span><input name="errorNumber" type="radio" value="15" />3次以下</span>
									<span><input name="errorNumber" type="radio" value="5" />5次以下</span>
									<span><input name="errorNumber" type="radio" value="0" />5次以上</span>
								</div>
							</li>
						</ol>
						<ol class="auto">
							<li>
								<label>反馈效率 <em> *</em></label>
								<div class="feedbackEfficiencyDiv">
									<span><input name="feedbackEfficiency" type="radio" value="5" />差</span>
									<span><input name="feedbackEfficiency" type="radio" value="10" />一般</span>
									<span><input name="feedbackEfficiency" type="radio" value="15" />好</span>
									<span><input name="feedbackEfficiency" type="radio" value="25" />很好</span>
								</div>
							</li>
						</ol>
						<ol class="auto">
							<li>
								<label>服务报表质量 <em> *</em></label>
								<div class="reportQualityDiv">
									<span><input name="reportQuality" type="radio" value="5" />差</span>
									<span><input name="reportQuality" type="radio" value="10" />一般</span>
									<span><input name="reportQuality" type="radio" value="15" />好</span>
									<span><input name="reportQuality" type="radio" value="25" />很好</span>
								</div>
							</li>
						</ol>
						<ol class="auto">
							<li>
								<label>本月满意度 <em> *</em></label>
								<div class="satisfactionDiv">
									<span><input name="satisfaction" type="radio" value="0.2" />差</span>
									<span><input name="satisfaction" type="radio" value="0.4" />一般</span>
									<span><input name="satisfaction" type="radio" value="0.8" />好</span>
									<span><input name="satisfaction" type="radio" value="1.0" />很好</span>
								</div>
							</li>
						</ol>
					</fieldset>
				</form>
			</div>
			<div class="bottom">
				<p>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<table class="table hover" id="resultTable">
					<thead>
						<tr>
							<th class="checkbox-col">
								<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
							</th>
							<th style="width: 10%" class="header-nosort">
								供应商ID
							</th>
							<th style="width: 20%" class="header-nosort">
								供应商名称（中文）
							</th>
							<th style="width: 20%" class="header-nosort">
								供应商名称（英文）
							</th>
							<th style="width: 10%" class="header-nosort">
								服务月份
							</th>
							<th style="width: 15%" class="header-nosort">
								得分
							</th>
							<th style="width: 10%" class="header-nosort">
								修改人
							</th>
							<th style="width: 15%" class="header-nosort">
								修改时间
							</th>
						</tr>
					</thead>
					<tbody id="myTbody">
						<tr class="odd">
							<td class="checkbox-col">
								<input type="checkbox" id="kanList_chkSelectRecord_0" name="chkSelectRow[]" value="0" />
							</td>
							<td>600000001</td>
							<td>标创</td>
							<td></td>
							<td>2014/07</td>
							<td class="right">100</td>
							<td>Administrator</td>
							<td>2014-07-30 18:00:00</td>
						</tr>
					</tbody>
					<tfoot>
						<tr class="total">
						  	<td  colspan="8" class="left"> 
							  	<label>&nbsp;<bean:message bundle="public" key="page.total" />：<span id="total">1</span> </label>
						 	</td>					
					   </tr>
					</tfoot>
				</table>
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchVendor.jsp"></jsp:include>
</div>	

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式，如果当前用户是Super
		$('#menu_vendor_Modules').addClass('current');
		$('#menu_vendor_ServiceEvaluate').addClass('selected');
		$('#searchDiv').hide();
		
		$('#vendorIdLI').append('<a onclick="popupVendorSearch();" class="kanhandle"><img src="images/search.png" title="搜索供应商" /></a>');
		
		// 供应商ID keyup事件
		$("#vendorId").bind('keyup', function(){
			if($("#vendorId").val()!=''){
				$.ajax({url: 'vendorAction.do?proc=get_object_json&vendorId=' + $(this).val() + '&date=' + new Date(),
					dataType : 'json',
					success: function(data){
						cleanError('vendorId');
						
						if(data.success == 'true'){
							$('#vendorNameZH').val(data.nameZH);
							$('#vendorNameEN').val(data.nameEN);
						}else{
							//$('#vendorId').val('');
							$('#vendorNameZH').val('');
							$('#vendorNameEN').val('');
							addError('vendorId', '供应商ID无效；');  
						}
					}
				});
			}
		});
	
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	<%
		final String username = ( String )request.getAttribute( "username" );
	%>
	
	// 添加服务
	function addServiceEvaluate(){
		var formWrapper = document.getElementById( "formWrapper" );
		var subAction = document.getElementById( "subAction" );
		document.getElementById("btnCancel").style.display = "block";
		
		if( subAction.value == '' ){
			subAction.value = "createObject";
			formWrapper.style.display = "block";
		}else{
			var flag = 0;
			flag = flag + validate('manageServiceEvaluateVendorId', true, 'common', 50, 0);
			
			if( checkRadio( "complaintNumber", "complaintNumberDiv" ) == false){
				flag = flag + 1;
			}
			
			if( checkRadio( "errorNumber", "errorNumberDiv" ) == false){
				flag = flag + 1;
			}
			
			if( checkRadio( "feedbackEfficiency", "feedbackEfficiencyDiv" ) == false){
				flag = flag + 1;
			}
			
			if( checkRadio( "reportQuality", "reportQualityDiv" ) == false){
				flag = flag + 1;
			}
			
			if( checkRadio( "satisfaction", "satisfactionDiv" ) == false){
				flag = flag + 1;
			}
			
			if( flag == 0 ){
				dynamicAddRow();
			}
		}
	};
	
	
	// 动态添加行
	function dynamicAddRow(){
		var trSize = $('#myTbody tr').length;
		if( trSize < 0){
			trSize = 0;
		}
		var objTr = document.getElementById("myTbody").insertRow(parseInt(trSize));
		objTr.setAttribute("class", trSize % 2 == 0 ? "odd" : "even"); 
		
		var td0 = objTr.insertCell(0);
		td0.innerHTML = "<td><input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + trSize +  "\" name=\"chkSelectRow[]\" value=\"" + trSize + "\"></td>";
		
		var td1 = objTr.insertCell(1);
		var td1Value = document.getElementById( "vendorId" ).value;
		td1.innerHTML = "<td>" + td1Value + "</td>";
		
		var td2 = objTr.insertCell(2);
		var td2Value = document.getElementById( "vendorNameZH" ).value;
		td2.innerHTML = "<td>" + td2Value + "</td>";
		
		var td3 = objTr.insertCell(3);
		var td3Value = document.getElementById( "vendorNameEN" ).value;
		td3.innerHTML = "<td>" + td3Value + "</td>";
		
		var td4 = objTr.insertCell(4);
		var td4Value = document.getElementById( "manageServiceEvaluateServiceMonthly" ).value;
		td4.innerHTML = "<td>" + td4Value + "</td>";
		
		var td5 = objTr.insertCell(5);
		td5.setAttribute('class','right');
		var complaintNumber = getValueByRadioName( "complaintNumber" );
		var errorNumber = getValueByRadioName( "errorNumber" );
		var feedbackEfficiency = getValueByRadioName( "feedbackEfficiency" );
		var reportQuality = getValueByRadioName( "reportQuality" ); 
		var satisfaction = getValueByRadioName( "satisfaction" );
		var td5Value = parseInt(complaintNumber) + parseInt(errorNumber) + parseInt(feedbackEfficiency) + parseInt(reportQuality);
		td5.innerHTML = "<td>" + (parseFloat(td5Value) * parseFloat( satisfaction ))  + "</td>";
		
		var td6 = objTr.insertCell(6);
		td6.innerHTML = "<td><%=username%></td>"; 
		
		var td7 = objTr.insertCell(7);
		td7.innerHTML = "<td>" + new Date().format( "yyyy-MM-dd hh:mm:ss") + "</td>"; 
		
		kanList_init();
		
		var message = document.getElementById('messageWrapper');
		message.innerHTML = '<div class="message success fadable">添加成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>';
		messageWrapperFada();
		document.getElementById("kanList_chkSelectAll").checked = 0; 
		document.getElementById( "formWrapper" ).style.display = "none";
		
		var number = document.getElementById('total').innerText;
		document.getElementById('total').innerText = parseInt(number) + 1;
		cancel();
	};
	
	// 动态删除行
	function dynamicDeleteRow(){
		var selectedIds = document.getElementById("selectedIds").value; 
		var tableObject = document.getElementById("myTbody");
		if( selectedIds != '' ){
			for( var selectedId in selectedIds.split(',') ){
				tableObject.deleteRow(selectedId);
			}
		}
		var message = document.getElementById('messageWrapper');
		message.innerHTML = '<div class="message success fadable">删除成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>';
		messageWrapperFada();
		document.getElementById("kanList_chkSelectAll").checked = 0; 
		document.getElementById( "formWrapper" ).style.display = "none";
		var number = document.getElementById('total').innerText;
		document.getElementById('total').innerText = parseInt(number) - selectedIds.split(',').length;
	};
	
	// 检查单选框
	function checkRadio( elementName, divClass ){
		cleanError( divClass );
		var errorMsg = "至少选择一项；";
		var target = document.getElementsByName(elementName);
		for( var i = 0; i < target.length; i++ ){
			if(target[i].checked == true){
				return true;
			}
		}
		addError(divClass, errorMsg);
		return false;	
	}; 
	
	// 获取单选框选中值
	function getValueByRadioName( elementName ){
		var target = document.getElementsByName(elementName);
		for( var i = 0; i < target.length; i ++ ){
			if(target[i].checked == true){
				return target[i].value;
			}
		}
		return 0;
	};
	
	// 重置form
	function resetForm(){
		$('.serviceEvaluateForm input[type="hidden"]').val('');
		$('.serviceEvaluateForm input[type="text"]').val('');
		$('.serviceEvaluateForm select').val('2014/07');
		$('.serviceEvaluateForm input[type="radio"]').attr('checked',false);
	};
	
	// 取消
	function cancel(){
		resetForm();
		document.getElementById("btnCancel").style.display = "none";
		document.getElementById("formWrapper").style.display = "none";
	};
	
	Date.prototype.format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
</script>
