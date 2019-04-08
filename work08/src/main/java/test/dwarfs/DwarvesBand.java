package test.dwarfs;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DwarvesBand {
    Collection<Dwarf> dwarves = new LinkedList<>();
    int a =1;
    Integer aq = 21;
    double ee=456;
    Double www = 234.0;
    public Collection<Dwarf> getDwarves()
    {
        return new LinkedList<>(dwarves);
    }

    public void addDwarf(Dwarf dwarf)
    {
        this.dwarves.add(dwarf);
    }
}
