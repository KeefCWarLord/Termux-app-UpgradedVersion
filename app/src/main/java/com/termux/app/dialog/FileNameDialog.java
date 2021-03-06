package main.java.com.termux.app.dialog;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.termux.R;

import java.io.File;

import androidx.annotation.NonNull;
import main.java.com.termux.utils.UUtils;

/**
 * @author ZEL
 * @create By ZEL on 2020/10/20 14:23
 **/
public class FileNameDialog extends BaseDialogCentre {
    private File mWinConfig = new File(Environment.getExternalStorageDirectory(),"/xinhao/windows_config/");
    private EditText edit_text;
    private TextView commit;
    private TextView cancel;
    public FileNameDialog(@NonNull Context context) {
        super(context);
    }

    public FileNameDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    void initViewDialog(View mView) {
        edit_text = mView.findViewById(R.id.edit_text);
        commit = mView.findViewById(R.id.commit);
        cancel = mView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edit_text.getText().toString();
                if(s.trim().isEmpty()){
                    UUtils.showMsg(UUtils.getString(R.string.文件名不能为空));
                    return;
                }
                File[] files = mWinConfig.listFiles();
                if(files != null && files.length > 0) {
                    for (int i = 0; i < files.length; i++) {

                        File file = files[i];
                        if (file.getName().equals(s)) {
                            UUtils.showMsg(UUtils.getString(R.string.目录下有一个相同文件));
                            return;
                        }

                    }
                }


                if(mOnSaveFileNameListener!= null){
                    mOnSaveFileNameListener.saveFileName(s);
                }

            }
        });

    }

    @Override
    int getContentView() {
        return R.layout.dialog_save_name;
    }
    private OnSaveFileNameListener mOnSaveFileNameListener;
    public void setOnSaveFileNameListener(OnSaveFileNameListener mOnSaveFileNameListener){
        this.mOnSaveFileNameListener = mOnSaveFileNameListener;
    }

    public interface OnSaveFileNameListener{

        void saveFileName(String name);

    }
}
