package model;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/**
 * The concrete ASM service provider that will recursively parse all related
 * classes request. {@see NonRecursiveASMParser}
 *
 * @author zhang
 */
class ASMParser {
    public static int RECURSE_SUPERCLASS = 0x02;
    public static int RECURSE_INTERFACE = 0x04;
    public static int RECURSE_HAS_A = 0x08;
    public static int RECURSE_DEPENDS_ON = 0x10;

    private static Map<String, ClassModel> map = new HashMap<>();

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
    public static ClassModel getClassByName(String className) {
        className = className.replace(".", "/");
        if (map.containsKey(className))
            return map.get(className);
        try {
            ClassReader reader = new ClassReader(className);
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);
            ClassModel model = new ClassModel(classNode);
            map.put(className, model);
            return model;
        } catch (IOException e) {
            System.err.println("ASM parsing of " + className + " failed.");
            return null;
        }
    }

    /**
     * @param importClassesList
     *            the important list of classes that are required explicitly
     * @param recursiveFlag
     *            the flag indicating how much related classes should get
     *            recursively parsed
     * @return the collection of classes acquired based on the requirement
     */
    public static Collection<ClassModel> getClasses(Iterable<String> importClassesList, int recursiveFlag) {
        Collection<ClassModel> classesList = new HashSet<>();
        Queue<ClassModel> unextended = new LinkedList<>();
        for (String impClass : importClassesList) {
            ClassModel explicitClass = getClassByName(impClass);
            classesList.add(explicitClass);
            unextended.add(explicitClass);
        }
        while (!unextended.isEmpty()) {
            ClassModel model = unextended.poll();
            if ((recursiveFlag & RECURSE_SUPERCLASS) != 0)
                addToBothList(classesList, unextended, model.getSuperClass());
            if ((recursiveFlag & RECURSE_INTERFACE) != 0)
                addToBothList(classesList, unextended, model.getInterfaces());
            if ((recursiveFlag & RECURSE_DEPENDS_ON) != 0 || (recursiveFlag & RECURSE_HAS_A) != 0) {
                for (FieldModel field : model.getFields()) {
                    TypeModel type = field.getFieldType();
                    addToBothList(classesList, unextended, type.getDependentClass());
                }
                if ((recursiveFlag & RECURSE_DEPENDS_ON) != 0) {
                    for (MethodModel method : model.getMethods()) {
                        addToBothList(classesList, unextended, method.getReturnType().getDependentClass());
                        List<TypeModel> args = method.getArguments();
                        for (TypeModel t : args)
                            addToBothList(classesList, unextended, t.getDependentClass());
                        for (FieldModel t : method.getAccessedFields())
                            addToBothList(classesList, unextended, t.getBelongTo());
                        for (MethodModel m : method.getCalledMethods()) {
                            addToBothList(classesList, unextended, m.getBelongTo());
                        }
                    }
                }
            }
        }
        return classesList;
    }

    public static ClassModel getObject() {
        return ASMParser.getClassByName("java.lang.Object");
    }
    
    public static ClassModel getEnum() {
        return ASMParser.getClassByName("java.lang.Enum");
    }

    private static void addToBothList(Collection<ClassModel> classesList, Collection<ClassModel> unextended,
            ClassModel x) {
        if (x != null) {
            if (!classesList.contains(x)) {
                classesList.add(x);
                unextended.add(x);
            }
        }
    }

    private static void addToBothList(Collection<ClassModel> classesList, Collection<ClassModel> unextended,
            Iterable<? extends ClassModel> ls) {
        for (ClassModel x : ls) {
            if (!classesList.contains(x)) {
                classesList.add(x);
                unextended.add(x);
            }
        }
    }

}
