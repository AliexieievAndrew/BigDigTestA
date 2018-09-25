package comw.example.user.testtaska.TabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import comw.example.user.testtaska.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private EditText textImageLink;
    private TextView btnOk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container,false);

        textImageLink = (EditText) v.findViewById(R.id.text_image_link);
        btnOk = (TextView) v.findViewById(R.id.btn_ok);
        btnOk.setClickable(true);
        btnOk.setOnClickListener(this);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick (View view) {
        if (!isLink(textImageLink.getText().toString())){
            Toast.makeText(getContext(),"должна быть ссылка",Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent("IntentFilterImageActivity");
            intent.putExtra("link", textImageLink.getText().toString());
            startActivity(intent);
        }
    }
    private boolean isLink(String link){
        String regEx = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return link.matches(regEx);
    }
}