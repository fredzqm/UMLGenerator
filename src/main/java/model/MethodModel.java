package model;

import analyzer.utility.IMethodModel;
import model.TypeParser.MethodSignatureParseResult;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
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
    private final boolean isSynthetic;
    private final MethodType methodtype;

    private final TypeModel returnType;
    private final Signature signature;

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
        this.methodtype = MethodType.parse(asmMethodNode.name, access);
        this.modifier = Modifier.parse(access);
        this.isFinal = Modifier.parseIsFinal(access);
        this.isStatic = Modifier.parseIsStatic(access);
        this.isSynthetic = Modifier.parseIsSynthetic(access);
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
        getGenericList().forEach((param) -> paramMap.put(param.getName(), param));
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

    @Override
    public boolean isSynthetic() {
        return isSynthetic;
    }

    public Signature getSignature() {
        return signature;
    }

    @Override
    public boolean hasSameSignature(IMethodModel methodModel) {
        if (methodModel instanceof MethodModel) {
            MethodModel m = (MethodModel) methodModel;
            return signature.equals(m.signature);
        }
        return false;
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
            for (int i = 1; i < args.size(); i++)
                sb.append(",").append(args.get(i));
        }
        return String.format("%s %s(%s)", returnType.toString(), getName(), sb.toString());
    }

    public List<InstructionModel> getInstructions() {
        if (instructions == null) {
            instructions = new ArrayList<>();
            InsnList insnList = asmMethodNode.instructions;
            ListIterator<AbstractInsnNode> itr = insnList.iterator();
            while (itr.hasNext()) {
                AbstractInsnNode insn = itr.next();
                InstructionModel i = InstructionModel.parseInstruction(this, insn);
                if (i != null) {
                    instructions.add(i);
                }
            }
        }
        return instructions;
    }

    @Override
    public Collection<FieldModel> getAccessedFields() {
        Collection<FieldModel> accessedFields = new HashSet<>();
        for (InstructionModel instr : getInstructions()) {
            if (instr instanceof InstructionField) {
                InstructionField instructionField = InstructionField.class.cast(instr);
                accessedFields.add(instructionField.getAccessComponent());
            }
        }
        return accessedFields;
    }

    @Override
    public Collection<MethodModel> getCalledMethods() {
        Collection<MethodModel> calledMethods = new HashSet<>();
        for (InstructionModel instr : getInstructions()) {
            if (instr instanceof InstructionMethod) {
                InstructionMethod instructionMethod = InstructionMethod.class.cast(instr);
                calledMethods.add(instructionMethod.getAccessComponent());
            }
        }
        return calledMethods;
    }
}