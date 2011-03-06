package de.tdng2011.game.visualizer

case class ScoreBoardChangedMessage(scoreBoard : Map[Long, Int])
case class NamesChangedMessage(names : Map[Long, String])

