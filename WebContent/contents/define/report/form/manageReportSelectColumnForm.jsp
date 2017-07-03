<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="messageWrapper">
	<logic:present name="MESSAGE_DETAIL">
		<logic:present name="MESSAGE">
			<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
				<bean:write name="MESSAGE" />
    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
			</div>
		</logic:present>
	</logic:present>
</div>
<html:form action="reportHeaderAction.do?proc=add_column" styleClass="manageReportDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="tableId" value="<bean:write name="reportHeaderForm" property="tableId" />"/>
	<input type="hidden" name="nameEN"  value="<bean:write name="reportHeaderForm" property="nameEN" />"/>
	<input type="hidden" name="nameZH"  value="<bean:write name="reportHeaderForm" property="nameZH" />"/>
	<input type="hidden" name="subAction" id="subAction" class="subAction" value="" />
<%-- 	<input type="hidden" name="reportDetailId" id="reportDetailId" class="reportDetailId" value="<bean:write name="reportDetailForm" property="encodedId" />" /> --%>
	<input type="hidden" name="reportHeaderId" id="reportHeaderId" class="reportHeaderId" value="<bean:write name="reportHeaderForm" property="reportHeaderId" />"/>
	<input type="hidden" name="unSelectColumnsJson" id="unSelectColumnsJson" value="<bean:write name="reportHeaderForm" property="unSelectColumnsJson" />"/>
	<input type="hidden" name="selectColumnsJson" id="selectColumnsJson" value="<bean:write name="reportHeaderForm" property="selectColumnsJson" />"/>
	<input type="hidden" name="selectTablesJson" id="selectTablesJson" value="<bean:write name="reportHeaderForm" property="selectTablesJson" />"/>
	<input type="hidden" name="unSelectTablesJson" id="unSelectTablesJson" value="<bean:write name="reportHeaderForm" property="unSelectTablesJson" />"/>
</html:form>
<html:form action="reportDetailAction" styleClass="reportDetailAction_form">
	<fieldset>
		<ol class="auto">
			<li class="required">
				<label><em>* </em><bean:message bundle="public" key="required.field" /></label>
			</li>
		</ol>
		<ol class="auto">
			<li class="myli">
				<label><bean:message bundle="define" key="define.column" /> <img src="images/tips.png" title="<bean:message bundle="define" key="define.report.double.click.select" />" /></label> 
				<div class="mydiv">
					<div class="mydiv_header"><bean:message bundle="define" key="define.report.detail.column.list" /></div>
					<div class="mydiv_body"><ul id="unSelectColumns"></ul></div>
					<div class="mydiv_foot"></div>
				</div>
				<div class="mydivblank"></div>
				<div class="mydiv">
					<div class="mydiv_header"><bean:message bundle="define" key="define.report.detail.column.selected" /></div>
					<div class="mydiv_body"><ul id="selectColumns"></ul></div>
					<div class="mydiv_foot"></div>
				</div>
			</li>
		
		</ol>
	</fieldset>
	<fieldset>
		<div class="top">
			<input type="button" name="btnSaveStep5" id="saveColumn" value="<bean:message bundle="define" key="define.report.detail.button.column.save" />" />
		</div>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.report.detail.column.count" /></label>
				<html:select property="statisticsFun" styleClass="manageReportDetail_statisticsFun" styleId="statisticsFun">
					<html:optionsCollection property="statisticsColumnses" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
		</ol>
		
		<!-- ��ѡ�ֶ�Ϊ�������� -->
		<ol class="auto" id="datetimeFormatOL" style="display: none;">
			<li>
				<label><bean:message bundle="public" key="public.date.format" /></label> 
				<html:select property="datetimeFormat" styleClass="manageReportDetail_datetimeFormat" styleId="datetimeFormat">
					<html:optionsCollection property="datetimeFormats" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
		<!-- ��ѡ�ֶ�ΪС������ -->
		<ol class="auto" id="decimalFormatOL" style="display: none;">		
			<li>
				<label><bean:message bundle="public" key="public.accuracy" /></label> 
				<html:select property="accuracy" styleClass="manageReportDetail_accuracy" styleId="accuracy">
					<html:optionsCollection property="accuracys" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label><bean:message bundle="public" key="public.round" /></label> 
				<html:select property="round" styleClass="manageReportDetail_round" styleId="round">
					<html:optionsCollection property="rounds" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>		
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageReportDetail_nameZH" styleId="nameZH"/>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.manager.detail.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageReportDetail_nameEN" styleId="nameEN"/>
			</li>	
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.list.detail.column.width" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.column.width.tips" />" /></label> 
				<html:select property="columnWidthType" styleClass="small manageReportDetail_columnWidthType" styleId="columnWidthType">
					<html:optionsCollection property="columnWidthTypies" value="mappingId" label="mappingValue" />
				</html:select> 	
				<html:text property="columnWidth" maxlength="3" styleClass="small manageReportDetail_columnWidth" styleId="columnWidth"/>
			</li>	
			<li>
				<label><bean:message bundle="define" key="define.column.column.index" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.column.column.index.tips" />" /><em> *</em></label> 
				<html:text property="columnIndex" maxlength="2" styleClass="manageReportDetail_columnIndex" styleId="columnIndex"/>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.font.size" /></label> 
				<html:select property="fontSize" styleClass="manageReportDetail_fontSize" styleId="fontSize">
					<html:optionsCollection property="fontSizes" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.is.decode" /></label>
				<html:select property="isDecoded" styleClass="manageReportDetail_isDecoded" styleId="isDecoded">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>
		</ol>
		<ol class="auto">		
			<li>
				<label><bean:message bundle="define" key="define.list.detail.is.linked" /></label>
				<html:select property="isLinked" styleClass="manageReportDetail_isLinked" styleId="isLinked">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>	
		</ol>	
		<ol class="auto" style="display: none;" id="linkedDetailOL">	
         	<li>
         		<label><bean:message bundle="define" key="define.list.detail.linked.action" />  <img src="images/tips.png" title="<bean:message bundle="define" key="define.list.detail.linked.action.tips" />" /><em> *</em></label> 
         		<html:text property="linkedAction" maxlength="2" styleClass="manageReportDetail_linkedAction" styleId="linkedAction"/>
        	</li>
        	<li>
        		<label><bean:message bundle="define" key="define.list.detail.linked.target" /></label> 
        		<html:select property="linkedTarget" styleClass="manageReportDetail_linkedTarget" styleId="linkedTarget">
					<html:optionsCollection property="linkedTargets" value="mappingId" label="mappingValue" />
				</html:select> 	
			</li>	
		</ol>
		<ol class="auto">							
			<li>
				<label><bean:message bundle="define" key="define.list.detail.align" /></label> 
				<html:select property="align" styleClass="manageReportDetail_align" styleId="align">
					<html:optionsCollection property="aligns" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.sort" /></label> 
				<html:select property="sort" styleClass="manageReportDetail_sort" styleId="sort">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="define" key="define.list.detail.display" /></label> 
				<html:select property="display" styleClass="manageReportDetail_display" styleId="display">
					<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 	
				<html:textarea property="description" cols="3" styleClass="manageReportDetail_description" styleId="description"></html:textarea>
			</li>				
			<li>
				<label><bean:message bundle="public" key="public.status" />  <em>*</em></label> 
				<html:select property="status" styleClass="manageReportDetail_status" styleId="status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>	

</html:form>
<script type="text/javascript">
var language = 'ZH';
language = '<%=request.getLocale().getLanguage()%>';
var selectColumns = "";
var unSelectColumns = "";
var divLeftId = "unSelectColumns";
var divRightId = "selectColumns";
var saveColumnId;
	(function($) {
		kanList_init();
		kanCheckbox_init();
		//��ʼ������
		initData_column();
		disableForm('reportDetailAction_form');
		
		<logic:notEmpty name="MESSAGE">
			messageWrapperFada();
		</logic:notEmpty>
		
// 		// ѡ���ֶ�change�¼�
// 		$('.manageReportDetail_form select#columnId').change(function(){
// 			$.ajax({
// 				url:"reportDetailAction.do?proc=columnId_change_ajax&columnId=" + $(this).val(),
// 				dataType:"json",
// 				success:function(data){
// 					$('#datetimeFormatOL').hide();
// 					$('#decimalFormatOL').hide();
// 					if( data.success == 'true' ){
// 						$('.manageReportDetail_nameZH').val(data.nameZH);
// 						$('.manageReportDetail_nameEN').val(data.nameEN);
// 						if(data.valueType=='1'){
// 							$('#decimalFormatOL').show();
// 						}else if( data.valueType == '3' ){
// 							$('#datetimeFormatOL').show();
// 						}
// 					}else{
// 						$('.manageReportDetail_nameZH').val('');
// 						$('.manageReportDetail_nameEN').val('');
// 					}
// 				}
// 			});
// 		});
		// ѡ���ֶ�change�¼�
		/** $('.manageReportDetail_form select#columnId').change(function(){
			if( $('.manageReportDetail_form #subAction').val() != 'viewObject')
				$('.manageReportDetail_statisticsColumns').removeAttr('disabled');
			$.ajax({
				url:"reportDetailAction.do?proc=columnId_change_ajax&columnId=" + $(this).val(),
				dataType:"json",
				success:function(data){
					$('#datetimeFormatOL').hide();
					$('#decimalFormatOL').hide();
					if( data.success == 'true' ){
						$('.manageReportDetail_nameZH').val(data.nameZH);
						$('.manageReportDetail_nameEN').val(data.nameEN);
						if(data.valueType=='1'){
							$('#decimalFormatOL').show();
						}else if( data.valueType == '3' ){
							$('#datetimeFormatOL').show();
						}
						if($('.manageReportDetail_form #subAction').val() != 'viewObject' && data.accountId != '1'){
							$('.manageReportDetail_statisticsColumns').val('0');
							$('.manageReportDetail_statisticsColumns').attr('disabled','disabled');
						}
					}else{
						$('.manageReportDetail_nameZH').val('');
						$('.manageReportDetail_nameEN').val('');
					}
				}
			});
		});
		*/
		$('.manageReportDetail_isLinked').change(function(){
			isLinkedChange();
		});
		$('#saveColumn').click(function(){
			saveColumn();
		});
		
		
	})(jQuery);
	
	// ������OnChange�¼�
	function isLinkedChange(){
		if($('.manageReportDetail_isLinked').val() == '1'){
			$('#linkedDetailOL').show();
		}else{
			$('#linkedDetailOL').hide();
		}
	};
	
	// ���report Detail �����ӣ�Ajax�����޸�ҳ��	
	function reportDetailModify()
	{
		$('.manageReportDetail_form input#subAction').val('viewObject');
		$('#btnSaveStep2').val('<bean:message bundle="public" key="button.edit" />');
		var tokenValue = $('.manageReportDetail_form input[id=\"com.kan.token\"]').val();
		changeOtherToken(tokenValue);
		disableUl("manageReportDetail_form");
	};
	
	//�޸�ʱ��ʼҳ������
	function initData_column() {
		selectColumns = $("#selectColumnsJson").val();
		unSelectColumns = $("#unSelectColumnsJson").val();
		if(selectColumns==null||selectColumns==''){
			selectColumns = new Array();
		}else{
			selectColumns = JSON.parse(selectColumns);
		}
		if(unSelectColumns==null||unSelectColumns==''){
			unSelectColumns = new Array();
		}else{
			unSelectColumns = JSON.parse(unSelectColumns);
		}
		
		//��ʼ����ѡ���
		initSelectUl_column("left");
		//��ʼ����ѡ���
		initSelectUl_column("right");
	}
	
	//���table
	function clearUl_column(id){
	  $("#"+id).empty();
	}
	
	//��ʼul
	function initSelectUl_column(target){
		var list;
		if(target=="left"){
			ulId = divLeftId;
			list = unSelectColumns;
		}else{
			ulId = divRightId;
			list = selectColumns;
		}
		//clearUl_column(ulId);
		list = unSelectColumns;
		for(var l in unSelectColumns){ 
			addLi_column(list[l],target);
		}
	}
	//����table �� �� tr  data ���ݣ�ulId ����Ŀ���tableid,ondbliclickfn �󶨵�˫���¼�,tableId tableId
	function addLi_column(data,target){
		var ulId;
		//var display;
		if(target=="left"){
			ulId = divLeftId;
			
// 			display = data.displayStyle;
		}else{
			ulId = divRightId;
// 			if(data.displayStyle==""){
// 				display="none";	
// 			}else{
// 				display="";
// 			}
		}
		var ul=document.getElementById(ulId);
		var li= document.createElement("li");
		var subUl= document.createElement("ul");
		ul.appendChild(li);
		li.appendChild(subUl); 
		//li.style.cssText="display:"+display+";";
		//���۵��¼�
		var id = target+"_"+data.tableId;
		//li.setAttribute("ondblclick",+"ondbliclickFn("+id+")" );
		subUl.setAttribute("id",id);
		if(data.isMasterTable==1){
			subUl.innerHTML = "<label>"+ (language == 'en' ? data.nameEN : data.nameZH) +" (<bean:message bundle="define" key="define.report.parent" />) </label>";
// 				getAlink(data.nameZH+"(����)","ondbliclickFn('"+id+"')");
		}else{
			subUl.innerHTML = "<label>"+ (language == 'en' ? data.nameEN : data.nameZH) +" (<bean:message bundle="define" key="define.report.sub" />) </label>";
// 				getAlink(data.nameZH+"(�ӱ�)","ondbliclickFn('"+id+"')");
		}
		//ѭ����
		var list = data.reportColumnVOList;
		for(var l in list){ 
			addSubli_column(list[l],id,subUl,target);
		}
		
	}
	//����table �� �� tr  data ���ݣ�ulId ����Ŀ���tableid,ondbliclickfn �󶨵�˫���¼�,tableId tableId
	function addSubli_column(data,idPre,subUl,target){
		//var ul=document.getElementById(tableId);
		var display="";
		var text;
		var id = idPre+"_"+data.columnId;
		if(target=="left"){
			display = data.displayStyle;
			text = language == 'en' ? data.nameEN : data.nameZH;
			//����� Ϊ������ɾ��ʱ��ʾ
			if(data.initStatus=="insert"){
				display="";	
			}else{
				display="none";	
			}
		}else{
			columnData = data;
			text = (language == 'en' ? data.nameEN : data.nameZH )+"<a onclick=toEdit('"+id+"') ><image title='<bean:message bundle="public" key="oper.edit" /> <bean:message bundle="define" key="define.column" />'  src='images/modify.png'></a>";
			//�ұ��� Ϊ�޸�ʱ��ʾ
			if(data.initStatus=="def"){
				display="";	
			}else{
				display="none";	
			}
		}
		
		var li= document.createElement("li");
		subUl.appendChild(li);
		li.setAttribute("ondblclick","ondbliclickFn('"+id+"')" );
		li.setAttribute("id",id );
		li.setAttribute("title","<bean:message bundle="define" key="define.report.double.click.select" />");
		//li.setAttribute("data",columnData );
		li.style.cssText="display:"+display;
		//li.style.cssText="display:none;";
		li.innerHTML = text;
	};
	
	
	function ondbliclickFn(id){
		disableForm('reportDetailAction_form');
		var targetId;
		var originalId = id;
		if(id.indexOf("left")>-1){
			targetId =id.replace("left","right");
		}else{
			targetId =id.replace("right","left");
		}
		
		updateStatus(id);
		document.getElementById(originalId).style.cssText="display:none;";
		document.getElementById(targetId).style.cssText='display:"";';
		
	}
	
	
	function getAlink(text,func){
		return "<a ondblclick="+func+" href='#'>"+text+"</a>";
	}
	function toEdit(id){
		enableForm('reportDetailAction_form');
		var arr = id.split("_");
		var tableId = arr[1];
		var columnId = arr[2];
		var list ;
		var columnData;
		for(var l in selectColumns){ 
			if(tableId==selectColumns[l].tableId){
				list = selectColumns[l].reportColumnVOList;
				for(var s in list){ 
					if(columnId==list[s].columnId){
						columnData = list[s];
						saveColumnId = id;
						$("#accuracy").val(columnData.accuracy);
						$("#align").val(columnData.align);
						//$("#columnId").val(columnData.columnId);
						$("#columnIndex").val(columnData.columnIndex);
						$("#columnWidth").val(columnData.columnWidth);
						$("#columnWidthType").val(columnData.columnWidthType);
						$("#datetimeFormat").val(columnData.datetimeFormat);
						$("#description").val(columnData.description);
						$("#display").val(columnData.display);
						$("#fontSize").val(columnData.fontSize);
						$("#isDecoded").val(columnData.isDecoded);
						$("#isLinked").val(columnData.isLinked);
						$("#linkedAction").val(columnData.linkedAction);
						$("#linkedTarget").val(columnData.linkedTarget);
						$("#nameEN").val(columnData.nameEN);
						$("#nameZH").val(columnData.nameZH);
						$("#round").val(columnData.round);
						$("#sort").val(columnData.sort);
						$("#status").val(columnData.status);
						$("#statisticsFun").val(columnData.statisticsFun);
	 					$('#datetimeFormatOL').hide();
	 					$('#decimalFormatOL').hide();
 						if(columnData.valueType=='1'){
 							$('#decimalFormatOL').show();
 						}else if( columnData.valueType == '3' ){
 							$('#datetimeFormatOL').show();	
 						}
						return;
					}
				}
			}
		}
	}	
// 		[{"displayStyle":"",
// 			"isMasterTable":1,
// 			"nameEN":""
// 			,"nameZH":"��Ӧ�� - ��ϵ��",
// 			"reportColumnVOList":[
// 			                 {   "accuracy":"",
// 			                	 "align":"",
// 			                	 "columnId":"401",
// 			                	 "columnIndex":"",
// 			                	 "columnWidth":"",
// 			                	 "columnWidthType":"",
// 			                	 "datetimeFormat":"",
// // 			                	 "decodeColumn":"",
// // 			                	 "decodeColumnId":"",
// 			                	 "description":"",
// 			                	 "display":"",
// 			                	// "displayStyle":"",
// 			                	 "fontSize":"",
// 			                	 "isDecoded":"",
// 			                	 "isLinked":"",
// 			                	 "linkedAction":"",
// 			                	 "linkedTarget":"",
// 			                	 "nameEN":"Vendor ID",
// 			                	 "nameZH":"��Ӧ��ID",
// // 			                	 "reportDetailId":"",
// // 			                	 "reportHeaderId":"",
// 			                	 "round":"",
// 			                	 "sort":"",
// 			                	 "statisticsColumns":"",
// // 			                	 "valueType":"2"}
			                 
//�ռ��������Ϣ   
function saveColumn(){
	
	var arr = saveColumnId.split("_");
	var tableId = arr[1];
	var columnId = arr[2];
	var list ;
	var columnData;
	if( validate_form_step2() != 0 ){
		return ;
	}
	$("#"+saveColumnId+" a img ").attr("src","images/icons/save.png");
	saveColumnId = "";
	for(var l in selectColumns){ 
		if(tableId==selectColumns[l].tableId){
			list = selectColumns[l].reportColumnVOList;
			for(var s in list){ 
				if(columnId==list[s].columnId){
					columnData = list[s];
					//saveColumnId = id;
					columnData.accuracy = $("#accuracy").val();
					columnData.align = $("#align").val();
					//columnData.columnId = $("#columnId").val();
					columnData.columnIndex = $("#columnIndex").val();
					columnData.columnWidth = $("#columnWidth").val();
					columnData.columnWidthType = $("#columnWidthType").val();
					columnData.datetimeFormat = $("#datetimeFormat").val();
					columnData.description = $("#description").val();
					columnData.display = $("#display").val();
					columnData.fontSize = $("#fontSize").val();
					columnData.isDecoded = $("#isDecoded").val();
					columnData.isLinked = $("#isLinked").val();
					columnData.linkedAction = $("#linkedAction").val();
					columnData.linkedTarget = $("#linkedTarget").val();
					columnData.nameEN = $("#nameEN").val();
					columnData.nameZH = $("#nameZH").val();
					columnData.round = $("#round").val();
					columnData.sort = $("#sort").val();
					columnData.status = $("#status").val();
					columnData.statisticsFun = $("#statisticsFun").val();
					
					$('#datetimeFormatOL').hide();
 					$('#decimalFormatOL').hide();
					//alert($("#statisticsColumns").val());
					//alert(selectColumns[l].reportColumnVOList[s].statisticsColumns);
					//$("#selectColumnsJson").val(JSON.stringify(selectColumns));
					//alert(JSON.stringify($("#selectColumnsJson").val()));
					//Ĭ�ϱ�Ϊ�޸�
					if(columnData.initStatus=="def"){
						columnData.operStatus ="modify";
						}
					selectColumns[l].reportColumnVOList[s] = columnData;
					disableForm('reportDetailAction_form');
					return;
				}
			}
		}
	}
}

//�Ҳ�ѡ���ȥ��ʱҪ�������ݵ�ɾ��״̬ 
function updateStatus(id){
	var arr = id.split("_");
	var tableId = arr[1];
	var columnId = arr[2];
	var list ;
	var columnData;
	for(var l in selectColumns){ 
		if(tableId==selectColumns[l].tableId){
			list = selectColumns[l].reportColumnVOList;
			for(var s in list){ 
				if(columnId==list[s].columnId){
					columnData = list[s];
					//---->����
					if(id.indexOf("left")>-1){
						if(columnData.initStatus=="insert"){
							columnData.operStatus ="insert";
							}
					// <---�ҵ���
					}else{
						if(columnData.initStatus=="def"){
							columnData.operStatus ="delete";
							}
					}
					
					selectColumns[l].reportColumnVOList[s] = columnData;
//						alert(columnData.statisticsColumns);
					return;
				}
			}
		}
	}
}
			
</script>
