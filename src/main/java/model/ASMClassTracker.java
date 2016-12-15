package model;

/**
 * The concrete ASM service provider that will recursively parse all related
 * classes request. {@see NonRecursiveASMParser}
 *
 * @author zhang
 */
public interface ASMClassTracker extends ASMServiceProvider {

    /**
     * add those classes to ASMClassTracker by explicitly
     *
     * @param importClassesList
     */
    void addClasses(Iterable<String> importClassesList);

    /**
     * @return an iterable of all the classes this ASM Class Tracker currently
     * contains
     */
    public Iterable<ClassModel> getClasses();
}
