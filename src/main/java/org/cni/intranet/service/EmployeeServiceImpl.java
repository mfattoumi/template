package org.cni.intranet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.cni.intranet.dao.StructureDao;
import org.cni.intranet.entities.Structure;
import org.cni.intranet.dao.EmployeeDao;
import org.cni.intranet.entities.Employee;
import org.cni.intranet.utils.HibernateUtil;


@Service("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public void indexEmployees() {
		System.out.println("debut index Employee DAO");
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
	      
       
        try {
        	System.out.println("debut index DAO TRY");
        	 FullTextSession fullTextSession = Search.getFullTextSession(session);
			fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// session.flush();
			session.close();
		}
		
	}
	
	@Override
	public Employee addEmployee(Employee employee) {
		return employeeDao.addEmployee(employee);
	}

	@Override
	public Employee deleteEmployee(int id) {
		return employeeDao.deleteEmployee(id);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		return employeeDao.updateEmployee(employee);
	}

	@Override
	public Employee getEmployeeById(int id) {
		return employeeDao.getEmployeeById(id);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}
	
	@Override
	public List<Employee> searchForEmployee(String searchText) {
		List<Employee> results=new ArrayList<Employee>();
		
		Transaction trns = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		FullTextSession fullTextSession = Search.getFullTextSession(session);

		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Employee.class).get();
		org.apache.lucene.search.Query query = qb.keyword()
				.onFields("latinName", "arabicName", "latinFirstName","arabicFirstName", "structure.acronym", "structure.keyword", "structure.latinLongName","structure.arabicLongName")
				.matching(searchText).createQuery();

		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, Employee.class);
		
		results = hibQuery.list();
		return results;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<Employee> searchRapidForEmployee(  
			String function, String nomEtPrenom) {
		
		List<Employee> results = new ArrayList<Employee>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		
		trns = fullTextSession.beginTransaction();
		System.out.println("CURRENT TERMDAO" + nomEtPrenom);
		StringTokenizer searchTerms = new StringTokenizer(nomEtPrenom, " ");
		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Employee.class).get();
		
		BooleanJunction bool = qb.bool();
		
		if(!function.equals("#"))
		bool = bool.must(qb.phrase().onField("function.latinName")
				.sentence(function).createQuery());
		else System.out.println("function "+function);		
		
		if(!nomEtPrenom.equals(""))	{
			System.out.println("ddd");
			while (searchTerms.hasMoreElements()) {
				bool = bool.must(qb.keyword().onFields("latinName", "arabicName", "latinFirstName",	"arabicFirstName","structure.latinLongName","structure.arabicLongName")
					.matching(searchTerms.nextElement()).createQuery());
			}
		}
		else System.out.println("nomEtPrenom "+nomEtPrenom);

		org.apache.lucene.search.Query query = bool.createQuery();
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Employee.class);

		results = hibQuery.list();
		trns.commit();
		session.close();
		return results;
	}

	@Override
	public List<Employee> searchAdvForEmployee(String searchedGrade,
			String searchedFunction, String empSearchAdv,
			String selectedGouvernoratLatinName,
			String selectedStructureLatinName) {
		// TODO Auto-generated method stub
		List<Employee> results = new ArrayList<Employee>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		
		trns = fullTextSession.beginTransaction();
		System.out.println("CURRENT TERMDAO" + empSearchAdv);
		StringTokenizer searchTerms = new StringTokenizer(empSearchAdv, " ");
		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Employee.class).get();
		
		BooleanJunction bool = qb.bool();
		
		if(!searchedGrade.equals("#"))
		bool = bool.must(qb.phrase().onField("grade.latinName")
				.sentence(searchedGrade).createQuery());
		else System.out.println("Grade "+searchedGrade);
		
		if(!searchedFunction.equals("#"))
		bool = bool.must(qb.phrase().onField("function.latinName")
				.sentence(searchedFunction).createQuery());
		else System.out.println("function "+searchedFunction);
		
		if(!selectedGouvernoratLatinName.equals("#"))
			bool = bool.must(qb.phrase().onField("structure.locals.delegation.gouvernorat.latinName")
					.sentence(selectedGouvernoratLatinName).createQuery());
			else System.out.println("gouvernorat "+selectedGouvernoratLatinName);
		
		if(!selectedStructureLatinName.equals("#"))
			bool = bool.must(qb.phrase().onField("structure.latinLongName")
					.sentence(selectedStructureLatinName).createQuery());
			else System.out.println("structure "+selectedStructureLatinName);	
		
		if(!empSearchAdv.equals(""))	{
			System.out.println("ddd");
			while (searchTerms.hasMoreElements()) {
				bool = bool.must(qb.keyword().onFields("latinName", "arabicName", "latinFirstName",	"arabicFirstName")
					.matching(searchTerms.nextElement()).createQuery());
			}
		}
		else System.out.println("nomEtPrenom "+empSearchAdv);

		org.apache.lucene.search.Query query = bool.createQuery();
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Employee.class);

		results = hibQuery.list();
		trns.commit();
		session.close();
		return results;
	}
}
