<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="transferHROwner">
    <div class="modal-header" id="transferHROwnerHeader"> 
        <a class="close" data-dismiss="modal" onclick="$('#transferHROwner').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle='security' key='transfer.hr.owner' /></label>
    </div>
    <div class="modal-body">
        <div class="top">
            <input type="button" class="save" name="btnConrirm" id="btnConrirm" value="<bean:message bundle="public" key="button.confirm" />" onclick="confirmSelect();" />
            <input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetFrom()" value="<bean:message bundle="public" key="button.reset" />" />
        </div>
        <html:form action="positionAction.do?proc=transferHROwner" method="post" styleClass="transferHROwner_form">
            <fieldset>
                <ol class="auto">
                    <li>
                        <label><bean:message bundle='security' key='security.entity' /></label>
                        <select class="entityId" name="entityId" id="entityId">
                            <logic:iterate id="entity" name="entities">
                                <option value='<bean:write name="entity" property="mappingId" />'>
                                    <bean:write name="entity" property="mappingValue" />
                                </option>
                            </logic:iterate>
                        </select>
                    </li>
                </ol>
                <ol class="auto">
                    <li>
                        <label>EX- HR Owner<em> *</em></label>
                        <input class="oldHROwnerName" type="text" id="oldHROwnerName" /> 
                        <input class="oldHROwner" type="hidden" name="oldHROwner" id="oldHROwner" />
                    </li>
                    <li>
                        <label>New HR Owner<em> *</em></label>
                        <input class="newHROwnerName" type="text" id="newHROwnerName" /> 
                        <input class="newHROwner" type="hidden" name="newHROwner" id="newHROwner" />
                    </li>
                </ol>
            </fieldset>
        </html:form >
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
    // 选择重置 
    function resetFrom(){
    	$('#entityId').val(null);
    	$('#oldHROwnerName').val('');
    	$('#oldHROwnerName').focus();
    	$('#oldHROwnerName').blur();
    	$('#newHROwnerName').val('');
        $('#newHROwnerName').focus();
        $('#newHROwnerName').blur();
    };
    
    // 确认
    function confirmSelect(){
        var oldHROwnerName_ERROR = validate("oldHROwner", true, "common", 100, 0);
        if( oldHROwnerName_ERROR > 0) {
        	$('#oldHROwnerName').addClass('error')
        } else {
        	$('#oldHROwnerName').removeClass('error')
        }
        var newHROwnerName_ERROR = validate("newHROwner", true, "common", 100, 0);
        if( newHROwnerName_ERROR > 0) {
            $('#newHROwnerName').addClass('error')
        } else {
            $('#newHROwnerName').removeClass('error')
        }
        
        if( oldHROwnerName_ERROR == 0 && newHROwnerName_ERROR == 0 ) {
        	submit('transferHROwner_form');
        }
    };
    
    // 弹出模态窗口
    function popupTransferHROwner(){
        $('#transferHROwner').removeClass('hide');
        $('#shield').show();
    };
    
    // Use the common thinking
    kanThinking_column('oldHROwnerName', 'oldHROwner', 'positionAction.do?proc=list_object_json');
    kanThinking_column('newHROwnerName', 'newHROwner', 'positionAction.do?proc=list_object_json');
    
    // Esc按键事件 - 隐藏弹出框
    $(document).keyup(function(e){
        if (e.keyCode == 27) 
        {
            $('#transferHROwner').addClass('hide');
            $('#shield').hide();
        }
    });
</script>