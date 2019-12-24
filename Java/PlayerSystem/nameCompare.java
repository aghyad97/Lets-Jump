package PlayerSystem;

import java.util.Comparator;
// class to compare according to the names using Comparator
class nameCompare implements Comparator<Player>
{
    @Override
    public int compare(Player s1, Player s2)
    {
        return s1.name.compareTo(s2.name);
    }
}

