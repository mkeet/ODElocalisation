package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.inference.ReasonerPreferences;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.protege.editor.owl.ui.frame.OWLFrameSectionRow;
import org.protege.editor.owl.ui.frame.cls.AbstractOWLClassAxiomFrameSection;
import org.protege.editor.owl.ui.frame.cls.OWLEquivalentClassesAxiomFrameSectionRow;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.CollectionFactory;
import za.ac.uct.cs.multilingualClassDescription.spanish.EnumWord;
import za.ac.uct.cs.multilingualClassDescription.spanish.Utils;
import za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section.row.OWLMultilingualEquivalentClassesAxiomFrameSectionRow;

import java.util.*;

/**
 * Created by Casey on 19-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualEquivalentClassesAxiomFrameSection extends AbstractOWLClassAxiomFrameSection<OWLEquivalentClassesAxiom, OWLClassExpression> {

    private static final String LABEL = Utils.getDico().getWord(EnumWord.EQUIVALENT_TO_LABEL);

    private Set<OWLClassExpression> added = new HashSet<>();

    private boolean inferredEquivalentClasses = true;

    public OWLMultilingualEquivalentClassesAxiomFrameSection(OWLEditorKit editorKit, OWLFrame<OWLClass> frame) {
        super(editorKit, LABEL, Utils.getDico().getWord(EnumWord.EQUIVALENT_TO_ROW_LABEL), frame);
    }

    public boolean canAdd() {
        return true;
    }

    @Override
    protected void addAxiom(OWLEquivalentClassesAxiom ax, OWLOntology ontology) {
        OWLEditorKit owlEditorKit = getOWLEditorKit();

        addRow(new OWLMultilingualEquivalentClassesAxiomFrameSectionRow(owlEditorKit,
                this,
                ontology,
                getRootObject(),
                ax));
        for (OWLClassExpression desc : ax.getClassExpressions()) {
            added.add(desc);
        }
    }

    @Override
    protected Set<OWLEquivalentClassesAxiom> getClassAxioms(OWLClassExpression owlClassExpression, OWLOntology owlOntology) {
        if (!owlClassExpression.isAnonymous()) {
            return owlOntology.getEquivalentClassesAxioms(owlClassExpression.asOWLClass());
        } else {
            Set<OWLEquivalentClassesAxiom> axioms = new HashSet<>();
            for (OWLAxiom ax : owlOntology.getGeneralClassAxioms()) {
                if (ax instanceof OWLEquivalentClassesAxiom &&
                        ((OWLEquivalentClassesAxiom) ax).getClassExpressions().contains(owlClassExpression)) {
                    axioms.add((OWLEquivalentClassesAxiom) ax);
                }
            }
            return axioms;
        }
    }

    @Override
    protected OWLEquivalentClassesAxiom createAxiom(OWLClassExpression owlClassExpression) {
        return getOWLDataFactory().getOWLEquivalentClassesAxiom(CollectionFactory.createSet(getRootObject(), owlClassExpression));
    }

    @Override
    public OWLObjectEditor<OWLClassExpression> getObjectEditor() {
        return getOWLEditorKit().getWorkspace().getOWLComponentFactory().getOWLClassDescriptionEditor(null, AxiomType.EQUIVALENT_CLASSES);
    }
    protected void refillInferred() {
        if (!inferredEquivalentClasses) {
            return;
        }
        getOWLModelManager().getReasonerPreferences().executeTask(ReasonerPreferences.OptionalInferenceTask.SHOW_INFERRED_EQUIVALENT_CLASSES,
                () -> {
                    final OWLReasoner reasoner = getOWLModelManager().getReasoner();
                    if (!reasoner.isConsistent()) {
                        return;
                    }
                    if (!reasoner.isSatisfiable(getRootObject())) {
                        if (!getRootObject().isOWLNothing()) {
                            OWLClass nothing = getOWLModelManager().getOWLDataFactory().getOWLNothing();
                            addRow(new OWLEquivalentClassesAxiomFrameSectionRow(getOWLEditorKit(),
                                    this,
                                    null,
                                    getRootObject(),
                                    getOWLDataFactory().getOWLEquivalentClassesAxiom(CollectionFactory.createSet(getRootObject(),
                                            nothing))));
                        }
                    }
                    else{
                        for (OWLClassExpression cls : reasoner.getEquivalentClasses(getRootObject())) {
                            if (!added.contains(cls) && !cls.equals(getRootObject())) {
                                addRow(new OWLEquivalentClassesAxiomFrameSectionRow(getOWLEditorKit(),
                                        this,
                                        null,
                                        getRootObject(),
                                        getOWLDataFactory().getOWLEquivalentClassesAxiom(CollectionFactory.createSet(getRootObject(),
                                                cls))));
                            }
                        }
                    }
                });

    }

    @Override
    protected void clear() {
        added.clear();
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
        for (OWLObject obj : objects) {
            if (obj instanceof OWLClassExpression) {
                OWLClassExpression desc = (OWLClassExpression) obj;
                OWLAxiom ax = getOWLDataFactory().getOWLEquivalentClassesAxiom(CollectionFactory.createSet(getRootObject(),
                        desc));
                changes.add(new AddAxiom(getOWLModelManager().getActiveOntology(), ax));
            }
            else {
                return false;
            }
        }
        getOWLModelManager().applyChanges(changes);
        return true;
    }



    @Override
    protected boolean isResettingChange(OWLOntologyChange change) {
        return change.isAxiomChange() &&
                change.getAxiom() instanceof OWLEquivalentClassesAxiom &&
                ((OWLEquivalentClassesAxiom) change.getAxiom()).getClassExpressions().contains(getRootObject());
    }

    @Override
    public Comparator<OWLFrameSectionRow<OWLClassExpression, OWLEquivalentClassesAxiom, OWLClassExpression>> getRowComparator() {
        return null;
    }
}
