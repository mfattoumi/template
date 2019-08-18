package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Country;

public interface CountryDao {
	public Country addCountry(Country country);
	public Country deleteCountry(int id);
	public Country updateCountry(Country country);
	public Country getCountryById(int id);
	public List<Country> getAllCountrys();
}