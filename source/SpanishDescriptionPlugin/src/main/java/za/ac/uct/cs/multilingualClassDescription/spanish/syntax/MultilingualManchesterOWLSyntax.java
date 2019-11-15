package za.ac.uct.cs.multilingualClassDescription.spanish.syntax;

import javax.annotation.Nonnull;

import za.ac.uct.cs.multilingualClassDescription.spanish.EnumWord;
import za.ac.uct.cs.multilingualClassDescription.spanish.Utils;

/**
 * Created by Casey on 20-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public enum MultilingualManchesterOWLSyntax {

    /*AND           */      AND(Utils.getDico().getWord(EnumWord.AND), false, false, false, false, true, Utils.getDico().getWord(EnumWord.AND)),
    /*OR            */      OR(Utils.getDico().getWord(EnumWord.OR), false, false, false, false, true, Utils.getDico().getWord(EnumWord.OR)),
    /*SOME          */      SOME(Utils.getDico().getWord(EnumWord.SOME), false, false, false, true, false, Utils.getDico().getWord(EnumWord.SOME)),
    /*ONLY          */      ONLY(Utils.getDico().getWord(EnumWord.ONLY), false, false, false, true, false, Utils.getDico().getWord(EnumWord.ONLY)),
    /*NOT           */      NOT(Utils.getDico().getWord(EnumWord.NOT), false, false, false, false, true, Utils.getDico().getWord(EnumWord.NOT)),
    /*VALUE         */      VALUE(Utils.getDico().getWord(EnumWord.VALUE), false, false, false, true, false, Utils.getDico().getWord(EnumWord.VALUE)),
    /*MIN           */      MIN(Utils.getDico().getWord(EnumWord.MIN), false, false, false, true, false, Utils.getDico().getWord(EnumWord.MIN)),
    /*MAX           */      MAX(Utils.getDico().getWord(EnumWord.MAX), false, false, false, true,  false, Utils.getDico().getWord(EnumWord.MAX)),
    /*EXACTLY       */      EXACTLY(Utils.getDico().getWord(EnumWord.EXACTLY), false, false, false, true,  false, Utils.getDico().getWord(EnumWord.EXACTLY)),
    /*DISJOINT_WITH */      DISJOINT_WITH(Utils.getDico().getWord(EnumWord.DISJOINT_WITH), false, true,  true,  false, false),
    /*SUBCLASS_OF   */      SUBCLASS_OF(Utils.getDico().getWord(EnumWord.SUBCLASS_OF), false, true,  true,  false, false),
    /*EQUIVALENT_TO */      EQUIVALENT_TO(Utils.getDico().getWord(EnumWord.EQUIVALENT_TO), false, true,  true,  false, false),
    /*EQUIVALENT_CLASSES*/  EQUIVALENT_CLASSES(Utils.getDico().getWord(EnumWord.EQUIVALENT_CLASSES), false, true,  true,  false, false),
    /*DISJOINT_CLASSES*/    DISJOINT_CLASSES(Utils.getDico().getWord(EnumWord.DISJOINT_CLASSES), true,  true,  true,  false, false),
    /*INVERSE*/             INVERSE(Utils.getDico().getWord(EnumWord.INVERSE), false, false, true, false, false, Utils.getDico().getWord(EnumWord.INVERSE));


    private final boolean frameKeyword;
    private final boolean sectionKeyword;
    private final boolean axiomKeyword;
    private final boolean classExpressionQuantifierKeyword;
    private final boolean classExpressionConnectiveKeyword;

    @Nonnull
    private final String rendering;
    @Nonnull
    private final String keyword;

    MultilingualManchesterOWLSyntax(@Nonnull String rendering, boolean frameKeyword,
                                    boolean sectionKeyword, boolean axiomKeyword,
                                    boolean classExpressionQuantifierKeyword,
                                    boolean classExpressionConnectiveKeyword, @Nonnull String keyword) {
        this.rendering = rendering;
        this.frameKeyword = frameKeyword;
        this.sectionKeyword = sectionKeyword;
        this.axiomKeyword = axiomKeyword;
        this.classExpressionConnectiveKeyword = classExpressionConnectiveKeyword;
        this.classExpressionQuantifierKeyword = classExpressionQuantifierKeyword;
        this.keyword = keyword;
    }

    MultilingualManchesterOWLSyntax(@Nonnull String rendering, boolean frameKeyword,
                                    boolean sectionKeyword, boolean axiomKeyword,
                                    boolean classExpressionQuantifierKeyword,
                                    boolean classExpressionConnectiveKeyword) {
        this(rendering, frameKeyword, sectionKeyword, axiomKeyword,
                classExpressionQuantifierKeyword,
                classExpressionConnectiveKeyword, rendering + ':');
    }

    public String toString() {return rendering;}

    public boolean isFrameKeyword() {
        return this.frameKeyword;
    }

    public boolean isSectionKeyword() {
        return this.sectionKeyword;
    }

    public boolean isAxiomKeyword() {
        return this.axiomKeyword;
    }

    public boolean isClassExpressionConnectiveKeyword() {
        return this.classExpressionConnectiveKeyword;
    }

    public boolean isClassExpressionQuantifierKeyword() {
        return this.classExpressionQuantifierKeyword;
    }

    public String keyword() {
        return this.rendering;
    }


}
