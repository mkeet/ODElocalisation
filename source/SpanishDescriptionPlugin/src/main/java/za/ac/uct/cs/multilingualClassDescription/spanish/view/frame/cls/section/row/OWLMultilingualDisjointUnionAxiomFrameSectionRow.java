package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLClassExpressionSetEditor;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import za.ac.uct.cs.multilingualClassDescription.spanish.AbstractOWLMultilingualFrameSectionRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Casey on 23-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualDisjointUnionAxiomFrameSectionRow extends AbstractOWLMultilingualFrameSectionRow<OWLClass, OWLDisjointUnionAxiom, Set<OWLClassExpression>> {


    public OWLMultilingualDisjointUnionAxiomFrameSectionRow(OWLEditorKit owlEditorKit,
                                                            OWLFrameSection<OWLClass, OWLDisjointUnionAxiom, Set<OWLClassExpression>> section,
                                                            OWLOntology ontology, OWLClass rootObject,
                                                            OWLDisjointUnionAxiom axiom) {
        super(owlEditorKit, section, ontology, rootObject, axiom);
    }

    public List<OWLClassExpression> getManipulatableObjects() {
        return new ArrayList<>(getAxiom().getClassExpressions());
    }

    @Override
    protected OWLObjectEditor<Set<OWLClassExpression>> getObjectEditor() {
        return new OWLClassExpressionSetEditor(getOWLEditorKit(), getManipulatableObjects());
    }

    @Override
    protected OWLDisjointUnionAxiom createAxiom(Set<OWLClassExpression> editedObject) {
        return getOWLDataFactory().getOWLDisjointUnionAxiom(getRootObject(), editedObject);
    }

    @Override
    public boolean checkEditorResults(OWLObjectEditor<Set<OWLClassExpression>> editor) {
        Set<OWLClassExpression> disjoints = editor.getEditedObject();
        return disjoints.size() >= 2;
    }
}
