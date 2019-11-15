package za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.cls.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.AbstractOWLMultilingualFrameSectionRow;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Adam on 19-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualSubClassesAxiomFrameSectionRow extends AbstractOWLMultilingualFrameSectionRow<OWLClassExpression, OWLSubClassOfAxiom, OWLClassExpression> {

    public OWLMultilingualSubClassesAxiomFrameSectionRow(OWLEditorKit editorKit, OWLFrameSection<OWLClassExpression, OWLSubClassOfAxiom,
            OWLClassExpression> section, OWLOntology ontology, OWLClassExpression rootObject, OWLSubClassOfAxiom axiom) {
        super(editorKit, section, ontology, rootObject, axiom);
    }

   /* @Override
    protected OWLObjectEditor getObjectEditor() {
        return null;
    }*/

   @Override
    protected OWLObjectEditor<OWLClassExpression> getObjectEditor() {
        return getOWLEditorKit().getWorkspace().getOWLComponentFactory().getOWLClassDescriptionEditor(getAxiom().getSuperClass(), AxiomType.SUBCLASS_OF);
    }

    @Override
    protected OWLSubClassOfAxiom createAxiom(OWLClassExpression editedObject) {
        return getOWLDataFactory().getOWLSubClassOfAxiom(getRootObject(), editedObject);
    }


    /**
     * Gets a list of objects contained in this row.  These objects
     * could be placed on the clip board during a copy operation,
     * or navigated to etc.
     */
    public List<OWLClassExpression> getManipulatableObjects() {
        return Arrays.asList(getAxiom().getSuperClass());
    }
}