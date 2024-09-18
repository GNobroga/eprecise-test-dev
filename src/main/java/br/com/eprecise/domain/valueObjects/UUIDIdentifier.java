package br.com.eprecise.domain.valueObjects;

import java.util.Objects;
import java.util.UUID;

import lombok.Getter;

@Getter
public class UUIDIdentifier implements ValueObject {

    private UUID uuid;

    public UUIDIdentifier() {
        this.uuid = UUID.randomUUID();
    }
    
    public UUIDIdentifier(String uuid) {
        if (Objects.isNull(uuid)) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        this.uuid = UUID.fromString(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UUIDIdentifier that = (UUIDIdentifier) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
