Classpaths

Classpaths are a bit tricky with OpenMHP. The main process is launched from command line, and it has naturally it's own classpath. The applications are launched in to separate Virtual Machines, so they need also own classpaths.

If your copy from OpenMHP won't start, you should check the classpath from the file "startmhp.bat". Check that the folder names are correct and jar-files can be found from the path.

The xlet classpath's are defined in the file named "constants.properties" which can be found under the folder "static". Check that the folder names are correct and jar-files can be found from the path.

The classpath handling is under construction, and hopefully it will change in the future.