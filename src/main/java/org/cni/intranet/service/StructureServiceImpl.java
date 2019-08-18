package org.cni.intranet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.cni.intranet.dao.StructureDao;
import org.cni.intranet.entities.Category;
import org.cni.intranet.entities.Local;
import org.cni.intranet.entities.Structure;
import org.cni.intranet.utils.HibernateUtil;


@Service("StructureService")
public class StructureServiceImpl implements StructureService {

	@Autowired
	private StructureDao structureDao;

	public void setStructureDao(StructureDao structureDao) {
		this.structureDao = structureDao;
	}

	@SuppressWarnings({ "unused", "null" })
	@Override
	public void indexStructures() {
		System.out.println("debut index Service");
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			System.out.println("debut index DAO TRY");
			trns = session.beginTransaction();
			FullTextSession fullTextSession = Search
					.getFullTextSession(session);

			// trns=fullTextSession.beginTransaction();
			fullTextSession.createIndexer().startAndWait();
			// fullTextSession.f
			trns.commit();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public Structure addStructure(Structure structure) {
		return structureDao.addStructure(structure);
	}

	@Override
	public Structure deleteStructure(int id) {
		return structureDao.deleteStructure(id);
	}

	@Override
	public Structure updateStructure(Structure structure) {
		return structureDao.updateStructure(structure);
	}

	@Override
	public Structure getStructureById(int id) {
		return structureDao.getStructureById(id);
	}

	@Override
	public List<Structure> getAllStructures() {
		return structureDao.getAllStructures();
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<Structure> searchForStructure(String searchText) {
		List<Structure> results = new ArrayList<Structure>();

		Transaction trns = null;

		Session session = HibernateUtil.getSessionFactory().openSession();

		FullTextSession fullTextSession = Search.getFullTextSession(session);
		trns = fullTextSession.beginTransaction();

		StringTokenizer searchTerms = new StringTokenizer(searchText, " ");
		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Structure.class).get();

		BooleanJunction bool = qb.bool();
		while (searchTerms.hasMoreElements()) {

			bool = bool.must(qb
					.keyword()
					.onFields("latinLongName", "arabicLongName", "acronym",
							"activitySectors.latinName",
							"activitySectors.arabicName", "category.latinName",
							"category.arabicName", "subType.latinName",
							"subType.arabicName")
					.matching(searchTerms.nextElement()).createQuery());
		}

		org.apache.lucene.search.Query query = bool.createQuery();

		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, Structure.class);

		results = hibQuery.list();
		trns.commit();
		session.close();
		return results;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<Structure> searchAdvForStructure(String searchText,
			String searchText1, String searchText2, String searchText3,
			String searchText4) {
		List<Structure> results = new ArrayList<Structure>();

		Transaction trns = null;

		Session session = HibernateUtil.getSessionFactory().openSession();

		FullTextSession fullTextSession = Search.getFullTextSession(session);
		trns = fullTextSession.beginTransaction();
		System.out.println("CURRENT TERMDAO" + searchText);
		// String[] searchTerms = searchText.split("\\s+");
		StringTokenizer searchTerms = new StringTokenizer(searchText, " ");
		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Structure.class).get();
		// org.apache.lucene.search.Query query = qb.keyword()
		// .onFields("latinLongName", "arabicLongName", "acronym",
		// "activitySectors.latinName", "activitySectors.arabicName",
		// "category.latinName","category.arabicName",
		// "subType.latinName","subType.arabicName")
		// .matching(searchText).createQuery();
		BooleanJunction bool = qb.bool();

		// for (int j = 0; j < searchTerms.length; j++) {
		// String currentTerm = searchTerms[j];
		// System.out.println("CURRENT TERMDAO"+currentTerm);
		// bool = bool.must(qb.keyword()
		// .onFields("latinLongName", "arabicLongName", "acronym", "keyword",
		// "category.latinName","category.arabicName",
		// "subType.type.latinName","subType.type.arabicName","locals.latinAdress",
		// "locals.arabicAdress",
		// "activitySectors.domain.arabicName","activitySectors.domain.latinName")
		// .matching(currentTerm).createQuery())
		// ;
		//
		//
		// }
		if(!searchText1.equals("#"))
		bool = bool.must(qb
				.phrase()
				.onField("locals.delegation.gouvernorat.latinName")
				.sentence(searchText1).createQuery());
		else System.out.println("Gouv"+searchText1);
		if(!searchText2.equals("#"))
		bool = bool.must(qb
				.phrase()
				.onField("locals.delegation.latinName").sentence(searchText2)
				.createQuery());
		else System.out.println("deleg"+searchText2);
		if(!searchText3.equals("#"))
		bool = bool.must(qb
				.phrase()
				.onField("activitySectors.domain.latinName")
				.sentence(searchText3).createQuery());
		else System.out.println("secteur"+searchText3);
		if(!searchText4.equals("#")){
		bool = bool.must(qb.phrase()
				.onField("subType.type.latinName")
				.sentence(searchText4).createQuery());
		System.out.println("type"+searchText4);
		}
		else System.out.println("type"+searchText4);
		
		if(!searchText.equals(""))
		{
			System.out.println("ddd");
		while (searchTerms.hasMoreElements()) {

			bool = bool.must(qb
					.keyword()
					.onFields("latinLongName", "arabicLongName", "acronym",
							"keyword", "category.latinName",
							"category.arabicName", "locals.latinAdress",
							"locals.arabicAdress")
					.matching(searchTerms.nextElement()).createQuery());

		}
		}
		else System.out.println("libre"+searchText);

		org.apache.lucene.search.Query query = bool.createQuery();

		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, Structure.class);

		results = hibQuery.list();
		trns.commit();
		session.close();
		return results;
	}

	@Override
	public List<Structure> searchRapidForStructure(String searchText,	String searchText1, String searchText2) {
		// TODO Auto-generated method stub
		
		
	List<Structure> results = new ArrayList<Structure>();

	Transaction trns = null;

	Session session = HibernateUtil.getSessionFactory().openSession();

	FullTextSession fullTextSession = Search.getFullTextSession(session);
	trns = fullTextSession.beginTransaction();
	System.out.println("CURRENT TERMDAO" + searchText);
	
	StringTokenizer searchTerms = new StringTokenizer(searchText, " ");
	QueryBuilder qb = fullTextSession.getSearchFactory()
			.buildQueryBuilder().forEntity(Structure.class).get();
	
	BooleanJunction bool = qb.bool();

	if(!searchText1.equals("#"))
	{
	bool = bool.must(qb
			.phrase()
			.onField("locals.delegation.gouvernorat.latinName")
			.sentence(searchText1).createQuery());
	System.out.println("GOUVVVVV"+searchText1);}
	else System.out.println("Gouv"+searchText1);
	if(!searchText2.equals("#"))
	bool = bool.must(qb
			.phrase()
			.onField("activitySectors.domain.latinName")
			.sentence(searchText2).createQuery());
	else System.out.println("secteur"+searchText2);
	
	if(!searchText.equals(""))
	{
		System.out.println("ddd");
	while (searchTerms.hasMoreElements()) {

		bool = bool.must(qb
				.keyword()
				.onFields("latinLongName", "arabicLongName", "acronym",
						"keyword", "category.latinName",
						"category.arabicName", "locals.latinAdress",
						"locals.arabicAdress")
				.matching(searchTerms.nextElement()).createQuery());

	}
	}
	else System.out.println("libre"+searchText);

	org.apache.lucene.search.Query query = bool.createQuery();

	org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
			query, Structure.class);

	results = hibQuery.list();
	trns.commit();
	session.close();
	return results;
	}
	
	@Override
	public List<Local> getLocalsByStructure(int idStructure){
		List<Local> locals = new ArrayList<Local>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createSQLQuery("SELECT L.* "
					+ "FROM local L "
					+ " INNER JOIN structure S ON  L.structureId = S.structureId "
					+ " where  S.structureId=:id").addEntity("local", Local.class).setParameter("id", idStructure);
			locals = query.list();	
		System.out.println(query);	
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
//		for(Local o :locals){
//			//System.out.println("local "+i+" "+((((List<Local>)(Object)locals).get(i).getArabicAdress())));
//			System.out.println(((Local)o).getLocalId());
//		}
		return locals;
	}
	
//	public static void main(String[] args) {
//		StructureServiceImpl structureServiceImpl = new StructureServiceImpl();		
//		List<Local> list = structureServiceImpl.getLocalsByStructure(50);
//		for(Local o :list){
//			//System.out.println("local "+i+" "+((((List<Local>)(Object)locals).get(i).getArabicAdress())));
//			
//			System.out.println(o.getLocalId());
//		}
//	}

}
