package com.kan.hro.web.actions.biz.performance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.pdf.PDFTool;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.UndefineExcelExport;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;
import com.kan.hro.domain.biz.performance.SelfAssessmentVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.performance.BudgetSettingHeaderService;
import com.kan.hro.service.inf.biz.performance.SelfAssessmentService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

import net.sf.json.JSONObject;

public class SelfAssessmentAction extends BaseAction {
  // Action 标识
  public static final String ACCESS_ACTION = "HRO_PM_SELF_ASSESSMENT";

  public static final String ACCESS_ACTION_GOAL = "HRO_PM_GOAL_SETTING";

  // 角色 - 普通员工
  public static final String ROLE_EE = new String("1");

  // 角色 - 业务主管
  public static final String ROLE_BL = new String("2");

  // 角色 - 直系主管
  public static final String ROLE_PM = new String("3");

  // 角色 - 部门老大
  public static final String ROLE_BU = new String("4");

  // 角色 - HR对接人
  public static final String ROLE_HR = new String("5");

  // 角色 - 直线主管兼部门老大
  public static final String ROLE_PM_AND_BU = new String("6");

  // 角色 - 非直系主管
  public static final String ROLE_PM_NON_DIRECT_1 = new String("7");

  // 角色 - 非直系主管
  public static final String ROLE_PM_NON_DIRECT_2 = new String("8");

  // 角色 - 非直系主管
  public static final String ROLE_PM_NON_DIRECT_3 = new String("9");

  // 角色 - 非直系主管
  public static final String ROLE_PM_NON_DIRECT_4 = new String("10");

  @Override
  public ActionForward list_object(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      // 获得当前页
      final String page = request.getParameter("page");
      // 获得是否Ajax调用
      final String ajax = request.getParameter("ajax");
      // 初始化Service接口
      final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
      // 获得Action Form
      final SelfAssessmentVO selfAssessmentVO = (SelfAssessmentVO) form;
      // HR_Service登录、IN_House登录
      if (KANConstants.ROLE_IN_HOUSE.equals(BaseAction.getRole(request, response)) || KANConstants.ROLE_HR_SERVICE.equals(BaseAction.getRole(request, response))) {
        setDataAuth(request, response, selfAssessmentVO);
      } // 雇员登录
      else if (BaseAction.getRole(request, response).equalsIgnoreCase(KANConstants.ROLE_EMPLOYEE)) {
        selfAssessmentVO.setEmployeeId(EmployeeSecurityAction.getEmployeeId(request, response));
      }

      selfAssessmentVO.setSelfPositionId(getPositionId(request, null));
      selfAssessmentVO.setEmployeeId(getEmployeeId(request, null));
      // 调用删除方法
      if (selfAssessmentVO.getSubAction() != null && selfAssessmentVO.getSubAction().equalsIgnoreCase(DELETE_OBJECTS)) {
        delete_objectList(mapping, form, request, response);
      } else {
        decodedObject(selfAssessmentVO);
      }

      // 初始化PagedListHolder，用于引用方式调用Service
      PagedListHolder selfAssessmentHolder = new PagedListHolder();
      // 传入当前页
      selfAssessmentHolder.setPage(page);
      // 传入当前值对象
      selfAssessmentHolder.setObject(selfAssessmentVO);
      // 设置页面记录条数
      selfAssessmentHolder.setPageSize(listPageSize);
      boolean isDownload = getSubAction(form).equalsIgnoreCase(DOWNLOAD_OBJECTS) || "exportProgress".equalsIgnoreCase(getSubAction(form));
      // 调用Service方法，引用对象返回，第二个参数说明是否分页
      selfAssessmentService.getSelfAssessmentVOsByCondition(selfAssessmentHolder, !isDownload);
      // 刷新Holder，国际化传值
      refreshHolder(selfAssessmentHolder, request);
      PositionVO positionVO = KANConstants.getKANAccountConstants(getAccountId(request, response)).getPositionVOByPositionId(getPositionId(request, response));
      if (positionVO != null) {
        List<GroupDTO> groupDTOs = KANConstants.getKANAccountConstants(getAccountId(request, response)).getGroupDTOsByPositionId(positionVO.getPositionId());
        if (groupDTOs != null && groupDTOs.size() > 0) {
          for (GroupDTO groupDTO : groupDTOs) {
            if ("1".equalsIgnoreCase(groupDTO.getGroupVO().getHrFunction())) {
              request.setAttribute("isHR", true);
              break;
            }
          }
        }
      }
      // Holder需写入Request对象
      request.setAttribute("selfAssessmentHolder", selfAssessmentHolder);
      // Ajax调用
      if (new Boolean(ajax)) {
        if (isDownload) {
          // Excel文件类型
          XSSFWorkbook workbook = null;
          String fileName = "";
          if ("exportProgress".equalsIgnoreCase(getSubAction(form))) {
            workbook = UndefineExcelExport.exportSelfAssessmentProgress(request);
            fileName = KANUtil.getProperty(request.getLocale(), "performance.export.progress") + " " + KANUtil.formatDate(new Date(), "yyyyMMddHHmmss");
          } else {
            workbook = UndefineExcelExport.generateSelfAssessment(request);
            fileName = KANUtil.getProperty(request.getLocale(), "emp.self.assessment") + " " + KANUtil.formatDate(new Date(), "yyyyMMddHHmmss");
          }
          // 初始化OutputStream
          final OutputStream os = response.getOutputStream();
          // 设置返回文件下载
          response.setContentType("application/x-msdownload");
          // 解决文件中文名下载问题
          response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(URLDecoder.decode(fileName + ".xlsx", "UTF-8").getBytes(), "iso-8859-1") + "\"");
          // Excel文件写入OutputStream
          workbook.write(os);
          // 输出OutputStream
          os.flush();
          // 关闭流
          os.close();
          return mapping.findForward("");
        } else {
          // Ajax Table调用，直接传回Item JSP
          request.setAttribute("accountId", getAccountId(request, null));
          return mapping.findForward("listSelfAssessmentTable");
        }
      }
    } catch (final Exception e) {
      throw new KANException(e);
    }
    // 跳转JSP页面
    return mapping.findForward("listSelfAssessment");
  }

  /***
   * 目标设定列表
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
  public ActionForward list_goal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      // 获得当前页
      final String page = request.getParameter("page");
      // 获得是否Ajax调用
      final String ajax = request.getParameter("ajax");
      // 初始化Service接口
      final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
      // 获得Action Form
      final SelfAssessmentVO selfAssessmentVO = (SelfAssessmentVO) form;
      // HR_Service登录、IN_House登录
      if (KANConstants.ROLE_IN_HOUSE.equals(BaseAction.getRole(request, response)) || KANConstants.ROLE_HR_SERVICE.equals(BaseAction.getRole(request, response))) {
        setDataAuth(request, response, selfAssessmentVO);
      } // 雇员登录
      else if (BaseAction.getRole(request, response).equalsIgnoreCase(KANConstants.ROLE_EMPLOYEE)) {
        selfAssessmentVO.setEmployeeId(EmployeeSecurityAction.getEmployeeId(request, response));
      }
      selfAssessmentVO.setSelfPositionId(getPositionId(request, null));
      selfAssessmentVO.setEmployeeId(getEmployeeId(request, null));
      // 调用删除方法
      if (selfAssessmentVO.getSubAction() != null && selfAssessmentVO.getSubAction().equalsIgnoreCase(DELETE_OBJECTS)) {
        delete_objectList(mapping, form, request, response);
      } else {
        decodedObject(selfAssessmentVO);
      }

      // 初始化PagedListHolder，用于引用方式调用Service
      PagedListHolder selfAssessmentHolder = new PagedListHolder();
      // 传入当前页
      selfAssessmentHolder.setPage(page);
      // 传入当前值对象
      selfAssessmentHolder.setObject(selfAssessmentVO);
      // 设置页面记录条数
      selfAssessmentHolder.setPageSize(listPageSize);
      // 调用Service方法，引用对象返回，第二个参数说明是否分页
      selfAssessmentService.getSelfAssessmentVOsByCondition(selfAssessmentHolder, true);
      // 刷新Holder，国际化传值
      refreshHolder(selfAssessmentHolder, request);
      // Holder需写入Request对象
      request.setAttribute("selfAssessmentHolder", selfAssessmentHolder);
      // Ajax调用
      if (new Boolean(ajax)) {
        // Ajax Table调用，直接传回Item JSP
        request.setAttribute("accountId", getAccountId(request, null));
        return mapping.findForward("listGoalTable");
      }
    } catch (final Exception e) {
      throw new KANException(e);
    }
    // 跳转JSP页面
    return mapping.findForward("listGoal");
  }

  // 根据request中的pageRole控制页面跳转
  private void controlPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    String positionId = getPositionId(request, null);
    SelfAssessmentVO selfAssessmentVO = (SelfAssessmentVO) form;
    // 如果是员工本人
    if (((SelfAssessmentVO) form).getEmployeeId().equals(getEmployeeId(request, null))) {
      request.setAttribute("pageRole", ROLE_EE);
    }
    // 非员工本人
    else {
      if (positionId.equals(selfAssessmentVO.getHrOwnerPositionId())) {
        request.setAttribute("pageRole", ROLE_HR);
      }
      if (positionId.equals(selfAssessmentVO.getBizLeaderPositionId())) {
        request.setAttribute("pageRole", ROLE_BL);
      }
      if (positionId.equals(selfAssessmentVO.getParentPositionId()) && !positionId.equals(selfAssessmentVO.getBuLeaderPositionId())) {
        // 如果是PM 但是不是BU 临时清空业务主管的评估内容
        selfAssessmentVO.tempReset_bl();
        request.setAttribute("pageRole", ROLE_PM);
      }

      if (positionId.equals(selfAssessmentVO.getPm1PositionId())) {
        request.setAttribute("pageRole", ROLE_PM_NON_DIRECT_1);
      } else if (positionId.equals(selfAssessmentVO.getPm2PositionId())) {
        request.setAttribute("pageRole", ROLE_PM_NON_DIRECT_2);
      } else if (positionId.equals(selfAssessmentVO.getPm3PositionId())) {
        request.setAttribute("pageRole", ROLE_PM_NON_DIRECT_3);
      } else if (positionId.equals(selfAssessmentVO.getPm4PositionId())) {
        request.setAttribute("pageRole", ROLE_PM_NON_DIRECT_4);
      }

      if (positionId.equals(selfAssessmentVO.getBuLeaderPositionId())) {
        request.setAttribute("pageRole", ROLE_BU);
      }
      if (positionId.equals(selfAssessmentVO.getParentPositionId()) && positionId.equals(selfAssessmentVO.getBuLeaderPositionId())) {
        request.setAttribute("pageRole", ROLE_PM_AND_BU);
      }
    }
  }

  // 过滤超过有效日期的自评
  private boolean filterOutsideValidDate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    Integer year = ((SelfAssessmentVO) form).getYear();
    if (year == null || year == 0) {
      year = Integer.valueOf(KANUtil.formatDate(new Date(), "yyyy"));
    }
    // 不在填写日期范围内
    final BudgetSettingHeaderService budgetSettingHeaderService = (BudgetSettingHeaderService) getService("budgetSettingHeaderService");
    final Map<String, Object> mapParameter = new HashMap<String, Object>();
    mapParameter.put("accountId", getAccountId(request, response));
    mapParameter.put("corpId", getCorpId(request, response));
    mapParameter.put("year", year);
    final List<Object> list = budgetSettingHeaderService.getBudgetSettingHeaderVOsByMapParameter(mapParameter);
    if (list != null && list.size() > 0) {
      final BudgetSettingHeaderVO object = (BudgetSettingHeaderVO) list.get(0);
      request.setAttribute("periodObject", list.get(0));

      Date nowDate = new Date();
      Date beginDate = null;
      Date endDate = null;

      String role = (String) request.getAttribute("pageRole");

      if (ROLE_EE.equals(role)) {
        beginDate = KANUtil.createDate(object.getStartDate());
        endDate = KANUtil.createDate(object.getEndDate() + " 23:59:59");
      } else if (ROLE_BL.equals(role)) {
        beginDate = KANUtil.createDate(object.getStartDate_bl());
        endDate = KANUtil.createDate(object.getEndDate_bl() + " 23:59:59");
      } else {
        beginDate = KANUtil.createDate(object.getStartDate_pm());
        endDate = KANUtil.createDate(object.getEndDate_pm() + " 23:59:59");
      }

      // 主管有权限作最终修改评语
      if ((ROLE_PM.equals(role) || ROLE_PM_AND_BU.equals(role)) && KANUtil.filterEmpty(object.getStartDate_final()) != null && KANUtil.filterEmpty(object.getEndDate_final()) != null
          && nowDate.getTime() >= KANUtil.createDate(object.getStartDate_final()).getTime() && nowDate.getTime() <= KANUtil.createDate(object.getEndDate_final()).getTime()) {
        request.setAttribute("finalEditBtnShow", true);
      }

      if (!(nowDate.getTime() >= beginDate.getTime() && nowDate.getTime() <= endDate.getTime())) {
        warning(request, null, KANUtil.getProperty(request.getLocale(), "self.assessment.not.in.write.effective.date", object.getStartDate(), object.getEndDate()));
        request.setAttribute("editBtnHide", true);
        return false;
      }
    }
    return true;
  }

  // 过滤不符合条件的自评
  private boolean filterNotMeetConditon(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    // 过滤administrator等特殊账号
    if (KANUtil.filterEmpty(getEmployeeId(request, response)) == null) {
      warning(request, null, KANUtil.getProperty(request.getLocale(), "self.assessment.not.base.info"));
      return false;
    }

    PositionVO mainPositionVO = KANConstants.getKANAccountConstants(getAccountId(request, response)).getMainPositionVOByStaffId(getStaffId(request, response));
    // 过滤企图用非主职位填写自评的
    if (mainPositionVO != null && !mainPositionVO.getPositionId().equals(getPositionId(request, response))) {
      warning(request, null, KANUtil.getProperty(request.getLocale(), "self.assessment.switch.main.position"));
      return false;
    }

    // 不在填写日期范围内
    return filterOutsideValidDate(mapping, form, request, response);
  }

  @Override
  public ActionForward to_objectNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    request.setAttribute("pageRole", ROLE_EE);
    // 过滤不符合条件的自评
    if (!filterNotMeetConditon(mapping, form, request, response)) {
      return list_object(mapping, form, request, response);
    }

    // 查询员工自评是否已经添加
    final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
    final Map<String, Object> paramters = new HashMap<String, Object>();
    paramters.put("year", Integer.valueOf(KANUtil.formatDate(new Date(), "yyyy")));
    paramters.put("employeeId", getEmployeeId(request, response));
    final List<Object> list = selfAssessmentService.getSelfAssessmentVOsByMapParameter(paramters);

    if (list != null && list.size() > 0) {
      ((SelfAssessmentVO) form).setAssessmentId(((SelfAssessmentVO) list.get(0)).getAssessmentId());
      return to_objectModify(mapping, form, request, response);
    } else {
      this.saveToken(request);
      ((SelfAssessmentVO) form).setEmployeeId(getEmployeeId(request, response));
      ((SelfAssessmentVO) form).setStatus("1");
      ((SelfAssessmentVO) form).setSubAction(CREATE_OBJECT);
      ((SelfAssessmentVO) form).setYear(Integer.valueOf(paramters.get("year").toString()));
      ((SelfAssessmentVO) form).setYear(Integer.valueOf(paramters.get("year").toString()));
      return mapping.findForward("manageSelfAssessment");
    }
  }

  @Override
  public ActionForward add_object(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      // 避免重复提交
      if (this.isTokenValid(request, true)) {
        // 初始化 Service接口
        final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
        // 获得当前FORM
        final SelfAssessmentVO selfAssessmentVO = (SelfAssessmentVO) form;
        selfAssessmentVO.setYear(Integer.valueOf(KANUtil.formatDate(new Date(), "yyyy")));
        selfAssessmentVO.setSelfPositionId(getPositionId(request, response));
        selfAssessmentVO.setIsPromotion_pm("3");
        selfAssessmentVO.setIsPromotion_bm("3");
        selfAssessmentVO.setIsPromotion_bu("3");
        selfAssessmentVO.setCreateBy(getUserId(request, response));
        selfAssessmentVO.setModifyBy(getUserId(request, response));
        selfAssessmentVO.setAccountId(getAccountId(request, response));
        selfAssessmentService.insertSelfAssessment(selfAssessmentVO);

        // 提交的时候，因为员工没有填写目标设定，保存当前并去填写目标设定的页面
        if (KANUtil.filterEmpty(request.getParameter("saveCurrAndToGoalModify")) != null) {
          response.sendRedirect("selfAssessmentAction.do?proc=to_goalModify&id=" + selfAssessmentVO.getEncodedId());
          return null;
        }

        // 返回添加成功标记
        success(request, MESSAGE_TYPE_ADD);
        // 记录操作日志
        insertlog(request, selfAssessmentVO, Operate.ADD, selfAssessmentVO.getAssessmentId(), null);
      } else {
        // 清空Form
        ((SelfAssessmentVO) form).reset();
        // 返回添加重复提交的警告
        error(request, null, KANUtil.getProperty(this.getLocale(request), "message.prompt.duplicate.submission"));
        return list_object(mapping, form, request, response);
      }
    } catch (final Exception e) {
      throw new KANException(e);
    }

    return to_objectModify(mapping, form, request, response);
  }

  @Override
  public ActionForward to_objectModify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      // 添加页面Token
      this.saveToken(request);
      // 初始化Service接口
      final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
      // 主键获取需解码，request中获取不到从form中获取
      String assessmentId = request.getParameter("id");
      if (KANUtil.filterEmpty(assessmentId) == null) {
        assessmentId = ((SelfAssessmentVO) form).getAssessmentId();
      } else {
        assessmentId = Cryptogram.decodeString(URLDecoder.decode(assessmentId, "UTF-8"));
      }
      final SelfAssessmentVO selfAssessmentVO = selfAssessmentService.getSelfAssessmentVOByAssessmentId(assessmentId);
      // 刷新对象，初始化对象列表及国际化
      selfAssessmentVO.reset(null, request);
      // 设置AccessAction
      selfAssessmentVO.setSubAction(VIEW_OBJECT);
      // 处理员工角色页面
      controlPage(mapping, selfAssessmentVO, request, response);
      filterOutsideValidDate(mapping, selfAssessmentVO, request, response);
      // 将form存放到request对象
      request.setAttribute("selfAssessmentForm", selfAssessmentVO);

      KANAccountConstants constants = KANConstants.getKANAccountConstants(getAccountId(request, null));
      if (KANUtil.filterEmpty(selfAssessmentVO.getPm1PositionId()) != null) {
        String staffName = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm1PositionId());
        request.setAttribute("PM_NAME_1", staffName);
      }
      if (KANUtil.filterEmpty(selfAssessmentVO.getPm2PositionId()) != null) {
        String staffName = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm2PositionId());
        request.setAttribute("PM_NAME_2", staffName);
      }
      if (KANUtil.filterEmpty(selfAssessmentVO.getPm3PositionId()) != null) {
        String staffName = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm3PositionId());
        request.setAttribute("PM_NAME_3", staffName);
      }
      if (KANUtil.filterEmpty(selfAssessmentVO.getPm4PositionId()) != null) {
        String staffName = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm4PositionId());
        request.setAttribute("PM_NAME_4", staffName);
      }

      // 跳转到编辑界面
      if ("exportPDF".equalsIgnoreCase(request.getParameter("export"))) {
        return mapping.findForward("exportSelfAssessment");
      } else {
        return mapping.findForward("manageSelfAssessment");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public ActionForward exportPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    boolean isZH = "zh".equalsIgnoreCase(request.getLocale().getLanguage());
    try {
      File file = new File(KANConstants.WKHTMLTOPDF_TEMP_FILE_PATH + "/kanpower.css");
      if (!file.exists()) {
        String cssPath = request.getSession().getServletContext().getRealPath("") + "/css/kanpower.css";
        File cssFile = new File(cssPath);
        File targetFile = new File(KANConstants.WKHTMLTOPDF_TEMP_FILE_PATH + "/kanpower.css");
        FileInputStream fis = new FileInputStream(cssFile);
        FileOutputStream fos = new FileOutputStream(targetFile);
        byte[] data = new byte[1024];
        int len = 0;
        while ((len = fis.read(data, 0, 1024)) != -1) {
          fos.write(data, 0, len);
        }
        fis.close();
        fos.close();
      }
      //
      final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
      String assessmentId = Cryptogram.decodeString(URLDecoder.decode(request.getParameter("assessmentId"), "UTF-8"));
      final SelfAssessmentVO selfAssessmentVO = selfAssessmentService.getSelfAssessmentVOByAssessmentId(assessmentId);
      selfAssessmentVO.reset(null, request);
      filterOutsideValidDate(mapping, selfAssessmentVO, request, response);
      BudgetSettingHeaderVO budgetSettingHeaderVO = (BudgetSettingHeaderVO) request.getAttribute("periodObject");
      String ftlPath = isZH ? "/ftl/selfAssessment_zh.ftl" : "/ftl/selfAssessment_en.ftl";
      if (Integer.parseInt(selfAssessmentVO.getStatus()) >= 5) {
        ftlPath = isZH ? "/ftl/selfAssessment_zh5.ftl" : "/ftl/selfAssessment_en5.ftl";
      }
      File content = new File(request.getSession().getServletContext().getRealPath("") + ftlPath);
      BufferedReader reader = new BufferedReader(new FileReader(content));
      StringBuffer sb = new StringBuffer();
      String tempString = null;
      while ((tempString = reader.readLine()) != null) {
        sb.append(tempString);
      }
      reader.close();

      String contentHtml = sb.toString();
      contentHtml = contentHtml.replaceAll("\\$\\{year\\}", String.valueOf(selfAssessmentVO.getYear()));
      contentHtml = contentHtml.replaceAll("\\$\\{date_start\\}", String.valueOf(budgetSettingHeaderVO.getStartDate()));
      contentHtml = contentHtml.replaceAll("\\$\\{date_end\\}", String.valueOf(budgetSettingHeaderVO.getEndDate()));
      contentHtml = contentHtml.replaceAll("\\$\\{employeeId\\}", selfAssessmentVO.getEmployeeId());
      contentHtml = contentHtml.replaceAll("\\$\\{bu\\}", selfAssessmentVO.getDecodeParentBranchId());
      contentHtml = contentHtml.replaceAll("\\$\\{employeeNameZH\\}", selfAssessmentVO.getEmployeeNameZH());
      contentHtml = contentHtml.replaceAll("\\$\\{employeeNameEN\\}", selfAssessmentVO.getEmployeeNameEN());
      contentHtml = contentHtml.replaceAll("\\$\\{directLeaderNameZH\\}", selfAssessmentVO.getDirectLeaderNameZH());
      contentHtml = contentHtml.replaceAll("\\$\\{directLeaderNameEN\\}", selfAssessmentVO.getDirectLeaderNameEN());
      contentHtml = contentHtml.replaceAll("\\$\\{status\\}", selfAssessmentVO.getDecodeStatus());
      contentHtml = contentHtml.replaceAll("\\$\\{accomplishments\\}", replaceEnter(selfAssessmentVO.getAccomplishments()));
      contentHtml = contentHtml.replaceAll("\\$\\{areasOfStrengths\\}", replaceEnter(selfAssessmentVO.getAreasOfStrengths()));
      contentHtml = contentHtml.replaceAll("\\$\\{areasOfImprovement\\}", replaceEnter(selfAssessmentVO.getAreasOfImprovement()));
      contentHtml = contentHtml.replaceAll("\\$\\{successors\\}", selfAssessmentVO.getSuccessors());
      contentHtml = contentHtml.replaceAll("\\$\\{otherComments\\}", replaceEnter(selfAssessmentVO.getOtherComments()));
      contentHtml = contentHtml.replaceAll("\\$\\{accomplishments_pm\\}", replaceEnter(selfAssessmentVO.getAccomplishments_pm()));
      contentHtml = contentHtml.replaceAll("\\$\\{areasOfStrengths_pm\\}", replaceEnter(selfAssessmentVO.getAreasOfStrengths_pm()));
      contentHtml = contentHtml.replaceAll("\\$\\{areasOfImprovement_pm\\}", replaceEnter(selfAssessmentVO.getAreasOfImprovement_pm()));
      for (int i = 1; i <= 3; i++) {
        if (KANUtil.filterEmpty(selfAssessmentVO.getIsPromotion_pm()) != null && String.valueOf(i).equals(selfAssessmentVO.getIsPromotion_pm())) {
          contentHtml = contentHtml.replaceAll("\\$\\{isPromotion_pm_check" + i + "\\}", "checked=\"checked\"");
        } else {
          contentHtml = contentHtml.replaceAll("\\$\\{isPromotion_pm_check" + i + "\\}", "");
        }
      }
      contentHtml = contentHtml.replaceAll("\\$\\{promotionReason_pm\\}", selfAssessmentVO.getPromotionReason_pm());
      contentHtml = contentHtml.replaceAll("\\$\\{successors_pm\\}", selfAssessmentVO.getSuccessors_pm());
      for (double i = 1; i <= 5; i += 0.5) {
        String tmpI = String.valueOf(i).replaceAll("\\.", "_");
        if (KANUtil.filterEmpty(selfAssessmentVO.getRating_final()) != null && i == Double.parseDouble(selfAssessmentVO.getRating_final())) {
          contentHtml = contentHtml.replaceAll("\\$\\{rating_final_" + tmpI + "\\}", "checked=\"checked\"");
        } else {
          contentHtml = contentHtml.replaceAll("\\$\\{rating_final_" + tmpI + "\\}", "");
        }
      }
      String name = isZH ? ("年终考核 - " + selfAssessmentVO.getEmployeeNameZH() + "自评.pdf") : (" Year End Review - " + selfAssessmentVO.getEmployeeNameEN() + " assessment.pdf");
      new DownloadFileAction().download(response, PDFTool.generationPdfDzOrder(contentHtml), selfAssessmentVO.getYear() + name);
    } catch (final Exception e) {
      throw new KANException(e);
    }
    return null;
  }

  public static String replaceEnter(String str) {
    if (StringUtils.isNotBlank(str)) {
      return str.replaceAll("\r\n", "<br/>");
    }
    return "";
  }

  /***
   * 去目标设定页面
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
  public ActionForward to_goalModify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      // 添加页面Token
      this.saveToken(request);
      // 初始化Service接口
      final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
      // 主键获取需解码，request中获取不到从form中获取
      String assessmentId = request.getParameter("id");
      if (KANUtil.filterEmpty(assessmentId) == null) {
        assessmentId = ((SelfAssessmentVO) form).getAssessmentId();
      } else {
        assessmentId = Cryptogram.decodeString(URLDecoder.decode(assessmentId, "UTF-8"));
      }
      final SelfAssessmentVO selfAssessmentVO = selfAssessmentService.getSelfAssessmentVOByAssessmentId(assessmentId);
      // 刷新对象，初始化对象列表及国际化
      selfAssessmentVO.reset(null, request);
      // 设置AccessAction
      selfAssessmentVO.setSubAction(VIEW_OBJECT);
      request.setAttribute("selfAssessmentForm", selfAssessmentVO);
      request.setAttribute("nextYear", Integer.valueOf(selfAssessmentVO.getYear()) + 1);
      // 处理员工角色页面
      controlPage(mapping, selfAssessmentVO, request, response);
    } catch (final Exception e) {
      throw new KANException(e);
    }

    // 跳转到编辑界面
    return mapping.findForward("manageGoal");
  }

  @Override
  public ActionForward modify_object(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      // 判断防止重复提交
      if (this.isTokenValid(request, true)) {
        // 获取员工角色
        final String role = request.getParameter("emp_role");
        // 初始化 Service接口
        final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
        // 主键获取需解码
        final String assessmentId = Cryptogram.decodeString(URLDecoder.decode(request.getParameter("id"), "UTF-8"));
        // 获取SelfAssessmentVO对象
        final SelfAssessmentVO selfAssessmentVO = selfAssessmentService.getSelfAssessmentVOByAssessmentId(assessmentId);
        // 根据角色装载界面传值
        if (ROLE_EE.equals(role)) {
          selfAssessmentVO.update((SelfAssessmentVO) form);
        } else if (ROLE_BL.equals(role)) {
          selfAssessmentVO.update_bl((SelfAssessmentVO) form);
        } else if (ROLE_PM.equals(role)) {
          selfAssessmentVO.update_pm((SelfAssessmentVO) form);
        } else if (ROLE_PM_AND_BU.equals(role)) {
          selfAssessmentVO.update_buh((SelfAssessmentVO) form);
        } else if (ROLE_BU.equals(role)) {
          selfAssessmentVO.update_buh((SelfAssessmentVO) form);
        } else if (ROLE_PM_NON_DIRECT_1.equals(role)) {
          selfAssessmentVO.update_pm_non_direct_1(request);
        } else if (ROLE_PM_NON_DIRECT_2.equals(role)) {
          selfAssessmentVO.update_pm_non_direct_2(request);
        } else if (ROLE_PM_NON_DIRECT_3.equals(role)) {
          selfAssessmentVO.update_pm_non_direct_3(request);
        } else if (ROLE_PM_NON_DIRECT_4.equals(role)) {
          selfAssessmentVO.update_pm_non_direct_4(request);
        }

        // 获取登录用户
        selfAssessmentVO.setModifyBy(getUserId(request, response));
        selfAssessmentVO.setModifyDate(new Date());
        // 修改对象
        selfAssessmentService.updateSelfAssessment(selfAssessmentVO);

        // 如果BU已经提交，立马同步
        if ("2".equalsIgnoreCase(selfAssessmentVO.getStatus_bu())) {
          if (KANUtil.filterEmpty(selfAssessmentVO.getRating_bu()) == null)
            selfAssessmentVO.setRating_bu(selfAssessmentVO.getRating_pm());
          selfAssessmentService.syncSelfAssessmentVO(selfAssessmentVO);
        }

        // 提交的时候，因为员工没有填写目标设定，保存当前并去填写目标设定的页面
        if (KANUtil.filterEmpty(request.getParameter("saveCurrAndToGoalModify")) != null) {
          response.sendRedirect("selfAssessmentAction.do?proc=to_goalModify&id=" + selfAssessmentVO.getEncodedId());
          return null;
        }

        if ("6".equalsIgnoreCase(selfAssessmentVO.getStatus())) {
          success(request, null, KANUtil.getProperty(request.getLocale(), "self.assessment.btnAcknowledge.submit.success"));
        } else {
          // 返回编辑成功标记
          success(request, getSubAction(form).equalsIgnoreCase(MODIFY_OBJECT) ? MESSAGE_TYPE_UPDATE : MESSAGE_TYPE_SUBMIT);
        }

        // 记录日志
        insertlog(request, selfAssessmentVO, getSubAction(form).equalsIgnoreCase(MODIFY_OBJECT) ? Operate.MODIFY : Operate.SUBMIT, selfAssessmentVO.getAssessmentId(), null);
      }
    } catch (final Exception e) {
      throw new KANException(e);
    }
    return to_objectModify(mapping, form, request, response);
  }

  /***
   * 修改目标设定
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
  public ActionForward modify_goal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      // 判断防止重复提交
      if (this.isTokenValid(request, true)) {
        // 获取员工角色
        final String role = request.getParameter("emp_role");
        // 初始化 Service接口
        final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
        // 主键获取需解码
        final String assessmentId = Cryptogram.decodeString(URLDecoder.decode(request.getParameter("id"), "UTF-8"));
        // 获取SelfAssessmentVO对象
        final SelfAssessmentVO selfAssessmentVO = selfAssessmentService.getSelfAssessmentVOByAssessmentId(assessmentId);
        // 修改状态
        selfAssessmentVO.setStatus(((SelfAssessmentVO) form).getStatus());
        // 根据角色装载界面传值
        if (ROLE_EE.equals(role)) {
          selfAssessmentVO.setNextYearGoals(request.getParameter("nextYearGoals"));
          selfAssessmentVO.setNextYearPlans(request.getParameter("nextYearPlans"));
        } else if (ROLE_BL.equals(role)) {
          selfAssessmentVO.setNextYearGoalsAndPlans_bm(request.getParameter("nextYearGoalsAndPlans_bm"));
        } else if (ROLE_PM.equals(role) || ROLE_PM_AND_BU.equals(role)) {
          selfAssessmentVO.setNextYearGoalsAndPlans_pm(request.getParameter("nextYearGoalsAndPlans_pm"));
        }

        // 获取登录用户
        selfAssessmentVO.setModifyBy(getUserId(request, response));
        selfAssessmentVO.setModifyDate(new Date());
        // 修改对象
        selfAssessmentService.updateSelfAssessment(selfAssessmentVO);
        // 返回编辑成功标记
        success(request, getSubAction(form).equalsIgnoreCase(MODIFY_OBJECT) ? MESSAGE_TYPE_UPDATE : MESSAGE_TYPE_SUBMIT);
        // 记录日志
        insertlog(request, selfAssessmentVO, getSubAction(form).equalsIgnoreCase(MODIFY_OBJECT) ? Operate.MODIFY : Operate.SUBMIT, selfAssessmentVO.getAssessmentId(), null);
      }
    } catch (final Exception e) {
      throw new KANException(e);
    }
    return to_goalModify(mapping, form, request, response);
  }

  @Override
  protected void delete_object(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {

  }

  /***
   * 同步绩效评分
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
  public ActionForward sync_objects(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      final SelfAssessmentVO selfAssessmentVO = new SelfAssessmentVO();
      selfAssessmentVO.setSelfPositionId(getPositionId(request, null));
      selfAssessmentVO.setAccountId(getAccountId(request, null));
      selfAssessmentVO.setCorpId(getCorpId(request, null));
      selfAssessmentVO.setStatus("3");
      selfAssessmentVO.setModifyBy(getUserId(request, null));
      final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
      int successRows = selfAssessmentService.syncSelfAssessmentVOs(selfAssessmentVO);
      success(request, null, "总共 " + successRows + " 条数据同步成功！");
    } catch (final Exception e) {
      throw new KANException(e);
    }

    return list_object(mapping, form, request, response);
  }

  @Override
  protected void delete_objectList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {

  }

  /***
   * ajax加载员工信息
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @throws KANException
   */
  public void employeeId_keyup_ajax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      final String employeeId = request.getParameter("employeeId");

      Map<String, String> mapReturn = new HashMap<String, String>();
      mapReturn.put("parentBranchId", "0");

      if (KANUtil.filterEmpty(employeeId) != null) {
        KANAccountConstants constants = KANConstants.getKANAccountConstants(getAccountId(request, response));
        EmployeeService employeeService = (EmployeeService) getService("employeeService");
        EmployeeContractService employeeContractService = (EmployeeContractService) getService("employeeContractService");
        StaffService staffService = (StaffService) getService("staffService");
        EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId(employeeId);
        if (employeeVO != null) {
          mapReturn.put("employeeNameZH", employeeVO.getNameZH());
          mapReturn.put("employeeNameEN", employeeVO.getNameEN());
          mapReturn.put("hrOwnerPositionId", employeeVO.getOwner());
        }

        String bizLeaderName = "";
        List<Object> employeeContractVOs = employeeContractService.getEmployeeContractVOsByEmployeeId(employeeId);
        if (employeeContractVOs != null && employeeContractVOs.size() > 0) {
          for (Object o : employeeContractVOs) {
            if (((EmployeeContractVO) o).getEmployStatus().equals("1")) {
              EmployeeContractVO contractVO = employeeContractService.getEmployeeContractVOByContractId(((EmployeeContractVO) o).getContractId());
              if (contractVO != null && KANUtil.filterEmpty(contractVO.getRemark1()) != null) {
                final JSONObject jsonObject = JSONObject.fromObject(contractVO.getRemark1());
                if (jsonObject.get("yewuhuibaoxianjingli") != null) {
                  bizLeaderName = jsonObject.get("yewuhuibaoxianjingli").toString();
                }
              }
            }
          }
        }

        if (KANUtil.filterEmpty(bizLeaderName) != null) {
          EmployeeVO searchEmployee = new EmployeeVO();
          searchEmployee.setAccountId(getAccountId(request, response));
          searchEmployee.setCorpId(getCorpId(request, response));
          searchEmployee.setRemark1("%\"jiancheng\":\"" + bizLeaderName.trim() + "%");

          List<Object> employeeVOs = employeeService.getEmployeeVOsByCondition(searchEmployee);
          if (employeeVOs != null && employeeVOs.size() == 1) {
            StaffVO staffVO = staffService.getStaffVOByEmployeeId(((EmployeeVO) employeeVOs.get(0)).getEmployeeId());
            if (staffVO != null) {
              PositionVO bizLeaderPositionVO = constants.getMainPositionVOByStaffId(staffVO.getStaffId());
              if (bizLeaderPositionVO != null) {
                mapReturn.put("bizLeaderPositionId", bizLeaderPositionVO.getPositionId());
              }
            }
          }
        }

        PositionVO mainPositionVO = constants.getMainPositionVOByStaffId(getStaffId(request, response));
        if (mainPositionVO != null) {
          BranchVO branchVO = constants.getBranchVOByBranchId(mainPositionVO.getBranchId());

          if (branchVO != null) {
            mapReturn.put("parentBranchId", branchVO.getParentBranchId());

            if ("0".equals(branchVO.getParentBranchId())) {
              mapReturn.put("parentBranchId", branchVO.getBranchId());
            }
          }

          String directLeaderNameZH = "";
          String directLeaderNameEN = "";
          final PositionDTO parentPositionDTO = constants.getPositionDTOByPositionId(mainPositionVO.getParentPositionId());

          if (parentPositionDTO != null && parentPositionDTO.getPositionStaffRelationVOs() != null && parentPositionDTO.getPositionStaffRelationVOs().size() > 0) {
            for (PositionStaffRelationVO relation : parentPositionDTO.getPositionStaffRelationVOs()) {
              if ("1".equals(relation.getStaffType())) {
                directLeaderNameZH = constants.getStaffNameByStaffIdAndLanguage(relation.getStaffId(), "zh");
                directLeaderNameEN = constants.getStaffNameByStaffIdAndLanguage(relation.getStaffId(), "en");
              }
            }

            List<String> pms = constants.getParentPositionIdsByPositionId(parentPositionDTO.getPositionVO().getParentPositionId());
            for (int i = 0; i < pms.size() && i < 3; i++) {
              mapReturn.put("pm" + (i + 1) + "PositionId", pms.get(i));
            }
          }

          mapReturn.put("parentPositionId", mainPositionVO.getParentPositionId());
          mapReturn.put("directLeaderNameZH", directLeaderNameZH);
          mapReturn.put("directLeaderNameEN", directLeaderNameEN);

          PositionVO buLeaderPositionVO = constants.getBUHeaderPositionVOByPositionId(mainPositionVO.getPositionId());
          if (buLeaderPositionVO != null) {
            if (buLeaderPositionVO.getPositionId().equals(getPositionId(request, response))) {
              mapReturn.put("buLeaderPositionId", mainPositionVO.getParentPositionId());
            } else {
              mapReturn.put("buLeaderPositionId", buLeaderPositionVO.getPositionId());
            }
          }
        }
      }

      // Config the response
      response.setContentType("text/html");
      response.setCharacterEncoding("UTF-8");
      // 初始化PrintWrite对象
      final PrintWriter out = response.getWriter();
      // Send to front
      out.println(JSONObject.fromObject(mapReturn).toString());
      out.flush();
      out.close();
    } catch (final Exception e) {
      throw new KANException(e);
    }
  }

  /***
   * 员工自评 - 导出初始模板
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
  public void export_template(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    String templateName = "Template(Employee self assessment).xlsx";
    try {
      final String path = KANUtil.basePath + "/" + templateName;
      // 创建File
      final File file = new File(path);
      // 存在模板路径
      if (file.exists()) {
        // 初始化工作薄
        final XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        // final String[] ratingArray = new String[] { "5", "4.5", "4", "3.5", "3", "2.5", "2", "1"
        // };
        // final List< MappingVO > isPromption_pms = KANUtil.getMappings( request.getLocale(),
        // "emp.self.assessment.isPromotion_pms" );
        // POIUtil.SetDataValidation( workbook, KANUtil.mappingListToArray( isPromption_pms ), 2,
        // 12, 1000, 12, "是否晋升" );
        // POIUtil.SetDataValidation( workbook, KANUtil.mappingListToArray( isPromption_pms ), 2,
        // 19, 1000, 19, "是否晋升" );
        //
        // POIUtil.addValidationData( workbook, ratingArray, 2, 22, 1000, 22, "" );
        // POIUtil.addValidationData( workbook, ratingArray, 2, 24, 1000, 24, "" );

        // 初始化OutputStream
        final OutputStream os = response.getOutputStream();
        // 设置返回文件下载
        response.setContentType("application/x-msdownload");
        // 解决文件中文名下载问题
        response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(URLDecoder.decode(templateName, "UTF-8").getBytes(), "iso-8859-1") + "\"");
        // Excel文件写入OutputStream
        workbook.write(os);
        // 输出OutputStream
        os.flush();
        // 关闭流
        os.close();
      }
    } catch (Exception e) {
      throw new KANException(e);
    }
  }

  public ActionForward getSelfAssessment_ajax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws KANException {
    try {
      final String assessmentId = request.getParameter("assessmentId");
      final SelfAssessmentService selfAssessmentService = (SelfAssessmentService) getService("selfAssessmentService");
      SelfAssessmentVO selfAssessmentVO = selfAssessmentService.getSelfAssessmentVOByAssessmentId(assessmentId);
      request.setAttribute("selfAssessmentVO", selfAssessmentVO);
      KANAccountConstants constants = KANConstants.getKANAccountConstants(getAccountId(request, null));

      request.setAttribute("PM_NAME_1", constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm1PositionId()));
      request.setAttribute("PM_NAME_2", constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm2PositionId()));
      request.setAttribute("PM_NAME_3", constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm3PositionId()));
      request.setAttribute("PM_NAME_4", constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm4PositionId()));
      // biz leader
      request.setAttribute("BL_NAME", constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getBizLeaderPositionId()));
      // people manager
      request.setAttribute("PM_NAME", constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getParentPositionId()));
      // bu header
      request.setAttribute("BU_NAME", constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getBuLeaderPositionId()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mapping.findForward("showSelfAssessmentProgessTable");
  }
}
