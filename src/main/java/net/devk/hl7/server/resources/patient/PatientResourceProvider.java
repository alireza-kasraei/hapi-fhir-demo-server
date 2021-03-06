package net.devk.hl7.server.resources.patient;

import java.util.HashMap;
import java.util.Map;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

public class PatientResourceProvider implements IResourceProvider {

	private Map<String, Patient> myPatients = new HashMap<>();

	/**
	 * Constructor
	 */
	public PatientResourceProvider() {
		Patient pat1 = new Patient();
		pat1.setId("1");
		pat1.addIdentifier().setSystem("http://acme.com/MRNs").setValue("7000135");
		pat1.addName().setFamily("Simpson").addGiven("Homer").addGiven("J");
		myPatients.put("1", pat1);
	}

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Patient.class;
	}

	/**
	 * Simple implementation of the "read" method
	 */
	@Read()
	public Patient read(@IdParam IdType theId) {
		Patient retVal = myPatients.get(theId.getIdPart());
		if (retVal == null) {
			throw new ResourceNotFoundException(theId);
		}
		return retVal;
	}

}