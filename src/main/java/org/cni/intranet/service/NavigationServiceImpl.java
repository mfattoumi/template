package org.cni.intranet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.cni.intranet.dto.StructuresByGouvernoratDTO;
import org.cni.intranet.entities.Employee;
import org.cni.intranet.entities.Structure;
import org.cni.intranet.utils.HibernateUtil;



@Service("NavigationService")
public class NavigationServiceImpl implements NavigationService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStructuresByGouvernoratByType(int id) {
		// TODO Auto-generated method stub
		//List<StructuresByGouvernoratDTO> strByGouv = new ArrayList<StructuresByGouvernoratDTO>();
		List<Object[]> result;
		result = new ArrayList<Object[]>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createSQLQuery("SELECT DISTINCT S.structureId as structureId, G.gouvernoratId as gouvernoratId, T.typeId as typeId, D.delegationId as delegationId "
					+ "FROM gouvernorat G INNER JOIN delegation D ON G.gouvernoratId = D.gouvernoratId "
					+ " INNER JOIN local L ON  D.delegationId = L.delegationId "
					+ " INNER JOIN structure S ON  L.structureId=S.structureId"
					+ " INNER JOIN subType ST ON  S.subTypeId=ST.subTypeId"
					+ " INNER JOIN type T ON  ST.typeId=T.typeId"
					+ " where  T.typeId=:id").setParameter("id", id);
			result = query.list();
//					.setResultTransformer(Transformers.aliasToBean(StructuresByGouvernoratDTO.class))
//					.setParameter("id", id).list();
			System.out.println("OBjjjjjj"+result.size());
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			session.close();
			// trns.commit();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStructuresByDelegationByGouv(int id)
	{
		List<Object[]> result;
		result = new ArrayList<Object[]>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createSQLQuery("SELECT DISTINCT S.structureId as structureId, G.gouvernoratId as gouvernoratId, Dom.domainId as domainId, D.delegationId as delegationId FROM gouvernorat G" 
					+" INNER JOIN delegation D ON G.gouvernoratId = D.gouvernoratId"
					+" INNER JOIN local L ON  D.delegationId = L.delegationId"
					+" INNER JOIN structure S ON  L.structureId=S.structureId"
					+" INNER JOIN structureactivitySector SAs ON  S.structureId=SAs.structureId"
					+" INNER JOIN activitySector Ase ON  SAs.activitySectorId=Ase.activitySectorId"
					+" INNER JOIN domain Dom ON  Ase.domainId=Dom.domainId where  G.gouvernoratId=:id").setParameter("id", id);
			result = query.list();
//					.setResultTransformer(Transformers.aliasToBean(StructuresByGouvernoratDTO.class))
//					.setParameter("id", id).list();
			System.out.println("OBjjjjjj"+result.size());
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			session.close();
			// trns.commit();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStructuresByDomainByGouv(int id)
	{
		List<Object[]> result;
		result = new ArrayList<Object[]>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createSQLQuery("SELECT DISTINCT S.structureId as structureId, G.gouvernoratId as gouvernoratId, Dom.domainId as domainId, D.delegationId as delegationId FROM gouvernorat G" 
					+" INNER JOIN delegation D ON G.gouvernoratId = D.gouvernoratId"
					+" INNER JOIN local L ON  D.delegationId = L.delegationId"
					+" INNER JOIN structure S ON  L.structureId=S.structureId"
					+" INNER JOIN structureactivitySector SAs ON  S.structureId=SAs.structureId"
					+" INNER JOIN activitySector Ase ON  SAs.activitySectorId=Ase.activitySectorId"
					+" INNER JOIN domain Dom ON  Ase.domainId=Dom.domainId where  Dom.domainId=:id").setParameter("id", id);
			result = query.list();
//					.setResultTransformer(Transformers.aliasToBean(StructuresByGouvernoratDTO.class))
//					.setParameter("id", id).list();
			System.out.println("OBjjjjjj"+result.size());
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			session.close();
			// trns.commit();
		}
		
		return result;
	}

	@Override
	public List<Employee> getAllEmployeeByStructureId(int id) {
		List<Employee> employees = new ArrayList<Employee>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			employees = session.createQuery("from Employee where structureId="+id).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			session.close();
			// trns.commit();
		}
		return employees;
	}
	
	
	@Override
	public List<Employee> getAllResponsablesByStructureId(int id) {
		List<Employee> employees = new ArrayList<Employee>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			employees = session.createQuery("from Employee where structureId="+id+" and isResponsable=true").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			session.close();
			// trns.commit();
		}
		return employees;
	}
	
	
	@Override
	public List<Employee> getAllContactsByStructureId(int id) {
		List<Employee> employees = new ArrayList<Employee>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			employees = session.createQuery("from Employee where structureId="+id+" and isContact=true").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			session.close();
			// trns.commit();
		}
		return employees;
	}
	
	@Override
	public List<Structure> getAllSubstrutursByStructureId(int id) {
		List<Structure> substructures = new ArrayList<Structure>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			substructures = session.createQuery("from Structure where str_structureId="+id).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			session.close();
			// trns.commit();
		}
		return substructures;
	}
}
