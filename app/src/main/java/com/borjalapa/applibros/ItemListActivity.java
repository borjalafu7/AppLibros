package com.borjalapa.applibros;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.borjalapa.applibros.dummy.DummyContent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final ImageView imageView = findViewById(R.id.imageview);
        final TextView texto = findViewById(R.id.id_text);
/*
        String nombreImagen = "nombreimagen"; //Sin extensión
        Context context = imageView.getContext();
        int id = context.getResources().getIdentifier(
                nombreImagen,
                "drawable",
                context.getPackageName()
        );

        imageView.setImageResource(id);

         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Pulsa para cargar los libros", Snackbar.LENGTH_LONG)
                        .setAction("Cargar", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String DATE_PATTERN = "yyyy-MM-dd'T'hh:mm:ss";
                                Gson gson = new GsonBuilder().setDateFormat(DATE_PATTERN).disableHtmlEscaping().create();

                                InputStream raw = getResources().openRawResource(R.raw.datos_json);
                                Reader rd = new BufferedReader(new InputStreamReader(raw));
                                Type listType = new TypeToken<List<DummyContent.LibroItem>>() {}.getType();
                                Gson gson2 = new Gson();
                                List<DummyContent.LibroItem> Libros = gson2.fromJson(rd, listType);
                                DummyContent.libros = Libros;

                                for ( int i=0;i<Libros.size();i++){
                                    DummyContent.ITEM_MAP.put(Libros.get(i).id, Libros.get(i));
                                }

                                View recyclerView = findViewById(R.id.item_list);
                                assert recyclerView != null;
                                setupRecyclerView((RecyclerView) recyclerView);

                            }
                        }).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.libros, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<DummyContent.LibroItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.LibroItem item = (DummyContent.LibroItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_DATE, item.publication_date);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<DummyContent.LibroItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).author);
            holder.mContentView.setText(mValues.get(position).title);
            String NameImagen = mValues.get(position).url_image;
            int imagenID = mParentActivity.getResources().getIdentifier(NameImagen, "drawable", mParentActivity.getPackageName());
            holder.image.setImageResource(imagenID);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final ImageView image;
            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                image = (ImageView) view.findViewById(R.id.imageview);
            }
        }
    }
}