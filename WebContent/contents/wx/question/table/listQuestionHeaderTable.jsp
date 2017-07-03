<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder questionHeaderHolder = (PagedListHolder) request.getAttribute( "questionHeaderHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=questionHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listQuestionHeader_form', null, null, 'headerId', '<%=questionHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');">
					ID
				</a>
			</th>	
			<th style="width: 30%" class="header <%=questionHeaderHolder.getCurrentSortClass("titleZH")%>">
				<a onclick="submitForm('listQuestionHeader_form', null, null, 'titleZH', '<%=questionHeaderHolder.getNextSortOrder("titleZH")%>', 'tableWrapper');">
					Question Title (Chinese)
				</a>
			</th>					
			<th style="width: 30%" class="header <%=questionHeaderHolder.getCurrentSortClass("titleEN")%>">
				<a onclick="submitForm('listQuestionHeader_form', null, null, 'titleEN', '<%=questionHeaderHolder.getNextSortOrder("titleEN")%>', 'tableWrapper');">
					Question Title (English)
				</a>
			</th>	
			<th style="width: 20%" class="header <%=questionHeaderHolder.getCurrentSortClass("bankId")%>">
				<a onclick="submitForm('listQuestionHeader_form', null, null, 'bankId', '<%=questionHeaderHolder.getNextSortOrder("bankId")%>', 'tableWrapper');">
					Expiration Date
				</a>
			</th>	
			<th style="width: 10%" class="header <%=questionHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listQuestionHeader_form', null, null, 'status', '<%=questionHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');">
					Status
				</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="questionHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="questionHeaderVO" name="questionHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="questionHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="questionHeaderVO" property="headerId"/>" />
					</td>
					<td class="left"><a onclick="link('questionDetailAction.do?proc=list_object&id=<bean:write name="questionHeaderVO" property="encodedId"/>');"><bean:write name="questionHeaderVO" property="headerId" /></a></td>
					<td class="left"><a onclick="link('questionDetailAction.do?proc=list_object&id=<bean:write name="questionHeaderVO" property="encodedId"/>');"><bean:write name="questionHeaderVO" property="titleZH" /></a></td>
					<td class="left"><a onclick="link('questionDetailAction.do?proc=list_object&id=<bean:write name="questionHeaderVO" property="encodedId"/>');"><bean:write name="questionHeaderVO" property="titleEN" /></a></td>
					<td class="left"><bean:write name="questionHeaderVO" property="expirationDate" /></td>
					<td class="left">
						<bean:write name="questionHeaderVO" property="decodeStatus" />
						<a class="kanhandle" onclick="showAnswerModal('<bean:write name="questionHeaderVO" property="encodedId" />');"><img src="images/search.png" title=""  /></a>
					<%-- 	<a class="copy_a">copy</a>
						<input value="<bean:write name="questionHeaderVO" property="headerId"/>" /> --%>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="questionHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="questionHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="questionHeaderHolder" property="indexStart" /> - <bean:write name="questionHeaderHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listQuestionHeader_form', null, '<bean:write name="questionHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listQuestionHeader_form', null, '<bean:write name="questionHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listQuestionHeader_form', null, '<bean:write name="questionHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listQuestionHeader_form', null, '<bean:write name="questionHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="questionHeaderHolder" property="realPage" />/<bean:write name="questionHeaderHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>

<script type="text/javascript">
	/* (function($) {
		
		var clip = new ZeroClipboard.Client(); // 新建一个对象 
		clip.setHandCursor( true ); // 设置鼠标为手型 
		clip.setText("哈哈"); // 设置要复制的文本。 
		// 注册一个 button，参数为 id。点击这个 button 就会复制。 
		//这个 button 不一定要求是一个 input 按钮，也可以是其他 DOM 元素。 
		clip.glue("copy-botton"); // 和上一句位置不可调换 
		
		$(document).ready(function(){  
            if ( window.clipboardData ) {  
                $('.copy_a').click(function() {  
                	var init = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx059471abeb063951&redirect_uri=https%3a%2f%2fhris.i-click.com%2fanswerAction.do%3fproc%3dto_answer%26q_id%3d"+ $(this).prev('input').val() +"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
                    window.clipboardData.setData("Text", init);  
                    alert('复制成功！');  
                });  
            } else {  
                $(".copy_a").zclip({  
                    path:'http://img3.job1001.com/js/ZeroClipboard/ZeroClipboard.swf',  
                    copy:function(){return $(this).prev('input').val();},  
                    afterCopy:function(){alert('复制成功！');}  
                });  
            }  
        });   
	})(jQuery); */
</script>