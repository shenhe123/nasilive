package com.feicui365.live.interfaces;

import com.feicui365.live.model.entity.RankAnchorItem;
import com.feicui365.live.model.entity.RankItem;

public interface OnFollowClickListener {
    void onFollowAnchorClick(RankAnchorItem live);
    void onAnchorAvatarClick(RankItem live);
    void onFollowClick(RankItem live);
    void onAvatarClick(RankItem live);
}
