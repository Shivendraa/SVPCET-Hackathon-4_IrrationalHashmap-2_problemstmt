package com.nighteye.myapplication;

public class Complaint {
    private String complaintCategory, complaintInfo, complaintOccurDate, complaintResolved, complaintSubmittedBy;

    public Complaint(String complaintCategory, String complaintInfo, String complaintOccurDate, String complaintResolved, String complaintSubmittedBy) {
        this.complaintCategory = complaintCategory;
        this.complaintInfo = complaintInfo;
        this.complaintOccurDate = complaintOccurDate;
        this.complaintResolved = complaintResolved;
        this.complaintSubmittedBy = complaintSubmittedBy;
    }

    public String getComplaintCategory() {
        return complaintCategory;
    }

    public void setComplaintCategory(String complaintCategory) {
        this.complaintCategory = complaintCategory;
    }

    public String getComplaintInfo() {
        return complaintInfo;
    }

    public void setComplaintInfo(String complaintInfo) {
        this.complaintInfo = complaintInfo;
    }

    public String getComplaintOccurDate() {
        return complaintOccurDate;
    }

    public void setComplaintOccurDate(String complaintOccurDate) {
        this.complaintOccurDate = complaintOccurDate;
    }

    public String getComplaintResolved() {
        return complaintResolved;
    }

    public void setComplaintResolved(String complaintResolved) {
        this.complaintResolved = complaintResolved;
    }

    public String getComplaintSubmittedBy() {
        return complaintSubmittedBy;
    }

    public void setComplaintSubmittedBy(String complaintSubmittedBy) {
        this.complaintSubmittedBy = complaintSubmittedBy;
    }

    @Override
    public String toString() {
        return "Complaint:"+complaintInfo;
    }
}
