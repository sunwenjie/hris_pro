<%@page import="com.kan.hro.domain.biz.attendance.LeaveHeaderVO"%>
<%@page import="java.util.List"%>
<%@page import="com.kan.base.page.PagedListHolder"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet" href="mobile/css/mobile.css" />
<script src="js/kan.js"></script>
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
<style type="text/css">
	.btndel{
	
		padding:10px;
		background-color: red;
		color: white;
		font-size: 39pt;
		text-shadow: 0 3px 3px rgba(0,0,0,0.4);/*设置文本阴影*/
		-webkit-border-radius: 20px;
		-moz-border-radius: 20px;
		border-radius: 20px;

		-webkit-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
		-moz-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
		box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
	
		-webkit-transition: all 0.15s ease;
		-moz-transition: all 0.15s ease;
		-o-transition: all 0.15s ease;
		-ms-transition: all 0.15s ease;
		transition: all 0.15s ease;
	}
</style>

</head>
<body>
<div class="gongzichaxu">
	<div class="zongji" style="position: fixed;">
		<sup><a href="#" class="dangqian ap" onclick="pClick();" style="font-size: 48pt;"><bean:message bundle="wx" key="wx.list.leave" /></a></sup>
		<sup><a href="#" class="dangqian ac" style="color: #000000;font-size: 48pt;display: none;" onclick="cClick();" ><bean:message bundle="wx" key="wx.list.ot" /></a></sup>
		<input type="hidden" id="hidden_otClick" value="0">
	</div>
	<div class="leave">
		<%PagedListHolder leaveListHolder = (PagedListHolder)request.getAttribute("pagedListHolderLeave");%>
		<%PagedListHolder otListHolder = (PagedListHolder)request.getAttribute("pagedListHolderOT");%>
		<%
			int cur_page = (leaveListHolder.getPage()>otListHolder.getPage()?leaveListHolder.getPage():otListHolder.getPage())+1;
		%>
		<input type="hidden" name="page" id="page" value="<%=cur_page %>"/>
		<input type="hidden" name="realPage" id="realPage" value="<%=leaveListHolder.getRealPage() %>"/>
		<input type="hidden" name="nextPage" id="nextPage" value="<%=leaveListHolder.getNextPage() %>"/>
		<input type="hidden" name="leave_pageCount" id="leave_pageCount" value="<%=leaveListHolder.getPageCount() %>"/>
		<input type="hidden" name="ot_pageCount" id="ot_pageCount" value="<%=otListHolder.getPageCount() %>"/>
		
		<logic:iterate id="leaveHeaderVO" name="pagedListHolderLeave" property="source" indexId="number">
			<div class="daiban">
		   			<!-- 如果是退回的就能重新打开 -->
		   			<logic:notEqual value="4" name="leaveHeaderVO" property="status">
		   			<div class="biaoti2" onclick="">
		   			<a href="#" >
		   			</logic:notEqual>
		   			<logic:equal value="4" name="leaveHeaderVO" property="status">
		   			<!-- 
		   			<div class="biaoti2" onclick="link('leaveHeaderAction.do?proc=to_objectModify_mobile&id=<bean:write name="leaveHeaderVO" property="encodedId" />');">
		   			 -->
		   			 <div class="biaoti2" >
		   			<a href="#" >
		   			</logic:equal>
		   			
		   				<b class="biaoti2"><%=number+1%></b><bean:write name="leaveHeaderVO" property="decodeItemId" />
		   				（<bean:write name="leaveHeaderVO" property="estimateStartDate" />-<bean:write name="leaveHeaderVO" property="estimateEndDate" />）
		   				（<bean:write name="leaveHeaderVO" property="decodeStatus" />
		   					
		   					<logic:equal name="leaveHeaderVO" property="status" value="2">
		   					<logic:notEmpty name="leaveHeaderVO" property="workflowId" > - 
		   						<%
		   							if( "zh".equals( request.getLocale().getLanguage() ) ){
		   						%>
		   							<bean:write name="leaveHeaderVO" property="auditorZH" />
		   							
		   						<%
		   							}else{
		   						%>
		   							<bean:write name="leaveHeaderVO" property="auditorEN" />
		   						<%
		   							}
		   						%>
		   					</logic:notEmpty>
		   					</logic:equal>
		   					<logic:equal name="leaveHeaderVO" property="retrieveStatus" value="2">
		   					<logic:notEmpty name="leaveHeaderVO" property="workflowId" > - 
		   						<%
		   							if( "zh".equals( request.getLocale().getLanguage() ) ){
		   						%>
		   							<bean:write name="leaveHeaderVO" property="auditorZH" />
		   							
		   						<%
		   							}else{
		   						%>
		   							<bean:write name="leaveHeaderVO" property="auditorEN" />
		   						<%
		   							}
		   						%>
		   					</logic:notEmpty>
		   					</logic:equal>
		   				）
		   				<logic:equal value="1" name="leaveHeaderVO" property="unread">
		   					<img src="mobile/images/new2.png"/>
		   				</logic:equal>
		   				<span></span>
		   			</a>
		   			<logic:equal value="4" name="leaveHeaderVO" property="status">
		   				<button class="btndel" onclick="btnDeleted(<bean:write name="leaveHeaderVO" property="leaveHeaderId" />,'leave');">
		   				<bean:message bundle="wx" key="wx.leave.list.delete"/></button>
		   			</logic:equal>
		   			<logic:equal value="2" name="leaveHeaderVO" property="status">
		   				<button class="btndel" onclick="btnResend(<bean:write name="leaveHeaderVO" property="workflowId" />);">
		   				<bean:message bundle="wx" key="wx.leave.list.resend"/></button>
		   			</logic:equal>
		   			</div>
		   			<p ></p>
			</div>
		</logic:iterate>
	</div>
	<div class="ot" style="display: none">
		<logic:iterate id="otHeaderVO" name="pagedListHolderOT" property="source" indexId="number">
			<div class="daiban">
		   			<div class="biaoti2" onclick=""><a href="#" >
		   				<b class="biaoti2"><%=number+1%></b><bean:message bundle="wx" key="wx.list.ot" />（<bean:write name="otHeaderVO" property="estimateStartDate" />-<bean:write name="otHeaderVO" property="estimateEndDate" />）
		   				（<bean:write name="otHeaderVO" property="decodeStatus" />）
		   				<logic:equal value="1" name="otHeaderVO" property="unread">
		   					<img src="mobile/images/new2.png"/>
		   				</logic:equal>
		   				<span></span></a></div>
		   			<p ></p>
			</div>
		</logic:iterate>
	</div>
</div>
</body>

<script type="text/javascript">
		(function($) {	
			fixPosition();
			// 修改未读为 已读
			readMessage();
			// 初始化用
			if($("#page").val()<$("#leave_pageCount").val()){
				$(".leave").append("<div class='loadMore' onclick='loadMore(\"loadMore\");'><span><bean:message bundle="wx" key="wx.load.more" /></span></div>");
			}
			if($("#page").val()<$("#ot_pageCount").val()){
				$(".ot").append("<div class='loadMore' onclick='loadMore(\"loadMore\");'><span><bean:message bundle="wx" key="wx.load.more" /></span></div>");
			}
			
			
		})(jQuery);
		function pClick(){
			$(".leave").show();
			$(".ot").hide();
			$(".ap").css("color","#FF3300");
			$(".ac").css("color","#000000");
		};
		function cClick(){
			$(".leave").hide();
			$(".ot").show();
			$(".ap").css("color","#000000");
			$(".ac").css("color","#FF3300");
			$("#hidden_otClick").val(Number($("#hidden_otClick").val())+1);
			if($("#hidden_otClick").val()==1){
				//修改未读为 已读
				$.post("otHeaderAction.do?proc=readMessage",{},function(){},"text");
			}
		};
		
		function fixPosition(){
			$("b[class^='biaoti2']").each(function(){
				if('1' == $(this).text()){
					$(this).parent().parent().css("margin-top","120px");
				}
			});
		};
		
		function readMessage(){
			$.post("leaveHeaderAction.do?proc=readMessage",{},function(){},"text");
		};
		
		function loadMore(actionOrStatus){
			// 先移除加载更多
			$(".loadMore").remove();
			nextPage = $("#nextPage").val();
			$.post("leaveHeaderAction.do?proc=list_object_mobile&ajax=true",{
				ajax:true,
				page:nextPage
			},function(data){
				var page = data.page;
				var realPage = data.realPage;
				var nextPage = data.realPage;
				var pageSize = data.pageSize;
				// 赋值隐藏值
				$("#realPage").val(realPage);
				$("#nextPage").val(nextPage);
				
				//添加数据
				var jsonLeaves = data.leave;
				var jsonOTs = data.ot;
				var html = "";
				var html_ot = "";
				for(var i = 0 ; i<jsonLeaves.length;i++){
					var leaveObject = jsonLeaves[i];
		   			html = html + "<div class='daiban'>";
		  			if(leaveObject.status==4){
			  			html = html+"<div class='biaoti2' onclick=\"link('leaveHeaderAction.do?proc=to_objectModify_mobile&id="+leaveObject.encodedId+"');\" >";
		  			}else{
		  				html = html + "<div class='biaoti2'>";
		  			}
		  			html = html+"<a href='#' >";
		   			html = html + "<b class='biaoti2'>"+(i+pageSize*page+1)+"</b>"+leaveObject.decodeItemId;
		   			html = html + "（"+leaveObject.estimateStartDate+"-"+leaveObject.estimateEndDate+"）";
		   			html = html + "（"+leaveObject.decodeStatus+"）";
		   			if(leaveObject.unread==1){
	   					html = html + "<img src='mobile/images/new2.png'/>";
		   			}
		   			html = html +"<span></span></a></div><p ></p>";
		   			html = html + "</div>";
				}
				for(var i = 0 ; i<jsonOTs.length;i++){
					var otObject = jsonOTs[i];
					html_ot = html_ot + "<div class='daiban'>";
					html_ot = html_ot + "<div class='biaoti2'><a href='#' >";
					html_ot = html_ot + "<b class='biaoti2'>"+(i+pageSize*page+1)+"</b>"+otObject.decodeItemId;
					html_ot = html_ot + "（"+otObject.estimateStartDate+"-"+otObject.estimateEndDate+"）";
					html_ot = html_ot + "（"+otObject.decodeStatus+"）";
		   			if(otObject.unread==1){
		   				html_ot = html_ot + "<img src='mobile/images/new2.png'/>";
		   			}
		   			html_ot = html_ot +"<span></span></a></div><p ></p>";
		   			html_ot = html_ot + "</div>";
				}
				var leave_pageCount = data.leave_pageCount;
				var ot_pageCount = data.ot_pageCount;
				if(page<leave_pageCount){
					$(".leave").append(html);
				}
				if(page<leave_pageCount-1){
					$(".leave").append("<div class='loadMore' onclick='loadMore(\"loadMore\");'><span><bean:message bundle="wx" key="wx.load.more" /></span></div>");
				}
				
				if(page<ot_pageCount){
					$(".ot").append(html_ot);
				}
				if(page<ot_pageCount-1){
					$(".ot").append("<div class='loadMore' onclick='loadMore(\"loadMore\");'><span><bean:message bundle="wx" key="wx.load.more" /></span></div>");
				}
				fixPosition();
			},"json");
		};
		
		function link(url){
			window.location.href=url;
		};
		
		function btnDeleted(id,type){
			if(confirm('<bean:message bundle="wx" key="wx.leave.list.confirm.delete" />?')){
				$.post("leaveHeaderAction.do?proc=deleteLeaveOT_mobile",{"id":id,"type":type},function(){
					location.reload();
				},"text");	
			}		
		};
		
		function btnResend(workflowId){
			if(confirm('<bean:message bundle="wx" key="wx.leave.list.confirm.resend" />')){
				$.post("workflowActualStepsAction.do?proc=re_send_mail",{"workflowId":workflowId},function(data){
					alert(data);
					location.reload();
				},"text");	
			}		
		};
</script>

