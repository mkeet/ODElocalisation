package za.ac.uct.cs.multilingualClassDescription.afrikaans;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.framelist.OWLFrameList;
import org.protege.editor.owl.ui.view.cls.AbstractOWLClassViewComponent;
import org.semanticweb.owlapi.model.OWLClass;
import za.ac.uct.cs.multilingualClassDescription.afrikaans.view.frame.OWLMultilingualClassDescriptionFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Casey on 15-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 *
 * View Class for replicating class description view but providing Afrikaans
 * language descriptions
 */

public class OWLMultilingualClassDescriptionViewComponent extends AbstractOWLClassViewComponent{

    private OWLFrameList<OWLClass> list;

    @Override
    public void initialiseClassView() {
        OWLEditorKit ek = getOWLEditorKit();
        Utils.UpdateOWLEditorKit(ek);
        list = new OWLFrameList<>(ek, new OWLMultilingualClassDescriptionFrame(ek));
        setLayout(new BorderLayout());
        JScrollPane sp = new JScrollPane(list);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(sp);
    }

    @Override
    public OWLClass updateView(OWLClass selectedClass) {
        list.setRootObject(selectedClass);
        return selectedClass;
    }

    @Override
    public void disposeView() {list.dispose();}
}
