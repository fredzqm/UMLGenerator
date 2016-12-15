package model;

public interface ASMServiceProvider {

    /**
     * ASMServiceProvider manages the parsing of ASM model model under the hood.
     * It should not be directly used by external user, but provides
     * {@link ClassModel to {@link SystemModel} transparently. It also
     * facilitates lazy initialization
     * <p>
     * It keeps track of all the ClassModel has been parsed, and make sure one
     * class is only parsed once.
     *
     * @param name
     * @return
     */
    ClassModel getClassByName(String name);
}
