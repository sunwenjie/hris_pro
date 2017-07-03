package com.kan.base.web.renders.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.domain.biz.performance.SelfAssessmentVO;

/**
 * 手写导出excel类
 * 
 * @author Iori
 *
 */
public class UndefineExcelExport {

  /**
   * 导出评估进度
   * 
   * @param request
   * @return
   * @throws KANException
   */
  public static XSSFWorkbook exportSelfAssessmentProgress(final HttpServletRequest request) throws KANException {
    // 初始化PagedListHolder
    final PagedListHolder pagedListHolder = (PagedListHolder) request.getAttribute("selfAssessmentHolder");

    // 初始化工作薄
    final XSSFWorkbook workbook = new XSSFWorkbook();

    // 创建字体
    final Font font = workbook.createFont();
    font.setFontName("Calibri");
    font.setFontHeightInPoints((short) 11);

    // 创建单元格样式
    final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
    cellStyleLeft.setFont(font);
    cellStyleLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);

    // 创建单元格样式
    final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
    cellStyleCenter.setFont(font);
    cellStyleCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);

    // 创建单元格样式
    final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
    cellStyleRight.setFont(font);
    cellStyleRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

    // 绿色背景样式
    final XSSFCellStyle greenCellStyle = (XSSFCellStyle) cellStyleLeft.clone();
    greenCellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
    greenCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    greenCellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);

    // 创建表格
    final XSSFSheet sheet = workbook.createSheet("sheet1");
    // 设置表格默认列宽度为15个字节
    sheet.setDefaultColumnWidth(15);

    // 生成Excel Header Row
    final XSSFRow rowHeader = sheet.createRow(0);

    // 表头
    final String[] rowHeaderStrs = new String[8];
    rowHeaderStrs[0] = KANUtil.getProperty(request.getLocale(), "performance.show.progress.employee.name");
    rowHeaderStrs[1] = KANUtil.getProperty(request.getLocale(), "performance.show.progress.bl");
    rowHeaderStrs[2] = KANUtil.getProperty(request.getLocale(), "performance.show.progress.biz.peer") + " 1";
    rowHeaderStrs[3] = KANUtil.getProperty(request.getLocale(), "performance.show.progress.biz.peer") + " 2";
    rowHeaderStrs[4] = KANUtil.getProperty(request.getLocale(), "performance.show.progress.biz.peer") + " 3";
    rowHeaderStrs[5] = KANUtil.getProperty(request.getLocale(), "performance.show.progress.biz.peer") + " 4";
    rowHeaderStrs[6] = KANUtil.getProperty(request.getLocale(), "performance.show.progress.pm");
    rowHeaderStrs[7] = KANUtil.getProperty(request.getLocale(), "performance.show.progress.bu");

    // 遍历生成表头
    int headerColumnIndex = 0;
    for (int i = 0; i < rowHeaderStrs.length; i++) {
      final XSSFCell thisCell = rowHeader.createCell(headerColumnIndex);
      thisCell.setCellValue(rowHeaderStrs[i]);
      thisCell.setCellStyle(cellStyleLeft);
      headerColumnIndex++;
    }

    if (pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0) {
      // 行下表
      int bodyRowIndex = 1;
      // 列下标
      int bodyColumnIndex = -1;
      KANAccountConstants constants = KANConstants.getKANAccountConstants(BaseAction.getAccountId(request, null));
      // 遍历生成表体
      for (Object object : pagedListHolder.getSource()) {
        final SelfAssessmentVO selfAssessmentVO = (SelfAssessmentVO) object;
        // 创建行
        final XSSFRow rowBody = sheet.createRow(bodyRowIndex);

        bodyColumnIndex++;
        final XSSFCell employeeIdCell = rowBody.createCell(bodyColumnIndex);
        employeeIdCell.setCellStyle(cellStyleLeft);
        boolean isZH = "zh".equalsIgnoreCase(request.getLocale().getLanguage());
        employeeIdCell.setCellValue(isZH ? selfAssessmentVO.getEmployeeNameZH() : selfAssessmentVO.getEmployeeNameEN());

        String PM_NAME_1 = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm1PositionId());
        String PM_NAME_2 = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm2PositionId());
        String PM_NAME_3 = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm3PositionId());
        String PM_NAME_4 = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getPm4PositionId());
        String BL_NAME = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getBizLeaderPositionId());
        String PM_NAME = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getParentPositionId());
        String BU_NAME = constants.getStaffNamesByPositionId(request.getLocale().getLanguage(), selfAssessmentVO.getBuLeaderPositionId());

        bodyColumnIndex++;
        final XSSFCell directLeaderNameZHCell = rowBody.createCell(bodyColumnIndex);
        directLeaderNameZHCell.setCellStyle("2".equals(selfAssessmentVO.getStatus_bm()) ? greenCellStyle : cellStyleLeft);
        directLeaderNameZHCell.setCellValue(BL_NAME);

        bodyColumnIndex++;
        final XSSFCell yearCell = rowBody.createCell(bodyColumnIndex);
        yearCell.setCellStyle("2".equals(selfAssessmentVO.getStatus_pm1())?greenCellStyle:cellStyleLeft);
        yearCell.setCellValue(PM_NAME_1);

        bodyColumnIndex++;
        final XSSFCell employeeNameZHCell = rowBody.createCell(bodyColumnIndex);
        employeeNameZHCell.setCellStyle("2".equals(selfAssessmentVO.getStatus_pm2())?greenCellStyle:cellStyleLeft);
        employeeNameZHCell.setCellValue(PM_NAME_2);

        bodyColumnIndex++;
        final XSSFCell employeeNameENCell = rowBody.createCell(bodyColumnIndex);
        employeeNameENCell.setCellStyle("2".equals(selfAssessmentVO.getStatus_pm3())?greenCellStyle:cellStyleLeft);
        employeeNameENCell.setCellValue(PM_NAME_3);

        bodyColumnIndex++;
        final XSSFCell decodeParentBranchIdCell = rowBody.createCell(bodyColumnIndex);
        decodeParentBranchIdCell.setCellStyle("2".equals(selfAssessmentVO.getStatus_pm4())?greenCellStyle:cellStyleLeft);
        decodeParentBranchIdCell.setCellValue(PM_NAME_4);

        bodyColumnIndex++;
        final XSSFCell directLeaderNameENCell = rowBody.createCell(bodyColumnIndex);
        directLeaderNameENCell.setCellStyle("2".equals(selfAssessmentVO.getStatus_pm())?greenCellStyle:cellStyleLeft);
        directLeaderNameENCell.setCellValue(PM_NAME);

        bodyColumnIndex++;
        final XSSFCell rating_finalCell = rowBody.createCell(bodyColumnIndex);
        rating_finalCell.setCellStyle("2".equals(selfAssessmentVO.getStatus_bu())?greenCellStyle:cellStyleLeft);
        rating_finalCell.setCellValue(BU_NAME);


        bodyRowIndex++;
        bodyColumnIndex = -1;
      }
    }

    return workbook;
  }

  public static XSSFWorkbook generateSelfAssessment(final HttpServletRequest request) throws KANException {
    // 初始化PagedListHolder
    final PagedListHolder pagedListHolder = (PagedListHolder) request.getAttribute("selfAssessmentHolder");

    // 初始化工作薄
    final XSSFWorkbook workbook = new XSSFWorkbook();

    // 创建字体
    final Font font = workbook.createFont();
    font.setFontName("Calibri");
    font.setFontHeightInPoints((short) 11);

    // 创建单元格样式
    final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
    cellStyleLeft.setFont(font);
    cellStyleLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);

    // 创建单元格样式
    final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
    cellStyleCenter.setFont(font);
    cellStyleCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);

    // 创建单元格样式
    final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
    cellStyleRight.setFont(font);
    cellStyleRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

    // 红色背景样式
    final XSSFCellStyle redCellStyle = (XSSFCellStyle) cellStyleLeft.clone();
    redCellStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
    redCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

    // 创建表格
    final XSSFSheet sheet = workbook.createSheet("sheet1");
    // 设置表格默认列宽度为15个字节
    sheet.setDefaultColumnWidth(15);

    // 生成Excel Header Row
    final XSSFRow rowHeader = sheet.createRow(0);

    // 表头
    final String[] rowHeaderStrs = new String[9];
    rowHeaderStrs[0] = KANUtil.getProperty(request.getLocale(), "public.employee2.id");
    rowHeaderStrs[1] = KANUtil.getProperty(request.getLocale(), "YERR.year");
    rowHeaderStrs[2] = KANUtil.getProperty(request.getLocale(), "public.employee2.name.cn");
    rowHeaderStrs[3] = KANUtil.getProperty(request.getLocale(), "public.employee2.name.en");
    rowHeaderStrs[4] = "BU/Function";
    rowHeaderStrs[5] = KANUtil.getProperty(request.getLocale(), "emp.self.assessment.directLeaderNameZH");
    rowHeaderStrs[6] = KANUtil.getProperty(request.getLocale(), "emp.self.assessment.directLeaderNameEN");
    rowHeaderStrs[7] = "Final Rating";
    rowHeaderStrs[8] = KANUtil.getProperty(request.getLocale(), "public.status");

    // 遍历生成表头
    int headerColumnIndex = 0;
    for (int i = 0; i < rowHeaderStrs.length; i++) {
      final XSSFCell thisCell = rowHeader.createCell(headerColumnIndex);
      thisCell.setCellValue(rowHeaderStrs[i]);
      thisCell.setCellStyle(cellStyleLeft);
      headerColumnIndex++;
    }

    if (pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0) {
      // 行下表
      int bodyRowIndex = 1;
      // 列下标
      int bodyColumnIndex = -1;
      // 遍历生成表体
      for (Object object : pagedListHolder.getSource()) {
        final SelfAssessmentVO selfAssessmentVO = (SelfAssessmentVO) object;
        // 创建行
        final XSSFRow rowBody = sheet.createRow(bodyRowIndex);

        bodyColumnIndex++;
        final XSSFCell employeeIdCell = rowBody.createCell(bodyColumnIndex);
        employeeIdCell.setCellStyle(cellStyleLeft);
        employeeIdCell.setCellValue(selfAssessmentVO.getEmployeeId());

        bodyColumnIndex++;
        final XSSFCell yearCell = rowBody.createCell(bodyColumnIndex);
        yearCell.setCellStyle(cellStyleLeft);
        yearCell.setCellValue(selfAssessmentVO.getYear());

        bodyColumnIndex++;
        final XSSFCell employeeNameZHCell = rowBody.createCell(bodyColumnIndex);
        employeeNameZHCell.setCellStyle(cellStyleLeft);
        employeeNameZHCell.setCellValue(selfAssessmentVO.getEmployeeNameZH());

        bodyColumnIndex++;
        final XSSFCell employeeNameENCell = rowBody.createCell(bodyColumnIndex);
        employeeNameENCell.setCellStyle(cellStyleLeft);
        employeeNameENCell.setCellValue(selfAssessmentVO.getEmployeeNameEN());

        bodyColumnIndex++;
        final XSSFCell decodeParentBranchIdCell = rowBody.createCell(bodyColumnIndex);
        decodeParentBranchIdCell.setCellStyle(cellStyleLeft);
        decodeParentBranchIdCell.setCellValue(selfAssessmentVO.getDecodeParentBranchId());

        bodyColumnIndex++;
        final XSSFCell directLeaderNameZHCell = rowBody.createCell(bodyColumnIndex);
        directLeaderNameZHCell.setCellStyle(cellStyleLeft);
        directLeaderNameZHCell.setCellValue(selfAssessmentVO.getDirectLeaderNameZH());

        bodyColumnIndex++;
        final XSSFCell directLeaderNameENCell = rowBody.createCell(bodyColumnIndex);
        directLeaderNameENCell.setCellStyle(cellStyleLeft);
        directLeaderNameENCell.setCellValue(selfAssessmentVO.getDirectLeaderNameEN());

        bodyColumnIndex++;
        final XSSFCell rating_finalCell = rowBody.createCell(bodyColumnIndex);
        rating_finalCell.setCellStyle(cellStyleLeft);
        rating_finalCell.setCellValue(selfAssessmentVO.getRating_final());

        bodyColumnIndex++;
        final XSSFCell decodeStatusCell = rowBody.createCell(bodyColumnIndex);
        decodeStatusCell.setCellStyle(cellStyleLeft);
        decodeStatusCell.setCellValue(selfAssessmentVO.getDecodeStatus());

        bodyRowIndex++;
        bodyColumnIndex = -1;
      }
    }

    return workbook;
  }

  /***
   * 导出调薪列表Excel
   * 
   * @param request
   * @return Excel
   * @throws KANException
   */
  public static XSSFWorkbook generateEmployeeSalaryAdjustment(final HttpServletRequest request) throws KANException {
    // 添加Log Start跟踪
    LogFactory.getLog("generateEmployeeSalaryAdjustment").info("Excel Generate (" + BaseAction.getAccountId(request, null) + ", " + BaseAction.getUsername(request, null) + ") Start.");

    // 初始化PagedListHolder
    final PagedListHolder pagedListHolder = (PagedListHolder) request.getAttribute("salaryAdjustmentHolder");

    // 初始化工作薄
    final XSSFWorkbook workbook = new XSSFWorkbook();

    // 创建字体
    final Font font = workbook.createFont();
    font.setFontName("Calibri");
    font.setFontHeightInPoints((short) 11);

    // 创建单元格样式
    final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
    cellStyleLeft.setFont(font);
    cellStyleLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);

    // 创建单元格样式
    final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
    cellStyleCenter.setFont(font);
    cellStyleCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);

    // 创建单元格样式
    final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
    cellStyleRight.setFont(font);
    cellStyleRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

    // 红色背景样式
    final XSSFCellStyle redCellStyle = (XSSFCellStyle) cellStyleLeft.clone();
    redCellStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
    redCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

    // 创建表格
    final XSSFSheet sheet = workbook.createSheet("sheet1");
    // 设置表格默认列宽度为15个字节
    sheet.setDefaultColumnWidth(15);

    // 生成Excel Header Row
    final XSSFRow rowHeader = sheet.createRow(0);

    // 表头
    final String[] rowHeaderStrs = new String[11];
    rowHeaderStrs[0] = KANUtil.getProperty(request.getLocale(), "public.employee2.id");
    rowHeaderStrs[1] = KANUtil.getProperty(request.getLocale(), "public.employee2.name");
    rowHeaderStrs[2] = KANUtil.getProperty(request.getLocale(), "public.certificate.number");
    rowHeaderStrs[3] = KANUtil.getProperty(request.getLocale(), "public.contract2.id");
    rowHeaderStrs[4] = KANUtil.getProperty(request.getLocale(), "public.contract2.name");
    rowHeaderStrs[5] = KANUtil.getProperty(request.getLocale(), "employee.salary.adjustment.item");
    rowHeaderStrs[6] = KANUtil.getProperty(request.getLocale(), "employee.salary.adjustment.adjustment.before.base");
    rowHeaderStrs[7] = KANUtil.getProperty(request.getLocale(), "employee.salary.adjustment.adjustment.before.period");
    rowHeaderStrs[8] = KANUtil.getProperty(request.getLocale(), "employee.salary.adjustment.adjustment.after.base");
    rowHeaderStrs[9] = KANUtil.getProperty(request.getLocale(), "employee.salary.adjustment.adjustment.after.period");
    rowHeaderStrs[10] = KANUtil.getProperty(request.getLocale(), "public.status");

    // 遍历生成表头
    int headerColumnIndex = 0;
    for (int i = 0; i < rowHeaderStrs.length; i++) {
      final XSSFCell thisCell = rowHeader.createCell(headerColumnIndex);
      thisCell.setCellValue(rowHeaderStrs[i]);
      thisCell.setCellStyle(cellStyleLeft);
      headerColumnIndex++;
    }

    if (pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0) {
      // 行下表
      int bodyRowIndex = 1;
      // 列下标
      int bodyColumnIndex = -1;
      // 遍历生成表体
      for (Object employeePositionChangeObject : pagedListHolder.getSource()) {
        final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = (EmployeeSalaryAdjustmentVO) employeePositionChangeObject;
        // 创建行
        final XSSFRow rowBody = sheet.createRow(bodyRowIndex);

        bodyColumnIndex++;
        final XSSFCell employeeIdCell = rowBody.createCell(bodyColumnIndex);
        employeeIdCell.setCellStyle(cellStyleLeft);
        employeeIdCell.setCellValue(employeeSalaryAdjustmentVO.getEmployeeId());

        bodyColumnIndex++;
        final XSSFCell employeeNameCell = rowBody.createCell(bodyColumnIndex);
        employeeNameCell.setCellStyle(cellStyleLeft);
        employeeNameCell.setCellValue(employeeSalaryAdjustmentVO.getEmployeeName());

        bodyColumnIndex++;
        final XSSFCell certificateNumberCell = rowBody.createCell(bodyColumnIndex);
        certificateNumberCell.setCellStyle(cellStyleLeft);
        certificateNumberCell.setCellValue(employeeSalaryAdjustmentVO.getEmployeeCertificateNumber());

        bodyColumnIndex++;
        final XSSFCell contractIdCell = rowBody.createCell(bodyColumnIndex);
        contractIdCell.setCellStyle(cellStyleLeft);
        contractIdCell.setCellValue(employeeSalaryAdjustmentVO.getContractId());

        bodyColumnIndex++;
        final XSSFCell employeeContractNameCell = rowBody.createCell(bodyColumnIndex);
        employeeContractNameCell.setCellStyle(cellStyleLeft);
        employeeContractNameCell.setCellValue(employeeSalaryAdjustmentVO.getEmployeeContractName());

        bodyColumnIndex++;
        final XSSFCell decodeItemIdCell = rowBody.createCell(bodyColumnIndex);
        decodeItemIdCell.setCellStyle(cellStyleLeft);
        decodeItemIdCell.setCellValue(employeeSalaryAdjustmentVO.getDecodeItemId());

        bodyColumnIndex++;
        final XSSFCell oldBaseCell = rowBody.createCell(bodyColumnIndex);
        oldBaseCell.setCellStyle(cellStyleLeft);
        oldBaseCell.setCellValue(employeeSalaryAdjustmentVO.getOldBase());

        bodyColumnIndex++;
        String oldPeriod = "";
        final XSSFCell oldSalaryPeriodCell = rowBody.createCell(bodyColumnIndex);
        oldSalaryPeriodCell.setCellStyle(cellStyleLeft);
        if (KANUtil.filterEmpty(employeeSalaryAdjustmentVO.getOldStartDate()) != null) {
          oldPeriod = employeeSalaryAdjustmentVO.getOldStartDate() + " ~ ";
          if (KANUtil.filterEmpty(employeeSalaryAdjustmentVO.getOldEndDate()) != null) {
            oldPeriod += employeeSalaryAdjustmentVO.getOldEndDate();
          }
          oldSalaryPeriodCell.setCellValue(oldPeriod);
        }

        bodyColumnIndex++;
        final XSSFCell newBaseCell = rowBody.createCell(bodyColumnIndex);
        newBaseCell.setCellStyle(cellStyleLeft);
        newBaseCell.setCellValue(employeeSalaryAdjustmentVO.getNewBase());

        bodyColumnIndex++;
        String newPeriod = "";
        final XSSFCell newSalaryPeriodCell = rowBody.createCell(bodyColumnIndex);
        newSalaryPeriodCell.setCellStyle(cellStyleLeft);
        if (KANUtil.filterEmpty(employeeSalaryAdjustmentVO.getNewStartDate()) != null) {
          newPeriod = employeeSalaryAdjustmentVO.getNewStartDate() + " ~ ";
          if (KANUtil.filterEmpty(employeeSalaryAdjustmentVO.getNewEndDate()) != null) {
            newPeriod += employeeSalaryAdjustmentVO.getNewEndDate();
          }
          newSalaryPeriodCell.setCellValue(newPeriod);
        }

        bodyColumnIndex++;
        final XSSFCell decodeStatusCell = rowBody.createCell(bodyColumnIndex);
        decodeStatusCell.setCellStyle(cellStyleLeft);
        decodeStatusCell.setCellValue(employeeSalaryAdjustmentVO.getDecodeStatus());

        bodyRowIndex++;
        bodyColumnIndex = -1;
      }
    }

    // 添加Log End跟踪
    LogFactory.getLog("general employee position change excel").info("Excel Generate (" + BaseAction.getAccountId(request, null) + ", " + BaseAction.getUsername(request, null) + ") End.");

    return workbook;
  }

  public static XSSFWorkbook generateEmployeePositionChange(final HttpServletRequest request) throws KANException {
    // 添加Log Start跟踪
    LogFactory.getLog("generateEmployeePositionChange").info("Excel Generate (" + BaseAction.getAccountId(request, null) + ", " + BaseAction.getUsername(request, null) + ") Start.");

    // 初始化PagedListHolder
    final List<Object> employeePositionChangeVOs = ((PagedListHolder) request.getAttribute("positionChangeHolder")).getSource();

    // 初始化工作薄
    final XSSFWorkbook workbook = new XSSFWorkbook();

    if (employeePositionChangeVOs != null && employeePositionChangeVOs.size() > 0) {
      // 创建字体
      final Font font = workbook.createFont();
      font.setFontName("Calibri");
      font.setFontHeightInPoints((short) 11);

      // 创建单元格样式
      final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
      cellStyleLeft.setFont(font);
      cellStyleLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);

      // 创建单元格样式
      final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
      cellStyleCenter.setFont(font);
      cellStyleCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);

      // 创建单元格样式
      final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
      cellStyleRight.setFont(font);
      cellStyleRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

      // 红色背景样式
      final XSSFCellStyle redCellStyle = (XSSFCellStyle) cellStyleLeft.clone();
      redCellStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
      redCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

      // 创建表格
      final XSSFSheet sheet = workbook.createSheet("sheet1");
      // 设置表格默认列宽度为15个字节
      sheet.setDefaultColumnWidth(15);

      // 生成Excel Header Row
      final XSSFRow rowHeader = sheet.createRow(0);

      // 表头
      final String[] rowHeaderStrs = new String[20];
      rowHeaderStrs[0] = KANUtil.getProperty(request.getLocale(), "public.employee2.id");
      rowHeaderStrs[1] = KANUtil.getProperty(request.getLocale(), "public.employee2.name");
      rowHeaderStrs[2] = KANUtil.getProperty(request.getLocale(), "public.certificate.number");
      rowHeaderStrs[3] = KANUtil.getProperty(request.getLocale(), "employee.position.change.oldBranchName");
      rowHeaderStrs[4] = KANUtil.getProperty(request.getLocale(), "employee.position.change.oldParentBranchName");
      rowHeaderStrs[5] = KANUtil.getProperty(request.getLocale(), "employee.position.change.oldPositionName");
      rowHeaderStrs[6] = KANUtil.getProperty(request.getLocale(), "employee.position.change.oldParentPositionName");
      rowHeaderStrs[7] = KANUtil.getProperty(request.getLocale(), "employee.position.change.oldPositionGradeName");
      rowHeaderStrs[8] = KANUtil.getProperty(request.getLocale(), "employee.position.change.oldParentPositionOwnersName");

      rowHeaderStrs[9] = KANUtil.getProperty(request.getLocale(), "employee.position.change.newBranchId");
      rowHeaderStrs[10] = KANUtil.getProperty(request.getLocale(), "employee.position.change.newParentBranchName");
      rowHeaderStrs[11] = KANUtil.getProperty(request.getLocale(), "employee.position.change.newPositionId");
      rowHeaderStrs[12] = KANUtil.getProperty(request.getLocale(), "employee.position.change.newParentPositionName");
      rowHeaderStrs[13] = KANUtil.getProperty(request.getLocale(), "employee.position.change.newPositionGradeName");
      rowHeaderStrs[14] = KANUtil.getProperty(request.getLocale(), "employee.position.change.newParentPositionOwnersName");
      rowHeaderStrs[15] = "New Direct Report Manager (Biz Leader)";
      rowHeaderStrs[16] = "Job Role";

      rowHeaderStrs[17] = KANUtil.getProperty(request.getLocale(), "employee.position.change.effectiveDate");
      rowHeaderStrs[18] = KANUtil.getProperty(request.getLocale(), "public.status");
      rowHeaderStrs[19] = KANUtil.getProperty(request.getLocale(), "employee.position.change.description");

      // 遍历生成表头
      int headerColumnIndex = 0;
      for (int i = 0; i < rowHeaderStrs.length; i++) {
        final XSSFCell thisCell = rowHeader.createCell(headerColumnIndex);
        thisCell.setCellValue(rowHeaderStrs[i]);
        thisCell.setCellStyle(cellStyleLeft);
        headerColumnIndex++;
      }

      // 行下表
      int bodyRowIndex = 1;
      // 列下标
      int bodyColumnIndex = -1;
      // 遍历生成表体
      for (Object employeePositionChangeVOObject : employeePositionChangeVOs) {
        final EmployeePositionChangeVO employeePositionChangeVO = (EmployeePositionChangeVO) employeePositionChangeVOObject;
        employeePositionChangeVO.reset(null, request);
        // 创建行
        final XSSFRow rowBody = sheet.createRow(bodyRowIndex);

        bodyColumnIndex++;
        final XSSFCell employeeIdCell = rowBody.createCell(bodyColumnIndex);
        employeeIdCell.setCellStyle(cellStyleLeft);
        employeeIdCell.setCellValue(employeePositionChangeVO.getEmployeeId());

        bodyColumnIndex++;
        final XSSFCell employeeNameCell = rowBody.createCell(bodyColumnIndex);
        employeeNameCell.setCellStyle(cellStyleLeft);
        employeeNameCell.setCellValue(employeePositionChangeVO.getEmployeeName());

        bodyColumnIndex++;
        final XSSFCell certificateNumberCell = rowBody.createCell(bodyColumnIndex);
        certificateNumberCell.setCellStyle(cellStyleLeft);
        certificateNumberCell.setCellValue(employeePositionChangeVO.getEmployeeCertificateNumber());

        bodyColumnIndex++;
        final XSSFCell oldBranchNameCell = rowBody.createCell(bodyColumnIndex);
        oldBranchNameCell.setCellStyle(cellStyleLeft);
        oldBranchNameCell.setCellValue(employeePositionChangeVO.getOldBranchName());

        bodyColumnIndex++;
        final XSSFCell oldParentBranchNameCell = rowBody.createCell(bodyColumnIndex);
        oldParentBranchNameCell.setCellStyle(cellStyleLeft);
        oldParentBranchNameCell.setCellValue(employeePositionChangeVO.getOldParentBranchName());

        bodyColumnIndex++;
        final XSSFCell oldPositionNameCell = rowBody.createCell(bodyColumnIndex);
        oldPositionNameCell.setCellStyle(cellStyleLeft);
        oldPositionNameCell.setCellValue(employeePositionChangeVO.getOldPositionName());

        bodyColumnIndex++;
        final XSSFCell oldPatentPositionNameCell = rowBody.createCell(bodyColumnIndex);
        oldPatentPositionNameCell.setCellStyle(cellStyleLeft);
        oldPatentPositionNameCell.setCellValue(employeePositionChangeVO.getOldParentPositionName());

        bodyColumnIndex++;
        final XSSFCell oldPositionGradeNameCell = rowBody.createCell(bodyColumnIndex);
        oldPositionGradeNameCell.setCellStyle(cellStyleLeft);
        oldPositionGradeNameCell.setCellValue(employeePositionChangeVO.getOldPositionGradeName());

        bodyColumnIndex++;
        final XSSFCell oldParentPositionOwnersNameCell = rowBody.createCell(bodyColumnIndex);
        oldParentPositionOwnersNameCell.setCellStyle(cellStyleLeft);
        oldParentPositionOwnersNameCell.setCellValue(employeePositionChangeVO.getOldParentPositionOwnersName());

        // 如果调整了，则显示
        bodyColumnIndex++;
        final XSSFCell newBranchNameCell = rowBody.createCell(bodyColumnIndex);
        if (KANUtil.filterEmpty(employeePositionChangeVO.getNewBranchName()) != null && KANUtil.filterEmpty(employeePositionChangeVO.getOldBranchName()) != null
            && !employeePositionChangeVO.getNewBranchName().equals(employeePositionChangeVO.getOldBranchName())) {
          newBranchNameCell.setCellStyle(redCellStyle);
          newBranchNameCell.setCellValue(employeePositionChangeVO.getNewBranchName());
        }

        bodyColumnIndex++;
        final XSSFCell newParentBranchNameCell = rowBody.createCell(bodyColumnIndex);
        if (KANUtil.filterEmpty(employeePositionChangeVO.getNewParentBranchName()) != null && KANUtil.filterEmpty(employeePositionChangeVO.getOldParentBranchName()) != null
            && !employeePositionChangeVO.getNewParentBranchName().equals(employeePositionChangeVO.getOldParentBranchName())) {
          newParentBranchNameCell.setCellStyle(redCellStyle);
          newParentBranchNameCell.setCellValue(employeePositionChangeVO.getNewParentBranchName());
        }

        bodyColumnIndex++;
        final XSSFCell newPositionNameCell = rowBody.createCell(bodyColumnIndex);
        if (KANUtil.filterEmpty(employeePositionChangeVO.getNewPositionName()) != null && KANUtil.filterEmpty(employeePositionChangeVO.getOldPositionName()) != null
            && !employeePositionChangeVO.getNewPositionName().equals(employeePositionChangeVO.getOldPositionName())) {
          newPositionNameCell.setCellStyle(redCellStyle);
          newPositionNameCell.setCellValue(employeePositionChangeVO.getNewPositionName());
        }

        bodyColumnIndex++;
        final XSSFCell newParentPositionNameCell = rowBody.createCell(bodyColumnIndex);
        if (KANUtil.filterEmpty(employeePositionChangeVO.getNewParentPositionName()) != null && KANUtil.filterEmpty(employeePositionChangeVO.getOldParentPositionName()) != null
            && !employeePositionChangeVO.getNewParentPositionName().equals(employeePositionChangeVO.getOldParentPositionName())) {
          newParentPositionNameCell.setCellStyle(redCellStyle);
          newParentPositionNameCell.setCellValue(employeePositionChangeVO.getNewParentPositionName());
        }

        bodyColumnIndex++;
        final XSSFCell newPositionGradeNameCell = rowBody.createCell(bodyColumnIndex);
        if (KANUtil.filterEmpty(employeePositionChangeVO.getNewPositionGradeName()) != null && KANUtil.filterEmpty(employeePositionChangeVO.getOldPositionGradeName()) != null
            && !employeePositionChangeVO.getNewPositionGradeName().equals(employeePositionChangeVO.getOldPositionGradeName())) {
          newPositionGradeNameCell.setCellStyle(redCellStyle);
          newPositionGradeNameCell.setCellValue(employeePositionChangeVO.getNewPositionGradeName());
        }

        bodyColumnIndex++;
        final XSSFCell newParentPositionOwnersNameCell = rowBody.createCell(bodyColumnIndex);
        if (KANUtil.filterEmpty(employeePositionChangeVO.getNewParentPositionOwnersName()) != null && KANUtil.filterEmpty(employeePositionChangeVO.getOldParentPositionOwnersName()) != null
            && !employeePositionChangeVO.getNewParentPositionOwnersName().equals(employeePositionChangeVO.getOldParentPositionOwnersName())) {
          newParentPositionOwnersNameCell.setCellStyle(redCellStyle);
          newParentPositionOwnersNameCell.setCellValue(employeePositionChangeVO.getNewParentPositionOwnersName());
        }

        bodyColumnIndex++;
        final XSSFCell remark2Cell = rowBody.createCell(bodyColumnIndex);
        if (KANUtil.filterEmpty(employeePositionChangeVO.getRemark2()) != null) {
          remark2Cell.setCellStyle(redCellStyle);
          remark2Cell.setCellValue(employeePositionChangeVO.getRemark2());
        }

        bodyColumnIndex++;
        final XSSFCell jobRoleCell = rowBody.createCell(bodyColumnIndex);
        if (KANUtil.filterEmpty(employeePositionChangeVO.getRemark1(), "0") != null) {
          jobRoleCell.setCellStyle(redCellStyle);
          jobRoleCell.setCellValue(employeePositionChangeVO.getDecodeJobRole());
        }

        bodyColumnIndex++;
        final XSSFCell effectiveDateCell = rowBody.createCell(bodyColumnIndex);
        effectiveDateCell.setCellStyle(cellStyleLeft);
        effectiveDateCell.setCellValue(employeePositionChangeVO.getEffectiveDate());

        bodyColumnIndex++;
        final XSSFCell statusCell = rowBody.createCell(bodyColumnIndex);
        statusCell.setCellStyle(cellStyleLeft);
        statusCell.setCellValue(employeePositionChangeVO.getDecodeStatus());

        bodyColumnIndex++;
        final XSSFCell changeReasonCell = rowBody.createCell(bodyColumnIndex);
        changeReasonCell.setCellStyle(cellStyleLeft);
        changeReasonCell.setCellValue(employeePositionChangeVO.getDecodeChangeReason());

        bodyColumnIndex = -1;
        bodyRowIndex++;
      }
    }

    // 添加Log End跟踪
    LogFactory.getLog("general employee position change excel").info("Excel Generate (" + BaseAction.getAccountId(request, null) + ", " + BaseAction.getUsername(request, null) + ") End.");

    return workbook;
  }

  /**
   * 通讯录
   * 
   * @param request
   * @return
   * @throws KANException
   */
  public static XSSFWorkbook generateContact(final HttpServletRequest request) throws KANException {
    // 添加Log Start跟踪
    LogFactory.getLog("generateContact").info("Excel Generate (" + BaseAction.getAccountId(request, null) + ", " + BaseAction.getUsername(request, null) + ") Start.");

    // 初始化PagedListHolder
    final List<Object> employeeReportVOs = ((PagedListHolder) request.getAttribute("employeeReportHolder")).getSource();

    // 初始化工作薄
    final XSSFWorkbook workbook = new XSSFWorkbook();

    if (employeeReportVOs != null && employeeReportVOs.size() > 0) {
      // 创建字体
      final Font font = workbook.createFont();
      font.setFontName("Calibri");
      font.setFontHeightInPoints((short) 11);

      // 创建单元格样式
      final CellStyle cellStyleLeft = workbook.createCellStyle();
      cellStyleLeft.setFont(font);
      cellStyleLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);

      // 创建单元格样式
      final CellStyle cellStyleCenter = workbook.createCellStyle();
      cellStyleCenter.setFont(font);
      cellStyleCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);

      // 创建单元格样式
      final CellStyle cellStyleRight = workbook.createCellStyle();
      cellStyleRight.setFont(font);
      cellStyleRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

      // 创建表格
      final Sheet sheet = workbook.createSheet("sheet1");
      // 设置表格默认列宽度为15个字节
      sheet.setDefaultColumnWidth(15);

      // 生成Excel Header Row
      final Row rowHeader = sheet.createRow(0);

      // 表头
      final String[] rowHeaderStrs = new String[] {"员工姓名（中文）", "员工姓名（英文）", "法务实体（中文）", "法务实体（英文）", "BU/Function", "Department (Chinese)", "Department (English)", "办公地点", "职位", "Working Title/Position (English)", "手机号码", "公司邮箱", "公司电话"};

      int headerColumnIndex = 0;

      Cell cell = null;
      for (int i = 0; i < rowHeaderStrs.length; i++) {
        cell = rowHeader.createCell(headerColumnIndex);
        cell.setCellValue(rowHeaderStrs[i]);
        cell.setCellStyle(cellStyleLeft);
        headerColumnIndex++;
      }

      // 表信息
      int bodyRowIndex = 1;
      for (Object employeeReportVOObject : employeeReportVOs) {
        final EmployeeReportVO employeeReportVO = (EmployeeReportVO) employeeReportVOObject;
        int bodyColumnIndex = 0;
        final Row rowBody = sheet.createRow(bodyRowIndex);

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getNameZH());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getShortName());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getEntityNameZH());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getEntityNameEN());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getParentBranchNameEN());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getBranchNameZH());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getBranchNameEN());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(String.valueOf(employeeReportVO.getDynaColumns().get("bangongdidian")));
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(String.valueOf(employeeReportVO.getDynaColumns().get("neibuchengwei")));
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(String.valueOf(employeeReportVO.getDynaColumns().get("zhiweimingchengyingwen")));
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getMobile1());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getEmail1());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getPhone2());
        bodyColumnIndex++;

        bodyRowIndex++;

      }

    }

    // 添加Log End跟踪
    LogFactory.getLog("general employee position change excel").info("Excel Generate (" + BaseAction.getAccountId(request, null) + ", " + BaseAction.getUsername(request, null) + ") End.");

    return workbook;
  }

  /**
   * 这个方法已经被exportWXContactCSV代替 现在这个方法也不用了
   * 
   * @param request
   * @return
   * @throws KANException
   */
  public static XSSFWorkbook generateWXContact(final HttpServletRequest request) throws KANException {
    // 添加Log Start跟踪
    LogFactory.getLog("generateContact").info("Excel Generate (" + BaseAction.getAccountId(request, null) + ", " + BaseAction.getUsername(request, null) + ") Start.");

    // 初始化PagedListHolder
    final List<Object> employeeReportVOs = ((PagedListHolder) request.getAttribute("employeeReportHolder")).getSource();

    // 初始化工作薄
    final XSSFWorkbook workbook = new XSSFWorkbook();

    if (employeeReportVOs != null && employeeReportVOs.size() > 0) {
      // 创建字体
      final Font font = workbook.createFont();
      font.setFontName("Calibri");
      font.setFontHeightInPoints((short) 11);

      // 创建单元格样式
      final CellStyle cellStyleLeft = workbook.createCellStyle();
      cellStyleLeft.setFont(font);
      cellStyleLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);

      // 创建单元格样式
      final CellStyle cellStyleCenter = workbook.createCellStyle();
      cellStyleCenter.setFont(font);
      cellStyleCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);

      // 创建单元格样式
      final CellStyle cellStyleRight = workbook.createCellStyle();
      cellStyleRight.setFont(font);
      cellStyleRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

      // 创建表格
      final Sheet sheet = workbook.createSheet("sheet1");
      // 设置表格默认列宽度为15个字节
      sheet.setDefaultColumnWidth(15);

      // 生成Excel Header Row
      final Row rowHeader = sheet.createRow(0);

      // 表头Dept,Location,Title,Mail,Phone
      final String[] rowHeaderStrs = new String[] {"姓名", "账号", "微信号", "手机号", "邮箱", "性别", "所在部门", "职位", "Name(CN)", "Name(EN)", "Dept", "Location", "Title", "Mail", "Phone"};

      int headerColumnIndex = 0;

      Cell cell = null;
      for (int i = 0; i < rowHeaderStrs.length; i++) {
        cell = rowHeader.createCell(headerColumnIndex);
        cell.setCellValue(rowHeaderStrs[i]);
        cell.setCellStyle(cellStyleLeft);
        headerColumnIndex++;
      }

      // 表信息
      int bodyRowIndex = 1;
      for (Object employeeReportVOObject : employeeReportVOs) {
        final EmployeeReportVO employeeReportVO = (EmployeeReportVO) employeeReportVOObject;
        int bodyColumnIndex = 0;
        final Row rowBody = sheet.createRow(bodyRowIndex);

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getNameZH());
        bodyColumnIndex++;

        // 账号.取邮箱的@前面
        String userid = "";
        if (KANUtil.filterEmpty(employeeReportVO.getEmail1()) != null) {
          userid = employeeReportVO.getEmail1().split("@")[0];
        }
        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(userid);
        bodyColumnIndex++;

        /*
         * 微信号不用写
         */
        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue("");
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getMobile1());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getEmail1());
        bodyColumnIndex++;

        // 性别
        final String[] sexs = new String[] {"", "男", "女"};
        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        int sex = KANUtil.filterEmpty(employeeReportVO.getSalutation()) == null ? 0 : Integer.parseInt(employeeReportVO.getSalutation());
        cell.setCellValue(sexs[sex]);
        bodyColumnIndex++;

        // bufunction , "职位", "Name(CN)", "Name(EN)", "Dept", "Location", "Title", "Mail", "Phone"
        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getDecode_tempParentBranchIds());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(String.valueOf(employeeReportVO.getDynaColumns().get("neibuchengwei")));
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getNameZH());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getNameEN());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getDecodeBranch());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(String.valueOf(employeeReportVO.getDynaColumns().get("bangongdidian")));
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(String.valueOf(employeeReportVO.getDynaColumns().get("zhiweimingchengyingwen")));
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellValue(employeeReportVO.getEmail1());
        bodyColumnIndex++;

        cell = rowBody.createCell(bodyColumnIndex);
        cell.setCellStyle(cellStyleLeft);
        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new XSSFRichTextString(employeeReportVO.getPhone2()));
        bodyColumnIndex++;

        bodyRowIndex++;

      }

    }

    // 添加Log End跟踪
    LogFactory.getLog("general employee position change excel").info("Excel Generate (" + BaseAction.getAccountId(request, null) + ", " + BaseAction.getUsername(request, null) + ") End.");

    return workbook;
  }

  public static String exportWXContactCSV(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    /** CSV文件列分隔符 */
    final String CSV_COLUMN_SEPARATOR = ",";

    /** CSV文件列分隔符 */
    final String CSV_RN = "\r\n";
    // 初始化PagedListHolder
    final List<Object> employeeReportVOs = ((PagedListHolder) request.getAttribute("employeeReportHolder")).getSource();

    StringBuffer sb = new StringBuffer();
    final String[] rowHeaderStrs = new String[] {"姓名", "账号", "微信号", "手机号", "邮箱", "性别", "所在部门", "职位", "Name(CN)", "Name(EN)", "Dept", "Location", "Title", "Mail", "Phone"};
    // 输出列头
    for (int i = 0; i < rowHeaderStrs.length; i++) {
      sb.append(rowHeaderStrs[i]).append(CSV_COLUMN_SEPARATOR);
    }
    sb.append(CSV_RN);

    final String[] sexs = new String[] {"", "男", "女"};
    for (int i = 0; i < employeeReportVOs.size(); i++) {
      final EmployeeReportVO employeeReportVO = (EmployeeReportVO) employeeReportVOs.get(i);
      final String nameZH = employeeReportVO.getNameZH();
      final String email1 = KANUtil.filterEmpty(employeeReportVO.getEmail1()) != null ? employeeReportVO.getEmail1().toLowerCase().replaceAll(" ", "") : "";
      String userid = "";
      if (KANUtil.filterEmpty(email1) != null) {
        userid = email1.split("@")[0];
      }
      // final String mobile1 = employeeReportVO.getMobile1();
      int sex = KANUtil.filterEmpty(employeeReportVO.getSalutation()) == null ? 0 : Integer.parseInt(employeeReportVO.getSalutation());
      final String buFunction = employeeReportVO.getDecode_tempParentBranchIds();
      final String nbcw = String.valueOf(employeeReportVO.getDynaColumns().get("neibuchengwei"));
      final String neibuchengwei = KANUtil.filterEmpty(nbcw) == null ? "" : nbcw.replaceAll(",", " & ");
      final String nameEN = employeeReportVO.getNameEN();
      final String branch = employeeReportVO.getDecode_tempBranchIds();
      final String banggongdidian = String.valueOf(employeeReportVO.getDynaColumns().get("bangongdidian"));
      final String zhiweimingchengyingwen = String.valueOf(employeeReportVO.getDynaColumns().get("zhiweimingchengyingwen"));
      final String phone2 = employeeReportVO.getPhone2();
      // "姓名", "账号", "微信号", "手机号", "邮箱", "性别", "所在部门", "职位",
      // "Name(CN)", "Name(EN)", "Dept", "Location", "Title", "Mail", "Phone"
      // userid做姓名
      sb.append(userid).append(CSV_COLUMN_SEPARATOR);
      sb.append(userid).append(CSV_COLUMN_SEPARATOR);
      sb.append("").append(CSV_COLUMN_SEPARATOR);
      sb.append("").append(CSV_COLUMN_SEPARATOR);
      sb.append(email1).append(CSV_COLUMN_SEPARATOR);
      sb.append(sexs[sex]).append(CSV_COLUMN_SEPARATOR);
      sb.append(buFunction).append(CSV_COLUMN_SEPARATOR);
      sb.append(neibuchengwei).append(CSV_COLUMN_SEPARATOR);
      //
      sb.append(nameZH).append(CSV_COLUMN_SEPARATOR);
      sb.append(nameEN).append(CSV_COLUMN_SEPARATOR);
      sb.append(branch).append(CSV_COLUMN_SEPARATOR);
      sb.append(banggongdidian).append(CSV_COLUMN_SEPARATOR);
      sb.append(zhiweimingchengyingwen).append(CSV_COLUMN_SEPARATOR);
      sb.append(email1).append(CSV_COLUMN_SEPARATOR);
      sb.append(phone2).append(CSV_COLUMN_SEPARATOR);
      sb.append(CSV_RN);
    }
    return sb.toString();
  }
}
