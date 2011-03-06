package de.tdng2011.game.visualizer

import de.tdng2011.game.library.connection.{RelationTypes, AbstractClient}
import de.tdng2011.game.library.{World, ScoreBoard}

class Client(hostname : String) extends AbstractClient(hostname, RelationTypes.Visualizer) {

  Visualizer.start

  def processWorld(world : World) {
    Visualizer !! world
  }

  override def processScoreBoard(scoreBoard : ScoreBoard) {
    Visualizer !! scoreBoard
  }
}

