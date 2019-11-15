package za.ac.uct.cs.multilingualClassDescription.spanish;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.frame.AbstractOWLFrameSectionRow;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import za.ac.uct.cs.multilingualClassDescription.spanish.syntax.MultilingualOWLObjectRendererImpl;

import java.util.Iterator;

/**
 * Created by Casey on 19-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public abstract class AbstractOWLMultilingualFrameSectionRow<R extends Object, A extends OWLAxiom, E> extends AbstractOWLFrameSectionRow<R, A, E> {

    protected AbstractOWLMultilingualFrameSectionRow(OWLEditorKit owlEditorKit, OWLFrameSection<R,A,E> section, OWLOntology ontology,
                                                     R rootObject, A axiom) {
        super(owlEditorKit, section, ontology, rootObject, axiom);
    }

    public boolean isEditable() {
        return false;
    }

    public boolean isDeleteable() {
        return true;
    }

    @Override
    protected OWLObjectEditor getObjectEditor() {
        return null;
    }

    protected Object getObjectRendering(OWLObject ob) {
        MultilingualOWLObjectRendererImpl renderer = new MultilingualOWLObjectRendererImpl(getOWLModelManager());
        return renderer.render(ob);
    }


    /**
     * Overridden to return the Afrikaans Manchester Rendering
     */
    public String getRendering() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPrefix());
        for (Iterator<? extends OWLObject> it = getManipulatableObjects().iterator(); it.hasNext(); ) {
            OWLObject obj = it.next();
            sb.append(getObjectRendering(obj));
            if (it.hasNext()) {
                sb.append(getDelimeter());
            }
        }
        sb.append(getSuffix());
        return sb.toString();
    }
}
