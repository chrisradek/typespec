package type.union;

/**
 * Defines values for EnumsOnlyCasesUd.
 */
public enum EnumsOnlyCasesUd {
    /**
     * Enum value up.
     */
    UP("up"),

    /**
     * Enum value down.
     */
    DOWN("down");

    /**
     * The actual serialized value for a EnumsOnlyCasesUd instance.
     */
    private final String value;

    EnumsOnlyCasesUd(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a EnumsOnlyCasesUd instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed EnumsOnlyCasesUd object, or null if unable to parse.
     */
    public static EnumsOnlyCasesUd fromString(String value) {
        if (value == null) {
            return null;
        }
        EnumsOnlyCasesUd[] items = EnumsOnlyCasesUd.values();
        for (EnumsOnlyCasesUd item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.value;
    }
}
