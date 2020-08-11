package hznu.edu.recipesharing;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AddRecipeFragment extends Fragment {
    private EditText r_name;
    private EditText r_content;
    private EditText r_tag;
    private Button r_upload;
    private Button r_upload_clear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.add_r_fragment, container,false);
        r_name = (EditText)view.findViewById(R.id.r_name_input);
        r_content = (EditText)view.findViewById(R.id.r_content_input);
        r_tag = (EditText)view.findViewById(R.id.r_tag_input);
        r_upload = (Button)view.findViewById(R.id.r_upload);
        r_upload_clear = (Button)view.findViewById(R.id.r_upload_clear);
        r_upload_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r_name.getText().clear();
                r_content.getText().clear();
                r_tag.getText().clear();
                Toast.makeText(getContext(),"输入已清空！",Toast.LENGTH_SHORT).show();
            }
        });
        r_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r_name.getText().toString().equals("") || r_content.getText().toString().equals("") || r_tag.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "输入不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String name = r_name.getText().toString();
                            String content = r_content.getText().toString();
                            String tag = r_tag.getText().toString();
                            Recipe r = new Recipe();
                            r.setRecipe_name(name);
                            content = content.replaceAll("\n", "<br />");
                            content = content.replaceAll("\r", "<br />");
                            r.setRecipe_content(content);
                            r.setRecipe_tag(tag);
                            UserUtils userUtils = new UserUtils(getContext());
                            int msg = userUtils.uploadRecipe(r);
                            mHandler.sendEmptyMessage(msg);
                        }
                    }).start();
                }
            }
        });
        return view;


    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(getContext(),"菜谱上传成功！",Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(getContext(),"菜谱上传失败！",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
