package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.objectproperty.section;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.AbstractOWLFrameSection;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.protege.editor.owl.ui.frame.OWLFrameSectionRow;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import za.ac.uct.cs.multilingualClassDescription.spanish.EnumWord;
import za.ac.uct.cs.multilingualClassDescription.spanish.Utils;
import za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.objectproperty.section.row.AbstractMultilingualPropertyDomainFrameSectionRow;

import java.util.*;

/*
 * Copyright (C) 2007, University of Manchester
 *
 *
 */

/**
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>

 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Oct 16, 2008<br><br>
 *
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 *
 */
public abstract class AbstractMultilingualPropertyDomainFrameSection<P extends OWLProperty, A extends OWLPropertyDomainAxiom>  extends AbstractOWLFrameSection<P, A, OWLClassExpression> {

    public static final String LABEL = Utils.getDico().getWord(EnumWord.DOMAINS_OBJ_PROP_LABEL); // Domains (intersection)

    Set<OWLClassExpression> addedDomains = new HashSet<>();


    public AbstractMultilingualPropertyDomainFrameSection(OWLEditorKit editorKit, OWLFrame<P> frame) {
        super(editorKit, LABEL, Utils.getDico().getWord(EnumWord.DOMAIN_ROW_OBJ_PROP_LABEL), frame); // Domain
    }


    public OWLObjectEditor<OWLClassExpression> getObjectEditor() {
        AxiomType type;
        if (getRootObject() instanceof OWLObjectProperty){
            type = AxiomType.OBJECT_PROPERTY_DOMAIN;
        }
        else{
            type = AxiomType.DATA_PROPERTY_DOMAIN;
        }
        return getOWLEditorKit().getWorkspace().getOWLComponentFactory().getOWLClassDescriptionEditor(null, type);
    }


    public final boolean canAcceptDrop(List<OWLObject> objects) {
        for (OWLObject obj : objects) {
            if (!(obj instanceof OWLClassExpression)) {
                return false;
            }
        }
        return true;
    }


    public final boolean dropObjects(List<OWLObject> objects) {
        List<OWLOntologyChange> changes = new ArrayList<>();
        for (OWLObject obj : objects) {
            if (obj instanceof OWLClassExpression) {
                OWLClassExpression desc = (OWLClassExpression) obj;
                OWLAxiom ax = createAxiom(desc);
                changes.add(new AddAxiom(getOWLModelManager().getActiveOntology(), ax));
            }
            else {
                return false;
            }
        }
        getOWLModelManager().applyChanges(changes);
        return true;
    }


    protected abstract AbstractMultilingualPropertyDomainFrameSectionRow<P, A> createFrameSectionRow(A domainAxiom, OWLOntology ontology);


    protected abstract Set<A> getAxioms(OWLOntology ontology);


    protected abstract NodeSet<OWLClass> getInferredDomains();


    protected void clear() {
        addedDomains.clear();
    }


    protected final void refill(OWLOntology ontology) {
        for (A ax : getAxioms(ontology)) {
            addRow(createFrameSectionRow(ax, ontology));
            addedDomains.add(ax.getDomain());
        }
    }


    protected void refillInferred() {
        for (Node<OWLClass> domains : getInferredDomains()) {
            for (OWLClassExpression domain : domains){
                if (!addedDomains.contains(domain)) {
                    addInferredRowIfNontrivial(createFrameSectionRow(createAxiom(domain), null));
                    addedDomains.add(domain);
                }
            }
        }
    }


    public Comparator<OWLFrameSectionRow<P, A, OWLClassExpression>> getRowComparator() {
        return null;
    }
}
