package net.devk.hl7.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import net.devk.hl7.server.resources.patient.PatientResourceProvider;

@WebServlet("/*")
public class DemoRestfulServer extends RestfulServer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void initialize() throws ServletException {
		// Create a context for the appropriate version
		setFhirContext(FhirContext.forR4());

		// Register resource providers
		registerProvider(new PatientResourceProvider());

		// Format the responses in nice HTML
		registerInterceptor(new ResponseHighlighterInterceptor());
	}
}
