package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import analyzer.IMethodModel;
import model.TypeParser.MethodSignatureParseResult;
import utility.MethodType;
import utility.Modifier;

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
                AbstractInsnNode insn = itr.next();
                InstructionModel i = InstructionModel.parseInstruction(this, insn);
                if (i != null) {
                    instructions.add(i);
                }
            }
        }
        return instructions;
    }

}