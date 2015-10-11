package in.sandz.careerfairnc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Company {

    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("active_count")
    @Expose
    private Integer activeCount;
    @SerializedName("applicants_count")
    @Expose
    private Integer applicantsCount;

    /**
     * @return The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName The company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return The activeCount
     */
    public Integer getActiveCount() {
        return activeCount;
    }

    /**
     * @param activeCount The active_count
     */
    public void setActiveCount(Integer activeCount) {
        this.activeCount = activeCount;
    }

    /**
     * @return The applicantsCount
     */
    public Integer getApplicantsCount() {
        return applicantsCount;
    }

    /**
     * @param applicantsCount The applicants_count
     */
    public void setApplicantsCount(Integer applicantsCount) {
        this.applicantsCount = applicantsCount;
    }

    @Override
    public String toString() {
        return this.companyName;
    }

}