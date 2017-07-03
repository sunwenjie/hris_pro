<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<table class="table hover" id="resultTable">
    <thead>
        <tr>
            <th style="width: 10%" ><bean:message bundle="performance" key="performance.show.progress.employee.name" /></th>
            <th style="width: 10%" ><bean:message bundle="performance" key="performance.show.progress.bl" /></th>
            <th style="width: 10%" ><bean:message bundle="performance" key="performance.show.progress.biz.peer" /> 1</th>
            <th style="width: 10%" ><bean:message bundle="performance" key="performance.show.progress.biz.peer" /> 2</th>
            <th style="width: 10%" ><bean:message bundle="performance" key="performance.show.progress.biz.peer" /> 3</th>
            <th style="width: 10%" ><bean:message bundle="performance" key="performance.show.progress.biz.peer" /> 4</th>
            <th style="width: 10%" ><bean:message bundle="performance" key="performance.show.progress.pm" /></th>
            <th style="width: 10%" ><bean:message bundle="performance" key="performance.show.progress.bu" /></th>
        </tr>
    </thead>
    <logic:present name="selfAssessmentVO">
        <tbody>
                <tr>
                    <td class="left">
                    <% 
                    if("zh".equalsIgnoreCase(request.getLocale().getLanguage())){
                      %>
                      <bean:write name="selfAssessmentVO" property="employeeNameZH" />
                      <%
                    }else{
                      %>
                      <bean:write name="selfAssessmentVO" property="employeeNameEN" />
                      <%
                    }
                    %>
                    </td>
                    <td class="left"><bean:write name="BL_NAME" />
                       <logic:equal value="2" name="selfAssessmentVO" property="status_bm">
                        <img alt="" width="16px" src="images/true.png">
                        </logic:equal>
                    </td>
                    
                    <td class="left"><bean:write name="PM_NAME_1"/>
                        <logic:equal value="2" name="selfAssessmentVO" property="status_pm1">
                        <img alt="" width="16px" src="images/true.png">
                        </logic:equal>
                    </td>
                    
                    <td class="left"><bean:write name="PM_NAME_2"/>
                        <logic:equal value="2" name="selfAssessmentVO" property="status_pm2">
                        <img alt="" width="16px" src="images/true.png">
                        </logic:equal>
                    </td>
                    
                    <td class="left"><bean:write name="PM_NAME_3"/>
                        <logic:equal value="2" name="selfAssessmentVO" property="status_pm3">
                        <img alt="" width="16px" src="images/true.png">
                        </logic:equal>
                    </td>
                    
                    <td class="left"><bean:write name="PM_NAME_4"/>
                        <logic:equal value="2" name="selfAssessmentVO" property="status_pm4">
                        <img alt="" width="16px" src="images/true.png">
                        </logic:equal>
                    </td>
                    
                    <td class="left"><bean:write name="PM_NAME" />
                        <logic:equal value="2" name="selfAssessmentVO" property="status_pm">
                        <img alt="" width="16px" src="images/true.png">
                        </logic:equal>
                    </td>
                    
                    <td class="left"><bean:write name="BU_NAME" />
                        <logic:equal value="2" name="selfAssessmentVO" property="status_bu">
                        <img alt="" width="16px" src="images/true.png">
                        </logic:equal>
                    </td>
                </tr>
        </tbody>
    </logic:present>
    
</table>