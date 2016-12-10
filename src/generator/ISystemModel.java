package generator;

import java.util.List;

/**
 * Created by lamd on 12/9/2016.
 */
public interface ISystemModel {
    List<? extends IClassModel> getClasses();
}
