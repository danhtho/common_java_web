package com.tctvn.form;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tctvn.annotation.DateFormat;
import com.tctvn.annotation.EnumRange;
import com.tctvn.dao.StaffDao;
import com.tctvn.entity.Staff;
import com.tctvn.utils.DateUtils;

@Component
public class StaffForm implements Validator {
    private String id;
    @NotEmpty(message="{validator.require}")
    private String name;
    private String kananame;
    @DateFormat(dateFormat="yyyy/MM/dd", message="{validator.date.format}", arg = { "Birthday", "yyyy/MM/dd" })
    private String birthday;
    
    @Email(message="{validator.email}")
    private String email;
    
    @EnumRange(message="{validator.enum.invalid}", enumClass = Staff.GENDER.class, arg = { "Gender" })
    private String gender;
    
    @Pattern(regexp="^$|[0-9]+", message="{validator.mobile}")
    private String mobile;
    
    @EnumRange(message="{validator.enum.invalid}", enumClass = Staff.POSITION.class, arg = { "Position" })
    private String position;
    private Integer mode;
    
    @Autowired
    StaffDao staffDao;
    
    public enum MODE {
        VIEW(2, "View"),
        EDIT(1, "Edit"),
        REGISTER(0, "Register"),
        ;
        private final Integer key;
        private final String value;
        MODE(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
        public Integer getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
    }
    public StaffForm() {
        this.setMode(StaffForm.MODE.REGISTER.getKey());
        this.setGender("0");
    }
    public boolean supports(Class<?> arg0) {
        return StaffForm.class.equals(arg0);
    }
    public void validate(Object obj, Errors errors) {
        StaffForm staffForm = (StaffForm) obj;
        if (StringUtils.isNotBlank(staffForm.getName()) && StringUtils.isNotBlank(staffForm.getKananame())
                && StringUtils.isNotBlank(staffForm.getBirthday())) {
            Date birthday = DateUtils.stringToDate(staffForm.getBirthday());
            Staff staff = staffDao.findDuplicateStaff(staffForm.getName(), staffForm.getKananame(), birthday);
            if (staff != null) {
                errors.rejectValue("", "validator.duplicate.staff");
            }
        }
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getKananame() {
        return kananame;
    }
    public void setKananame(String kananame) {
        this.kananame = kananame;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public Integer getMode() {
        return mode;
    }
    public void setMode(Integer mode) {
        this.mode = mode;
    }
}
