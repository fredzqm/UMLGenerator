package generator;

/**
 * Created by lamd on 12/9/2016.
 */
public interface ISystemModel {
    Iterable<? extends IClassModel> getClasses();
}
