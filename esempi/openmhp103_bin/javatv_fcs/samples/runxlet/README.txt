			     __RunXlet__

	       "A quick and dirty way to test an Xlet"

OVERVIEW
--------

RunXlet was developed to serve two purposes, 1) to write a quick and
dirty application to allow someone to test and Xlet, and 2) provide
some more reference implementation sample code.

The RunXlet class will take the class name of an xlet that is on the
CLASSPATH as a command line argument and execute that Xlet. RunXlet
has the added feature of populating the SI database.

USAGE
-----

(assuming your CLASSPATH is set)

% pjava RunXlet <classname>

There is an additional option '-n' which will not load the
SI DB if it is set.

% pjava RunXlet -n <classname>

A simple C-Shell script is included as an example.

REQUIREMENTS
------------

1) You must have the following in your CLASSPATH
   - javatv.jar (tested with 'ai' build)
   - classes.zip (from dino or JDK)
   - parser.jar (from JAXP (java xml parser))
   - jax.jar (from JAXP)
   - any classes required by your Xlet

2) You will also need the following:
   - Compiled versions of the following:
	RunXlet.java (Xlet runner)
	SIParser.java (XML SI parser)
	Any Xlets you intend to run

   - The XML file and DTD for Java TV
	- tck.xml
	- tckxml.dtd
     (these are hard wired into RunXlet)

