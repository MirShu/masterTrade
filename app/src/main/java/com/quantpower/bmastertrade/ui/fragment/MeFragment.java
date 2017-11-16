package com.quantpower.bmastertrade.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quantpower.bmastertrade.R;
import com.quantpower.bmastertrade.ui.activity.CollectionActivity;
import com.quantpower.bmastertrade.ui.activity.OpinionActivity;
import com.quantpower.bmastertrade.ui.activity.ReadingActivity;
import com.quantpower.bmastertrade.ui.activity.SettingActivity;
import com.quantpower.bmastertrade.utils.Constants;
import com.quantpower.bmastertrade.utils.UIHelper;

/**
 * Created by ShuLin on 2017/5/18.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MeFragment extends Fragment {
    public static MeFragment newInstance(String s) {
        MeFragment homeFragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    private TextView tvMeCollection;
    private TextView tvMeReading;
    private TextView tvMeOpinion;
    private TextView tvMeSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        tvMeCollection = (TextView) view.findViewById(R.id.tv_me_collection);
        tvMeReading = (TextView) view.findViewById(R.id.tv_me_reading);
        tvMeOpinion = (TextView) view.findViewById(R.id.tv_me_opinion);
        tvMeSetting = (TextView) view.findViewById(R.id.tv_me_setting);
        tvMeCollection.setOnClickListener(v -> UIHelper.startActivity(getActivity(), CollectionActivity.class));
        tvMeReading.setOnClickListener(v -> UIHelper.startActivity(getActivity(), ReadingActivity.class));
        tvMeOpinion.setOnClickListener(v -> UIHelper.startActivity(getActivity(), OpinionActivity.class));
        tvMeSetting.setOnClickListener(v -> UIHelper.startActivity(getActivity(), SettingActivity.class));
        return view;
    }
}
