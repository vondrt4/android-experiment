Experimental Android project
============================

This is my proof of concept project to demonstrate how to design Android 2.3.x application with the [MVC Passive View](http://martinfowler.com/eaaDev/PassiveScreen.html) pattern and [annotations](https://github.com/excilys/androidannotations). The main goal is a clean aplication design, enhance testability and reduce of boilerplate code.

It’s very simple application for fast sharing of text notes between Android and desktop through some sharing server with RESTful interface secured with OAuth2.

Originally it was created as a seminar work to school, but I didn’t have time to finish it yet. There is no documentation, comments or tests and some features are not completed. However I’ve shown it to @jnv few days ago, he liked it and told me that it’s better to publish it as it is then wait to some day when I’ll have a time to finish it. So there it is, hope somebody will incidentally find it.

KOSAPI and CVUT OAuth2 modification
===================================

This version of the project was modified to authenticate against the CVUT OAuth2 server using the installed application workflow. Storing tokens to persistent memory is not implemented, but a framework for storing codes exists in the application.

Parsing the KOSAPI Atom messages was attempted using AndroidAnnotations' dependency ROME, however this has failed. It is possible to read metadata, but not the XML encoded <content> tag. See MainController.

If anyone wants to continue where I left off, pointers may lie here:
http://stackoverflow.com/questions/342619/using-the-rome-java-api-to-access-metadata-fields
http://sujitpal.blogspot.co.uk/2007/10/custom-modules-with-rome.html
https://rometools.jira.com/wiki/display/MODULES/Home

If ROME fails, it's possible to try SimpleXML by using SimpleXmlHttpMessageConverter instead of AtomFeedHttpMessageConverter in RESTServicesFacade.