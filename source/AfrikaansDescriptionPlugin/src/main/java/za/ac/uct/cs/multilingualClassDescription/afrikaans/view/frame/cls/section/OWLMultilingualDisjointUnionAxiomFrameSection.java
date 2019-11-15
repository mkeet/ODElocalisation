package za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.cls.section;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLClassExpressionSetEditor;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.AbstractOWLFrameSection;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.protege.editor.owl.ui.frame.OWLFrameSectionRow;
import org.semanticweb.owlapi.model.*;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.EnumWord;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.Utils;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.cls.section.row.OWLMultilingualDisjointUnionAxiomFrameSectionRow;

import java.util.Comparator;
import java.util.Set;

/**
 * Created by Casey on 23-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualDisjointUnionAxiomFrameSection extends AbstractOWLFrameSection<OWLClass, OWLDisjointUnionAxiom, Set<OWLClassExpression>> {

    public OWLMultilingualDisjointUnionAxiomFrameSection(OWLEditorKit editorKit, OWLFrame<OWLClass> frame) {
        super(editorKit, Utils.getDico().getWord(EnumWord.DISJOINT_UNION_OF_LABEL), Utils.getDico().getWord(EnumWord.DISJOINT_UNION_OF_ROW_LABEL), frame);
    }

    @Override
    protected OWLDisjointUnionAxiom createAxiom(Set<OWLClassExpression> owlClassExpressions) {
        return getOWLDataFactory().getOWLDisjointUnionAxiom(getRootObject(), owlClassExpressions);
    }

    @Override
    public OWLObjectEditor<Set<OWLClassExpression>> getObjectEditor() {
        return new OWLClassExpressionSetEditor(getOWLEditorKit());
    }

    @Override
    protected void refill(OWLOntology owlOntology) {
        for (OWLDisjointUnionAxiom axiom : owlOntology.getDisjointUnionAxioms(getRootObject())) {
            OWLEditorKit owlEditorKit = getOWLEditorKit();
            addRow(new OWLMultilingualDisjointUnionAxiomFrameSectionRow(owlEditorKit, this, owlOntology, getRootObject(), axiom));
        }
    }

    @Override
    public boolean checkEditorResults(OWLObjectEditor<Set<OWLClassExpression>> editor) {
        Set<OWLClassExpression> disjoints = editor.getEditedObject();
        return disjoints.size() >= 2;
    }

    @Override
    protected void clear() {

    }

    @Override
    public boolean canAdd() {
        return true;
    }

    @Override
    protected boolean isResettingChange(OWLOntologyChange change) {
        return change.isAxiomChange() &&
                change.getAxiom() instanceof OWLDisjointUnionAxiom &&
                ((OWLDisjointUnionAxiom) change.getAxiom()).getOWLClass().equals(getRootObject());
    }

    @Override
    public Comparator<OWLFrameSectionRow<OWLClass, OWLDisjointUnionAxiom, Set<OWLClassExpression>>> getRowComparator() {
        return null;
    }
}
