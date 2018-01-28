package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailByCheckedModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.ui.widget.SimpleAlertDialog;

import com.yibogame.util.ToastUtil;
import com.yibogame.util.ValidateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/12/2.
 */

public class SubmitCensorShipRecordAdapter extends RecyclerView.Adapter<SubmitCensorShipRecordAdapter.ViewHolder> {
    private Context context;
    private List<CompanyDetailByCheckedModel> lists;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public SubmitCensorShipRecordAdapter(Context context, List<CompanyDetailByCheckedModel> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public SubmitCensorShipRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_submit_censorship_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubmitCensorShipRecordAdapter.ViewHolder holder, int position) {
        if (lists.size() > 0) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long now = lists.get(position).getCreateTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(now);
            holder.tvDateContent.setText(formatter.format(calendar.getTime()));
            holder.tvCheckedUnitContent.setText(lists.get(position).getCompanyName());
            holder.tvExport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showExportDialog(lists.get(position).getSecrecyInspectId());
                }
            });
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    /***
     * 导出
     * @param secrecyInspectId
     */
    private void showExportDialog(int secrecyInspectId) {
        new SimpleAlertDialog(context)
                .visibleEditText()
                .title("导出")
                .message("请输入一个您需要导出的邮箱地址")
                .setOkOnClickListener("确定", (dialog, view) -> {
                    String email = dialog.getEditText().getText().toString();
                    if (!ValidateUtil.getInstance().validateEmail(email)) {
                        ToastUtil.showShort("请输入一个正确的邮箱地址哦！");
                        return;
                    }
                    export(dialog,secrecyInspectId, email);
                }).setCancelOnClickListener("取消", null)
                .show();
//        final EditText et = new EditText(context);
//        et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//        new AlertDialog.Builder(context)
//                .setTitle("导出")
//                .setMessage("请输入一个您需要导出的邮箱地址")
////                .setIcon(android.R.drawable.ic_dialog_info)
//                .setView(et)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        String input = et.getText().toString();
//                        if (!ValidateUtil.getInstance().validateEmail(input)) {
//                            ToastUtil.showShort("请输入一个正确的邮箱地址哦！");
//                            return;
//                        }
//                        Map<Object, Object> map = new HashMap<>();
//                        map.put("secrecyInspectId", secrecyInspectId);
//                        map.put("toMail", input);
//                        APIService.getInstance().showExportDialog(map, new DoOnSubscriber<String>() {
//                            @Override
//                            public void doOnSubscriber() {
//
//                            }
//
//                            @Override
//                            public void onCompleted() {
//                                ToastUtil.showShort("导出成功！");
//                            }
//
//                            @Override
//                            public void onNext(String s) {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                super.onError(e);
//                                ToastUtil.showShort(e.getMessage());
//                            }
//                        });
//                    }
//                })
//                .setNegativeButton("取消", null)
//                .show();


    }

    private void export(SimpleAlertDialog dialog, int secrecyInspectId, String email) {
        Map<Object, Object> map = new HashMap<>();
        map.put("secrecyInspectId", secrecyInspectId);
        map.put("toMail", email);
        APIService.getInstance()
                .export(map, new SimpleSubscriber<String>() {
                    @Override
                    public void onResponse(String response) {
                        ToastUtil.showShort("导出成功！");
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCheckedUnitContent, tvDateContent, tvExport;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCheckedUnitContent = (TextView) itemView.findViewById(R.id.tv_checked_unit_content);
            tvDateContent = (TextView) itemView.findViewById(R.id.tv_date_content);
            tvExport = (TextView) itemView.findViewById(R.id.tv_export);//导出按钮
        }
    }
}
