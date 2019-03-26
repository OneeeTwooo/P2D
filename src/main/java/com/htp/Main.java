package com.htp;

import com.htp.config.AppConfig;
import com.htp.domain.Department;
import com.htp.domain.Factory;
import com.htp.domain.User;
import com.htp.repository.DepartmentDao;
import com.htp.repository.FactoryDao;
import com.htp.repository.UserDao;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
//        Car myCar = (Car) context.getBean("myCar");
//        System.out.println("Simple " + myCar.getModel());
//
//        Car myCar2 = (Car) context.getBean("myCar2");
//
//        System.out.println("With engine " + myCar2.getEngine().getVolume());
//
//        ApplicationContext context = new AnnotationConfigApplicationContext("com.htp");
//        Car myCar = (Car) context.getBean("supercar");
//        System.out.println("Simple " + myCar.getModel());
//        System.out.println("Engine inside car " + myCar.getEngine().getVolume());
//
//        Engine carEngine = (Engine) context.getBean("carEngine");
//        System.out.println("Engine info :" + carEngine.getVolume());

        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserDao userDao = (UserDao) context.getBean("userDaoImpl");
        for (User user : userDao.findAll()) {
            System.out.println(user.toString());
        }

        DepartmentDao departmentDao = (DepartmentDao) context.getBean("departmentDaoImpl");
        for (Department department : departmentDao.findAll()) {
            System.out.println(department.toString());
        }

        FactoryDao factoryDao = (FactoryDao) context.getBean("factoryDaoImpl");
        for (Factory factory : factoryDao.findAll()) {
            System.out.println(factory.toString());
        }

    }
}
