package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.CountryDao;
import org.cni.intranet.entities.Country;

@Transactional
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryDao countryDao;

	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}
	
	@Override
	public Country addCountry(Country country) {
		return countryDao.addCountry(country);
	}

	@Override
	public Country deleteCountry(int id) {
		return countryDao.deleteCountry(id);
	}

	@Override
	public Country updateCountry(Country country) {
		return countryDao.updateCountry(country);
	}

	@Override
	public Country getCountryById(int id) {
		return countryDao.getCountryById(id);
	}

	@Override
	public List<Country> getAllCountrys() {
		return countryDao.getAllCountrys();
	}

}
