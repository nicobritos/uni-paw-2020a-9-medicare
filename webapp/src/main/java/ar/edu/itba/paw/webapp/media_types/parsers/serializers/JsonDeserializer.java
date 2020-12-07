package ar.edu.itba.paw.webapp.media_types.parsers.serializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Predicate;

public abstract class JsonDeserializer<T> {
    protected final Predicate<String> emailValidator = s -> {
        return s.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    };

    public abstract T fromJson(JsonNode object);

    public Collection<T> fromJsonArray(ArrayNode jsonArray) {
        Collection<T> collection = new LinkedList<>();
        for (Object o : jsonArray) {
            try {
                collection.add((T) o);
            } catch (ClassCastException e) {
                collection.add(this.fromJson((JsonNode) o));
            }
        }
        return collection;
    }

    protected int getIntegerNonNull(ObjectNode objectNode, String key) {
        JsonNode node = objectNode.get(key);
        if (node.isNull())
            throw new IllegalArgumentException();
        return node.asInt();
    }

    protected String getStringNull(ObjectNode objectNode, String key) {
        return this.getString(objectNode, key, s -> true);
    }

    protected String getStringNonNull(ObjectNode objectNode, String key) {
        return this.getStringNonNull(objectNode, key, s -> true);
    }

    protected String getStringNonNull(ObjectNode objectNode, String key, Predicate<String> predicate) {
        String s = this.getString(objectNode, key, predicate);
        if (s == null)
            throw new IllegalArgumentException();
        return s;
    }

    private String getString(ObjectNode objectNode, String key, Predicate<String> predicate) {
        JsonNode node = objectNode.get(key);
        if (node.isNull() || !predicate.test(node.asText()))
            return null;
        return node.asText();
    }
}