package models;

import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Signature {
    private final TypeModel ret;
    private final List<TypeModel> args;
    private final String name;
    private int hashCode;

    public Signature(TypeModel returnType, List<TypeModel> argumentList, String name) {
        if (returnType == null)
            throw new RuntimeException("return type cannot be null");
        if (argumentList == null)
            throw new RuntimeException("argument list cannot be null");
        this.ret = returnType;
        this.args = argumentList;
        this.name = name;
    }

    public static Signature parse(ASMServiceProvider serviceProvider, String name, String desc) {
        TypeModel ret = TypeModel.parse(serviceProvider, Type.getReturnType(desc));
        List<TypeModel> args = new ArrayList<>();
        for (Type argType : Type.getArgumentTypes(desc)) {
            args.add(TypeModel.parse(serviceProvider, argType));
        }
        return new Signature(ret, args, name);
    }

    public TypeModel getReturnType() {
        return ret;
    }

    public List<TypeModel> getArgumentList() {
        return args;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Signature) {
            Signature o = (Signature) obj;
            return Objects.equals(name, o.name) && args.equals(o.args);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = name.hashCode() * 31 + args.hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return String.format("%s %s(%s)", ret, name, args.toString());
    }

}
