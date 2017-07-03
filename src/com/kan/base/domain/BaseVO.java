package com.kan.base.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
@JsonIgnoreProperties(value = {"servletWrapper"})
public abstract class BaseVO extends ActionForm implements Serializable {
  /**
   * Serial Version UID
   */
  private static final long serialVersionUID = 6768401684011620840L;
  @JsonIgnore
  public static String TRUE = "1";
  @JsonIgnore
  public static String FALSE = "2";

  private static Map<String, String> currecyMap = new HashMap<String, String>();

  @JsonIgnore
  private String sortColumn;
  @JsonIgnore
  private String sortOrder = "asc";

  private String selectedIds;
  @JsonIgnore
  private String subAction;

  @JsonIgnore
  private String accountId;

  @JsonIgnore
  private String corpId;

  @JsonIgnore
  private String clientId;

  private String remark1;

  private String remark2;

  private String remark3;

  private String remark4;

  private String remark5;

  private String deleted;

  private String status;

  // 是否有关联对象（1、有关联对象，不允许删除；2、无关联对象，可以删除）
  @JsonIgnore
  private String extended;

  private String createBy;

  private Date createDate = new Date();

  private String modifyBy;

  private Date modifyDate = new Date();

  private Locale locale;

  private String systemId;

  private String ip;

  /**
   * For Application
   */
  // 当前登录角色（1:HR Service；2:Inhouse；3:Vendor；4:Client；5:Employee）
  @JsonIgnore
  private String role;

  // 当前职位ID
  @JsonIgnore
  private String currentPositionId;

  // 数据权限专用, 存放PositionIds，逗号分隔
  @JsonIgnore
  private String hasIn;

  @JsonIgnore
  private String notIn;

  // 权限In
  @JsonIgnore
  private List<String> inList;

  // 权限Not In
  @JsonIgnore
  private List<String> notInList;

  // 用于状态查询 in (statusList)
  @JsonIgnore
  private List<String> statusList;

  // 工作流ID
  @JsonIgnore
  private String workflowId;

  // 工作流状态
  @JsonIgnore
  private String workflowStatus;

  @JsonIgnore
  private String rulePublic;

  @JsonIgnore
  private Set<String> rulePrivateIds;
  @JsonIgnore
  private Set<String> rulePositionIds;
  @JsonIgnore
  private Set<String> ruleBranchIds;
  @JsonIgnore
  private Set<String> ruleBusinessTypeIds;
  @JsonIgnore
  private Set<String> ruleEntityIds;

  // 历史记录编号
  @JsonIgnore
  private HistoryVO historyVO = new HistoryVO();

  @JsonIgnore
  private List<MappingVO> deleteds = new ArrayList<MappingVO>();
  @JsonIgnore
  private List<MappingVO> statuses = new ArrayList<MappingVO>();
  @JsonIgnore
  private List<MappingVO> workflowStatuses = new ArrayList<MappingVO>();
  @JsonIgnore
  private List<MappingVO> flags = new ArrayList<MappingVO>();

  /**
   * For Application search
   */
  @JsonIgnore
  private Map<String, Object> searchField = new HashMap<String, Object>();

  /**
   * Encryption Ids
   * 
   * @throws KANException
   */
  abstract public String getEncodedId() throws KANException;

  abstract public void reset() throws KANException;

  abstract public void update(final Object object) throws KANException;

  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    try {
      this.deleteds = KANUtil.getMappings(request.getLocale(), "deleted");
      this.statuses = KANUtil.getMappings(request.getLocale(), "status");
      this.workflowStatuses = KANUtil.getMappings(getLocale(), "actual.steps.status");
      this.flags = KANUtil.getMappings(request.getLocale(), "flag");
      this.locale = request.getLocale();

      // 获取SystemId
      final String systemId = BaseAction.getSystemId(request, null);

      if (KANUtil.filterEmpty(this.systemId) == null) {
        if (KANUtil.filterEmpty(systemId) != null) {
          this.systemId = systemId;
        } else {
          this.systemId = (String) request.getAttribute("systemId");
        }
      }

      // 获取AccountId
      final String accountId = BaseAction.getAccountId(request, null);

      if (KANUtil.filterEmpty(this.accountId) == null) {
        if (KANUtil.filterEmpty(accountId) != null) {
          this.accountId = accountId;
        } else {
          this.accountId = (String) request.getAttribute("accountId");
        }
      }

      // 获取PostionId
      this.currentPositionId = BaseAction.getPositionId(request, null);

      if (KANUtil.filterEmpty(this.currentPositionId) == null) {
        this.currentPositionId = (String) request.getAttribute("postionId");
      }

      this.getHistoryVO().setPositionId(currentPositionId);

      // 获取CorpId
      final String corpId = BaseAction.getCorpId(request, null);

      if (KANUtil.filterEmpty(this.corpId) == null) {
        if (KANUtil.filterEmpty(corpId) != null) {
          this.corpId = corpId;
        } else {
          this.corpId = (String) request.getAttribute("corpId");
        }
      }

      // 获取role
      final String role = BaseAction.getRole(request, null);

      if (KANUtil.filterEmpty(this.role) == null) {
        if (KANUtil.filterEmpty(role) != null) {
          this.role = role;
        } else {
          this.role = (String) request.getAttribute("role");
        }
      }
    } catch (final Exception e) {
    }
  }

  @JsonIgnore
  public String getDecodeDeleted() {
    return decodeField(this.deleted, this.deleteds);
  }

  @JsonIgnore
  public String getDecodeFlag(final String flag) {
    return decodeField(flag, this.flags);
  }

  @JsonIgnore
  public String getDecodeStatus() {
    return decodeField(this.status, this.statuses);
  }

  @JsonIgnore
  public String getDecodeModifyDate() {
    return decodeDatetime(this.modifyDate);
  }

  public String decodeDate(Object dateObject) {
    if (dateObject != null) {
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants(accountId);
      if (accountConstants != null) {
        return new SimpleDateFormat(accountConstants.OPTIONS_DATE_FORMAT).format(dateObject);
      } else {
        return KANUtil.formatDate(dateObject, "yyyy-MM-dd");
      }
    } else {
      return "";
    }
  }

  public String getSystemId() {
    return systemId;
  }

  public void setSystemId(String systemId) {
    this.systemId = systemId;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String decodeDatetime(Object dateObject) {
    if (dateObject != null) {
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants(accountId);
      if (accountConstants != null) {
        return new SimpleDateFormat(accountConstants.OPTIONS_DATE_FORMAT + " " + accountConstants.OPTIONS_TIME_FORMAT).format(dateObject);
      } else {
        return KANUtil.formatDate(dateObject, "yyyy-MM-dd HH:mm:ss");
      }
    } else {
      return "";
    }
  }

  public String decodeDate(String dateString) {
    if (dateString != null && !dateString.trim().isEmpty()) {
      return decodeDate(KANUtil.createDate(dateString));
    } else {
      return "";
    }
  }

  public String decodeDatetime(String dateString) {
    if (dateString != null && !dateString.trim().isEmpty()) {
      return decodeDatetime(KANUtil.createDate(dateString));
    } else {
      return "";
    }
  }

  @JsonIgnore
  public MappingVO getEmptyMappingVO() {
    return KANUtil.getEmptyMappingVO(locale);
  }

  public String getSortColumn() {
    return sortColumn;
  }

  public void setSortColumn(final String sortColumn) {
    this.sortColumn = sortColumn;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(final String sortOrder) {
    if (sortOrder != null && (sortOrder.trim().equalsIgnoreCase("asc") || sortOrder.trim().equalsIgnoreCase("desc"))) {
      this.sortOrder = sortOrder;
    }
  }

  public String getSelectedIds() {
    return selectedIds;
  }

  public void setSelectedIds(final String selectedIds) {
    this.selectedIds = selectedIds;
  }

  public String getSubAction() {
    return subAction;
  }

  public void setSubAction(final String subAction) {
    this.subAction = subAction;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  @JsonIgnore
  public String getDecodeCreateBy() {
    return this.decodeUserId(this.createBy);
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getModifyBy() {
    return modifyBy;
  }

  public void setModifyBy(String modifyBy) {
    this.modifyBy = modifyBy;
  }

  @JsonIgnore
  public String getDecodeModifyBy() {
    return this.decodeUserId(this.modifyBy);
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public String getDeleted() {
    return KANUtil.filterEmpty(deleted);
  }

  public void setDeleted(String deleted) {
    this.deleted = deleted;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<MappingVO> getDeleteds() {
    return deleteds;
  }

  public void setDeleteds(List<MappingVO> deleteds) {
    this.deleteds = deleteds;
  }

  public List<MappingVO> getStatuses() {
    return statuses;
  }

  public void setStatuses(List<MappingVO> statuses) {
    this.statuses = statuses;
  }

  public List<MappingVO> getFlags() {
    return flags;
  }

  public void setFlags(List<MappingVO> flags) {
    this.flags = flags;
  }

  public String getClientId() {
    return KANUtil.filterEmpty(clientId);
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getRemark1() {
    return remark1;
  }

  public void setRemark1(String remark1) {
    this.remark1 = remark1;
  }

  public String getRemark2() {
    return remark2;
  }

  public void setRemark2(String remark2) {
    this.remark2 = remark2;
  }

  public String getRemark3() {
    return remark3;
  }

  public void setRemark3(String remark3) {
    this.remark3 = remark3;
  }

  public String getRemark4() {
    return remark4;
  }

  public void setRemark4(String remark4) {
    this.remark4 = remark4;
  }

  public String getRemark5() {
    return remark5;
  }

  public void setRemark5(String remark5) {
    this.remark5 = remark5;
  }

  public String getAccountId() {
    return KANUtil.filterEmpty(accountId) == null ? "100017" : accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public Map<String, Object> getSearchField() {
    return searchField;
  }

  public void setSearchField(Map<String, Object> searchField) {
    this.searchField = searchField;
  }

  public HistoryVO getHistoryVO() {
    return historyVO;
  }

  public void setHistoryVO(HistoryVO historyVO) {
    this.historyVO = historyVO;
  }

  /***
   * 添加其他的查询选项
   * 
   * @param key
   * @param value
   */
  public void addSearchField(String key, Object value) {
    this.searchField.put(key, value);
  }

  @JsonIgnore
  public String getDecodeCreateDate() {
    return decodeDatetime(this.createDate);
  }

  // 根据字段的值获得对应的Decode值
  public String decodeField(final String fieldValue, final List<MappingVO> mappingVOs) {
    return decodeField(fieldValue, mappingVOs, false);
  }

  // 根据字段的值获得对应的Decode值
  public String decodeField(final String fieldValue, final List<MappingVO> mappingVOs, final boolean isTemp) {
    if (KANUtil.filterEmpty(fieldValue, "0") != null && mappingVOs != null && mappingVOs.size() > 0) {
      for (MappingVO mappingVO : mappingVOs) {
        if (mappingVO != null && mappingVO.getMappingId() != null && mappingVO.getMappingId().equals(fieldValue)) {
          if (isTemp) {
            return mappingVO.getMappingTemp();
          } else {
            return mappingVO.getMappingValue();
          }
        }
      }
    }

    return "";
  }

  // 通用的String类型字段加密方法
  public String encodedField(final String fieldValue) throws KANException {
    return KANUtil.encodeStringWithCryptogram(fieldValue);
  }

  public String decodeUserId(String userId) {
    if (userId != null) {
      // 获得常量中的StaffBaseView集合
      final List<StaffBaseView> staffBaseViews = KANConstants.getKANAccountConstants(getAccountId()).STAFF_BASEVIEW;

      for (StaffBaseView staffBaseView : staffBaseViews) {
        if (userId.equals(staffBaseView.getUserId())) {
          if (getLocale() != null && getLocale().getLanguage() != null && getLocale().getLanguage().equalsIgnoreCase("ZH")) {
            return staffBaseView.getNameZH();
          } else {
            return staffBaseView.getNameEN();
          }

        }
      }
    }

    return "";
  }

  /**
   * 按照系统配置格式化小数位数
   * 
   * @param numberString
   * @return
   */
  public String formatNumber(String numberString) {
    final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants(accountId);

    if (accountConstants != null) {
      if (KANUtil.filterEmpty(numberString) != null) {
        return KANUtil.round(numberString, accountConstants.OPTIONS_ACCURACY, accountConstants.OPTIONS_ROUND);
      } else {
        return KANUtil.round("0", accountConstants.OPTIONS_ACCURACY, accountConstants.OPTIONS_ROUND);
      }
    } else {
      return numberString != null ? numberString : "0";
    }
  }

  /**
   * 保留3位小数
   * 
   * @param numberString
   * @return
   */
  public String formatNumber2(String numberString) {
    final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants(accountId);

    if (accountConstants != null) {
      if (KANUtil.filterEmpty(numberString) != null) {
        return KANUtil.round(numberString, "2", accountConstants.OPTIONS_ROUND);
      } else {
        return KANUtil.round("0", "2", accountConstants.OPTIONS_ROUND);
      }
    } else {
      return KANUtil.filterEmpty(numberString) != null ? numberString : "0.00";
    }
  }

  public String formatNumber_2(String numberString) {
    if (KANUtil.filterEmpty(numberString) == null)
      return "0.00";

    return KANUtil.round(numberString, "2", null);
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  // 客户ID编码
  public String getEncodedClientId() throws KANException {
    return encodedField(clientId);
  }

  public String getExtended() {
    return extended;
  }

  public void setExtended(String extended) {
    this.extended = extended;
  }

  @JsonIgnore
  public String getObjectClass() {
    return this.getClass().getName();
  }

  public String getWorkflowId() {
    return workflowId;
  }

  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

  public String getCurrentPositionId() {
    return currentPositionId;
  }

  public void setCurrentPositionId(String currentPositionId) {
    this.currentPositionId = currentPositionId;
  }

  public String getCorpId() {
    return KANUtil.filterEmpty(corpId) == null ? "300115" : corpId;
  }

  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }

  public String getHasIn() {
    return hasIn;
  }

  public void setHasIn(String in) {
    this.hasIn = in;
  }

  public String getNotIn() {
    return notIn;
  }

  public void setNotIn(String notIn) {
    this.notIn = notIn;
  }

  public List<String> getInList() {
    inList = KANUtil.convertStringToList(hasIn, ",");
    return inList;
  }

  public List<String> getNotInList() {
    notInList = KANUtil.convertStringToList(notIn, ",");
    return notInList;
  }

  public List<String> getStatusList() {
    statusList = KANUtil.convertStringToList(status, ",");
    return statusList;
  }

  public void setStatusList(List<String> statusList) {
    this.statusList = statusList;
  }

  public String getRulePublic() {
    return rulePublic;
  }

  public void setRulePublic(String rulePublic) {
    this.rulePublic = rulePublic;
  }

  public Set<String> getRulePrivateIds() {
    return rulePrivateIds;
  }

  public void setRulePrivateIds(Set<String> rulePrivateIds) {
    this.rulePrivateIds = rulePrivateIds;
  }

  public Set<String> getRulePositionIds() {
    return rulePositionIds;
  }

  public void setRulePositionIds(Set<String> rulePositionIds) {
    this.rulePositionIds = rulePositionIds;
  }

  public Set<String> getRuleBranchIds() {
    return ruleBranchIds;
  }

  public void setRuleBranchIds(Set<String> ruleBranchIds) {
    this.ruleBranchIds = ruleBranchIds;
  }

  public Set<String> getRuleBusinessTypeIds() {
    return ruleBusinessTypeIds;
  }

  public void setRuleBusinessTypeIds(Set<String> ruleBusinessTypeIds) {
    this.ruleBusinessTypeIds = ruleBusinessTypeIds;
  }

  public Set<String> getRuleEntityIds() {
    return ruleEntityIds;
  }

  public void setRuleEntityIds(Set<String> ruleEntityIds) {
    this.ruleEntityIds = ruleEntityIds;
  }

  @JsonIgnore
  public Map<String, String> getCurrecyMap() {
    if (currecyMap == null) {
      currecyMap = new HashMap<String, String>();
    }
    if (currecyMap.size() == 0) {
      currecyMap.put("HK", "zh");
      currecyMap.put("CN", "zh");
      currecyMap.put("DE", "de");
      currecyMap.put("US", "en");
      currecyMap.put("GB", "en");
      currecyMap.put("JP", "ja");
    }
    return currecyMap;
  }

  // 货币符号
  @JsonIgnore
  public String getCurrencyByOrderId(String currency) {
    if (currency != null && currency.length() > 0 && !currency.equals("0")) {
      String value = getCurrecyMap().get(currency);
      Currency c = Currency.getInstance(new Locale(value, currency));
      return c.getSymbol(new Locale(value, currency));
    }
    return "";
  }

  public String getWorkflowStatus() {
    return workflowStatus;
  }

  public void setWorkflowStatus(String workflowStatus) {
    this.workflowStatus = workflowStatus;
  }

  public List<MappingVO> getWorkflowStatuses() {
    return workflowStatuses;
  }

  public void setWorkflowStatuses(List<MappingVO> workflowStatuses) {
    this.workflowStatuses = workflowStatuses;
  }

  /**
   * for decodeObject() 报错
   * 
   * @return
   */
  public String getTRUE() {
    return BaseVO.TRUE;
  }

  public void setTRUE(String str) {

  }

  public String getFALSE() {
    return BaseVO.FALSE;
  }

  public void setFALSE(String str) {

  }
}
