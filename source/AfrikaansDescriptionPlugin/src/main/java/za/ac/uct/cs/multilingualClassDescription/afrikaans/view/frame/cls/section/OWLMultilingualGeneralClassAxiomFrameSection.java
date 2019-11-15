package za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.cls.section;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.OWLWorkspace;
import org.protege.editor.owl.ui.editor.OWLGeneralAxiomEditor;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.AbstractOWLFrameSection;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.protege.editor.owl.ui.frame.OWLFrameSectionRow;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLAxiomVisitorExAdapter;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.EnumWord;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.Utils;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.cls.section.row.OWLMultilingualGeneralClassAxiomFrameSectionRow;

import javax.swing.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Adam on 2017/03/22.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualGeneralClassAxiomFrameSection extends AbstractOWLFrameSection<
        OWLClass,
        OWLClassAxiom,
        OWLClassAxiom> {

    public OWLMultilingualGeneralClassAxiomFrameSection(OWLEditorKit editorKit, OWLFrame<? extends OWLClass> frame) {
        super(editorKit, Utils.getDico().getWord(EnumWord.GENERAL_CLASS_AXIOMS_LABEL),
                Utils.getDico().getWord(EnumWord.GENERAL_CLASS_AXIOMS_ROW_LABEL), frame);
    }

    @Override
    protected OWLClassAxiom createAxiom(OWLClassAxiom object) {
        return object;
    }

    @Override
    public OWLObjectEditor<OWLClassAxiom> getObjectEditor() {
        return new OWLGeneralAxiomEditor(getOWLEditorKit());
    }

    public boolean canAdd() {
        return true;
    }

    @Override
    protected void refill(OWLOntology ontology) {
        OWLWorkspace workspace = getOWLEditorKit().getOWLWorkspace();
        OWLClass cls = workspace.getOWLSelectionModel().getLastSelectedClass();
        if(cls == null) {
            return;
        }
        for(OWLClassAxiom ax : ontology.getGeneralClassAxioms()) {
            if (ax.containsEntityInSignature(cls)) {
                OWLEditorKit owlEditorKit = getOWLEditorKit();
                addRow(new OWLMultilingualGeneralClassAxiomFrameSectionRow(owlEditorKit, this, ontology, getRootObject(), ax));
            }
        }
    }


    public void handleEditingFinished(Set<OWLClassAxiom> editedObjects) {
        checkEditedAxiom(getOWLEditorKit(), editedObjects, getRootObject());
        super.handleEditingFinished(editedObjects);
    }

    static void checkEditedAxiom(OWLEditorKit editorKit, Set<OWLClassAxiom> editedObjects, OWLClass root) {
        if(editedObjects.isEmpty()) {
            return;
        }

        OWLClassAxiom axiom = editedObjects.iterator().next();
        if(!axiom.containsEntityInSignature(root)) {
            String classesInSigRendering = "";
            for(Iterator<OWLClass> it = axiom.getClassesInSignature().iterator(); it.hasNext(); ) {
                OWLClass cls = it.next();
                classesInSigRendering += editorKit.getModelManager().getRendering(cls);
                if(it.hasNext()) {
                    classesInSigRendering += ",\n";
                }
            }

            JOptionPane.showMessageDialog(editorKit.getOWLWorkspace(),
                    "The axiom that you edited has been added to the ontology.  However, it will not be visible " +
                            "in the view below as it does not mention the selected class (" + editorKit.getOWLModelManager().getRendering(root) + ").\n" +
                            "To view the axiom, select any of the classes it mentions: \n" + classesInSigRendering);
        }
    }
    @Override
    protected void clear() {

    }

    @Override
    protected boolean isResettingChange(OWLOntologyChange change) {
        if(!change.isAxiomChange()) {
            return false;
        }
        if(!change.getSignature().contains(getRootObject())) {
            return false;
        }
        OWLAxiom axiom = change.getAxiom();
        return axiom.accept(new OWLAxiomVisitorExAdapter<Boolean>(false) {

            @Override
            public Boolean visit(OWLSubClassOfAxiom axiom) {
                return axiom.isGCI();
            }

            @Override
            public Boolean visit(OWLEquivalentClassesAxiom axiom) {
                return !axiom.contains(getRootObject());
            }

            @Override
            public Boolean visit(OWLDisjointClassesAxiom axiom) {
                return !axiom.contains(getRootObject());
            }
        });
    }

    @Override
    public Comparator<OWLFrameSectionRow<OWLClass, OWLClassAxiom, OWLClassAxiom>> getRowComparator() {
        return null;
    }

}

