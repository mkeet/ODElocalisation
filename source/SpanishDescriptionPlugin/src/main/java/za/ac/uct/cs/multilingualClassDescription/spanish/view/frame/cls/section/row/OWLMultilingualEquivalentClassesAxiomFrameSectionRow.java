package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.CollectionFactory;
import za.ac.uct.cs.multilingualClassDescription.spanish.AbstractOWLMultilingualFrameSectionRow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Casey on 19-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualEquivalentClassesAxiomFrameSectionRow extends AbstractOWLMultilingualFrameSectionRow<OWLClassExpression, OWLEquivalentClassesAxiom, OWLClassExpression> {

    public OWLMultilingualEquivalentClassesAxiomFrameSectionRow(OWLEditorKit editorKit, OWLFrameSection<OWLClassExpression, OWLEquivalentClassesAxiom,
            OWLClassExpression> section, OWLOntology ontology, OWLClassExpression rootObject, OWLEquivalentClassesAxiom axiom) {
        super(editorKit, section, ontology, rootObject, axiom);
    }

    @Override
    protected OWLEquivalentClassesAxiom createAxiom(OWLClassExpression editedObject) {
        return getOWLDataFactory().getOWLEquivalentClassesAxiom(CollectionFactory.createSet(getRoot(), editedObject));
    }

    protected List<OWLClassExpression> getObjects() {
        Set<OWLClassExpression> clses = new HashSet<>(getAxiom().getClassExpressions());
        clses.remove(getRoot());
        return new ArrayList<>(clses);
    }

    public boolean isEditable() {
        Set<OWLClassExpression> descs = new HashSet<>(getAxiom().getClassExpressions());
        descs.remove(getRoot());
        return descs.size() == 1;
    }
    @Override
    public boolean isDeleteable() {
        return true;
    }

    protected OWLObjectEditor<OWLClassExpression> getObjectEditor() {
        Set<OWLClassExpression> descs = new HashSet<>(getAxiom().getClassExpressions());
        descs.remove(getRoot());
        return descs.size() == 1 ? getOWLEditorKit().getWorkspace().getOWLComponentFactory().getOWLClassDescriptionEditor(descs.iterator().next(), AxiomType.EQUIVALENT_CLASSES)
                : null;
    }

    public boolean checkEditorResults(OWLObjectEditor<OWLClassExpression> editor) {
        Set<OWLClassExpression> equivalents = editor.getEditedObjects();
        return equivalents.size() != 1 || !equivalents.contains(getRootObject());
    }

    @Override
    public void handleEditingFinished(Set<OWLClassExpression> editedObjects) {
        editedObjects = new HashSet<>(editedObjects);
        editedObjects.remove(getRootObject());
        super.handleEditingFinished(editedObjects);
    }

    /**
     * Gets a list of objects contained in this row.  These objects
     * could be placed on the clip board during a copy operation,
     * or navigated to etc.
     */
    public List<OWLClassExpression> getManipulatableObjects() {
        return getObjects();
    }
}
