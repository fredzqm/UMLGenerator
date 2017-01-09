package model;

import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representing the signature of a method. It has two parts -- name and argument
 * list types
 *
 * @author zhang
 */
class Signature {
    private final List<TypeModel> args;
    private final String name;
    private int hashCode;
    
    /**
     * creates a Signature
     *
     * @param returnType
     * @param argumentList
     * @param name
     */
    public Signature(List<TypeModel> argumentList, String name) {
        this.args = argumentList;
        this.name = name;
    }
    
    public static Signature parse(String name, String desc) {
        List<TypeModel> args = new ArrayList<>();
        for (Type argType : Type.getArgumentTypes(desc)) {
            args.add(TypeParser.parse(argType));
        }
        return new Signature(args, name);
    }
    
    public List<TypeModel> getArguments() {
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
        return String.format("%s(%s)", name, args.toString());
    }
    
}