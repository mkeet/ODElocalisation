package za.ac.uct.cs.multilingualClassDescription.spanish.syntax;

/**
 * Author: Toky Raboanary<br>
 * UCT - University of Cape Town<br>
 * Computer Science Department<br>
 * Date: 13-Nov-2019<br><br>
 *
 *     This class is used to process double negative keywords (Afrikaans)
 */

public class MultilingualKeyword {
    private MultilingualManchesterOWLSyntax keyword;
    private String[] split;

    public MultilingualKeyword(MultilingualManchesterOWLSyntax keyword) {
        this.keyword = keyword;
        String str = this.keyword.toString();
        this.split = str.split("#"); // separation key is #
    }
    public Boolean isDouble() {
        if (this.split.length == 2)
            return true;
        return false;
    }
    public String getPart1() {
        return this.split[0];
    }
    public String getPart2() {
        if (this.split.length>1)
            return this.split[1];
        return "";
    }
}
