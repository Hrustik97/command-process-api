package command.process.api.option;

public class NonArgumentOption extends Option {

    private Boolean active;

    public NonArgumentOption(String[] aliases, String description) {
        super(aliases, description);
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    @Override
    public void deactivate() {
        this.active = false;
    }

    public Boolean isActive() {
        return this.active;
    }

    @Override
    public boolean isMandatory() {
        return false;
    }

    @Override
    public Object getValue() {
        return this.isActive();
    }

    @Override
    public String helpMessageRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (String alias : this.aliases) {
            sb.append(alias).append(" ");
        }
        sb.append(": ").append(this.description).append(" (Optional)");
        return sb.toString();
    }

}
