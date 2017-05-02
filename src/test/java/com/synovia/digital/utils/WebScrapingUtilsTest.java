package com.synovia.digital.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.synovia.digital.exceptions.EavTechnicalException;

import static com.synovia.digital.utils.WebScrapingUtils.*;

import java.io.IOException;


@RunWith(Theories.class)
public class WebScrapingUtilsTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	

	@Test
	public void testGetPrevClose_Null() throws EavTechnicalException{
		String url = null;

		thrown.expect(EavTechnicalException.class);
		thrown.expectMessage(XWEBSCRAP_URL_NULL);
			getPrevDayClose(url);
	}
	
	
	@Theory
	public void testIsDouble(String url) {
		try {
			Object output = getPrevDayClose(url);
			Assert.assertTrue(output instanceof Double);
			System.out.println("getPrevDayClose("
					+ url + "): " + output);
		} catch (Exception e) {
			Assert.fail("Should not have thrown an exception.");
		}
	}
	public static @DataPoints String[] urls = {
			"http://www.boursorama.com/cours.phtml?symbole=1rPCAC", //CAC
			"https://www.bloomberg.com/quote/CAC:IND", //CAC
			"http://www.boursorama.com/cours.phtml?symbole=2cSX5E", //EUROSTOXX 50
			"https://www.bloomberg.com/quote/SX5E:IND", //EUROSTOXX 50
			"http://www.boursorama.com/cours.phtml?symbole=2cSD3E", //Euro Stoxx® Select Dividend 30
			"http://www.boursorama.com/cours.phtml?symbole=1rPCLEWE", //CAC® Large 60 EWER 
			"http://www.boursorama.com/cours.phtml?symbole=2cISXEC50", //Euro iStoxx® EWC 50
			"http://www.boursorama.com/cours.phtml?symbole=2cISXEHBP", //iSTOXX Europe Select High Beta 50
			"http://www.boursorama.com/cours.phtml?symbole=2cSXECS3P", //iSTOXX Europe Centenary Select 30
			"http://www.boursorama.com/cours.phtml?symbole=$707914", //MSCI EURO 50 SELECT 4.75% DECREMENT
			
			"http://www.boursorama.com/cours.phtml?symbole=3eFSLA1F7", //Solactive Eurozone 50 EW

			



			}; 

	

	@Test
	public void testSameAsReferenceSite() {
		
		//MAKE SURE THEY ARE OF SAME LENGTH AND THEY ARE MAPPED CORRECTLY
		String [] urlRefs = {
				"https://www.bloomberg.com/quote/CAC:IND",
				"https://www.bloomberg.com/quote/SX5E:IND", //EURO STOXX 50
				 
				
		};
		String [] urls = {
				"http://www.boursorama.com/cours.phtml?symbole=1rPCAC",
				"http://www.boursorama.com/cours.phtml?symbole=2cSX5E", //EUROSTOXX 50
				 
		};
		
		//String urlReference = "https://www.bloomberg.com/quote/CAC:IND";
		//String url = "http://www.boursorama.com/cours.phtml?symbole=1rPCAC";

		try {
			for(int i = 0; i<urlRefs.length; i++) {
				String urlRef = urlRefs[i];
				String url = urls[i];

				Object valueReference = getPrevDayClose(urlRef);
				Double expected = (Double) valueReference;
				Object output = getPrevDayClose(url);
				Assert.assertEquals(expected,(double) output, 0.1);
			}

		} catch (Exception e) {
			Assert.fail("Should not have thrown an exception.");
		}
	}
	
	
	@Theory
	public void testMultipleCallsReturnSameValue(String url) {

		try {
			Object output = getPrevDayClose(url);
			Object outputNextCall = getPrevDayClose(url);
			Assert.assertEquals(output, outputNextCall);

		} catch (Exception e) {
			Assert.fail("Should not have thrown an exception.");
		}
	}


	
	

	@Test
	public void testGetPrevClose_CAC40_UnfamiliarURL() throws EavTechnicalException {
		// Init: Check with the 'previous value' in a reference web site

		String unfamiliarURL = "htps://www.yahoo.com/uote/CAC:IND"; 
		thrown.expect(EavTechnicalException.class);
		thrown.expectMessage(XWEBSCRAP_UNFAMILIAR_URL);
			getPrevDayClose(unfamiliarURL);
		
	}
	
	@Test
	public void testGetPrevClose_CAC40_ErroneousURL() throws EavTechnicalException {
		// Init: Check with the 'previous value' in a reference web site

		String unfamiliarURL = "http://www.boursorama.com/cours.phtml?symbole=PCAC"; 
		thrown.expect(EavTechnicalException.class);
		thrown.expectMessage(XWEBSCRAP_PAGE_PARSING_PROBLEM);
			getPrevDayClose(unfamiliarURL);
		
	}
	
	@Test
	public void testGetPrevClose_CAC40_ErroneousWebsiteURL() throws EavTechnicalException {
		// Init: Check with the 'previous value' in a reference web site

		String unfamiliarURL = "http://www.prefixboursorama.com/cours.phtml?symbole=PCAC"; 
		thrown.expect(EavTechnicalException.class);
		thrown.expectMessage(XWEBSCRAP_INVALID_URL);
			getPrevDayClose(unfamiliarURL);
		
	}




}
