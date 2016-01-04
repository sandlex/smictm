*smictm* is not Super Mega Incredibly Complicated Task Manager

*smictm* is simple task manager providing you with answers on questions:
 * What should be done?
 * What I am working on right now?a
 * What have I been doing in particular day?

##Installation
*smictm* requires [JRE installed](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
Download latest version of application from [here](http://code.google.com/p/smictm/downloads/list).

##Starting application
To run application from console go to the folder containing downloaded jar file and run command:
`java -jar smictm-0.x.jar project`

If parameter `project` is omitted then project with default name "Tasks" will be created. You can use a folder name with path: `C:\dropbox\project`.

##Using application
Step | Description
--------- | ---------
Input field: Ctrl-Enter / Command-Enter | Add task or update task
Input field: Enter | Go to the next row. First row is displayed in tables as a task name
Input field: Esc | Clear input area or/and exit editing mode
Task table: Double click on "..." column | Start task editing (`*` displayed)
Task table: Double click on "Task" column | Mark "In progress" task as "touched" (consumed some time but not completed)
Task table: Change value in "State" column | Bring a task to the new state
Calendar: Click on date | Display tasks state changes for selected date


![screenshot](https://raw.githubusercontent.com/sandlex/smictm/master/img/smictm-0.2.png)

##Changelog
Version 0.2 (November 26, 2010)
 * Task editing implemented.
 * Task name and description displayed in tooltip.
 * Added confirmation dialog on state change action.
 * Default system look and feel is applied on application start.
 * Small fixes and additions.

*smictm* uses:
 * http://www.toedter.com/en/jcalendar/index.html
 * http://xstream.codehaus.org/
