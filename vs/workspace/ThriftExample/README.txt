This Thrift Java Starting Kit was prepared by

    Prof. Ronald C. Moore
    https://www.fbi.h-da.de/organisation/personen/moore-ronald.html
    mailto:ronald.moore@h-da.de

Changes:

    Prof. Lars-Olof Burchard

This archive contains:
    simplifiedTutorial.thrift <-- definition of the interface
    CalculatorHandler.java    <-- implementation of the remote procedures
    JavaClient.java           <-- simple test client
    JavaServer.java           <-- sample server (not very safe)
    README.txt                <-- this file
    thrift-jars               <-- a directory containing required jar files

This version of this file was prepared on Friday, 6 May 2017.

This has been tested on the SS17 Linux Virtual Box Image. Only. Hopefully it will work elsewhere, but I make no claims about this. Yet.

The material here is either directly from the Apache Thrift project, or
from there but slightly modified.  The tutorial here is even simpler than
their tutorial -- see thrift.apache.org for the original!!

PREPERATION

You will need the thrift compiler executable.

You can download it for windows directly (from thrift.apache.org), or install
it with most linux distributions' package managers.

Mac OS X users should try installing homebrew, then thrift. I've seen it there.

Once you have the program installed, check your installation by running
(on the command line):

    > thrift --version

You should get this output (on the SS17 Linux VirtualBox Image):

    Thrift version 0.9.1

Note: If you install thrift from source, you should have the java library.
In this case, you should be able to use your local files instead of the
contents of the directory "thrift-jars" supplied with this tutorial!
In this case, remove all references to that directory below! These jar files
are supplied ONLY for those readers who have found (or perhaps installed the thrift compiler without java library support).

BUILDING THE TUTORIAL

First, compile the thrift file:

    > thrift -gen java simplifiedTutorial.thrift

After this, your working directory should contain a new directory named
"gen-java"(which itself contains a directory named "simpleTutorial").

There may be a better way to build these java files, but the following worked
for me:

    > javac -cp ./thrift-jars/\*\: gen-java/simpleTutorial/*.java

The "business logic" for the tutorial is in the three files
CalculatorHandler.java, JavaClient.java and JavaServer.java. These need to be
compiled next:

    > javac -cp ./thrift-jars/\*\:gen-java: *.java

RUNNING THE SERVER

    > java -cp ./thrift-jars/\*\:gen-java: JavaServer
    Starting the simple server...
    add(1,1)
    calculate(1, {DIVIDE,1,0})
    calculate(1, {SUBTRACT,15,10})

(Some of the output will only appear after the client has been run).

RUNNING THE CLIENT

    >  java -cp ./thrift-jars/\*\:gen-java: JavaClient
    Received 1
    1+1=2
    Received 2
    Invalid operation: Cannot divide by 0
    Received 3
    15-10=5


For the complete tutorial, please see http://thrift.apache.org 




