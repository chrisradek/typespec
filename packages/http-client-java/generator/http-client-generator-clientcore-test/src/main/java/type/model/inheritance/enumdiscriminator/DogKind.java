// Code generated by Microsoft (R) TypeSpec Code Generator.

package type.model.inheritance.enumdiscriminator;

import io.clientcore.core.annotation.Metadata;
import io.clientcore.core.util.ExpandableEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * extensible enum type for discriminator.
 */
public final class DogKind implements ExpandableEnum<String> {
    private static final Map<String, DogKind> VALUES = new ConcurrentHashMap<>();

    private static final Function<String, DogKind> NEW_INSTANCE = DogKind::new;

    /**
     * Species golden.
     */
    @Metadata(generated = true)
    public static final DogKind GOLDEN = fromValue("golden");

    private final String value;

    private DogKind(String value) {
        this.value = value;
    }

    /**
     * Creates or finds a DogKind.
     * 
     * @param value a value to look for.
     * @return the corresponding DogKind.
     * @throws IllegalArgumentException if value is null.
     */
    @Metadata(generated = true)
    public static DogKind fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("'value' cannot be null.");
        }
        return VALUES.computeIfAbsent(value, NEW_INSTANCE);
    }

    /**
     * Gets known DogKind values.
     * 
     * @return Known DogKind values.
     */
    @Metadata(generated = true)
    public static Collection<DogKind> values() {
        return new ArrayList<>(VALUES.values());
    }

    /**
     * Gets the value of the DogKind instance.
     * 
     * @return the value of the DogKind instance.
     */
    @Metadata(generated = true)
    @Override
    public String getValue() {
        return this.value;
    }

    @Metadata(generated = true)
    @Override
    public String toString() {
        return Objects.toString(this.value);
    }

    @Metadata(generated = true)
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Metadata(generated = true)
    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }
}
