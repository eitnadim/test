/**
 * 
 */
package com.eit.gateway.repository;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

/**
 * 
 */
@Repository
public class CustomRepositoryImpl implements CustomRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomRepositoryImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public <T> T findByVin(String vin) {

		try {
			Query query = em.createQuery(FIND_BY_VIN);
			query.setParameter("vin", vin);

			return (T) query.getSingleResult();

		} catch (NoResultException e) {
			LOGGER.error("Coudln't load CompanyTrackService for the vin {}", vin);
			return null; // Or handle the case where no result is found
		} catch (NonUniqueResultException e) {
			LOGGER.error("More than one CompanyTrackService found for the vin {}", vin);
			return null;
		}
	}

	@Override
	public Collection<String> getActiveIMEINumbers() {

		Query query = em.createQuery(GET_ACTIVE_IMEI_NUMBERS);
		return query.getResultList();
	}

	@Override
	@Transactional
	public long getDuration(String vin, Date date) {
	    Query query = em.createNativeQuery(DURATION_CHECK);
	    query.setParameter("vin", vin);
	    query.setParameter("date", date);
	    
	    try {
	        Object result = query.getSingleResult();
	        return result != null ? ((Number) result).longValue() : 0L;
	    } catch (NoResultException e) {
	        return 0L;
	    }
	}

	@Override
	public String getOverspeedRange(String vin, String alertType) {
		Query query = em.createQuery(GET_OVERSPEED_CONFIG_RANGE);
		query.setParameter("vin", vin);
		query.setParameter("alertType", alertType);

		return (String) query.getSingleResult();
	}
	
	@Override
	public Object[] getTripDetails(String vin, String time, String rFID) {
		java.sql.Time sqlTime = java.sql.Time.valueOf(time);
		Query query = em.createQuery(GET_TRIP_DETAILS);
		query.setParameter("vin", vin);
		query.setParameter("time", sqlTime);
		query.setParameter("rFID", rFID);
		try {
			return (Object[]) query.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.error("No trip details found for VIN: {}, RFID: {}", vin, rFID);
		} catch (NonUniqueResultException e) {
			LOGGER.error("Multiple trip details found for VIN: {}, RFID: {}", vin, rFID);
		}
		return null;
	}

}
