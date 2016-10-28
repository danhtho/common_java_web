package com.tctvn.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tctvn.dao.StaffDao;
import com.tctvn.entity.Staff;
import com.tctvn.exceptions.DataNotFoundException;
import com.tctvn.form.StaffForm;
import com.tctvn.page.StaffPage;
import com.tctvn.utils.DateUtils;
import com.tctvn.utils.EnumUtils;

@Controller
@RequestMapping(value="/staff")
public class StaffController {
    @Autowired
    StaffDao staffDao;
    @Autowired
    StaffForm staffForm;
    
    @RequestMapping(value="/list")
    public String list(Model model) {
        List<Staff> listStaff = staffDao.listStaff();
        List<StaffPage> pages = new ArrayList<StaffPage>();
        for (Staff staff : listStaff) {
            StaffPage page = new StaffPage();
            BeanUtils.copyProperties(staff, page);
            page.setBirthday(DateUtils.dateToString(staff.getBirthday()));
            Staff.GENDER gender = EnumUtils.valueOf(Staff.GENDER.class, staff.getGender());
            page.setGender(gender.getLabel());
            Staff.POSITION position = EnumUtils.valueOf(Staff.POSITION.class, staff.getPosition());
            page.setPosition(position.getLabel());
            pages.add(page);
        }
        model.addAttribute("listStaff", pages);
        return "listStaff";
    }
    
    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String register(@ModelAttribute("staffForm") StaffForm staffForm, Model model) {
        return "registerStaff";
    }
    
    @RequestMapping(value="/register", method={RequestMethod.POST})
    public String doRegister(@ModelAttribute("staffForm") @Valid StaffForm staffForm, BindingResult result, Model model) {
        this.staffForm.validate(staffForm, result);
        if (result.hasErrors()) {
            model.addAttribute("staffForm", staffForm);
            return "registerStaff";
        }
        Staff staff = new Staff();
        BeanUtils.copyProperties(staffForm, staff);
        staff.setBirthday(DateUtils.stringToDate(staffForm.getBirthday()));
        staff.setGender(Integer.valueOf(staffForm.getGender()));
        staff.setPosition(Integer.valueOf(staffForm.getPosition()));
        staffDao.insert(staff);
        return "redirect:/staff/list";
    }
    
    @RequestMapping(value="/view/{id}", method=RequestMethod.GET)
    public String view(@PathVariable("id") Integer id, Model model) throws Exception {
        Staff staff = staffDao.getStaff(id);
        if (staff != null) {
            StaffForm staffForm = new StaffForm();
            BeanUtils.copyProperties(staff, staffForm);
            staffForm.setGender(String.valueOf(staff.getGender()));
            staffForm.setId(String.valueOf(staff.getId()));
            staffForm.setPosition(String.valueOf(staff.getPosition()));
            staffForm.setBirthday(DateUtils.dateToString(staff.getBirthday()));
            staffForm.setMode(StaffForm.MODE.VIEW.getKey());
            model.addAttribute("staffForm", staffForm);
        } else {
            throw new DataNotFoundException();
        }
        return "registerStaff";
    }
    
    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public String delete(@PathVariable("id") Integer id, Model model) throws Exception {
        Staff staff = staffDao.getStaff(id);
        if (staff != null) {
            staffDao.delete(staff);
        } else {
            throw new DataNotFoundException();
        }
        return "redirect:/staff/list";
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model) throws Exception {
        Staff staff = staffDao.getStaff(id);
        if (staff != null) {
            StaffForm staffForm = new StaffForm();
            BeanUtils.copyProperties(staff, staffForm);
            staffForm.setGender(String.valueOf(staff.getGender()));
            staffForm.setId(String.valueOf(staff.getId()));
            staffForm.setPosition(String.valueOf(staff.getPosition()));
            staffForm.setBirthday(DateUtils.dateToString(staff.getBirthday()));
            staffForm.setMode(StaffForm.MODE.EDIT.getKey());
            model.addAttribute("staffForm", staffForm);
        } else {
            throw new DataNotFoundException();
        }
        return "registerStaff";
    }
    
    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String doEdit(@ModelAttribute("staffForm") @Valid StaffForm staffForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registerStaff";
        }
        Staff staff = staffDao.getStaff(Integer.valueOf(staffForm.getId()));
        BeanUtils.copyProperties(staffForm, staff);
        staff.setBirthday(DateUtils.stringToDate(staffForm.getBirthday()));
        staff.setGender(Integer.valueOf(staffForm.getGender()));
        staff.setPosition(Integer.valueOf(staffForm.getPosition()));
        staffDao.update(staff);
        return "redirect:/staff/list";
    }
    
    @ModelAttribute("genders")
    public Map<String, String> genderList() {
        Map<String, String> genders = EnumUtils.convertMap(Staff.GENDER.class);
        return genders;
    }
    
    @ModelAttribute("positions")
    public Map<String, String> positionList() {
        Map<String, String> positions = EnumUtils.convertMap(Staff.POSITION.class);
        return positions;
    }
}
