package main.java.com.termux.app.dialog;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.termux.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import androidx.annotation.NonNull;
import main.java.com.termux.datat.TermuxData;
import main.java.com.termux.utils.UUtils;

/**
 * @author ZEL
 * @create By ZEL on 2020/10/15 17:29
 **/
public class EditTextDialog extends BaseDialogCentre {

    private TextView cancel;
    private TextView commit;
    private TextView start;
    private TextView commit_file;
    private String id;
    private EditText edit_text;
    public EditTextDialog(@NonNull Context context) {
        super(context);
        this.id = id;
    }

    public EditTextDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    void initViewDialog(View mView) {

        cancel = mView.findViewById(R.id.cancel);
        commit = mView.findViewById(R.id.commit);
        commit_file = mView.findViewById(R.id.commit_file);
        start = mView.findViewById(R.id.start);
        edit_text = mView.findViewById(R.id.edit_text);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSystemSwitchListener.switchEdit(edit_text);


            }
        });

        commit_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileNameDialog fileNameDialog = new FileNameDialog(mContext);
                fileNameDialog.show();
                fileNameDialog.setOnSaveFileNameListener(new FileNameDialog.OnSaveFileNameListener() {
                    @Override
                    public void saveFileName(String name) {
                        fileNameDialog.dismiss();
                        UUtils.setFileString(new File(Environment.getExternalStorageDirectory(),"/xinhao/windows_config/" + name + ".txt"),edit_text.getText().toString());
                    }
                });


            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartCommand.startCommand(edit_text.getText().toString());
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mEditStartCommand.editCommand(edit_text.getText().toString());

            }
        });
    }

    @Override
    int getContentView() {
        return R.layout.dialog_edit;
    }

    public void setStringData(String data){


        edit_text.setText(data);


    }

    public void setVisible(boolean isVisible){

        if(!isVisible){
            cancel.setVisibility(View.INVISIBLE);
            commit.setVisibility(View.INVISIBLE);
            commit_file.setVisibility(View.VISIBLE);
        }else{
            cancel.setVisibility(View.VISIBLE);
            commit.setVisibility(View.VISIBLE);
            commit_file.setVisibility(View.GONE);
        }

    }

    private SystemSwitchListener mSystemSwitchListener;

    public void setSystemSwitchListener(SystemSwitchListener mSystemSwitchListener){
        this.mSystemSwitchListener = mSystemSwitchListener;
    }

    private EditStartCommand mEditStartCommand;

    public void setEditStartCommand(EditStartCommand mEditStartCommand){
        this.mEditStartCommand = mEditStartCommand;
    }

    private StartCommand mStartCommand;

    public void setStartCommand(StartCommand mStartCommand){
        this.mStartCommand = mStartCommand;
    }

    //恢复默认值
    public interface SystemSwitchListener{

        void switchEdit(EditText editText);

    }
    //修改
    public interface EditStartCommand{
        void editCommand(String string);

    }

    //启动

    public interface StartCommand{
        void startCommand(String string);

    }
}
