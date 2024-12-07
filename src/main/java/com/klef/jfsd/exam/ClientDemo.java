package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ClientDemo {
    public static void main(String[] args) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory sf = md.getSessionFactoryBuilder().build();

        Session session = sf.openSession();

        try {
            Transaction tr = session.beginTransaction();

            Department newDept = new Department();
            newDept.setName("CSE");
            newDept.setLocation("KLU Vijayawada");
            newDept.setHodName("A.Senthil");
            session.save(newDept);

            tr.commit();
            System.out.println("New department created successfully!");

            tr = session.beginTransaction();

            String hql = "UPDATE Department SET name = ?1, location = ?2 WHERE id = ?3";
            int updatedRows = session.createQuery(hql)
                                      .setParameter(1, "CSE(Honors)")
                                      .setParameter(2, "KLEF Vaddeswaram")
                                      .setParameter(3, 1)
                                      .executeUpdate();

            tr.commit();
            System.out.println("Number of rows updated: " + updatedRows);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sf.close();
        }
    }
}
