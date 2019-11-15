package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.inference.ReasonerPreferences;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.AbstractOWLFrameSection;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.protege.editor.owl.ui.frame.OWLFrameSectionRow;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import za.ac.uct.cs.multilingualClassDescription.spanish.EnumWord;
import za.ac.uct.cs.multilingualClassDescription.spanish.Utils;
import za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section.row.OWLMultilingualInheritedAnonymousClassesFrameSectionRow;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Casey on 23-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualInheritedAnonymousClassesFrameSection extends AbstractOWLFrameSection<OWLClass, OWLClassAxiom, OWLClassExpression> {

    private Set<OWLClass> processedClasses = new HashSet<>();

    public OWLMultilingualInheritedAnonymousClassesFrameSection(OWLEditorKit editorKit, OWLFrame<? extends OWLClass> frame) {
        super(editorKit, Utils.getDico().getWord(EnumWord.SUBCLASS_OF_ANONYMOUS_ANCESTOR_LABEL), // GENERAL CLASS AXIOM
                Utils.getDico().getWord(EnumWord.SUBCLASS_OF_ANONYMOUS_ANCESTOR_ROW_LABEL), frame); // ANONYMOUS ANCESTOR CLASS
    }

    // we can't new object here
    public boolean canAdd() {return false;}

    @Override
    protected OWLClassAxiom createAxiom(OWLClassExpression owlClassExpression) {
        return null;
    }

    @Override
    public OWLObjectEditor<OWLClassExpression> getObjectEditor() {
        return null;
    }

    @Override
    protected void refill(OWLOntology owlOntology) {
        Set<OWLClass> clses = getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getAncestors(getRootObject());
        clses.remove(getRootObject());
        OWLEditorKit owlEditorKit = getOWLEditorKit();

        for (OWLClass cls : clses) {
            for (OWLSubClassOfAxiom ax : owlOntology.getSubClassAxiomsForSubClass(cls)) {
                if (ax.getSuperClass().isAnonymous()) {
                    addRow(new OWLMultilingualInheritedAnonymousClassesFrameSectionRow(owlEditorKit, this, owlOntology, cls, ax));
                }
            }
            for (OWLEquivalentClassesAxiom ax : owlOntology.getEquivalentClassesAxioms(cls)) {
                addRow(new OWLMultilingualInheritedAnonymousClassesFrameSectionRow(owlEditorKit, this, owlOntology, cls, ax));
            }
            processedClasses.add(cls);
        }
    }

    protected void refillInferred() {
        getOWLModelManager().getReasonerPreferences().executeTask(ReasonerPreferences.OptionalInferenceTask.SHOW_INFERRED_SUPER_CLASSES,
                () -> {
                    refillInferredDoIt();
                });
    }

    private void refillInferredDoIt() {
        OWLReasoner reasoner = getOWLModelManager().getReasoner();
        if (!reasoner.isConsistent()) {
            return;
        }
        if(!reasoner.isSatisfiable(getRootObject())) {
            return;
        }
        Set<OWLClass> clses = getReasoner().getSuperClasses(getRootObject(), true).getFlattened();
        clses.remove(getRootObject());
        for (OWLClass cls : clses) {
            if (!processedClasses.contains(cls)) {
                for (OWLOntology ontology : getOWLModelManager().getActiveOntology().getImportsClosure()) {
                    for (OWLSubClassOfAxiom ax : ontology.getSubClassAxiomsForSubClass(cls)) {
                        OWLClassExpression superClass = ax.getSuperClass();
                        if (superClass.isAnonymous()) {
                            OWLSubClassOfAxiom entailedAxiom = getOWLDataFactory().getOWLSubClassOfAxiom(getRootObject(), superClass);
                            addRow(new OWLMultilingualInheritedAnonymousClassesFrameSectionRow(getOWLEditorKit(),
                                    this,
                                    null,
                                    cls,
                                    entailedAxiom));
                        }
                    }
                    for (OWLEquivalentClassesAxiom ax : ontology.getEquivalentClassesAxioms(cls)) {
                        Set<OWLClassExpression> descs = new HashSet<>(ax.getClassExpressions());
                        descs.remove(getRootObject());
                        for (OWLClassExpression superCls : descs) {
                            if (superCls.isAnonymous()) {
                                OWLSubClassOfAxiom entailedAxiom = getOWLDataFactory().getOWLSubClassOfAxiom(getRootObject(), superCls);
                                addRow(new OWLMultilingualInheritedAnonymousClassesFrameSectionRow(getOWLEditorKit(),
                                        this,
                                        null,
                                        cls, entailedAxiom));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void clear() {
        processedClasses.clear();
    }

    @Override
    protected boolean isResettingChange(OWLOntologyChange change) {
        return change.isAxiomChange() && (change.getAxiom() instanceof OWLSubClassOfAxiom || change.getAxiom() instanceof OWLEquivalentClassesAxiom);
    }

    @Override
    public Comparator<OWLFrameSectionRow<OWLClass, OWLClassAxiom, OWLClassExpression>> getRowComparator() {
        return null;
    }
}
