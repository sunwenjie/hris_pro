package com.kan.hro.domain.biz.performance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class PerformanceVO extends BaseVO {

  /**
   * status = 1新建 2调薪 4升职 6调薪+升职
   */
  private static final long serialVersionUID = 13040579452321L;

  private String performanceId;

  private String employeeId;

  private String yearly;

  private String fullName;

  private String shortName;

  private String chineseName;
  // #法务名称(英文)
  private String employmentEntityEN;
  // #法务名称(中文)
  private String employmentEntityZH;
  // #公司名称
  private String companyInitial;
  // 上级部门(英文)
  private String buFunctionEN;

  private String buFunctionZH;

  private String departmentEN;

  private String departmentZH;

  private String costCenter;

  // 业务类型
  private String functionCode;

  private String location;

  private String jobRole;

  // 职位
  private String positionEN;

  private String positionZH;

  private String jobGrade;

  // 内部职称
  private String internalTitle;

  // 业务直线汇报人
  private String lineBizManager;

  // HR直线汇报人
  private String lineHRManager;

  // 首次工作时间
  private String seniorityDate;

  // 入司日期
  private String employmentDate;

  private String shareOptions;

  // 上年绩效等级
  private String lastYearPerformanceRating;

  // 上年晋升
  private String lastYearPerformancePromotion;

  // 本年中晋升
  private String midYearPromotion;

  // 本年中薪资增长
  private String midYearSalaryIncrease;

  // 货币
  private String currencyCode;

  // 月基本工资(本地)
  private String baseSalaryLocal;

  // 月基本工资(美元)
  private String baseSalaryUSD;

  // 年薪(本地)
  private String annualBaseSalaryLocal;

  private String annualBaseSalaryUSD;

  // 年住房补贴
  private String housingAllowanceLocal;

  // 年儿童教育补贴
  private String childrenEduAllowanceLocal;

  // 年保险费
  private String guaranteedCashLocal;

  // 年保险费(美元)
  private String guaranteedCashUSD;

  // 月奖金
  private String monthlyTarget;

  // 季度奖金
  private String quarterlyTarget;

  private String gpTarget;

  private String yearEndBonus;

  // 奖金总值
  private String targetValueLocal;

  private String targetValueUSD;

  private String ttcLocal;

  private String ttcUSD;

  private String currentYearEndBonus;

  // 年绩效评分
  private String yearPerformanceRating;

  // 年绩效晋升
  private String yearPerformancePromotion;

  // 推荐TTC增长
  private String recommendTTCIncrease;

  // TTC增长
  private String ttcIncrease;

  private String newTTCLocal;

  private String newTTCUSD;

  // 新月薪
  private String newBaseSalaryLocal;

  private String newBaseSalaryUSD;

  // 新年薪
  private String newAnnualSalaryLocal;

  private String newAnnualSalaryUSD;

  private String newAnnualHousingAllowance;

  private String newAnnualChildrenEduAllowance;

  private String newAnnualGuaranteedAllowanceLocal;

  private String newAnnualGuatanteedAllowanceUSD;

  private String newMonthlyTarget;

  private String newQuarterlyTarget;

  private String newGPTarget;

  private String newTargetValueLocal;

  private String newTargetValueUSD;

  private String newJobGrade;

  private String newInternalTitle;

  private String newPositionEN;

  private String newPositionZH;

  private String newShareOptions;

  // 分红
  private String targetBonus;

  // 预留分红
  private String proposedBonus;

  // 预留支出
  private String proposedPayoutLocal;

  private String proposedPayoutUSD;

  private String description;

  // for app
  private String tmpYearPerformanceRating;

  // 导出excel，安BU/function导出
  private String selectBUFunctionName;

  private List<MappingVO> years = new ArrayList<MappingVO>();

  private List<MappingVO> ratings = new ArrayList<MappingVO>();

  private List<MappingVO> isPromotions = new ArrayList<MappingVO>();

  private List<MappingVO> jobGrades = new ArrayList<MappingVO>();

  private List<MappingVO> buFunctions = new ArrayList<MappingVO>();

  private int yesGPTarget;
  private int yesNewGPTarget;

  @Override
  public String getEncodedId() throws KANException {
    return encodedField(this.performanceId);
  }

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    super.reset(mapping, request);
    int currYear = Integer.valueOf(KANUtil.formatDate(new Date(), "yyyy"));
    years.addAll(KANUtil.getYears(currYear - 5, currYear));
    if (ratings == null) {
      ratings = new ArrayList<MappingVO>();
    }
    if (isPromotions == null) {
      isPromotions = new ArrayList<MappingVO>();
    }
    if (jobGrades == null) {
      jobGrades = new ArrayList<MappingVO>();
    }

    final String[] ratingArray = new String[] {"5", "4.5", "4", "3.5", "3", "2.5", "2", "1"};
    final String[] promotionTypeArray = new String[] {"P", "R", "N"};
    String[] jobGradeArray = new String[] {"E2", "E1", "M4", "IC7", "M3", "IC6", "M2", "IC5", "M1", "IC4", "IC3", "IC2", "IC1", "S2", "S1"};
    ratings.add(getEmptyMappingVO());
    for (String ratingStr : ratingArray) {
      ratings.add(new MappingVO(ratingStr, ratingStr));
    }
    isPromotions.add(getEmptyMappingVO());
    for (String isPromotionStr : promotionTypeArray) {
      isPromotions.add(new MappingVO(isPromotionStr, isPromotionStr));
    }
    jobGrades.add(getEmptyMappingVO());
    for (String str : jobGradeArray) {
      jobGrades.add(new MappingVO(str, str));
    }

    buFunctions = KANConstants.getKANAccountConstants(super.getAccountId()).getBUFunction();
    buFunctions.add(0, new MappingVO("", "All"));
  }

  @Override
  public void reset() throws KANException {
    employeeId = "";
    yearly = "";
    fullName = "";
    shortName = "";
    chineseName = "";
    employmentEntityEN = "";
    employmentEntityZH = "";
    companyInitial = "";
    buFunctionEN = "";
    buFunctionZH = "";
    departmentEN = "";
    departmentZH = "";
    costCenter = "";
    functionCode = "";
    location = "";
    jobRole = "";
    positionEN = "";
    positionZH = "";
    jobGrade = "";
    internalTitle = "";
    lineBizManager = "";
    lineHRManager = "";
    seniorityDate = null;
    employmentDate = null;
    shareOptions = null;
    lastYearPerformanceRating = "";
    lastYearPerformancePromotion = "";
    midYearPromotion = "";
    midYearSalaryIncrease = "";
    currencyCode = "";
    baseSalaryLocal = "0";
    baseSalaryUSD = "0";
    annualBaseSalaryLocal = "0";
    annualBaseSalaryUSD = "0";
    housingAllowanceLocal = "0";
    childrenEduAllowanceLocal = "0";
    guaranteedCashLocal = "0";
    guaranteedCashUSD = "0";
    monthlyTarget = "0";
    quarterlyTarget = "0";
    gpTarget = "0";
    yearEndBonus = "0";
    targetValueLocal = "0";
    targetValueUSD = "0";
    ttcLocal = "0";
    ttcUSD = "0";
    currentYearEndBonus = "0";
    yearPerformanceRating = "";
    yearPerformancePromotion = "";
    recommendTTCIncrease = "";
    ttcIncrease = "";
    newTTCLocal = "0";
    newTTCUSD = "0";
    newBaseSalaryLocal = "0";
    newBaseSalaryUSD = "0";
    newAnnualSalaryLocal = "0";
    newAnnualSalaryUSD = "0";
    newAnnualHousingAllowance = "0";
    newAnnualChildrenEduAllowance = "0";
    newAnnualGuaranteedAllowanceLocal = "0";
    newAnnualGuatanteedAllowanceUSD = "0";
    newMonthlyTarget = "0";
    newQuarterlyTarget = "0";
    newGPTarget = "0";
    newTargetValueLocal = "0";
    newTargetValueUSD = "0";
    newJobGrade = "";
    newInternalTitle = "";
    newPositionEN = "";
    newPositionZH = "";
    newShareOptions = "";
    targetBonus = "0";
    proposedBonus = "0";
    proposedPayoutLocal = "0";
    proposedPayoutUSD = "0";
    description = "";
  }

  @Override
  public void update(Object object) throws KANException {
    PerformanceVO performanceVO = (PerformanceVO) object;
    yearPerformanceRating = performanceVO.getYearPerformanceRating();
    yearPerformancePromotion = performanceVO.getYearPerformancePromotion();
    recommendTTCIncrease = performanceVO.getRecommendTTCIncrease();
    ttcIncrease = performanceVO.getTtcIncrease();
    newTTCLocal = performanceVO.getNewTTCLocal();
    newTTCUSD = performanceVO.getNewTTCUSD();
    newBaseSalaryLocal = performanceVO.getNewBaseSalaryLocal();
    newBaseSalaryUSD = performanceVO.getBaseSalaryUSD();
    newAnnualSalaryLocal = performanceVO.getNewAnnualSalaryLocal();
    newAnnualSalaryUSD = performanceVO.getNewAnnualSalaryUSD();
    newAnnualHousingAllowance = performanceVO.getNewAnnualHousingAllowance();
    newAnnualChildrenEduAllowance = performanceVO.getNewAnnualChildrenEduAllowance();
    newAnnualGuaranteedAllowanceLocal = performanceVO.getNewAnnualGuaranteedAllowanceLocal();
    newAnnualGuatanteedAllowanceUSD = performanceVO.getNewAnnualGuatanteedAllowanceUSD();
    newMonthlyTarget = performanceVO.getNewMonthlyTarget();
    newQuarterlyTarget = performanceVO.getNewQuarterlyTarget();
    newGPTarget = performanceVO.getNewGPTarget();
    newTargetValueLocal = performanceVO.getNewTargetValueLocal();
    newTargetValueUSD = performanceVO.getNewTargetValueUSD();
    newJobGrade = performanceVO.getNewJobGrade();
    newInternalTitle = performanceVO.getNewInternalTitle();
    newPositionEN = performanceVO.getNewPositionEN();
    newPositionZH = performanceVO.getNewPositionZH();
    newShareOptions = performanceVO.getNewShareOptions();
    targetBonus = performanceVO.getTargetBonus();
    proposedBonus = performanceVO.getProposedBonus();
    proposedPayoutLocal = performanceVO.getProposedPayoutLocal();
    proposedPayoutUSD = performanceVO.getProposedPayoutUSD();
    description = performanceVO.getDescription();
    super.setStatus(performanceVO.getStatus());
    super.setModifyDate(new Date());
    yearEndBonus = performanceVO.getYearEndBonus();// 预算年终奖
    super.setRemark2(performanceVO.getRemark2());// 年薪
    super.setRemark3(performanceVO.getRemark3());// 实发年终奖
    super.setRemark4(performanceVO.getRemark4());// 香港的
  }

  public String getPerformanceId() {
    return performanceId;
  }

  public void setPerformanceId(String performanceId) {
    this.performanceId = performanceId;
  }

  public String getYearly() {
    return yearly;
  }

  public void setYearly(String yearly) {
    this.yearly = yearly;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getChineseName() {
    return chineseName;
  }

  public void setChineseName(String chineseName) {
    this.chineseName = chineseName;
  }

  public String getEmploymentEntityEN() {
    return employmentEntityEN;
  }

  public void setEmploymentEntityEN(String employmentEntityEN) {
    this.employmentEntityEN = employmentEntityEN;
  }

  public String getEmploymentEntityZH() {
    return employmentEntityZH;
  }

  public void setEmploymentEntityZH(String employmentEntityZH) {
    this.employmentEntityZH = employmentEntityZH;
  }

  public String getCompanyInitial() {
    return companyInitial;
  }

  public void setCompanyInitial(String companyInitial) {
    this.companyInitial = companyInitial;
  }

  public String getBuFunctionEN() {
    return buFunctionEN;
  }

  public void setBuFunctionEN(String buFunctionEN) {
    this.buFunctionEN = buFunctionEN;
  }

  public String getBuFunctionZH() {
    return buFunctionZH;
  }

  public void setBuFunctionZH(String buFunctionZH) {
    this.buFunctionZH = buFunctionZH;
  }

  public String getDepartmentEN() {
    return departmentEN;
  }

  public void setDepartmentEN(String departmentEN) {
    this.departmentEN = departmentEN;
  }

  public String getDepartmentZH() {
    return departmentZH;
  }

  public void setDepartmentZH(String departmentZH) {
    this.departmentZH = departmentZH;
  }

  public String getCostCenter() {
    return costCenter;
  }

  public void setCostCenter(String costCenter) {
    this.costCenter = costCenter;
  }

  public String getFunctionCode() {
    return functionCode;
  }

  public void setFunctionCode(String functionCode) {
    this.functionCode = functionCode;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getJobRole() {
    return jobRole;
  }

  public void setJobRole(String jobRole) {
    this.jobRole = jobRole;
  }

  public String getPositionEN() {
    return positionEN;
  }

  public void setPositionEN(String positionEN) {
    this.positionEN = positionEN;
  }

  public String getPositionZH() {
    return positionZH;
  }

  public void setPositionZH(String positionZH) {
    this.positionZH = positionZH;
  }

  public String getJobGrade() {
    return jobGrade;
  }

  public void setJobGrade(String jobGrade) {
    this.jobGrade = jobGrade;
  }

  public String getInternalTitle() {
    return internalTitle;
  }

  public void setInternalTitle(String internalTitle) {
    this.internalTitle = internalTitle;
  }

  public String getLineBizManager() {
    return lineBizManager;
  }

  public void setLineBizManager(String lineBizManager) {
    this.lineBizManager = lineBizManager;
  }

  public String getLineHRManager() {
    return lineHRManager;
  }

  public void setLineHRManager(String lineHRManager) {
    this.lineHRManager = lineHRManager;
  }

  public String getSeniorityDate() {
    return KANUtil.filterEmpty(seniorityDate) == null ? null : decodeDate(seniorityDate);
  }

  public void setSeniorityDate(String seniorityDate) {
    this.seniorityDate = seniorityDate;
  }

  public String getEmploymentDate() {
    return KANUtil.filterEmpty(employmentDate) == null ? null : decodeDate(employmentDate);
  }

  public void setEmploymentDate(String employmentDate) {
    this.employmentDate = employmentDate;
  }

  public String getShareOptions() {
    return shareOptions;
  }

  public void setShareOptions(String shareOptions) {
    this.shareOptions = shareOptions;
  }

  public String getLastYearPerformanceRating() {
    return lastYearPerformanceRating;
  }

  public void setLastYearPerformanceRating(String lastYearPerformanceRating) {
    this.lastYearPerformanceRating = lastYearPerformanceRating;
  }

  public String getLastYearPerformancePromotion() {
    return lastYearPerformancePromotion;
  }

  public void setLastYearPerformancePromotion(String lastYearPerformancePromotion) {
    this.lastYearPerformancePromotion = lastYearPerformancePromotion;
  }

  public String getMidYearPromotion() {
    return midYearPromotion;
  }

  public void setMidYearPromotion(String midYearPromotion) {
    this.midYearPromotion = midYearPromotion;
  }

  public String getMidYearSalaryIncrease() {
    return midYearSalaryIncrease;
  }

  public void setMidYearSalaryIncrease(String midYearSalaryIncrease) {
    this.midYearSalaryIncrease = midYearSalaryIncrease;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getBaseSalaryLocal() {
    return formatNumber_2(baseSalaryLocal);
  }

  public void setBaseSalaryLocal(String baseSalaryLocal) {
    this.baseSalaryLocal = baseSalaryLocal;
  }

  public String getBaseSalaryUSD() {
    return formatNumber_2(baseSalaryUSD);
  }

  public void setBaseSalaryUSD(String baseSalaryUSD) {
    this.baseSalaryUSD = baseSalaryUSD;
  }

  public String getAnnualBaseSalaryLocal() {
    return formatNumber_2(annualBaseSalaryLocal);
  }

  public void setAnnualBaseSalaryLocal(String annualBaseSalaryLocal) {
    this.annualBaseSalaryLocal = annualBaseSalaryLocal;
  }

  public String getAnnualBaseSalaryUSD() {
    return formatNumber_2(annualBaseSalaryUSD);
  }

  public void setAnnualBaseSalaryUSD(String annualBaseSalaryUSD) {
    this.annualBaseSalaryUSD = annualBaseSalaryUSD;
  }

  public String getHousingAllowanceLocal() {
    return formatNumber_2(housingAllowanceLocal);
  }

  public void setHousingAllowanceLocal(String housingAllowanceLocal) {
    this.housingAllowanceLocal = housingAllowanceLocal;
  }

  public String getChildrenEduAllowanceLocal() {
    return formatNumber_2(childrenEduAllowanceLocal);
  }

  public void setChildrenEduAllowanceLocal(String childrenEduAllowanceLocal) {
    this.childrenEduAllowanceLocal = childrenEduAllowanceLocal;
  }

  public String getGuaranteedCashLocal() {
    return formatNumber_2(guaranteedCashLocal);
  }

  public void setGuaranteedCashLocal(String guaranteedCashLocal) {
    this.guaranteedCashLocal = guaranteedCashLocal;
  }

  public String getGuaranteedCashUSD() {
    return formatNumber_2(guaranteedCashUSD);
  }

  public void setGuaranteedCashUSD(String guaranteedCashUSD) {
    this.guaranteedCashUSD = guaranteedCashUSD;
  }

  public String getMonthlyTarget() {
    return formatNumber_2(monthlyTarget);
  }

  public void setMonthlyTarget(String monthlyTarget) {
    this.monthlyTarget = monthlyTarget;
  }

  public String getQuarterlyTarget() {
    return formatNumber_2(quarterlyTarget);
  }

  public void setQuarterlyTarget(String quarterlyTarget) {
    this.quarterlyTarget = quarterlyTarget;
  }

  public String getGpTarget() {
    return formatNumber_2(gpTarget);
  }

  public void setGpTarget(String gpTarget) {
    this.gpTarget = gpTarget;
  }

  public String getYearEndBonus() {
    return formatNumber_2(yearEndBonus);
  }

  public void setYearEndBonus(String yearEndBonus) {
    this.yearEndBonus = yearEndBonus;
  }

  public String getTargetValueLocal() {
    return formatNumber_2(targetValueLocal);
  }

  public void setTargetValueLocal(String targetValueLocal) {
    this.targetValueLocal = targetValueLocal;
  }

  public String getTargetValueUSD() {
    return formatNumber_2(targetValueUSD);
  }

  public void setTargetValueUSD(String targetValueUSD) {
    this.targetValueUSD = targetValueUSD;
  }

  public String getTtcLocal() {
    return formatNumber_2(ttcLocal);
  }

  public void setTtcLocal(String ttcLocal) {
    this.ttcLocal = ttcLocal;
  }

  public String getTtcUSD() {
    return formatNumber_2(ttcUSD);
  }

  public void setTtcUSD(String ttcUSD) {
    this.ttcUSD = ttcUSD;
  }

  public String getCurrentYearEndBonus() {
    return formatNumber_2(currentYearEndBonus);
  }

  public void setCurrentYearEndBonus(String currentYearEndBonus) {
    this.currentYearEndBonus = currentYearEndBonus;
  }

  public String getYearPerformanceRating() {
    return yearPerformanceRating;
  }

  public void setYearPerformanceRating(String yearPerformanceRating) {
    this.yearPerformanceRating = yearPerformanceRating;
  }

  public String getYearPerformancePromotion() {
    return yearPerformancePromotion;
  }

  public void setYearPerformancePromotion(String yearPerformancePromotion) {
    this.yearPerformancePromotion = yearPerformancePromotion;
  }

  public String getRecommendTTCIncrease() {
    if (KANUtil.filterEmpty(recommendTTCIncrease) == null)
      return null;
    return KANUtil.round(recommendTTCIncrease, 3, null);
  }

  public void setRecommendTTCIncrease(String recommendTTCIncrease) {
    this.recommendTTCIncrease = recommendTTCIncrease;
  }

  public String getTtcIncrease() {
    if (KANUtil.filterEmpty(ttcIncrease) == null)
      return null;
    return KANUtil.round(ttcIncrease, 3, null);
  }

  public void setTtcIncrease(String ttcIncrease) {
    this.ttcIncrease = ttcIncrease;
  }

  public String getNewTTCLocal() {
    return formatNumber_2(newTTCLocal);
  }

  public void setNewTTCLocal(String newTTCLocal) {
    this.newTTCLocal = newTTCLocal;
  }

  public String getNewTTCUSD() {
    return formatNumber_2(newTTCUSD);
  }

  public void setNewTTCUSD(String newTTCUSD) {
    this.newTTCUSD = newTTCUSD;
  }

  public String getNewBaseSalaryLocal() {
    return formatNumber_2(newBaseSalaryLocal);
  }

  public void setNewBaseSalaryLocal(String newBaseSalaryLocal) {
    this.newBaseSalaryLocal = newBaseSalaryLocal;
  }

  public String getNewBaseSalaryUSD() {
    return formatNumber_2(newBaseSalaryUSD);
  }

  public void setNewBaseSalaryUSD(String newBaseSalaryUSD) {
    this.newBaseSalaryUSD = newBaseSalaryUSD;
  }

  public String getNewAnnualSalaryLocal() {
    return formatNumber_2(newAnnualSalaryLocal);
  }

  public void setNewAnnualSalaryLocal(String newAnnualSalaryLocal) {
    this.newAnnualSalaryLocal = newAnnualSalaryLocal;
  }

  public String getNewAnnualSalaryUSD() {
    return formatNumber_2(newAnnualSalaryUSD);
  }

  public void setNewAnnualSalaryUSD(String newAnnualSalaryUSD) {
    this.newAnnualSalaryUSD = newAnnualSalaryUSD;
  }

  public String getNewAnnualHousingAllowance() {
    return formatNumber_2(newAnnualHousingAllowance);
  }

  public void setNewAnnualHousingAllowance(String newAnnualHousingAllowance) {
    this.newAnnualHousingAllowance = newAnnualHousingAllowance;
  }

  public String getNewAnnualChildrenEduAllowance() {
    return formatNumber_2(newAnnualChildrenEduAllowance);
  }

  public void setNewAnnualChildrenEduAllowance(String newAnnualChildrenEduAllowance) {
    this.newAnnualChildrenEduAllowance = newAnnualChildrenEduAllowance;
  }

  public String getNewAnnualGuaranteedAllowanceLocal() {
    return formatNumber_2(newAnnualGuaranteedAllowanceLocal);
  }

  public void setNewAnnualGuaranteedAllowanceLocal(String newAnnualGuaranteedAllowanceLocal) {
    this.newAnnualGuaranteedAllowanceLocal = newAnnualGuaranteedAllowanceLocal;
  }

  public String getNewAnnualGuatanteedAllowanceUSD() {
    return formatNumber_2(newAnnualGuatanteedAllowanceUSD);
  }

  public void setNewAnnualGuatanteedAllowanceUSD(String newAnnualGuatanteedAllowanceUSD) {
    this.newAnnualGuatanteedAllowanceUSD = newAnnualGuatanteedAllowanceUSD;
  }

  public String getNewMonthlyTarget() {
    return formatNumber_2(newMonthlyTarget);
  }

  public void setNewMonthlyTarget(String newMonthlyTarget) {
    this.newMonthlyTarget = newMonthlyTarget;
  }

  public String getNewQuarterlyTarget() {
    return formatNumber_2(newQuarterlyTarget);
  }

  public void setNewQuarterlyTarget(String newQuarterlyTarget) {
    this.newQuarterlyTarget = newQuarterlyTarget;
  }

  public String getNewGPTarget() {
    return formatNumber_2(newGPTarget);
  }

  public String getNotFormatNewGPTarget() {
    return newGPTarget;
  }

  public void setNewGPTarget(String newGPTarget) {
    this.newGPTarget = newGPTarget;
  }

  public String getNewTargetValueLocal() {
    return formatNumber_2(newTargetValueLocal);
  }

  public void setNewTargetValueLocal(String newTargetValueLocal) {
    this.newTargetValueLocal = newTargetValueLocal;
  }

  public String getNewTargetValueUSD() {
    return formatNumber_2(newTargetValueUSD);
  }

  public void setNewTargetValueUSD(String newTargetValueUSD) {
    this.newTargetValueUSD = newTargetValueUSD;
  }

  public String getNewJobGrade() {
    return newJobGrade;
  }

  public void setNewJobGrade(String newJobGrade) {
    this.newJobGrade = newJobGrade;
  }

  public String getNewInternalTitle() {
    return newInternalTitle;
  }

  public void setNewInternalTitle(String newInternalTitle) {
    this.newInternalTitle = newInternalTitle;
  }

  public String getNewPositionEN() {
    return newPositionEN;
  }

  public void setNewPositionEN(String newPositionEN) {
    this.newPositionEN = newPositionEN;
  }

  public String getNewPositionZH() {
    return newPositionZH;
  }

  public void setNewPositionZH(String newPositionZH) {
    this.newPositionZH = newPositionZH;
  }

  public String getNewShareOptions() {
    return newShareOptions;
  }

  public void setNewShareOptions(String newShareOptions) {
    this.newShareOptions = newShareOptions;
  }

  public String getTargetBonus() {
    return formatNumber_2(targetBonus);
  }

  public void setTargetBonus(String targetBonus) {
    this.targetBonus = targetBonus;
  }

  public String getProposedBonus() {
    return formatNumber_2(proposedBonus);
  }

  public void setProposedBonus(String proposedBonus) {
    this.proposedBonus = proposedBonus;
  }

  public String getProposedPayoutLocal() {
    return formatNumber_2(proposedPayoutLocal);
  }

  public void setProposedPayoutLocal(String proposedPayoutLocal) {
    this.proposedPayoutLocal = proposedPayoutLocal;
  }

  public String getProposedPayoutUSD() {
    return formatNumber_2(proposedPayoutUSD);
  }

  public void setProposedPayoutUSD(String proposedPayoutUSD) {
    this.proposedPayoutUSD = proposedPayoutUSD;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public String getTmpYearPerformanceRating() {
    return tmpYearPerformanceRating;
  }

  public void setTmpYearPerformanceRating(String tmpYearPerformanceRating) {
    this.tmpYearPerformanceRating = tmpYearPerformanceRating;
  }

  public List<MappingVO> getYears() {
    return years;
  }

  public void setYears(List<MappingVO> years) {
    this.years = years;
  }

  public String getRemark2() {
    return formatNumber_2(super.getRemark2());
  }

  public List<MappingVO> getRatings() {
    return ratings;
  }

  public void setRatings(List<MappingVO> ratings) {
    this.ratings = ratings;
  }

  public List<MappingVO> getIsPromotions() {
    return isPromotions;
  }

  public void setIsPromotions(List<MappingVO> isPromotions) {
    this.isPromotions = isPromotions;
  }

  public List<MappingVO> getJobGrades() {
    return jobGrades;
  }

  public void setJobGrades(List<MappingVO> jobGrades) {
    this.jobGrades = jobGrades;
  }

  public String getSelectBUFunctionName() {
    return selectBUFunctionName;
  }

  public void setSelectBUFunctionName(String selectBUFunctionName) {
    this.selectBUFunctionName = selectBUFunctionName;
  }

  public List<MappingVO> getBuFunctions() {
    return buFunctions;
  }

  public void setBuFunctions(List<MappingVO> buFunctions) {
    this.buFunctions = buFunctions;
  }


  public int getYesGPTarget() {
    return yesGPTarget;
  }

  public void setYesGPTarget(int yesGPTarget) {
    this.yesGPTarget = yesGPTarget;
  }


  public int getYesNewGPTarget() {
    return yesNewGPTarget;
  }

  public void setYesNewGPTarget(int yesNewGPTarget) {
    this.yesNewGPTarget = yesNewGPTarget;
  }

  // 返回是否薪资调薪
  public boolean isNeedSalaryAdjustment() {
    return false;
  }

  // 返回是否职位调整
  public boolean isNeedPositionChange() {
    if (KANUtil.filterEmpty(this.getNewPositionZH()) != null || KANUtil.filterEmpty(this.getNewPositionEN()) != null || KANUtil.filterEmpty(this.getNewJobGrade()) != null)
      return true;
    return false;
  }

  public Long getWorkingDays() {
    Long days = 0l;
    if (KANUtil.filterEmpty(this.getYearly()) != null && KANUtil.filterEmpty(this.getSeniorityDate()) != null) {
      Calendar seniortyDate = KANUtil.getCalendar(KANUtil.createDate(getSeniorityDate()));
      Calendar d365 = KANUtil.createCalendar(yearly + "-12-31");
      days = KANUtil.getGapDays(d365, seniortyDate) + 1;

      Calendar d1 = KANUtil.createCalendar(yearly + "-01-01");
      Long temp = KANUtil.getGapDays(d365, d1) + 1;

      days = days >= temp ? temp : days;
    }
    return days;
  }

}
