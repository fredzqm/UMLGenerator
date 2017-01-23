package model;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.*;

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
            throw new RuntimeException("ASM parsing of " + className + " failed.");
        }
    }

    /**
     * @param importClassesList
     *            the important list of classes that are required explicitly
     * @param blackList
     *            the list of packages that we do not want to include
     * @param flag
     *            the flag indicating how much related classes should get
     *            recursively parsed
     * @return the collection of classes acquired based on the requirement
     */
    public static Collection<ClassModel> getClasses(Iterable<String> importClassesList, Collection<String> blackList,
            int flag) {
        Collection<ClassModel> classesList = new HashSet<>();
        Queue<ClassModel> unextended = new LinkedList<>();
        for (String impClass : importClassesList) {
            ClassModel explicitClass = getClassByName(impClass);
            classesList.add(explicitClass);
            unextended.add(explicitClass);
        }
        while (!unextended.isEmpty()) {
            ClassModel model = unextended.poll();
            if ((flag & RECURSE_SUPERCLASS) != 0)
                addToBothList(classesList, unextended, blackList, model.getSuperClass());
            if ((flag & RECURSE_INTERFACE) != 0)
                addToBothList(classesList, unextended, blackList, model.getInterfaces());
            if ((flag & RECURSE_DEPENDS_ON) != 0 || (flag & RECURSE_HAS_A) != 0) {
                for (FieldModel field : model.getFields()) {
                    TypeModel type = field.getFieldType();
                    addToBothList(classesList, unextended, blackList, type.getDependentClass());
                }
                if ((flag & RECURSE_DEPENDS_ON) != 0) {
                    for (MethodModel method : model.getMethods()) {
                        addToBothList(classesList, unextended, blackList, method.getReturnType().getDependentClass());
                        List<TypeModel> args = method.getArguments();
                        for (TypeModel t : args)
                            addToBothList(classesList, unextended, blackList, t.getDependentClass());
                        for (InstructionModel inst : method.getInstructions()) {
                            for (TypeModel t : inst.getDependentClass())
                                addToBothList(classesList, unextended, blackList, t.getDependentClass());
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
            Collection<String> blackList, ClassModel x) {
        if (x != null) {
            if (canBeAdded(classesList, blackList, x)) {
                classesList.add(x);
                unextended.add(x);
            }
        }
    }

    private static void addToBothList(Collection<ClassModel> classesList, Collection<ClassModel> unextended,
            Collection<String> blackList, Iterable<? extends ClassModel> ls) {
        for (ClassModel x : ls) {
            if (canBeAdded(classesList, blackList, x)) {
                classesList.add(x);
                unextended.add(x);
            }
        }
    }

    private static boolean canBeAdded(Collection<ClassModel> classesList, Collection<String> blackList, ClassModel x) {
        if (classesList.contains(x))
            return false;
        for (String black : blackList) {
            if (x.getName().startsWith(black))
                return false;
        }
        return true;
    }
}
