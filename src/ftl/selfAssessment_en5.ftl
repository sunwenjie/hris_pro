<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<style type="text/css" >
    div.pm_title{ padding-top: 10px; padding-bottom: 10px; font-family: 黑体, Calibri; color: #5d5d5d; text-align: center; }
    div.pm_title span{ text-align: center; }
    .manageSelfAssessment_form #tab .tabContent table{ width: 100%; border-spacing: 0; border-collapse: collapse; border: 1px solid #dedede; margin: 10px 0px 10px 0px; }
    .manageSelfAssessment_form #tab .tabContent table th{ font-size: 13px; font-family: 黑体, Calibri; background: #217FC4; color: #fff; padding: 7px; border: 1px solid #fff; text-align: left; }
    .manageSelfAssessment_form #tab .tabContent table td{ padding: 7px; line-height: 23px; }
    .pm-div-error { color: #FF0000; width: auto; font-size: 13px; font-family: 黑体, Calibri; padding-left: 10px; border: none; background: none; }
    .manageSelfAssessment_form #tab .tabContent ol li{ width: 100%; }
    .manageSelfAssessment_form #tab .tabContent ol li textarea{ width: 98%; }
    .manageSelfAssessment_form #tab .tabContent ol h2 em{ color: #aa4935 }
    .manageSelfAssessment_form #tab .tabContent ol li.radio_li label{ padding-left: 5px; }
    body,.box{margin: 0px!important;}
    .auto li label{width: 130px;!important;height:23px;}
</style>
<link href="kanpower.css" rel="stylesheet">
</head>
<body>
<div id="content1" style="width:800px;">
    <div class="box toggableForm">
        <div class="inner">
            <div class="pm_title">
                <h1>${year} Year End Review – Employee Assessment Form</h1>
                <span>Effective time:${date_start} ~ ${date_end}</span> 
            </div>
            
            <form name="selfAssessmentForm" method="post" action="/selfAssessmentAction.do?proc=exportPDF" class="manageSelfAssessment_form">
                    <div class="pm-div" id="emp-base-info-DIV">
                        <ol class="auto">
                            <li>
                                <label><strong>Employee ID</strong></label>
                                <label>${employeeId}</label>
                            </li>
                            <li>
                                <label><strong>BU/Function</strong></label>
                                <label>${bu}</label>
                            </li>
                            <li>
                                <label><strong>Chinese Name</strong></label>
                                <label>${employeeNameZH}</label>
                            </li>
                            <li>
                                <label><strong>Full Name</strong></label>
                                <label>${employeeNameEN}</label>
                            </li>
                            <li>
                                <label><strong>Direct manager's name( Chinese )</strong></label>
                                <label>${directLeaderNameZH}</label>
                            </li>
                            <li>
                                <label><strong>Direct manager's name( English )</strong></label>
                                <label>${directLeaderNameEN}</label>
                            </li>
                            <li>
                                <label><strong>Status</strong></label>
                                <label>${status}</label>
                            </li> 
                        </ol>
                    </div>
                    
                    <div id="tab"> 
                        <div class="tabMenu"> 
                            <ul>
                                <li id="tabMenu1" onclick="changeTab(1,2);" class="hover first">Self Assessment</li>
                            </ul> 
                        </div>
                
                        <div class="tabContent"> 
    
                            <div id="tabContent1" class="kantab">
                                <ol>
                                    <dl>
                                        <dt><a onclick="shrinkDD(this);"><h2>Accomplishments<em> *</em></h2></a></dt>
                                        <dd>Your achievements;</dd>
                                        <dd>How you worked with others to achieve your results;</dd>
                                        <dd>Overall impact to iClick;</dd>
                                        <dd>If you are a people manager, also include how you lead the team to deliver results.</dd>
                                    </dl>
                                    <li>
                                        ${accomplishments}
                                    </li>
                                    
                                    <dl>
                                        <dt><a onclick="shrinkDD(this);"><h2>Areas of Strengths<em> *</em></h2></a></dt>
                                        <dd>List your strengths (2-3 recommended);</dd>
                                        <dd>Provide examples of how you demonstrated these strengths to deliver results;</dd>
                                        <dd>If you are a people manager, please provide input on your strengths as a manager.</dd>
                                    </dl>
                                    <li>
                                        ${areasOfStrengths}
                                    </li>
                                    
                                    <dl>
                                        <dt><a onclick="shrinkDD(this);"><h2>Areas of Improvement<em> *</em></h2></a></dt>
                                        <dd>List your areas of improvement (2-3 recommended);</dd>
                                        <dd>Describe specific action(s) you will take to improve these areas;</dd>
                                        <dd>Indicate how your manager can support you;</dd>
                                        <dd>If you are a people manager, please include which management areas of development and how they could be improved.</dd>
                                    </dl>
                                    <li>
                                        ${areasOfImprovement}
                                    </li>
                                    
                                    <h2>
                                        Successor
                                    </h2>
                                    <li>
                                        <input type="text" name="successors" value="${successors}" id="successors" class="successors">
                                    </li>
                                    <h2>
                                        <label>Enter optional comments here</label>
                                    </h2>
                                    <li>
                                        ${otherComments}
                                    </li>
                                </ol>
                            </div>

                                


                            <div id="tab"> 
                                <div class="tabMenu"> 
                                    <ul>
                                        <li id="tabMenu2" class="hover first">Evaluation - People Manager</li>
                                    </ul> 
                                </div>
                             </div>
                            <div id="tabContent2" class="kantab">
                                <ol style="border-bottom: none;">
                                    <dl>
                                        <dt><a onclick="shrinkDD(this);"><h2>Accomplishments</h2></a></dt>
                                        <dd>Your employee's achievements;</dd>
                                        <dd>How your employee worked with others to achieve results;</dd>
                                        <dd>Overall impact to iClick;</dd>
                                        <dd>If your employee is a people manager, also include how they demonstrated in team management.</dd>
                                    </dl>
                                    <li>
                                        ${accomplishments_pm}
                                    </li>
                                    
                                    <dl>
                                        <dt><a onclick="shrinkDD(this);"><h2>Areas of Strength</h2></a></dt>
                                        <dd>Review and comment on your employee's strengths (2-3 recommended);</dd>
                                        <dd>Provide examples of how your employee demonstrated these strengths to deliver results;</dd>
                                        <dd>If your employee is a people manager, please provide input on their strengths as a manager.</dd>
                                    </dl>
                                    <li>
                                        ${areasOfStrengths_pm}
                                    </li>
                                    
                                    <dl>
                                        <dt><a onclick="shrinkDD(this);"><h2>Areas of Improvement</h2></a></dt>
                                        <dd>Review and comment on your employee's areas of improvement (2-3 recommended);</dd>
                                        <dd>Describe specific action(s) your employee should take to improve these areas;</dd>
                                        <dd>Indicate how you will provide support;</dd>
                                        <dd>If your employee is a people manager, please include which management areas of improvement and how they could be improved.</dd>
                                    </dl>
                                    <li>
                                        ${areasOfImprovement_pm}
                                    </li>
                                    
                                    <h2>
                                        Promotion or not
                                    </h2>
                                    <li class="radio_li">
                                        <label><input type="radio" name="isPromotion_pm" value="1" ${isPromotion_pm_check1} id="isPromotion_pm" class="isPromotion_pm">Yes</label>
                                        <label><input type="radio" name="isPromotion_pm" value="2" ${isPromotion_pm_check2} id="isPromotion_pm" class="isPromotion_pm">No</label>
                                        <label><input type="radio" name="isPromotion_pm" value="3" ${isPromotion_pm_check3} id="isPromotion_pm" class="isPromotion_pm">No Opinion</label>
                                    </li>
                                    <li>
                                        <label>Justification for promotion</label>
                                        ${promotionReason_pm}
                                    </li>
                                    <h2>
                                        Successor
                                    </h2>
                                    <li>
                                        <input type="text" name="successors_pm" value="${successors_pm}" id="successors_pm" class="successors_pm">
                                    </li>
                                    <h2>
                                        Performance Rating
                                    </h2>
                                    <table>
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>Greatly Exceeds</th>
                                                <th>Exceeds</th>
                                                <th>Achieve</th>
                                                <th>Occasionally Misses</th>
                                                <th>Misses</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr id="rating_final_row" class="odd">
                                                <td>Performance Rating</td>
                                                <td>
                                                    <label><input type="radio" name="rating_final" value="5" ${rating_final_5_0} class="rating_final">5</label>
                                                </td>
                                                <td>
                                                    <label><input type="radio" name="rating_final" value="4.5" ${rating_final_4_5} class="rating_final">4.5</label>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <label><input type="radio" name="rating_final" value="4" ${rating_final_4_0} class="rating_final">4</label>
                                                </td>
                                                <td>
                                                    <label><input type="radio" name="rating_final" value="3.5" ${rating_final_3_5} class="rating_final">3.5</label>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <label><input type="radio" name="rating_final" value="3" ${rating_final_3_0} class="rating_final">3</label>
                                                </td>
                                                <td>
                                                    <label><input type="radio" name="rating_final" value="2.5" ${rating_final_2_5} class="rating_final">2.5</label>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <label><input type="radio" name="rating_final" value="2" ${rating_final_2_0} class="rating_final">2</label>
                                                </td>
                                                <td>
                                                    <label><input type="radio" name="rating_final" value="1" ${rating_final_1_0} class="rating_final">1</label>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </ol>
                            </div>

                        </div>
                    </div>
                    
            </form>
        </div>
    </div>
</div>
<div id="footer">
    <p>&copy;iClick</p>
</div> 

</body>
</html>

