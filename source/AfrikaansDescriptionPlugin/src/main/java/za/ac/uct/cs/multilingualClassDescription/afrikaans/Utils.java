package za.ac.uct.cs.multilingualClassDescription.afrikaans;

import org.protege.editor.owl.OWLEditorKit;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.syntax.MultilingualManchesterOWLSyntax;

import java.awt.*;
import java.util.Map;

/**
 * Author: Toky Raboanary<br>
 * UCT - University of Cape Town<br>
 * Computer Science Department<br>
 * Date: 13-Nov-2019<br><br>
 *
 */

public class Utils {
    private static Dico dico;

    public static Dico getDico() {
        if (dico == null)
            dico = new Dico();
        return dico;
    }

    public static synchronized void UpdateOWLEditorKit(OWLEditorKit owlEditorKit) {


        // to color the new keywords with respect to the language
        Color restrictionColor = new Color(178, 0, 178);
        Color logicalOpColor = new Color(0, 178, 178);
        Color axiomColor = new Color(10, 94, 168);
        Color typeColor = new Color(178, 178, 178);

        Map<String, Color> keyWordColorMap = owlEditorKit.getOWLWorkspace().getKeyWordColorMap();

        for (MultilingualManchesterOWLSyntax keyword : MultilingualManchesterOWLSyntax.values()) {

            if (keyword.isAxiomKeyword()) {
                String[] keys = keyword.toString().split("[ #]+");
                for (String key : keys) {
                    if (!keyWordColorMap.containsKey(key))
                        keyWordColorMap.put(key, axiomColor);
                    else keyWordColorMap.replace(key, axiomColor);

                    if (!keyWordColorMap.containsKey(key + ":"))
                        keyWordColorMap.put(key + ":", axiomColor);
                    else keyWordColorMap.replace(key, axiomColor);
                }
            } else if (keyword.isClassExpressionConnectiveKeyword()) {
                String[] keys = keyword.toString().split("[ #]+");
                for (String key : keys) {
                    if (!keyWordColorMap.containsKey(key))
                        keyWordColorMap.put(key, logicalOpColor);
                    else keyWordColorMap.replace(key, logicalOpColor);
                }
            } else if (keyword.isClassExpressionQuantifierKeyword()) {
                String[] keys = keyword.toString().split("[ #]+");
                for (String key : keys) {
                    if (!keyWordColorMap.containsKey(key))
                        keyWordColorMap.put(key, restrictionColor);
                    else keyWordColorMap.replace(key, restrictionColor);
                }
            } else if (keyword.isSectionKeyword()) {
                String[] keys = keyword.toString().split("[ #]+");
                for (String key : keys) {
                    if (!keyWordColorMap.containsKey(key))
                        keyWordColorMap.put(key, typeColor);
                    else keyWordColorMap.replace(key, typeColor);

                    if (!keyWordColorMap.containsKey(key + ":"))
                        keyWordColorMap.put(key + ":", typeColor);
                    else keyWordColorMap.replace(key + ":", typeColor);
                }
            }
        }

    }
}
