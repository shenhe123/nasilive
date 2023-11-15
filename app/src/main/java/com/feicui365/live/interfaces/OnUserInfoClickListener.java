package com.feicui365.live.interfaces;

import com.feicui365.live.model.entity.RankAnchorItem;
import com.feicui365.live.model.entity.RankItem;

public interface OnUserInfoClickListener {

    void onAnchorAvatarClick(RankAnchorItem live);

    void onAvatarClick(RankItem live);
}
