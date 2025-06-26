/**
 * 
 */
package com.eit.gateway.repository;

import java.util.Collection;
import java.util.Date;

/**
 * When to Use JpaRepository and EntityManager?
 * 
 * Use JpaRepository for standard CRUD (Create, Read, Update, Delete)
 * operations. Its methods are convenient and reduce boilerplate code. JPA’s
 * default methods are incredibly effective. They’re easy to implement and
 * provide a clean, minimalistic approach.
 * 
 * Use CustomRepository when you need more control or when JpaRepository methods
 * don't suffice. This includes: 1. Complex queries not supported by
 * JpaRepository. 2. Fine-grained control over entity lifecycle (e.g., when to
 * persist or merge). 3. Named queries. 4. Batch operations. 5. Accessing
 * specific features of the underlying JPA implementation. 6. To perform
 * advanced filtering, multiple joins, or write complex native SQL queries.
 */
public interface CustomRepository {

	String FIND_BY_VIN = "SELECT ct FROM CompanyTrackDevice ct inner join Vehicle v ON v.vehicleDeviceImei=ct.imeiNo WHERE v.vin = :vin";

	public <T> T findByVin(String vin);

	String GET_ACTIVE_IMEI_NUMBERS = "SELECT v.vehicleDeviceImei from Vehicle v where v.status = 'true'";

	public Collection<String> getActiveIMEINumbers();

	String DURATION_CHECK = """
			WITH data AS(
			SELECT
			vin,
			eventtimestamp AS c_event,
			LEAD(eventtimestamp) OVER (PARTITION BY vin ORDER BY eventtimestamp DESC) AS n_event,
			status AS c_status,
			LEAD(status) OVER (PARTITION BY vin ORDER BY eventtimestamp DESC) AS n_status
			FROM mvt.vehicleevent
			WHERE vin = :vin
			AND DATE(eventtimestamp) = :date
			ORDER BY eventtimestamp DESC
			)
			SELECT
			COALESCE(
			    cast(EXTRACT(EPOCH FROM(MAX(c_event) - (SELECT c_event FROM data WHERE c_status <> n_status LIMIT 1))) as bigint),
			    0
			) AS timing
			FROM data
			""";

	public long getDuration(String vin, Date date);

	String GET_OVERSPEED_CONFIG_RANGE = "SELECT ac.alertconfig FROM AlertConfig ac WHERE ac.vin = :vin AND ac.alerttype = :alertType";
	public String getOverspeedRange(String vin, String alertType);

}
