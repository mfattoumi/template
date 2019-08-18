package org.cni.intranet.service;
/**/
import java.util.List;

import org.cni.intranet.entities.Country;

public interface CountryService {
	public Country addCountry(Country country);
	public Country deleteCountry(int id);
	public Country updateCountry(Country country);
	public Country getCountryById(int id);
	public List<Country> getAllCountrys();
}