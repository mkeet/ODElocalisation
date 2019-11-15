package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLClassExpressionSetEditor;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import za.ac.uct.cs.multilingualClassDescription.spanish.AbstractOWLMultilingualFrameSectionRow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Adam on 19-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualDisjointClassesAxiomFrameSectionRow extends AbstractOWLMultilingualFrameSectionRow<OWLClassExpression, OWLDisjointClassesAxiom, Set<OWLClassExpression>> {


    public OWLMultilingualDisjointClassesAxiomFrameSectionRow(OWLEditorKit owlEditorKit,
                                                  OWLFrameSection<OWLClassExpression, OWLDisjointClassesAxiom, Set<OWLClassExpression>> section,
                                                  OWLOntology ontology, OWLClassExpression rootObject,
                                                  OWLDisjointClassesAxiom axiom) {
        super(owlEditorKit, section, ontology, rootObject, axiom);
    }


    protected OWLObjectEditor<Set<OWLClassExpression>> getObjectEditor() {
        return new OWLClassExpressionSetEditor(getOWLEditorKit(), getManipulatableObjects());
    }

    @Override
    public boolean checkEditorResults(OWLObjectEditor<Set<OWLClassExpression>> editor) {
        Set<OWLClassExpression> disjoints = editor.getEditedObject();
        return disjoints.size() != 1 || !disjoints.contains(getRoot());
    }


    protected OWLDisjointClassesAxiom createAxiom(Set<OWLClassExpression> editedObject) {
        editedObject.add(getRootObject());
        return getOWLDataFactory().getOWLDisjointClassesAxiom(editedObject);
    }


    /**
     * Gets a list of objects contained in this row.
     */
    public List<OWLClassExpression> getManipulatableObjects() {
        Set<OWLClassExpression> disjointClasses = new HashSet<>(getAxiom().getClassExpressions());
        disjointClasses.remove(getRootObject());
        return new ArrayList<>(disjointClasses);
    }
}
