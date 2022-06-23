package com.guilin.studycode.entrity;

import java.io.Serializable;
import java.util.Date;

public class BaseInfo implements Serializable {
    private String province;

    private String cityCode;

    private String orderId;

    private String batchId;

    private String serviceId;

    private Integer serviceMode;

    private String applyEvent;

    private String beforeFee;

    private String totalFee;

    private String certName;

    private Integer certKind;

    private String certCode;

    private String contactPhone;

    private Integer groupFlag;

    private Integer cardType;

    private Integer deliverType;

    private Integer payType;

    private Integer ifInvoice;

    private Integer acceptWay;

    private Integer acceptMode;

    private Date acceptDate;

    private Integer orderStatus;

    private String flowId;

    private String contactId;

    private String payId;

    private String note;

    private String shareId;

    private String remark;

    private String activationType;

    private String contactAddr;

    private String matchResource;

    private String zbBackJsonstr;

    private String zbCallJsonstr;

    private String setupId;

    private String commId;

    private String payResultJsonstr;

    private String payResponsJsonstr;

    private String ifFirstPay;

    private static final long serialVersionUID = 1L;

    public BaseInfo(String province, String cityCode, String orderId, String batchId, String serviceId, Integer serviceMode, String applyEvent, String beforeFee, String totalFee, String certName, Integer certKind, String certCode, String contactPhone, Integer groupFlag, Integer cardType, Integer deliverType, Integer payType, Integer ifInvoice, Integer acceptWay, Integer acceptMode, Date acceptDate, Integer orderStatus, String flowId, String contactId, String payId, String note, String shareId, String remark, String activationType, String contactAddr, String matchResource, String zbBackJsonstr, String zbCallJsonstr, String setupId, String commId, String payResultJsonstr, String payResponsJsonstr, String ifFirstPay) {
        this.province = province;
        this.cityCode = cityCode;
        this.orderId = orderId;
        this.batchId = batchId;
        this.serviceId = serviceId;
        this.serviceMode = serviceMode;
        this.applyEvent = applyEvent;
        this.beforeFee = beforeFee;
        this.totalFee = totalFee;
        this.certName = certName;
        this.certKind = certKind;
        this.certCode = certCode;
        this.contactPhone = contactPhone;
        this.groupFlag = groupFlag;
        this.cardType = cardType;
        this.deliverType = deliverType;
        this.payType = payType;
        this.ifInvoice = ifInvoice;
        this.acceptWay = acceptWay;
        this.acceptMode = acceptMode;
        this.acceptDate = acceptDate;
        this.orderStatus = orderStatus;
        this.flowId = flowId;
        this.contactId = contactId;
        this.payId = payId;
        this.note = note;
        this.shareId = shareId;
        this.remark = remark;
        this.activationType = activationType;
        this.contactAddr = contactAddr;
        this.matchResource = matchResource;
        this.zbBackJsonstr = zbBackJsonstr;
        this.zbCallJsonstr = zbCallJsonstr;
        this.setupId = setupId;
        this.commId = commId;
        this.payResultJsonstr = payResultJsonstr;
        this.payResponsJsonstr = payResponsJsonstr;
        this.ifFirstPay = ifFirstPay;
    }

    public BaseInfo() {
        super();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
    }

    public Integer getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(Integer serviceMode) {
        this.serviceMode = serviceMode;
    }

    public String getApplyEvent() {
        return applyEvent;
    }

    public void setApplyEvent(String applyEvent) {
        this.applyEvent = applyEvent == null ? null : applyEvent.trim();
    }

    public String getBeforeFee() {
        return beforeFee;
    }

    public void setBeforeFee(String beforeFee) {
        this.beforeFee = beforeFee == null ? null : beforeFee.trim();
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee == null ? null : totalFee.trim();
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName == null ? null : certName.trim();
    }

    public Integer getCertKind() {
        return certKind;
    }

    public void setCertKind(Integer certKind) {
        this.certKind = certKind;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode == null ? null : certCode.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Integer getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Integer groupFlag) {
        this.groupFlag = groupFlag;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(Integer deliverType) {
        this.deliverType = deliverType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getIfInvoice() {
        return ifInvoice;
    }

    public void setIfInvoice(Integer ifInvoice) {
        this.ifInvoice = ifInvoice;
    }

    public Integer getAcceptWay() {
        return acceptWay;
    }

    public void setAcceptWay(Integer acceptWay) {
        this.acceptWay = acceptWay;
    }

    public Integer getAcceptMode() {
        return acceptMode;
    }

    public void setAcceptMode(Integer acceptMode) {
        this.acceptMode = acceptMode;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId == null ? null : flowId.trim();
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId == null ? null : contactId.trim();
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getActivationType() {
        return activationType;
    }

    public void setActivationType(String activationType) {
        this.activationType = activationType == null ? null : activationType.trim();
    }

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr == null ? null : contactAddr.trim();
    }

    public String getMatchResource() {
        return matchResource;
    }

    public void setMatchResource(String matchResource) {
        this.matchResource = matchResource == null ? null : matchResource.trim();
    }

    public String getZbBackJsonstr() {
        return zbBackJsonstr;
    }

    public void setZbBackJsonstr(String zbBackJsonstr) {
        this.zbBackJsonstr = zbBackJsonstr == null ? null : zbBackJsonstr.trim();
    }

    public String getZbCallJsonstr() {
        return zbCallJsonstr;
    }

    public void setZbCallJsonstr(String zbCallJsonstr) {
        this.zbCallJsonstr = zbCallJsonstr == null ? null : zbCallJsonstr.trim();
    }

    public String getSetupId() {
        return setupId;
    }

    public void setSetupId(String setupId) {
        this.setupId = setupId == null ? null : setupId.trim();
    }

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId == null ? null : commId.trim();
    }

    public String getPayResultJsonstr() {
        return payResultJsonstr;
    }

    public void setPayResultJsonstr(String payResultJsonstr) {
        this.payResultJsonstr = payResultJsonstr == null ? null : payResultJsonstr.trim();
    }

    public String getPayResponsJsonstr() {
        return payResponsJsonstr;
    }

    public void setPayResponsJsonstr(String payResponsJsonstr) {
        this.payResponsJsonstr = payResponsJsonstr == null ? null : payResponsJsonstr.trim();
    }

    public String getIfFirstPay() {
        return ifFirstPay;
    }

    public void setIfFirstPay(String ifFirstPay) {
        this.ifFirstPay = ifFirstPay == null ? null : ifFirstPay.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", province=").append(province);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", orderId=").append(orderId);
        sb.append(", batchId=").append(batchId);
        sb.append(", serviceId=").append(serviceId);
        sb.append(", serviceMode=").append(serviceMode);
        sb.append(", applyEvent=").append(applyEvent);
        sb.append(", beforeFee=").append(beforeFee);
        sb.append(", totalFee=").append(totalFee);
        sb.append(", certName=").append(certName);
        sb.append(", certKind=").append(certKind);
        sb.append(", certCode=").append(certCode);
        sb.append(", contactPhone=").append(contactPhone);
        sb.append(", groupFlag=").append(groupFlag);
        sb.append(", cardType=").append(cardType);
        sb.append(", deliverType=").append(deliverType);
        sb.append(", payType=").append(payType);
        sb.append(", ifInvoice=").append(ifInvoice);
        sb.append(", acceptWay=").append(acceptWay);
        sb.append(", acceptMode=").append(acceptMode);
        sb.append(", acceptDate=").append(acceptDate);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", flowId=").append(flowId);
        sb.append(", contactId=").append(contactId);
        sb.append(", payId=").append(payId);
        sb.append(", note=").append(note);
        sb.append(", shareId=").append(shareId);
        sb.append(", remark=").append(remark);
        sb.append(", activationType=").append(activationType);
        sb.append(", contactAddr=").append(contactAddr);
        sb.append(", matchResource=").append(matchResource);
        sb.append(", zbBackJsonstr=").append(zbBackJsonstr);
        sb.append(", zbCallJsonstr=").append(zbCallJsonstr);
        sb.append(", setupId=").append(setupId);
        sb.append(", commId=").append(commId);
        sb.append(", payResultJsonstr=").append(payResultJsonstr);
        sb.append(", payResponsJsonstr=").append(payResponsJsonstr);
        sb.append(", ifFirstPay=").append(ifFirstPay);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}