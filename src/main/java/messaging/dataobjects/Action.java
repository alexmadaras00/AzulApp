package messaging.dataobjects;


import model.TileColor;

public class Action implements DataObject {
    private ActionType type;
    private TileColor color;
    private int amount;
    private Location from;
    private Location to;

    public Action() {
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public TileColor getColor() {
        return color;
    }

    public void setColor(TileColor color) {
        this.color = color;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Action))
            return false;

        Action action = (Action) o;

        if (getAmount() != action.getAmount())
            return false;
        if (getType() != action.getType())
            return false;
        if (getColor() != action.getColor())
            return false;
        if (getFrom() != null ? !getFrom().equals(action.getFrom()) : action.getFrom() != null)
            return false;
        return getTo() != null ? getTo().equals(action.getTo()) : action.getTo() == null;
    }

}
