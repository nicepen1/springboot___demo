package com.example.springboot;

import controller.EmployeeMethods;
import models.EmployeeEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class SpringbootApplication {
    private static SessionFactory sessionFactory;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @RequestMapping("/errorPage")
    public ModelAndView errorPage() {
        return new ModelAndView("errorPage");
    }

    //List all employee
    @RequestMapping("/")
    public ModelAndView home() {
        EmployeeMethods ec = new EmployeeMethods();
        List<EmployeeEntity> et = ec.List(sessionFactory);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", et);
        return new ModelAndView("index", model);
    }

    @RequestMapping("/addPage")
    public ModelAndView addPage() {
        return new ModelAndView("addEmployee");
    }

    //Add an Employee
    @RequestMapping(method = RequestMethod.POST, value = "/addEmployee")
    public ModelAndView addEmployee(@ModelAttribute("name") String name, @ModelAttribute("surname") String surname, @ModelAttribute("salary") String salary) {
        if(name==null || surname==null || name.equals("") || surname.equals("")) {
            return new ModelAndView("redirect:/errorPage");
        } else {
            EmployeeMethods ec = new EmployeeMethods();
            EmployeeEntity et = ec.Insert(sessionFactory, name, surname, Integer.parseInt(salary));  //inserting an employee
            return new ModelAndView("redirect:/");
        }
    }

    //Delete an Employee
    @RequestMapping(params = "delete", value = "/employee/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteEmployee(@PathVariable Long id) {
        EmployeeMethods ec = new EmployeeMethods();
        ec.DeleteEmployee(sessionFactory, Integer.parseInt(id.toString()));
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(params = "update", value = "/updateEmployee/{id}", method = RequestMethod.POST)
    public ModelAndView updateEmployee(@PathVariable Long id) {
        Map<String, Object> model = new HashMap<String, Object>();
        EmployeeMethods ec = new EmployeeMethods();
        EmployeeEntity emp = ec.getById(id.toString(), sessionFactory);
        model.put("employee", emp);
        return new ModelAndView("updateEmployee", model);
    }

    //Update Employee
    @RequestMapping(method = RequestMethod.POST, value = "/updateEmployeeHandler")
    public ModelAndView updateEmployeeHandler(@ModelAttribute("employeeId") String employeeId, @ModelAttribute("name") String name, @ModelAttribute("surname") String surname, @ModelAttribute("salary") String salary) {
        if(employeeId==null || name==null || surname==null || salary==null || name.equals("") || surname.equals("") || salary.equals("")) {
            return new ModelAndView("redirect:/errorPage");
        }
        EmployeeMethods ec = new EmployeeMethods();
        EmployeeEntity ee = new EmployeeEntity();
        ee.setEmployeeId(Integer.parseInt(employeeId));
        ee.setName(name);
        ee.setSurname(surname);
        ee.setSalary(Integer.parseInt(salary));
        ec.updateEmployee(ee, sessionFactory);
        return new ModelAndView("redirect:/");
    }
}
