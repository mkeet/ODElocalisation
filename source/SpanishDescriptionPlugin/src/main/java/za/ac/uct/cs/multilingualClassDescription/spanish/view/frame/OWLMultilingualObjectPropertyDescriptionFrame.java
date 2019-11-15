package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.frame.AbstractOWLFrame;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.objectproperty.section.*;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Jan-2007<br><br>
 *
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualObjectPropertyDescriptionFrame extends AbstractOWLFrame<OWLObjectProperty> {

    public OWLMultilingualObjectPropertyDescriptionFrame(OWLEditorKit editorKit) {
        super(editorKit.getModelManager().getOWLOntologyManager());
        addSection(new OWLMultilingualEquivalentObjectPropertiesAxiomFrameSection(editorKit, this));
        addSection(new OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSection(editorKit, this));
        addSection(new OWLMultilingualInverseObjectPropertiesAxiomFrameSection(editorKit, this));


        addSection(new OWLMultilingualObjectPropertyDomainFrameSection(editorKit, this));
        addSection(new OWLMultilingualObjectPropertyRangeFrameSection(editorKit, this));

        addSection(new OWLMultilingualDisjointObjectPropertiesFrameSection(editorKit, this));
        addSection(new OWLMultilingualPropertyChainAxiomFrameSection(editorKit, this));
    }
}
