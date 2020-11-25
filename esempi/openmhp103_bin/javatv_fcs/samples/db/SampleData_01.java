/*
 * @(#)SampleData_01.java	1.2 99/08/26
 *
 * Copyright (c) 2000 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 */

import java.util.*;
import java.awt.*;

import com.sun.tv.*;
import com.sun.tv.si.*;
import com.sun.tv.receiver.*;

import javax.tv.locator.*;
import javax.tv.service.*;
import javax.tv.service.guide.*;
import javax.tv.service.transport.*;
import javax.tv.service.navigation.*;

public class SampleData_01 implements SampleDataInterface {

Vector MPAA = new Vector();
Vector YAMPAA = new Vector();
Vector PROGRAMS = new Vector(); // ServiceName, ProgramName, ProgramDescription
Vector TRANSPORT_STREAM = new Vector();
Vector BOUQUET = new Vector();
Vector NETWORK = new Vector();
Vector FAVORITES = new Vector();

SIEmulator emulator = null;

int numServices = 0;

public SampleData_01() {
}

public void prtInfo(String msg) {
	System.out.println(msg);
}

public void prtErr(String msg) {
	System.err.println(msg);
}

public int getCurrentHour() {
	return (new Date(System.currentTimeMillis())).getHours();
}

/**
 * This method setups the Rating Dimension Level, as the objects are created
 * the RatingDimensionImpl object stores them statically. The rating
 * dimension data is not run through the SIEmulator, maybe it should be.
 * The data is fake, it simulates the MPAA rating system and a phoney
 * YAMPAA, Yet Another MPAA. The rating level description are all 
 * made up.
 */
public void runRatingDimension() {
	MPAA.addElement("G");
	MPAA.addElement("General Audience");
	MPAA.addElement("PG");
	MPAA.addElement("Parental Guidance");
	MPAA.addElement("PG-13");
	MPAA.addElement("Parental Guidance, Under 13 strongly discouraged.");
	MPAA.addElement("R");
	MPAA.addElement("Restricted");
	MPAA.addElement("X");
	MPAA.addElement("No one under 21");
	MPAA.addElement("MA");
	MPAA.addElement("Mature Audience");

	RatingDimensionImpl rating1 = new RatingDimensionImpl("MPAA");
	for (int i = 0; i < MPAA.size(); i=i+2) {
		String name = (String)MPAA.elementAt(i);
		String desc = (String)MPAA.elementAt(i+1);
		rating1.addRatingLevelDescription(name, desc);
	}

	YAMPAA.addElement("A");
	YAMPAA.addElement("Phoney Desc for A");
	YAMPAA.addElement("B");
	YAMPAA.addElement("Phoney Desc for B");
	YAMPAA.addElement("C");
	YAMPAA.addElement("Phoney Desc for C");
	YAMPAA.addElement("D");
	YAMPAA.addElement("Phoney Desc for D");
	YAMPAA.addElement("E");
	YAMPAA.addElement("Phoney Desc for E");

	RatingDimensionImpl rating2 = new RatingDimensionImpl("YAMPAA");
	for (int i = 0; i < YAMPAA.size(); i=i+2) {
		String name = (String)YAMPAA.elementAt(i);
		String desc = (String)YAMPAA.elementAt(i+1);
		rating2.addRatingLevelDescription(name, desc);
	}
}

/**
 * This method invokes the SIEmulator to simualate some phoney
 * Services.
 */
public void runServiceData() {
	String name = null;

	emulator.putService(0, "SERV1",  false, "DVB", "DVBSI", 6, 1, "");
	emulator.putService(0, "SERV2",  false, "DVB", "DVBSI", 4, 1, "");
	emulator.putService(0, "SERV3",  false, "DVB", "DVBSI", 5, 1, "");
	emulator.putService(0, "SERV5",  false, "DVB", "DVBSI", 8, 1, "");
	emulator.putService(0, "SERV6",  false, "DVB", "DVBSI", 2, 1, ""); 
	emulator.putService(0, "SERV7",  false, "DVB", "DVBSI", 2, 2, "");
	emulator.putService(0, "SERV8",  false, "DVB", "DVBSI", 3, 1, "");
	emulator.putService(0, "SERV10", false, "DVB", "DVBSI", 7, 1, "");
	emulator.putService(0, "SERV15", false, "DVB", "DVBSI", 9, 1, "");

	numServices = 9;
}

/**
 * This method invokes the SIEmulator to simualate some phoney
 * Service Descriptions. The service name is a key to the service.
 * @see runServiceData
 */
public void runServiceDescriptions() {
	emulator.putServiceDescription(0, "SERV1", "Eisner's New Network.");
	emulator.putServiceDescription(0, "SERV2", "Columbia Broadcasting");
	emulator.putServiceDescription(0, "SERV3", "Bill's Network.");
	emulator.putServiceDescription(0, "SERV5", "Walt D's Channel");
	emulator.putServiceDescription(0, "SERV6", "The Sports Network.");
	emulator.putServiceDescription(0, "SERV7", "The Sports Network, 2nd Channel.");
	emulator.putServiceDescription(0, "SERV8", "Artsy Fartsy Stuff");
	emulator.putServiceDescription(0, "SERV10", "Home Line Channel");
	emulator.putServiceDescription(0, "SERV15", "That Stock Guy's Network.");
}

public void runServiceDetails() {

	final DeliverySystemType SATELLITE = DeliverySystemType.SATELLITE;
	final DeliverySystemType CABLE = DeliverySystemType.CABLE;
	final DeliverySystemType TERRESTRIAL = DeliverySystemType.TERRESTRIAL;
	final DeliverySystemType UNKNOWN = DeliverySystemType.UNKNOWN;

	String ids = "1, 2, 3, 4, 5, 6, 7, 8, 9";

	emulator.putServiceDetails(0, "SERV6",  "TCI", SATELLITE, "The Sports Network", ids);
	emulator.putServiceDetails(0, "SERV8",  "TCI", CABLE, "The Art Lovers Network", ids);
	emulator.putServiceDetails(0, "SERV3",  "TCI", TERRESTRIAL, "National Broadcasting", ids);
	emulator.putServiceDetails(0, "SERV2",  "TCI", UNKNOWN, "Columbia Broadcasting", ids);
	emulator.putServiceDetails(0, "SERV10", "TCI", UNKNOWN, "Home Line Network", ids);
	emulator.putServiceDetails(0, "SERV5",  "TCI", UNKNOWN, "Walt D's Channel", ids);
}

public void runServiceComponents() {

	emulator.putServiceComponent(
		0, "SERV6-VIDEO", "ENG", StreamType.VIDEO, "SERV6", null, "DVBSI", true, "");
	emulator.putServiceComponent(
		0, "SERV6-AUDIO", "ENG", StreamType.AUDIO, "SERV6", null, "DVBSI", true, "");
	emulator.putServiceComponent(
		0, "SERV6-SUBTITLES", "ENG", StreamType.SUBTITLES, "SERV6", null, "DVBSI", true, "");
	emulator.putServiceComponent(
		0, "SERV6-DATA", "ENG", StreamType.AUDIO, "SERV6", null, "DVBSI", true, "");
	emulator.putServiceComponent(
		0, "SERV6-SECTIONS", "ENG", StreamType.SECTIONS, "SERV6", null, "DVBSI", true, "");
}

public void runFavoriteServices() {
	FAVORITES.addElement("Movie Channels");
	String[] servs = {"SERV1", "HHB"}; 
	for (int i = 0; i < FAVORITES.size(); i++) {
		String name = (String)FAVORITES.elementAt(i);
		SIManagerImpl.putFavoriteServices(name, servs);
	}
}

public void runBouquetData() {
	BOUQUET.addElement("TCK-PREMIUM-PACKAGE");
	BOUQUET.addElement("TBD-PREMIUM-PACKAGE");
	BOUQUET.addElement("WORLDGATE-PREMIUM-PACKAGE");
	for (int i = 0; i < BOUQUET.size(); i++) {
		String name = (String)BOUQUET.elementAt(i);
		emulator.putBouquet(0, name, i+25, "DVBSI");
	}
}

public void runNetworkData() {
	NETWORK.addElement("SERV3-COMPLETE");
	NETWORK.addElement("SERV1-COMPLETE");
	NETWORK.addElement("SERV2-COMPLETE");
	for (int i = 0; i < NETWORK.size(); i++) {
		String name = (String)NETWORK.elementAt(i);
		emulator.putNetwork(0, name, i+11, "DVBSI");
	}

	for (int i = 0; i < NETWORK.size(); i++) {
		String desc = (String)NETWORK.elementAt(i);
		emulator.putTransportStream(0, desc, i+11, "DVBSI", i+11);
	}
}

public void runTransportStreamData() {
	TRANSPORT_STREAM.addElement("FastLinkSystem5");
	TRANSPORT_STREAM.addElement("SatCom7");
	TRANSPORT_STREAM.addElement("LandSat");

	for (int i = 0; i < TRANSPORT_STREAM.size(); i++) {
		String desc = (String)TRANSPORT_STREAM.elementAt(i);
		emulator.putTransportStream(0, desc, i+1, "DVBSI", i+11);
	}
}

public void runProgramEventData() {

	String sname = null;
	String name = null;
	String desc = null;
	int stime = 0;
	int duration = 120;

	Vector dimName = new Vector();
	dimName.addElement("MPAA");
	dimName.addElement("YAMPAA");

	Vector ratedG = new Vector();
	ratedG.addElement("G");
	ratedG.addElement("A");

	sname = "SERV6";	
	name = "NHL2Night: Detroit Red Wings - Ottawa Senators";
	desc = "a description of NHL2Night: Detroit Red Wings - Ottawa Senators";
	emulator.putProgramEvent(0, name, sname, 1400, 300, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV6";	
	name =  "NHL2Night: NY Rangers - San Jose Sharks";
	desc =  "a description of NHL2Night: NY Rangers - San Jose Sharks";
	emulator.putProgramEvent(0, name, sname, 1700, 300, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV6";	
	name =  "Figure Skating";
	desc =  "a description of Figure Skating";
	emulator.putProgramEvent(0, name, sname, 1200, 100, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV6";	
	name = "European Soccer: Juventus - Fiorentina";
	desc = "a description of European Soccer: Juventus - Fiorentina";
	emulator.putProgramEvent(0, name, sname, 1300, 230, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV6";	
	name = "Hockey: Boston Bruins - Buffalo Sabres";
	desc = "a description of Hockey: Boston Bruins - Buffalo Sabres";
	emulator.putProgramEvent(0, name, sname, 2100, 300, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV8";	
	name = "Mozart: Ein Klein Nachtmusik";
	desc = "a description of Mozart: Ein Klein Nachtmusik";
	emulator.putProgramEvent(0, name, sname, 2130, 003, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV8";	
	name = "Paganini: Violin Concerto #2";
	desc = "a description of Paganini: Violin Concerto #2";
	emulator.putProgramEvent(0, name, sname, 2200, 015, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV8";	
	name = "Vivaldi: Le Quattro Stagioni: Primavera";
	desc = "a description of Vivaldi: Le Quattro Stagioni: Primavera";
	emulator.putProgramEvent(0, name, sname, 2215, 015, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV8";	
	name = "Edit Piaf: Non, je ne regrette rien";
	desc = "a descritpion of Edit Piaf: Non, je ne regrette rien";
	emulator.putProgramEvent(0, name, sname, 2300, 003, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV2";	
	name = "Current Program";
	desc = "This is a test for the current program.";
	stime = getCurrentHour() * 100;
	duration = 200;
	emulator.putProgramEvent(0, name, sname, stime, duration, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV2";	
	name = "Next Program";
	desc = "This is a test for the next program.";
	stime = stime + duration;
	emulator.putProgramEvent(0, name, sname, stime, duration, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV2";	
	name = "Next(2) Program";
	desc = "This is the second future program for test purposes.";
	stime = stime + duration;
	emulator.putProgramEvent(0, name, sname, stime, duration, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV10";	
	name = "Current Program";
	desc = "This is a test for the current program.";
	stime = getCurrentHour() * 100;
	duration = 200;
	emulator.putProgramEvent(0, name, sname, stime, duration, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV10";	
	name = "Next Program";
	desc = "This is a test for the next program.";
	stime = stime + duration;
	emulator.putProgramEvent(0, name, sname, stime, duration, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);

	sname = "SERV10";	
	name = "Next(2) Program";
	desc = "This is the second future program for test purposes.";
	stime = stime + duration;
	emulator.putProgramEvent(0, name, sname, stime, duration, "DVBSI", dimName, ratedG);
	emulator.putProgramEventDescription(0, name, desc);
	PROGRAMS.addElement(sname);
	PROGRAMS.addElement(name);
	PROGRAMS.addElement(desc);
}

public void runLocatorEmulation() {
	LocatorImpl.setMediaFile("service:/SERV6", 
		"file:/c:/work/javatv/gatwick/data/lasco_c3.mpg");
	LocatorImpl.setMediaFile("service:/SERV2",
		"file:/c:/work/javatv/gatwick/data/12ftwt.mpg");
	LocatorImpl.setMediaFile("service:/SERV8",
		"file:/c:/work/javatv/gatwick/data/aae.mpg");
	LocatorImpl.setMediaFile("service:/SERV2",
		"file:/c:/work/javatv/gatwick/data/em-0002-01.mpg");
	LocatorImpl.setMediaFile("service:/SERV3", 
		"file:/c:/work/javatv/gatwick/data/em-0002-02.mpg");
	LocatorImpl.setMediaFile("service:/SERV1",
		"file:/c:/work/javatv/gatwick/data/lasco_c3.mpg");
	LocatorImpl.setMediaFile("service:/SERV10",
		"file:/c:/work/javatv/gatwick/data/lasco_c3.mpg");
	LocatorImpl.setMediaFile("service:/SERV15",
		"http://www.bloomberg.com/videos/broadband/wm");
	LocatorImpl.setMediaFile("service:/SERV5", 
		"file:/c:/work/javatv/gatwick/data/em-0002-02.mpg");
/**
gchute.mpg
gclouds.mpg
hedp.mpg
lasco_c3.mpg
orprobe1.mpg
orprobe2.mpg
recon2.mpg
x_36.mpg
**/
}

public boolean CheckRatingDimension() {
	boolean passed = true;

	prtInfo("CheckRatingDimension...");

	SIManager sim = SIManager.createInstance();
	if (sim == null) {
		prtErr("sim == null");
		return false;
	}

	String sd[] = sim.getSupportedDimensions();
	if (sd == null) {
		prtErr("sd == null");
		return false;
	}

	if (sd.length != 2 || sd[0] == null || sd[1] == null) {
		prtErr("sd.length != 1: " + sd.length);
		return false;
	}

	if ("MPAA".equals(sd[0]) == false) {
		prtErr("sd[0] != MPAA: " + sd[0]);
		return false;
	}

	RatingDimension rating = null;

	try {
		rating = sim.getRatingDimension(sd[0]);
	} catch (Exception e) {
		prtErr("getRatingDimension exception: " + e);
		return false;
	}

	if (rating == null) {
		prtErr("rating == null");
		return false;
	}

	String name = rating.getDimensionName();
	if ("MPAA".equals(name) == false) {
		prtErr("name != MPAA: " + name);
		return false;
	}

	short actualLevels = (short)(MPAA.size() / 2);
	short nlevels = rating.getNumberOfLevels();
	if (nlevels != actualLevels) {
		prtErr("nlevels != " +actualLevels+ " : " + nlevels);
		return false;
	}

// TBD: getRatingLevelDescription method doesn't indicate 0 or 1 based level.

	int ptr = 0;
	for (short level = 0; level < nlevels; level++) {
		try {
			String text[] = rating.getRatingLevelDescription(level);
			if (text == null || text.length != 2) {
				prtErr("text == null || text.length : " + level);
				passed = false;
				continue;
			}
			String ratingName = (String)MPAA.elementAt(ptr++);
			String ratingDesc = (String)MPAA.elementAt(ptr++);

			if (ratingName.equals(text[0]) == false) {
				prtErr("text[0].name: " + text[0] + " SB " + ratingName);
				passed = false;
			}
			if (ratingDesc.equals(text[1]) == false) {
				prtErr("text[0].desc: " + text[1] + " SB " + ratingDesc);
				passed = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			passed = false;
		}
	}

	try {
		rating.getRatingLevelDescription((short)nlevels);
		prtErr("Should have caught an SIException..");
		passed = false;
	} catch (SIException sie) {
		passed = true;
	} catch (Exception e) {
		e.printStackTrace();
		prtErr("Should have caught an SIException not an Exception.." + e);
		passed = false;
	}
	return passed;
}

public boolean CheckServices() {
	boolean passed = true;

	prtInfo("CheckServices...");

	SIManager sim = SIManager.createInstance();
	if (sim == null) {
		prtErr("sim == null");
		return false;
	}

	ServiceList sc = sim.filterServices(null);
	if (sc == null) {
		prtErr("sc == null");
		return false;
	}

	ServiceIterator list = sc.createServiceIterator();
	if (list == null) {
		prtErr("list == null");
		return false;
	}


	int count = 0;
	while (list.hasNext()) {
		try {
			Service service = list.nextService();
			count++;

			if (service == null) {
				prtErr("a null service was found on list.nextService()");
				passed = false;
			} else {
				prtInfo("Service[" +count+ "]= " +service.getName()+ ", "+
					service.hasMultipleInstances() + ", " +
					service.getServiceType());
			}

		} catch (Exception e) {
			prtErr("CheckService: list.nextService() " + e);
			e.printStackTrace();
			passed = false;
			break;
		}
	}

	if (count != numServices) {
		prtErr("CheckService: there are " +count+ " services S/B " + numServices);
		passed = false;
	}

	if (sc.size() != numServices) {
		prtErr("CheckService: there are " +sc.size()+ " services S/B " + numServices);
		passed = false;
	}

	return passed;
}

public boolean CheckServicesSorting() {
	boolean passed = true;

	prtInfo("CheckServices...");

	SIManager sim = SIManager.createInstance();
	if (sim == null) {
		prtErr("sim == null");
		return false;
	}

	ServiceList sc = sim.filterServices(null);
	if (sc == null) {
		prtErr("sc == null");
		return false;
	}

	sc.sortByName();

	ServiceIterator list = sc.createServiceIterator();
	if (list == null) {
		prtErr("list == null");
		return false;
	}


	String previous = "";
	int count = 0;
	while (list.hasNext()) {
		try {
			Service service = list.nextService();
			count++;

			prtInfo("Service["+count+"]= "+service.getName()+ ", "+
				service.hasMultipleInstances() + ", " +
				service.getServiceType());

			if (previous.compareTo(service.getName()) > 0) {
				prtErr("sort error: " +previous+ " is before " +service.getName());
				passed = false;
			}
			previous = service.getName();

		} catch (Exception e) {
			prtErr("CheckService: list.nextService() " + e);
			e.printStackTrace();
			passed = false;
			break;
		}
	}
	return passed;
}

public boolean CheckServiceDescription(ServiceDetails details) {
	boolean passed = true;

	MySIRequestor requestor = new MySIRequestor();

	SIRequest request = details.retrieveServiceDescription(requestor);
	
	if (requestor.waitFor() == false) {
		prtErr("CheckServiceDescription: TimedOut " + details.getLongName());
		return false;
		
	}
	
	if (requestor.isSuccess() == false) {
		prtInfo("ServiceDescription not found for " + details.getLongName() +
			", reason = " + requestor.getFailure());
		return passed;
	}
	
	SIRetrievable descs[] = requestor.getResult();
	if (descs == null || descs.length <= 0) {
		prtErr("CheckServiceDescription: result == null " + details.getLongName());
		return false;
	}
	
	for (int j = 0; j < descs.length; j++) {
		if (!(descs[j] instanceof ServiceDescription)) {
			prtErr("result not of ServiceDescription...");
			passed = false;
			continue;
		}
		ServiceDescription desc = (ServiceDescription)descs[j];
		prtInfo("ServiceDescription["+j+"] = " +
			details.getService().getName() + ", " +
			details.getLongName() + ", " +
			desc.getServiceDescription());
	}
	return passed;
}

public boolean CheckProgramSchedule(ServiceDetails details) {
	boolean passed = true;

	ProgramSchedule schedule = details.getProgramSchedule();
	if (schedule == null) {
		prtErr("ProgramSchedule not found for " + details.getService().getName());
		passed = false;
	}

// TBD: add more checks here...

	return passed;
}

public boolean CheckServiceDetails() {
	boolean passed = true;

	prtInfo("CheckServiceDetails...");

	SIManager sim = SIManager.createInstance();
	if (sim == null) {
		prtErr("sim == null");
		return false;
	}

	ServiceList sc = sim.filterServices(null);
	if (sc == null) {
		prtErr("sc == null");
		return false;
	}

	ServiceIterator list = sc.createServiceIterator();
	if (list == null) {
		prtErr("list == null");
		return false;
	}


	while (list.hasNext()) {
		try {
			Service service = list.nextService();
			if (service == null) {
				prtErr("a null service was found on list.nextService()");
				passed = false;
				continue;
			}

			MySIRequestor requestor = new MySIRequestor();

			SIRequest request = sim.retrieveServiceDetails(
					service.getLocator(), requestor);

			if (requestor.waitFor() == false) {
				prtErr("CheckServiceDetails: TimedOut " + service.getName());
				passed = false;
				continue;
			}

			if (requestor.isSuccess() == false) {
				prtInfo("ServiceDetails not found for " + service.getName() +
					", reason = " + requestor.getFailure());
				continue;
			}

			SIRetrievable result[] = requestor.getResult();
			if (result == null || result.length <= 0) {
				prtErr("CheckServiceDetails: result == null " + service.getName());
				passed = false;
				continue;
			}

			for (int i = 0; i < result.length; i++) {
				if (!(result[i] instanceof ServiceDetails)) {
					prtErr("result not of ServiceDetails...");
					passed = false;
					continue;
				}
				ServiceDetails details = (ServiceDetails)result[i];
				prtInfo("ServiceDetails["+i+"] = " +
					details.getService().getName() + ", " +
					details.getLongName() + ", " +
					details.getServiceType() + ", " +
					details.getDeliverySystemType() + ", " +
					details.getServiceInformationType() + ", " +
//					details.getServiceNumber() + ", " +
//					details.getMinorNumber() + ", " +
//					details.getProviderName() + ", " +
					details.getUpdateTime());


				if (CheckServiceDescription(details) == false) {
					passed = false;
				}

				if (CheckProgramSchedule(details) == false) {
					passed = false;
				}
			}

		} catch (Exception e) {
			prtErr("CheckService: list.nextService() " + e);
			e.printStackTrace();
			passed = false;
			break;
		}
	}
	return passed;
}


public boolean CheckProgramEvent() {
	boolean passed = true;

	prtInfo("CheckProgramEvent...");

	SIManager sim = SIManager.createInstance();
	if (sim == null) {
		prtErr("sim == null");
		return false;
	}

	for (int i = 0; i < PROGRAMS.size(); i=i+3) {
		String serviceName = (String)PROGRAMS.elementAt(i);
		String programName = (String)PROGRAMS.elementAt(i+1);
		String programDesc = (String)PROGRAMS.elementAt(i+2);

		try {
			MySIRequestor requestor = new MySIRequestor();

			Locator programLocator = LocatorImpl.createProgramEventLocator(
					serviceName, programName);

			SIRequest request = sim.retrieveProgramEvent(
					programLocator, requestor);

			if (requestor.waitFor() == false) {
				prtErr("CheckProgramEvent: TimedOut " + programName);
				passed = false;
				continue;
			}

			if (requestor.isSuccess() == false) {
				prtInfo("CheckProgramEvent not found " + programName +
					", reason = " + requestor.getFailure());
				continue;
			}

			SIRetrievable result[] = requestor.getResult();
			if (result == null) {
				prtErr("CheckProgramEvent: result == null " + programName);
				passed = false;
				continue;
			}
			if (result.length != 1) {
				prtErr("CheckProgramEvent: length != 1, " + programName);
				passed = false;
				continue;
			}

			for (int j = 0; j < result.length; j++) {
				if (!(result[j] instanceof ProgramEvent)) {
					prtErr("result not of ProgramEvent...");
					passed = false;
					continue;
				}

				ProgramEvent program = (ProgramEvent)result[j];

				prtInfo("ProgramEvent["+j+"] = " +
					program.getService().getName() + ", " +
					program.getName() + ", " +
					program.getStartTime() + ", " +
					program.getEndTime() + ", " +
					program.getDuration() + ", " +
					program.getUpdateTime());

				if (programName.equals(program.getName()) == false) {
					prtErr(programName +" not equals "+program.getName());
					passed = false;
				}

				if (serviceName.equals(program.getService().getName()) == false) {
					prtErr(serviceName +" not equals "+program.getService().getName());
					passed = false;
				}

			}

		} catch (Exception e) {
			prtErr("CheckProgramEvent exception: " + e);
			e.printStackTrace();
			passed = false;
			break;
		}
	}
	return passed;
}

public boolean CheckTransportStream() {
	boolean passed = true;

	prtInfo("CheckTransportStream...");

	SIManager sim = SIManager.createInstance();
	if (sim == null) {
		prtErr("sim == null");
		return false;
	}

	Transport transports[] = sim.getTransports();
	if (transports == null || transports.length != 1) {
		prtErr("transports == null || transports.length != 1");
		return false;
	}

	Transport transport = transports[0];
	if (transport == null) {
		prtErr("transport == null");
		return false;
	}

	if (!(transport instanceof TransportStreamCollection)) {
		prtErr("transport not instanceof TransportStreamCollection");
		return false;
	}
	TransportStreamCollection tsc = (TransportStreamCollection)transport;

	try {
		MySIRequestor requestor = new MySIRequestor();

		SIRequest request = tsc.retrieveTransportStreams(requestor);
		if (requestor.waitFor() == false) {
			prtErr("CheckTransportStream " + transport);
			return false;
		}

		if (requestor.isSuccess() == false) {
			prtInfo("CheckTransportStream not found " + transport +
				", reason = " + requestor.getFailure());
			return false;
		}

		SIRetrievable result[] = requestor.getResult();
		if (result == null) {
			prtErr("CheckTransportStream: result == null");
			return false;
		}

		if (result.length != 6) {
			prtErr("CheckTransportStream: length = " +result.length+ " != 3");
			return false;
		}

		for (int j = 0; j < result.length; j++) {
			if (!(result[j] instanceof TransportStream)) {
				prtErr("result not of TransportStream...");
				passed = false;
				continue;
			}

			TransportStream ts = (TransportStream)result[j];
			prtInfo("TransportStream["+j+"] = " +
				ts.getDescription() + ", " +
				ts.getTransportStreamID() + ", " +
				ts.getLocator() + ", " +
				ts.getServiceInformationType() + ", " +
				ts.getUpdateTime());

			boolean found = false;
			for (int i = 0; i < TRANSPORT_STREAM.size(); i++) {
				String desc = (String)TRANSPORT_STREAM.elementAt(i);
				if (desc != null && desc.equals(ts.getDescription())) {
					found = true;
					break;
				}
			}

			if (found == false) {
				prtErr("getDescription not found: " + ts.getDescription());
				passed = false;
			}
		}

	} catch (Exception e) {
		prtErr("CheckTransportStream exception: " + e);
		e.printStackTrace();
		passed = false;
	}

	return passed;
}

public boolean CheckBouquet() {
	boolean passed = true;

	prtInfo("CheckBouquet...");

	SIManager sim = SIManager.createInstance();
	if (sim == null) {
		prtErr("sim == null");
		return false;
	}

	Transport transports[] = sim.getTransports();
	if (transports == null || transports.length != 1) {
		prtErr("transports == null || transports.length != 1");
		return false;
	}

	Transport transport = transports[0];
	if (transport == null) {
		prtErr("transport == null");
		return false;
	}

	if (!(transport instanceof BouquetCollection)) {
		prtErr("transport not instanceof BouquetCollection");
		return false;
	}
	BouquetCollection bc = (BouquetCollection)transport;

	try {
		MySIRequestor requestor = new MySIRequestor();

		SIRequest request = bc.retrieveBouquets(requestor);
		if (requestor.waitFor() == false) {
			prtErr("CheckBouquet " + transport);
			return false;
		}

		if (requestor.isSuccess() == false) {
			prtInfo("CheckBouquet not found " + transport +
				", reason = " + requestor.getFailure());
			return false;
		}

		SIRetrievable result[] = requestor.getResult();
		if (result == null) {
			prtErr("CheckBouquet: result == null");
			return false;
		}

		if (result.length != 3) {
			prtErr("CheckBouquet: length = " +result.length+ " != 3");
			return false;
		}

		for (int j = 0; j < result.length; j++) {
			if (!(result[j] instanceof Bouquet)) {
				prtErr("result not of Bouquet...");
				passed = false;
				continue;
			}

			Bouquet b = (Bouquet)result[j];
			prtInfo("Bouquet["+j+"] = " +
				b.getName() + ", " +
				b.getBouquetID() + ", " +
				b.getLocator() + ", " +
				b.getServiceInformationType() + ", " +
				b.getUpdateTime());

			boolean found = false;
			for (int i = 0; i < BOUQUET.size(); i++) {
				String desc = (String)BOUQUET.elementAt(i);
				if (desc != null && desc.equals(b.getName())) {
					found = true;
					break;
				}
			}

			if (found == false) {
				prtErr("getName not found: " + b.getName());
				passed = false;
			}
		}

	} catch (Exception e) {
		prtErr("CheckBouquet exception: " + e);
		e.printStackTrace();
		passed = false;
	}
	return passed;
}


public boolean CheckNetwork() {
	boolean passed = true;

	prtInfo("CheckNetwork...");

	SIManager sim = SIManager.createInstance();
	if (sim == null) {
		prtErr("sim == null");
		return false;
	}

	Transport transports[] = sim.getTransports();
	if (transports == null || transports.length != 1) {
		prtErr("transports == null || transports.length != 1");
		return false;
	}

	Transport transport = transports[0];
	if (transport == null) {
		prtErr("transport == null");
		return false;
	}

	if (!(transport instanceof NetworkCollection)) {
		prtErr("transport not instanceof NetworkCollection");
		return false;
	}
	NetworkCollection nc = (NetworkCollection)transport;

	try {
		MySIRequestor requestor = new MySIRequestor();

		SIRequest request = nc.retrieveNetworks(requestor);
		if (requestor.waitFor() == false) {
			prtErr("CheckNetwork " + transport);
			return false;
		}

		if (requestor.isSuccess() == false) {
			prtInfo("CheckNetwork not found " + transport +
				", reason = " + requestor.getFailure());
			return false;
		}

		SIRetrievable result[] = requestor.getResult();
		if (result == null) {
			prtErr("CheckNetwork: result == null");
			return false;
		}

		if (result.length != 3) {
			prtErr("CheckNetwork: length = " +result.length+ " != 3");
			return false;
		}

		for (int j = 0; j < result.length; j++) {
			if (!(result[j] instanceof Network)) {
				prtErr("result not of Network...");
				passed = false;
				continue;
			}

			Network n = (Network)result[j];
			prtInfo("Network["+j+"] = " +
				n.getName() + ", " +
				n.getNetworkID() + ", " +
				n.getLocator() + ", " +
				n.getServiceInformationType() + ", " +
				n.getUpdateTime());

			boolean found = false;
			for (int i = 0; i < NETWORK.size(); i++) {
				String desc = (String)NETWORK.elementAt(i);
				if (desc != null && desc.equals(n.getName())) {
					found = true;
					break;
				}
			}

			if (found == false) {
				prtErr("getName not found: " + n.getName());
				passed = false;
			}
		}

	} catch (Exception e) {
		prtErr("CheckNetwork exception: " + e);
		e.printStackTrace();
		passed = false;
	}
	return passed;
}


public boolean CheckFavoriteServices() {
	boolean passed = true;

	prtInfo("CheckFavoriteServices...");

	SIManager sim = SIManager.createInstance();
	if (sim == null) {
		prtErr("sim == null");
		return false;
	}

	FavoriteServicesName favorites[] = PreferenceFilter.listPreferences();
	if (favorites == null) {
		prtErr("favorites == null");
		return false;
	}

	if (favorites.length != FAVORITES.size()) {
		prtErr("favorites.length = " +favorites.length+ " != " +FAVORITES.size());
		return false;
	}

	for (int i = 0; i < favorites.length; i++) {
		String name1 = favorites[i].getName();
		boolean found = false;
		for (int j = 0; j < FAVORITES.size(); j++) {
			String name2 = (String)FAVORITES.elementAt(j);
			if (name2 != null && name2.equals(name1)) {
				found = true;
				break;
			}
		}
		if (found == false) {
			prtErr("favorites.getName not found: " + name1);
			passed = false;
		}
	}

	return passed;
}

public boolean CheckSIManager() {
	boolean passed = true;

	if (CheckRatingDimension() == true) {
		prtErr("CheckRatingDimension test complete: PASSED.");
	} else {
		passed = false;
		prtErr("CheckRatingDimension test complete: FAILED.");
	}

	if (CheckServices() == true) {
		prtErr("CheckServices test complete: PASSED.");
	} else {
		passed = false;
		prtErr("CheckServices test complete: FAILED.");
	}

	if (CheckServicesSorting() == true) {
		prtErr("CheckServicesSorting test complete: PASSED.");
	} else {
		passed = false;
		prtErr("CheckServicesSorting test complete: FAILED.");
	}

	if (CheckServiceDetails() == true) {
		prtErr("CheckServiceDetails test complete: PASSED.");
	} else {
		passed = false;
		prtErr("CheckServiceDetails test complete: FAILED.");
	}

	if (CheckProgramEvent() == true) {
		prtErr("CheckProgramEvent test complete: PASSED.");
	} else {
		passed = false;
		prtErr("CheckProgramEvent test complete: FAILED.");
	}

	if (CheckTransportStream() == true) {
		prtErr("CheckTransportStream test complete: PASSED.");
	} else {
		passed = false;
		prtErr("CheckTransportStream test complete: FAILED.");
	}

	if (CheckBouquet() == true) {
		prtErr("CheckBouquet test complete: PASSED.");
	} else {
		passed = false;
		prtErr("CheckBouquet test complete: FAILED.");
	}

	if (CheckNetwork() == true) {
		prtErr("CheckNetwork test complete: PASSED.");
	} else {
		passed = false;
		prtErr("CheckNetwork test complete: FAILED.");
	}

	if (CheckFavoriteServices() == true) {
		prtErr("CheckFavoriteServices test complete: PASSED.");
	} else {
		passed = false;
		prtErr("CheckFavoriteServices test complete: FAILED.");
	}
	return passed;
}

public void play(SIEmulator emulator, String args[]) {
	this.emulator = emulator;

	runServiceData();
	runServiceDetails();
	runServiceDescriptions();
	runServiceComponents();
	runProgramEventData();
	runBouquetData();
	runNetworkData();
	runTransportStreamData();
	runRatingDimension();
	runLocatorEmulation();
	runFavoriteServices();
}

public void finish() {
	prtInfo("Waiting for Emulator...");
	emulator.isCaughtUp();
}

public boolean verify() {
	boolean passed = false;
	prtInfo("CheckSIManager tests...");
	if (CheckSIManager() == true) {
		passed = true;
		prtErr("SampleData_01: PASSED all tests...");
	} else {
		prtErr("SampleData_01: FAILED some tests...");
	}
	prtInfo("Finished...");
	return passed;
}

public static void main(String args[]) {

	Settings.SampleClass = "SampleData_01";
	SIManager manager = SIManager.createInstance();
	SIEmulator emulator = SIEmulator.getInstance();
	emulator.verify();
}
}

class MySIRequestor implements SIRequestor {

	final int WAITING = 0;
	final int SUCCESS = 1;
	final int FAILURE = 2;

	static Vector requestors = new Vector();

	SIRetrievable result[] = null;
	SIRequestFailureType reason = null;
	int status = WAITING;

	public MySIRequestor() {
		status = WAITING;
		requestors.addElement(this);
	}

	public void notifySuccess(SIRetrievable[] result) {
		this.result = result;
		status = SUCCESS;
	}

	public void notifyFailure(SIRequestFailureType reason) {
		this.reason = reason;
		status = FAILURE;
	}

	public boolean isDone() {
		return (status != WAITING);
	}

	public boolean isSuccess() {
		return (status == SUCCESS);
	}

	public SIRetrievable[] getResult() {
		return result;
	}

	public SIRequestFailureType getFailure() {
		return reason;
	}

	public boolean waitFor() {
		for (int i = 0; i < 30; i++) {
			if (this.isDone()) {
				return true;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
					;
			}
		}
		return false;
	}

	public static boolean waitForAll() {
		MySIRequestor requestor = null;

		for (int j = 0; j < 30; j++) {
			boolean done = true;
			for (int i = 0; i < requestors.size(); i++) {
				requestor = (MySIRequestor)requestors.elementAt(i);
				if (requestor.isDone() == false) {
					done = false;
				}
			}
			if (done) {
				return true;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
					;
			}
		}
		return false;
	}
}
