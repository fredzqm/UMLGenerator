package model;

public class NonRecursiveASMParser extends AbstractASMParser {

    /**
     * @param importClassesList important classes for this parser
     * @return ASMParser instance that already parsed the important classes.
     */
    public NonRecursiveASMParser(Iterable<String> importClassesList) {
        super(importClassesList);
    }

    @Override
    public ClassModel getClassByName(String name) {
        if (hasAlreadyParse(name))
            return getClassByName(name, false);
        return null;
    }

}
