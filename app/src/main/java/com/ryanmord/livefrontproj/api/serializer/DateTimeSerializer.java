/*
 * COPYRIGHT (C) CABOODLE INC. - ALL RIGHTS RESERVED
 *
 * ALL INFORMATION CONTAINED HEREIN IS THE PROPERTY OF CABOODLE INC.
 * UNAUTHORIZED COPYING, DISSEMINATION, OR REPRODUCTION OF THIS MATERIAL
 * IS STRICTLY FORBIDDEN UNLESS PRIOR WRITTEN PERMISSION IS OBTAINED
 * FROM CABOODLE INC.
 *
 */

package com.ryanmord.livefrontproj.api.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;


/**
 * Custom serializer for for converting JSON date data into DateTime objects,
 * and vise-versa.
 */
public class DateTimeSerializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    /**
     * Format of date items in JSON responses
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * DateTimeFormatter used to convert Date objects to JsonElements
     */
    private static final DateTimeFormatter UTC_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT).withZone(DateTimeZone.UTC);


    /**
     * {@inheritDoc}
     *
     * @param src       Source object to serialize
     * @param typeOfSrc Type of the source object
     * @param context   Context for serialization
     *
     * @return  Fully-serialized JsonElement for passed source item
     */
    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
        String dateString = UTC_FORMATTER.print(src);
        return new JsonPrimitive(dateString);
    }


    /**
     * {@inheritDoc}
     *
     * @param json      Json data to be deserialized to java object
     * @param typeOfT   Type of object to be deserialized into
     * @param context   Context for deserialization
     * @return
     */
    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        String date = json.getAsJsonPrimitive().getAsString();
        return DateTime.parse(date, UTC_FORMATTER);
    }

}
