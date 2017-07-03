package com.kan.hro.service.impl.biz.performance;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ColumnDao;
import com.kan.base.dao.inf.management.ExchangeRateDao;
import com.kan.base.dao.inf.management.YERRRuleDao;
import com.kan.base.dao.inf.message.MessageMailDao;
import com.kan.base.dao.inf.message.MessageTemplateDao;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.YERRRuleVO;
import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.domain.message.MessageTemplateVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.MatchUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.json.JsonMapper;
import com.kan.base.util.poi.POIUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentDao;
import com.kan.hro.dao.inf.biz.performance.BudgetSettingDetailDao;
import com.kan.hro.dao.inf.biz.performance.BudgetSettingHeaderDao;
import com.kan.hro.dao.inf.biz.performance.PerformanceDao;
import com.kan.hro.dao.inf.biz.performance.SelfAssessmentDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.performance.BudgetSettingDetailVO;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;
import com.kan.hro.domain.biz.performance.PerformanceVO;
import com.kan.hro.domain.biz.performance.SelfAssessmentVO;
import com.kan.hro.service.inf.biz.performance.PerformanceService;

import net.sf.json.JSONObject;

public class PerformanceServiceImpl extends ContextService implements PerformanceService {
  // 自定义字段前缀
  public static final String COLUMN_NAME_DB_PREFIX = "performanceRating_";

  // 自定义 - 字段分组<绩效> ID
  public static final String PERFORMANCE_DEFINE_GROUP_ID = "25";

  // 模板第14行开始写入数据
  private static final int FIRST_ROW_INDEX = 13;

  // 模板中对应的公式
  private static Map<String, String> FORMULA_MAP = new HashMap<String, String>();

  static {
    FORMULA_MAP.put("AF", "ROUND(AE00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)");
    FORMULA_MAP.put("AG", "AE00*12");
    FORMULA_MAP.put("AH", "ROUND(AG00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)");
    FORMULA_MAP.put("AK", "AG00+AI00+AJ00");
    FORMULA_MAP.put("AL", "ROUND(AK00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)");
    FORMULA_MAP.put("AR", "AK00+AP00");
    FORMULA_MAP.put("AS", "ROUND(AR00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)");
    FORMULA_MAP.put("AV", "IFERROR(IF(AU00=\"P\",HLOOKUP(AD00,'YERR rule'!$G$18:$J$26,MATCH(AT00,'YERR rule'!$B$18:$B$26),0),HLOOKUP(AD00,'YERR rule'!$B$18:$F$26,MATCH(AT00,'YERR rule'!$B$18:$B$26),0)),\"\")");
    FORMULA_MAP.put("AX", "AR00*AW00");
    FORMULA_MAP.put("AY", "ROUND(AX00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)");
    FORMULA_MAP.put("AZ", "BB00/12");
    FORMULA_MAP.put("BA", "ROUND(AZ00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)");
    FORMULA_MAP.put("BB", "AW00*AG00");
    FORMULA_MAP.put("BC", "ROUND(BB00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)");
    FORMULA_MAP.put("BD", "+AI00*AW00");
    FORMULA_MAP.put("BE", "+AJ00*AW00");
    FORMULA_MAP.put("BF", "BB00+BD00+BE00");
    FORMULA_MAP.put("BG", "ROUND(BF00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)");
    FORMULA_MAP.put("BN", "IFERROR(VLOOKUP(BM00,'Job grade'!$A$2:$B$16,2,0),\"\")");
    FORMULA_MAP.put("BR", "IFERROR(VLOOKUP(AT00,'YERR rule'!$G$19:$K$26,5,0),\"\")");
    //
    FORMULA_MAP.put("BT", "+CB00/366*CA00");
    FORMULA_MAP.put("BV", "ROUND(BU00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)");

    FORMULA_MAP.put("BZ", "IF(AW00=\"\",0,AS00*(AW00-1))");
    // =IF((DATEDIF(W14,CF14,"d")+1)>IF(MOD(YEAR(CF14),4),365,366),IF(MOD(YEAR(CF14),4),365,366),(DATEDIF(W14,CF14,"d")+1))
    FORMULA_MAP.put("CA", "IF((DATEDIF(W00,CF00,\"d\")+1)>IF(MOD(YEAR(CF00),4),365,366),IF(MOD(YEAR(CF00),4),365,366),(DATEDIF(W00,CF00,\"d\")+1))");
  }

  final static String ITEM_BASE_SALARY = "1";

  final static String ITEM_HOUSING_ALLOWANCE = "10201";

  final static String ITEM_CHILD_EDU_ALLOWANCE = "10145";

  // New Incentive(monthly)
  final static String ITEM_INCENTIVE_MONTHLY = "10207";

  // New Incentive (Quarterly Target)
  final static String ITEM_INCENTIVE_QUARTERLY = "10153";

  // New Incentive (Target - % of R/GP)
  final static String ITEM_INCENTIVE_TARGET = "10155";

  // Year-End Bonus
  final static String ITEM_ANNUAL_BONUS = "18";

  // 调薪科目{"基本工资","住房补贴","子女教育津贴","奖金（月度）","奖金（季度）","奖金（提成）","年终奖"}
  final static String[] ADJUSTMENT_SALARY_ITEM_IDS = new String[] {"1", "10201", "10145", "10207", "10153", "10155", "18"};

  // 调薪通知书，发英文的地域ID(澳大利亚、香港、伦敦、新加坡)
  final static String[] USED_ENGLISH_TEMPLATE_LOCATION_IDS = new String[] {"57", "63", "69", "106"};

  private EmployeeDao employeeDao;
  private EmployeeContractDao employeeContractDao;
  private EmployeeContractSalaryDao employeeContractSalaryDao;
  private EmployeePositionChangeDao employeePositionChangeDao;
  private EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao;
  private PositionDao positionDao;
  private LogDao logDao;
  private ColumnDao columnDao;
  private MessageMailDao messageMailDao;
  private MessageTemplateDao messageTemplateDao;

  private YERRRuleDao yerrRuleDao;
  private ExchangeRateDao exchangeRateDao;
  private BudgetSettingHeaderDao budgetSettingHeaderDao;
  private BudgetSettingDetailDao budgetSettingDetailDao;
  private SelfAssessmentDao selfAssessmentDao;

  public SelfAssessmentDao getSelfAssessmentDao() {
    return selfAssessmentDao;
  }

  public void setSelfAssessmentDao(SelfAssessmentDao selfAssessmentDao) {
    this.selfAssessmentDao = selfAssessmentDao;
  }

  public BudgetSettingHeaderDao getBudgetSettingHeaderDao() {
    return budgetSettingHeaderDao;
  }

  public void setBudgetSettingHeaderDao(BudgetSettingHeaderDao budgetSettingHeaderDao) {
    this.budgetSettingHeaderDao = budgetSettingHeaderDao;
  }

  public BudgetSettingDetailDao getBudgetSettingDetailDao() {
    return budgetSettingDetailDao;
  }

  public void setBudgetSettingDetailDao(BudgetSettingDetailDao budgetSettingDetailDao) {
    this.budgetSettingDetailDao = budgetSettingDetailDao;
  }

  public YERRRuleDao getYerrRuleDao() {
    return yerrRuleDao;
  }

  public void setYerrRuleDao(YERRRuleDao yerrRuleDao) {
    this.yerrRuleDao = yerrRuleDao;
  }

  public ExchangeRateDao getExchangeRateDao() {
    return exchangeRateDao;
  }

  public void setExchangeRateDao(ExchangeRateDao exchangeRateDao) {
    this.exchangeRateDao = exchangeRateDao;
  }

  public MessageMailDao getMessageMailDao() {
    return messageMailDao;
  }

  public void setMessageMailDao(MessageMailDao messageMailDao) {
    this.messageMailDao = messageMailDao;
  }

  public MessageTemplateDao getMessageTemplateDao() {
    return messageTemplateDao;
  }

  public void setMessageTemplateDao(MessageTemplateDao messageTemplateDao) {
    this.messageTemplateDao = messageTemplateDao;
  }

  public ColumnDao getColumnDao() {
    return columnDao;
  }

  public void setColumnDao(ColumnDao columnDao) {
    this.columnDao = columnDao;
  }

  public EmployeeDao getEmployeeDao() {
    return employeeDao;
  }

  public void setEmployeeDao(EmployeeDao employeeDao) {
    this.employeeDao = employeeDao;
  }

  public EmployeeContractDao getEmployeeContractDao() {
    return employeeContractDao;
  }

  public void setEmployeeContractDao(EmployeeContractDao employeeContractDao) {
    this.employeeContractDao = employeeContractDao;
  }

  public EmployeeContractSalaryDao getEmployeeContractSalaryDao() {
    return employeeContractSalaryDao;
  }

  public void setEmployeeContractSalaryDao(EmployeeContractSalaryDao employeeContractSalaryDao) {
    this.employeeContractSalaryDao = employeeContractSalaryDao;
  }

  public EmployeePositionChangeDao getEmployeePositionChangeDao() {
    return employeePositionChangeDao;
  }

  public void setEmployeePositionChangeDao(EmployeePositionChangeDao employeePositionChangeDao) {
    this.employeePositionChangeDao = employeePositionChangeDao;
  }

  public EmployeeSalaryAdjustmentDao getEmployeeSalaryAdjustmentDao() {
    return employeeSalaryAdjustmentDao;
  }

  public void setEmployeeSalaryAdjustmentDao(EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao) {
    this.employeeSalaryAdjustmentDao = employeeSalaryAdjustmentDao;
  }

  public PositionDao getPositionDao() {
    return positionDao;
  }

  public void setPositionDao(PositionDao positionDao) {
    this.positionDao = positionDao;
  }

  public LogDao getLogDao() {
    return logDao;
  }

  public void setLogDao(LogDao logDao) {
    this.logDao = logDao;
  }

  @Override
  public PagedListHolder getPerformanceVOsByCondition(final PagedListHolder pagedListHolder, final boolean isPaged) throws KANException {
    final PerformanceDao performanceDao = (PerformanceDao) getDao();
    pagedListHolder.setHolderSize(performanceDao.countPerformanceVOsByCondition((PerformanceVO) pagedListHolder.getObject()));
    if (isPaged) {
      pagedListHolder.setSource(performanceDao.getPerformanceVOsByCondition((PerformanceVO) pagedListHolder.getObject(), new RowBounds(pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize())));
    } else {
      pagedListHolder.setSource(performanceDao.getPerformanceVOsByCondition((PerformanceVO) pagedListHolder.getObject()));
    }

    return pagedListHolder;
  }

  @Override
  public PerformanceVO getPerformanceVOByPerformanceId(final String performanceId) throws KANException {
    return ((PerformanceDao) getDao()).getPerformanceVOByPerformanceId(performanceId);
  }

  @Override
  public int insertPerformance(final PerformanceVO performanceVO) throws KANException {
    return ((PerformanceDao) getDao()).insertPerformance(performanceVO);
  }

  @Override
  public int updatePerformance(final PerformanceVO performanceVO) throws KANException {
    return ((PerformanceDao) getDao()).updatePerformance(performanceVO);
  }

  @Override
  public int countPerformanceVOsByCondition(PerformanceVO performanceVO) throws KANException {
    return ((PerformanceDao) getDao()).countPerformanceVOsByCondition(performanceVO);
  }

  @Override
  public List<Object> getPerformanceVOsByCondition(PerformanceVO performanceVO) throws KANException {
    return ((PerformanceDao) getDao()).getPerformanceVOsByCondition(performanceVO);
  }

  /***
   * 发送调薪通知信，最后一步，hr点击发送调薪通知信按钮
   */
  @Override
  public int sendAdjustmentSalaryNoticeLetter(List<Object> performanceVOs, String userId) throws KANException {
    int rows = 0;

    if (performanceVOs == null || performanceVOs.size() == 0)
      return rows;

    Date modifyDate = new Date();

    String sendNoticeTemplateId = "0";

    final Map<String, Object> mapParameter = new HashMap<String, Object>();
    mapParameter.put("accountId", ((PerformanceVO) performanceVOs.get(0)).getAccountId());
    mapParameter.put("corpId", ((PerformanceVO) performanceVOs.get(0)).getCorpId());
    mapParameter.put("year", ((PerformanceVO) performanceVOs.get(0)).getYearly());
    List<Object> budgetSettingHeaderVOs = budgetSettingHeaderDao.getBudgetSettingHeaderVOsByMapParameter(mapParameter);

    if (budgetSettingHeaderVOs != null && budgetSettingHeaderVOs.size() > 0) {
      sendNoticeTemplateId = ((BudgetSettingHeaderVO) budgetSettingHeaderVOs.get(0)).getNoticeLetterTemplate();
    }

    try {
      startTransaction();

      for (Object obj : performanceVOs) {
        final PerformanceVO performanceVO = (PerformanceVO) obj;
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("remark2", performanceVO.getPerformanceId());
        List<Object> list = this.selfAssessmentDao.getSelfAssessmentVOsByMapParameter(searchMap);
        if (list != null && list.size() > 0) {
          final SelfAssessmentVO selfAssessmentVO = (SelfAssessmentVO) list.get(0);
          // 员工确认才发邮件通知
          if ("6".equals(selfAssessmentVO.getStatus())) {
            rows = rows + sendMailNotice((PerformanceVO) obj, sendNoticeTemplateId);

            // HR已邮件通知
            selfAssessmentVO.setStatus("7");
            selfAssessmentVO.setModifyBy(userId);
            selfAssessmentVO.setModifyDate(modifyDate);
            this.selfAssessmentDao.updateSelfAssessment(selfAssessmentVO);
          }
        }
      }

      commitTransaction();
    } catch (Exception e) {
      rollbackTransaction();
      throw new KANException(e);
    }
    return rows;
  }

  /***
   * 确认调整，HR调整rating完成，同步到SelfAssessmentVO的final_rating,
   */
  @Override
  public int confirmFinalRating(List<Object> performanceVOs, String userId) throws KANException {
    int rows = 0;
    Date modifyDate = new Date();
    try {
      startTransaction();

      for (Object obj : performanceVOs) {
        final PerformanceVO todoUpdate_obj = (PerformanceVO) obj;
        if (todoUpdate_obj.getYearPerformanceRating() == null || todoUpdate_obj.getYearPerformanceRating().equals(""))
          continue;

        final Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("remark2", todoUpdate_obj.getPerformanceId());
        searchMap.put("employeeId", todoUpdate_obj.getEmployeeId());
        searchMap.put("year", todoUpdate_obj.getYearly());

        List<Object> list = this.selfAssessmentDao.getSelfAssessmentVOsByMapParameter(searchMap);
        if (list != null && list.size() > 0) {
          final SelfAssessmentVO selfAssessmentVO = (SelfAssessmentVO) list.get(0);
          // 最终评分
          selfAssessmentVO.setRating_final(todoUpdate_obj.getYearPerformanceRating());
          // HR已调整
          selfAssessmentVO.setStatus("5");
          selfAssessmentVO.setModifyBy(userId);
          selfAssessmentVO.setModifyDate(modifyDate);

          rows = rows + this.selfAssessmentDao.updateSelfAssessment(selfAssessmentVO);
        }
      }

      commitTransaction();
    } catch (Exception e) {
      rollbackTransaction();
      throw new KANException(e);
    }
    return rows;
  }

  @Override
  public int updatePerformance(final PerformanceVO performanceDB, final PerformanceVO performanceVO) throws KANException {
    performanceDB.setYearPerformanceRating(performanceVO.getYearPerformanceRating());
    performanceDB.setYearPerformancePromotion(performanceVO.getYearPerformancePromotion());
    performanceDB.setRecommendTTCIncrease(performanceVO.getRecommendTTCIncrease());
    performanceDB.setTtcIncrease(performanceVO.getTtcIncrease());
    performanceDB.setNewTTCLocal(performanceVO.getNewTTCLocal());
    performanceDB.setNewTTCUSD(performanceVO.getNewTTCUSD());
    performanceDB.setNewBaseSalaryLocal(performanceVO.getNewBaseSalaryLocal());
    performanceDB.setNewBaseSalaryUSD(performanceVO.getNewBaseSalaryUSD());
    performanceDB.setNewAnnualSalaryLocal(performanceVO.getNewAnnualSalaryLocal());
    performanceDB.setNewAnnualSalaryUSD(performanceVO.getNewAnnualSalaryUSD());
    performanceDB.setNewAnnualHousingAllowance(performanceVO.getNewAnnualHousingAllowance());
    performanceDB.setNewAnnualChildrenEduAllowance(performanceVO.getNewAnnualChildrenEduAllowance());
    performanceDB.setNewAnnualGuaranteedAllowanceLocal(performanceVO.getNewAnnualGuaranteedAllowanceLocal());
    performanceDB.setNewAnnualGuatanteedAllowanceUSD(performanceVO.getNewAnnualGuatanteedAllowanceUSD());
    performanceDB.setNewMonthlyTarget(performanceVO.getNewMonthlyTarget());
    performanceDB.setNewQuarterlyTarget(performanceVO.getNewQuarterlyTarget());
    performanceDB.setNewGPTarget(performanceVO.getNewGPTarget());
    performanceDB.setYesNewGPTarget(performanceVO.getYesNewGPTarget());
    performanceDB.setYearEndBonus(performanceVO.getYearEndBonus());
    performanceDB.setNewTargetValueLocal(performanceVO.getNewTargetValueLocal());
    performanceDB.setNewTargetValueUSD(performanceVO.getNewTargetValueUSD());
    performanceDB.setNewJobGrade(performanceVO.getNewJobGrade());
    performanceDB.setNewInternalTitle(performanceVO.getNewInternalTitle());
    performanceDB.setNewPositionEN(performanceVO.getNewPositionEN());
    performanceDB.setNewPositionZH(performanceVO.getNewPositionZH());
    performanceDB.setNewShareOptions(performanceVO.getNewShareOptions());
    performanceDB.setTargetBonus(performanceVO.getTargetBonus());
    performanceDB.setProposedBonus(performanceVO.getProposedBonus());
    performanceDB.setProposedPayoutLocal(performanceVO.getProposedPayoutLocal());
    performanceDB.setProposedPayoutUSD(performanceVO.getProposedPayoutUSD());
    performanceDB.setDescription(performanceVO.getDescription());
    performanceDB.setModifyBy(performanceVO.getModifyBy());
    performanceDB.setModifyDate(performanceVO.getModifyDate());
    performanceDB.setRemark2(performanceVO.getRemark2()); // 年薪
    performanceDB.setRemark3(performanceVO.getRemark3()); // 实发年终奖
    performanceDB.setRemark4(performanceVO.getRemark4()); // Target
    return updatePerformance(performanceDB);
  }

  @Override
  public int deletePerformance(final String performanceId) throws KANException {
    return ((PerformanceDao) getDao()).deletePerformance(performanceId);
  }

  @Override
  public int syncPerformance(List<PerformanceVO> performanceVOs) throws KANException {
    int rows = 0;
    try {
      if (performanceVOs != null && performanceVOs.size() > 0) {
        this.startTransaction();
        for (PerformanceVO performanceVO : performanceVOs) {

          rows = rows + ((PerformanceDao) getDao()).insertPerformance(performanceVO);
        }
        this.commitTransaction();
      }
    } catch (Exception e) {
      this.rollbackTransaction();
      throw new KANException(e);
    }

    return rows;
  }

  @Override
  public XSSFWorkbook generatePerformanceReport(PagedListHolder pagedListHolder) throws KANException {
    // 初始化工程目录的模板路径
    final String path = KANUtil.basePath + File.separator + "performanceReportTemplate.xlsx";
    // 创建File
    final File file = new File(path);

    try {
      // 存在模板路径
      if (file.exists()) {
        final PerformanceVO performanceVO = (PerformanceVO) pagedListHolder.getObject();
        final XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        // workbook.setForceFormulaRecalculation( true );

        // 将数据库中的汇率更新在模板上
        setExchangeRate(workbook, performanceVO.getAccountId(), performanceVO.getCorpId());
        setYERRRule(workbook, performanceVO.getAccountId());
        final XSSFSheet sheet = workbook.getSheetAt(0);
        // 选中第一个sheet
        workbook.setSelectedTab(0);

        // 单元格样式靠左
        final XSSFCellStyle leftCellStyle = workbook.createCellStyle();
        leftCellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        leftCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        // 单元格样式居中
        final XSSFCellStyle rightCellStyle = workbook.createCellStyle();
        rightCellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        rightCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        // 单元格样式靠右
        final XSSFCellStyle centerCellStyle = workbook.createCellStyle();
        centerCellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        centerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 日期格式样式
        final XSSFCellStyle dateCellStyle = (XSSFCellStyle) leftCellStyle.clone();
        dateCellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy/MM/dd"));

        final XSSFCellStyle dateCellStyle2 = (XSSFCellStyle) leftCellStyle.clone();
        dateCellStyle2.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));

        // 保留两位小数样式
        final XSSFCellStyle accuracyTwoCellStyle = (XSSFCellStyle) rightCellStyle.clone();
        accuracyTwoCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        // 自定义单元格格式
        final XSSFCellStyle defineCellStyle = (XSSFCellStyle) rightCellStyle.clone();
        defineCellStyle.setDataFormat(workbook.createDataFormat().getFormat("_ * #,##0.00_ ;_ * -#,##0.00_ ;_ * \"\"??_ ;_ @_ "));

        // 保留三位小数样式
        final XSSFCellStyle accuracyThreeCellStyle = (XSSFCellStyle) rightCellStyle.clone();
        accuracyThreeCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.000"));

        // 两位小数百分数样式
        final XSSFCellStyle percentCellStyle = workbook.createCellStyle();
        percentCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));

        // 黄色背景样式
        final XSSFCellStyle yellowCellStyle = (XSSFCellStyle) centerCellStyle.clone();
        yellowCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        yellowCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        // 红色背景样式
        final XSSFCellStyle redCellStyle = (XSSFCellStyle) accuracyTwoCellStyle.clone();
        redCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        final XSSFCellStyle currencyCellStyle = (XSSFCellStyle) accuracyTwoCellStyle.clone();
        XSSFDataFormat currencyFomat = workbook.createDataFormat();
        currencyCellStyle.setDataFormat(currencyFomat.getFormat("[$$]#,##0.00_);([$$]#,##0.00)"));

        double budget_ttc = 0;
        double budget_bonus = 0;
        if (KANUtil.filterEmpty(performanceVO.getSelectBUFunctionName()) != null) {
          String buFunctionId = KANConstants.getKANAccountConstants(performanceVO.getAccountId()).getBUFunctionIdByBranchName(performanceVO.getSelectBUFunctionName());
          if (buFunctionId != null) {
            final Map<String, Object> mapParameter = new HashMap<String, Object>();
            mapParameter.put("accountId", performanceVO.getAccountId());
            mapParameter.put("corpId", performanceVO.getCorpId());
            mapParameter.put("year", performanceVO.getYearly());
            mapParameter.put("parentBranchId", buFunctionId);

            BudgetSettingDetailVO budgetSettingDetailVO = budgetSettingDetailDao.matchBudgetSettingDetailVOByMapParameter(mapParameter);
            if (budgetSettingDetailVO != null) {
              budget_ttc = Double.valueOf(budgetSettingDetailVO.getTtc());
              budget_bonus = Double.valueOf(budgetSettingDetailVO.getBonus());
            }
          }
        }

        // Excel模板对应预算TTC & Bonus
        // 货币格式
        final XSSFCellStyle budget_style = workbook.createCellStyle();
        budget_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
        budget_style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        budget_style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        budget_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        budget_style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        budget_style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

        final XSSFFont budget_font = workbook.createFont();
        budget_font.setFontName("Arial");
        budget_style.setFont(budget_font);

        final XSSFFont currency_font = workbook.createFont();
        currency_font.setFontHeightInPoints((short) 10);
        currency_font.setFontName("Arial");
        currencyCellStyle.setFont(currency_font);

        NumberFormat nf = new DecimalFormat("$,###.00");

        final XSSFRow budgetRow = sheet.getRow(2);
        XSSFCell ttc_budgetCell = budgetRow.createCell(58);
        ttc_budgetCell.setCellValue(nf.format(budget_ttc));
        ttc_budgetCell.setCellStyle(budget_style);

        XSSFCell bonus_budgetCell = budgetRow.createCell(60);
        bonus_budgetCell.setCellValue(nf.format(budget_bonus));
        bonus_budgetCell.setCellStyle(budget_style);

        // 模板中的下拉框
        final String[] yesOrNoArray = new String[] {"Y", "N"};
        final String[] ratingArray = new String[] {"5", "4.5", "4", "3.5", "3", "2.5", "2", "1"};
        final String[] promotionTypeArray = new String[] {"P", "R", "N"};
        // 下拉框引用源
        final String jobGardeFormula = "Job_Grade";

        if (pagedListHolder != null && pagedListHolder.getSource().size() > 0) {
          for (int i = 0; i < pagedListHolder.getSource().size(); i++) {
            final PerformanceVO tempPerformanceVO = (PerformanceVO) pagedListHolder.getSource().get(i);

            final XSSFRow tempRow = sheet.createRow(FIRST_ROW_INDEX + i);

            int columnIndex = 0;

            final XSSFCell employeeIdCell = tempRow.createCell(columnIndex);
            employeeIdCell.setCellValue(tempPerformanceVO.getEmployeeId());
            employeeIdCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell yearlyCell = tempRow.createCell(columnIndex);
            yearlyCell.setCellValue(tempPerformanceVO.getYearly());
            yearlyCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell fullNameCell = tempRow.createCell(columnIndex);
            fullNameCell.setCellValue(tempPerformanceVO.getFullName());
            fullNameCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell shortNameCell = tempRow.createCell(columnIndex);
            shortNameCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getShortName()));
            shortNameCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell chineseNameCell = tempRow.createCell(columnIndex);
            chineseNameCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getChineseName()));
            chineseNameCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell entityNameENCell = tempRow.createCell(columnIndex);
            entityNameENCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getEmploymentEntityEN()));
            entityNameENCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell entityNameZHCell = tempRow.createCell(columnIndex);
            entityNameZHCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getEmploymentEntityZH()));
            entityNameZHCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell entityTitleCell = tempRow.createCell(columnIndex);
            entityTitleCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getCompanyInitial()));
            entityTitleCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell bUFunctionENCell = tempRow.createCell(columnIndex);
            bUFunctionENCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getBuFunctionEN()));
            bUFunctionENCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell bUFunctionZHCell = tempRow.createCell(columnIndex);
            bUFunctionZHCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getBuFunctionZH()));
            bUFunctionZHCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell branchNameENCell = tempRow.createCell(columnIndex);
            branchNameENCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getDepartmentEN()));
            branchNameENCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell branchNameZHCell = tempRow.createCell(columnIndex);
            branchNameZHCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getDepartmentZH()));
            branchNameZHCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell costCenterCell = tempRow.createCell(columnIndex);
            costCenterCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getCostCenter()));
            costCenterCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell functionCodeCell = tempRow.createCell(columnIndex);
            functionCodeCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getFunctionCode()));
            functionCodeCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell bangongdidianCell = tempRow.createCell(columnIndex);
            bangongdidianCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getLocation()));
            bangongdidianCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell jobroleCell = tempRow.createCell(columnIndex);
            jobroleCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getJobRole()));
            jobroleCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell zhiweimingchengyingwenCell = tempRow.createCell(columnIndex);
            zhiweimingchengyingwenCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getPositionEN()));
            zhiweimingchengyingwenCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell neibuchengweiCell = tempRow.createCell(columnIndex);
            neibuchengweiCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getPositionZH()));
            neibuchengweiCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell jobGradeCell = tempRow.createCell(columnIndex);
            jobGradeCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getJobGrade()));
            jobGradeCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell innerTitleCell = tempRow.createCell(columnIndex);
            innerTitleCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getInternalTitle()));
            innerTitleCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell yewuhuibaoxianjingliCell = tempRow.createCell(columnIndex);
            yewuhuibaoxianjingliCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getLineBizManager()));
            yewuhuibaoxianjingliCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell lineManagerCell = tempRow.createCell(columnIndex);
            lineManagerCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getLineHRManager()));
            lineManagerCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell startWorkDateCell = tempRow.createCell(columnIndex);
            startWorkDateCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getSeniorityDate()));
            startWorkDateCell.setCellStyle(dateCellStyle);

            columnIndex++;
            final XSSFCell contractStartDateCell = tempRow.createCell(columnIndex);
            contractStartDateCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getEmploymentDate()));
            contractStartDateCell.setCellStyle(dateCellStyle);

            columnIndex++;
            final XSSFCell gupiaoshuliangCell = tempRow.createCell(columnIndex);
            gupiaoshuliangCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getShareOptions()));
            gupiaoshuliangCell.setCellStyle(rightCellStyle);

            columnIndex++;
            final XSSFCell performanceRating2013Cell = tempRow.createCell(columnIndex);
            performanceRating2013Cell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getLastYearPerformanceRating()));
            performanceRating2013Cell.setCellStyle(rightCellStyle);

            columnIndex++;
            final XSSFCell promotionYERR2013Cell = tempRow.createCell(columnIndex);
            promotionYERR2013Cell.setCellValue(tempPerformanceVO.getLastYearPerformancePromotion());
            promotionYERR2013Cell.setCellStyle(rightCellStyle);
            POIUtil.addValidationData(workbook, yesOrNoArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "");

            columnIndex++;
            final XSSFCell promotionReviewMidYear2014Cell = tempRow.createCell(columnIndex);
            promotionReviewMidYear2014Cell.setCellValue(tempPerformanceVO.getMidYearPromotion());
            promotionReviewMidYear2014Cell.setCellStyle(rightCellStyle);
            POIUtil.addValidationData(workbook, yesOrNoArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "");

            columnIndex++;
            final XSSFCell salaryIncreaseReviewMidYear2014 = tempRow.createCell(columnIndex);
            salaryIncreaseReviewMidYear2014.setCellValue(tempPerformanceVO.getMidYearSalaryIncrease());
            salaryIncreaseReviewMidYear2014.setCellStyle(percentCellStyle);

            columnIndex++;
            final XSSFCell bizhongCell = tempRow.createCell(columnIndex);
            bizhongCell.setCellValue(tempPerformanceVO.getCurrencyCode());
            bizhongCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell baseMonthlySalaryLocalCell = tempRow.createCell(columnIndex);
            baseMonthlySalaryLocalCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getBaseSalaryLocal()));
            baseMonthlySalaryLocalCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell baseMonthlySalaryUSDCell = tempRow.createCell(columnIndex);
            baseMonthlySalaryUSDCell.setCellValue(tempPerformanceVO.getBaseSalaryUSD());
            baseMonthlySalaryUSDCell.setCellStyle(accuracyTwoCellStyle);
            baseMonthlySalaryUSDCell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
            baseMonthlySalaryUSDCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell annualSalaryLocalCell = tempRow.createCell(columnIndex);
            annualSalaryLocalCell.setCellValue(tempPerformanceVO.getAnnualBaseSalaryLocal());
            annualSalaryLocalCell.setCellStyle(accuracyTwoCellStyle);
            annualSalaryLocalCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell annualSalaryUSDCell = tempRow.createCell(columnIndex);
            annualSalaryUSDCell.setCellValue(tempPerformanceVO.getAnnualBaseSalaryUSD());
            annualSalaryUSDCell.setCellStyle(accuracyTwoCellStyle);
            annualSalaryUSDCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell housingAllowanceCell = tempRow.createCell(columnIndex);
            housingAllowanceCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getHousingAllowanceLocal()));
            housingAllowanceCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell childenEducationAllowanceCell = tempRow.createCell(columnIndex);
            childenEducationAllowanceCell.setCellValue(KANUtil.filterEmpty(tempPerformanceVO.getChildrenEduAllowanceLocal()));
            childenEducationAllowanceCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell annualGuaranteedCashLocalCell = tempRow.createCell(columnIndex);
            annualGuaranteedCashLocalCell.setCellValue(tempPerformanceVO.getGuaranteedCashLocal());
            annualGuaranteedCashLocalCell.setCellStyle(accuracyTwoCellStyle);
            annualGuaranteedCashLocalCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell annualGuaranteedCashUSDCell = tempRow.createCell(columnIndex);
            annualGuaranteedCashUSDCell.setCellValue(tempPerformanceVO.getGuaranteedCashUSD());
            annualGuaranteedCashUSDCell.setCellStyle(accuracyTwoCellStyle);
            annualGuaranteedCashUSDCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            // Column Index AM(月度奖金)
            columnIndex++;
            final XSSFCell monthlyTargetCell = tempRow.createCell(columnIndex);
            monthlyTargetCell.setCellValue(tempPerformanceVO.getMonthlyTarget());
            monthlyTargetCell.setCellStyle(accuracyTwoCellStyle);

            // Column Index AN(季度奖金)
            columnIndex++;
            final XSSFCell quarterlyTargetCell = tempRow.createCell(columnIndex);
            quarterlyTargetCell.setCellValue(tempPerformanceVO.getQuarterlyTarget());
            quarterlyTargetCell.setCellStyle(accuracyTwoCellStyle);

            // Column Index AN(销售提成)
            columnIndex++;
            final XSSFCell gpTargetCell = tempRow.createCell(columnIndex);
            gpTargetCell.setCellValue(1 == tempPerformanceVO.getYesGPTarget() ? "YES" : tempPerformanceVO.getGpTarget());
            gpTargetCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell targetValueLocalCell = tempRow.createCell(columnIndex);
            targetValueLocalCell.setCellValue(tempPerformanceVO.getTargetValueLocal());
            targetValueLocalCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell targetValueUSDCell = tempRow.createCell(columnIndex);
            targetValueUSDCell.setCellValue(tempPerformanceVO.getTargetValueUSD());
            targetValueUSDCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell ttcLocalCell = tempRow.createCell(columnIndex);
            ttcLocalCell.setCellValue(tempPerformanceVO.getTtcLocal());
            ttcLocalCell.setCellStyle(accuracyTwoCellStyle);
            ttcLocalCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell ttcUSDCell = tempRow.createCell(columnIndex);
            ttcUSDCell.setCellValue(tempPerformanceVO.getTtcUSD());
            ttcUSDCell.setCellStyle(accuracyTwoCellStyle);
            ttcUSDCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            // Column Index AT
            columnIndex++;
            final XSSFCell yearPerformanceRatingCell = tempRow.createCell(columnIndex);
            if (KANUtil.filterEmpty(tempPerformanceVO.getYearPerformanceRating()) != null) {
              try{
                yearPerformanceRatingCell.setCellValue(Double.parseDouble(tempPerformanceVO.getYearPerformanceRating()));
              }catch(Exception e){
                yearPerformanceRatingCell.setCellValue("");
              }
            } else {
              yearPerformanceRatingCell.setCellValue("");
            }
            yearPerformanceRatingCell.setCellStyle(yellowCellStyle);
            POIUtil.addValidationData(workbook, ratingArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "");

            // Column Index AU
            columnIndex++;
            final XSSFCell yearPerformancePromotionCell = tempRow.createCell(columnIndex);
            yearPerformancePromotionCell.setCellValue(tempPerformanceVO.getYearPerformancePromotion());
            yearPerformancePromotionCell.setCellStyle(yellowCellStyle);
            POIUtil.addValidationData(workbook, promotionTypeArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "");

            // Column Index AV
            columnIndex++;
            final XSSFCell recommendTTCIncreaseCell = tempRow.createCell(columnIndex);
            // recommendTTCIncreaseCell.setCellValue(tempPerformanceVO.getRecommendTTCIncrease());
            recommendTTCIncreaseCell.setCellStyle(accuracyThreeCellStyle);
            recommendTTCIncreaseCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            // Column Index AW
            columnIndex++;
            final XSSFCell ttcIncreaseCell = tempRow.createCell(columnIndex);
            ttcIncreaseCell.setCellValue(tempPerformanceVO.getTtcIncrease());
            ttcIncreaseCell.setCellStyle(redCellStyle);

            // Column Index AX
            columnIndex++;
            final XSSFCell newTTCLocalCell = tempRow.createCell(columnIndex);
            newTTCLocalCell.setCellValue(tempPerformanceVO.getNewTTCLocal());
            newTTCLocalCell.setCellStyle(defineCellStyle);
            newTTCLocalCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newTTCUSDCell = tempRow.createCell(columnIndex);
            newTTCUSDCell.setCellValue(tempPerformanceVO.getNewTTCUSD());
            newTTCUSDCell.setCellStyle(defineCellStyle);
            newTTCUSDCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newBaseSalaryLocalCell = tempRow.createCell(columnIndex);
            newBaseSalaryLocalCell.setCellValue(tempPerformanceVO.getNewBaseSalaryLocal());
            newBaseSalaryLocalCell.setCellStyle(defineCellStyle);
            newBaseSalaryLocalCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newBaseSalaryUSDCell = tempRow.createCell(columnIndex);
            newBaseSalaryUSDCell.setCellValue(tempPerformanceVO.getNewBaseSalaryUSD());
            newBaseSalaryUSDCell.setCellStyle(defineCellStyle);
            newBaseSalaryUSDCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newAnnualSalaryLocalCell = tempRow.createCell(columnIndex);
            newAnnualSalaryLocalCell.setCellValue(tempPerformanceVO.getNewAnnualSalaryLocal());
            newAnnualSalaryLocalCell.setCellStyle(defineCellStyle);
            newAnnualSalaryLocalCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newAnnualSalaryUSDCell = tempRow.createCell(columnIndex);
            newAnnualSalaryUSDCell.setCellValue(tempPerformanceVO.getNewAnnualSalaryUSD());
            newAnnualSalaryUSDCell.setCellStyle(defineCellStyle);
            newAnnualSalaryUSDCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newAnnualHousingAllowanceCell = tempRow.createCell(columnIndex);
            newAnnualHousingAllowanceCell.setCellValue(tempPerformanceVO.getNewAnnualHousingAllowance());
            newAnnualHousingAllowanceCell.setCellStyle(defineCellStyle);
            newAnnualHousingAllowanceCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newAnnualChildrenEduAllowanceCell = tempRow.createCell(columnIndex);
            newAnnualChildrenEduAllowanceCell.setCellValue(tempPerformanceVO.getNewAnnualChildrenEduAllowance());
            newAnnualChildrenEduAllowanceCell.setCellStyle(defineCellStyle);
            newAnnualChildrenEduAllowanceCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newAnnualGuaranteedAllowanceLocalCell = tempRow.createCell(columnIndex);
            newAnnualGuaranteedAllowanceLocalCell.setCellValue(tempPerformanceVO.getNewAnnualGuaranteedAllowanceLocal());
            newAnnualGuaranteedAllowanceLocalCell.setCellStyle(defineCellStyle);
            newAnnualGuaranteedAllowanceLocalCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newAnnualGuatanteedAllowanceUSDCell = tempRow.createCell(columnIndex);
            newAnnualGuatanteedAllowanceUSDCell.setCellValue(tempPerformanceVO.getNewAnnualGuatanteedAllowanceUSD());
            newAnnualGuatanteedAllowanceUSDCell.setCellStyle(defineCellStyle);
            newAnnualGuatanteedAllowanceUSDCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newMonthlyTargetCell = tempRow.createCell(columnIndex);
            newMonthlyTargetCell.setCellValue(tempPerformanceVO.getNewMonthlyTarget());
            newMonthlyTargetCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell newQuarterlyTargetCell = tempRow.createCell(columnIndex);
            newQuarterlyTargetCell.setCellValue(tempPerformanceVO.getNewQuarterlyTarget());
            newQuarterlyTargetCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell newGPTargetCell = tempRow.createCell(columnIndex);
            newGPTargetCell.setCellValue(1 == tempPerformanceVO.getYesNewGPTarget() ? "YES" : tempPerformanceVO.getNewGPTarget());
            newGPTargetCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell newTargetValueLocalCell = tempRow.createCell(columnIndex);
            newTargetValueLocalCell.setCellValue(tempPerformanceVO.getNewTargetValueLocal());
            newTargetValueLocalCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell newTargetValueUSDCell = tempRow.createCell(columnIndex);
            newTargetValueUSDCell.setCellValue(tempPerformanceVO.getNewTargetValueUSD());
            newTargetValueUSDCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            final XSSFCell newJobGradeCell = tempRow.createCell(columnIndex);
            newJobGradeCell.setCellValue(tempPerformanceVO.getNewJobGrade());
            newJobGradeCell.setCellStyle(yellowCellStyle);
            POIUtil.addValidationData(workbook, yesOrNoArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, jobGardeFormula);

            columnIndex++;
            final XSSFCell newInternalTitleCell = tempRow.createCell(columnIndex);
            newInternalTitleCell.setCellValue(tempPerformanceVO.getNewInternalTitle());
            newInternalTitleCell.setCellStyle(centerCellStyle);
            newInternalTitleCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell newPositionENCell = tempRow.createCell(columnIndex);
            newPositionENCell.setCellValue(tempPerformanceVO.getNewPositionEN());
            newPositionENCell.setCellStyle(yellowCellStyle);

            columnIndex++;
            final XSSFCell newPositionZHCell = tempRow.createCell(columnIndex);
            newPositionZHCell.setCellValue(tempPerformanceVO.getNewPositionZH());
            newPositionZHCell.setCellStyle(yellowCellStyle);

            columnIndex++;
            final XSSFCell newShareOptionsCell = tempRow.createCell(columnIndex);
            newShareOptionsCell.setCellValue(tempPerformanceVO.getNewShareOptions());
            newShareOptionsCell.setCellStyle(yellowCellStyle);
            POIUtil.addValidationData(workbook, yesOrNoArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "");

            // BR
            columnIndex++;
            final XSSFCell targetBonusCell = tempRow.createCell(columnIndex);
            targetBonusCell.setCellValue(tempPerformanceVO.getTargetBonus());
            targetBonusCell.setCellStyle(accuracyTwoCellStyle);
            targetBonusCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            // BS
            columnIndex++;
            final XSSFCell proposedBonusCell = tempRow.createCell(columnIndex);
            proposedBonusCell.setCellValue(tempPerformanceVO.getProposedBonus());
            proposedBonusCell.setCellStyle(redCellStyle);

            // BT
            columnIndex++;
            final XSSFCell btCell = tempRow.createCell(columnIndex);
            btCell.setCellStyle(accuracyTwoCellStyle);
            btCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            // BU
            columnIndex++;
            final XSSFCell proposedPayoutLocalCell = tempRow.createCell(columnIndex);
            proposedPayoutLocalCell.setCellValue(tempPerformanceVO.getProposedPayoutLocal());
            proposedPayoutLocalCell.setCellStyle(redCellStyle);

            // BV
            columnIndex++;
            final XSSFCell proposedPayoutUSDCell = tempRow.createCell(columnIndex);
            proposedPayoutUSDCell.setCellValue(tempPerformanceVO.getProposedPayoutUSD());
            proposedPayoutUSDCell.setCellStyle(defineCellStyle);
            proposedPayoutUSDCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            columnIndex++;
            final XSSFCell descriptionCell = tempRow.createCell(columnIndex);
            descriptionCell.setCellValue(tempPerformanceVO.getDescription());
            descriptionCell.setCellStyle(yellowCellStyle);

            columnIndex++;
            final XSSFCell updateByCell = tempRow.createCell(columnIndex);
            updateByCell.setCellValue(tempPerformanceVO.getDecodeModifyBy());
            updateByCell.setCellStyle(leftCellStyle);

            columnIndex++;
            final XSSFCell updateDateCell = tempRow.createCell(columnIndex);
            updateDateCell.setCellValue(tempPerformanceVO.getDecodeModifyDate());
            updateDateCell.setCellStyle(leftCellStyle);

            // BZ
            columnIndex++;
            final XSSFCell BYCell = tempRow.createCell(columnIndex);
            BYCell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            // CA
            columnIndex++;
            final XSSFCell CACell = tempRow.createCell(columnIndex);
            CACell.setCellFormula(getFormula(columnIndex, FIRST_ROW_INDEX + i));

            // CB 年终奖
            columnIndex++;
            final XSSFCell CBCell = tempRow.createCell(columnIndex);
            /*
             * List<Object> contracts =
             * employeeContractDao.getEmployeeContractVOsByEmployeeId(tempPerformanceVO.
             * getEmployeeId()); String contractId = ""; if (contracts != null && contracts.size() >
             * 0) { for (Object o : contracts) { if ("3".equalsIgnoreCase(((EmployeeContractVO)
             * o).getStatus())) { contractId = ((EmployeeContractVO) o).getContractId(); break; } }
             * } if (KANUtil.filterEmpty(contractId) != null) { Map<String, Object> map = new
             * HashMap<String, Object>(); map.put("contractId", contractId); map.put("itemId",
             * "18"); List<Object> salarys =
             * employeeContractSalaryDao.getEmployeeContractSalaryVOsByContractIdAndItemId(map); if
             * (salarys != null && salarys.size() > 0) { EmployeeContractSalaryVO targetSalary =
             * null; for (Object s : salarys) { EmployeeContractSalaryVO salary =
             * (EmployeeContractSalaryVO) s; if (KANUtil.filterEmpty(salary.getEndDate()) == null ||
             * (KANUtil.filterEmpty(salary.getEndDate()) != null &&
             * KANUtil.getDays(KANUtil.createDate(salary.getEndDate())) > KANUtil.getDays(new
             * Date()))) { targetSalary = salary; break; } } if (targetSalary != null) {
             * CBCell.setCellValue(targetSalary.getBase()); } else { CBCell.setCellValue(""); } }
             * else { CBCell.setCellValue(""); } }
             */
            CBCell.setCellValue(tempPerformanceVO.getCurrentYearEndBonus());
            CBCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            // 年薪 CC
            final XSSFCell annualRemunerationCell = tempRow.createCell(columnIndex);
            annualRemunerationCell.setCellValue(tempPerformanceVO.getRemark2());
            annualRemunerationCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            // 实发年终奖 CD
            final XSSFCell actualYearEndBonusCell = tempRow.createCell(columnIndex);
            actualYearEndBonusCell.setCellValue(tempPerformanceVO.getRemark3());
            actualYearEndBonusCell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            // 香港的 CD
            final XSSFCell remark4Cell = tempRow.createCell(columnIndex);
            remark4Cell.setCellValue(tempPerformanceVO.getRemark4());
            remark4Cell.setCellStyle(accuracyTwoCellStyle);

            columnIndex++;
            // CF
            final XSSFCell CFCell = tempRow.createCell(columnIndex);
            CFCell.setCellValue(KANUtil.createDate(tempPerformanceVO.getYearly(), "12", "31"));
            CFCell.setCellStyle(dateCellStyle2);

          }
        }

        final XSSFRow headRow = sheet.getRow(3);
        XSSFCell ttcSumCell = headRow.getCell(58);
        ttcSumCell.setCellFormula("SUM(BZ14:BZ" + (FIRST_ROW_INDEX + pagedListHolder.getSource().size() + 1) + ")");
        ttcSumCell.setCellStyle(currencyCellStyle);

        XSSFCell bounsSumCell = headRow.getCell(60);
        bounsSumCell.setCellFormula("SUM(BV14:BV" + (FIRST_ROW_INDEX + pagedListHolder.getSource().size() + 1) + ")");
        bounsSumCell.setCellStyle(currencyCellStyle);

        // COUNTIF(AT14:AT,AY3)
        for (int i = 0; i < 8; i++) {
          final XSSFRow tmpRow = sheet.getRow(2 + i);
          XSSFCell azSumCell = tmpRow.getCell(51);
          azSumCell.setCellFormula("COUNTIF(AT14:AT" + (FIRST_ROW_INDEX + pagedListHolder.getSource().size() + 1) + ",AY" + (3 + i) + ")");
        }

        return workbook;
      }
    } catch (Exception e) {
      throw new KANException(e);
    }

    return null;
  }

  // 模板设置YERR Rule sheet
  private void setYERRRule(final XSSFWorkbook workbook, final String accountId) throws KANException {
    List<Object> YERRRuleVOs = this.getYerrRuleDao().getYERRRuleVOsByAccountId(accountId);

    if (YERRRuleVOs != null && YERRRuleVOs.size() > 0) {
      final XSSFSheet exchangeRateSheet = workbook.getSheetAt(5);

      for (int i = 0; i < YERRRuleVOs.size(); i++) {
        YERRRuleVO yerrRuleVO = (YERRRuleVO) YERRRuleVOs.get(i);

        XSSFRow baseRow = null;
        switch (yerrRuleVO.getRating()) {
          case "1":
            baseRow = exchangeRateSheet.getRow(6);
            break;
          case "2":
            baseRow = exchangeRateSheet.getRow(7);
            break;
          case "2.5":
            baseRow = exchangeRateSheet.getRow(7);
            break;
          case "3":
            baseRow = exchangeRateSheet.getRow(8);
            break;
          case "3.5":
            baseRow = exchangeRateSheet.getRow(8);
            break;
          case "4":
            baseRow = exchangeRateSheet.getRow(9);
            break;
          case "4.5":
            baseRow = exchangeRateSheet.getRow(9);
            break;
          case "5":
            baseRow = exchangeRateSheet.getRow(10);
            break;
        }

        if (baseRow != null) {
          XSSFCell cell1 = baseRow.getCell(3);
          XSSFCell cell2 = baseRow.getCell(4);
          XSSFCell cell3 = baseRow.getCell(5);
          XSSFCell cell4 = baseRow.getCell(7);
          XSSFCell cell5 = baseRow.getCell(8);
          XSSFCell cell6 = baseRow.getCell(9);
          XSSFCell cell7 = baseRow.getCell(10);

          if (cell1 != null)
            cell1.setCellValue(yerrRuleVO.getMeritRateRMB());
          if (cell2 != null)
            cell2.setCellValue(yerrRuleVO.getMeritRateHKD());
          if (cell3 != null)
            cell3.setCellValue(yerrRuleVO.getMeritRateSGD());
          if (cell4 != null)
            cell4.setCellValue(yerrRuleVO.getMeritAndPromotionRateRMB());
          if (cell5 != null)
            cell5.setCellValue(yerrRuleVO.getMeritAndPromotionRateHKD());
          if (cell6 != null)
            cell6.setCellValue(yerrRuleVO.getMeritAndPromotionRateSGD());
          if (cell7 != null)
            cell7.setCellValue(yerrRuleVO.getBounsRate());
        }
      }

      // 设置隐藏
      workbook.setSheetHidden(5, true);
    }
  }

  // 模板设置汇率sheet
  private void setExchangeRate(final XSSFWorkbook workbook, final String accountId, final String cropId) {
    // 获取缓存中汇率
    final List<ExchangeRateVO> exchangeRateVOs = KANConstants.getKANAccountConstants(accountId).EXCHANGE_RATE_VO;

    if (exchangeRateVOs != null && exchangeRateVOs.size() > 0) {
      // 普通字体
      final XSSFFont font = workbook.createFont();
      font.setFontName("Arial Unicode MS");
      font.setFontHeightInPoints((short) 10);

      // 蓝色字体
      final XSSFFont blueFont = workbook.createFont();
      blueFont.setFontName("Arial Unicode MS");
      blueFont.setFontHeightInPoints((short) 10);
      blueFont.setColor(HSSFColor.SKY_BLUE.index);

      // 普通样式
      final XSSFCellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
      cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
      cellStyle.setFont(font);

      // 蓝色字体
      final XSSFCellStyle blueCellStyle = (XSSFCellStyle) cellStyle.clone();
      blueCellStyle.setFont(blueFont);

      // 保留四位小数
      final XSSFCellStyle fourCellStyle = (XSSFCellStyle) cellStyle.clone();
      fourCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000"));

      // 获取模板sheet Exchange Rate
      final XSSFSheet exchangeRateSheet = workbook.getSheetAt(4);
      exchangeRateSheet.createRow(0);

      final XSSFRow titleRow = exchangeRateSheet.createRow(1);
      final XSSFCell titleCell1 = titleRow.createCell(0);
      titleCell1.setCellStyle(cellStyle);
      final XSSFCell titleCell2 = titleRow.createCell(1);
      titleCell2.setCellValue("Local/USD");
      titleCell2.setCellStyle(cellStyle);

      for (int i = 0; i < exchangeRateVOs.size(); i++) {
        final ExchangeRateVO tempExchangeRateVO = exchangeRateVOs.get(i);
        final XSSFRow tempRow = exchangeRateSheet.createRow(i + 2);

        final XSSFCell cell1 = tempRow.createCell(0);
        cell1.setCellValue(tempExchangeRateVO.getCurrencyCode());
        cell1.setCellStyle(blueCellStyle);

        final XSSFCell cell2 = tempRow.createCell(1);
        cell2.setCellFormula(tempExchangeRateVO.getFromUSD() + "/" + tempExchangeRateVO.getToLocal());
        cell2.setCellStyle(fourCellStyle);
      }

      // 设置隐藏
      workbook.setSheetHidden(4, true);
    }
  }

  private static String getFormula(final int columnIndex, final int rowIndex) {
    String rut = null;
    final String key = KANUtil.getExcelColumnName(columnIndex);
    final String formula = FORMULA_MAP.get(key);

    if (KANUtil.filterEmpty(formula) != null) {
      rut = formula.replaceAll("00", String.valueOf(rowIndex + 1));
    }

    return rut;
  }

  /***
   * 创建一个自定义字段：用以储存当前前绩效分数
   * 
   * @param year
   * @throws KANException
   */
  private boolean createDefineColumnPerformanceRating(String year) throws KANException {
    ColumnVO searchColumnVO = new ColumnVO();
    searchColumnVO.setAccountId(KANConstants.DEFAULT_ACCOUNTID);
    searchColumnVO.setTableId("71");
    searchColumnVO.setCorpId(KANConstants.DEFAULT_CORPID);
    searchColumnVO.setNameDB(COLUMN_NAME_DB_PREFIX + year);
    searchColumnVO.setGroupId(PERFORMANCE_DEFINE_GROUP_ID);

    List<Object> columnVOs = columnDao.getAccountColumnVOsByCondition(searchColumnVO);
    if (columnVOs == null || columnVOs.size() == 0) {
      final ColumnVO newColumnVO = new ColumnVO();
      newColumnVO.setAccountId(KANConstants.DEFAULT_ACCOUNTID);
      newColumnVO.setCorpId(KANConstants.DEFAULT_CORPID);
      newColumnVO.setTableId("71");
      newColumnVO.setNameDB(COLUMN_NAME_DB_PREFIX + year);
      newColumnVO.setNameSys(year + "绩效等级");
      newColumnVO.setNameZH(year + "绩效等级");
      newColumnVO.setNameEN(year + " Performance Evaluation");
      newColumnVO.setGroupId(PERFORMANCE_DEFINE_GROUP_ID);
      newColumnVO.setIsPrimaryKey("0");
      newColumnVO.setIsForeignKey("0");
      newColumnVO.setIsDBColumn("1");
      newColumnVO.setIsRequired("2");
      newColumnVO.setDisplayType("1");
      newColumnVO.setColumnIndex("100");
      newColumnVO.setInputType("1");
      newColumnVO.setValueType("1");
      newColumnVO.setOptionType("1");
      newColumnVO.setEditable("1");
      newColumnVO.setUseThinking("2");
      newColumnVO.setUseTitle("0");
      newColumnVO.setDescription("Performance confirm generate");
      newColumnVO.setCanImport("1");
      newColumnVO.setDeleted("1");
      newColumnVO.setStatus("1");
      newColumnVO.setCreateBy("1");
      newColumnVO.setCreateDate(new Date());
      newColumnVO.setModifyBy("1");
      newColumnVO.setModifyDate(newColumnVO.getCreateDate());
      columnDao.insertColumn(newColumnVO);

      return true;
    }

    return false;
  }

  /***
   * 确认绩效信息（既开始异动调薪）
   * 
   * @param performanceVO
   * @return success rows
   * @throws KANException
   */
  @SuppressWarnings("unused")
  @Override
  public int confirmPerformanceInfo(PerformanceVO performanceVO, HttpServletRequest request) throws KANException {
    // 创建一个自定义字段用存储绩效
    if (createDefineColumnPerformanceRating(performanceVO.getYearly())) {
      try {
        BaseAction.constantsInit("initTable", performanceVO.getAccountId());
      } catch (MalformedURLException | RemoteException | NotBoundException e) {
      }
    }

    int positionChangeSuccessRows = 0;
    try {
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants(performanceVO.getAccountId());
      final List<Object> performanceVOs = ((PerformanceDao) getDao()).getPerformanceVOsByCondition(performanceVO);

      // 开启事务
      startTransaction();

      if (performanceVOs != null && performanceVOs.size() > 0) {
        for (Object performanceVOObject : performanceVOs) {
          final PerformanceVO performanceDB = (PerformanceVO) performanceVOObject;

          // 获取有效EmployeeContractVO
          final EmployeeContractVO validEmployeeContractVO = getValidEmployeeContractVOByEmployeeId(performanceDB.getEmployeeId());

          if (validEmployeeContractVO == null)
            continue;

          performanceDB.setLocale(performanceVO.getLocale());
          performanceDB.setIp(performanceVO.getIp());
          performanceDB.setModifyBy(performanceVO.getModifyBy());
          performanceDB.setModifyDate(new Date());

          // 绩效信息的状态：1新建;2调薪;4升职;6调薪+升职
          int status = 0;
          // 加薪
          boolean changeSalary = needSalaryAdjustment(performanceDB, validEmployeeContractVO.getContractId()) == 1;
          // 升职
          boolean changePosition = false;
          // needPositionChange( accountConstants, performanceDB ) == 1;
          // 劳动合同信息修改
          validEmployeeContractVO.reset(null, request);
          needEmployeeContractChange(performanceDB, validEmployeeContractVO, changeSalary, changePosition);

          if (changeSalary) {
            status += 2;
          }

          if (changePosition) {
            status += 4;
          }

          if (status != 0) {
            performanceDB.setStatus(String.valueOf(status));
            updatePerformance(performanceDB);
            positionChangeSuccessRows++;
          }
        }
      }

      this.commitTransaction();
    } catch (Exception e) {
      this.rollbackTransaction();
      throw new KANException(e);
    }

    return positionChangeSuccessRows;
  }

  /***
   * 调薪发邮件通知
   * 
   * @param performanceDB
   * @throws KANException
   */
  private int sendMailNotice(PerformanceVO performanceDB, String sendNoticeTemplateId) throws KANException {
    if (KANUtil.filterEmpty(performanceDB.getRemark5()) == null)
      return 0;

    try {
      double oldBaseSalary = Double.valueOf(performanceDB.getBaseSalaryLocal());
      double newBaseSalary = Double.valueOf(performanceDB.getNewBaseSalaryLocal());
      if (oldBaseSalary == newBaseSalary)
        return 0;
    } catch (NumberFormatException nfe) {
      System.err.println("****调薪通知邮件出错>>>>工资金额转换异常");
      return 0;
    }

    // 格式化货币
    final DecimalFormat df = new DecimalFormat("#,##0.00");
    // 邮件模板需要替换的参数
    final Map<String, Object> parameters = new HashMap<String, Object>();
    // 邮件模板初始化
    final MessageTemplateVO messageTemplateVO = messageTemplateDao.getMessageTemplateVOByTemplateId(sendNoticeTemplateId);

    if (messageTemplateVO != null) {
      parameters.put("thisYear", performanceDB.getYearly());
      parameters.put("nextYear", Integer.parseInt(performanceDB.getYearly()) + 1);
      parameters.put("employeeName", performanceDB.getChineseName());
      if (performanceDB.isNeedPositionChange()) {
        parameters.put("before_positionTitle", performanceDB.getPositionZH());
        parameters.put("after_positionTitle", performanceDB.getNewPositionZH());
        parameters.put("before_jobGrade", performanceDB.getJobGrade());
        parameters.put("after_jobGrade", performanceDB.getNewJobGrade());
      }

      if ("2".equals(performanceDB.getStatus()) || "6".equals(performanceDB.getStatus())) {
        parameters.put("before_baseSalary", df.format(Double.valueOf(performanceDB.getBaseSalaryLocal())));
        parameters.put("after_baseSalary", df.format(Double.valueOf(performanceDB.getNewBaseSalaryLocal())));
        parameters.put("before_annualEndBonus", df.format(Double.valueOf(performanceDB.getCurrentYearEndBonus())));
        parameters.put("after_annualEndBonus", df.format(Double.valueOf(performanceDB.getYearEndBonus())));
      }

      parameters.put("actual_annualEndBonus", df.format(Double.valueOf(performanceDB.formatNumber_2(performanceDB.getRemark3()))));
      parameters.put("entityName", performanceDB.getEmploymentEntityZH());
    }

    if (messageTemplateVO != null) {
      // 初始化MessageInfoVO
      final MessageMailVO messageMailVO = new MessageMailVO();
      messageMailVO.setSystemId(KANConstants.SYSTEM_ID);
      messageMailVO.setAccountId(performanceDB.getAccountId());
      messageMailVO.setCorpId(performanceDB.getCorpId());
      messageMailVO.setTitle(messageTemplateVO.getDescription());
      messageMailVO.setContent(MatchUtil.generateMessageContent(messageTemplateVO.getContent(), parameters));
      messageMailVO.setContentType("2");
      messageMailVO.setStatus("1");
      messageMailVO.setCreateBy("system");
      messageMailVO.setCreateDate(new Date());
      messageMailVO.setModifyBy("system");
      messageMailVO.setModifyDate(new Date());
      messageMailVO.setReception(performanceDB.getRemark5());
      return this.messageMailDao.insertMessageMail(messageMailVO);
    }

    return 0;
  }

  /****
   * 劳动合同异动 1.更改劳动合同 2.插入异动日志
   * 
   * @param performanceDB
   * @param validEmployeeContractVO
   * @param changeAnnualSalary
   * @param changePosition
   * @throws KANException
   */
  private void needEmployeeContractChange(PerformanceVO performanceDB, EmployeeContractVO validEmployeeContractVO, boolean changeAnnualSalary, boolean changePosition) throws KANException {
    JSONObject jsonObject = JSONObject.fromObject(validEmployeeContractVO.getRemark1());
    if (jsonObject == null)
      return;

    boolean changed = false;

    // 更改劳动合同自定义字段：绩效等级
    jsonObject.put(COLUMN_NAME_DB_PREFIX + performanceDB.getYearly(), performanceDB.getYearPerformanceRating());

    // 更改劳动合同自定义字段：年薪
    if (changeAnnualSalary) {
      jsonObject.put("AnnualRemuneration", performanceDB.getRemark2());
      changed = true;
    }

    // 更改劳动合同自定义字段：职务名称
    if (changePosition) {
      jsonObject.put("neibuchengwei", performanceDB.getNewPositionZH());
      jsonObject.put("zhiweimingchengyingwen", performanceDB.getNewPositionEN());
      changed = true;
    }

    if (changed) {
      validEmployeeContractVO.setRemark1(jsonObject.toString());
      validEmployeeContractVO.setRemark3("12");// 异动原因：YERR
      validEmployeeContractVO.setModifyDate(new Date());
      validEmployeeContractVO.setModifyBy(performanceDB.getModifyBy());
      this.employeeContractDao.updateEmployeeContract(validEmployeeContractVO);
      insertLog(validEmployeeContractVO, performanceDB.getIp(), performanceDB.getDecodeModifyBy(), validEmployeeContractVO.getContractId());
    }
  }

  /***
   * 职位异动 1.插入异动记录 2.插入异动日志 3.更新职位信息
   * 
   * @param performanceDB
   * @throws KANException
   */
  @SuppressWarnings("unused")
  private int needPositionChange(final KANAccountConstants accountConstants, final PerformanceVO performanceDB) throws KANException {
    String positionId = null;
    String staffId = null;
    String positionStaffRelationId = null;
    // 一般employeeId和staffId是一对一
    final List<StaffDTO> staffDTOs = accountConstants.getStaffDTOsByEmployeeId(performanceDB.getEmployeeId());
    if (staffDTOs != null && staffDTOs.size() > 0 && staffDTOs.get(0).getStaffVO() != null) {
      staffId = staffDTOs.get(0).getStaffVO().getStaffId();

      final List<PositionStaffRelationVO> positionStaffRelationVOs = staffDTOs.get(0).getPositionStaffRelationVOs();
      // 一个staffId对应多个职位，但主职位只有一个
      if (positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0) {
        for (Object relation : positionStaffRelationVOs) {
          final PositionStaffRelationVO tempPositionStaffRelationVO = (PositionStaffRelationVO) relation;
          if (tempPositionStaffRelationVO.getStaffType().equals("1")) {
            positionId = tempPositionStaffRelationVO.getPositionId();
            positionStaffRelationId = tempPositionStaffRelationVO.getRelationId();
            break;
          }
        }
      }
    }

    if (positionId != null) {
      final PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId(positionId);

      if (positionDTO != null) {
        final PositionVO oldPositionVO = positionDTO.getPositionVO();
        final BranchVO oldBranchVO = accountConstants.getBranchVOByBranchId(positionDTO.getPositionVO().getBranchId());
        final EmployeeVO tempEmployeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId(performanceDB.getEmployeeId());
        final PositionGradeVO newPositionGradeVO = accountConstants.getPositionGradeVOByName(performanceDB.getNewInternalTitle(), performanceDB.getCorpId());
        if (tempEmployeeVO != null && newPositionGradeVO != null) {
          final EmployeePositionChangeVO tempEmployeePositionChangeVO = new EmployeePositionChangeVO();
          tempEmployeePositionChangeVO.setLocale(performanceDB.getLocale());
          tempEmployeePositionChangeVO.setAccountId(tempEmployeeVO.getAccountId());
          tempEmployeePositionChangeVO.setCorpId(tempEmployeeVO.getCorpId());
          tempEmployeePositionChangeVO.setEmployeeId(tempEmployeeVO.getEmployeeId());
          tempEmployeePositionChangeVO.setEmployeeNo(tempEmployeeVO.getEmployeeNo());
          tempEmployeePositionChangeVO.setEmployeeNameZH(tempEmployeeVO.getNameZH());
          tempEmployeePositionChangeVO.setEmployeeNameEN(tempEmployeeVO.getNameEN());
          tempEmployeePositionChangeVO.setEmployeeCertificateNumber(tempEmployeeVO.getCertificateNumber());
          tempEmployeePositionChangeVO.setStaffId(staffId);
          tempEmployeePositionChangeVO.setOldStaffPositionRelationId(positionStaffRelationId);
          tempEmployeePositionChangeVO.setEffectiveDate(KANUtil.formatDate(new Date(), "yyyy-MM-dd"));
          tempEmployeePositionChangeVO.setIsChildChange("2");
          tempEmployeePositionChangeVO.setStatus("3");
          tempEmployeePositionChangeVO.setSubmitFlag(4);
          tempEmployeePositionChangeVO.setDescription("Performance Info Confirm");
          tempEmployeePositionChangeVO.setRemark3("12");
          tempEmployeePositionChangeVO.setCreateBy(performanceDB.getModifyBy());
          tempEmployeePositionChangeVO.setCreateDate(new Date());
          tempEmployeePositionChangeVO.setModifyBy(performanceDB.getModifyBy());
          tempEmployeePositionChangeVO.setModifyDate(new Date());

          // Department old & new
          tempEmployeePositionChangeVO.setOldBranchId(oldBranchVO.getBranchId());
          tempEmployeePositionChangeVO.setNewBranchId(oldBranchVO.getBranchId());
          if (oldBranchVO != null) {
            tempEmployeePositionChangeVO.setOldBranchNameZH(oldBranchVO.getNameZH());
            tempEmployeePositionChangeVO.setOldBranchNameEN(oldBranchVO.getNameEN());
            tempEmployeePositionChangeVO.setNewBranchNameZH(oldBranchVO.getNameZH());
            tempEmployeePositionChangeVO.setNewBranchNameEN(oldBranchVO.getNameEN());

            // BU/Function old & new
            final BranchVO oldParentBranchVO = accountConstants.getBranchVOByBranchId(oldBranchVO.getParentBranchId());
            tempEmployeePositionChangeVO.setOldParentBranchId(oldBranchVO.getParentBranchId());
            tempEmployeePositionChangeVO.setNewParentBranchId(oldBranchVO.getParentBranchId());
            if (oldParentBranchVO != null) {
              tempEmployeePositionChangeVO.setOldParentBranchNameZH(oldParentBranchVO.getNameZH());
              tempEmployeePositionChangeVO.setOldParentBranchNameEN(oldParentBranchVO.getNameEN());
              tempEmployeePositionChangeVO.setNewParentBranchNameZH(oldParentBranchVO.getNameZH());
              tempEmployeePositionChangeVO.setNewParentBranchNameEN(oldParentBranchVO.getNameEN());
            }

            // Working title old & new
            tempEmployeePositionChangeVO.setOldPositionId(oldPositionVO.getPositionId());
            tempEmployeePositionChangeVO.setOldPositionNameZH(oldPositionVO.getTitleZH());
            tempEmployeePositionChangeVO.setOldPositionNameEN(oldPositionVO.getTitleEN());
            tempEmployeePositionChangeVO.setNewPositionId(oldPositionVO.getPositionId());
            tempEmployeePositionChangeVO.setNewPositionNameZH(performanceDB.getNewPositionZH());
            tempEmployeePositionChangeVO.setNewPositionNameEN(performanceDB.getNewPositionEN());

            // Leader Working title old & new
            tempEmployeePositionChangeVO.setOldParentPositionId(oldPositionVO.getParentPositionId());
            tempEmployeePositionChangeVO.setNewParentPositionId(oldPositionVO.getParentPositionId());
            final PositionVO oldParentPositionVO = accountConstants.getPositionVOByPositionId(oldPositionVO.getParentPositionId());
            if (oldParentPositionVO != null) {
              tempEmployeePositionChangeVO.setOldParentPositionNameZH(oldParentPositionVO.getTitleZH());
              tempEmployeePositionChangeVO.setOldParentPositionNameEN(oldParentPositionVO.getTitleEN());
              tempEmployeePositionChangeVO.setNewParentPositionNameZH(oldParentPositionVO.getTitleZH());
              tempEmployeePositionChangeVO.setNewParentPositionNameEN(oldParentPositionVO.getTitleEN());
            }

            // Direct Report Manager old & new
            tempEmployeePositionChangeVO.setOldParentPositionOwnersZH(accountConstants.getStaffNamesByPositionId("zh", oldPositionVO.getParentPositionId()));
            tempEmployeePositionChangeVO.setOldParentPositionOwnersEN(accountConstants.getStaffNamesByPositionId("en", oldPositionVO.getParentPositionId()));
            tempEmployeePositionChangeVO.setNewParentPositionOwnersZH(accountConstants.getStaffNamesByPositionId("zh", oldPositionVO.getParentPositionId()));
            tempEmployeePositionChangeVO.setNewParentPositionOwnersEN(accountConstants.getStaffNamesByPositionId("en", oldPositionVO.getParentPositionId()));

            // Job Grade old & new
            tempEmployeePositionChangeVO.setOldPositionGradeId(oldPositionVO.getPositionGradeId());
            tempEmployeePositionChangeVO.setNewPositionGradeId(newPositionGradeVO.getPositionGradeId());
            final PositionGradeVO oldPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId(oldPositionVO.getPositionGradeId());
            if (oldPositionGradeVO != null) {
              tempEmployeePositionChangeVO.setOldPositionGradeNameZH(oldPositionGradeVO.getGradeNameZH());
              tempEmployeePositionChangeVO.setOldPositionGradeNameEN(oldPositionGradeVO.getGradeNameEN());
            }

            tempEmployeePositionChangeVO.setNewPositionGradeNameZH(newPositionGradeVO.getGradeNameZH());
            tempEmployeePositionChangeVO.setNewPositionGradeNameEN(newPositionGradeVO.getGradeNameEN());

            // 1.插入异动记录
            this.getEmployeePositionChangeDao().insertEmployeePositionChange(tempEmployeePositionChangeVO);

            // 2.插入异动日志
            insertLog(tempEmployeePositionChangeVO, performanceDB.getIp(), performanceDB.getDecodeModifyBy(), tempEmployeePositionChangeVO.getPositionChangeId());

            // 3.更新职位信息
            oldPositionVO.setTitleZH(performanceDB.getNewPositionZH());
            oldPositionVO.setTitleEN(performanceDB.getNewPositionEN());
            oldPositionVO.setPositionGradeId(newPositionGradeVO.getPositionGradeId());
            this.getPositionDao().updatePosition(oldPositionVO);
          }
        }
      }
    }

    return 1;
  }

  /***
   * 根据employeeId获取有效的劳动合同
   * 
   * @param employeeId
   * @return 在职合同
   * @throws KANException
   */
  private EmployeeContractVO getValidEmployeeContractVOByEmployeeId(final String employeeId) throws KANException {
    final List<Object> employeeContractVOObjects = this.getEmployeeContractDao().getEmployeeContractVOsByEmployeeId(employeeId);
    if (employeeContractVOObjects != null && employeeContractVOObjects.size() > 0) {
      for (Object o : employeeContractVOObjects) {
        if (((EmployeeContractVO) o).getEmployStatus().equals("1")) {
          return this.getEmployeeContractDao().getEmployeeContractVOByContractId(((EmployeeContractVO) o).getContractId());
        }
      }
    }

    return null;
  }

  /***
   * 薪资调整
   * 
   * @param performanceVO
   * @param validEmployeeContractVO
   * @return 1
   * @throws KANException
   */
  private int needSalaryAdjustment(final PerformanceVO performanceVO, final String contractId) throws KANException {
    if (KANUtil.filterEmpty(performanceVO.getYearPerformanceRating()) == null)
      return 0;

    // 初始化Map，用以查找薪酬方案
    final Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("contractId", contractId);
    for (String salaryItemId : ADJUSTMENT_SALARY_ITEM_IDS) {
      String newBase = getSalaryNewBaseByItemId(performanceVO, salaryItemId);
      if (newBase != null) {
        boolean newSalaryAdjustment = false;
        String employeeSalaryId = null;
        String oldBase = null;
        String oldStartDate = null;
        String oldEndDate = null;

        double d_newBase = 0;
        double d_oldBase = 0;

        try {
          d_newBase = Double.valueOf(newBase);
        } catch (Exception e) {
          System.err.println("error>>>>>performance id:" + performanceVO.getPerformanceId());
          throw new KANException(e);
        }

        parameters.put("itemId", salaryItemId);
        final List<Object> salarySolutions = this.getEmployeeContractSalaryDao().getEmployeeContractSalaryVOsByContractIdAndItemId(parameters);
        if (salarySolutions != null && salarySolutions.size() > 0) {
          EmployeeContractSalaryVO temp = null;
          long tempLong = Long.MIN_VALUE;
          for (Object obj : salarySolutions) {
            final EmployeeContractSalaryVO tempVO = (EmployeeContractSalaryVO) obj;
            if (KANUtil.filterEmpty(tempVO.getStartDate()) != null && KANUtil.createDate(tempVO.getStartDate()).getTime() > tempLong) {
              temp = tempVO;
              tempLong = KANUtil.createDate(tempVO.getStartDate()).getTime();
            }
          }

          if (temp != null) {
            try {
              d_oldBase = Double.valueOf(temp.getBase());
            } catch (Exception e) {
              System.err.println("error>>>>>performance id:" + performanceVO.getPerformanceId());
              throw new KANException(e);
            }

            if (d_newBase != d_oldBase) {
              newSalaryAdjustment = true;
              employeeSalaryId = temp.getEmployeeSalaryId();
              oldBase = temp.getBase();
              oldStartDate = temp.getStartDate();
              oldEndDate = temp.getEndDate();
            }
          }
        } else {
          newSalaryAdjustment = d_newBase > 0;
        }

        if (newSalaryAdjustment) {
          EmployeeSalaryAdjustmentVO insert_db = new EmployeeSalaryAdjustmentVO();
          insert_db.setAccountId(performanceVO.getAccountId());
          insert_db.setCorpId(performanceVO.getCorpId());
          insert_db.setEmployeeId(performanceVO.getEmployeeId());
          insert_db.setContractId(contractId);
          insert_db.setItemId(salaryItemId);
          insert_db.setEffectiveDate((Integer.parseInt(performanceVO.getYearly()) + 1) + "-01-01");
          insert_db.setRemark3("12");
          insert_db.setDescription("Performance Info Confirm");
          insert_db.setCreateBy(performanceVO.getModifyBy());
          insert_db.setModifyBy(performanceVO.getModifyBy());
          insert_db.setStatus("3");
          insert_db.setEmployeeSalaryId(employeeSalaryId);
          insert_db.setOldBase(oldBase);
          insert_db.setOldStartDate(oldStartDate);
          insert_db.setOldEndDate(oldEndDate);
          insert_db.setNewBase(newBase);
          insert_db.setNewStartDate((Integer.parseInt(performanceVO.getYearly()) + 1) + "-01-01");
          ItemVO itemVO = KANConstants.getKANAccountConstants(performanceVO.getAccountId()).getItemVOByItemId(salaryItemId);
          if (itemVO != null) {
            insert_db.setItemNameZH(itemVO.getNameZH());
            insert_db.setItemNameEN(itemVO.getNameEN());
          }

          // 插入数据库
          this.getEmployeeSalaryAdjustmentDao().insertEmployeeSalaryAdjustment(insert_db);
          final EmployeeSalaryAdjustmentVO tempEmployeeSalaryAdjustmentVO = this.getEmployeeSalaryAdjustmentDao().getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId(insert_db.getSalaryAdjustmentId());
          tempEmployeeSalaryAdjustmentVO.setLocale(performanceVO.getLocale());
          // 插入日志
          insertLog(tempEmployeeSalaryAdjustmentVO, performanceVO.getIp(), tempEmployeeSalaryAdjustmentVO.getDecodeModifyBy(), tempEmployeeSalaryAdjustmentVO.getSalaryAdjustmentId());
        }
      }
    }

    return 1;
  }

  private String getSalaryNewBaseByItemId(final PerformanceVO performanceVO, final String salaryItemId) {
    String newBase = null;

    switch (salaryItemId) {
      case ITEM_BASE_SALARY:
        newBase = performanceVO.getNewBaseSalaryLocal();
        break;
      case ITEM_HOUSING_ALLOWANCE:
        newBase = performanceVO.getNewAnnualHousingAllowance();
        break;
      case ITEM_CHILD_EDU_ALLOWANCE:
        newBase = performanceVO.getNewAnnualChildrenEduAllowance();
        break;
      case ITEM_INCENTIVE_MONTHLY:
        newBase = performanceVO.getNewMonthlyTarget();
        break;
      case ITEM_INCENTIVE_QUARTERLY:
        newBase = performanceVO.getNewQuarterlyTarget();
        break;
      case ITEM_INCENTIVE_TARGET:
        newBase = performanceVO.getNewGPTarget();
        break;
      case ITEM_ANNUAL_BONUS:
        newBase = performanceVO.getYearEndBonus();
        break;
      default:
        break;
    }

    return newBase;
  }

  /***
   * 插入异动日志
   * 
   * @param object
   * @param ip
   * @param createBy
   * @throws KANException
   */
  private void insertLog(final Object object, final String ip, final String createBy, final String pk) throws KANException {
    final LogVO log = new LogVO();

    // 如果是异动/升迁
    if (object != null && object instanceof EmployeePositionChangeVO) {
      log.setEmployeeId(((EmployeePositionChangeVO) object).getEmployeeId());
      log.setChangeReason(((EmployeePositionChangeVO) object).getRemark3());
      log.setEmployeeNameZH(((EmployeePositionChangeVO) object).getEmployeeNameZH());
      log.setEmployeeNameEN(((EmployeePositionChangeVO) object).getEmployeeNameEN());
    }
    // 如果是薪酬调整
    else if (object != null && object instanceof EmployeeSalaryAdjustmentVO) {
      log.setEmployeeId(((EmployeeSalaryAdjustmentVO) object).getEmployeeId());
      log.setChangeReason(((EmployeeSalaryAdjustmentVO) object).getRemark3());
      log.setEmployeeNameZH(((EmployeeSalaryAdjustmentVO) object).getEmployeeNameZH());
      log.setEmployeeNameEN(((EmployeeSalaryAdjustmentVO) object).getEmployeeNameEN());
    }
    // 如果是员工劳动合同
    else if (object != null && object instanceof EmployeeContractVO) {
      log.setEmployeeId(((EmployeeContractVO) object).getEmployeeId());
      log.setChangeReason(((EmployeeContractVO) object).getRemark3());
      log.setEmployeeNameZH(((EmployeeContractVO) object).getEmployeeNameZH());
      log.setEmployeeNameEN(((EmployeeContractVO) object).getEmployeeNameEN());
    }

    log.setType(String.valueOf(Operate.SUBMIT.getIndex()));
    log.setModule(object == null ? "" : object.getClass().getCanonicalName());
    log.setContent(object == null ? "" : JsonMapper.toLogJson(object));
    log.setIp(ip);
    log.setOperateTime(KANUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
    log.setOperateBy(createBy);
    log.setpKey(pk);
    log.setRemark(null);

    this.getLogDao().insertLog(log);
  }

  /***
   * 预算薪资增长
   */
  @Override
  public boolean calculateNextYearSalaryDetails(PerformanceVO performanceVO) throws KANException {
    // 搜索对应的YERRRuleVO
    final Map<String, Object> search_condition = new HashMap<String, Object>();
    search_condition.put("accountId", performanceVO.getAccountId());
    search_condition.put("corpId", performanceVO.getCorpId());
    search_condition.put("rating", performanceVO.getYearPerformanceRating());
    search_condition.put("currencyCode", performanceVO.getCurrencyCode());
    final List<Object> listYERRRoluVOs = yerrRuleDao.getYERRRuleVOsByMapParameter(search_condition);

    // 根据货币、是否晋升，找到对应的recommendTTCIncrease
    double recommendTTCIncrease = 1.000;
    double bonusIncrease = 0;
    if (listYERRRoluVOs != null && listYERRRoluVOs.size() > 0) {
      YERRRuleVO yerrRuleVO = (YERRRuleVO) listYERRRoluVOs.get(0);
      switch (performanceVO.getCurrencyCode()) {
        case "RMB":
          recommendTTCIncrease = !"P".equalsIgnoreCase(performanceVO.getYearPerformancePromotion()) ? yerrRuleVO.getMeritRateRMB() : yerrRuleVO.getMeritAndPromotionRateRMB();
          break;
        case "HKD":
          recommendTTCIncrease = !"P".equalsIgnoreCase(performanceVO.getYearPerformancePromotion()) ? yerrRuleVO.getMeritRateRMB() : yerrRuleVO.getMeritAndPromotionRateRMB();
          break;
        case "SGD":
          recommendTTCIncrease = !"P".equalsIgnoreCase(performanceVO.getYearPerformancePromotion()) ? yerrRuleVO.getMeritRateRMB() : yerrRuleVO.getMeritAndPromotionRateRMB();
          break;
        default:
          recommendTTCIncrease = 1.000;
          break;
      }

      bonusIncrease = yerrRuleVO.getBounsRate();
    }

    // 根剧货币搜索对应的汇率
    final List<Object> listExchangeRateVOs = exchangeRateDao.getExchangeRateVOsByMapParameter(search_condition);
    double exchangeRate = 1.000;
    if (listExchangeRateVOs != null && listExchangeRateVOs.size() > 0) {
      ExchangeRateVO exchangeRateVO = (ExchangeRateVO) listExchangeRateVOs.get(0);
      exchangeRate = Double.valueOf(exchangeRateVO.getFromUSD()) / Double.valueOf(exchangeRateVO.getToLocal());
    }

    // TTC = 上年TTC * TTC增长率
    double newTTCLocal = Double.valueOf(performanceVO.getTtcLocal()) * recommendTTCIncrease;
    // 年基本收入 = 上年基本收入 * TTC增长率
    double newAnnualSalaryLocal = Double.valueOf(performanceVO.getAnnualBaseSalaryLocal()) * recommendTTCIncrease;
    // 月基本收入 = 年基本收入 / 12(无条件进位)
    double newBaseSalaryLocal = Math.ceil(newAnnualSalaryLocal / 12);
    // 房屋补贴 = 上年房屋补贴 * TTC增长率
    double newAnnualHousingAllowance = Double.valueOf(performanceVO.getHousingAllowanceLocal()) * recommendTTCIncrease;
    // 子女教育津贴 = 上年子女教育津贴 * TTC增长率
    double newAnnualChildrenEduAllowance = Double.valueOf(performanceVO.getChildrenEduAllowanceLocal()) * recommendTTCIncrease;
    // 年总收入 = 年基础收入 + 住房补贴 + 子女教育津贴
    double newYearlyIncome = newAnnualSalaryLocal + newAnnualHousingAllowance + newAnnualChildrenEduAllowance;
    // 预算年终奖
    double newYearEndBonus = Double.valueOf(performanceVO.getCurrentYearEndBonus()) * bonusIncrease;

    performanceVO.setRecommendTTCIncrease(String.valueOf(recommendTTCIncrease));
    performanceVO.setTtcIncrease(String.valueOf(recommendTTCIncrease));
    performanceVO.setNewTTCLocal(KANUtil.formatNumber2(newTTCLocal));
    performanceVO.setNewTTCUSD(KANUtil.formatNumber2(newTTCLocal * exchangeRate));
    performanceVO.setNewBaseSalaryLocal(KANUtil.formatNumber2(newBaseSalaryLocal));
    performanceVO.setNewBaseSalaryUSD(KANUtil.formatNumber2(newBaseSalaryLocal * exchangeRate));
    performanceVO.setNewAnnualSalaryLocal(KANUtil.formatNumber2(newAnnualSalaryLocal));
    performanceVO.setNewAnnualSalaryUSD(KANUtil.formatNumber2(newAnnualSalaryLocal * exchangeRate));
    performanceVO.setNewAnnualHousingAllowance(KANUtil.formatNumber2(newAnnualHousingAllowance));
    performanceVO.setNewAnnualChildrenEduAllowance(KANUtil.formatNumber2(newAnnualChildrenEduAllowance));
    performanceVO.setNewAnnualGuaranteedAllowanceLocal(KANUtil.formatNumber2(newYearlyIncome));
    performanceVO.setNewAnnualGuatanteedAllowanceUSD(KANUtil.formatNumber2(newYearlyIncome * exchangeRate));
    performanceVO.setYearEndBonus(KANUtil.formatNumber2(newYearEndBonus));

    return true;
  }

}
