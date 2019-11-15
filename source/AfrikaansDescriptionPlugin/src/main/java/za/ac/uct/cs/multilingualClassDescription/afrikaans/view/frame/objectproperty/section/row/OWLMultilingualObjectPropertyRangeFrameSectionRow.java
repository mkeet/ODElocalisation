package za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.objectproperty.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.*;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.AbstractOWLMultilingualFrameSectionRow;

import java.util.Arrays;
import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Jan-2007<br><br>
 *
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualObjectPropertyRangeFrameSectionRow extends AbstractOWLMultilingualFrameSectionRow<OWLObjectProperty, OWLObjectPropertyRangeAxiom, OWLClassExpression> {

    public OWLMultilingualObjectPropertyRangeFrameSectionRow(OWLEditorKit editorKit,
                                                             OWLFrameSection<OWLObjectProperty, OWLObjectPropertyRangeAxiom, OWLClassExpression> section,
                                                             OWLOntology ontology,
                                                             OWLObjectProperty rootObject, OWLObjectPropertyRangeAxiom axiom) {
        super(editorKit, section, ontology, rootObject, axiom);
    }


    protected OWLObjectEditor<OWLClassExpression> getObjectEditor() {
        return getOWLEditorKit().getWorkspace().getOWLComponentFactory().getOWLClassDescriptionEditor(getAxiom().getRange(), AxiomType.OBJECT_PROPERTY_RANGE);
    }


    protected OWLObjectPropertyRangeAxiom createAxiom(OWLClassExpression editedObject) {
        return getOWLDataFactory().getOWLObjectPropertyRangeAxiom(getRoot(), editedObject);
    }


    /**
     * Gets a list of objects contained in this row.  These objects
     * could be placed on the clip board during a copy operation,
     * or navigated to etc.
     */
    public List<OWLClassExpression> getManipulatableObjects() {
        return Arrays.asList(getAxiom().getRange());
    }
}
