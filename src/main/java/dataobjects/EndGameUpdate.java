package dataobjects;

import java.util.List;

public class EndGameUpdate implements DataObject {
    
    private RoundUpdate roundUpdate;
    private List<PlayerData> winners;

    public EndGameUpdate() {

    }
    public RoundUpdate getRoundUpdate() {
        return roundUpdate;
    }

    public void setRoundUpdate(RoundUpdate roundUpdate) {
        this.roundUpdate = roundUpdate;
    }
    
    public List<PlayerData> getWinners() {
        return winners;
    }

    public void setWinners(List<PlayerData> winners) {
        this.winners = winners;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EndGameUpdate))
            return false;

        EndGameUpdate that = (EndGameUpdate) o;

        if (getRoundUpdate() != null ? !getRoundUpdate().equals(that.getRoundUpdate()) : that.getRoundUpdate() != null) 
            return false;
        return getWinners() != null ? !getWinners().equals(that.getWinners()) : that.getWinners() == null;
    }
}
