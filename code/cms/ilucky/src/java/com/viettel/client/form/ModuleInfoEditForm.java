package com.viettel.client.form;

/**
 *
 * @author tiennv_telsoft
 */
public class ModuleInfoEditForm {

    private String code;
    private String name;
    private String email;
    private String msisdn;
    private String description;
    private Double status;
    private String strStatus;

    public ModuleInfoEditForm() {
    }

    public ModuleInfoEditForm(String code, String name, String email, String msisdn, String description, Double status, String strStatus) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.msisdn = msisdn;
        this.description = description;
        this.status = status;
        this.strStatus = strStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getStatus() {
        return status;
    }

    public void setStatus(Double status) {
        this.status = status;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

}
