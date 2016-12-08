package main.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.objectweb.asm.tree.ClassNode;

public class ClassModel implements Visitable<ClassModel> {
	private final ASMServiceProvider asmServiceProvider;
	private final ClassNode asmClassNode;

	private final boolean important;
	private final ClassType classType;
	private final Modifier modifier;
	private final boolean isFinal;
	private ClassModel superClass;
	private Collection<ClassModel> interfaces;
	private Iterable<MethodModel> constructors;
	private Iterable<MethodModel> methods;
	private Iterable<FieldModel> fileds;

	public ClassModel(ASMServiceProvider asmServiceProvider, ClassNode asmClassNode, boolean important) {
		this.asmServiceProvider = asmServiceProvider;
		this.asmClassNode = asmClassNode;
		this.important = important;
		// TODO: fix those
		this.classType = ClassType.CONCRETE;
		this.modifier = Modifier.PUBLIC;
		this.isFinal = false;
	}

	public void tranceInheritanceChain() {
		if (asmClassNode.superName != null)
			superClass = asmServiceProvider.getClassByName(asmClassNode.superName);
	}

	public ClassModel getSuperClass() {
		return superClass;
	}

	public String getName() {
		return asmClassNode.name;
	}

	public ClassType getType() {
		return classType;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public boolean isImportant() {
		return important;
	}

	public Iterable<ClassModel> getInterfaces() {
		if (interfaces == null) {
			interfaces = new ArrayList<ClassModel>();
			@SuppressWarnings("unchecked")
			List<String> ls = asmClassNode.interfaces;
			for (String s : ls) {
				interfaces.add(asmServiceProvider.getClassByName(s));
			}
		}
		return interfaces;
	}

	public Iterable<MethodModel> getConstructors() {
		if (constructors == null) {

		}
		return constructors;
	}

	public Iterable<MethodModel> getMethods() {
		if (methods == null) {

		}
		return methods;
	}

	public Iterable<FieldModel> getFields() {
		if (fileds == null) {

		}
		return fileds;
	}

	public boolean isFinal() {
		return isFinal;
	}

	@Override
	public void visit(Visitor<ClassModel> visitor) {
		visitor.visit(this);
	}

}
