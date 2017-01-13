package model;

import analyzer.IMethodModel;
import model.TypeParser.MethodSignatureParseResult;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import utility.MethodType;
import utility.Modifier;

import java.util.*;

/**
 * Representing method in java program
 *
 * @author zhang
 */
class MethodModel implements IMethodModel {
    private final MethodNode asmMethodNode;
    private final ClassModel belongsTo;

    private final Modifier modifier;
    private final boolean isFinal;
    private final boolean isStatic;
    private final MethodType methodtype;

    private final TypeModel returnType;
    private final Signature signature;

    private Collection<MethodModel> dependenOnMethod;
    private Collection<FieldModel> dependenOnField;
    private List<GenericTypeParam> genericParams;
    private List<InstructionModel> instructions;

    /**
     * constructs an method model given the class it belongs to and the asm
     * method node
     *
     * @param belongsTo
     * @param methodNode
     */
    MethodModel(ClassModel belongsTo, MethodNode methodNode) {
        this.belongsTo = belongsTo;
        this.asmMethodNode = methodNode;
        int access = methodNode.access;
        this.modifier = Modifier.parse(access);
        this.isFinal = Modifier.parseIsFinal(access);
        this.isStatic = Modifier.parseIsStatic(access);
        this.methodtype = MethodType.parse(asmMethodNode.name, access);
        if (asmMethodNode.signature == null) {
            this.genericParams = Collections.emptyList();
            this.returnType = TypeParser.parse(Type.getReturnType(methodNode.desc));
            this.signature = Signature.parse(methodNode.name, methodNode.desc);
        } else {
            // System.out.println(asmMethodNode.signature);
            MethodSignatureParseResult rs = TypeParser.parseMethodSignature(asmMethodNode.signature);
            this.genericParams = rs.getParameters();
            Map<String, GenericTypeParam> paramMap = getParamsMap();
            this.returnType = rs.getReturnType().replaceTypeVar(paramMap);
            List<TypeModel> arguments = new ArrayList<>(rs.getArguments().size());
            for (TypeModel t : rs.getArguments())
                arguments.add(t.replaceTypeVar(paramMap));
            this.signature = new Signature(arguments, asmMethodNode.name);
        }
    }

    List<GenericTypeParam> getGenericList() {
        return genericParams;
    }

    Map<String, GenericTypeParam> getParamsMap() {
        Map<String, GenericTypeParam> paramMap = belongsTo.getParamsMap();
        for (GenericTypeParam p : getGenericList()) {
            paramMap.put(p.getName(), p);
        }
        return paramMap;
    }

    public ClassModel getBelongTo() {
        return belongsTo;
    }

    public String getName() {
        switch (methodtype) {
            case CONSTRUCTOR:
                return belongsTo.getName();
            case STATIC_INITIALIZER:
                return "static";
            default:
                return signature.getName();
        }
    }

    public MethodType getMethodType() {
        return methodtype;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public Signature getSignature() {
        return signature;
    }

    public List<TypeModel> getArguments() {
        return signature.getArguments();
    }

    public TypeModel getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<TypeModel> args = getArguments();
        if (args.size() > 0) {
            sb.append(args.get(0));
            for (int i = 0; i < args.size(); i++)
                sb.append("," + args.get(i));
        }
        return String.format("%s %s(%s)", returnType.toString(), getName(), sb.toString());
    }

    public List<InstructionModel> getInstructions() {
        if (instructions == null) {
            instructions = new ArrayList<>();
            InsnList insnList = asmMethodNode.instructions;
            ListIterator<AbstractInsnNode> itr = insnList.iterator();
            while (itr.hasNext()) {
                InstructionModel i = InstructionModel.parseInstruction(this, itr.next());
                instructions.add(i);
            }
        }
        return instructions;
    }

    public Collection<MethodModel> getCalledMethods() {
        if (dependenOnMethod == null) {
            getInstructions();
            dependenOnMethod = new HashSet<>();
            InsnList instructions = asmMethodNode.instructions;
            for (int i = 0; i < instructions.size(); i++) {
                AbstractInsnNode insn = instructions.get(i);
                if (insn instanceof MethodInsnNode) {
                    MethodInsnNode methodCall = (MethodInsnNode) insn;
                    TypeModel type = TypeParser.parse(Type.getObjectType(methodCall.owner));
                    ClassModel destClass = ASMParser.getClassByName(type.getName());
                    if (destClass == null)
                        continue;
                    Signature signature = Signature.parse(methodCall.name, methodCall.desc);
                    MethodModel method = destClass.getMethodBySignature(signature);
                    if (method == null)
                        continue;
                    dependenOnMethod.add(method);
                }
            }
        }
        return dependenOnMethod;
    }

    public Collection<FieldModel> getAccessedFields() {
        if (dependenOnField == null) {
            dependenOnField = new HashSet<>();
            InsnList instructions = asmMethodNode.instructions;
            for (int i = 0; i < instructions.size(); i++) {
                AbstractInsnNode insn = instructions.get(i);
                if (insn instanceof FieldInsnNode) {
                    FieldInsnNode fiedlCall = (FieldInsnNode) insn;
                    TypeModel type = TypeParser.parse(Type.getObjectType(fiedlCall.owner));
                    ClassModel destClass = ASMParser.getClassByName(type.getName());
                    if (destClass == null)
                        continue;
                    FieldModel field = destClass.getFieldByName(fiedlCall.name);
                    if (field == null)
                        continue;
                    dependenOnField.add(field);
                }
            }
        }
        return dependenOnField;
    }

}