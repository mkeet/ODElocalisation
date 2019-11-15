# Afrikaans Class and Object Property Description  - Plugin for Protégé
This plugin is a "view" component for Protégé.  
It shows the "Description" view of the selected class and object property in Afrikaans.

## To simply install the plugin:

### Prerequisites 
Ensure that you have Protege 5.2.0 or newer installed.

### Instructions
Copy the JAR file from the "target" folder in this repository and put it in the "plugins" folder of your Protégé installation.

## To build from source:

### Prerequisites
Ensure that you have the latest version of Maven installed.  

### Instructions
1. Launch Terminal
2. cd to “AfrikaansDescriptionPlugin” folder
3. Type “mvn clean install”
4. JAR file "afrikaans-description-plugin-3.0.jar" will be created in the “target” folder.
5. Copy the JAR file into the “plugins” folder within Protege root folder. 
6. Launch Protégé
7. Go to Window>View>Class Views > Beskrywing
8. Drop the plugin in a window slot
9. Go to Window>View>Object Property Views > Beskrywing
10. Drop the plugin in a window slot
