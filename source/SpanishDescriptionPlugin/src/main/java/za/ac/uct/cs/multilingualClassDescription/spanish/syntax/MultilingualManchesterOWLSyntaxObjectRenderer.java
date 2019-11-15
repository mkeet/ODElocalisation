package za.ac.uct.cs.multilingualClassDescription.spanish.syntax;

import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax;
import org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterOWLSyntaxObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.ShortFormProvider;

import javax.annotation.Nonnull;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;


/**
 * Created by Casey on 20-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class MultilingualManchesterOWLSyntaxObjectRenderer extends ManchesterOWLSyntaxObjectRenderer{

    public MultilingualManchesterOWLSyntaxObjectRenderer(Writer writer, ShortFormProvider provider) {
        super(writer, provider);
    }

    /****** WRITE METHODS ******/

    // The following methods are overridden here because either
    // 1) the method in super only accepts ManchesterOWLSyntax delimeters and we need to support delimeters from
    //    syntax.MultilingualManchesterOWLSyntax
    // OR
    // 2) the method in super uses a delimeter in its body from ManchesterOWLSyntax that we wish to replace with one
    //    from syntax.MultilingualManchesterOWLSyntax

    protected void write(@Nonnull Set<? extends OWLObject> objects,
                         @Nonnull MultilingualManchesterOWLSyntax delimeter, boolean newline) {
        int tab = getIndent();
        pushTab(tab);
        for (Iterator<? extends OWLObject> it = sort(objects).iterator(); it
                .hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                if (newline && isUseWrapping()) {
                    writeNewLine();
                }
                write(delimeter);

            }
        }
        popTab();
    }

    protected void write(@Nonnull Set<? extends OWLClassExpression> objects, boolean newline) {
        boolean first = true;
        for (Iterator<? extends OWLObject> it = sort(objects).iterator(); it
                .hasNext();) {
            OWLObject desc = it.next();
            if (!first) {
                if (newline && isUseWrapping()) {
                    writeNewLine();
                }
                write(" ", MultilingualManchesterOWLSyntax.AND, " ");
            }
            first = false;
            if (desc instanceof OWLAnonymousClassExpression) {
                write("(");
            }
            desc.accept(this);
            if (desc instanceof OWLAnonymousClassExpression) {
                write(")");
            }
        }
    }

    private void writeRestriction(
            @Nonnull OWLQuantifiedDataRestriction restriction,
            @Nonnull MultilingualManchesterOWLSyntax keyword) {
        restriction.getProperty().accept(this);
        write(keyword);
        restriction.getFiller().accept(this);
    }

    private void writeRestriction(
            @Nonnull OWLQuantifiedObjectRestriction restriction,
            @Nonnull MultilingualManchesterOWLSyntax keyword) {
        restriction.getProperty().accept(this);
        write(keyword);
        boolean conjunctionOrDisjunction = false;
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            if (restriction.getFiller() instanceof OWLObjectIntersectionOf
                    || restriction.getFiller() instanceof OWLObjectUnionOf) {
                conjunctionOrDisjunction = true;
                incrementTab(4);
                writeNewLine();
            }
            write("(");
        }
        restriction.getFiller().accept(this);
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write(")");
            if (conjunctionOrDisjunction) {
                popTab();
            }
        }
    }

    private <V extends OWLObject> void writeRestriction(
            @Nonnull OWLHasValueRestriction<V> restriction,
            @Nonnull OWLPropertyExpression p) {
        p.accept(this);
        write(MultilingualManchesterOWLSyntax.VALUE);
        restriction.getFiller().accept(this);
    }

    private <F extends OWLPropertyRange> void writeRestriction(
            @Nonnull OWLCardinalityRestriction<F> restriction,
            @Nonnull MultilingualManchesterOWLSyntax keyword,
            @Nonnull OWLPropertyExpression p) {
        p.accept(this);
        write(keyword);
        write(Integer.toString(restriction.getCardinality()));
        writeSpace();
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write("(");
        }
        restriction.getFiller().accept(this);
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write(")");
        }
    }

    protected void write(String prefix, @Nonnull MultilingualManchesterOWLSyntax keyword,
                         String suffix) {
        write(prefix);
        write(keyword.toString());
        write(suffix);
    }

    protected void write(String prefix, String keyword,
                         String suffix) {
        write(prefix);
        write(keyword);
        write(suffix);
    }
    protected void write(@Nonnull MultilingualManchesterOWLSyntax keyword) {
        write(" ", keyword, " ");
    }

    protected void writeSectionKeyword(@Nonnull MultilingualManchesterOWLSyntax keyword) {
        write(" ", keyword, ": ");
    }

    private void writeBinaryOrNaryList(
            @Nonnull MultilingualManchesterOWLSyntax binaryKeyword,
            @Nonnull Set<? extends OWLObject> objects,
            @Nonnull MultilingualManchesterOWLSyntax naryKeyword) {
        if (objects.size() == 2) {
            Iterator<? extends OWLObject> it = objects.iterator();
            it.next().accept(this);
            write(binaryKeyword);
            it.next().accept(this);
        } else {
            writeSectionKeyword(naryKeyword);
            writeCommaSeparatedList(objects);
        }
    }

    /****** END OF WRITE METHODS******/

    /****** VISIT METHODS ******/

    //The following methods are overridden versions of those in super, rewritten so that they use keywords from
    //syntax.MultilingualManchesterOWLSyntax rather than ManchesterOWLSyntax

    /*** Class Expression support ***/
    
    //AND -> EN (Afrikaans)
    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf ce) {
        write(ce.getOperands(), true);
    }

    //OR -> OF (Afrikaans)
    @Override
    public void visit(@Nonnull OWLObjectUnionOf node) {
        boolean first = true;
        for (Iterator<? extends OWLClassExpression> it = node.getOperands()
                .iterator(); it.hasNext();) {
            OWLClassExpression op = it.next();
            if (!first) {
                write(" ", MultilingualManchesterOWLSyntax.OR, " ");
            }
            first = false;
            if (op.isAnonymous()) {
                write("(");
            }
            op.accept(this);
            if (op.isAnonymous()) {
                write(")");
            }
        }
    }

    //NOT -> NIE (Afrikaans)
    @Override
    public void visit(@Nonnull OWLObjectComplementOf node) {
        MultilingualKeyword multilingualKeyword = new MultilingualKeyword(MultilingualManchesterOWLSyntax.NOT);

        write("", multilingualKeyword.getPart1(), node.isAnonymous() ? " " : "");
        if (node.isAnonymous()) {
            write("(");
        }
        node.getOperand().accept(this);
        if (node.isAnonymous()) {
            write(")");
        }
        if (multilingualKeyword.isDouble())
            write(" "+multilingualKeyword.getPart2()); //for example, to implement the Afrikaans double negative

        // Example : has-part some (not (Branch)) => has-part sommige (nie (Branch) nie) // Afrikaans double negative
        // So, in the dico.xml (resources) we have : <NOT>nie#nie</NOT> the two parts of the keyword is separated by "#"

    }

    //SOME -> SOMMIGE (Afrikaans) Object
    @Override
    public void visit(OWLObjectSomeValuesFrom node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.SOME);
    }

    //ONLY -> SLEGS (Afrikaans) Object
    @Override
    public void visit(OWLObjectAllValuesFrom node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.ONLY);
    }

    //VALUE -> WAARDE (Afrikaans) Object
    @Override
    public void visit(@Nonnull OWLObjectHasValue node) {
        writeRestriction(node, node.getProperty());
    }

    //MIN -> TEN MINSTE (Afrikaans) Object
    @Override
    public void visit(@Nonnull OWLObjectMinCardinality node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.MIN, node.getProperty());
    }

    //EXACTLY -> PRESIES (Afrikaans) Object
    @Override
    public void visit(@Nonnull OWLObjectExactCardinality node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.EXACTLY, node.getProperty());
    }

    //MAX -> BY DIE MEESTE (Afrikaans) Object
    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.MAX, node.getProperty());
    }

    /*** Data expression support ***/

    //AND -> EN (Afrikaans)
    @Override
    public void visit(@Nonnull OWLDataIntersectionOf node) {
        write("(");
        write(node.getOperands(), MultilingualManchesterOWLSyntax.AND, false);
        write(")");
    }

    //OR -> OF (Afrikaans)
    @Override
    public void visit(@Nonnull OWLDataUnionOf node) {
        write("(");
        write(node.getOperands(), MultilingualManchesterOWLSyntax.OR, false);
        write(")");
    }

    //SOME -> SOMMIGE (Afrikaans) Data
    @Override
    public void visit(OWLDataSomeValuesFrom node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.SOME);
    }

    //ONLY -> SLEGS (Afrikaans) Data
    @Override
    public void visit(OWLDataAllValuesFrom node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.ONLY);
    }

    //VALUE -> WAARDE (Afrikaans) Data
    @Override
    public void visit(@Nonnull OWLDataHasValue node) {
        writeRestriction(node, node.getProperty());
    }

    //MIN -> TEN MINSTE (Afrikaans) Data
    @Override
    public void visit(@Nonnull OWLDataMinCardinality node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.MIN, node.getProperty());
    }

    //EXACTLY -> PRESIES (Afrikaans) Data
    @Override
    public void visit(@Nonnull OWLDataExactCardinality node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.EXACTLY, node.getProperty());
    }

    //MAX -> BY DIE MEESTE (Afrikaans) Data
    @Override
    public void visit(@Nonnull OWLDataMaxCardinality node) {
        writeRestriction(node, MultilingualManchesterOWLSyntax.MAX, node.getProperty());
    }

    /*** SUPPORT FOR GENERAL CLASS AXIOMS***/

    private boolean wrapSave;
    private boolean tabSave;

    private void setAxiomWriting() {
        wrapSave = isUseWrapping();
        tabSave = isUseTabbing();
        setUseWrapping(false);
        setUseTabbing(false);
    }

    private void restore() {
        setUseTabbing(tabSave);
        setUseWrapping(wrapSave);
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        setAxiomWriting();
        axiom.getSubClass().accept(this);
        write(MultilingualManchesterOWLSyntax.SUBCLASS_OF);
        axiom.getSuperClass().accept(this);
        restore();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        setAxiomWriting();
        writeBinaryOrNaryList(MultilingualManchesterOWLSyntax.EQUIVALENT_TO, axiom.getClassExpressions(),
                MultilingualManchesterOWLSyntax.EQUIVALENT_CLASSES);
        restore();
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        setAxiomWriting();
        writeBinaryOrNaryList(MultilingualManchesterOWLSyntax.DISJOINT_WITH, axiom.getClassExpressions(),
                MultilingualManchesterOWLSyntax.DISJOINT_CLASSES);
        restore();
    }

    // object property description Inverse (prop)
    // Inverse -> Inverso (Spanish)
    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        this.write(MultilingualManchesterOWLSyntax.INVERSE);
        this.write("(");
        property.getInverse().accept(this);
        this.write(")");
    }

    /****** END OF VISIT METHODS ******/
}
