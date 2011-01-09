package com.papp;

public interface PaddleDB {
  public static final String DATABASE_NAME = "paddle.db";
  public static final int DATABASE_VERSION = 1;
  static final String SERIES_TABLE_NAME = "series";
  static final String CLUB_TABLE_NAME = "club";
  static final String PLAYER_TO_SERIES_TABLE_NAME = "player_to_series";
  static final String TEAM_TABLE_NAME = "team";
  static final String MATCHES_TABLE_NAME = "matches";

  static final String captains_query ="select * from captains where player_id = ?";
  static final String players_by_series_n_club_query = "select p.* from player p, player_to_series s, player_to_club c where p._id = s.player_id and p._id = c.player_id and s.series_id = ? and c.club_id = ?";
  static final String player_query = "select * from player where _id = ?";
  static final String series_by_player_id_query= "select s.* from series s, player_to_series p where p.series_id = s._id and p.player_id = ?";
  static final String club_by_player_id_query="select c.* from club c, player_to_club p where p.club_id = c._id and p.player_id = ?";
  static final String me_query="select player_id from me";
}