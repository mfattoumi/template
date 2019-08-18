package org.cni.intranet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.cni.intranet.dao.LocalDao;
import org.cni.intranet.entities.FaxLocal;
import org.cni.intranet.entities.Local;
import org.cni.intranet.entities.PhoneLocal;
import org.cni.intranet.utils.HibernateUtil;


@Service("LocalService")
public class LocalServiceImpl implements LocalService, Serializable {

	@Autowired
	private LocalDao localDao; 

	public void setLocalDao(LocalDao localDao) {
		this.localDao = localDao;
	}

	@Override
	public Local addLocal(Local local) {
		return localDao.addLocal(local);
	}

	@Override
	public Local deleteLocal(int id) {
		return localDao.deleteLocal(id);
	}

	@Override
	public Local updateLocal(Local local) {
		return localDao.updateLocal(local);
	}

	@Override
	public Local getLocalById(int id) {
		return localDao.getLocalById(id);
	}

	@Override
	public List<Local> getAllLocals() {
		return localDao.getAllLocals();
	}
	
	@Override
	public List<FaxLocal> getFaxesByLocal(int idLocal) {
		List<FaxLocal> faxesLocal = new ArrayList<FaxLocal>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			faxesLocal = session.createQuery("from FaxLocal where localId = "+idLocal).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return faxesLocal;
	}
	
	@Override
	public List<PhoneLocal> getPhonesByLocal(int idLocal) {
		List<PhoneLocal> phonesLocal = new ArrayList<PhoneLocal>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			phonesLocal = session.createQuery("from PhoneLocal where localId = "+idLocal).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return phonesLocal;
	}

}
