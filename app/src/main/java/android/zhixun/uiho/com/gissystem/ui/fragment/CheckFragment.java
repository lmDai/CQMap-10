package android.zhixun.uiho.com.gissystem.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.ui.activity.CensorshipRegisterActivity;
import android.zhixun.uiho.com.gissystem.ui.activity.LocalFileActivity;
import android.zhixun.uiho.com.gissystem.ui.activity.SubmitCensorShipRecordActivity;

/**
 * Created by parcool on 2016/9/2.
 */

@Keep
public class CheckFragment extends Fragment {

    private View view;
    private RelativeLayout rlCensorshipRegister,rlLocalFile,rlSubmitCensorshipRecord;
    public CheckFragment() {
        Bundle args = new Bundle();
        args.putString("name", CheckFragment.class.getSimpleName());
        setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_check, container, false);
            initViews();
            //设置监听
            setListener();
        }

        return view;
    }

    private void setListener() {
        Intent intent = new Intent();
        rlCensorshipRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("tid",-1);
                intent.setClass(getActivity(), CensorshipRegisterActivity.class);
                startActivity(intent);
            }
        });
        rlLocalFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(getActivity(), LocalFileActivity.class);
                startActivity(intent);
            }
        });
        rlSubmitCensorshipRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(getActivity(), SubmitCensorShipRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        rlCensorshipRegister = (RelativeLayout) view.findViewById(R.id.relativeLayout1);
        rlLocalFile = (RelativeLayout) view.findViewById(R.id.relativeLayout2);
        rlSubmitCensorshipRecord = (RelativeLayout) view.findViewById(R.id.relativeLayout3);
    }


//    @Override
//    protected Store initActionsCreatorAndStore() {
//        return null;
//    }
//
//    @Override
//    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
