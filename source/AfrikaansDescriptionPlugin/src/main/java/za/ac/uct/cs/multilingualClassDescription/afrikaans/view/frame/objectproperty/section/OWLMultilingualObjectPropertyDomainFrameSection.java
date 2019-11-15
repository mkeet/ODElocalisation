package za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.objectproperty.section;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.inference.ReasonerPreferences.OptionalInferenceTask;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNodeSet;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.objectproperty.section.row.AbstractMultilingualPropertyDomainFrameSectionRow;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.objectproperty.section.row.OWLMultilingualObjectPropertyDomainFrameSectionRow;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Jan-2007<br><br>
 *
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 *
 */
public class OWLMultilingualObjectPropertyDomainFrameSection extends AbstractMultilingualPropertyDomainFrameSection<OWLObjectProperty, OWLObjectPropertyDomainAxiom> {

    public OWLMultilingualObjectPropertyDomainFrameSection(OWLEditorKit editorKit, OWLFrame<OWLObjectProperty> owlObjectPropertyOWLFrame) {
        super(editorKit, owlObjectPropertyOWLFrame);
    }


    protected OWLObjectPropertyDomainAxiom createAxiom(OWLClassExpression object) {
        return getOWLDataFactory().getOWLObjectPropertyDomainAxiom(getRootObject(), object);
    }


    protected AbstractMultilingualPropertyDomainFrameSectionRow<OWLObjectProperty, OWLObjectPropertyDomainAxiom> createFrameSectionRow(OWLObjectPropertyDomainAxiom domainAxiom, OWLOntology ontology) {
        return new OWLMultilingualObjectPropertyDomainFrameSectionRow(getOWLEditorKit(), this, ontology, getRootObject(), domainAxiom);
    }


    protected Set<OWLObjectPropertyDomainAxiom> getAxioms(OWLOntology ontology) {
        return ontology.getObjectPropertyDomainAxioms(getRootObject());
    }


    protected NodeSet<OWLClass> getInferredDomains() {
        OWLReasoner reasoner = getOWLModelManager().getReasoner();
        OWLObjectProperty p = getRootObject();
        OWLDataFactory factory = getOWLModelManager().getOWLOntologyManager().getOWLDataFactory();
        if (p.equals(factory.getOWLTopObjectProperty())) {
            return new OWLClassNodeSet(reasoner.getTopClassNode());
        }
        OWLClassExpression domain = factory.getOWLObjectSomeValuesFrom(p, factory.getOWLThing());
        Node<OWLClass> domainNode = reasoner.getEquivalentClasses(domain);
        if (domainNode != null && !domainNode.getEntities().isEmpty()) {
            return new OWLClassNodeSet(domainNode);
        }
        else {
            return reasoner.getObjectPropertyDomains(getRootObject(), true);
        }
    }
    
    @Override
    protected void refillInferred() {
        getOWLModelManager().getReasonerPreferences().executeTask(OptionalInferenceTask.SHOW_INFERRED_OBJECT_PROPERTY_DOMAINS,
                () -> {
                    if (!getOWLModelManager().getReasoner().isConsistent()) {
                        return;
                    }
                    OWLMultilingualObjectPropertyDomainFrameSection.super.refillInferred();
                });
    }
    
    @Override
    protected boolean isResettingChange(OWLOntologyChange change) {
    	if (!change.isAxiomChange()) {
    		return false;
    	}
    	OWLAxiom axiom = change.getAxiom();
    	if (axiom instanceof OWLObjectPropertyDomainAxiom) {
    		return ((OWLObjectPropertyDomainAxiom) axiom).getProperty().equals(getRootObject());
    	}
    	return false;
    }
}
