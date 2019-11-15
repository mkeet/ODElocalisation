package za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.objectproperty.section;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.inference.ReasonerPreferences.OptionalInferenceTask;
import org.protege.editor.owl.ui.editor.OWLObjectEditor;
import org.protege.editor.owl.ui.editor.OWLObjectPropertyExpressionEditor;
import org.protege.editor.owl.ui.frame.AbstractOWLFrameSection;
import org.protege.editor.owl.ui.frame.OWLFrame;
import org.protege.editor.owl.ui.frame.OWLFrameSectionRow;
import org.semanticweb.owlapi.model.*;
import za.ac.uct.cs.multilingualClassDescription.spanish.EnumWord;
import za.ac.uct.cs.multilingualClassDescription.spanish.Utils;
import za.ac.uct.cs.multilingualClassDescription.spanish.view.frame.objectproperty.section.row.OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSectionRow;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Jan-2007<br><br>
 *
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSection extends AbstractOWLFrameSection<OWLObjectProperty, OWLSubObjectPropertyOfAxiom, OWLObjectPropertyExpression> {

    public static final String LABEL = Utils.getDico().getWord(EnumWord.SUBPROP_OF_OBJ_PROP_LABEL); // SubProperty Of

    Set<OWLObjectPropertyExpression> added = new HashSet<>();


    public OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSection(OWLEditorKit editorKit,
                                                                          OWLFrame<? extends OWLObjectProperty> frame) {
        super(editorKit, LABEL, Utils.getDico().getWord(EnumWord.SUBPROP_OF_ROW_OBJ_PROP_LABEL), frame); // Super property
    }


    protected void clear() {
        added.clear();
    }


    /**
     * Refills the section with rows.  This method will be called
     * by the system and should be directly called.
     */
    protected void refill(OWLOntology ontology) {

        for (OWLSubObjectPropertyOfAxiom ax : ontology.getObjectSubPropertyAxiomsForSubProperty(getRootObject())) {
            addRow(new OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSectionRow(getOWLEditorKit(),
                                                                             this,
                                                                             ontology,
                                                                             getRootObject(),
                                                                             ax));
            added.add(ax.getSuperProperty());
        }
    }


    protected void refillInferred() {
        getOWLModelManager().getReasonerPreferences().executeTask(OptionalInferenceTask.SHOW_INFERRED_SUPER_OBJECT_PROPERTIES,
                () -> {
                    if (!getOWLModelManager().getReasoner().isConsistent()) {
                        return;
                    }
                    for (OWLObjectPropertyExpression infSup : getOWLModelManager().getReasoner().getSuperObjectProperties(getRootObject(),true).getFlattened()) {
                        if (!added.contains(infSup)) {
                            addInferredRowIfNontrivial(new OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSectionRow(getOWLEditorKit(),
                                                                                             OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSection.this,
                                                                                             null,
                                                                                             getRootObject(),
                                                                                             getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(getRootObject(),
                                                                                                                                                infSup)));
                        }
                    }
                });
    }


    protected OWLSubObjectPropertyOfAxiom createAxiom(OWLObjectPropertyExpression object) {
        return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(getRootObject(), object);
    }


    public OWLObjectEditor<OWLObjectPropertyExpression> getObjectEditor() {
        return new OWLObjectPropertyExpressionEditor(getOWLEditorKit());
    }
    
    @Override
    protected boolean isResettingChange(OWLOntologyChange change) {
    	if (!change.isAxiomChange()) {
    		return false;
    	}
    	OWLAxiom axiom = change.getAxiom();
    	if (axiom instanceof OWLSubObjectPropertyOfAxiom) {
    		return ((OWLSubObjectPropertyOfAxiom) axiom).getSubProperty().equals(getRootObject());
    	}
    	return false;
    }


    /**
     * Obtains a comparator which can be used to sort the rows
     * in this section.
     * @return A comparator if to sort the rows in this section,
     *         or <code>null</code> if the rows shouldn't be sorted.
     */
    public Comparator<OWLFrameSectionRow<OWLObjectProperty, OWLSubObjectPropertyOfAxiom, OWLObjectPropertyExpression>> getRowComparator() {
        return null;
    }
}
