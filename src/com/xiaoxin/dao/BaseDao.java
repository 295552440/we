package com.xiaoxin.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sun.xml.internal.stream.Entity;

public class BaseDao {

	public HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
	public boolean save(Object o) {
		try {
			hibernateTemplate.save(o);
			hibernateTemplate.flush();  
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

}
