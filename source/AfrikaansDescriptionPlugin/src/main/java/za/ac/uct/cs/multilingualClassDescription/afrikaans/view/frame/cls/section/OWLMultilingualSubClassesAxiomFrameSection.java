package za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.cls.section;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.inference.ReasonerPreferences;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.protege.editor.owl.ui.frame.OWLFrameSectionRow;
import org.protege.editor.owl.ui.frame.cls.AbstractOWLClassAxiomFrameSection;
import org.protege.editor.owl.ui.frame.cls.OWLSubClassAxiomFrameSectionRow;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.EnumWord;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.Utils;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.cls.section.row.OWLMultilingualSubClassesAxiomFrameSectionRow;

import java.util.*;

/**
 * Created by Adam on 2017/03/20.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualSubClassesAxiomFrameSection extends AbstractOWLClassAxiomFrameSection<OWLSubClassOfAxiom, OWLClassExpression> {

    private static final String LABEL = Utils.getDico().getWord(EnumWord.SUBCLASS_OF_LABEL); // SUBCLASS_OF

    private Set<OWLClassExpression> added = new HashSet<>();


    public OWLMultilingualSubClassesAxiomFrameSection(OWLEditorKit editorKit, OWLFrame<OWLClass> frame) {
        super(editorKit, LABEL, Utils.getDico().getWord(EnumWord.SUBCLASS_OF_ROW_LABEL), frame); // SUBCLASS
    }

    public boolean canAdd() {
        return true;
    }

    @Override
    protected void addAxiom(OWLSubClassOfAxiom ax, OWLOntology ontology) {
        OWLEditorKit owlEditorKit = getOWLEditorKit();
        addRow(new OWLMultilingualSubClassesAxiomFrameSectionRow(owlEditorKit,
                this,
                ontology,
                getRootObject(),
                ax));
        for (OWLClassExpression desc : ax.getNestedClassExpressions()) {
            added.add(desc);
        }
    }

    @Override
    protected Set<OWLSubClassOfAxiom> getClassAxioms(OWLClassExpression owlClassExpression, OWLOntology owlOntology) {
        if (!owlClassExpression.isAnonymous()) {
            return owlOntology.getSubClassAxiomsForSubClass(owlClassExpression.asOWLClass());
        } else {
            Set<OWLSubClassOfAxiom> axioms = new HashSet<>();
            for (OWLAxiom ax : owlOntology.getGeneralClassAxioms()) {
                if (ax instanceof OWLSubClassOfAxiom && ((OWLSubClassOfAxiom)ax).getSubClass().equals(owlClassExpression)){
                    axioms.add((OWLSubClassOfAxiom)ax);
                }
            }
            return axioms;
        }
    }

    protected void refillInferred() {
        final OWLReasoner reasoner = getOWLModelManager().getReasoner();
        if(!reasoner.isConsistent()) {
            return;
        }
        if(!reasoner.isSatisfiable(getRootObject())) {
            return;
        }
        getOWLModelManager().getReasonerPreferences().executeTask(ReasonerPreferences.OptionalInferenceTask.SHOW_INFERRED_SUPER_CLASSES, () -> {
            for (Node<OWLClass> inferredSuperClasses : reasoner.getSuperClasses(getRootObject(), true)) {
                for (OWLClassExpression inferredSuperClass : inferredSuperClasses) {
                    if (!added.contains(inferredSuperClass)) {
                        addInferredRowIfNontrivial(new OWLSubClassAxiomFrameSectionRow(getOWLEditorKit(),
                                this,
                                null,
                                getRootObject(),
                                getOWLModelManager().getOWLDataFactory().getOWLSubClassOfAxiom(getRootObject(),
                                        inferredSuperClass)));
                        added.add(inferredSuperClass);
                    }
                }
            }
        });
    }

    @Override
    protected OWLSubClassOfAxiom createAxiom(OWLClassExpression owlClassExpression) {
        return getOWLDataFactory().getOWLSubClassOfAxiom(getRootObject(), owlClassExpression);
    }

    @Override
    protected boolean isResettingChange(OWLOntologyChange change) {
        if (!change.isAxiomChange()) {
            return false;
        }
        OWLAxiom axiom = change.getAxiom();
        if (axiom instanceof OWLSubClassOfAxiom) {
            return ((OWLSubClassOfAxiom) axiom).getSubClass().equals(getRootObject());
        }
        return false;
    }

    @Override
    public OWLObjectEditor<OWLClassExpression> getObjectEditor() {
        return getOWLEditorKit().getWorkspace().getOWLComponentFactory().getOWLClassDescriptionEditor(null, AxiomType.SUBCLASS_OF);
    }

    private OWLObjectProperty prop;

    public boolean dropObjects(List<OWLObject> objects) {
        List<OWLOntologyChange> changes = new ArrayList<>();
        for (OWLObject obj : objects) {
            if (obj instanceof OWLClassExpression) {
                OWLClassExpression desc;
                if (prop != null) {
                    desc = getOWLDataFactory().getOWLObjectSomeValuesFrom(prop, (OWLClassExpression) obj);
                }
                else {
                    desc = (OWLClassExpression) obj;
                }
                OWLAxiom ax = getOWLDataFactory().getOWLSubClassOfAxiom(getRootObject(), desc);
                changes.add(new AddAxiom(getOWLModelManager().getActiveOntology(), ax));
            }
            else if (obj instanceof OWLObjectProperty) {
                // Prime
                prop = (OWLObjectProperty) obj;
            }
            else {
                return false;
            }
        }
        getOWLModelManager().applyChanges(changes);
        return true;
    }


    @Override
    protected void clear() {
        added.clear();
    }

    /**
     * Obtains a comparator which can be used to sort the rows
     * in this section.
     * @return A comparator if to sort the rows in this section,
     *         or <code>null</code> if the rows shouldn't be sorted.
     */
    public Comparator<OWLFrameSectionRow<OWLClassExpression, OWLSubClassOfAxiom, OWLClassExpression>> getRowComparator() {
        return new Comparator<OWLFrameSectionRow<OWLClassExpression, OWLSubClassOfAxiom, OWLClassExpression>>() {


            public int compare(OWLFrameSectionRow<OWLClassExpression, OWLSubClassOfAxiom, OWLClassExpression> o1,
                               OWLFrameSectionRow<OWLClassExpression, OWLSubClassOfAxiom, OWLClassExpression> o2) {
                if (o1.isInferred()) {
                    if (!o2.isInferred()) {
                        return 1;
                    }
                }
                else {
                    if (o2.isInferred()) {
                        return -1;
                    }
                }
                int val = getOWLModelManager().getOWLObjectComparator().compare(o1.getAxiom(), o2.getAxiom());

                if(val == 0) {
                    return o1.getOntology().getOntologyID().compareTo(o2.getOntology().getOntologyID());
                }
                else {
                    return val;
                }

            }
        };
    }
}
