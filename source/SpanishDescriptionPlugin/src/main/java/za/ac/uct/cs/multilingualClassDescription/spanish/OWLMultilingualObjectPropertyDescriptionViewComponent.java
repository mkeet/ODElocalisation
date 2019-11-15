package za.ac.uct.cs.multilingualClassDescription.spanish;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.framelist.OWLFrameList;
import org.protege.editor.owl.ui.view.objectproperty.AbstractOWLObjectPropertyViewComponent;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.OWLMultilingualObjectPropertyDescriptionFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Jan-2007<br><br>
 *
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */

public class OWLMultilingualObjectPropertyDescriptionViewComponent extends AbstractOWLObjectPropertyViewComponent {

    /**
     *
     */

    private OWLFrameList<OWLObjectProperty> list;

    public void initialiseView() throws Exception {
        OWLEditorKit ek = getOWLEditorKit();
        Utils.UpdateOWLEditorKit(ek);
        list = new OWLFrameList<>(ek,
                new OWLMultilingualObjectPropertyDescriptionFrame(getOWLEditorKit()));
        setLayout(new BorderLayout());
        add(new JScrollPane(list));
    }

    public void disposeView() {
        list.dispose();
    }

    protected OWLObjectProperty updateView(OWLObjectProperty property) {
        list.setRootObject(property);
        return property;
    }
}