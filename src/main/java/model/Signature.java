package model;

import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Representing the signature of a method. It has two parts -- name and argument
 * list types
 *
 * @author zhang
 */
public class Signature {
    private final List<TypeModel> args;
    private final String name;
    private int hashCode;

    /**
     * creates a Signature
     *
     * @param argumentList
     * @param name
     */
    Signature(List<TypeModel> argumentList, String name) {
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

    List<TypeModel> getArguments() {
        return args;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Signature) {
            Signature o = Signature.class.cast(obj);
            if (Objects.equals(name, o.name)) {
                Iterator<TypeModel> a = args.iterator();
                Iterator<TypeModel> b = args.iterator();
                while (a.hasNext() && b.hasNext()) {
                    if (!Objects.equals(a.next().eraseGenericType(), b.next().eraseGenericType())) {
                        return false;
                    }
                }
                return !a.hasNext() && !b.hasNext();
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = name.hashCode() * 31;
            for (TypeModel t : args) {
                hashCode = hashCode * 31 + t.eraseGenericType().hashCode();
            }
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", name, args.toString());
    }

}