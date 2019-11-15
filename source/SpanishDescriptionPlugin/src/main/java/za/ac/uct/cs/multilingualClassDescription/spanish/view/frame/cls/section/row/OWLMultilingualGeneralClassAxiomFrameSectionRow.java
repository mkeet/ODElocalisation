package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLGeneralAxiomEditor;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import za.ac.uct.cs.multilingualClassDescription.spanish.AbstractOWLMultilingualFrameSectionRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2017/03/22.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualGeneralClassAxiomFrameSectionRow extends AbstractOWLMultilingualFrameSectionRow<OWLClass, OWLClassAxiom, OWLClassAxiom> {

    public OWLMultilingualGeneralClassAxiomFrameSectionRow(OWLEditorKit owlEditorKit, OWLFrameSection<OWLClass, OWLClassAxiom, OWLClassAxiom> section, OWLOntology ontology, OWLClass rootObject, OWLClassAxiom axiom) {
        super(owlEditorKit, section, ontology, rootObject, axiom);
    }

    @Override
    protected OWLObjectEditor<OWLClassAxiom> getObjectEditor() {
        OWLGeneralAxiomEditor editor = new OWLGeneralAxiomEditor(getOWLEditorKit());
        editor.setEditedObject(getAxiom());
        return editor;
    }

    public boolean isEditable() {return false;}

    @Override
    protected OWLClassAxiom createAxiom(OWLClassAxiom editedObject) {
        return editedObject;
    }

    @Override
    public List<? extends OWLObject> getManipulatableObjects() {
        ArrayList<OWLClassAxiom> list = new ArrayList<>();
        list.add(getAxiom());
        return list;
    }
}
