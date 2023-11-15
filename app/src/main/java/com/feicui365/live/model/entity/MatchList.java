package com.feicui365.live.model.entity;

import java.io.Serializable;

public class MatchList implements Serializable {
    private String id;
    private String matchevent_id;
    private String status;
    private long time;
    private long kickoff_time;
    private String home_id;
    private String home_rank;
    private String home_score;
    private int home_halfscore;
    private int home_redcard;
    private int home_yellowcard;
    private int home_corner;
    private String home_otscrore;
    private String home_pointscore;
    private String away_id;
    private String away_rank;
    private String away_score;
    private int away_halfscore;
    private int away_redcard;
    private int away_yellowcard;
    private int away_corner;
    private String away_otscrore;
    private String away_pointscore;
    private String explain;
    private String isneutrality;
    private String round;
    private String match_season;
    private String is_animation;
    private String is_video;
    private String stage_id;
    private String stage_group;
    private String stage_round;
    private String attented;
    private Matchevent matchevent;
    private Awayteam awayteam;
    private Hometeam hometeam;
    private String animation_url;

    public String getAnimation_url() {
        return animation_url;
    }

    public void setAnimation_url(String animation_url) {
        this.animation_url = animation_url;
    }

    public String getAttented() {
        return attented;
    }

    public void setAttented(String attented) {
        this.attented = attented;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchevent_id() {
        return matchevent_id;
    }

    public void setMatchevent_id(String matchevent_id) {
        this.matchevent_id = matchevent_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getKickoff_time() {
        return kickoff_time;
    }

    public void setKickoff_time(long kickoff_time) {
        this.kickoff_time = kickoff_time;
    }

    public String getHome_id() {
        return home_id;
    }

    public void setHome_id(String home_id) {
        this.home_id = home_id;
    }

    public String getHome_rank() {
        return home_rank;
    }

    public void setHome_rank(String home_rank) {
        this.home_rank = home_rank;
    }

    public String getHome_score() {
        return home_score;
    }

    public void setHome_score(String home_score) {
        this.home_score = home_score;
    }

    public int getHome_halfscore() {
        return home_halfscore;
    }

    public void setHome_halfscore(int home_halfscore) {
        this.home_halfscore = home_halfscore;
    }

    public int getHome_redcard() {
        return home_redcard;
    }

    public void setHome_redcard(int home_redcard) {
        this.home_redcard = home_redcard;
    }

    public int getHome_yellowcard() {
        return home_yellowcard;
    }

    public void setHome_yellowcard(int home_yellowcard) {
        this.home_yellowcard = home_yellowcard;
    }

    public int getHome_corner() {
        return home_corner;
    }

    public void setHome_corner(int home_corner) {
        this.home_corner = home_corner;
    }

    public String getHome_otscrore() {
        return home_otscrore;
    }

    public void setHome_otscrore(String home_otscrore) {
        this.home_otscrore = home_otscrore;
    }

    public String getHome_pointscore() {
        return home_pointscore;
    }

    public void setHome_pointscore(String home_pointscore) {
        this.home_pointscore = home_pointscore;
    }

    public String getAway_id() {
        return away_id;
    }

    public void setAway_id(String away_id) {
        this.away_id = away_id;
    }

    public String getAway_rank() {
        return away_rank;
    }

    public void setAway_rank(String away_rank) {
        this.away_rank = away_rank;
    }

    public String getAway_score() {
        return away_score;
    }

    public void setAway_score(String away_score) {
        this.away_score = away_score;
    }

    public int getAway_halfscore() {
        return away_halfscore;
    }

    public void setAway_halfscore(int away_halfscore) {
        this.away_halfscore = away_halfscore;
    }

    public int getAway_redcard() {
        return away_redcard;
    }

    public void setAway_redcard(int away_redcard) {
        this.away_redcard = away_redcard;
    }

    public int getAway_yellowcard() {
        return away_yellowcard;
    }

    public void setAway_yellowcard(int away_yellowcard) {
        this.away_yellowcard = away_yellowcard;
    }

    public int getAway_corner() {
        return away_corner;
    }

    public void setAway_corner(int away_corner) {
        this.away_corner = away_corner;
    }

    public String getAway_otscrore() {
        return away_otscrore;
    }

    public void setAway_otscrore(String away_otscrore) {
        this.away_otscrore = away_otscrore;
    }

    public String getAway_pointscore() {
        return away_pointscore;
    }

    public void setAway_pointscore(String away_pointscore) {
        this.away_pointscore = away_pointscore;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getIsneutrality() {
        return isneutrality;
    }

    public void setIsneutrality(String isneutrality) {
        this.isneutrality = isneutrality;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getMatch_season() {
        return match_season;
    }

    public void setMatch_season(String match_season) {
        this.match_season = match_season;
    }

    public String getIs_animation() {
        return is_animation;
    }

    public void setIs_animation(String is_animation) {
        this.is_animation = is_animation;
    }

    public String getIs_video() {
        return is_video;
    }

    public void setIs_video(String is_video) {
        this.is_video = is_video;
    }

    public String getStage_id() {
        return stage_id;
    }

    public void setStage_id(String stage_id) {
        this.stage_id = stage_id;
    }

    public String getStage_group() {
        return stage_group;
    }

    public void setStage_group(String stage_group) {
        this.stage_group = stage_group;
    }

    public String getStage_round() {
        return stage_round;
    }

    public void setStage_round(String stage_round) {
        this.stage_round = stage_round;
    }

    public Matchevent getMatchevent() {
        return matchevent;
    }

    public void setMatchevent(Matchevent matchevent) {
        this.matchevent = matchevent;
    }

    public Awayteam getAwayteam() {
        return awayteam;
    }

    public void setAwayteam(Awayteam awayteam) {
        this.awayteam = awayteam;
    }

    public Hometeam getHometeam() {
        return hometeam;
    }

    public void setHometeam(Hometeam hometeam) {
        this.hometeam = hometeam;
    }
}
