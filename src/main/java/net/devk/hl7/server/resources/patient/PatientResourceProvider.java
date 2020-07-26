package net.devk.hl7.server.resources.patient;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.model.primitive.UriDt;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
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

	@Search()
	public List<Patient> getPatient(@RequiredParam(name = Patient.SP_FAMILY) StringParam theFamilyName) {
		Patient patient = new Patient();
		patient.addIdentifier();
		patient.getIdentifier().get(0).setUse(IdentifierUse.OFFICIAL);
		patient.getIdentifier().get(0).setSystem(new UriDt("urn:hapitest:mrns").getValue());
		patient.getIdentifier().get(0).setValue("00001");
		patient.setId("123");
		patient.addName();
		patient.getName().get(0).setFamily(theFamilyName.getValue());
		patient.getName().get(0).addGiven("PatientOne");
		patient.setGender(AdministrativeGender.MALE);
		return Collections.singletonList(patient);
	}

}