# How to create a new plugin for another language
This plugin is designed to make easy the creation of a new plugin for another language.

## Instructions

1. Modify “SpanishDescriptionPlugin” folder to “OtherLanguageDescriptionPlugin”
2. Modify the content of src/main/resources/dico.xml with respect the new language
3. Open the project using an IDE (we used IntelliJ IDEA)
4. Rename the package "za.ac.uct.cs.multilingualClassDescription.spanish"
   to "za.ac.uct.cs.multilingualClassDescription.otherLanguage" 
   using Refactor 
5. Update "src/main/resources/plugin.xml": extension id, label value and class value (name of the package)
6. Update "pom.xml": artifactId, name and description

7. Launch Terminal
8. cd to “OtherLanguageDescriptionPlugin” folder   
9. Type “mvn clean install”
10. JAR file "otherLanguage-description-plugin-1.0.jar" will be created in the “target” folder.
11. Copy the JAR file into the “plugins” folder within Protege root folder. 
12. Launch Protégé
13. Go to Window>View>Class Views > Description (Other Language)
14. Drop the plugin in a window slot
15. Go to Window>View>Object Property Views > Description (Other Language)
16. Drop the plugin in a window slot
