package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.inference.ReasonerPreferences;
import org.protege.editor.owl.ui.editor.OWLClassExpressionSetEditor;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.protege.editor.owl.ui.frame.OWLFrameSectionRow;
import org.protege.editor.owl.ui.frame.cls.AbstractOWLClassAxiomFrameSection;
import org.protege.editor.owl.ui.frame.cls.OWLDisjointClassesAxiomFrameSectionRow;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import za.ac.uct.cs.multilingualClassDescription.spanish.EnumWord;
import za.ac.uct.cs.multilingualClassDescription.spanish.Utils;
import za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section.row.OWLMultilingualDisjointClassesAxiomFrameSectionRow;

import java.util.*;

/**
 * Created by Adam on 2017/03/20.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualDisjointClassesAxiomFrameSection extends AbstractOWLClassAxiomFrameSection<OWLDisjointClassesAxiom, Set<OWLClassExpression>> {

    private static final String LABEL = Utils.getDico().getWord(EnumWord.DISJOINT_WITH_LABEL);

    private Set<OWLClassExpression> added = new HashSet<>();


    public OWLMultilingualDisjointClassesAxiomFrameSection(OWLEditorKit editorKit, OWLFrame<OWLClass> frame) {
        super(editorKit, LABEL, Utils.getDico().getWord(EnumWord.DISJOINT_WITH_ROW_LABEL), frame);
    }

    public boolean canAdd() {
        return true;
    }

    @Override
    protected void addAxiom(OWLDisjointClassesAxiom ax, OWLOntology ont) {
        addRow(new OWLMultilingualDisjointClassesAxiomFrameSectionRow(getOWLEditorKit(), this, ont, getRootObject(), ax));
        added.addAll(ax.getClassExpressions());
    }


    @Override
    protected Set<OWLDisjointClassesAxiom> getClassAxioms(OWLClassExpression owlClassExpression, OWLOntology owlOntology) {
        if (!owlClassExpression.isAnonymous()) {
            return owlOntology.getDisjointClassesAxioms(owlClassExpression.asOWLClass());
        } else {
            Set<OWLDisjointClassesAxiom> axioms = new HashSet<>();
            for (OWLAxiom ax : owlOntology.getGeneralClassAxioms()) {
                if (ax instanceof OWLDisjointClassesAxiom &&
                        ((OWLDisjointClassesAxiom) ax).getClassExpressions().contains(owlClassExpression)) {
                    axioms.add((OWLDisjointClassesAxiom) ax);
                }
            }
            return axioms;
        }
    }

    @Override
    protected boolean isResettingChange(OWLOntologyChange change) {
        return change.isAxiomChange() &&
                change.getAxiom() instanceof OWLDisjointClassesAxiom &&
                ((OWLDisjointClassesAxiom) change.getAxiom()).getClassExpressions().contains(getRootObject());
    }


    protected OWLDisjointClassesAxiom createAxiom(Set<OWLClassExpression> object) {
        object.add(getRootObject());
        return getOWLDataFactory().getOWLDisjointClassesAxiom(object);
    }



    @Override
    public OWLObjectEditor<Set<OWLClassExpression>> getObjectEditor() {
        return new OWLClassExpressionSetEditor(getOWLEditorKit());
    }

    @Override
    protected void refillInferred() {
        getOWLModelManager().getReasonerPreferences().executeTask(ReasonerPreferences.OptionalInferenceTask.SHOW_INFERRED_DISJOINT_CLASSES, () -> {
            OWLReasoner reasoner = getOWLModelManager().getReasoner();
            if (!reasoner.isConsistent()) {
                return;
            }
            NodeSet<OWLClass> disjointFromRoot = reasoner.getSubClasses(getOWLDataFactory().getOWLObjectComplementOf(getRootObject()), true);
            for (OWLClass c : disjointFromRoot.getFlattened()) {
                if (!added.contains(c) && !c.equals(getRootObject())) {
                    addInferredRowIfNontrivial(new OWLDisjointClassesAxiomFrameSectionRow(
                                    getOWLEditorKit(),
                                    this,
                                    null,
                                    getRootObject(),
                                    getOWLModelManager().getOWLDataFactory().getOWLDisjointClassesAxiom(getRootObject(), c)
                            )
                    );
                    added.add(c);
                }
            }
        });
    }

    public boolean checkEditorResults(OWLObjectEditor<Set<OWLClassExpression>> editor) {
        Set<OWLClassExpression> disjoints = editor.getEditedObject();
        return disjoints.size() != 1 || !disjoints.contains(getRootObject());
    }


    public boolean canAcceptDrop(List<OWLObject> objects) {
        for (OWLObject obj : objects) {
            if (!(obj instanceof OWLClassExpression)) {
                return false;
            }
        }
        return true;
    }


    public boolean dropObjects(List<OWLObject> objects) {
        List<OWLOntologyChange> changes = new ArrayList<>();
        Set<OWLClassExpression> descriptions = new HashSet<>();
        descriptions.add(getRootObject());
        for (OWLObject obj : objects) {
            if (obj instanceof OWLClassExpression) {
                OWLClassExpression desc = (OWLClassExpression) obj;
                descriptions.add(desc);
            }
            else {
                return false;
            }
        }
        if (descriptions.size() > 1) {
            OWLAxiom ax = getOWLDataFactory().getOWLDisjointClassesAxiom(descriptions);
            changes.add(new AddAxiom(getOWLModelManager().getActiveOntology(), ax));
            getOWLModelManager().applyChanges(changes);
        }
        return true;
    }


    @Override
    protected void clear() {
        added.clear();
    }

    @Override
    public Comparator<OWLFrameSectionRow<OWLClassExpression, OWLDisjointClassesAxiom, Set<OWLClassExpression>>> getRowComparator() {
        return null;
    }
}