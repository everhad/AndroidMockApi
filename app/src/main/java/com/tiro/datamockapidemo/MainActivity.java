package com.tiro.datamockapidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tiro.datamockapidemo.data.Task;
import com.tiro.datamockapidemo.dataapi.DataApiCallback;
import com.tiro.datamockapidemo.dataapi.DataApiManager;
import com.tiro.datamockapidemo.dataapi.task.ITaskApi;

import java.util.List;

public class MainActivity extends Activity {
    private TextView tv_data;
    private boolean requesting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_data = (TextView) findViewById(R.id.tv_data);

        getData();
    }

    private void getData() {
        if (requesting) return;
        requesting = true;

        ITaskApi api = DataApiManager.ofTask();

        if (api != null) {
            api.getTasks(new DataApiCallback<List<Task>>() {
                @Override
                public void onSuccess(List<Task> data) {
                    // show data
                    StringBuilder sb = new StringBuilder("Success：\n");
                    for (int i = 0; i < data.size(); i++) {
                        sb.append(data.get(i).name).append("\n");
                    }

                    tv_data.setText(sb.toString());
                    requesting = false;
                }

                @Override
                public void onError(Throwable e) {
                    // show error
                    tv_data.setText("Error：\n" + e.getMessage());
                    requesting = false;
                }

                @Override
                public void onStart() {
                    // show loading
                    tv_data.setText("Loading...");
                }
            });
        } else {
            requesting = false;
        }
    }

    public void onRefreshClick(View view) {
        getData();
    }
}
