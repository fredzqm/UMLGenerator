package config;

import utility.IFilter;
import utility.Modifier;

/**
 * Created by lamd on 1/11/2017.
 */
public class ModifierFilter implements IFilter<Modifier> {
    @Override
    public boolean filter(Modifier data) {
        return false;
    }
}
