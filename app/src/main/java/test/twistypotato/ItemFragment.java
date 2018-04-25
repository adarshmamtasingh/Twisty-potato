package test.twistypotato;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import test.twistypotato.dummy.DummyContent;
import test.twistypotato.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {

    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        final TextView total_cost = view.findViewById(R.id.total_cost);
        final RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final List<CartItem> list = new ArrayList<>();
//        list.add(new CartItem("test", 30, 1));
//        list.add(new CartItem("test1", 30, 1));
//        list.add(new CartItem("test2", 30, 1));
//        list.add(new CartItem("test3", 30, 1));

        final MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(list);

        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("cart")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();
                        int total = 0;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            CartItem item = snapshot.getValue(CartItem.class);
                            list.add(item);

                            total += item.price * item.quantity;

                        }

                        total_cost.setText("" + total);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
