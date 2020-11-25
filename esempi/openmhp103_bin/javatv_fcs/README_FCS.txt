

	__README FOR THE JAVA TV(TM) API REFERENCE IMPLEMENTATION__
	
			      11/15/2000

Copyright 2000, Sun Microsystems, Inc.
------------------------------------------------------------------------

TABLE OF CONTENTS

INTRODUCTION
REQUIREMENTS
RUNNING THE REFERENCE IMPLEMENTATION
CONFIGURING THE REFERENCE IMPLEMENTATION
COMPILING THE SOURCE
KNOWN DEFECTS

------------------------------------------------------------------------

INTRODUCTION
------------

Thank you for your interest in the the Java TV(TM) API Reference 
Implementation, Version 1.0.  This document provides general
information about running this release, as well as what is included in
it.

When we began work on the Java TV API reference implementation, we
determined that we could best serve our licensees by providing a
"protocol" and "platform" neutral implementation. Therefore, this 
reference implementation (RI) is written in the Java(TM) programming
language, except for some native code in the Java(TM) Media Framework
(JMF) implementation as an example of how to write a player.

About this release:

This release conforms to the Java TV API Specification, Version 1.0. 
The 1.0 version of the specification is available from where you 
downloaded this ZIP file.  More information on the Java TV API can be
found at http://java.sun.com/products/javatv/.

REQUIREMENTS
------------

Software:

The Java TV API reference implementation requires the following software
components:

 o PersonalJava(TM) Application Environment, Version 3.1
 o JAXP 1.0.1 (Java(TM) API for XML Parsing)
 o Microsoft Windows NT 4

Hardware:

The Java TV API reference implementation was developed and tested on
PCs running Windows NT 4. Therefore, it is only supported on that
hardware/operating system configuration. The reference implementation
is, however, written entirely in the Java(TM) programming language 
except for the JMF component.  The core implementation should in theory
operate on any platform that supports the previously mentioned software 
components as long as JMF is installed.

RUNNING THE REFERENCE IMPLEMENTATION
------------------------------------

Environment:

The Java TV API reference implementation operates with the PersonalJava 
application environment, Version 3.1. Your class path should include:

 o javatv.jar : the Java TV API binaries
 o jaxp.jar & parser.jar : from JAXP (XML parsing extension)
 o classes.zip : the system class files from the PersonalJava Application 
   Environment, Version 3.1
 o "lib" directory from this ZIP file
 o Any application files you may have

Also, your path should include:
 o "lib" directory from this ZIP file, for jmam.dll, which is the RI's
   implementation of JMF.
   
Additionally, the reference implementation assumes that there is a
directory called "lib" that is one level higher than the directory in
which the Java(TM) runtime environment is started. The lib directory 
should include the following files:

 o JavaTV.properties       : the properties file
 o JavaTVServiceFile.txt   : the information file
 o JavaTVServiceFile.dtd   : the SI DTD
 o JavaTVServiceFile01.xml : a sample description of the SI database
 o JavaTVServiceFile02.xml : a sample description of the SI database
 o JavaTVServiceFile03.xml : a sample description of the SI database
 o SampleData_01.class, MySIRequestor.class: classes providing sample
   data to the reference implementation

Running the code:

The reference implementation does not include a "main()" method. There
needs to be an application that does (see the "runxlet" sample
directory). Your class path must be set as per the previous section
(either in an environment variable or by using the "-classpath"
option on the PersonalJava runtime environment). For example:

  % pjava -classpath %CLASSPATH%;. MyTVApp

Please note that the reference implementation reinforces some classes
in the PersonalJava application environment.  For the reference 
implementation to work properly, you should first use the option 
"-bootclasspath" and then place the RI archive file "javatv.jar" before
the "classes.zip" file from the PersonalJava application environment, 
Version 3.1.  For example:

  % pjava -bootclasspath %JAVATV_RI_javatv.jar%;%PJAVA_classes.zip% 
          -classpath %CLASSPATH%;. MyTVApp

CONFIGURING THE REFERENCE IMPLEMENTATION
----------------------------------------

The Java TV API reference implementation is designed to be highly
configurable. It allows programmatic population of the SI database, as
well as population via an XML file. This release includes examples of 
both programmatic and XML file population. In the "lib" directory you 
will find the file "JavaTV.properties". The reference implementation 
looks for this file (in the lib directory) and uses the property 
"ServiceFileHandler" to reference a class that will be used to populate
the SI database. This class must be in the CLASSPATH of the reference 
implementation and must implement the interface
"com.sun.tv.receiver.SampleDataInterface".

Details on the interface com.sun.tv.receiver.SampleDataInterface follow.
For more details see SampleDataInterface.java.

public interface SampleDataInterface {

 // Populates SI database via SIEmulator.
 public void play( SIEmulator emulator );

 // A blocking call that returns when population is done.
 public void finish();

 // Returns true if population is complete.
 public boolean verify();

}

Included SampleDataInterface implementations:

1) SampleData_01 (samples/db/SampleData_01.java):

This example includes the following:
 - creation of services
 - inclusion of service details
 - inclusion of service descriptions
 - creation of program events
 - specification of ratings
 - specification of favorite services
 - attaching MPEG files to a service

As included with the distribution, all services specified in the
SampleData_01 class have MPEG files associated with them. Unless
edited by the you, SampleData_01 will look for the following files
in the "C:\work\javatv\gatwick" directory:

  lasco_c3.mpg
  12ftwt.mpg
  aae.mpg
  em-0002-01.mpg
  em-0002-02.mpg

These can be any MPEG-1 movies files. The names can be changed by
editing SampleData_01.java and recompiling the class. The RI does not 
include these files.

2) com.sun.tv.receiver.ReceiverFile:

The ReceiverFile class parses an XML file specified in the 
ServiceFile property of the JavaTV.properties file. This file's path
is relative to the directory where the reference implementation is
run. ReceiverFile is the default ServiceFileHandler. The default
XML file is lib/JavaTVSampleFile01.xml. The specified XML file is read
on startup of the reference implementation. Every ten seconds the XML
file is checked to see if it has changed (the modification time is
noted). If it has changed, the file is re-parsed. The general semantics
of the re-parse are to replace duplicate SI elements (MODIFY are
events generated), add new elements (ADD are events generated) and
remove elements marked for removal (REMOVE are events generated).

Note that JavaTVSampleFile01.xml includes the following MPEG files
which, like SampleData_01 mentioned above, must be present. They can be
any MPEG files, but must have the following names:

In the c:/work/javatv/gatwick/data directory:

  12ftwt.mpg
  aae.mpg
  asco_c3.mpg
  em-0002-01.mpg
  em-0002-02.mpg
  cnn.mpg
  lasco_c3.mpg
  orprobe1.mpg
  probe1.mpg

COMPILING THE SOURCE
--------------------

The "src" directory has three top-level directories, "com", "java", and
"javax". These directories contain the source files for the com.sun.tv.*
packages (private APIs used by the implementation), java.net.* package
(PersonalJava application environment APIs overwritten for the reference
implementation), and javax.tv.* packages (public source implementation
files of the Java TV API reference implementation). Our build system
effectively does the following:

  % javac -target 1.1 -bootclasspath <bootclasspath> \
   -classpath <classpath> \
   -d <dest directory> <sources>

<bootclasspath>: The boot class path is used to define the Java
platform library classes to compile against. The Java TV API reference
implementation is dependent on some of the class libraries in the
PersonalJava application environment, Version 3.1. This flag should 
include the "classes.zip" from the PersonalJava application environment,
Version 3.1.

<classpath> : this flag should include the other classes (non-platform
classes) that are required by the Java TV API to compile, such as 
JAXP and a JMF implementation.

<dest directory> : the destination directory is the directory used by
"javac" to deposit the classes once they have been compiled. Sub-
directories will be created for each package by "javac".

<sources> : this specifies the source files for all of the reference
implementation. For instance, you could specify for sources as
follows:

  com/sun/tv/*.java com/sun/tv/si/*.java ... javax/tv/xlet/*.java

We recommend that you compile your source code with 
the Java Development Kit (TM) 1.2 compiler using the option 
to generate class files compatible with the JDK 1.1 VM (-target 1.1). 
This is a work around to a problem with the JDK 1.1.8 compiler and 
will ensure that the class files generated are compatible with 
version 3.1 of the PersonalJava application environment 
which is based on the JDK 1.1 VM.

KNOWN DEFECTS
-------------

1) Known *critical* defects in this release:


 4383401 Extra NormalContentEvent(s) are generating after
         Service selection operation

 RI can return more than one NormalContentEvents when the selection
 succeeds.  While the JavaTV specification 1.0f states that
 ServiceContext.select(Locator[]) and MediaSelectControl.select(Locator[])
 should notify it's successful completion "by either a single
 NormalContentEvent or a single AlternativeContentEvent", RI may
 intermittently return two or more PresentationChangedEvent.
 This is due to the synchronization issue between the jmf player and
 the RI. 


 4387065 MediaSelectControl.select(Locator) select 2 components
         when 1 requested
 4387066 MediaSelectControl.select(Locator[]) selects 2 components
         when 1 requested

 RI may fail to stop one of the previously presented ServiceComponents
 when MediaSelctControl.select(Locator) or
 MediaSelectControl.select(Locators[]) are called with the ServiceComponent
 locators that belong to the same service as the service that
 MediaSelectControl had been playing prior to the method call.
 The workaround is to select another service before the method call, or to
 use MediaSelectControl.remove() on a ServiceComponent that failed to be
 un-selected after the method call.


 4387851 SIRequest.cancel() does not work if request results in a success

 It is known that SIRequest.cancel() does not cancel the retrieval request
 that will be a success.  In this case, cancel() returns false, the retrieval 
 would be processed, and SIRequestor.notifySuccess() would be invoked 
 with the requested data.  If the request is for the unavailable data, 
 then cancel() do cancel the retrieval and SIRequestor.notifyFailure() with
 a reason code CANCELED is successfully invoked.


 4389657 Java TV's JMF implementation memory leak during player life cycle 

 The JMF1.0 reference implementation is known to have a problem with 
 releasing threads in the MediaController.  Unfortunately this issue has 
 not been solved in the javatv JMF implemenation.  It is reported 
 that the thread count goes up and the memory usage rises 
 when the player repeatedly reloads and displays the mpeg files.  We 
 have observed the NT machine running out of memory after 10 hours of
 consecutive test run for the service selection.
 
 Please note that this problem has no effect on the regular usage of
 JavaTV RI.


2) Incorrect functionality

 Other known incorrect functionalities are as follows.

 1. Two xlet windows

 The RI currently uses two windows to present a service. One window
 is used for playing the video stream that is associated to a currently
 presenting service, and the other is allocated for the user to utilize
 functionalities provided with javax.tv.graphics package and
 java.awt package.  In this release, the user cannot get access to the
 window that displays the MPEG-1 video.  Thus, one cannot layer additional
 components directly over a video component in this release.

 2. DeliverySystemType

 javax.tv.service.navigation.DeliverySystemType may not reflect the
 actual type of transport which the object is delivered with.
 While the user can specify several different DeliverySystemType for
 transport dependent objects such as ServiceDetails in his/her own
 implementation of SimpleDataInterface, this release of the RI supports
 only one transport object of a type UNKNOWN.   All transport dependent
 objects is delivered with this single transport object.

3) Interaction with Solstice NFS Client(TM):

 We have observed the following behavior with Solstice NFS Client. If a
 PC is running Solstice NFS Client, modification times on directories on
 the system or "C:" drive are not updated. This causes problems with 
 CarouselFile objects that refer to directories; CarouselFileChangeEvents
 are never received. The work around is not to install Solstice NFS Client
 on your PC.

------------------------------------------------------------------------

Copyright 2000, Sun Microsystems, Inc.

