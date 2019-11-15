package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.objectproperty.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.editor.OWLObjectPropertyExpressionEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import za.ac.uct.cs.multilingualClassDescription.spanish.AbstractOWLMultilingualFrameSectionRow;

import java.util.Arrays;
import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Jan-2007<br><br>
 *
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 *
 */
public class OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSectionRow extends AbstractOWLMultilingualFrameSectionRow<OWLObjectProperty, OWLSubObjectPropertyOfAxiom, OWLObjectPropertyExpression> {

    public OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSectionRow(OWLEditorKit owlEditorKit,
                                                                             OWLFrameSection<OWLObjectProperty, OWLSubObjectPropertyOfAxiom, OWLObjectPropertyExpression> section,
                                                                             OWLOntology ontology, OWLObjectProperty rootObject,
                                                                             OWLSubObjectPropertyOfAxiom axiom) {
        super(owlEditorKit, section, ontology, rootObject, axiom);
    }


    protected OWLObjectEditor<OWLObjectPropertyExpression> getObjectEditor() {
        OWLObjectPropertyExpressionEditor editor = new OWLObjectPropertyExpressionEditor(getOWLEditorKit());
        OWLObjectPropertyExpression p = getAxiom().getSuperProperty();
        editor.setEditedObject(p);
        return editor;
    }


    protected OWLSubObjectPropertyOfAxiom createAxiom(OWLObjectPropertyExpression editedObject) {
        return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(getRoot(), editedObject);
    }


    /**
     * Gets a list of objects contained in this row.  These objects
     * could be placed on the clip board during a copy operation,
     * or navigated to etc.
     */
    public List<OWLObjectPropertyExpression> getManipulatableObjects() {
        return Arrays.asList(getAxiom().getSuperProperty());
    }
}
