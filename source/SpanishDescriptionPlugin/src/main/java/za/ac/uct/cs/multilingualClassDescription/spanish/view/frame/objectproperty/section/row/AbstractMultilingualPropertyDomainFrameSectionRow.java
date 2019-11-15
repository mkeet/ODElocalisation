package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.objectproperty.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.protege.editor.owl.ui.util.OWLComponentFactory;
import org.semanticweb.owlapi.model.*;
import za.ac.uct.cs.multilingualClassDescription.spanish.AbstractOWLMultilingualFrameSectionRow;

import java.util.Arrays;
import java.util.List;

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
 */
public abstract class AbstractMultilingualPropertyDomainFrameSectionRow<P extends OWLProperty, A extends OWLPropertyDomainAxiom> extends AbstractOWLMultilingualFrameSectionRow<P, A, OWLClassExpression> {

    public AbstractMultilingualPropertyDomainFrameSectionRow(OWLEditorKit owlEditorKit, OWLFrameSection<P, A, OWLClassExpression> section,
                                                 OWLOntology ontology, P rootObject,
                                                 A axiom) {
        super(owlEditorKit, section, ontology, rootObject, axiom);
    }


    protected OWLObjectEditor<OWLClassExpression> getObjectEditor() {
        final OWLComponentFactory cf = getOWLEditorKit().getWorkspace().getOWLComponentFactory();
        final A ax = getAxiom();
        return cf.getOWLClassDescriptionEditor(ax.getDomain(), ax.getAxiomType());
    }


    public List<? extends OWLObject> getManipulatableObjects() {
        return Arrays.asList(getAxiom().getDomain());
    }
}