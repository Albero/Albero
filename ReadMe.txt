Starten met Albero
==================

Installeer java 1.5 of 1.6.
	
MySQL (bijvoorbeeld als onderdeel van Mamp) installeren en een database 'albero' aanmaken, 
	- root/<geen ww> alle rechten geven of de gewenste user klaar zetten in 
	      * web/management/src/resource/salbero-context.xml
	      * examples/src/test/resources/jetty/jetty.xml (JNDI configuratie)
	      Als je albero deployed als losse war, dan moet je een jndi datasource aanmaken met de naam 'jdbc/albero-ds'
	- poort 3306 laten luisteren (MySQL default, maar niet de Mamp default; bij Mamp onder preference/ports instelbaar)
	
In de database de volgende SQL uitvoeren:
	USE albero;
	DROP TABLE IF EXISTS trees;
	CREATE TABLE trees
	(code varchar(255),
	parser varchar(255),
	tree text);
	INSERT INTO `trees` 
	VALUES('demo', 'groovy', 
	'tree(code:''demo''){model{isWerkend(''text'')};nodes{multipleChoiceQuestion(labelText:''Werkt het?'',answers:''isWerkend'',options:[''ja'',''nee''])};results{model{uitslag(''text'')};rules{rule{when isWerkend.is(''ja'');set''uitslag'',''eureka!!''};rule{when isWerkend.is(''nee'');set ''uitslag'',''leugenaar!''}}}}')

Uit svn https://svn.profict.nl/svn/projecten/albero/ Albero ophalen/updaten

In een terminal venster, in de root van Albero: ./gradlew -if albero-examples-web:jettyRun

Vervolgens kun je op http://localhost:8080/  de boom doorlopen
