package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.objectproperty.section.row;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.editor.OWLObjectPropertyEditor;
import org.protege.editor.owl.ui.frame.OWLFrameSection;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import za.ac.uct.cs.multilingualClassDescription.spanish.AbstractOWLMultilingualFrameSectionRow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class OWLMultilingualInverseObjectPropertiesAxiomFrameSectionRow extends AbstractOWLMultilingualFrameSectionRow<OWLObjectProperty, OWLInverseObjectPropertiesAxiom, OWLObjectProperty> {

    public OWLMultilingualInverseObjectPropertiesAxiomFrameSectionRow(OWLEditorKit owlEditorKit,
                                                                      OWLFrameSection<OWLObjectProperty, OWLInverseObjectPropertiesAxiom, OWLObjectProperty> section,
                                                                      OWLOntology ontology, OWLObjectProperty rootObject,
                                                                      OWLInverseObjectPropertiesAxiom axiom) {
        super(owlEditorKit, section, ontology, rootObject, axiom);
    }


    protected OWLObjectEditor<OWLObjectProperty> getObjectEditor() {
        OWLObjectPropertyEditor editor = new OWLObjectPropertyEditor(getOWLEditorKit());
        OWLObjectPropertyExpression p = axiom.getFirstProperty();
        if (p.equals(getRootObject())){
            p = axiom.getSecondProperty();
        }
        
        if (!p.isAnonymous()){
            editor.setEditedObject(p.asOWLObjectProperty());
        }
        return editor;
    }


    protected OWLInverseObjectPropertiesAxiom createAxiom(OWLObjectProperty editedObject) {
        return getOWLDataFactory().getOWLInverseObjectPropertiesAxiom(getRoot(), editedObject);
    }


    /**
     * Gets a list of objects contained in this row.  These objects
     * could be placed on the clip board during a copy operation,
     * or navigated to etc.
     */
    public List<OWLObjectPropertyExpression> getManipulatableObjects() {
        Set<OWLObjectPropertyExpression> props = new HashSet<>(getAxiom().getProperties());
        if(props.size() > 1) {
            props.remove(getRootObject());
        }
        return new ArrayList<>(props);
    }
}
