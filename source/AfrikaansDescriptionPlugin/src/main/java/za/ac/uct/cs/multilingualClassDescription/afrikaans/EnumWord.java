package za.ac.uct.cs.multilingualClassDescription.afrikaans;

/**
 * Author: Toky Raboanary<br>
 * UCT - University of Cape Town<br>
 * Computer Science Department<br>
 * Date: 13-Nov-2019<br><br>
 */

public enum EnumWord {

    /*****************************************************************
     *                                                               *
     *                      ManchesterOWLSyntax                      *
     *                                                               *
     *****************************************************************/

    AND,
    OR,
    SOME,
    ONLY,
    NOT,
    VALUE,
    MIN,
    MAX,
    EXACTLY,
    DISJOINT_WITH,
    SUBCLASS_OF,
    EQUIVALENT_TO,
    EQUIVALENT_CLASSES,
    DISJOINT_CLASSES,
    INVERSE, // Object Property Description

    /*****************************************************************
     *                                                               *
     *                             Class                             *
     *                                                               *
     *****************************************************************/


    /********************** OWLMultilingualClassAssertionAxiomMembersSection **********************/
    INSTANCES_LABEL, // Instances
    INSTANCES_ROW_LABEL, // Type assertion

    /********************** OWLMultilingualDisjointClassesAxiomFrameSection **********************/
    DISJOINT_WITH_LABEL, // Disjoint With
    DISJOINT_WITH_ROW_LABEL, // Disjoint With

    /********************** OWLMultilingualDisjointUnionAxiomFrameSection **********************/
    DISJOINT_UNION_OF_LABEL, // Disjoint Union Of
    DISJOINT_UNION_OF_ROW_LABEL, // Disjoint Union Of

    /********************** OWLMultilingualEquivalentClassesAxiomFrameSection **********************/
    EQUIVALENT_TO_LABEL, // Equivalent To
    EQUIVALENT_TO_ROW_LABEL, // Equivalent class

    /********************** OWLMultilingualGeneralClassAxiomFrameSection **********************/
    GENERAL_CLASS_AXIOMS_LABEL, // General class axioms
    GENERAL_CLASS_AXIOMS_ROW_LABEL, // General class axioms

    /********************** OWLMultilingualInheritedAnonymousClassesFrameSection **********************/
    SUBCLASS_OF_ANONYMOUS_ANCESTOR_LABEL, // SubClass Of (Anonymous Ancestor)
    SUBCLASS_OF_ANONYMOUS_ANCESTOR_ROW_LABEL, // Anonymous Ancestor Class


    /********************** OWLMultilingualSubClassesAxiomFrameSection **********************/
    SUBCLASS_OF_LABEL, // SubClass Of
    SUBCLASS_OF_ROW_LABEL, // Superclass


    /*****************************************************************
     *                                                               *
     *                        Object Property                        *
     *                                                               *
     *****************************************************************/

    /********************** OWLMultilingualDisjointObjectPropertiesFrameSection **********************/
    DISJOINT_WITH_OBJ_PROP_LABEL, // en: Disjoint with
    DISJOINT_WITH_OBJ_PROP_ROW_LABEL, // en: Disjoint properties

    /********************** OWLMultilingualEquivalentObjectPropertiesAxiomFrameSection **********************/
    EQUIVALENT_TO_OBJ_PROP_LABEL, // en: Equivalent To
    EQUIVALENT_TO_ROW_OBJ_PROP_LABEL, //en: Equivalent object property

    /********************** OWLMultilingualInverseObjectPropertiesAxiomFrameSection **********************/
    INVERSE_OF_OBJ_PROP_LABEL, // en: Inverse Of
    INVERSE_OF_ROW_OBJ_PROP_LABEL, //en: Inverse property

    /********************** OWLMultilingualObjectPropertyDomainFrameSection **********************/
    DOMAINS_OBJ_PROP_LABEL, // en: Domains (intersection)
    DOMAIN_ROW_OBJ_PROP_LABEL, // en: Domain

    /********************** OWLMultilingualObjectPropertyRangeFrameSection **********************/
    RANGES_OBJ_PROP_LABEL, // en: Ranges (intersection)
    RANGE_ROW_OBJ_PROP_LABEL, // en: Range

    /********************** OWLMultilingualPropertyChainAxiomFrameSection **********************/
    PROP_CHAIN_OBJ_PROP_LABEL, // en: SuperProperty Of (Chain)
    PROP_CHAIN_ROW_OBJ_PROP_LABEL, // en: Property chain

    /********************** OWLMultilingualSubObjectPropertyAxiomSuperPropertyFrameSection **********************/
    SUBPROP_OF_OBJ_PROP_LABEL, // en: SubProperty Of
    SUBPROP_OF_ROW_OBJ_PROP_LABEL, // en: Super property

}
