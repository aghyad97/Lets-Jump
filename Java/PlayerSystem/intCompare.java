package PlayerSystem;

import java.util.Comparator;
// class to compare according to the scores using Comparator
class marksCompare implements Comparator<Player>
{
    @Override
    public int compare(Player p1, Player p2)
    {
        return p2.score - p1.score;
    }
}