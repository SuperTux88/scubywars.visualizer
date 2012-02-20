package de.tdng2011.game.visualizer

import de.tdng2011.game.library.connection.{ RelationTypes, AbstractClient }
import de.tdng2011.game.library.World

class Client(hostname: String) extends AbstractClient(hostname, RelationTypes.VisualizerNG) {

  Visualizer.start

  new SoundPlayer("/background.wav", true).playSound

  def processWorld(world: World) {
    Visualizer !! world
  }

  override def processScoreBoard(scoreBoard: Map[Long, Int]) {
    Visualizer.currentScores = scoreBoard.toList.sort { (a, b) => a._2 > b._2 }
  }

  override def processNames(names: Map[Long, String]) {
    Visualizer.currentNames = names
  }

  override def shotSpawnedEvent() {
    val x = scala.util.Random.shuffle(Sounds.shotSounds)
    var first = x(0)
    first.playSound
  }

  override def playerKilledEvent() {
    val x = scala.util.Random.shuffle(Sounds.killSounds)
    var first = x(0)
    first.playSound
  }
}

object Sounds {
  val shotSounds = 0 to 4 map (i => new SoundPlayer("/shotSpawned" + i + ".wav"))
  val killSounds = 0 to 5 map (i => new SoundPlayer("/playerKilled" + i + ".wav"))
}

