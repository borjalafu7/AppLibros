package com.borjalapa.applibros.dummy;

import com.borjalapa.applibros.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<LibroItem> libros = new ArrayList<LibroItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, LibroItem> ITEM_MAP = new HashMap<String, LibroItem>();

    private static final int COUNT = 25;
/*
    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createLibroItem(i));
        }
    }

    private static void addItem(LibroItem item) {
        libros.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static LibroItem createLibroItem(int position) {
        return new LibroItem(String.valueOf(position), "Prueba " + position, makeDetails(position),makeDetails(position),makeDetails(position),makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
*/
    /**
     * A dummy item representing a piece of content.
     */
    public static class LibroItem {
        public final String id;
        public final String author;
        public final String description;
        public final String publication_date;
        public final String title;
        public final String url_image;

        public LibroItem(String id, String author, String description, String publication_date, String title, String url_image) {
            this.id = id;
            this.author = author;
            this.description = description;
            this.publication_date = publication_date;
            this.title = title;
            this.url_image = url_image;
        }

        @Override
        public String toString() {
            return this.author + " " + this.title;
        }

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getDescription() {
            return description;
        }

        public String getPublication_date() {
            return publication_date;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl_image() {
            return url_image;
        }
    }
}