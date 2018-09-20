package com.netease.nim.demo.session.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.session.activity.AckMsgInfoActivity;
import com.netease.nim.demo.session.adapter.AckMsgDetailAdapter;
import com.netease.nim.demo.session.model.AckMsgViewModel;
import com.netease.nim.demo.session.viewholder.AckMsgDetailHolder;
import com.netease.nim.uikit.business.team.ui.TeamInfoGridView;
import com.netease.nim.uikit.common.adapter.TAdapterDelegate;
import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.TeamMsgAckInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 群未读人员界面
 * Created by winnie on 2018/3/19.
 */

