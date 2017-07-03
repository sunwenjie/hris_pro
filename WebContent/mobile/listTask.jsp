<%@page import="java.util.List"%>
<%@page import="com.kan.base.domain.message.TempMessageVO"%>
<%@page import="com.kan.base.domain.workflow.WorkflowActualVO"%>
<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.js"></script>
<link rel="stylesheet" href="mobile/css/bootstrap.min.css">
<link rel="stylesheet" href="mobile/css/mui.min.css">
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
<style type="text/css">
	body{
		padding: 15px;
	}
	.btn{
		width: 100%;
	}
	.table{
		border-top: 0px;
	}
	p{
		padding: 15px;
	}
	td{
		padding-top: 15px;
	}
	.info .btn{
		padding: 0px;
	}
	.nodata,.loadmore{
		text-align: center;
	}
	.mui-badge {
		margin: 10px;
	}
	.loadmorebtn{
		color: blue;
	}
	.none-loadmorebtn{
		color: gray;
	}
	td{
		width: 20%;
	}
	.span-time{
		
	}
</style>
</head>
<body>
<%
	PagedListHolder listHolder = (PagedListHolder)request.getAttribute("workflowActualHolder");
%>
<input type="hidden" name="realPage" id="realPage" value="<%=listHolder.getRealPage() %>"/>
<input type="hidden" name="pageCount" id="pageCount" value="<%=listHolder.getPageCount() %>"/>
<input type="hidden" name="nextPage" id="nextPage" value="<%=listHolder.getNextPage() %>"/>
<input type="hidden" name="actualStepStatus" id="actualStepStatus" value=""/>
<div class="table-responsive">
<table class="table">
	<tr>
		<td><a class="loadmorebtn" id="titlecolor0" href="#" onclick="loadMore(0);"><bean:message bundle="wx" key="wx.list.task.all" /></a></td>
		<td><a class="none-loadmorebtn" id="titlecolor2" href="#" onclick="loadMore(2);"><bean:message bundle="wx" key="wx.list.task.to.deal" /></a></td>
		<td><a class="none-loadmorebtn" id="titlecolor3" href="#" onclick="loadMore(3);"><bean:message bundle="wx" key="wx.list.task.agree" /></a></td>
		<td><a class="none-loadmorebtn" id="titlecolor4" href="#" onclick="loadMore(4);"><bean:message bundle="wx" key="wx.list.task.disagree" /></a></td>
		<td><a class="none-loadmorebtn" id="titlecolor1" href="#" onclick="loadMore(1);"><bean:message bundle="wx" key="wx.list.task.to.start" /></a></td>
	</tr>
</table>
</div>	
<div class="contain">
	<%
		if(((PagedListHolder)request.getAttribute("workflowActualHolder")).getSource().size()==0){
		   %>
		   <p class="nodata bg-danger">NO DATA!</p>
		   <p class="info bg-warning">	
		   		<a type="button" class="btn" onclick="btnBack();"><bean:message bundle="wx" key="wx.btn.main.page" /></a>
		   </p>
		   <% 
		}else{
		   %>
		   <logic:iterate id="workflowActualVO" name="workflowActualHolder" property="source" indexId="number">
		   <p class="bg-info" onclick="link('workflowActualStepsAction.do?proc=list_object_mobile&workflowId=<bean:write name="workflowActualVO" property="encodedId"/>');">
		   		<span class="mui-badge mui-badge-success"><%=number+1%></span>
		   		<%
   					if("zh".equalsIgnoreCase(request.getLocale().getLanguage())){
   					   %>
		   					<bean:write name="workflowActualVO" property="nameZH" />
   					   <%
   					}else{
   					   %>
	   					<bean:write name="workflowActualVO" property="nameEN" />
					   <%
   					}
		   		%>
		   		<span class="span-time">
		   			<br/>
		   			<bean:write name="workflowActualVO" property="decodeCreateDate" />
		   		</span>
		   </p>
		  
		</logic:iterate>	
		<%
		}
		%>

</div>
</body>
<script type="text/javascript">
	(function($) {	
		if($("#realPage").val()<$("#pageCount").val()){
			$("body").append("<p class='loadMore bg-success' onclick='loadMore(\"loadMore\");'><span><bean:message bundle="wx" key="wx.load.more" /></span></p>");
		}
		// 修正位置
		fixPosition();
		
	})(jQuery);
	function btnBack(){
		link('securityAction.do?proc=index_mobile');
	}
	function loadMore(actionOrStatus){
		
		// 先移除加载更多
		$(".loadMore").remove();
		var nextPage = '0';
		if(actionOrStatus=='loadMore'){
			nextPage = $("#nextPage").val();
		}else{
			$("#actualStepStatus").val(actionOrStatus);
			titleColorChange(actionOrStatus);
		}
		$.post("workflowActualAction.do?proc=list_object_unfinished_mobile&actualStepStatus="+$("#actualStepStatus").val(),{
			ajax:true,
			page:nextPage
		},function(data){
			var workflowActualJsonArray = data.workflowActualJsonArray;
			var page = data.page;
			var realPage = data.realPage;
			var pageCount = data.pageCount;
			var nextPage = data.realPage;
			var pageSize = data.pageSize;
			// 赋值隐藏值
			$("#realPage").val(realPage);
			$("#pageCount").val(pageCount);
			$("#nextPage").val(nextPage);
			
			//添加数据
			var html = "";
			for(var i = 0 ; i<workflowActualJsonArray.length;i++){
				var workflowActualJsonObject = workflowActualJsonArray[i];
	   			html = html + "<p class='bg-info' onclick='link(\"workflowActualStepsAction.do?proc=list_object_mobile&workflowId="+workflowActualJsonObject.encodedId+"\");'>";
	   			html = html + "<span class='mui-badge mui-badge-success'>"+(i+pageSize*page+1)+"</span>";
	   			<%
	   				if("zh".equalsIgnoreCase(request.getLocale().getLanguage())){
				%>
					html = html + workflowActualJsonObject.nameZH;
	   			<%
	   				}else{
	   			%>
	   				html = html + workflowActualJsonObject.nameEN;
	   			<%
	   				}
	   			%>
	   			
	   			html = html + "<span class='span-time'><br/>"+workflowActualJsonObject.decodeCreateDate+"</span</p>";
			}
			if(actionOrStatus!='loadMore'){
				$("body").find(".bg-info").each(function(){
					$(this).remove();
				});
			}
			
			$(".contain").append(html);
			if(realPage<pageCount){
				$(".contain").append("<p class='loadMore bg-success' onclick='loadMore(\"loadMore\");'><span><bean:message bundle="wx" key="wx.load.more" /></span></p>");
			}
			fixPosition();
		},"json");
	};
	
	function fixPosition(){
		$("b[class^='biaoti2_']").each(function(){
			if('1' == $(this).text()){
				$(this).parent().parent().css("margin-top","120px");
			}
		});
	};
	
	function titleColorChange(actionOrStatus){
		//先全部变灰
		$("a[id^='titlecolor']").removeClass("loadmorebtn").removeClass("none-loadmorebtn");
				
		$("a[id^='titlecolor']").addClass("none-loadmorebtn");
		$("#titlecolor"+actionOrStatus).removeClass("none-loadmorebtn").addClass("loadmorebtn");
		
	};
</script>

