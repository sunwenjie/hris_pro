<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<style type="text/css" >
    div.pm_title{ padding-top: 10px; padding-bottom: 10px; font-family: 榛戜綋, Calibri; color: #5d5d5d; text-align: center; }
    div.pm_title span{ text-align: center; }
    .manageSelfAssessment_form #tab .tabContent table{ width: 100%; border-spacing: 0; border-collapse: collapse; border: 1px solid #dedede; margin: 10px 0px 10px 0px; }
    .manageSelfAssessment_form #tab .tabContent table th{ font-size: 13px; font-family: 榛戜綋, Calibri; background: #217FC4; color: #fff; padding: 7px; border: 1px solid #fff; text-align: left; }
    .manageSelfAssessment_form #tab .tabContent table td{ padding: 7px; line-height: 23px; }
    .pm-div-error { color: #FF0000; width: auto; font-size: 13px; font-family: 榛戜綋, Calibri; padding-left: 10px; border: none; background: none; }
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
                <h1>${year}年终考核 – 员工自评表</h1>
                <span>填写时间：${date_start} ~ ${date_end}</span> 
            </div>
            
            <form name="selfAssessmentForm" method="post" action="/selfAssessmentAction.do?proc=exportPDF" class="manageSelfAssessment_form">
                    <div class="pm-div" id="emp-base-info-DIV">
                        <ol class="auto">
                            <li>
                                <label><strong>员工ID</strong></label>
                                <label>${employeeId}</label>
                            </li>
                            <li>
                                <label><strong>BU/Function</strong></label>
                                <label>${bu}</label>
                            </li>
                            <li>
                                <label><strong>员工姓名（中文）</strong></label>
                                <label>${employeeNameZH}</label>
                            </li>
                            <li>
                                <label><strong>员工姓名（英文）</strong></label>
                                <label>${employeeNameEN}</label>
                            </li>
                            <li>
                                <label><strong>主管姓名（中文）</strong></label>
                                <label>${directLeaderNameZH}</label>
                            </li>
                            <li>
                                <label><strong>主管姓名（英文）</strong></label>
                                <label>${directLeaderNameEN}</label>
                            </li>
                            <li>
                                <label><strong>状态</strong></label>
                                <label>${status}</label>
                            </li> 
                        </ol>
                    </div>
                    
                    <div id="tab"> 
                        <div class="tabMenu"> 
                            <ul>
                                <li id="tabMenu1" onclick="changeTab(1,2);" class="hover first">员工自评</li>
                            </ul> 
                        </div>
                
                        <div class="tabContent"> 
    
                            <div id="tabContent1" class="kantab">
                                <ol>
                                    <dl>
                                        <dt><a onclick="shrinkDD(this);"><h2>工作成果<em> *</em></h2></a></dt>
                                        <dd>您的工作成果；</dd>
                                        <dd>您如何与其他人合作以达成工作成果；</dd>
                                        <dd>您的工作对公司整体的影响；</dd>
                                        <dd>如果您担任主管职务，请列出您如何带领团队达成工作成果。</dd>
                                    </dl>
                                    <li>
                                        ${accomplishments}
                                    </li>
                                    
                                    <dl>
                                        <dt><a onclick="shrinkDD(this);"><h2>优势长处<em> *</em></h2></a></dt>
                                        <dd>列出您工作上的优势长处 (2-3 项)；</dd>
                                        <dd>提供您运用您的优势长处以达成工作成果的实际案例；</dd>
                                        <dd>如果您担任主管职务，请填写您在管理职务上展现的优势长处。</dd>
                                    </dl>
                                    <li>
                                        ${areasOfStrengths}
                                    </li>
                                    
                                    <dl>
                                        <dt><a onclick="shrinkDD(this);"><h2>待改善领域<em> *</em></h2></a></dt>
                                        <dd>请列出您认为可再改善求取进步的领域 (2-3 项即可)；</dd>
                                        <dd>请具体描述您计划采取哪些行动以取得进步；</dd>
                                        <dd>您的主管可以如何协助您；</dd>
                                        <dd>如果您担任主管职务，请列出您可再改善求取进步的管理能力，以及您认为您可以如何取得进步。</dd>
                                    </dl>
                                    <li>
                                        ${areasOfImprovement}
                                    </li>
                                    
                                    <h2>
                                        接班人
                                    </h2>
                                    <li>
                                        <input type="text" name="successors" value="${successors}" id="successors" class="successors">
                                    </li>
                                    <h2>
                                        <label>其它的意见（选填）</label>
                                    </h2>
                                    <li>
                                        ${otherComments}
                                    </li>
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
