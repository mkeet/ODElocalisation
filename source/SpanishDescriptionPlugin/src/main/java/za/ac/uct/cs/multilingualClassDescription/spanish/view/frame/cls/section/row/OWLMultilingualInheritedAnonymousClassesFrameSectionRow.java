package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import za.ac.uct.cs.multilingualClassDescription.spanish.AbstractOWLMultilingualFrameSectionRow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Casey on 23-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualInheritedAnonymousClassesFrameSectionRow extends AbstractOWLMultilingualFrameSectionRow<OWLClass,
        OWLClassAxiom, OWLClassExpression> {

    public OWLMultilingualInheritedAnonymousClassesFrameSectionRow(OWLEditorKit owlEditorKit, OWLFrameSection<OWLClass,
            OWLClassAxiom, OWLClassExpression> section, OWLOntology ontology, OWLClass rootObject, OWLClassAxiom axiom) {
        super(owlEditorKit, section, ontology, rootObject, axiom);
    }

    @Override
    protected OWLClassAxiom createAxiom(OWLClassExpression editedObject) {
        if (getAxiom() instanceof OWLSubClassOfAxiom) {
            return getOWLDataFactory().getOWLSubClassOfAxiom(getRoot(), editedObject);
        }
        else {
            return getOWLDataFactory().getOWLEquivalentClassesAxiom(CollectionFactory.createSet(getRoot(),
                    editedObject));
        }
    }
    protected OWLObjectEditor<OWLClassExpression> getObjectEditor() {
        if (getAxiom() instanceof OWLSubClassOfAxiom) {
            OWLClassExpression superCls = ((OWLSubClassOfAxiom) getAxiom()).getSuperClass();
            return getOWLEditorKit().getWorkspace().getOWLComponentFactory().getOWLClassDescriptionEditor(superCls, AxiomType.SUBCLASS_OF);
        }
        else {
            Set<OWLClassExpression> descs = new HashSet<>(((OWLEquivalentClassesAxiom) getAxiom()).getClassExpressions());
            descs.remove(getRootObject());
            OWLClassExpression desc;
            if (descs.isEmpty()){
                // in the weird case that something is asserted equiv to itself
                desc = getRootObject();
            }
            else{
                desc = descs.iterator().next();
            }
            return getOWLEditorKit().getWorkspace().getOWLComponentFactory().getOWLClassDescriptionEditor(desc, AxiomType.EQUIVALENT_CLASSES);
        }
    }

    @Override
    public List<? extends OWLObject> getManipulatableObjects() {
        if (getAxiom() instanceof OWLSubClassOfAxiom) {
            return Arrays.asList(((OWLSubClassOfAxiom) getAxiom()).getSuperClass());
        } else {
            Set<OWLClassExpression> descs = new HashSet<>(((OWLEquivalentClassesAxiom) getAxiom()).getClassExpressions());
            descs.remove(getRootObject());
            if (descs.isEmpty()) {
                // in the weird case that something is asserted equiv to itself
                OWLClassExpression cls = getRootObject();
                return Arrays.asList(cls);
            }
            return Arrays.asList(descs.iterator().next());
        }
    }

    public String getTooltip() {
        return "Inherited from " + getOWLModelManager().getRendering(getRootObject());
    }
}
