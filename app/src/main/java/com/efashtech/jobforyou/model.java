package com.efashtech.jobforyou;

public class model {
    private String Aadhar_number;
    private String Name;
    private String alternate_phone;
    private String cast_category;
    private String current_address;
    private String department;
    private String dob;
    private String experience;
    private String expertise;
    private String father_name;
    private String gender;
    private String job_category;
    private String operator_category;
    private String permanent_address;
    private String phone;
    private String employee_age;
    private String purl;

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public model(String employee_age) {
        this.employee_age = employee_age;
    }

    public String getEmployee_age() {
        return employee_age;
    }

    public void setEmployee_age(String employee_age) {
        this.employee_age = employee_age;
    }

    public model() {
    }

    public model(String aadhar_number, String name, String alternate_phone, String cast_category, String current_address, String department, String dob, String experience, String expertise, String father_name, String gender, String job_category, String operator_category, String permanent_address, String phone, String preferred_location, String previous_company, String religion, String specialized_operations, String userCity, String userPincode) {
        Aadhar_number = aadhar_number;
        Name = name;
        this.alternate_phone = alternate_phone;
        this.cast_category = cast_category;
        this.current_address = current_address;
        this.department = department;
        this.dob = dob;
        this.experience = experience;
        this.expertise = expertise;
        this.father_name = father_name;
        this.gender = gender;
        this.job_category = job_category;
        this.operator_category = operator_category;
        this.permanent_address = permanent_address;
        this.phone = phone;
        this.preferred_location = preferred_location;
        this.previous_company = previous_company;
        this.religion = religion;
        this.specialized_operations = specialized_operations;
        this.userCity = userCity;
        this.userPincode = userPincode;
    }

    public String getAadhar_number() {
        return Aadhar_number;
    }

    public void setAadhar_number(String aadhar_number) {
        Aadhar_number = aadhar_number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAlternate_phone() {
        return alternate_phone;
    }

    public void setAlternate_phone(String alternate_phone) {
        this.alternate_phone = alternate_phone;
    }

    public String getCast_category() {
        return cast_category;
    }

    public void setCast_category(String cast_category) {
        this.cast_category = cast_category;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob_category() {
        return job_category;
    }

    public void setJob_category(String job_category) {
        this.job_category = job_category;
    }

    public String getOperator_category() {
        return operator_category;
    }

    public void setOperator_category(String operator_category) {
        this.operator_category = operator_category;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPreferred_location() {
        return preferred_location;
    }

    public void setPreferred_location(String preferred_location) {
        this.preferred_location = preferred_location;
    }

    public String getPrevious_company() {
        return previous_company;
    }

    public void setPrevious_company(String previous_company) {
        this.previous_company = previous_company;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getSpecialized_operations() {
        return specialized_operations;
    }

    public void setSpecialized_operations(String specialized_operations) {
        this.specialized_operations = specialized_operations;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserPincode() {
        return userPincode;
    }

    public void setUserPincode(String userPincode) {
        this.userPincode = userPincode;
    }

    private String preferred_location;
    private String previous_company;
    private String religion;
    private String specialized_operations;
    private String userCity;
    private String userPincode;
}
