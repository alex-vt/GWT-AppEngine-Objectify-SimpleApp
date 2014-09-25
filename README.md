##GWT-AppEngine-Objectify-SimpleApp

A very simple GWT app which can run on App Engine and store data using Objectify.

####Available Online:

http://gwt-objectify-simpleapp.appspot.com/


###Running the app

You need Java 1.7+ and Maven 3+.

	git clone https://github.com/oleksiykovtun/GWT-AppEngine-Objectify-SimpleApp.git
    cd GWT-AppEngine-Objectify-SimpleApp
	mvn appengine:devserver

###Deploying the app to the cloud

After specifying application ID in `src/main/webapp/WEB-INF/appengine-web.xml`, run

    mvn appengine:update
