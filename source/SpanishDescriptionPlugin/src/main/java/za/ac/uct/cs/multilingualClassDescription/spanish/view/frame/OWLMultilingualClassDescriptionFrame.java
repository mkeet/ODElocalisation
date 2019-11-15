package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.frame.AbstractOWLFrame;
import org.semanticweb.owlapi.model.OWLClass;
import za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.cls.section.*;

/**
 * Created by Casey on 17-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualClassDescriptionFrame extends AbstractOWLFrame<OWLClass>{

    public OWLMultilingualClassDescriptionFrame(OWLEditorKit editorKit) {
        super(editorKit.getModelManager().getOWLOntologyManager());

        addSection(new OWLMultilingualEquivalentClassesAxiomFrameSection(editorKit, this));
        addSection(new OWLMultilingualSubClassesAxiomFrameSection(editorKit, this));
        addSection(new OWLMultilingualGeneralClassAxiomFrameSection(editorKit, this));
        addSection(new OWLMultilingualInheritedAnonymousClassesFrameSection(editorKit, this));
        addSection(new OWLMultilingualClassAssertionAxiomMembersSection(editorKit, this));
        addSection(new OWLMultilingualDisjointClassesAxiomFrameSection(editorKit, this));
        addSection(new OWLMultilingualDisjointUnionAxiomFrameSection(editorKit, this));
    }
}
